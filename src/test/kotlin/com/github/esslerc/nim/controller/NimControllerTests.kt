package com.github.esslerc.nim.controller

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class NimControllerTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setup() {
        // reset server state
        mockMvc.perform(get("/api/nim/reset"))
            .andExpect(status().isOk)
    }

    @Test
    fun testInvalidMove() {
        val requestBody = "{\"row\":\"2\", \"stonesToTake\": \"6\"}"
        mockMvc.perform(
            post("/api/nim/move")
                .content(requestBody)
                .contentType("application/json"))
                .andExpect(status().isOk)
                .andExpect(content().string("Invalid move"))
    }

    @Test
    fun testValidMove() {
        val requestBody = "{\"row\":\"1\", \"stonesToTake\": \"2\"}"

        mockMvc.perform(
            post("/api/nim/move")
                .content(requestBody)
                .contentType("application/json"))
                .andExpect(status().isOk)
                .andExpect(content().string("""
                    You have removed 2 stones from row 1.
                    Server has removed  3 stones from row 3.
                    Current status: [1, 5, 4]""".trimIndent()))

    }

    @Test
    fun testInitialStatus() {
        mockMvc.perform(get("/api/nim/status"))
            .andExpect(status().isOk)
            .andExpect(content().json("[3, 5, 7]"))
    }
}