package no.nav.medlemskap.saga.kafka

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.time.delay
import mu.KotlinLogging
import no.nav.medlemskap.saga.config.Configuration
import no.nav.medlemskap.saga.config.Environment
import no.nav.medlemskap.saga.domain.medlemskapVurdertRecord
import no.nav.medlemskap.saga.kafka.config.KafkaConfig
import no.nav.medlemskap.saga.lytter.Metrics
import no.nav.medlemskap.saga.service.SagaService
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration

class Consumer(
    environment: Environment,
    private val config: KafkaConfig = KafkaConfig(environment),
    private val service: SagaService = SagaService(Configuration()),
    private val consumer: KafkaConsumer<String, String> = config.createConsumer(),
) {

    private val secureLogger = KotlinLogging.logger("tjenestekall")
    private val logger = KotlinLogging.logger { }
    init {
        consumer.subscribe(listOf(config.topic))
    }

    fun pollMessages(): List<medlemskapVurdertRecord> = //listOf("Message A","Message B","Message C")

        consumer.poll(Duration.ofSeconds(4))
            .map { medlemskapVurdertRecord(it.partition(),
                it.offset(),
                it.value(),
                it.key(),
                it.topic(),
                it.value())
            }
            .also {
                //Metrics.incReceivedTotal(it.count())
                //it.forEach { hendelse ->
                //    Metrics.incReceivedKilde(hendelse.kilde)
                //}
            }

    fun flow(): Flow<List<medlemskapVurdertRecord>> =
        flow {
            while (true) {
                emit(pollMessages())
                delay(Duration.ofSeconds(5))
            }
        }.onEach {
            logger.debug { "receiced :"+ it.size + "on topic "+config.topic }
            it.forEach { record -> service.handle(record) }
        }.onEach {
            consumer.commitAsync()
        }.onEach {
            Metrics.incProcessedTotal(it.count())
        }

}