package no.nav.medlemskap.saga.domain.datagrunnlag.udi

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class OppholdsTillatelseTest {

    @Test
    fun `oppholdstillatelsePaSammeVilkar - returnerer fom, tom og type`() {
        val oppholdsTillatelse = OppholdsTillatelse(
            gjeldendeOppholdsstatus = GjeldendeOppholdsstatus(
                oppholdstillatelsePaSammeVilkar = OppholdstillatelsePaSammeVilkar(
                    periode = UdiPeriode(
                        fom = LocalDate.of(2024, 1, 1),
                        tom = LocalDate.of(2024, 12, 31)
                    ),
                    type = "permanent"
                ),
                eosellerEFTAOpphold = null,
                uavklart = null,
                ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum = null
            )
        )

        assertEquals(LocalDate.of(2024, 1, 1), oppholdsTillatelse.hentOppholdstillatelseUDIFom())
        assertEquals(LocalDate.of(2024, 12, 31), oppholdsTillatelse.hentOppholdstillatelseUDITom())
        assertEquals("permanent", oppholdsTillatelse.hentOppholdstillatelseUDIType())
    }

    @Test
    fun `eosellerEFTAOpphold - returnerer fom, tom og type`() {
        val oppholdsTillatelse = OppholdsTillatelse(
            gjeldendeOppholdsstatus = GjeldendeOppholdsstatus(
                oppholdstillatelsePaSammeVilkar = null,
                eosellerEFTAOpphold = EOSellerEFTAOpphold(
                    periode = Periode(
                        fom = "2024-03-01",
                        tom = "2024-09-30"
                    ),
                    eosellerEFTAOppholdType = EOSellerEFTAOppholdType.EOS_ELLER_EFTA_BESLUTNING_OM_OPPHOLDSRETT,
                    eosellerEFTAGrunnlagskategoriOppholdsrettType = null,
                    eosellerEFTAGrunnlagskategoriOppholdstillatelseType = null
                ),
                uavklart = null,
                ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum = null
            )
        )

        assertEquals(LocalDate.of(2024, 3, 1), oppholdsTillatelse.hentOppholdstillatelseUDIFom())
        assertEquals(LocalDate.of(2024, 9, 30), oppholdsTillatelse.hentOppholdstillatelseUDITom())
        assertEquals("EOS_ELLER_EFTA_BESLUTNING_OM_OPPHOLDSRETT", oppholdsTillatelse.hentOppholdstillatelseUDIType())
    }

    @Test
    fun `uavklart - returnerer null for fom og tom, og uavklart som type`() {
        val oppholdsTillatelse = OppholdsTillatelse(
            gjeldendeOppholdsstatus = GjeldendeOppholdsstatus(
                oppholdstillatelsePaSammeVilkar = null,
                eosellerEFTAOpphold = null,
                uavklart = Uavklart(),
                ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum = null
            )
        )

        assertNull(oppholdsTillatelse.hentOppholdstillatelseUDIFom())
        assertNull(oppholdsTillatelse.hentOppholdstillatelseUDITom())
        assertEquals("uavklart", oppholdsTillatelse.hentOppholdstillatelseUDIType())
    }

    @Test
    fun `ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum - returnerer null for fom og tom, og ikke_opphold som type`() {
        val oppholdsTillatelse = OppholdsTillatelse(
            gjeldendeOppholdsstatus = GjeldendeOppholdsstatus(
                oppholdstillatelsePaSammeVilkar = null,
                eosellerEFTAOpphold = null,
                uavklart = null,
                ikkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum = IkkeOppholdstillatelseIkkeOppholdsPaSammeVilkarIkkeVisum(
                    utvistMedInnreiseForbud = null,
                    avslagEllerBortfallAvPOBOSellerTilbakekallEllerFormeltVedtak = null,
                    ovrigIkkeOpphold = null
                )
            )
        )

        assertNull(oppholdsTillatelse.hentOppholdstillatelseUDIFom())
        assertNull(oppholdsTillatelse.hentOppholdstillatelseUDITom())
        assertEquals("ikke_opphold", oppholdsTillatelse.hentOppholdstillatelseUDIType())
    }
}
