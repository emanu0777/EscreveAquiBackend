package br.com.escreveaqui.model;
//Create by Emanuel dos Santos Costa.
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.springframework.security.core.GrantedAuthority;
@Entity
@Table(name = "perfil")

public class Perfil implements GrantedAuthority{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@Max(50)
	private String nome;

	@Override
	public String getAuthority() {
		
		return nome;
	}


}
