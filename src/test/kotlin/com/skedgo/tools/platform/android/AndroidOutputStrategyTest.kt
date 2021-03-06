package com.skedgo.tools.platform.android

import com.skedgo.tools.translations.TransUnit
import com.skedgo.tools.translations.Translations
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should start with`
import org.junit.Test
import rx.Observable

class AndroidOutputStrategyTest {

    private fun assertCorrectHeaderAndFooter(stringResource: String) {
        val lines = stringResource.split("\n")

        lines[0] `should be equal to` "<?xml version='1.0' encoding='UTF-8'?>"
        lines[1] `should be equal to` "<resources>"
        lines[lines.size - 2] `should be equal to` "</resources>"
        lines[lines.size - 1] `should start with` "<!-- This is an auto generated file "
    }

    @Test
    fun `should have right rules set`() {
        // Arrange.
        val inputStrategy = AndroidOutputStrategy()

        // Assert.
        inputStrategy.rules.sourceTransformationRules.size `should be equal to` 6
        inputStrategy.rules.targetTransformationRules.size `should be equal to` 5
    }

    @Test
    fun `should create empty strings resource`() {
        // Arrange.
        val translations = Translations(mutableListOf())

        // Act.
        val stringResource = AndroidOutputStrategy().generateOutputWithRules(translations)
                .test()
                .assertNoErrors()
                .assertCompleted()
                .onNextEvents[0]

        // Assert.
        assertCorrectHeaderAndFooter(stringResource)
    }

    @Test
    fun `should create strings resource with one clean translation`() {
        // Arrange.
        val transUnit = TransUnit(
                "one",
                "one",
                "uno",
                "Number 1")
        val translations = Translations(mutableListOf(transUnit))

        // Act.
        val stringResource = AndroidOutputStrategy().generateOutputWithRules(translations)
                .test()
                .assertNoErrors()
                .assertCompleted()
                .onNextEvents[0]
        val lines = stringResource.split("\n")

        // Assert.
        assertCorrectHeaderAndFooter(stringResource)
        lines[2] `should be equal to` "\t<!--Number 1-->"
        lines[3] `should be equal to` "\t<string name=\"one\">uno</string>"
    }

    @Test
    fun `should create strings resource with two clean translation`() {
        // Arrange.
        val transUnitOne = TransUnit(
                "one",
                "one",
                "uno",
                "Number 1")
        val transUnitTwo = TransUnit(
                "two",
                "two",
                "dos",
                "Number 2")
        val translations = Translations(mutableListOf(transUnitOne, transUnitTwo))

        // Act.
        val stringResource = AndroidOutputStrategy().generateOutputWithRules(translations)
                .test()
                .assertNoErrors()
                .assertCompleted()
                .onNextEvents[0]
        val lines = stringResource.split("\n")

        // Assert.
        assertCorrectHeaderAndFooter(stringResource)
        lines[2] `should be equal to` "\t<!--Number 1-->"
        lines[3] `should be equal to` "\t<string name=\"one\">uno</string>"
        lines[4] `should be equal to` "\t<!--Number 2-->"
        lines[5] `should be equal to` "\t<string name=\"two\">dos</string>"
    }

    @Test
    fun `should create strings resource with no comments`() {
        // Arrange.
        val transUnitOne = TransUnit(
                "one",
                "one",
                "uno",
                "")
        val transUnitTwo = TransUnit(
                "two",
                "two",
                "dos",
                "Number 2")
        val translations = Translations(mutableListOf(transUnitOne, transUnitTwo))

        // Act.
        val stringResource = AndroidOutputStrategy().generateOutputWithRules(translations)
                .test()
                .assertNoErrors()
                .assertCompleted()
                .onNextEvents[0]
        val lines = stringResource.split("\n")

        // Assert.
        assertCorrectHeaderAndFooter(stringResource)
        lines[2] `should be equal to` "\t<string name=\"one\">uno</string>"
        lines[3] `should be equal to` "\t<!--Number 2-->"
        lines[4] `should be equal to` "\t<string name=\"two\">dos</string>"
    }

    @Test
    fun `should create strings resource with one unclean translation id`() {
        // Arrange.
        val transUnit = TransUnit(
                "once upon a time",
                "story start for %@",
                "había una vez",
                "This is the first story line")
        val translations = Translations(mutableListOf(transUnit))

        // Act.
        val stringResource = AndroidOutputStrategy().generateOutputWithRules(translations)
                .test()
                .assertNoErrors()
                .assertCompleted()
                .onNextEvents[0]
        val lines = stringResource.split("\n")

        // Assert.
        assertCorrectHeaderAndFooter(stringResource)
        lines[2] `should be equal to` "\t<!--This is the first story line-->"
        lines[3] `should be equal to` "\t<string name=\"story_start_for__pattern\">había una vez</string>"
    }

    @Test
    fun `should create strings resource with one unclean translation target`() {
        // Arrange.
        val transUnit = TransUnit(
                "once upon a time",
                "story start for %@",
                " había %1$@ vez %2$@ 'en' ",
                "This is the first story line")
        val translations = Translations(mutableListOf(transUnit))

        // Act.
        val stringResource = AndroidOutputStrategy().generateOutputWithRules(translations)
                .test()
                .assertNoErrors()
                .assertCompleted()
                .onNextEvents[0]
        val lines = stringResource.split("\n")

        // Assert.
        assertCorrectHeaderAndFooter(stringResource)
        lines[2] `should be equal to` "\t<!--This is the first story line-->"
        lines[3] `should be equal to` "\t<string name=\"story_start_for__pattern\">\" había %1\$s vez %2\$s \\'en\\' \"</string>"
    }

    @Test
    fun `should clean source with source rules`() {
        // Arrange.
        val transUnit = TransUnit("", "&%@' OK", "", "")

        // Act.
        val newSource = AndroidOutputStrategy().rules.applyRules(Translations(mutableListOf(transUnit)))
                .test()
                .onNextEvents[0].transUnits[0].source

        // Assert.
        newSource `should be equal to` "_ampersand_pattern_apost_ok"
    }

    @Test
    fun `should clean source when identifier is a keyword`() {
        // Arrange.
        val transUnit = TransUnit("", "Void", "", "")

        // Act.
        val newSource = AndroidOutputStrategy().rules.applyRules(Translations(mutableListOf(transUnit)))
                .test()
                .onNextEvents[0].transUnits[0].source

        // Assert.
        newSource `should be equal to` "void_"
    }

    @Test
    fun `should clean target with target rules`() {
        // Arrange.
        val transUnit = TransUnit("", "", " &%@' ", "")

        // Act.
        val newTarget = AndroidOutputStrategy().rules.applyRules(Translations(mutableListOf(transUnit)))
                .test()
                .onNextEvents[0].transUnits[0].target
        // Assert.
        newTarget `should be equal to` "\" &amp;%1\$s\\' \""
    }

    @Test
    fun `should create strings resources from two sources`() {
        // Arrange.
        val transUnit1 = TransUnit(
                "one",
                "one",
                "uno",
                "Number 1")
        val translations1 = Translations(mutableListOf(transUnit1))
        val transUnit2 = TransUnit(
                "two",
                "two",
                "dos",
                "Number 2")
        val translations2 = Translations(mutableListOf(transUnit2))

        val androidOutputStrategy = AndroidOutputStrategy()
        androidOutputStrategy.addTimeGeneration = false
        // Act.
        val stringResource =
                Observable.from(listOf(translations1, translations2))
                        .flatMapSingle { androidOutputStrategy.generateOutputWithRules(it) }
                        .test()
                        .assertNoErrors()
                        .assertCompleted()
                        .onNextEvents

        // Assert.
        stringResource[0] `should be equal to` "<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<resources>\n" +
                "\t<!--Number 1-->\n" +
                "\t<string name=\"one\">uno</string>\n" +
                "</resources>"
        stringResource[1] `should be equal to` "<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<resources>\n" +
                "\t<!--Number 2-->\n" +
                "\t<string name=\"two\">dos</string>\n" +
                "</resources>"
    }

    @Test
    fun `should skip duplicates`() {
        // Arrange.
        val transUnitOne = TransUnit(
                "one",
                "one",
                "uno",
                "")
        val transUnitTwo = TransUnit(
                "one",
                "one",
                "uno",
                "")
        val translations = Translations(mutableListOf(transUnitOne, transUnitTwo))

        val androidOutputStrategy = AndroidOutputStrategy()
        androidOutputStrategy.addTimeGeneration = false

        // Act.
        val stringResource = androidOutputStrategy.generateOutputWithRules(translations)
                .test()
                .assertNoErrors()
                .assertCompleted()
                .onNextEvents[0]

        // Assert.
        stringResource `should be equal to` "<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<resources>\n" +
                "\t<string name=\"one\">uno</string>\n" +
                "</resources>"
    }
}