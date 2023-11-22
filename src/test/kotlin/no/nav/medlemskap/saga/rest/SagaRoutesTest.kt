package no.nav.medlemskap.saga.rest

import no.nav.medlemskap.saga.persistence.VurderingDao
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import java.util.*

class SagaRoutesTest {

    @Test
    fun SkalMappeFlexResponsFraKonklusjonsElement(){
        val fileContent = this::class.java.classLoader.getResource("SampleResponsMedHaleJaKonklusjon.json").readText(Charsets.UTF_8)
        val soknadsID = UUID.randomUUID().toString()
        val dbId = "1234"
        val dao = VurderingDao(dbId,soknadsID, Date(),fileContent)

        val flexResponse = mapToFlexVurderingsRespons(dao)
        Assertions.assertEquals(dbId,flexResponse.vurdering_id)
        Assertions.assertEquals(soknadsID,flexResponse.sykepengesoknad_id)
        Assertions.assertEquals("JA",flexResponse.status)
    }
    @Test
    fun SkalMappeFlexResponsFraResultatMedGammelModell(){
        val fileContent = this::class.java.classLoader.getResource("sampleVurdering.json").readText(Charsets.UTF_8)
        val soknadsID = UUID.randomUUID().toString()
        val dbId = "1234"
        val dao = VurderingDao(dbId,soknadsID, Date(),fileContent)

        val flexResponse = mapToFlexVurderingsRespons(dao)
        Assertions.assertEquals(dbId,flexResponse.vurdering_id)
        Assertions.assertEquals(soknadsID,flexResponse.sykepengesoknad_id)
        Assertions.assertEquals("JA",flexResponse.status)
    }
}