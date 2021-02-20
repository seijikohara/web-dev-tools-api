package net.relaxism.devtools.resources

import io.quarkus.panache.common.Page
import net.relaxism.devtools.entities.Html
import net.relaxism.devtools.services.HtmlEntityService
import net.relaxism.devtools.valueobjects.Pagination
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType

@Path("/api/html-entities")
@Produces(MediaType.APPLICATION_JSON)
class HtmlEntitiesResource(val htmlEntityService: HtmlEntityService) {

    @GET
    fun getHtmlEntities(
        @QueryParam("name") name: String,
        @QueryParam("page") page: Int,
        @QueryParam("size") size: Int,
    ): Pagination<Html> = htmlEntityService.findByNameContaining(name, Page.of(page - 1, size))

}