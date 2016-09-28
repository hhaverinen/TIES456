package ties456.data;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
public class Blog extends BaseData<Blog> {
    private String title, blogText;
    private Writer writer;
    
    
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
    
    public Writer getWriter() {
        return writer;
    }
    
    public void setWriter(Writer writer) {
        this.writer = writer;
    }
}
