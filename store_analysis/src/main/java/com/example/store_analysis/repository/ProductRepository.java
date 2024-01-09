package com.example.store_analysis.repository;

import com.example.store_analysis.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product,String>
{

    @Query("SELECT p FROM Product p WHERE p.product_name LIKE %:val%")
    List<Product> findByProductName(@Param("val") String value);
}
