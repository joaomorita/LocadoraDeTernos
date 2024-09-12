package br.com.locadoradeternos.api_rest_locadora.controller;
// Criando os controladores para gerenciar as requisições http

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.locadoradeternos.api_rest_locadora.model.Ternos;
import br.com.locadoradeternos.api_rest_locadora.service.TernoDeleteService;
import br.com.locadoradeternos.api_rest_locadora.service.TernoReadService;
import br.com.locadoradeternos.api_rest_locadora.service.TernoUpdateService;
import br.com.locadoradeternos.api_rest_locadora.service.TernoWriteService;

@RestController /* Define a classe como um controlador REST para lidar com as requisições  */
@RequestMapping("/terno")
public class TernoController {

    @Autowired
    private TernoReadService ternoReadService;

    @Autowired
    private TernoWriteService ternoWriteService;

    @Autowired
    private TernoUpdateService ternoUpdateService;

    @Autowired
    private TernoDeleteService ternoDeleteService;

    // Listando todos os ternos no banco de dados
    @GetMapping("/api/ternos")
    public ResponseEntity<List<Ternos>> listaTodosOsTernos(){
        List<Ternos> todosOsTernos = ternoReadService.listaTernos();
        return ResponseEntity.ok().body(todosOsTernos);
    }

    // Buscando um único terno pelo id
    @GetMapping("/api/ternos/{id}")
    public ResponseEntity<Ternos> encontrarTernoPeloId(@PathVariable Long id){
        Ternos terno = ternoReadService.buscarTernoPeloId(id);
        return ResponseEntity.ok().body(terno);
    }

    // Cadastrar um terno no banco de dados
    @PostMapping("/api/ternos")
    ResponseEntity<Ternos> cadastrarNovoTerno(@RequestBody Ternos terno){
        Ternos novoTerno = ternoWriteService.cadastrarNovoTerno(terno);
        return ResponseEntity.ok().body(novoTerno);
    }

    // Alterar dados de um terno
    @PutMapping("/api/ternos/{id}")
    public ResponseEntity<Ternos> updateTerno(@PathVariable Long id, @RequestBody Ternos ternoAlterado){
        Ternos terno = ternoUpdateService.atualizaDadosDoTerno(id, ternoAlterado);
        return ResponseEntity.ok().body(terno);
    }

    // Deletando um terno da base de dados
    @DeleteMapping("/api/ternos/{id}")
    public ResponseEntity<Boolean> deletarPeloId(@PathVariable Long id){
        Boolean sinaliza = ternoDeleteService.deletarTernoPeloId(id);
        return ResponseEntity.ok().body(sinaliza);
    }
}