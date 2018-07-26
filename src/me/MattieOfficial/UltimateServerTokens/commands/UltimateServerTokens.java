package me.MattieOfficial.UltimateServerTokens.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.MattieOfficial.UltimateServerTokens.Main;
import net.md_5.bungee.api.ChatColor;

public class UltimateServerTokens implements CommandExecutor {
	
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

	public UltimateServerTokens(Main main) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
			try {
				
				userData.load(userDataFile);
				chequeData.load(chequeDataFile);
				config.load(configFile);
				messages.load(messagesFile);
			
				Player p = (Player) sender;
				String prefix = (ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', config.getString("customize.prefix"))) + " ");
				
				//todo: help, info, set, add, take
				//start 
				if(args.length == 0) {
					if(p.hasPermission("ust.admin.info")) {
						
						//display info
						p.sendMessage(prefix + ChatColor.DARK_AQUA + "Info");
						p.sendMessage("");
						p.sendMessage(ChatColor.DARK_AQUA + "Plugin: " + ChatColor.GRAY + "Simpletokens");
						p.sendMessage(ChatColor.DARK_AQUA + "Author: " + ChatColor.GRAY + "MattieOfficial");
						p.sendMessage(ChatColor.DARK_AQUA + "Version: " + ChatColor.GRAY + "1.0");
						p.sendMessage(ChatColor.DARK_AQUA + "MC Version: " + ChatColor.GRAY + "1.12.2");
						
					} else {
						//no perms
						int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
						String pname = p.getName();
						String puuid = p.getUniqueId().toString();
						String amountString = Integer.toString(amount);
						p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("noPerms").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
					}
				}
				else if(args.length == 3) {
					
					//Is sender a player?
					if(sender instanceof Player) {
						
						Player target = Bukkit.getServer().getPlayerExact(args[1]);

						if(target != null) {
							
							if(userData.contains("user." + target.getUniqueId().toString())) {
						
								//sender is player, is online and registered
								if(args[0].equalsIgnoreCase("add")) {
									//command add is executed
									//right perms?
									if(p.hasPermission("ust.admin.add")) {
										
										//player has perms, target is online and registered, add money
										int targetsCurrent = userData.getInt("user." + target.getUniqueId().toString() + ".tokenAmount");
										if(isInt(args[2])) {
											
											//set args[2] as int
											int toAdd = Integer.parseInt(args[2]);
											
											//add toAdd to targetCurrent
											userData.set("user." + target.getUniqueId().toString() + ".tokenAmount", targetsCurrent + toAdd);
											
											//send message to sender
											int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
											String pname = p.getName();
											String puuid = p.getUniqueId().toString();
											String amountString = Integer.toString(amount);
											String targetName = target.getName();
											String toAddString = Integer.toString(toAdd);
											p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("succesfulAdd").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString).replace("%target_name%", targetName).replace("%send_amount%", toAddString)));
											
											//send message to target
											target.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("addReceived").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString).replace("%target_name%", targetName).replace("%send_amount%", toAddString)));
											
											//save
											userData.save(userDataFile);
											config.save(configFile);
											
											
										} else {
											//wrong usage
											int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
											String pname = p.getName();
											String puuid = p.getUniqueId().toString();
											String amountString = Integer.toString(amount);
											p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("commandNotFound").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
										}
										
										
									} else {
										//no perms
										int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
										String pname = p.getName();
										String puuid = p.getUniqueId().toString();
										String amountString = Integer.toString(amount);
										p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("noPerms").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
									}
									
								}
								else if(args[0].equalsIgnoreCase("take")) {
									//command take is executed
									//right perms?
									//command add is executed
									//right perms?
									if(p.hasPermission("ust.admin.take")) {
										
										//player has perms, target is online and registered, add money
										int targetsCurrent = userData.getInt("user." + target.getUniqueId().toString() + ".tokenAmount");
										if(isInt(args[2])) {
											
											//set args[2] as int
											int toTake = Integer.parseInt(args[2]);
											
											//add toAdd to targetCurrent
											userData.set("user." + target.getUniqueId().toString() + ".tokenAmount", targetsCurrent - toTake);
											
											//send message to sender
											int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
											String pname = p.getName();
											String puuid = p.getUniqueId().toString();
											String amountString = Integer.toString(amount);
											String targetName = target.getName();
											String toTakeString = Integer.toString(toTake);
											p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("succesfulTake").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString).replace("%target_name%", targetName).replace("%take_amount%", toTakeString)));
											
											//send message to target
											target.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("takeTook").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString).replace("%target_name%", targetName).replace("%take_amount%", toTakeString)));
											
											//save
											userData.save(userDataFile);
											config.save(configFile);
											
											
										} else {
											//wrong usage
											int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
											String pname = p.getName();
											String puuid = p.getUniqueId().toString();
											String amountString = Integer.toString(amount);
											p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("commandNotFound").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
										}
										
										
									} else {
										//no perms
										int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
										String pname = p.getName();
										String puuid = p.getUniqueId().toString();
										String amountString = Integer.toString(amount);
										p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("noPerms").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
									}
									
								} else if(args[0].equalsIgnoreCase("set")) {
									//command set is executed
									//right perms?
									if(p.hasPermission("ust.admin.set")) {
										
										//player has perms, target is online and registered, add money
										if(isInt(args[2])) {
											
											//set args[2] as int
											int toSet = Integer.parseInt(args[2]);
											
											//add toAdd to targetCurrent
											userData.set("user." + target.getUniqueId().toString() + ".tokenAmount", toSet);
											
											//send message to sender
											int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
											String pname = p.getName();
											String puuid = p.getUniqueId().toString();
											String amountString = Integer.toString(amount);
											String targetName = target.getName();
											String toSetString = Integer.toString(toSet);
											p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("succesfulSet").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString).replace("%target_name%", targetName).replace("%set_amount%", toSetString)));
											
											//send message to target
											target.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("setSet").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString).replace("%target_name%", targetName).replace("%set_amount%", toSetString)));
											
											//save
											userData.save(userDataFile);
											config.save(configFile);
											
											
										} else {
											//wrong usage
											int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
											String pname = p.getName();
											String puuid = p.getUniqueId().toString();
											String amountString = Integer.toString(amount);
											p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("commandNotFound").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
										}
										
										
									} else {
										//no perms
										int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
										String pname = p.getName();
										String puuid = p.getUniqueId().toString();
										String amountString = Integer.toString(amount);
										p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("noPerms").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
									}
									
								} else {
									//command not found
									int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
									String pname = p.getName();
									String puuid = p.getUniqueId().toString();
									String amountString = Integer.toString(amount);
									p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("commandNotFound").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
								}
							
							}
						
						} else {
							//player not found
							int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
							String pname = p.getName();
							String puuid = p.getUniqueId().toString();
							String amountString = Integer.toString(amount);
							p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("playerNotOnline").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
						}
						
					} else {
						sender.sendMessage("[UST][Error] You have to be a player to execute this command!");
					}
					
				}
				else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						//show help
						
						//permscheck
						if(p.hasPermission("ust.admin.help")) {
							
							p.sendMessage(prefix + ChatColor.DARK_AQUA + "Admin help");
							p.sendMessage("");
							p.sendMessage(ChatColor.DARK_AQUA + "/st add <player> <amount>" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Adds a certain number of tokens to a player!");
							p.sendMessage(ChatColor.DARK_AQUA + "/st take <player> <amount>" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Takes a certain number of tokens to a player!");
							p.sendMessage(ChatColor.DARK_AQUA + "/st set <player> <amount>" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Sets tokens of a player to a certain number!!");
							
						}
						
					} else {
						//command not found
						int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
						String pname = p.getName();
						String puuid = p.getUniqueId().toString();
						String amountString = Integer.toString(amount);
						p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("commandNotFound").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
					}
				} else {
					//command not found
					int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
					String pname = p.getName();
					String puuid = p.getUniqueId().toString();
					String amountString = Integer.toString(amount);
					p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("commandNotFound").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
				}

			} catch(Exception ex) {
				ex.printStackTrace();
			}
			
		return false;
	
	
		}
	
	public static boolean isInt(String s) {
	    try {
	        Integer.parseInt(s);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}

}
