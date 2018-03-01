package com.skedgo.tools.rules

fun String.applyRules(rules: List<TransformationRule>): String {
    var newString = this
    rules.forEach {
        newString = newString.applyRule(it)
    }
    return newString
}

fun String.applyRule(rule: TransformationRule): String =
        when (rule) {
            is TransformationRule.ReplacementRule -> this.applyReplacementRule(rule)
            is TransformationRule.AddPrefixRule -> this.applyAddPrefixRule(rule)
            is TransformationRule.CustomRule -> this.applyCustomRule(rule)
        }

private fun String.applyReplacementRule(rule: TransformationRule.ReplacementRule): String {
    var newString = this
    rule.replacements.forEach { (match, replacement) ->
        newString = when (match) {
            is PatternToReplace.StringPattern -> newString.replace(match.string, replacement)
            is PatternToReplace.RegexPattern -> newString.replace(match.regex, replacement)
        }
    }
    return newString
}

private fun String.applyAddPrefixRule(rule: TransformationRule.AddPrefixRule): String =
    if (this.isNotEmpty() && Character.isDigit(this[0])) {
        rule.prefix + this
    } else {
        this
    }

private fun String.applyCustomRule(rule: TransformationRule.CustomRule): String =
        rule.action(this)
