package java_web.kiemtracuoimuon.service.impl;

import java_web.kiemtracuoimuon.dto.AuthRequest;
import java_web.kiemtracuoimuon.dto.AuthResponse;
import java_web.kiemtracuoimuon.dto.RegisterRequest;
import java_web.kiemtracuoimuon.entity.Customer;
import java_web.kiemtracuoimuon.repository.CustomerRepository;
import java_web.kiemtracuoimuon.security.JwtUtil;
import java_web.kiemtracuoimuon.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final AuthenticationManager authenticationManager;
    private final CustomerRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public void registerCustomer(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email đã được sử dụng");
        }
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Số điện thoại đã được sử dụng");
        }
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new RuntimeException("Tên đăng nhập đã được sử dụng");
        }
        Customer customer = Customer.builder()
                .userName(request.getUserName())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .isLogin(false)
                .status(true)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(customer);
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        Customer customer = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new RuntimeException("Tên đăng nhập không tồn tại"));

        if (!passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            throw new RuntimeException("Mật khẩu không đúng");
        }

        customer.setIsLogin(true);

        String token = jwtUtil.generateToken(customer.getEmail());
        return new AuthResponse(token, token);
    }

    @Override
    public void logout(String token) {
        Customer customer = userRepository.findByEmail(jwtUtil.extractEmail(token))
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));
        customer.setIsLogin(false);
    }
}

