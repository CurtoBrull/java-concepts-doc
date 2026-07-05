package com.javaconcepts.doc.dto;

public record SearchResultDto(
        Long id,
        String title,
        String slug,
        String type,
        String conceptTitle
) {
    public boolean isConcept() {
        return "CONCEPT".equals(type);
    }

    public boolean isSubConcept() {
        return "SUBCONCEPT".equals(type);
    }
}
