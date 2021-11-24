package no.nav.medlemskap.saga.kafka.config

import no.nav.medlemskap.saga.config.Configuration
import no.nav.medlemskap.saga.config.Environment
import no.nav.medlemskap.saga.config.EnvironmentKey
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer

open class KafkaConfig(
    environment: Environment,
    private val securityStrategy: SecurityStrategy = PlainStrategy(environment = environment)
) {

    //private val schemaRegistry = environment.requireEnv(EnvironmentKey.SCHEMA_REGISTRY)
    val topic = Configuration.KafkaConfig().topic


    fun inst2Config() = mapOf(
        CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG to Configuration.KafkaConfig().bootstrapServers,
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        CommonClientConfigs.CLIENT_ID_CONFIG to Configuration.KafkaConfig().clientId,
        //ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to KafkaAvroDeserializer::class.java,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        //KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG to true,
        //KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG to schemaRegistry,
        ConsumerConfig.GROUP_ID_CONFIG to Configuration.KafkaConfig().groupID,
        //ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",
    ) + securityStrategy.securityConfig()

    fun inst2Configdev() = mapOf(
        CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG to "localhost:29092",
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        CommonClientConfigs.CLIENT_ID_CONFIG to Configuration.KafkaConfig().clientId,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        ConsumerConfig.GROUP_ID_CONFIG to Configuration.KafkaConfig().groupID,

    )


    fun createConsumer():KafkaConsumer<String, String>  {
        return if (EnvironmentKey.IS_LOCAL.equals("true"))
            KafkaConsumer<String, String>(inst2Configdev())
        else KafkaConsumer<String, String>(inst2Config())

    }

    interface SecurityStrategy {
        fun securityConfig(): Map<String, String>
    }
}