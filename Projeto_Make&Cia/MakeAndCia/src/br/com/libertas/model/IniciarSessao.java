package br.com.libertas.model;

import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.libertas.db.UsuarioDao;
import br.com.libertas.dto.Usuario;

/**
 * Servlet implementation class IniciarSessao
 */
@WebServlet("/IniciarSessao")
public class IniciarSessao extends HttpServlet implements Modelo {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IniciarSessao() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void execute(HttpServletRequest request, HttpServletResponse response) {
    	UsuarioDao uDao = new UsuarioDao();
    	Usuario u = new Usuario();
    	JsonObject saida = new JsonObject();
    	try {
			// Tentativa de Login
    		String login = request.getParameter("login");
    		String senha = request.getParameter("senha");
    		
    		u.setLogin(login);
    		u.setSenha(senha);
    		u = uDao.verificaLogin(u);
    		
    		if(u != null) {
    			HttpSession sessao = request.getSession();
    			sessao.setAttribute("Usuario", u);
    			
    			saida.addProperty("Condicao", true);
    			saida.addProperty("Mensagem", "Logado com sucesso !");
    		} else {
    			saida.addProperty("Condicao", false);
    			saida.addProperty("Mensagem", "Usuário ou Senha inválidos !");
    		}
    		
    		
		} catch (Exception e) {
			saida.addProperty("Condicao", false);
			saida.addProperty("Mensagem", "Usuário ou Senha inválidos !");
			e.printStackTrace();
		}
    	
    	// Retorno da Requisão
    	try {
    		PrintWriter out = response.getWriter();
			out.print(new Gson().toJson(saida));
		} catch (Exception e) { }
    }

}
