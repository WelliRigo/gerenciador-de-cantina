package br.edu.ifcvideira.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.edu.ifcvideira.beans.Aquisicao;
import br.edu.ifcvideira.beans.Cliente;
import br.edu.ifcvideira.beans.Venda;
import br.edu.ifcvideira.utils.Conexao;

public class VendaDao {
	public void CadastrarVenda(Venda vn) throws SQLException, Exception{
		try{
			String sql = "INSERT INTO vendas (id_usuario_venda, tipo_venda, situacao_venda, id_cliente_venda, data_venda) VALUES (?,?,?,?,?)";
			java.sql.PreparedStatement sqlPrep = Conexao.conectar().prepareStatement(sql);
			int contador = 1;
			sqlPrep.setInt(contador++, vn.getUsuario().getIdUs());
			sqlPrep.setString(contador++, vn.getTipoVn());
			sqlPrep.setString(contador++, vn.getSituacaoVn());
			sqlPrep.setInt(contador++, vn.getCliente().getIdCl());
			sqlPrep.setTimestamp(contador++, vn.getDataVn());
			
			sqlPrep.execute();
			
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void CadastrarVendaSemCliente(Venda vn) throws SQLException, Exception{
		try{
			String sql = "INSERT INTO vendas (id_usuario_venda, tipo_venda, situacao_venda, data_venda) VALUES (?,?,?,?)";
			java.sql.PreparedStatement sqlPrep = Conexao.conectar().prepareStatement(sql);
			int contador = 1;
			sqlPrep.setInt(contador++, vn.getUsuario().getIdUs());
			sqlPrep.setString(contador++, vn.getTipoVn());
			sqlPrep.setString(contador++, vn.getSituacaoVn());
			sqlPrep.setTimestamp(contador++, vn.getDataVn());
			
			sqlPrep.execute();
			
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public List<Object> buscarTodos() throws SQLException, Exception{
		List<Object> venda = new ArrayList<Object>();
		try {
			String sql = "SELECT * FROM vendas";
			java.sql.Statement state = Conexao.conectar().createStatement();
			ResultSet rs = state.executeQuery(sql);
			
			while (rs.next())
			{
				Object[] linha = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)};
				venda.add(linha);
			}
			state.close();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return venda;
	}
	
	public int RetornarProximoCodigoVenda() throws Exception {
		try{
			String sql ="SELECT MAX(id_venda)+1 AS id_venda FROM vendas ";
			PreparedStatement sqlPrep = Conexao.conectar().prepareStatement(sql);
			ResultSet rs = sqlPrep.executeQuery();
			if (rs.next()){
				return rs.getInt("id_venda");
			}else{
				return 1;
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return 1;
		}
	}
	
	public String RetornarNomeCliente(Cliente cl) throws Exception {
		try{
			String sql ="SELECT nome_cliente FROM clientes where id_cliente=?";
			PreparedStatement sqlPrep = Conexao.conectar().prepareStatement(sql);
			sqlPrep.setInt(1, cl.getIdCl());
			
			ResultSet rs = sqlPrep.executeQuery();
			
			if (rs.next()){
				return rs.getString("nome_cliente");
			}else{
				return "";
			}

		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return "Erro pegar nome cliente";
		}
	}
	
	//BuscarVendaView ----------------------------------------------------
	public List<Object> buscarVendasComNomePrazo() throws SQLException, Exception{
		List<Object> venda = new ArrayList<Object>();
		try {
			String sql = "SELECT "
					+ "vendas.id_venda, "
					+ "usuarios.id_usuario, "
					+ "usuarios.nome_usuario, "
					+ "clientes.id_cliente, "
					+ "clientes.nome_cliente, "
					+ "vendas.tipo_venda, "
					+ "vendas.situacao_venda, "
					+ "vendas.data_venda "
					+ " FROM vendas INNER JOIN usuarios ON vendas.id_usuario_venda = usuarios.id_usuario INNER JOIN clientes ON vendas.id_cliente_venda = clientes.id_cliente";
			java.sql.Statement state = Conexao.conectar().createStatement();
			ResultSet rs = state.executeQuery(sql);
			
			while (rs.next())
			{
				Object[] linha = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)};
				venda.add(linha);
			}
			state.close();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return venda;
	}
	
	public List<Object> buscarVendasComNomeVista() throws SQLException, Exception{
		List<Object> venda = new ArrayList<Object>();
		try {
			String sql = "SELECT "
					+ "vendas.id_venda, "
					+ "usuarios.id_usuario, "
					+ "usuarios.nome_usuario, "
					+ "vendas.tipo_venda, "
					+ "vendas.situacao_venda, "
					+ "vendas.data_venda "
					+ "FROM vendas INNER JOIN usuarios ON vendas.id_usuario_venda = usuarios.id_usuario AND tipo_venda = 'vista';";

			java.sql.Statement state = Conexao.conectar().createStatement();
			ResultSet rs = state.executeQuery(sql);
			
			while (rs.next())
			{
				Object[] linha = {rs.getString(1), rs.getString(2), rs.getString(3), null, null, rs.getString(4), rs.getString(5), rs.getString(6)};
				venda.add(linha);
			}
			state.close();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return venda;
	}
	
	public void mudarSituacao(int idVenda, String situacao) throws SQLException, Exception{
		try{
			String sql = "UPDATE vendas set situacao_venda = ? where id_venda=?";
			java.sql.PreparedStatement sqlPrep = Conexao.conectar().prepareStatement(sql);
			sqlPrep.setString(1, situacao);
			sqlPrep.setInt(2, idVenda);
			sqlPrep.execute();
			
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	//Produto Venda ---------------------------------------------------------
	public void CadastrarProdutoVenda(Venda vn) throws SQLException, Exception{
		try{
			String sql = "INSERT INTO produtos_venda (id_produto_produtos_venda, id_venda_produtos_venda, valor_unitario_produtos_venda) VALUES (?,?,?)";
			java.sql.PreparedStatement sqlPrep = Conexao.conectar().prepareStatement(sql);
			int contador = 1;
			sqlPrep.setInt(contador++, vn.getProduto().getIdPr());
			sqlPrep.setInt(contador++, vn.getIdVn());
			sqlPrep.setDouble(contador++, vn.getProduto().getValorUnitarioPr());
			
			sqlPrep.execute();
			
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
	}
	
	public List<Object> buscarProdutosVenda(Venda vn) throws Exception{
		List<Object> venda = new ArrayList<Object>();
		try {
			String sql = "SELECT produtos_venda.id_produto_produtos_venda, produtos.nome_produto, produtos_venda.valor_unitario_produtos_venda FROM  produtos_venda JOIN produtos ON  (produtos_venda.id_venda_produtos_venda = (?)) AND (produtos_venda.id_produto_produtos_venda = produtos.id_produto)";
			
			PreparedStatement sqlPrep = Conexao.conectar().prepareStatement(sql);
			sqlPrep.setInt(1, vn.getIdVn());
			ResultSet rs = sqlPrep.executeQuery();
			while (rs.next())
			{
				Object[] linha = {rs.getString(1), rs.getString(2), rs.getString(3)};
				venda.add(linha);
			}
			sqlPrep.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return venda;
	}
	
	public void deletarVenda(Venda vn) throws Exception{
		try{
			String sql = "DELETE FROM vendas WHERE id_venda=?";
			PreparedStatement sqlPrep = (PreparedStatement) Conexao.conectar().prepareStatement(sql);
			sqlPrep.setInt(1, vn.getIdVn());
			sqlPrep.execute();
		} catch (SQLException e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
