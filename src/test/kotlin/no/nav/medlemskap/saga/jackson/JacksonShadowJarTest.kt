package no.nav.medlemskap.saga.jackson

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.net.URLClassLoader
import java.nio.file.Path
import java.time.LocalDate

class JacksonShadowJarTest {

    @Test
    fun `skal utlede vurderingstagger med bare produksjonsjar paa classpath`() {
        val json = requireNotNull(javaClass.classLoader.getResource("vurdering.json")).readText()
        val jar = Path.of(requireNotNull(System.getProperty("saga.shadowJar")))

        URLClassLoader(arrayOf(jar.toUri().toURL()), ClassLoader.getPlatformClassLoader()).use { classLoader ->
            val thread = Thread.currentThread()
            val originalClassLoader = thread.contextClassLoader
            try {
                thread.contextClassLoader = classLoader
                val utleder = classLoader.loadClass("no.nav.medlemskap.saga.utled_vurderingstagger.UtledVurderingstagger")
                val vurdering = utleder.getMethod("utled", String::class.java, String::class.java)
                    .invoke(utleder.getField("INSTANCE").get(null), json, "test-call-id")

                assertEquals("SYKEPENGER", vurdering.javaClass.getMethod("getYtelse").invoke(vurdering).toString())
                assertEquals(LocalDate.parse("2025-09-10"), vurdering.javaClass.getMethod("getFom").invoke(vurdering))
                assertEquals("test-call-id", vurdering.javaClass.getMethod("getNavCallId").invoke(vurdering))
            } finally {
                thread.contextClassLoader = originalClassLoader
            }
        }
    }
}
