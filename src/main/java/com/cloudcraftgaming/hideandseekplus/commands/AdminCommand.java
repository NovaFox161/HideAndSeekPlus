package com.cloudcraftgaming.hideandseekplus.commands;

import com.cloudcraftgaming.hideandseekplus.arena.ArenaManager;
import com.cloudcraftgaming.hideandseekplus.data.ArenaDataManager;
import com.cloudcraftgaming.hideandseekplus.data.PlayerDataManager;
import com.cloudcraftgaming.hideandseekplus.utils.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Nova Fox on 6/21/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus.
 */
public class AdminCommand {
    public static void adminCommand(Player player, String[] args) {
        if (player.hasPermission("CARP.use.command.admin") || player.hasPermission("CARP.use.command.set")) {
            if (args.length == 1) {
                //car <type>
                String type = args[0];
                if (type.equalsIgnoreCase("set")) {
                    SetCommand.setCommand(player, args);
                } else if (type.equalsIgnoreCase("create") || type.equalsIgnoreCase("createArena")) {
                    int id = ArenaDataManager.createArena();
                    player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Create", id));
                } else if (type.equalsIgnoreCase("tool") || type.equalsIgnoreCase("arenaTool")) {
                    PlayerDataManager.setArenaToolEnabled(player, !PlayerDataManager.hasArenaToolEnabled(player));
                    String msgOr = MessageManager.getMessagesYml().getString("Command.Tool");
                    String msg = msgOr.replaceAll("%value%", String.valueOf(PlayerDataManager.hasArenaToolEnabled(player)));
                    player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                } else {
                    player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Invalid"));
                }
            } else if (args.length == 2) {
                //car <type> <id>
                String type = args[0];
                try {
                    Integer id = Integer.valueOf(args[1]);
                    if (type.equalsIgnoreCase("enable") || type.equalsIgnoreCase("enableArena")) {
                        if (ArenaDataManager.canBeLoaded(id)) {
                            ArenaDataManager.enableArena(id);
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Enable", id));
                        } else {
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Enable.Cannot"));
                        }
                    } else if (type.equalsIgnoreCase("disable") || type.equalsIgnoreCase("disableArena")) {
                        ArenaDataManager.disableArena(id);
                        player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Disable", id));
                    } else if (type.equalsIgnoreCase("reload") || type.equalsIgnoreCase("reloadArena")) {
                        ArenaManager.getManager().safeReloadArena(id);
                        player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.ReloadArena", id));
                    }

                } catch (NumberFormatException e) {
                    player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.Arena"));
                }
            }
        } else {
            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.NoPerm"));
        }
    }
}