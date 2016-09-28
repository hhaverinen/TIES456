package ties456.resource;

import ties456.data.TestData;
import ties456.service.TestDataService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
@Path("/resources")
public class TestResource {
    TestDataService service = TestDataService.getInstance();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TestData> get() {
        return service.getAll();
    }
    
    @GET
    @Path("/{testDataId}")
    @Produces(MediaType.APPLICATION_JSON)
    public TestData get(@PathParam("testDataId") long id) {
        return service.getById(id);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(TestData testData, @Context UriInfo context) {
        TestData data = service.add(testData);
        String hateoasUri = context.getBaseUriBuilder()
                .path(TestResource.class)
                .path(String.valueOf(data.getId()))
                .build().toString();
        
        data.addLink(hateoasUri, "self");
        
        URI uri = context.getAbsolutePathBuilder().path(String.valueOf(data.getId())).build();
        return Response.created(uri).entity(data).build();
    }
    
    @DELETE
    @Path("/{testDataId}")
    public void delete(@PathParam("testDataId") long id) {
        service.removeById(id);
    }
    
    @PUT
    @Path("/{testDataId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TestData update(@PathParam("testDataId") long id, TestData testData) {
        return service.update(id, testData);
    }
}
