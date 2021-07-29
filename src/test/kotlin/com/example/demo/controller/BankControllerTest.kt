package com.example.demo.controller

import com.example.demo.model.Bank
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

// isBadRequest and isNotFound is different

// 2 ways, 2nd way is better
//content { contentType(MediaType.APPLICATION_JSON) }
//jsonPath("$.accountNumber") {value("acc123")}
//jsonPath("$.trust") {value("31.415")}
//jsonPath("$.transactionFee") {value("2")}
//
//content {
//    contentType(MediaType.APPLICATION_JSON)
//    json(objectMapper.writeValueAsString(updatedBank))
//}

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor( val mockMvc: MockMvc, val objectMapper: ObjectMapper) {

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
    
    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank {
        @Test
        fun `should add the new bank`() {
            // given
            val newBank = Bank("acc123",31.415,2)
            
            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }
            
            // then
            performPost.andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.accountNumber") {value("acc123")}
                    jsonPath("$.trust") {value("31.415")}
                    jsonPath("$.transactionFee") {value("2")}

                }
        }
        
        @Test
        fun `should return BAD REQUEST if bank with given account number already exists`() {
            // given
            val invalidBank = Bank("1234",0.0,2)
            
            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }
            
            // then
            performPost.andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }
    }
    
    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchExistingBank {
        
        @Test
        fun `should update an existing bank`() {
            // given
            val updatedBank = Bank("1234",0.0,2)

            // when
            val performPatchRequest = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)
            }
            
            // then
            performPatchRequest.andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updatedBank))
                    }
                }

            mockMvc.get("$baseUrl/${updatedBank.accountNumber}")
                .andExpect { content { json(objectMapper.writeValueAsString(updatedBank)) } }
        }
        
        @Test
        fun `should return BAD REQUEST if no bank with given account number exists`() {
            // given
            val invalidBank =Bank("no_exists",1.0,1)
            
            // when
            val performPatchRequest = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }
            
            // then
            performPatchRequest.andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }
    
    @Nested
    @DisplayName("DELETE /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteExistingBank {
        
        @Test
        fun `should delete the bank with the given accountNumber`() {
            // given
            val accountNumber = 1234
            
            // when
            val performPatchRequest = mockMvc.delete("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNoContent() }
                }
            
            // then
            mockMvc.get("$baseUrl/$accountNumber")
                .andExpect { status { isNotFound() } }
        }

        @Test
        fun `should return NOT FOUND if account number does not exist`() {
            // given
            val accountNumber ="not_exist"

            // when, then
            mockMvc.delete("$baseUrl/$accountNumber")
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