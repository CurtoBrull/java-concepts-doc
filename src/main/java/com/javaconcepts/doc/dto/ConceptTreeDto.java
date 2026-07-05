package com.javaconcepts.doc.dto;

import com.javaconcepts.doc.entity.Block;

import java.util.List;

public record ConceptTreeDto(
        Long id,
        String title,
        String slug,
        Block block,
        List<SubConceptSummaryDto> subConcepts
) {
}
