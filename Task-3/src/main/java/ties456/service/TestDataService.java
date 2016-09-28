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
    private static TestDataService instance;
    public static TestDataService getInstance() {if(instance == null) instance = new TestDataService(); return instance;}
    
    private static long idGen = 3;
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
        
        resMap.put(test1.getId(), test1);
        resMap.put(test2.getId(), test2);
        
        //If we ever implement saving/loading, this will take care of the id
        resMap.values().stream().mapToLong(TestData::getId).max().ifPresent(max -> idGen = max + 1);
    }
    
    public List<TestData> getAllTestData() {
        return resMap.values().stream().collect(Collectors.toList());   
    }
    
    public TestData getTestDataById(long id) {
        return resMap.get(id);
    }
    
    public void removeTestDataById(long id) {
        resMap.remove(id);
    }
    
    public TestData updateTestDataById(long id, TestData newData) {
        TestData data = resMap.get(id);
        if(data == null) return null;
        data.setUpdated(newData.getUpdated());
        data.setGroup(newData.getGroup());
        data.setName(newData.getName());
        return data;
    }
    
    public TestData addTestData(TestData testData) {
        testData.setId(idGen++);
        resMap.put(testData.getId(), testData);
        System.out.println("New Data: "+testData);
        return testData;
    }
}
