package de.trusteq.usermanager.controllers

import de.trusteq.usermanager.dto.IdNameTypeResponse
import de.trusteq.usermanager.models.Company
import de.trusteq.usermanager.models.User
import de.trusteq.usermanager.services.CompanyService
import de.trusteq.usermanager.services.UserService
import de.trusteq.usermanager.toIdNameTypeResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/search")
class SearchController(
    private val userService: UserService,
    private val companyService: CompanyService,
) {

    @GetMapping
    fun searchByNames(
        @RequestParam(name = "query") query: String,
    ): Flow<IdNameTypeResponse> {
        val usersFlow = userService.findAllUsersByNameLike(name = query)
            .map(User::toIdNameTypeResponse)
        val companiesFlow = companyService.findAllCompaniesByNameLike(name = query)
            .map(Company::toIdNameTypeResponse)

        return merge(usersFlow, companiesFlow)
    }
}
