package com.plaxa.dto;

import com.plaxa.entity.PersonalInfo;
import com.plaxa.entity.Role;
import com.plaxa.validation.UpdateCheck;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public record UserCreateDto(
        @Valid
        PersonalInfo personalInfo,
        @NotNull
        String username,
        String info,
        @NotNull(groups = UpdateCheck.class)
        Role role,
        Integer companyId) {
}
