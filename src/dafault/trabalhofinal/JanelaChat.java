package dafault.trabalhofinal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JanelaChat extends JFrame {

	private JPanel contentPane;
	private static String meuNome;
	public static JanelaChat frame;
	public static ArrayList<String> amigos = new ArrayList<String>(); 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new JanelaChat();
					frame.setVisible(true);
					meuNome="Levi";
					//amigos.add("João");
					//meu nome
					frame.setTitle("Levi");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void MeSendTextToChat() {
		String texto=textArea_1.getText();
		if(texto.compareTo("")!=0) {
			textArea.append(meuNome+": "+textArea_1.getText()+"\n");
			textArea_1.setText("");
		}		
	}
	
	private void OtherSendTextToChat(String message) {
		textArea.append(message+"\n");
		//textArea_1.setText("");
		
	}
	

	public JButton CriaBotaoDeAmigo(String nomeAmigo) {
		JButton btnNewButton = new JButton(nomeAmigo);
		btnNewButton.setAlignmentX(CENTER_ALIGNMENT);
		btnNewButton.setPreferredSize(new Dimension(170, 25));
		btnNewButton.setMaximumSize(new Dimension(170, 25));
		//evento de pegar o historico do chat do cara
		return btnNewButton;
	}
	
	public void AddAmigoNoPanel(String nomeAmigo) {
		panel.add(CriaBotaoDeAmigo(nomeAmigo));
		
		//panel.repaint();
	}
	public void AttAmigos() {
		panel.removeAll();
		Collections.sort(amigos);
		panel.add(lblNewLabel);
		for(int i=0;i<amigos.size();i++) {
			AddAmigoNoPanel(amigos.get(i));
		}
		setContentPane(contentPane);
	}
	
	
	/**
	 * Create the frame.
	 */
	public JanelaChat() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 38, 414, 178);
		contentPane.add(scrollPane);
		panel = new JPanel();
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		scrollPane_1 = new JScrollPane(panel);
		scrollPane_1.setBounds(434, 37, 190, 179);
		contentPane.add(scrollPane_1);
		
		panel.setBounds(434, 38, 190, 178);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		//contentPane.add(panel);
		

		//panel.setBounds(432, 38, 202, 178);
		//contentPane.add(panel);
		//panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		JLabel lblNewLabel_1 = new JLabel("Chat");
		lblNewLabel_1.setAlignmentX(CENTER_ALIGNMENT);
		lblNewLabel_1.setBounds(199, 15, 29, 14);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel = new JLabel("Amigos");
		//lblNewLabel.setBounds(264, 225, 57, 39);
		lblNewLabel.setAlignmentX(CENTER_ALIGNMENT);
		//contentPane.add(lblNewLabel);
		panel.add(lblNewLabel);
		
		//JButton btnNewButton2=CriaBotaoDeAmigo("Levi");
		//JButton btnNewButton3=CriaBotaoDeAmigo("Felipe");
		
		//panel.add(btnNewButton2);
		//panel.add(btnNewButton2);
		//panel.add(btnNewButton3);
		//AddAmigoNoPanel("João");
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MeSendTextToChat();
			}
		});
		btnEnviar.setBounds(434, 306, 190, 33);
		contentPane.add(btnEnviar);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 306, 414, 33);
		contentPane.add(scrollPane_2);
		
		textArea_1 = new JTextArea();
		textArea_1.setBounds(10, 306, 414, 33);
		//contentPane.add(textArea_1);
		scrollPane_2.setViewportView(textArea_1);
		
		JButton btnRegistrarAmigo = new JButton("Add novo Amigo");
		btnRegistrarAmigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PopOutAddAmigo novo=new PopOutAddAmigo(frame);
				novo.setVisible(true);
			}
		});
		btnRegistrarAmigo.setBounds(434, 11, 190, 23);
		contentPane.add(btnRegistrarAmigo);
		
		
		
	}
	private JTextArea textArea_1;
	private JTextArea textArea;
	private JPanel panel;
	private JScrollPane scrollPane_1;
	private JLabel lblNewLabel;
}
