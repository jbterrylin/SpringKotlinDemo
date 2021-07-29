package com.example.demo.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Bank (
    // JsonProperty is for network variable mapping, normal one no need
    @JsonProperty("account_number")
    var accountNumber: String,
    @JsonProperty("trust")
    var trust: Double,
    @JsonProperty("default_transaction_fee")
    var transactionFee: Int
)