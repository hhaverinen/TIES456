package ties456.resource;

import ties456.data.Like;
import ties456.data.Podcast;
import ties456.service.LikeService;
import ties456.service.PodcastService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
@Path("/podcasts")
public class PodcastResource {
    PodcastService service = PodcastService.getInstance();
    LikeService likeService = LikeService.getInstance();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Podcast> getPodcasts(){return service.getAll();}
    
    @GET
    @Path("/{podcastId}/likes")
    public List<Like> getLikeResource(@PathParam("podcastId") long podcastId) {
        return likeService.getLikesByPodcastId(podcastId);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPodcast (Podcast pod, @Context UriInfo uriInfo)
    {
    	Podcast newPod = service.add(pod);
    	
    	String uri = uriInfo.getBaseUriBuilder().path(PodcastResource.class)
    				.path(Long.toString(pod.getId())).build().toString();
    	
    	newPod.addLink(uri, "self");
    	
    	uri = uriInfo.getBaseUriBuilder().path(PodcastResource.class)
    			.path(PodcastResource.class, "getLikeResource")
    			.path(LikeResource.class)
    			.resolveTemplate("podcastId", pod.getId()).build().toString();
    	
    	newPod.addLink(uri, "likes");
    	
    	String newId = String.valueOf(newPod.getId());
    	URI uri1 = uriInfo.getAbsolutePathBuilder().path(newId).build();
    	return Response.created(uri1).entity(newPod).build();
    }
     
}
