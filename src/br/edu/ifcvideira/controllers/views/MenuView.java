package br.edu.ifcvideira.controllers.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.edu.ifcvideira.beans.Login;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class MenuView extends JFrame {

	private JPanel contentPane;
	JLabel lbOlaNomeUsuario = new JLabel("Ol\u00E1");
	
	FornecedorView fv = new FornecedorView();
	ProdutoView pv = new ProdutoView();
	ClienteView cv = new ClienteView();
	UsuarioView uv = new UsuarioView();
	AquisicaoView av = new AquisicaoView();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuView frame = new MenuView();
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
	public MenuView() {
		setResizable(false);
		setTitle("Menu");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuView.class.getResource("/br/edu/ifcvideira/imgs/icone1 laranja.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				lbOlaNomeUsuario.setText(""+Login.getUsuario().getNome()+" ("+Login.getUsuario().getIdUs()+")");
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 515, 458);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setLocationRelativeTo(null);
		
		JButton btFornecedores = new JButton("Fornecedores");
		btFornecedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fv.setVisible(true);
			}
		});
		
		JLabel lblSair = new JLabel("Deslogar");
		lblSair.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				LoginView lv = new LoginView();
				lv.setVisible(true);
				dispose();
			}
		});
		lblSair.setForeground(Color.BLACK);
		lblSair.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSair.setBounds(433, 35, 62, 14);
		contentPane.add(lblSair);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 118, 479, 5);
		contentPane.add(separator_1);
		btFornecedores.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btFornecedores.setBounds(83, 152, 130, 39);
		contentPane.add(btFornecedores);
		
		JButton btUsuarios = new JButton("Usu\u00E1rios");
		btUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uv.setVisible(true);
			}
		});
		btUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btUsuarios.setBounds(306, 227, 130, 39);
		contentPane.add(btUsuarios);
		
		JButton btClientes = new JButton("Clientes");
		btClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cv.setVisible(true);
			}
		});
		btClientes.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btClientes.setBounds(306, 152, 130, 39);
		contentPane.add(btClientes);
		
		JButton btProdutos = new JButton("Produtos");
		btProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pv.setVisible(true);
			}
		});
		btProdutos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btProdutos.setBounds(83, 227, 130, 39);
		contentPane.add(btProdutos);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(71, 283, 377, 2);
		contentPane.add(separator);
		
		JButton btAquisicoes = new JButton("Aquisi\u00E7\u00F5es");
		btAquisicoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				av.setVisible(true);
			}
		});
		btAquisicoes.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btAquisicoes.setBounds(83, 313, 130, 39);
		contentPane.add(btAquisicoes);
		
		JButton btVendas = new JButton("Vendas");
		btVendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VendaView vnView = new VendaView();
				vnView.setVisible(true);
			}
		});
		btVendas.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btVendas.setBounds(306, 313, 130, 39);
		contentPane.add(btVendas);
		lbOlaNomeUsuario.setForeground(SystemColor.textText);
		lbOlaNomeUsuario.setFont(new Font("Dialog", Font.BOLD, 16));
		lbOlaNomeUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		

		lbOlaNomeUsuario.setBounds(276, 8, 219, 28);
		contentPane.add(lbOlaNomeUsuario);
		
		JButton button = new JButton("Sair");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		button.setBackground(SystemColor.inactiveCaptionBorder);
		button.setBounds(192, 391, 130, 28);
		contentPane.add(button);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MenuView.class.getResource("/br/edu/ifcvideira/imgs/Horizontal.png")));
		lblNewLabel.setBounds(3, -25, 319, 278);
		contentPane.add(lblNewLabel);

	}
}
