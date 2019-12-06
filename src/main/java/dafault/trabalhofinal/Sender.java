package dafault.trabalhofinal;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {
	private static Connection connection;
	private static Channel channel;
	private static ConnectionFactory factory;
	private static String nomeDaFila;
	public static void CriaConexao(String fila) throws IOException, TimeoutException {
		nomeDaFila=fila;
		factory=new ConnectionFactory();
		connection= factory.newConnection();
		channel=connection.createChannel();
		channel.queueDeclare(nomeDaFila,false,false,false,null);
		
	}
	public static void FechaConexao() throws IOException, TimeoutException{
		channel.close();
		connection.close();
	}
	public static void MandaMensagem(String message) throws IOException, TimeoutException{
		//String enviar=message+" Mandada em: "+LocalDateTime.now();
		String enviar=message+"\n";
		channel.basicPublish("", nomeDaFila, false, null,enviar.getBytes("UTF-8"));
		System.out.println("Mandou a mensagem");
	}
	public static void main(String[] args) throws IOException, TimeoutException{
		String nome="levi";
		//mandei pro asd
		CriaConexao(nome);
		//FechaConexao();
		//ReiniciaConexao();
		//MandaMensagem("Fábio"+": "+"Olá");
		MandaMensagem("asd"+": "+"teste");
		FechaConexao();
		//mandei pro coragem
		//CriaConexao("Coragem");
		//MandaMensagem("Fernando"+": "+"Olá");
		//FechaConexao();
			
	}
}
