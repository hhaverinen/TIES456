package ties456;

import ties456.data.TestData;
import ties456.service.TestDataService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
@Path("/resources")
public class TestRes {
    TestDataService service = TestDataService.getInstance();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TestData> get() {
        return service.getAllTestData();
    }
    
    @GET
    @Path("/{testDataId}")
    @Produces(MediaType.APPLICATION_JSON)
    public TestData get(@PathParam("testDataId") long id) {
        return service.getTestDataById(id);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TestData add(TestData testData) {
        return service.addTestData(testData);
    }
    
    @DELETE
    @Path("/{testDataId}")
    public void delete(@PathParam("testDataId") long id) {
        service.removeTestDataById(id);
    }
    
    @PUT
    @Path("/{testDataId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TestData update(@PathParam("testDataId") long id, TestData testData) {
        return service.updateTestDataById(id, testData);
    }
}
