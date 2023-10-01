package com.company.inventory.response;

import java.util.List;
import java.util.Objects;

import com.company.inventory.model.Category;

public class CategoryResponse {
	
	private List<Category> category;
	
	public List<Category> getCategory() {
		return category;
	}

	public void setCategory(List<Category> category) {
		this.category = category;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryResponse other = (CategoryResponse) obj;
		return Objects.equals(category, other.category);
	}
	

}
