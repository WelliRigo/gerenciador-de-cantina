package br.edu.ifcvideira.beans;

public class Usuario extends Pessoa{
	private int idUs;
	private String rgUs;
	private String loginUs;
	private String senhaUs;
	
	//construtor vazio
	public Usuario(){
		
	}
	
	//Encapsulamento
	public int getIdUs() {
		return idUs;
	}

	public void setIdUs(int idUs) {
		this.idUs = idUs;
	}

	public String getRgUs() {
		return rgUs;
	}

	public void setRgUs(String rgUs) {
		this.rgUs = rgUs;
	}

	public String getLoginUs() {
		return loginUs;
	}

	public void setLoginUs(String loginUs) {
		this.loginUs = loginUs;
	}

	public String getSenhaUs() {
		return senhaUs;
	}

	public void setSenhaUs(String senhaUs) {
		this.senhaUs = senhaUs;
	}
}
