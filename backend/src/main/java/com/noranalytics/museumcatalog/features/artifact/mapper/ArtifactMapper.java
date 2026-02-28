package com.noranalytics.museumcatalog.features.artifact.mapper;

import com.noranalytics.museumcatalog.features.artifact.domain.Artifact;
import com.noranalytics.museumcatalog.features.artifact.domain.ArtifactEdition;
import com.noranalytics.museumcatalog.features.artifact.dto.response.*;

import java.util.List;

public final class ArtifactMapper {
    private ArtifactMapper() {}

    public static ArtifactListItemDto toListItemDto(Artifact a) {
        return new ArtifactListItemDto(a.getId(), a.getCategory(), a.getTitle(), a.getAccessionNumber());
    }

    public static ArtifactEditionDto toEditionDto(ArtifactEdition e) {
        return new ArtifactEditionDto(e.getId(), e.getArtifactGuid(), e.getVersion(), e.getLanguage(), e.getDisplayLabel());
    }

    public static ArtifactDetailsDto toDetailsDto(Artifact a) {
        List<ArtifactEditionDto> editions = a.getEditions().stream()
                .map(ArtifactMapper::toEditionDto)
                .toList();

        return new ArtifactDetailsDto(
                a.getId(),
                a.getCategory(),
                a.getTitle(),
                a.getDescription(),
                a.getAccessionNumber(),
                editions
        );
    }
}