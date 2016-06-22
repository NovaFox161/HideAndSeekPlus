package com.cloudcraftgaming.hideandseekplus.listeners;

import com.cloudcraftgaming.hideandseekplus.data.PlayerDataManager;
import com.cloudcraftgaming.hideandseekplus.utils.MessageManager;
import com.cloudcraftgaming.hideandseekplus.utils.PlayerHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Nova Fox on 6/18/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus.
 */
public class PlayerInteractListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getClickedBlock().getState() instanceof Sign) {
                Sign sign = (Sign) event.getClickedBlock().getState();
                if (sign.getLine(0).contains("[HideAndSeek]")) {
                    if (player.hasPermission("hasp.sign.use")) {
                        if (sign.getLine(1).contains("Quit")) {
                            PlayerHandler.quitArena(player);
                        } else if (sign.getLine(1).contains("Spectate")) {
                            try {
                                Integer arenaId = Integer.valueOf(sign.getLine(2));
                                //Would send to spectate, but not coded in yet.
                                //PlayerHandler.spectateArena(player, arenaId);
                            } catch (NumberFormatException e) {
                                String msgOr = MessageManager.getMessagesYml().getString("Notifications.ArenaDoesNotExist");
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                            }
                        } else if (sign.getLine(1).contains("Join")) {
                            try {
                                Integer arenaId = Integer.valueOf(sign.getLine(2));
                                PlayerHandler.joinArena(player, arenaId);
                            } catch (NumberFormatException e) {
                                String msgOr = MessageManager.getMessagesYml().getString("Notifications.ArenaDoesNotExist");
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                            }
                        }
                    } else {
                        String msg = MessageManager.getMessagesYml().getString("Notifications.NoPerm");
                        player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                    }
                }
            } else {
                if (!(event.getItem() == null)) {
                    if (event.getItem().getType().equals(Material.STICK)) {
                        if (player.hasPermission("hasp.use.command.set")) {
                            if (PlayerDataManager.hasArenaToolEnabled(player)) {
                                PlayerDataManager.saveLocationTwo(player, event.getClickedBlock().getLocation());
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Loc.Loc2"));
                                checkLocationStatus(player);
                            }
                        }
                    }
                }
            }
        } else if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (event.getClickedBlock().getState() instanceof Sign) {
                Sign sign = (Sign) event.getClickedBlock().getState();
                if (sign.getLine(0).contains("[HideAndSeek]")) {
                    if (player.hasPermission("hasp.sign.use")) {
                        if (sign.getLine(1).contains("Join")) {
                            try {
                                Integer arenaId = Integer.valueOf(sign.getLine(2));
                                PlayerHandler.sendArenaInfo(player, arenaId);
                            } catch (NumberFormatException e) {
                                String msgOr = MessageManager.getMessagesYml().getString("Notifications.ArenaDoesNotExist");
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                            }
                        }
                    } else {
                        String msg = MessageManager.getMessagesYml().getString("Notifications.NoPerm");
                        player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                    }
                }
            } else {
                if (!(event.getItem() == null)) {
                    if (event.getItem().getType().equals(Material.STICK)) {
                        if (player.hasPermission("hasp.use.command.set")) {
                            if (PlayerDataManager.hasArenaToolEnabled(player)) {
                                PlayerDataManager.saveLocationOne(player, event.getClickedBlock().getLocation());
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Loc.Loc1"));
                                checkLocationStatus(player);
                            }
                        }
                    }
                }
            }
        }
    }

    private void checkLocationStatus(Player player) {
        if (PlayerDataManager.hasLocationOneSaved(player) && !PlayerDataManager.hasLocationTwoSaved(player)) {
            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Loc.Loc1Only"));
        } else if (!PlayerDataManager.hasLocationOneSaved(player) && PlayerDataManager.hasLocationTwoSaved(player)) {
            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Loc.Loc2Only"));
        } else if (PlayerDataManager.hasLocationOneSaved(player) && PlayerDataManager.hasLocationTwoSaved(player)) {
            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Loc.Both"));
        }
    }
}