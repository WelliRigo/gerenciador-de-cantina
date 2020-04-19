package br.edu.ifcvideira.beans;

import java.sql.Timestamp;

public class Aquisicao extends Produto{
	private int idAq;
	private Fornecedor fornecedor = new Fornecedor();
	private Produto produto = new Produto();
	private int quantidadeAq;
	private Usuario usuario = new Usuario();
	
	public int getIdAq() {
		return idAq;
	}
	public void setIdAq(int idAq) {
		this.idAq = idAq;
	}
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public int getQuantidadeAq() {
		return quantidadeAq;
	}
	public void setQuantidadeAq(int quantidadeAq) {
		this.quantidadeAq = quantidadeAq;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public double getValorUnitarioAq() {
		return valorUnitarioAq;
	}
	public void setValorUnitarioAq(double valorUnitarioAq) {
		this.valorUnitarioAq = valorUnitarioAq;
	}
	public Timestamp getDataAq() {
		return dataAq;
	}
	public void setDataAq(Timestamp dataAq) {
		this.dataAq = dataAq;
	}
	private double valorUnitarioAq;
	private Timestamp dataAq;
	

}
