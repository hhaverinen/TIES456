package ties456;

import ties456.resource.BlogResource;
import ties456.resource.PodcastResource;
import ties456.resource.TestResource;

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
        classes.add(TestResource.class);
        classes.add(BlogResource.class);
        classes.add(PodcastResource.class);
        return classes;
    }
}
