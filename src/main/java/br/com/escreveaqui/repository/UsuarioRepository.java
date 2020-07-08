package br.com.escreveaqui.repository;
//Create by Emanuel dos Santos Costa.
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.escreveaqui.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String username);
	

}
