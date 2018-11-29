package de.gamelos.trump;

import java.sql.ResultSet;
import java.sql.SQLException;


@SuppressWarnings("static-access")
public class SpielerUUID {
public static boolean playerExists(String uuid){
		try {
			ResultSet rs = Main.mysql.querry("SELECT * FROM SpielerUUID WHERE UUID = '"+ uuid + "'");
			
			if(rs.next()){
				return rs.getString("UUID") != null;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

public static boolean spielernameExists(String uuid){
	try {
		ResultSet rs = Main.mysql.querry("SELECT * FROM SpielerUUID WHERE Spielername = '"+ uuid + "'");
		
		if(rs.next()){
			return rs.getString("Spielername") != null;
		}
		return false;
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return false;
}
	
public static void createPlayer(String uuid, String spielername){
	if(!(playerExists(uuid))){
		Main.mysql.update("INSERT INTO SpielerUUID(UUID, Spielername) VALUES ('" +uuid+ "', '"+spielername+"');");
	}
}
	
	//get-----------------------------------------------------------------------------------------------------------------------------------
	public static String getSpielername(String uuid){
		String i = "";
		if(playerExists(uuid)){
			try {
				ResultSet rs = Main.mysql.querry("SELECT * FROM SpielerUUID WHERE UUID = '"+ uuid + "'");
				
				if((!rs.next()) || (String.valueOf(rs.getString("Spielername")) == null));
				
				i = rs.getString("Spielername");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
		}
		return i;
	}
	
	public static String getUUID(String spielername){
		String i = "";
		if(spielernameExists(spielername)){
			try {
				ResultSet rs = Main.mysql.querry("SELECT * FROM SpielerUUID WHERE Spielername = '"+ spielername + "'");
				
				if((!rs.next()) || (String.valueOf(rs.getString("UUID")) == null));
				
				i = rs.getString("UUID");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
		}
		return i;
	}
	
	//set-----------------------------------------------------------------------------------------------------------------------------------
	
public static void setSpielername(String uuid, String spielername){
		
		if(playerExists(uuid)){
			Main.mysql.update("UPDATE SpielerUUID SET Spielername= '" + spielername+ "' WHERE UUID= '" + uuid+ "';");
		}else{
		}
	}

public static void removeSpieler(String UUID){
	Main.mysql.update("DELETE FROM SpielerUUID WHERE UUID = '"+UUID+"'");
}
}
