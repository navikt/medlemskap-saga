package no.nav.medlemskap.saga.domain

import no.nav.medlemskap.saga.domain.datagrunnlag.PdlPersonHistorikk
import no.nav.medlemskap.saga.domain.datagrunnlag.Statsborgerskap
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PdlPersonHistorikkTest {

    @Test
    fun `skal finne norge som aktivt statsborgerskap`() {
        val statsborgerskapListe = listOf(
            Statsborgerskap(landkode = "NOR", fom = null, tom = null, historisk = false)
        )
        val personHistorikk = PdlPersonHistorikk(statsborgerskap = statsborgerskapListe)
        val aktiveStatsborgerskap = personHistorikk.finnAktiveStatsborgerskap()
        assertTrue(aktiveStatsborgerskap.contains("NOR"))
    }

    @Test
    fun `skal finne to aktive statsborgerskap`() {
        val statsborgerskapListe = listOf(
            Statsborgerskap(landkode = "NOR", fom = null, tom = null, historisk = false),
            Statsborgerskap(landkode = "SWE", fom = null, tom = null, historisk = false)
        )
        val personHistorikk = PdlPersonHistorikk(statsborgerskap = statsborgerskapListe)
        val aktiveStatsborgerskap = personHistorikk.finnAktiveStatsborgerskap()
        assertEquals(2, aktiveStatsborgerskap.size)
        assertTrue(aktiveStatsborgerskap.containsAll(listOf("NOR", "SWE")))
    }


    @Test
    fun `skal finne kun ett aktivt statsborgerskap når en er historisk`() {
        val statsborgerskapListe = listOf(
            Statsborgerskap(landkode = "NOR", fom = null, tom = null, historisk = false),
            Statsborgerskap(landkode = "SWE", fom = null, tom = null, historisk = true)
        )
        val personHistorikk = PdlPersonHistorikk(statsborgerskap = statsborgerskapListe)
        val aktiveStatsborgerskap = personHistorikk.finnAktiveStatsborgerskap()
        assertEquals(1, aktiveStatsborgerskap.size)
        assertTrue(aktiveStatsborgerskap.contains("NOR"))
    }

    @Test
    fun `skal ikke finne aktive statsborgerskap når alle er historiske`() {
        val statsborgerskapListe = listOf(
            Statsborgerskap(landkode = "NOR", fom = null, tom = null, historisk = true),
            Statsborgerskap(landkode = "SWE", fom = null, tom = null, historisk = true)
        )
        val personHistorikk = PdlPersonHistorikk(statsborgerskap = statsborgerskapListe)
        val aktiveStatsborgerskap = personHistorikk.finnAktiveStatsborgerskap()
        assertTrue(aktiveStatsborgerskap.isEmpty())
    }
}