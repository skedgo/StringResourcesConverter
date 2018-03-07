package com.skedgo.tools

import com.skedgo.tools.rules.TransformationRule
import com.skedgo.tools.translations.TransUnit
import com.skedgo.tools.translations.Translations
import org.amshove.kluent.`should be equal to`
import org.junit.Test

class TransformationRulesTest {

    @Test
    fun `should apply target rules`() {
        // Arrange.
        val rules = TransformationRules()
        rules.targetTransformationRules.add(TransformationRule.CustomRule(
                { it.toUpperCase() }
        ))

        val transUnit = TransUnit(
                "one",
                "one",
                "uno",
                "Number 1")

        // Act & Assert.
        val translated = rules.applyRules(Translations(mutableListOf(transUnit)))
                .test()
                .onNextEvents[0]

        translated.transUnits[0].target `should be equal to` "UNO"
    }
}