package br.edu.ifcvideira.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import br.edu.ifcvideira.beans.Login;
import br.edu.ifcvideira.utils.Conexao;

public class LoginDao {
	
	public boolean recebeDados() throws SQLException{
		int flag=0;
		boolean dadosCorretos = false;
		try{
		String sql = "Select nome_usuario, senha_usuario, id_usuario FROM usuarios where login_usuario=?";
		
		java.sql.PreparedStatement sqlPrep = (PreparedStatement) Conexao.conectar().prepareStatement(sql);
		int contador = 1;
		sqlPrep.setString(contador++, Login.getUsuario().getLoginUs());
		
		ResultSet rs = sqlPrep.executeQuery();
		
		while (rs.next()){
				
				String senha = rs.getString("senha_usuario");
				
				if (Login.getUsuario().getSenhaUs().equals(senha)){
					
					Login.getUsuario().setNome(rs.getString("nome_usuario"));
					Login.getUsuario().setIdUs(rs.getInt("id_usuario"));
					dadosCorretos = true;
					
				}else{
					JOptionPane.showMessageDialog(null, "Senha incorreta");
					dadosCorretos = false;
				}
				flag=1;
		}
		
		if (flag==0){
			JOptionPane.showMessageDialog(null, "Dados de login inexistente");
			dadosCorretos = false;
		}
	} catch(Exception e) {
		JOptionPane.showMessageDialog(null, "Dados de login inexistente");
		dadosCorretos = false;
	}
		
	return dadosCorretos;
}
}
