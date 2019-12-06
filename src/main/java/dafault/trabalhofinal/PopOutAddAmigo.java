package dafault.trabalhofinal;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.rabbitmq.http.client.Client;
import com.rabbitmq.http.client.domain.QueueInfo;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
//import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import javax.swing.JButton;

public class PopOutAddAmigo extends JFrame {

	private JPanel contentPane;
	private JanelaChat JanelaChat;
	private ArrayList<String> usuarios = new ArrayList<String>(); 
	private JButton CriarBotaoUsuarios(final String nomeBotao) {
		JButton btnNewButton = new JButton(nomeBotao);
		btnNewButton.setAlignmentX(CENTER_ALIGNMENT);
		btnNewButton.setPreferredSize(new Dimension(400, 25));
		btnNewButton.setMaximumSize(new Dimension(400, 25));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(JanelaChat.amigos.contains(nomeBotao));
				if(!JanelaChat.amigos.contains(nomeBotao)) {
					//JButton novoAmigo=JanelaChat.CriaBotaoDeAmigo(nomeBotao);
					JanelaChat.AddAmigoNaLista(nomeBotao);
					JanelaChat.AttAmigos();
					/*ArrayList<String>historico=new ArrayList<String>();
					JanelaChat.historico.add(historico);*/
					//JanelaChat.AttAmigos();
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
	public void AddAllUsers (List<QueueInfo> lista){
		for(int i=0;i<lista.size();i++) {
			String name=lista.get(i).getName();
			if(JanelaChat.meuNome.compareTo(name)!=0) {
				usuarios.add(name);
			}
			
		}
		Collections.sort(usuarios);
	}
	
	public void CriaBotesSemNomesQueOUserJaTem() {
		for (int i=0;i<usuarios.size();i++) {
			boolean achou=false;
			for(int j=0;j<JanelaChat.amigos.size();j++) {
				if(JanelaChat.amigos.get(j).GetButton().getText().compareTo(usuarios.get(i))==0) {
					achou=true;
				}
			}
			if(achou==false) {
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
		List<QueueInfo> nova=JanelaChat.cliente.getQueues();		
		AddAllUsers(nova);
		
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
