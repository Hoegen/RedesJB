package server;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import client.Connection;
import main.Player;

public class ServidorFake {
	public static main.Match match;
	private DocBuilder docbuilder;
	
	
	public ServidorFake() {
		match = new main.Match();
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
			server.newMessage();
		}
	}
	
	public Document newMessage() {
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
		Element ball = message.createElement("Ball");
		packet.appendChild(ball);
		
		docbuilder.addElement(message, ball, "speed", Float.toString(match.ball.getSpeed()));
		docbuilder.addElement(message, ball, "xpos", Float.toString(match.ball.getXpos()));
		docbuilder.addElement(message, ball, "ypos", Float.toString(match.ball.getYpos()));
		docbuilder.addElement(message, ball, "dir", Float.toString(match.ball.getDir()));
		
		System.out.println("______________________________________________\n"
						+ "               Mensagem enviada pelo servidor:");
		System.out.println(packet.toString());
		
		Connection.Receiver.receive(packet.toString());
		
		return message;
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
