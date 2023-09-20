package no.nav.medlemskap.saga.rest

import no.nav.medlemskap.saga.persistence.*

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
    fun RequestPeriodeErDirektePaaFolgendeSkalReturnereTrue(){
        val RequestPeridoe = Periode(LocalDate.parse("2022-01-20"),LocalDate.parse("2022-01-25"))
        val daoPeriode = Periode(LocalDate.parse("2022-01-15"),LocalDate.parse("2022-01-19"))
        Assertions.assertTrue(RequestPeridoe.erdirektePaaFolgende(daoPeriode))
    }

    @Test
    fun begynnerIPeriodenTest(){
        val RequestPeridoe = Periode(LocalDate.parse("2022-01-20"),LocalDate.parse("2022-01-29"))
        val daoPeriode = Periode(LocalDate.parse("2022-01-21"),LocalDate.parse("2022-01-28"))
        Assertions.assertFalse(RequestPeridoe.begynnerIPerioden(daoPeriode))
    }
    @Test
    fun PerioderSomStarterMindreEn16DagerFørPeriodeSkalRetunereTrue(){
        val RequestPeridoe = Periode(LocalDate.parse("2022-01-15"),LocalDate.parse("2022-01-29"))
        val daoPeriode = Periode(LocalDate.parse("2022-01-21"),LocalDate.parse("2022-01-29"))
        Assertions.assertTrue(RequestPeridoe.erInnenforEllerSammePeriodeMedDagerDiffFør(12,daoPeriode))
    }
    @Test
    fun RequestPeriodeSomErPaaLopendeDerPeriodeIDBErArbeidsTagegerPeriodeSkalReturnereArbeidsPeriode(){
        val RequestPeridoe = Periode(LocalDate.parse("2022-09-19"),LocalDate.parse("2022-10-02"))
        val dao = VurderingDao("1",UUID.randomUUID().toString(),Date(),"{\"datagrunnlag\":{\"fnr\": \"08026644373\",\"periode\": {\"fom\": \"2022-09-08\",\"tom\": \"2022-09-18\"}}}")
        val result = filterVurderinger(listOf(dao),RequestPeridoe,"08026644373")
        Assertions.assertTrue(result.isPresent)

    }
    @Test
    fun direktePaaFolgendeRequestPeriodeSkalReturnereDAOPeriode(){
        val RequestPeridoe = Periode(LocalDate.parse("2022-10-06"),LocalDate.parse("2022-10-17"))
        val dao = VurderingDao("1",UUID.randomUUID().toString(),Date(),"{\"datagrunnlag\":{\"fnr\": \"08026644373\",\"periode\": {\"fom\": \"2022-10-01\",\"tom\": \"2022-10-05\"}}}")
        val result = filterVurderinger(listOf(dao),RequestPeridoe,"08026644373")
        Assertions.assertTrue(result.isPresent)
    }
    @Test
    fun skalFinnePerioderMedStartDatoMindreEn21DagerFørSoknadsPeriode(){
        val RequestPeridoe = Periode(LocalDate.parse("2022-10-10"),LocalDate.parse("2022-10-16"))
        val dao = VurderingDao("1",UUID.randomUUID().toString(),Date(),"{\"datagrunnlag\":{\"fnr\": \"08026644373\",\"periode\": {\"fom\": \"2022-09-23\",\"tom\": \"2022-10-02\"}}}")
        val result = filterVurderinger(listOf(dao),RequestPeridoe,"08026644373")
        Assertions.assertTrue(result.isPresent)
    }
    @Test
    fun feilsøkProduksjon(){
        val RequestPeridoe = Periode(LocalDate.parse("2023-08-01"),LocalDate.parse("2023-09-11"))
        val dao = VurderingDao("1",UUID.randomUUID().toString(),Date(),"{\"datagrunnlag\":{\"fnr\": \"08026644373\",\"periode\": {\"fom\": \"2023-08-21\",\"tom\": \"2023-09-11\"}}}")
        val result = filterVurderinger(listOf(dao),RequestPeridoe,"08026644373")
        Assertions.assertTrue(result.isPresent)
    }

}
