package net.relaxism.devtools.services

import com.google.common.collect.ImmutableRangeMap
import com.google.common.collect.Range
import com.google.common.collect.RangeMap
import inet.ipaddr.IPAddress
import inet.ipaddr.IPAddressString
import net.relaxism.devtools.rest.client.RdapRestClient
import net.relaxism.devtools.utils.JsonUtils
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.rest.client.RestClientBuilder
import java.io.BufferedReader
import java.io.InputStream
import java.net.URI
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class RdapService @Inject constructor(
    @ConfigProperty(name = "application.rest.client.rdap.ipv4") val ipv4Json: String,
    @ConfigProperty(name = "application.rest.client.rdap.ipv6") val ipv6Json: String,
) {

    private val rdapFileManager: RdapFileManager

    init {
        val ipv4JsonURI = URI.create(ipv4Json)
        val ipv6JsonURI = URI.create(ipv6Json)
        rdapFileManager = RdapFileManager(ipv4JsonURI, ipv6JsonURI)
    }

    fun getRdap(ipAddress: String): Map<String, Any?> {
        val uri = rdapFileManager.getURIByIpAddress(ipAddress)
        val restClient = RestClientBuilder.newBuilder()
            .baseUri(uri)
            .build(RdapRestClient::class.java)
        return restClient.getRdap(ipAddress)
    }

}

@Suppress("UnstableApiUsage")
class RdapFileManager(ipv4RdapJsonURI: URI, ipv6RdapJsonURI: URI) {
    private val ipRangeMap: RangeMap<IPAddress, URI>
    private val defaultRdapURI: URI

    init {
        val ipv4RdapJson = inputStreamToString(ipv4RdapJsonURI.toURL().openStream())
        val ipv4RangeMap = resolveJson(ipv4RdapJson)
        val ipv6RdapJson = inputStreamToString(ipv6RdapJsonURI.toURL().openStream())
        val ipv6RangeMap = resolveJson(ipv6RdapJson)
        val allIPAddressRangeMap = ImmutableRangeMap.builder<IPAddress, URI>()
            .putAll(ipv4RangeMap)
            .putAll(ipv6RangeMap)
            .build()
        ipRangeMap = allIPAddressRangeMap
        defaultRdapURI = allIPAddressRangeMap.asMapOfRanges().values.distinct().sorted()[0]
    }

    private fun inputStreamToString(inputStream: InputStream): String {
        val reader = BufferedReader(inputStream.reader())
        reader.use { return it.readText() }
    }

    private fun resolveJson(json: String): RangeMap<IPAddress, URI> {
        val jsonMap = JsonUtils.fromJson(json)
        val services = jsonMap["services"] as List<List<List<String>>>

        val ipRangeMapBuilder = ImmutableRangeMap.builder<IPAddress, URI>()
        services.forEach { service ->
            val cidrList = service[0]
            val rdapURI = URI.create(service[1][0])
            cidrList.forEach { cidr ->
                val ipAddress = IPAddressString(cidr).address
                val lowerIPAddress = ipAddress.lower
                val upperIPAddress = ipAddress.upper
                ipRangeMapBuilder.put(Range.closed(lowerIPAddress, upperIPAddress), rdapURI)
            }
        }
        return ipRangeMapBuilder.build()
    }

    fun getURIByIpAddress(ipAddressString: String): URI {
        val ipAddress = IPAddressString(ipAddressString).address
        return ipRangeMap[ipAddress] ?: defaultRdapURI
    }

}