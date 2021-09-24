package com.epam.rd.java.basic.finalProject.mapper.impl;

import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.entity.User;
import com.epam.rd.java.basic.finalProject.mapper.Mapper;


public class UserMapper implements Mapper<UserDTO, User> {
    @Override
    public User mapToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setPassword(userDTO.getPassword());
        user.setEncrypt(userDTO.getEncrypt());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        user.setStatusName(userDTO.getStatusName());
        return user;
    }

    @Override
    public UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setPassword(user.getPassword());
        userDTO.setEncrypt(user.getEncrypt());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setStatusName(user.getStatusName());
        return userDTO;
    }
}
