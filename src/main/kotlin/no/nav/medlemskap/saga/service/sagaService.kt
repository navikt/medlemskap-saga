package no.nav.medlemskap.saga.service


import com.fasterxml.jackson.databind.JsonNode
import mu.KotlinLogging
import no.nav.medlemskap.saga.config.Configuration
import no.nav.medlemskap.saga.domain.medlemskapVurdertRecord
import no.nav.medlemskap.sykepenger.lytter.jakson.JaksonParser
import java.lang.Exception

class SagaService(configuration: Configuration) {

    companion object {
        private val log = KotlinLogging.logger { }

    }
    fun handle(record: medlemskapVurdertRecord) {
        if (validateRecord(record)){
            //TODO: lagre til database
            record.logSLagret()
        }
        else{
            record.logIkkeLagret()
        }

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
        SagaService.log.info(
            "Søknad ikke  lagret til lovme basert på validering ${key}, offsett: $offset, partiotion: $partition, topic: $topic",
            //kv("callId", key),
        )

    private fun medlemskapVurdertRecord.logSLagret() =
        SagaService.log.info(
            "Søknad lagret til Lovme - sykmeldingId: ${key}, offsett: $offset, partiotion: $partition, topic: $topic",
           //kv("callId", sykepengeSoknad.sykmeldingId),
        )


}
