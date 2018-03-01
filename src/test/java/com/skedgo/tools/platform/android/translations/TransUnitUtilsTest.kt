package com.skedgo.tools.platform.android.translations

import com.skedgo.tools.rules.TransformationRule
import com.skedgo.tools.translations.TransUnit
import com.skedgo.tools.translations.cleanSource
import com.skedgo.tools.translations.cleanTarget
import org.amshove.kluent.`should be equal to`
import org.junit.Test

class TransUnitUtilsTest {

    @Test
    fun `should clean source with several rules`() {
        // Arrange.
        val transUnit = TransUnit("", "xxyyzz", "", "")
        val rules = listOf(TransformationRule.CustomRule({ string -> string.toUpperCase() }))

        // Act.
        transUnit.cleanSource(rules)

        // Assert.
        transUnit.source `should be equal to` "XXYYZZ"
    }

    @Test
    fun `should clean target with several rules`() {
        // Arrange.
        val transUnit = TransUnit("", "", "xxyyzz", "")
        val rules = listOf(TransformationRule.CustomRule({ string -> string.toUpperCase() }))

        // Act.
        transUnit.cleanTarget(rules)

        // Assert.
        transUnit.target `should be equal to` "XXYYZZ"
    }
}