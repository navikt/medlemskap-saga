package no.nav.kafkaproduser



import no.nav.medlemskap.saga.config.Configuration
import no.nav.medlemskap.saga.config.Environment
import no.nav.medlemskap.saga.config.EnvironmentKey
import no.nav.medlemskap.saga.kafka.config.KafkaConfig
import org.apache.kafka.clients.CommonClientConfigs

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.config.SslConfigs
import org.apache.kafka.common.security.auth.SecurityProtocol

import org.apache.kafka.common.serialization.StringSerializer

import java.util.*

import java.util.logging.Level
import java.util.logging.Logger

fun main(args: Array<String>) {
    SimpleProducer(KafkaConfig(System.getenv())).produce(10)
}

class SimpleProducer(brokers: KafkaConfig) {
    private val env: Environment = System.getenv()
    private val logger = Logger.getLogger("SimpleProducer")



    fun inst2Config() = mapOf(
        CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG to Configuration.KafkaConfig().bootstrapServers,
        //ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        CommonClientConfigs.CLIENT_ID_CONFIG to "Produser",//Configuration.KafkaConfig().clientId,
        //ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ProducerConfig.CLIENT_ID_CONFIG to Configuration.KafkaConfig().groupID,
        //ConsumerConfig.GROUP_ID_CONFIG to Configuration.KafkaConfig().groupID,
        //ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",

        CommonClientConfigs.SECURITY_PROTOCOL_CONFIG to SecurityProtocol.SASL_SSL.name,
        SaslConfigs.SASL_MECHANISM to "PLAIN",
        SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG to Configuration.KafkaConfig().keystoreLocation,
        SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG to Configuration.KafkaConfig().keystorePassword,
        CommonClientConfigs.SECURITY_PROTOCOL_CONFIG to Configuration.KafkaConfig().securityProtocol,
        //CommonClientConfigs.CLIENT_ID_CONFIG to Configuration.KafkaConfig().clientId,
        SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG to Configuration.KafkaConfig().trustStorePath,
        SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG to Configuration.KafkaConfig().keystorePassword,
        SslConfigs.SSL_KEYSTORE_TYPE_CONFIG to Configuration.KafkaConfig().keystoreType

    )
    fun inst2ConfigLocal() = mapOf(
        CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG to "localhost:29092",
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        CommonClientConfigs.CLIENT_ID_CONFIG to "Produser",//Configuration.KafkaConfig().clientId,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ProducerConfig.CLIENT_ID_CONFIG to Configuration.KafkaConfig().groupID,


    )
    private val producer = createProducer()
    private fun createProducer(): Producer<String, String> {

        if(EnvironmentKey.IS_LOCAL.equals("true")){
            return KafkaProducer<String, String>(inst2ConfigLocal())
        }
        else{
            return KafkaProducer<String, String>(inst2Config())
        }


    }

    fun produce(ratePerSecond: Int) {
        //val jsonString: String = File("./src/main/resources/sampleRequest.json").readText(Charsets.UTF_8)
        val fileContent = this::class.java.classLoader.getResource("sampleVurdering.json").readText(Charsets.UTF_8)
        logger.info(fileContent)
        val waitTimeBetweenIterationsMs = 1000L * ratePerSecond
        while(true) {
            logger.info("Producing $ratePerSecond records per second (1 every ${waitTimeBetweenIterationsMs}ms)")
            val fakeSoknadJson = fileContent
            //logger.log(Level.INFO, "JSON data: $fakeSoknadJson")
            val id = UUID.randomUUID().toString()
            val futureResult = producer.send(
                ProducerRecord(
                    Configuration.KafkaConfig().topic,
                    id, fakeSoknadJson
                )
            )

            logger.log(Level.INFO, "Sent a record with key $id ")

            Thread.sleep(waitTimeBetweenIterationsMs)

            // wait for the write acknowledgment
            futureResult.get()
        }
        }
    }