package com.skedgo.tools

import com.nhaarman.mockito_kotlin.mock
import com.skedgo.tools.platform.android.AndroidOutputStrategy
import com.skedgo.tools.platform.xliff.XLIFFInputStrategy
import com.skedgo.tools.rules.TransformationRule
import com.skedgo.tools.translations.TransUnit
import com.skedgo.tools.translations.Translations
import org.amshove.kluent.*
import org.apache.commons.io.IOUtils
import org.junit.Test
import rx.Observable
import rx.Single
import java.io.InputStream

class StrategiesTest {

    @Test
    fun `should translate xliff to android`() {
        // Arrange.
        val xliffStream = this.javaClass.classLoader
                .getResourceAsStream("es.xliff")
        val xmlExpected = this.javaClass.classLoader
                .getResourceAsStream("es.xml")
        val xmlStringExpected = IOUtils.toString(xmlExpected, "UTF-8")

        val inputStrategy = XLIFFInputStrategy()
        val outputStrategy = AndroidOutputStrategy()
        outputStrategy.addTimeGeneration = false

        // Act & Assert.
        val translationXml =
                inputStrategy.createInputValues(xliffStream)
                        .flatMap { outputStrategy.generateOutputWithRules(it) }
                        .test()
                        .assertNoErrors()
                        .assertValueCount(1)
                        .assertCompleted()
                        .onNextEvents[0]

        translationXml.replace("\t", "  ") `should be equal to` xmlStringExpected
    }

    @Test
    fun `should translate ios xliff to android`() {
        // Arrange.
        val xliffStream = this.javaClass.classLoader
                .getResourceAsStream("es_ios.xliff")
        val xmlExpected = this.javaClass.classLoader
                .getResourceAsStream("es_ios.xml")
        val xmlStringExpected = IOUtils.toString(xmlExpected, "UTF-8")

        val inputStrategy = XLIFFInputStrategy()
        val outputStrategy = AndroidOutputStrategy()
        outputStrategy.addTimeGeneration = false

        // Act & Assert.
        val translationXml =
                inputStrategy.createInputValues(xliffStream)
                        .flatMap { outputStrategy.generateOutputWithRules(it) }
                        .test()
                        .assertNoErrors()
                        .assertValueCount(1)
                        .assertCompleted()
                        .onNextEvents[0]

        translationXml.replace("\t", "  ") `should be equal to` xmlStringExpected
    }

    @Test
    fun `should translate multiple xliff files to android`() {
        // Arrange.
        val xliffStreamES = this.javaClass.classLoader
                .getResourceAsStream("es.xliff")
        val xmlExpectedES = this.javaClass.classLoader
                .getResourceAsStream("es.xml")
        val xmlStringExpectedES = IOUtils.toString(xmlExpectedES, "UTF-8")
        val xliffStreamEN = this.javaClass.classLoader
                .getResourceAsStream("en.xliff")
        val xmlExpectedEN = this.javaClass.classLoader
                .getResourceAsStream("en.xml")
        val xmlStringExpectedEN = IOUtils.toString(xmlExpectedEN, "UTF-8")

        val inputStrategy = XLIFFInputStrategy()
        val outputStrategy = AndroidOutputStrategy()
        outputStrategy.addTimeGeneration = false

        // Act & Assert.
        val translationXml =
                Observable.from(listOf(xliffStreamES, xliffStreamEN))
                        .flatMapSingle { inputStrategy.createInputValues(it) }
                        .flatMapSingle { outputStrategy.generateOutputWithRules(it) }
                        .test()
                        .assertNoErrors()
                        .assertValueCount(2)
                        .assertCompleted()
                        .onNextEvents

        translationXml[0].replace("\t", "  ") `should be equal to` xmlStringExpectedES
        translationXml[1].replace("\t", "  ") `should be equal to` xmlStringExpectedEN
    }

    @Test
    fun `should add target rules dynamically`() {
        // Arrange.
        val inputStrategy: InputStringsStrategy = mock()
        val outputStrategy: OutputStringsStrategy = object : OutputStringsStrategy {
            override val rules = TransformationRules()

            override fun generateOutputText(translations: Translations): Single<String> =
                    Single.just(translations.transUnits.joinToString { it.target })

        }
        val transUnit = TransUnit(
                "one",
                "one",
                "uno",
                "Number 1")
        val translations = Translations(mutableListOf(transUnit))
        val inputStream: InputStream = mock()

        When calling inputStrategy.createInputValues(inputStream) `it returns` Single.just(translations)

        // Act & Assert.
        outputStrategy.rules.targetTransformationRules.add(TransformationRule.CustomRule(
                { it.toUpperCase() }
        ))
        val translationString =
                inputStrategy.createInputValues(inputStream)
                        .flatMap { outputStrategy.generateOutputWithRules(it) }
                        .test()
                        .assertNoErrors()
                        .assertValueCount(1)
                        .assertCompleted()
                        .onNextEvents[0]

        translationString `should be equal to` "UNO"
    }
}