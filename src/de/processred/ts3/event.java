package de.processred.ts3;

import java.util.ArrayList;


import com.github.theholywaffle.teamspeak3.api.event.ChannelCreateEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDeletedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDescriptionEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelPasswordChangedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.PrivilegeKeyUsedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ServerEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3Listener;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import de.processred.ts3.Main;

public class event implements TS3Listener {

	ArrayList<secureClient> clientIds;

	public event() {
		clientIds = new ArrayList<secureClient>();
	}

	@Override
	public void onChannelCreate(ChannelCreateEvent e) {
		Main.CheckChannel(e.getInvokerUniqueId(), Main.api.getChannelInfo(e.getChannelId()).getName());
		Main.CheckChannel(e.getInvokerUniqueId(), Main.api.getChannelInfo(e.getChannelId()).getDescription());

		int clientId = e.getInvokerId();
		for (secureClient client : clientIds) {
			if (client.getClientId() == clientId) {
				if (!client.getRoomCreateValidation()) {
					Main.api.kickClientFromServer("Channel Flooding not allowed", clientId);
				}
				return;
			}
		}

		secureClient client = new secureClient(clientId);
		clientIds.add(client);
		client.getRoomCreateValidation();
	}

	@Override
	public void onChannelDeleted(ChannelDeletedEvent arg0) {
	}

	@Override
	public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent e) {
			}

	@Override
	public void onChannelEdit(ChannelEditedEvent e) {
		Main.CheckChannel(e.getInvokerUniqueId(), Main.api.getChannelInfo(e.getChannelId()).getName());
		Main.CheckChannel(e.getInvokerUniqueId(), Main.api.getChannelInfo(e.getChannelId()).getDescription());
	}

	@Override
	public void onChannelPasswordChanged(ChannelPasswordChangedEvent arg0) {
	}

	@Override
	public void onClientMoved(ClientMovedEvent e) {

		int clientId = e.getClientId();

		for (secureClient client1 : clientIds) {
			if (client1.getClientId() == clientId) {
				if (!client1.getChannelMoveValidation()) {
					Main.api.kickClientFromServer("Channel hopping not allowed", clientId);
				}
				return;
			}
		}

		secureClient client1 = new secureClient(clientId);
		clientIds.add(client1);
		client1.getChannelMoveValidation();

	  }
    	    	
	       


	@Override
	public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent e) {
	}
	
	@Override
	public void onServerEdit(ServerEditedEvent arg0) {
	}

	@Override
	public void onTextMessage(TextMessageEvent e) {
		
	}

	@Override
	public void onClientJoin(ClientJoinEvent e) {
		int clientId = e.getClientId();

		Client client = Main.api.getClientInfo(clientId);
		String version = Main.api.getClientInfo(clientId).getVersion();
		String clientip = Main.api.getClientInfo(clientId).getIp();

		if (version != "ServerQuery") {
			secureClient sClient = new secureClient(clientId);
			clientIds.add(sClient);

			try {
				boolean isValideGroup = client.isInServerGroup(27) || client.isInServerGroup(29)
						|| client.isInServerGroup(34);

				if (!(version.contains("3.3.1") || version.contains("5.0.0-alpha316") || isValideGroup)) {
					Main.api.sendPrivateMessage(clientId,
							"\nDistrict24\nPlease update your client:\nhttps://files.teamspeak-services.com/releases/client/3.3.1/TeamSpeak3-Client-win64-3.3.1.exe");
				
				}
			} catch (Exception excep) {
				System.out.println(version);
			}
		}
		
		String group = e.getClientServerGroups().replaceAll("9", "User").replaceAll("12", "Admin").replaceAll(",", "").replaceAll("17", "Developer").replaceAll("14", "Moderator").replaceAll("31", "Supporter").replaceAll("35", "CEO").replaceAll("36", "").replaceAll("67", "").replaceAll("73", "").replaceAll("82", "").replaceAll("1", "!Guest Query").replaceAll("2", "!!!!!!!Admin Query!!!!!!!");
	
		for (Client adminclient : Main.api.getClients()) {
				if(adminclient.isInServerGroup(82)) {
					Main.api.sendPrivateMessage(adminclient.getId(),"\nUsername: " + e.getClientNickname() + "\nVersion: " + version + "\nGruppe: " + group + "\nTools: ([url=https://ipinfo.io/"+ clientip +"]IPINFO[/url]) ([url=https://ts3index.com/?page=searchclient&nickname=" + e.getClientNickname().replaceAll(" ", "%20") +"]Nickname Search[/url]) [url=https://ts3index.com/?page=searchclient&uid=" + e.getUniqueClientIdentifier() +"]UID Search[/url])");
				}
			}
		
		
		
		String name = e.getClientNickname();
		Client client1 = Main.api.getClientByNameExact(name, true);
		String ip = client1.getIp();
		int count = 0;
		for (Client clients : Main.api.getClients()) {
			if (client1 != clients) {
				if (clients.getIp().equalsIgnoreCase(ip)) {
					count++;
				}
			}
		}
		if (count > 3) {
			boolean kick = true;
			int[] groups = client1.getServerGroups();
			int[] saveGroups = { 843 };
			for (int group1 : groups) {
				for (int saveGroup : saveGroups) {
					if(group1 == saveGroup) {
						kick = false;
					}
				}
			}
			if (kick) {
				Main.api.kickClientFromServer("Dont use 3 Clients on the server", client1);
			}
		}

		
		
		}
	
	@Override
	public void onClientLeave(ClientLeaveEvent e) {
		int clientId = e.getClientId();

		for (secureClient client : clientIds) {
			if (client.getClientId() == clientId) {
				clientIds.remove(client);
				return;
			}
		}
	}

	@Override
	public void onChannelMoved(ChannelMovedEvent arg0) {
		// TODO Auto-generated method stub

	}

}
