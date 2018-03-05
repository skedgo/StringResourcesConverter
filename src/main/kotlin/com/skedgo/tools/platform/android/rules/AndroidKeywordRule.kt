package com.skedgo.tools.platform.android.rules

import com.skedgo.tools.rules.TransformationRule

object AndroidKeywordRule : TransformationRule.CustomRule(
        {
            if (AndroidKeywordRule.keywords.contains(it)) {
                "${it}_"
            } else {
                it
            }
        }
) {
    private val keywords = listOf(
            "abstract", "continue", "for", "new", "switch",
            "assert", "default", "goto", "package", "synchronized",
            "boolean", "do", "if", "private", "this",
            "break", "double", "implements", "protected", "throw",
            "byte", "else", "import", "public", "throws",
            "case", "enum", "instanceof", "return", "transient",
            "catch", "extends", "int", "short", "try",
            "char", "final", "interface", "static", "void",
            "class", "finally", "long", "strictfp", "volatile",
            "const", "float", "native", "super", "while"
    )
}