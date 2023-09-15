package com.driver.ui.controller;

import java.util.List;

import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.service.impl.FoodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foods")
public class FoodController {
	@Autowired
	FoodServiceImpl foodService;
	@GetMapping(path="/{id}")
	public FoodDetailsResponse getFood(@PathVariable String id) throws Exception{

		FoodDetailsResponse response=foodService.getFood(id);
		return response;
	}

	@PostMapping("/create")
	public FoodDetailsResponse createFood(@RequestBody FoodDetailsRequestModel foodDetails)throws Exception{
		FoodDetailsResponse response=foodService.createFood(foodDetails);
		return response;
	}

	@PutMapping(path="/{id}")
	public FoodDetailsResponse updateFood(@PathVariable String id, @RequestBody FoodDetailsRequestModel foodDetails) throws Exception{

		FoodDetailsResponse response=foodService.updateFood(id,foodDetails);
		return response;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteFood(@PathVariable String id) throws Exception{

		OperationStatusModel response=foodService.deleteFoo(id);
		return response;
	}
	
	@GetMapping(path = "")
	public List<FoodDetailsResponse> getFoods() {
       return foodService.get_Foods();
	}
}
