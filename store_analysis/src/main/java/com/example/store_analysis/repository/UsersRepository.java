package com.example.store_analysis.repository;

import com.example.store_analysis.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, String>
{
}
