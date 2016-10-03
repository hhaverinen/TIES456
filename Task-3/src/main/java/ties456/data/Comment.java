package ties456.data;

import ties456.service.BlogService;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
@XmlRootElement
public class Comment extends BaseData<Comment> {
    private String user, comment;
    private long blogId;
    
    
    @Override
    public void updateData(Comment update) {
        this.user = update.user;
        this.comment = update.comment;
    }
    
    public String getUser() {
        return user;
    }
    
    public void setUser(String user) {
        this.user = user;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public long getBlogId() {
        return blogId;
    }
    
    public void setBlogId(long blogId) {
        this.blogId = blogId;
    }
    
    @XmlTransient
    public Blog getBlog() {return BlogService.getInstance().getById(blogId);}
}
