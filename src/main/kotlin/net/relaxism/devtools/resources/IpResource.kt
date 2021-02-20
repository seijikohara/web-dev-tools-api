package net.relaxism.devtools.resources

import org.jboss.resteasy.spi.HttpRequest
import java.net.InetAddress
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType

@Path("/api/ip")
@Produces(MediaType.APPLICATION_JSON)
class IpResource {

    @GET
    fun getHttpHeaders(
        @Context httpHeaders: HttpHeaders,
        @Context httpRequest: HttpRequest

    ): Response {
        val xForwardedForHeaderValues = httpHeaders.getRequestHeader("X-Forwarded-For")
        val xForwardedForHeaderValue =
            if (xForwardedForHeaderValues.isEmpty()) null else xForwardedForHeaderValues.first()

        val remoteIpAddress = xForwardedForHeaderValue ?: httpRequest.remoteAddress

        val inetAddress = InetAddress.getByName(remoteIpAddress)

        val hostName = inetAddress.canonicalHostName
        return Response(remoteIpAddress, hostName)
    }

    data class Response(val ipAddress: String?, val hostName: String?)

}