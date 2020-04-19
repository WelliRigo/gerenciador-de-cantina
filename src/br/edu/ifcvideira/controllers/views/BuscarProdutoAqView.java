package br.edu.ifcvideira.controllers.views;

import java.awt.Color;
import java.awt.EventQueue;
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
import java.awt.event.WindowFocusListener;
import java.awt.Toolkit;

public class BuscarProdutoAqView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textPNome;
	private JTextField textPCodigo;
	private JTable table;

	
	private List<Object> produto = new ArrayList<Object>();
	
	Produto pr = new Produto();
	ProdutoDao prDao = new ProdutoDao();
	private JTextField tfCodigoSelecionado;
	private JTextField tfNomeSelecionado;
	
	ProdutoView pv = new ProdutoView();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BuscarProdutoAqView frame = new BuscarProdutoAqView();
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
	public BuscarProdutoAqView() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(BuscarProdutoAqView.class.getResource("/br/edu/ifcvideira/imgs/icone1 laranja.png")));
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				atualizarTabela();
				ordenarPorNome();
			}
			public void windowLostFocus(WindowEvent e) {
			}
		});
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent arg0) {
				atualizarTabela();
			}
		});
		setTitle("Buscar Produto");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 413, 474);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label_5 = new JLabel("Busca:");
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setBounds(23, 11, 46, 20);
		contentPane.add(label_5);
		
		JLabel lblProdutoesCadastrados = new JLabel("Produtos Cadastrados:");
		lblProdutoesCadastrados.setBounds(10, 110, 175, 20);
		contentPane.add(lblProdutoesCadastrados);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 128, 384, 159);
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
				"Código", "Nome"
			}
		));
		
		JLabel label_6 = new JLabel("C\u00F3digo:");
		label_6.setBounds(23, 39, 46, 20);
		contentPane.add(label_6);
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		
		textPCodigo = new JTextField();
		textPCodigo.setBounds(79, 39, 315, 20);
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
		textPNome.setBounds(79, 66, 315, 20);
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
		label_7.setBounds(23, 67, 46, 20);
		contentPane.add(label_7);
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setBounds(10, 97, 384, 2);
		contentPane.add(separator);
		
		JLabel lblProdutoSelecionado = new JLabel("Produto Selecionado:");
		lblProdutoSelecionado.setBounds(10, 299, 156, 14);
		contentPane.add(lblProdutoSelecionado);
		
		JLabel lblCdigo = new JLabel("C\u00F3digo:");
		lblCdigo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCdigo.setBounds(10, 329, 46, 14);
		contentPane.add(lblCdigo);
		
		tfCodigoSelecionado = new JTextField();
		tfCodigoSelecionado.setEditable(false);
		tfCodigoSelecionado.setBounds(66, 326, 86, 20);
		contentPane.add(tfCodigoSelecionado);
		tfCodigoSelecionado.setColumns(10);
		
		tfNomeSelecionado = new JTextField();
		tfNomeSelecionado.setEditable(false);
		tfNomeSelecionado.setBounds(218, 326, 176, 20);
		contentPane.add(tfNomeSelecionado);
		tfNomeSelecionado.setColumns(10);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tfCodigoSelecionado.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Selecione um produto");
				} else {
					int codigo = Integer.parseInt(tfCodigoSelecionado.getText());
					AquisicaoView.tfCodigoProduto.setText(""+codigo);
					dispose();
				}
			}
		});
		btnConfirmar.setBounds(79, 377, 106, 23);
		contentPane.add(btnConfirmar);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 354, 387, 2);
		contentPane.add(separator_1);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(253, 377, 106, 23);
		contentPane.add(btnCancelar);
		
		JButton btnCadastrarNovoProduto = new JButton("Cadastrar Novo");
		btnCadastrarNovoProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pv.setVisible(true);
			}
		});
		btnCadastrarNovoProduto.setBounds(147, 411, 139, 23);
		contentPane.add(btnCadastrarNovoProduto);
	}

	public void setCamposFromTabela() {
		tfCodigoSelecionado.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
		tfNomeSelecionado.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
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
