package com.cloudcraftgaming.hideandseekplus.game;

import com.cloudcraftgaming.hideandseekplus.Main;
import com.cloudcraftgaming.hideandseekplus.utils.MessageManager;
import com.cloudcraftgaming.hideandseekplus.arena.Arena;
import com.cloudcraftgaming.hideandseekplus.arena.ArenaManager;
import com.cloudcraftgaming.hideandseekplus.data.ArenaDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Nova Fox on 6/15/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus.
 */
public class GameMessages {
    public static void announceWaitDelay(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        String msgOr = MessageManager.getMessagesYml().getString("Game.Announce.WaitDelay");
        Integer time = ArenaDataManager.getWaitTime(id);
        String msg = msgOr.replaceAll("%time%", String.valueOf(time));
        for (UUID pId : arena.getPlayers()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
        for (UUID pId : arena.getPlayers()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
    }
    public static void announceWaitCancel(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        String msgOr = MessageManager.getMessagesYml().getString("Game.Announce.Cancel.Wait");
        for (UUID pId : arena.getPlayers()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
        }
        for (UUID pId : arena.getSpectators()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
        }
    }
    public static void announceStartDelay(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        String msgOr = MessageManager.getMessagesYml().getString("Game.Announce.StartDelay");
        Integer time = ArenaDataManager.getStartTime(id);
        String msg = msgOr.replaceAll("%time%", String.valueOf(time));
        for (UUID pId : arena.getPlayers()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
        for (UUID pId : arena.getSpectators()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
    }
    public static void announceStartCancel(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        String msgOr = MessageManager.getMessagesYml().getString("Game.Announce.Cancel.Start");
        for (UUID pId : arena.getPlayers()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
        }
        for (UUID pId : arena.getSpectators()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
        }
    }
    public static void announceGameStart(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        String msgOr = MessageManager.getMessagesYml().getString("Game.Announce.Start");
        for (UUID pId : arena.getPlayers()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
        }
        for (UUID pId : arena.getSpectators()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
        }
    }
    public static void announceHideTimeStart(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        String msgOr = MessageManager.getMessagesYml().getString("Game.Announce.Hide.Start");
        Integer time = ArenaDataManager.getHideTime(id);
        String msg = msgOr.replaceAll("%time%", String.valueOf(time));
        for (UUID pId : arena.getPlayers()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
        for (UUID pId : arena.getSpectators()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
    }
    public static void announceHideTimeOver(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        String msgOr = MessageManager.getMessagesYml().getString("Game.Announce.Hide.End");
        for (UUID pId : arena.getPlayers()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
        }
        for (UUID pId : arena.getSpectators()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
        }
    }

    public static void announcePlayerJoin(Player player) {
        Arena arena = ArenaManager.getManager().getArena(player);
        String msgOr = MessageManager.getMessagesYml().getString("Game.Announce.Player.Join");
        String msg = msgOr.replaceAll("%player%", player.getDisplayName());
        for (UUID pId : arena.getPlayers()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
        for (UUID pId : arena.getSpectators()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
    }
    public static void announcePlayerQuit(int id, Player player) {
        Arena arena = ArenaManager.getManager().getArena(id);
        String msgOr = MessageManager.getMessagesYml().getString("Game.Announce.Player.Quit");
        String msg = msgOr.replaceAll("%player%", player.getDisplayName());
        for (UUID pId : arena.getPlayers()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
        for (UUID pId : arena.getSpectators()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
    }

    public static void announcePlayerDeathLobby(Player player) {
        Arena arena = ArenaManager.getManager().getArena(player);
        String msgOr = MessageManager.getMessagesYml().getString("Game.Announce.Death.Lobby");
        String msg = msgOr.replaceAll("%player%", player.getDisplayName());
        for (UUID pId : arena.getPlayers()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
        for (UUID pId : arena.getSpectators()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
    }
    public static void announceHiderDeath(Player player) {
        Arena arena = ArenaManager.getManager().getArena(player);
        String msgOr = MessageManager.getMessagesYml().getString("Game.Announce.Death.Hider");
        String msg = msgOr.replaceAll("%player%", player.getDisplayName());
        for (UUID pId : arena.getPlayers()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
        for (UUID pId : arena.getSpectators()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
    }
    public static void announceSeekerDeath(Player player) {
        Arena arena = ArenaManager.getManager().getArena(player);
        String msgOr = MessageManager.getMessagesYml().getString("Game.Announce.Death.Seeker");
        String msg = msgOr.replaceAll("%player%", player.getDisplayName());
        for (UUID pId : arena.getPlayers()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
        for (UUID pId : arena.getSpectators()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
    }

    public static void announceHiderOutTagged(Player hider) {
        Arena arena = ArenaManager.getManager().getArena(hider);
        String msgOr = MessageManager.getMessagesYml().getString("Game.Announce.Out.Tagged");
        String msg = msgOr.replaceAll("%player%", hider.getDisplayName());
        for (UUID pId : arena.getPlayers()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
        for (UUID pId : arena.getSpectators()) {
            Bukkit.getPlayer(pId).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
    }

    public static void announceWinners(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        if (arena.getWinType().equals(WinType.HIDERS)) {
            String msgOr = MessageManager.getMessagesYml().getString("Game.Announce.Win.Hiders");
            if (Main.plugin.getConfig().getString("Game.BroadcastWinner").equalsIgnoreCase("True")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                }
            } else {
                for (UUID uuid : arena.getPlayers()) {
                    Bukkit.getPlayer(uuid).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                }
                for (UUID uuid : arena.getSpectators()) {
                    Bukkit.getPlayer(uuid).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                }
            }
        } else if (arena.getWinType().equals(WinType.SEEKERS)) {
            String msgOr = MessageManager.getMessagesYml().getString("Game.Announce.Win.Seekers");
            if (Main.plugin.getConfig().getString("Game.BroadcastWinner").equalsIgnoreCase("True")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                }
            } else {
                for (UUID uuid : arena.getPlayers()) {
                    Bukkit.getPlayer(uuid).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                }
                for (UUID uuid : arena.getSpectators()) {
                    Bukkit.getPlayer(uuid).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                }
            }
        } else if (arena.getWinType().equals(WinType.TIE)) {
            String msgOr = MessageManager.getMessagesYml().getString("Game.Announce.Win.Tie");
            if (Main.plugin.getConfig().getString("Game.BroadcastWinner").equalsIgnoreCase("True")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                }
            } else {
                for (UUID uuid : arena.getPlayers()) {
                    Bukkit.getPlayer(uuid).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                }
                for (UUID uuid : arena.getSpectators()) {
                    Bukkit.getPlayer(uuid).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                }
            }
        } else if (arena.getWinType().equals(WinType.NONE)) {
            String msgOr = MessageManager.getMessagesYml().getString("Game.Announce.Win.None");
            if (Main.plugin.getConfig().getString("Game.BroadcastWinner").equalsIgnoreCase("True")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                }
            } else {
                for (UUID uuid : arena.getPlayers()) {
                    Bukkit.getPlayer(uuid).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                }
                for (UUID uuid : arena.getSpectators()) {
                    Bukkit.getPlayer(uuid).sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                }
            }
        }
    }
}