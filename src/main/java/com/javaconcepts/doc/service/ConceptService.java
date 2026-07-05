package com.javaconcepts.doc.service;

import com.javaconcepts.doc.dto.*;
import com.javaconcepts.doc.entity.Block;
import com.javaconcepts.doc.entity.Concept;
import com.javaconcepts.doc.entity.SubConcept;
import com.javaconcepts.doc.entity.SubConceptQuestion;
import com.javaconcepts.doc.repository.ConceptRepository;
import com.javaconcepts.doc.repository.SubConceptRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ConceptService {

    private final ConceptRepository conceptRepository;
    private final SubConceptRepository subConceptRepository;

    public ConceptService(ConceptRepository conceptRepository, SubConceptRepository subConceptRepository) {
        this.conceptRepository = conceptRepository;
        this.subConceptRepository = subConceptRepository;
    }

    public List<BlockTreeDto> getBlockTree() {
        List<Concept> rootConcepts = conceptRepository.findAllByParentIsNullOrderByBlockAscOrderIndexAsc();
        Map<Block, List<Concept>> byBlock = rootConcepts.stream()
                .collect(Collectors.groupingBy(Concept::getBlock));

        List<BlockTreeDto> result = new ArrayList<>();
        for (Block block : byBlock.keySet()) {
            List<Concept> concepts = byBlock.get(block);
            List<ConceptTreeDto> conceptDtos = concepts.stream()
                    .map(this::toConceptTreeDto)
                    .toList();
            result.add(new BlockTreeDto(block, conceptDtos));
        }
        return result;
    }

    public Optional<ConceptTreeDto> getConceptBySlug(String slug) {
        return conceptRepository.findBySlug(slug)
                .filter(c -> c.getParent() == null)
                .map(this::toConceptTreeDto);
    }

    public Optional<ConceptDetailDto> getConceptDetailBySlug(String slug) {
        return conceptRepository.findBySlug(slug)
                .filter(c -> c.getParent() == null)
                .map(this::toConceptDetailDto);
    }

    public Optional<SubConceptDetailDto> getSubConceptBySlug(String slug) {
        return subConceptRepository.findBySlug(slug)
                .map(this::toSubConceptDetailDto);
    }

    public List<SearchResultDto> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }
        List<SearchResultDto> concepts = conceptRepository.searchRootConcepts(keyword).stream()
                .map(c -> new SearchResultDto(c.getId(), c.getTitle(), c.getSlug(), "CONCEPT", c.getTitle()))
                .toList();
        List<SearchResultDto> subConcepts = subConceptRepository.search(keyword).stream()
                .map(sc -> new SearchResultDto(sc.getId(), sc.getTitle(), sc.getSlug(), "SUBCONCEPT", sc.getConcept().getTitle()))
                .toList();
        List<SearchResultDto> combined = new ArrayList<>(concepts);
        combined.addAll(subConcepts);
        return combined;
    }

    private ConceptTreeDto toConceptTreeDto(Concept concept) {
        List<SubConceptSummaryDto> subDtos = concept.getSubConcepts().stream()
                .map(sc -> new SubConceptSummaryDto(sc.getId(), sc.getTitle(), sc.getSlug()))
                .toList();
        return new ConceptTreeDto(
                concept.getId(),
                concept.getTitle(),
                concept.getSlug(),
                concept.getBlock(),
                subDtos
        );
    }

    private SubConceptDetailDto toSubConceptDetailDto(SubConcept sc) {
        Concept parent = sc.getConcept();
        List<QuestionDto> questions = sc.getQuestions().stream()
                .map(q -> new QuestionDto(q.getQuestion(), q.getAnswer()))
                .toList();
        return new SubConceptDetailDto(
                sc.getId(),
                sc.getTitle(),
                sc.getSlug(),
                parent.getTitle(),
                parent.getSlug(),
                sc.getDescription(),
                sc.getCodeExample(),
                questions
        );
    }

    private ConceptDetailDto toConceptDetailDto(Concept concept) {
        List<QuestionDto> questions = concept.getQuestions().stream()
                .map(q -> new QuestionDto(q.getQuestion(), q.getAnswer()))
                .toList();
        List<SubConceptSummaryDto> subDtos = concept.getSubConcepts().stream()
                .map(sc -> new SubConceptSummaryDto(sc.getId(), sc.getTitle(), sc.getSlug()))
                .toList();
        return new ConceptDetailDto(
                concept.getId(),
                concept.getTitle(),
                concept.getSlug(),
                concept.getBlock().name(),
                concept.getDescription(),
                concept.getCodeExample(),
                questions,
                subDtos
        );
    }
}
