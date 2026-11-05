package co.edu.uniajc.matriculas.web;

import co.edu.uniajc.matriculas.service.CredencialesInvalidasException;
import co.edu.uniajc.matriculas.service.ReglaNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ReglaNegocioException.class)
    ResponseEntity<Map<String, String>> regla(ReglaNegocioException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensaje", e.getMessage()));
    }
    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<Map<String, String>> noEncontrado(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensaje", e.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String, String>> invalido(MethodArgumentNotValidException e) {
        String detalle = e.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + " " + f.getDefaultMessage()).findFirst().orElse("Datos invalidos");
        return ResponseEntity.badRequest().body(Map.of("mensaje", detalle));
    }
    @ExceptionHandler(CredencialesInvalidasException.class)
    ResponseEntity<Map<String, String>> credenciales(CredencialesInvalidasException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("mensaje", e.getMessage()));
    }
}
