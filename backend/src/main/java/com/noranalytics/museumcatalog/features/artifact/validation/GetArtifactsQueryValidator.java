package com.noranalytics.museumcatalog.features.artifact.validation;

import com.noranalytics.museumcatalog.features.artifact.dto.request.GetArtifactsQuery;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class GetArtifactsQueryValidator {

    private static final Set<String> ALLOWED_SORT = Set.of(
            "title",
            "category",
            "accession_number",
            "accessionnumber"
    );

    public void validate(GetArtifactsQuery q) {
        if (q.page() == null || q.page() < 1) {
            throw new IllegalArgumentException("Page must be >= 1");
        }

        if (q.pageSize() == null || q.pageSize() < 1 || q.pageSize() > 200) {
            throw new IllegalArgumentException("PageSize must be between 1 and 200");
        }

        if (q.sort() == null || !ALLOWED_SORT.contains(q.sort())) {
            throw new IllegalArgumentException("Sort must be one of: title, category, accession_number");
        }

        if (q.direction() == null ||
                (!q.direction().equals("asc") && !q.direction().equals("desc"))) {
            throw new IllegalArgumentException("Direction must be 'asc' or 'desc'");
        }
    }
}