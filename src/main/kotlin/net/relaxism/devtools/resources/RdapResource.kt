package net.relaxism.devtools.resources

import net.relaxism.devtools.services.RdapService
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/api/rdap/{ipAddress}")
@Produces(MediaType.APPLICATION_JSON)
class RdapResource(val rdapService: RdapService) {

    @GET
    fun getGeo(@PathParam("ipAddress") ipAddress: String): Response {
        return Response(rdapService.getRdap(ipAddress))
    }

    data class Response(val rdap: Map<String, Any?>?)

}