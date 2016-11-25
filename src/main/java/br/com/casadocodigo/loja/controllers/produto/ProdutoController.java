package br.com.casadocodigo.loja.controllers.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.models.Produtos;

@Controller
public class ProdutoController {

	@Autowired
	private ProdutoDAO dao;

	@RequestMapping("/produtos/form")
	public String index() {
		System.out.println("Cadastro Produto");
		return "produtos/form";
	}

	@RequestMapping("/produtos")
	public String gravar(Produtos produtos) {
		System.out.println(produtos.toString());
		dao.gravar(produtos);
		return "produtos/ok";
	}
}
