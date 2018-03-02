package com.skedgo.tools.platform.android.rules

import com.skedgo.tools.rules.TransformationRule

object AndroidPatternsListRule : TransformationRule.CustomRule(
        {
            var string = it
            var i = 1
            while (string.contains("PERCAT")) {
                string = string.replaceFirst("PERCAT".toRegex(), "%" + i++ + "\\\$s")
            }
            string
        }
)