package com.noranalytics.museumcatalog.features.artifact.dto.response;

import java.util.UUID;

public record ArtifactEditionDto(
        UUID editionId,
        UUID artifactGuid,
        String version,
        String language,
        String displayLabel
) {}