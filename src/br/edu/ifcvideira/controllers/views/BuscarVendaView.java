package br.edu.ifcvideira.controllers.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import br.edu.ifcvideira.DAOs.VendaDao;
import br.edu.ifcvideira.beans.Venda;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;

import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Toolkit;

public class BuscarVendaView extends JFrame {
	
	private JTable tbVendas;

	private JPanel contentPane;
	
	private List<Object> venda = new ArrayList<Object>();
	VendaDao vnDao = new VendaDao();
	private JTextField tfIdVenda;
	private JTextField tfIdUsuario;
	private JTextField tfIdCliente;
	private JTextField tfNomeUsuario;
	private JTextField tfNomeCliente;
	private JTextField tfAno;
	private JTextField tfMes;
	private JTextField tfDia;
	JComboBox cbTipoVenda = new JComboBox();
	JComboBox cbSituacao = new JComboBox();
	private JTable tbProdutos;
	private List<Object> produto = new ArrayList<Object>();
	
	/***************/
	private TableRowSorter<TableModel> sorter;
	private JTextField tfSituacaoVendaSelecionada;
	/*************/
	Venda vn = new Venda();

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BuscarVendaView frame = new BuscarVendaView();
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
	public BuscarVendaView() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(BuscarVendaView.class.getResource("/br/edu/ifcvideira/imgs/icone1 laranja.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				atualizarTabela();
			}
		});
		
		tfSituacaoVendaSelecionada = new JTextField();
		JTextField tfCodigoVendaSelecionada = new JTextField();
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 684, 668);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setLocationRelativeTo(null);
		
		JLabel lblBuscarVendas = new JLabel("Buscar Vendas");
		lblBuscarVendas.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscarVendas.setFont(new Font("Lucida Sans", Font.BOLD, 22));
		lblBuscarVendas.setBounds(10, 11, 188, 35);
		contentPane.add(lblBuscarVendas);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 44, 621, 2);
		contentPane.add(separator);
		
		JScrollPane scrollPaneVendas = new JScrollPane();
		scrollPaneVendas.setBounds(10, 220, 648, 184);
		contentPane.add(scrollPaneVendas);
		
		tbVendas = new JTable();
		tbVendas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				tfCodigoVendaSelecionada.setText( ""+ tbVendas.getValueAt(tbVendas.getSelectedRow(), 0));
				tfSituacaoVendaSelecionada.setText( ""+ tbVendas.getValueAt(tbVendas.getSelectedRow(), 6));

				vn.setIdVn(Integer.parseInt(tfCodigoVendaSelecionada.getText()));
				
				try {
					produto = vnDao.buscarProdutosVenda(vn);
					DefaultTableModel modelProdutos = (DefaultTableModel) tbProdutos.getModel();
					modelProdutos.setNumRows(0);
				for (int x=0; x!=produto.size(); x++)
					{
					modelProdutos.addRow((Object[]) produto.get(x));
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		scrollPaneVendas.setViewportView(tbVendas);
		tbVendas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id Venda", "Id Usuario", "Nome Usuario", "Id Cliente","Nome Cliente" , "Tipo", "Situa\u00E7\u00E3o", "Data"
			}
		));
		DefaultTableModel model = (DefaultTableModel) tbVendas.getModel();
		tbVendas.getColumnModel().getColumn(0).setPreferredWidth(45);
		tbVendas.getColumnModel().getColumn(1).setPreferredWidth(60);
		tbVendas.getColumnModel().getColumn(2).setPreferredWidth(100);
		tbVendas.getColumnModel().getColumn(3).setPreferredWidth(55);
		tbVendas.getColumnModel().getColumn(4).setPreferredWidth(100);
		tbVendas.setAutoCreateRowSorter(true);
		
		JLabel lblBuscarPor = new JLabel("Buscar por:");
		lblBuscarPor.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblBuscarPor.setBounds(31, 55, 105, 14);
		contentPane.add(lblBuscarPor);
		
		JLabel lblIdVenda = new JLabel("Id Venda:");
		lblIdVenda.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIdVenda.setBounds(31, 80, 90, 14);
		contentPane.add(lblIdVenda);
		
		tfIdVenda = new JTextField();
		tfIdVenda.setBounds(131, 78, 103, 20);
		contentPane.add(tfIdVenda);
		tfIdVenda.setColumns(10);
		
		tfIdUsuario = new JTextField();
		tfIdUsuario.setColumns(10);
		tfIdUsuario.setBounds(131, 112, 103, 20);
		contentPane.add(tfIdUsuario);
		
		JLabel lblIdUsurio = new JLabel("Id Usu\u00E1rio:");
		lblIdUsurio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIdUsurio.setBounds(41, 115, 80, 14);
		contentPane.add(lblIdUsurio);
		
		JLabel lblIdCliente = new JLabel("Id Cliente:");
		lblIdCliente.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIdCliente.setBounds(244, 115, 80, 14);
		contentPane.add(lblIdCliente);
		
		tfIdCliente = new JTextField();
		tfIdCliente.setColumns(10);
		tfIdCliente.setBounds(334, 112, 105, 20);
		contentPane.add(tfIdCliente);
		
		JLabel lblNomeUsurio = new JLabel("Nome Usu\u00E1rio:");
		lblNomeUsurio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNomeUsurio.setBounds(10, 155, 111, 14);
		contentPane.add(lblNomeUsurio);
		
		tfNomeUsuario = new JTextField();
		tfNomeUsuario.setColumns(10);
		tfNomeUsuario.setBounds(131, 152, 103, 20);
		contentPane.add(tfNomeUsuario);
		
		JLabel lblNomeCliente = new JLabel("Nome Cliente:");
		lblNomeCliente.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNomeCliente.setBounds(244, 155, 80, 14);
		contentPane.add(lblNomeCliente);
		
		tfNomeCliente = new JTextField();
		tfNomeCliente.setColumns(10);
		tfNomeCliente.setBounds(334, 152, 105, 20);
		contentPane.add(tfNomeCliente);
		
		JLabel lblTipoVenda = new JLabel("Tipo Venda:");
		lblTipoVenda.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipoVenda.setBounds(234, 80, 90, 14);
		contentPane.add(lblTipoVenda);
		

		cbTipoVenda.setModel(new DefaultComboBoxModel(new String[] {"Selecione:", "\u00C0 vista", "A prazo"}));
		cbTipoVenda.setBounds(334, 77, 105, 20);
		contentPane.add(cbTipoVenda);
		
		JLabel lblSituaoVenda = new JLabel("Situa\u00E7\u00E3o Venda:");
		lblSituaoVenda.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSituaoVenda.setBounds(438, 80, 105, 14);
		contentPane.add(lblSituaoVenda);
		
		cbSituacao.setModel(new DefaultComboBoxModel(new String[] {"Selecione:", "Pendente", "Paga"}));
		cbSituacao.setBounds(553, 77, 105, 20);
		contentPane.add(cbSituacao);
		
		JLabel lblData = new JLabel("Data:");
		lblData.setBounds(467, 133, 46, 14);
		contentPane.add(lblData);
		
		JLabel lblDia = new JLabel("Dia");
		lblDia.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDia.setBounds(497, 115, 46, 14);
		contentPane.add(lblDia);
		
		JLabel lblMs = new JLabel("M\u00EAs");
		lblMs.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMs.setBounds(497, 133, 46, 14);
		contentPane.add(lblMs);
		
		JLabel lblAno = new JLabel("Ano");
		lblAno.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAno.setBounds(497, 152, 46, 14);
		contentPane.add(lblAno);
		
		tfAno = new JTextField();
		tfAno.setToolTipText("");
		tfAno.setBounds(553, 148, 105, 20);
		contentPane.add(tfAno);
		tfAno.setColumns(10);
		
		tfMes = new JTextField();
		tfMes.setColumns(10);
		tfMes.setBounds(553, 130, 105, 20);
		contentPane.add(tfMes);
		
		tfDia = new JTextField();
		tfDia.setColumns(10);
		tfDia.setBounds(553, 112, 105, 20);
		contentPane.add(tfDia);
		
		JButton btnTestar = new JButton("Filtrar");
		btnTestar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFilterInJTable();
			}
		});
		btnTestar.setBounds(244, 186, 89, 23);
		contentPane.add(btnTestar);
		
		JButton btnNewButton = new JButton("Limpar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
				atualizarTabela();
			}
		});
		btnNewButton.setBounds(375, 186, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Voltar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton_1.setBounds(273, 589, 121, 30);
		contentPane.add(btnNewButton_1);
		
		JLabel lblVendaSelecionada = new JLabel("Venda Selecionada:");
		lblVendaSelecionada.setBounds(10, 415, 121, 14);
		contentPane.add(lblVendaSelecionada);
		
		
		tfCodigoVendaSelecionada.setEditable(false);
		tfCodigoVendaSelecionada.setBounds(127, 415, 46, 17);
		contentPane.add(tfCodigoVendaSelecionada);
		tfCodigoVendaSelecionada.setColumns(10);
		
		JLabel lblSituao = new JLabel("Situa\u00E7\u00E3o:");
		lblSituao.setBounds(183, 415, 55, 14);
		contentPane.add(lblSituao);
		
		
		tfSituacaoVendaSelecionada.setEditable(false);
		tfSituacaoVendaSelecionada.setColumns(10);
		tfSituacaoVendaSelecionada.setBounds(240, 412, 84, 20);
		contentPane.add(tfSituacaoVendaSelecionada);
		
		JButton btMudarSituacao = new JButton("Mudar Situa\u00E7\u00E3o");
		btMudarSituacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int id = Integer.parseInt(tfCodigoVendaSelecionada.getText());
				if (tfSituacaoVendaSelecionada.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Selecione uma venda");
				} else if(tfSituacaoVendaSelecionada.getText().equals("Pendente")){
					try {
						vnDao.mudarSituacao(id, "Paga");
						tfSituacaoVendaSelecionada.setText("Paga");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				atualizarTabela();
			}
		});
		btMudarSituacao.setBounds(337, 415, 126, 18);
		contentPane.add(btMudarSituacao);
		ordenarPorData();
		
		JScrollPane scrollPaneProdutos = new JScrollPane();
		scrollPaneProdutos.setBounds(10, 463, 648, 115);
		contentPane.add(scrollPaneProdutos);
		
		tbProdutos = new JTable();
		scrollPaneProdutos.setViewportView(tbProdutos);
		tbProdutos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id Produto", "Nome", "Valor unitário"
			}
		));
		
		JLabel lblProdutosDaVenda = new JLabel("Produtos da venda selecionada");
		lblProdutosDaVenda.setBounds(10, 440, 188, 14);
		contentPane.add(lblProdutosDaVenda);
		
//		JButton btnNewButton_2 = new JButton("Excluir Venda");
//		btnNewButton_2.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				if (tbVendas.getSelectedRow() < 0) {
//					JOptionPane.showMessageDialog(null, "Selecione a venda que deseja excluir");
//				} else {
//					//Verifica se a venda foi cadastrada a mais de 5 minutos (5 minutos = 300000 milisegundos)
//					//Se sim, não exclui. Se não, exclui.
//					Long time = System.currentTimeMillis();			
//					Timestamp tempo = new Timestamp(time - 300000);
//					Timestamp dataVenda = Timestamp.valueOf((String) tbVendas.getValueAt(tbVendas.getSelectedRow(), 7));
//					if (dataVenda.before(tempo)){
//						JOptionPane.showMessageDialog(null, "Você não pode excluir uma venda feita há mais de 5 minutos");
//					} else {
//						vn.setIdVn(Integer.parseInt((String) tbVendas.getValueAt(tbVendas.getSelectedRow(), 0)));
//						try {
//							vnDao.deletarVenda(vn);
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						atualizarTabela();
//					}
//				}
//			}
//		});
//		btnNewButton_2.setBounds(537, 415, 121, 20);
//		contentPane.add(btnNewButton_2);
	}
	
	public void atualizarTabela(){

		DefaultTableModel model = (DefaultTableModel) tbVendas.getModel();

		try {
			venda = vnDao.buscarVendasComNomePrazo();;
			model.setNumRows(0);
			for (int x=0; x!=venda.size(); x++)
			{
				model.addRow((Object[]) venda.get(x));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		try {
			venda = vnDao.buscarVendasComNomeVista();;
			for (int x=0; x!=venda.size(); x++)
			{
				model.addRow((Object[]) venda.get(x));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void ordenarPorData(){
		tbVendas.setAutoCreateRowSorter(true);
		tbVendas.getRowSorter().toggleSortOrder(7);
		tbVendas.getRowSorter().toggleSortOrder(7);
	}
	
	protected void setFilterInJTable() {
		//FILTRAR VENDAS
		
		 //resgata o TableModel da sua JTable
		TableModel teste = tbVendas.getModel();
		//Cria um RowSorter baseado no TableModel resgatado
		sorter = new TableRowSorter<TableModel>(teste);
		//Aplica o RowSorter na JTable
		tbVendas.setRowSorter(sorter);
		
		String tipoVenda = "";
		if (cbTipoVenda.getSelectedIndex() == 0) {
			tipoVenda = "";
		} else if(cbTipoVenda.getSelectedIndex() == 1){  
			tipoVenda = "vista";
		} else if(cbTipoVenda.getSelectedIndex() == 2){
			tipoVenda = "prazo";
		}
		
		String situacao = "";
		if (cbSituacao.getSelectedIndex() == 0) {
			situacao = "";
		} else if(cbSituacao.getSelectedIndex() == 1){  
			situacao = "Pendente";
		} else if(cbSituacao.getSelectedIndex() == 2){
			situacao = "Paga";
		}
		
		//atualizar a tabela apenas com DIA correspondente
		String dia = "-"+tfDia.getText()+" ";
		if (tfDia.getText().length() == 0) {
			dia = "";
		}
		if (tfDia.getText().length() == 1) {
			dia = "-0"+tfDia.getText()+" ";
		}
		
		//atualizar a tabela apenas com MES correspondente
		String mes = "-"+tfMes.getText()+"-";
		if (tfMes.getText().length() == 0) {
			mes = "";
		}
	    if (tfMes.getText().length() == 1) {
	    	mes = "-0"+tfMes.getText()+"-";
		}
	    
	  //atualizar a tabela apenas com ANO correspondente
		String ano = tfAno.getText()+"-";
		if (tfAno.getText().length() == 0) {
			ano = "";
		}
		
		String  idVenda = tfIdVenda.getText().trim(),
	            idUsuario = tfIdUsuario.getText().trim(),
	    		nomeUsuario = tfNomeUsuario.getText().trim(),
	    		idCliente = tfIdCliente.getText().trim(),
	    		nomeCliente = tfNomeCliente.getText().trim();
	    
	    //cria uma lista para guardar os filtros de cada coluna
	    List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>();
	    filters.add(RowFilter.regexFilter("(?i)" + idVenda, 0));
	    filters.add(RowFilter.regexFilter("(?i)" + idUsuario, 1));
	    filters.add(RowFilter.regexFilter("(?i)" + nomeUsuario, 2));
	    filters.add(RowFilter.regexFilter("(?i)" + situacao, 6));
	    filters.add(RowFilter.regexFilter("(?i)" + tipoVenda, 5));
	    filters.add(RowFilter.regexFilter("(?i)" + idCliente, 3));
	    filters.add(RowFilter.regexFilter("(?i)" + nomeCliente, 4));
	    filters.add(RowFilter.regexFilter("(?i)" + dia, 7));
	    filters.add(RowFilter.regexFilter("(?i)" + mes, 7));
	    filters.add(RowFilter.regexFilter("(?i)" + ano, 7));
	    //aplica os filtros no RowSorter que foi criado no construtor
	    //utilizando o andFilter
	    sorter.setRowFilter(RowFilter.andFilter(filters));

	}
	
	public void limpar(){
		tfIdVenda.setText(null);
		tfIdUsuario.setText(null);
		tfNomeUsuario.setText(null);
		cbTipoVenda.setSelectedIndex(0);
		tfIdCliente.setText(null);
		tfNomeCliente.setText(null);
		cbSituacao.setSelectedIndex(0);
		tfDia.setText(null);
		tfMes.setText(null);
		tfAno.setText(null);
		sorter.setRowFilter(null);
		ordenarPorData();
	}
}
