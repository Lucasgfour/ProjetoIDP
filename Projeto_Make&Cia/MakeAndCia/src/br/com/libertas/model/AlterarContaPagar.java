package br.com.libertas.model;

import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import br.com.libertas.db.ContaPagarDao;
import br.com.libertas.dto.ContaPagar;

/**
 * Servlet implementation class AlterarContaPagar
 */
@WebServlet("/AlterarContaPagar")
public class AlterarContaPagar extends HttpServlet implements Modelo{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlterarContaPagar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			ContaPagarDao cpDao = new ContaPagarDao();
			ContaPagar cp = new ContaPagar();
			Gson gson = new Gson();
			
			cp.setDescricao(request.getParameter("descricaoContasPagar"));
			cp.setValor(Double.parseDouble(request.getParameter("valorContasPagar")));
			cp.setData(Date.valueOf(request.getParameter("dataContasPagar")));
			cp.setStatus(request.getParameter("statusContasPagar"));
			cp.setParcela(Integer.parseInt(request.getParameter("parcelaContasPagar")));
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
