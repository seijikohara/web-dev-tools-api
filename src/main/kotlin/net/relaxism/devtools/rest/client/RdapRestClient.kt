package net.relaxism.devtools.rest.client

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RegisterRestClient
interface RdapRestClient {

    @GET
    @Path("/ip/{ipAddress}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getRdap(@PathParam("ipAddress") ipAddress: String): Map<String, Any?>

}