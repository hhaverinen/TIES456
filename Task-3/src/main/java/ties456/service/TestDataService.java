package ties456.service;

import ties456.data.TestData;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
public class TestDataService {
    private Map<Long, TestData> resMap;
    
    public TestDataService() {
        resMap = new HashMap<>();
        TestData test1 = new TestData();
        test1.setId(1);
        test1.setName("Testi 1");
        test1.setGroup("TestiRyhmanen");
        test1.setUpdated(new Date());
    
        TestData test2 = new TestData();
        test2.setId(2);
        test2.setName("Testi 2");
        test2.setGroup("TestiRyhmanen");
        test2.setUpdated(new Date());
        
        resMap.put(1L, test1);
        resMap.put(2L, test2);
    }
    
    public List<TestData> getAllTestData() {
        return resMap.values().stream().collect(Collectors.toList());   
    }
    
    public TestData getTestDataById(long id) {
        return resMap.get(id);
    }
}
