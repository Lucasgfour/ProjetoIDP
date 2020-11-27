package br.com.libertas.model;

import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.com.libertas.db.UsuarioDao;
import br.com.libertas.dto.Usuario;

/**
 * Servlet implementation class InserirUsuario
 */
@WebServlet("/InserirUsuario")
public class InserirUsuario extends HttpServlet implements Modelo {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public InserirUsuario() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			UsuarioDao uDao = new UsuarioDao();
			Usuario u = new Usuario();
			Gson gson = new Gson();
			
			u.setNome(request.getParameter("nome"));
			u.setLogin(request.getParameter("login"));
			u.setSenha(request.getParameter("senha"));
			u.setAdministrador(Boolean.parseBoolean(request.getParameter("isAdmin")));
			
			PrintWriter out = response.getWriter();
			String res = gson.toJson(uDao.inserir(u));
			out.print(res);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	/*protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}*/

}
