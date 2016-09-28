package ties456.resource;

import ties456.data.Blog;
import ties456.service.BlogService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
@Path("/blogs")
public class BlogResource {
    BlogService service = BlogService.getInstance();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Blog> get() {return service.getAll();}
    
}
