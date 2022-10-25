package server;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import client.Connection;

public class ServidorFake {
	public static Match match;
	private DocBuilder docbuilder;
	
	
	public ServidorFake() {
		match = new Match();
		docbuilder = new DocBuilder();
	};
	
	public static void main() {
		ServidorFake server = new ServidorFake();
		
		
		while(true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			server.sendMessage(server.newMessage());
		}
	}
	
	public String receiveMessage(String message) {
		Elements packet = Jsoup.parse(message).getElementsByTag("packet");
		String keys = packet.select("keysdown").text();
		
		Match.KeyDown.A = keys.contains("A");
		Match.KeyDown.Z = keys.contains("Z");
		Match.KeyDown.L = keys.contains("L");
		Match.KeyDown.comma = keys.contains(",");
		Match.KeyDown.SPACE = keys.contains(" ");
		
		if(Boolean.valueOf(packet.select("pausecommand").text())) {
			System.out.println("comando de pausa recebido");
			System.out.println("match estava pausada?" + match.isPaused());
		}
		
		if(Boolean.valueOf(packet.select("pausecommand").text())) {
			System.out.println("receiveMessage");
			match.changePause();
		}
		
		return "placeholder para uma resposta do sistema";
	}
	
	public String newMessage() {
		Document message = Document.createShell("");
		
		Element packet = message.createElement("packet");
		message.appendChild(packet);
		
		Element playertag; 
		
		//adiciona os jogadores na mensagem
		for(Player itplayer : match.playerlist.values()) {
			
			playertag = message.createElement("player");
			playertag.attr("id", Integer.toString(itplayer.getId()));
			
			packet.appendChild(playertag);
			
			docbuilder.addElement(message, playertag, "name", itplayer.getName());
			docbuilder.addElement(message, playertag, "ypos", Float.toString(itplayer.getYpos()));
			docbuilder.addElement(message, playertag, "score", Integer.toString(itplayer.getScore()));
			docbuilder.addElement(message, playertag, "speed", Float.toString(itplayer.getSpeed()));
		}
		
		
		//adiciona a bola na mensagem
		Element ball = message.createElement("ball");
		packet.appendChild(ball);
		
		docbuilder.addElement(message, ball, "speed", Float.toString(match.ball.getSpeed()));
		docbuilder.addElement(message, ball, "xpos", Float.toString(match.ball.getXpos()));
		docbuilder.addElement(message, ball, "ypos", Float.toString(match.ball.getYpos()));
		docbuilder.addElement(message, ball, "dir", Float.toString(match.ball.getDir()));
		
		
		
		//adiciona a partida na mensagem
		Element match = message.createElement("match");
		packet.appendChild(match);
		
		docbuilder.addElement(message, match, "paused", String.valueOf(ServidorFake.match.isPaused()));
		
		return packet.toString();
	}
	
	public String sendMessage(String msg) {
		return Connection.Receiver.receive(msg);
	}
	
	private class DocBuilder{
		
		Element addElement(Document doc, Element parent, String tag, String value) {
			Element element = doc.createElement(tag);
			element.appendText(value);
			parent.appendChild(element);
			return element;
		}
		
	}
}
