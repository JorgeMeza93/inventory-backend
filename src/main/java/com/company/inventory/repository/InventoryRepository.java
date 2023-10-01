package com.company.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.inventory.model.Category;

public interface InventoryRepository extends JpaRepository<Category, Long> {
	
	

}
