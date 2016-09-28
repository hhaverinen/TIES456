package ties456.service;

import ties456.data.TestData;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
public class TestDataService extends BaseService<TestData> {
    private static final TestDataService instance = new TestDataService();
    public static TestDataService getInstance() {return instance;}
    private TestDataService(){}
}
