package ties456.resource;

import ties456.data.Comment;
import ties456.service.BlogService;
import ties456.service.CommentService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentResource {
    CommentService commentService = CommentService.getInstance();
    BlogService blogService = BlogService.getInstance();
    
    @GET
    public List<Comment> getComments(@PathParam("blogId") long blogId){return commentService.getCommentsByBlogId(blogId);}
    
    @POST
    public Comment addComment(@PathParam("blogId") long blogId, Comment comment) {
        return blogService.addCommentToBlog(blogId, comment);
    }
    
    @GET
    @Path("/{commentId}")
    public Comment getComment(@PathParam("blogId") long blogId, @PathParam("commentId") long commentId) {
        return blogService.getComment(blogId, commentId);
    }
}
