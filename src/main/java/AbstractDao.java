import javax.persistence.EntityManager;

public abstract class AbstractDao<T> implements Dao<T> {
    protected EntityManager em;

    public AbstractDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(T entity) {
        em.getTransaction().begin();
        try {
            em.persist(entity);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
        }
    }

    @Override
    public void delete(T entity) {
        em.getTransaction().begin();
        try {
            em.remove(entity);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
        }

    }
    @Override
    public void update(T entity){
        em.getTransaction().begin();
        try {
            em.persist(entity);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
        }
    }
}
