package br.edu.ifcvideira.controllers.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import br.edu.ifcvideira.DAOs.AquisicaoDao;
import br.edu.ifcvideira.DAOs.FornecedorDao;
import br.edu.ifcvideira.DAOs.ProdutoDao;
import br.edu.ifcvideira.beans.Aquisicao;
import br.edu.ifcvideira.beans.Fornecedor;
import br.edu.ifcvideira.beans.Login;
import br.edu.ifcvideira.beans.Produto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;
import java.awt.Toolkit;

public class AquisicaoView extends JFrame {

	private JPanel contentPane;
	static JTextField tfCodigoFornecedor;
	private JTextField tfNomeFornecedor;
	static JTextField tfCodigoProduto;
	private JTextField tfNomeProduto;
	private JTable tbAquisicoes;
	
	Fornecedor fr = new Fornecedor();
	FornecedorDao frDao = new FornecedorDao();
	Aquisicao aq = new Aquisicao();
	AquisicaoDao aqDao = new AquisicaoDao();
	Produto pr = new Produto();
	ProdutoDao prDao = new ProdutoDao();

	private List<Object> aquisicao = new ArrayList<Object>();
	private JTextField tfValorUnitario;
	
	JSpinner spQuantidade = new JSpinner();
	
	long time = System.currentTimeMillis();
	Timestamp timestamp = new Timestamp(time);
	private JTextField tfCodigoAquisicaoP;
	private JTextField tfIdProdutoP;
	
	BuscarFornecedorAqView bf = new BuscarFornecedorAqView();
	BuscarProdutoAqView bp = new BuscarProdutoAqView();
	
	JLabel lbUsuarioLogado = new JLabel("Ol\u00E1");
	private JTextField tfNomeProdutoP;
	private JTextField tfIdFornecedorP;
	private JTextField tfNomeFornecedorP;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AquisicaoView frame = new AquisicaoView();
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
	public AquisicaoView() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AquisicaoView.class.getResource("/br/edu/ifcvideira/imgs/icone1 laranja.png")));
		setResizable(false);
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent arg0) {
				atualizarTabela();
				lbUsuarioLogado.setText(""+Login.getUsuario().getNome()+" ("+Login.getUsuario().getIdUs()+")");
			}
		});
		setTitle("Aquisi\u00E7\u00F5es");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 655);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Coloca janela no meio da tela
		this.setLocationRelativeTo(null);
		
		JLabel lbAquisicao = new JLabel("AQUISI\u00C7\u00C3O");
		lbAquisicao.setBounds(12, 12, 127, 35);
		lbAquisicao.setHorizontalAlignment(SwingConstants.CENTER);
		lbAquisicao.setFont(new Font("Lucida Sans", Font.BOLD, 22));
		contentPane.add(lbAquisicao);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 45, 642, 2);
		contentPane.add(separator);
		
		JLabel lbFornecedor = new JLabel("FORNECEDOR");
		lbFornecedor.setBounds(12, 58, 85, 15);
		lbFornecedor.setForeground(Color.BLACK);
		lbFornecedor.setFont(new Font("Lucida Sans", Font.BOLD, 12));
		contentPane.add(lbFornecedor);
		
		tfCodigoFornecedor = new JTextField();
		tfCodigoFornecedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				//Permitir somente números
				
				boolean teclaCerta = false;
                /* aceita somente números */
                if (Character.isDigit(e.getKeyChar())) {
                       teclaCerta = true;
                 }
                /* se não for um digito no jTextFied o evento é consumido*/
	            if (!teclaCerta) {
	                  e.consume();
	             }
			}
		});
		tfCodigoFornecedor.setBounds(79, 81, 73, 20);
		tfCodigoFornecedor.addCaretListener(new CaretListener() {
			
			//Evento para qualquer alteração
			public void caretUpdate(CaretEvent arg0) {
				//Procurar por código do fornecedor
				
				//Setar o campo tfNomeFornecedor com o nome do fornecedor buscando no AquisicaoDao com o método RetornarNomeFornecedor: 
				if (!tfCodigoFornecedor.getText().isEmpty()) {
					fr.setIdFr(Integer.parseInt(tfCodigoFornecedor.getText()));
					try {
						tfNomeFornecedor.setText(String.valueOf(aqDao.RetornarNomeFornecedor(fr)));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					tfNomeFornecedor.setText("");
				}
			}
		});
		contentPane.add(tfCodigoFornecedor);
		tfCodigoFornecedor.setColumns(10);
		
		JLabel lbCodigoFornecedor = new JLabel("C\u00F3digo:");
		lbCodigoFornecedor.setHorizontalAlignment(SwingConstants.RIGHT);
		lbCodigoFornecedor.setBounds(12, 84, 57, 14);
		contentPane.add(lbCodigoFornecedor);
		
		tfNomeFornecedor = new JTextField();
		tfNomeFornecedor.setBounds(162, 81, 398, 20);
		tfNomeFornecedor.setEditable(false);
		contentPane.add(tfNomeFornecedor);
		tfNomeFornecedor.setColumns(10);
		
		JButton btBuscarFornecedor = new JButton("Buscar");
		btBuscarFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Abrir janela "BuscarFornecedorView"
				bf.setVisible(true);
			}
		});
		btBuscarFornecedor.setBounds(570, 80, 84, 23);
		contentPane.add(btBuscarFornecedor);
		
		JLabel lbCodigoProduto = new JLabel("C\u00F3digo:");
		lbCodigoProduto.setHorizontalAlignment(SwingConstants.RIGHT);
		lbCodigoProduto.setBounds(12, 144, 57, 14);
		contentPane.add(lbCodigoProduto);
		
		tfCodigoProduto = new JTextField();
		tfCodigoProduto.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				boolean teclaCerta = false;
                /* aceita somente números */
                if (Character.isDigit(e.getKeyChar())) {
                       teclaCerta = true;
                }
              /* se não for um digito no jTextFied o evento é consumido*/
	            if (!teclaCerta) {
	                  e.consume();
	             }
			}
		});
		tfCodigoProduto.addCaretListener(new CaretListener() {
			//Qualquer alteração no campo tfCodigoProduto:
			public void caretUpdate(CaretEvent arg0) {
				
				//Setar o campo tfNomeFornecedor com o nome do produto: 
				if (!tfCodigoProduto.getText().isEmpty()) {
					pr.setIdPr(Integer.parseInt(tfCodigoProduto.getText()));
					try {
						tfNomeProduto.setText(String.valueOf(prDao.RetornarNomeProduto(pr)));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					tfNomeProduto.setText("");
				}
				
			}
		});
		tfCodigoProduto.setBounds(81, 141, 73, 20);
		tfCodigoProduto.setColumns(10);
		contentPane.add(tfCodigoProduto);
		
		tfNomeProduto = new JTextField();
		tfNomeProduto.setBounds(162, 141, 398, 20);
		tfNomeProduto.setEditable(false);
		tfNomeProduto.setColumns(10);
		contentPane.add(tfNomeProduto);
		
		JButton btProduto = new JButton("Buscar");
		btProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Abre a janela "Buscar Produto View"
				bp.setVisible(true);
			}
		});
		btProduto.setBounds(570, 140, 84, 23);
		contentPane.add(btProduto);
		
		JLabel lbProduto = new JLabel("PRODUTO");
		lbProduto.setBounds(12, 118, 62, 15);
		lbProduto.setForeground(Color.BLACK);
		lbProduto.setFont(new Font("Lucida Sans", Font.BOLD, 12));
		contentPane.add(lbProduto);
		
		JButton btCadastrarAquisicao = new JButton("Cadastrar Aquisi\u00E7\u00E3o");
		btCadastrarAquisicao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Cadastrar aquisição
				//Se o campo tfCodigoFornecedor, tfCodigoProduto ou tfValorUnitario estiver vazio, exibir mensagem
				if( tfCodigoFornecedor.getText().isEmpty() || tfCodigoProduto.getText().isEmpty() || tfValorUnitario.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Você deve preencher todos os campos!");
				} else {
					//Passar dados para objeto Aq (Aquisicao)
					aq.getFornecedor().setIdFr(Integer.parseInt(tfCodigoFornecedor.getText()));
					aq.getProduto().setIdPr(Integer.parseInt(tfCodigoProduto.getText()));
					aq.setQuantidadeAq( (int) spQuantidade.getValue());
					aq.getUsuario().setIdUs(Login.getUsuario().getIdUs());
					aq.setValorUnitarioAq(Double.parseDouble(tfValorUnitario.getText()));
					aq.setDataAq(timestamp);
					
					try {
						//Cadastrar Aquisição passando o objeto Aq
						aqDao.CadastrarAquisicao(aq);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					atualizarTabela();
				}
			}
		});
		btCadastrarAquisicao.setBounds(266, 245, 160, 23);
		contentPane.add(btCadastrarAquisicao);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 389, 642, 189);
		contentPane.add(scrollPane);
		
		tbAquisicoes = new JTable();
		tbAquisicoes.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				setCamposFromTabela();
			}
		});
		scrollPane.setColumnHeaderView(tbAquisicoes);
		scrollPane.setViewportView(tbAquisicoes);
		tbAquisicoes.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id Aq", "Id Fr", "Nome Fr", "Id Pr", "Nome Pr", "Quantidade", "Id Us", "Nome Us", "Valor Unit\u00E1rio", "Data"
			}
			
		));
		
		JLabel lbQuantidade = new JLabel("Quantidade:");
		lbQuantidade.setHorizontalAlignment(SwingConstants.RIGHT);
		lbQuantidade.setBounds(72, 198, 84, 17);
		contentPane.add(lbQuantidade);
		
		
		spQuantidade.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spQuantidade.setBounds(166, 196, 85, 20);
		contentPane.add(spQuantidade);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(12, 184, 642, 2);
		contentPane.add(separator_1);
		
		JLabel lbValorUnitario = new JLabel("Valor Unit\u00E1rio:");
		lbValorUnitario.setHorizontalAlignment(SwingConstants.RIGHT);
		lbValorUnitario.setBounds(304, 199, 95, 14);
		contentPane.add(lbValorUnitario);
		
		tfValorUnitario = new JTextField();
		tfValorUnitario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				boolean teclaCerta = false;
                Character ch = e.getKeyChar();
                /* aceita números e ponto (.) , por exemplo 12.36, mas não 12,6 */
                if (Character.isDigit(e.getKeyChar()) || String.valueOf(ch).equals(".")) {
                       teclaCerta = true;
                 }
                /* se não for um digito no jTextFied o evento é consumido*/
	            if (!teclaCerta) {
	                  e.consume();
	             }
			}
		});
		tfValorUnitario.setBounds(409, 196, 85, 20);
		contentPane.add(tfValorUnitario);
		tfValorUnitario.setColumns(10);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(12, 279, 642, 8);
		contentPane.add(separator_2);
		
		JLabel lbBuscar = new JLabel("BUSCAR");
		lbBuscar.setForeground(Color.BLACK);
		lbBuscar.setFont(new Font("Lucida Sans", Font.BOLD, 12));
		lbBuscar.setBounds(12, 291, 62, 15);
		contentPane.add(lbBuscar);
		
		JLabel lbCodigoAquisicaoP = new JLabel("Id Aquisi\u00E7\u00E3o:");
		lbCodigoAquisicaoP.setHorizontalAlignment(SwingConstants.CENTER);
		lbCodigoAquisicaoP.setBounds(525, 317, 114, 16);
		contentPane.add(lbCodigoAquisicaoP);
		
		JLabel lbCodigoProdutoP = new JLabel("Id Produto:");
		lbCodigoProdutoP.setHorizontalAlignment(SwingConstants.RIGHT);
		lbCodigoProdutoP.setBounds(22, 347, 108, 16);
		contentPane.add(lbCodigoProdutoP);
		
		tfCodigoAquisicaoP = new JTextField();
		tfCodigoAquisicaoP.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				//atualizar a tabela apenas com valores correspondentes aos digitados no campo de busca por codigo
				TableRowSorter<TableModel> filtro = null;  
				DefaultTableModel model = (DefaultTableModel) tbAquisicoes.getModel();  
				filtro = new TableRowSorter<TableModel>(model);  
				tbAquisicoes.setRowSorter(filtro);
				
				if (tfCodigoAquisicaoP.getText().length() == 0) {
					filtro.setRowFilter(null);
					ordenarPorData();
				} else {  
					filtro.setRowFilter(RowFilter.regexFilter(tfCodigoAquisicaoP.getText(), 0));  
				}  
			}
		});
		tfCodigoAquisicaoP.setBounds(525, 335, 114, 23);
		contentPane.add(tfCodigoAquisicaoP);
		tfCodigoAquisicaoP.setColumns(10);
		
		tfIdProdutoP = new JTextField();
		tfIdProdutoP.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				//atualizar a tabela apenas com valores correspondentes aos digitados no campo de busca por codigo
				TableRowSorter<TableModel> filtro = null;  
				DefaultTableModel model = (DefaultTableModel) tbAquisicoes.getModel();  
				filtro = new TableRowSorter<TableModel>(model);  
				tbAquisicoes.setRowSorter(filtro);
				
				if (tfIdProdutoP.getText().length() == 0) {
					filtro.setRowFilter(null);
					ordenarPorData();
				} else {  
					filtro.setRowFilter(RowFilter.regexFilter(tfIdProdutoP.getText(), 3));  
				}  
			}
		});
		tfIdProdutoP.setColumns(10);
		tfIdProdutoP.setBounds(140, 345, 114, 20);
		contentPane.add(tfIdProdutoP);
		
		JButton btLimpar = new JButton("Limpar");
		btLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpar();
			}
		});
		btLimpar.setBounds(559, 245, 95, 23);
		contentPane.add(btLimpar);
		

		lbUsuarioLogado.setHorizontalAlignment(SwingConstants.RIGHT);
		lbUsuarioLogado.setForeground(Color.BLACK);
		lbUsuarioLogado.setFont(new Font("Dialog", Font.PLAIN, 15));
		lbUsuarioLogado.setBounds(436, 17, 218, 28);
		contentPane.add(lbUsuarioLogado);
		
		JButton btVoltar = new JButton("Voltar");
		btVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btVoltar.setBackground(SystemColor.controlHighlight);
		btVoltar.setBounds(255, 589, 166, 31);
		contentPane.add(btVoltar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tbAquisicoes.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null, "Selecione a aquisição que deseja excluir");
				} else {
					//Verifica se a aquisicao foi cadastrada a mais de 30 minutos (30 minutos = 1800000 milisegundos)
					//Se sim, não exclui. Se não, exclui.
					time = System.currentTimeMillis();			
					Timestamp tempo = new Timestamp(time - 1800000);
					Timestamp dataAquisicao = Timestamp.valueOf((String) tbAquisicoes.getValueAt(tbAquisicoes.getSelectedRow(), 6));
					if (dataAquisicao.before(tempo)){
						JOptionPane.showMessageDialog(null, "Você não pode excluir uma aquisição feita há mais de 30 minutos");
					} else {
						aq.setIdAq(Integer.parseInt((String) tbAquisicoes.getValueAt(tbAquisicoes.getSelectedRow(), 0)));
						try {
							aqDao.deletarAquisicao(aq);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						atualizarTabela();
					}
				}
			}
		});
		btnExcluir.setBounds(12, 245, 89, 23);
		contentPane.add(btnExcluir);
		
		tfNomeProdutoP = new JTextField();
		tfNomeProdutoP.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				//atualizar a tabela apenas com valores correspondentes aos digitados no campo de busca por codigo
				TableRowSorter<TableModel> filtro = null;  
				DefaultTableModel model = (DefaultTableModel) tbAquisicoes.getModel();  
				filtro = new TableRowSorter<TableModel>(model);  
				tbAquisicoes.setRowSorter(filtro);
				
				if (tfNomeProdutoP.getText().length() == 0) {
					filtro.setRowFilter(null);
					ordenarPorData();
				} else {  
					filtro.setRowFilter(RowFilter.regexFilter(tfNomeProdutoP.getText(), 4));  
				} 
			}
		});
		tfNomeProdutoP.setColumns(10);
		tfNomeProdutoP.setBounds(380, 343, 114, 20);
		contentPane.add(tfNomeProdutoP);
		
		JLabel lblNomeProduto = new JLabel("Nome Produto:");
		lblNomeProduto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNomeProduto.setBounds(266, 345, 104, 16);
		contentPane.add(lblNomeProduto);
		
		JLabel lblIdFornecedor = new JLabel("Id Fornecedor:");
		lblIdFornecedor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIdFornecedor.setBounds(22, 317, 108, 16);
		contentPane.add(lblIdFornecedor);
		
		tfIdFornecedorP = new JTextField();
		tfIdFornecedorP.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				//atualizar a tabela apenas com valores correspondentes aos digitados no campo de busca por codigo
				TableRowSorter<TableModel> filtro = null;  
				DefaultTableModel model = (DefaultTableModel) tbAquisicoes.getModel();  
				filtro = new TableRowSorter<TableModel>(model);  
				tbAquisicoes.setRowSorter(filtro);
				
				if (tfIdFornecedorP.getText().length() == 0) {
					filtro.setRowFilter(null);
					ordenarPorData();
				} else {  
					filtro.setRowFilter(RowFilter.regexFilter(tfIdFornecedorP.getText(), 1));  
				} 
			}
		});
		tfIdFornecedorP.setColumns(10);
		tfIdFornecedorP.setBounds(140, 315, 114, 20);
		contentPane.add(tfIdFornecedorP);
		
		JLabel lblNomeFornecedor = new JLabel("Nome Fornecedor:");
		lblNomeFornecedor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNomeFornecedor.setBounds(255, 317, 115, 16);
		contentPane.add(lblNomeFornecedor);
		
		tfNomeFornecedorP = new JTextField();
		tfNomeFornecedorP.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				//atualizar a tabela apenas com valores correspondentes aos digitados no campo de busca
				TableRowSorter<TableModel> filtro = null;  
				DefaultTableModel model = (DefaultTableModel) tbAquisicoes.getModel();  
				filtro = new TableRowSorter<TableModel>(model);  
				tbAquisicoes.setRowSorter(filtro);
				
				if (tfNomeFornecedorP.getText().length() == 0) {
					filtro.setRowFilter(null);
					ordenarPorData();
				} else {  
					filtro.setRowFilter(RowFilter.regexFilter(tfNomeFornecedorP.getText(), 2));  
				} 
			}
		});
		tfNomeFornecedorP.setColumns(10);
		tfNomeFornecedorP.setBounds(380, 315, 114, 20);
		contentPane.add(tfNomeFornecedorP);
		
		ordenarPorData();

	}
	public void setCamposFromTabela() {
		//Setar campos de acordo com linha selecionada na tbAquisicoes
		tfCodigoFornecedor.setText(String.valueOf(tbAquisicoes.getValueAt(tbAquisicoes.getSelectedRow(), 1)));
		tfCodigoProduto.setText(String.valueOf(tbAquisicoes.getValueAt(tbAquisicoes.getSelectedRow(), 2)));
		spQuantidade.setValue(Integer.parseInt((String) tbAquisicoes.getValueAt(tbAquisicoes.getSelectedRow(), 3)));
		tfValorUnitario.setText(String.valueOf(tbAquisicoes.getValueAt(tbAquisicoes.getSelectedRow(), 5)));
	}
	
	public void atualizarTabela() {
		//Atualizar a tabela tbAquisicoes
		try {
			aquisicao = aqDao.buscarTodos();
			DefaultTableModel model = (DefaultTableModel) tbAquisicoes.getModel();
			model.setNumRows(0);
		for (int x=0; x!=aquisicao.size(); x++)
			{
				model.addRow((Object[]) aquisicao.get(x));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void limpar(){
		tfCodigoFornecedor.setText(null);
		tfCodigoProduto.setText(null);
		tfNomeFornecedor.setText(null);
		tfNomeProduto.setText(null);
		spQuantidade.setValue(1);
		tfValorUnitario.setText("");
	}
	
	public void ordenarPorData(){
		tbAquisicoes.setAutoCreateRowSorter(true);
		tbAquisicoes.getRowSorter().toggleSortOrder(9);
		tbAquisicoes.getRowSorter().toggleSortOrder(9);
	}
}
