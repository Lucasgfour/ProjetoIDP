package br.com.libertas.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import com.google.gson.JsonObject;

import br.com.libertas.dto.Venda;

public class VendaDao {
	
	/** @author Marques Alexandre Veçoso
	 *  @date 10-11-2020
	 *  @Descripition Responsavel por realizar a inserção de uma venda no banco de dados
	 *  @param Venda u
	 *  @return JsonObejct resposta
	 */ 
	public JsonObject inserirVenda(Venda v) {
		
		boolean cond = true;
		String saida = "Sem retorno!";
		int id = 0;
		JsonObject resposta = new JsonObject();
		Conexao con = new Conexao();
		
		try {
			String sql = "SELECT fn_inserir_venda(?, ?) AS 'Venda'";
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setDate(1, java.sql.Date.valueOf(v.getData_venda()));
			prep.setString(2, v.getForma_pagamento());
			ResultSet res = prep.executeQuery();
			if(res.next()) {
				id = res.getInt("Venda");
				cond = true;
				saida = "Implantado com sucesso!";
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			saida = "Não foi possivel registrar a venda, motivo : " + e.toString();
			cond = false;
		}
		
		con.desconecta();
		
		resposta.addProperty("Condicao", cond);
		resposta.addProperty("Mensagem" , saida);
		resposta.addProperty("codigo", id);
		
		return resposta;
	}
	
	/** @author Marques Alexandre Veçoso
	 *  @date 10-11-2020
	 *  @Descripition Responsavel por realizar a exclusão de uma venda
	 *  @param int id
	 *  @return JsonObejct resposta
	 */ 
	public JsonObject excluirVenda(int id) {
		
		boolean cond = true;
		String saida = "Sem retorno!";
		JsonObject resposta = new JsonObject();
		Conexao con = new Conexao();
		
		try {
			String sql = "DELETE FROM vendas WHERE idvenda = ?";
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setInt(1, id);
			prep.execute();
			
			saida = "Venda excluída com sucesso!";
			
		} catch (Exception e) {
			e.printStackTrace();
			saida = "Não foi possível exlcuir a venda, motivo: " + e.toString();
			cond = false;
		}
		
		con.desconecta();
		
		resposta.addProperty("Condicao", cond);
		resposta.addProperty("Mensagem" , saida);
		
		return resposta;
	}
	
	/** @author Marques Alexandre Veçoso
	 *  @date 10-11-2020
	 *  @Descripition Responsavel por realizar a alteração de uma venda
	 *  @param Venda v
	 *  @return JsonObejct resposta
	 */
	public JsonObject alterarVenda(Venda v) {
		
		boolean cond = true;
		String saida = "Sem retorno!";
		JsonObject resposta = new JsonObject();
		Conexao con = new Conexao();
		
		try {
			String sql = "UPDATE vendas SET data=?, forma_pagamento=? WHERE id_venda=?";
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setDate(1, java.sql.Date.valueOf(v.getData_venda()));
			prep.setString(2, v.getForma_pagamento());
			prep.setInt(3, v.getId());
			prep.execute();
			
			saida = "Venda atualizada com sucesso!";
			
		} catch (Exception e) {
			e.printStackTrace();
			saida = "Não foi possível atualizar a venda, motivo: " + e.toString();
			cond = false;
		}
		
		con.desconecta();
		
		resposta.addProperty("Condicao", cond);
		resposta.addProperty("Mensagem" , saida);
		
		return resposta;
	}
	
	/** @author Marques Alexandre Veçoso
	 *  @date 10-11-2020
	 *  @Descripition Responsavel por realizar a consulta de uma venda
	 *  @param int id
	 *  @return Venda v
	 */
	public Venda consultarVenda(int id) {
		
		Venda v = new Venda();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Conexao con = new Conexao();
		
		try {
			String sql = "SELECT * FROM vendas where id_venda = " + id;
			Statement sta = con.getConexao().createStatement();
			ResultSet res = sta.executeQuery(sql);
			
			if (res.next()) {
				v.setData_venda(LocalDate.parse(res.getString("data"), formatter));
				v.setForma_pagamento(res.getString("forma_pagamento"));
				v.setId(res.getInt("id_venda"));
				
			}
			
			res.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		con.desconecta();
		
		return v;
	}
	
	/** @author Marques Alexandre Veçoso
	 *  @date 10-11-2020
	 *  @Descripition Responsavel por listar todas as vendas
	 *  @return LinkedList listaVendas
	 */
	public LinkedList<Venda> listar(){
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LinkedList<Venda> listaVendas = new LinkedList<Venda>();
		Conexao con = new Conexao();
		
		try {
			String sql = "SELECT * FROM vendas";
			Statement sta = con.getConexao().createStatement();
			ResultSet res = sta.executeQuery(sql);
			
			while (res.next()) {
				Venda v = new Venda();
				v.setData_venda(LocalDate.parse(res.getString("data"), formatter));
				v.setForma_pagamento(res.getString("forma_pagamento"));
				v.setId(res.getInt("id_venda"));
				listaVendas.add(v);
				
			}
			
			res.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		con.desconecta();
		
		return listaVendas;
	}
	
	

}
