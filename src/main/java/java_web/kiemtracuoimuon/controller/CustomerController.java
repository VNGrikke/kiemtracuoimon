package java_web.kiemtracuoimuon.controller;


import jakarta.validation.Valid;
import java_web.kiemtracuoimuon.dto.AuthRequest;
import java_web.kiemtracuoimuon.dto.AuthResponse;
import java_web.kiemtracuoimuon.dto.RegisterRequest;
import java_web.kiemtracuoimuon.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        customerService.registerCustomer(request);
        return ResponseEntity.ok("Đăng ký thành công!");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(customerService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody String token) {
        customerService.logout(token);
        return ResponseEntity.ok("Đăng xuất thành công!");
    }
}

