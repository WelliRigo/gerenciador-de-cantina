package br.edu.ifcvideira.controllers.views;

import java.awt.Color;
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
import java.awt.Toolkit;

public class BuscarClienteVnView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textPNome;
	private JTextField textPCodigo;
	private JTable tbClientesCadastrados;

	private List<Object> cliente = new ArrayList<Object>();
	
	Cliente cl = new Cliente();
	ClienteDao clDao = new ClienteDao();
	ClienteView clView = new ClienteView();
	private JTextField tfCodigoSelecionado;
	private JTextField tfNomeSelecionado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BuscarClienteVnView frame = new BuscarClienteVnView();
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
	public BuscarClienteVnView() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(BuscarClienteVnView.class.getResource("/br/edu/ifcvideira/imgs/icone1 laranja.png")));
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent arg0) {
				atualizarTabela();
			}
			public void windowLostFocus(WindowEvent arg0) {
			}
		});
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent arg0) {
				atualizarTabela();
				ordenarPorNome();
			}
		});
		
		setTitle("Buscar Cliente");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 413, 474);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbBusca = new JLabel("Busca:");
		lbBusca.setHorizontalAlignment(SwingConstants.RIGHT);
		lbBusca.setBounds(23, 11, 46, 20);
		contentPane.add(lbBusca);
		
		JLabel lblClientesCadastrados = new JLabel("Clientes Cadastrados:");
		lblClientesCadastrados.setBounds(10, 110, 175, 20);
		contentPane.add(lblClientesCadastrados);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 128, 384, 159);
		contentPane.add(scrollPane);
		
		tbClientesCadastrados = new JTable();
		tbClientesCadastrados.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				setCamposFromTabela();
			}
		});
		scrollPane.setViewportView(tbClientesCadastrados);
		tbClientesCadastrados.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Nome", "cpf"
			}
		));
		
		JLabel lbCodigoP = new JLabel("C\u00F3digo:");
		lbCodigoP.setBounds(23, 39, 46, 20);
		contentPane.add(lbCodigoP);
		lbCodigoP.setHorizontalAlignment(SwingConstants.RIGHT);
		
		textPCodigo = new JTextField();
		textPCodigo.setBounds(79, 39, 315, 20);
		contentPane.add(textPCodigo);
		textPCodigo.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				//atualizar a tabela apenas com valores correspondentes aos digitados no campo de busca por codigo
				TableRowSorter<TableModel> filtro = null;  
				DefaultTableModel model = (DefaultTableModel) tbClientesCadastrados.getModel();  
				filtro = new TableRowSorter<TableModel>(model);  
				tbClientesCadastrados.setRowSorter(filtro);
				
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
				DefaultTableModel model = (DefaultTableModel) tbClientesCadastrados.getModel();  
				filtro = new TableRowSorter<TableModel>(model);  
				tbClientesCadastrados.setRowSorter(filtro); 
				
				if (textPNome.getText().length() == 0) {
					filtro.setRowFilter(null);
				} else {  
					filtro.setRowFilter(RowFilter.regexFilter("(?i)" + textPNome.getText(), 1));  
				}  
				
			}
		});
		textPNome.setColumns(10);
		
		JLabel lbNomeP = new JLabel("Nome:");
		lbNomeP.setBounds(23, 67, 46, 20);
		contentPane.add(lbNomeP);
		lbNomeP.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setBounds(10, 97, 384, 2);
		contentPane.add(separator);
		
		JLabel lblClienteSelecionado = new JLabel("Cliente Selecionado:");
		lblClienteSelecionado.setBounds(10, 299, 156, 14);
		contentPane.add(lblClienteSelecionado);
		
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
				//Setar tfCodigoCliente de VendaView como o código selecionado
				if (tfCodigoSelecionado.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Selecione um Cliente");
				} else {
					int codigo = Integer.parseInt(tfCodigoSelecionado.getText());
					VendaView.tfCodigoCliente.setText(""+codigo);
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
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancelar.setBounds(253, 377, 106, 23);
		contentPane.add(btnCancelar);
		
		JButton btnCadastrarNovoFornecedor = new JButton("Cadastrar Novo");
		btnCadastrarNovoFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Abrir janela ClienteView
				clView.setVisible(true);
			}
		});
		btnCadastrarNovoFornecedor.setBounds(147, 411, 139, 23);
		contentPane.add(btnCadastrarNovoFornecedor);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setBounds(162, 329, 46, 14);
		contentPane.add(lblNome);
	}

	public void setCamposFromTabela() {
		//Setar campos de acordo com linha selecionada na tbClientesCadastrados
		tfCodigoSelecionado.setText(String.valueOf(tbClientesCadastrados.getValueAt(tbClientesCadastrados.getSelectedRow(), 0)));
		tfNomeSelecionado.setText(String.valueOf(tbClientesCadastrados.getValueAt(tbClientesCadastrados.getSelectedRow(), 1)));
	}

	public void atualizarTabela() {
		//Atualizar tabela tbClientesCadastrados de acordo com clientes do Banco
		try {
			cliente = clDao.buscarTodos();
			DefaultTableModel model = (DefaultTableModel) tbClientesCadastrados.getModel();
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
		tbClientesCadastrados.setAutoCreateRowSorter(true);
		tbClientesCadastrados.getRowSorter().toggleSortOrder(1);
	}
}

