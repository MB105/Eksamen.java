package daos;

import dtos.TripDTO;

import java.util.Set;

public interface ITripGuideDAO extends IDAO<TripDTO> {
    void addGuideToTrip(Long tripId, Long guideId);
    Set<TripDTO> getTripsByGuide(Long guideId);
}


