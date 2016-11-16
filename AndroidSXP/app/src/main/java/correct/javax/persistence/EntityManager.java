package correct.javax.persistence;

/**
 * Created by jeproQxT on 09/11/2016.
 */

public class EntityManager<Entity> {


    public Entity find(Class<?> theClass, String id) {
        return null;
    }

    public Query createQuery(String s) {
        return new Query();
    }

    public <Entity> void persist(Entity entity) {
    }

    public Connection getTransaction() {
        return new Connection();
    }
}