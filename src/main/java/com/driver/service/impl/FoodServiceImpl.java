package com.driver.service.impl;

import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class FoodServiceImpl implements FoodService {

    /*
    foodEntity-
    private long id;
    private String foodId;
    private String foodName;
    private float foodPrice;
    private String foodCategory;

    foodDto-
    private long id;
    private String foodId;
    private String foodName;
    private String foodCategory;
    private float foodPrice;  */


    @Autowired
    FoodRepository foodRepository;

    @Override
    public FoodDto createFood(FoodDto food) {
        FoodEntity foodEntity=new FoodEntity();
        foodEntity.setId(food.getId());
        String fId= UUID.randomUUID().toString();
        foodEntity.setFoodId(fId);
        foodEntity.setFoodName(food.getFoodName());
        foodEntity.setFoodCategory(food.getFoodCategory());
        foodEntity.setFoodPrice(food.getFoodPrice());

        //saving in db
        FoodEntity saveFoodEntity=foodRepository.save(foodEntity);

        food.setId(saveFoodEntity.getId());
        food.setFoodId(saveFoodEntity.getFoodId());

        return food;
    }

    @Override
    public FoodDto getFoodById(String foodId) throws Exception {

        //getting foodEntity from db by foodId
        FoodEntity foodEntity=foodRepository.findByFoodId(foodId);

        FoodDto foodDto=new FoodDto();

        foodDto.setId(foodEntity.getId());
        foodDto.setFoodId(foodEntity.getFoodId());
        foodDto.setFoodName(foodEntity.getFoodName());
        foodDto.setFoodCategory(foodEntity.getFoodCategory());
        foodDto.setFoodPrice(foodEntity.getFoodPrice());

        return foodDto;

    }

    @Override
    public FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws Exception {

        FoodEntity foodEntity=foodRepository.findByFoodId(foodId);


        foodEntity.setFoodId(foodDetails.getFoodId());
        foodEntity.setFoodName(foodDetails.getFoodName());
        foodEntity.setFoodCategory(foodDetails.getFoodCategory());
        foodEntity.setFoodPrice(foodDetails.getFoodPrice());

        //again saving in db
        FoodEntity saveFoodEntity=foodRepository.save(foodEntity);


        foodDetails.setId(saveFoodEntity.getId());
        foodDetails.setFoodId(saveFoodEntity.getFoodId());

        return  foodDetails;
    }

    @Override
    public void deleteFoodItem(String id) throws Exception {

        foodRepository.deleteById(Long.parseLong(id));


    }

    @Override
    public List<FoodDto> getFoods() {

        Iterable<FoodEntity>  foodEntityList= foodRepository.findAll();

        List<FoodDto> foodDtoList=new ArrayList<>();
        for(FoodEntity f:foodEntityList)
        {
            FoodDto foodDto=new FoodDto();

            foodDto.setId(f.getId());
            foodDto.setFoodId(f.getFoodId());
            foodDto.setFoodName(f.getFoodName());
            foodDto.setFoodCategory(f.getFoodCategory());
            foodDto.setFoodPrice(f.getFoodPrice());

            foodDtoList.add(foodDto);

        }
        return foodDtoList;

    }

    /*
    FoodRequestModel-

    private String foodName;
	private String foodCategory;
	private float foodPrice;

    FoodDetailsResponseM-
    private String foodId;
	private String foodName;
	private float foodPrice;
	private String foodCategory;
     */
    public FoodDetailsResponse getFood(String id) throws Exception {

       FoodDto foodDto=getFoodById(id);

       FoodDetailsResponse foodDetailsResponse=new FoodDetailsResponse();
       foodDetailsResponse.setFoodId(foodDto.getFoodId());
       foodDetailsResponse.setFoodName(foodDto.getFoodName());
       foodDetailsResponse.setFoodCategory(foodDto.getFoodCategory());
       foodDetailsResponse.setFoodPrice(foodDto.getFoodPrice());

       return foodDetailsResponse;



    }
    public FoodDetailsResponse createFood(FoodDetailsRequestModel foodDetails) throws Exception
    {
        //method Overloading

        FoodDto foodDto=new FoodDto();
        foodDto.setFoodName(foodDetails.getFoodName());
        foodDto.setFoodCategory(foodDetails.getFoodCategory());
        foodDto.setFoodPrice(foodDto.getFoodPrice());

        FoodDto finalFoodDto=createFood(foodDto);

        //converting to foodDto to FoodDetailsResponse

        FoodDetailsResponse foodDetailsResponse=new FoodDetailsResponse();
        foodDetailsResponse.setFoodId(finalFoodDto.getFoodId());
        foodDetailsResponse.setFoodName(finalFoodDto.getFoodName());
        foodDetailsResponse.setFoodCategory(finalFoodDto.getFoodCategory());
        foodDetailsResponse.setFoodPrice(finalFoodDto.getFoodPrice());

        return foodDetailsResponse;
    }

    public FoodDetailsResponse updateFood(String id, FoodDetailsRequestModel foodDetails) throws  Exception {

        FoodDto foodDto=new FoodDto();
        foodDto.setFoodName(foodDetails.getFoodName());
        foodDto.setFoodCategory(foodDetails.getFoodCategory());
        foodDto.setFoodPrice(foodDto.getFoodPrice());

        FoodDto finalFoodDto=updateFoodDetails(id,foodDto);

        //converting to foodDto to FoodDetailsResponse

        FoodDetailsResponse foodDetailsResponse=new FoodDetailsResponse();
        foodDetailsResponse.setFoodId(finalFoodDto.getFoodId());
        foodDetailsResponse.setFoodName(finalFoodDto.getFoodName());
        foodDetailsResponse.setFoodCategory(finalFoodDto.getFoodCategory());
        foodDetailsResponse.setFoodPrice(finalFoodDto.getFoodPrice());

        return foodDetailsResponse;



    }

    public OperationStatusModel deleteFoo(String id) throws Exception {

        OperationStatusModel operationStatusModel=new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.DELETE.toString());

        try {
            deleteFoodItem(id);
        }
        catch (Exception e)
        {
            operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.toString());
            return operationStatusModel;
        }

        operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.toString());
        return operationStatusModel;


    }

    public List<FoodDetailsResponse> get_Foods() {

        List<FoodDto> foodDtoList = getFoods();
        List<FoodDetailsResponse> foodDetailsResponseList = new ArrayList<>();
        for (FoodDto f : foodDtoList)
        {
            foodDetailsResponseList.add(new FoodDetailsResponse(f.getFoodId(),f.getFoodName(),
                    f.getFoodPrice(), f.getFoodCategory()));
        }
        return foodDetailsResponseList;
    }
}