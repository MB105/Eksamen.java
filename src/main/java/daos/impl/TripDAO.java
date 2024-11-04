package daos.impl;

import daos.IDAO;
import daos.ITripGuideDAO;
import dtos.TripDTO;
import entities.Guide;
import entities.Trip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import Enum.Category;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TripDAO implements IDAO<TripDTO>, ITripGuideDAO {
    private static TripDAO instance;
    private final EntityManagerFactory emf;

    private TripDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static TripDAO getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new TripDAO(emf);
        }
        return instance;
    }

    @Override
    public TripDTO create(TripDTO tripDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Trip trip = new Trip();
            trip.setStartTime(tripDTO.getStartTime());
            trip.setEndTime(tripDTO.getEndTime());
            trip.setLongitude(tripDTO.getLongitude());
            trip.setLatitude(tripDTO.getLatitude());
            trip.setName(tripDTO.getName());
            trip.setPrice(tripDTO.getPrice());
            trip.setCategory(tripDTO.getCategory());

            em.persist(trip);
            em.getTransaction().commit();

            tripDTO.setId(trip.getId());
            return tripDTO;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<TripDTO> getAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Trip> query = em.createQuery("SELECT t FROM Trip t", Trip.class);
            List<Trip> trips = query.getResultList();
            return trips.stream().map(this::convertToDTO).toList();
        } finally {
            em.close();
        }
    }

    @Override
    public TripDTO getById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Trip trip = em.find(Trip.class, id);
            return convertToDTO(trip);
        } finally {
            em.close();
        }
    }

    @Override
    public TripDTO update(TripDTO tripDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, tripDTO.getId());
            if (trip != null) {
                trip.setStartTime(tripDTO.getStartTime());
                trip.setEndTime(tripDTO.getEndTime());
                trip.setLongitude(tripDTO.getLongitude());
                trip.setLatitude(tripDTO.getLatitude());
                trip.setName(tripDTO.getName());
                trip.setPrice(tripDTO.getPrice());
                trip.setCategory(tripDTO.getCategory());
                em.persist(trip);
            }
            em.getTransaction().commit();
            return tripDTO;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, id);
            if (trip != null) {
                em.remove(trip);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void addGuideToTrip(Long tripId, Long guideId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, tripId);
            Guide guide = em.find(Guide.class, guideId);
            if (trip != null && guide != null) {
                trip.setGuide(guide);
                em.persist(trip);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Set<TripDTO> getTripsByGuide(Long guideId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Trip> query = em.createQuery("SELECT t FROM Trip t WHERE t.guide.id = :guideId", Trip.class);
            query.setParameter("guideId", guideId);
            List<Trip> trips = query.getResultList();
            Set<TripDTO> tripDTOS = new HashSet<>();
            for (Trip trip : trips) {
                tripDTOS.add(convertToDTO(trip));
            }
            return tripDTOS;
        } finally {
            em.close();
        }
    }

    private TripDTO convertToDTO(Trip trip) {
        if (trip == null) return null;
        return new TripDTO(
                trip.getId(),
                trip.getStartTime(),
                trip.getEndTime(),
                trip.getLongitude(),
                trip.getLatitude(),
                trip.getName(),
                trip.getPrice(),
                trip.getCategory()
        );
    }

    public void populateDatabase() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Guide guide1 = new Guide();
            guide1.setFirstName("John");
            guide1.setLastName("Doe");
            guide1.setEmail("john.doe@example.com");
            guide1.setPhone("1234567890");
            guide1.setYearsOfExperience(5);
            em.persist(guide1);

            Guide guide2 = new Guide();
            guide2.setFirstName("Jane");
            guide2.setLastName("Smith");
            guide2.setEmail("jane.smith@example.com");
            guide2.setPhone("0987654321");
            guide2.setYearsOfExperience(3);
            em.persist(guide2);

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
            em.persist(trip1);

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
            em.persist(trip2);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void deleteDatabase() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Guide").executeUpdate();
            em.createQuery("DELETE FROM Trip").executeUpdate();
            em.getTransaction().commit();

            // Reset the sequences
            em.getTransaction().begin();
            em.createNativeQuery("ALTER SEQUENCE guides_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE trips_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}