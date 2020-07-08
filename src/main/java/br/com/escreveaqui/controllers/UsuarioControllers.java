package br.com.escreveaqui.controllers;
//Create by Emanuel dos Santos Costa.

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.escreveaqui.Dto.AtualizacaoFormUsuario;
import br.com.escreveaqui.Dto.UsuarioDto;
import br.com.escreveaqui.Dto.UsuarioPost;
import br.com.escreveaqui.model.Usuario;
import br.com.escreveaqui.repository.UsuarioRepository;


@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/usuarios")
public class UsuarioControllers {

	@Autowired
	UsuarioRepository usuarioRepository;
	

	
	
	//LIST
	@GetMapping
	private List<UsuarioDto> listar(){
		List<Usuario> usuarios = usuarioRepository.findAll();
		return UsuarioDto.converter(usuarios);	
	}
	
	
	//CRIATED
	@PostMapping
	private ResponseEntity<UsuarioDto> cadastrarUsuario(@RequestBody  @Valid UsuarioPost usuarioRetornado, UriComponentsBuilder uriBuilder) {
	
		Usuario usuario = usuarioRetornado.converter();
		URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getCodigo()).toUri();
		usuarioRepository.save(usuario);
		return ResponseEntity.created(uri).body(new UsuarioDto(usuario));
		
	}
	
	//DELETE - Não pretendo implementar pq na minha app  (até agora) o Usuario não pode excluir sua conta
	
	@DeleteMapping("/{codigo}")
	private ResponseEntity<UsuarioDto> deletarUsuario(@PathVariable Long codigo){
		usuarioRepository.deleteById(codigo);
		return ResponseEntity.noContent().build();
	}

	
	@GetMapping("/{codigo}")
	private UsuarioDto buscaPorId(@PathVariable Long codigo) {
		Usuario usuario = usuarioRepository.getOne(codigo);
		return new UsuarioDto(usuario);
		
	}
	
	@PutMapping("/{codigo}")
	private ResponseEntity<UsuarioDto> atualiza(@PathVariable Long codigo, @RequestBody @Valid AtualizacaoFormUsuario form ){
		Usuario usuario = form.atualiza(codigo, usuarioRepository);
		usuarioRepository.save(usuario);
		return ResponseEntity.ok(new UsuarioDto(usuario));
	}
	
	
}
