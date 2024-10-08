package br.com.locadoradeternos.api_rest_locadora.service;

import org.springframework.stereotype.Service;
import br.com.locadoradeternos.api_rest_locadora.repository.TernoRepository;

import java.util.NoSuchElementException;

@Service
public class TernoDeleteService {

    private final TernoRepository ternoRepository;
    private final TernoReadService ternoReadService;

    public TernoDeleteService(TernoRepository ternoRepository, TernoReadService ternoReadService) {
        this.ternoRepository = ternoRepository;
        this.ternoReadService = ternoReadService;
    }

    // Método para deletar um terno
    public void deletarTernoPeloId(Long id) {
        // Verifica se o terno existe, senão lança exceção
        ternoReadService.buscarTernoPeloId(id);
        // Se chegar aqui, o terno foi encontrado, então pode deletar
        ternoRepository.deleteById(id);
    }
}