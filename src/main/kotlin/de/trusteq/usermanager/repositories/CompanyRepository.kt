package de.trusteq.usermanager.repositories

import de.trusteq.usermanager.models.Company
import kotlinx.coroutines.flow.Flow

interface CompanyRepository {
    fun findByNameContaining(name: String): Flow<Company>
    fun findAll(): Flow<Company>
    suspend fun findById(id: Int): Company?
    suspend fun deleteById(id: Int): Int
    suspend fun save(company: Company): Company?
}
