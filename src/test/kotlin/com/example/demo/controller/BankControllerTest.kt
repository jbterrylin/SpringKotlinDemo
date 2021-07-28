package com.example.demo.controller

import io.mockk.MockK
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    val baseUrl = "/api/banks"

    // with nested test
    @Nested
    @DisplayName("getBanks()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {
        @Test
        fun `should return all banks`() {
            // given

            // when,then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") {value("1234")}
                }
        }
    }

    @Nested
    @DisplayName("getBank()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {
        @Test
        fun `should return bank with given account number`() {
            // given
            val accountNumber =1234

            // when, then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") {value("3.14")}
                    jsonPath("$.transactionFee") {value("17")}
                }
        }

        @Test
        fun `should return NOT FOUND if account number does not exist`() {
            // given
            val accountNumber ="not_exist"

            // when, then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }

    // without nested class
//    @Test
//    fun `should return bank with given account number`() {
//        // given
//        val accountNumber =1234
//
//        // when, then
//        mockMvc.get("/api/banks/$accountNumber")
//            .andDo { print() }
//            .andExpect {
//                status { isOk() }
//                content { contentType(MediaType.APPLICATION_JSON) }
//                jsonPath("$.trust") {value("3.14")}
//                jsonPath("$.transactionFee") {value("17")}
//            }
//    }
}