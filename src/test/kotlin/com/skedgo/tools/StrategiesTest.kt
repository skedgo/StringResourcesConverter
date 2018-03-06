package com.skedgo.tools

import com.skedgo.tools.platform.android.AndroidOutputStrategy
import com.skedgo.tools.platform.xliff.XLIFFInputStrategy
import org.amshove.kluent.`should be equal to`
import org.apache.commons.io.IOUtils
import org.junit.Test
import rx.Observable

class StrategiesTest {

    @Test
    fun `should translate xliff to android`(){
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
                        .flatMap { outputStrategy.generateOutput(it) }
                .test()
                .assertNoErrors()
                .assertValueCount(1)
                .assertCompleted()
                .onNextEvents[0]

        translationXml.replace("\t", "  ") `should be equal to` xmlStringExpected
    }

    @Test
    fun `should translate multiple xliff files to android`(){
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
                        .flatMapSingle { outputStrategy.generateOutput(it) }
                .test()
                .assertNoErrors()
                .assertValueCount(2)
                .assertCompleted()
                .onNextEvents

        translationXml[0].replace("\t", "  ") `should be equal to` xmlStringExpectedES
        translationXml[1].replace("\t", "  ") `should be equal to` xmlStringExpectedEN
    }
}