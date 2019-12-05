package dafault.trabalhofinal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

public class Consumer {
	private static Connection connection;
	private static Channel channel;
	private static ConnectionFactory factory;
	public static ArrayList<String>mensagensRecebidas;
	private static JanelaChat janelaChat;
	private static String nomeFila;
	public static void CriaConexao(JanelaChat chat,String nomeDaFila) throws IOException, TimeoutException {
		nomeFila=nomeDaFila;
		janelaChat=chat;
		mensagensRecebidas=new ArrayList<String>();
		factory=new ConnectionFactory();
		connection= factory.newConnection();
		channel=connection.createChannel();
		channel.queueDeclare(nomeDaFila,false,false,false,null);
		
	}
	public static void FechaConexao() throws IOException, TimeoutException{
		channel.close();
		connection.close();
	}
	public static void ReiniciaConexao() throws IOException, TimeoutException {
		connection= factory.newConnection();
		channel=connection.createChannel();		
	}
	
	public static void RecebeMensagem() throws IOException, TimeoutException {
		channel.basicConsume(nomeFila, true,new DeliverCallback() {
			
			public void handle(String consumerTag, Delivery message) throws IOException {
				// TODO Auto-generated method stub
				String m=new String(message.getBody(),"UTF-8");
				janelaChat.AttHistoricoDeUmaPessoa(m, nomeFila);
				janelaChat.AttChat();
				//salva no histórico do cara da fila
				//mensagensRecebidas.add(m);
				System.out.println("Mensagem recebida: "+m);
				
			}
		}, new CancelCallback() {
			
			public void handle(String consumerTag) throws IOException {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	/*public static void main(String[] args) throws IOException, TimeoutException {
		CriaConexao("hello-word");
		RecebeMensagem("hello-word");
	}*/
}
