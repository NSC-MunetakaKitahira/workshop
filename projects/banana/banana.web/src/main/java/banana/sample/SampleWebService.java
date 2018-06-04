package banana.sample;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("sample")
@Produces(MediaType.APPLICATION_JSON)
public class SampleWebService {

	// おまじない
	@Inject
	private SampleTransaction tran;
	
	@Path("write")
	@POST
	public void write(SampleWriteParameter param) {
		
		this.tran.write(param);
	}
	
	@Path("read/{name}")
	@GET
	public SampleReadResult read(@PathParam("name") String name) {
		
		return this.tran.read(name);
	}
}
