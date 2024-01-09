package com.example.demo2productmanagespringboot.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ColumnDefault("true")
    private boolean isActive;

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
        this.isActive = true;
    }

    public Category() {
        this.isActive = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
