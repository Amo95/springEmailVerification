package com.services.emailverification.controller;

import com.services.emailverification.dto.requests.RegisterRequest;
import com.services.emailverification.dto.requests.VerificationRequest;
import com.services.emailverification.dto.responses.AllUserResponses;
import com.services.emailverification.dto.responses.RegisterResponse;
import com.services.emailverification.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "authenticate user", description = "Returns register response")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved cart"),
            @ApiResponse(responseCode = "404", description = "cart not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<RegisterResponse> authenticateUser(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/getUsers")
    @Operation(summary = "fetch all user auth info", description = "Returns all users auth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved cart"),
            @ApiResponse(responseCode = "404", description = "cart not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<AllUserResponses>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/verify")
    @Operation(summary = "verify user", description = "returns ok if user is verified")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved cart"),
            @ApiResponse(responseCode = "404", description = "cart not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> verifyUser(@RequestBody @Valid VerificationRequest request){
        userService.verify(request.getEmail(), request.getOtp());
        return ResponseEntity.ok(String.format("user is verified %s successfully", request.getEmail()));
    }
}
