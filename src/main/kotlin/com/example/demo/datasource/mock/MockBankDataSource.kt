package com.example.demo.datasource.mock

import com.example.demo.datasource.BankDataSource
import com.example.demo.model.Bank
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class MockBankDataSource: BankDataSource {
    val banks = mutableListOf(
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

    override fun createBank(bank: Bank): Bank {
        if(banks.any{it.accountNumber == bank.accountNumber}) {
            throw IllegalArgumentException("Bank with account number ${bank.accountNumber} already exists")
        }
        banks.add(bank)

        return bank
    }

    override fun updateBank(bank: Bank): Bank {
        val currentBank = banks.firstOrNull {it.accountNumber == bank.accountNumber}
            ?:throw NoSuchElementException("Cant find")

        banks.remove(currentBank)
        banks.add(bank)

        return bank
    }

    override fun deleteBank(accountNumber: String) {
        val currentBank = banks.firstOrNull {it.accountNumber == accountNumber}
            ?:throw NoSuchElementException("Cant find")

        banks.remove(currentBank)
    }


}