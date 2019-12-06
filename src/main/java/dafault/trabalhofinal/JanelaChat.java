package dafault.trabalhofinal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.rabbitmq.http.client.Client;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeoutException;

import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class JanelaChat extends JFrame {

	private JPanel contentPane;
	public static String meuNome;
	private static Consumer consumer;
	private static Sender sender;
	public static JanelaChat frame;
	public static Client cliente=null;
	public static BotaoComHistorico comQuemEstouConversando;
	public static ArrayList<BotaoComHistorico> amigos = new ArrayList<BotaoComHistorico>(); 

	/**
	 * Launch the application.
	 */
	public static void main(final String nome) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new JanelaChat(nome);
					frame.setVisible(true);
					cliente= new Client("http://127.0.0.1:15672/api/", "guest", "guest");
					meuNome=nome;
					consumer=new Consumer();
					consumer.CriaConexao(frame, meuNome);
					consumer.ComecaServicoDeReceberMensagem();
					frame.setResizable(false);
					frame.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent we) {
							cliente.deleteQueue("/", meuNome);
						  }
					});
					//amigos.add("João");
					//meu nome
					frame.setTitle(nome);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void MeSendTextToChat() throws IOException, TimeoutException {
		String texto=textArea_1.getText();
		if(texto.compareTo("")!=0) {
			String mensagem=meuNome+": "+textArea_1.getText();
			AttHistorico(mensagem+"\n");
			AttChat(comQuemEstouConversando.GetButton().getText());
			sender.MandaMensagem(mensagem);
			//textArea.append(meuNome+": "+textArea_1.getText()+"\n");
			textArea_1.setText("");
		}		
	}
	
	private void OtherSendTextToChat(String message) {
		//a mensagem que o cara vai mandar tem que ser no formato meuNome+": "+mensagem+"\n"
		AttHistorico(message);
		//textArea.append(message+"\n");
		//textArea_1.setText("");
		
	}
	

	public BotaoComHistorico CriaBotaoDeAmigo(String nomeAmigo) {
		final BotaoComHistorico novoBotao=new BotaoComHistorico(nomeAmigo);
		novoBotao.GetButton().setAlignmentX(CENTER_ALIGNMENT);
		novoBotao.GetButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				comQuemEstouConversando=novoBotao;
				sender=new Sender();
				try {
					sender.CriaConexao(comQuemEstouConversando.GetButton().getText());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				lblNewLabel_1.setText("Chat com "+comQuemEstouConversando.GetButton().getText());
				//System.out.println(comQuemEstouConversando);
				AttChat(comQuemEstouConversando.GetButton().getText());
			}
		});
		if(comQuemEstouConversando==null) {
			comQuemEstouConversando=novoBotao;
		}
		
		//JButton btnNewButton = new JButton(nomeAmigo);
		//btnNewButton.setAlignmentX(CENTER_ALIGNMENT);
		//btnNewButton.setPreferredSize(new Dimension(170, 25));
		//btnNewButton.setMaximumSize(new Dimension(170, 25));
		//evento de pegar o historico do chat do cara
		
		
		return novoBotao;
	}
	
	public void AddAmigoNaLista(String nomeAmigo) {
		BotaoComHistorico novoAmigo=CriaBotaoDeAmigo(nomeAmigo);
		amigos.add(novoAmigo);
		//panel.add(novoAmigo);
		//setContentPane(contentPane);
		
		//panel.repaint();
	}
	// tem que ter um attchat especifico
	public void AttChat(String quemMandou) {
		if(comQuemEstouConversando.GetButton().getText().compareTo(quemMandou)==0) {
			textArea.setText("");
			ArrayList<String>historico=comQuemEstouConversando.GetHistorico();
			for(int i=0;i<historico.size();i++) {
				textArea.append(historico.get(i));
			}
		}
		
	}
	public void AttAmigos() {
		panel.removeAll();
		ArrayList<String>nomeDosAmigos=new ArrayList<String>();
		for(int i=0;i<amigos.size();i++) {
			nomeDosAmigos.add(amigos.get(i).GetButton().getText());
		}
		Collections.sort(nomeDosAmigos);
		panel.add(lblNewLabel);
		for(int i=0;i<nomeDosAmigos.size();i++) {
			for(int j=0;j<amigos.size();j++) {
				if(nomeDosAmigos.get(i).compareTo(amigos.get(j).GetButton().getText())==0) {
					panel.add(amigos.get(j).GetButton());
				}
			}
			
		}
		
		setContentPane(contentPane);
	}
	public void AttHistorico(String mensagem) {
		comQuemEstouConversando.GetHistorico().add(mensagem);
	}
	public void AttHistoricoDeUmaPessoa(String mensagem,String quemEnviou) {
		int i=0;
		for(i=0;i<amigos.size();i++) {
			if(amigos.get(i).GetButton().getText().compareTo(quemEnviou)==0) {
				amigos.get(i).GetHistorico().add(mensagem);
				break;
			}
		}	
		System.out.println(amigos.get(i).GetHistorico());
	}
	/*public void AttAmigos() {
		panel.removeAll();
		Collections.sort(amigos);
		panel.add(lblNewLabel);
		for(int i=0;i<amigos.size();i++) {
			AddAmigoNoPanel(amigos.get(i));
		}
		
		setContentPane(contentPane);
	}*/
	
	
	/**
	 * Create the frame.
	 */
	public JanelaChat(String nome) {
		comQuemEstouConversando=null;
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
		lblNewLabel_1 = new JLabel("Chat");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setAlignmentX(CENTER_ALIGNMENT);
		lblNewLabel_1.setBounds(109, 15, 315, 14);
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
		btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comQuemEstouConversando!=null) {
					try {
						MeSendTextToChat();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TimeoutException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
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
		
		btnRegistrarAmigo = new JButton("Add novo Amigo");
		btnRegistrarAmigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PopOutAddAmigo novo=new PopOutAddAmigo(frame);
				novo.setVisible(true);
				novo.setResizable(false);
			}
		});
		btnRegistrarAmigo.setBounds(434, 11, 190, 23);
		contentPane.add(btnRegistrarAmigo);
		btnOnoff = new JButton("Off");
		btnOnoff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (btnOnoff.getText().compareTo("Off")==0) {
					btnOnoff.setText("On");
					//função de pegar e atualizar as mensagens
					/*if(comQuemEstouConversando!=null) {
						AttChat();
					}*/
					try {
						consumer.FechaConexao();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TimeoutException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					textArea_1.setEnabled(false);
					textArea.setEnabled(false);
					panel.setVisible(false);
					btnRegistrarAmigo.setEnabled(false);
					btnEnviar.setEnabled(false);
				}
				else if(btnOnoff.getText().compareTo("On")==0) {
					btnOnoff.setText("Off");
					try {
						consumer.ReiniciaConexao();
						consumer.ComecaServicoDeReceberMensagem();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TimeoutException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					textArea_1.setEnabled(true);
					textArea.setEnabled(true);
					panel.setVisible(true);
					btnRegistrarAmigo.setEnabled(true);
					btnEnviar.setEnabled(true);
				}
			}
		});
		btnOnoff.setBounds(10, 11, 89, 23);
		contentPane.add(btnOnoff);
		
		
		
	}
	private JTextArea textArea_1;
	private JTextArea textArea;
	private JPanel panel;
	private JScrollPane scrollPane_1;
	private JLabel lblNewLabel;
	private JButton btnOnoff;
	private JButton btnEnviar;
	private JButton btnRegistrarAmigo;
	private JLabel lblNewLabel_1;
}
