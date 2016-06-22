package com.cloudcraftgaming.hideandseekplus.utils;

import com.cloudcraftgaming.hideandseekplus.Main;
import com.cloudcraftgaming.hideandseekplus.arena.Arena;
import com.cloudcraftgaming.hideandseekplus.arena.ArenaManager;
import com.cloudcraftgaming.hideandseekplus.data.ArenaDataManager;
import com.cloudcraftgaming.hideandseekplus.game.GameBoardManager;
import com.cloudcraftgaming.hideandseekplus.game.GameMessages;
import com.cloudcraftgaming.hideandseekplus.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

/**
 * Created by Nova Fox on 6/15/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus.
 */
public class PlayerHandler {
    public static void joinArena(Player player, int id) {
        if (!ArenaManager.getManager().isInGame(player)) {
            if (ArenaDataManager.arenaExists(id)) {
                Arena arena = ArenaManager.getManager().getArena(id);
                if (arena.getPlayerCount() < ArenaDataManager.getMaxPlayers(id)) {
                    if (arena.getGameState().equals(GameState.WAITING_FOR_PLAYERS) || arena.getGameState().equals(GameState.EMPTY)) {
                        if (inventoryEmpty(player)) {
                            arena.setPlayerCount(arena.getPlayerCount() + 1);
                            arena.getPlayers().add(player.getUniqueId());
                            player.teleport(ArenaDataManager.getLobbyLocation(id));
                            GameMessages.announcePlayerJoin(player);
                            ArenaManager.getManager().checkPlayerCount(id);
                            if (FileManager.verbose()) {
                                Main.plugin.getLogger().info("Player: " + player.getName() + " has joined a minigame.");
                            }
                        } else {
                            String msgOr = MessageManager.getMessagesYml().getString("Notifications.Player.HasItems");
                            player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                        }
                    } else if (arena.getGameState().equals(GameState.STARTING)) {
                        String msgOr = MessageManager.getMessagesYml().getString("Notifications.Arena.State.Starting");
                        player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                    } else if (arena.getGameState().equals(GameState.INGAME)) {
                        String msgOr = MessageManager.getMessagesYml().getString("Notifications.Arena.State.InGame");
                        player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                    } else if (arena.getGameState().equals(GameState.REGENERATING)) {
                        String msgOr = MessageManager.getMessagesYml().getString("Notifications.Arena.State.Regenerating");
                        player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                    }
                } else {
                    String msgOr = MessageManager.getMessagesYml().getString("Notifications.Arena.Full");
                    player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                }
            } else {
                String msgOr = MessageManager.getMessagesYml().getString("Notifications.ArenaDoesNotExist");
                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
            }
        } else {
            String msgOr = MessageManager.getMessagesYml().getString("Notifications.Player.InGame");
            player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
        }
    }


    public static void quitArena(Player player) {
        if (ArenaManager.getManager().isInGame(player)) {
            Arena arena = ArenaManager.getManager().getArena(player);
            player.setExhaustion(20f);
            player.getInventory().clear();
            player.setFoodLevel(20);
            player.setHealth(20);
            player.setFireTicks(0);
            player.removePotionEffect(PotionEffectType.SLOW);
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.teleport(ArenaDataManager.getQuitLocation(arena.getId()));
            GameBoardManager.removePlayer(player, arena.getId(), "Player");
            arena.getPlayers().remove(player.getUniqueId());
            arena.setPlayerCount(arena.getPlayerCount() - 1);
            GameMessages.announcePlayerQuit(arena.getId(), player);
            ArenaManager.getManager().checkPlayerCount(arena.getId());
            String msgOr = MessageManager.getMessagesYml().getString("Notifications.Player.Quit");
            player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
            if (FileManager.verbose()) {
                Main.plugin.getLogger().info("Player: " + player.getName() + " has quit a minigame.");
            }
        } else {
            String msgOr = MessageManager.getMessagesYml().getString("Notifications.Player.NotInGame");
            player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
        }
    }

    public static void hiderOutDeath(Player player) {
        Arena arena = ArenaManager.getManager().getArena(player);
        player.setExhaustion(20f);
        player.getInventory().clear();
        player.setFoodLevel(20);
        player.setHealth(20);
        player.setFireTicks(0);
        //Would teleport but the respawn listener handles that.
        GameBoardManager.removePlayer(player, arena.getId(), "Player");
        arena.getPlayers().remove(player.getUniqueId());
        arena.setPlayerCount(arena.getPlayerCount() - 1);
        ArenaManager.getManager().checkPlayerCount(arena.getId());

        String msgOr = MessageManager.getMessagesYml().getString("Notifications.Player.Out.Death.Hider");
        player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
        if (FileManager.verbose()) {
            Main.plugin.getLogger().info("Player: " + player.getName() + " has lost a minigame.");
        }
    }
    public static void seekerOutDeath(Player player) {
        Arena arena = ArenaManager.getManager().getArena(player);
        player.setExhaustion(20f);
        player.getInventory().clear();
        player.setFoodLevel(20);
        player.setHealth(20);
        player.setFireTicks(0);
        //Would teleport but the respawn listener handles that.
        GameBoardManager.removePlayer(player, arena.getId(), "Player");
        arena.getPlayers().remove(player.getUniqueId());
        arena.setPlayerCount(arena.getPlayerCount() - 1);
        ArenaManager.getManager().checkPlayerCount(arena.getId());

        String msgOr = MessageManager.getMessagesYml().getString("Notifications.Player.Out.Death.Seeker");
        player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
        if (FileManager.verbose()) {
            Main.plugin.getLogger().info("Player: " + player.getName() + " has lost a minigame.");
        }
    }
    public static void hiderOutTagged(Player hider, Player seeker) {
        Arena arena = ArenaManager.getManager().getArena(hider);
        String hiderOutMsg = MessageManager.getMessagesYml().getString("Notifications.Player.Out.Tagged.Hider");
        hider.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', hiderOutMsg));
        String seekerTagMsgOr = MessageManager.getMessagesYml().getString("Notifications.Player.Out.Tagged.Seeker");
        String seekerTagMsg = seekerTagMsgOr.replace("%player%", hider.getDisplayName());
        seeker.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', seekerTagMsg));
        GameMessages.announceHiderOutTagged(hider);

        hider.setExhaustion(20f);
        hider.getInventory().clear();
        hider.setFoodLevel(20);
        hider.setHealth(20);
        hider.setFireTicks(0);
        hider.teleport(ArenaDataManager.getEndLocation(arena.getId()));
        GameBoardManager.removePlayer(hider, arena.getId(), "Player");
        arena.getPlayers().remove(hider.getUniqueId());
        arena.setPlayerCount(arena.getPlayerCount() - 1);
        ArenaManager.getManager().checkPlayerCount(arena.getId());

        if (FileManager.verbose()) {
            Main.plugin.getLogger().info("Player: " + hider.getName() + " has lost a minigame");
        }
    }


    public static boolean inventoryEmpty(Player player) {
        for (ItemStack itemStack : player.getInventory().getArmorContents()) {
            if (!(itemStack == null)) {
                if (!(itemStack.getType().equals(Material.AIR))) {
                    return false;
                }
            }
        }
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (!(itemStack == null)) {
                if (!(itemStack.getType().equals(Material.AIR))) {
                    return false;
                }
            }
        }
        return true;
    }
    public static void sendArenaInfo(Player player, int id) {
        if (ArenaDataManager.arenaExists(id)) {
            GameState state = GameState.EMPTY;
            String players = "";
            Integer playerCount = 0;
            Boolean enabled = false;
            Boolean lateJoinAllowed = ArenaDataManager.allowsLateJoin(id);
            Integer max = ArenaDataManager.getMaxPlayers(id);
            if (ArenaManager.getManager().arenaLoaded(id)) {
                Arena arena = ArenaManager.getManager().getArena(id);
                playerCount = arena.getPlayerCount();
                if (arena.getPlayerCount() > 0) {
                    for (UUID uuid : arena.getPlayers()) {
                        Player p = Bukkit.getPlayer(uuid);
                        players = players + p.getDisplayName() + ", " + ChatColor.RESET;
                    }
                }
                state = arena.getGameState();
                enabled = true;
            }
            String headingOr = MessageManager.getMessagesYml().getString("Sign.Info.Heading");
            String heading = headingOr.replaceAll("%id%", String.valueOf(id));
            String statusString = String.valueOf(state).replaceAll("_", " ").toLowerCase();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', heading));
            player.sendMessage(ChatColor.GREEN + "Enabled: " + ChatColor.RED + enabled);
            player.sendMessage(ChatColor.GREEN + "Status: " + ChatColor.RED + statusString);
            player.sendMessage(ChatColor.GREEN + "LateJoin Allowed: " + ChatColor.RED + lateJoinAllowed);
            player.sendMessage(ChatColor.GREEN + "Player Count: " + ChatColor.RED + playerCount + "/" + max);
            player.sendMessage(ChatColor.GREEN + "Players: " + ChatColor.RED + players);
        } else {
            String msgOr = MessageManager.getMessagesYml().getString("Notifications.ArenaDoesNotExist");
            player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
        }
    }
}