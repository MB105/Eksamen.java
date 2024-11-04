package controllers.impl;

import config.HibernateConfig;
import daos.impl.TripDAO;
import dtos.TripDTO;
import exceptions.ApiException;
import exceptions.Message;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.EntityManagerFactory;

public class TripController {

    private final TripDAO tripDAO;

    public TripController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.tripDAO = TripDAO.getInstance(emf);
    }

    public void readAll(Context ctx) throws ApiException {
        List<TripDTO> tripDTOs = tripDAO.getAll();
        ctx.status(200).json(tripDTOs);
    }

    public void create(Context ctx) throws ApiException {
        TripDTO tripDTO = ctx.bodyAsClass(TripDTO.class);
        TripDTO createdTrip = tripDAO.create(tripDTO);
        ctx.status(201).json(createdTrip);
    }

    public void read(Context ctx) throws ApiException {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        TripDTO tripDTO = tripDAO.getById(id);
        ctx.status(200).json(tripDTO);
    }

    public void update(Context ctx) throws ApiException {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        TripDTO updatedTripDTO = ctx.bodyAsClass(TripDTO.class);
        updatedTripDTO.setId(id);
        TripDTO tripDTO = tripDAO.update(updatedTripDTO);
        ctx.status(200).json(tripDTO);
    }

    public void delete(Context ctx) throws ApiException {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        tripDAO.delete(id);
        ctx.status(204);
    }

    public void addGuideToTrip(Context ctx) throws ApiException {
        Long tripId = ctx.pathParamAsClass("tripId", Long.class).get();
        Long guideId = ctx.pathParamAsClass("guideId", Long.class).get();
        tripDAO.addGuideToTrip(tripId, guideId);
        ctx.status(204);
    }

    public void populateDatabase(Context ctx) throws ApiException {
        tripDAO.populateDatabase();
        ctx.status(200).json(new Message(200, "Database populated successfully", LocalDateTime.now()));
    }
}



