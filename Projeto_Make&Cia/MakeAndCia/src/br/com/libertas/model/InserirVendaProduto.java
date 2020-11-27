package br.com.libertas.model;

import java.io.PrintWriter;
import java.time.LocalDate;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.libertas.db.VendaDao;
import br.com.libertas.db.VendasProdutoDao;
import br.com.libertas.dto.Venda;
import br.com.libertas.dto.VendasProduto;

/**
 * Servlet implementation class InserirVendaProduto
 */
@WebServlet("/InserirVendaProduto")
public class InserirVendaProduto extends HttpServlet implements Modelo {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InserirVendaProduto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public void execute(HttpServletRequest request, HttpServletResponse response) {
 		try {
 			
 			PrintWriter out = response.getWriter();
 			out.print("");
 			
 		}
 		catch (Exception e) {
 			e.printStackTrace();
 		}
 	}

}
