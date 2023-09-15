package com.driver.service.impl;

import com.driver.io.entity.OrderEntity;
import com.driver.io.entity.UserEntity;
import com.driver.io.repository.UserRepository;
import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.model.response.UserResponse;
import com.driver.service.UserService;
import com.driver.shared.dto.OrderDto;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto user) throws Exception {

        UserEntity userEntity=UserDtoToUserEntity(user);
        UserEntity saveUserEntity=userRepository.save(userEntity);

        return UserEntityToUserDto(saveUserEntity);



    }

    @Override
    public UserDto getUser(String email) throws Exception {
        UserEntity userEntity=userRepository.findByEmail(email);
        return UserEntityToUserDto(userEntity);
    }

    @Override
    public UserDto getUserByUserId(String userId) throws Exception {
        UserEntity userEntity=userRepository.findByUserId(userId);
        return UserEntityToUserDto(userEntity);
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) throws Exception {
       UserEntity userEntity=userRepository.findByUserId(userId);
        userEntity.setUserId(user.getUserId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());

        UserEntity finalUserEntity=userRepository.save(userEntity);

        return UserEntityToUserDto(finalUserEntity);

    }

    @Override
    public void deleteUser(String userId) throws Exception {

        long id=userRepository.findByUserId(userId).getId();
        userRepository.deleteById(id);

    }

    @Override
    public List<UserDto> getUsers() {

        List<UserEntity> userEntities=(List<UserEntity>)userRepository.findAll();
        List<UserDto> userDtos=new ArrayList<>();

        for(UserEntity u:userEntities)
        {
            userDtos.add(UserEntityToUserDto(u));
        }

        return userDtos;
    }

    public UserEntity UserDtoToUserEntity(UserDto userDto)
    {
       UserEntity userEntity=new UserEntity();
       userEntity.setUserId(userDto.getUserId());
       userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setEmail(userDto.getEmail());

        return userEntity;


    }

    public UserDto UserEntityToUserDto(UserEntity userEntity)
    {
        UserDto userDto=new UserDto();

        userDto.setId(userEntity.getId());
        userDto.setUserId(userEntity.getUserId());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userDto.getLastName());
        userDto.setEmail(userDto.getEmail());

        return userDto;
    }
    public UserResponse UserDtoToUserResponse(UserDto userDto)
    {
        UserResponse userResponse=new UserResponse();
        userResponse.setUserId(userDto.getUserId());
        userResponse.setFirstName(userDto.getFirstName());
        userResponse.setLastName(userDto.getLastName());
        userResponse.setEmail(userDto.getEmail());

        return userResponse;
    }

    public UserResponse get_User(String id) throws Exception {

        Optional<UserEntity> optionalUserEntity=userRepository.findById(Long.valueOf(id));
        if (optionalUserEntity.isPresent()) {
            UserDto userDto = UserEntityToUserDto(optionalUserEntity.get());
            return UserDtoToUserResponse(userDto);
        } else {
            throw new Exception("User not found");
        }

    }

    public UserResponse create_User(UserDetailsRequestModel userDetails)throws  Exception {

        UserDto userDto=new UserDto();
        userDto.setFirstName(userDetails.getFirstName());
        userDto.setLastName(userDetails.getLastName());
        userDto.setEmail(userDetails.getEmail());

        UserDto userDto1=createUser(userDto);

        return UserDtoToUserResponse(userDto1);


    }

    public UserResponse update_User(String id, UserDetailsRequestModel userDetails) throws Exception {
        Optional<UserEntity> optionalUserEntity=userRepository.findById(Long.valueOf(id));
        if (optionalUserEntity.isPresent()) {
            String userId = optionalUserEntity.get().getUserId();
            UserDto userDto = new UserDto();
            userDto.setFirstName(userDetails.getFirstName());
            userDto.setLastName(userDetails.getLastName());
            userDto.setEmail(userDetails.getEmail());

            UserDto userDto1 = updateUser(userId, userDto);
            return UserDtoToUserResponse(userDto1);
        } else {
            throw new Exception("User Not Found");
        }
    }

    public OperationStatusModel delete_User(String id) {

        OperationStatusModel operationStatusModel=new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.DELETE.toString());
        try {
            userRepository.deleteById(Long.valueOf(id));
        }
        catch (Exception e)
        {
            operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.toString());
            return operationStatusModel;
        }

        operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.toString());
        return operationStatusModel;

    }

    public List<UserResponse> get_Users() {
        List<UserDto> userDtos=getUsers();
        List<UserResponse> userResponses=new ArrayList<>();

        for(UserDto d:userDtos)
        {
            userResponses.add(UserDtoToUserResponse(d));
        }
        return userResponses;
    }
}
