package com.skedgo.tools.translations

import com.skedgo.tools.rules.TransformationRule
import com.skedgo.tools.rules.applyRules

fun TransUnit.cleanSource(rules: List<TransformationRule>): TransUnit {
    source = source.applyRules(rules)
    return this
}

fun TransUnit.cleanTarget(rules: List<TransformationRule>): TransUnit {
    target = target.applyRules(rules)
    return this
}