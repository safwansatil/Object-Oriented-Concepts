package com.foodapp.api.soap;

import com.foodapp.api.soap.dto.LoginResult;
import com.foodapp.api.soap.dto.RegisterRequest;
import com.foodapp.api.soap.dto.UserInfo;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

/**
 * SOAP User Service interface.
 */
@WebService(name = "UserService", targetNamespace = "http://foodapp.com/ws")
public interface UserService {
    
    @WebMethod
    UserInfo registerUser(@WebParam(name = "request") RegisterRequest request);
    
    @WebMethod
    LoginResult login(@WebParam(name = "email") String email, @WebParam(name = "password") String password);
}
