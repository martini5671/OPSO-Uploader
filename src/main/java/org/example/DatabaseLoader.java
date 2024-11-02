package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entities.Meal;
import org.example.entities.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DatabaseLoader {
    private EntityManager entityManager;
    private List<Meal> mealList;
    private Menu menu;

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

        Meal m1 = new Meal("Solnik", "soup");
        Meal m2 = new Meal("Krupnik", "soup");
        Meal m3 = new Meal("Sałatka z sałatą", "vege");
        Menu menu1 = new Menu();
        menu1.setLocalDate(LocalDate.now().minusDays(1));
        DatabaseLoader databaseLoader = new DatabaseLoader(List.of(m1, m2, m3), menu1);
        databaseLoader.persistAll();
    }

}
