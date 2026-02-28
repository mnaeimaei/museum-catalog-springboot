package com.noranalytics.museumcatalog.features.artifact.dto.response;

import java.util.List;
import java.util.UUID;

public record ArtifactDetailsDto(
        UUID id,
        String category,
        String title,
        String description,
        String accessionNumber,
        List<ArtifactEditionDto> editions
) {}