package com.skedgo.tools.platform.android.rules

import com.skedgo.tools.rules.PatternToReplace.StringPattern
import com.skedgo.tools.rules.TransformationRule.ReplacementRule

object ValidAndroidIdentifierRule : ReplacementRule(
        listOf(
                Pair(StringPattern(" "), "_"),
                Pair(StringPattern("\n"), "_"),
                Pair(StringPattern("\""), ""),
                Pair(StringPattern("."), "_DOT"),
                Pair(StringPattern("!"), "_EXCLAM"),
                Pair(StringPattern("%s"), "nps"),
                Pair(StringPattern("?"), "_QUESTION"),
                Pair(StringPattern("\'"), "_APOST"),
                Pair(StringPattern("’"), "_APOST"),
                Pair(StringPattern("/"), "_SLASH"),
                Pair(StringPattern(","), "_COMA"),
                Pair(StringPattern("("), "_START_PARENT"),
                Pair(StringPattern(")"), "_END_PARENT"),
                Pair(StringPattern("{"), "_START_QBRAQUET"),
                Pair(StringPattern("}"), "_END_QBRAQUET"),
                Pair(StringPattern("&amp;"), "_AMPERSAND"),
                Pair(StringPattern("PERCAT"), "_pattern"),
                Pair(StringPattern("-"), "_MINUS"),
                Pair(StringPattern("<"), "_LESST"),
                Pair(StringPattern(">"), "_MORET"),
                Pair(StringPattern("@"), "_AT"),
                Pair(StringPattern("="), "_EQUAL"),
                Pair(StringPattern("%"), "_PERC"),
                Pair(StringPattern("₂"), "_2"),
                Pair(StringPattern(":"), "_2POINTS"),
                Pair(StringPattern("\\n"), "_"),
                Pair(StringPattern("…"), "_DOT_DOT_DOT"),
                Pair(StringPattern("*"), "ASTERISK")
        )
)