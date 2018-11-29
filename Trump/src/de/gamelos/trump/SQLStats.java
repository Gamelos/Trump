package de.gamelos.trump;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;




public class SQLStats {

	public static boolean playerExists(String uuid){
		try {
			@SuppressWarnings("static-access")
			ResultSet rs = de.gamelos.trump.Main.mysql.querry("SELECT * FROM Trump WHERE UUID = '"+ uuid + "'");
			
			if(rs.next()){
				return rs.getString("UUID") != null;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void createPlayer(String uuid, String name){
		String name1 = "";
		if(!(playerExists(uuid))){
				name1 = name;
				de.gamelos.trump.Main.mysql.update("INSERT INTO Trump(UUID, KILLS, TODE, WINS, GAMES, NAME, COINS) VALUES ('" +uuid+ "', '0', '0', '0', '0', '"+name1+"', '0');");
		}
	}
	
	//get-----------------------------------------------------------------------------------------------------------------------------------
	public static Integer getKills(String uuid, String name){
		Integer i = 0;
		if(playerExists(uuid)){
			try {
				@SuppressWarnings("static-access")
				ResultSet rs = de.gamelos.trump.Main.mysql.querry("SELECT * FROM Trump WHERE UUID = '"+ uuid + "'");
				
				if((!rs.next()) || (Integer.valueOf(rs.getInt("KILLS")) == null));
				
				i = rs.getInt("KILLS");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			createPlayer(uuid, name);
			getKills(uuid, name);
		}
		return i;
	}
	
	
	
	public static Integer getTode(String uuid, String name){
		Integer i = 0;
		if(playerExists(uuid)){
			try {
				@SuppressWarnings("static-access")
				ResultSet rs = de.gamelos.trump.Main.mysql.querry("SELECT * FROM Trump WHERE UUID = '"+ uuid + "'");
				
				if((!rs.next()) || (Integer.valueOf(rs.getInt("TODE")) == null));
				
				i = rs.getInt("TODE");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			createPlayer(uuid, name);
			getTode(uuid, name);
		}
		return i;
	}
	
	public static Integer getWins(String uuid, String name){
		Integer i = 0;
		if(playerExists(uuid)){
			try {
				@SuppressWarnings("static-access")
				ResultSet rs = de.gamelos.trump.Main.mysql.querry("SELECT * FROM Trump WHERE UUID = '"+ uuid + "'");
				
				if((!rs.next()) || (Integer.valueOf(rs.getInt("WINS")) == null));
				
				i = rs.getInt("WINS");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			createPlayer(uuid, name);
			getWins(uuid, name);
		}
		return i;
	}
	
	public static Integer getGames(String uuid, String name){
		Integer i = 0;
		if(playerExists(uuid)){
			try {
				@SuppressWarnings("static-access")
				ResultSet rs = de.gamelos.trump.Main.mysql.querry("SELECT * FROM Trump WHERE UUID = '"+ uuid + "'");
				
				if((!rs.next()) || (Integer.valueOf(rs.getInt("GAMES")) == null));
				
				i = rs.getInt("GAMES");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			createPlayer(uuid, name);
			getGames(uuid, name);
		}
		return i;
	}
	
	public static Integer getCoins(String uuid, String name){
		Integer i = 0;
		if(playerExists(uuid)){
			try {
				@SuppressWarnings("static-access")
				ResultSet rs = de.gamelos.trump.Main.mysql.querry("SELECT * FROM Trump WHERE UUID = '"+ uuid + "'");
				
				if((!rs.next()) || (Integer.valueOf(rs.getInt("COINS")) == null));
				
				i = rs.getInt("COINS");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			createPlayer(uuid, name);
			getCoins(uuid, name);
		}
		return i;
	}
	
	
	public static String getName(String uuid){
		String i = "";
		if(playerExists(uuid)){
			try {
				@SuppressWarnings("static-access")
				ResultSet rs = de.gamelos.trump.Main.mysql.querry("SELECT * FROM Trump WHERE UUID = '"+ uuid + "'");
				
				if((!rs.next()) || (String.valueOf(rs.getString("NAME")) == null));
				
				i = rs.getString("NAME");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			i = null;
		}
		return i;
	}
	
	
	//set-----------------------------------------------------------------------------------------------------------------------------------
	
	public static void setKills(String uuid, Integer kills, String name){
		
		if(playerExists(uuid)){
			de.gamelos.trump.Main.mysql.update("UPDATE Trump SET KILLS= '" + kills+ "' WHERE UUID= '" + uuid+ "';");
		}else{
			createPlayer(uuid, name);
			setKills(uuid, kills, name);
		}
		
	}
	
	public static void setTode(String uuid, Integer tode, String name){
		
		if(playerExists(uuid)){
			de.gamelos.trump.Main.mysql.update("UPDATE Trump SET TODE= '" + tode+ "' WHERE UUID= '" + uuid+ "';");
		}else{
			createPlayer(uuid, name);
			setTode(uuid, tode, name);
		}
	}
	
	public static void setWins(String uuid, Integer tode, String name){
		
		if(playerExists(uuid)){
			de.gamelos.trump.Main.mysql.update("UPDATE Trump SET WINS= '" + tode+ "' WHERE UUID= '" + uuid+ "';");
		}else{
			createPlayer(uuid, name);
			setWins(uuid, tode, name);
		}
	}
	
	public static void setGames(String uuid, Integer tode, String name){
		
		if(playerExists(uuid)){
			de.gamelos.trump.Main.mysql.update("UPDATE Trump SET GAMES= '" + tode+ "' WHERE UUID= '" + uuid+ "';");
		}else{
			createPlayer(uuid, name);
			setGames(uuid, tode, name);
		}
	}
	
	public static void setCoins(String uuid, Integer tode, String name){
		
		if(playerExists(uuid)){
			de.gamelos.trump.Main.mysql.update("UPDATE Trump SET COINS= '" + tode+ "' WHERE UUID= '" + uuid+ "';");
		}else{
			createPlayer(uuid, name);
			setCoins(uuid, tode, name);
		}
	}
	
	public static void setName(String uuid, String name){
		
		if(playerExists(uuid)){
			de.gamelos.trump.Main.mysql.update("UPDATE Trump SET NAME= '" + name+ "' WHERE UUID= '" + uuid+ "';");
		}
	}
	
	//add------------------------------------------------------------------------------------------------------------------------------------
	
	public static void addKills(String uuid, Integer kills, String name){
		
		if(playerExists(uuid)){
			setKills(uuid, Integer.valueOf(getKills(uuid, name).intValue() + kills.intValue()), name);
		}else{
			createPlayer(uuid, name);
			addKills(uuid, kills, name);
		}
		
	}
	
	public static void addTode(String uuid, Integer tode, String name){
		
		if(playerExists(uuid)){
			setTode(uuid, Integer.valueOf(getTode(uuid, name).intValue() + tode.intValue()), name);
		}else{
			createPlayer(uuid, name);
			addTode(uuid, tode, name);
		}
	}
	
	public static void addWins(String uuid, Integer tode, String name){
		
		if(playerExists(uuid)){
			setWins(uuid, Integer.valueOf(getWins(uuid, name).intValue() + tode.intValue()), name);
		}else{
			createPlayer(uuid, name);
			addWins(uuid, tode, name);
		}
	}
	
	public static void addGames(String uuid, Integer tode, String name){
		
		if(playerExists(uuid)){
			setGames(uuid, Integer.valueOf(getGames(uuid, name).intValue() + tode.intValue()), name);
		}else{
			createPlayer(uuid, name);
			addGames(uuid, tode, name);
		}
	}
	
	public static void addCoins(String uuid, Integer tode, String name){
		
		if(playerExists(uuid)){
			setCoins(uuid, Integer.valueOf(getCoins(uuid, name).intValue() + tode.intValue()), name);
		}else{
			createPlayer(uuid, name);
			addCoins(uuid, tode, name);
		}
	}
	
	//remove------------------------------------------------------------------------------------------------------------------------------------
	
	public static void removeKills(String uuid, Integer kills, String name){
		
		if(playerExists(uuid)){
			setKills(uuid, Integer.valueOf(getKills(uuid, name).intValue() - kills.intValue()), name);
		}else{
			createPlayer(uuid, name);
			removeKills(uuid, kills, name);
		}
		
	}
	
	public static void removeTode(String uuid, Integer tode, String name){
		
		if(playerExists(uuid)){
			setTode(uuid, Integer.valueOf(getTode(uuid, name).intValue() - tode.intValue()), name);
		}else{
			createPlayer(uuid, name);
			removeTode(uuid, tode, name);
		}
	}
	
	public static void removeWins(String uuid, Integer tode, String name){
		
		if(playerExists(uuid)){
			setWins(uuid, Integer.valueOf(getWins(uuid, name).intValue() - tode.intValue()), name);
		}else{
			createPlayer(uuid, name);
			removeWins(uuid, tode, name);
		}
	}
	
	public static void removeGames(String uuid, Integer tode, String name){
		
		if(playerExists(uuid)){
			setGames(uuid, Integer.valueOf(getGames(uuid, name).intValue() - tode.intValue()), name);
		}else{
			createPlayer(uuid, name);
			removeGames(uuid, tode, name);
		}
	}
	
	public static void removeCoins(String uuid, Integer tode, String name){
		
		if(playerExists(uuid)){
			setCoins(uuid, Integer.valueOf(getCoins(uuid, name).intValue() - tode.intValue()), name);
		}else{
			createPlayer(uuid, name);
			removeCoins(uuid, tode, name);
		}
	}
	
//	======================================================================================
	public static Integer getUserRanking(String uuid){
		@SuppressWarnings("static-access")
		ResultSet rs = de.gamelos.trump.Main.mysql.querry("SELECT * FROM Trump ORDER BY COINS DESC");
		int count = 0;
		try {
			while(rs.next()){
				count++;
				String nameUUID = rs.getString("UUID");
				UUID uuid1 = UUID.fromString(nameUUID);
				if(uuid1.toString().equals(uuid)){
					return count;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
//	=========================================================================================
	public static String getPlayerWho(Integer i){
		String pp = null;
		@SuppressWarnings("static-access")
		ResultSet rs = de.gamelos.trump.Main.mysql.querry("SELECT * FROM Trump ORDER BY COINS DESC");
		int count = 0;
		try {
			while(rs.next()){
				count++;
				String nameUUID = rs.getString("UUID");
				if(count == i){
					pp = getName(nameUUID);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pp;
	}
	
	
//	=========================================================================================
	public static String getPlayerWhoUUID(Integer i){
		String pp = null;
		@SuppressWarnings("static-access")
		ResultSet rs = de.gamelos.trump.Main.mysql.querry("SELECT * FROM Trump ORDER BY COINS DESC");
		int count = 0;
		try {
			while(rs.next()){
				count++;
				if(count == i){
					pp = rs.getString("UUID");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pp;
	}
}
