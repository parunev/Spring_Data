package com.example.json_exercise.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "categories")
@Entity
public class Category extends BaseEntity{
    @Column
    private String name;

    @Override
    public boolean equals(Object o) {


        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name)
                && Objects.equals(getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, getId());
    }
}
