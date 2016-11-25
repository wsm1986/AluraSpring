package br.com.casadocodigo.loja.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Produtos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String titulo;
	private String descricao;
	private int paginas;

	@Override
	public String toString() {
		return "Produtos [titulo=" + titulo + ", descricao=" + descricao + ", paginas=" + paginas + "]";
	}
}
