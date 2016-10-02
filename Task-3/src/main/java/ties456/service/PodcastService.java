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
    public boolean removeById(long id) {
        Podcast podcast = getById(id);
        if(podcast == null) return false;
        boolean ret = super.removeById(id);
        //Removes any Likes attached to podcast
        podcast.getLikes().keySet().forEach(likeService::removeById);
        return ret;
    }
    
    /**
     * Adds Like to Podcast
     * @param podcastId podcast id
     * @param like like, no nulls!
     * @return Like or null if Podcast not found
     */
    public Like addLikeToPodcast(long podcastId, Like like) {
        Podcast podcast = getById(podcastId);
        if(podcast == null) return null;
        
        like.setPodcastId(podcastId);
        Like realLike = likeService.add(like);
        if(realLike == null) return null;
        
        podcast.getLikes().put(realLike.getId(), realLike);
        return realLike;
    }
    
    /**
     * Gets Like from Podcast
     * @param podcastId podcast Id
     * @param likeId like id
     * @return Like or null if not found
     */
    public Like getLike(long podcastId, long likeId) {
        Podcast podcast = getById(podcastId);
        if(podcast == null) return null;
        return podcast.getLikes().get(likeId);
    }
    
    /**
     * Removes Like from Podcast
     * @param podcastId podcast id
     * @param likeId like id
     * @return true if actually removed something, else false
     */
    public boolean removeLike(long podcastId, long likeId) {
        Podcast podcast = getById(podcastId);
        if(podcast == null) return false;
        podcast.getLikes().remove(likeId);
        return likeService.removeById(likeId);
    }
    
    /**
     * Updates Like
     * @param podcastId podcast id
     * @param likeId like id
     * @param like like object
     * @return Updated Like or null if not found
     */
    public Like updateLike(long podcastId, long likeId, Like like) {
        Podcast podcast = getById(podcastId);
        if(podcast == null) return null;
        Like updated = likeService.update(likeId, like);
        if(updated == null) return null;
        podcast.getLikes().remove(likeId);
        podcast.getLikes().put(updated.getId(), updated);
        return updated;
    }
    
    /**
     * Gets Podcasts Likes
     * @param podcastId podcast id
     * @return List of Likes or null if not found
     */
    public List<Like> getLikesByPodcastId(long podcastId) {
        Podcast podcast = getById(podcastId);
        if(podcast == null) return null;
        return podcast.getLikes().values().stream().collect(Collectors.toList());
    }
    
    /**
     * Lists Podcasts with requested writer
     * @param caster casters 'name'
     * @return List of podcasts with the caster or null
     */
	public List<Podcast> getAll(String caster) {
		if(caster.isEmpty()) return getAll();
        return search(blog -> blog.getCaster().equals(caster));		
	}
    
    
    
}
