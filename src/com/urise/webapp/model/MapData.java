package com.urise.webapp.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MapData extends AbstractSections<Map<String, String>> {

    Map<String,String> data = new HashMap<>();

    public Map<String,String> getData() {
        return data;
    }

    public void setData(String...data) {
        Objects.requireNonNull(data, "setMapData - must be 2 arguments");
        this.data.put(data[0], data[1]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapData mapData = (MapData) o;
        return Objects.equals(data, mapData.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return data + "";
    }
}
