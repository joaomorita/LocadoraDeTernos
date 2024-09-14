package br.com.locadoradeternos.api_rest_locadora.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import br.com.locadoradeternos.api_rest_locadora.exception.TernoNotFoundException;
import br.com.locadoradeternos.api_rest_locadora.model.Ternos;
import br.com.locadoradeternos.api_rest_locadora.repository.TernoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TernoReadServiceTest {

    @Mock
    private TernoRepository ternoRepository; // Simula o comportamento do repositório

    @InjectMocks
    private TernoReadService ternoReadService; // Injeta o repositório simulado no serviço

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa as anotações do Mockito
    }

    @Test
    void testBuscarTernoPeloId_Encontrado() {
        // Simulando um terno existente no repositório
        Ternos terno = new Ternos();
        terno.setId(1L);
        terno.setCor("Preto");

        // Quando o repositório for chamado com o ID 1, retorne o terno simulado
        when(ternoRepository.findById(1L)).thenReturn(Optional.of(terno));

        // Chama o método que estamos testando
        Ternos result = ternoReadService.buscarTernoPeloId(1L);

        // Verifica se o resultado é o esperado
        assertEquals(1L, result.getId());
        assertEquals("Preto", result.getCor());
    }

    @Test
    void testBuscarTernoPeloId_NaoEncontrado() {
        // Simulando a ausência de um terno no repositório
        when(ternoRepository.findById(1L)).thenReturn(Optional.empty());

        // Verifica se a exceção personalizada é lançada
        TernoNotFoundException exception = assertThrows(TernoNotFoundException.class, () -> {
            ternoReadService.buscarTernoPeloId(1L);
        });

        // Verifica se a mensagem de erro é a correta
        assertEquals("Terno com ID 1 não foi encontrado.", exception.getMessage());
    }
}

