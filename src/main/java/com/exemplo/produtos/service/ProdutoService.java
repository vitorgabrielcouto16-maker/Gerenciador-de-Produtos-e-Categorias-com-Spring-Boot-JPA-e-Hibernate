package com.exemplo.produtos.service;

import com.exemplo.produtos.model.Produto;
import com.exemplo.produtos.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Produto save(Produto produto) {
        return repository.save(produto);
    }

    @Transactional
    public List<Produto> saveAll(List<Produto> produtos) {
        return repository.saveAll(produtos);
    }

    @Transactional
    public void delete(Produto produto) {
        repository.delete(produto);
    }

    @Transactional(readOnly = true)
    public Optional<Produto> findById(Long id) {
        return repository.findById(id);
    }

    /** Traz o produto já com a categoria carregada (evita LazyInitializationException fora da transação). */
    @Transactional(readOnly = true)
    public Optional<Produto> findByIdComCategoria(Long id) {
        return repository.findByIdComCategoria(id);
    }

    @Transactional(readOnly = true)
    public List<Produto> buscarPorNome(String nome) {
        return repository.buscarPorNome(nome);
    }

    /**
     * Listagem paginada, ordenada e filtrada por nome.
     *
     * @param nome      trecho do nome (vazio traz todos)
     * @param page      número da página, começando em 0
     * @param size      quantidade de itens por página
     * @param sortBy    campo para ordenar (ex.: "nome", "preco")
     * @param direction "asc" ou "desc"
     */
    @Transactional(readOnly = true)
    public Page<Produto> listarPaginado(String nome, int page, int size, String sortBy, String direction) {
        Sort sort = "desc".equalsIgnoreCase(direction)
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return repository.buscarPaginadoPorNome(nome == null ? "" : nome, pageable);
    }
}
