package com.plaxa.service;

import com.plaxa.dao.UserRepository;
import com.plaxa.dto.UserCreateDto;
import com.plaxa.dto.UserReadDto;
import com.plaxa.entity.User;
import com.plaxa.mapper.Mapper;
import com.plaxa.mapper.UserCreateMapper;
import com.plaxa.mapper.UserReadMapper;
import com.plaxa.validation.UpdateCheck;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;

    @Transactional
    public Long create(UserCreateDto userCreateDto) {
//        validation
        var validatorFactory = Validation.buildDefaultValidatorFactory();
        var validator = validatorFactory.getValidator();
        var validationResult = validator.validate(userCreateDto, UpdateCheck.class);

        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }
//        map
        var userEntity = userCreateMapper.mapFrom(userCreateDto);
        return userRepository.save(userEntity).getId();
    }

    @Transactional
    public Optional<UserReadDto> findById(Long id) {
        return findById(id, userReadMapper);
    }

    @Transactional
    public <T> Optional<T> findById(Long id, Mapper<User, T> mapper) {
        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJpaHintName(), userRepository.getEntityManager().getEntityGraph("withCompany")
        );
        return userRepository.findById(id, properties)
                .map(mapper::mapFrom);
    }

    @Transactional
    public boolean delete(Long id) {
        var maybeUser = userRepository.findById(id);
        maybeUser.ifPresent(user -> userRepository.delete(user.getId()));
        return maybeUser.isPresent();
    }
}
