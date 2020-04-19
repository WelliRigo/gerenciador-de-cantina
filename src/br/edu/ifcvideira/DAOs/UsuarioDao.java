package br.edu.ifcvideira.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.edu.ifcvideira.beans.Usuario;
import br.edu.ifcvideira.utils.Conexao;

public class UsuarioDao {
	
	public void CadastrarUsuario(Usuario us) throws SQLException, Exception{
		try{
			String sql = "INSERT INTO usuarios (nome_usuario, cpf_usuario, rg_usuario, telefone_usuario, celular_usuario, login_usuario, senha_usuario, data_cadastro_usuario) VALUES (?,?,?,?,?,?,?,?)";
			java.sql.PreparedStatement sqlPrep = Conexao.conectar().prepareStatement(sql);
			int contador = 1;
			sqlPrep.setString(contador++, us.getNome());
			sqlPrep.setString(contador++, us.getCpf());
			sqlPrep.setString(contador++, us.getRgUs());
			sqlPrep.setString(contador++, us.getTelefone());
			sqlPrep.setString(contador++, us.getCelular());
			sqlPrep.setString(contador++, us.getLoginUs());
			sqlPrep.setString(contador++, us.getSenhaUs());
			sqlPrep.setTimestamp(contador++, us.getDataCadastro());
			sqlPrep.execute();
			
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
	}

	public void AlterarUsuarioComSenha(Usuario us) throws Exception {
		try{
			String sql = "UPDATE usuarios SET nome_usuario=?, cpf_usuario=?, rg_usuario=?, telefone_usuario=?, celular_usuario=?, login_usuario=?, senha_usuario=? WHERE id_usuario=?";
			PreparedStatement sqlPrep = Conexao.conectar().prepareStatement(sql);
			int contador = 1;
			sqlPrep.setString(contador++, us.getNome());
			sqlPrep.setString(contador++, us.getCpf());
			sqlPrep.setString(contador++, us.getRgUs());
			sqlPrep.setString(contador++, us.getTelefone());
			sqlPrep.setString(contador++, us.getCelular());
			sqlPrep.setString(contador++, us.getLoginUs());
			sqlPrep.setString(contador++, us.getSenhaUs());
			sqlPrep.setInt(contador++, us.getIdUs());
			sqlPrep.execute();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}  
	
	public void AlterarUsuarioSemSenha(Usuario us) throws Exception {
		try{
			String sql = "UPDATE usuarios SET nome_usuario=?, cpf_usuario=?, rg_usuario=?, telefone_usuario=?, celular_usuario=?, login_usuario=? WHERE id_usuario=?";
			PreparedStatement sqlPrep = Conexao.conectar().prepareStatement(sql);
			int contador = 1;
			sqlPrep.setString(contador++, us.getNome());
			sqlPrep.setString(contador++, us.getCpf());
			sqlPrep.setString(contador++, us.getRgUs());
			sqlPrep.setString(contador++, us.getTelefone());
			sqlPrep.setString(contador++, us.getCelular());
			sqlPrep.setString(contador++, us.getLoginUs());
			sqlPrep.setInt(contador++, us.getIdUs());
			sqlPrep.execute();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	} 

	public void deletarUsuario(Usuario us) throws Exception{
		try{
			String sql = "DELETE FROM usuarios WHERE id_usuario=? ";
			PreparedStatement sqlPrep = (PreparedStatement) Conexao.conectar().prepareStatement(sql);
			sqlPrep.setInt(1, us.getIdUs());
			sqlPrep.execute();
		} catch (SQLException e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public List<Object> buscarTodos() throws SQLException, Exception{
		List<Object> usuario = new ArrayList<Object>();
		try {
			String sql = "SELECT * FROM usuarios";
			java.sql.Statement state = Conexao.conectar().createStatement();
			ResultSet rs = state.executeQuery(sql);
			
			while (rs.next())
			{
				Object[] linha = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(9)};
				usuario.add(linha);
			}
			state.close();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return usuario;
	}
	
	public int RetornarProximoCodigoUsuario() throws Exception {
		try{
			String sql ="SELECT MAX(id_usuario)+1 AS id_usuario FROM usuarios ";
			PreparedStatement sqlPrep = Conexao.conectar().prepareStatement(sql);
			ResultSet rs = sqlPrep.executeQuery();
			if (rs.next()){
				return rs.getInt("id_usuario");
			}else{
				return 1;
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return 1;
		}
	}
}