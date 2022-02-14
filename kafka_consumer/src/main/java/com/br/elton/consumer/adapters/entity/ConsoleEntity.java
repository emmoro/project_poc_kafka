package com.br.elton.consumer.adapters.entity;

import javax.persistence.*;

@Entity
@Table(name = "console")
public class ConsoleEntity {

    @Id
    @SequenceGenerator(name = "console_idsequence", sequenceName = "console_idsequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "console_idsequence")
    private Long id;

    @Column(name = "name", unique=true)
    private String name;

    @Column(name = "release_year")
    private Integer releaseYear;

    public ConsoleEntity() {

    }
    
    public ConsoleEntity(Long id, String name, Integer releaseYear) {
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
        if (releaseYear < 0) {
            throw new NumberFormatException("Console release year must be a positive number");
        }
        this.releaseYear = releaseYear;
    }

    @Override
    public String toString() {
        return "ConsoleEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", releaseYear=" + releaseYear +
                '}';
    }

}
