package com.nislav.settleexpenses.domain

import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.util.normalized

/**
 * Extracts name suitable for searching and ordering.
 */
val Contact.searchableName
    inline get() = name.normalized()

/**
 * Initials of [Contact].
 */
val Contact.initials
    inline get() = "${firstName.first()}${lastName.first()}".uppercase()

/**
 * Joined [Contact.firstName] with [Contact.lastName].
 */
val Contact.name
    inline get() = "$firstName $lastName"