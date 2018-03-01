package com.skedgo.tools.platform.android.rules

import com.skedgo.tools.rules.PatternToReplace
import com.skedgo.tools.rules.TransformationRule.ReplacementRule

object EscapeApostropheRule : ReplacementRule(
        listOf(
                Pair(PatternToReplace.StringPattern("'"), "\\'")
        )
)