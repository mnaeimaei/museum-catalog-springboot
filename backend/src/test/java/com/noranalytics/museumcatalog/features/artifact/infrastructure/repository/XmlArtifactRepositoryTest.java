package com.noranalytics.museumcatalog.features.artifact.infrastructure.repository;

import com.noranalytics.museumcatalog.features.artifact.domain.Artifact;
import com.noranalytics.museumcatalog.features.artifact.domain.ArtifactEdition;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class XmlArtifactRepositoryTest {

    private static final String XML_PATH = "classpath:data/artifacts.xml";

    private final XmlArtifactRepository repo = new XmlArtifactRepository(XML_PATH);

    // ── findById ────────────────────────────────────────────────────────────

    @Test
    void findById_FindsExistingRecord() {
        Optional<Artifact> artifact = repo.findById(
                UUID.fromString("1b0f6b9d-9f2e-4b1e-8f7e-7a7e4d7f0b6a"));

        assertTrue(artifact.isPresent());
        assertEquals("The Fjord at Dawn",  artifact.get().getTitle());
        assertEquals("Painting",           artifact.get().getCategory());
        assertEquals("ACC-2026-0001",      artifact.get().getAccessionNumber());
        assertFalse(artifact.get().getEditions().isEmpty());
        assertEquals(2, artifact.get().getEditions().size());
    }

    @Test
    void findById_ReturnsEmpty_WhenIdNotFound() {
        Optional<Artifact> artifact = repo.findById(UUID.randomUUID());

        assertTrue(artifact.isEmpty());
    }

    // ── findAll + filter (equivalent of WhereAsync) ──────────────────────────

    @Test
    void findAll_Filter_ByTitle() {
        List<Artifact> actual = repo.findAll().stream()
                .filter(a -> a.getTitle().equalsIgnoreCase("Bronze Runner"))
                .toList();

        assertEquals(1, actual.size());
        assertEquals("2c9c2f4d-7a91-4f2e-8d0b-1e8a5a0d3a4f",
                actual.get(0).getId().toString());
    }

    @Test
    void findAll_WithSorting_OrdersArtifacts() {
        List<Artifact> actual = repo.findAll().stream()
                .filter(a -> a.getCategory().equalsIgnoreCase("Painting")
                          || a.getCategory().equalsIgnoreCase("Sculpture"))
                .sorted(Comparator.comparing(Artifact::getTitle, String.CASE_INSENSITIVE_ORDER))
                .toList();

        assertFalse(actual.isEmpty());
        // "Bronze Runner" should come before "The Fjord at Dawn"
        assertEquals("Bronze Runner", actual.get(0).getTitle());
    }

    // ── findByCategory ───────────────────────────────────────────────────────

    @Test
    void findByCategory_ReturnsMatching() {
        List<Artifact> actual = repo.findByCategory("Manuscript");

        assertEquals(1, actual.size());
        assertEquals("Mariner's Log, 1887", actual.get(0).getTitle());
    }

    // ── findByAccessionNumber ────────────────────────────────────────────────

    @Test
    void findByAccessionNumber_ReturnsMatching() {
        List<Artifact> actual = repo.findByAccessionNumber("ACC-2026-0007");

        assertEquals(1, actual.size());
        assertEquals("Coastal Survey Chart", actual.get(0).getTitle());
    }

    // ── findEdition ──────────────────────────────────────────────────────────

    @Test
    void findEdition_ReturnsEdition_WhenItBelongsToArtifact() {
        UUID artifactId = UUID.fromString("2c9c2f4d-7a91-4f2e-8d0b-1e8a5a0d3a4f"); // Bronze Runner
        UUID editionId  = UUID.fromString("6a2f9d0c-1b7e-4c5a-8e2d-0f9a1c2b3d4e"); // v2.1

        Optional<ArtifactEdition> edition = repo.findEdition(artifactId, editionId);

        assertTrue(edition.isPresent());
        assertEquals("2.1",                                    edition.get().getVersion());
        assertEquals("en-US",                                  edition.get().getLanguage());
        assertEquals("Bronze Runner — Gallery Label v2.1",     edition.get().getDisplayLabel());
        assertEquals(artifactId,                               edition.get().getArtifactGuid());
    }

    @Test
    void findEdition_ReturnsEmpty_WhenEditionDoesNotExist() {
        UUID artifactId = UUID.fromString("2c9c2f4d-7a91-4f2e-8d0b-1e8a5a0d3a4f");

        Optional<ArtifactEdition> edition = repo.findEdition(artifactId, UUID.randomUUID());

        assertTrue(edition.isEmpty());
    }
}
