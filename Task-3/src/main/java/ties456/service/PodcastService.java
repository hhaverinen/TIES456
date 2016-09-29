package ties456.service;

import ties456.data.Like;
import ties456.data.Podcast;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
public class PodcastService extends BaseService<Podcast> {
    private static final PodcastService instance = new PodcastService();
    public static PodcastService getInstance() {return instance;}
    private PodcastService(){}
    LikeService likeService = LikeService.getInstance();
    
    @Override
    public void removeById(long id) {
        Podcast podcast = getById(id);
        super.removeById(id);
        //Removes any Likes attached to podcast
        podcast.getLikes().keySet().forEach(likeService::removeById);
    }
    
    public Like addLikeToPodcast(long podcastId, Like like) {
        Podcast podcast = getById(podcastId);
        if(podcast == null) return null;
        
        like.setPodcastId(podcastId);
        Like realLike = likeService.add(like);
        if(realLike == null) return null;
        
        podcast.getLikes().put(realLike.getId(), realLike);
        return realLike;
    }
    
    public Like getLike(long podcastId, long likeId) {
        return getById(podcastId).getLikes().get(likeId);
    }
    
    public void removeLike(long podcastId, long likeId) {
        Podcast podcast = getById(podcastId);
        if(podcast == null) return;
        podcast.getLikes().remove(likeId);
        likeService.removeById(likeId);
    }
    
    public Like updateLike(long podcastId, long likeId, Like like) {
        Podcast podcast = getById(podcastId);
        if(podcast == null) return null;
        Like updated = likeService.update(likeId, like);
        podcast.getLikes().remove(likeId);
        podcast.getLikes().put(updated.getId(), updated);
        return updated;
    }
    
    
    public List<Like> getLikesByPodcastId(long podcastId) {
        Podcast podcast = getById(podcastId);
        if(podcast == null) return null;
        return podcast.getLikes().values().stream().collect(Collectors.toList());
    }
}
