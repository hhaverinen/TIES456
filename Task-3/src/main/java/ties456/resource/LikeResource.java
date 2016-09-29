package ties456.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ties456.data.Like;
import ties456.service.LikeService;
import ties456.service.PodcastService;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LikeResource {
	LikeService likeService = LikeService.getInstance();
    PodcastService podcastService = PodcastService.getInstance();
    
    @GET
    public List<Like> getLikes(@PathParam("podcastId") long podcastId){return likeService.getLikesByPodcastId(podcastId);}
    
    @POST
    public Like addLike(@PathParam("podcastId") long podcastId, Like like) {
        return podcastService.addLikeToPodcast(podcastId, like);
    }
    
    @GET
    @Path("/{likeId}")
    public Like getLike(@PathParam("podcastId") long podcastId, @PathParam("likeId") long likeId) {
        return podcastService.getLike(podcastId, likeId);
    }
}