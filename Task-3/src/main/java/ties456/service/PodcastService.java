package ties456.service;

import ties456.data.Like;
import ties456.data.Podcast;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
public class PodcastService extends BaseService<Podcast> {
    private static final PodcastService instance = new PodcastService();
    public static PodcastService getInstance() {return instance;}
    private PodcastService(){}
    LikeService likeService = LikeService.getInstance();
    
    
    public Like addLikeToPodcast(long podcastId, Like like) {
        Podcast podcast = getById(podcastId);
        if(podcast == null) return null;
        
        like.setPodcastId(podcastId);
        Like realLike = likeService.add(like);
        if(realLike == null) return null;
        
        podcast.getLikes().put(realLike.getId(), realLike);
        
        update(podcastId, podcast); //Updates stamp
        
        return realLike;
    }
    
    public Like getLike(long podcastId, long likeId) {
        return getById(podcastId).getLikes().get(likeId);
    }
}
