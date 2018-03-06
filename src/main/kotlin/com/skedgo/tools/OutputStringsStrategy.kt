package com.skedgo.tools

import com.skedgo.tools.rules.TransformationRule
import com.skedgo.tools.translations.Translations
import com.skedgo.tools.translations.cleanSource
import com.skedgo.tools.translations.cleanTarget
import rx.Single

interface OutputStringsStrategy {

    val sourceTransformationRules: List<TransformationRule>
    val targetTransformationRules: List<TransformationRule>

    fun generateOutputText(translations: Translations): Single<String>

    fun generateOutput(translations: Translations): Single<String> =
            cleanTranslations(translations)
                    .flatMap { generateOutputText(it) }

    private fun cleanTranslations(translations: Translations): Single<Translations> =
            Single.fromCallable {
                translations.transUnits.forEach {
                    it.cleanSource(sourceTransformationRules)
                    it.cleanTarget(targetTransformationRules)
                }
                translations
            }
}