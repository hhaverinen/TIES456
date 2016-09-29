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
public class Blog extends BaseData<Blog> {
    private String writer, title, blogText;
    
    private Map<Long, Comment> commentMap = new HashMap<>();
  
    @Override
    public void updateData(Blog update) {
        this.title = update.title;
        this.blogText = update.blogText;
        this.writer = update.writer;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getBlogText() {
        return blogText;
    }
    
    public void setBlogText(String blogText) {
        this.blogText = blogText;
    }
    
    public String getWriter() {
        return writer;
    }
    
    public void setWriter(String writer) {
        this.writer = writer;
    }
    
    @XmlTransient
    public Map<Long, Comment> getComments() {
        return commentMap;
    }
}
