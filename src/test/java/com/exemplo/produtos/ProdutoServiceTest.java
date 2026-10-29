package com.exemplo.produtos;

import com.exemplo.produtos.model.Categoria;
import com.exemplo.produtos.model.Produto;
import com.exemplo.produtos.service.CategoriaService;
import com.exemplo.produtos.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProdutoServiceTest {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CategoriaService categoriaService;

    @Test
    void deveSalvarBuscarPaginarEDeletar() {
        Categoria cat = categoriaService.save(new Categoria("Teste", "Categoria de teste"));
        Produto a = new Produto("Zeta Teste", "d", new BigDecimal("10.00"), cat);
        Produto b = new Produto("Alfa Teste", "d", new BigDecimal("30.00"), cat);
        Produto c = new Produto("Outro", "d", new BigDecimal("20.00"), cat);
        produtoService.saveAll(List.of(a, b, c));

        // findById
        assertTrue(produtoService.findById(a.getId()).isPresent());

        // JPQL por nome
        assertEquals(2, produtoService.buscarPorNome("teste").size());

        // paginação + ordenação + filtro (nome asc)
        Page<Produto> pagina = produtoService.listarPaginado("teste", 0, 1, "nome", "asc");
        assertEquals(2, pagina.getTotalElements());
        assertEquals(2, pagina.getTotalPages());
        assertEquals("Alfa Teste", pagina.getContent().get(0).getNome());

        // delete não apaga a categoria
        produtoService.delete(a);
        assertTrue(produtoService.findById(a.getId()).isEmpty());
        assertTrue(categoriaService.findById(cat.getId()).isPresent());
    }
}
