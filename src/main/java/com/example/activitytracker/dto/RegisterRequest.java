package com.example.activitytracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "İsim boş olamaz.")
    private String fullName;
    @NotBlank(message = "Email boş olamaz.")
    @Email(message = "Geçerli bir email adresi giriniz.")
    private String email;
    @NotBlank(message = "Şifre boş olamaz.")
    @Size(min = 8,message = "Şifre en az 8 karakter olmalıdır.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
    message = "Şifre en az 1 büyük harf , 1 küçük harf ,1 rakam ve özel karakter içermelidir.")
    private String password;
}
