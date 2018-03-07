package com.skedgo.tools

import com.skedgo.tools.translations.Translations
import rx.Single

interface OutputStringsStrategy {

    val rules:TransformationRules

    fun generateOutputText(translations: Translations): Single<String>

    fun generateOutputWithRules(translations: Translations): Single<String> =
            rules.applyRules(translations)
                    .flatMap { generateOutputText(it) }
}