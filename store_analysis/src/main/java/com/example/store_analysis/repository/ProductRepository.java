package com.example.store_analysis.repository;

import com.example.store_analysis.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,String>
{
}
