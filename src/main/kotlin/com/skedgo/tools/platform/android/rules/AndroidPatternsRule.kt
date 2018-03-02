package com.skedgo.tools.platform.android.rules

import com.skedgo.tools.rules.PatternToReplace.RegexPattern
import com.skedgo.tools.rules.PatternToReplace.StringPattern
import com.skedgo.tools.rules.TransformationRule.ReplacementRule

object AndroidPatternsRule : ReplacementRule(
        listOf(
                Pair(StringPattern("%ld"), "PERCAT"),
                Pair(StringPattern("%@"), "PERCAT"),
                Pair(RegexPattern("%\\d@".toRegex()), "PERCAT")
        )
)