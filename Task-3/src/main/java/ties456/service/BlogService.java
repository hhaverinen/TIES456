package ties456.service;

import ties456.data.Blog;
import ties456.data.Comment;

import java.util.List;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
public class BlogService extends BaseService<Blog> {
    private static final BlogService instance = new BlogService();
    public static BlogService getInstance() {return instance;}
    private BlogService(){}
    
    CommentService commentService = CommentService.getInstance();
    
    public List<Blog> searchWithTitle(String titleSearchTerm) {
        if(titleSearchTerm == null || titleSearchTerm.isEmpty()) return getAll();
        return search(blog -> blog.getTitle().contains(titleSearchTerm));
    }
    
    public Comment addCommentToBlog(long blogId, Comment comment) {
        Blog blog = getById(blogId);
        if(blog == null) return null;
        
        comment.setBlogId(blogId);
        Comment realComment = commentService.add(comment);
        if(realComment == null) return null;
        
        blog.getComments().put(realComment.getId(), realComment);
        
        update(blog.getId(), blog);//Sets updated stamp to correct one
        
        return realComment; 
    }
    
    public Comment getComment(long blogId, long commentId) {
        return getById(blogId).getComments().get(commentId);
    }
}
