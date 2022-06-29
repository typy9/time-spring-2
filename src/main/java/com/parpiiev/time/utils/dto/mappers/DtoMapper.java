package com.parpiiev.time.utils.dto.mappers;

import org.springframework.stereotype.Component;

@Component
public interface DtoMapper<T, E> {

    T mapToDto(E e);

    E mapFromDto(T t);
}
