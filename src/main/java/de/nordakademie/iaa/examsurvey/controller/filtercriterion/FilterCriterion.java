package de.nordakademie.iaa.examsurvey.controller.filtercriterion;


import com.google.common.collect.Sets;

import java.util.Set;

public class FilterCriterion {
    private final FilterType filterType;

    private FilterCriterion(FilterType type) {
        this.filterType = type;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public static Set<FilterCriterion> of(final Set<String> filterExpressions) {
        final Set<FilterCriterion> criteria = Sets.newHashSet();
        filterExpressions.forEach(filterString -> {
            final FilterType type = FilterType.of(filterString);
            if (type != null) {
                criteria.add(new FilterCriterion(type));
            }
        });
        return criteria;
    }

    public enum FilterType {
        OWN("own"),
        OPEN("open"),
        PARTICIPATED("participated");

        private final String target;

        FilterType(String target) {
            this.target = target;
        }

        private static FilterType of(final String filterExpression) {
            for (FilterType filterType : FilterType.values()) {
                if (filterType.target.equals(filterExpression)) {
                    return filterType;
                }
            }
            return null;
        }
    }
}
