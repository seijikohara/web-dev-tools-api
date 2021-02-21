package net.relaxism.devtools.resources

import io.quarkus.panache.common.Page
import net.relaxism.devtools.services.HtmlEntityService
import net.relaxism.devtools.valueobjects.Pagination
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/api/html-entities")
@Produces(MediaType.APPLICATION_JSON)
class HtmlEntitiesResource(val htmlEntityService: HtmlEntityService) {

    @GET
    fun getHtmlEntities(
        @QueryParam("name") @DefaultValue("") name: String,
        @QueryParam("page") @DefaultValue("0") page: Int,
        @QueryParam("size") @DefaultValue("50") size: Int,
    ): Pagination<Entity> {
        val paginationHtml = htmlEntityService.findByNameContaining(name, Page.of(page, size))

        val entities = paginationHtml.content.map { html ->
            Entity(html.name, html.code, html.code2, html.standard, html.dtd, html.description)
        }

        return Pagination(entities, paginationHtml.page, paginationHtml.pageSize, paginationHtml.totalElements)
    }

    data class Entity(
        val name: String,
        val code: Long,
        val code2: Long?,
        val standard: String?,
        val dtd: String?,
        val description: String?,
    ) {
        val entityReference: String
            get() = "&#${code};" + if (code2 != null) "&#${code2};" else ""
    }

}