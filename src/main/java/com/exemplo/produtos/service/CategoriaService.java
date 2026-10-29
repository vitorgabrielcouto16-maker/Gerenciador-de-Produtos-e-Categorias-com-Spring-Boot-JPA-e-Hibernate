package com.exemplo.produtos.service;

import com.exemplo.produtos.model.Categoria;
import com.exemplo.produtos.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Categoria save(Categoria categoria) {
        return repository.save(categoria);
    }

    @Transactional
    public List<Categoria> saveAll(List<Categoria> categorias) {
        return repository.saveAll(categorias);
    }

    @Transactional
    public void delete(Categoria categoria) {
        repository.delete(categoria);
    }

    @Transactional(readOnly = true)
    public Optional<Categoria> findById(Long id) {
        return repository.findById(id);
    }
}
