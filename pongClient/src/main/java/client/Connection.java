package client;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import server.ServidorFake;

public class Connection {
	ServidorFake svfk;
	Sender sender;
	
	public Connection(ServidorFake svfk){
		this.svfk = svfk;
		sender = new Sender();
	}
	
	//serve só para poder instanciar as posições de jogadores e das bolinhas
	public Connection(){
	}
	
	public class Sender{
		public String send() {
			return svfk.receiveMessage(newMessage());
		}
		
		String newMessage() {
			Document message = Document.createShell("");
			
			Element packet = message.createElement("packet");
			message.appendChild(packet);
			
			
			Element keysdown = message.createElement("keysdown");
			packet.appendChild(keysdown);
			
			String keys = "";
			
			if(ClientWindow.KeyDown.A) keys = keys + "A";
			if(ClientWindow.KeyDown.Z) keys = keys + "Z";
			if(ClientWindow.KeyDown.L) keys = keys + "L";
			if(ClientWindow.KeyDown.comma) keys = keys + ",";
			if(ClientWindow.KeyDown.SPACE) keys = keys + " ";
			
			keysdown.appendText(keys);
			
			Element pausecommand = message.createElement("pausecommand");
			packet.appendChild(pausecommand);
			pausecommand.appendText(Boolean.toString(ClientWindow.KeyPressed.SPACE));
			if(ClientWindow.KeyPressed.SPACE) ClientWindow.KeyPressed.SPACE = false;
			
			return message.toString();
		}
	}
	
	
	public static class Receiver{
		static ArrayList<PlayerData> players = new ArrayList<PlayerData>();
		static BallData balldata;
		static Connection c = new Connection();
		static boolean paused = false;
		static boolean unchanged = true;
		
		
		//recebe as mensagens html/xml e armazena os novos valores.
		public static String receive(String message) {
			
			Elements packet = Util.XML.toElements(message);
			
			
			//seleciona os jogadores do pacote
			Elements playerelements = packet.select("player");
			Element currentplayer;
			PlayerData playerdata;

			players = new ArrayList<PlayerData>();
			for(int i = 1; i <= playerelements.size(); i ++) {
				currentplayer = playerelements.select("#" + i).first();
				
				playerdata = c.new PlayerData();
				playerdata.elementToData(currentplayer);
				players.add(playerdata);
			}
			
			//seleciona o elemento da bola
			Element ball = packet.select("ball").first();
			balldata = c.new BallData();
			balldata.elementToData(ball);
			
			//verifica se o jogo está pausado
			paused = Boolean.valueOf(packet.select("match").text());
			
			unchanged = false;
			
			return "placeholder para uma resposta do sistema";
		}
	}
	
	class PlayerData {

		int id;
		String name;
		float ypos;
		int score;
		float speed;
		
		void elementToData(Element player) {
			id = Integer.valueOf(player.id());
			name = player.getElementsByTag("name").text();
			ypos = Float.parseFloat(player.getElementsByTag("ypos").text());
			score = Integer.parseInt(player.getElementsByTag("score").text());
			speed = Float.parseFloat(player.getElementsByTag("speed").text());
		}
		
		@Override
		public String toString() {
			return "PlayerData [id=" + id + ", name=" + name + ", ypos=" + ypos + ", score=" + score + ", speed="
					+ speed + "]";
		}
	}
	
	class BallData {
		float speed;
		float xpos;
		float ypos;
		float dir;
		
		void elementToData(Element ball){
			speed = Float.parseFloat(ball.select("speed").text());
			xpos = Float.parseFloat(ball.select("xpos").text());
			ypos = Float.parseFloat(ball.select("ypos").text());
			dir = Float.parseFloat(ball.select("dir").text());
		}
	
		@Override
		public String toString() {
			return "BallData [speed=" + speed + ", xpos=" + xpos + ", ypos=" + ypos + ", dir=" + dir + "]";
		}
	}
}
