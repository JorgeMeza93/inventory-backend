package com.company.inventory.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventory.model.Category;
import com.company.inventory.repository.CategoryRepository;
import com.company.inventory.response.CategoryResponseRest;

@Service
public class CategoryService implements ICategoryService{
	
	@Autowired
	private CategoryRepository daoCategory;	
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> search() {
		CategoryResponseRest response = new CategoryResponseRest();
		try {
			List<Category> categories = daoCategory.findAll();
			response.getCategoryResponse().setCategory(categories);
			response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
			
		} catch (Exception e) {
			response.setMetadata("Respuesta no ok", "-1", "Error al consultar");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> searchById(Long id) {
		CategoryResponseRest response = new CategoryResponseRest();
		List<Category> list = new ArrayList<>();
		try {
			Optional<Category> category = daoCategory.findById(id);
			if(category.isPresent()) {
				list.add(category.get());
				response.getCategoryResponse().setCategory(list);
				response.setMetadata("Respuesta ok", "00", "Categoria encontrada");
			}
			else {
				response.setMetadata("Respuesta no ok", "-1", "Categoria no encontrada");
				return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			response.setMetadata("Respuesta no ok", "-1", "Error al consultar por id");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<CategoryResponseRest> save(Category category) {
		CategoryResponseRest response = new CategoryResponseRest();
		List<Category> list = new ArrayList<>();
		try {
			if(daoCategory.existsById(category.getId())) {
				response.setMetadata("Respuesta no ok", "-1", "Categoría ya registrada");
				return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
				
			}
			else{
				Category categorySaved = daoCategory.save(category);
				if(categorySaved != null) {
					list.add(categorySaved);
					response.getCategoryResponse().setCategory(list);
					response.setMetadata("Respuesta Ok", "00", "Categoría registrada exitosamente");
					return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
				}
				else {
					response.setMetadata("Respuesta no ok", "-1", "Error al registrar la categoría");
					return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
				}
			}
			
		} catch (Exception e) {
			response.setMetadata("Respuesta no ok", "-1", "Error al registrar la categoría.");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@Override
	public ResponseEntity<CategoryResponseRest> update(Category category, Long id) {
		CategoryResponseRest response = new CategoryResponseRest();
		List<Category> list = new ArrayList<>();
		try {
			if(daoCategory.existsById(id)) {
				Category categoryAux = daoCategory.findById(id).orElse(null);
				categoryAux.setName(category.getName());
				categoryAux.setDescription(category.getDescription());
				Category categoryToUpdate = daoCategory.save(categoryAux);
				if(categoryToUpdate != null) {
					list.add(categoryToUpdate);
					response.getCategoryResponse().setCategory(list);
					response.setMetadata("Respuesta oK", "00", "Categoria Actualizada");
					return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
				}
				else {
					response.setMetadata("Respuesta no ok", "-1", "Categoria no actualizada");
					return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
					
				}
			}
			else {
				response.setMetadata("Respuesta no ok", "-1", "Categoría no encontrada");
				return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			response.setMetadata("Respuesta no ok", "-1", "Error al actualizar la categoría.");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<CategoryResponseRest> delete(Long id) {
		CategoryResponseRest response = new CategoryResponseRest();
		try {
			if(daoCategory.existsById(id)) {
				daoCategory.deleteById(id);
				response.setMetadata("Respuesta ok", "00", "Categoría eliminada correctamente");
				return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
			}
			else {
				response.setMetadata("Respuesta no ok", "-1", "Categoría no encontrada");
				return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			response.setMetadata("Respuesta no ok", "-1", "Error al eliminar la categoría.");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
