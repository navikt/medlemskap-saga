package no.nav.medlemskap.saga.config

import com.natpryce.konfig.*
import mu.KotlinLogging
import java.io.File
import java.io.FileNotFoundException

private val logger = KotlinLogging.logger { }

private val defaultProperties = ConfigurationMap(
    mapOf(
        "AZURE_OPENID_CONFIG_TOKEN_ENDPOINT" to "https://login.microsoftonline.com/966ac572-f5b7-4bbe-aa88-c76419c0f851/oauth2/v2.0/token",
        "AZURE_APP_WELL_KNOWN_URL" to "https://login.microsoftonline.com/966ac572-f5b7-4bbe-aa88-c76419c0f851/v2.0/.well-known/openid-configuration",
        "AZURE_TENANT" to "966ac572-f5b7-4bbe-aa88-c76419c0f851",
        "AZURE_AUTHORITY_ENDPOINT" to "https://login.microsoftonline.com",
        "SERVICE_USER_USERNAME" to "srvmedlemskap",
        "SERVICE_USER_PASSWORD" to "Ks7NeOThPx9otSu",
        "AZURE_APP_TENANT_ID" to "966ac572-f5b7-4bbe-aa88-c76419c0f851",
        "NAIS_APP_NAME" to "",
        "NAIS_CLUSTER_NAME" to "localhost",
        "NAIS_APP_IMAGE" to "",
        "AZURE_APP_CLIENT_ID" to "39f402c0-7373-49e3-9e64-9669181f78d4",
        "AZURE_APP_CLIENT_SECRET" to "",
        "KAFKA_BROKERS" to "nav-dev-kafka-nav-dev.aivencloud.com:26484",
        "KAFKA_TRUSTSTORE_PATH" to "c:\\dev\\secrets\\client.truststore.jks",
        "KAFKA_CREDSTORE_PASSWORD" to "changeme",
        "KAFKA_KEYSTORE_PATH" to "c:\\dev\\secrets\\client.keystore.p12",
        "KAFKA_CREDSTORE_PASSWORD" to "changeme"
    )
)

private val config = ConfigurationProperties.systemProperties() overriding
    EnvironmentVariables overriding
    defaultProperties

private fun String.configProperty(): String = config[Key(this, stringType)]

private fun String.readFile() =
    try {
        logger.info { "Leser fra azure-fil $this" }
        File(this).readText(Charsets.UTF_8)
    } catch (err: FileNotFoundException) {
        logger.warn { "Azure fil ikke funnet" }
        null
    }

private fun hentCommitSha(image: String): String {
    val parts = image.split(":")
    if (parts.size == 1) return image
    return parts[1].substring(0, 7)
}

data class Configuration(
    val azureAd: AzureAd = AzureAd(),
    val kafkaConfig: KafkaConfig = KafkaConfig(),
    val cluster: String = "NAIS_CLUSTER_NAME".configProperty(),
    val commitSha: String = hentCommitSha("NAIS_APP_IMAGE".configProperty())
) {


    data class AzureAd(
        val clientId: String = "NAIS_APP_NAME".configProperty(),
        val jwtAudience: String = "AZURE_APP_CLIENT_ID".configProperty(),
        val tenant: String = "AZURE_APP_TENANT_ID".configProperty(),
        val authorityEndpoint: String = "AZURE_AUTHORITY_ENDPOINT".configProperty().removeSuffix("/")
    )

    data class KafkaConfig(
        val clientId: String = "NAIS_APP_NAME".configProperty(),
        val bootstrapServers: String = "KAFKA_BROKERS".configProperty(),
        val securityProtocol: String = "SSL",
        val trustStorePath: String = "KAFKA_TRUSTSTORE_PATH".configProperty(),
        val groupID: String = "medlemskap-saga",
        val trustStorePassword: String = "KAFKA_CREDSTORE_PASSWORD".configProperty(),
        val keystoreType: String = "PKCS12",
        val keystoreLocation: String = "KAFKA_KEYSTORE_PATH".configProperty(),
        val keystorePassword: String = "KAFKA_CREDSTORE_PASSWORD".configProperty(),
        val topic : String =  "medlemskap.medlemskap-vurdert"
    )
}
