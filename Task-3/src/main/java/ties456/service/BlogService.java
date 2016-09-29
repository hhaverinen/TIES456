package ties456.service;

import ties456.data.Blog;
import ties456.data.Comment;

import java.util.List;
import java.util.stream.Collectors;

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
    
    @Override
    public void removeById(long id) {
        Blog blog = getById(id);
        super.removeById(id);
        //Removes any comments attached to blog
        blog.getComments().keySet().forEach(commentService::removeById);
    }
    
    public Comment addCommentToBlog(long blogId, Comment comment) {
        Blog blog = getById(blogId);
        if(blog == null) return null;
        
        comment.setBlogId(blogId);
        Comment realComment = commentService.add(comment);
        if(realComment == null) return null;
        
        blog.getComments().put(realComment.getId(), realComment);
        return realComment; 
    }
    
    public Comment getComment(long blogId, long commentId) {
        return getById(blogId).getComments().get(commentId);
    }
    
    public void removeComment(long blogId, long commentId) {
        Blog blog = getById(blogId);
        if(blog == null) return;
        blog.getComments().remove(commentId);
        commentService.removeById(commentId);
    }
    
    public Comment updateComment(long blogId, long commentId, Comment comment) {
        Blog blog = getById(blogId);
        if(blog == null) return null;
        Comment updated = commentService.update(commentId, comment);
        blog.getComments().remove(commentId);
        blog.getComments().put(updated.getId(), updated);
        return updated;
    }
    
    public List<Comment> getCommentsByBlogId(long blogId) {
        Blog blog = getById(blogId);
        if(blog == null) return null;
        return blog.getComments().values().stream().collect(Collectors.toList());
    }
}
