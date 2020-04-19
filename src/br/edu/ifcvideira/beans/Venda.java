package br.edu.ifcvideira.beans;

import java.sql.Timestamp;

public class Venda {
	private int idVn;
	private Usuario usuario = new Usuario();
	private String tipoVn;
	private String situacaoVn;
	private Cliente cliente = new Cliente();
	private Timestamp dataVn;
	//ProdutoVenda
	private Produto produto = new Produto();
	//
	
	public int getIdVn() {
		return idVn;
	}
	public void setIdVn(int idVn) {
		this.idVn = idVn;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getTipoVn() {
		return tipoVn;
	}
	public void setTipoVn(String tipoVn) {
		this.tipoVn = tipoVn;
	}
	public String getSituacaoVn() {
		return situacaoVn;
	}
	public void setSituacaoVn(String situacaoVn) {
		this.situacaoVn = situacaoVn;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Timestamp getDataVn() {
		return dataVn;
	}
	public void setDataVn(Timestamp dataVn) {
		this.dataVn = dataVn;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
}
