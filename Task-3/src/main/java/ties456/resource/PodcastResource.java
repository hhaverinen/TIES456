package ties456.resource;

import ties456.data.Like;
import ties456.data.Podcast;
import ties456.service.LikeService;
import ties456.service.PodcastService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
}
