package de.processred.ts3;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.TS3Query.FloodRate;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;


public class Main {

	public static final int port = 9987;
	public static final int queryport = 10011;
	public static final String ip = "127.0.0.1";
	public static final String username = "serveradmin";
	public static final String password = "passwort";
	public static final String nickname = "Queryusername";

	final static TS3Config config = new TS3Config();
	final static TS3Query query = new TS3Query(config);
	final static TS3Api api = query.getApi();

	static String[] invalideNames = new String[] { "siegheil", "judenvergasung", "ficken","hitler", "adolf hitler", "hurensohn", "fotze", "ficker", "missit", "vergaser", "schwuchtel", "cock", "penis"
			, "pimmel", "hure", "hurensoh", "huren", "pisser", "l2p", "l²p", "e²", "geschlechtsverkehr", "wichskr¼ppel", "arschlochkind", "penisschlumpf", "sperma", "wichs"
			, "sex", "muschi", "vagina", "erektion", "hoden", "m¶se", "cracknutte", "tripper", "sack", "pisse", "kotze", "scheiŸ", "scheiss", "vorhaut", "prostata"
			, "schlampe", "luder", "transe", "pissnelke", "motherfucker", "fick", "wichser", "ddos", "asshole", "dumbass", "bitch", "transe", "huso", "titten", "wixer", "wixxer", "wikser", "noob", "noop"
			, "nigger", "e2", "spast", "bastard", "Fvck", "hvere", "wxer", "Gasdusche", "vergasen", "n4b", "nob", "gaskammer", ".de", ".d e", "[punkt]", "(punkt)", ".dee", "xxx", ".ip", ".biz", "join now", ".pl", ".net", "mein netzwerk", "noip", "c o m", "x x x", "c om", ".com", "www"
			, ".tk", ",de", ",com", ",tk", ",net", "sytrex", "s y t r e x", "s ytrex", "sy trex", "syt rex", "sytr ex", "sytre x", "sy tre x", "sytr ex", "s ytrex"
			, "syntaxgaming", "syntax gaming", "blauban", "blau ban", "b lauban", "s yntaxgaming", "[.]", ".tv"  };

	public static void main(String[] args){
		
		config.setHost(ip);
		config.setQueryPort(queryport);
		config.setFloodRate(FloodRate.UNLIMITED);
		query.connect();
		api.login(username, password);
		api.selectVirtualServerByPort(port);
		api.setNickname(nickname);

		checkNickname.start();
		api.registerAllEvents();
		api.addTS3Listeners(new event());
	}

	public static void CheckClient(Client c) {
		String name = c.getNickname().toLowerCase();

		for (String string : invalideNames) {
			if (name.contains(string)) {
				Main.api.sendPrivateMessage(c.getId(), "\nDistrict24\nPlease use a differnt name: " + c.getNickname() + "");
				Main.api.kickClientFromServer(c.getId());
				
			}
		}
	}
	
	

	public static void CheckChannel(String id, String name) {
		Client c = Main.api.getClientByUId(id);
		name = name.toLowerCase();
		for (String string : invalideNames) {
			if (name.contains(string)) {
				Main.api.sendPrivateMessage(c.getId(), "\nDistrict24\nPlease use a differnt name: " + c.getNickname() + "");
				Main.api.kickClientFromServer(c.getId());
			}
		}
	}
}
