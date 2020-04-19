package br.edu.ifcvideira.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.edu.ifcvideira.beans.Produto;
import br.edu.ifcvideira.utils.Conexao;

public class ProdutoDao {
	public void CadastrarProduto(Produto pr) throws SQLException, Exception{
		try{
			String sql = "INSERT INTO produtos (nome_produto, valor_unitario_produto) VALUES (?,?)";
			java.sql.PreparedStatement sqlPrep = Conexao.conectar().prepareStatement(sql);
			int contador = 1;
			sqlPrep.setString(contador++, pr.getNomePr());
			sqlPrep.setDouble(contador++, pr.getValorUnitarioPr());
			sqlPrep.execute();
			
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
	}
	
	public void AlterarProduto(Produto pr) throws Exception {
		try{
			String sql = "UPDATE produtos SET nome_produto=?, valor_unitario_produto=? WHERE id_produto=?";
			PreparedStatement sqlPrep = Conexao.conectar().prepareStatement(sql);
			int contador = 1;
			sqlPrep.setString(contador++, pr.getNomePr());
			sqlPrep.setDouble(contador++, pr.getValorUnitarioPr());
			sqlPrep.setInt(contador++, pr.getIdPr());
			sqlPrep.execute();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void deletarProduto(Produto fr) throws Exception{
		try{
			String sql = "DELETE FROM produtos WHERE id_produto=? ";
			PreparedStatement sqlPrep = (PreparedStatement) Conexao.conectar().prepareStatement(sql);
			sqlPrep.setInt(1, fr.getIdPr());
			sqlPrep.execute();
		} catch (SQLException e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public List<Object> buscarTodos() throws SQLException, Exception{
		List<Object> produto = new ArrayList<Object>();
		try {
			String sql = "SELECT * FROM produtos";
			java.sql.Statement state = Conexao.conectar().createStatement();
			ResultSet rs = state.executeQuery(sql);
			
			while (rs.next())
			{
				Object[] linha = {rs.getString(1), rs.getString(2), rs.getString(3)};
				produto.add(linha);
			}
			state.close();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return produto;
	}
	
	public int RetornarProximoCodigoProduto() throws Exception {
		try{
			String sql ="SELECT MAX(id_produto)+1 AS id_produto FROM produtos";
			PreparedStatement sqlPrep = Conexao.conectar().prepareStatement(sql);
			ResultSet rs = sqlPrep.executeQuery();
			if (rs.next()){
				return rs.getInt("id_produto");
			}else{
				return 1;
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return 1;
		}
	}
	
	public String RetornarNomeProduto(Produto pr) throws Exception {
		try{
			String sql ="SELECT nome_produto FROM produtos where id_produto=?";
			PreparedStatement sqlPrep = Conexao.conectar().prepareStatement(sql);
			sqlPrep.setInt(1, pr.getIdPr());
			
			ResultSet rs = sqlPrep.executeQuery();

			if (rs.next()){
				return rs.getString("nome_produto");
			}else{
				return "";
			}

		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return "Erro02";
		}
	}
}
