package com.skedgo.tools.platform.android.rules

import com.skedgo.tools.rules.TransformationRule

object LowerCaseRule : TransformationRule.CustomRule(
        {
            it.toLowerCase()
        }
)