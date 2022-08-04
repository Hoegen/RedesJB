package amain;
import java.util.ArrayList;

import javax.swing.JProgressBar;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgcodecs.Imgcodecs;

public class Codificador {
	static final int TEXT_MAX = 225;
	
	//teste

	public static String readFromImage(String path, JProgressBar progressBar) {
		path = relativizePath(path);
		
		System.out.println("Reading text from path " + path);
		
		//lê a imagem
		nu.pattern.OpenCV.loadLocally();
		Mat image = Imgcodecs.imread(path, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if(image.empty()) {
			System.out.println("ARQUIVO CORROMPIDO OU NÃO ENCONTRADO");
			return "ARQUIVO CORROMPIDO OU NÃO ENCONTRADO";
		}
		
		
		//pega o tamanho da imagem
		int xsize = image.width();
		int ysize = image.height();
		
		System.out.println("xsize" + xsize);
		
		//configura barra de progresso:
		progressBar.setMaximum((8*TEXT_MAX)/3 );
		progressBar.setValue(0);
		progressBar.setVisible(true);
		
		int zerosseguidos = 0;
		double[] pixel = new double[3];
		ArrayList<Boolean> binariolido = new ArrayList<Boolean>();
		
		for(int y = 0; y < ysize; y++) {
			for(int x = 0; x < xsize; x++) {
				pixel = image.get(y, x);
				
				//atualiza barra de progresso e sai do método se ele passar do limite de caracteres.
				progressBar.setValue(progressBar.getValue() + 1);
				if(progressBar.getMaximum() <= progressBar.getValue()) {
					return binToText(binariolido);
				}
				
				for(int i = 0; i < 3; i++) {
					binariolido.add(pixel[i]%2 == 1);
					
					if(binariolido.get(binariolido.size()-1)) {
						zerosseguidos = 0;
					}else {
						zerosseguidos++;
						if(zerosseguidos > 14) {
							progressBar.setValue(progressBar.getMaximum());
							break;
						}
					}
				}
				if(zerosseguidos > 14) break;
			}
			if(zerosseguidos > 8) break;
		}
		
		progressBar.setValue(progressBar.getMaximum());
		System.out.println("Finished reading");
		return binToText(binariolido);
	}

	public static void writeOnImage(String path, String text, JProgressBar progressBar) {
		if(text.length() > TEXT_MAX) text = text.substring(0, TEXT_MAX);
		
		path = relativizePath(path);
		
		System.out.println("Writing text: " + text);
		System.out.println("	 on path: " + path);
		
		//lê a imagem
		nu.pattern.OpenCV.loadLocally();
		Mat image = Imgcodecs.imread(path, Highgui.CV_LOAD_IMAGE_COLOR);
		
		if(image.empty()) {
			System.out.println("AAAAAAAAAAAAAAAAAAAA");
		}
		
		//pega o tamanho da imagem
		int xsize = image.width();
		int ysize = image.height();

		//configura barra de progresso:
		progressBar.setValue(0);
		progressBar.setVisible(true);
		
		//conta quantas vezes o pixel só está armazenando zerosseguidos
		int zerosseguidos = 0;
		
		//declara a variável onde será armazenada o texto em binário a ser 
		boolean[] bintext = textToBin(text + (char)0 + (char)0 + (char)0);
		
		//itera cada pixel da imagem, primeiro nas linhas e depois nas colunas
		
		for(int y = 0; y < ysize; y++) {
			for(int x = 0; x < xsize; x++) {
				//atualiza barra de progresso.
				progressBar.setValue(progressBar.getValue() + 1);
				
				//este método retorna verdadeiro se ele tiver escrito somente bits 0
				if(putTextIntoPixel(x, y, bintext, image)) {
					zerosseguidos = zerosseguidos + 3;
					if(zerosseguidos > 14) break; //Por que catorze? Para finalizar a mensagem, o programa adiciona três bytes nulos. O pior caso seria se tivéssemos um byte 10000000 seguido de um byte 00000001, somando 14 zeros seguidos. Ao ler 15 bits zero seguidos, é sempre certo que há um byte nulo.
				}else {
					zerosseguidos = 0;
				}
			}
			if(zerosseguidos > 14) break;
		}
		
		progressBar.setValue(progressBar.getMaximum());
		Imgcodecs.imwrite(path, image);
		System.out.println("Finished writing");
	}

	private static String binToText(ArrayList<Boolean> bintext) {
		String stringedbintext = "";
		String stringedtext = "";

		//converte o array de booleanos para sua representação em string
		for(int i = 0; i < bintext.size(); i++) {
			stringedbintext = stringedbintext + (bintext.get(i)? "1" : "0");
		}
		
		// Converte de binário para texto em blocos de 8 bits para caracteres, até não sobrar mais um bloco de 8 bits (isto seria muito mais eficiente em C) 
		for(int i = 0; i + 8 < bintext.size(); i = i+8) {
			stringedtext = stringedtext + (char)Integer.parseInt(stringedbintext.substring(i, i+8), 2);
		}
		System.out.println(stringedbintext);
		System.out.println(stringedtext);
		return stringedtext;
	}

	private static boolean putTextIntoPixel(int x, int y, boolean[] binaryText, Mat image) {
		boolean allzero = true;
		
		try {
		double[] pixel = image.get(y, x);
		int numberofpixel = x + y*image.width();
		
		
		//se for par e precisar ser ímpar, soma um. Se for ímpar e precisar ser par, subtrai um
		for(int i = 0; i < 3; i++) {
			if(3*numberofpixel + i >= binaryText.length) break;
			
			if(binaryText[3*numberofpixel + i]) {
				allzero = false;
				if(pixel[i] % 2 == 0) {
					pixel[i] ++;
				}
			}else {
				if(pixel[i] % 2 == 1) {
					pixel[i] --;
				}
			}
		}
		
		image.put(y, x, pixel);
		
		}catch (Exception e) {}
		
		return allzero;
	}

	private static boolean[] textToBin(String text) {
		boolean[] to_tradition = new boolean[text.length() * 8];
		
		//Variável que armazenará representação em string do valor binário de um char
		String stringedbinarychar;
		
		for(int i = 0; i < text.length(); i++) {
			stringedbinarychar = String.format("%8s", Integer.toBinaryString(text.charAt(i))).replace(" ", "0");
			for(int j = 0; j < 8; j++) {
				to_tradition[i * 8 + j] = stringedbinarychar.charAt(j) == '1';
			}
			if(i > text.length()) break;
		}
		
		return to_tradition;
	}
	
	static void printPixel(int x, int y, Mat image){
		for(int i = 0; i < 3; i++) {
			System.out.print((int)image.get(y, x)[i] + " ");
		}
	}

	static void printPixel(double[] pixel){
		for(int i = 0; i < 3; i++) {
			System.out.print((int)pixel[i] + " ");
		}
	}
	
	private static String relativizePath(String path){
		System.out.println(System.getProperty("user.dir"));
		return path;
		
		
//		String executionPath = System.getProperty("user.dir");
//		
//		return "";
	}

}