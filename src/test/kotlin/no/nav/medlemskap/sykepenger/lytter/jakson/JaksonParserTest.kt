package no.nav.medlemskap.sykepenger.lytter.jakson


import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class JaksonParserTest {

    //@Test()
    fun `parse Json String`() {
        val fileContent = this::class.java.classLoader.getResource("sampleVurdering.json").readText(Charsets.UTF_8)
        val JsonNode = JaksonParser().parse(fileContent)
        assertNotNull(JsonNode)
    }

}