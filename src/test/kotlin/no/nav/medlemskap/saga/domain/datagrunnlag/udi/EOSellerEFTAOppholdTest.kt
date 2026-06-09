package no.nav.medlemskap.saga.domain.datagrunnlag.udi

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class EOSellerEFTAOppholdTest {

    private val fom = LocalDate.of(2024, 1, 1)
    private val tom = LocalDate.of(2024, 12, 31)

    @Test
    fun `oppretter oppholdstillatelse periode med fom og tom satt`() {
        val opphold = EOSellerEFTAOpphold(
            periode = UdiPeriode(fom = fom, tom = tom),
            eosellerEFTAOppholdType = EOSellerEFTAOppholdType.EOS_ELLER_EFTA_BESLUTNING_OM_OPPHOLDSRETT,
            eosellerEFTAGrunnlagskategoriOppholdsrettType = null,
            eosellerEFTAGrunnlagskategoriOppholdstillatelseType = null
        )

        assertEquals(fom, opphold.periode.fom)
        assertEquals(tom, opphold.periode.tom)
    }

    @Test
    fun `oppretter oppholdstillatelse periode med fom satt men uten tom`() {
        val opphold = EOSellerEFTAOpphold(
            periode = UdiPeriode(fom = fom, tom = null),
            eosellerEFTAOppholdType = EOSellerEFTAOppholdType.EOS_ELLER_EFTA_BESLUTNING_OM_OPPHOLDSRETT,
            eosellerEFTAGrunnlagskategoriOppholdsrettType = null,
            eosellerEFTAGrunnlagskategoriOppholdstillatelseType = null
        )

        assertEquals(fom, opphold.periode.fom)
        assertNull(opphold.periode.tom)
    }

    @Test
    fun `oppretter oppholdstillatelse periode uten fom men med tom`() {
        val opphold = EOSellerEFTAOpphold(
            periode = UdiPeriode(fom = null, tom = tom),
            eosellerEFTAOppholdType = EOSellerEFTAOppholdType.EOS_ELLER_EFTA_BESLUTNING_OM_OPPHOLDSRETT,
            eosellerEFTAGrunnlagskategoriOppholdsrettType = null,
            eosellerEFTAGrunnlagskategoriOppholdstillatelseType = null
        )

        assertNull(opphold.periode.fom)
        assertEquals(tom, opphold.periode.tom)
    }

    @Test
    fun `oppretter oppholdstillatelse periode uten fom og uten tom`() {
        val opphold = EOSellerEFTAOpphold(
            periode = UdiPeriode(fom = null, tom = null),
            eosellerEFTAOppholdType = EOSellerEFTAOppholdType.EOS_ELLER_EFTA_BESLUTNING_OM_OPPHOLDSRETT,
            eosellerEFTAGrunnlagskategoriOppholdsrettType = null,
            eosellerEFTAGrunnlagskategoriOppholdstillatelseType = null
        )

        assertNull(opphold.periode.fom)
        assertNull(opphold.periode.tom)
    }
}
