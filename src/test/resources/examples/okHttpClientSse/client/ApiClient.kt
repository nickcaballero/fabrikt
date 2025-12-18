package examples.sse.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import examples.sse.models.EnumQueryParam
import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.jvm.Throws

@Suppress("unused")
public class EventsClient(
    private val objectMapper: ObjectMapper,
    private val baseUrl: String,
    private val okHttpClient: OkHttpClient,
) {
    /**
     * GET example path 1
     *
     * @param explodeListQueryParam
     * @param queryParam2
     * @param intListQueryParam
     * @param xJsonEncodedHeader Json Encoded header
     * @param enumQueryParam
     * @param eventSourceListener Listener for Server-Sent Events
     */
    @Throws(ApiException::class)
    public fun getEvents(
        explodeListQueryParam: List<String>? = null,
        queryParam2: Int? = null,
        intListQueryParam: List<Int>? = null,
        xJsonEncodedHeader: String? = null,
        enumQueryParam: EnumQueryParam? = null,
        eventSourceListener: EventSourceListener,
        additionalHeaders: Map<String, String> = emptyMap(),
        additionalQueryParameters: Map<String, String> = emptyMap(),
    ): EventSource {
        val httpUrl: HttpUrl =
            "$baseUrl/events"
                .toHttpUrl()
                .newBuilder()
                .queryParam("explode_list_query_param", explodeListQueryParam, true)
                .queryParam("query_param2", queryParam2)
                .queryParam("int_list_query_param", intListQueryParam, true)
                .queryParam("enum_query_param", enumQueryParam)
                .also { builder -> additionalQueryParameters.forEach { builder.queryParam(it.key, it.value) } }
                .build()

        val headerBuilder =
            Headers
                .Builder()
                .`header`("X-Json-Encoded-Header", xJsonEncodedHeader)
        additionalHeaders.forEach { headerBuilder.header(it.key, it.value) }
        val httpHeaders: Headers = headerBuilder.build()

        val request: Request =
            Request
                .Builder()
                .url(httpUrl)
                .headers(httpHeaders)
                .get()
                .build()

        return EventSources.createFactory(okHttpClient).newEventSource(request, eventListener)
    }
}
