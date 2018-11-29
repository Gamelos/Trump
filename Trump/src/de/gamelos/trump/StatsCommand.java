package de.gamelos.trump;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class StatsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("stats")){
			if(sender instanceof Player){
			Player p = (Player)sender;
//			
			if(args.length == 0){
				if(SQLStats.playerExists(p.getUniqueId().toString())){
//				===========================
//				...........................
				int kills = SQLStats.getKills(p.getUniqueId().toString(), p.getName());
				int tode = SQLStats.getTode(p.getUniqueId().toString(), p.getName());
				int wins = SQLStats.getWins(p.getUniqueId().toString(), p.getName());
				int Games = SQLStats.getGames(p.getUniqueId().toString(), p.getName());
				int rang = SQLStats.getUserRanking(p.getUniqueId().toString());
				int coins = SQLStats.getCoins(p.getUniqueId().toString(), p.getName());
				double kd = 0.0;
				if(tode == 0){
					kd = kills;
				}else{
				double z = ((double) kills) / ((double) tode);
				kd = Math.round(z * 100.0D) / 100.0D;
				}
//				...........................
				
				p.sendMessage(ChatColor.YELLOW+"============"+ChatColor.AQUA+"Deine Stats"+ChatColor.YELLOW+"=============");
				p.sendMessage(ChatColor.AQUA+"Rang: "+ChatColor.GRAY+rang);
				p.sendMessage(ChatColor.AQUA+"Coins: "+ChatColor.GRAY+coins);
				p.sendMessage(ChatColor.AQUA+"Kills: "+ChatColor.GRAY+kills);
				p.sendMessage(ChatColor.AQUA+"Tode: "+ChatColor.GRAY+tode);
				p.sendMessage(ChatColor.AQUA+"Wins: "+ChatColor.GRAY+wins);
				p.sendMessage(ChatColor.AQUA+"Games: "+ChatColor.GRAY+Games);
				p.sendMessage(ChatColor.AQUA+"K/D: "+ChatColor.GRAY+kd);
				p.sendMessage(ChatColor.YELLOW+"================================");
				
//				===========================
				}else{
					p.sendMessage(ChatColor.RED+"Du hast noch keine Stats");
				}
			}else if(args.length == 1){
				@SuppressWarnings("deprecation")
				OfflinePlayer pp = Bukkit.getOfflinePlayer(args[0]);
				if(SQLStats.playerExists(pp.getUniqueId().toString())){
//				===========================
//					...........................
					int kills = SQLStats.getKills(pp.getUniqueId().toString(), args[0]);
					int tode = SQLStats.getTode(pp.getUniqueId().toString(), args[0]);
					int wins = SQLStats.getWins(pp.getUniqueId().toString(), args[0]);
					int Games = SQLStats.getGames(pp.getUniqueId().toString(), args[0]);
					int rang = SQLStats.getUserRanking(pp.getUniqueId().toString());
					int coins = SQLStats.getCoins(pp.getUniqueId().toString(), pp.getName());
					double kd = 0.0;
					if(tode == 0){
						kd = kills;
					}else{
					double z = ((double) kills) / ((double) tode);
					kd = Math.round(z * 100.0D) / 100.0D;
					}
//					...........................
					
					p.sendMessage(ChatColor.YELLOW+"============"+ChatColor.AQUA+"Stats von "+args[0]+ChatColor.YELLOW+"=============");
					p.sendMessage(ChatColor.AQUA+"Rang: "+ChatColor.GRAY+rang);
					p.sendMessage(ChatColor.AQUA+"Coins: "+ChatColor.GRAY+coins);
					p.sendMessage(ChatColor.AQUA+"Kills: "+ChatColor.GRAY+kills);
					p.sendMessage(ChatColor.AQUA+"Tode: "+ChatColor.GRAY+tode);
					p.sendMessage(ChatColor.AQUA+"Wins: "+ChatColor.GRAY+wins);
					p.sendMessage(ChatColor.AQUA+"Games: "+ChatColor.GRAY+Games);
					p.sendMessage(ChatColor.AQUA+"K/D: "+ChatColor.GRAY+kd);
					p.sendMessage(ChatColor.YELLOW+"==================================");
					
//				===========================
				}else{
					p.sendMessage(ChatColor.RED+"Dieser Spieler hat noch nie Trump gespielt");
				}
			}else{
				p.sendMessage(ChatColor.RED+"Nutze /stats <Spielername>");
			}
//			
			}
		}
		return false;
	}

}
