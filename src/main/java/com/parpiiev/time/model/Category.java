package com.parpiiev.time.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Table(name="categories")
@Entity
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;


    @OneToMany(mappedBy="category", cascade = CascadeType.MERGE)
    private List<Activity> activitiesList;

    public Category() {
    }

    /**
     * Test constructor
     * @param name to set category name
     */
    public Category(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer categoryId) {
        this.id = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Activity> getActivitiesList() {
        return activitiesList;
    }

    public void setActivitiesList(List<Activity> activitiesList) {
        this.activitiesList = activitiesList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "\n Category{" +
                "category_id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
