package ties456.resource;

import ties456.data.Like;
import ties456.data.Podcast;
import ties456.errorhandling.DataNotFoundException;
import ties456.errorhandling.InvalidEntryException;
import ties456.service.PodcastService;

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
@Path("/podcasts")
public class PodcastResource {
    PodcastService podcastService = PodcastService.getInstance();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Podcast> getPodcasts() {
        return podcastService.getAll();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPodcast(Podcast podcast, @Context UriInfo uriInfo) {
        Podcast newPodcast = podcastService.add(podcast);
        if (newPodcast==null) throw new InvalidEntryException("Could not add a new podcast. Check your entry.");
        
        String uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(newPodcast.getId()))
                .build().toString();
        newPodcast.addLink(uri, "self");
        
        uri = uriInfo.getBaseUriBuilder()
                .path(PodcastResource.class)
                .path(PodcastResource.class, "getLikes")
                .resolveTemplate("podcastId", newPodcast.getId())
                .build().toString();
        newPodcast.addLink(uri, "likes");
        
        return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(newPodcast.getId())).build())
                .entity(newPodcast)
                .build();
    }
    
    @GET
    @Path("/{podcastId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Podcast getPodcast(@PathParam("podcastId") long podcastId) {
        Podcast p =podcastService.getById(podcastId);
        if(p==null) throw new DataNotFoundException("Podcast (id "+podcastId+") not found");
    	return p;
    }
    
    @DELETE
    @Path("/{podcastId}")
    public Response deletePodcast(@PathParam("podcastId") long podcastId) {
        podcastService.removeById(podcastId);
        return Response.noContent().build();
    }
    
    @PUT
    @Path("/{podcastId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Podcast updatePodcast(@PathParam("podcastId") long podcastId, Podcast podcast) {
        Podcast p = podcastService.update(podcastId, podcast);
        if(p==null) throw new InvalidEntryException("Could not update podcast(id "+podcastId+")");
    	return p;
    }
    
    /*
     * Below "LikeResource" implementation since creating an actual class
     * causes the jersey bind multiple class on same root, which causes errors...
     */
    @GET
    @Path("/{podcastId}/likes")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Like> getLikes(@PathParam("podcastId") long podcastId) {
        return podcastService.getLikesByPodcastId(podcastId);
    }
    
    @POST
    @Path("/{podcastId}/likes")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addLike(@PathParam("podcastId") long podcastId, Like like, @Context UriInfo uriInfo) {
        Like newLike = podcastService.addLikeToPodcast(podcastId, like);
        if(newLike==null) throw new InvalidEntryException("Could not add a like for Podcast (id "+podcastId+")");
        
        String uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(newLike.getId()))
                .build().toString();
        newLike.addLink(uri, "self");
        
        uri = uriInfo.getBaseUriBuilder()
                .path(PodcastResource.class)
                .path(String.valueOf(podcastId))
                .build().toString();
        newLike.addLink(uri, "parent");
        
        return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(newLike.getId())).build())
                .entity(newLike)
                .build();
    }
    
    @GET
    @Path("/{podcastId}/likes/{likeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Like getLike(@PathParam("podcastId") long podcastId, @PathParam("likeId") long likeId) {
        Like l = podcastService.getLike(podcastId, likeId);
        if (l==null) throw new DataNotFoundException("Like (id "+likeId+") for podcast (id "+podcastId+") could not be found");
    	return  l;
    }
    
    @DELETE
    @Path("/{podcastId}/likes/{likeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteLike(@PathParam("podcastId") long podcastId, @PathParam("likeId") long likeId) {
        podcastService.removeLike(podcastId, likeId);
        return Response.noContent().build();
    }
    
    @PUT
    @Path("/{podcastId}/likes/{likeId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Like updateLike(@PathParam("podcastId") long podcastId, @PathParam("likeId") long likeId, Like like) {
        Like l = podcastService.updateLike(podcastId, likeId, like);
        if(l==null) throw new InvalidEntryException("Like (id "+likeId+") could not be updated");
    	return l;
    }
}
