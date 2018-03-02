package com.skedgo.tools.platform.android

import com.skedgo.tools.OutputStringsStrategy
import com.skedgo.tools.platform.android.rules.*
import com.skedgo.tools.rules.TransformationRule
import com.skedgo.tools.translations.Translations
import rx.Single
import java.util.*


class AndroidOutputStrategy : OutputStringsStrategy() {

    override val sourceTransformationRules: List<TransformationRule> =
            listOf(
                    EncodeAmpersandRule,
                    AndroidPatternsRule,
                    ValidAndroidIdentifierRule,
                    AddFriendlyPrefixRule,
                    LowerCaseRule
            )

    override val targetTransformationRules: List<TransformationRule> =
            listOf(
                    EncodeAmpersandRule,
                    AndroidPatternsRule,
                    AndroidPatternsListRule,
                    EscapeApostropheRule,
                    QuotesRule
            )

    var addTimeGeneration = true
    private val androidStrings = StringBuffer()

    override fun generateOutputText(translations: Translations): Single<String> =
            Single.fromCallable {
                addAndroidStringsHeader()
                addStringTranslations(translations)
                addAndroidStringsFooter()
            }.map { androidStrings.toString() }

    private fun addAndroidStringsHeader() =
            androidStrings.append("<?xml version='1.0' encoding='UTF-8'?>\n<resources>\n")

    private fun addStringTranslations(translations: Translations) {
        translations.transUnits
                .forEach {
                    addComment(it.note)
                    addTranslation(it.source, it.target)
                }
    }

    private fun addComment(note: String) {
        if (note.isNotEmpty()) androidStrings.append("\t<!--$note-->\n")
    }

    private fun addTranslation(source: String, target: String) {
        androidStrings.append("\t<string name=\"$source\">$target</string>\n")
    }

    private fun addAndroidStringsFooter() {
        androidStrings.append("</resources>")
        if (addTimeGeneration)
            androidStrings.append("\n<!-- This is an auto generated file (" + Date(System.currentTimeMillis()) + ") -->")
    }
}