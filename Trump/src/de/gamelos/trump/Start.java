package de.gamelos.trump;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class Start implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("game.start")){
			if(Events.startcount == false){
			if(Bukkit.getOnlinePlayers().size()>=2){
			Events.startcount1();
			sender.sendMessage(ChatColor.GREEN+"Erfolgreich gestartet!");
			}else{
				sender.sendMessage(ChatColor.RED+"Es sind nicht genügend Spieler Online");
			}
			}else{
				if(Events.count1i > 10){
					if(Bukkit.getOnlinePlayers().size()>=2){
					Events.count1i = 10;
					sender.sendMessage(ChatColor.GREEN+"Countdown erfolgreich gekürzt!");
					}else{
						sender.sendMessage(ChatColor.RED+"Es sind nicht genügend Spieler Online");
					}
				}else{
				sender.sendMessage(ChatColor.RED+"Der Countdown läuft bereits");
				}
			}
		}else{
			sender.sendMessage(ChatColor.RED+"Du hast keine Permissions dazu");
		}
		return false;
	}

}
