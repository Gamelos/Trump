package de.gamelos.trump;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import com.connorlinfoot.titleapi.TitleAPI;
import de.dytanic.cloudnet.bridge.CloudServer;
import de.gamelos.jaylosapi.JaylosAPI;
import de.gamelos.nick.unNickEvent;

@SuppressWarnings("deprecation")
public class Events implements Listener {
	public static org.bukkit.plugin.Plugin pl = Bukkit.getPluginManager().getPlugin("Trump");
	
	
	@EventHandler
	public void onjoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if(Main.ingame == false){
			e.setJoinMessage(null);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Trump"), new Runnable(){
				@Override
				public void run() {
					for(Player pp:Bukkit.getOnlinePlayers()){
						pp.sendMessage(Main.Prefix+JaylosAPI.getchatname(e.getPlayer(), pp)+ChatColor.DARK_GRAY+" ist dem Spiel beigetreten!");
					}
				}
			}, 10);
//			
			p.getInventory().clear();
			p.setFoodLevel(20);
			p.setHealth(20);
			p.setLevel(0);
			p.setExp(0);
			p.getInventory().setHelmet(new ItemStack(Material.AIR));
			p.getInventory().setLeggings(new ItemStack(Material.AIR));
			p.getInventory().setBoots(new ItemStack(Material.AIR));
			p.getInventory().setChestplate(new ItemStack(Material.AIR));
			TitleAPI.sendTabTitle(p, "§8===================§9 Jaylos.net §7- §eTrump §8===================",
					ChatColor.GRAY + "Reporte Spieler mit " + ChatColor.RED + "/report " + ChatColor.GRAY
							+ "oder erstelle eine Party mit" + ChatColor.DARK_PURPLE + " /party");
//			
			ItemStack item = new ItemStack(Material.PAPER);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.YELLOW+"Game-Setup §8(§7Rechtsklick§8)");
			item.setItemMeta(meta);
			p.getInventory().setItem(0, item);
			Main.setlobbyscoreboard();
//			
			for (PotionEffect effect : p.getActivePotionEffects()) {
				p.removePotionEffect(effect.getType());
			}
			Double x1 = Main.loc.getDouble("spawn.x");
			Double y1 = Main.loc.getDouble("spawn.y");
			Double z1 = Main.loc.getDouble("spawn.z");
			Float yaw1 = (float) Main.loc.getDouble("spawn.yaw");
			Float pitch1 = (float) Main.loc.getDouble("spawn.pitch");
			World w1 = Bukkit.getWorld(Main.loc.getString("spawn.world"));
			Location lobbyspawn = new Location(w1,x1,y1,z1,yaw1,pitch1);
			p.teleport(lobbyspawn);
			p.setGameMode(GameMode.SURVIVAL);
//			
			if(Bukkit.getOnlinePlayers().size() >= Integer.parseInt(Main.loc.getString(Main.mapname + ".minplayers"))){
				startcount1();
			}
//			
		}else{
			e.setJoinMessage(null);
			Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
				@Override
				public void run() {
					for(Player pp:Bukkit.getOnlinePlayers()){
						if(!Main.spectatorplayer.contains(pp)){
							pp.hidePlayer(p);
						}
					}
				}
			},20);
			Main.spectatorplayer.add(p);
			Main.setspectatorscoreboard(e.getPlayer());
			e.getPlayer().setHealth(20);
			e.getPlayer().setFoodLevel(20);
			for(Player pp : Main.playeringame){
				pp.hidePlayer(e.getPlayer());
			}
			TitleAPI.sendTabTitle(e.getPlayer(), "§8===================§9 Jaylos.net §7- §eTrump §8===================",
					ChatColor.GRAY + "Reporte Spieler mit " + ChatColor.RED + "/report " + ChatColor.GRAY
							+ "oder erstelle eine Party mit" + ChatColor.DARK_PURPLE + " /party");
			setspectator(e.getPlayer());
		}
	}
//	
	@EventHandler
	public void onbrak(BlockBreakEvent e){
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onplace(BlockPlaceEvent e){
		if(Main.ingame == false|| schutz){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onfood(FoodLevelChangeEvent e){
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onhealth(EntityDamageEvent e){
		if(Main.ingame == false|| schutz){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void ondrop(PlayerDropItemEvent e){
		if(Main.ingame == false){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void oninv(InventoryClickEvent e){
		if(Main.ingame == false){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onhit(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Player){
			if(Main.spectatorplayer.contains(e.getDamager())){
				e.setCancelled(true);
			}
		}
	}
	
//	====================TESTER===============================================
	public static boolean istesteractive = false;
	public static boolean delay = false;
	@EventHandler
	public void onbutton(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getClickedBlock().getType() == Material.STONE_BUTTON){
				if(e.getClickedBlock().getWorld().getName().equals("gameworld")){
				if(schutz == false){
				if(team.containsKey(p)){
				if(istesteractive==false){
					if(delay==false){
				Double x1 = Main.loc.getDouble(Main.mapname+".tester.knopf.x1");
				Double y1 = Main.loc.getDouble(Main.mapname+".tester.knopf.y1");
				Double z1 = Main.loc.getDouble(Main.mapname+".tester.knopf.z1");
				Location b  = e.getClickedBlock().getLocation();
				if(b.getBlockX()==x1&&b.getBlockY()==y1&&b.getBlockZ()==z1){
					for(Player pp:Bukkit.getOnlinePlayers()){
						pp.sendMessage(Main.Prefix+JaylosAPI.getchatname(p, pp)+ChatColor.GRAY+" ist den Taco-Tester beigetreten");
					}
					Double x11 = Main.loc.getDouble(Main.mapname+".tester.spawn.x1");
					Double y11 = Main.loc.getDouble(Main.mapname+".tester.spawn.y1");
					Double z11 = Main.loc.getDouble(Main.mapname+".tester.spawn.z1");
					Location loc = new Location(p.getWorld(), x11, y11, z11);
					p.teleport(loc);
					removeplayer(p);
					istesteractive = true;
					for(Player pp:Bukkit.getOnlinePlayers()){
						pp.playSound(loc, Sound.NOTE_BASS, 5F, 5F);
					}
//					setbarrier
					setbarrier(Material.STAINED_GLASS, p);
//					
					playeffekt(p);
					Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
						@Override
						public void run() {
							if((!Main.spectatorplayer.contains(p))&&Events.team.containsKey(p)){
							if(Shop.pass.contains(p)||Shop.visum.contains(p)){
								Shop.pass.remove(p);
//								
								for(Player pp:Bukkit.getOnlinePlayers()){
									pp.playSound(loc, Sound.NOTE_PIANO, 5F, 5F);
								}
								setlampen(p, DyeColor.GREEN);
								istesteractive = false;
								delay = true;
								Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
									@Override
									public void run() {
										delay = false;
									}
								},20*10);
								Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
									@Override
									public void run() {
										setlampen(p, DyeColor.WHITE);
									}
								},60);
								setbarrier(Material.AIR, p);
//								
							}else if(team.get(p).equals("Mexikaner")){
//								
								for(Player pp:Bukkit.getOnlinePlayers()){
									pp.playSound(loc, Sound.ENDERDRAGON_GROWL, 5F, 5F);
								}
								setlampen(p, DyeColor.RED);
								istesteractive = false;
								delay = true;
								Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
									@Override
									public void run() {
										delay = false;
									}
								},20*10);
								Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
									@Override
									public void run() {
										setlampen(p, DyeColor.WHITE);
									}
								},60);
								setbarrier(Material.AIR, p);
//								
							}else{
//								
							for(Player pp:Bukkit.getOnlinePlayers()){
								pp.playSound(loc, Sound.NOTE_PIANO, 5F, 5F);
							}
							setlampen(p, DyeColor.GREEN);
							istesteractive = false;
							delay = true;
							Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
								@Override
								public void run() {
									delay = false;
								}
							},20*10);
							Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
								@Override
								public void run() {
									setlampen(p, DyeColor.WHITE);
								}
							},60);
							setbarrier(Material.AIR, p);
//							
							}
						}
						}
					},200);
//					
				}
					}else{
						p.sendMessage(Main.Prefix+"Der Tester ist erst in ein paar Sekunden wieder bereit!");
					}
				}else{
					p.sendMessage(Main.Prefix+ChatColor.RED+"Der Tester ist bereits besetzt!");
				}
				}else{
					p.sendMessage(Main.Prefix+ChatColor.RED+"Du kannst den Tester nicht betreten!");
				}
			}else{
				p.sendMessage(Main.Prefix+ChatColor.RED+"Du kannst den Tester jetzt noch nicht betreten!");
			}
		}
			}
		}
	}
	
	public static void setbarrier(Material m, Player p){
		for(int i = 1;(Main.loc.getString(Main.mapname+".tester.barrier.x"+i))!=null;i++){
			Double x1 = Main.loc.getDouble(Main.mapname+".tester.barrier.x"+i);
			Double y1 = Main.loc.getDouble(Main.mapname+".tester.barrier.y"+i);
			Double z1 = Main.loc.getDouble(Main.mapname+".tester.barrier.z"+i);
			Location loc = new Location(p.getWorld(), x1, y1, z1);
			p.getWorld().getBlockAt(loc).setType(m);
		}
	}
	
	public static int count;
	public static int data = 0;
	public static void playeffekt(Player p){
		count = Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Runnable(){
			@Override
			public void run() {
				if(data<=40){
					data++;
					Location loc = p.getLocation();
					p.playEffect(loc, Effect.INSTANT_SPELL, 1);
					p.playEffect(loc, Effect.INSTANT_SPELL, 1);
					p.playEffect(loc, Effect.INSTANT_SPELL, 1);
					p.playEffect(loc, Effect.INSTANT_SPELL, 1);
				}else{
				Bukkit.getScheduler().cancelTask(count);
				data = 0;	
				}
			}
		}, 0, 5);
	}
	
	public static void setlampen(Player p, DyeColor b){
		Double x1 = Main.loc.getDouble(Main.mapname+".tester.lampen.x1");
		Double y1 = Main.loc.getDouble(Main.mapname+".tester.lampen.y1");
		Double z1 = Main.loc.getDouble(Main.mapname+".tester.lampen.z1");
		Location loc = new Location(p.getWorld(), x1, y1, z1);
		Double x2 = Main.loc.getDouble(Main.mapname+".tester.lampen.x2");
		Double y2 = Main.loc.getDouble(Main.mapname+".tester.lampen.y2");
		Double z2 = Main.loc.getDouble(Main.mapname+".tester.lampen.z2");
		Location loc2 = new Location(p.getWorld(), x2, y2, z2);
		p.getWorld().getBlockAt(loc).setData(b.getData());
		p.getWorld().getBlockAt(loc2).setData(b.getData());
	}
	
	public static void removeplayer(Player p){
//		
		Double y1 = Main.loc.getDouble(Main.mapname+".tester.knopf.y1");
		Double y11 = Main.loc.getDouble(Main.mapname+".tester.knopf.y1");
		int y22 = y11.intValue();
		int y2 = y22-1;
//		
		Double soilx1 = Main.loc.getDouble(Main.mapname+".tester.soil.x1");
		Double soily1 = Main.loc.getDouble(Main.mapname+".tester.soil.y1");
		Double soilz1 = Main.loc.getDouble(Main.mapname+".tester.soil.z1");
		Location soil = new Location(p.getWorld(), soilx1, soily1, soilz1);
		Double x11 = Main.loc.getDouble(Main.mapname+".tester.barrier.x1");
		Double z11 = Main.loc.getDouble(Main.mapname+".tester.barrier.z1");
		Double x12 = Main.loc.getDouble(Main.mapname+".tester.barrier.x2");
		Double z12 = Main.loc.getDouble(Main.mapname+".tester.barrier.z2");
		String s = "";
		if(x11.intValue()==x12.intValue()){
			s = "x";
		}else if(z11.intValue()==z12.intValue()){
			s = "z";
		}
		
		ArrayList<Integer>barriers = new ArrayList<>();
		String b = "";
		if(s.equals("x")){
			b = "z";
		}else if(s.equals("z")){
			b = "x";
		}
		for(int i = 1;(Main.loc.getString(Main.mapname+".tester.barrier.x"+i))!=null;i++){
			Double x111 = Main.loc.getDouble(Main.mapname+".tester.barrier."+b+i);
			barriers.add(x111.intValue());
		}
//		
		Double x1 = Main.loc.getDouble(Main.mapname+".tester.knopf."+s+"1");
		Double z121 = Main.loc.getDouble(Main.mapname+".tester.barrier."+s+"1");
		if(x1< z121){
			for(int i = x1.intValue();i<= z121;i++){
				for(Integer ii: barriers){
					int x;
					int z;
					if(s.equals("x")){
						 x = i;
						 z = ii;
						}else{
							 x = ii;
							 z = i;
						}
					for(Player pp : Bukkit.getOnlinePlayers()){
						if(pp.getLocation().getBlockX()==x&&pp.getLocation().getBlockZ()==z&&(pp.getLocation().getBlockY()==y1||pp.getLocation().getBlockY()==y2)){
							if(pp!=p){
							pp.teleport(soil);
							}
						}
					}
				}
			}
		}else{
			for(int i = x1.intValue();i>= z121;i--){
				for(Integer ii: barriers){
					int x;
					int z;
					if(s.equals("x")){
						 x = i;
						 z = ii;
						}else{
							 x = ii;
							 z = i;
						}
					for(Player pp : Bukkit.getOnlinePlayers()){
						if(pp.getLocation().getBlockX()==x&&pp.getLocation().getBlockZ()==z&&(pp.getLocation().getBlockY()==y1||pp.getLocation().getBlockY()==y2)){
							if(pp!=p){
							pp.teleport(soil);
							}
						}
					}
				}
			}
		}
		
		
	}
	
	@EventHandler
	public void oniii(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(Main.spectatorplayer.contains(e.getPlayer())){
				e.setCancelled(true);
			}
		}
	}
	
//	===========================KISTEN====================================
	@EventHandler
	public void oni (PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getClickedBlock().getLocation().getWorld().getName().equals("gameworld")){
			if(e.getClickedBlock().getType() == Material.CHEST && (!Main.spectatorplayer.contains(e.getPlayer()))){
//				if(Main.ingame){
				e.setCancelled(true);
				if(!(containitem(Material.BOW,e.getPlayer())&&containitem(Material.STONE_SWORD,e.getPlayer())&&containitem(Material.WOOD_SWORD,e.getPlayer()))){
				e.getClickedBlock().setType(Material.AIR);
				e.getPlayer().playSound(e.getClickedBlock().getLocation(), Sound.CHEST_OPEN, 1F, 1F);
				for(Player pp:Bukkit.getOnlinePlayers()){
					pp.playEffect(e.getClickedBlock().getLocation(), Effect.SMOKE, 5);
				}
				Player p = e.getPlayer();
				if(containitem(Material.WOOD_SWORD,p)){
					if(containitem(Material.STONE_SWORD,p)){
						p.getInventory().addItem(new ItemStack(Material.BOW));
						p.getInventory().addItem(new ItemStack(Material.ARROW, 32));
					}else{
						Random r = new Random();
						int i = r.nextInt(1)+1;
						if(i==0){
							p.getInventory().addItem(new ItemStack(Material.BOW));	
							p.getInventory().addItem(new ItemStack(Material.ARROW, 32));	
						}else{
							p.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
						}
					}
				}else if(containitem(Material.STONE_SWORD,p)){
					if(containitem(Material.WOOD_SWORD,p)){
						p.getInventory().addItem(new ItemStack(Material.BOW));
						p.getInventory().addItem(new ItemStack(Material.ARROW, 32));	
					}else{
						Random r = new Random();
						int i = r.nextInt(1)+1;
						if(i==0){
							p.getInventory().addItem(new ItemStack(Material.BOW));	
							p.getInventory().addItem(new ItemStack(Material.ARROW, 32));	
						}else{
							p.getInventory().addItem(new ItemStack(Material.WOOD_SWORD));
						}
					}
				}else if(containitem(Material.BOW,p)){
					if(containitem(Material.WOOD_SWORD,p)){
						p.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
					}else{
						Random r = new Random();
						int i = r.nextInt(1)+1;
						if(i==0){
							p.getInventory().addItem(new ItemStack(Material.STONE_SWORD));	
						}else{
							p.getInventory().addItem(new ItemStack(Material.WOOD_SWORD));
						}
					}
				}else{
					Random r = new Random();
					int i = r.nextInt(2)+1;
					if(i==0){
						p.getInventory().addItem(new ItemStack(Material.STONE_SWORD));	
					}else if (i==1){
						p.getInventory().addItem(new ItemStack(Material.WOOD_SWORD));
					}else{
						p.getInventory().addItem(new ItemStack(Material.BOW));
						p.getInventory().addItem(new ItemStack(Material.ARROW, 32));	
					}
				}
				}
//				}
			}else if(e.getClickedBlock().getType() == Material.ENDER_CHEST){
				if(!Main.spectatorplayer.contains(e.getPlayer())){
				if(schutz == false){
					e.setCancelled(true);
					e.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_SWORD));
					e.getClickedBlock().setType(Material.AIR);
				}else{
					e.setCancelled(true);
					e.getPlayer().sendMessage(ChatColor.RED+"Du kannst diese Kiste erst nach der Schutzzeit öffnen");
				}
				}
			}
		}
		}
	}
	
	public static boolean containitem(Material m,Player p){
		boolean b= false;
		for(ItemStack item:p.getInventory().getContents()){
			if(item!=null){
			if(item.getType() == m){
				b = true;
			}
			}
		}
		return b;
	}
	
	
	static int count1;
	public static int num = 30;
	public static boolean startcount = false;
	public static int count1i = 30;
	
	public static void startcount1(){
		
		
		if(Main.ingame == false){
			if(startcount == false){
				startcount = true;
				count1i = 30;
		Bukkit.broadcastMessage(Main.Prefix+"Der Countdown wurde gestartet");
		
		count1 = Bukkit.getScheduler().scheduleAsyncRepeatingTask(pl, new Runnable(){
			@Override
			public void run() {
				if(count1i>1){
					
					count1i--;
					
					float exp = (float) ((double) count1i / (double) num);
					
					for(Player pp:Bukkit.getOnlinePlayers()){
						pp.setLevel(count1i);
						pp.setExp(exp);
					}
					
					
					
					if(count1i==50){
						Bukkit.broadcastMessage(Main.Prefix+"Die Runde Startet in "+ChatColor.YELLOW+count1i+ChatColor.GRAY+" Sekunden");
						for(Player pp:Bukkit.getOnlinePlayers()){
							pp.playSound(pp.getLocation(), Sound.NOTE_BASS, 1F, 1F);
						}
					}
					if(count1i==40){
						Bukkit.broadcastMessage(Main.Prefix+"Die Runde Startet in "+ChatColor.YELLOW+count1i+ChatColor.GRAY+" Sekunden");
						for(Player pp:Bukkit.getOnlinePlayers()){
							pp.playSound(pp.getLocation(), Sound.NOTE_BASS, 1F, 1F);
						}
					}
					if(count1i==30){
						Bukkit.broadcastMessage(Main.Prefix+"Die Runde Startet in "+ChatColor.YELLOW+count1i+ChatColor.GRAY+" Sekunden");
						for(Player pp:Bukkit.getOnlinePlayers()){
							pp.playSound(pp.getLocation(), Sound.NOTE_BASS, 1F, 1F);
						}
					}
					if(count1i==20){
						Bukkit.broadcastMessage(Main.Prefix+"Die Runde Startet in "+ChatColor.YELLOW+count1i+ChatColor.GRAY+" Sekunden");
						for(Player pp:Bukkit.getOnlinePlayers()){
							pp.playSound(pp.getLocation(), Sound.NOTE_BASS, 1F, 1F);
						}
					}
					
					if(count1i==5){
						for(Player pp:Bukkit.getOnlinePlayers()){
						TitleAPI.sendFullTitle(pp, 20, 60, 20, ChatColor.YELLOW+"Trump", Main.mapname);
						}
					}
					
					if(count1i<=10){
						Bukkit.broadcastMessage(Main.Prefix+"Die Runde Startet in "+ChatColor.YELLOW+count1i+ChatColor.GRAY+" Sekunden");
						for(Player pp:Bukkit.getOnlinePlayers()){
							pp.playSound(pp.getLocation(), Sound.NOTE_BASS, 1F, 1F);
						}
					}
					
					if(count1i==15){
						String deletworld = "gameworld";
						Bukkit.getServer().unloadWorld(deletworld, true);
						try {
						FileUtils.deleteDirectory(new File(deletworld));
						}catch (IOException e){
							e.printStackTrace();
						}
					}
					
					if(count1i==5){
						kopyWorld("Maps/Trump/"+Main.mapname, "gameworld");
					}
					
					if(count1i==2){
						Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
							@Override
							public void run() {
								for(org.bukkit.entity.Entity ent : Bukkit.getWorld("gameworld").getEntities()){
									if(!(ent instanceof Player)&&!(ent instanceof Villager)){
										ent.remove();
									}
								}
							}
						}, 0);
					}
					
				}else{
					CloudServer.getInstance().changeToIngame();
					CloudServer.getInstance().setMotdAndUpdate("ingame");
					JaylosAPI.showrang(false);
					Commands.setdrog();
					Bukkit.getScheduler().cancelTask(count1);
					Bukkit.broadcastMessage(Main.Prefix+ChatColor.GREEN+"Die Runde Startet");
					startschutzzeit();
					for(Player pp:Bukkit.getOnlinePlayers()){
						SQLStats.addGames(pp.getUniqueId().toString(), 1, pp.getName());
						pp.playSound(pp.getLocation(), Sound.LEVEL_UP, 1F, 1F);
					}
					Main.ingame = true;
					
					for(Player pp:Bukkit.getOnlinePlayers()){
						Main.playeringame.add(pp);
					}
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
						@Override
						public void run() {
							int i = 1;
							for(Player pp:Bukkit.getOnlinePlayers()){
								pp.setLevel(0);
								pp.setExp(0);
								pp.getInventory().clear();
//								
								if(Main.loc.getString(Main.mapname+".spawns."+i+".x")!= null){
									pp.setFallDistance(0);
									Double x1 = Main.loc.getDouble(Main.mapname+".spawns."+i+".x");
									Double y1 = Main.loc.getDouble(Main.mapname+".spawns."+i+".y");
									Double z1 = Main.loc.getDouble(Main.mapname+".spawns."+i+".z");
									Float yaw1 = (float) Main.loc.getDouble(Main.mapname+".spawns."+i+".yaw");
									Float pitch1 = (float) Main.loc.getDouble(Main.mapname+".spawns."+i+".pitch");
									World w1 = Bukkit.getWorld("gameworld");
									Location lobbyspawn = new Location(w1,x1,y1,z1,yaw1,pitch1);
									pp.teleport(lobbyspawn);
								}
								i++;
//								
							}
						}
					}, 0);
					
				}
			}
		}, 20, 20);
		}
		}
		
	}
	
	public static int schcount;
	public static int b = 30;
	public static boolean schutz = true;
	public static void startschutzzeit(){
		schcount = Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Runnable(){
			@Override
			public void run() {
				if(b>=1){
					if(b == 20){
						Bukkit.broadcastMessage(Main.Prefix+ChatColor.GRAY+"Die Schutzzeit endet in 20 Sekunden");	
						for(Player pp:Bukkit.getOnlinePlayers()){
							pp.playSound(pp.getLocation(), Sound.NOTE_BASS, 1F, 1F);
						}
					}
					if(b <= 10){
						Bukkit.broadcastMessage(Main.Prefix+ChatColor.GRAY+"Die Schutzzeit endet in "+b+" Sekunden");	
						for(Player pp:Bukkit.getOnlinePlayers()){
							pp.playSound(pp.getLocation(), Sound.NOTE_BASS, 1F, 1F);
						}
					}
					b--;
				}else{
					Bukkit.getScheduler().cancelTask(schcount);
					Bukkit.broadcastMessage(Main.Prefix+ChatColor.GREEN+"Die Schutzzeit ist vorbei!");
					setteam();
					for(Player pp:Bukkit.getOnlinePlayers()){
						ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
						LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
						meta.setColor(Color.GRAY);
						item.setItemMeta(meta);
						pp.getInventory().setChestplate(item);
						if(team.containsKey(pp)){
						if(team.get(pp).equals("Mexikaner")){
							Main.setmexikanerscoreboard(pp);
						}else if(team.get(pp).equals("NSA")){
							Main.setnsascoreboard(pp);
						}else{
							Main.setamerikanerscoreboard(pp);
						}
						}else{
							Main.setspectatorscoreboard(pp);
						}
					}
					for(Player pp:Bukkit.getOnlinePlayers()){
						if(team.containsKey(pp)){
							if(team.get(pp).equals("NSA")){
								ItemStack item = new ItemStack(Material.STICK);
								ItemMeta meta = item.getItemMeta();
								meta.setDisplayName(ChatColor.RED+"Authenticator");
								item.setItemMeta(meta);
								pp.getInventory().addItem(item);
								for(int i = pp.getLocation().getBlockY()+3;i<256;i++){
									Location loc = new Location(pp.getWorld(), pp.getLocation().getBlockX(),i,pp.getLocation().getBlockZ());
									pp.getWorld().playEffect(loc, Effect.STEP_SOUND, 22);
								}
							}
						}
					}
					b = 30;
					schutz = false;
				}

			}
		}, 20, 20);
	}
	
	@EventHandler
	public void onc(InventoryClickEvent e){
		if(e.getCurrentItem() !=null){
		if(e.getCurrentItem().getType() == Material.LEATHER_CHESTPLATE){
			e.setCancelled(true);
		}
		}
	}
	
public static void kopyWorld(String worldtocopyname, String newname){
		Bukkit.getServer().unloadWorld(worldtocopyname, true);
		try {
			FileUtils.copyDirectory(new File(worldtocopyname), new File(newname));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bukkit.createWorld(new WorldCreator(newname));
	}

public static HashMap<Player,String>team = new HashMap<>();
public static void startaction(){
	Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("Trump"), new Runnable(){
		@Override
		public void run() {
			for(Player pp:Bukkit.getOnlinePlayers()){
			if(team.containsKey(pp)){
				if(team.get(pp).equals("Mexikaner")){
					ActionBar.sendActionBar(pp, ChatColor.RED+"Mexikaner");
				}else if(team.get(pp).equals("NSA")){
					ActionBar.sendActionBar(pp, ChatColor.BLUE+"NSA");
				}else{
					ActionBar.sendActionBar(pp, ChatColor.GREEN+"Amerikaner");
				}
			}else if(Main.ingame &&schutz){
				if(b<=9){
					ActionBar.sendActionBar(pp, ChatColor.GRAY+"Schutzzeit: "+ChatColor.YELLOW+"00:0"+b);
				}else{
				ActionBar.sendActionBar(pp, ChatColor.GRAY+"Schutzzeit: "+ChatColor.YELLOW+"00:"+b);
				}
			}else if(Main.spectatorplayer.contains(pp)){
				ActionBar.sendActionBar(pp, ChatColor.GRAY+"Spectator");
			}else{
			ActionBar.sendActionBar(pp, ChatColor.RED+"ACHTUNG: "+ChatColor.GRAY+"Dieser Spielmodi ist mit Humor zu nehmen");
			}
			}
		}
	}, 20, 20);
}

public static ArrayList<Player> mexikaner = new ArrayList<>();

public static void setteam(){
//	Mexikaner
//	NSA
//	Amerikaner
	ArrayList<Player> plu = new ArrayList<>();
	for(Player pp:Bukkit.getOnlinePlayers()){
		plu.add(pp);
	}
	ArrayList<Player> pla = new ArrayList<>();
	int bbb = plu.size();
	for(int i = 0;i<bbb;i++){
		Random r = new Random();
		Player ppp = plu.get(r.nextInt(plu.size()));
		pla.add(ppp);
		plu.remove(ppp);
	}
	
	int bb = pla.size();
	
	if(mexikaner1!=null && bb>1){
		pla.remove(mexikaner1);
		team.put(mexikaner1, "Mexikaner");
		mexikaner.add(mexikaner1);
	}
	if(mexikaner2!=null && bb>4){
		pla.remove(mexikaner2);
		team.put(mexikaner2, "Mexikaner");
		mexikaner.add(mexikaner2);
	}
	if(mexikaner3!=null&& bb>7){
		pla.remove(mexikaner3);
		team.put(mexikaner3, "Mexikaner");
		mexikaner.add(mexikaner3);
	}
	if(mexikaner4!=null&& bb>10){
		pla.remove(mexikaner4);
		team.put(mexikaner4, "Mexikaner");
		mexikaner.add(mexikaner4);
	}
	
	if(nsa1!=null){
		pla.remove(nsa1);
		team.put(nsa1, "NSA");
	}
	
	if(nsa2!=null&& bb>6){
		pla.remove(nsa2);
		team.put(nsa2, "NSA");
	}
	
	int i = 1;
	for(Player pp:pla){
		if(!Main.spectatorplayer.contains(pp)){
		if(i==2 && mexikaner1==null){
			team.put(pp, "Mexikaner");
			mexikaner.add(pp);
			
		}else if(i==1 && nsa1==null){
			team.put(pp, "NSA");
			
		}else if(i==5&& mexikaner2==null){
				team.put(pp, "Mexikaner");
				mexikaner.add(pp);
				
		}else if(i==8&& mexikaner3==null){

			team.put(pp, "Mexikaner");
			mexikaner.add(pp);

		}else if(i==11&& mexikaner4==null){
			team.put(pp, "Mexikaner");
			mexikaner.add(pp);
			
		}else if(i==7 && nsa2==null){
			team.put(pp, "NSA");
			
		}else{
			team.put(pp, "Amerikaner");
		}
		i++;
		}
	}
}

@EventHandler
public void onk(PlayerInteractEvent e){
	if(e.getAction()==Action.RIGHT_CLICK_AIR||e.getAction()==Action.RIGHT_CLICK_BLOCK){
		if(e.getPlayer().getItemInHand().getType() != Material.AIR){
		if(e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().getItemMeta().getDisplayName() != null){
		if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.YELLOW+"Game-Setup §8(§7Rechtsklick§8)")){
			if(e.getPlayer().hasPermission("trump.pass")){
			Inventory inv = Bukkit.createInventory(null, 9, ChatColor.YELLOW+"Game-Setup");
			ItemStack item = new ItemStack(Material.WOOL, 1, (short) DyeColor.RED.getData());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.RED+"Mexikaner-Pass");
			item.setItemMeta(meta);
			inv.setItem(2, item);
//			
			ItemStack item1 = new ItemStack(Material.WOOL, 1, (short) DyeColor.BLUE.getData());
			ItemMeta meta1 = item1.getItemMeta();
			meta1.setDisplayName(ChatColor.RED+"NSA-Pass");
			item1.setItemMeta(meta1);
			inv.setItem(6, item1);
			e.getPlayer().openInventory(inv);
		}else{
			e.getPlayer().sendMessage(ChatColor.RED+"Dieses Feature ist noch nicht verfügbar");
		}
		}
		}
		}
	}
}

public static Player mexikaner1 = null;
public static Player mexikaner2 = null;
public static Player mexikaner3 = null;
public static Player mexikaner4 = null;

public static Player nsa1 = null;
public static Player nsa2 = null;


@EventHandler
public void onkli(InventoryClickEvent e){
	if(e.getClickedInventory() != null && e.getClickedInventory().getName() != null){
	if(e.getClickedInventory().getName().equals(ChatColor.YELLOW+"Game-Setup")){
		e.setCancelled(true);
		if(e.getCurrentItem() !=null &&e.getCurrentItem().getItemMeta() != null){
			Player p = (Player) e.getWhoClicked();
			if(mexikaner1!=p&&mexikaner2!=p&&mexikaner3!=p&&mexikaner4!=p&&nsa1!=p&&nsa2!=p){
			if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED+"Mexikaner-Pass")){
//				
				if(mexikaner1==null){
					mexikaner1 = p;
					p.sendMessage(Main.Prefix+ChatColor.GREEN+"Du hast einen Mexikaner Pass erfolgreich eingelößt");
					p.closeInventory();
				}else if(mexikaner2==null){
					mexikaner2 = p;
					p.sendMessage(Main.Prefix+ChatColor.GREEN+"Du hast einen Mexikaner Pass erfolgreich eingelößt");
					p.closeInventory();
				}else if(mexikaner3==null){
					mexikaner3 = p;
					p.sendMessage(Main.Prefix+ChatColor.GREEN+"Du hast einen Mexikaner Pass erfolgreich eingelößt");
					p.closeInventory();
				}else if(mexikaner4==null){
					mexikaner4 = p;
					p.sendMessage(Main.Prefix+ChatColor.GREEN+"Du hast einen Mexikaner Pass erfolgreich eingelößt");
					p.closeInventory();
				}else{
					p.sendMessage(Main.Prefix+ChatColor.RED+"Es sind keine Plätze als Mexikaner mehr frei!");
					p.closeInventory();
				}
//				
			}else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED+"NSA-Pass")){
				if(nsa1==null){
					nsa1 = p;
					p.sendMessage(Main.Prefix+ChatColor.GREEN+"Du hast einen NSA Pass erfolgreich eingelößt");
					p.closeInventory();
				}else if(nsa2==null){
					nsa2 = p;
					p.sendMessage(Main.Prefix+ChatColor.GREEN+"Du hast einen NSA Pass erfolgreich eingelößt");
					p.closeInventory();
				}else{
					p.sendMessage(Main.Prefix+ChatColor.RED+"Es sind keine Plätze als NSA Mitglied mehr frei!");
					p.closeInventory();
				}
			}
		}else{
			p.sendMessage(Main.Prefix+ChatColor.RED+"Du hast bereits einen Pass eingelößt!");
			p.closeInventory();
		}
		}
	}
	}
}

@EventHandler
public void onqut(PlayerQuitEvent e){
	Player p = e.getPlayer();
	if(mexikaner1==p){
		mexikaner1 = mexikaner2;
		mexikaner2 = mexikaner3;
		mexikaner3 = mexikaner4;
		mexikaner4 = null;
	}else if(mexikaner2==p){
		mexikaner2 = mexikaner3;
		mexikaner3 = mexikaner4;
		mexikaner4 = null;
	}else if(mexikaner3==p){
		mexikaner3 = mexikaner4;
		mexikaner4 = null;
	}else if(mexikaner4==p){
		mexikaner4 = null;
	}else if(nsa1==p){
		nsa1 = nsa2;
	}else if(nsa2==p){
		nsa2 = null;
	}
}

@EventHandler
public void ondd(EntityDamageByEntityEvent e){
	if(e.getEntity() instanceof Player&&e.getDamager() instanceof Player){
	Player p = (Player) e.getEntity();
	Player pp = (Player) e.getDamager();
	
	if(team.containsKey(p)&&team.containsKey(pp)){
	if(team.get(p).equals("Mexikaner")){
	if(team.get(pp).equals("Mexikaner")){
		pp.setHealth(pp.getHealth()+e.getDamage());
	}
	}
	}
	}
}

//============================================================================
@EventHandler
public static void ondeath(PlayerDeathEvent e){
	Main.playeringame.remove(e.getEntity());
	e.setDeathMessage(null);
	e.getDrops().clear();
	Player p = e.getEntity();
	if(p.getKiller()!=null){
		Player pp = p.getKiller();
//		
		if(points.containsKey(pp)){
			int i = points.get(pp);
			points.remove(pp);
			i++;
			points.put(pp, i);
		}else{
			points.put(pp, 4);
		}
//
		pp.sendMessage(Main.Prefix+"Du hast "+JaylosAPI.getchatname(p, pp)+ChatColor.GRAY+" getötet!");
		SQLStats.addTode(p.getUniqueId().toString(), 1, p.getName());
		
		if(team.get(pp).equals("Mexikaner")){
			SQLStats.addKills(pp.getUniqueId().toString(), 1, pp.getName());
			SQLStats.addCoins(pp.getUniqueId().toString(), 25, pp.getName());
			pp.sendMessage(ChatColor.YELLOW+"+25"+ChatColor.GRAY+"Coins");
		}else{
			if(team.get(p).equals("Amerikaner")){
				SQLStats.removeKills(pp.getUniqueId().toString(), 1, pp.getName());
				SQLStats.removeCoins(pp.getUniqueId().toString(), 50, pp.getName());
				pp.sendMessage(ChatColor.YELLOW+"-50"+ChatColor.GRAY+"Coins");
			}else if(team.get(p).equals("NSA")){
				SQLStats.removeKills(pp.getUniqueId().toString(), 1, pp.getName());
				SQLStats.removeCoins(pp.getUniqueId().toString(), 50, pp.getName());
				pp.sendMessage(ChatColor.YELLOW+"-50"+ChatColor.GRAY+"Coins");
			}else{
				SQLStats.addKills(pp.getUniqueId().toString(), 1, pp.getName());
				SQLStats.addCoins(pp.getUniqueId().toString(), 25, pp.getName());
				pp.sendMessage(ChatColor.YELLOW+"+25"+ChatColor.GRAY+"Coins");
			}
		}
		
		
		p.sendMessage(Main.Prefix+"Du wurdest von "+ChatColor.YELLOW+JaylosAPI.getchatname(pp, p)+ChatColor.GRAY+" getötet!");
		if(team.get(pp).equals("Mexikaner")){
			p.sendMessage(Main.Prefix+"Er war ein "+ChatColor.DARK_RED+"Mexikaner");
		}else if(team.get(pp).equals("NSA")){
			p.sendMessage(Main.Prefix+"Er war ein "+ChatColor.BLUE+"NSA mitglied");
		}else{
		p.sendMessage(Main.Prefix+"Er war ein "+ChatColor.GREEN+"Amerikaner");
		}
		
		if(team.get(p).equals("Mexikaner")){
			pp.sendMessage(Main.Prefix+"Er war ein "+ChatColor.DARK_RED+"Mexikaner");
		}else if(team.get(p).equals("NSA")){
			pp.sendMessage(Main.Prefix+"Er war ein "+ChatColor.BLUE+"NSA mitglied");
		}else{
		pp.sendMessage(Main.Prefix+"Er wahr ein "+ChatColor.GREEN+"Amerikaner");
		}
		
	}
	team.remove(e.getEntity());
	
	for(Player pp : Bukkit.getOnlinePlayers()){
	if(team.containsKey(pp)){
	if(team.get(pp).equals("Mexikaner")){
		Main.setmexikanerscoreboard(pp);
	}else if(team.get(pp).equals("NSA")){
		Main.setnsascoreboard(pp);
	}
	}
	}
	
	for(Player pp:Main.playeringame){
		pp.hidePlayer(p);
	}
	checkend();
}

//======================================================================================
@EventHandler
public void onbrak1(BlockBreakEvent e){
	if(Main.spectatorplayer.contains(e.getPlayer())||isend){
		e.setCancelled(true);
	}
}

@EventHandler
public void onplace11(BlockPlaceEvent e){
	if(Main.ingame == false||Main.spectatorplayer.contains(e.getPlayer())||isend){
		e.setCancelled(true);
	}
}

@EventHandler
public void onfood1(FoodLevelChangeEvent e){
	if(Main.spectatorplayer.contains(e.getEntity())||isend){
		e.setCancelled(true);
	}
}

@EventHandler
public void onhealth1(EntityDamageEvent e){
	if(Main.spectatorplayer.contains(e.getEntity())||isend){
		e.setCancelled(true);
	}
}

@EventHandler
public void ondrop1(PlayerDropItemEvent e){
	if(Main.spectatorplayer.contains(e.getPlayer())||isend){
		e.setCancelled(true);
	}
}

public static boolean isend = false;


@EventHandler
public void blockdamage(BlockDamageEvent e){
if(Main.spectatorplayer.contains(e.getPlayer())){
	e.setCancelled(true);
}
}


@EventHandler
public void onSpecSpeed(PlayerItemHeldEvent e){
Player p = e.getPlayer();
if(Main.spectatorplayer.contains(p)){
	int slot = e.getNewSlot()+1;
	float speed = (float)slot/10;
	p.setLevel(slot);
	p.setFlySpeed(speed);
}
}

@EventHandler
public void onrespawn(PlayerRespawnEvent e){
Player p = e.getPlayer();

p.getInventory().clear();
p.getInventory().setHelmet(new ItemStack(Material.AIR));
p.getInventory().setLeggings(new ItemStack(Material.AIR));
p.getInventory().setBoots(new ItemStack(Material.AIR));
p.getInventory().setChestplate(new ItemStack(Material.AIR));
p.spigot().setCollidesWithEntities(false);
e.setRespawnLocation(Bukkit.getWorld("gameworld").getSpawnLocation());

for (PotionEffect effect : p.getActivePotionEffects()) {
	p.removePotionEffect(effect.getType());
}

ItemStack item = new ItemStack(Material.COMPASS);
ItemMeta meta = item.getItemMeta();
meta.setDisplayName(ChatColor.YELLOW+"Spieler Teleporter "+ChatColor.GRAY+"(Rechtsklick)");
item.setItemMeta(meta);
p.getInventory().setItem(0, item);
Main.spectatorplayer.add(p);
p.setAllowFlight(true);
Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
	@Override
	public void run() {
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
		for(Player pp : Main.spectatorplayer){
			Main.setspectatorscoreboard(pp);
			}
	}
}, 0);
for(Player pp:Bukkit.getOnlinePlayers()){
	p.showPlayer(pp);
}
}

public static void setspectator(Player p){
p.getInventory().clear();
p.getInventory().setHelmet(new ItemStack(Material.AIR));
p.getInventory().setLeggings(new ItemStack(Material.AIR));
p.getInventory().setBoots(new ItemStack(Material.AIR));
p.getInventory().setChestplate(new ItemStack(Material.AIR));
p.teleport(Bukkit.getWorld("gameworld").getSpawnLocation());
p.spigot().setCollidesWithEntities(false);

for (PotionEffect effect : p.getActivePotionEffects()) {
	p.removePotionEffect(effect.getType());
}

Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
	@Override
	public void run() {
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
	}
}, 0);

ItemStack item = new ItemStack(Material.COMPASS);
ItemMeta meta = item.getItemMeta();
meta.setDisplayName(ChatColor.YELLOW+"Spieler Teleporter "+ChatColor.GRAY+"(Rechtsklick)");
item.setItemMeta(meta);
p.getInventory().setItem(0, item);
Main.spectatorplayer.add(p);
p.setAllowFlight(true);
}

@EventHandler
public void onklick(PlayerInteractEvent e){
Player p = e.getPlayer();
if(e.getAction() == Action.RIGHT_CLICK_AIR||e.getAction() == Action.RIGHT_CLICK_BLOCK){
	if(e.getPlayer().getItemInHand().getType() == Material.COMPASS){
	if(Main.spectatorplayer.contains(p)){
		Inventory inv = Bukkit.createInventory(null, 9*3, ChatColor.YELLOW+"Teleporter");
		
		for(Player pp : Main.playeringame){
			ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
			skullmeta.setDisplayName(ChatColor.AQUA+pp.getName());
			skullmeta.setOwner(pp.getName());
			skull.setItemMeta(skullmeta);
			inv.addItem(skull);
			
		}
		p.openInventory(inv);
	}
	}
}
}

@EventHandler
public void onklick(InventoryClickEvent e){
Player p = (Player) e.getWhoClicked();
if(e.getInventory().getTitle().equals(ChatColor.YELLOW+"Teleporter")){
	if(e.getCurrentItem()!=null){
	if(e.getCurrentItem().getItemMeta().getDisplayName()!=null){
	String s = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
	Player pp = Bukkit.getPlayer(s);
	p.teleport(pp);
	p.closeInventory();
	}
	}
}
}

//====================================================================================================
@EventHandler
public void onquit(PlayerQuitEvent e){
	Main.playeringame.remove(e.getPlayer());
	Player p = e.getPlayer();
	if(Main.ingame == false){
		if(Bukkit.getOnlinePlayers().size() <= Integer.parseInt(Main.loc.getString(Main.mapname + ".minplayers"))){
			Bukkit.broadcastMessage(Main.Prefix+ChatColor.RED+"Der Countdown wurde abgebrochen");
			Bukkit.getScheduler().cancelTask(count1);
			startcount = false;
		}
		e.setQuitMessage(null);
		for(Player pp:Bukkit.getOnlinePlayers()){
			pp.sendMessage(Main.Prefix+JaylosAPI.getchatname(e.getPlayer(), pp)+ChatColor.GRAY+" hat das Spiel verlassen!");
		}
	}else{
		e.setQuitMessage(null);
		if(damage.containsKey(p)){
			if(Bukkit.getPlayer(damage.get(p))!=null){
			Player killer = Bukkit.getPlayer(damage.get(p));
//			
			if(points.containsKey(killer)){
				int i = points.get(p);
				i++;
				points.put(killer, i);
			}else{
				points.put(killer, 4);
			}
			
			killer.sendMessage(Main.Prefix+"Du hast "+p.getName()+" getötet!");
			if(team.get(p).equals("Mexikaner")){
				killer.sendMessage(Main.Prefix+"Er wahr ein "+ChatColor.DARK_RED+"Mexikaner");
			}else if(team.get(p).equals("NSA")){
				killer.sendMessage(Main.Prefix+"Er wahr ein "+ChatColor.BLUE+"NSA mitglied");
			}else{
			killer.sendMessage(Main.Prefix+"Er wahr ein "+ChatColor.GREEN+"Amerikaner");
			}
			if(team.containsKey(killer)&&team.containsKey(p)){
			if(team.get(killer).equals(team.get(p))){
				killer.sendMessage(ChatColor.RED+"-50"+ChatColor.GRAY+" Taco's");
				SQLStats.removeCoins(killer.getUniqueId().toString(), 50, killer.getName());
			}else{
			killer.sendMessage(ChatColor.YELLOW+"+25"+ChatColor.GRAY+" Taco's");
			SQLStats.addCoins(killer.getUniqueId().toString(), 25, killer.getName());
			SQLStats.addKills(killer.getUniqueId().toString(), 1, killer.getName());
			}
			}
//			
		
			}
		}
		
		team.remove(e.getPlayer());
		for(Player pp : Bukkit.getOnlinePlayers()){
			if(team.containsKey(pp)){
			if(team.get(pp).equals("Mexikaner")){
				Main.setmexikanerscoreboard(pp);
			}else if(team.get(pp).equals("NSA")){
				Main.setnsascoreboard(pp);
			}
			}else if(Main.spectatorplayer.contains(pp)){
				Main.setspectatorscoreboard(pp);
			}
		}
		checkend();
	}
}


public static HashMap<Player,String> damage = new HashMap<>();
public static HashMap<Player,Integer> points = new HashMap<>();


@EventHandler
public void ondamma(EntityDamageByEntityEvent e){
	
	if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){
	Player p = (Player) e.getEntity();
	Player damager = (Player) e.getDamager();
	
	damage.put(p, damager.getName());
	
	Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
		@Override
		public void run() {
			if(damage.containsKey(p)){
				damage.remove(p);
			}
		}
	},20*5);
	
	}
}
//==============================================================
public static void checkend(){
	if(isend == false&&Main.ingame == true&&schutz == false){
	String winner = "null";
	ArrayList<String> list = new ArrayList<>();
	int i = 0;
	for(Player p: Bukkit.getOnlinePlayers()){
		if(team.containsKey(p)){
			String s;
			if(team.get(p).equals("Mexikaner")){
				s = "Mexikaner";
			}else{
				s = "Amerikaner";
			}
			if(!list.contains(s)){
				winner = s;
				i++;
				list.add(s);
			}
		}
	}
	
	if(i<=1){
		isend = true;
		JaylosAPI.endunnick();
//		=========================
		Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Trump"), new Runnable() {	
			@Override
			public void run() {
				for(Player pp:Bukkit.getOnlinePlayers()) {
					if(Main.spectatorplayer.contains(pp)) {
						Main.setspectatorscoreboard(pp);
					}else 
					if(mexikaner.contains(pp)) {
						Main.setmexikanerscoreboard(pp);
					}else if(team.containsKey(pp)) {
						if(team.get(pp).equals("NSA")) {
						Main.setnsascoreboard(pp);
						}else {
							Main.setmexikanerscoreboard(pp);	
						}
					}else {
						Main.setmexikanerscoreboard(pp);
					}
				}
			}
		}, 20);
//		=========================
		if(winner.equals("Mexikaner")){
			for(Player pp: Bukkit.getOnlinePlayers()){
				TitleAPI.sendFullTitle(pp, 20, 50, 20, ChatColor.DARK_RED+"Die Mexikaner", "haben die USA eingenommen");
				if(mexikaner.contains(pp)){
					SQLStats.addWins(pp.getUniqueId().toString(), 1, pp.getName());
					SQLStats.addCoins(pp.getUniqueId().toString(), 50, pp.getName());
				}
				}
			Main.playmexikosong();
			endgame();
		}else {
			for(Player pp: Bukkit.getOnlinePlayers()){
				TitleAPI.sendFullTitle(pp, 20, 50, 20, ChatColor.GREEN+"Die Amerikaner", "haben ihr Land verteidigt");
				if(!mexikaner.contains(pp)){
					SQLStats.addWins(pp.getUniqueId().toString(), 1, pp.getName());
					SQLStats.addCoins(pp.getUniqueId().toString(), 50, pp.getName());
				}
				}	
			Main.playussong();
			endgame();
		}
		
		for(Player ppp : Bukkit.getOnlinePlayers()){
		String s = null;
		for(Player pp : mexikaner){
			if(s==null){
				s = JaylosAPI.getchatname(pp, ppp);
			}else{
			s = s+", "+JaylosAPI.getchatname(pp, ppp);
			}
		}
		ppp.sendMessage(Main.Prefix+ChatColor.GRAY+"Folgende Spieler waren Mexikaner: "+ChatColor.RED+s);
		}
	}
	}
	
}

@EventHandler
public void onwchange(WeatherChangeEvent e){
	e.setCancelled(true);
}

@EventHandler
public void onspawn(EntitySpawnEvent e){
	if(e.getEntityType() != EntityType.PLAYER && e.getEntityType() != EntityType.DROPPED_ITEM && e.getEntityType() != EntityType.ARMOR_STAND){
		e.setCancelled(true);
	}
}

@EventHandler
public void oniklick(InventoryClickEvent e){
	if(Main.spectatorplayer.contains(e.getWhoClicked())){
		e.setCancelled(true);
	}
}

@EventHandler
public void onPlayerInteractEvent(PlayerInteractEvent e) {
if(e.getAction().equals(Action.PHYSICAL) && e.getClickedBlock().getType().equals(Material.SOIL)){
e.setCancelled(true);
}
}

@EventHandler
public void onfire(PlayerInteractEvent e){
	if(e.getClickedBlock()!=null){
	Location loc = e.getClickedBlock().getLocation();
	loc.add(0, 1, 0);
	if(loc.getBlock().getType()==Material.FIRE){
		if(Main.spectatorplayer.contains(e.getPlayer())){
		e.setCancelled(true);
		loc.getBlock().setType(Material.FIRE);
		}
	}
	}
}

@EventHandler
public void dd (EntityDamageEvent e){
	if(e.getEntity()instanceof Villager){
		e.setCancelled(true);
	}
}

public static boolean onendgame = false;

public static void endgame(){
	if(onendgame == false){
		onendgame = true;
	Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Runnable(){
		int i = 30;
		@Override
		public void run() {
			
			if(i>=-3){
				
				if(i == 30){
					Bukkit.broadcastMessage(Main.Prefix+"Der Server startet in "+ChatColor.YELLOW+i+ChatColor.DARK_GRAY+" Sekunden neu");
				}
				
				if(i==20){
					Bukkit.broadcastMessage(Main.Prefix+"Der Server startet in "+ChatColor.YELLOW+i+ChatColor.DARK_GRAY+" Sekunden neu");
				}
				
				if(i<=10){
					Bukkit.broadcastMessage(Main.Prefix+"Der Server startet in "+ChatColor.YELLOW+i+ChatColor.DARK_GRAY+" Sekunden neu");
				}
				
				if(i==0){
					for(Player pp:Bukkit.getOnlinePlayers()){
						pp.kickPlayer("");
					}
				}
				
			}else{
				Bukkit.shutdown();
			}
			
			
			i--;
		}
	}, 20, 20);
	}
}

	
	@EventHandler
	public void onchat(PlayerChatEvent e){
		Player p = e.getPlayer();
		e.setCancelled(true);
		String Massage = e.getMessage();
		if(Main.spectatorplayer.contains(e.getPlayer())){
			if(onendgame == true){
				for(Player pp:Bukkit.getOnlinePlayers()){
					pp.sendMessage(JaylosAPI.getchatname(p, pp)+ChatColor.DARK_GRAY+" >> "+ChatColor.GRAY+Massage);
				}
			}else{
			for(Player pp:Main.spectatorplayer){
				String speczeichen = ChatColor.GRAY+"["+ChatColor.RED+"✘"+ChatColor.GRAY+"]";
				pp.sendMessage(speczeichen+ChatColor.GRAY+p.getName()+ChatColor.DARK_GRAY+" >> "+ChatColor.GRAY+Massage);
				}
			}
		}else{
			for(Player pp:Bukkit.getOnlinePlayers()){
				pp.sendMessage(JaylosAPI.getchatname(p, pp)+ChatColor.DARK_GRAY+" >> "+ChatColor.GRAY+Massage);
			}
		}
	}

	@EventHandler
    public void onda(EntityDamageByEntityEvent e){
    	if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){
    		Player p = (Player) e.getEntity();
    		Player damager = (Player) e.getDamager();
    		if(team.containsKey(p)&&team.containsKey(damager)){
    			if(team.get(p).equals("Mexikaner")&&team.get(damager).equals("Mexikaner")){
    		Double d = p.getHealth()+e.getDamage();
    		if(d<20){
    		p.setHealth(p.getHealth()+e.getDamage());
    		}else{
    			p.setHealth(20.0);
    		}
    			}
    	}
    	}
    }
	
	@EventHandler
	public void onintera(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK||e.getAction() == Action.PHYSICAL){
		if(Main.spectatorplayer.contains(e.getPlayer())){
			e.setCancelled(true);
		}
		}
	}
	
    @EventHandler
    public void onlk(PlayerInteractAtEntityEvent e){
    	if(e.getRightClicked() instanceof Player){
    		if(e.getPlayer().getItemInHand().getType() == Material.BLAZE_ROD){
    		ArrayList<ArmorStand> list = new ArrayList<>();
    		Player pp = (Player) e.getRightClicked();
    		Player p = e.getPlayer();
    		if(p.getItemInHand().getAmount() >1){
    			p.getItemInHand().setAmount(p.getItemInHand().getAmount()-1);
    		}else{
    			p.getInventory().setItemInHand(new ItemStack(Material.AIR));
    		}
    		double i = 1.5;
    		double d = 1.1;
    		
    		
    		for(ItemStack item:pp.getInventory().getContents()){
    			if(item != null){
    			ArmorStand armors = (ArmorStand) pp.getWorld().spawnCreature(pp.getLocation().add(0, i, -1), EntityType.ARMOR_STAND);
    			armors.setArms(true);
        		armors.setVisible(false);
        		armors.setGravity(false);
        		armors.setItemInHand(item);
        		ArmorStand ms = (ArmorStand) pp.getWorld().spawnCreature(pp.getLocation().add(0, d-1.1, 0.8), EntityType.ARMOR_STAND);
        		ms.setVisible(false);
        		ms.setGravity(false);
        		ms.setCustomNameVisible(true);
        		ms.setCustomName(ChatColor.YELLOW+item.getType().toString());
        		list.add(ms);
        		list.add(armors);
        		d++;
        		i++;
    			}
    		}
    		
    		ArmorStand p1 = (ArmorStand) pp.getWorld().spawnCreature(pp.getLocation().add(0, i-1.5, 0.3), EntityType.ARMOR_STAND);
    		p1.setVisible(false);
    		p1.setGravity(false);
    		p1.setCustomNameVisible(true);
    		p1.setCustomName(ChatColor.YELLOW+"Das Inventory von "+ChatColor.GRAY+pp.getName()+ChatColor.YELLOW+" :");
    		list.add(p1);
    		
    		
    		Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Trump"), new Runnable(){
				@Override
				public void run() {
					for(ArmorStand a:list){
						if(!a.isDead()){
							a.remove();
						}
					}
				}
    		}, 20*5);
    	}
    	}
    }
    
//    ===================================================================================
    
    public static HashMap<ArmorStand,Player> leichen = new HashMap<>();
    public static HashMap<Player,String> rankg = new HashMap<>();
    
    @EventHandler
    public void ond(PlayerDeathEvent e){
    	Player p = e.getEntity();
    	ArmorStand stand = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
    	stand.setArms(true);
    	stand.setBasePlate(false);
    	stand.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
    	stand.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
    	stand.setBoots(new ItemStack(Material.LEATHER_BOOTS));
    	leichen.put(stand, p);
    	rankg.put(p, team.get(p));
    }
    
    
    @EventHandler
    public void onnn(EntityDamageEvent e){
    	if(e.getEntity() instanceof ArmorStand){
    		e.setCancelled(true);
    	}
    }
    
    @EventHandler
    public void oni1(PlayerInteractAtEntityEvent e){
    	if(e.getRightClicked() instanceof ArmorStand){
    		e.setCancelled(true);
    		if(e.getPlayer().getItemInHand().getType() == Material.STICK){
    			if(((ArmorStand)e.getRightClicked()).getHelmet().getType() == Material.AIR){
    			Player p = leichen.get(e.getRightClicked());
    			ArmorStand stand = (ArmorStand) e.getRightClicked();
    			
    			ItemStack kopf = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
    			SkullMeta im = (SkullMeta) kopf.getItemMeta();
    			im.setOwner(p.getName());
    			kopf.setItemMeta(im);
    			
    			stand.setHelmet(kopf);
    			String s = rankg.get(p);
    			String c = "";
    			if(s.equals("Mexikaner")){
    				c = ChatColor.RED+"";
    			}else if ( s.equals("NSA")){
    				c = ChatColor.BLUE+"";
    			}else{
    				c = ChatColor.GREEN+"";
    			}
    			e.getPlayer().sendMessage(ChatColor.GRAY+"Du hast "+p.getName()+" identifiziert! Er war: "+c+s);
    		}
    		}
    	}
    }
    
    @EventHandler
    public void oninter(PlayerInteractEvent e){
    	if(Main.ingame == false){
    		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
    			if(e.getClickedBlock().getType() !=Material.WOOD_BUTTON &&e.getClickedBlock().getType() !=Material.STONE_BUTTON){
    				e.setCancelled(true);
    			}
    		}else{
    		if(e.getAction() != Action.PHYSICAL) {
    			e.setCancelled(true);		
    		}
    		}
    	}
    }

     @EventHandler
     public void oni(PlayerInteractAtEntityEvent e) {
    	 if(e.getRightClicked().getType() == EntityType.ARMOR_STAND) {
    		 e.setCancelled(true);
    	 }
     }
	
	@EventHandler
	public void onunnick(unNickEvent e) {
//		=========================
		Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Trump"), new Runnable() {	
			@Override
			public void run() {
				for(Player pp:Bukkit.getOnlinePlayers()) {
					if(Main.ingame ==false) {
						JaylosAPI.updaterang();
					}else
					if(Main.spectatorplayer.contains(pp)) {
						Main.setspectatorscoreboard(pp);
					}else 
					if(mexikaner.contains(pp)) {
						Main.setmexikanerscoreboard(pp);
					}else if(team.containsKey(pp)) {
						if(team.get(pp).equals("NSA")) {
						Main.setnsascoreboard(pp);
						}else {
							Main.setmexikanerscoreboard(pp);	
						}
					}else {
						Main.setmexikanerscoreboard(pp);
					}
				}
			}
		}, 20);
//		=========================
	}
	
}
