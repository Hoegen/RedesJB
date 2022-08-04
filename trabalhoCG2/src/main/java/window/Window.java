package window;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import amain.Codificador;

public class Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String path;

	private JPanel contentPane;
	private static JTextArea textArea;
	private static JProgressBar progressBar;
	private static JButton btnEscrever;
	private static JButton btnLer;
	private static JEditorPane textoLido;
	private static JEditorPane textoEscrito;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Window() {
		setTitle("ENCRIPTADOR DE MENSAGENS");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 539, 385);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setForeground(Color.BLACK);
		textArea.setBackground(UIManager.getColor("Button.light"));
		textArea.setEnabled(false);
		textArea.setBounds(10, 21, 276, 20);
		contentPane.add(textArea);
		
		JButton btnNewButton = new JButton("Escolha um arquivo");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				path = openFilechooser();
				textArea.setText(path);
				
//				JFileChooser chooser = new JFileChooser();
//				chooser.showOpenDialog(chooser);
//				chooser.setVisible(true);
//				path = chooser.getSelectedFile().toString();
//				textArea.setText(path);
			}
		});
		btnNewButton.setBounds(309, 20, 201, 23);
		contentPane.add(btnNewButton);
		
		btnLer = new JButton("LER");
		btnLer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new Thread(()-> textoLido.setText(Codificador.readFromImage(path, progressBar))).start();
				}catch(Exception f) {
					new Erro();
					f.printStackTrace();
				}
			}
		});
		btnLer.setBounds(10, 52, 96, 23);
		contentPane.add(btnLer);
		
		btnEscrever = new JButton("ESCREVER");
		btnEscrever.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new Thread(()-> Codificador.writeOnImage(path, textoEscrito.getText(), progressBar) ).start();
				}catch(Exception f) {
					new Erro();
					f.printStackTrace();
				}
			}
		});
		btnEscrever.setBounds(128, 52, 96, 23);
		contentPane.add(btnEscrever);
		
		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		progressBar.setBounds(234, 53, 276, 23);
		contentPane.add(progressBar);
		
		JLabel lblNewLabel = new JLabel("Texto lido");
		lblNewLabel.setBounds(10, 82, 89, 14);
		contentPane.add(lblNewLabel);
		
		textoLido = new JEditorPane();
		textoLido.setBackground(UIManager.getColor("Button.light"));
		textoLido.setEnabled(false);
		textoLido.setEditable(false);
		textoLido.setBounds(10, 107, 500, 62);
		contentPane.add(textoLido);
		
		textoEscrito = new JEditorPane();
		textoEscrito.setBounds(10, 218, 500, 62);
		contentPane.add(textoEscrito);
		
		JLabel lblTextoEscrito = new JLabel("Texto a escrever");
		lblTextoEscrito.setBounds(10, 193, 113, 14);
		contentPane.add(lblTextoEscrito);
	}
	
	public String openFilechooser() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Escolha a imagem desejada");

		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.addChoosableFileFilter(new FileNameExtensionFilter("Imagens sem compressão", "png", "bmp"));
		int returnValue = jfc.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			return jfc.getSelectedFile().toString();
		}
		return null;
	}
}
