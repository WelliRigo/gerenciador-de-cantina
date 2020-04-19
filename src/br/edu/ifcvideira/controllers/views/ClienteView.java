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

import br.edu.ifcvideira.DAOs.ClienteDao;
import br.edu.ifcvideira.beans.Cliente;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

public class ClienteView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfNome;
	private JTextField tfCpf;
	private JTextField tfCelular;
	private JTextField tfTelefone;
	private JTextField textPNome;
	private JTextField textPId;
	private JTable table;

	private List<Object> cliente = new ArrayList<Object>();
	
	Cliente cl = new Cliente();
	ClienteDao clDao = new ClienteDao();
	
	long time = System.currentTimeMillis();
	Timestamp timestamp = new Timestamp(time);
	
	private JTextField tfId;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClienteView frame = new ClienteView();
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
	}

	public ClienteView() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClienteView.class.getResource("/br/edu/ifcvideira/imgs/icone1 laranja.png")));
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent arg0) {
				atualizarTabela();
				ordenarPorNome();
				limpar();
			}
		});
		setTitle("Cadastro Clientes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 499, 533);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setLocationRelativeTo(null);
		
		JLabel label_1 = new JLabel("Nome:");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(10, 10, 59, 20);
		contentPane.add(label_1);
		
		tfNome = new JTextField();
		tfNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				boolean teclaCerta = false;
                Character ch = e.getKeyChar();
                /* aceita letras e espaço */
                if (Character.isAlphabetic(e.getKeyChar()) || String.valueOf(ch).equals(" ")) {
                       teclaCerta = true;
                 }
                /* se não for um digito no jTextFied o evento é consumido. Nada acontece */
	            if (!teclaCerta) {
	                  e.consume();
	             }
			}
		});
		tfNome.setColumns(10);
		tfNome.setBounds(79, 11, 384, 20);
		contentPane.add(tfNome);
		
		tfCpf = new JTextField();
		tfCpf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				boolean teclaCerta = false;
                Character ch = e.getKeyChar();
                /* aceita números*/
                if (Character.isDigit(e.getKeyChar())) {
                       teclaCerta = true;
                 }
                /* se não for um digito no jTextFied o evento é consumido. Nada acontece */
	            if (!teclaCerta) {
	                  e.consume();
	             }
			}
		});
		tfCpf.setColumns(10);
		tfCpf.setBounds(79, 121, 227, 20);
		contentPane.add(tfCpf);
		
		JLabel lbCpf = new JLabel("CPF:");
		lbCpf.setHorizontalAlignment(SwingConstants.RIGHT);
		lbCpf.setBounds(10, 121, 59, 20);
		contentPane.add(lbCpf);
		
		
		JButton alterar = new JButton("Alterar");
		alterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1){
					try {
						
						//atribuição dos valores dos campos para o objeto cliente
						cl.setIdCl(Integer.parseInt(tfId.getText()));
						cl.setNome(tfNome.getText());
						cl.setCpf(tfCpf.getText());
						cl.setTelefone(tfTelefone.getText());
						cl.setCelular(tfCelular.getText());

						
						// chamada do método de alteração na classe Dao
						clDao.AlterarCliente(cl);
						
		
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
		alterar.setBackground(SystemColor.controlHighlight);
		alterar.setBounds(316, 93, 147, 21);
		contentPane.add(alterar);
		
		JButton cadastrar = new JButton("Cadastrar");
		cadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(tfNome.getText().isEmpty()){
						JOptionPane.showMessageDialog(null, "Campo - 'Nome' não preenchido");
					} else {
						cl.setNome(tfNome.getText());
						
						if(tfTelefone.getText().isEmpty()){
							JOptionPane.showMessageDialog(null, "Campo - 'Telefone' não preenchido");
							
						}else{
							cl.setTelefone(tfTelefone.getText());
							
							if(tfCelular.getText().isEmpty()){
								JOptionPane.showMessageDialog(null, "Campo - 'Celular' não preenchido");
							
							}else{
								cl.setCelular(tfCelular.getText());
								
								if(tfCpf.getText().isEmpty()){
									JOptionPane.showMessageDialog(null, "Campo - 'CPF' não preenchido");
								}else{
									cl.setCpf(tfCpf.getText());
									cl.setDataCadastro(timestamp);
									clDao.CadastrarCliente(cl);
								}
							}
						}			
					}

					//atribuição dos valores dos campos para o objeto cliente
//					cl.setNome_cliente(textNome.getText());
//					cl.setCpf_cliente(textCpf.getText());
//					cl.setTelefone_cliente(textTelefone.getText());
//					cl.setCelular_cliente(textCelular.getText());
//					cl.setData(ti);

					
					// chamada do método de cadastro na classe Dao
					
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				atualizarTabela();
			}
		});
		cadastrar.setBackground(SystemColor.controlHighlight);
		cadastrar.setBounds(316, 61, 147, 21);
		contentPane.add(cadastrar);
		
		JLabel lbTelefone = new JLabel("Telefone:");
		lbTelefone.setHorizontalAlignment(SwingConstants.RIGHT);
		lbTelefone.setBounds(10, 65, 59, 20);
		contentPane.add(lbTelefone);
		
		JButton limpar = new JButton("Limpar");
		limpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		limpar.setBackground(SystemColor.controlHighlight);
		limpar.setBounds(316, 33, 147, 21);
		contentPane.add(limpar);
		
		tfTelefone = new JTextField();
		tfTelefone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				boolean teclaCerta = false;
                Character ch = e.getKeyChar();
                /* aceita números*/
                if (Character.isDigit(e.getKeyChar()) ) {
                       teclaCerta = true;
                 }
                /* se não for um digito no jTextFied o evento é consumido. Nada acontece */
	            if (!teclaCerta) {
	                  e.consume();
	             }
			}
		});
		tfTelefone.setColumns(10);
		tfTelefone.setBounds(79, 65, 227, 20);
		contentPane.add(tfTelefone);
		
		JButton excluir = new JButton("Excluir");
		excluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1){
					Object[] options3 = {"Excluir", "Cancelar"};
					if(JOptionPane.showOptionDialog(null, "Tem certeza que deseja excluir o registro:\n>   " 
							+ table.getValueAt(table.getSelectedRow(), 0) + "   -   "
							+ table.getValueAt(table.getSelectedRow(), 1), null,
							JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options3, options3[0]) == 0){
						try {
						
							//atribuição do valor do campo código para o objeto cliente
							cl.setIdCl(Integer.parseInt(tfId.getText()));
							
							// chamada do método de exclusão na classe Dao passando como parâmetro o código do cliente para ser excluído
							clDao.deletarCliente(cl);
							
						
							atualizarTabela();
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
		excluir.setBackground(SystemColor.controlHighlight);
		excluir.setBounds(316, 121, 147, 21);
		contentPane.add(excluir);
		
		JLabel lbBusca = new JLabel("Busca:");
		lbBusca.setHorizontalAlignment(SwingConstants.RIGHT);
		lbBusca.setBounds(23, 177, 46, 20);
		contentPane.add(lbBusca);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 293, 493, 159);
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
				"Id", "Nome", "CPF", "Telefone", "Celular", "Data",
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		
		JLabel lbId = new JLabel("Id:");
		lbId.setHorizontalAlignment(SwingConstants.RIGHT);
		lbId.setBounds(10, 38, 59, 20);
		contentPane.add(lbId);
		
		tfId = new JTextField();
		tfId.setEditable(false);
		tfId.setColumns(10);
		tfId.setBounds(79, 38, 227, 20);
		contentPane.add(tfId);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.LIGHT_GRAY);
		separator_1.setBounds(0, 163, 493, 3);
		contentPane.add(separator_1);
		
		JLabel lblId = new JLabel("Id:");
		lblId.setBounds(23, 205, 46, 20);
		contentPane.add(lblId);
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		
		textPId = new JTextField();
		textPId.setBounds(79, 205, 384, 20);
		contentPane.add(textPId);
		textPId.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				
				//atualizar a tabela apenas com valores correspondentes aos digitados no campo de busca por codigo
				TableRowSorter<TableModel> filtro = null;  
				DefaultTableModel model = (DefaultTableModel) table.getModel();  
				filtro = new TableRowSorter<TableModel>(model);  
				table.setRowSorter(filtro);
				
				if (textPId.getText().length() == 0) {
					filtro.setRowFilter(null);
					ordenarPorNome();
				} else {  
					filtro.setRowFilter(RowFilter.regexFilter(textPId.getText(), 0));  
				}  
			}
		});
		textPId.setColumns(10);
		
		textPNome = new JTextField();
		textPNome.setBounds(79, 232, 384, 20);
		contentPane.add(textPNome);
		textPNome.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				
				//atualizar a tabela apenas com valores correspondentes aos digitados no campo de busca por nome
				TableRowSorter<TableModel> filtro = null;  
				DefaultTableModel model = (DefaultTableModel) table.getModel();  
				filtro = new TableRowSorter<TableModel>(model);  
				table.setRowSorter(filtro); 
				
				if (textPNome.getText().length() == 0) {
					filtro.setRowFilter(null);
					ordenarPorNome();
				} else {  
					filtro.setRowFilter(RowFilter.regexFilter("(?i)" + textPNome.getText(), 1));  
				}  
				
			}
		});
		textPNome.setColumns(10);
		
		JLabel label_7 = new JLabel("Nome:");
		label_7.setBounds(23, 233, 46, 20);
		contentPane.add(label_7);
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lbCelular = new JLabel("Celular:");
		lbCelular.setHorizontalAlignment(SwingConstants.RIGHT);
		lbCelular.setBounds(10, 93, 59, 20);
		contentPane.add(lbCelular);
		
		tfCelular = new JTextField();
		tfCelular.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				boolean teclaCerta = false;
                Character ch = e.getKeyChar();
                /* aceita números*/
                if (Character.isDigit(e.getKeyChar())) {
                       teclaCerta = true;
                 }
                /* se não for um digito no jTextFied o evento é consumido. Nada acontece */
	            if (!teclaCerta) {
	                  e.consume();
	             }
			}
		});
		tfCelular.setColumns(10);
		tfCelular.setBounds(79, 90, 227, 20);
		contentPane.add(tfCelular);
		
		JButton button = new JButton("Voltar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.setBackground(SystemColor.controlHighlight);
		button.setBounds(169, 463, 166, 31);
		contentPane.add(button);
		
		JLabel label = new JLabel("Clientes Cadastrados:");
		label.setBounds(10, 273, 156, 20);
		contentPane.add(label);
	}

	public void setCamposFromTabela() {
		tfId.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
		tfNome.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
		tfCpf.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
		tfTelefone.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 3)));
		tfCelular.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 4)));
	}

	public void limpar() {
		tfCpf.setText(null);
		tfNome.setText(null);
		tfCelular.setText(null);
		tfTelefone.setText(null);
		try {
			tfId.setText(String.valueOf(clDao.RetornarProximoCodigoCliente()));
			if (tfId.getText().equals("0")) {
				tfId.setText("1");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void atualizarTabela() {
		try {
			cliente = clDao.buscarTodos();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setNumRows(0);
		for (int x=0; x!=cliente.size(); x++)
			{
				model.addRow((Object[]) cliente.get(x));
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
