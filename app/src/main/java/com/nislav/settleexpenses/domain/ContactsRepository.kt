package com.nislav.settleexpenses.domain

import kotlinx.coroutines.flow.flow

class ContactsRepository {

    val identity = flow<String> {
        emit("Contacts repository")
    }
}