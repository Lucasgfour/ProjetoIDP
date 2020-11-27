package br.com.libertas.model;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.com.libertas.db.ProdutoDao;
import br.com.libertas.dto.Produto;

/**
 * Servlet implementation class InserirProduto
 */
@WebServlet("/InserirProduto")
public class InserirProduto extends HttpServlet implements Modelo{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InserirProduto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			ProdutoDao pDao = new ProdutoDao();
			Produto p = new Produto();
			Gson gson = new Gson();
			
			p.setCodigo(request.getParameter("codigo"));
			p.setDescricao(request.getParameter("descricao"));
			p.setPreco_custo(Double.parseDouble(request.getParameter("preco-custo")));
			p.setPreco_venda(Double.parseDouble(request.getParameter("preco-venda")));
			p.setCategoria(Integer.parseInt(request.getParameter("categoria")));;
			p.setCod_fornecedor(Integer.parseInt(request.getParameter("fornecedor")));
			p.setQuantidade(Integer.parseInt(request.getParameter("qtd-estoque")));
			
			PrintWriter out = response.getWriter();
			String res = gson.toJson(pDao.inserir(p));
			out.print(res);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//execute(request, response);
	}

}
