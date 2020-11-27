package br.com.libertas.model;

import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import br.com.libertas.db.CategoriaDao;
import br.com.libertas.dto.Categoria;


/**
 * Servlet implementation class InserirCategoria
 */
@WebServlet("/InserirCategoria")
public class InserirCategoria extends HttpServlet implements Modelo {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InserirCategoria() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			CategoriaDao cDao = new CategoriaDao();
			Categoria c = new Categoria();
			Gson gson = new Gson();
			
			c.setNome(request.getParameter("nomeCategoria"));
			c.setDescricao(request.getParameter("descricaoCategoria"));
	
			PrintWriter out = response.getWriter();
			String res = gson.toJson(cDao.inserir(c));
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
