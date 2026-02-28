package com.noranalytics.museumcatalog.features.artifact.service;

import com.noranalytics.museumcatalog.features.artifact.domain.Artifact;
import com.noranalytics.museumcatalog.features.artifact.dto.request.GetArtifactsQuery;
import com.noranalytics.museumcatalog.features.artifact.dto.response.*;
import com.noranalytics.museumcatalog.features.artifact.mapper.ArtifactMapper;
import com.noranalytics.museumcatalog.features.artifact.repository.ArtifactRepository;
import org.springframework.stereotype.Service;
import com.noranalytics.museumcatalog.features.artifact.validation.GetArtifactsQueryValidator;


import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArtifactServiceImpl implements ArtifactService {

    private final ArtifactRepository repo;
    private final GetArtifactsQueryValidator validator;

    public ArtifactServiceImpl(ArtifactRepository repo, GetArtifactsQueryValidator validator) {
        this.repo = repo;
        this.validator = validator;
    }

    @Override
    public GetArtifactsResponseDto getArtifacts(GetArtifactsQuery rawQuery) {
        GetArtifactsQuery q = rawQuery.normalize();
        validator.validate(q);

        // 1) Filter (like your WhereAsync predicate)
        List<Artifact> items = repo.findAll().stream()
                .filter(a -> q.title() == null || a.getTitle().toLowerCase().contains(q.title().toLowerCase()))
                .filter(a -> q.accessionNumber() == null || a.getAccessionNumber().toLowerCase().contains(q.accessionNumber().toLowerCase()))
                .toList();

        // 2) Sort
        boolean desc = "desc".equalsIgnoreCase(q.direction());
        Comparator<Artifact> cmp = switch (q.sort()) {
            case "title" -> Comparator.comparing(Artifact::getTitle, String.CASE_INSENSITIVE_ORDER);
            case "category" -> Comparator.comparing(Artifact::getCategory, String.CASE_INSENSITIVE_ORDER);
            case "accession_number", "accessionnumber" ->
                    Comparator.comparing(Artifact::getAccessionNumber, String.CASE_INSENSITIVE_ORDER);
            default -> Comparator.comparing(Artifact::getTitle, String.CASE_INSENSITIVE_ORDER);
        };
        if (desc) cmp = cmp.reversed();
        items = items.stream().sorted(cmp).toList();

        // 3) Page
        int total = items.size();
        int from = Math.max(0, (q.page() - 1) * q.pageSize());
        int to = Math.min(total, from + q.pageSize());

        List<ArtifactListItemDto> paged = (from >= total)
                ? List.of()
                : items.subList(from, to).stream().map(ArtifactMapper::toListItemDto).toList();

        return new GetArtifactsResponseDto(q.page(), q.pageSize(), total, paged);
    }

    @Override
    public Optional<ArtifactDetailsDto> getById(UUID id) {
        return repo.findById(id).map(ArtifactMapper::toDetailsDto);
    }

    @Override
    public Optional<ArtifactEditionDto> getEdition(UUID artifactId, UUID editionId) {
        return repo.findEdition(artifactId, editionId).map(ArtifactMapper::toEditionDto);
    }
}