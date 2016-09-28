package ties456.service;

import ties456.data.Blog;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
public class BlogService extends BaseService<Blog> {
    private static final BlogService instance = new BlogService();
    public static BlogService getInstance() {return instance;}
    private BlogService(){}
}
