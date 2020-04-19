package br.edu.ifcvideira.controllers.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.edu.ifcvideira.DAOs.LoginDao;
import br.edu.ifcvideira.beans.Login;
import br.edu.ifcvideira.beans.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;




public class LoginView extends JFrame {

	private JPanel contentPane;
	private JTextField tfUsuario;
	private JPasswordField tfSenha;
	

	LoginDao lgDao = new LoginDao();
	MenuView mv = new MenuView();
	
	/*
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView frame = new LoginView();
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
	
	public LoginView() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginView.class.getResource("/br/edu/ifcvideira/imgs/icone1 laranja.png")));
		setTitle("LOGIN");
		setBackground(new Color(204,115,48));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 613, 408);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setLocationRelativeTo(null);
		
		JLabel lbUsuario = new JLabel("USU\u00C1RIO:");
		lbUsuario.setForeground(SystemColor.textText);
		lbUsuario.setFont(new Font("Sitka Display", Font.BOLD, 17));
		
		
		lbUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		lbUsuario.setBounds(124, 225, 113, 26);
		contentPane.add(lbUsuario);
		
		tfUsuario = new JTextField();
		tfUsuario.setBackground(SystemColor.window);
		tfUsuario.setBounds(247, 229, 181, 20);
		contentPane.add(tfUsuario);
		tfUsuario.setColumns(10);
		
		JLabel lbSenha = new JLabel("SENHA:");
		lbSenha.setHorizontalAlignment(SwingConstants.RIGHT);
		lbSenha.setForeground(SystemColor.textText);
		lbSenha.setFont(new Font("Sitka Display", Font.BOLD, 17));
		lbSenha.setBackground(Color.WHITE);
		lbSenha.setBounds(153, 270, 84, 26);
		contentPane.add(lbSenha);
		
		tfSenha = new JPasswordField();
		tfSenha.setBackground(SystemColor.text);
		tfSenha.setBounds(247, 274, 181, 20);
		contentPane.add(tfSenha);
		
		JButton btnLogar = new JButton("ENTRAR");
		btnLogar.setBackground(SystemColor.inactiveCaptionBorder);
		btnLogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(tfUsuario.getText().isEmpty() || tfSenha.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Preencha todos os campos");
				} else {
					
					Login.getUsuario().setLoginUs(tfUsuario.getText());
					Login.getUsuario().setSenhaUs(tfSenha.getText());
					try {
						if (lgDao.recebeDados()) {
							mv.setVisible(true);
							dispose();
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
		});
		btnLogar.setFont(new Font("Lucida Sans", Font.BOLD, 13));
		btnLogar.setBounds(257, 320, 97, 31);
		contentPane.add(btnLogar);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(LoginView.class.getResource("/br/edu/ifcvideira/imgs/Logo 01.png")));
		lblNewLabel.setBounds(124, 11, 372, 203);
		contentPane.add(lblNewLabel);
	}
}
