package no.nav.medlemskap.saga.domain.datagrunnlag.brukerinput

data class OppholdUtenforNorgeTag(
    val oppholdUtenforNorge: Boolean,
    val oppholdUtenforNorgeLand: String,
    val oppholdUtenforNorgeFom: String,
    val oppholdUtenforNorgeTom: String,
    val oppholdUtenforNorgeAntallPerioder: Int,
    val oppholdUtenforNorgeGrunn: String
) {

    companion object {
        fun fra(oppholdUtenforNorge: OppholdUtenforNorge?): OppholdUtenforNorgeTag? {
            if (oppholdUtenforNorge == null) return null

            return OppholdUtenforNorgeTag(
                oppholdUtenforNorge.oppholdUtenforNorgeOppgitt(),
                oppholdUtenforNorge.oppholdUtenforNorgeFørsteOppgitteLand(),
                oppholdUtenforNorge.oppholdUtenforNorgeFørsteOppgitteFom(),
                oppholdUtenforNorge.oppholdUtenforNorgeFørsteOppgitteTom(),
                oppholdUtenforNorge.oppholdUtenforNorgeAntallPerioderOppgitt(),
                oppholdUtenforNorge.oppholdUtenforNorgeFørsteOppgitteGrunn()
            )
        }
    }
}
