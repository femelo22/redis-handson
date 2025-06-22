package br.com.lfmelo.services;

import br.com.lfmelo.entities.Livro;

import java.util.Optional;

public interface LivroService {
    Livro salvar(Livro livro);
    Livro buscarPorId(Long id);
    Livro atualizar(Long id, Livro livro);
    void deletar(Long id);
}
