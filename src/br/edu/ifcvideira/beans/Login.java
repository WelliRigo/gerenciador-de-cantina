package br.edu.ifcvideira.beans;

public class Login {
	private static Usuario usuario = new Usuario();

	public static Usuario getUsuario() {
		return usuario;
	}

	public static void setUsuario(Usuario usuario) {
		Login.usuario = usuario;
	}
	
}
