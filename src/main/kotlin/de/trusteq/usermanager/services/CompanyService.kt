package de.trusteq.usermanager.services

import de.trusteq.usermanager.models.Company
import de.trusteq.usermanager.repositories.CompanyRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class CompanyService(
    private val companyRepository: CompanyRepository,
) {

    suspend fun saveCompany(company: Company): Company? =
        companyRepository.save(company)

    fun findAllCompanies(): Flow<Company> =
        companyRepository.findAll()

    suspend fun findCompanyById(id: Int): Company? =
        companyRepository.findById(id)

    suspend fun deleteCompanyById(id: Int) {
        val foundCompany = companyRepository.findById(id)

        if (foundCompany == null) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Company with id $id was not found.")
        } else {
            companyRepository.deleteById(id)
        }
    }

    fun findAllCompaniesByNameLike(name: String): Flow<Company> =
        companyRepository.findByNameContaining(name)

    suspend fun updateCompany(id: Int, requestedCompany: Company): Company {
        val foundCompany = companyRepository.findById(id)

        return if (foundCompany == null) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Company with id $id was not found.")
        } else {
            companyRepository.save(
                requestedCompany.copy(id = foundCompany.id),
            )!!
        }
    }
}
