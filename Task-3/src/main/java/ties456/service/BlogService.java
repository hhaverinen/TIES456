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
    
    @Override
    public boolean removeById(long id) {
        Blog blog = getById(id);
        if(blog == null) return false;
        boolean ret = super.removeById(id);
        //Removes any comments attached to blogn
        blog.getComments().keySet().forEach(commentService::removeById);
        return ret;
    }
    
    /**
     * Searches Blogs based on title
     * @param titleSearchTerm string to search from titles
     * @return list of blogs with title containing the string
     */
    public List<Blog> searchWithTitle(String titleSearchTerm) {
        if(titleSearchTerm == null || titleSearchTerm.isEmpty()) return getAll();
        return search(blog -> blog.getTitle().contains(titleSearchTerm));
    }
    
    /**
     * Adds Comment given to Blog by blogs id
     * @param blogId blogs id
     * @param comment comment to add, no nulls!
     * @return added comment or null if no blog is found
     */
    public Comment addCommentToBlog(long blogId, Comment comment) {
        Blog blog = getById(blogId);
        if(blog == null) return null;
        
        comment.setBlogId(blogId);
        Comment realComment = commentService.add(comment);
        if(realComment == null) return null;
        
        blog.getComments().put(realComment.getId(), realComment);
        return realComment; 
    }
    
    /**
     * Gets Comment from Blog
     * @param blogId blog id
     * @param commentId comment id
     * @return Comment or null if not found
     */
    public Comment getComment(long blogId, long commentId) {
        Blog blog = getById(blogId);
        if(blog == null) return null;
        return blog.getComments().get(commentId);
    }
    
    /**
     * Removes Comment from blog
     * @param blogId blog id
     * @param commentId comment id
     * @return true if actually removed comment, otherwise false
     */
    public boolean removeComment(long blogId, long commentId) {
        Blog blog = getById(blogId);
        if(blog == null) return false;
        blog.getComments().remove(commentId);
        return commentService.removeById(commentId);
    }
    
    /**
     * Updates Comment with given Comment in given Blog
     * @param blogId blog id 
     * @param commentId comment id
     * @param comment new comment
     * @return Comment or null if Blog or Comment was not found
     */
    public Comment updateComment(long blogId, long commentId, Comment comment) {
        Blog blog = getById(blogId);
        if(blog == null) return null;
        Comment updated = commentService.update(commentId, comment);
        if(updated == null) return null;
        blog.getComments().remove(commentId);
        blog.getComments().put(updated.getId(), updated);
        return updated;
    }
    
    /**
     * Lists Blogs Comments
     * @param blogId blog id
     * @return List of Comments or null if not found
     */
    public List<Comment> getCommentsByBlogId(long blogId) {
        Blog blog = getById(blogId);
        if(blog == null) return null;
        return blog.getComments().values().stream().collect(Collectors.toList());
    }
    
    /**
     * Lists Blogs with requested writer
     * @param writer writers name
     * @return List of blogs with the writer or null
     */
	public List<Blog> getAll(String writer) {
		if(writer.isEmpty()) return getAll();
        return search(blog -> blog.getWriter().equals(writer));
	}
	
}
