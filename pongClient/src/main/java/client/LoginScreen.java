package client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginScreen extends JPanel {
	
	private static final long serialVersionUID = 1L;
	ClientWindow frame;
	private JTextField textField;
	
	public LoginScreen(ClientWindow frame) {
		this.frame = frame;
		setPreferredSize(new Dimension(ClientWindow.WINDOW_WIDTH, ClientWindow.WINDOW_HEIGHT));
		setBackground(new Color(0,0,0));
		setLayout(null);
		addStuff();
		
	}
	
	private void startGame() {
		if(textField.getText().equals(null) || textField.getText().length() == 0) {
			textField.setText("Sem-graça");
		}
		frame.remove(this);
		frame.revalidate();
		new Thread(()-> frame.newMatch(textField.getText())).start();
		
	}
	
	
	
	
	
	private void addStuff(){
		JLabel lblNewLabel = new JLabel("PONGIO");
		lblNewLabel.setFont(new Font("Candara Light", Font.PLAIN, 60));
		lblNewLabel.setBounds(396, 184, 208, 74);
		lblNewLabel.setBounds(ClientWindow.WINDOW_WIDTH/2 - lblNewLabel.getWidth()/2, ClientWindow.WINDOW_HEIGHT/3 - lblNewLabel.getHeight()/2, 208, 74);
		lblNewLabel.setForeground(Color.WHITE);
		add(lblNewLabel);
		
		textField = new JTextField();
		textField.setForeground(Color.BLACK);
		textField.setBounds(457, 245, 86, 20);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblInsiraSeuNome = new JLabel("INSIRA SEU NOME OU PEREÇA");
		lblInsiraSeuNome.setForeground(Color.WHITE);
		lblInsiraSeuNome.setFont(new Font("Candara Light", Font.PLAIN, 10));
		lblInsiraSeuNome.setBounds(436, 214, 129, 20);
		add(lblInsiraSeuNome);
		
		JButton btnNewButton = new JButton("INICIAR NOVO JOGO");
		btnNewButton.setFocusPainted(true);
		btnNewButton.setFocusable(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGame();
			}
		});
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		btnNewButton.setBorder(new LineBorder(Color.WHITE, 2, true));
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setFont(new Font("Candara Light", Font.BOLD | Font.ITALIC, 35));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBounds(324, 298, 352, 74);
		add(btnNewButton);
		
	}
}
