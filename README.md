# OPSO-Uploader
This Java application scrapes the menu data from a canteen in Krakow called OPSO and uploads it to a local instance of MySQL database. The scraping process is scheduled to run daily to ensure that the database is always up to date with the latest menu offerings. This application serves as the initial stage in the development of a Spring Boot web application for scoring food at OPSO canteen.

# Features
- Scrapes menu data from the OPSO canteen in Krakow
- Uploads the scraped data to a MySQL database
- Automated daily scraping process to keep the database updated
- Uses Hibernate ORM and Log4J2 for logging errors 
