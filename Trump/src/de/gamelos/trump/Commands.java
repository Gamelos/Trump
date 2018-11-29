package de.gamelos.trump;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import net.md_5.bungee.api.ChatColor;

public class Commands implements CommandExecutor, Listener {

	public static Location drog;
	
	public static void setdrog(){
		int b = 0;
		for(int i = 1; Main.loc.getString(Main.mapname+".spawns."+i+".x")!=null;i++){
			b++;
		}
//		
		Random r = new Random();
		int a = r.nextInt(b-1)+1;
		
		Double x1 = Main.loc.getDouble(Main.mapname+".spawns."+a+".x");
		Double y1 = Main.loc.getDouble(Main.mapname+".spawns."+a+".y");
		Double z1 = Main.loc.getDouble(Main.mapname+".spawns."+a+".z");
		Float yaw1 = (float) Main.loc.getDouble(Main.mapname+".spawns."+a+".yaw");
		Float pitch1 = (float) Main.loc.getDouble(Main.mapname+".spawns."+a+".pitch");
		World w1 = Bukkit.getWorld("gameworld");
		drog = new Location(w1,x1,y1,z1,yaw1,pitch1);
//		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equals("trump")){
			Player p = (Player)sender;
			if(p.isOp()){
				if(args.length>=1){
				if(args[0].equals("setspawn")){
				Location loc = p.getLocation();
				Main.loc.set("spawn.x", loc.getX());
				Main.loc.set("spawn.y", loc.getY());
				Main.loc.set("spawn.z", loc.getZ());
				Main.loc.set("spawn.yaw", loc.getYaw());
				Main.loc.set("spawn.pitch", loc.getPitch());
				Main.loc.set("spawn.world", loc.getWorld().getName());
				try {
					Main.loc.save(Main.locations);
					p.sendMessage(ChatColor.GREEN+"Der Spawn wurde erfolgreich gesetzt");	
				} catch (IOException e) {
					e.printStackTrace();
				}
				}else if(args[0].equals("addmap")){
					if(args.length == 4){
						int i = Main.getmapanzahl() + 1;
						Main.loc.set("Mapnames." + i, args[1]);
						Main.loc.set(args[1] + ".maxplayers", args[2]);
						Main.loc.set(args[1] + ".minplayers", args[3]);
						try {
							Main.loc.save(Main.locations);
							Bukkit.broadcastMessage(ChatColor.GREEN + "Die Map wurde erfolgreich gespeichert");
						} catch (IOException e) {
							e.printStackTrace();
						}
						}else{
							p.sendMessage(Main.Prefix+ChatColor.RED+"/trump addmap <mapname> <maxplayers> <minplayers>");
						}
				}else if (args[0].equalsIgnoreCase("maplist")) {
					// TODO /sw maplist
					if(args.length == 1){
					int ii = 1;
					while (Main.loc.getString("Mapnames." + ii) != null) {
						Bukkit.broadcastMessage(Main.loc.getString("Mapnames." + ii));
						ii++;
					}
					}else{
						p.sendMessage(Main.Prefix+ChatColor.RED+"/trump maplist");
					}

				} else if (args[0].equalsIgnoreCase("addspawn")) {
					// TODO /sw setspawn <map>
					int i = Main.getspawnanzahl(args[1]) + 1;

					Main.loc.set(args[1] + ".spawns." + i + ".x", p.getLocation().getX());
					Main.loc.set(args[1] + ".spawns." + i + ".y", p.getLocation().getY());
					Main.loc.set(args[1] + ".spawns." + i + ".z", p.getLocation().getZ());
					Main.loc.set(args[1] + ".spawns." + i + ".yaw", p.getLocation().getYaw());
					Main.loc.set(args[1] + ".spawns." + i + ".pitch", p.getLocation().getPitch());

					try {
						Main.loc.save(Main.locations);
						Bukkit.broadcastMessage(
								ChatColor.GREEN + "Der spawn " + i + " der Map " + args[1] + " wurde erfolgreich gesetzt!");
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (args[0].equalsIgnoreCase("tp")) {
					p.teleport(Bukkit.getWorld(args[1]).getSpawnLocation());
				}else if(args[0].equalsIgnoreCase("settester")){
					settester.put(p, "Lampen");
					map.put(p, args[1]);
					Main.loc.set(map.get(p) + ".tester.spawn.x1", p.getLocation().getX());
					Main.loc.set(map.get(p) + ".tester.spawn.y1", p.getLocation().getY());
					Main.loc.set(map.get(p) + ".tester.spawn.z1", p.getLocation().getZ());
					try {
						Main.loc.save(Main.locations);
						Bukkit.broadcastMessage(
								ChatColor.GREEN + "Der Spawn wurde erfolgreich gesetzt!");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					p.sendMessage(Main.Prefix+"Klicke als erstes auf die beiden Lampen");
				}else if(args[0].equalsIgnoreCase("setsoillocation")){
					Main.loc.set(args[1] + ".tester.soil.x1", p.getLocation().getX());
					Main.loc.set(args[1] + ".tester.soil.y1", p.getLocation().getY());
					Main.loc.set(args[1] + ".tester.soil.z1", p.getLocation().getZ());
					try {
						Main.loc.save(Main.locations);
						Bukkit.broadcastMessage(
								ChatColor.GREEN + "Der Soillocation wurde erfolgreich gesetzt!");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}else if(args[0].equalsIgnoreCase("setdrogenspawn")){
					Main.loc.set(args[1] + ".drogenspawn.x1", p.getLocation().getX());
					Main.loc.set(args[1] + ".drogenspawn.y1", p.getLocation().getY());
					Main.loc.set(args[1] + ".drogenspawn.z1", p.getLocation().getZ());
					try {
						Main.loc.save(Main.locations);
						Bukkit.broadcastMessage(
								ChatColor.GREEN + "Der Drogenspawn wurde erfolgreich gesetzt!");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}else{
					p.sendMessage(ChatColor.GRAY+"===============================");
					p.sendMessage(ChatColor.GOLD+"/trump addmap <mapname> <maxplayers> <minplayers>");
					p.sendMessage(ChatColor.GOLD+"/trump maplist");
					p.sendMessage(ChatColor.GOLD+"/trump addspawn <map>");
					p.sendMessage(ChatColor.GOLD+"/trump setspawn");
					p.sendMessage(ChatColor.GOLD+"/trump tp <mapname>");
					p.sendMessage(ChatColor.GOLD+"/trump settester <mapname>");
					p.sendMessage(ChatColor.GOLD+"/trump setsoillocation <mapname>");
					p.sendMessage(ChatColor.GOLD+"/trump setdrogenspawn <mapname>");
					p.sendMessage(ChatColor.GRAY+"===============================");
				}
				}else{
					p.sendMessage(ChatColor.GRAY+"===============================");
					p.sendMessage(ChatColor.GOLD+"/trump addmap <mapname> <maxplayers> <minplayers>");
					p.sendMessage(ChatColor.GOLD+"/trump maplist");
					p.sendMessage(ChatColor.GOLD+"/trump addspawn <map>");
					p.sendMessage(ChatColor.GOLD+"/trump setspawn");
					p.sendMessage(ChatColor.GOLD+"/trump tp <mapname>");
					p.sendMessage(ChatColor.GOLD+"/trump settester <mapname>");
					p.sendMessage(ChatColor.GOLD+"/trump setsoillocation <mapname>");
					p.sendMessage(ChatColor.GOLD+"/trump setdrogenspawn <mapname>");
					p.sendMessage(ChatColor.GRAY+"===============================");
				}
			}else{
				p.sendMessage(Main.Prefix+ChatColor.RED+"Du hast keine Rechte dazu");
			}
		}else if(cmd.getName().equals("promote")){
			Player p = (Player)sender;
			if(p.hasPermission("rang.premium")){
				if(promote){
			p.sendMessage(Main.Prefix+ChatColor.GREEN+"Du hast die Runde erfolgreich promotet");
			sendmsgtobungee("promote", p);
			promote = false;
			Bukkit.getScheduler().scheduleAsyncDelayedTask(Bukkit.getPluginManager().getPlugin("Trump"), new Runnable(){
				@Override
				public void run() {
					promote = true;
				}
			}, 6000);
				}else{
					p.sendMessage(Main.Prefix+ChatColor.RED+"Die Runde wurde erst vor kurzem promotet");
				}
			}else{
				p.sendMessage(Main.Prefix+ChatColor.RED+"Du brauchst Premium um dieses Feature nutzen zu können");
			}
		}	
		return false;
	}
//	
	
	
	public static HashMap<Player,String> map = new HashMap<>();
	public static HashMap<Player,String> settester = new HashMap<>();
	public static ArrayList<Player> ram = new ArrayList<>();

	
	
	@EventHandler
	public void onklick(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
		if(settester.containsKey(p)){
			if(settester.get(p).equals("Lampen")){
				if(ram.contains(p)){
					Main.loc.set(map.get(p) + ".tester.lampen.x2", e.getClickedBlock().getLocation().getBlockX());
					Main.loc.set(map.get(p) + ".tester.lampen.y2", e.getClickedBlock().getLocation().getBlockY());
					Main.loc.set(map.get(p) + ".tester.lampen.z2", e.getClickedBlock().getLocation().getBlockZ());
					try {
						Main.loc.save(Main.locations);
						Bukkit.broadcastMessage(
								ChatColor.GREEN + "Die Lampe 2 wurde erfolgreich gesetzt!");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					ram.remove(p);
					settester.remove(p);
					settester.put(p, "Knopf");
					p.sendMessage(Main.Prefix+"Setze als nächstes den Knopf");
				}else{
					Main.loc.set(map.get(p) + ".tester.lampen.x1", e.getClickedBlock().getLocation().getBlockX());
					Main.loc.set(map.get(p) + ".tester.lampen.y1", e.getClickedBlock().getLocation().getBlockY());
					Main.loc.set(map.get(p) + ".tester.lampen.z1", e.getClickedBlock().getLocation().getBlockZ());
					try {
						Main.loc.save(Main.locations);
						Bukkit.broadcastMessage(
								ChatColor.GREEN + "Die Lampe 1 wurde erfolgreich gesetzt!");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					ram.add(p);
				}
			}else if(settester.get(p).equals("Knopf")){
				if(e.getClickedBlock().getType() == Material.STONE_BUTTON){
					Main.loc.set(map.get(p) + ".tester.knopf.x1", e.getClickedBlock().getLocation().getBlockX());
					Main.loc.set(map.get(p) + ".tester.knopf.y1", e.getClickedBlock().getLocation().getBlockY());
					Main.loc.set(map.get(p) + ".tester.knopf.z1", e.getClickedBlock().getLocation().getBlockZ());
					try {
						Main.loc.save(Main.locations);
						Bukkit.broadcastMessage(
								ChatColor.GREEN + "Der Knopf wurde erfolgreich gesetzt!");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					settester.remove(p);
					settester.put(p, "barrier");
					p.sendMessage(Main.Prefix+"Klicke auf die Barriere blöcke");
				}
			}else if(settester.get(p).equals("barrier")){
				if(baramount.containsKey(p)){
					Main.loc.set(map.get(p) + ".tester.barrier.x"+baramount.get(p), e.getClickedBlock().getLocation().getBlockX());
					Main.loc.set(map.get(p) + ".tester.barrier.y"+baramount.get(p), e.getClickedBlock().getLocation().getBlockY());
					Main.loc.set(map.get(p) + ".tester.barrier.z"+baramount.get(p), e.getClickedBlock().getLocation().getBlockZ());
					try {
						Main.loc.save(Main.locations);
						Bukkit.broadcastMessage(
								ChatColor.GREEN + "Die Barriere "+baramount.get(p)+" wurde erfolgreich gesetzt!");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					int i = baramount.get(p)+1;
					baramount.remove(p);
					baramount.put(p, i);
					
				}else{
				Main.loc.set(map.get(p) + ".tester.barrier.x1", e.getClickedBlock().getLocation().getBlockX());
				Main.loc.set(map.get(p) + ".tester.barrier.y1", e.getClickedBlock().getLocation().getBlockY());
				Main.loc.set(map.get(p) + ".tester.barrier.z1", e.getClickedBlock().getLocation().getBlockZ());
				try {
					Main.loc.save(Main.locations);
					Bukkit.broadcastMessage(
							ChatColor.GREEN + "Die Barriere 1 wurde erfolgreich gesetzt!");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				baramount.put(p, 2);
				}
			}
		}
		}
	}
	public static HashMap<Player,Integer> baramount = new HashMap<>();
	@EventHandler
	public void onsneak(PlayerToggleSneakEvent e){
		Player p = e.getPlayer();
		if(baramount.containsKey(p)){
			p.sendMessage(Main.Prefix+"Die einrichtung wurde erfolgreich abgeschlossen");
			baramount.remove(p);
			settester.remove(p);
			map.remove(p);
		}
	}
	
	boolean promote = true;
	
    public void sendmsgtobungee(String msg, Player p) {
    	ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("data");
			out.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		p.sendPluginMessage(Bukkit.getPluginManager().getPlugin("Trump"), "BungeeCord", b.toByteArray());
    }
	
}
