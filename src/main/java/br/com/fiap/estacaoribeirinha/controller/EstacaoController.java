package br.com.fiap.estacaoribeirinha.controller;

import br.com.fiap.estacaoribeirinha.bo.EstacaoBo;
import br.com.fiap.estacaoribeirinha.entities.Estacao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("/estacoes")
public class EstacaoController {

    EstacaoBo estacaoBo = new EstacaoBo();

    // Select
    @GetMapping
    public ArrayList<Estacao> selecionarRs() throws SQLException, ClassNotFoundException {

        return estacaoBo.selecionarBo();
    }

    // Buscatr por ID
    // Buscar por ID
    @GetMapping("/{idEstacao}")
    public Estacao buscarPorIdRs(@PathVariable int idEstacao) throws SQLException, ClassNotFoundException {
        return estacaoBo.buscarPorIdBo(idEstacao);
    }

    // Insert
    @PostMapping
    public ResponseEntity<Void> inserirRs(@RequestBody Estacao estacao) throws SQLException, ClassNotFoundException {
        int idEstacao = estacaoBo.inserirBo(estacao);

        URI location = URI.create("/estacoes/" + idEstacao);

        return ResponseEntity.created(location).build();
    }

    // Update
    @PutMapping("/{idEstacao}")
    public ResponseEntity<Void> atualizarRs(@PathVariable int idEstacao, @RequestBody Estacao estacao) throws SQLException, ClassNotFoundException {
        estacao.setIdEstacao(idEstacao);
        estacaoBo.atualizarBo(estacao);

        return ResponseEntity.ok().build();
    }

    // Delete
    @DeleteMapping("/{idEstacao}")
    public ResponseEntity<Void> deletarRs(@PathVariable int idEstacao) throws SQLException, ClassNotFoundException {
        estacaoBo.deletarBo(idEstacao);

        return ResponseEntity.noContent().build();
    }
}
