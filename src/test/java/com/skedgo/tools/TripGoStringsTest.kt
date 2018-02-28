package com.skedgo.tools

import com.skedgo.tools.platform.android.AndroidOutputStrategy
import com.skedgo.tools.platform.xliff.XLIFFInputStrategy
import org.amshove.kluent.`should be equal to`
import org.apache.commons.io.IOUtils
import org.junit.Test

class TripGoStringsTest {

    @Test
    fun `should translate xliff to android`(){
        // Arrange.
        val xliffStream = this.javaClass.classLoader
                .getResourceAsStream("android_localizable_strings.xliff")
        val xmlExpected = this.javaClass.classLoader
                .getResourceAsStream("android_localizable_strings.xliff.xml")
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

        translationXml `should be equal to` xmlStringExpected
    }
}