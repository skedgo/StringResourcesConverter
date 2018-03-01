package com.skedgo.tools.platform.xliff

import org.amshove.kluent.`should be`
import org.amshove.kluent.`should equal`
import org.junit.Test

class XLIFFInputStrategyTest {

    @Test
    fun `should create a strings structure`() {
        // Arrange.
        val xliffStream = this.javaClass.classLoader
                .getResourceAsStream("es.xliff")

        // Act & Assert.
        val translation = XLIFFInputStrategy().createInputValues(xliffStream)
                .test()
                .assertNoErrors()
                .assertValueCount(1)
                .assertCompleted()
                .onNextEvents[0]

        // Assert.
        translation.transUnits.size `should be` 3
        translation.transUnits[0].id `should equal` "test"
        translation.transUnits[0].source `should equal` "Some text"
        translation.transUnits[0].target `should equal` "Algun texto"
        translation.transUnits[0].note `should equal` "Some text example"
        translation.transUnits[1].id `should equal` "Agenda"
        translation.transUnits[1].source `should equal` "Agenda"
        translation.transUnits[1].target `should equal` "Agenda"
        translation.transUnits[1].note `should equal` "Title for button to access agenda"
        translation.transUnits[2].id `should equal` "Alerts"
        translation.transUnits[2].source `should equal` "Alerts"
        translation.transUnits[2].target `should equal` "Alerts"
        translation.transUnits[2].note `should equal` "No comments..."
    }

    @Test
    fun `should create a strings structure with no comments`() {
        // Arrange.
        val xliffStream = this.javaClass.classLoader
                .getResourceAsStream("es_no_comments.xliff")

        // Act & Assert.
        val translation = XLIFFInputStrategy().createInputValues(xliffStream)
                .test()
                .assertNoErrors()
                .assertValueCount(1)
                .assertCompleted()
                .onNextEvents[0]

        // Assert.
        translation.transUnits.size `should be` 3
        translation.transUnits[0].id `should equal` "test"
        translation.transUnits[0].source `should equal` "Some text"
        translation.transUnits[0].target `should equal` "Algun texto"
        translation.transUnits[0].note `should equal` ""
        translation.transUnits[1].id `should equal` "Agenda"
        translation.transUnits[1].source `should equal` "Agenda"
        translation.transUnits[1].target `should equal` "Agenda"
        translation.transUnits[1].note `should equal` ""
        translation.transUnits[2].id `should equal` "Alerts"
        translation.transUnits[2].source `should equal` "Alerts"
        translation.transUnits[2].target `should equal` "Alerts"
        translation.transUnits[2].note `should equal` ""
    }

    @Test
    fun `should create a strings structure with extra spaces`() {
        // Arrange.
        val xliffStream = this.javaClass.classLoader
                .getResourceAsStream("es_with_extra_spaces.xliff")

        // Act & Assert.
        val translation = XLIFFInputStrategy().createInputValues(xliffStream)
                .test()
                .assertNoErrors()
                .assertValueCount(1)
                .assertCompleted()
                .onNextEvents[0]

        // Assert.
        translation.transUnits.size `should be` 3
        translation.transUnits[0].id `should equal` "test"
        translation.transUnits[0].source `should equal` "Some text"
        translation.transUnits[0].target `should equal` "Algun texto"
        translation.transUnits[0].note `should equal` "Some text example"
        translation.transUnits[1].id `should equal` "Agenda"
        translation.transUnits[1].source `should equal` "Agenda"
        translation.transUnits[1].target `should equal` "Agenda"
        translation.transUnits[1].note `should equal` "Title for button to access agenda"
        translation.transUnits[2].id `should equal` "Alerts"
        translation.transUnits[2].source `should equal` "Alerts"
        translation.transUnits[2].target `should equal` "Alerts"
        translation.transUnits[2].note `should equal` "No comments..."
    }
}