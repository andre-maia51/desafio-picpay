package com.andre.picpaydesafio.domain.dto;

import com.andre.picpaydesafio.domain.enums.UserType;

import java.math.BigDecimal;

public record UserDTO(
        String firstName,
        String lastName,
        String document,
        BigDecimal balance,
        String email,
        String password,
        UserType userType
    ) {
}
