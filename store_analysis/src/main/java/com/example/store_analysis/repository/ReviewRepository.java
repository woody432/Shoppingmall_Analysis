package com.example.store_analysis.repository;

import com.example.store_analysis.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, ReviewId>
{
}
