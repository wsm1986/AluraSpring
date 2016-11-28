package br.com.casadocodigo.loja.controllers.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.models.Produtos;
import br.com.casadocodigo.loja.models.TipoPreco;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoDAO dao;

	@RequestMapping("/form")
	public ModelAndView index() {
		System.out.println("Cadastro Produto");
		ModelAndView model = new ModelAndView("produtos/form");
		model.addObject("tipos", TipoPreco.values());
		return model;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String gravar(Produtos produtos, RedirectAttributes redirectAttributes) {
		System.out.println(produtos.toString());
		redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
		dao.gravar(produtos);
		return "redirect:produtos";
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView lista() {
		ModelAndView model = new ModelAndView("produtos/lista");
		model.addObject("produtos", dao.findAll());
		return model;
	}
}
