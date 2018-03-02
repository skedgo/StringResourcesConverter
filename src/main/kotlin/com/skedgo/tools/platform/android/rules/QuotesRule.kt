package com.skedgo.tools.platform.android.rules

import com.skedgo.tools.rules.TransformationRule

object QuotesRule : TransformationRule.CustomRule(
        {
            if (it.startsWith(" ") || it.endsWith(" ")) {
                "\"" + it + "\""
            } else {
                it
            }
        }
)