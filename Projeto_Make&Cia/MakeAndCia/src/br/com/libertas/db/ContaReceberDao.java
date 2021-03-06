package br.com.libertas.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import com.google.gson.JsonObject;
import br.com.libertas.dto.ContaReceber;

public class ContaReceberDao {
	
	/** @author Thulio Barbosa de Paula Martins
	 *  @date 15-11-2020
	 *  @Descripition Responsavel por realizar a inser��o de uma conta a Receber
	 *  @param ContaReceber cr
	 *  @return JsonObejct resposta
	 */ 
	public JsonObject inserir(ContaReceber cr) {
		
		boolean cond = true;
		String saida = "Sem retorno!";
		JsonObject resposta = new JsonObject();
		Conexao con = new Conexao();
		
		try {
			String sql = "INSERT INTO cad_contaReceber (descricao, valor, data, status, parcela) VALUES"
					+ "(?,?,?,?,?)";
			
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setString(1, cr.getDescricao());
			prep.setDouble(2, cr.getValor());
			prep.setDate(3, cr.getData());
			prep.setString(4, cr.getStatus());
			prep.setInt(5, cr.getParcela());
			prep.execute();
			
			saida = "Conta a Receber cadastrada com sucesso!";
			
		} catch (Exception e) {
			e.printStackTrace();
			saida = "N�o foi possivel cadastrar a conta a Receber, motivo : " + e.toString();
			cond = false;
		}
		con.desconecta();
		
		resposta.addProperty("Condi��o", cond);
		resposta.addProperty("Mensagem" , saida);
		
		return resposta;
		
	}
	
	/** @author Thulio Barbosa de Paula Martins
	 *  @date 15-11-2020
	 *  @Descripition Responsavel por realizar a exclus�o de uma conta a Receber
	 *  @param int id
	 *  @return JsonObject resposta
	 */
	public JsonObject excluir(int id) {
		
		boolean cond = true;
		String saida = "Sem retorno!";
		JsonObject resposta = new JsonObject();
		Conexao con = new Conexao();
		
		try {
			
			String sql = "DELETE FROM cad_contaReceber WHERE id = ?";
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setInt(1, id);
			prep.execute();
			
			saida = "Conta a Receber exclu�da com sucesso!";
			
		} catch (Exception e) {
			e.printStackTrace();
			saida = "N�o foi poss�vel exlcuir a conta a Receber, motivo: " + e.toString();
			cond = false;
		}
		con.desconecta();
		
		resposta.addProperty("Condi��o", cond);
		resposta.addProperty("Mensagem", saida);
		
		return resposta;
	}
	
	/** @author Thulio Barbosa de Paula Martins
	 *  @date 15-11-2020
	 *  @Descripition Responsavel por realizar a altera��o de uma conta a Receber
	 *  @param ContaReceber cr
	 *  @return JsonObject resposta
	 */
	public JsonObject alterar(ContaReceber cr) {
		
		boolean cond = true;
		String mensagem = "Sem retorno!";
		JsonObject resposta = new JsonObject();
		Conexao con = new Conexao();
		
		try {
			
			String sql = "UPDATE cad_contaReceber SET descricao=?, valor=?, data=?, status=?, parcela=? WHERE id=?";
			
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setString(1, cr.getDescricao());
			prep.setDouble(2, cr.getValor());
			prep.setDate(3, cr.getData());
			prep.setString(4, cr.getStatus());
			prep.setInt(5, cr.getParcela());
			prep.setInt(6, cr.getId());
			prep.execute();
			
			mensagem = "Conta a Receber atualizada com sucesso!";
			
		} catch (Exception e) {
			e.printStackTrace();
			mensagem = "Nao foi poss�vel atualizar a conta a Receber, motivo: "+ e.toString();
			cond = false;
		}
		
		con.desconecta();
		
		resposta.addProperty("Condi��o", cond);
		resposta.addProperty("Mensagem" , mensagem);
		
		return resposta;
	}
	
	/** @author Thulio Barbosa de Paula Martins
	 *  @date 15-11-2020
	 *  @Descripition Responsavel por realizar a consulta de um Fornecedor
	 *  @param int id
	 *  @return ContaReceber cr
	 */
	public ContaReceber consultar(int id) {
		
		ContaReceber cr = new ContaReceber();
		Conexao con = new Conexao();
		
		System.out.print("Parametro => " + id);
		
		try {
			
			String sql = "SELECT * FROM cad_contaReceber WHERE id = " + id;
			
			Statement sta = con.getConexao().createStatement();
			ResultSet res = sta.executeQuery(sql);
			
			if (res.next()) {
				cr.setDescricao(res.getString("descricao"));
				cr.setValor(Double.parseDouble(res.getString("valor")));
				cr.setData(res.getDate("data"));
				cr.setStatus(res.getString("status"));
				cr.setParcela(Integer.parseInt(res.getString("parcela")));
				cr.setId(res.getInt("id"));
			}
			
			res.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconecta();
		
		return cr;
	}
	
	/** @author Thulio Barbosa de Paula Martins
	 *  @date 15-11-2020
	 *  @Descripition Responsavel por listar todas contas a Receber
	 *  @param int id
	 *  @return LinkedList listaContasReceber
	 */
	public LinkedList<ContaReceber> listar(){
		
		Conexao con = new Conexao();
		LinkedList<ContaReceber> listaContasReceber = new LinkedList<ContaReceber>();
		
		try {
			
			String sql = "SELECT * FROM cad_contaReceber";
			
			Statement sta = con.getConexao().createStatement();
			ResultSet res = sta.executeQuery(sql);
			
			while (res.next()) {
				ContaReceber cr = new ContaReceber();
				cr.setDescricao(res.getString("descricao"));
				cr.setValor(Double.parseDouble(res.getString("valor")));
				cr.setData(res.getDate("data"));
				cr.setStatus(res.getString("status"));
				cr.setParcela(Integer.parseInt(res.getString("parcela")));
				cr.setId(res.getInt("id"));
				listaContasReceber.add(cr);
			}
			res.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconecta();
		
		return listaContasReceber;
	}

}
