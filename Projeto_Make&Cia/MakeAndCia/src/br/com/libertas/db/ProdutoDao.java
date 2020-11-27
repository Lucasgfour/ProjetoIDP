package br.com.libertas.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import br.com.libertas.dto.Produto;



public class ProdutoDao {

	// Inseri produto na base de dados
	public String inserir(Produto prod) {
		
		Conexao con = new Conexao();
		String saida = "Sem Retorno !";
		
		try {
			String sql = "INSERT INTO cad_produto "
					+ "(codigo, descricao, preco_custo, preco_venda, categoria, cod_fornecedor, quantidade)"
					+ "VALUES  (?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setString(1, prod.getCodigo());
			prep.setString(2, prod.getDescricao());
			prep.setDouble(3, prod.getPreco_custo());
			prep.setDouble(4, prod.getPreco_venda());
			prep.setInt(5, prod.getCategoria());
			prep.setInt(6, prod.getCod_fornecedor());
			prep.setInt(7, prod.getQuantidade());
			prep.execute();
			
			saida = "Produto cadastrado com sucesso !"; 
			
		} 
		catch (Exception e) {
			e.printStackTrace();
			saida = "Não foi possivel cadastrar o produto, motivo : " + e.toString();
		}
		con.desconecta();
		
		return saida;
	}
	
	// Exclui usuário da base de dados
	public String excluir(int id) {
		
		Conexao con = new Conexao();
		String saida = "Sem Retorno !";
		
		try {
			
			String sql = "DELETE FROM cad_produto WHERE id = ?";
			
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setInt(1, id);
			prep.execute();
			
			saida = "Produto excluido com sucesso !"; 
			
			System.out.print("Parametro chegado => " + id);
			
			prep.close();
			con.desconecta();
			
		} 
		catch (Exception e) {
			e.printStackTrace();
			saida = "Não foi possivel excluir o produto, motivo : " + e.toString();
		}

		return saida;
	}
	
	
	// Altera produto da base de dados
	public String alterar(Produto prod) {
		
		Conexao con = new Conexao();
		String saida = "Sem Retorno !";
		
		try {
			String sql = "UPDATE cad_produto SET codigo=?, descricao=?, preco_custo=?, preco_venda=?, categoria=?, cod_fornecedor=?, quantidade=? "
					+ "WHERE id=?";
			
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setString(1, prod.getCodigo());
			prep.setString(2, prod.getDescricao());
			prep.setDouble(3, prod.getPreco_custo());
			prep.setDouble(4, prod.getPreco_venda());
			prep.setInt(5, prod.getCategoria());
			prep.setInt(6, prod.getCod_fornecedor());
			prep.setInt(7, prod.getQuantidade());
			prep.setInt(8, prod.getId());
			prep.execute();
			
			saida = "Produto atualizado com sucesso !"; 
			
		} 
		catch (Exception e) {
			e.printStackTrace();
			saida = "Não foi possivel atualizar o produto, motivo : " + e.toString();
		}
		con.desconecta();
		
		return saida;
	}
	
	// Consulta produto da base de dados
	public Produto consutar(int id) {
		
		Produto prod = new Produto();
		Conexao con = new Conexao();
		
		try {
			
			String sql = "SELECT * FROM cad_produto WHERE id = " + id;
			
			Statement sta = con.getConexao().createStatement();
			ResultSet res = sta.executeQuery(sql);
			
			if(res.next()) {
				prod.setCodigo(res.getString("codigo"));
				prod.setDescricao(res.getString("descricao"));
				prod.setPreco_custo(res.getDouble("preco_custo"));
				prod.setPreco_venda(res.getDouble("preco_venda"));
				prod.setCategoria(res.getInt("categoria"));
				prod.setCod_fornecedor(res.getInt("cod_fornecedor"));
				prod.setQuantidade(res.getInt("quantidade"));
				prod.setId(res.getInt("id"));
			}
			
			res.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		con.desconecta();
		
		return prod;
	}
	
	// Lista todos produtos cadastrado na base
	public LinkedList<Produto> listar(){
		
		Conexao con = new Conexao();
		LinkedList<Produto> lista = new LinkedList<Produto>();
		
		try {
			
			String sql = "SELECT * FROM cad_produto";
			
			Statement instrucao = con.getConexao().createStatement();
			ResultSet res = instrucao.executeQuery(sql);
			while (res.next()) {
				Produto prod = new Produto();
				prod.setCodigo(res.getString("codigo"));
				prod.setDescricao(res.getString("descricao"));
				prod.setPreco_custo(res.getDouble("preco_custo"));
				prod.setPreco_venda(res.getDouble("preco_venda"));
				prod.setCategoria(res.getInt("categoria"));
				prod.setCod_fornecedor(res.getInt("cod_fornecedor"));
				prod.setQuantidade(res.getInt("quantidade"));
				prod.setId(res.getInt("id"));
				lista.add(prod);
			}
			
			res.close();
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		con.desconecta();
		
		return lista;
	}
	

}
