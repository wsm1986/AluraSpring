package br.com.casadocodigo.loja.controllers.produto;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoDAO dao;

	@Autowired
	private FileSaver savar;

	// Metodo responsavel para habilitar o nosso validador para a classe Produto
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.addValidators(new ProdutoValidation());
	}

	@RequestMapping("/form")
	public ModelAndView form(Produto produto) throws IOException, ServletException {
		System.out.println("Cadastro Produto");
		ModelAndView model = new ModelAndView("produtos/form");
		model.addObject("tipos", TipoPreco.values());
		return model;
	}
	@RequestMapping("/teste")
	public void teste(Produto produto) throws IOException, ServletException {
		savar.gerarZipRecursivo("/home/dbaldani/AZUL/SchemaCentric");
		
	}


	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView grava(MultipartFile sumario, @Valid Produto produto, BindingResult result,
			RedirectAttributes redirectAttributes) throws IOException, ServletException {

		if (result.hasErrors()) {
			return form(produto);
			// return new ModelAndView("produtos/form");
		}

		String sumarioPath = savar.write("arquivos-sumario", sumario);

		
		produto.setSumarioPath(sumarioPath);

		System.out.println(sumario.getOriginalFilename());

		dao.gravar(produto);

		redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");

		return new ModelAndView("redirect:produtos");
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView lista() {
		ModelAndView model = new ModelAndView("produtos/lista");
		model.addObject("produtos", dao.findAll());
		return model;
	}

	@RequestMapping("/detalhe/{id}")
	public ModelAndView detalhe(@PathVariable("id") Integer id) {
		ModelAndView model = new ModelAndView("produtos/detalhe");
		model.addObject("produto", dao.findById(id));
		return model;
	}

}
