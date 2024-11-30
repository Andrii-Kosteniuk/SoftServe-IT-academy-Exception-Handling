package com.softserve.itacademy;

import com.softserve.itacademy.exception.NullEntityReferenceException;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ValidationHandler {

	public <T, V> void onNullValidation(T entity, Class<V> clazz) {

		if (Objects.isNull(entity)) {
			throw new NullEntityReferenceException("%s cannot be 'null'".formatted(clazz.getSimpleName()));
		}
	}
}
