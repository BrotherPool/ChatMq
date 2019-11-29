package dafault.trabalhofinal;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class PopOutAddAmigo extends JFrame {

	private JPanel contentPane;
	private JanelaChat JanelaChat;
	private ArrayList<String> usuarios = new ArrayList<String>(); 
	private JButton CriarBotaoUsuarios(String nomeBotao) {
		JButton btnNewButton = new JButton(nomeBotao);
		btnNewButton.setAlignmentX(CENTER_ALIGNMENT);
		btnNewButton.setPreferredSize(new Dimension(400, 25));
		btnNewButton.setMaximumSize(new Dimension(400, 25));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(JanelaChat.amigos.contains(nomeBotao));
				if(!JanelaChat.amigos.contains(nomeBotao)) {
					JanelaChat.amigos.add(nomeBotao);
					JanelaChat.AttAmigos();
				}
				dispose();
			}
		});
		return btnNewButton;
	}
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PopOutAddAmigo frame = new PopOutAddAmigo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/
	public void AddAllUsers (){
		usuarios.add("João");
		usuarios.add("Fernando");
		usuarios.add("José");
		usuarios.add("Pedro");
		usuarios.add("Fábio");
		usuarios.add("Levi");
		usuarios.add("Rebeca");
		usuarios.add("Josimar");
		usuarios.add("Josias");
		usuarios.add("Rafael");
		usuarios.add("Marcia");
		usuarios.add("Judas");
		usuarios.add("Moacir");
		usuarios.add("Moriel");
		usuarios.add("Coragem");
		usuarios.add("Heitor");
		usuarios.add("Isaias");
		usuarios.add("Tamar");
		Collections.sort(usuarios);
	}
	
	public void CriaBotesSemNomesQueOUserJaTem() {
		for (int i=0;i<usuarios.size();i++) {
			//textArea.append(usuarios.get(i)+"\n");
			if(!JanelaChat.amigos.contains(usuarios.get(i))) {
				panel.add(CriarBotaoUsuarios(usuarios.get(i)));
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public PopOutAddAmigo(JanelaChat JanelaChat) {
		//pegar todos os usuarios pelo rmi
		this.JanelaChat=JanelaChat;
		AddAllUsers();
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("Todos os usu\u00E1rios");
		//lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(getMaximumSize().width, 200));
		scrollPane.setMaximumSize(new Dimension(getMaximumSize().width, 200));
		
		JLabel lblCliqueNoBoto = new JLabel("Clique no bot\u00E3o para adicionar o usu\u00E1rio");
		//lblCliqueNoBoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblCliqueNoBoto.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblCliqueNoBoto);
		//contentPane.add(scrollPane);
		panel = new JPanel();
		JScrollPane scrollPane_1 = new JScrollPane(panel);
		//scrollPane_1.setBounds(434, 37, 190, 179);
		contentPane.add(scrollPane_1);
		//panel.setBounds(434, 38, 190, 178);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		//contentPane.add(teste);
		CriaBotesSemNomesQueOUserJaTem();
	}
	private JPanel panel;
}
