package br.com.locadoradeternos.api_rest_locadora.service;

import org.springframework.stereotype.Service;
import br.com.locadoradeternos.api_rest_locadora.model.Ternos;
import br.com.locadoradeternos.api_rest_locadora.repository.TernoRepository;

@Service
public class TernoDeleteService {

    private final TernoRepository ternoRepository;
    private final TernoReadService ternoReadService; // Pode reutilizar o serviço de leitura

    public TernoDeleteService(TernoRepository ternoRepository, TernoReadService ternoReadService) {
        this.ternoRepository = ternoRepository;
        this.ternoReadService = ternoReadService;
    }

    // Método para deletar um terno
    public Boolean deletarTernoPeloId(Long id) {
        Ternos terno = ternoReadService.buscarTernoPeloId(id); // Reutiliza a lógica de busca
        ternoRepository.deleteById(id);
        return true;
    }
}