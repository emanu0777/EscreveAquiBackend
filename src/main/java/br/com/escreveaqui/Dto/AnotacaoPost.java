package br.com.escreveaqui.Dto;

//Create by Emanuel dos Santos Costa.
import java.time.LocalDate;

import br.com.escreveaqui.model.Anotacao;
import br.com.escreveaqui.model.Usuario;

public class AnotacaoPost {

	private Long id;
	
	private String titulo;
	
	private String texto;
	
	private LocalDate dataCriacao = LocalDate.now();
	
	private Usuario usuario;
	
	public Anotacao converter() {
		
		return new Anotacao(id, titulo, texto, dataCriacao, usuario);
	
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
	
	
}
