package br.com.escreveaqui.repository;
//Create by Emanuel dos Santos Costa.
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.escreveaqui.model.Anotacao;

public interface AnotacaoRepository extends  JpaRepository<Anotacao, Long> {

	List<Anotacao> findByTitulo(String titulo);
	
	@Query("SELECT a FROM Anotacao a WHERE a.titulo LIKE %:titulo% AND a.usuario.id = :id")
	List<Anotacao> buscarAnotacaoDeUsuariosPorTitulo(@Param("id") Long id, @Param("titulo") String titulo);	
	
	
	@Query("SELECT a FROM Anotacao a WHERE a.dataCriacao = :data AND a.usuario.id = :id")
	List<Anotacao> buscarAnotacaoDeUsuariosPorData(@Param("id") Long id, @Param("data") LocalDate data);
}
