package no.nav.medlemskap.saga.jackson

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.treeToValue


object JacksonParser {
    fun parse(jsonString: String): JsonNode {
        val mapper = JsonMapper.builder()
            .findAndAddModules()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
            .build()
        return  mapper.readValue(jsonString)

    }

    inline fun <reified T> toDomainObject(jsonNode: JsonNode): T {
        val mapper = JsonMapper.builder()
            .findAndAddModules()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
            .build()
        return mapper.treeToValue(jsonNode)
    }


    inline fun <reified T> fraJsonTilDomene(json: String): T {
        val jsonNoden = parse(json)
        return toDomainObject(jsonNoden)
    }

}