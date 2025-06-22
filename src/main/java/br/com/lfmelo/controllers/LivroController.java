package br.com.lfmelo.controllers;

import br.com.lfmelo.entities.Livro;
import br.com.lfmelo.services.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    @Qualifier("livroServiceCacheManual")
    private LivroService service;

    @PostMapping
    public ResponseEntity<Livro> criar(@RequestBody Livro livro) {
        return ResponseEntity.ok(service.salvar(livro));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizar(@PathVariable Long id, @RequestBody Livro livro) {
        livro.setId(id);
        return ResponseEntity.ok(service.atualizar(id, livro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
