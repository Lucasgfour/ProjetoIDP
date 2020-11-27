package br.com.libertas.model;

import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import br.com.libertas.db.ProdutoDao;

/**
 * Servlet implementation class ConsultarProduto
 */
@WebServlet("/ConsultarProduto")
public class ConsultarProduto extends HttpServlet implements Modelo {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsultarProduto() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
    	
		try {
			
			int id = Integer.parseInt(request.getParameter("identCad"));
			
    		ProdutoDao pdao = new ProdutoDao();
        	PrintWriter pw = response.getWriter();
        	Gson gson = new Gson();
        	pw.print(gson.toJson(pdao.consutar(id)));
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
		
	}

	/*/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	/*protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}*/


}
