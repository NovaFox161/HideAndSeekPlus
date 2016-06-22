package com.cloudcraftgaming.hideandseekplus.listeners;

import com.cloudcraftgaming.hideandseekplus.data.ArenaDataManager;
import com.cloudcraftgaming.hideandseekplus.utils.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * Created by Nova Fox on 6/18/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus.
 */
public class SignChangeListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSignChange(SignChangeEvent event) {
        if (!(event.isCancelled())) {
            if (event.getLine(0).equalsIgnoreCase("[HideAndSeek]")) {
                Player player = event.getPlayer();
                if (player.hasPermission("hasp.sign.place")) {
                    if (event.getLine(1).equalsIgnoreCase("Join")) {
                        if (!(event.getLine(2).isEmpty())) {
                            try {
                                Integer arenaId = Integer.valueOf(event.getLine(2));
                                if (ArenaDataManager.arenaExists(arenaId)) {
                                    event.setLine(0, ChatColor.BLUE + "[HideAndSeek]");
                                    event.setLine(1, ChatColor.DARK_GREEN + "Join");
                                    event.setLine(2, String.valueOf(arenaId));
                                    String msgOr = MessageManager.getMessagesYml().getString("Sign.Place.Join");
                                    player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                                } else {
                                    String msg = MessageManager.getMessagesYml().getString("Notifications.ArenaDoesNotExist");
                                    player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                                    event.setCancelled(true);
                                }
                            } catch (NumberFormatException e) {
                                String msg = MessageManager.getMessagesYml().getString("Notifications.Int.Arena");
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                                event.setCancelled(true);
                            }
                        } else {
                            String msg = MessageManager.getMessagesYml().getString("Notifications.Int.Arena");
                            player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                            event.setCancelled(true);
                        }
                    } else if (event.getLine(1).equalsIgnoreCase("Spectate")) {
                        if (!(event.getLine(2).isEmpty())) {
                            try {
                                Integer arenaId = Integer.valueOf(event.getLine(2));
                                if (ArenaDataManager.arenaExists(arenaId)) {
                                    event.setLine(0,ChatColor.BLUE + "[HideAndSeek]");
                                    event.setLine(1, ChatColor.GREEN + "Spectate");
                                    event.setLine(2, String.valueOf(arenaId));
                                    String msgOr = MessageManager.getMessagesYml().getString("Sign.Place.Spectate");
                                    player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                                }
                            } catch (NumberFormatException e) {
                                String msg = MessageManager.getMessagesYml().getString("Notifications.Int.Arena");
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                                event.setCancelled(true);
                            }
                        } else {
                            String msg = MessageManager.getMessagesYml().getString("Notifications.Int.Arena");
                            player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                            event.setCancelled(true);
                        }
                    } else if (event.getLine(1).equalsIgnoreCase("Quit")) {
                        event.setLine(0, ChatColor.BLUE + "[HideAndSeek]");
                        event.setLine(1, ChatColor.RED + "Quit");
                        String msgOr = MessageManager.getMessagesYml().getString("Sign.Place.Quit");
                        player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                    }
                } else {
                    String msg = MessageManager.getMessagesYml().getString("Notifications.NoPerm");
                    player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                    event.setCancelled(true);
                }
            }
        }
    }
}