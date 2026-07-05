package com.javaconcepts.doc.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sub_concepts")
public class SubConcept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(length = 8000)
    private String description;

    @Column(length = 8000)
    private String codeExample;

    private Integer orderIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concept_id", nullable = false)
    private Concept concept;

    @OneToMany(mappedBy = "subConcept", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<SubConceptQuestion> questions = new ArrayList<>();

    public SubConcept() {
    }

    public SubConcept(String title, String slug, Integer orderIndex, String description, String codeExample) {
        this.title = title;
        this.slug = slug;
        this.orderIndex = orderIndex;
        this.description = description;
        this.codeExample = codeExample;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCodeExample() {
        return codeExample;
    }

    public void setCodeExample(String codeExample) {
        this.codeExample = codeExample;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Concept getConcept() {
        return concept;
    }

    public void setConcept(Concept concept) {
        this.concept = concept;
    }

    public List<SubConceptQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<SubConceptQuestion> questions) {
        this.questions = questions;
    }

    public void addQuestion(SubConceptQuestion question) {
        questions.add(question);
        question.setSubConcept(this);
    }
}
