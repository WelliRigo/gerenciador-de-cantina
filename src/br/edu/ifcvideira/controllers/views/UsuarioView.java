package br.edu.ifcvideira.controllers.views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import br.edu.ifcvideira.DAOs.UsuarioDao;
import br.edu.ifcvideira.beans.Login;
import br.edu.ifcvideira.beans.Usuario;
import javax.swing.JPasswordField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;
import java.awt.Component;

public class UsuarioView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfNomeUs;
	private JTextField tfCpfUs;
	private JTextField tfTelefoneUs;
	private JTextField tfPNome;
	private JTextField tfPCodigo;
	private JTable table;

	private List<Object> usuario = new ArrayList<Object>();
	
	Usuario us = new Usuario();
	UsuarioDao usDao = new UsuarioDao();
	
	long time = System.currentTimeMillis();
	Timestamp timestamp = new Timestamp(time);
	
	private JTextField tfCodigoUs;
	private JTextField tfCelularUs;
	private JTextField tfRgUs;
	private JTextField tfLoginUs;
	private JPasswordField tfSenhaUs;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsuarioView frame = new UsuarioView();
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
	}

	public UsuarioView() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(UsuarioView.class.getResource("/br/edu/ifcvideira/imgs/icone1 laranja.png")));
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent arg0) {
				atualizarTabela();
				ordenarPorNome();
				limpar();
			}
		});
		setTitle("Usu\u00E1rios");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 568, 603);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setLocationRelativeTo(null);
		
		JLabel lbNome = new JLabel("Nome:");
		lbNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lbNome.setBounds(73, 11, 59, 20);
		contentPane.add(lbNome);
		
		tfNomeUs = new JTextField();
		tfNomeUs.setColumns(10);
		tfNomeUs.setBounds(142, 12, 315, 20);
		contentPane.add(tfNomeUs);
		
		JLabel lblCodigo = new JLabel("Codigo:");
		lblCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodigo.setBounds(73, 42, 59, 20);
		contentPane.add(lblCodigo);
		
		tfCodigoUs = new JTextField();
		tfCodigoUs.setEditable(false);
		tfCodigoUs.setColumns(10);
		tfCodigoUs.setBounds(142, 42, 147, 20);
		contentPane.add(tfCodigoUs);
		
		JLabel label_Rg = new JLabel("RG:");
		label_Rg.setHorizontalAlignment(SwingConstants.RIGHT);
		label_Rg.setBounds(73, 69, 59, 20);
		contentPane.add(label_Rg);
		
		tfRgUs = new JTextField();
		tfRgUs.setColumns(10);
		tfRgUs.setBounds(142, 70, 147, 20);
		contentPane.add(tfRgUs);
		
		JLabel label_Cpf = new JLabel("CPF:");
		label_Cpf.setHorizontalAlignment(SwingConstants.RIGHT);
		label_Cpf.setBounds(73, 98, 59, 20);
		contentPane.add(label_Cpf);
		
		tfCpfUs = new JTextField();
		tfCpfUs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				boolean teclaCerta = false;
				Character ch = e.getKeyChar();
                /* aceita números e ponto (.)*/
                if (Character.isDigit(e.getKeyChar())) {
                       teclaCerta = true;
                 }
                /* se não for um digito no jTextFied o evento é consumido. Nada acontece */
	            if (!teclaCerta) {
	                  e.consume();
	             }
			}
		});
		tfCpfUs.setColumns(10);
		tfCpfUs.setBounds(142, 98, 147, 20);
		contentPane.add(tfCpfUs);
		
		JLabel label_Tel;
		label_Tel = new JLabel("Telefone:");
		label_Tel.setHorizontalAlignment(SwingConstants.RIGHT);
		label_Tel.setBounds(73, 129, 59, 20);
		contentPane.add(label_Tel);
		
		tfTelefoneUs = new JTextField();
		tfTelefoneUs.setColumns(10);
		tfTelefoneUs.setBounds(142, 129, 147, 20);
		contentPane.add(tfTelefoneUs);
		
		JLabel lblCelular = new JLabel("Celular:");
		lblCelular.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCelular.setBounds(73, 156, 59, 20);
		contentPane.add(lblCelular);
		
		tfCelularUs = new JTextField();
		tfCelularUs.setColumns(10);
		tfCelularUs.setBounds(142, 156, 147, 20);
		contentPane.add(tfCelularUs);
		
		JButton btAlterar = new JButton("Alterar");
		btAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1){
					try {
						
						//atribuição dos valores dos campos para o objeto cliente
						us.setIdUs(Integer.parseInt(tfCodigoUs.getText()));
						us.setNome(tfNomeUs.getText());
						us.setRgUs(tfRgUs.getText());
						us.setCpf(tfCpfUs.getText());
						us.setTelefone(tfTelefoneUs.getText());
						us.setCelular(tfCelularUs.getText());	
						us.setLoginUs(tfLoginUs.getText());

						if (Integer.parseInt(tfCodigoUs.getText()) != Login.getUsuario().getIdUs()) {
							JOptionPane.showMessageDialog(null, "Atenção: Você não pode alterar a senha de outro usuário! \n\n"
									+ "Os outros dados foram alterados com sucesso.");
							
							usDao.AlterarUsuarioSemSenha(us);
						} else {
							if (tfSenhaUs.getText().isEmpty()) {
								JOptionPane.showMessageDialog(null, "Você deve preencher o campo 'senha' antes de alterar");
							} else {
								us.setSenhaUs(tfSenhaUs.getText());
								usDao.AlterarUsuarioComSenha(us);
							}
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
					atualizarTabela();
				}
				
				else{
					JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada");
				}
			}
		});
		btAlterar.setBackground(SystemColor.controlHighlight);
		btAlterar.setBounds(357, 107, 147, 21);
		contentPane.add(btAlterar);
		
		JButton btCadastrar = new JButton("Cadastrar");
		btCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//atribuição dos valores dos campos para o objeto cliente	
					if(tfNomeUs.getText().isEmpty()){
						JOptionPane.showMessageDialog(null, "Nome de usuário não preenchido");
					} else {
						us.setNome(tfNomeUs.getText());
						
						if (tfRgUs.getText().isEmpty()){
							JOptionPane.showMessageDialog(null, "RG do usuário não preenchido");
						} else {
							us.setRgUs(tfRgUs.getText());
							
							if (tfCpfUs.getText().isEmpty()){
								JOptionPane.showMessageDialog(null, "CPF do usuário não preenchido");
							} else {
								us.setCpf(tfCpfUs.getText());
								
								if (tfTelefoneUs.getText().isEmpty()){
									JOptionPane.showMessageDialog(null, "Telefone do usuário não preenchido");
								} else {
									us.setTelefone(tfTelefoneUs.getText());
									
									if (tfCelularUs.getText().isEmpty()){
										JOptionPane.showMessageDialog(null, "Celular do usuário não preenchido");
									} else {
										us.setCelular(tfCelularUs.getText());
										
										if (tfLoginUs.getText().isEmpty()){
											JOptionPane.showMessageDialog(null, "Login do usuário não preenchido");
										} else {
											boolean existe = false;
											for(int i = 0; i<table.getRowCount(); i++){
												if (table.getValueAt(i, 6).equals(tfLoginUs.getText())) {
													existe = true;
												}
											}
											if (existe) {
												JOptionPane.showMessageDialog(null, "Login já cadastrado");
											} else {
												us.setLoginUs(tfLoginUs.getText());
												
												if (tfSenhaUs.getText().isEmpty()){
													JOptionPane.showMessageDialog(null, "Senha do usuário não preenchida");
												} else {
													us.setSenhaUs(tfSenhaUs.getText());
													
													us.setDataCadastro(timestamp);
											
													// chamada do método de cadastro na classe Dao
													usDao.CadastrarUsuario(us);
												}
											}
										}
									}
								}
							}
						}
					} 
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				atualizarTabela();
			}
		});
		btCadastrar.setBackground(SystemColor.controlHighlight);
		btCadastrar.setBounds(357, 75, 147, 21);
		contentPane.add(btCadastrar);
		
		JButton btLimpar = new JButton("Limpar");
		btLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btLimpar.setBackground(SystemColor.controlHighlight);
		btLimpar.setBounds(357, 43, 147, 21);
		contentPane.add(btLimpar);
		
		JButton btExcluir = new JButton("Excluir");
		btExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1){
					Object[] options3 = {"Excluir", "Cancelar"};
					if(JOptionPane.showOptionDialog(null, "Tem certeza que deseja excluir o registro:\n>   " 
							+ table.getValueAt(table.getSelectedRow(), 0) + "   -   "
							+ table.getValueAt(table.getSelectedRow(), 1), null,
							JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options3, options3[0]) == 0){
						try {
						
							//atribuição do valor do campo código para o objeto cliente
							us.setIdUs(Integer.parseInt(tfCodigoUs.getText()));
							
							// chamada do método de exclusão na classe Dao passando como parâmetro o código do cliente para ser excluído
							usDao.deletarUsuario(us);
							
						
							atualizarTabela();
							limpar();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada");
				}
			}
		});
		btExcluir.setBackground(SystemColor.controlHighlight);
		btExcluir.setBounds(357, 145, 147, 21);
		contentPane.add(btExcluir);
		
		JLabel label_5 = new JLabel("Busca:");
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setBounds(22, 237, 46, 20);
		contentPane.add(label_5);
		
		JLabel lblUsuriosCadastrados = new JLabel("Usu\u00E1rios Cadastrados:");
		lblUsuriosCadastrados.setBounds(10, 323, 156, 28);
		contentPane.add(lblUsuriosCadastrados);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 351, 532, 159);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				setCamposFromTabela();
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Nome", "CPF", "RG", "Telefone", "Celular", "Login", "Data"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(43);
		table.getColumnModel().getColumn(1).setPreferredWidth(90);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.LIGHT_GRAY);
		separator_1.setBounds(10, 237, 532, 2);
		contentPane.add(separator_1);
		
		JLabel label_6 = new JLabel("C\u00F3digo:");
		label_6.setBounds(22, 265, 46, 20);
		contentPane.add(label_6);
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		
		tfPCodigo = new JTextField();
		tfPCodigo.setBounds(78, 265, 437, 20);
		contentPane.add(tfPCodigo);
		tfPCodigo.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				
				//atualizar a tabela apenas com valores correspondentes aos digitados no campo de busca por codigo
				TableRowSorter<TableModel> filtro = null;  
				DefaultTableModel model = (DefaultTableModel) table.getModel();  
				filtro = new TableRowSorter<TableModel>(model);  
				table.setRowSorter(filtro);
				
				if (tfPCodigo.getText().length() == 0) {
					filtro.setRowFilter(null);
				} else {  
					filtro.setRowFilter(RowFilter.regexFilter(tfPCodigo.getText(), 0));  
				}  
			}
		});
		tfPCodigo.setColumns(10);
		
		tfPNome = new JTextField();
		tfPNome.setBounds(78, 292, 437, 20);
		contentPane.add(tfPNome);
		tfPNome.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				
				//atualizar a tabela apenas com valores correspondentes aos digitados no campo de busca por nome
				TableRowSorter<TableModel> filtro = null;  
				DefaultTableModel model = (DefaultTableModel) table.getModel();  
				filtro = new TableRowSorter<TableModel>(model);  
				table.setRowSorter(filtro); 
				
				if (tfPNome.getText().length() == 0) {
					filtro.setRowFilter(null);
				} else {  
					filtro.setRowFilter(RowFilter.regexFilter("(?i)" + tfPNome.getText(), 1));  
				}  
				
			}
		});
		tfPNome.setColumns(10);
		
		JLabel label_7 = new JLabel("Nome:");
		label_7.setBounds(22, 293, 46, 20);
		contentPane.add(label_7);
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setBounds(10, 325, 532, 2);
		contentPane.add(separator);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLogin.setBounds(73, 200, 59, 20);
		contentPane.add(lblLogin);
		
		tfLoginUs = new JTextField();
		tfLoginUs.setColumns(10);
		tfLoginUs.setBounds(142, 200, 147, 20);
		contentPane.add(tfLoginUs);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSenha.setBounds(301, 200, 59, 20);
		contentPane.add(lblSenha);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.LIGHT_GRAY);
		separator_2.setBounds(102, 187, 402, 2);
		contentPane.add(separator_2);
		
		tfSenhaUs = new JPasswordField();
		tfSenhaUs.setBounds(369, 200, 135, 20);
		contentPane.add(tfSenhaUs);
		
		JButton button = new JButton("Voltar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.setBackground(SystemColor.controlHighlight);
		button.setBounds(194, 523, 166, 31);
		contentPane.add(button);
	}

	public void setCamposFromTabela() {
		tfCodigoUs.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
		tfNomeUs.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
		tfCpfUs.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
		tfRgUs.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 3)));
		tfTelefoneUs.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 4)));
		tfCelularUs.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 5)));
		tfLoginUs.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 6)));
	}

	public void limpar() {
		tfCpfUs.setText(null);
		tfNomeUs.setText(null);
		tfTelefoneUs.setText(null);
		tfRgUs.setText(null);
		tfCelularUs.setText(null);
		tfLoginUs.setText(null);
		tfSenhaUs.setText(null);
		try {
			tfCodigoUs.setText(String.valueOf(usDao.RetornarProximoCodigoUsuario()));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void atualizarTabela() {
		try {
			usuario = usDao.buscarTodos();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setNumRows(0);
		for (int x=0; x!=usuario.size(); x++)
			{
				model.addRow((Object[]) usuario.get(x));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void ordenarPorNome(){
		table.setAutoCreateRowSorter(true);
		table.getRowSorter().toggleSortOrder(1);
	}
}
