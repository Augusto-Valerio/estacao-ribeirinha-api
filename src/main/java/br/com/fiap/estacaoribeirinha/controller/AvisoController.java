package br.com.fiap.estacaoribeirinha.controller;

import br.com.fiap.estacaoribeirinha.bo.AvisoBo;
import br.com.fiap.estacaoribeirinha.entities.Aviso;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("/avisos")
public class AvisoController {

    AvisoBo avisoBo = new AvisoBo();

    // Select
    @GetMapping
    public ArrayList<Aviso> selecionarRs() throws SQLException, ClassNotFoundException {
        return avisoBo.selecionarBo();
    }

    // Buscar por ID
    @GetMapping("/{idAviso}")
    public Aviso buscarPorIdRs(@PathVariable int idAviso) throws SQLException, ClassNotFoundException {
        return avisoBo.buscarPorIdBo(idAviso);
    }

    // Buscar avisos da estacao
    @GetMapping("/estacao/{idEstacao}")
    public ArrayList<Aviso> buscarPorIdEstacaoRs(@PathVariable int idEstacao) throws SQLException, ClassNotFoundException {
        return avisoBo.buscarPorIdEstacaoBo(idEstacao);
    }

    // Insert
    @PostMapping
    public ResponseEntity<Void> inserirRs(@RequestBody Aviso aviso) throws SQLException, ClassNotFoundException {
        int idAviso = avisoBo.inserirBo(aviso);

        URI location = URI.create("/avisos/" + idAviso);

        return ResponseEntity.created(location).build();
    }

    // Update
    @PutMapping("/{idAviso}")
    public ResponseEntity<Void> atualizarRs(@PathVariable int idAviso, @RequestBody Aviso aviso) throws SQLException, ClassNotFoundException {
        aviso.setIdAviso(idAviso);
        avisoBo.atualizarBo(aviso);

        return ResponseEntity.ok().build();
    }

    // Delete
    @DeleteMapping("/{idAviso}")
    public ResponseEntity<Void> deletarRs(@PathVariable int idAviso) throws SQLException, ClassNotFoundException {
        avisoBo.deletarBo(idAviso);

        return ResponseEntity.noContent().build();
    }
}
