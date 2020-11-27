package br.com.libertas.model;

import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import br.com.libertas.db.ContaPagarDao;

/**
 * Servlet implementation class ExcluiContaPagar
 */
@WebServlet("/ExcluiContaPagar")
public class ExcluiContaPagar extends HttpServlet implements Modelo {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExcluiContaPagar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			ContaPagarDao cpDao = new ContaPagarDao();
			Gson gson = new Gson();
			
			int id = Integer.parseInt(request.getParameter("identCad"));
			
			PrintWriter out = response.getWriter();
			String res = gson.toJson(cpDao.excluir(id));
			out.print(res);
			
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
