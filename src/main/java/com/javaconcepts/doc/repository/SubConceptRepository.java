package com.javaconcepts.doc.repository;

import com.javaconcepts.doc.entity.SubConcept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubConceptRepository extends JpaRepository<SubConcept, Long> {

    Optional<SubConcept> findBySlug(String slug);

    List<SubConcept> findByConceptIdOrderByOrderIndexAsc(Long conceptId);

    @Query("SELECT sc FROM SubConcept sc WHERE LOWER(sc.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(sc.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<SubConcept> search(String keyword);
}
