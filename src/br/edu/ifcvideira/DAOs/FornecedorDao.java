package br.edu.ifcvideira.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.edu.ifcvideira.beans.Fornecedor;
import br.edu.ifcvideira.utils.Conexao;

public class FornecedorDao {
	public void CadastrarFornecedor(Fornecedor fr) throws SQLException, Exception{
		try{
			String sql = "INSERT INTO fornecedores (nome_fornecedor, cnpj_fornecedor) VALUES (?,?)";
			java.sql.PreparedStatement sqlPrep = Conexao.conectar().prepareStatement(sql);
			int contador = 1;
			sqlPrep.setString(contador++, fr.getNomeFr());
			sqlPrep.setString(contador++, fr.getCnpjFr());
			sqlPrep.execute();
			
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
	}

	public void AlterarCliente(Fornecedor fr) throws Exception {
		try{
			String sql = "UPDATE fornecedores SET nome_fornecedor=?, cnpj_fornecedor=? WHERE id_fornecedor=?";
			PreparedStatement sqlPrep = Conexao.conectar().prepareStatement(sql);
			int contador = 1;
			sqlPrep.setString(contador++, fr.getNomeFr());
			sqlPrep.setString(contador++, fr.getCnpjFr());
			sqlPrep.setInt(contador++, fr.getIdFr());
			sqlPrep.execute();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}  

	public void deletarFornecedor(Fornecedor fr) throws Exception{
		try{
			String sql = "DELETE FROM fornecedores WHERE id_fornecedor=? ";
			PreparedStatement sqlPrep = (PreparedStatement) Conexao.conectar().prepareStatement(sql);
			sqlPrep.setInt(1, fr.getIdFr());
			sqlPrep.execute();
		} catch (SQLException e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public List<Object> buscarTodos() throws SQLException, Exception{
		List<Object> fornecedor = new ArrayList<Object>();
		try {
			String sql = "SELECT * FROM fornecedores";
			java.sql.Statement state = Conexao.conectar().createStatement();
			ResultSet rs = state.executeQuery(sql);
			
			while (rs.next())
			{
				Object[] linha = {rs.getString(1), rs.getString(2), rs.getString(3)};
				fornecedor.add(linha);
			}
			state.close();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return fornecedor;
	}
	
	public int RetornarProximoCodigoFornecedor() throws Exception {
		try{
			String sql ="SELECT MAX(id_fornecedor)+1 AS id_fornecedor FROM fornecedores";
			PreparedStatement sqlPrep = Conexao.conectar().prepareStatement(sql);
			ResultSet rs = sqlPrep.executeQuery();
			if (rs.next()){
				return rs.getInt("id_fornecedor");
			}else{
				return 1;
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return 1;
		}
	}
	
}
