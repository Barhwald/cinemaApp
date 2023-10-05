It's a cinema management app written using Spring, Spring Boot for backend and Vaadin for frontend.

To run the app follow this guide:
1. Download the app using git clone to have it locally.
2. The app has an external MySQL database connected so there is no need to set any database on your own.
3. Simply run the CinemaApplication.java file in your IDE.
4. The app will open on http://localhost:8080/

-----

The app is built on these 4 core entities:
Movie, Room, Performance, Employee

Two external APIs are used:
1. OMDB API
2. Freecurrency API

-----
The app contains the following endpoints:

1. GET Fetch all movies
2. GET Fetch a single movie
3. POST Add a movie
4. PUT Edit a movie
5. DELETE Delete a movie
6. GET Fetch all rooms
7. GET Fetch a single room
8. POST Add a room
9. PUT Edit a room
10. DELETE Delete a room
11. GET Fetch all employees
12. GET Fetch a single employee
13. POST Add an employee
14. PUT Edit an employee
15. DELETE Delete an employee
16. GET Fetch all performances
17. GET Fetch a single performance
18. POST Add a performance
19. PUT Edit a performance
20. DELETE Delete a performance