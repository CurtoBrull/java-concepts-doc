package com.javaconcepts.doc.dto;

import java.util.List;

public record ConceptDetailDto(
        Long id,
        String title,
        String slug,
        String block,
        String description,
        String codeExample,
        List<QuestionDto> questions,
        List<SubConceptSummaryDto> subConcepts
) {
}
