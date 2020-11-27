package br.com.libertas.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.com.libertas.db.VendaDao;
import br.com.libertas.dto.Venda;


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
			
			venda.setData_venda(LocalDate.now());
			venda.setForma_pagamento(request.getParameter("formaPagamento"));
	
			PrintWriter out = response.getWriter();
			String res = new Gson().toJson(vDao.inserirVenda(venda));
			out.print(res);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
