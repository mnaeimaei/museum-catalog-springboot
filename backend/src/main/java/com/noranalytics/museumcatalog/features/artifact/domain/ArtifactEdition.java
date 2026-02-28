package com.noranalytics.museumcatalog.features.artifact.domain;

import java.util.UUID;

public final class ArtifactEdition {
    private final UUID id;
    private final UUID artifactGuid;
    private final String version;
    private final String language;
    private final String displayLabel;

    public ArtifactEdition(UUID id, UUID artifactGuid, String version, String language, String displayLabel) {
        this.id = id;
        this.artifactGuid = artifactGuid;
        this.version = version == null ? "" : version;
        this.language = language == null ? "" : language;
        this.displayLabel = displayLabel == null ? "" : displayLabel;
    }

    public UUID getId() { return id; }
    public UUID getArtifactGuid() { return artifactGuid; }
    public String getVersion() { return version; }
    public String getLanguage() { return language; }
    public String getDisplayLabel() { return displayLabel; }
}