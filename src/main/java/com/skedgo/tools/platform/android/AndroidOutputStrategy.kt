package com.skedgo.tools.platform.android

import com.skedgo.tools.OutputStringsStrategy
import com.skedgo.tools.model.Translations
import rx.Single
import java.util.*


class AndroidOutputStrategy : OutputStringsStrategy {

    var addTimeGeneration = true
    private val androidStrings = StringBuffer()

    override fun generateOutput(translations: Translations): Single<String> =
            Single.fromCallable {
                addAndroidStringsHeader()
                addStringTranslations(translations)
                addAndroidStringsFooter()
            }.map { androidStrings.toString() }

    private fun addAndroidStringsHeader() =
            androidStrings.append("<?xml version='1.0' encoding='UTF-8'?>\n<resources>\n")

    private fun addStringTranslations(translations: Translations) {
        translations.transUnits
                .map { it.clean() }
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