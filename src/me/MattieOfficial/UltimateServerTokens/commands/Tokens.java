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

public class Tokens implements CommandExecutor {
	
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

	public Tokens(Main main) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		try {
			
			userData.load(userDataFile);
			messages.load(messagesFile);
			config.load(configFile);
			
			
		
			Player p = (Player) sender;
			String prefix = (ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', config.getString("customize.prefix"))) + " ");
			
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("help")) {
						if(p.hasPermission("ust.player.help")) {
							
							//Player has permission, 
							p.sendMessage(prefix + ChatColor.DARK_AQUA + "Help");
							p.sendMessage("");
							p.sendMessage(ChatColor.DARK_AQUA + "/tokens" + ChatColor.WHITE + " - " + ChatColor.GRAY + "See how many tokens you have!");
							p.sendMessage(ChatColor.DARK_AQUA + "/tokens send <player> <amount>" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Send a certain number of tokens to a player!");
							p.sendMessage(ChatColor.DARK_AQUA + "/tokens cheque write <name> <amount>" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Write a cheque for other players to redeem!");
							p.sendMessage(ChatColor.DARK_AQUA + "/tokens cheque redeem <name>" + ChatColor.WHITE + " - " + ChatColor.GRAY + "Redeem a cheque wrote by another player!");
							
						} else {
							
							int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
							String pname = p.getName();
							String puuid = p.getUniqueId().toString();
							String amountString = Integer.toString(amount);
							p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("noPerms").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
						}
				} 
				
					
				 else {
					int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
					String pname = p.getName();
					String puuid = p.getUniqueId().toString();
					String amountString = Integer.toString(amount);
					p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("commandNotFound").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
				}
			
			}
			else if(args.length == 0) {
				if(p.hasPermission("ust.player.tokens")) {
					
					//Check the amount of tokens a player has
					try {
						
						userData.load(userDataFile);
						config.load(configFile);
						messages.load(messagesFile);
						
						int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
						String pname = p.getName();
						String puuid = p.getUniqueId().toString();
						String amountString = Integer.toString(amount);
						
						p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("currentAmount").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
						
					} catch(Exception ex) {
						ex.printStackTrace();
					}
					
				} else {
					int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
					String pname = p.getName();
					String puuid = p.getUniqueId().toString();
					String amountString = Integer.toString(amount);
					p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("noPerms").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
				}
			}
			else if(args.length == 3) {
				if(args[0].equalsIgnoreCase("send")) {
					if(p.hasPermission("ust.player.send")) {
						
						//player has perm, check if args[1] has played before and is registered in the datafile
						Player target = Bukkit.getServer().getPlayerExact(args[1]);

						if(target != null) {
							try {
								
								userData.load(userDataFile);
								if(userData.contains("user." + target.getUniqueId())) {
									
									//Player is online and is in userData.yml
									//check if args[2] is an int
									if(isInt(args[2])) {
										int sendAmount = Integer.parseInt(args[2]);
										
										//Check if players amount is >= as sendAmount, when not say that
										//if so, take that amount from players amount, and add it to targets amount
										
										if(userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount") >= sendAmount) {
											
											int ownCurrent = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
											int targetCurrent = userData.getInt("user." + target.getUniqueId().toString() + ".tokenAmount");
											
											// player has enough money, take amount from player
											// add amount to target
											userData.set("user." + p.getUniqueId().toString() + ".tokenAmount", ownCurrent - sendAmount);
											userData.set("user." + target.getUniqueId().toString() + ".tokenAmount", + targetCurrent + sendAmount);
											
											userData.save(userDataFile);
											
											//send message to player
											int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
											String pname = p.getName();
											String puuid = p.getUniqueId().toString();
											String targetname = target.getName();
											String amountString = Integer.toString(amount);
											String sendString = Integer.toString(sendAmount);
											p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("tokensSent").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString).replace("%send_amount%", sendString).replace("%target_player%", targetname)));
											
											//send message to target
											target.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("tokensReceived").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString).replace("%receive_amount%", sendString).replace("%target_player%", targetname)));
											
											
										} else {
											int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
											String pname = p.getName();
											String puuid = p.getUniqueId().toString();
											String amountString = Integer.toString(amount);
											p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("notEnough").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
										}
									} else {
										int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
										String pname = p.getName();
										String puuid = p.getUniqueId().toString();
										String amountString = Integer.toString(amount);
										p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("commandNotFound").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
									}
									
								}
								
							} catch(Exception ex) {
								ex.printStackTrace();
							}
						} else {
							int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
							String pname = p.getName();
							String puuid = p.getUniqueId().toString();
							String amountString = Integer.toString(amount);
							p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("playerNotOnline").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
						}
						
					} else {
						int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
						String pname = p.getName();
						String puuid = p.getUniqueId().toString();
						String amountString = Integer.toString(amount);
						p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("noPerms").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
					}
				} else if(args[0].equalsIgnoreCase("cheque")) {
					
					//redeem here, all to do is to check if the cheque exists, then add the chequeamount to 
					//players tokenamount, and delete the cheque, and send a message
					if(args[1].equalsIgnoreCase("redeem")) {
						if(p.hasPermission("ust.player.cheque.redeem")) {
							//command is used correctly, check if args[2] is a cheque
							String rChequeName = args[2].toString();
							try {
								
								chequeData.load(chequeDataFile);
								userData.load(userDataFile);
								if(chequeData.contains("cheque." + rChequeName)) {
									
									//cheque exists, add tokens to player and delete cheque
									//register vars
									int rChequeWorth = chequeData.getInt("cheque." + rChequeName + ".chequeAmount");
									int rPlayerCurrent = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
									
									//add amount to player
									userData.set("user." + p.getUniqueId() + ".tokenAmount", rPlayerCurrent + rChequeWorth);
									
									//Delete cheque
									chequeData.set("cheque." + rChequeName, null);
									
									//send the message
									int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
									String pname = p.getName();
									String puuid = p.getUniqueId().toString();
									String amountString = Integer.toString(amount);
									String worthString = Integer.toString(rChequeWorth);
									p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("chequeRedeemed").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString).replace("%cheque_name%", rChequeName).replace("%cheque_amount%", worthString)));
									
									//save
									userData.save(userDataFile);
									chequeData.save(chequeDataFile);
									
								} else {
									//send noexist message
									int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
									String pname = p.getName();
									String puuid = p.getUniqueId().toString();
									String amountString = Integer.toString(amount);
									p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("chequeNoExist").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
								}
								
							} catch(Exception ex) {
								ex.printStackTrace();
							}
						
						} else {
							int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
							String pname = p.getName();
							String puuid = p.getUniqueId().toString();
							String amountString = Integer.toString(amount);
							p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("noPerms").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
						}
						
					}
					
				} else {
					//more commands with 3 args can go here
					int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
					String pname = p.getName();
					String puuid = p.getUniqueId().toString();
					String amountString = Integer.toString(amount);
					p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("commandNotFound").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
				}
				
			}
			else if(args.length == 4) {
				if(args[0].equalsIgnoreCase("cheque")) {
					
					if(args[1].equalsIgnoreCase("write")) {
						if(p.hasPermission("ust.player.cheque.write")) {
						
							//is write, check if args[3] is an int
							if(isInt(args[3])) {
								
								//args[3] is a int, check if args[3] is >= tokenAmount of writer
								int playerCurrentTokens = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
								int chequeAmount = Integer.parseInt(args[3]);
								
								String chequeName = args[2].toString();
								String chequeWriter = p.getName();
								String writerUuid = p.getUniqueId().toString();
								
								if(userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount") >= chequeAmount) {
									
									//player has enough money, check if cheque doent exist
									try {
										
										userData.load(userDataFile);
										chequeData.load(chequeDataFile);
										
										if(!(chequeData.contains("cheque." + chequeName))) {
											
											//write cheque
											chequeData.set("cheque." + chequeName + ".name", chequeName);
											chequeData.set("cheque." + chequeName + ".writer", chequeWriter);
											chequeData.set("cheque." + chequeName + ".writerUuid", writerUuid);
											chequeData.set("cheque." + chequeName + ".chequeAmount", chequeAmount);
											
											//take amount from players amount
											userData.set("user." + p.getUniqueId().toString() + ".tokenAmount", playerCurrentTokens - chequeAmount);
											
											//send message
											int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
											String pname = p.getName();
											String puuid = p.getUniqueId().toString();
											String amountString = Integer.toString(amount);
											String chequeWorth = Integer.toString(chequeAmount);
											
											p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("chequeCreated").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString).replace("%cheque_amount%", chequeWorth).replace("%cheque_writer%", chequeWriter).replace("%cheque_name%", chequeName)));
											
											//save files
											userData.save(userDataFile);
											chequeData.save(chequeDataFile);
											
										} else {
											int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
											String pname = p.getName();
											String puuid = p.getUniqueId().toString();
											String amountString = Integer.toString(amount);
											String chequeWorth = Integer.toString(chequeAmount);
											
											p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("chequeAlreadyExists").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString).replace("%cheque_amount%", chequeWorth).replace("%cheque_writer%", chequeWriter).replace("%cheque_name%", chequeName)));
										}
										
									} catch(Exception ex) {
										ex.printStackTrace();
									}
									
									
								} else {
									int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
									String pname = p.getName();
									String puuid = p.getUniqueId().toString();
									String amountString = Integer.toString(amount);
									p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("notEnough").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
								}
								
							} else {
								int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
								String pname = p.getName();
								String puuid = p.getUniqueId().toString();
								String amountString = Integer.toString(amount);
								p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("commandNotFound").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
							}
						
						} else {
							int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
							String pname = p.getName();
							String puuid = p.getUniqueId().toString();
							String amountString = Integer.toString(amount);
							p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("noPerms").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
						}
						
					} else {
						int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
						String pname = p.getName();
						String puuid = p.getUniqueId().toString();
						String amountString = Integer.toString(amount);
						p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("commandNotFound").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
					}
					
				} else {
					//evt more commands with 5 args here
					int amount = userData.getInt("user." + p.getUniqueId().toString() + ".tokenAmount");
					String pname = p.getName();
					String puuid = p.getUniqueId().toString();
					String amountString = Integer.toString(amount);
					p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', messages.getString("commandNotFound").replace("%player_name%", pname).replace("%player_uuid%", puuid).replace("%amount%", amountString)));
				}
			} else {
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
