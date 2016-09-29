package ties456.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
public abstract class BaseData<T extends BaseData> {
    private long id;
    private Date created, updated;
    private List<Link> links = new ArrayList<>();
    
    public void addLink(String url, String rel) {
        links.add(new Link(url, rel));
    }
    
    public List<Link> getLinks() {
        return links;
    }
    
    public void setLinks(List<Link> links) {
        this.links = links;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public Date getCreated() {
        return created;
    }
    
    public Date getUpdated() {
        return updated;
    }
    
    public void setUpdated(Date updated) {
        this.updated = updated;
    }
    
    public void setCreated(Date created) {
        this.created = created;
    }
    
    public abstract void updateData(T update);
    
    public static class Link {
        private String rel, href;
        
        //For Serialization
        public Link(){}
        
        public Link(String link, String rel) {
            this.href = link; this.rel = rel;
        }
    
        public String getHref() {
            return href;
        }
    
        public void setHref(String href) {
            this.href = href;
        }
    
        public String getRel() {
            return rel;
        }
    
        public void setRel(String rel) {
            this.rel = rel;
        }

    }
}
