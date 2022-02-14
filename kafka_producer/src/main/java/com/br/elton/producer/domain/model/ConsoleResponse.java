package com.br.elton.producer.domain.model;

public class ConsoleResponse {

    private Long id;

    private String name;

    private Integer releaseYear;

    public ConsoleResponse() {
    }

    public ConsoleResponse(Long id, String name, Integer releaseYear) {
        this.id = id;
        this.name = name;
        this.releaseYear = releaseYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", releaseYear=" + releaseYear +
                '}';
    }

}
