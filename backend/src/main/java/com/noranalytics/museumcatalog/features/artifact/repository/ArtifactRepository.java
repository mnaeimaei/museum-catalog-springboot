package com.noranalytics.museumcatalog.features.artifact.repository;

import com.noranalytics.museumcatalog.features.artifact.domain.Artifact;
import com.noranalytics.museumcatalog.features.artifact.domain.ArtifactEdition;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArtifactRepository {

    List<Artifact> findAll();

    Optional<Artifact> findById(UUID id);

    List<Artifact> findByCategory(String category);

    List<Artifact> findByAccessionNumber(String accessionNumber);

    Optional<ArtifactEdition> findEdition(UUID artifactId, UUID editionId);
}