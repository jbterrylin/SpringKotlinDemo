package com.example.demo.datasource.mock

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat


internal class MockBankDataSourceTest {

    private val mockDataSource = MockBankDataSource()

    @Test
    fun `should provide full banks data sets`() {
        // given


        // when
        val banks = mockDataSource.retrieveBanks()

        // then
        Assertions.assertThat(banks).isNotEmpty
    }
    
    @Test
    fun `should provide some mock data`() {
        // given
        
        
        // when
        val banks = mockDataSource.getBanks()
        
        // then
        assertThat(banks).allMatch{it.accountNumber.isNotBlank()}
        assertThat(banks).anyMatch{it.trust != 0.0}
        assertThat(banks).anyMatch{it.transactionFee != 0}
    }
}