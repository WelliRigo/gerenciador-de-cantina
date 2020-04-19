package br.edu.ifcvideira.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.edu.ifcvideira.beans.Aquisicao;
import br.edu.ifcvideira.beans.Fornecedor;
import br.edu.ifcvideira.beans.Produto;
import br.edu.ifcvideira.utils.Conexao;

public class AquisicaoDao {
	public void CadastrarAquisicao(Aquisicao aq) throws SQLException, Exception{
		try{
			String sql = "INSERT INTO aquisicoes (id_fornecedor_aquisicao, id_produto_aquisicao, quantidade_aquisicao, id_usuario_aquisicao, valor_unitario_aquisicao, data_aquisicao) VALUES (?,?,?,?,?,?)";
			java.sql.PreparedStatement sqlPrep = Conexao.conectar().prepareStatement(sql);
			int contador = 1;
			sqlPrep.setInt(contador++, aq.getFornecedor().getIdFr());
			sqlPrep.setInt(contador++, aq.getProduto().getIdPr());
			sqlPrep.setInt(contador++, aq.getQuantidadeAq());
			sqlPrep.setInt(contador++, aq.getUsuario().getIdUs());
			sqlPrep.setDouble(contador++, aq.getValorUnitarioAq());
			sqlPrep.setTimestamp(contador++, aq.getDataAq());
			
			sqlPrep.execute();
			
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
	}
	
	public List<Object> buscarTodos() throws SQLException, Exception{
		List<Object> aquisicao = new ArrayList<Object>();
		try {
			String sql = "SELECT aquisicoes.id_aquisicao, aquisicoes.id_fornecedor_aquisicao, fornecedores.nome_fornecedor, aquisicoes.id_produto_aquisicao, produtos.nome_produto, aquisicoes.quantidade_aquisicao, "
					+ "aquisicoes.id_usuario_aquisicao, usuarios.nome_usuario, aquisicoes.valor_unitario_aquisicao, aquisicoes.data_aquisicao"
					+ " FROM aquisicoes INNER JOIN fornecedores ON aquisicoes.id_fornecedor_aquisicao = fornecedores.id_fornecedor"
					+ " INNER JOIN produtos ON aquisicoes.id_produto_aquisicao = produtos.id_produto"
					+ " INNER JOIN usuarios ON aquisicoes.id_usuario_aquisicao = usuarios.id_usuario;";
			java.sql.Statement state = Conexao.conectar().createStatement();
			ResultSet rs = state.executeQuery(sql);
			
			while (rs.next())
			{
				Object[] linha = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)};
				aquisicao.add(linha);
			}
			state.close();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return aquisicao;
	}
	 
	
	public int RetornarProximoCodigoAquisicao() throws Exception {
		try{
			String sql ="SELECT MAX(id_aquisicao)+1 AS id_aquisicao FROM aquisicoes ";
			PreparedStatement sqlPrep = Conexao.conectar().prepareStatement(sql);
			ResultSet rs = sqlPrep.executeQuery();
			if (rs.next()){
				return rs.getInt("id_aquisicao");
			}else{
				return 1;
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return 1;
		}
	}
	
	public String RetornarNomeFornecedor(Fornecedor fr) throws Exception {
		try{
			String sql ="SELECT nome_fornecedor FROM fornecedores where id_fornecedor=?";
			PreparedStatement sqlPrep = Conexao.conectar().prepareStatement(sql);
			sqlPrep.setInt(1, fr.getIdFr());
			
			ResultSet rs = sqlPrep.executeQuery();
			
			if (rs.next()){
				return rs.getString("nome_fornecedor");
			}else{
				return "";
			}

		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return "Erro02";
		}
	}
	
	public void deletarAquisicao(Aquisicao aq) throws Exception{
		try{
			String sql = "DELETE FROM aquisicoes WHERE id_aquisicao=?";
			PreparedStatement sqlPrep = (PreparedStatement) Conexao.conectar().prepareStatement(sql);
			sqlPrep.setInt(1, aq.getIdAq());
			sqlPrep.execute();
		} catch (SQLException e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

}
