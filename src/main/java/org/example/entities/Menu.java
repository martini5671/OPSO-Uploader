package org.example.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "published_date")
    private LocalDate localDate;
    @OneToMany(mappedBy = "menu", cascade =
            {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST})
    private List<Meal> meals;

    // persist: propagates the persist operation from a parent to a child entity.
    // When we save the person entity, the address entity will also get saved.
    // address.setPerson(person);
    //    person.setAddresses(Arrays.asList(address));
    //    session.persist(person);

    public Menu() {
    }


    public Menu(List<Meal> meals, LocalDate localDate) {
        this.meals = meals;
        this.localDate = localDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
