package br.com.casadocodigo.loja.controllers.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.models.Produtos;
import br.com.casadocodigo.loja.models.TipoPreco;

@Controller
public class ProdutoController {

	@Autowired
	private ProdutoDAO dao;

	@RequestMapping("/produtos/form")
	public ModelAndView index() {
		System.out.println("Cadastro Produto");
		ModelAndView model= new ModelAndView("produtos/form");
		model.addObject("tipos", TipoPreco.values());
		return model;
	}

	@RequestMapping("/produtos")
	public String gravar(Produtos produtos) {
		System.out.println(produtos.toString());
		dao.gravar(produtos);
		return "produtos/ok";
	}
}
