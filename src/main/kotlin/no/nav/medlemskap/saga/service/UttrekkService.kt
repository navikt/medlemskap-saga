package no.nav.medlemskap.saga.service

import no.nav.medlemskap.saga.persistence.UttrekkRepository

class UttrekkService(val uttrekkRepository: UttrekkRepository) {

    fun lagUttrekkForPeriode(årOgMåned: String): String {
        val uttrekksperiode = Uttrekksperiode(årOgMåned)
        return "Kall på repository for å hente uttrekk for periode"
    }

}
