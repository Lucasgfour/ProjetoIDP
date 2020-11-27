package br.com.libertas.model;

import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.com.libertas.db.FornecedorDao;
import br.com.libertas.dto.Fornecedor;

/**
 * Servlet implementation class AlterarFornecedor
 */
@WebServlet("/AlterarFornecedor")
public class AlterarFornecedor extends HttpServlet implements Modelo{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlterarFornecedor() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			FornecedorDao fDao = new FornecedorDao();
			Fornecedor f = new Fornecedor();
			Gson gson = new Gson();
			
			f.setNome(request.getParameter("nomeFornecedor"));
			f.setCnpj(request.getParameter("cnpjFornecedor"));
			f.setEndereco(request.getParameter("enderecoFornecedor"));
			f.setCidade(request.getParameter("cidadeFornecedor"));
			f.setTelefone(request.getParameter("telefoneFornecedor"));
			f.setId(Integer.parseInt(request.getParameter("identCad")));
			
			PrintWriter out = response.getWriter();
			String res = gson.toJson(fDao.alterar(f));
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
