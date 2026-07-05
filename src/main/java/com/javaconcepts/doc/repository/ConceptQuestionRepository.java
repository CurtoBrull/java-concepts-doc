package com.javaconcepts.doc.repository;

import com.javaconcepts.doc.entity.ConceptQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConceptQuestionRepository extends JpaRepository<ConceptQuestion, Long> {
}
