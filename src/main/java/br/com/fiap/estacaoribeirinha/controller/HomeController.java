package br.com.fiap.estacaoribeirinha.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class HomeController {
    @GetMapping("/")
    public Map<String, Object> inicio() {
        return Map.of(
                "aplicacao", "Estacao Ribeirinha API",
                "status", "online",
                "descricao", "API academica para monitoramento local de estacoes ribeirinhas.",
                "observacao", "Os avisos sao locais e demonstrativos.",
                "endpoints", List.of("/estacoes", "/leituras", "/avisos")
        );
    }
}
