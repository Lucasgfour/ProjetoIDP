package br.com.libertas.dto;

public class Produto {
	
	private int id;
	private String codigo;
	private String descricao;
	private Double preco_custo;
	private Double preco_venda;
	private int categoria;
	private int cod_fornecedor;
	private int quantidade;
	
	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Double getPreco_custo() {
		return preco_custo;
	}
	
	public void setPreco_custo(Double preco_custo) {
		this.preco_custo = preco_custo;
	}
	
	public Double getPreco_venda() {
		return preco_venda;
	}
	
	public void setPreco_venda(Double preco_venda) {
		this.preco_venda = preco_venda;
	}
	
	public int getCategoria() {
		return categoria;
	}
	
	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	
	public int getCod_fornecedor() {
		return cod_fornecedor;
	}
	
	public void setCod_fornecedor(int cod_fornecedor) {
		this.cod_fornecedor = cod_fornecedor;
	}

}
