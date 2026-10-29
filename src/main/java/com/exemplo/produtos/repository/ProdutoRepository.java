package com.exemplo.produtos.repository;

import com.exemplo.produtos.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    /** Consulta JPQL personalizada: busca produtos cujo nome contém o texto (sem diferenciar maiúsculas). */
    @Query("SELECT p FROM Produto p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Produto> buscarPorNome(@Param("nome") String nome);

    /**
     * Paginação + ordenação + filtro por nome.
     * O JOIN FETCH traz a categoria na mesma consulta (evita o problema N+1 mesmo com LAZY).
     * O countQuery é separado porque o JOIN FETCH não pode ser usado na contagem.
     */
    @Query(value = "SELECT p FROM Produto p JOIN FETCH p.categoria "
            + "WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))",
            countQuery = "SELECT COUNT(p) FROM Produto p "
                    + "WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<Produto> buscarPaginadoPorNome(@Param("nome") String nome, Pageable pageable);

    /** Busca por id já trazendo a categoria (útil quando o relacionamento é LAZY). */
    @Query("SELECT p FROM Produto p JOIN FETCH p.categoria WHERE p.id = :id")
    Optional<Produto> findByIdComCategoria(@Param("id") Long id);
}
