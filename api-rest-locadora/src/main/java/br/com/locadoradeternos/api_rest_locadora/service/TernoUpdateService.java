package br.com.locadoradeternos.api_rest_locadora.service;

import org.springframework.stereotype.Service;
import br.com.locadoradeternos.api_rest_locadora.model.Terno;
import br.com.locadoradeternos.api_rest_locadora.repository.TernoRepository;

@Service
public class TernoUpdateService {

    private final TernoRepository ternoRepository;
    private final TernoReadService ternoReadService; // Pode reutilizar o serviço de leitura

    public TernoUpdateService(TernoRepository ternoRepository, TernoReadService ternoReadService) {
        this.ternoRepository = ternoRepository;
        this.ternoReadService = ternoReadService;
    }

    // Método para atualizar dados de um terno
    public Terno atualizaDadosDoTerno(Long id, Terno ternoComDadosAlterados) {
        Terno ternoAtual = ternoReadService.buscarTernoPeloId(id); // Reutiliza a lógica de busca
        ternoAtual.setCor(ternoComDadosAlterados.getCor());
        ternoAtual.setDisponivel(ternoComDadosAlterados.getDisponivel());
        ternoAtual.setTamanho(ternoComDadosAlterados.getTamanho());

        return ternoRepository.save(ternoAtual);
    }
}