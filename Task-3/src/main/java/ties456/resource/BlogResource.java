package ties456.resource;

import ties456.data.Blog;
import ties456.service.BlogService;

import javax.ws.rs.*;
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
    public List<Blog> getBlogs() {return service.getAll();}
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Blog addBlog(Blog blog) {
        return service.add(blog);
    }
    
    @GET
    @Path("/{blogId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Blog getBlog(@PathParam("blogId") long id) {
        return service.getById(id);
    }
    
    @DELETE
    @Path("/{blogId}")
    public void deleteBlog(@PathParam("blogId") long id) {
        service.removeById(id);
    }
    
    @PUT
    @Path("/{blogId}")
    public Blog updateBlog(@PathParam("blogId") long id, Blog updatedBlog) {
        return service.update(id, updatedBlog);
    }
    
    @Path("/{blogId}/comments")
    public CommentResource getCommentResource() {
        return new CommentResource();
    }
}
