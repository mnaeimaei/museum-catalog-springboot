package com.noranalytics.museumcatalog.features.artifact.api;

import com.noranalytics.museumcatalog.exception.NotFoundException;
import com.noranalytics.museumcatalog.features.artifact.dto.request.GetArtifactsQuery;
import com.noranalytics.museumcatalog.features.artifact.dto.response.*;
import com.noranalytics.museumcatalog.features.artifact.service.ArtifactService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/artifacts")
@Validated
public class ArtifactController {

    private final ArtifactService service;

    public ArtifactController(ArtifactService service) {
        this.service = service;
    }

    @GetMapping
    public GetArtifactsResponseDto getArtifacts(@Valid @ModelAttribute GetArtifactsQuery query) {
        return service.getArtifacts(query);
    }

    @GetMapping("/{id}")
    public ArtifactDetailsDto getById(@PathVariable UUID id) {
        return service.getById(id).orElseThrow(() -> new NotFoundException("Artifact not found"));
    }

    @GetMapping("/{id}/editions/{editionId}")
    public ArtifactEditionDto getEdition(@PathVariable UUID id, @PathVariable UUID editionId) {
        return service.getEdition(id, editionId).orElseThrow(() -> new NotFoundException("Edition not found"));
    }
}
