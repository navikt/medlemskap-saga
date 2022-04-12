package no.nav.medlemskap.sykepenger.lytter.jakson


import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.medlemskap.saga.rest.FnrRequest
import no.nav.medlemskap.saga.rest.objectMapper
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class JaksonParserTest {

    @Test()
    fun `parse Json String`() {
        val fileContent = this::class.java.classLoader.getResource("sampleVurdering.json").readText(Charsets.UTF_8)
        val JsonNode = JaksonParser().parse(fileContent)
        assertNotNull(JsonNode)
    }

    @Test
    fun print(){
        println(objectMapper.writeValueAsString(FnrRequest("12344")))
    }

}