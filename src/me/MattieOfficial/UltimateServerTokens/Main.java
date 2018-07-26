package me.MattieOfficial.UltimateServerTokens;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.MattieOfficial.UltimateServerTokens.SaveDefaults.SDConfig;
import me.MattieOfficial.UltimateServerTokens.SaveDefaults.SDMessages;
import me.MattieOfficial.UltimateServerTokens.commands.Tokens;
import me.MattieOfficial.UltimateServerTokens.commands.UltimateServerTokens;
import me.MattieOfficial.UltimateServerTokens.library.ConsoleLogs;
import me.MattieOfficial.UltimateServerTokens.library.RegisterEvents;

public class Main extends JavaPlugin {
	
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
	
	public void onEnable() {
		
		//Register commands here
		getCommand("tokens").setExecutor(new Tokens(this));
		getCommand("token").setExecutor(new Tokens(this));
		getCommand("simpletokens").setExecutor(new UltimateServerTokens(this));
		getCommand("st").setExecutor(new UltimateServerTokens(this));
		
		//Make folders if they do not exist
		if(!(mainFolder.exists())) {
			mainFolder.mkdir();
		}
		if(!(dataFolder.exists())) {
			dataFolder.mkdir();
		}
		ConsoleLogs.Info("All folders are made and registered!");
		
		//Make files in mainFolder if they do not exist
		if(!(configFile.exists())) {
			try {
				configFile.createNewFile();
			} catch(Exception ex) {
				ex.printStackTrace();
				ConsoleLogs.Error("Something went wrong while creating config.yml!");
			}
		}
		if(!(messagesFile.exists())) {
			try {
				messagesFile.createNewFile();
			} catch(Exception ex) {
				ex.printStackTrace();
				ConsoleLogs.Error("Something went wrong while creating messages.yml!");
			}
		}
		ConsoleLogs.Info("All files in mainFolder are made and registered!");
		
		//Make files in dataFolder if they do not exist
		if(!(userDataFile.exists())) {
			try {
				userDataFile.createNewFile();
			} catch(Exception ex) {
				ex.printStackTrace();
				ConsoleLogs.Error("Something went wrong while creating userData.yml");
			}
		}
		if(!(chequeDataFile.exists())) {
			try {
				chequeDataFile.createNewFile();
			} catch(Exception ex) {
				ex.printStackTrace();
				ConsoleLogs.Error("Something went wrong while creating chequeData.yml");
			}
		}
		ConsoleLogs.Info("All files in dataFolder are made and registered!");
		
		//Save default values in files
		SDConfig.saveConfigDefaults();
		SDMessages.saveDefaultMessages();
		
		
		//Register all other things that need to be registered
		getServer().getPluginManager().registerEvents(new RegisterEvents(), this);
		
		//When plugin enables
		ConsoleLogs.Info("Plugin has enabled!");
		
	}
	
	public void onDisble() {
		
		//When plugin disables
		
	}

}
