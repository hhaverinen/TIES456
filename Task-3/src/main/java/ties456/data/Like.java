package ties456.data;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
public class Like extends BaseData<Like> {
    private long podcastId;
    private String user;
    private int score;
    
    
    @Override
    public void updateData(Like update) {
        this.user = update.user;
        this.score = update.score;
    }
    
    public long getPodcastId() {
        return podcastId;
    }
    
    public void setPodcastId(long podcastId) {
        this.podcastId = podcastId;
    }
    
    public String getUser() {
        return user;
    }
    
    public void setUser(String user) {
        this.user = user;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
}
