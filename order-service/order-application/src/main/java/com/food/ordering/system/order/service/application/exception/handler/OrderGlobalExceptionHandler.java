package com.food.ordering.system.order.service.application.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.food.ordering.system.order.service.domain.exception.OrderDomainException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class OrderGlobalExceptionHandler {

	@ResponseBody
	@ExceptionHandler(value = {OrderDomainException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO handleException(OrderDomainException orderDomainException) {
		log.error(orderDomainException.getMessage(), orderDomainException);
		return ErrorDTO.builder().build();
	}

}