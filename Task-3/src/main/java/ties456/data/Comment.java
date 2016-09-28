package ties456.data;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
public class Comment extends BaseData<Comment> {
    private String user, comment;
    
    
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
}
