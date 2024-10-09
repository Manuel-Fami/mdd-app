import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../../common/header/header.component';
import { BackButtonComponent } from '../../common/back-button/back-button.component';
import { AuthService } from '../../../services/auth.service';
import { LoginResponse } from '../../../models/auth/login-response';
import { Subscription } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule,
    HeaderComponent,
    BackButtonComponent,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup; // Formulaire réactif
  loginSubscription: Subscription = new Subscription();
  errorMessage: string = '';
  onError: boolean = false;

  constructor(
    private fb: FormBuilder, // FormBuilder pour créer le formulaire
    private authService: AuthService,
    private router: Router // Pour la navigation après la connexion
  ) {}

  ngOnInit() {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const loginData = this.loginForm.value; // Récupère les valeurs du formulaire
      console.log('Login form submitted', loginData);

      this.loginSubscription = this.authService.login(loginData).subscribe({
        next: (response: LoginResponse) => {
          localStorage.setItem('currentUser', JSON.stringify(response));
          this.router.navigate(['/dashboard']);
          // this.isLoading = false;
          console.log(response);
        },
        error: (error: unknown) => {
          this.handleError(error);
          // this.isLoading = false;
        },
      });
    }
  }

  handleError(error: unknown): void {
    if (error instanceof HttpErrorResponse) {
      switch (error.status) {
        case 404:
          this.errorMessage =
            "Erreur : aucun compte n'est associé à cet email. Veuillez créer un compte.";
          break;
        case 400:
          this.errorMessage = 'Erreur : identifiants invalides.';
          break;
        default:
          this.errorMessage =
            'Une erreur est survenue. Veuillez réessayer ultérieurement.';
      }
      console.error('Login failed', error);
    } else if (typeof error === 'string') {
      this.errorMessage = error;
    } else {
      this.errorMessage =
        'Une erreur inattendue est survenue. Veuillez réessayer.';
    }
    this.onError = true;
  }
}
