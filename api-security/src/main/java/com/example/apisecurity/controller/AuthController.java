package com.example.apisecurity.controller;

import com.example.apisecurity.data.User;
import com.example.apisecurity.data.UserRepo;
import com.example.apisecurity.service.Jwt;
import com.example.apisecurity.service.Login;
import com.example.apisecurity.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    record RegisterRequest(@JsonProperty("first_name")String firstName,
                           @JsonProperty("last_name")String lastName,
                           String email,
                           String password,
                           @JsonProperty("confirm_password")String confirmPassword
    ){}
    record RegisterResponse(Long id,
                            @JsonProperty("first_name")String firstName,
                            @JsonProperty("last_name")String lastName,
                            String email){}
    record RefreshResponse(String token){}
    @PostMapping("/refresh")
    public RefreshResponse refresh(@CookieValue("refresh_token")
                                   String refreshToken){
        return new RefreshResponse(
                userService.refreshAccess(refreshToken)
                        .getAccessToken().getToken()
        );
    }

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest user){
        var u= userService.register(
                user.firstName,user.lastName,
                user.password,user.confirmPassword,user.email);
        return new RegisterResponse(
                u.getId(),
                u.getFirstName(),
                u.getLastName(),
                u.getEmail()
        );
    }
    record LoginRequest(String email,String password){}
    record LoginResponse(String token){}
    record LogoutResponse(String msg){}
    @PostMapping("/logout")
    public LogoutResponse logout(@CookieValue("refresh_token")String refreshToken,
                                 HttpServletResponse response){
        userService.logout(refreshToken);
        Cookie cookie = new Cookie("refresh_token", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return new LogoutResponse("successfully logout!");
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request,
                               HttpServletResponse response){
        var login=userService.login(request.email,request.password);
        Cookie cookie=new Cookie("refresh_token",
                login.getRefreshToken().getToken());
        cookie.setMaxAge(3600);
        cookie.setHttpOnly(true);
        cookie.setPath("/auth");
        response.addCookie(cookie);
        System.out.println("Expiration:"+ login.
                getAccessToken().
                getExpiredAt());


        return new LoginResponse(login.getAccessToken().getToken());
    }
    record UserResponse(Long id,
                        @JsonProperty("first_name")String firstName,
                        @JsonProperty("last_name")String lastName,
                        String email
    ){}
    @GetMapping("/user")
    public UserResponse user(HttpServletRequest request){
        var user =(User) request.getAttribute("user");
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }
}
