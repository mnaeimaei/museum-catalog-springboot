package com.noranalytics.museumcatalog.features.artifact.infrastructure.xmlmodel;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.UUID;

@XmlRootElement(name = "edition")
public class ArtifactEditionXml {

    @XmlElement(name = "id")
    public UUID id;

    @XmlElement(name = "artifact_guid")
    public UUID artifactGuid;

    @XmlElement(name = "version")
    public String version;

    @XmlElement(name = "language")
    public String language;

    @XmlElement(name = "display_label")
    public String displayLabel;
}