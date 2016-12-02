package br.com.casadocodigo.loja.controllers.produto;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoDAO dao;

	// Metodo responsavel para habilitar o nosso validador para a classe Produto
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.addValidators(new ProdutoValidation());
	}

	@RequestMapping("/form")
	public ModelAndView form(Produto produto) {
		System.out.println("Cadastro Produto");
		ModelAndView model = new ModelAndView("produtos/form");
		model.addObject("tipos", TipoPreco.values());
		return model;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView grava(@Valid Produto produto, BindingResult result,
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			 return form(produto);
			//return new ModelAndView("produtos/form");
		}

		dao.gravar(produto);

		redirectAttributes.addFlashAttribute("sucesso",
				"Produto cadastrado com sucesso!");

		return new ModelAndView("redirect:produtos");
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView lista() {
		ModelAndView model = new ModelAndView("produtos/lista");
		model.addObject("produtos", dao.findAll());
		return model;
	}
}
