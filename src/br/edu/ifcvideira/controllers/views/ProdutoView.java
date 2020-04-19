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

import br.edu.ifcvideira.DAOs.ProdutoDao;
import br.edu.ifcvideira.beans.Produto;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

public class ProdutoView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textNome;
	private JTextField textVlUnitario;
	private JTextField textPNome;
	private JTextField textPCodigo;
	private JTable table;

	private List<Object> produto = new ArrayList<Object>();
	
	Produto pr = new Produto();
	ProdutoDao prDao = new ProdutoDao();
	
	private JTextField textCodigo;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProdutoView frame = new ProdutoView();
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
	}

	public ProdutoView() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProdutoView.class.getResource("/br/edu/ifcvideira/imgs/icone1 laranja.png")));
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent arg0) {
				atualizarTabela();
				ordenarPorNome();
				limpar();
			}
		});
		setTitle("Cadastro Produtos");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 413, 518);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setLocationRelativeTo(null);
		
		JLabel label_1 = new JLabel("Nome:");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(28, 11, 59, 20);
		contentPane.add(label_1);
		
		textNome = new JTextField();
		textNome.setColumns(10);
		textNome.setBounds(97, 11, 297, 20);
		contentPane.add(textNome);
		
		textVlUnitario = new JTextField();
		textVlUnitario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				boolean teclaCerta = false;
                Character ch = e.getKeyChar();
                /* aceita números e ponto (.)*/
                if (Character.isDigit(e.getKeyChar()) || String.valueOf(ch).equals(".")) {
                       teclaCerta = true;
                 }
                /* se não for um digito no jTextFied o evento é consumido. Nada acontece */
	            if (!teclaCerta) {
	                  e.consume();
	             }
			}
		});
		textVlUnitario.setColumns(10);
		textVlUnitario.setBounds(97, 94, 131, 20);
		contentPane.add(textVlUnitario);
		
		JLabel lblVlUnitario = new JLabel("Valor Unit\u00E1rio:");
		lblVlUnitario.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVlUnitario.setBounds(0, 94, 87, 20);
		contentPane.add(lblVlUnitario);
		
		JButton alterar = new JButton("Alterar");
		alterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1){
					try {
						
						//atribuição dos valores dos campos para o objeto cliente
						pr.setIdPr(Integer.parseInt(textCodigo.getText()));
						pr.setNomePr(textNome.getText());
						pr.setValorUnitarioPr(Double.parseDouble(textVlUnitario.getText()));
						
						// chamada do método de alteração na classe Dao
						prDao.AlterarProduto(pr);
						
		
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
		alterar.setBounds(247, 93, 147, 21);
		contentPane.add(alterar);
		
		JButton cadastrar = new JButton("Cadastrar");
		cadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					
					//atribuição dos valores dos campos para o objeto cliente
					if (textNome.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Campo 'Nome' não preenchido.");
					} else {
						pr.setNomePr(textNome.getText());
						
						if (textVlUnitario.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Campo 'Valor Unitário' não preenchido.");
						} else {
							pr.setValorUnitarioPr(Double.parseDouble(textVlUnitario.getText()));
							
							// chamada do método de cadastro na classe Dao
							prDao.CadastrarProduto(pr);
						}
					}
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				atualizarTabela();
				//limpar();
			}
		});
		cadastrar.setBackground(SystemColor.controlHighlight);
		cadastrar.setBounds(247, 65, 147, 21);
		contentPane.add(cadastrar);
		
		JButton limpar = new JButton("Limpar");
		limpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		limpar.setBackground(SystemColor.controlHighlight);
		limpar.setBounds(247, 37, 147, 21);
		contentPane.add(limpar);
		
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
							pr.setIdPr(Integer.parseInt(textCodigo.getText()));
							
							// chamada do método de exclusão na classe Dao passando como parâmetro o código do cliente para ser excluído
							prDao.deletarProduto(pr);
							
						
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
		excluir.setBounds(247, 120, 147, 21);
		contentPane.add(excluir);
		
		JLabel label_5 = new JLabel("Busca:");
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setBounds(23, 158, 46, 20);
		contentPane.add(label_5);
		
		JLabel label_8 = new JLabel("Produtos Cadastrados:");
		label_8.setBounds(10, 257, 156, 20);
		contentPane.add(label_8);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 275, 384, 159);
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
				"Id_produto", "Nome", "Valor Unitário"
			}
		));
		
		JLabel lblCodigo = new JLabel("Codigo:");
		lblCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodigo.setBounds(10, 52, 77, 20);
		contentPane.add(lblCodigo);
		
		textCodigo = new JTextField();
		textCodigo.setEditable(false);
		textCodigo.setColumns(10);
		textCodigo.setBounds(97, 52, 131, 20);
		contentPane.add(textCodigo);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.LIGHT_GRAY);
		separator_1.setBounds(10, 153, 384, 2);
		contentPane.add(separator_1);
		
		JLabel label_6 = new JLabel("C\u00F3digo:");
		label_6.setBounds(23, 186, 46, 20);
		contentPane.add(label_6);
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		
		textPCodigo = new JTextField();
		textPCodigo.setBounds(79, 186, 315, 20);
		contentPane.add(textPCodigo);
		textPCodigo.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				
				//atualizar a tabela apenas com valores correspondentes aos digitados no campo de busca por codigo
				TableRowSorter<TableModel> filtro = null;  
				DefaultTableModel model = (DefaultTableModel) table.getModel();  
				filtro = new TableRowSorter<TableModel>(model);  
				table.setRowSorter(filtro);
				
				if (textPCodigo.getText().length() == 0) {
					filtro.setRowFilter(null);
				} else {  
					filtro.setRowFilter(RowFilter.regexFilter(textPCodigo.getText(), 0));  
				}  
			}
		});
		textPCodigo.setColumns(10);
		
		textPNome = new JTextField();
		textPNome.setBounds(79, 213, 315, 20);
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
				} else {  
					filtro.setRowFilter(RowFilter.regexFilter("(?i)" + textPNome.getText(), 1));  
				}  
				
			}
		});
		textPNome.setColumns(10);
		
		JLabel label_7 = new JLabel("Nome:");
		label_7.setBounds(23, 214, 46, 20);
		contentPane.add(label_7);
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setBounds(10, 244, 384, 2);
		contentPane.add(separator);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnVoltar.setBackground(SystemColor.controlHighlight);
		btnVoltar.setBounds(122, 445, 163, 33);
		contentPane.add(btnVoltar);
	}

	public void setCamposFromTabela() {
		textCodigo.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
		textNome.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
		textVlUnitario.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
	}

	public void limpar() {
		textVlUnitario.setText(null);
		textNome.setText(null);
		try {
			textCodigo.setText(String.valueOf(prDao.RetornarProximoCodigoProduto()));
			//Se Código == 0, então setar como 1, deixando mais claro ao usuário
			if (textCodigo.getText().equals("0")) {
				textCodigo.setText("1");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void atualizarTabela() {
		try {
			produto = prDao.buscarTodos();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setNumRows(0);
		for (int x=0; x!=produto.size(); x++)
			{
				model.addRow((Object[]) produto.get(x));
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
