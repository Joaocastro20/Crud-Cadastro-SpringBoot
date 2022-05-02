package aula.com.projeto.controller;

import aula.com.projeto.exception.NameNullException;
import aula.com.projeto.exception.NameVazioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ProdutoControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> erro(){
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("message", "occoreu um erro generico");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }


    @ExceptionHandler(NameNullException.class)
    public ResponseEntity<Object> capturaErroNull(){
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("message", "verifique os campos do produto");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    @ExceptionHandler(NameVazioException.class)
    public ResponseEntity<Object> nomeVazioNull(){
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("message", "o nome nao pode ser vazio");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
