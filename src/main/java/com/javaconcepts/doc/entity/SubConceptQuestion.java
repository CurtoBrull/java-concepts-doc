package com.javaconcepts.doc.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sub_concept_questions")
public class SubConceptQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String question;

    @Column(length = 4000)
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_concept_id", nullable = false)
    private SubConcept subConcept;

    public SubConceptQuestion() {
    }

    public SubConceptQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public SubConcept getSubConcept() {
        return subConcept;
    }

    public void setSubConcept(SubConcept subConcept) {
        this.subConcept = subConcept;
    }
}
