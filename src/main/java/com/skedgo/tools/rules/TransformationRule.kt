package com.skedgo.tools.rules

sealed class TransformationRule {
    open class ReplacementRule(open val replacements: List<Pair<PatternToReplace, String>>) : TransformationRule()
    open class AddPrefixRule(val prefix: String) : TransformationRule()
    open class CustomRule(val action: (string: String) -> String) : TransformationRule()
}

sealed class PatternToReplace {
    class StringPattern(val string: String) : PatternToReplace()
    class RegexPattern(val regex: Regex) : PatternToReplace()
}