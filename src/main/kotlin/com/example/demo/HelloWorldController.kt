package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/hello")
class HelloWorldController {

//    @GetMapping("spring_boot")
    @GetMapping
    fun helloWorld(): String = "Hello, this is rest endpoint"
}