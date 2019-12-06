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
	private static String minhaTag;
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
		//connection.close();
		channel.close();
		//channel.basicCancel(minhaTag);
		connection.close();
	}
	public static void ReiniciaConexao() throws IOException, TimeoutException {
		connection= factory.newConnection();
		channel=connection.createChannel();	
	}
	
	public static void ComecaServicoDeReceberMensagem() throws IOException, TimeoutException {
		//nome da fila é quem tá recebendo
		channel.basicConsume(nomeFila, true,new DeliverCallback() {
			
			public void handle(String consumerTag, Delivery message) throws IOException {
				// TODO Auto-generated method stub
				minhaTag=consumerTag;
				String mensagem=new String(message.getBody(),"UTF-8");
				String[] output = mensagem.split(":");
				//mensagem tem que ser dividida pra ver quem mandou
				janelaChat.AttHistoricoDeUmaPessoa(mensagem, output[0]);
				janelaChat.AttChat(output[0]);
				//salva no histórico do cara da fila
				//mensagensRecebidas.add(m);
				System.out.println("Mensagem recebida: "+mensagem);
				//System.out.println("Teste do split-> "+output[0]+" "+output[1]);
				
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
