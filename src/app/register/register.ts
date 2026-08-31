import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router, RouterLink } from '@angular/router';
@Component({
  selector: 'app-register',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {

  name: string = '';
  email: string = '';
  password: string = '';
  confirmPassword: string='';
  errorMessage: string = '';

  showPassword: boolean = false;
  showConfirmPassword: boolean = false;

  get hasMinLength(): boolean {
    return this.password.length >= 8;
  }

  get hasUppercase(): boolean {
    return /[A-Z]/.test(this.password);
  }

  get hasLowercase(): boolean {
    return /[a-z]/.test(this.password);
  }

  get hasNumber(): boolean {
    return /\d/.test(this.password);
  }

  get hasSpecialChar(): boolean {
    return /[!@#$%^&*]/.test(this.password);
  }
  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  register(): void {

    if (this.password !== this.confirmPassword) {
      this.errorMessage = 'Şifreler eşleşmiyor.';
      return;
    }
    if (this.password.length < 8) {
      this.errorMessage = 'Şifre en az 8 karakter olmalıdır.';
      return;
    }
    const strongPassword =
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*]).+$/;

    if (!strongPassword.test(this.password)) {
      this.errorMessage =
        'Şifre en az bir büyük harf, bir küçük harf, bir rakam ve bir özel karakter içermelidir.';
      return;
    }
    const registerData = {
      fullName: this.name,
      email: this.email,
      password: this.password

    };

    this.http
      .post('https://activity-tracker-backend-4erw.onrender.com/api/users/register', registerData)
      .subscribe({

        next: () => {
          this.router.navigate(['/login'],{
          state:{
            message:'Kayıt başarılı! Şimdi hesabınıza giriş yapabilirsiniz.'
             }
          });
        },

        error: (error) => {
          console.error(error);

          this.errorMessage =
            error.error?.message ||
            'Kayıt işlemi başarısız oldu.';
        }

      });
  }
}
