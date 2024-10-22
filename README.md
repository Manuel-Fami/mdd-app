# MDD-APP

mdd-app is a web application that allows users to browse different topics, subscribe to them, create articles, and comment on others' posts.

## Prerequisites

- Nodejs (npm) v20.12.2
- java JDK 21
- Maeven
- MySQL

## Setting up the project

### Clone the projet

> git clone https://github.com/Manuel-Fami/mdd-app.git

### Database connection

Update the database connection details in the application.properties file in the Spring Boot project (mdd-back):

> spring.datasource.url=jdbc:mysql://localhost:3306/mdd_db
> spring.datasource.username=your_username
> spring.datasource.password=your_password
> spring.jpa.hibernate.ddl-auto=update

### Compile and start Spring Boot

> cd mdd-app/mdd-back

> mvn clean install
> mvn spring-boot:run

### Run Angular

> cd ../mdd-front

> npm install
> ng serve
