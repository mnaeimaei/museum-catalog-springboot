package com.noranalytics.museumcatalog.features.artifact.infrastructure.xmlmodel;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@XmlRootElement(name = "artifact")
public class ArtifactXml {

    @XmlElement(name = "id")
    public UUID id;

    @XmlElement(name = "category")
    public String category;

    @XmlElement(name = "title")
    public String title;

    @XmlElement(name = "description")
    public String description;

    @XmlElement(name = "accession_number")
    public String accessionNumber;

    @XmlElementWrapper(name = "editions")
    @XmlElement(name = "edition")
    public List<ArtifactEditionXml> editions = new ArrayList<>();
}