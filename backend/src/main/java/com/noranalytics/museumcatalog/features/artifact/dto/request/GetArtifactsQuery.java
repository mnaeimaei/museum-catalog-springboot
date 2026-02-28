package com.noranalytics.museumcatalog.features.artifact.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record GetArtifactsQuery(
        @Min(1) Integer page,
        @Min(1) @Max(200) Integer pageSize,
        String title,
        String accessionNumber,
        String sort,
        String direction
) {
    public GetArtifactsQuery normalize() {
        int p = (page == null || page < 1) ? 1 : page;
        int ps = (pageSize == null || pageSize < 1) ? 10 : Math.min(pageSize, 200);

        String s = (sort == null || sort.isBlank()) ? "title" : sort.trim().toLowerCase();
        String d = (direction == null || direction.isBlank()) ? "asc" : direction.trim().toLowerCase();

        String t = (title == null || title.isBlank()) ? null : title.trim();
        String acc = (accessionNumber == null || accessionNumber.isBlank()) ? null : accessionNumber.trim();

        return new GetArtifactsQuery(p, ps, t, acc, s, d);
    }
}