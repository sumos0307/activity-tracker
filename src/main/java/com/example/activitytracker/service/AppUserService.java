package com.example.activitytracker.service;
import com.example.activitytracker.dto.LoginRequest;
import com.example.activitytracker.dto.LoginResponse;
import com.example.activitytracker.dto.RegisterRequest;
import com.example.activitytracker.exception.AuthenticationException;
import com.example.activitytracker.exception.EmailAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.activitytracker.entity.AppUser;
import com.example.activitytracker.entity.Role;
import com.example.activitytracker.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserService {
private final PasswordEncoder passwordEncoder;
private final AppUserRepository appUserRepository;
private final JwtService jwtService;
public AppUser register(RegisterRequest request){
    if(appUserRepository.existsByEmail(request.getEmail())){
        throw new EmailAlreadyExistsException("Email already exists");
    }
    AppUser user=new AppUser();
    user.setFullName(request.getFullName());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole(Role.USER);
    return appUserRepository.save(user);
}
public LoginResponse login(LoginRequest request) {
    AppUser user = appUserRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() ->
                    new AuthenticationException("Kullanıcı bulunamadı."));
    if (!passwordEncoder.matches(
            request.getPassword(),
            user.getPassword())) {
        throw new AuthenticationException("Şifre hatalı.");
    }
    String token = jwtService.generateToken(user.getEmail());
    return new LoginResponse(jwtService.generateToken(user.getEmail()));
}
}
