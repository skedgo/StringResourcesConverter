package com.skedgo.tools.platform.android.rules

import com.skedgo.tools.rules.applyRule
import org.amshove.kluent.`should be equal to`
import org.junit.Test

class AddFriendlyPrefixRuleTest {

    @Test
    fun `should transform into a valid identifier`() {
        // Arrange.
        val string = "24"

        // Act.
        val updatedString = string.applyRule(AddFriendlyPrefixRule)

        // Assert.
        updatedString `should be equal to` "_24"
    }
}