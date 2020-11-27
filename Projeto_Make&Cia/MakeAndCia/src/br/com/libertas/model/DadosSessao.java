package br.com.libertas.model;

import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.libertas.dto.Usuario;

/**
 * Servlet implementation class DadosSessao
 */
@WebServlet("/DadosSessao")
public class DadosSessao extends HttpServlet implements Modelo {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DadosSessao() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void execute(HttpServletRequest request, HttpServletResponse response) {
    	HttpSession sessao = request.getSession();
    	JsonObject saida = new JsonObject();
    	
    	try {
    		// Verificar se possui usuário logado
			Usuario u = (Usuario) sessao.getAttribute("Usuario");
			
			saida.addProperty("ID", u.getId());
			saida.addProperty("Nome", u.getNome());
			saida.addProperty("Login", u.getLogin());
			saida.addProperty("Administrador", u.getAdministrador());
			saida.addProperty("Condicao", true);
			
		} catch (Exception e) {
			saida.addProperty("Condicao", false);
			saida.addProperty("Mensagem", "Não esta logado !");
			e.printStackTrace();
		}
    	
    	// Retorno da Requisão
    	try {
    		PrintWriter out = response.getWriter();
			out.print(new Gson().toJson(saida));
		} catch (Exception e) { }
    }

}
