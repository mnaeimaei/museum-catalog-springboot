package com.noranalytics.museumcatalog.features.artifact.dto.response;

import java.util.UUID;

public record ArtifactListItemDto(
        UUID id,
        String category,
        String title,
        String accessionNumber
) {}