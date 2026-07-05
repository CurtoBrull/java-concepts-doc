package com.javaconcepts.doc.dto;

import com.javaconcepts.doc.entity.Block;

import java.util.List;

public record BlockTreeDto(
        Block block,
        List<ConceptTreeDto> concepts
) {
}
