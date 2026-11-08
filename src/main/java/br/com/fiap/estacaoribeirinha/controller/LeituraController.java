package br.com.fiap.estacaoribeirinha.controller;

import br.com.fiap.estacaoribeirinha.bo.LeituraBo;
import br.com.fiap.estacaoribeirinha.entities.Leitura;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("/leituras")
public class LeituraController {

    LeituraBo leituraBo = new LeituraBo();

    // Select
    @GetMapping
    public ArrayList<Leitura> selecionarRs() throws SQLException, ClassNotFoundException {
        return leituraBo.selecionarBo();
    }

    // Buscar por ID
    @GetMapping("/{idLeitura}")
    public Leitura buscarPorIdRs(@PathVariable int idLeitura) throws SQLException, ClassNotFoundException {
        return leituraBo.buscarPorIdBo(idLeitura);
    }

    // Buscar leituras da estacao
    @GetMapping("/estacao/{idEstacao}")
    public ArrayList<Leitura> buscarPorIdEstacaoRs(@PathVariable int idEstacao) throws SQLException, ClassNotFoundException {
        return leituraBo.buscarPorIdEstacaoBo(idEstacao);
    }

    // Buscar ultima leitura da estacao
    @GetMapping("/estacao/{idEstacao}/ultima")
    public Leitura buscarUltimaPorIdEstacaoRs(@PathVariable int idEstacao) throws SQLException, ClassNotFoundException {
        return leituraBo.buscarUltimaPorIdEstacaoBo(idEstacao);
    }

    // Insert
    @PostMapping
    public ResponseEntity<Void> inserirRs(@RequestBody Leitura leitura) throws SQLException, ClassNotFoundException {
        int idLeitura = leituraBo.inserirBo(leitura);

        URI location = URI.create("/leituras/" + idLeitura);

        return ResponseEntity.created(location).build();
    }

    // Update
    @PutMapping("/{idLeitura}")
    public ResponseEntity<Void> atualizarRs(@PathVariable int idLeitura, @RequestBody Leitura leitura) throws SQLException, ClassNotFoundException {
        leitura.setIdLeitura(idLeitura);
        leituraBo.atualizarBo(leitura);

        return ResponseEntity.ok().build();
    }

    // Delete
    @DeleteMapping("/{idLeitura}")
    public ResponseEntity<Void> deletarRs(@PathVariable int idLeitura) throws SQLException, ClassNotFoundException {
        leituraBo.deletarBo(idLeitura);

        return ResponseEntity.noContent().build();
    }
}
