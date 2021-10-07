package no.nav.medlemskap.saga.persistence

import java.util.*

data class VurderingDao(val id: String, val soknadId: String, val date: Date, val json: String)
