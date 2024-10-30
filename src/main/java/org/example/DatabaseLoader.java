package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entities.Meal;
import org.example.entities.Menu;

import java.time.LocalDate;
import java.util.List;

public class DatabaseLoader {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("MPU");
    private final EntityManager entityManager = emf.createEntityManager();
    private List<Meal> mealList;
    private Menu menu;

    public DatabaseLoader(List<Meal> mealList, Menu menu) {
        this.mealList = mealList;
        this.menu = menu;
    }

    public DatabaseLoader() {
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

    public void persistTest() {
        entityManager.getTransaction().begin();
        Meal meal = new Meal("pomidorowa", "soup");
        Meal meal1 = new Meal("ryba", "fishMeat");
        // set menu:
        Menu menu1 = new Menu();
        menu1.setLocalDate(LocalDate.now());
        meal.setMenu(menu1);
        meal1.setMenu(menu1);
        // set relaionships for meals:
        meal.setMenu(menu1);
        meal1.setMenu(menu1);
        // persist:
        entityManager.persist(meal);
        entityManager.persist(meal1);
        entityManager.persist(menu1);
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
