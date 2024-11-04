import config.ApplicationConfig;
import config.HibernateConfig;
import dtos.TripDTO;
import entities.Guide;
import entities.Trip;
import io.javalin.Javalin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import Enum.Category;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TestEndpoints {

    private static final String BASE_URI = "http://localhost:7070/api";

    private static Javalin app;
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void setup() {
        HibernateConfig.setTest(true);
        app = ApplicationConfig.startServer(7070);
        baseURI = BASE_URI;
        entityManagerFactory = HibernateConfig.getEntityManagerFactoryForTest();
    }

    @BeforeEach
    public void setupEach() {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Guide guide1 = new Guide();
        guide1.setFirstName("John");
        guide1.setLastName("Doe");
        guide1.setEmail("john.doe@example.com");
        guide1.setPhone("1234567890");
        guide1.setYearsOfExperience(5);
        entityManager.persist(guide1);

        Guide guide2 = new Guide();
        guide2.setFirstName("Jane");
        guide2.setLastName("Smith");
        guide2.setEmail("jane.smith@example.com");
        guide2.setPhone("0987654321");
        guide2.setYearsOfExperience(3);
        entityManager.persist(guide2);

        Trip trip1 = new Trip(
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(3),
                10.0,
                20.0,
                "Beach Adventure",
                299.99,
                Category.BEACH,
                guide1
        );
        entityManager.persist(trip1);

        Trip trip2 = new Trip(
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(5),
                30.0,
                40.0,
                "City Tour",
                199.99,
                Category.CITY,
                guide2
        );
        entityManager.persist(trip2);

        entityManager.getTransaction().commit();
    }

    @AfterEach
    public void tearDownEach() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.getTransaction().begin();
            entityManager.createQuery("DELETE FROM Trip").executeUpdate();
            entityManager.createQuery("DELETE FROM Guide").executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    @AfterAll
    public static void tearDown() {
        ApplicationConfig.stopServer(app);
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @Test
    public void testGetAllTrips() {
        given()
                .contentType("application/json")
                .when()
                .get("/trips")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    public void testGetTripById() {
        Trip trip = entityManager.createQuery("SELECT t FROM Trip t", Trip.class).getResultList().get(0);

        given()
                .contentType("application/json")
                .when()
                .get("/trips/" + trip.getId())
                .then()
                .statusCode(200)
                .body("id", equalTo(trip.getId().intValue()))
                .body("name", equalTo(trip.getName()))
                .body("price", equalTo(trip.getPrice()));
    }

    @Test
    public void testCreateTrip() {
        TripDTO newTrip = new TripDTO(null, LocalDateTime.now(), LocalDateTime.now().plusDays(4), 50.0, 60.0, "Mountain Climbing", 499.99, Category.FOREST);

        given()
                .contentType("application/json")
                .body(newTrip)
                .when()
                .post("/trips")
                .then()
                .statusCode(201)
                .body("name", equalTo(newTrip.getName()))
                .body("price", equalTo(newTrip.getPrice()));
    }

    @Test
    public void testUpdateTrip() {
        Trip trip = entityManager.createQuery("SELECT t FROM Trip t", Trip.class).getResultList().get(0);

        TripDTO updatedTrip = new TripDTO(trip.getId(), LocalDateTime.now(), LocalDateTime.now().plusDays(2), 15.0, 25.0, "Updated Beach Adventure", 299.99, Category.BEACH);

        given()
                .contentType("application/json")
                .body(updatedTrip)
                .when()
                .put("/trips/" + trip.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo(updatedTrip.getName()));
    }

    @Test
    public void testDeleteTrip() {
        Trip trip = entityManager.createQuery("SELECT t FROM Trip t", Trip.class).getResultList().get(0);

        given()
                .contentType("application/json")
                .when()
                .delete("/trips/" + trip.getId())
                .then()
                .statusCode(204);
    }
}
