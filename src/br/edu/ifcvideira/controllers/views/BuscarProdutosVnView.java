package br.edu.ifcvideira.controllers.views;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

import java.awt.Font;
import java.awt.Toolkit;

public class BuscarProdutosVnView extends JFrame {

	private JPanel contentPane1;

	//private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textPNome;
	private JTextField textPCodigo;
	private JTable table;
	public static JTable tableAdicionados;

	
	private List<Object> produto = new ArrayList<Object>();

	Produto pr = new Produto();
	ProdutoDao prDao = new ProdutoDao();
	private JTextField tfCodigoSelecionado;
	private JTextField tfNomeSelecionado;
	
	ProdutoView pv = new ProdutoView();
	private JTextField tfValorUnitario;
	
	public static boolean ativar = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BuscarProdutosVnView frame = new BuscarProdutosVnView();
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
	public BuscarProdutosVnView() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(BuscarProdutosVnView.class.getResource("/br/edu/ifcvideira/imgs/icone1 laranja.png")));
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				atualizarTabelaProdutos();
			}
			public void windowLostFocus(WindowEvent e) {
			}
		});
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent arg0) {
				atualizarTabelaProdutos();
				ordenarPorNome();
			}
		});
		setTitle("Buscar Produto");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 446, 553);
		contentPane1 = new JPanel();
		contentPane1.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane1);
		contentPane1.setLayout(null);
		
		JLabel label_5 = new JLabel("Busca:");
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setBounds(10, 11, 46, 20);
		contentPane1.add(label_5);
		
		JLabel lblProdutoesCadastrados = new JLabel("Produtos Cadastrados:");
		lblProdutoesCadastrados.setBounds(10, 88, 175, 20);
		contentPane1.add(lblProdutoesCadastrados);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 110, 420, 111);
		contentPane1.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				setCamposFromTabelaProdutos();
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Código", "Nome", "Valor Unitário"
			}
		));
		
		
		JScrollPane scrollPaneAdicionados = new JScrollPane();
		scrollPaneAdicionados.setBounds(10, 357, 420, 111);
		contentPane1.add(scrollPaneAdicionados);
		
		tableAdicionados = new JTable();
		tableAdicionados.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				setCamposFromTabelaAdicionados();
			}
		});
		scrollPaneAdicionados.setViewportView(tableAdicionados);
		tableAdicionados.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Codigo", "Nome", "Valor Unitário"
			}
		));
		//Adicionar Quantidade no banco
		
		JLabel label_6 = new JLabel("C\u00F3digo:");
		label_6.setBounds(20, 30, 46, 20);
		contentPane1.add(label_6);
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		
		textPCodigo = new JTextField();
		textPCodigo.setBounds(76, 30, 354, 20);
		contentPane1.add(textPCodigo);
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
		textPNome.setBounds(76, 57, 354, 20);
		contentPane1.add(textPNome);
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
		label_7.setBounds(20, 58, 46, 20);
		contentPane1.add(label_7);
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblProdutoSelecionado = new JLabel("Produto Selecionado:");
		lblProdutoSelecionado.setBounds(10, 232, 156, 14);
		contentPane1.add(lblProdutoSelecionado);
		
		JLabel lblCdigo = new JLabel("C\u00F3digo:");
		lblCdigo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCdigo.setBounds(10, 260, 46, 14);
		contentPane1.add(lblCdigo);
		
		tfCodigoSelecionado = new JTextField();
		tfCodigoSelecionado.setEditable(false);
		tfCodigoSelecionado.setBounds(66, 257, 64, 20);
		contentPane1.add(tfCodigoSelecionado);
		tfCodigoSelecionado.setColumns(10);
		
		tfNomeSelecionado = new JTextField();
		tfNomeSelecionado.setEditable(false);
		tfNomeSelecionado.setBounds(140, 257, 181, 20);
		contentPane1.add(tfNomeSelecionado);
		tfNomeSelecionado.setColumns(10);
		
		JButton btnCadastrarNovoProduto = new JButton("Cadastrar Produtos");
		btnCadastrarNovoProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pv.setVisible(true);
			}
		});
		btnCadastrarNovoProduto.setBounds(274, 88, 156, 20);
		contentPane1.add(btnCadastrarNovoProduto);
		
		/******************************/
		DefaultTableModel modelAdicionados = (DefaultTableModel) tableAdicionados.getModel();
		modelAdicionados.setNumRows(0);
		/*****************************/
		
		JButton btAdicionar = new JButton("Adicionar");
		btAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tfCodigoSelecionado.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Você deve selecionar um produto para adicionar");
				} else {
					modelAdicionados.addRow(new String[]{ tfCodigoSelecionado.getText(), tfNomeSelecionado.getText(), tfValorUnitario.getText()});
				}
			}
		});
		btAdicionar.setBounds(140, 288, 89, 23);
		contentPane1.add(btAdicionar);
		
		JButton btRemover = new JButton("Remover");
		btRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modelAdicionados.removeRow(tableAdicionados.getSelectedRow());
			}
		});
		btRemover.setBounds(250, 288, 89, 23);
		contentPane1.add(btRemover);
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ativar = true;
				dispose();
			}
		});

		btnConfirmar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnConfirmar.setBounds(49, 488, 146, 23);
		contentPane1.add(btnConfirmar);
		
		tfValorUnitario = new JTextField();
		tfValorUnitario.setEditable(false);
		tfValorUnitario.setBounds(331, 257, 99, 20);
		contentPane1.add(tfValorUnitario);
		tfValorUnitario.setColumns(10);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCancelar.setBounds(250, 488, 146, 23);
		contentPane1.add(btnCancelar);
	}

	public void setCamposFromTabelaProdutos() {
		tfCodigoSelecionado.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
		tfNomeSelecionado.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
		tfValorUnitario.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
	}
	public void setCamposFromTabelaAdicionados() {
		tfCodigoSelecionado.setText(String.valueOf(tableAdicionados.getValueAt(tableAdicionados.getSelectedRow(), 0)));
		tfNomeSelecionado.setText(String.valueOf(tableAdicionados.getValueAt(tableAdicionados.getSelectedRow(), 1)));
		tfValorUnitario.setText(String.valueOf(tableAdicionados.getValueAt(tableAdicionados.getSelectedRow(), 2)));
	}
	
	public void atualizarTabelaProdutos() {
		try {
			produto = prDao.buscarTodos();
			DefaultTableModel modelProdutos = (DefaultTableModel) table.getModel();
			modelProdutos.setNumRows(0);
		for (int x=0; x!=produto.size(); x++)
			{
			modelProdutos.addRow((Object[]) produto.get(x));
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


