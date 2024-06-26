package de.trusteq.usermanager.controllers

import com.ninjasquad.springmockk.MockkBean
import de.trusteq.usermanager.config.TestConfig
import de.trusteq.usermanager.models.Company
import de.trusteq.usermanager.services.CompanyService
import de.trusteq.usermanager.services.UserService
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.flow.flow
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.context.annotation.Import
import java.util.UUID
import kotlin.random.Random

@WebFluxTest
@Import(TestConfig::class)
class CompanyControllerTest(@Autowired val webClient: WebTestClient) {
    @MockkBean
    private lateinit var companyService: CompanyService

    @MockkBean
    private lateinit var userService: UserService

    @Test
    fun `get company with no users by id`() {
        // given
        val id = Random.nextInt()
        val name = UUID.randomUUID().toString()
        val address = UUID.randomUUID().toString()

        coEvery { companyService.findCompanyById(id) } returns Company(id, name, address)
        coEvery { userService.findUsersByCompanyId(id) } returns flow {}

        // when/then
        webClient.get()
            .uri("/api/companies/$id")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isEqualTo(id)
            .jsonPath("$.name").isEqualTo(name)
            .jsonPath("$.address").isEqualTo(address)
            .jsonPath("$.users").isEmpty

        coVerify(exactly = 1) { companyService.findCompanyById(id) }
        coVerify(exactly = 1) { userService.findUsersByCompanyId(id) }
    }
}
