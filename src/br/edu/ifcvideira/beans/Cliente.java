package br.edu.ifcvideira.beans;

public class Cliente extends Pessoa{
	private int idCl;
	
	//construtor vazio
	public Cliente(){
		
	}
	//Encapsulamento
	public int getIdCl() {
		return idCl;
	}

	public void setIdCl(int idCl) {
		this.idCl = idCl;
	}

	
}
