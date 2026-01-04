package com.example.domain.usecase

import com.example.domain.model.Product

interface GetSoonExpiredProductsUseCase  {
    suspend fun  execute() : List<Product>
}