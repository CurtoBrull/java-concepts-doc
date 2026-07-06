package com.javaconcepts.doc.service;

import com.javaconcepts.doc.dto.BlockTreeDto;
import com.javaconcepts.doc.dto.ConceptDetailDto;
import com.javaconcepts.doc.dto.SearchResultDto;
import com.javaconcepts.doc.entity.Block;
import com.javaconcepts.doc.entity.Concept;
import com.javaconcepts.doc.entity.SubConcept;
import com.javaconcepts.doc.repository.ConceptRepository;
import com.javaconcepts.doc.repository.SubConceptRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConceptServiceTest {

    @Mock
    private ConceptRepository conceptRepository;

    @Mock
    private SubConceptRepository subConceptRepository;

    @InjectMocks
    private ConceptService conceptService;

    @Test
    void getBlockTreeShouldGroupByBlockInNaturalOrder() {
        Concept javaCore = concept("Clase", "clase", Block.JAVA_CORE, 1);
        Concept tools = concept("Maven", "maven", Block.TOOLS, 1);
        Concept spring = concept("IoC", "ioc", Block.SPRING, 1);

        when(conceptRepository.findAllByParentIsNullOrderByOrderIndexAsc())
                .thenReturn(List.of(javaCore, spring, tools));

        List<BlockTreeDto> result = conceptService.getBlockTree();

        assertThat(result).hasSize(3);
        assertThat(result.get(0).block()).isEqualTo(Block.JAVA_CORE);
        assertThat(result.get(1).block()).isEqualTo(Block.SPRING);
        assertThat(result.get(2).block()).isEqualTo(Block.TOOLS);
    }

    @Test
    void getConceptDetailBySlugShouldReturnDetailWhenFound() {
        Concept concept = concept("JDBC", "jdbc", Block.JPA_HIBERNATE, 4);
        concept.setDescription("JDBC description");
        SubConcept sub = new SubConcept("CRUD", "crud-jdbc", 1, "desc", "code");
        concept.addSubConcept(sub);

        when(conceptRepository.findBySlug("jdbc")).thenReturn(Optional.of(concept));

        Optional<ConceptDetailDto> result = conceptService.getConceptDetailBySlug("jdbc");

        assertThat(result).isPresent();
        assertThat(result.get().title()).isEqualTo("JDBC");
        assertThat(result.get().description()).isEqualTo("JDBC description");
        assertThat(result.get().subConcepts()).hasSize(1);
    }

    @Test
    void getConceptDetailBySlugShouldReturnEmptyWhenNotFound() {
        when(conceptRepository.findBySlug("unknown")).thenReturn(Optional.empty());

        Optional<ConceptDetailDto> result = conceptService.getConceptDetailBySlug("unknown");

        assertThat(result).isEmpty();
    }

    @Test
    void searchShouldReturnResultsFromConceptsAndSubConcepts() {
        Concept concept = concept("Maven", "maven", Block.TOOLS, 1);
        SubConcept sub = new SubConcept("pom.xml", "pom-basico", 1, "desc", "code");
        sub.setConcept(concept);

        when(conceptRepository.searchRootConcepts("maven")).thenReturn(List.of(concept));
        when(subConceptRepository.search("maven")).thenReturn(List.of(sub));

        List<SearchResultDto> results = conceptService.search("maven");

        assertThat(results).hasSize(2);
        assertThat(results).extracting(SearchResultDto::title).contains("Maven", "pom.xml");
    }

    @Test
    void searchShouldReturnEmptyListForBlankKeyword() {
        List<SearchResultDto> results = conceptService.search("  ");

        assertThat(results).isEmpty();
    }

    private Concept concept(String title, String slug, Block block, int order) {
        return new Concept(title, slug, block, order);
    }
}
