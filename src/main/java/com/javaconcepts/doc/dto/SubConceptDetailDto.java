package com.javaconcepts.doc.dto;

import java.util.List;

public record SubConceptDetailDto(
        Long id,
        String title,
        String slug,
        String conceptTitle,
        String conceptSlug,
        String description,
        String codeExample,
        List<QuestionDto> questions
) {
}
