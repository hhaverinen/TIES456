package ties456.resource;

import ties456.data.Comment;
import ties456.service.BlogService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentResource {
    BlogService blogService = BlogService.getInstance();
    
    @GET
    public List<Comment> getComments(@PathParam("blogId") long blogId){return blogService.getCommentsByBlogId(blogId);}
    
    @POST
    public Response addComment(@PathParam("blogId") long blogId, Comment comment, @Context UriInfo uriInfo) {
        Comment newComment = blogService.addCommentToBlog(blogId, comment);
        String uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(newComment.getId()))
                .build().toString();
        newComment.addLink(uri, "self");
        
        uri = uriInfo.getBaseUriBuilder()
                .path(BlogResource.class)
                .path(String.valueOf(blogId))
                .build().toString();
        newComment.addLink(uri, "parent");
        
        return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(newComment.getId())).build())
                .entity(newComment)
                .build();
    }
    
    @GET
    @Path("/{commentId}")
    public Comment getComment(@PathParam("blogId") long blogId, @PathParam("commentId") long commentId) {
        return blogService.getComment(blogId, commentId);
    }
    
    @DELETE
    @Path("/{commentId}")
    public Response deleteComment(@PathParam("blogId") long blogId, @PathParam("commentId") long commentId) {
        blogService.removeComment(blogId, commentId);
        return Response.noContent().build();
    }
    
    @PUT
    @Path("/{commentId}")
    public Comment updateComment(@PathParam("blogId") long blogId, @PathParam("commentId") long commentId, Comment comment) {
        return blogService.updateComment(blogId, commentId, comment);
    }
}
