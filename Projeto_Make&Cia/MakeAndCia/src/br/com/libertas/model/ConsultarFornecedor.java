package br.com.libertas.model;

import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import br.com.libertas.db.FornecedorDao;

/**
 * Servlet implementation class ConsultarFornecedor
 */
@WebServlet("/ConsultarFornecedor")
public class ConsultarFornecedor extends HttpServlet implements Modelo {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsultarFornecedor() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			int id = Integer.parseInt(request.getParameter("identCad"));
			
			FornecedorDao fdao = new FornecedorDao();
        	PrintWriter pw = response.getWriter();
        	Gson gson = new Gson();
        	pw.print(gson.toJson(fdao.consultar(id)));
        	
		} catch (Exception e) {
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
