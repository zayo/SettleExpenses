package com.nislav.settleexpenses.domain

/**
 * Represents expense entry.
 */
data class Expense(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val amount: Long,
    val date: String
)