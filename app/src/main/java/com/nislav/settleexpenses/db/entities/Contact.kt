package com.nislav.settleexpenses.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents the Contact item.
 */
@Entity
data class Contact(
    val firstName: String,
    val lastName: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}