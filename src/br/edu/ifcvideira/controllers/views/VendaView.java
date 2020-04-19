package br.edu.ifcvideira.controllers.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.edu.ifcvideira.DAOs.VendaDao;
import br.edu.ifcvideira.beans.Cliente;
import br.edu.ifcvideira.beans.Login;
import br.edu.ifcvideira.beans.Venda;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JTable;

import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;
import java.awt.event.WindowFocusListener;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;

public class VendaView extends JFrame {
	
	private JPanel contentPane;
	
	JLabel lbUsuarioLogado = new JLabel("Usu\u00E1rio (0)");
	static JTextField tfCodigoCliente;
	private JTextField tfFundoCliente;
	private JTextField tfNomeCliente;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField tfSituacao;
	private JTable tbProdutos;
	private JTable tbVendas;
	
	private List<Object> venda = new ArrayList<Object>();
	private List<Object> produto = new ArrayList<Object>();
	
	Venda vn = new Venda();
	VendaDao vnDao = new VendaDao();
	Cliente cl = new Cliente();
	BuscarClienteVnView bsCl = new BuscarClienteVnView();
	BuscarProdutosVnView bpview = new BuscarProdutosVnView();
	
	private final JSeparator sp2 = new JSeparator();
	private JTextField tfValorTotal;
	private JSeparator sp3;
	static JTextField tfCodigoVenda;
	
	long time = System.currentTimeMillis();
	Timestamp timestamp = new Timestamp(time);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VendaView frame = new VendaView();
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
	public VendaView() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(VendaView.class.getResource("/br/edu/ifcvideira/imgs/icone1 laranja.png")));
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				if (BuscarProdutosVnView.ativar) {
					atualizarProdutos();
					setValorTotal();
					BuscarProdutosVnView.ativar = false;
				}
			}
			public void windowLostFocus(WindowEvent e) {
			}
		});
		
		JLabel lbCliente = new JLabel("Cliente:");
		JRadioButton rbVista = new JRadioButton("\u00C0 vista");
		JRadioButton rbPrazo = new JRadioButton("A prazo");
		rbPrazo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		JLabel lbCodigoCliente = new JLabel("C\u00F3digo:");
		
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent arg0) {
				atualizarDados();
				atualizarTabelaVendas();
				lbUsuarioLogado.setText(""+Login.getUsuario().getNome()+" ("+Login.getUsuario().getIdUs()+")");
				vn.getUsuario().setIdUs(Login.getUsuario().getIdUs());
			}
		});
		
		setTitle("Vendas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 571, 708);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setLocationRelativeTo(null);
		
		JButton btBuscarCliente = new JButton("Buscar");
		btBuscarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bsCl.setVisible(true);
			}
		});
		btBuscarCliente.setBounds(442, 168, 89, 23);
		contentPane.add(btBuscarCliente);
		
		tfNomeCliente = new JTextField();
		tfNomeCliente.setEditable(false);
		tfNomeCliente.setColumns(10);
		tfNomeCliente.setBounds(170, 169, 262, 20);
		contentPane.add(tfNomeCliente);
		
		JLabel lbVenda = new JLabel("VENDA");
		lbVenda.setHorizontalAlignment(SwingConstants.CENTER);
		lbVenda.setFont(new Font("Lucida Sans", Font.BOLD, 22));
		lbVenda.setBounds(10, 11, 92, 35);
		contentPane.add(lbVenda);
		
		JSeparator sp1 = new JSeparator();
		sp1.setBounds(10, 44, 539, 2);
		contentPane.add(sp1);
		

		lbUsuarioLogado.setHorizontalAlignment(SwingConstants.RIGHT);
		lbUsuarioLogado.setForeground(Color.BLACK);
		lbUsuarioLogado.setFont(new Font("Dialog", Font.PLAIN, 15));
		lbUsuarioLogado.setBounds(331, 16, 218, 28);
		contentPane.add(lbUsuarioLogado);
		
		JLabel lbTipoVenda = new JLabel("Tipo de venda:");
		lbTipoVenda.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbTipoVenda.setHorizontalAlignment(SwingConstants.RIGHT);
		lbTipoVenda.setBounds(20, 57, 92, 14);
		contentPane.add(lbTipoVenda);
		
		rbPrazo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rbPrazo.isSelected()) {
					rbVista.setSelected(false);
					tfSituacao.setText("Pendente");
					
					boolean estado = true;
					lbCliente.setEnabled(estado);
					lbCodigoCliente.setEnabled(estado);
					tfCodigoCliente.setEnabled(estado);
					tfNomeCliente.setEnabled(estado);
					btBuscarCliente.setEnabled(estado);
				}
			}
		});

		rbPrazo.setSelected(true);
		buttonGroup.add(rbPrazo);
		rbPrazo.setBounds(128, 53, 109, 23);
		contentPane.add(rbPrazo);
		

		rbVista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rbVista.isSelected()) {
					rbPrazo.setSelected(false);
					tfSituacao.setText("Paga");
					
					boolean estado = false;
					lbCliente.setEnabled(estado);
					lbCodigoCliente.setEnabled(estado);
					tfCodigoCliente.setEnabled(estado);
					tfNomeCliente.setEnabled(estado);
					btBuscarCliente.setEnabled(estado);
				}
			}
		});
		buttonGroup.add(rbVista);
		rbVista.setBounds(251, 53, 109, 23);
		contentPane.add(rbVista);
		

		lbCliente.setHorizontalAlignment(SwingConstants.RIGHT);
		lbCliente.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbCliente.setBounds(-13, 146, 92, 14);
		contentPane.add(lbCliente);
		
		tfCodigoCliente = new JTextField();
		tfCodigoCliente.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				//Setar o campo tfNomeFornecedor com o nome do fornecedor: 
				if (!tfCodigoCliente.getText().isEmpty()) {
					cl.setIdCl(Integer.parseInt(tfCodigoCliente.getText()));
					try {
						tfNomeCliente.setText(vnDao.RetornarNomeCliente(cl));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					tfNomeCliente.setText("");
				}
			}
		});
		tfCodigoCliente.setBounds(88, 168, 72, 22);
		contentPane.add(tfCodigoCliente);
		tfCodigoCliente.setColumns(10);
		
		
		lbCodigoCliente.setHorizontalAlignment(SwingConstants.RIGHT);
		lbCodigoCliente.setBounds(32, 172, 46, 14);
		contentPane.add(lbCodigoCliente);
		
		tfFundoCliente = new JTextField();
		tfFundoCliente.setEnabled(false);
		tfFundoCliente.setEditable(false);
		tfFundoCliente.setBackground(SystemColor.controlHighlight);
		tfFundoCliente.setBounds(20, 135, 525, 68);
		contentPane.add(tfFundoCliente);
		tfFundoCliente.setColumns(10);
		
		JLabel lbSituacao = new JLabel("Situa\u00E7\u00E3o:");
		lbSituacao.setHorizontalAlignment(SwingConstants.RIGHT);
		lbSituacao.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbSituacao.setBounds(20, 93, 92, 14);
		contentPane.add(lbSituacao);
		
		tfSituacao = new JTextField();
		tfSituacao.setText("Pendente");
		tfSituacao.setEditable(false);
		tfSituacao.setBounds(128, 91, 114, 20);
		contentPane.add(tfSituacao);
		sp2.setBounds(10, 214, 539, 6);
		
		contentPane.add(sp2);
		
		JLabel lbProdutosDaVenda = new JLabel("Produtos da Venda");
		lbProdutosDaVenda.setForeground(Color.BLACK);
		lbProdutosDaVenda.setFont(new Font("Lucida Sans", Font.BOLD, 12));
		lbProdutosDaVenda.setBounds(20, 231, 140, 15);
		contentPane.add(lbProdutosDaVenda);
		
		JScrollPane scrollPaneProdutos = new JScrollPane();
		scrollPaneProdutos.setBounds(20, 257, 525, 101);
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
		
		
		JScrollPane scrollPaneVendas = new JScrollPane();
		scrollPaneVendas.setBounds(20, 482, 525, 132);
		contentPane.add(scrollPaneVendas);
		
		tbVendas = new JTable();
		tbVendas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				Venda vn = new Venda();
				vn.setIdVn(Integer.parseInt( (String) tbVendas.getValueAt(tbVendas.getSelectedRow(), 0)));
				
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
				
				setValorTotal();
			}
		});
		scrollPaneVendas.setViewportView(tbVendas);
		tbVendas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id Venda", "Id Usuario", "Tipo", "Situação", "Id Cliente", "Data"
			}
		));
		tbVendas.setAutoCreateRowSorter(true);
		tbVendas.getRowSorter().toggleSortOrder(5);
		tbVendas.getRowSorter().toggleSortOrder(5);
		
		JLabel lbValorTotal = new JLabel("Valor total:");
		lbValorTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lbValorTotal.setBounds(33, 369, 63, 14);
		contentPane.add(lbValorTotal);
		
		tfValorTotal = new JTextField();
		tfValorTotal.setEditable(false);
		tfValorTotal.setBounds(106, 366, 86, 20);
		contentPane.add(tfValorTotal);
		tfValorTotal.setColumns(10);
		
		sp3 = new JSeparator();
		sp3.setBounds(10, 439, 539, 6);
		contentPane.add(sp3);
		
		JLabel lblVendas = new JLabel("Vendas");
		lblVendas.setForeground(Color.BLACK);
		lblVendas.setFont(new Font("Lucida Sans", Font.BOLD, 12));
		lblVendas.setBounds(20, 456, 140, 15);
		contentPane.add(lblVendas);
		
		JButton btConfirmarVenda = new JButton("Confirmar Venda");
		btConfirmarVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ((tfCodigoCliente.getText().isEmpty() || tfNomeCliente.getText().equals("")) && rbPrazo.isSelected()) {
					JOptionPane.showMessageDialog(null, "Você deve selecionar um cliente válido");
				} else if(tbProdutos.getRowCount() == 0){
					JOptionPane.showMessageDialog(null, "Adicione produtos à sua venda");
				} else {
					vn.getUsuario().setIdUs(Login.getUsuario().getIdUs());
					vn.setSituacaoVn(tfSituacao.getText());
					vn.setDataVn(timestamp);
					
					if (rbPrazo.isSelected()) {
						
						vn.setTipoVn("prazo");
						vn.getCliente().setIdCl(Integer.parseInt(tfCodigoCliente.getText()));
						try {
							vnDao.CadastrarVenda(vn);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						vn.setTipoVn("vista");
						try {
							vnDao.CadastrarVendaSemCliente(vn);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				
				
					DefaultTableModel modelProdutos = (DefaultTableModel) tbProdutos.getModel();
					vn.setIdVn(Integer.parseInt(tfCodigoVenda.getText()));
					
					for(int i = 0; i < tbProdutos.getRowCount(); i++){
						String codigoProdutoEnviar = String.valueOf(modelProdutos.getValueAt(i, 0));
						vn.getProduto().setIdPr(Integer.parseInt(codigoProdutoEnviar));
						vn.getProduto().setValorUnitarioPr(Double.parseDouble((String) modelProdutos.getValueAt(i, 2)));
						try {
							vnDao.CadastrarProdutoVenda(vn);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					atualizarTabelaVendas();
					atualizarDados();
					}
				}
			}
		});
		btConfirmarVenda.setBounds(200, 405, 149, 23);
		contentPane.add(btConfirmarVenda);
		
		JButton btAdicionarProduto = new JButton("Adicionar");
		btAdicionarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BuscarProdutosVnView.ativar = false;
				
				bpview.setVisible(true);
				DefaultTableModel modelAdicionados = (DefaultTableModel) BuscarProdutosVnView.tableAdicionados.getModel();
				modelAdicionados.setNumRows(0);
				for(int i = 0; i<tbProdutos.getRowCount(); i++){
					//Linhas i
					modelAdicionados.addRow(new String[]{ (String) tbProdutos.getValueAt(i, 0), (String) tbProdutos.getValueAt(i, 1), (String) tbProdutos.getValueAt(i, 2)});
				}
				
			}
		});
		btAdicionarProduto.setBounds(251, 365, 89, 23);
		contentPane.add(btAdicionarProduto);
		
		JButton btRemoverProduto = new JButton("Remover");
		btRemoverProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel modelProdutos = (DefaultTableModel) tbProdutos.getModel();
				modelProdutos.removeRow(tbProdutos.getSelectedRow());
				setValorTotal();
			}
		});
		btRemoverProduto.setBounds(350, 365, 89, 23);
		contentPane.add(btRemoverProduto);
		
		tfCodigoVenda = new JTextField();
		tfCodigoVenda.setEditable(false);
		tfCodigoVenda.setBounds(463, 55, 86, 20);
		contentPane.add(tfCodigoVenda);
		tfCodigoVenda.setColumns(10);
		tfCodigoVenda.setVisible(false);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnVoltar.setBounds(200, 625, 149, 33);
		contentPane.add(btnVoltar);
		
		JButton btLimpar = new JButton("Limpar");
		btLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel modelProdutos = (DefaultTableModel) tbProdutos.getModel();
				modelProdutos.setRowCount(0);
				setValorTotal();
			}
		});
		btLimpar.setBounds(446, 365, 99, 23);
		contentPane.add(btLimpar);
		
		JButton btnBuscaAvanada = new JButton("Busca Avan\u00E7ada");
		btnBuscaAvanada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuscarVendaView buscVend = new BuscarVendaView();
				buscVend.setVisible(true);
			}
		});
		btnBuscaAvanada.setBounds(415, 618, 130, 28);
		contentPane.add(btnBuscaAvanada);
		

		
	}

	public void atualizarDados(){
		try {
			tfCodigoVenda.setText(String.valueOf(vnDao.RetornarProximoCodigoVenda()));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		if (tfCodigoVenda.getText().equals("0")) {
			tfCodigoVenda.setText("1");
		}
	}
	
	public void atualizarTabelaVendas() {
		try {
			venda = vnDao.buscarTodos();
			DefaultTableModel model = (DefaultTableModel) tbVendas.getModel();
			model.setNumRows(0);
		for (int x=0; x!=venda.size(); x++)
			{
				model.addRow((Object[]) venda.get(x));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void atualizarProdutos(){
		DefaultTableModel modelProdutos = (DefaultTableModel) tbProdutos.getModel();
		modelProdutos.setNumRows(0); 
		modelProdutos.rowsRemoved(null);
		
		for(int i = 0; i<BuscarProdutosVnView.tableAdicionados.getRowCount(); i++){
			//Linhas i
			modelProdutos.addRow(new String[]{ (String) BuscarProdutosVnView.tableAdicionados.getValueAt(i, 0), (String) BuscarProdutosVnView.tableAdicionados.getValueAt(i, 1), (String) BuscarProdutosVnView.tableAdicionados.getValueAt(i, 2)});
			
		}
	}
	
	public void setValorTotal(){
		Double valorTotal = 0.0;
		for(int i = 0; i<tbProdutos.getRowCount(); i++){
			valorTotal += Double.parseDouble((String) tbProdutos.getValueAt(i, 2));
		}
		tfValorTotal.setText(String.valueOf(valorTotal));
	}
}
