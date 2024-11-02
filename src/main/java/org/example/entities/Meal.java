package org.example.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "meals")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String type;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    public Meal() {
    }

    public Meal(String name, String type, Menu menu) {
        this.name = name;
        this.type = type;
        this.menu = menu;
    }

    public Meal(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return id == meal.id && Objects.equals(name, meal.name) && Objects.equals(type, meal.type) && Objects.equals(menu, meal.menu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, menu);
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", menu=" + menu +
                '}';
    }
}
