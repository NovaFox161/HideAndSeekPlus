package com.cloudcraftgaming.hideandseekplus.utils;

import com.cloudcraftgaming.hideandseekplus.Main;

/**
 * Created by Nova Fox on 6/15/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus.
 */
public class PluginChecker {
    public static void checkForPerWorldChatPlus() {
        if (Main.plugin.getConfig().getString("Chat.PerWorldChatPlus.CompatibilityMode").equalsIgnoreCase("True")) {
            if (Main.plugin.getServer().getPluginManager().getPlugin("PerWorldChatPlus") != null) {
                if (Main.plugin.getServer().getPluginManager().getPlugin("PerWorldChatPlus").getDescription().getVersion().startsWith("5")
                        || Main.plugin.getServer().getPluginManager().getPlugin("PerWorldChatPlus").getDescription().getVersion().equals("5.0.1")) {
                    Main.plugin.perWorldChatPlus = Main.plugin.getServer().getPluginManager().getPlugin("PerWorldChatPlus");
                    if (FileManager.verbose()) {
                        Main.plugin.getLogger().info("PerWorldChatPlus detected! Will use compatibility mode for chat!");
                    }
                } else {
                    if (FileManager.verbose()) {
                        Main.plugin.getLogger().info("PerWorldChatPlus is installed! But it is outdated!!");
                    }
                    Main.plugin.getConfig().set("Chat.PerWorldChatPlus.CompatibilityMode", false);
                    Main.plugin.saveConfig();
                }
            } else {
                Main.plugin.getConfig().set("Chat.PerWorldChatPlus.CompatibilityMode", false);
                Main.plugin.saveConfig();
                if (FileManager.verbose()) {
                    Main.plugin.getLogger().info("PerWorldChatPlus not found! Turning off compatibility mode!");
                }
            }
        }
    }
}
