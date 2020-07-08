package br.com.escreveaqui.Dto;
//Create by Emanuel dos Santos Costa.
import br.com.escreveaqui.model.Usuario;

public class UsuarioPost {
	
	private Long codigo;
	
	private String nome;
	
	private String email;
	
	private String senha;
	
	public Usuario converter() {
		
		return new Usuario(codigo, nome, email, senha);
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	
}
