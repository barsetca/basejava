package com.urise.webapp.model;

import java.util.Objects;

public class LineSection extends AbstractSections {
    private static final long serialVersionUID = 1L;

    public static final LineSection EMPTY = new LineSection("");

    private String text;

    public LineSection() {
    }

    public LineSection(String text) {
        Objects.requireNonNull(text, "text must not be null");
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineSection that = (LineSection) o;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return text;
    }
}
