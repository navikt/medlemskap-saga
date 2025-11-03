package no.nav.medlemskap.saga.generer_uttrekk

import no.nav.medlemskap.saga.domain.VurderingForAnalyseDAO
import no.nav.medlemskap.saga.domain.VurderingForAnalyseUttrekk
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.OppholdUtenforEOSTag
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.OppholdUtenforNorgeTag
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.OppholdstillatelseOppgittTag
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.UtfortArbeidUtenforNorgeTag
import no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput.formater
import no.nav.medlemskap.saga.jackson.JacksonParser
import java.time.format.DateTimeFormatter
import kotlin.Boolean
import kotlin.String

object VurderingMapper {

    fun tilVurderingForAnalyseUttrekk(vurderingForAnalyseDAO: VurderingForAnalyseDAO): VurderingForAnalyseUttrekk {

        val utførtArbeidUtenforNorge: UtfortArbeidUtenforNorgeTag? =
            JacksonParser.fraJsonTilDomene(vurderingForAnalyseDAO.utfoert_arbeid_utenfor_norge)
        val oppholdUtenforEØS: OppholdUtenforEOSTag? =
            JacksonParser.fraJsonTilDomene(vurderingForAnalyseDAO.opphold_utenfor_eos)
        val oppholdUtenforNorge: OppholdUtenforNorgeTag? =
            JacksonParser.fraJsonTilDomene(vurderingForAnalyseDAO.opphold_utenfor_norge)
        val oppholdstillatelseOppgitt: OppholdstillatelseOppgittTag? =
            JacksonParser.fraJsonTilDomene(vurderingForAnalyseDAO.oppholdstillatelse_oppgitt)

        val datoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        return VurderingForAnalyseUttrekk(
            dato = vurderingForAnalyseDAO.dato.format(datoFormatter),
            ytelse = vurderingForAnalyseDAO.ytelse,
            fom = vurderingForAnalyseDAO.fom.format(datoFormatter),
            tom = vurderingForAnalyseDAO.tom.format(datoFormatter),
            førsteDagForYtelse = vurderingForAnalyseDAO.foerste_dag_for_ytelse.format(datoFormatter),
            startDatoForYtelse = vurderingForAnalyseDAO.start_dato_for_ytelse.format(datoFormatter),
            svar = vurderingForAnalyseDAO.svar,
            årsaker = vurderingForAnalyseDAO.aarsaker.formater(),
            konklusjon = vurderingForAnalyseDAO.konklusjon,
            avklaringsListe = vurderingForAnalyseDAO.avklaringsliste.formater(),
            nyeSpørsmål = vurderingForAnalyseDAO.nye_spoersmaal.formater(),
            antallDagerMedSykmelding = vurderingForAnalyseDAO.antall_dager_med_sykmelding.toString(),
            statsborgerskap = vurderingForAnalyseDAO.statsborgerskap.formater(),
            statsborgerskapskategori = vurderingForAnalyseDAO.statsborgerskapskategori,
            arbeidUtenforNorge = vurderingForAnalyseDAO.arbeid_utenfor_norge.formater(),
            utførtArbeidUtenforNorge = utførtArbeidUtenforNorge.formater(),
            utførtArbeidUtenforNorgeLand = utførtArbeidUtenforNorge?.utførtArbeidUtenforNorgeLand ?: "",
            utførtArbeidUtenforNorgeFom = utførtArbeidUtenforNorge?.utførtArbeidUtenforNorgeFom ?: "",
            utførtArbeidUtenforNorgeTom = utførtArbeidUtenforNorge?.utførtArbeidUtenforNorgeTom ?: "",
            utførtArbeidUtenforNorgeAntallPerioder = utførtArbeidUtenforNorge?.utførtArbeidUtenforNorgeAntallPerioder.formater(),
            oppholdUtenforEØS = oppholdUtenforEØS.formater(),
            oppholdUtenforEØSLand = oppholdUtenforEØS?.oppholdUtenforEØSLand ?: "",
            oppholdUtenforEØSFom = oppholdUtenforEØS?.oppholdUtenforEØSFom ?: "",
            oppholdUtenforEØSTom = oppholdUtenforEØS?.oppholdUtenforEØSTom ?: "",
            oppholdUtenforEØSAntallPerioder = oppholdUtenforEØS?.oppholdUtenforEØSAntallPerioder.formater(),
            oppholdUtenforEØSGrunn = oppholdUtenforEØS?.oppholdUtenforEØSGrunn ?: "",
            oppholdUtenforNorge = oppholdUtenforNorge.formater(),
            oppholdUtenforNorgeLand = oppholdUtenforNorge?.oppholdUtenforNorgeLand ?: "",
            oppholdUtenforNorgeFom = oppholdUtenforNorge?.oppholdUtenforNorgeFom ?: "",
            oppholdUtenforNorgeTom = oppholdUtenforNorge?.oppholdUtenforNorgeTom ?: "",
            oppholdUtenforNorgeAntallPerioder = oppholdUtenforNorge?.oppholdUtenforNorgeAntallPerioder.formater(),
            oppholdUtenforNorgeGrunn = oppholdUtenforNorge?.oppholdUtenforNorgeGrunn ?: "",
            oppholdstillatelseOppgitt = oppholdstillatelseOppgitt.formater(),
            oppholdstillatelseOppgittFom = oppholdstillatelseOppgitt?.oppholdstillatelseOppgittFom ?: "",
            oppholdstillatelseOppgittTom = oppholdstillatelseOppgitt?.oppholdstillatelseOppgittTom ?: "",
            oppholdstillatelseOppgittAntallPerioder = oppholdstillatelseOppgitt?.oppholdstillatelseOppgittAntallPerioder.formater(),
            oppholdstillatelseUDIFom = vurderingForAnalyseDAO.oppholdstillatelse_udi_fom?.format(datoFormatter) ?: "",
            oppholdstillatelseUDITom = vurderingForAnalyseDAO.oppholdstillatelse_udi_tom?.format(datoFormatter) ?: "",
            oppholdstillatelseUDIType = vurderingForAnalyseDAO.oppholdstillatelse_udi_type,
            kilde = vurderingForAnalyseDAO.kilde
        )
    }
}

private fun Array<String>.formater(): String {
    return joinToString(", ")
}

private fun Boolean.formater(): String {
    return when (this) {
        true -> "true"
        false -> "false"
        else -> ""
    }
}

private fun Int?.formater(): String {
    if (this == null || this == 0) return ""
    return this.toString()
}