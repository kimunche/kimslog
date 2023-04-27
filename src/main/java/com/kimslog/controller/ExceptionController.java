package com.kimslog.controller;

import com.kimslog.exception.KimslogException;
import com.kimslog.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e){
        //log.info("e", e);
        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();

        for(FieldError fieldError :  e.getFieldErrors()){
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return response;
    }

    @ResponseBody
    //@ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(KimslogException.class) //어플리케이션이 커질수록 예외클래스는 계속 늘어남 => 비즈니스에 맞는 최상위 exception =>kimslogexception
    public ResponseEntity<ErrorResponse> kimslogException(KimslogException e){
        int statusCode = e.getStatusCode();
        //log.info("e", e);
        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        //응답 validation -> title: 제목에 바보를 포함할 수 없습니다.
        //errorResponse 에 생성자 만들어 kimslogException 에서 메세지 처리하게 수정
//        if (e instanceof InvalidRequest){
//            InvalidRequest invalidRequest = (InvalidRequest) e;
//            String fieldName = invalidRequest.getFieldName();
//            String message = invalidRequest.getMessage();
//            body.addValidation(fieldName, message);
//        }

        ResponseEntity<ErrorResponse> response = ResponseEntity.status(statusCode)
                .body(body);

        return response;
    }

}
