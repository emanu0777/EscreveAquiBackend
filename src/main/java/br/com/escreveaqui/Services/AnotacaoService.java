package br.com.escreveaqui.Services;
//Create by Emanuel dos Santos Costa.

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.escreveaqui.form.LoginForm;
import br.com.escreveaqui.model.Usuario;

public class AnotacaoService {
	
	
	public String indentificaHost(String email) {
		if(this.emailValido(email)) {
			if(email.contains("@hotmail.com"))
				return "smtp.live.com";
			else if(email.contains("@gmail.com"))
				return "smtp.gmail.com";
		}
		
		return "Endereco de Email Invalido";
	}
	
	public Boolean emailValido(String email) {
		
		if(email.contains("@hotmail.com") ||  email.contains("@gmail.com") || email.contains("@outlook.com.br"))
				return true;
		
		return false;
	}
	
	public Boolean verificaDadosEmail(Usuario usuario, LoginForm Dadoslogin) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(!(usuario.getEmail().toLowerCase() == Dadoslogin.getEmail().toLowerCase()) || !(usuario.getPassword() == encoder.encode(Dadoslogin.getSenha()))) {			
			return false;
		}
		
		return true;
		
	}
	

}
