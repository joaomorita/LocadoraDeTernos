package br.com.locadoradeternos.api_rest_locadora.controller;

import br.com.locadoradeternos.api_rest_locadora.model.Terno;
import br.com.locadoradeternos.api_rest_locadora.repository.TernoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TernoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TernoRepository ternoRepository;

    @BeforeEach
    public void setUp(){
        ternoRepository.deleteAll();

    }

    @Test
    void testListaTodosOsTernos() throws Exception{

        Terno terno1 = new Terno();
        terno1.setTamanho("G");
        terno1.setCor("Preto");
        terno1.setDisponivel(true);
        ternoRepository.save(terno1);

        Terno terno2 = new Terno();
        terno2.setTamanho("M");
        terno2.setCor("Cinza");
        terno2.setDisponivel(false);
        ternoRepository.save(terno2);

        mockMvc.perform(get("/api/ternos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(terno1.getId()))
                .andExpect(jsonPath("$[0].tamanho").value(terno1.getTamanho()))
                .andExpect(jsonPath("$[0].cor").value(terno1.getCor()))
                .andExpect(jsonPath("$[0].disponivel").value(terno1.getDisponivel()))
                .andExpect(jsonPath("$[1].id").value(terno2.getId()))
                .andExpect(jsonPath("$[1].tamanho").value(terno2.getTamanho()))
                .andExpect(jsonPath("$[1].cor").value(terno2.getCor()))
                .andExpect(jsonPath("$[1].disponivel").value(terno2.getDisponivel()));

    }

    @Test
    void testListaTernoPeloId() throws Exception{

        Terno terno1 = new Terno();
        terno1.setTamanho("G");
        terno1.setCor("Preto");
        terno1.setDisponivel(true);
        ternoRepository.save(terno1);

        Terno terno2 = new Terno();
        terno2.setTamanho("M");
        terno2.setCor("Cinza");
        terno2.setDisponivel(false);
        ternoRepository.save(terno2);

        Terno ternoSalvo = ternoRepository.findAll().get(0);

        mockMvc.perform(get("/api/ternos/{id}", ternoSalvo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tamanho").value(ternoSalvo.getTamanho()))
                .andExpect(jsonPath("$.cor").value(ternoSalvo.getCor()))
                .andExpect(jsonPath("$.disponivel").value(ternoSalvo.getDisponivel()));
    }

    @Test
    void testListaTernoPeloIdNaoExistente() throws Exception {
        mockMvc.perform(get("/api/ternos/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testListaDeTernosDisponiveis() throws Exception {

        Terno terno1 = new Terno();
        terno1.setTamanho("G");
        terno1.setCor("Preto");
        terno1.setDisponivel(true);
        ternoRepository.save(terno1);

        Terno terno2 = new Terno();
        terno2.setTamanho("M");
        terno2.setCor("Cinza");
        terno2.setDisponivel(true);
        ternoRepository.save(terno2);

        Terno terno3 = new Terno();
        terno3.setTamanho("GG");
        terno3.setCor("Azul");
        terno3.setDisponivel(false);
        ternoRepository.save(terno3);

        mockMvc.perform(get("/api/ternos/disponiveis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].id").value(terno1.getId()))
                .andExpect(jsonPath("$[0].tamanho").value(terno1.getTamanho()))
                .andExpect(jsonPath("$[0].cor").value(terno1.getCor()))
                .andExpect(jsonPath("$[0].disponivel").value(terno1.getDisponivel()))

                .andExpect(jsonPath("$[1].id").value(terno2.getId()))
                .andExpect(jsonPath("$[1].tamanho").value(terno2.getTamanho()))
                .andExpect(jsonPath("$[1].cor").value(terno2.getCor()))
                .andExpect(jsonPath("$[1].disponivel").value(terno2.getDisponivel()));

    }

    @Test
    void testCadastraNovoTerno() throws Exception {
        String novoTernoJson = """
                    {
                        "tamanho": "G",
                        "cor": "Branco",
                        "disponivel": true
                    }
                """;

        mockMvc.perform(post("/api/ternos")
                .contentType("application/json")
                .content(novoTernoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tamanho").value("G"))
                .andExpect(jsonPath("$.cor").value("Branco"))
                .andExpect(jsonPath("$.disponivel").value(true));

        List<Terno> ternos = ternoRepository.findAll();
        assertThat(ternos).hasSize(1);
        assertThat(ternos.get(0).getCor()).isEqualTo("Branco");
    }

    @Test
    void testAtualizaDadosDoTerno() throws Exception {
        Terno terno1 = new Terno();
        terno1.setTamanho("G");
        terno1.setCor("Preto");
        terno1.setDisponivel(true);
        ternoRepository.save(terno1);

        Terno ternoExistente = ternoRepository.findAll().get(0);

        String ternoAtualizadoJson = """
                    {
                        "tamanho":"GG",
                        "cor":"Cinza Escuro",
                        "disponivel":true
                    }
                """;

        mockMvc.perform(put("/api/ternos/{id}", ternoExistente.getId())
                .contentType("application/json")
                .content(ternoAtualizadoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tamanho").value("GG"))
                .andExpect(jsonPath("$.cor").value("Cinza Escuro"))
                .andExpect(jsonPath("$.disponivel").value(true));

        Terno ternoAtualizado = ternoRepository.findById(ternoExistente.getId()).get();
        assertThat(ternoAtualizado.getTamanho()).isEqualTo("GG");
        assertThat(ternoAtualizado.getCor()).isEqualTo("Cinza Escuro");
        assertThat(ternoAtualizado.getDisponivel()).isEqualTo(true);

    }

    @Test
    void testDeletaTerno() throws Exception {
        Terno terno1 = new Terno();
        terno1.setTamanho("G");
        terno1.setCor("Preto");
        terno1.setDisponivel(true);
        ternoRepository.save(terno1);

        Terno ternoExistente = ternoRepository.findAll().get(0);

        mockMvc.perform(delete("/api/ternos/{id}", ternoExistente.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        List<Terno> ternosRestantes = ternoRepository.findAll();
        assertThat(ternosRestantes).isEmpty();
    }
}