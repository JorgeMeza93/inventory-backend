package com.company.inventory.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.company.inventory.response.CategoryResponseRest;

@Service
public class CategoryService implements ICategoryService{
	
	@Override
	public ResponseEntity<CategoryResponseRest> search() {
		// TODO Auto-generated method stub
		return null;
	}

}
