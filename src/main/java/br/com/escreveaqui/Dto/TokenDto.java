package br.com.escreveaqui.Dto;
//Create by Emanuel dos Santos Costa.


public class TokenDto {
	
	private String token;
	
	private String tipo;
	
	
	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public TokenDto(String token, String tipo) {

		this.token = token;
		this.tipo = tipo;
		
	}

	
	
}
