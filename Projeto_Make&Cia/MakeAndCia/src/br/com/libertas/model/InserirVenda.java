package br.com.libertas.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.jdi.Type;

import br.com.libertas.db.VendaDao;
import br.com.libertas.db.VendasProdutoDao;
import br.com.libertas.dto.Venda;
import br.com.libertas.dto.VendasProduto;


/**
 * Servlet implementation class InserirVenda
 */
@WebServlet("/InserirVenda")
public class InserirVenda extends HttpServlet implements Modelo {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InserirVenda() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			Venda venda = new Venda();
			VendaDao vDao = new VendaDao();
			VendasProdutoDao pDao =new VendasProdutoDao();
			
			venda.setData_venda(LocalDate.now());
			venda.setForma_pagamento(request.getParameter("formaPagamento"));
			
			JsonObject resultado = vDao.inserirVenda(venda);
			int codigo = Integer.parseInt(resultado.get("codigo").toString());
			
			if(codigo > 0) {
				String produtos[] = request.getParameter("vendaProdutos").split(";");
				for (String prod : produtos) {
					try {
						String p[] = prod.split("/");
						VendasProduto a = new VendasProduto();
						a.setPedido(codigo);
						a.setProduto((Integer.parseInt(p[0])));
						a.setQuantidade(Integer.parseInt(p[1]));
						a.setPreco(Double.parseDouble(p[2]));
						JsonObject resProduto = pDao.inserir(a);
						System.out.println(resProduto.get("Condicao").getAsString() + " - " + resProduto.get("Mensagem").getAsString());
					} catch (Exception e) { e.printStackTrace(); }
				}
			}
			
			
	
			PrintWriter out = response.getWriter();
			String res = new Gson().toJson(resultado);
			out.print(res);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
