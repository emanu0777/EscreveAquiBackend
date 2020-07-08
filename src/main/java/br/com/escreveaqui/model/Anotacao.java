package br.com.escreveaqui.model;
//Create by Emanuel dos Santos Costa.
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "anotacao")
public class Anotacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(max = 200, min = 1)
	private String titulo;

	private String texto;
	
	private LocalDate dataCriacao;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	public Anotacao() {}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Anotacao other = (Anotacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public Anotacao(Long id, @Size(max = 200, min = 1) String titulo, String texto, LocalDate dataCriacao,
			Usuario usuario) {
		this.id = id;
		this.titulo = titulo;
		this.texto = texto;
		this.dataCriacao = dataCriacao;
		this.usuario = usuario;
	}
	
	
}
