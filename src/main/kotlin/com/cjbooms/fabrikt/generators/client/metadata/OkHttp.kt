package com.cjbooms.fabrikt.generators.client.metadata

import com.squareup.kotlinpoet.ClassName

object OkHttpImports {

    private object Packages {
        const val BASE = "okhttp3"
        const val SSE = "$BASE.sse"
    }

    val CLIENT = ClassName(Packages.BASE, "OkHttpClient")
    val EVENT_SOURCE = ClassName(Packages.SSE, "EventSource")
    val EVENT_SOURCE_LISTENER = ClassName(Packages.SSE, "EventSourceListener")
    val EVENT_SOURCES = ClassName(Packages.SSE, "EventSources")
    val HEADERS = ClassName(Packages.BASE, "Headers")
    val HTTP_URL = ClassName(Packages.BASE, "HttpUrl")
    val HTTP_URL_COMPANION = HTTP_URL.nestedClass("Companion")
    val MEDIA_TYPE = ClassName(Packages.BASE, "MediaType")
    val MEDIA_TYPE_COMPANION = MEDIA_TYPE.nestedClass("Companion")
    val REQUEST = ClassName(Packages.BASE, "Request")
    val REQUEST_BODY = ClassName(Packages.BASE, "RequestBody")
    val REQUEST_BODY_COMPANION = REQUEST_BODY.nestedClass("Companion")
}