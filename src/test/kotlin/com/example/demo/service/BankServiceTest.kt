package com.example.demo.service

import com.example.demo.datasource.BankDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BankServiceTest {

    private val dataSource: BankDataSource = mockk(relaxed = true)
    private val banksService = BankService(dataSource)

    @Test
    fun `should call data source to retrieve banks`() {
        // given
        // no need to write if write mockk( --> relaxed = true)
//        every { dataSource.retrieveBanks() } returns emptyList()
        
        // when
        banksService.getBanks()
        
        // then
        verify(exactly = 1) { dataSource.retrieveBanks() }
    }
}