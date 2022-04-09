package com.nislav.settleexpenses.domain

import kotlinx.coroutines.flow.flow

class ExpensesRepository {
    val identity = flow<String> {
        emit("Expenses repository")
    }
}