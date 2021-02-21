package net.relaxism.devtools.services

import io.quarkus.panache.common.Page
import net.relaxism.devtools.entities.Html
import net.relaxism.devtools.valueobjects.Pagination
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class HtmlEntityService {

    fun findByNameContaining(name: String, page: Page): Pagination<Html> {
        val count = Html.countByNameContaining(name)
        val entities = Html.findByNameContaining(name, page)
        return Pagination(entities, page.index, page.size, count.toInt())
    }

}