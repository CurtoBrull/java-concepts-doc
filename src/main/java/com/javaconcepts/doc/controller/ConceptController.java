package com.javaconcepts.doc.controller;

import com.javaconcepts.doc.dto.BlockTreeDto;
import com.javaconcepts.doc.dto.ConceptDetailDto;
import com.javaconcepts.doc.dto.SearchResultDto;
import com.javaconcepts.doc.dto.SubConceptDetailDto;
import com.javaconcepts.doc.service.ConceptService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ConceptController {

    private final ConceptService conceptService;

    public ConceptController(ConceptService conceptService) {
        this.conceptService = conceptService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/map";
    }

    @GetMapping("/map")
    public String map(Model model) {
        List<BlockTreeDto> blockTree = conceptService.getBlockTree();
        model.addAttribute("blockTree", blockTree);
        return "concepts/map";
    }

    @GetMapping("/concept/{slug}")
    public String conceptDetail(@PathVariable String slug, Model model) {
        ConceptDetailDto concept = conceptService.getConceptDetailBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Concepto no encontrado: " + slug));
        model.addAttribute("concept", concept);
        return "concepts/concept-detail";
    }

    @GetMapping("/subconcept/{slug}")
    public String subConceptDetail(@PathVariable String slug, Model model) {
        SubConceptDetailDto subConcept = conceptService.getSubConceptBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Subconcepto no encontrado: " + slug));
        model.addAttribute("subConcept", subConcept);
        return "concepts/subconcept-detail";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "q", required = false) String keyword, Model model) {
        List<SearchResultDto> results = conceptService.search(keyword);
        model.addAttribute("results", results);
        model.addAttribute("keyword", keyword);
        return "concepts/search-results";
    }
}
