package daos.impl;

import daos.IDAO;
import dtos.GuideDTO;
import entities.Guide;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class GuideDAO implements IDAO<GuideDTO> {
    private EntityManagerFactory emf;

    public GuideDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public GuideDTO create(GuideDTO guideDTO) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Guide guide = new Guide();
        guide.setFirstName(guideDTO.getFirstName());
        guide.setLastName(guideDTO.getLastName());
        guide.setEmail(guideDTO.getEmail());
        guide.setPhone(guideDTO.getPhone());
        guide.setYearsOfExperience(guideDTO.getYearsOfExperience());
        em.persist(guide);
        em.getTransaction().commit();
        em.close();
        return guideDTO;
    }

    @Override
    public List<GuideDTO> getAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Guide> query = em.createQuery("SELECT g FROM Guide g", Guide.class);
        List<Guide> guides = query.getResultList();
        em.close();
        return guides.stream().map(this::convertToDTO).toList();
    }

    @Override
    public GuideDTO getById(Long id) {
        EntityManager em = emf.createEntityManager();
        Guide guide = em.find(Guide.class, id);
        em.close();
        return convertToDTO(guide);
    }

    @Override
    public GuideDTO update(GuideDTO guideDTO) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Guide guide = em.find(Guide.class, guideDTO.getId());
        if (guide != null) {
            guide.setFirstName(guideDTO.getFirstName());
            guide.setLastName(guideDTO.getLastName());
            guide.setEmail(guideDTO.getEmail());
            guide.setPhone(guideDTO.getPhone());
            guide.setYearsOfExperience(guideDTO.getYearsOfExperience());
            em.persist(guide);
        }
        em.getTransaction().commit();
        em.close();
        return guideDTO;
    }

    @Override
    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Guide guide = em.find(Guide.class, id);
        if (guide != null) {
            em.remove(guide);
        }
        em.getTransaction().commit();
        em.close();
    }

    private GuideDTO convertToDTO(Guide guide) {
        if (guide == null) return null;
        return new GuideDTO(
                guide.getId(),
                guide.getFirstName(),
                guide.getLastName(),
                guide.getEmail(),
                guide.getPhone(),
                guide.getYearsOfExperience()
        );
    }
}

