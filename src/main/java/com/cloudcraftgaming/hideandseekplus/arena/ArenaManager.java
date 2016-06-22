package com.cloudcraftgaming.hideandseekplus.arena;

import com.cloudcraftgaming.hideandseekplus.utils.FileManager;
import com.cloudcraftgaming.hideandseekplus.Main;
import com.cloudcraftgaming.hideandseekplus.data.ArenaDataManager;
import com.cloudcraftgaming.hideandseekplus.game.GameManager;
import com.cloudcraftgaming.hideandseekplus.game.GameState;
import com.cloudcraftgaming.hideandseekplus.game.TimeManager;
import com.cloudcraftgaming.hideandseekplus.game.WinType;
import com.cloudcraftgaming.hideandseekplus.utils.Regenerator;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Nova Fox on 6/14/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus.
 */
public class ArenaManager {
    private static ArenaManager instance;

    private ArrayList<Arena> arenas = new ArrayList<>();

    private ArenaManager() {} //Prevent initialization

    public static ArenaManager getManager() {
        if (instance == null) {
            instance = new ArenaManager();
        }
        return instance;
    }

    //Getters
    public Arena getArena(int id) {
        for (Arena a : arenas) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }
    public Arena getArena(Player player) {
        for (Arena a : arenas) {
            if (a.getPlayers().contains(player.getUniqueId())) {
                return a;
            }
        }
        return null;
    }

    //Booleans/Checkers
    public Boolean arenaLoaded(int i) {
        for (Arena a : arenas) {
            if (a.getId() == i) {
                return true;
            }
        }
        return false;
    }
    public Boolean isInGame(Player player) {
        for (Arena a : arenas) {
            if (a.getPlayers().contains(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }
    public Boolean isSpectating(Player player) {
        for (Arena a : arenas) {
            if (a.getSpectators().contains(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    //Functionals
    public void loadArena(int id) {
        if (!arenaLoaded(id) && ArenaDataManager.arenaExists(id) && ArenaDataManager.canBeLoaded(id)) {
            if (FileManager.verbose()) {
                Main.plugin.getLogger().info("Loading arena id: " + id);
            }
            Arena arena = new Arena(id);
            arena.setPlayerCount(0);
            arena.setGameState(GameState.EMPTY);
            arena.setWinType(WinType.NONE);
            arena.setWaitId(0);
            arena.setStartId(0);
            arena.setGameId(0);
            arenas.add(arena);
            if (FileManager.verbose()) {
                Main.plugin.getLogger().info("Loaded arena id: " + id);
            }
        }
    }
    public void unloadArena(int id) {
        if (arenaLoaded(id)) {
            if (FileManager.verbose()) {
                Main.plugin.getLogger().info("Unloading arena id: " + id);
            }
            arenas.remove(getArena(id));
            if (FileManager.verbose()) {
                Main.plugin.getLogger().info("Unloaded arena id: " + id);
            }
        }
    }
    public void reloadArena(int id) {
        if (arenaLoaded(id)) {
            if (FileManager.verbose()) {
                Main.plugin.getLogger().info("Reloading arena id: " + id);
            }
            unloadArena(id);
            loadArena(id);
            if (FileManager.verbose()) {
                Main.plugin.getLogger().info("Reloaded arena id: " + id);
            }
        }
    }
    public void safeLoadArena(int id) {
        if (!arenaLoaded(id) && ArenaDataManager.arenaExists(id)) {
            if (FileManager.verbose()) {
                Main.plugin.getLogger().info("Safe loading arena id: " + id);
            }
            if (ArenaDataManager.canBeLoaded(id)) {
                Arena arena = new Arena(id);
                arena.setPlayerCount(0);
                arena.setGameState(GameState.EMPTY);
                arena.setWinType(WinType.NONE);
                arena.setWaitId(0);
                arena.setStartId(0);
                arena.setGameId(0);
                arenas.add(arena);
                Regenerator.regenArena(id);
                if (FileManager.verbose()) {
                    Main.plugin.getLogger().info("Successfully safe loaded arena id: " + id);
                }
            } else {
                if (FileManager.verbose()) {
                    Main.plugin.getLogger().warning("Failed to safe load arena id: " + id + ", because it is missing settings!");
                }
            }
        }
    }
    public void safeUnloadArena(int id) {
        if (arenaLoaded(id)) {
            if (FileManager.verbose()) {
                Main.plugin.getLogger().info("Safe unloading arena id: " + id);
            }
            Arena arena = getArena(id);
            if (arena.getPlayerCount() > 0) {
                GameManager.endGame(id);
            } else {
                Regenerator.regenArena(id);
            }
            if (arenas.contains(arena)) {
                arenas.remove(arena);
            }
            if (FileManager.verbose()) {
                Main.plugin.getLogger().info("Successfully safe unloaded arena id: " + id);
            }
        }
    }
    public void safeReloadArena(int id) {
        if (arenaLoaded(id)) {
            if (FileManager.verbose()) {
                Main.plugin.getLogger().info("Safe reloading arena id: " + id);
            }
            safeUnloadArena(id);
            safeLoadArena(id);
            if (FileManager.verbose()) {
                Main.plugin.getLogger().info("Successfully safe reloaded arena id: " + id);
            }
        }
    }

    public void checkPlayerCount(int id) {
        if (arenaLoaded(id)) {
            Arena arena = getArena(id);
            if (arena.getGameState().equals(GameState.EMPTY)) {
                if (arena.getPlayerCount() > 0) {
                    arena.setGameState(GameState.WAITING_FOR_PLAYERS);
                    checkPlayerCount(id);
                }
            } else if (arena.getGameState().equals(GameState.WAITING_FOR_PLAYERS)) {
                if (arena.getPlayerCount() < 1) {
                    arena.setGameState(GameState.EMPTY);
                } else if (arena.getPlayerCount() < ArenaDataManager.getMinPlayers(id)) {
                    if (arena.getWaitId() != 0) {
                        TimeManager.getManager().cancelWaitDelay(id);
                    }
                } else if (arena.getPlayerCount() >= ArenaDataManager.getMinPlayers(id)) {
                    if (arena.getWaitId() == 0) {
                        arena.setGameState(GameState.WAITING_FOR_PLAYERS);
                        TimeManager.getManager().startWaitDelay(id);
                    }
                }
            } else if (arena.getGameState().equals(GameState.STARTING)) {
                if (arena.getPlayerCount() < 1) {
                    TimeManager.getManager().cancelStartDelay(id);
                    reloadArena(id);
                } else if (arena.getPlayerCount() < ArenaDataManager.getMinPlayers(id)) {
                    TimeManager.getManager().cancelStartDelay(id);
                    arena.setGameState(GameState.WAITING_FOR_PLAYERS);
                }
            } else if (arena.getGameState().equals(GameState.INGAME)) {
                if (arena.getPlayerCount() < 1) {
                    //Arena empty? End game.
                    GameManager.endGame(id);
                } else if (arena.getPlayerCount() == 1) {
                    //Only 1 player remaining, end game as they won.
                    GameManager.endGame(id);
                } else if (!arena.getPlayers().contains(arena.getSeeker())) {
                    //Seeker must have quit, left, or died. This ends the game automatically.
                    GameManager.endGame(id);
                }
            }
        }
    }
}