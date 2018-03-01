package com.skedgo.tools.platform.android.rules

import com.skedgo.tools.rules.applyRule
import org.amshove.kluent.`should be equal to`
import org.junit.Test

class EscapeApostropheRuleTest {

    @Test
    fun `should transform into a valid identifier`() {
        // Arrange.
        val string = "'"

        // Act.
        val updatedString = string.applyRule(EscapeApostropheRule)

        // Assert.
        updatedString `should be equal to` "\\'"
    }
}