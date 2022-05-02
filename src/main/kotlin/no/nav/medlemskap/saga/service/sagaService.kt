package no.nav.medlemskap.saga.service


import mu.KotlinLogging
import net.logstash.logback.argument.StructuredArguments.kv
import no.nav.medlemskap.saga.domain.medlemskapVurdertRecord
import no.nav.medlemskap.saga.persistence.MedlemskapVurdertRepository
import no.nav.medlemskap.saga.persistence.VurderingDao
import no.nav.medlemskap.sykepenger.lytter.jakson.JaksonParser
import java.lang.Exception
import java.util.*

class SagaService(val medlemskapVurdertRepository: MedlemskapVurdertRepository) {

    companion object {
        private val log = KotlinLogging.logger { }
    }
    fun handle(record: medlemskapVurdertRecord) {
        if (validateRecord(record)){
            try {
                medlemskapVurdertRepository.lagreVurdering(record.key, Date(), record.json)
            }
            catch (e:Exception){
                record.logLagringFeilet(e)
                log.error { record.json }
                throw e

            }
            record.logSLagret()
        }
        else{
            record.logIkkeLagret()
        }

    }
    fun finnAlleVurderingerForFnr(fnr:String) :List<VurderingDao>{
        val vurderinger = medlemskapVurdertRepository.finnVurderingMedFnr(fnr)
        return vurderinger
    }

    private fun validateRecord(record: medlemskapVurdertRecord) :Boolean{
        try{
            val node = JaksonParser().parse(record.json)
        }
        catch (e:Exception){
            return false
        }
        return true
    }


    private fun medlemskapVurdertRecord.logIkkeLagret() =
        SagaService.log.warn(
            "Søknad ikke  lagret til lovme basert på validering ${key}, offsett: $offset, partiotion: $partition, topic: $topic",
            kv("callId", key),
        )
    private fun medlemskapVurdertRecord.logLagringFeilet(cause:Throwable) =
        SagaService.log.error(
            "Lagring av medlemskapsvurdering feilet pga teknisk feil. Årsak  : ${cause.message}",
            kv("callId", key),
        )

    private fun medlemskapVurdertRecord.logSLagret() =
        SagaService.log.info(
            "Søknad lagret til Lovme - sykmeldingId:Søknad lagret til Lovme - sykmeldingId: ${key}, offsett: $offset, partiotion: $partition, topic: $topic",
           kv("callId", key),
        )


}
