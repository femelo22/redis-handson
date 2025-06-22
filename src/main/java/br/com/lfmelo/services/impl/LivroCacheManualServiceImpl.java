package br.com.lfmelo.services.impl;

import br.com.lfmelo.entities.Livro;
import br.com.lfmelo.repositories.LivroRepository;
import br.com.lfmelo.services.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service("livroServiceCacheManual")
public class LivroCacheManualServiceImpl implements LivroService {

    @Autowired
    private LivroRepository repository;

    @Autowired
    private RedisCacheServiceImpl cacheService;

    private static final String CACHE_PREFIX = "livro::";

    @Override
    public Livro salvar(Livro livro) {
        Livro saved = repository.save(livro);
        String cacheKey = CACHE_PREFIX + saved.getId();
        cacheService.setWithTTL(cacheKey, saved, Duration.ofMinutes(1));
        return saved;
    }

    @Override
    public Livro buscarPorId(Long id) {
        String cacheKey = CACHE_PREFIX + id;

        Optional<Livro> livro = cacheService.get(cacheKey, Livro.class);

        if (livro.isPresent()) {
            System.out.println("Buscando do cache Redis");
            return livro.get();
        }

        System.out.println("Buscando do banco de dados");
        Livro livroDb = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        cacheService.setWithTTL(cacheKey, livroDb, Duration.ofMinutes(1));

        return livroDb;
    }

    @Override
    public Livro atualizar(Long id, Livro livro) {
        var livroUpdate = buscarPorId(id);
        livroUpdate.setId(id);
        livroUpdate.setAutor(livro.getAutor());
        livroUpdate.setTitulo(livro.getTitulo());

        Livro updated = repository.save(livroUpdate);
        String cacheKey = CACHE_PREFIX + id;
        cacheService.setWithTTL(cacheKey, updated, Duration.ofMinutes(1));
        return updated;
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
        String cacheKey = CACHE_PREFIX + id;
        cacheService.delete(cacheKey);
    }
}
