package com.example.demo.datasource.mock

import com.example.demo.datasource.BankDataSource
import com.example.demo.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource: BankDataSource {
    val banks = listOf(
        Bank("1234",3.14,17),
        Bank("5678",11.0,0),
        Bank("9012",0.0,13)
    )

    override fun retrieveBanks(): Collection<Bank> = banks

    override fun retrieveBank(accountNumber: String): Bank {
        return banks.first() {it.accountNumber == accountNumber}
//        return banks.firstOrNull() {it.accountNumber == accountNumber}
//            ?:throw NoSuchElementException("Cant find")
    }
}