package com.skedgo.tools

import com.skedgo.tools.model.Translations
import rx.Single
import java.io.InputStream

interface InputStringsStrategy {
    fun createInputValues(input: InputStream): Single<Translations>
}