package com.skedgo.tools.platform.android.rules

import com.skedgo.tools.rules.applyRule
import org.amshove.kluent.`should be equal to`
import org.junit.Test

class ValidAndroidIdentifierRuleTest {

    @Test
    fun `should transform into a valid identifier`() {
        // Arrange.
        val string = " \n\".!%s?\'’/,(){}&amp;PERCAT-<>@=%₂:\\n…*"

        // Act.
        val updatedString = string.applyRule(ValidAndroidIdentifierRule)

        // Assert.
        updatedString `should be equal to` "___DOT_EXCLAMnps_QUESTION" +
                "_APOST_APOST_SLASH_COMA_START_PARENT_END_PARENT_START" +
                "_QBRAQUET_END_QBRAQUET_AMPERSAND_pattern_MINUS_LESST_MORET_AT_" +
                "EQUAL_PERC_2_2POINTS__DOT_DOT_DOTASTERISK"
    }
}