USE opso;

CREATE TABLE menus (
    id INT AUTO_INCREMENT PRIMARY KEY,
    published_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE meals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    type VARCHAR(255),
    menu_id INT,
    FOREIGN KEY (menu_id) REFERENCES menus(id)
);

CREATE TABLE reviews (
    id INT AUTO_INCREMENT PRIMARY KEY,
    meal_id INT,
    rating INT,
    comment TEXT,
    FOREIGN KEY (meal_id) REFERENCES meals(id)
);