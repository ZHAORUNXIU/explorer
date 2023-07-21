package com.crypted.explorer

import com.crypted.explorer.api.service.transaction.TransactionService
import com.crypted.explorer.gateway.action.TransactionAction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class CorsTest(private val mockMvc: MockMvc) {

	@Test
	@Throws(Exception::class)
	fun testCorsRequest() {
		val origin = "https://explorer.dev.kstadium.io/" // Replace with your origin configured in CorsConfig file

		// Perform the request using MockMvc
		val mvcResult = mockMvc.perform(
			MockMvcRequestBuilders
				.get("/v1/transaction/history") // Replace with your actual endpoint
				.header("Origin", origin)
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk())
			.andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin))
			.andReturn()

		// You can assert other things based on the response if needed
		// For example, checking the response content, status code, etc.

		// Get the response content from MvcResult
		val responseContent: String = mvcResult.response.contentAsString

		// Assertions
		Assertions.assertEquals("This is a CORS-enabled endpoint.", responseContent)
	}
}