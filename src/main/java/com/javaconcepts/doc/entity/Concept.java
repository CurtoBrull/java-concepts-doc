package com.javaconcepts.doc.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "concepts")
public class Concept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Block block;

    private Integer orderIndex;

    @Column(length = 8000)
    private String description;

    @Column(length = 8000)
    private String codeExample;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Concept parent;

    @OneToMany(mappedBy = "concept", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    @org.hibernate.annotations.BatchSize(size = 50)
    private List<SubConcept> subConcepts = new ArrayList<>();

    @OneToMany(mappedBy = "concept", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    @org.hibernate.annotations.BatchSize(size = 50)
    private List<ConceptQuestion> questions = new ArrayList<>();

    public Concept() {
    }

    public Concept(String title, String slug, Block block, Integer orderIndex) {
        this.title = title;
        this.slug = slug;
        this.block = block;
        this.orderIndex = orderIndex;
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

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
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

    public Concept getParent() {
        return parent;
    }

    public void setParent(Concept parent) {
        this.parent = parent;
    }

    public List<SubConcept> getSubConcepts() {
        return subConcepts;
    }

    public void setSubConcepts(List<SubConcept> subConcepts) {
        this.subConcepts = subConcepts;
    }

    public void addSubConcept(SubConcept subConcept) {
        subConcepts.add(subConcept);
        subConcept.setConcept(this);
    }

    public List<ConceptQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ConceptQuestion> questions) {
        this.questions = questions;
    }

    public void addQuestion(ConceptQuestion question) {
        questions.add(question);
        question.setConcept(this);
    }
}
