package com.softserve.itacademy.exception;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomErrorsUtils {
	
	Logger logger = LoggerFactory.getLogger(CustomErrorsUtils.class);
	
	public <T> void validateArgumentLogAndThrow(T argument, String exceptionMessage, String loggerMessage) throws NullEntityReferenceException, EntityNotFoundException {
		if(Objects.isNull(argument) ) {
			logger.error(loggerMessage);
			throw new NullEntityReferenceException(exceptionMessage);
		} else if((argument instanceof Optional<?> && ((Optional<?>) argument).isEmpty())) {
			logger.error(loggerMessage);
			throw new EntityNotFoundException(exceptionMessage);
		}
	}
}
