package br.com.escreveaqui.Dto;
//Create by Emanuel dos Santos Costa.
import javax.validation.constraints.Size;
import br.com.escreveaqui.model.Anotacao;
import br.com.escreveaqui.repository.AnotacaoRepository;

public class AtualizacaoAnotacao {

	@Size(max = 200, min = 1)
	private String titulo;

	private String texto;

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

	public Anotacao atualizaFormAnotacao(Long codigo, AnotacaoRepository anotacaoRepository) {
		
		Anotacao anotacao = anotacaoRepository.getOne(codigo);
		
		anotacao.setTitulo(this.getTitulo());
		anotacao.setTexto(this.getTexto());
		
		return anotacao;
		
	}


	
	
	
	
}
