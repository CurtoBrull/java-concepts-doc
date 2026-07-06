package com.javaconcepts.doc.controller;

import com.javaconcepts.doc.dto.BlockTreeDto;
import com.javaconcepts.doc.dto.ConceptDetailDto;
import com.javaconcepts.doc.dto.SearchResultDto;
import com.javaconcepts.doc.dto.SubConceptDetailDto;
import com.javaconcepts.doc.entity.Block;
import com.javaconcepts.doc.service.ConceptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ConceptController.class)
class ConceptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConceptService conceptService;

    @Test
    void mapShouldReturnMapView() throws Exception {
        when(conceptService.getBlockTree()).thenReturn(List.of(new BlockTreeDto(Block.JAVA_CORE, List.of())));

        mockMvc.perform(get("/map"))
                .andExpect(status().isOk())
                .andExpect(view().name("concepts/map"));
    }

    @Test
    void conceptDetailShouldReturnDetailView() throws Exception {
        ConceptDetailDto dto = new ConceptDetailDto(
                1L, "JDBC", "jdbc", "JPA_HIBERNATE",
                "desc", null, List.of(), List.of());

        when(conceptService.getConceptDetailBySlug("jdbc")).thenReturn(Optional.of(dto));
        when(conceptService.getBlockTree()).thenReturn(List.of());

        mockMvc.perform(get("/concept/jdbc"))
                .andExpect(status().isOk())
                .andExpect(view().name("concepts/concept-detail"));
    }

    @Test
    void subConceptDetailShouldReturnDetailView() throws Exception {
        SubConceptDetailDto dto = new SubConceptDetailDto(
                1L, "CRUD", "crud-jdbc", "JDBC", "jdbc",
                "desc", "code", List.of());

        when(conceptService.getSubConceptBySlug("crud-jdbc")).thenReturn(Optional.of(dto));
        when(conceptService.getBlockTree()).thenReturn(List.of());

        mockMvc.perform(get("/subconcept/crud-jdbc"))
                .andExpect(status().isOk())
                .andExpect(view().name("concepts/subconcept-detail"));
    }

    @Test
    void searchShouldReturnSearchView() throws Exception {
        when(conceptService.search("maven")).thenReturn(List.of(
                new SearchResultDto(1L, "Maven", "maven", "CONCEPT", "Maven")));
        when(conceptService.getBlockTree()).thenReturn(List.of());

        mockMvc.perform(get("/search").param("q", "maven"))
                .andExpect(status().isOk())
                .andExpect(view().name("concepts/search-results"));
    }
}
