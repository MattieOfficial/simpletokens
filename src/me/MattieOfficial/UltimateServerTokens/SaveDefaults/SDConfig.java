package me.MattieOfficial.UltimateServerTokens.SaveDefaults;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class SDConfig {
	
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
	
	public static void saveConfigDefaults() {
	
	//Load in the configFile and add the default values and options
	try {
		
		config.load(configFile);
		
		config.options().header("SimpleTokens 1.0\nIf you do not know what you're doing, do not edit\nanything in this config.yml file");
		
		config.addDefault("doNotEditThis.configVersion", "st.1");
		config.addDefault("options.useConsoleColors", false);
		config.addDefault("options.defaultAmount", 25);
		config.addDefault("options.alt-protection", false);
		config.addDefault("customize.prefix", "&b&lSimpleTokens &r&8»");
		
		config.options().copyDefaults(true);
		config.save(configFile);
		
	} catch(Exception ex) {
		ex.printStackTrace();
	}
	
	}

}
