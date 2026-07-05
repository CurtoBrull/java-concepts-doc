package com.javaconcepts.doc.repository;

import com.javaconcepts.doc.entity.Block;
import com.javaconcepts.doc.entity.Concept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConceptRepository extends JpaRepository<Concept, Long> {

    Optional<Concept> findBySlug(String slug);

    List<Concept> findAllByParentIsNullOrderByBlockAscOrderIndexAsc();

    List<Concept> findByBlockAndParentIsNullOrderByOrderIndexAsc(Block block);

    @Query("SELECT DISTINCT c.block FROM Concept c WHERE c.parent IS NULL ORDER BY c.block")
    List<Block> findDistinctBlocksWithRootConcepts();

    @Query("SELECT c FROM Concept c WHERE c.parent IS NULL AND LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY c.orderIndex")
    List<Concept> searchRootConcepts(String keyword);
}
