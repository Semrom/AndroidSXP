package correct.javax.persistence;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by jeproQxT on 09/11/2016.
 */

public class Query {
    public <Entity> Collection<Entity> getResultList() {
        return new ArrayList<>();
    }

    public void setParameter(String value, String value1) {
    }

    public <Entity> Object getSingleResult()throws Exception {
        return new Object();
    }
}
