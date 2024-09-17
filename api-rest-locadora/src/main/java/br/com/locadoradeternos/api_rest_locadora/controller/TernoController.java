package br.com.locadoradeternos.api_rest_locadora.controller;
// Criando os controladores para gerenciar as requisições http

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.locadoradeternos.api_rest_locadora.model.Terno;
import br.com.locadoradeternos.api_rest_locadora.service.TernoDeleteService;
import br.com.locadoradeternos.api_rest_locadora.service.TernoReadService;
import br.com.locadoradeternos.api_rest_locadora.service.TernoUpdateService;
import br.com.locadoradeternos.api_rest_locadora.service.TernoWriteService;

@RestController /* Define a classe como um controlador REST para lidar com as requisições  */
@RequestMapping("/api")
@RequiredArgsConstructor /* Cria construtor com variaveis de inicialização obrigatória */
public class TernoController {


    private final TernoReadService ternoReadService;
    private final TernoWriteService ternoWriteService;
    private final TernoUpdateService ternoUpdateService;
    private final TernoDeleteService ternoDeleteService;


    // Listando todos os ternos no banco de dados
    @GetMapping("/ternos")
    public ResponseEntity<List<Terno>> listarTodosOsTernos(){
        List<Terno> todosOsTernos = ternoReadService.listaTernos(); // chama o ternoReadService e usa o metodo listaTernos para retornar todos os ternos
        return ResponseEntity.ok().body(todosOsTernos);
    }

    // Buscando um único terno pelo id
    @GetMapping("/ternos/{id}")
    public ResponseEntity<Terno> encontrarTernoPeloId(@PathVariable Long id){
        Terno terno = ternoReadService.buscarTernoPeloId(id);
        return ResponseEntity.ok().body(terno);
    }

    // Buscando apenas ternos disponiveis
    @GetMapping("/ternos/disponiveis")
    public ResponseEntity<List<Terno>> listarTernosDisponiveis(){
        List<Terno> ternosDisponiveis = ternoReadService.TernosDisponiveis();
        return ResponseEntity.ok().body(ternosDisponiveis);
    }

    // Cadastrar um terno no banco de dados
    @PostMapping("/ternos")
    ResponseEntity<Terno> cadastrarNovoTerno(@RequestBody Terno terno){
        Terno novoTerno = ternoWriteService.cadastrarNovoTerno(terno);
        return ResponseEntity.ok().body(novoTerno);
    }

    // Alterar dados de um terno
    @PutMapping("/ternos/{id}")
    public ResponseEntity<Terno> atualizaDadosDoTerno(@PathVariable Long id, @RequestBody Terno ternoAlterado){
        Terno terno = ternoUpdateService.atualizaDadosDoTerno(id, ternoAlterado);
        return ResponseEntity.ok().body(terno);
    }

    // Deletando um terno da base de dados
    @DeleteMapping("/ternos/{id}")
    public ResponseEntity<Void> deletarPeloId(@PathVariable Long id){
        ternoDeleteService.deletarTernoPeloId(id);
        return ResponseEntity.noContent().build(); // Retorna status 204 (nenhum conteúdo ou erro), indicando que foi deletado com sucesso
    }
}
