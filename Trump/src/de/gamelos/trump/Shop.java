package de.gamelos.trump;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.gamelos.jaylosapi.JaylosAPI;
import net.md_5.bungee.api.ChatColor;

public class Shop implements CommandExecutor, Listener {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("shop")){
			Player p = (Player) sender;
			if(Events.team.containsKey(p)){
				if(Events.team.get(p).equals("Mexikaner")){
//					
					Inventory inv = Bukkit.createInventory(null, 9, ChatColor.RED+"Tacoladen");
					inv.setItem(1,createitem(Material.FIREWORK_CHARGE, ChatColor.YELLOW+"Granate", ChatColor.RED+"1 Punkt"));
					inv.setItem(2,createitem(Material.EYE_OF_ENDER, ChatColor.GREEN+"Swapper", ChatColor.RED+"4 Punkte"));
					inv.setItem(3,createitem(Material.TNT, ChatColor.RED+"Selbstmord", ChatColor.RED+"5 Punkte"));	
					inv.setItem(4,createitem(Material.COMPASS, ChatColor.RED+"Drogenkompass", ChatColor.RED+"1 Punkte"));	
					inv.setItem(5,createitem(Material.NAME_TAG, ChatColor.RED+"USA Visum", ChatColor.RED+"4 Punkte"));	
//					
					String s ="";
					for(Player pp: Bukkit.getOnlinePlayers()){
						if(Events.team.containsKey(pp)){
							if(Events.team.get(pp).equals("NSA")){
								s = s+JaylosAPI.getchatname(pp, p)+"\n";
							}
						}
					}
					ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
					BookMeta meta = (BookMeta) book.getItemMeta();
					meta.setAuthor("Edward Snowden");
					ArrayList<String> list = new ArrayList<>();
					list.add(ChatColor.RED+"3 Punkte");
					meta.setLore(list);
					meta.setDisplayName(ChatColor.BLUE+"Alle Aktuellen Leute der NSA");
					meta.addPage("§9Mitarbeiter:\n\n§1"+s);
					book.setItemMeta(meta);
//					
					inv.setItem(6,book);	
					p.openInventory(inv);
//					
				}else if(Events.team.get(p).equals("NSA")){
//					
					Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLUE+"Shop");
					inv.setItem(2,createitem(Material.FIREWORK_CHARGE, ChatColor.YELLOW+"Granate", ChatColor.RED+"1 Punkt"));
					inv.setItem(3,createitem(Material.EYE_OF_ENDER, ChatColor.GREEN+"Swapper", ChatColor.RED+"4 Punkte"));
					inv.setItem(5,createitem(Material.BLAZE_ROD, ChatColor.RED+"Durchsuchungsbefehl", ChatColor.RED+"4 Punkte"));
//					
					ItemStack item = new ItemStack(Material.POTION, 1, (short) 16453);
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName(ChatColor.AQUA+"Heilungstrank");
					ArrayList<String> list = new ArrayList<>();
					list.add(ChatColor.RED+"2 Punkte");
					meta.setLore(list);
					item.setItemMeta(meta);
					inv.setItem(4,item);
					p.openInventory(inv);
//					
				}else{
					p.sendMessage(Main.Prefix+ChatColor.RED+"Als Amerikaner hast du keinen Shop");	
				}
			}else{
				p.sendMessage(Main.Prefix+ChatColor.RED+"Du hast keinen Shop");
			}
		}
		return false;
	}
	
	int b = 0;
	int alarm = 0;
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onI(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_AIR||e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(p.getItemInHand().getType()==Material.FIREWORK_CHARGE){
				if(p.getItemInHand().getAmount()==1){
					p.getInventory().remove(Material.FIREWORK_CHARGE);
				}else{
				p.getItemInHand().setAmount(p.getItemInHand().getAmount()-1);
				}
				final Item plate = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.FIREWORK_CHARGE));
				plate.setVelocity(p.getLocation().getDirection().multiply(0.8D));
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Trump"), new Runnable(){
					@Override
					public void run() {
					plate.getWorld().createExplosion(plate.getLocation().getX(), plate.getLocation().getY(), plate.getLocation().getZ(), 3, false, false);
					}
				},60);
			}else if(p.getItemInHand().getType() == Material.EYE_OF_ENDER){
				if(p.getItemInHand().getAmount()==1){
					p.getInventory().remove(Material.EYE_OF_ENDER);
				}else{
				p.getItemInHand().setAmount(p.getItemInHand().getAmount()-1);
				}
				List<Player> list = new ArrayList<>();
				for(Player pp: Main.playeringame){
					if(pp!=p){
						list.add(pp);
					}
				}
				if(list.size() > 0){
				Random r = new Random();
				int i = r.nextInt(list.size());
				Player sw = list.get(i);
				Location loc = p.getLocation();
				p.teleport(sw);
				sw.teleport(loc);
				p.playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 1);
				p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1F);
				sw.playEffect(sw.getLocation(), Effect.ENDER_SIGNAL, 1);
				sw.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1F);
				}
			}else if(p.getItemInHand().getType() == Material.TNT){
				e.setCancelled(true);
				p.setHealth(0);
				if(p.getItemInHand().getAmount()==1){
					p.getInventory().remove(Material.TNT);
				}else{
				p.getItemInHand().setAmount(p.getItemInHand().getAmount()-1);
				}
				p.getWorld().createExplosion(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), 7, true, false);
//				
//				
				for(Player pp:Bukkit.getOnlinePlayers()){
					pp.sendMessage(Main.Prefix+ChatColor.DARK_RED+JaylosAPI.getchatname(p, pp)+ChatColor.GRAY+" hat einen Anschlag ausgeübt");
				}
//				
				b = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Bukkit.getPluginManager().getPlugin("Trump"), new Runnable(){
					@Override
					public void run() {
						if(alarm<= 2*50){
							for(Player pp : Bukkit.getOnlinePlayers()){
								pp.playSound(pp.getLocation(), Sound.NOTE_PIANO, 1F, 1F);
							}
						}else{
							Bukkit.getScheduler().cancelTask(b);
							alarm = 0;
							for(Player pp : Bukkit.getOnlinePlayers()){
								pp.playSound(pp.getLocation(), Sound.ANVIL_LAND, 1F, 1F);
							}
							cancelfire = true;
							Bukkit.broadcastMessage(Main.Prefix+ChatColor.GREEN+"Die Gefahr wurde beseitigt");
							Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Trump"), new Runnable(){
								@Override
								public void run() {
									cancelfire = false;
								}
							}, 5*20);
						}
						alarm++;
					}
				}, 10, 10);
//				
			}else if(p.getItemInHand().getType() == Material.NAME_TAG){
				if(p.getItemInHand().getAmount()==1){
					p.getInventory().remove(Material.NAME_TAG);
				}else{
				p.getItemInHand().setAmount(p.getItemInHand().getAmount()-1);
				}
				p.sendMessage(Main.Prefix+ChatColor.GREEN+"Das Visum wurde erfolgreich genehmigt");
				visum.add(p);
			}
		}
	}
	
	public static ArrayList<Player> visum = new ArrayList<>();
	public static boolean cancelfire = false;
	
	 @SuppressWarnings("deprecation")
	@EventHandler
		public void onBlockSpread(BlockSpreadEvent e){
		 
		 if(cancelfire){
			 e.getBlock().setType(Material.AIR);
		 }
		 
			if(e.getSource().getTypeId()==51){
				e.setCancelled(true);
			}
		}
		
		@EventHandler
		public void onBlockBurn(BlockBurnEvent e){
			e.setCancelled(true);
		}
	
//	
	
	@EventHandler
	public void onkl(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		if(e.getClickedInventory()!=null){
		if(e.getClickedInventory().getTitle().equals(ChatColor.RED+"Tacoladen")){
			e.setCancelled(true);
			if(e.getCurrentItem() !=null){
				String s = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(0));
				int ss = Integer.parseInt(s.substring(0, 1));
				int i;
				if(Events.points.containsKey(p)){
					i = Events.points.get(p);
				}else{
					i = 3;
				}
					if(i>=ss){
						if(Events.points.containsKey(p)){
						Events.points.remove(p);
						int b = i-ss;
						Events.points.put(p, b);
						}else{
							Events.points.put(p, 2);
						}
						
//						
						if(e.getCurrentItem().getType() == Material.COMPASS){
							p.setCompassTarget(Commands.drog);
						}
//						
						
				p.getInventory().addItem(e.getCurrentItem());
					}else{
						p.sendMessage(Main.Prefix+ChatColor.RED+"Du hast nicht genügend Punkte");
					}
					Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Trump"), new Runnable(){
						@Override
						public void run() {
							Main.setmexikanerscoreboard(p);
						}
					},0);
			}
		}else if(e.getClickedInventory().getTitle().equals(ChatColor.BLUE+"Shop")){
			e.setCancelled(true);
			if(e.getCurrentItem() !=null){
				String s = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(0));
				int ss = Integer.parseInt(s.substring(0, 1));
				int i;
				if(Events.points.containsKey(p)){
					i = Events.points.get(p);
				}else{
					i = 3;
				}
					if(i>=ss){
						if(Events.points.containsKey(p)){
						Events.points.remove(p);
						int b = i-ss;
						Events.points.put(p, b);
						}else{
							Events.points.put(p, 2);
						}
						
//						
						if(e.getCurrentItem().getType() == Material.COMPASS){
							p.setCompassTarget(Commands.drog);
						}
//						
						
				p.getInventory().addItem(e.getCurrentItem());
					}else{
						p.sendMessage(Main.Prefix+ChatColor.RED+"Du hast nicht genügend Punkte");
					}
					Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Trump"), new Runnable(){
						@Override
						public void run() {
							Main.setnsascoreboard(p);
						}
					},0);
			}
		}
		}
	}
	
	@EventHandler
	public void onpik(PlayerPickupItemEvent e){
		if(e.getItem().getItemStack().getType() == Material.FIREWORK_CHARGE){
			e.setCancelled(true);
		}
	}
	
	public static ItemStack createitem(Material m, String displayname, String Preis){
		ItemStack item = new ItemStack(m);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayname);
		ArrayList<String> list = new ArrayList<>();
		list.add(Preis);
		meta.setLore(list);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ArrayList<Player> isinaria = new ArrayList<>();
	public static ArrayList<Player> msg = new ArrayList<>();
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onmove(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(Main.ingame == true&&Events.schutz == false){
			if(Commands.drog != null){
			if(Commands.drog.distance(p.getLocation())<= 3.0){
				if(p.getInventory().contains(Material.COMPASS)&&(!Main.spectatorplayer.contains(p))){
				if(!isinaria.contains(p)){
				Inventory inv = Bukkit.createInventory(null, 9, ChatColor.YELLOW+"Drogenladen");
				ItemStack redwool = new ItemStack(Material.WOOL, 1, DyeColor.RED.getData());
				ItemMeta meta = redwool.getItemMeta();
				meta.setDisplayName(ChatColor.RED+"Nicht Teleportieren");
				redwool.setItemMeta(meta);
				ItemStack greenwool = new ItemStack(Material.WOOL, 1, DyeColor.GREEN.getData());
				ItemMeta meta1 = greenwool.getItemMeta();
				meta1.setDisplayName(ChatColor.GREEN+"Teleportieren");
				greenwool.setItemMeta(meta1);
				inv.setItem(2, greenwool);
				inv.setItem(6, redwool);
				p.openInventory(inv);
				isinaria.add(p);
				Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Trump"), new Runnable(){
					@Override
					public void run() {
					isinaria.remove(p);
					}
				},100);
				}else{
					if(!msg.contains(p)){
						p.sendMessage(Main.Prefix+"Warte einen Moment...");
						msg.add(p);
						Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Trump"), new Runnable(){
							@Override
							public void run() {
							msg.remove(p);
							}
						},20*2);	
					}
				}
			}
			}
			}
		}
	}
	
	public static HashMap<Player,Location>locat = new HashMap<>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onc(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		if(e.getClickedInventory() !=null){
		if(e.getClickedInventory().getTitle().equals(ChatColor.YELLOW+"Drogenladen")){
			e.setCancelled(true);
			if(e.getCurrentItem() !=null){
				if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED+"Nicht Teleportieren")){
					p.closeInventory();
					isinaria.add(p);
					Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Trump"), new Runnable(){
						@Override
						public void run() {
						isinaria.remove(p);
						}
					},100);
				}else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN+"Teleportieren")){
				locat.put(p, p.getLocation());
				Double x1 = Main.loc.getDouble(Main.mapname+".drogenspawn.x1");
				Double y1 = Main.loc.getDouble(Main.mapname+".drogenspawn.y1");
				Double z1 = Main.loc.getDouble(Main.mapname+".drogenspawn.z1");
				World w1 = Bukkit.getWorld("gameworld");
				Location lobbyspawn = new Location(w1,x1,y1,z1);
				p.teleport(lobbyspawn);
				p.teleport(lobbyspawn);
				p.playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 5);
				p.sendMessage(Main.Prefix+"Du hast jetzt 20 Sekunden Zeit");
				Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Trump"), new Runnable(){
					@Override
					public void run() {
						if(locat.containsKey(p)){
							isinaria.add(p);
							p.teleport(locat.get(p));
							Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Trump"), new Runnable(){
								@Override
								public void run() {
								isinaria.remove(p);
								}
							},100);
						}
					}
				},20*20);
				
				}
			}
		}
		}
	}
	
	
	public static ArrayList<Player>del = new ArrayList<>();
	@EventHandler
	public void oni(PlayerInteractAtEntityEvent e){
		Entity entity = (Entity) e.getRightClicked();
		if(entity.getType() == EntityType.VILLAGER){
			Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Trump"), new Runnable(){
				@Override
				public void run() {
					if(!del.contains(e.getPlayer())){
					Inventory inv = Bukkit.createInventory(null, 9, ChatColor.YELLOW+"Shop");
					for(int i = 0; i<9;i++){
						inv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE));
					}
					@SuppressWarnings("deprecation")
					ItemStack item = new ItemStack(351,3,(byte)15);
					inv.setItem(4, item);
					e.getPlayer().openInventory(inv);
					del.add(e.getPlayer());
					Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Trump"), new Runnable(){
						@Override
						public void run() {
							del.remove(e.getPlayer());
						}
					},20*10);
					}else{
						e.getPlayer().closeInventory();
						e.getPlayer().sendMessage(Main.Prefix+"Warte ein paar Sekunden bevor du den Shop wieder öffnen kannst");
					}
				}
			},0);
		}
	}
	
	@EventHandler
	public void onnn(InventoryClickEvent e){
		if(e.getClickedInventory() !=null){
			if(e.getClickedInventory().getTitle().equals(ChatColor.YELLOW+"Shop")){
				if(e.getCurrentItem()!=null){
					if(e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE){
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void oni(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_AIR||e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(p.getItemInHand().getType() == Material.INK_SACK){
				if(p.getItemInHand().getAmount() == 1){
					p.getInventory().remove(Material.INK_SACK);
				}else{
					p.getItemInHand().setAmount(p.getItemInHand().getAmount()-1);
				}
				Random r = new Random();
				int i = r.nextInt(2);
				if(i==0){
					p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 200, 1));
				}else if(i==1){
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));
				}else{
					p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
				}
			}
		}else if(p.getItemInHand().getType() == Material.NAME_TAG){
			if(pass.contains(p)){
				p.sendMessage(Main.Prefix+"Du hast ein Visum bereits eingelöst!");
			}else{
			p.getInventory().remove(Material.NAME_TAG);
			pass.add(p);
			p.sendMessage(Main.Prefix+ChatColor.GREEN+"Das Visum wurde eingesetzt!");
			}
		}
	}
	
	public static ArrayList<Player> pass = new ArrayList<>();
	
}
