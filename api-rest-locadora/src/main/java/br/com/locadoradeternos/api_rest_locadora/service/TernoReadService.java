package br.com.locadoradeternos.api_rest_locadora.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.locadoradeternos.api_rest_locadora.exception.TernoNotFoundException;
import br.com.locadoradeternos.api_rest_locadora.model.Terno;
import br.com.locadoradeternos.api_rest_locadora.repository.TernoRepository;

@Service
public class TernoReadService {

    private final TernoRepository ternoRepository;

    public TernoReadService(TernoRepository ternoRepository) {
        this.ternoRepository = ternoRepository;
    }

    // Método para listar ternos
    public List<Terno> listaTernos() {
        return ternoRepository.findAll();
    }

    // Método para buscar terno pelo ID
    public Terno buscarTernoPeloId(Long id) {
        return ternoRepository.findById(id).orElseThrow(() -> new TernoNotFoundException(id));
    }

    // Método para buscar ternos disponiveis
    public List<Terno> TernosDisponiveis(){
        return ternoRepository.findByDisponivelTrue();
    }
}