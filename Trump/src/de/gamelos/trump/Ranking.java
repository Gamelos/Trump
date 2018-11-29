package de.gamelos.trump;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.TileEntitySkull;

public class Ranking {
	
	
	
	@SuppressWarnings("deprecation")
	public static String getUUID(String name){
		return Bukkit.getOfflinePlayer(name).getUniqueId().toString().replace("-", "");
	}
  
	public static void gethead(String uuid,Block b){
		if(MySQLSkin.playerExists(uuid)){
			String name = MySQLSkin.getname(uuid);
			String value = MySQLSkin.getvalue(uuid);
			String signature = MySQLSkin.getSignature(uuid);
			String s1 = "http://textures.minecraft.net/texture/"+value;
		setHead(b, name, value, signature);
		
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void setHead(Block skull, String skinname,String skinvalue,String skinsegnature) {
		 TileEntitySkull tile = (TileEntitySkull) ((CraftWorld) skull.getWorld()).getHandle().getTileEntity(new BlockPosition.MutableBlockPosition(skull.getX(), skull.getY(), skull.getZ()));
		 if(skinname !=null){
		 GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		 profile.getProperties().put(skinname, new Property(skinname,skinvalue,skinsegnature));
		 tile.setGameProfile(profile);
		 }
		 skull.getWorld().refreshChunk(skull.getChunk().getX(), skull.getChunk().getZ());
		 }
	
	@SuppressWarnings("deprecation")
	public static void setHead(Block skull, String skinUrl) {
		 TileEntitySkull tile = (TileEntitySkull) ((CraftWorld) skull.getWorld()).getHandle().getTileEntity(new BlockPosition.MutableBlockPosition(skull.getX(), skull.getY(), skull.getZ()));

		 GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		 profile.getProperties().put("textures", new Property("textures", Base64Coder.encodeString("{textures:{SKIN:{url:" + skinUrl + "}}}")));
		 tile.setGameProfile(profile);

		 skull.getWorld().refreshChunk(skull.getChunk().getX(), skull.getChunk().getZ());
		 }
	
	public static void updateranking(){
		
		Location loc = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)19, (double)434);
		Block bb = Bukkit.getWorld("wartelobby").getBlockAt(loc);
		Sign ss = (Sign) bb.getState();
		ss.setLine(0, "===========");
		ss.setLine(1, "Top 10");
		ss.setLine(2, "in Trump");
		ss.setLine(3, "===========");
		ss.update();
		
//		===================================================================================================================
		if(SQLStats.getPlayerWho(1) != null){
		String uuid = SQLStats.getPlayerWhoUUID(1);
		String spieler1 = SpielerUUID.getSpielername(uuid);
		Location loc1 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)18, (double)435);
		Location kopf1 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)19, (double)435);
		int wins = SQLStats.getCoins(uuid, spieler1);
		int kills = SQLStats.getWins(uuid.toString(), spieler1);
		int tode = SQLStats.getTode(uuid.toString(), spieler1);
		double kd = 0.0;
		if(tode == 0){
			kd = kills;
		}else{
		double z = ((double) kills) / ((double) tode);
		kd = Math.round(z * 100.0D) / 100.0D;
		}
//		...........................................................
		Block b1 = Bukkit.getWorld("wartelobby").getBlockAt(loc1);
		Sign s = (Sign) b1.getState();
		s.setLine(0, "Platz 1");
		s.setLine(1, spieler1);
		s.setLine(2, "Punkte: "+wins);
		s.setLine(3, "Wins: "+kills);
		s.update();
//		...........................................................
			gethead(uuid, Bukkit.getWorld("wartelobby").getBlockAt(kopf1));
		
		
//		...........................................................
		}else{
			Location loc4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)18, (double)435);
			Block b4 = Bukkit.getWorld("wartelobby").getBlockAt(loc4);
			Sign s4 = (Sign) b4.getState();
			s4.setLine(0, "Platz 1");
			s4.setLine(1, "--");
			s4.setLine(2, "--");
			s4.setLine(3, "--");
			s4.update();
		}
//		===========================================================================================================
		if(SQLStats.getPlayerWho(2) != null){
			String uuid = SQLStats.getPlayerWhoUUID(2);
			String spieler1 = SpielerUUID.getSpielername(uuid);
		int wins2 = SQLStats.getCoins(uuid, spieler1);
		int kills2 =SQLStats.getWins(uuid, spieler1);
		int tode2 = SQLStats.getTode(uuid, spieler1);
		double kd2 = 0.0;
		if(tode2 == 0){
			kd2 = kills2;
		}else{
		double z2 = ((double) kills2) / ((double) tode2);
		kd2 = Math.round(z2 * 100.0D) / 100.0D;
		}
		Location loc2 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)18, (double)436);
		Location kopf2 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)19, (double)436);
		Block b2 = Bukkit.getWorld("wartelobby").getBlockAt(loc2);
		Sign s2 = (Sign) b2.getState();
		s2.setLine(0, "Platz 2");
		s2.setLine(1, spieler1);
		s2.setLine(2, "Punkte: "+wins2);
		s2.setLine(3, "Wins: "+kills2);
		s2.update();
//		...........................................................
			gethead(uuid, Bukkit.getWorld("wartelobby").getBlockAt(kopf2));
//		...........................................................
		}else{
			Location loc4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)18, (double)436);
			Block b4 = Bukkit.getWorld("wartelobby").getBlockAt(loc4);
			Sign s4 = (Sign) b4.getState();
			s4.setLine(0, "Platz 2");
			s4.setLine(1, "--");
			s4.setLine(2, "--");
			s4.setLine(3, "--");
			s4.update();
		}
//		===========================================================================================================
		if(SQLStats.getPlayerWho(3) != null){
			String uuid = SQLStats.getPlayerWhoUUID(3);
			String spieler1 = SpielerUUID.getSpielername(uuid);
		int wins3 = SQLStats.getCoins(uuid, spieler1);
		int kills3 = SQLStats.getWins(uuid, spieler1);
		int tode3 = SQLStats.getTode(uuid, spieler1);
		double kd3 = 0.0;
		if(tode3 == 0){
			kd3 = kills3;
		}else{
		double z3 = ((double) kills3) / ((double) tode3);
		kd3 = Math.round(z3 * 100.0D) / 100.0D;
		}
		Location loc3 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)18, (double)437);
		Location kopf3 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)19, (double)437);
		Block b3 = Bukkit.getWorld("wartelobby").getBlockAt(loc3);
		Sign s3 = (Sign) b3.getState();
		s3.setLine(0, "Platz 3");
		s3.setLine(1, spieler1);
		s3.setLine(2, "Punkte: "+wins3);
		s3.setLine(3, "Wins: "+kills3);
		s3.update();
//		...........................................................
		gethead(uuid, Bukkit.getWorld("wartelobby").getBlockAt(kopf3));
		}else{
			Location loc4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)18, (double)437);
			Block b4 = Bukkit.getWorld("wartelobby").getBlockAt(loc4);
			Sign s4 = (Sign) b4.getState();
			s4.setLine(0, "Platz 3");
			s4.setLine(1, "--");
			s4.setLine(2, "--");
			s4.setLine(3, "--");
			s4.update();
		}
//		===========================================================================================================
		if(SQLStats.getPlayerWho(4) != null){
			String uuid = SQLStats.getPlayerWhoUUID(4);
			String spieler1 = SpielerUUID.getSpielername(uuid);
		int wins4 = SQLStats.getCoins(uuid, spieler1);
		int kills4 = SQLStats.getWins(uuid, spieler1);
		int tode4 = SQLStats.getTode(uuid, spieler1);
		double kd4 = 0.0;
		if(tode4 == 0){
			kd4 = kills4;
		}else{
		double z4 = ((double) kills4) / ((double) tode4);
		kd4 = Math.round(z4 * 100.0D) / 100.0D;
		}
		Location loc4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)18, (double)438);
		Location kopf4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)19, (double)438);
		Block b4 = Bukkit.getWorld("wartelobby").getBlockAt(loc4);
		Sign s4 = (Sign) b4.getState();
		s4.setLine(0, "Platz 4");
		s4.setLine(1, spieler1);
		s4.setLine(2, "Punkte: "+wins4);
		s4.setLine(3, "Wins: "+kills4);
		s4.update();
//		...........................................................
			gethead(uuid, Bukkit.getWorld("wartelobby").getBlockAt(kopf4));
		}else{
			Location loc4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)18, (double)438);
			Block b4 = Bukkit.getWorld("wartelobby").getBlockAt(loc4);
			Sign s4 = (Sign) b4.getState();
			s4.setLine(0, "Platz 4");
			s4.setLine(1, "--");
			s4.setLine(2, "--");
			s4.setLine(3, "--");
			s4.update();
		}
//		===========================================================================================================
		if(SQLStats.getPlayerWho(5) != null){
			String uuid = SQLStats.getPlayerWhoUUID(5);
			String spieler1 = SpielerUUID.getSpielername(uuid);
		int wins4 = SQLStats.getCoins(uuid, spieler1);
		int kills4 = SQLStats.getWins(uuid, spieler1);
		int tode4 = SQLStats.getTode(uuid, spieler1);
		double kd4 = 0.0;
		if(tode4 == 0){
			kd4 = kills4;
		}else{
		double z4 = ((double) kills4) / ((double) tode4);
		kd4 = Math.round(z4 * 100.0D) / 100.0D;
		}
		Location loc4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)18, (double)439);
		Location kopf4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)19, (double)439);
		Block b4 = Bukkit.getWorld("wartelobby").getBlockAt(loc4);
		Sign s4 = (Sign) b4.getState();
		s4.setLine(0, "Platz 5");
		s4.setLine(1, spieler1);
		s4.setLine(2, "Punkte: "+wins4);
		s4.setLine(3, "Wins: "+kills4);
		s4.update();
//		...........................................................
		gethead(uuid, Bukkit.getWorld("wartelobby").getBlockAt(kopf4));
		}else{
			Location loc4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)18, (double)439);
			Block b4 = Bukkit.getWorld("wartelobby").getBlockAt(loc4);
			Sign s4 = (Sign) b4.getState();
			s4.setLine(0, "Platz 5");
			s4.setLine(1, "--");
			s4.setLine(2, "--");
			s4.setLine(3, "--");
			s4.update();
		}
//		===========================================================================================================
		if(SQLStats.getPlayerWho(6) != null){
			String uuid = SQLStats.getPlayerWhoUUID(6);
			String spieler1 = SpielerUUID.getSpielername(uuid);
		int wins4 = SQLStats.getCoins(uuid, spieler1);
		int kills4 = SQLStats.getWins(uuid, spieler1);
		int tode4 = SQLStats.getTode(uuid, spieler1);
		double kd4 = 0.0;
		if(tode4 == 0){
			kd4 = kills4;
		}else{
		double z4 = ((double) kills4) / ((double) tode4);
		kd4 = Math.round(z4 * 100.0D) / 100.0D;
		}
		Location loc4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)18, (double)440);
		Location kopf4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)19, (double)440);
		Block b4 = Bukkit.getWorld("wartelobby").getBlockAt(loc4);
		Sign s4 = (Sign) b4.getState();
		s4.setLine(0, "Platz 6");
		s4.setLine(1, spieler1);
		s4.setLine(2, "Punkte: "+wins4);
		s4.setLine(3, "Wins: "+kills4);
		s4.update();
//		...........................................................
			gethead(uuid, Bukkit.getWorld("wartelobby").getBlockAt(kopf4));
		
		}else{
			Location loc4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)18, (double)440);
			Block b4 = Bukkit.getWorld("wartelobby").getBlockAt(loc4);
			Sign s4 = (Sign) b4.getState();
			s4.setLine(0, "Platz 6");
			s4.setLine(1, "--");
			s4.setLine(2, "--");
			s4.setLine(3, "--");
			s4.update();
		}
//		===========================================================================================================
		if(SQLStats.getPlayerWho(7) != null){
			String uuid = SQLStats.getPlayerWhoUUID(7);
			String spieler1 = SpielerUUID.getSpielername(uuid);
		int wins4 = SQLStats.getCoins(uuid, spieler1);
		int kills4 = SQLStats.getWins(uuid, spieler1);
		int tode4 = SQLStats.getTode(uuid, spieler1);
		double kd4 = 0.0;
		if(tode4 == 0){
			kd4 = kills4;
		}else{
		double z4 = ((double) kills4) / ((double) tode4);
		kd4 = Math.round(z4 * 100.0D) / 100.0D;
		}
		Location loc4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)18, (double)441);
		Location kopf4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)19, (double)441);
		Block b4 = Bukkit.getWorld("wartelobby").getBlockAt(loc4);
		Sign s4 = (Sign) b4.getState();
		s4.setLine(0, "Platz 7");
		s4.setLine(1, spieler1);
		s4.setLine(2, "Punkte: "+wins4);
		s4.setLine(3, "Wins: "+kills4);
		s4.update();
//		...........................................................
		gethead(uuid, Bukkit.getWorld("wartelobby").getBlockAt(kopf4));
		}else{
			Location loc4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)18, (double)441);
			Block b4 = Bukkit.getWorld("wartelobby").getBlockAt(loc4);
			Sign s4 = (Sign) b4.getState();
			s4.setLine(0, "Platz 7");
			s4.setLine(1, "--");
			s4.setLine(2, "--");
			s4.setLine(3, "--");
			s4.update();
		}
//		===========================================================================================================
		if(SQLStats.getPlayerWho(8) != null){
			String uuid = SQLStats.getPlayerWhoUUID(8);
			String spieler1 = SpielerUUID.getSpielername(uuid);
		int wins4 = SQLStats.getCoins(uuid, spieler1);
		int kills4 = SQLStats.getWins(uuid, spieler1);
		int tode4 = SQLStats.getTode(uuid, spieler1);
		double kd4 = 0.0;
		if(tode4 == 0){
			kd4 = kills4;
		}else{
		double z4 = ((double) kills4) / ((double) tode4);
		kd4 = Math.round(z4 * 100.0D) / 100.0D;
		}
		Location loc4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)18, (double)442);
		Location kopf4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)19, (double)442);
		Block b4 = Bukkit.getWorld("wartelobby").getBlockAt(loc4);
		Sign s4 = (Sign) b4.getState();
		s4.setLine(0, "Platz 8");
		s4.setLine(1, spieler1);
		s4.setLine(2, "Punkte: "+wins4);
		s4.setLine(3, "Wins: "+kills4);
		s4.update();
//		...........................................................
			gethead(uuid, Bukkit.getWorld("wartelobby").getBlockAt(kopf4));
		
		}else{
			Location loc4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)18, (double)442);
			Block b4 = Bukkit.getWorld("wartelobby").getBlockAt(loc4);
			Sign s4 = (Sign) b4.getState();
			s4.setLine(0, "Platz 8");
			s4.setLine(1, "--");
			s4.setLine(2, "--");
			s4.setLine(3, "--");
			s4.update();
		}
//		===========================================================================================================
		if(SQLStats.getPlayerWho(9) != null){
			String uuid = SQLStats.getPlayerWhoUUID(9);
			String spieler1 = SpielerUUID.getSpielername(uuid);
		int wins4 = SQLStats.getCoins(uuid, spieler1);
		int kills4 = SQLStats.getWins(uuid, spieler1);
		int tode4 = SQLStats.getTode(uuid, spieler1);
		double kd4 = 0.0;
		if(tode4 == 0){
			kd4 = kills4;
		}else{
		double z4 = ((double) kills4) / ((double) tode4);
		kd4 = Math.round(z4 * 100.0D) / 100.0D;
		}
		Location loc4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)18, (double)443);
		Location kopf4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)19, (double)443);
		Block b4 = Bukkit.getWorld("wartelobby").getBlockAt(loc4);
		Sign s4 = (Sign) b4.getState();
		s4.setLine(0, "Platz 9");
		s4.setLine(1, spieler1);
		s4.setLine(2, "Punkte: "+wins4);
		s4.setLine(3, "Wins: "+kills4);
		s4.update();
//		...........................................................
		gethead(uuid, Bukkit.getWorld("wartelobby").getBlockAt(kopf4));
		}else{
			Location loc4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)18, (double)443);
			Block b4 = Bukkit.getWorld("wartelobby").getBlockAt(loc4);
			Sign s4 = (Sign) b4.getState();
			s4.setLine(0, "Platz 9");
			s4.setLine(1, "--");
			s4.setLine(2, "--");
			s4.setLine(3, "--");
			s4.update();
		}
//	===========================================================================================================
	if(SQLStats.getPlayerWho(10) != null){
		String uuid = SQLStats.getPlayerWhoUUID(10);
		String spieler1 = SpielerUUID.getSpielername(uuid);
	int wins4 = SQLStats.getCoins(uuid, spieler1);
	int kills4 = SQLStats.getWins(uuid, spieler1);
	int tode4 = SQLStats.getTode(uuid, spieler1);
	double kd4 = 0.0;
	if(tode4 == 0){
		kd4 = kills4;
	}else{
	double z4 = ((double) kills4) / ((double) tode4);
	kd4 = Math.round(z4 * 100.0D) / 100.0D;
	}
	Location loc4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)18, (double)444);
	Location kopf4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)19, (double)444);
	Block b4 = Bukkit.getWorld("wartelobby").getBlockAt(loc4);
	Sign s4 = (Sign) b4.getState();
	s4.setLine(0, "Platz 10");
	s4.setLine(1, spieler1);
	s4.setLine(2, "Punkte: "+wins4);
	s4.setLine(3, "Wins: "+kills4);
	s4.update();
//	...........................................................
	gethead(uuid, Bukkit.getWorld("wartelobby").getBlockAt(kopf4));
	
	}else{
		Location loc4 = new Location(Bukkit.getWorld("wartelobby"), (double)-347, (double)18, (double)444);
		Block b4 = Bukkit.getWorld("wartelobby").getBlockAt(loc4);
		Sign s4 = (Sign) b4.getState();
		s4.setLine(0, "Platz 10");
		s4.setLine(1, "--");
		s4.setLine(2, "--");
		s4.setLine(3, "--");
		s4.update();
	}
}
}
