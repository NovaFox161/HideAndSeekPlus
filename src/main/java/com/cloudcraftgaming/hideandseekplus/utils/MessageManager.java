package com.cloudcraftgaming.hideandseekplus.utils;

import com.cloudcraftgaming.hideandseekplus.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * Created by Nova Fox on 6/15/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus.
 */
public class MessageManager {
    public static void createMessagesFiles() {
        createEnglishFile();
    }
    private static void createEnglishFile() {
        File enFile = new File(Main.plugin.getDataFolder() + "/Messages/English.yml");
        if (!enFile.exists()) {
            Main.plugin.getLogger().info("Generating english messages...");
            YamlConfiguration en = YamlConfiguration.loadConfiguration(enFile);

            en.addDefault("DO NOT DELETE", "HideAndSeekPlus is developed and managed by Shades161");
            en.addDefault("Messages Version", FileManager.messageVersion);
            en.addDefault("Prefix", "&5[HideAndSeek]");

            en.addDefault("Game.Announce.WaitDelay", "&6Waiting for more players! Time: %time% seconds");
            en.addDefault("Game.Announce.StartDelay", "&6Starting game in &5%time% seconds&6!");
            en.addDefault("Game.Announce.Cancel.Wait", "&4Not enough players to start game! &6Waiting for more players!");
            en.addDefault("Game.Announce.Cancel.Start", "&4Not enough players to start game! &6Waiting for more players!");
            en.addDefault("Game.Announce.Start", "&6The game has started! Good luck! &5Hiders: Find a place to hide! &4Seeker: Find the hiders!");
            en.addDefault("Game.Announce.Hide.Start", "&6You have &5%time% seconds &6to hide!");
            en.addDefault("Game.Announce.Hide.End", "&6Hope you hiders have a hiding place! &4Because the seeker is out! &5Good Luck!");
            en.addDefault("Game.Announce.Player.Join", "&6%player% &6has joined the mini game!");
            en.addDefault("Game.Announce.Player.Quit", "&6%player% &6has quit the mini game!");
            en.addDefault("Game.Announce.Win.Hiders", "&6The hiders have won the minigame! Good job staying hidden hiders!");
            en.addDefault("Game.Announce.Win.Seekers", "&6The seekers have won the minigame! They found all of the hiders!");
            en.addDefault("Game.Announce.Win.Tie", "&6Somehow the game ended in a tie! This shouldn't happen, but it did so...");
            en.addDefault("Game.Announce.Win.None", "&6No one won the minigame! That's quite sad :(");
            en.addDefault("Game.Announce.Death.Lobby", "&4No one knows how, but &6%player% &4died. &6Please don't do that again.");
            en.addDefault("Game.Announce.Death.Hider", "&4Player &6%player% &4is out! They died. Good job (sorta)!");
            en.addDefault("Game.Announce.Death.Seeker", "&4Welp. &6%player% &4just died. Good(ish) job hiders! You won!!");
            en.addDefault("Game.Announce.Out.Tagged", "&6%player% &5was tagged and is now out! &4They probably didn't try tbh.");
            en.addDefault("Game.Event.HiderHitSeeker", "&6Wow, you have guts... &4Did you have fun doing that? Cuz you'l be out soon(hopefully).");
            en.addDefault("Game.Event.HiderHitHider.Damager", "&4Stop hitting your own and actually /try/ to win the game.");
            en.addDefault("Game.Event.HiderHitHider.Hitie", "&4Get a real hiding place please. Your own teammate is hitting you!");
            en.addDefault("Game.Event.PlayerHitPlayer", "&4Stop it!!! &6The game hasn't event started yet! &4Come on! Leave them alone...?");

            en.addDefault("Rules.Block.Break", "&4You cannot break blocks while in a minigame!");
            en.addDefault("Rules.Block.Place", "&4You cannot place blocks while in a minigame!");

            en.addDefault("Command.Create", "&5Created new arena with Id: &6%id%");
            en.addDefault("Command.Tool", "&3Arena tool set to: %value%!");
            en.addDefault("Command.ReloadArena", "&3Reloaded arena &6%id%&3!");
            en.addDefault("Command.Set.Enable", "&3Enabled arena &6%id%&3!");
            en.addDefault("Command.Set.Disable", "&3Disabled arena &6%id%&3!");
            en.addDefault("Command.Set.End", "&3End position for arena &6%id% &3set!");
            en.addDefault("Command.Set.Quit", "&3Quit position for arena &6%id% &3set!");
            en.addDefault("Command.Set.Lobby", "&3Lobby position for arena &6%id% &3set!");
            en.addDefault("Command.Set.Spawn", "&3Spawn position for arena &6%id% &3set!");
            en.addDefault("Command.Set.Spectate", "&3Spectating position for arena &6%id% &3set!");
            en.addDefault("Command.Set.Regen", "&3Regen area for arena &6%id% &3set!");
            en.addDefault("Command.Set.Name", "&3Display name for arena &6%id% &3is now &6%name%&3!");
            en.addDefault("Command.Set.MinPlayers", "&3Minimum players for arena &6%id% &3set to &6%count%&3!");
            en.addDefault("Command.Set.MaxPlayers", "&3Maximum players for arena &6%id% &3set to &6%count%&3!");
            en.addDefault("Command.Set.Spawn", "&3Spawn for arena &6%id% &3set!");
            en.addDefault("Command.Set.Time.Wait", "&3Wait delay for arena &6%id% &3set to &6%time% seconds&3!");
            en.addDefault("Command.Set.Time.Start", "&3Start delay for arena &6%id% &3set to &6%time% seconds&3!");
            en.addDefault("Command.Set.Time.Game", "&3Game length for arena &6%id% &3set to &6%time% minutes&3!");
            en.addDefault("Command.Set.Time.Hide", "&3Hide time for arena &6%id% &3set to &6%time% seconds&3!");
            en.addDefault("Command.Set.LateJoin", "&3Late join for arena &6%id% &3set to &6%allowed%&3!");
            en.addDefault("Command.Set.Loc.Loc1", "&3Location 1 set!");
            en.addDefault("Command.Set.Loc.Loc1Only", "&3Set location 2 (right click) and then finalize your selection!");
            en.addDefault("Command.Set.Loc.Loc2", "&3Location 2 set!");
            en.addDefault("Command.Set.Loc.Loc2Only", "&3Set location 1 (left click) and then finalize your selection!");
            en.addDefault("Command.Set.Loc.Both", "&3Locations 1 and 2 set! &6Use the correct command to finalize your selection!");
            en.addDefault("Command.Set.Loc.Need", "&4You need to set both positions 1 and 2 in order to set this!");

            en.addDefault("Sign.Info.Heading", "&6-~- &5Arena %id% Info &6-~-");
            en.addDefault("Sign.Place.Join", "&6You have placed a join sign! &5Right click to join arena, left click to view info!");
            en.addDefault("Sign.Place.Quit", "&6You have placed a quit sign! &5Right click to quit minigame when ingame!");
            en.addDefault("Sign.Place.Spectate", "&6You have placed a spectate sign! &5Right click to spectate arena!");

            en.addDefault("Notifications.Player.InGame", "&4You can only be in 1 Hide And Seek game at a time!");
            en.addDefault("Notifications.Player.HasItems", "&4Your inventory must be empty to join the minigame!");
            en.addDefault("Notifications.Player.NotInGame", "&4You are not in a Hide And Seek game!");
            en.addDefault("Notifications.Player.Quit", "&4You have quit the mini game!");
            en.addDefault("Notifications.Player.Out.Tagged.Hider", "&4You got tagged! You're out. Maybe you can win next time (prob not)!");
            en.addDefault("Notifications.Player.Out.Tagged.Seeker", "&6You tagged &5%player%&6! &4Now go find the others (and actually try)!");
            en.addDefault("Notifications.Player.Out.Death.Hider", "&4You died. Good job! You lost.");
            en.addDefault("Notifications.Player.Out.Death.Seeker", "&4You died. Good job! You lost and the hiders all won! Brilliant.");
            en.addDefault("Notifications.Player.BlockedCommand", "&4You cannot use that command while in game!");
            en.addDefault("Notifications.Arena.Full", "&4You cannot join that arena because it is full!");
            en.addDefault("Notifications.Arena.State.Starting", "&4You cannot join that arena because it already starting!");
            en.addDefault("Notifications.Arena.State.InGame", "&4You cannot join that arena because it is already in game!");
            en.addDefault("Notifications.Arena.State.Regenerating", "&You cannot join that arena while it is regenerating!");
            en.addDefault("Notifications.ArenaDoesNotExist", "&4The specified arena does not exist or is not enabled!");
            en.addDefault("Notifications.NoPerm", "&4You do not have permission to do that!");
            en.addDefault("Notifications.Args.Few", "&4Too few arguments for this command!");
            en.addDefault("Notifications.Args.Many", "&4Too many arguments for this command!");
            en.addDefault("Notifications.Args.Invalid", "&4Invalid arguments for this command!");
            en.addDefault("Notifications.Int.Arena", "&4Arena Id must be a valid Integer (EX: 1, 2, 3)!");
            en.addDefault("Notifications.Int.MinPlayers", "&4Minimum players must be a number!");
            en.addDefault("Notifications.Int.MaxPlayers", "&4Maximum players musts be a number!");
            en.addDefault("Notifications.Int.Time", "&4Time must be a number!");
            en.addDefault("Notifications.Int.Spawn", "&4Spawn value must be a number!");
            en.addDefault("Notifications.Bool.Kits", "&4Can use kits must be 'True' or 'False'");
            en.addDefault("Notifications.Bool.LateJoin", "&4Late join allowed must be 'True' or 'False'");

            en.options().copyDefaults(true);
            Main.plugin.saveCustomConfig(en, enFile);

            en.options().copyDefaults(true);
            Main.plugin.saveCustomConfig(en, enFile);
        }
    }

    public static File getMessagesFile() {
        String lang = Main.plugin.getConfig().getString("Language");
        return new File(Main.plugin.getDataFolder() + "/Messages/" + lang + ".yml");
    }
    public static YamlConfiguration getMessagesYml() {
        return YamlConfiguration.loadConfiguration(getMessagesFile());
    }
    public static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', getMessagesYml().getString("Prefix")) + " " + ChatColor.RESET;
    }
    public static String getMessage(String msgString) {
        String msgOr = getMessagesYml().getString(msgString);
        return ChatColor.translateAlternateColorCodes('&', msgOr);
    }
    public static String getMessage(String msgString, int id) {
        String msgOr = getMessagesYml().getString(msgString);
        String msg = msgOr.replaceAll("%id%", String.valueOf(id));
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
