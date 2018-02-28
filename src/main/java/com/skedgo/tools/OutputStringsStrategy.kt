package com.skedgo.tools

import com.skedgo.tools.model.Translations
import rx.Single

interface OutputStringsStrategy {

    fun generateOutput(translations: Translations): Single<String>
}