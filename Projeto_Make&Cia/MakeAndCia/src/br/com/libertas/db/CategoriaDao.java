package br.com.libertas.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import com.google.gson.JsonObject;
import br.com.libertas.dto.Categoria;


public class CategoriaDao {
	
	/** @author Thulio Barbosa de Paula Martins
	 *  @date 15-11-2020
	 *  @Descripition Responsavel por realizar a inserção de uma categoria
	 *  @param Categoria c
	 *  @return JsonObejct resposta
	 */ 
	public JsonObject inserir(Categoria c) {
		
		boolean cond = true;
		String saida = "Sem retorno!";
		JsonObject resposta = new JsonObject();
		Conexao con = new Conexao();
		
		try {
			String sql = "INSERT INTO cad_categoria (nome, descricao) VALUES"
					+ "(?,?)";
			
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setString(1, c.getNome());
			prep.setString(2, c.getDescricao());
			prep.execute();
			
			saida = "Categoria cadastrada com sucesso!";
			
		} catch (Exception e) {
			e.printStackTrace();
			saida = "Não foi possivel cadastrar a categoria, motivo : " + e.toString();
			cond = false;
		}
		con.desconecta();
		
		resposta.addProperty("Condição", cond);
		resposta.addProperty("Mensagem" , saida);
		
		return resposta;
		
	}
	
	/** @author Thulio Barbosa de Paula Martins
	 *  @date 15-11-2020
	 *  @Descripition Responsavel por realizar a exclusão de uma categoria
	 *  @param int id
	 *  @return JsonObject resposta
	 */
	public JsonObject excluir(int id) {
		
		boolean cond = true;
		String saida = "Sem retorno!";
		JsonObject resposta = new JsonObject();
		Conexao con = new Conexao();
		
		try {
			
			String sql = "DELETE FROM cad_categoria WHERE id = ?";
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setInt(1, id);
			prep.execute();
			
			saida = "Categoria excluída com sucesso!";
			
		} catch (Exception e) {
			e.printStackTrace();
			saida = "Não foi possível exlcuir a Categoria, motivo: " + e.toString();
			cond = false;
		}
		con.desconecta();
		
		resposta.addProperty("Condição", cond);
		resposta.addProperty("Mensagem", saida);
		
		return resposta;
	}
	
	/** @author Thulio Barbosa de Paula Martins
	 *  @date 15-11-2020
	 *  @Descripition Responsavel por realizar a alteração de uma categoria
	 *  @param Categoria c
	 *  @return JsonObject resposta
	 */
	public JsonObject alterar(Categoria c) {
		
		boolean cond = true;
		String mensagem = "Sem retorno!";
		JsonObject resposta = new JsonObject();
		Conexao con = new Conexao();
		
		try {
			
			String sql = "UPDATE cad_categoria SET nome=?, descricao=? WHERE id=?";
			
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setString(1, c.getNome());
			prep.setString(2, c.getDescricao());
			prep.setInt(3, c.getId());
			prep.execute();
			
			mensagem = "Categoria atualizada com sucesso!";
			
		} catch (Exception e) {
			e.printStackTrace();
			mensagem = "Não foi possível atualizar a Categoria, motivo: "+ e.toString();
			cond = false;
		}
		
		con.desconecta();
		
		resposta.addProperty("Condição", cond);
		resposta.addProperty("Mensagem" , mensagem);
		
		return resposta;
	}
	
	/** @author Thulio Barbosa de Paula Martins
	 *  @date 15-11-2020
	 *  @Descripition Responsavel por realizar a consulta de uma categoria
	 *  @param int id
	 *  @return Categoria c
	 */
	public Categoria consultar(int id) {
		
		Categoria c = new Categoria();
		Conexao con = new Conexao();
		
		System.out.print("Parametro => " + id);
		
		try {
			
			String sql = "SELECT * FROM cad_categoria WHERE id = " + id;
			
			Statement sta = con.getConexao().createStatement();
			ResultSet res = sta.executeQuery(sql);
			
			if (res.next()) {
				c.setNome(res.getString("nome"));
				c.setDescricao(res.getString("descricao"));
				c.setId(res.getInt("id"));
			}
			
			res.close();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconecta();
		
		return c;
	}
	
	/** @author Thulio Barbosa de Paula Martins
	 *  @date 15-11-2020
	 *  @Descripition Responsavel por listar todas categorias
	 *  @param int id
	 *  @return LinkedList listaCategoria
	 */
	public LinkedList<Categoria> listar(){
		
		Conexao con = new Conexao();
		LinkedList<Categoria> listaCategoria = new LinkedList<Categoria>();
		
		try {
			
			String sql = "SELECT * FROM cad_categoria";
			
			Statement sta = con.getConexao().createStatement();
			ResultSet res = sta.executeQuery(sql);
			
			while (res.next()) {
				Categoria c = new Categoria();
				c.setNome(res.getString("nome"));
				c.setDescricao(res.getString("descricao"));
				c.setId(res.getInt("id"));
				listaCategoria.add(c);
				
			}
			res.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconecta();
		
		return listaCategoria;
	}
	
}
