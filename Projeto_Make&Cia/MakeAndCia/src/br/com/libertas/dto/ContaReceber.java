package br.com.libertas.dto;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class ContaReceber {
	
	private int id;
	private String descricao;
	private Double valor;
	private Date data;
	private String status;
	private int parcela;
	private String dataFormatada;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Date getData() {
		return data;
	}
	public String getDataFormatada() {
		return dataFormatada;
	}
	public void setData(Date data) {
		this.data = data;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		this.dataFormatada = sdf.format(data);
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getParcela() {
		return parcela;
	}
	public void setParcela(int parcela) {
		this.parcela = parcela;
	}
	
	
	

}
