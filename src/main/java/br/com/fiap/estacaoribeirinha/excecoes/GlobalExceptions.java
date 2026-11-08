package br.com.fiap.estacaoribeirinha.excecoes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptions {

    // Erros de validacao e regras de negocio
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> tratarArgumentoInvalido(IllegalArgumentException erro) {
        Map<String, String> resposta = Map.of("erro", erro.getMessage());

        return ResponseEntity.badRequest().body(resposta);
    }

    // Registro nao encontrado
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> tratarRegistroNaoEncontrado(NoSuchElementException erro) {
        Map<String, String> resposta = Map.of("erro", erro.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }

    // Corpo JSON invalido
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> tratarCorpoInvalido(HttpMessageNotReadableException erro) {
        Map<String, String> resposta = Map.of("erro", "O corpo da requisicao possui dados invalidos.");

        return ResponseEntity.badRequest().body(resposta);
    }

    // Erros de banco, driver ou credenciais de ambiente
    @ExceptionHandler({SQLException.class, ClassNotFoundException.class, IllegalStateException.class})
    public ResponseEntity<Map<String, String>> tratarErroDeBanco(Exception erro) {
        Map<String, String> resposta = Map.of("erro", "Nao foi possivel acessar o banco de dados.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }

    // Outros erros nao previstos
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> tratarErroInesperado(Exception erro) {
        Map<String, String> resposta = Map.of("erro", "Ocorreu um erro interno inesperado.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }

}
