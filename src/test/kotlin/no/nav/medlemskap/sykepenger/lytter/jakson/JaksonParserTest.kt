package no.nav.medlemskap.sykepenger.lytter.jakson


import no.nav.medlemskap.saga.jackson.JacksonParser
import no.nav.medlemskap.saga.persistence.Periode
import no.nav.medlemskap.saga.rest.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.time.LocalDate

class JaksonParserTest {

    @Test()
    fun `parse Json String`() {
        val fileContent = this::class.java.classLoader.getResource("sampleVurdering.json").readText(Charsets.UTF_8)
        val ytelse = kotlin.runCatching { objectMapper.readTree(fileContent).get("datagrunnlag").get("ytelse").asText() }.getOrElse { "UKJENT" }
        assertEquals("SYKEPENGER", ytelse, "")
        val jsonNode = JacksonParser.parse(fileContent)
        assertNotNull(jsonNode)
    }

    @Test
    fun print(){
        println(objectMapper.writeValueAsString(FnrRequest("12344")))
    }
    @Test
    fun printRequest(){
        val request = Request("1234", LocalDate.now(), InputPeriode(LocalDate.now(),LocalDate.now()),Ytelse.SYKEPENGER);
        println(objectMapper.writeValueAsString(
            request
        ))
        println(Periode(LocalDate.now(),LocalDate.now()))

    }

}