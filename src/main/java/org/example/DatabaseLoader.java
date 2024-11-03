package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.example.entities.Meal;
import org.example.entities.Menu;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseLoader {
    private final EntityManager entityManager;
    private final List<Meal> mealList;
    private final Menu menu;

    public DatabaseLoader(List<Meal> mealList, Menu menu, Map<String, String> hibernatePropertiesMap) {
        this.mealList = mealList;
        this.menu = menu;
        this.entityManager = Persistence.createEntityManagerFactory("MPU", hibernatePropertiesMap)
                .createEntityManager();
    }

    public DatabaseLoader(List<Meal> mealList, Menu menu) {
        this.mealList = mealList;
        this.menu = menu;
        this.entityManager = Persistence.createEntityManagerFactory("MPU").createEntityManager();
    }


    public void persistAll() {
        entityManager.getTransaction().begin();
        menu.setMeals(mealList);
        for (Meal meal : mealList) {
            meal.setMenu(menu);
        }
        entityManager.persist(menu);
        entityManager.getTransaction().commit(); // thats where queries happen

        // or also in flush()
        entityManager.close();
    }

    public static void main(String[] args) {
        // persist test
        Meal m1 = new Meal("Solnik", "soup");
        Meal m2 = new Meal("Krupnik", "soup");
        Meal m3 = new Meal("Sałatka z sałatą", "vege");
        Menu menu1 = new Menu();
        Map<String, String> hibernateProps = new HashMap<>();
        DatabaseLoader databaseLoader = new DatabaseLoader(List.of(m1, m2, m3), menu1, hibernateProps);
        databaseLoader.persistAll();
    }

}
