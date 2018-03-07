package com.skedgo.tools

import com.skedgo.tools.rules.TransformationRule
import com.skedgo.tools.translations.Translations
import com.skedgo.tools.translations.cleanSource
import com.skedgo.tools.translations.cleanTarget
import rx.Single

class TransformationRules {

    var sourceTransformationRules = mutableListOf<TransformationRule>()
    var targetTransformationRules = mutableListOf<TransformationRule>()

    fun applyRules(translations: Translations): Single<Translations> =
            Single.fromCallable {
                translations.transUnits.forEach {
                    it.cleanSource(sourceTransformationRules)
                    it.cleanTarget(targetTransformationRules)
                }
                translations
            }
}