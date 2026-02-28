package com.noranalytics.museumcatalog.features.artifact.dto.response;

import java.util.List;

public record GetArtifactsResponseDto(
        int page,
        int pageSize,
        int total,
        List<ArtifactListItemDto> data
) {}