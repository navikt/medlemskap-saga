package no.nav.medlemskap.saga.domain

import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.UtfortArbeidUtenforNorge
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UtfortArbeidUtenforNorgeTest {

    @Test
    fun `når arbeid utenfor Norge ikke er oppgitt skal feltene være tomme`() {
        val utførtArbeidUtenforNorge = UtfortArbeidUtenforNorge(
            "",
            "",
            false,
            emptyList()
        )
        assertEquals("", utførtArbeidUtenforNorge.utførtArbeidUtenforNorgeFørsteLandetOppgitt())
        assertEquals("", utførtArbeidUtenforNorge.utførtArbeidUtenforNorgeFørsteOppgitteFom())
        assertEquals("", utførtArbeidUtenforNorge.utførtArbeidUtenforNorgeFørsteOppgitteTom())
        assertEquals(0, utførtArbeidUtenforNorge.utførtArbeidUtenforNorgeAntallPerioderOppgitt())
    }
}