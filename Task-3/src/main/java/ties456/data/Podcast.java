package ties456.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
@XmlRootElement
public class Podcast extends BaseData<Podcast> {
    private String caster, title, podcastUrl;
    
    private Map<Long, Like> likeMap = new HashMap<>();
    
    @Override
    public void updateData(Podcast update) {
        this.caster = update.caster;
        this.title = update.title;
        this.podcastUrl = update.podcastUrl;
    }
    
    public String getCaster() {
        return caster;
    }
    
    public void setCaster(String caster) {
        this.caster = caster;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getPodcastUrl() {
        return podcastUrl;
    }
    
    public void setPodcastUrl(String podcastUrl) {
        this.podcastUrl = podcastUrl;
    }
    
    @XmlTransient
    public Map<Long, Like> getLikes() {
        return likeMap;
    }
}
