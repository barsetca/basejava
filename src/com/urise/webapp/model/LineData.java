package com.urise.webapp.model;

import java.util.Objects;

public class LineData extends AbstractSections<String> {

    private String data;

      public String getData() {
        return data;
    }

    public void setData(String...data) {
        this.data = data[0];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineData that = (LineData) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return data;
    }
}
