package examples.sse.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import examples.sse.models.EnumQueryParam
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import okhttp3.OkHttpClient
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.jvm.Throws

/**
 * The circuit breaker registry should have the proper configuration to correctly action on circuit
 * breaker transitions based on the client exceptions [ApiClientException], [ApiServerException] and
 * [IOException].
 *
 * @see ApiClientException
 * @see ApiServerException
 */
@Suppress("unused")
public class EventsService(
    private val circuitBreakerRegistry: CircuitBreakerRegistry,
    objectMapper: ObjectMapper,
    baseUrl: String,
    okHttpClient: OkHttpClient,
) {
    public var circuitBreakerName: String = "eventsClient"

    private val apiClient: EventsClient = EventsClient(objectMapper, baseUrl, okHttpClient)

    @Throws(ApiException::class)
    public fun getEvents(
        explodeListQueryParam: List<String>? = null,
        queryParam2: Int? = null,
        intListQueryParam: List<Int>? = null,
        xJsonEncodedHeader: String? = null,
        enumQueryParam: EnumQueryParam? = null,
        eventSourceListener: EventSourceListener,
        additionalHeaders: Map<String, String> = emptyMap(),
    ): EventSource =
        withCircuitBreaker(circuitBreakerRegistry, circuitBreakerName) {
            apiClient.getEvents(
                explodeListQueryParam,
                queryParam2,
                intListQueryParam,
                xJsonEncodedHeader,
                enumQueryParam,
                eventSourceListener,
                additionalHeaders,
            )
        }
}
