package net.relaxism.devtools.resources

import net.relaxism.devtools.services.GeoService
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/api/geo/{ipAddress}")
@Produces(MediaType.APPLICATION_JSON)
class GeoResource(val geoService: GeoService) {

    @GET
    fun getGeo(@PathParam("ipAddress") ipAddress: String): Response {
        return Response(geoService.getGeo(ipAddress))
    }

    data class Response(val geo: Map<String, Any?>?)

}