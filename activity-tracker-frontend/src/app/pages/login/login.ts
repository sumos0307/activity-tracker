import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import {Router, RouterLink} from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule , RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  email = '';
  password = '';
  errorMessage = '';
  successMessage: string='';

  constructor(private http: HttpClient,private router: Router) {}
  ngOnInit(): void {
    const navigation = this.router.getCurrentNavigation();

    this.successMessage =
      navigation?.extras?.state?.['message'] || '';
  }
  login() {
    const body = {
      email: this.email,
      password: this.password
    };

    this.http
      .post<{ token: string }>(
        'https://activity-tracker-backend-4erw.onrender.com',
        body
      )
      .subscribe({
        next: (response) => {
          localStorage.setItem('token', response.token);
          this.errorMessage = '';
          console.log('Login başarılı');
          this.router.navigate(['/activities'])
        },
        error: (error) => {
          this.errorMessage =
            error.error?.message || 'Giriş başarısız.';
        }
      });
  }
}
