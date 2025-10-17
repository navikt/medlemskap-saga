import no.nav.medlemskap.saga.utled_vurderingstagger.UttrekkForPeriode
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

class UttrekkForPeriodeTest {

    private val uttrekkForPeriode = UttrekkForPeriode()

    @Test
    fun `skal gi riktig første og siste dag for september 2025`() {
        val resultat = uttrekkForPeriode.hentUttrekkForDato("202509")

        assertEquals(LocalDate.of(2025, 9, 1), resultat.startDato)
        assertEquals(LocalDate.of(2025, 9, 30), resultat.sluttDato)
    }

    @Test
    fun `skal gi riktig første og siste dag for februar skuddår`() {
        val resultat = uttrekkForPeriode.hentUttrekkForDato("202402")

        assertEquals(LocalDate.of(2024, 2, 1), resultat.startDato)
        assertEquals(LocalDate.of(2024, 2, 29), resultat.sluttDato)
    }

    @Test
    fun `skal gi riktig første og siste dag for februar ikke-skuddår`() {
        val resultat = uttrekkForPeriode.hentUttrekkForDato("202502")

        assertEquals(LocalDate.of(2025, 2, 1), resultat.startDato)
        assertEquals(LocalDate.of(2025, 2, 28), resultat.sluttDato)
    }
}