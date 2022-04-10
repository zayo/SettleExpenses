package com.nislav.settleexpenses.domain

/**
 * Represents the Contact item.
 */
data class Contact(
    val id: Long = System.currentTimeMillis(),
    val firstName: String,
    val lastName: String,
)