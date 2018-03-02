package com.skedgo.tools.platform.android.rules

import com.skedgo.tools.rules.applyRule
import org.amshove.kluent.`should be equal to`
import org.junit.Test

class QuotesRuleTest {

    @Test
    fun `should transform into a valid identifier`() {
        // Arrange.
        val string = " some text "

        // Act.
        val updatedString = string.applyRule(QuotesRule)

        // Assert.
        updatedString `should be equal to` "\" some text \""
    }
}