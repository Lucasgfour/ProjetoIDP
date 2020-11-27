package br.com.libertas.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

import com.google.gson.JsonObject;

import br.com.libertas.dto.Usuario;

public class UsuarioDao {

	/** @author Marques Alexandre Veçoso
	 *  @date 04-11-2020
	 *  @Descripition Responsavel por realizar a inserção de um usuário
	 *  @param Usuario u
	 *  @return JsonObejct resposta
	 */ 
	public JsonObject inserir(Usuario u) {
		
		boolean cond = true;
		String saida = "Sem retorno!";
		JsonObject resposta = new JsonObject();
		Conexao con = new Conexao();
		
		try {
			String sql = "INSERT INTO cad_usuario (nome, login, senha, administrador) VALUES"
					+ "(?,?,MD5(?),?)";
			
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setString(1, u.getNome());
			prep.setString(2, u.getLogin());
			prep.setString(3, u.getSenha());
			prep.setBoolean(4, u.getAdministrador());
			prep.execute();
			
			saida = "Usuário cadastrado com sucesso!";
			
		} catch (Exception e) {
			e.printStackTrace();
			saida = "Não foi possivel cadastrar o usuário, motivo : " + e.toString();
			cond = false;
		}
		con.desconecta();
		
		resposta.addProperty("Condição", cond);
		resposta.addProperty("Mensagem" , saida);
		
		return resposta;
		
	}
	
	/** @author Marques Alexandre Veçoso
	 *  @date 04-11-2020
	 *  @Descripition Responsavel por realizar a exclusão de um usuário
	 *  @param int id
	 *  @return JsonObject resposta
	 */
	public JsonObject excluir(int id) {
		
		boolean cond = true;
		String saida = "Sem retorno!";
		JsonObject resposta = new JsonObject();
		Conexao con = new Conexao();
		
		try {
			
			String sql = "DELETE FROM cad_usuario WHERE id = ?";
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setInt(1, id);
			prep.execute();
			
			saida = "Usuário excluído com sucesso!";
			
		} catch (Exception e) {
			e.printStackTrace();
			saida = "Não foi possível exlcuir o usuário, motivo: " + e.toString();
			cond = false;
		}
		con.desconecta();
		
		resposta.addProperty("Condição", cond);
		resposta.addProperty("Mensagem", saida);
		
		return resposta;
	}
	
	/** @author Marques Alexandre Veçoso
	 *  @date 04-11-2020
	 *  @Descripition Responsavel por realizar a alteração de um usuário
	 *  @param Usuario u
	 *  @return JsonObject resposta
	 */
	public JsonObject alterar(Usuario u) {
		
		boolean cond = true;
		String mensagem = "Sem retorno!";
		JsonObject resposta = new JsonObject();
		Conexao con = new Conexao();
		
		try {
			
			String sql = "UPDATE cad_usuario SET nome=?, login=?, senha=?, administrador=? WHERE id=?";
			if(u.getSenha().length() != 32) {
				sql = "UPDATE cad_usuario SET nome=?, login=?, senha=MD5(?), administrador=? WHERE id=?";
			}
			
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setString(1, u.getNome());
			prep.setString(2, u.getLogin());
			prep.setString(3, u.getSenha());
			prep.setBoolean(4, u.getAdministrador());
			prep.setInt(5, u.getId());
			prep.execute();
			
			mensagem = "Usuário atualizado com sucesso!";
			
		} catch (Exception e) {
			e.printStackTrace();
			mensagem = "Não foi possível atualizar o usuário, motivo: "+ e.toString();
			cond = false;
		}
		
		con.desconecta();
		
		resposta.addProperty("Condição", cond);
		resposta.addProperty("Mensagem" , mensagem);
		
		return resposta;
	}
	
	/** @author Marques Alexandre Veçoso
	 *  @date 04-11-2020
	 *  @Descripition Responsavel por realizar a consulta de um usuário
	 *  @param int id
	 *  @return Usuario u
	 */
	public Usuario consultar(int id) {
		
		Usuario u = new Usuario();
		Conexao con = new Conexao();
		
		System.out.print("Parametro => " + id);
		
		try {
			
			String sql = "SELECT * FROM cad_usuario WHERE id = " + id;
			
			Statement sta = con.getConexao().createStatement();
			ResultSet res = sta.executeQuery(sql);
			
			if (res.next()) {
				u.setNome(res.getString("nome"));
				u.setLogin(res.getString("login"));
				u.setSenha(res.getString("senha"));
				u.setAdministrador(res.getBoolean("administrador"));
				u.setId(res.getInt("id"));
			}
			
			res.close();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconecta();
		
		return u;
	}
	
	/** @author Marques Alexandre Veçoso
	 *  @date 04-11-2020
	 *  @Descripition Responsavel por listar todos os usuários
	 *  @param int id
	 *  @return LinkedList listaUsuario
	 */
	public LinkedList<Usuario> listar(){
		
		Conexao con = new Conexao();
		LinkedList<Usuario> listaUsuario = new LinkedList<Usuario>();
		
		try {
			
			String sql = "SELECT * FROM cad_usuario";
			
			Statement sta = con.getConexao().createStatement();
			ResultSet res = sta.executeQuery(sql);
			
			while (res.next()) {
				Usuario u = new Usuario();
				u.setNome(res.getString("nome"));
				u.setLogin(res.getString("login"));
				u.setSenha(res.getString("senha"));
				u.setAdministrador(res.getBoolean("administrador"));
				u.setId(res.getInt("id"));
				listaUsuario.add(u);
				
			}
			res.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconecta();
		
		return listaUsuario;
	}
	
	public Usuario verificaLogin (Usuario u) {
		Usuario uRetorno = null;
		Conexao con = new Conexao();
		try {
			String sql = "SELECT * FROM cad_usuario WHERE login = '" + u.getLogin() + "' AND senha = MD5('" + u.getSenha() + "')";
			Statement sta = con.getConexao().createStatement();
			ResultSet res = sta.executeQuery(sql);
			if(res.next()) {
				uRetorno = new Usuario();
				uRetorno.setId(Integer.parseInt(res.getString("id")));
				uRetorno.setNome(res.getString("nome"));
				uRetorno.setLogin(res.getString("login"));
				uRetorno.setAdministrador(Boolean.parseBoolean(res.getString("administrador")));
			}
			res.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconecta();
		return uRetorno;
	}
	
}
