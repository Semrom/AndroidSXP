package correct.javax.persistence;

/**
 * Created by jeproQxT on 09/11/2016.
 */

public class Persistence {
    public static EntityManagerFactory createEntityManagerFactory(String unitName) {
        return  new EntityManagerFactory();
    }
}
