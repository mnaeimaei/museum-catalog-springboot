package com.noranalytics.museumcatalog.features.artifact.service;

import com.noranalytics.museumcatalog.features.artifact.dto.request.GetArtifactsQuery;
import com.noranalytics.museumcatalog.features.artifact.dto.response.*;

import java.util.Optional;
import java.util.UUID;

public interface ArtifactService {
    GetArtifactsResponseDto getArtifacts(GetArtifactsQuery query);
    Optional<ArtifactDetailsDto> getById(UUID id);
    Optional<ArtifactEditionDto> getEdition(UUID artifactId, UUID editionId);
}