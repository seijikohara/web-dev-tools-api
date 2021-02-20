package net.relaxism.devtools.resources

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType

@Path("/api/http-headers")
@Produces(MediaType.APPLICATION_JSON)
class HttpHeadersResource {

    @GET
    fun getHttpHeaders(@Context httpHeaders: HttpHeaders): Response {
        val headers = httpHeaders.requestHeaders.flatMap { headerEntry ->
            headerEntry.value.map { headerValue -> Response.Header(headerEntry.key, headerValue) }
        }
        return Response(headers)
    }

    data class Response(val headers: List<Header>) {
        data class Header(
            val name: String,
            val value: String
        )
    }

}