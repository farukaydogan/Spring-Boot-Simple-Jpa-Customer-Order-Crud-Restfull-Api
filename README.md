# Spring-Boot-Simple-Jpa-Customer-Order-Crud-Restfull-Api
Document for the Use of the Application
Installing Maven Dependencies

Your application is managed through Maven. Therefore, you should make sure that Maven is installed on your system before installing your project's dependencies. If Maven is not installed, you can access the installation instructions from the official Maven site.
Open terminal or command line in the root of your project and install your project's dependencies by running the following command:
mvn clean install

Database Configuration
You can find the necessary settings for the database connection in the resources/application.yml file. You need to update these settings according to your own database.

spring:
   datasource:
     url: jdbc:postgresql://[Database_HOST]:[PORT]/[DATABASE_NAME]
     username: [USER_NAME]
     password: [PASSWORD]

For example, if you are connecting to a database running on your local machine, you can replace [DATABASE_HOST] with localhost, and [PORT] with 5432 (the default port for PostgreSQL).
Note: For security reasons, it is recommended not to share your actual database password in the application.yml file. You should only forward this information privately to those who need it.

Hibernate and JPA Settings
Hibernate automatically manages your application's database operations. The ddl-auto:update setting ensures that the database schema is automatically updated when the application is started. This is very useful in development, but in production it is recommended to set this to none.
The show-sql: false setting is used to prevent Hibernate from printing SQL queries to the console. You can observe SQL queries by changing this setting to true.

Running the Application
After installing your dependencies, you can run the application with the following Maven command:

mvn spring-boot:run

This command starts your application and runs at http://localhost:8080 by default.




API Documentation and Testing (Swagger)
Swagger is used to view the documentation of the application's API endpoints and test these endpoints.
You can open Swagger UI by visiting the address below in your browser and test it as you wish.
http://localhost:8080/swagger-ui.html

Through this interface, you can view API endpoints, examine their details, and test them by sending requests to these endpoints.
