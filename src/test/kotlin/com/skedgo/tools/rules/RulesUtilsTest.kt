package com.skedgo.tools.rules

import org.amshove.kluent.*
import org.junit.Test

class RulesUtilsTest {

    @Test
    fun `should apply rule replacements`() {
        // Arrange.
        val rule: TransformationRule.ReplacementRule = mock()
        When calling rule.replacements `it returns` listOf(
                Pair(PatternToReplace.StringPattern("a"),"x"),
                Pair(PatternToReplace.StringPattern("b"),"y"))

        val string = "aabbcc"

        // Act.
        val updatedString = string.applyRule(rule)

        // Assert.
        updatedString `should be equal to` "xxyycc"
    }
}