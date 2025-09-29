package no.nav.medlemskap.saga.utled_vurderingstagger

import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

class UtledVurderingstaggerTest {
    @Test
    fun `should parse json and return VurderingForAnalyse`() {
        val path = Paths.get("src/test/resources/vurdering.json")
        val json = Files.readString(path)
        val utleder = UtledVurderingstagger()
        val result = utleder.utled(json)
    }

    @Test
    fun `should parse json and return VurderingForAnalysee`() {
        val path = Paths.get("src/test/resources/vurdering_OppholdUtenforEOS.json")
        val json = Files.readString(path)
        val utleder = UtledVurderingstagger()
        val result = utleder.utled(json)
    }

    @Test
    fun `should parse json and return VurderingForAnalyseee`() {
        val path = Paths.get("src/test/resources/vurdering_Oppholdstillatelse.json")
        val json = Files.readString(path)
        val utleder = UtledVurderingstagger()
        val result = utleder.utled(json)
    }
}