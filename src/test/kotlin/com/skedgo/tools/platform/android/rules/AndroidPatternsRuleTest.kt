package com.skedgo.tools.platform.android.rules

import com.skedgo.tools.rules.applyRule
import org.amshove.kluent.`should be equal to`
import org.junit.Test

class AndroidPatternsRuleTest {

    @Test
    fun `should transform into a valid identifier`() {
        // Arrange.
        val string = "%ld%@%1$@%2$@"

        // Act.
        val updatedString = string.applyRule(AndroidPatternsRule)

        // Assert.
        updatedString `should be equal to` "PERCATPERCATPERCATPERCAT"
    }
}