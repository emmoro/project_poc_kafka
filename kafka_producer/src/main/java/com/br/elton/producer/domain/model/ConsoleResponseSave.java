package com.br.elton.producer.domain.model;

public class ConsoleResponseSave {

    private String name;

    private Integer releaseYear;

    public ConsoleResponseSave() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Override
    public String toString() {
        return "ConsoleResponse{" +
                ", name='" + name + '\'' +
                ", releaseYear=" + releaseYear +
                '}';
    }

}
