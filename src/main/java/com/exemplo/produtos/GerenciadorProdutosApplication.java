package com.exemplo.produtos;

import com.exemplo.produtos.model.Categoria;
import com.exemplo.produtos.model.Produto;
import com.exemplo.produtos.service.CategoriaService;
import com.exemplo.produtos.service.ProdutoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class GerenciadorProdutosApplication {

    public static void main(String[] args) {
        SpringApplication.run(GerenciadorProdutosApplication.class, args);
    }

    /** Demonstra todas as funcionalidades ao subir a aplicação. */
    @Bean
    CommandLineRunner demo(CategoriaService categoriaService, ProdutoService produtoService) {
        return args -> {
            Categoria eletronicos = categoriaService.save(new Categoria("Eletrônicos", "Dispositivos eletrônicos"));
            Categoria livros = categoriaService.save(new Categoria("Livros", "Livros técnicos e literatura"));

            produtoService.saveAll(List.of(
                    new Produto("Notebook Gamer", "Notebook 16GB RAM", new BigDecimal("5999.90"), eletronicos),
                    new Produto("Notebook Office", "Notebook para escritório", new BigDecimal("2999.90"), eletronicos),
                    new Produto("Mouse Gamer", "Mouse 12000 DPI", new BigDecimal("199.90"), eletronicos),
                    new Produto("Teclado Mecânico", "Switch blue", new BigDecimal("349.90"), eletronicos),
                    new Produto("Clean Code", "Robert C. Martin", new BigDecimal("89.90"), livros),
                    new Produto("Effective Java", "Joshua Bloch", new BigDecimal("129.90"), livros)
            ));

            System.out.println("\n===== Página 0 (tamanho 3), ordenada por preço DESC, filtro 'o' =====");
            Page<Produto> pagina = produtoService.listarPaginado("o", 0, 3, "preco", "desc");
            pagina.forEach(p -> System.out.println(p.getNome() + " | R$ " + p.getPreco()
                    + " | " + p.getCategoria().getNome()));
            System.out.println("Total de elementos: " + pagina.getTotalElements()
                    + " | Total de páginas: " + pagina.getTotalPages());

            System.out.println("\n===== Busca JPQL por nome 'notebook' =====");
            produtoService.buscarPorNome("notebook")
                    .forEach(p -> System.out.println(p.getNome()));

            System.out.println("\n===== findById (LAZY, dentro de transação) =====");
            System.out.println(produtoService.findByIdComCategoria(1L));
        };
    }
}
