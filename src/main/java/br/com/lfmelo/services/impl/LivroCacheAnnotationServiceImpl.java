package br.com.lfmelo.services.impl;

import br.com.lfmelo.entities.Livro;
import br.com.lfmelo.repositories.LivroRepository;
import br.com.lfmelo.services.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service("livroServiceCacheAnnotation")
public class LivroCacheAnnotationServiceImpl implements LivroService {

    @Autowired
    private LivroRepository repository;

    @Cacheable(value = "livros", key = "#id")
    public Livro buscarPorId(Long id) {
        System.out.println("Buscando do banco de dados...");
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado!"));
    }

    public Livro salvar(Livro livro) {
        return repository.save(livro);
    }

    @CachePut(value = "livros", key = "#livro.id")
    public Livro atualizar(Long id, Livro livro) {
        var livroUpdate = buscarPorId(id);
        livroUpdate.setId(id);
        livroUpdate.setAutor(livro.getAutor());
        livroUpdate.setTitulo(livro.getTitulo());
        return repository.save(livro);
    }

    @CacheEvict(value = "livros", key = "#id")
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
