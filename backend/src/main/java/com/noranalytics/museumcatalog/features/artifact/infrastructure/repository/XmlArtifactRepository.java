package com.noranalytics.museumcatalog.features.artifact.infrastructure.repository;

import com.noranalytics.museumcatalog.features.artifact.domain.Artifact;
import com.noranalytics.museumcatalog.features.artifact.domain.ArtifactEdition;
import com.noranalytics.museumcatalog.features.artifact.infrastructure.xmlmodel.ArtifactEditionXml;
import com.noranalytics.museumcatalog.features.artifact.infrastructure.xmlmodel.ArtifactXml;
import com.noranalytics.museumcatalog.features.artifact.infrastructure.xmlmodel.ArtifactsDocumentXml;
import com.noranalytics.museumcatalog.features.artifact.repository.ArtifactRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class XmlArtifactRepository implements ArtifactRepository {

    private final List<Artifact> artifacts;

    /**
     * Reads artifacts from XML on startup.
     *
     * Supports:
     * - classpath:...   (e.g., classpath:data/artifacts.xml)
     * - file system path (e.g., ./data/artifacts.xml or /var/museum/artifacts.xml)
     */
    public XmlArtifactRepository(@Value("${museum.artifacts.xml-path}") String xmlPath) {
        this.artifacts = load(xmlPath);
    }

    private static List<Artifact> load(String xmlPath) {
        if (xmlPath == null || xmlPath.isBlank()) {
            throw new IllegalStateException("Property 'museum.artifacts.xml-path' is missing or blank.");
        }

        String trimmed = xmlPath.trim();
        if (trimmed.startsWith("classpath:")) {
            String cp = trimmed.substring("classpath:".length());
            // allow both "classpath:data/..." and "classpath:/data/..."
            if (cp.startsWith("/")) cp = cp.substring(1);
            return loadFromClasspath(cp);
        }

        // Treat everything else as a filesystem path
        return loadFromFile(trimmed);
    }

    private static List<Artifact> loadFromClasspath(String classpathLocation) {
        try (InputStream is = new ClassPathResource(classpathLocation).getInputStream()) {
            ArtifactsDocumentXml doc = unmarshal(is);
            if (doc == null || doc.items == null) return List.of();
            return doc.items.stream().map(XmlArtifactRepository::toDomain).toList();
        } catch (Exception ex) {
            throw new IllegalStateException(
                    "Failed to load artifacts XML from classpath: " + classpathLocation, ex
            );
        }
    }

    private static List<Artifact> loadFromFile(String filePath) {
        try (InputStream is = Files.newInputStream(Path.of(filePath))) {
            ArtifactsDocumentXml doc = unmarshal(is);
            if (doc == null || doc.items == null) return List.of();
            return doc.items.stream().map(XmlArtifactRepository::toDomain).toList();
        } catch (Exception ex) {
            throw new IllegalStateException(
                    "Failed to load artifacts XML from file: " + filePath, ex
            );
        }
    }

    private static ArtifactsDocumentXml unmarshal(InputStream is) throws Exception {
        JAXBContext ctx = JAXBContext.newInstance(ArtifactsDocumentXml.class, ArtifactXml.class, ArtifactEditionXml.class);
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        return (ArtifactsDocumentXml) unmarshaller.unmarshal(is);
    }

    private static Artifact toDomain(ArtifactXml x) {
        List<ArtifactEditionXml> src = (x.editions == null) ? List.of() : x.editions;

        var editions = src.stream()
                .map(e -> new ArtifactEdition(
                        e.id,
                        e.artifactGuid,
                        e.version,
                        e.language,
                        e.displayLabel
                ))
                .toList();

        return new Artifact(
                x.id,
                x.category,
                x.title,
                x.description,
                x.accessionNumber,
                editions
        );
    }

    @Override
    public List<Artifact> findAll() {
        return List.copyOf(artifacts);
    }

    @Override
    public Optional<Artifact> findById(UUID id) {
        if (id == null) return Optional.empty();
        return artifacts.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    @Override
    public List<Artifact> findByCategory(String category) {
        String c = (category == null) ? "" : category;
        return artifacts.stream()
                .filter(a -> a.getCategory().equalsIgnoreCase(c))
                .toList();
    }

    @Override
    public List<Artifact> findByAccessionNumber(String accessionNumber) {
        String acc = (accessionNumber == null) ? "" : accessionNumber;
        return artifacts.stream()
                .filter(a -> a.getAccessionNumber().equalsIgnoreCase(acc))
                .toList();
    }

    @Override
    public Optional<ArtifactEdition> findEdition(UUID artifactId, UUID editionId) {
        if (artifactId == null || editionId == null) return Optional.empty();

        return findById(artifactId)
                .flatMap(a -> a.getEditions().stream()
                        .filter(e -> e.getId().equals(editionId))
                        .findFirst()
                );
    }
}