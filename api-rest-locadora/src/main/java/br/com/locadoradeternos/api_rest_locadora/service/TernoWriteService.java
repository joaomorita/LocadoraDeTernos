package br.com.locadoradeternos.api_rest_locadora.service;

import org.springframework.stereotype.Service;
import br.com.locadoradeternos.api_rest_locadora.model.Ternos;
import br.com.locadoradeternos.api_rest_locadora.repository.TernoRepository;

// Método para cadastrar novo terno
@Service
public class TernoWriteService {

    private final TernoRepository ternoRepository;

    public TernoWriteService(TernoRepository ternoRepository) {
        this.ternoRepository = ternoRepository;
    }

    public Ternos cadastrarNovoTerno(Ternos terno) {
        return ternoRepository.save(terno);
    }
}