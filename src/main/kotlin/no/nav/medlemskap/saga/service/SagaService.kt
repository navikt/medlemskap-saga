package no.nav.medlemskap.saga.service


import mu.KotlinLogging
import net.logstash.logback.argument.StructuredArguments.kv
import no.nav.medlemskap.saga.domain.medlemskapVurdertRecord
import no.nav.medlemskap.saga.persistence.MedlemskapVurdertRepository
import no.nav.medlemskap.saga.persistence.VurderingDao
import no.nav.medlemskap.saga.persistence.VurderingForAnalyseRepository
import no.nav.medlemskap.saga.rest.objectMapper
import no.nav.medlemskap.sykepenger.lytter.jakson.JacksonParser
import org.slf4j.MarkerFactory
import java.lang.Exception
import java.util.*

class SagaService(
    val medlemskapVurdertRepository: MedlemskapVurdertRepository,
    vurderingForAnalyseRepository: VurderingForAnalyseRepository
) {

    private val log = KotlinLogging.logger { }
    private val teamLogs = MarkerFactory.getMarker("TEAM_LOGS")
    private val analyseService = AnalyseService(vurderingForAnalyseRepository)

    fun handle(record: medlemskapVurdertRecord) {
        log.info(
            teamLogs,
            "Kafka: Leser melding fra medlemskap vurdert",
            kv("callId", record.key),
            kv("topic", record.topic),
            kv("partition", record.partition),
            kv("offset", record.offset)
        )

        if (validateRecord(record)){
            try {
                val ytelse = kotlin.runCatching { objectMapper.readTree(record.json).get("datagrunnlag").get("ytelse").asText() }.getOrElse { "UKJENT" }
                medlemskapVurdertRepository.lagreVurdering(record.key, Date(), record.json,ytelse)

                //Future: Vurdere å flytte denne prosessen til en egen kafka consumer
                analyseService.lagreTilVurderingForAnalyse(record.json, record.key)
            }
            catch (e:Exception){
                record.logLagringFeilet(e)
                log.error(
                    teamLogs,
                    "Feil oppstod ved lagring av medlemskap vurdert med feilmelding: {}", record.json
                )
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
            val node = JacksonParser.parse(record.json)
        }
        catch (e:Exception){
            return false
        }
        return true
    }


    private fun medlemskapVurdertRecord.logIkkeLagret() =
        log.warn(
            "Søknad ikke  lagret til lovme basert på validering ${key}, offsett: $offset, partiotion: $partition, topic: $topic",
            kv("callId", key),
        )
    private fun medlemskapVurdertRecord.logLagringFeilet(cause:Throwable) =
        log.error(
            "Lagring av medlemskapsvurdering feilet pga teknisk feil. Årsak  : ${cause.message}",
            kv("callId", key),
        )

    private fun medlemskapVurdertRecord.logSLagret() =
        log.info(
            "Søknad lagret til Lovme - sykmeldingId:Søknad lagret til Lovme - sykmeldingId: ${key}, offsett: $offset, partiotion: $partition, topic: $topic",
           kv("callId", key),
        )


}
