package com.nislav.settleexpenses.util

import java.text.Normalizer

/**
 * Normalize [String] for search/sort purposes.
 */
fun String.normalized(): String = stripAccents().lowercase()

/**
 * Strips accent characters from the string.
 *
 * @return The new string.
 */
fun String.stripAccents(): String =
    Normalizer.normalize(this, Normalizer.Form.NFD).replace(ACCENTS_REGEX, "")

/**
 * Since the Normalization will strip characters from accents, we still have to strip those accents from it.
 *
 * src: https://www.regular-expressions.info/unicode.html
 */
private val ACCENTS_REGEX = "\\p{Mn}+".toRegex()
