package me.MattieOfficial.UltimateServerTokens.SaveDefaults;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.MattieOfficial.UltimateServerTokens.library.ConsoleLogs;

public class SDMessages {
	
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
	
	public static void saveDefaultMessages() {
		
		try {
			
			//Load messages.yml
			messages.load(messagesFile);
			
			//Save default header
			messages.options().header("SimpleTokens 1.0 Messages\nVariables: %player_name%, %player_uuid%, %amount%");
			
			//save default messages
			messages.addDefault("currentAmount", "&3You have %amount% tokens!");
			messages.addDefault("noPerms", "&cYou do not have the right permissions to perform this command!");
			messages.addDefault("notEnough", "&cYou do not have enough tokens!");
			messages.addDefault("tokensSent", "&3You have succesfully sent &a%send_amount% &3tokens to &a%target_player%&3!");
			messages.addDefault("tokensReceived", "&3You have received &a%receive_amount% &3tokens from &a%player_name%&3!");
			messages.addDefault("playerNotOnline", "&cPlayer is not online or not found!");
			messages.addDefault("commandNotFound", "&cWrong command! Use &a/tokens help &cfor more info!");
			messages.addDefault("chequeCreated", "&3You've succesfully created cheque &a%cheque_name% &3worth of &a%cheque_amount% &3tokens!");
			messages.addDefault("chequeAlreadyExists", "&cCheque &a%cheque_name% &calready exists! Think of another name!");
			messages.addDefault("chequeNoExist", "&cThat cheque does not exist!");
			messages.addDefault("chequeRedeemed", "&3You've succesfully redeemed cheque &a%cheque_name% &3worth of &a%cheque_amount% &3tokens!");
			messages.addDefault("succesfulAdd", "&3You've succesfully added &a%send_amount% &3tokens to &a%target_name%&3!");
			messages.addDefault("addReceived", "&3You've received &a%send_amount% &3tokens from player &a%player_name%&3!");
			messages.addDefault("succesfulTake", "&3You've succesfully took &a%take_amount% &3tokens from &a%target_name%&3!");
			messages.addDefault("takeTook", "&3Player &a%player_name% &3took &a%take_amount% &3tokens from you!");
			messages.addDefault("succesfulSet", "&3You have succesfully set tokens of player &a%target_name% &3to &a%set_amount%&3!");
			messages.addDefault("setSet", "&3Your tokens are set to &a%set_amount% &3by player &a%player_name%&3!");
			
			messages.options().copyDefaults(true);
			messages.save(messagesFile);
			
			
		} catch(Exception ex) {
			ConsoleLogs.Error("ja dis dus waar ut mis gaai");
			ex.printStackTrace();
		}
		
	}

}
