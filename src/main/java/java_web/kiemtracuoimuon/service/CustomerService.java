package java_web.kiemtracuoimuon.service;


import java_web.kiemtracuoimuon.dto.AuthRequest;
import java_web.kiemtracuoimuon.dto.AuthResponse;
import java_web.kiemtracuoimuon.dto.RegisterRequest;

public interface CustomerService {
    void registerCustomer(RegisterRequest registerRequest);
    AuthResponse login(AuthRequest authRequest);
    void logout(String token);
}
