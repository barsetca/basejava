package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListData extends AbstractSections<List<String>> {

    private List<String> data = new ArrayList<>();

    public List<String> getData() {
        return data;
    }

    public void setData(String...data) {
        this.data.add(data[0]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListData listData = (ListData) o;
        return Objects.equals(data, listData.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
       return data +"";

    }
}
