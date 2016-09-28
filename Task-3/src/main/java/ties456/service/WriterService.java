package ties456.service;

import ties456.data.Writer;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
public class WriterService extends BaseService<Writer> {
    private static final WriterService instance = new WriterService();
    public static WriterService getInstance() {return instance;}
    private WriterService(){}
}
