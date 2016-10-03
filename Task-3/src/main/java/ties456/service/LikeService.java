package ties456.service;

import ties456.data.Like;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
public class LikeService extends BaseService<Like> {
    private static final LikeService instance = new LikeService();
    public static LikeService getInstance() {return instance;}
    private LikeService(){}
}
