package me.MattieOfficial.UltimateServerTokens.library;

import org.bukkit.Bukkit;

public class ConsoleLogs {
	
	public static void Info(String message) {
		
		Bukkit.getServer().getLogger().info("[SimpleTokens][Info]" + message);
		
	}
	
	public static void Error(String message) {
		
		Bukkit.getServer().getLogger().info("[SimpleTokens][Error]" + message);
		
	}
	
	public static void Load(String message) {
		
		Bukkit.getServer().getLogger().info("[SimpleTokens][Load]" + message);
		
	}

}
