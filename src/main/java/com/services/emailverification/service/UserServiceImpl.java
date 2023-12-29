package com.services.emailverification.service;

import com.services.emailverification.dto.requests.RegisterRequest;
import com.services.emailverification.dto.responses.AllUserResponses;
import com.services.emailverification.dto.responses.RegisterResponse;
import com.services.emailverification.model.Users;
import com.services.emailverification.repository.UsersRepository;
import com.services.emailverification.service.impl.EmailSenderService;
import com.services.emailverification.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final EmailSenderService emailSenderService;


    @Override
    public RegisterResponse register(RegisterRequest request) {
        Users findUser = usersRepository.findByEmail(request.getEmail());
        if(findUser != null && findUser.isVerified()){
            throw new RuntimeException("user already exists!");
        }
        String otp = generateOTP();

        Users users = Users.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .otp(otp)
                .build();


        sendVerification(request.getEmail(), otp);

        usersRepository.save(users);

        return RegisterResponse.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .build();
    }

    @Override
    public List<AllUserResponses> getAllUsers() {
        return usersRepository.findAll().stream()
                .map(this::mapAllUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void verify(String email, String otp) {
        Users user = usersRepository.findByEmail(email);
        if(user == null){
            throw new RuntimeException("user does not exist!");
        } else if(user.isVerified()) {
            throw new RuntimeException("user already exists!");
        } else if(otp.equals(user.getOtp())) {
            user.setVerified(true);
            usersRepository.save(user);
        }
    }

    private AllUserResponses mapAllUserResponse(Users user) {
        return AllUserResponses.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    private String generateOTP(){
        Random randomizer = new Random();
        return String.valueOf(1000 + randomizer.nextInt(9000));
    }

    private void sendVerification(String email, String otp) {
        String subject = "Email verification test";
        String body = String.format("your verification otp code is %s", otp);
        emailSenderService.sendMail(email, subject,body);
    }
}
