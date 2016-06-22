package com.cloudcraftgaming.hideandseekplus.game;

import com.cloudcraftgaming.hideandseekplus.arena.Arena;
import com.cloudcraftgaming.hideandseekplus.arena.ArenaManager;
import com.cloudcraftgaming.hideandseekplus.data.ArenaDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.UUID;

/**
 * Created by Nova Fox on 6/15/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus.
 */
public class GameBoardManager {
    private static ScoreboardManager manager = Bukkit.getScoreboardManager();

    public static void createScoreboard(int id) {
        Scoreboard board = manager.getNewScoreboard();
        Objective boardObj = board.registerNewObjective("Board", "dummy");
        boardObj.setDisplayName(ArenaDataManager.getDisplayName(id));
        boardObj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Team seekers = board.registerNewTeam("seekers");
        seekers.setDisplayName(ChatColor.DARK_RED + "Seekers");
        seekers.setAllowFriendlyFire(true);
        Team hiders = board.registerNewTeam("hiders");
        hiders.setDisplayName(ChatColor.DARK_GREEN + "Hiders");
        hiders.setAllowFriendlyFire(true);
        Team spectators = board.registerNewTeam("spectators");
        spectators.setDisplayName(ChatColor.GOLD + "Spectators");
        spectators.setAllowFriendlyFire(false);
        Score specScoreFake = boardObj.getScore(ChatColor.GOLD + "Spectators");
        specScoreFake.setScore(600);
        Score seekerScoreFake = boardObj.getScore(ChatColor.DARK_RED + "Seekers");
        seekerScoreFake.setScore(400);
        Score hiderScoreFake = boardObj.getScore(ChatColor.DARK_GREEN + "Hiders");
        hiderScoreFake.setScore(300);
        Arena arena = ArenaManager.getManager().getArena(id);
        arena.setScoreboard(board);
    }
    public static void addPlayers(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        if (!(arena.getScoreboard() == null)) {
            for (UUID uuid : arena.getPlayers()) {
                Player p = Bukkit.getPlayer(uuid);
                Scoreboard gameBoard = arena.getScoreboard();
                Objective object = gameBoard.getObjective("Board");
                if (arena.getSeeker() == uuid) {
                    Team team = gameBoard.getTeam("seekers");
                    team.addPlayer(p);
                    Score pScore = object.getScore(ChatColor.DARK_RED + p.getName());
                    pScore.setScore(399);
                } else {
                    Team team = gameBoard.getTeam("hiders");
                    team.addPlayer(p);
                    Score pScore = object.getScore(ChatColor.DARK_GREEN + p.getName());
                    pScore.setScore(299);
                }
                arena.setScoreboard(gameBoard);
            }
            for (UUID uuid : arena.getSpectators()) {
                Player p = Bukkit.getPlayer(uuid);
                Scoreboard gameBoard = arena.getScoreboard();
                Objective object = gameBoard.getObjective("Board");
                Team team = gameBoard.getTeam("spectators");
                team.addPlayer(p);
                Score pScore = object.getScore(ChatColor.GOLD + p.getName());
                pScore.setScore(599);
                arena.setScoreboard(gameBoard);
            }
        }
    }
    public static void removePlayers(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        for (UUID uuid : arena.getPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            player.setScoreboard(manager.getNewScoreboard());
        }
        for (UUID uuid : arena.getSpectators()) {
            Player player = Bukkit.getPlayer(uuid);
            player.setScoreboard(manager.getNewScoreboard());
        }
    }
    public static void updateBoards(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        for (UUID uuid : arena.getPlayers()) {
            if (!(arena.getScoreboard() == null)) {
                Player p = Bukkit.getPlayer(uuid);
                p.setScoreboard(arena.getScoreboard());
            }
        }
        for (UUID uuid : arena.getSpectators()) {
            if (!(arena.getScoreboard() == null)) {
                Player p = Bukkit.getPlayer(uuid);
                p.setScoreboard(arena.getScoreboard());
            }
        }
    }
    public static void addPlayer(Player player, int id, String type) {
        Arena arena = ArenaManager.getManager().getArena(id);
        Scoreboard board = arena.getScoreboard();
        if (!(board == null)) {
            Objective object = board.getObjective("Board");
            if (type.equalsIgnoreCase("Player")) {
                if (arena.getSeeker() == player.getUniqueId()) {
                    Team team = board.getTeam("seekers");
                    team.addPlayer(player);
                    Score pScore = object.getScore(ChatColor.DARK_RED + player.getName());
                    pScore.setScore(399);
                    arena.setScoreboard(board);
                } else {
                    Team team = board.getTeam("hiders");
                    team.addPlayer(player);
                    Score pScore = object.getScore(ChatColor.DARK_GREEN + player.getName());
                    pScore.setScore(299);
                    arena.setScoreboard(board);
                }
            } else if (type.equalsIgnoreCase("Spectator")) {
                Team team = board.getTeam("spectators");
                team.addPlayer(player);
                Score pScore = object.getScore(ChatColor.GOLD + player.getName());
                pScore.setScore(599);
                arena.setScoreboard(board);
            }
            updateBoards(id);
        }
    }
    public static void removePlayer(Player player, int id, String type) {
        Arena arena = ArenaManager.getManager().getArena(id);
        Scoreboard board = arena.getScoreboard();
        if (!(board == null)) {
            if (type.equalsIgnoreCase("Player")) {
                if (arena.getSeeker() == player.getUniqueId()) {
                    board.resetScores(ChatColor.DARK_RED + player.getName());
                    Team team = board.getTeam("seekers");
                    team.removePlayer(player);
                    arena.setScoreboard(board);
                } else {
                    board.resetScores(ChatColor.DARK_GREEN + player.getName());
                    Team team = board.getTeam("hiders");
                    team.removePlayer(player);
                    arena.setScoreboard(board);
                }
            } else if (type.equalsIgnoreCase("Spectator")) {
                board.resetScores(ChatColor.GOLD + player.getName());
                Team team = board.getTeam("spectators");
                team.removePlayer(player);
                arena.setScoreboard(board);
            }
            updateBoards(id);
        }
        if (player != null) {
            player.setScoreboard(manager.getNewScoreboard());
        }
    }
}
