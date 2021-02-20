package net.relaxism.devtools.entities

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Parameters
import javax.persistence.Entity

@Entity
class Html : PanacheEntity() {

    companion object : PanacheCompanion<Html> {

        fun countByNameContaining(name: String): Long = count("name like :name", Parameters.with("name", "%${name}%"))

        fun findByNameContaining(name: String, page: Page): List<Html> {
            val query = Html.find("name like :name", Parameters.with("name", "%${name}%"))
            query.page(page)
            return query.list()
        }

    }

    lateinit var name: String
    var code: Long = 0L
    var code2: Long? = null
    var standard: String? = null
    var dtd: String? = null
    var description: String? = null

}