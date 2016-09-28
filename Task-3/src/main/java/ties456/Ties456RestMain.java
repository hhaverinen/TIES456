package ties456;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Root resource (exposed at "myresource" path)
 */
@ApplicationPath("webapi")
public class Ties456RestMain extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(TestRes.class);
        return classes;
    }
}
