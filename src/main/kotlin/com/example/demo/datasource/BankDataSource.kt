package com.example.demo.datasource

import com.example.demo.model.Bank

// use for different implementation for example, we can do action by API or get(steal) data from network
interface BankDataSource {
    fun retrieveBanks():Collection<Bank>
    fun retrieveBank(accountNumber: String):Bank
    fun createBank(bank:Bank): Bank
    fun updateBank(bank:Bank): Bank
    fun deleteBank(accountNumber: String): Unit
}