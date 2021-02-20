package net.relaxism.devtools.services

import net.relaxism.devtools.rest.client.GeoRestClient
import org.eclipse.microprofile.rest.client.inject.RestClient
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class GeoService constructor(@RestClient val geoRestClient: GeoRestClient) {

    fun getGeo(ipAddress: String): Map<String, Any?> {
        return geoRestClient.getGeo(ipAddress)
    }

}