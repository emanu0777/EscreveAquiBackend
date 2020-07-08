package br.com.escreveaqui.Dto;
//Create by Emanuel dos Santos Costa.
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

import br.com.escreveaqui.model.Usuario;
import br.com.escreveaqui.repository.UsuarioRepository;

public class AtualizacaoFormUsuario {
	
	@NonNull
	@Max(50)
	private String nome;
	
	@Size(min=14, max = 50)
	private String email;

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public Usuario atualiza(Long codigo,  UsuarioRepository usuarioRepository) {
		Usuario usuario = usuarioRepository.getOne(codigo);
		usuario.setEmail(this.getEmail());
		usuario.setNome(this.getNome());
		
		return usuario;
	}
	
	
	

	
	
	
	

}
