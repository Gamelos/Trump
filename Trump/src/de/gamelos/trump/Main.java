package de.gamelos.trump;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.xxmicloxx.NoteBlockAPI.NBSDecoder;
import com.xxmicloxx.NoteBlockAPI.Song;
import com.xxmicloxx.NoteBlockAPI.SongPlayer;

import de.dytanic.cloudnet.bridge.CloudServer;
import de.gamelos.PermissionsAPI.MySQLRang;
import de.gamelos.jaylosapi.JaylosAPI;
import de.gamelos.stats.MySQL;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener{

	public static ArrayList<Player> spectatorplayer = new ArrayList<>();
	public static Boolean ingame = false;
	public static org.bukkit.plugin.Plugin pl = Bukkit.getPluginManager().getPlugin("Trump");
	public static String Prefix = ChatColor.GRAY+"["+ChatColor.YELLOW+"Trump"+ChatColor.GRAY+"] ";
	public static String mapname = "";
	public static File locations;
	public static FileConfiguration loc;
	public static ArrayList<Player>playeringame = new ArrayList<>();
	
	Player lastjoin;
	
	@EventHandler
	public void onjoin(PlayerJoinEvent e){
			lastjoin = e.getPlayer();
	}
	
	
	public static MySQL mysql;
	private void ConnectMySQL(){
		mysql = new MySQL(JaylosAPI.gethost(), JaylosAPI.getuser(), JaylosAPI.getdatabase(), JaylosAPI.getpassword());
		mysql.update("CREATE TABLE IF NOT EXISTS Trump(UUID varchar(64), KILLS int, TODE int, WINS int, GAMES int, NAME varchar(64), COINS int);");
	}
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		JaylosAPI.showrang(true);
//		====
		getCommand("promote").setExecutor(new Commands());
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "info");
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
//        ===
		Main.locations = new File("plugins/Trump", "data.yml");
		Main.loc = YamlConfiguration.loadConfiguration(Main.locations);
		Bukkit.getPluginManager().registerEvents(new Commands(), this);
		getCommand("trump").setExecutor(new Commands());
		System.out.println("[Trump] Das Plugin wurde erfolgreich geladen");
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new Events(), this);
		Bukkit.getPluginManager().registerEvents(new Shop(), this);
		Bukkit.getPluginManager().registerEvents(new Forcemap(), this);
		getCommand("trump").setExecutor(new Commands());
		getCommand("shop").setExecutor(new Shop());
		getCommand("start").setExecutor(new Start());
		getCommand("forcemap").setExecutor(new Forcemap());
		getCommand("stats").setExecutor(new StatsCommand());
		mapname = getRandomMap();
		CloudServer.getInstance().setMotd(Main.mapname);
		CloudServer.getInstance().setMaxPlayersAndUpdate(12);
		Events.startaction();
//		
		if(Bukkit.getWorld(Main.loc.getString("spawn.world"))!=null){
		World w1 = Bukkit.getWorld(Main.loc.getString("spawn.world"));
		for(Entity ee : w1.getEntities()){
			if(!(ee instanceof Player)){
			ee.remove();
			}
		}
		}
		ConnectMySQL();
//		
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
			@Override
			public void run() {
				restartcheck();
			}
		}, 20*60);
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){

			@Override
			public void run() {
				Ranking.updateranking();
			}
			
		}, 40);
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		System.out.println("[Trump] Das Plugin wurde erfolgreich deaktiviert");
		super.onDisable();
	}

	
	
	
    static SongPlayer sss;
	static Song ggg;
    public static void playussong(){
		Song s1 = NBSDecoder.parse(new File("plugins/songs/ussong.nbs"));
		SongPlayer sp = new com.xxmicloxx.NoteBlockAPI.RadioSongPlayer(s1);
		sp.setAutoDestroy(true);
		for(Player current : Bukkit.getOnlinePlayers()){
		sp.addPlayer(current);
		}
		sp.setPlaying(true);
		sss=sp;
		ggg=s1;
    }
    
    static SongPlayer sss1;
	static Song ggg1;
    public static void playmexikosong(){
		Song s1 = NBSDecoder.parse(new File("plugins/songs/mexiko musik.nbs"));
		SongPlayer sp = new com.xxmicloxx.NoteBlockAPI.RadioSongPlayer(s1);
		sp.setAutoDestroy(true);
		for(Player current : Bukkit.getOnlinePlayers()){
		sp.addPlayer(current);
		}
		sp.setPlaying(true);
		sss=sp;
		ggg=s1;
    }
	
	
	public static String getRandomMap(){
		String mapname = "";
		int i;
		int ii = getmapanzahl()-1;
		
		Random random = new Random();
		i = random.nextInt(ii+(1))+1;
		mapname = Main.loc.getString("Mapnames."+i);
//		
//		
		
		return mapname;
	}
	
	public static int getmapanzahl() {
		int i = 0;
		int ii = 1;
		while (loc.getString("Mapnames." + ii) != null) {
			i++;
			ii++;
		}
		return i;
	}
	
	public static int getspawnanzahl(String mapname) {
		int i = 0;
		int ii = 1;
		while (loc.getString(mapname + ".spawns." + ii) != null) {
			i++;
			ii++;
		}
		return i;
	}
	
	public static void setlobbyscoreboard(){
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("asf", "bbb");
		obj.setDisplayName(ChatColor.BOLD+"Jaylos.net");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
//		
//		

//				
				Score map = obj.getScore(ChatColor.YELLOW+"Mapname:");	
				map.setScore(1);	
				Score mapscore = obj.getScore(ChatColor.GREEN+""+Main.mapname);	
				mapscore.setScore(0);	
//			
		for(Player pp:Bukkit.getOnlinePlayers()){
		pp.setScoreboard(board);
		}
		
	}
	

	public static void setspectatorscoreboard(Player pp){
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("asf", "bbb");
		obj.setDisplayName(ChatColor.BOLD+"Jaylos.net");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
//		
//		
		Team spectator = board.registerNewTeam("002Spectator");
		spectator.setPrefix(ChatColor.GRAY+"");
		spectator.canSeeFriendlyInvisibles();
//		
		Team spieler = board.registerNewTeam("001Spieler");
		spieler.setPrefix(ChatColor.GREEN+"");
//		
		for(Player p:Bukkit.getOnlinePlayers()){
			if(Main.playeringame.contains(p)){
						JaylosAPI.setteam(spieler, p, pp);
			}else{
						JaylosAPI.setteam(spectator, p, pp);
			}
		}
			//
				Score spieler1 = obj.getScore(ChatColor.YELLOW+"Spieler:");	
				spieler1.setScore(4);	
				Score five11 = obj.getScore(ChatColor.GREEN+""+Events.team.size());	
				five11.setScore(3);	
//				
				obj.getScore(ChatColor.RED+" ").setScore(2);
				Score map = obj.getScore(ChatColor.YELLOW+"Mapname:");	
				map.setScore(1);	
				Score mapscore = obj.getScore(ChatColor.GREEN+""+Main.mapname);	
				mapscore.setScore(0);	
//			
				
		pp.setScoreboard(board);
		
	}
	
	public static void setnsascoreboard(Player pp){
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("asf", "bbb");
		obj.setDisplayName(ChatColor.BOLD+"Jaylos.net");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
//		
//		
		Team nsa = board.registerNewTeam("NSA");
		nsa.setPrefix(ChatColor.BLUE+"");
//		
		Team spieler = board.registerNewTeam("Spieler");
		spieler.setPrefix(ChatColor.GREEN+"");
//		
		for(Player p:Bukkit.getOnlinePlayers()){
			if(Events.team.containsKey(p)){
			if(Events.team.get(p).equals("NSA")){
						JaylosAPI.setteam(nsa, p, pp);
			}else{
						JaylosAPI.setteam(spieler, p, pp);
			}
			}
		}
			//
			int points1 = 3;
			if(Events.points.containsKey(pp)){
				points1 = Events.points.get(pp);
			}
			
				Score points = obj.getScore(ChatColor.BLUE+"NSA-Punkte:");	
				points.setScore(10);	
				Score p1 = obj.getScore(ChatColor.GREEN+""+points1);	
				p1.setScore(9);	
//
				obj.getScore(ChatColor.AQUA+" ").setScore(8);
				Score five = obj.getScore(ChatColor.YELLOW+"Coins:");	
				five.setScore(7);	
				Score five1 = obj.getScore(ChatColor.GREEN+"0");	
				five1.setScore(6);	
//				
				obj.getScore(ChatColor.RED+" ").setScore(5);
				Score spieler1 = obj.getScore(ChatColor.YELLOW+"Spieler:");	
				spieler1.setScore(4);	
				Score five11 = obj.getScore(ChatColor.GREEN+""+Events.team.size());	
				five11.setScore(3);	
//				
				obj.getScore(ChatColor.YELLOW+" ").setScore(2);
				Score map = obj.getScore(ChatColor.YELLOW+"Mapname:");	
				map.setScore(1);	
				Score mapscore = obj.getScore(ChatColor.GREEN+""+Main.mapname);	
				mapscore.setScore(0);	
//			
		pp.setScoreboard(board);
		
	}
	
	public static void setmexikanerscoreboard(Player pp){
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("asf", "bbb");
		obj.setDisplayName(ChatColor.BOLD+"Jaylos.net");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
//		
//		
		Team mexikaner = board.registerNewTeam("Mexikaner");
		mexikaner.setPrefix(ChatColor.DARK_RED+"");
//		
		Team spieler = board.registerNewTeam("Spieler");
		spieler.setPrefix(ChatColor.GREEN+"");
//		
		for(Player p:Bukkit.getOnlinePlayers()){
			if(Events.team.containsKey(p)){
			if(Events.team.get(p).equals("Mexikaner")){
						JaylosAPI.setteam(mexikaner, p, pp);
			}else{
						JaylosAPI.setteam(spieler, p, pp);
			}
			}
		}
			//
				Score points = obj.getScore(ChatColor.RED+"Taco-Punkte:");	
				points.setScore(10);	
				
				int points1 = 3;
				if(Events.points.containsKey(pp)){
					points1 = Events.points.get(pp);
				}
				String s = ""+points1;
				
				obj.getScore(ChatColor.GREEN+s).setScore(9);;	
//
				obj.getScore(ChatColor.AQUA+" ").setScore(8);
				Score five = obj.getScore(ChatColor.YELLOW+"Coins:");	
				five.setScore(7);	
				Score five1 = obj.getScore(ChatColor.GREEN+"0");	
				five1.setScore(6);	
//				
				obj.getScore(ChatColor.YELLOW+" ").setScore(5);
				Score spieler1 = obj.getScore(ChatColor.YELLOW+"Spieler:");	
				spieler1.setScore(4);	
				Score five11 = obj.getScore(ChatColor.GREEN+""+Events.team.size());	
				five11.setScore(3);	
//				
				obj.getScore(ChatColor.RED+" ").setScore(2);
				Score map = obj.getScore(ChatColor.YELLOW+"Mapname:");	
				map.setScore(1);	
				Score mapscore = obj.getScore(ChatColor.GREEN+""+Main.mapname);	
				mapscore.setScore(0);	
//			
				
		pp.setScoreboard(board);
		
	}
	
	public static void setamerikanerscoreboard(Player pp){
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("asf", "bbb");
		obj.setDisplayName(ChatColor.BOLD+"Jaylos.net");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
//		
//		
		Team nsa = board.registerNewTeam("NSA");
		nsa.setPrefix(ChatColor.BLUE+"");
//		
		Team spieler = board.registerNewTeam("Spieler");
		spieler.setPrefix(ChatColor.GREEN+"");
//		
		for(Player p:Bukkit.getOnlinePlayers()){
			if(Events.team.containsKey(p)){
			if(Events.team.get(p).equals("NSA")){
				nsa.addEntry(p.getName());
						JaylosAPI.setteam(nsa, p, pp);
			}else{
				JaylosAPI.setteam(spieler, p, pp);
			}
			}
//
				Score five = obj.getScore(ChatColor.YELLOW+"Coins:");	
				five.setScore(7);	
				Score five1 = obj.getScore(ChatColor.GREEN+"0");	
				five1.setScore(6);	
//				
				obj.getScore(ChatColor.AQUA+" ").setScore(5);
				Score spieler1 = obj.getScore(ChatColor.YELLOW+"Spieler:");	
				spieler1.setScore(4);	
				Score five11 = obj.getScore(ChatColor.GREEN+""+Events.team.size());	
				five11.setScore(3);	
//				
				obj.getScore(ChatColor.AQUA+" ").setScore(2);
				Score map = obj.getScore(ChatColor.YELLOW+"Mapname:");	
				map.setScore(1);	
				Score mapscore = obj.getScore(ChatColor.GREEN+""+Main.mapname);	
				mapscore.setScore(0);	
//			
				
			}
		pp.setScoreboard(board);
		
	}
	
	@SuppressWarnings("resource")
	@EventHandler
	public void onlog(PlayerLoginEvent e){
		Player p = e.getPlayer();
		if(ingame == false){
			int maxplayer = 12;
				if(Bukkit.getOnlinePlayers().size()>=maxplayer){
					if(MySQLRang.getRangname(p.getUniqueId().toString()).equals("Admin")||MySQLRang.getRangname(p.getUniqueId().toString()).equals("Mod")||MySQLRang.getRangname(p.getUniqueId().toString()).equals("Sup")||MySQLRang.getRangname(p.getUniqueId().toString()).equals("Youtuber")||MySQLRang.getRangname(p.getUniqueId().toString()).equals("Premium")||MySQLRang.getRangname(p.getUniqueId().toString()).equals("Builder")||MySQLRang.getRangname(p.getUniqueId().toString()).equals("Dev")||MySQLRang.getRangname(p.getUniqueId().toString()).equals("Contant")||MySQLRang.getRangname(p.getUniqueId().toString()).equals("Prem+")){
					for(Player pp:Bukkit.getOnlinePlayers()){
						if(!pp.hasPermission("cloudnet.fulljoin")){
							pp.sendMessage(ChatColor.GRAY+"Du wurdest gekickt um platz für ein "+ChatColor.GOLD+"Premium Spieler §7/ §5Youtuber §7/ §cTeammitglied §7zu machen! Kaufe dir Premium unter §eweb.jaylos.net §7 um nicht mehr gekickt zu werden!");
//							
							ByteArrayOutputStream b = new ByteArrayOutputStream();
							DataOutputStream out = new DataOutputStream(b);
							try{
								out.writeUTF("Connect");
								out.writeUTF("Lobby-1");
							}catch(IOException ex){
								System.err.println("Es gab einen Fehler:");
								ex.printStackTrace();
							}
							Plugin pl = Bukkit.getPluginManager().getPlugin("Trump");
							pp.sendPluginMessage(pl, "BungeeCord", b.toByteArray());
//							
							e.allow();
							return;
						}
					}
					e.disallow(Result.KICK_OTHER, ChatColor.RED+"Dieser Server ist voll! Keiner kann den Server mehr betreten");
					}else{
						e.disallow(Result.KICK_FULL, ChatColor.RED+"Dieser Server ist voll!"+ChatColor.GRAY+" Kaufe dir Premium unter "+ChatColor.YELLOW+"web.jaylos.net"+ChatColor.GRAY+" um trotzdem Joinen zu können!");
					}
					}
		}
	}
    
	public void restartcheck(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			@Override
			public void run() {
				Calendar c = Calendar.getInstance();
				c.setTime(new Timestamp(System.currentTimeMillis()));
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.GERMAN);
				String s = sdf.format(c.getTime());
				if(s.equals("03:30")){
					Bukkit.shutdown();
				}
			}
		}, 0, 20*10);
	}
	
}
