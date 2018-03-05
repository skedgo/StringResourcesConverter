package com.skedgo.tools.platform.android.rules

import com.skedgo.tools.rules.applyRule
import org.amshove.kluent.`should be equal to`
import org.junit.Test

class AndroidKeywordRuleTest {

    @Test
    fun `should transform into a valid identifier`() {
        // Arrange.
        val string = "continue"

        // Act.
        val updatedString = string.applyRule(AndroidKeywordRule)

        // Assert.
        updatedString `should be equal to` "continue_"
    }
}