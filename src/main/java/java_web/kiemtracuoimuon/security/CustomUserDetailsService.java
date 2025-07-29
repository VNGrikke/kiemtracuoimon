package java_web.kiemtracuoimuon.security;


import java_web.kiemtracuoimuon.entity.Customer;
import java_web.kiemtracuoimuon.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUserName(name).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản"));

        return CustomerPrinciple.builder()
                .id(customer.getId())
                .userName(customer.getUserName())
                .fullName(customer.getFullName())
                .password(customer.getPassword())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .enable(customer.getIsLogin())
                .authorities(List.of())
                .build();
    }
}
