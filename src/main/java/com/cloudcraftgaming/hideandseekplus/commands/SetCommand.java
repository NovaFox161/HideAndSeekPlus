package com.cloudcraftgaming.hideandseekplus.commands;

import com.cloudcraftgaming.hideandseekplus.data.ArenaDataManager;
import com.cloudcraftgaming.hideandseekplus.data.PlayerDataManager;
import com.cloudcraftgaming.hideandseekplus.utils.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by Nova Fox on 6/21/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus.
 */
public class SetCommand {
    public static void setCommand(Player player, String[] args) {
        if (player.hasPermission("CARP.use.command.set")) {
            if (args.length == 1) {
                //car set
                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Few"));
            } else if (args.length == 2) {
                //car set <id>
                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Few"));
            } else if (args.length == 3) {
                //car set <id> <type>
                String type = args[2];
                try {
                    Integer id = Integer.valueOf(args[1]);
                    if (ArenaDataManager.arenaExists(id)) {
                        if (type.equalsIgnoreCase("lobby") || type.equalsIgnoreCase("LobbyLocation") || type.equalsIgnoreCase("LobbyPosition")) {
                            ArenaDataManager.setLobbyLocation(id, player.getLocation());
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Lobby", id));
                        } else if (type.equalsIgnoreCase("end") || type.equalsIgnoreCase("endLocation") || type.equalsIgnoreCase("EndPosition")) {
                            ArenaDataManager.setEndLocation(id, player.getLocation());
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.End", id));
                        } else if (type.equalsIgnoreCase("quit") || type.equalsIgnoreCase("QuitLocation") || type.equalsIgnoreCase("QuitPosition")) {
                            ArenaDataManager.setQuitLocation(id, player.getLocation());
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Quit", id));
                        } else if (type.equalsIgnoreCase("spectate") || type.equalsIgnoreCase("spectateLocation")
                                || type.equalsIgnoreCase("SpectatePosition")) {
                            ArenaDataManager.setSpectateLocation(id, player.getLocation());
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Spectate", id));
                        } else if (type.equalsIgnoreCase("Spawn") || type.equalsIgnoreCase("SpawnPosition")
                                || type.equalsIgnoreCase("SpawnLocation")) {
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Spawn", id));
                            ArenaDataManager.setSpawnLocation(id, player.getLocation());
                        } else if (type.equalsIgnoreCase("regen") || type.equalsIgnoreCase("RegenArea")) {
                            setRegen(player, id);
                        } else {
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Few"));
                        }
                    } else {
                        player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.ArenaDoesNotExist"));
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.Arena"));
                }
            } else if (args.length == 4) {
                //car set <id> <type> <value>
                String type = args[2];
                String valueString = args[3];
                try {
                    Integer id = Integer.valueOf(args[1]);
                    if (ArenaDataManager.arenaExists(id)) {
                        if (type.equalsIgnoreCase("minPlayers")) {
                            try {
                                Integer value = Integer.valueOf(valueString);
                                ArenaDataManager.setMinPlayers(id, value);
                                String msgOr = MessageManager.getMessagesYml().getString("Command.Set.MinPlayers");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%count%", valueString);
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.MinPlayers"));
                            }
                        } else if (type.equalsIgnoreCase("maxPlayers")) {
                            try {
                                Integer value = Integer.valueOf(valueString);
                                ArenaDataManager.setMaxPlayers(id, value);
                                String msgOr = MessageManager.getMessagesYml().getString("Command.Set.MaxPlayers");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%count%", valueString);
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.MaxPlayers"));
                            }
                        } else if (type.equalsIgnoreCase("Wait") || type.equalsIgnoreCase("waitDelay")) {
                            try {
                                Integer value = Integer.valueOf(valueString);
                                ArenaDataManager.setWaitTime(id, value);
                                String msgOr = MessageManager.getMessagesYml().getString("Command.Set.Time.Wait");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%time%", valueString);
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.Time"));
                            }
                        } else if (type.equalsIgnoreCase("Start") || type.equalsIgnoreCase("StartDelay")) {
                            try {
                                Integer value = Integer.valueOf(valueString);
                                ArenaDataManager.setStartTime(id, value);
                                String msgOr = MessageManager.getMessagesYml().getString("Command.Set.Time.Start");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%time%", valueString);
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.Time"));
                            }
                        } else if (type.equalsIgnoreCase("GameTime") || type.equalsIgnoreCase("GameLength")) {
                            try {
                                Integer value = Integer.valueOf(valueString);
                                ArenaDataManager.setGameTime(id, value);
                                String msgOr = MessageManager.getMessagesYml().getString("Command.Set.Time.Game");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%time%", valueString);
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.Time"));
                            }
                        } else if (type.equals("HideTime") || type.equalsIgnoreCase("Hide")) {
                            try {
                                Integer value = Integer.valueOf(valueString);
                                ArenaDataManager.setHideTime(id, value);
                                String msgOr = MessageManager.getMessagesYml().getString("Command.Set.Time.Hide");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%time%", valueString);
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.Time"));
                            }
                        } else if (type.equalsIgnoreCase("lateJoin")) {
                            try {
                                Boolean lateJoinAllowed = Boolean.valueOf(valueString);
                                ArenaDataManager.setAllowLateJoin(id, lateJoinAllowed);
                                String msgOr = MessageManager.getMessagesYml().getString("Command.Set.LateJoin");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%allowed%", valueString);
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (Exception e) {
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Bool.LateJoin"));
                            }
                        } else {
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Invalid"));
                        }
                    } else {
                        player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.ArenaDoesNotExist"));
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.Arena"));
                }
            } else if (args.length > 4) {
                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Many"));
            }
        } else {
            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.NoPerm"));
        }
    }
    private static void setRegen(Player player, int id) {
        if (PlayerDataManager.hasLocationOneSaved(player) && PlayerDataManager.hasLocationTwoSaved(player)) {
            Location loc1 = PlayerDataManager.getSaveLocationOne(player);
            Location loc2 = PlayerDataManager.getSaveLocationTwo(player);
            ArenaDataManager.setRegenArea(id, loc1, loc2);
            PlayerDataManager.deleteSaveLocationOne(player);
            PlayerDataManager.deleteSaveLocationTwo(player);
            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Regen", id));
        } else {
            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Loc.Need"));
        }
    }
}