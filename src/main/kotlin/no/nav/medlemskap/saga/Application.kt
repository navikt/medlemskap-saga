package no.nav.medlemskap.saga

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import no.nav.medlemskap.saga.config.Environment
import no.nav.medlemskap.saga.kafka.Consumer
import no.nav.medlemskap.saga.nais.naisLiveness
import no.nav.medlemskap.saga.persistence.DataSourceBuilder

import org.slf4j.Logger
import org.slf4j.LoggerFactory


fun main() {
    Application().start()
}

class Application(private val env: Environment = System.getenv(), private val consumer: Consumer = Consumer(env)) {
    companion object {
        val log: Logger = LoggerFactory.getLogger(Application::class.java)
    }

    fun start() {
        log.info("Start application")
        val dataSourceBuilder = DataSourceBuilder(env)
        try {
            dataSourceBuilder.migrate()
        }
        catch (t:Throwable){
            log.warn("klarte ikke å kjøre migrerings skript. årsak : ${t.message}")
        }
        @OptIn(DelicateCoroutinesApi::class)
        val consumeJob = consumer.flow().launchIn(GlobalScope)

        naisLiveness(consumeJob).start(wait = true)
    }
}