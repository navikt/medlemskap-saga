package no.nav.medlemskap.saga.utled_vurderingstagger

import no.nav.medlemskap.saga.domain.Svar
import no.nav.medlemskap.saga.domain.datagrunnlag.Ytelse
import no.nav.medlemskap.saga.domain.konklusjon.Statsborgerskapskategori
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate

class UtledVurderingstaggerITTest {

    @Test
    fun `skal mappe json til vurdering med riktige verdier`() {
        val path = Paths.get("src/test/resources/vurdering.json")
        val vurdering = Files.readString(path)
        val NAV_CALL_ID = "test-nav-call-id-123"
        val vurderingForAnalyse = UtledVurderingstagger.utled(vurdering, NAV_CALL_ID)

        val YTELSE = Ytelse.SYKEPENGER
        val FOM = LocalDate.parse("2025-09-10")
        val TOM = LocalDate.parse("2025-09-17")
        val SVAR = Svar.UAVKLART
        val ÅRSAKER = listOf("REGEL_10", "REGEL_3", "REGEL_25")
        val KONKLUSJON = Svar.UAVKLART
        val AVKLARINGSLISTE = listOf("SP6120", "REGEL_10", "REGEL_3", "REGEL_25")
        val ANTALL_DAGER_MED_SYKMELDING = 8L
        val STATSBORGERSKAPSKATEGORI =  Statsborgerskapskategori.NORSK_BORGER

        assertEquals(YTELSE, vurderingForAnalyse.ytelse)
        assertEquals(FOM, vurderingForAnalyse.fom)
        assertEquals(TOM, vurderingForAnalyse.tom)
        assertEquals(SVAR, vurderingForAnalyse.svar)
        assertEquals(ÅRSAKER, vurderingForAnalyse.årsaker)
        assertEquals(KONKLUSJON, vurderingForAnalyse.konklusjon)
        assertEquals(AVKLARINGSLISTE, vurderingForAnalyse.avklaringsListe)
        assertEquals(ANTALL_DAGER_MED_SYKMELDING, vurderingForAnalyse.antallDagerMedSykmelding)
        assertEquals(STATSBORGERSKAPSKATEGORI, vurderingForAnalyse.statsborgerskapskategori)
        assertEquals(NAV_CALL_ID, vurderingForAnalyse.navCallId)
    }
}