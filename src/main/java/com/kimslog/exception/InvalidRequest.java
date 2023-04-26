package com.kimslog.exception;
/*
* status -> 400
*
*/
public class InvalidRequest extends KimslogException{
    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest(){
        super(MESSAGE);
    }

    @Override
    public int getStatusCode(){
        return 400;
    }
}
