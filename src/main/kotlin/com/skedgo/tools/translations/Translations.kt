package com.skedgo.tools.translations

data class Translations(var transUnits: MutableList<TransUnit>) {
    var targetLanguage: String? = null
}