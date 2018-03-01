package com.skedgo.tools

import com.skedgo.tools.rules.TransformationRule
import com.skedgo.tools.translations.Translations
import com.skedgo.tools.translations.cleanSource
import com.skedgo.tools.translations.cleanTarget
import rx.Single

abstract class OutputStringsStrategy {

    abstract val sourceTransformationRules: List<TransformationRule>
    abstract val targetTransformationRules: List<TransformationRule>

    abstract fun generateOutputText(translations: Translations): Single<String>

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