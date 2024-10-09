import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { Subscription } from 'rxjs';
import { AuthService } from '../../../services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { SignupResponse } from '../../../models/auth/signup-response';
import { HttpErrorResponse } from '@angular/common/http';
import { HeaderComponent } from '../../common/header/header.component';
import { BackButtonComponent } from '../../common/back-button/back-button.component';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ErrorBannerComponent } from '../../common/error-banner/error-banner.component';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    HeaderComponent,
    BackButtonComponent,
    ReactiveFormsModule,
    CommonModule,
    ErrorBannerComponent,
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  onError: boolean = false;
  errorMessage: string = '';
  formSubmitted: boolean = false;
  registerSubscription: Subscription = new Subscription();

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm(): void {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, this.passwordValidator()]],
    });
  }
  onSubmit(): void {
    this.formSubmitted = true;
    if (this.registerForm.valid) {
      // this.isLoading = true;
      const loginData = this.registerForm.value;
      console.log(loginData);

      this.registerSubscription = this.authService
        .register(this.registerForm.value)
        .subscribe({
          next: (response: SignupResponse) => {
            // this.isLoading = false;
            this.snackBar.open(response.message, 'Fermer', {
              duration: 5000,
              verticalPosition: 'top',
            });
            this.router.navigate(['/login']);
          },
          error: (error: unknown) => {
            console.log('Ici Erreur');
            console.log(error);
            // this.isLoading = false;
            this.handleError(error);
          },
        });
    }
  }

  passwordValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: boolean } | null => {
      const value = control.value;

      if (!value) {
        return { passwordInvalid: true };
      }

      const passwordRegex =
        /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\W\_])[0-9a-zA-Z\W\_]{8,}$/;
      const valid = passwordRegex.test(value);

      return valid ? null : { passwordInvalid: true };
    };
  }

  private handleError(error: unknown): void {
    if (error instanceof HttpErrorResponse) {
      if (error.status === 409) {
        this.errorMessage = 'Erreur : un mail est déjà associé à ce compte.';
      } else {
        this.errorMessage =
          "Erreur : une erreur est survenue lors de l'inscription de l'utilusateur.";
      }
    } else {
      this.errorMessage =
        'Erreur : une erreur inattendue est survenue. Veuillez réessayer plus tard.';
    }

    this.onError = true;
  }
}
