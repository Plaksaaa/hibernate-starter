package com.plaxa.dto;

import com.plaxa.entity.PersonalInfo;
import com.plaxa.entity.Role;

public record UserReadDto(Long id,
                          PersonalInfo personalInfo,
                          String username,
                          String info,
                          Role role,
                          CompanyReadDto company) {
}
