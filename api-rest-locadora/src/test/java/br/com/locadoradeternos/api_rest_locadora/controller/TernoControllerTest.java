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


@SpringBootTest // Indica que é um teste para app Spring Boot e carrega o contexto para o teste
@AutoConfigureMockMvc   // Facilita a testagem de endpoints HTTP por testar sem iniciar um servidor real
@ActiveProfiles("test") // Isola os testes do ambiente de desenvolvimento
class TernoControllerTest {

    @Autowired // Injeta MocMvc para o teste
    private MockMvc mockMvc; // Simula as requisições http e verifica as respostas do endpoint

    @Autowired  // Injeta TernoRepository para o teste
    private TernoRepository ternoRepository; // Repositorio JPA para acessar o banco de dados

    @BeforeEach // Indica que deve rodar o setUp antes de cada testagem
    public void setUp(){
        ternoRepository.deleteAll(); // Remove todas as informações do DB antes de cada teste

    }

    @Test // Marca que o método deve ser testado
    void testListaTodosOsTernos() throws Exception{
        // Criando instancias da entidade Terno
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

        mockMvc.perform(get("/api/ternos")) // Simula uma requisição get no endpoint da api
                .andExpect(status().isOk()) // Verifica se a resposta do status é 200 (ok)
                .andExpect(jsonPath("$", hasSize(2))) // Verifica se o tamanho do JSON é 2
                .andExpect(jsonPath("$[0].id").value(terno1.getId())) // Verifica se os dados batem com os ternos criados
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

        Terno ternoSalvo = ternoRepository.findAll().get(0); // Recupera o primeiro terno encontrado, que será o usado para o teste

        mockMvc.perform(get("/api/ternos/{id}", ternoSalvo.getId())) // Simula a requisição no id do ternoSalvo
                .andExpect(status().isOk()) // Confere se o status é 200
                .andExpect(jsonPath("$.tamanho").value(ternoSalvo.getTamanho())) // Confere se os atributos são iguais
                .andExpect(jsonPath("$.cor").value(ternoSalvo.getCor()))
                .andExpect(jsonPath("$.disponivel").value(ternoSalvo.getDisponivel()));
    }

    @Test
    void testListaTernoPeloIdNaoExistente() throws Exception {
        mockMvc.perform(get("/api/ternos/{id}", 999L)) // Verificando um id inexistente
                .andExpect(status().isNotFound()); // Deve retornar 404 (Not found)
    }

    @Test
    void testListaDeTernosDisponiveis() throws Exception {
        // Criando 3 ternos para encontrar os que estão disponiveis
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

        mockMvc.perform(get("/api/ternos/disponiveis")) // Simulando a requisição get
                .andExpect(status().isOk()) // Verificando se a resposta é 200
                .andExpect(jsonPath("$", hasSize(2))) // Verifica se o tamanho do JSON é 2
                // Ternos disponivel = true para teste
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
        // Json para cadastrar terno
        String novoTernoJson = """
                    {
                        "tamanho": "G",
                        "cor": "Branco",
                        "disponivel": true
                    }
                """;

        mockMvc.perform(post("/api/ternos") // utilizando o metodo post para cadastro
                .contentType("application/json") // Define que o corpo da requisição está em JSON
                .content(novoTernoJson)) // O conteúdo é o JSON de novoTernoJson
                .andExpect(status().isOk()) // Retorna o status 200
                .andExpect(jsonPath("$.tamanho").value("G")) // Conferindo os parametros
                .andExpect(jsonPath("$.cor").value("Branco"))
                .andExpect(jsonPath("$.disponivel").value(true));

        List<Terno> ternos = ternoRepository.findAll(); //Recupera todos os ternos
        assertThat(ternos).hasSize(1); // Certifica que existe apenas 1
        assertThat(ternos.get(0).getCor()).isEqualTo("Branco"); // Verifica se a cor é a cor do novoTernoJson
    }

    @Test
    void testAtualizaDadosDoTerno() throws Exception {
        // Criando um terno
        Terno terno1 = new Terno();
        terno1.setTamanho("G");
        terno1.setCor("Preto");
        terno1.setDisponivel(true);
        ternoRepository.save(terno1);

        Terno ternoExistente = ternoRepository.findAll().get(0); // Definindo como o terno existente

        // Modificando os dados do terno
        String ternoAtualizadoJson = """
                    {
                        "tamanho":"GG",
                        "cor":"Cinza Escuro",
                        "disponivel":true
                    }
                """;

        mockMvc.perform(put("/api/ternos/{id}", ternoExistente.getId()) //Utilizando o PUT
                .contentType("application/json") // Informando que é um conteúdo no formado JSON
                .content(ternoAtualizadoJson)) // Passando o ternoAtualizadoJson como conteúdo
                .andExpect(status().isOk()) // Status 200
                .andExpect(jsonPath("$.tamanho").value("GG"))
                .andExpect(jsonPath("$.cor").value("Cinza Escuro"))
                .andExpect(jsonPath("$.disponivel").value(true));

        Terno ternoAtualizado = ternoRepository.findById(ternoExistente.getId()).get(); // Recupera o terno após atualização
        assertThat(ternoAtualizado.getTamanho()).isEqualTo("GG"); // Verifica se o tamanho é o atualizado
        assertThat(ternoAtualizado.getCor()).isEqualTo("Cinza Escuro"); // Se a cor está de acordo
        assertThat(ternoAtualizado.getDisponivel()).isEqualTo(true); // Se o boolean é true

    }

    @Test
    void testDeletaTerno() throws Exception {
        Terno terno1 = new Terno();
        terno1.setTamanho("G");
        terno1.setCor("Preto");
        terno1.setDisponivel(true);
        ternoRepository.save(terno1);

        Terno ternoExistente = ternoRepository.findAll().get(0); // Pegar o terno na posição 0

        mockMvc.perform(delete("/api/ternos/{id}", ternoExistente.getId())) // Usando o DELETE
                .andExpect(status().isOk()) // Verifica se o status é 200
                .andExpect(jsonPath("$").value(true)); // Verifica se o boolean é true

        List<Terno> ternosRestantes = ternoRepository.findAll(); // Busca todos os ternos no repo
        assertThat(ternosRestantes).isEmpty(); // Verifica se esta vazio
    }
}