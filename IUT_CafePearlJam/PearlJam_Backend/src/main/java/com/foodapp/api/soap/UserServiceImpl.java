package com.foodapp.api.soap;

import com.foodapp.api.soap.dto.LoginResult;
import com.foodapp.api.soap.dto.RegisterRequest;
import com.foodapp.api.soap.dto.UserInfo;
import com.foodapp.model.User;
import com.foodapp.model.UserRole;
import jakarta.jws.WebService;
import java.util.Optional;

/**
 * Implementation of User SOAP Service.
 */
@WebService(endpointInterface = "com.foodapp.api.soap.UserService", targetNamespace = "http://foodapp.com/ws", serviceName = "UserService")
public class UserServiceImpl implements UserService {
    
    private final com.foodapp.service.UserService userService;

    public UserServiceImpl(com.foodapp.service.UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserInfo registerUser(RegisterRequest request) {
        User user = userService.register(
            request.name, 
            request.email, 
            request.password, 
            request.phone, 
            UserRole.valueOf(request.role.toUpperCase())
        );
        return mapToInfo(user);
    }

    @Override
    public LoginResult login(String email, String password) {
        Optional<User> userOpt = userService.login(email, password);
        if (userOpt.isPresent()) {
            return new LoginResult(true, "Login successful", mapToInfo(userOpt.get()));
        }
        return new LoginResult(false, "Invalid credentials", null);
    }

    private UserInfo mapToInfo(User user) {
        return new UserInfo(
            user.getId(), 
            user.getName(), 
            user.getEmail(), 
            user.getPhone(), 
            user.getRole().name(), 
            user.isActive(), 
            user.getCreatedAt()
        );
    }
}
