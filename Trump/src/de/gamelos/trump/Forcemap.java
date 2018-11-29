package de.gamelos.trump;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class Forcemap implements CommandExecutor, Listener {

	public static boolean force = false;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("game.forcemap")){
			if(force == false || Main.ingame==true || Events.count1i <=5){
				
				Inventory inv = Bukkit.createInventory(null, 9*4, ChatColor.YELLOW+"Maps");
				int ii = 1;
				while (Main.loc.getString("Mapnames." + ii) != null) {
					String ss = Main.loc.getString("Mapnames." + ii);
					inv.addItem(getrandomitem(ChatColor.YELLOW+ss));
					ii++;
				}
				Player p = (Player) sender;
				p.openInventory(inv);
				
			}else{
				sender.sendMessage(ChatColor.RED+"Es wurde bereits eine Map gewählt");
			}
		}else{
			sender.sendMessage(ChatColor.RED+"Du hast keine Permissions dazu");
		}
		return false;
	}

	public static ItemStack getrandomitem(String name){
		ItemStack item = null;
		Material m = null;
		Random r = new Random();
		int i = r.nextInt(4)+1;
		if(i==1){
			m = Material.WOOD;
		}else if(i==2){
			m = Material.DIAMOND_BLOCK;
		}else if(i==3){
			m = Material.GOLD_BLOCK;
		}else if(i==4){
			m = Material.IRON_INGOT;
		}else{
			m = Material.GRASS;
		}
		item = new ItemStack(m);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
	
	@EventHandler
	public void onk(InventoryClickEvent e){
		if(e.getClickedInventory() != null){
		if(e.getClickedInventory().getName() != null){
		if(e.getClickedInventory().getName().equals(ChatColor.YELLOW+"Maps")){
			if(e.getCurrentItem() != null){
				if(force == false || Main.ingame==true || Events.count1i <=5){
					force = true;
					String ss = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
					Main.mapname = ss;
					e.getWhoClicked().closeInventory();
					if(Bukkit.getOnlinePlayers().size() < Integer.parseInt(Main.loc.getString(Main.mapname + ".minplayers"))){
						Bukkit.getScheduler().cancelTask(Events.count1);
					}
					e.getWhoClicked().sendMessage(ChatColor.GREEN+"Die Map wurde erfolgrich gewählt");
					Main.setlobbyscoreboard();
					
				}else{
					e.getWhoClicked().sendMessage(ChatColor.RED+"Es wurde bereits eine Map gewählt");
				}
				
			}
		}
		}
		}
	}
	
}
