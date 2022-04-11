package no.nav.medlemskap. saga.rest

import no.nav.medlemskap.saga.persistence.Periode
import no.nav.medlemskap.saga.persistence.VurderingDao
import no.nav.medlemskap.saga.persistence.begynnerIPerioden
import no.nav.medlemskap.saga.persistence.periode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

import java.util.*

class RestMappingTest {

    @Test
    fun mappingAvFnrResponsUtenAArsakTest(){
        val fileContent = this::class.java.classLoader.getResource("sampleVurdering.json").readText(Charsets.UTF_8)
        val dao = VurderingDao("1", UUID.randomUUID().toString(),Date(),fileContent)
        val response = mapToFnrResponse(dao)
        Assertions.assertEquals(dao.id,response.id)
        Assertions.assertEquals(dao.date,response.date)
        Assertions.assertEquals(dao.soknadId,response.soknadId)
        Assertions.assertEquals("JA",response.status)
        Assertions.assertNull(response.aarsaker)


    }
    @Test
    fun mappingAvFnrResponsMedAArsakTest(){
        val fileContent = this::class.java.classLoader.getResource("sampleVurdering_UAVKLART.json").readText(Charsets.UTF_8)
        val dao = VurderingDao("1", UUID.randomUUID().toString(),Date(),fileContent)
        val response = mapToFnrResponse(dao)
        Assertions.assertEquals(dao.id,response.id)
        Assertions.assertEquals(dao.date,response.date)
        Assertions.assertEquals(dao.soknadId,response.soknadId)
        Assertions.assertEquals("UAVKLART",response.status)
        Assertions.assertEquals(1,response.aarsaker?.size)
    }

    @Test
    fun RequestPeriodeIdentiskmedDaoPeriodeSkalFaaTrue(){
        val fileContent = this::class.java.classLoader.getResource("sampleVurdering_UAVKLART.json").readText(Charsets.UTF_8)
        val dao = VurderingDao("1", UUID.randomUUID().toString(),Date(),fileContent)
        Assertions.assertTrue(dao.periode().begynnerIPerioden(dao.periode()))
    }

    /*
    * Note test below. We choose to ignore validation of TOM dato!!!
    * */
    @Test
    fun RequestPeriodeMedFomDatoEtterDaoFomOgFomDatoFoerDaoTomSkalGiTrue(){
        val RequestPeridoe = Periode(LocalDate.parse("2022-01-25"),LocalDate.parse("2022-01-29"))
        val daoPeriode = Periode(LocalDate.parse("2022-01-21"),LocalDate.parse("2022-01-28"))
        Assertions.assertTrue(RequestPeridoe.begynnerIPerioden(daoPeriode))
    }

    @Test
    fun RequestPeriodeFomFoerDaoFomSkalIkkeFaaTrue(){
        val RequestPeridoe = Periode(LocalDate.parse("2022-01-20"),LocalDate.parse("2022-01-29"))
        val daoPeriode = Periode(LocalDate.parse("2022-01-21"),LocalDate.parse("2022-01-28"))
        Assertions.assertFalse(RequestPeridoe.begynnerIPerioden(daoPeriode))
    }
}
