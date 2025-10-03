package no.nav.medlemskap.saga.kafka

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.time.delay
import mu.KotlinLogging
import no.nav.medlemskap.saga.config.Environment
import no.nav.medlemskap.saga.domain.medlemskapVurdertRecord
import no.nav.medlemskap.saga.kafka.config.KafkaConfig
import no.nav.medlemskap.saga.lytter.Metrics
import no.nav.medlemskap.saga.persistence.DataSourceBuilder
import no.nav.medlemskap.saga.persistence.PostgresMedlemskapVurdertRepository
import no.nav.medlemskap.saga.persistence.VurderingForAnalyseRepositoryImpl
import no.nav.medlemskap.saga.service.SagaService
import org.apache.kafka.clients.consumer.CommitFailedException
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration

class Consumer(
    environment: Environment,
    private val config: KafkaConfig = KafkaConfig(environment),
    private val service: SagaService = SagaService(
        PostgresMedlemskapVurdertRepository(DataSourceBuilder(environment).getDataSource()),
        VurderingForAnalyseRepositoryImpl(DataSourceBuilder(environment).getDataSource())
    ),
    private val consumer: KafkaConsumer<String, String> = config.createConsumer(),
) {
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

    fun flow(): Flow<List<medlemskapVurdertRecord>> =
        flow {
            while (true) {
                emit(pollMessages())
                delay(Duration.ofSeconds(5))
            }
        }.onEach {
            logger.debug { "received  :"+ it.size + "on topic "+config.topic }
            it.forEach { record -> service.handle(record) }
        }.onEach {
            try {
                consumer.commitSync()
            } catch (e: CommitFailedException) {
                logger.error { "Commit feilet med feilmeldingen: ${e.message}" }
            }
        }.onEach {
            Metrics.incProcessedTotal(it.count())
        }

}