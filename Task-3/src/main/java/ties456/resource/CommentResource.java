package ties456.resource;

import ties456.data.Comment;
import ties456.service.CommentService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
@Path("/comments")
public class CommentResource {
    CommentService service = CommentService.getInstance();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> get(){return service.getAll();}
}
