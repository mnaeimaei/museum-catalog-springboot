package com.noranalytics.museumcatalog.features.artifact.infrastructure.xmlmodel;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "artifacts")
public class ArtifactsDocumentXml {

    @XmlElement(name = "artifact")
    public List<ArtifactXml> items = new ArrayList<>();
}