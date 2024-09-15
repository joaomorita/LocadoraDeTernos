package br.com.locadoradeternos.api_rest_locadora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.locadoradeternos.api_rest_locadora.model.Terno;

import java.util.List;

@Repository /* Para a classe manipular e acessar os dados do banco */
public interface TernoRepository extends JpaRepository<Terno, Long>{

    // Usando a convenção de nomes do JPA para criar uma query que retorna os ternos disponíveis
    List<Terno> findByDisponivelTrue();
}
