
package com.noranalytics.museumcatalog.features.artifact.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Artifact {
    private final UUID id;
    private final String category;
    private final String title;
    private final String description;
    private final String accessionNumber;
    private final List<ArtifactEdition> editions;

    public Artifact(
            UUID id,
            String category,
            String title,
            String description,
            String accessionNumber,
            List<ArtifactEdition> editions
    ) {
        this.id = id;
        this.category = category == null ? "" : category;
        this.title = title == null ? "" : title;
        this.description = description == null ? "" : description;
        this.accessionNumber = accessionNumber == null ? "" : accessionNumber;
        this.editions = editions == null ? new ArrayList<>() : new ArrayList<>(editions);
    }

    public UUID getId() { return id; }
    public String getCategory() { return category; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getAccessionNumber() { return accessionNumber; }
    public List<ArtifactEdition> getEditions() { return List.copyOf(editions); }
}