package br.com.escreveaqui.Dto;

//Create by Emanuel dos Santos Costa.

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import br.com.escreveaqui.model.Anotacao;

public class AnotacaoDto {
	
	private Long id;
	
	private String titulo;
	
	private String texto;
	
	private LocalDate dataCriacao;

	
	public AnotacaoDto(Anotacao anotacao) {
		this.id =  anotacao.getId();
		this.titulo = anotacao.getTitulo();
		this.texto = anotacao.getTexto();
		this.dataCriacao = anotacao.getDataCriacao();
	}
	
	public static List<AnotacaoDto> converter(List<Anotacao> anotacoes){
		
		return anotacoes.stream().map(AnotacaoDto::new).collect(Collectors.toList());
	}
	
	
	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getTexto() {
		return texto;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}
	
}
