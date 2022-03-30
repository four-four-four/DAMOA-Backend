package com.fourfourfour.damoa.domain.trend.enums;

public enum TrendTitle {

    TWENTY_GEN_TOP_5("20대 키워드 TOP 5"), THIRTY_GEN_TOP_5("30대 키워드 TOP 5"),
    HIGH_SCHOOL_STUDENT_TOP_5("고등학생 키워드 TOP 5"), UNIVERSITY_STUDENT_TOP_5("대학생 키워드 TOP 5");

    private final String description;

    TrendTitle(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
