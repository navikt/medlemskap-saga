package no.nav.medlemskap.sykepenger.lytter.jakson

import com.fasterxml.jackson.databind.JsonNode
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.time.LocalDate


class JaksonParserTest {

    @Test
    fun `parse Json String`() {
        val fileContent = this::class.java.classLoader.getResource("sampleVurdering.json").readText(Charsets.UTF_8)
        val JsonNode = JaksonParser().parse(fileContent)
        assertNotNull(JsonNode)
    }

}