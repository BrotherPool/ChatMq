package dafault.trabalhofinal;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JanelaEscolheNome extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaEscolheNome frame = new JanelaEscolheNome();
					frame.setResizable(false);
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
	public JanelaEscolheNome() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 446, 76);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDigiteSeuNome = new JLabel("Digite seu nome:");
		lblDigiteSeuNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblDigiteSeuNome.setBounds(10, 11, 99, 14);
		contentPane.add(lblDigiteSeuNome);
		
		textField = new JTextField();
		textField.setBounds(120, 8, 198, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnIr = new JButton("Ir");
		btnIr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(textField.getText());
				String entrada=textField.getText();
				if(entrada.compareTo("")!=0) {
					//cadastrar fila
					dispose();
					JanelaChat.main(entrada);
					//frame.setVisible(true);
					
				}
			}
		});
		btnIr.setBounds(328, 8, 89, 20);
		contentPane.add(btnIr);
	}
}
