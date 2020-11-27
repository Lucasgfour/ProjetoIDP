package br.com.libertas.model;

import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import br.com.libertas.db.ContaReceberDao;
import br.com.libertas.dto.ContaReceber;

/**
 * Servlet implementation class AlterarContaReceber
 */
@WebServlet("/AlterarContaReceber")
public class AlterarContaReceber extends HttpServlet implements Modelo{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlterarContaReceber() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			ContaReceberDao cpDao = new ContaReceberDao();
			ContaReceber cp = new ContaReceber();
			Gson gson = new Gson();
			
			cp.setDescricao(request.getParameter("descricaoContasReceber"));
			cp.setValor(Double.parseDouble(request.getParameter("valorContasReceber")));
			cp.setData(Date.valueOf(request.getParameter("dataContasReceber")));
			cp.setStatus(request.getParameter("statusContasReceber"));
			cp.setParcela(Integer.parseInt(request.getParameter("parcelaContasReceber")));
			cp.setId(Integer.parseInt(request.getParameter("identCad")));
			
			PrintWriter out = response.getWriter();
			String res = gson.toJson(cpDao.alterar(cp));
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
