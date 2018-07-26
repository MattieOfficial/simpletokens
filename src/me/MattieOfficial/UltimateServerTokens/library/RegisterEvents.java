package me.MattieOfficial.UltimateServerTokens.library;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.md_5.bungee.api.ChatColor;

public class RegisterEvents implements Listener {
	
	public static File mainFolder = new File("plugins/SimpleTokens");
	public static File dataFolder = new File("plugins/SimpleTokens/Data");
	
	public static File configFile = new File("plugins/SimpleTokens/config.yml");
	public static FileConfiguration config = new YamlConfiguration();
	public static File messagesFile = new File("plugins/SimpleTokens/messages.yml");
	public static FileConfiguration messages = new YamlConfiguration();
	
	public static File userDataFile = new File("plugins/SimpleTokens/Data/userData.yml");
	public static FileConfiguration userData = new YamlConfiguration();
	public static File chequeDataFile = new File("plugins/SimpleTokens/Data/chequeData.yml");
	public static FileConfiguration chequeData = new YamlConfiguration();
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		
		Player p = event.getPlayer();
		
		//When a player is not registered in the userData.yml, their needs to be created a new userRec.
		//First there need to be checked if the configVersion is 3, then set all the values in the config using UUID's
		
		//Check if configVersion is right
		if(!(userData.contains("user." + p.getUniqueId().toString()))) {
			
			//Player isnt in the userData, make a new record for him
			try {
				
				String pid = p.getUniqueId().toString();
				
				config.load(configFile);
				
				int defaultTokens = config.getInt("options.defaultAmount");
				
				userData.load(userDataFile);
				userData.set("user." + pid + ".name", p.getName());
				userData.set("user." + pid + ".uuid", pid);
				userData.set("user." + pid + ".tokenAmount", defaultTokens); //this is what is going wrong
				userData.save(userDataFile);
				config.save(configFile);
				
			} catch(Exception ex) {
				if(config.getBoolean("options.useConsoleColors")) {
					ConsoleLogs.Error(ChatColor.RED + "Something went wrong while creating new userRec in onJoin event");
				} else {
					ConsoleLogs.Error("Something went wrong while creating new userRec in onJoin event");
				}
				ex.printStackTrace();
				
			}
			
			if(config.getBoolean("options.useConsoleColors")) {
				//found in the records, cc
				ConsoleLogs.Info(ChatColor.DARK_AQUA + "Player " + ChatColor.GREEN + p.getName() + ChatColor.DARK_AQUA + " has joined for the first time using this plugin! Registering him in userData.yml...");
			} else {
				//found in the records, no cc
				ConsoleLogs.Info("Player " + p.getName() + " has joined for the first time using this plugin! Registering him in userData.yml...");
			}
			
		} else {
			
			//player is fouund in the record
			if(config.getBoolean("options.useConsoleColors")) {
				
				//use consolecolor
				ConsoleLogs.Info(ChatColor.DARK_AQUA + "Player " + ChatColor.GREEN + p.getName() + ChatColor.DARK_AQUA + " is found in the records!");
				
			} else {
				
				//do not use consolecolor
				ConsoleLogs.Info("Player " + p.getName() + " is found in the records!");
				
			}
		}
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		
		Player p = event.getPlayer();
		
		//Check if there's a record from the player leaving, if not make one
		if(!(userData.contains("user." + p.getUniqueId().toString()))) {
			
			//player doesn't exist, make a new record
			try {
				
				String pid = p.getUniqueId().toString();
				
				userData.load(userDataFile);
				userData.set("user." + pid + ".name", p.getName());
				userData.set("user." + pid + ".uuid", pid);
				userData.set("user." + pid + ".tokenAmount", config.getInt("options.defaultAmount"));
				userData.save(userDataFile);
				
			} catch(Exception ex) {
				if(config.getBoolean("options.useConsoleColors")) {
					ConsoleLogs.Error(ChatColor.RED + "Something went wrong while creating new userRec in onJoin event");
				} else {
					ConsoleLogs.Error("Something went wrong while creating new userRec in onJoin event");
				}
				ex.printStackTrace();
				
			}
			
		} else {
			
			// player is found in the record, save and send message
			
			try {
				
				//save userData.yml
				
				
			} catch(Exception ex) {
				if(config.getBoolean("options.useConsoleColors")) {
					ConsoleLogs.Error(ChatColor.RED + "Something went wrong while saving userRec in onQuit event");
				} else {
					ConsoleLogs.Error("Something went wrong while saving userRec in onQuit event");
				}
				ex.printStackTrace();
			}
			
			if(config.getBoolean("options.useConsoleColors")) {
				
				//use consolecolor
				ConsoleLogs.Info(ChatColor.DARK_AQUA + "Player " + ChatColor.GREEN + p.getName() + ChatColor.DARK_AQUA + " is found in the records!");
				
			} else {
				
				//do not use consolecolor
				ConsoleLogs.Info("Player " + p.getName() + " is found in the records!");
				
			}
			
		}
		
	}

}
