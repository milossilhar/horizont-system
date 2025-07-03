package sk.leziemevpezinku.spring.service.base;

public interface DeleteService<ID> {

    /**
     * Deletes an entity identified by the given ID.
     *
     * @param id the unique identifier of the entity to be deleted
     */
    void delete(ID id);
}
