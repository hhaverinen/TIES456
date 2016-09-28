package ties456.service;

import ties456.data.Comment;

import java.util.List;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
public class CommentService extends BaseService<Comment> {
    private static final CommentService instance = new CommentService();
    public static CommentService getInstance() {return instance;}
    private CommentService(){}
    
    public List<Comment> getCommentsByBlogId(long blogId) {
        return search(comment -> comment.getBlogId() == blogId);
    }
}
