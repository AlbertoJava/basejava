package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link homePage;
    private final String title;
    private final String description;
    private List<DateInterval> periods = new ArrayList<>();

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(startDate, "startDate must not null");
        Objects.requireNonNull(endDate, "endDate must not null");
        Objects.requireNonNull(title, "title must not null");

        this.homePage = new Link(name, url);
        periods.add(new DateInterval(startDate, endDate));
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        final String periodDescription = periods.stream().map((x) -> x.toString()).reduce((x, y) -> x + ", " + y).toString();
        return "Organization{" +
                "homePage=" + homePage +
                ", workPeriod=" + periodDescription +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (homePage != null ? !homePage.equals(that.homePage) : that.homePage != null) return false;
        if (!periods.equals(that.periods)) return false;
        if (!title.equals(that.title)) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = homePage != null ? homePage.hashCode() : 0;
        result = 31 * result + periods.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    private class DateInterval {
        private LocalDate startDate;
        private LocalDate endDate;

        private DateInterval(LocalDate startDate, LocalDate endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Override
        public String toString() {
            return startDate + " - " + endDate;
        }
    }
}
