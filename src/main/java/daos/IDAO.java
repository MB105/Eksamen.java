package daos;



import java.util.List;

public interface IDAO<T> {
    T create(T dto);
    List<T> getAll();
    T getById(Long id);
    T update(T dto);
    void delete(Long id);
}


