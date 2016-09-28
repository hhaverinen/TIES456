package ties456;

import ties456.data.TestData;
import ties456.service.TestDataService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
@Path("/resources")
public class TestRes {
    TestDataService service = new TestDataService();
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TestData> getResources() {
        return service.getAllTestData();
    }
    
    @GET
    @Path("/{testDataId}")
    @Produces(MediaType.APPLICATION_JSON)
    public TestData getTestData(@PathParam("testDataId") long id) {
        return service.getTestDataById(id);
    }
}
