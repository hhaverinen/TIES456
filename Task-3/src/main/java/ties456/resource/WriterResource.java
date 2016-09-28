package ties456.resource;

import ties456.data.Writer;
import ties456.service.WriterService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
@Path("/writers")
public class WriterResource {
    WriterService service = WriterService.getInstance();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Writer> get(){return service.getAll();}
}
