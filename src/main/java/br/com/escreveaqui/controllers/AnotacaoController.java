package br.com.escreveaqui.controllers;
//Create by Emanuel dos Santos Costa.
import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Optional;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.escreveaqui.Dto.AnotacaoDto;
import br.com.escreveaqui.Dto.AnotacaoPost;
import br.com.escreveaqui.Dto.AtualizacaoAnotacao;
import br.com.escreveaqui.Services.AnotacaoService;
import br.com.escreveaqui.form.LoginForm;
import br.com.escreveaqui.model.Anotacao;
import br.com.escreveaqui.model.Usuario;
import br.com.escreveaqui.repository.AnotacaoRepository;
import br.com.escreveaqui.repository.UsuarioRepository;

@CrossOrigin("http://localhost:4200")
@RestController
public class AnotacaoController {

	@Autowired
	private AnotacaoRepository anotacaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	
	//GET POR TITULO
	@GetMapping("usuarios/{id}/anotacoes")
	private List<AnotacaoDto> lista(String titulo, @DateTimeFormat(iso = ISO.DATE) LocalDate data, @PathVariable Long id){
		
		if(titulo == null && data == null) {
			Usuario usuario = usuarioRepository.getOne(id);
			List<Anotacao> anotacoes = usuario.getAnotacoes();
			return AnotacaoDto.converter(anotacoes);
		}else if(titulo != null && data == null) {
			List<Anotacao> anotacoes = anotacaoRepository.buscarAnotacaoDeUsuariosPorTitulo(id, titulo);
			return AnotacaoDto.converter(anotacoes);
		}
		
		List<Anotacao> anotacoes = anotacaoRepository.buscarAnotacaoDeUsuariosPorData(id, data);
		return AnotacaoDto.converter(anotacoes);

	}
	
	@GetMapping("usuarios/{id}/anotacoes/{codigoAnotacao}")
	private AnotacaoDto selecionaAnotacao(String titulo, @PathVariable Long id, @PathVariable Long codigoAnotacao){
		
		Usuario usuario = usuarioRepository.getOne(id);
		Anotacao anotacao = anotacaoRepository.findById(codigoAnotacao).get();

		Anotacao anotacaoUsuario = usuario.buscaAnotacaoDoUsuario(anotacao);
		
		if(anotacaoUsuario == null) {
			throw new EntityNotFoundException();
		}
		
	
		return ResponseEntity.ok(new AnotacaoDto(anotacaoUsuario)).getBody();

		}
		
	
	// CREATE
	@PostMapping("usuarios/{id}/anotacoes")
	private ResponseEntity<AnotacaoDto> cadastrarAnotacao(@RequestBody @Valid AnotacaoPost anotacao, UriComponentsBuilder uriBuilder, @PathVariable Long id) {
		
		
		Usuario usuario = usuarioRepository.findById(id).get();
		
		//Adicionado o Usuario na Anotacao
		anotacao.setUsuario(usuario);
				
		Anotacao anotacao2 = anotacao.converter();
		
		//Adicionando a anotacao no Usuario
		usuario.getAnotacoes().add(anotacao2);
		
		URI uri = uriBuilder.path("/anotacoes/{id}").buildAndExpand(anotacao2.getId()).toUri();
		anotacaoRepository.save(anotacao2);
		usuarioRepository.save(usuario);
		
		return ResponseEntity.created(uri).body(new AnotacaoDto(anotacao2));
	}
	
	@PostMapping("usuarios/{id}/anotacoes/service/envia/{codigoAnotacao}")
	private ResponseEntity<Map<String, Object>> enviarAnotacaoParaEmail(@Valid @RequestBody LoginForm form, @PathVariable Long id, @PathVariable Long codigoAnotacao) {
		
		
		 Map<String, Object> json = new HashMap();
		AnotacaoService anotacaoService = new AnotacaoService();
		Usuario usuario = usuarioRepository.findById(id).get();
		Anotacao anotacao = anotacaoRepository.findById(codigoAnotacao).get();
		
		String statusEnvio = new String();
		String emailUser = usuario.getEmail().toLowerCase();
		String senhaUsuario = form.getSenha();
		//Definindo propriedades para enviar o Email
		//System.out.println(form.getSenha());
		
			String to = emailUser;
			String from = emailUser;
			Properties properties = System.getProperties();
		    properties.put("mail.smtp.starttls.enable", "true"); 
		    properties.put("mail.smtp.host", anotacaoService.indentificaHost(emailUser));
		    properties.put("mail.smtp.port", "587");
		    properties.put("mail.smtp.auth", "true");
		    properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		  Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
		    return new PasswordAuthentication(emailUser, senhaUsuario);
		}});
		  	
		  
		  try{
		     MimeMessage message = new MimeMessage(session);
		     message.setFrom(new InternetAddress(from));
		     message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
		     message.setSubject(anotacao.getTitulo());
		     message.setContent(anotacao.getTexto(),"text/html" );
		     Transport.send(message);
		     statusEnvio = "Mensagem Enviada!";
		  }catch (MessagingException mex) {
		    statusEnvio =  mex.getMessage();
		    json.put("status", statusEnvio);
		    return ResponseEntity.badRequest().body(json);
		  }
		  
		  json.put("status", statusEnvio);
		  
		  return ResponseEntity.ok(json);
	}
	
	
	
	//UPDATE
	@PutMapping("usuarios/{id_Usuario}/anotacoes/{codigo}")
	private ResponseEntity<AnotacaoDto> atualiza(@PathVariable Long id_Usuario, @PathVariable Long codigo, @RequestBody @Valid AtualizacaoAnotacao form){
		Usuario usuarioRetornado = usuarioRepository.getOne(id_Usuario);
		Anotacao anotacaoRetornada = anotacaoRepository.getOne(codigo);
		
		if(anotacaoRetornada == null) {
			throw new EntityNotFoundException();
		}
		
		
		// Faco o update com o form retornado
		Anotacao anotacaoAtualizada = form.atualizaFormAnotacao(codigo, anotacaoRepository);
		 
		anotacaoRepository.save(anotacaoAtualizada);
		
		usuarioRepository.save(usuarioRetornado);
		
		return ResponseEntity.ok().body(new AnotacaoDto(anotacaoAtualizada));
		
	}
	
	//DELETE
	@DeleteMapping("usuarios/{id_Usuario}/anotacoes/{codigo}")
	private ResponseEntity<AnotacaoDto> deleta(@PathVariable Long codigo, @PathVariable Long id_Usuario){
		Usuario usuarioRetornado = usuarioRepository.getOne(id_Usuario);
		Optional<Anotacao> anotacaoRetornada = anotacaoRepository.findById(codigo);
		
		/*
		if(usuarioRetornado == null) {
			throw new EntityNotFoundException();
			
		}
		*/
		
		usuarioRetornado.getAnotacoes().remove(anotacaoRetornada.get());
		
		anotacaoRepository.delete(anotacaoRetornada.get());
		usuarioRepository.save(usuarioRetornado);
		
		return ResponseEntity.noContent().build();
		
		
	}

	
	
	
}
