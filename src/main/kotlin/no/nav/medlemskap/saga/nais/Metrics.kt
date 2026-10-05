package no.nav.medlemskap.saga.lytter

import io.micrometer.core.instrument.Counter
import io.micrometer.prometheusmetrics.PrometheusConfig
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry

object Metrics {
    val registry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)

    fun incReceivedTotal(count: Int = 1) =
        receivedTotal.increment(count.toDouble())

    fun incProcessedTotal(count: Int = 1) =
        processedTotal.increment(count.toDouble())

    fun incSuccessfulPenPosts(count: Int = 1) =
        successEventsHandled.increment(count.toDouble())

    private val receivedTotal = Counter.builder("medlemskap_saga_lytter_received")
        .description("Totalt mottatte saga meldinger")
        .register(registry)
    private val processedTotal = Counter.builder("medlemskap_saga_lytte_processed_counter")
        .description("Totalt prosesserte meldinger")
        .register(registry)
    private val successEventsHandled = Counter.builder("medlemskap_saga_lytte_successful_joark_posts_counter")
        .description("Vellykede meldinger behandlet")
        .register(registry)

}
