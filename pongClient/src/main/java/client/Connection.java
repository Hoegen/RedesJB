package client;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import server.ServidorFake;

public class Connection {
	Util.XML xml = new Util.XML();
	ServidorFake svfk;
	
	
	public Connection(ServidorFake svfk){
		this.svfk = svfk;
	}
	
	class Sender{
		
	}
	
	
	public static class Receiver{
		static ArrayList<Player> players = new ArrayList<Player>();
		static Ball ball;
		
		
		//recebe as mensagens html/xml e armazena os novos valores.
		public static void receive(String message) {
			Elements packet = Util.XML.toElements(message);
			
			System.out.println("_______________________________________________"
				           	+  "                Mensagem recebida pelo cliente:");
			System.out.println(packet);
			
			Elements playerelements = packet.select("player");
			Element currentplayer;
			Player player;
			for(int i = 0; i < playerelements.size(); i ++) {
				currentplayer = playerelements.select("#" + i).first();
				player = new ;
				
			}
			
		}
	}
	
}
