package com.skedgo.tools.platform.android

import com.skedgo.tools.model.TransUnit

fun TransUnit.clean() = cleanSource().cleanTarget()

fun TransUnit.cleanSource(): TransUnit {
    source = source.replaceWithNameRules()
    return this
}

fun TransUnit.cleanTarget(): TransUnit {
    target = target.replaceWithValueRules()
    return this
}

private fun String.replaceWithNameRules(): String = this
        .replacePatterns()
        .replaceGeneralNameRules()
        .addFirstCharIfNeeded()
        .toLowerCase()

private fun String.replaceWithValueRules(): String = this
        .replacePatterns()
        .replaceWithAndroidPatterns()
        .replaceGeneralValueRules()
        .addQuotesIfNeeded()


private fun String.addFirstCharIfNeeded(): String =
        if (Character.isDigit(this[0])) {
            "_" + this
        } else {
            this
        }

private fun String.replacePatterns(): String = this
        .replace("&", "&amp;")
        .replace("%ld", "PERCAT")
        .replace("%@", "PERCAT")
        .replace("%\\d@".toRegex(), "PERCAT")

private fun String.replaceGeneralNameRules(): String = this
// TODO: add unit tests for all these cases after deciding the final replacement
        .replace(" ", "_")
        .replace("\n", "_")
        .replace("\"", "")
        .replace(".", "_DOT")
        .replace("!", "_EXCLAM")
        .replace("%s", "nps")
        .replace("?", "_QUESTION")
        .replace("\'", "_APOST")
        .replace("’", "_APOST")
        .replace("/", "_SLASH")
        .replace(",", "_COMA")
        .replace("(", "_START_PARENT")
        .replace(")", "_END_PARENT")
        .replace("{", "_START_QBRAQUET")
        .replace("}", "_END_QBRAQUET")
        .replace("&amp;", "_AMPERSAND")
        .replace("PERCAT", "_pattern")
        .replace("-", "_MINUS")
        .replace("<", "_LESST")
        .replace(">", "_MORET")
        .replace("@", "_AT")
        .replace("=", "_EQUAL")
        .replace("%", "_PERC")
        .replace("₂", "_2")
        .replace(":", "_2POINTS")
        .replace("\\n", "_")
        .replace("…", "_DOT_DOT_DOT")
        .replace("*", "ASTERISK")


private fun String.replaceGeneralValueRules(): String = this
        .replace("'", "\\'")

private fun String.addQuotesIfNeeded(): String =
        if (startsWith(" ") || endsWith(" ")) {
            "\"" + this + "\""
        } else {
            this
        }
private fun String.replaceWithAndroidPatterns():String {
    var string = this
    var i = 1
    while (string.contains("PERCAT")) {
        string = string.replaceFirst("PERCAT".toRegex(), "%" + i++ + "\\\$s")
    }
    return string
}