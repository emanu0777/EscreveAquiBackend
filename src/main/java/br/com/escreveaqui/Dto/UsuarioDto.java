package br.com.escreveaqui.Dto;
//Create by Emanuel dos Santos Costa.
import java.util.List;
import java.util.stream.Collectors;

import br.com.escreveaqui.model.Usuario;

public class UsuarioDto {
	
	private String nome;
	
	private String email;
	
	private String senha;

	
	public UsuarioDto(Usuario usuario) {
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.senha = usuario.getSenha();
	}

	
	public static List<UsuarioDto> converter(List<Usuario> usuarios) {
		return usuarios.stream().map(UsuarioDto::new).collect(Collectors.toList());
	}
	
	
	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	

}
