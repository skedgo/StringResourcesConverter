package com.skedgo.tools.platform.android.rules

import com.skedgo.tools.rules.applyRule
import org.amshove.kluent.`should be equal to`
import org.junit.Test

class AndroidPatternsListRuleTest {

    @Test
    fun `should transform into a valid identifier`() {
        // Arrange.
        val string = " PERCAT PERCAT PERCAT PERCAT"

        // Act.
        val updatedString = string.applyRule(AndroidPatternsListRule)

        // Assert.
        updatedString `should be equal to` " %1\$s %2\$s %3\$s %4\$s"
    }
}