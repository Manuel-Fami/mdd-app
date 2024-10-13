import { Component } from '@angular/core';
import { HeaderComponent } from '../common/header/header.component';
import { LoginResponse } from '../../models/auth/login-response';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { finalize, Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-profil',
  standalone: true,
  imports: [HeaderComponent, CommonModule, ReactiveFormsModule],
  templateUrl: './profil.component.html',
  styleUrl: './profil.component.scss',
})
export class ProfilComponent {
  currentUser!: LoginResponse;
  userForm!: FormGroup;
  isLoading: boolean = false;
  errorMessage: string = '';
  formSubmitted: boolean = false;

  constructor(
    private authService: AuthService,
    // private topicService: TopicService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    if (this.currentUser?.email && this.currentUser?.username) {
      this.initForm(this.currentUser);
    }
    // this.loadUserSubscription();
  }

  initForm(currentUser: LoginResponse): void {
    this.userForm = this.fb.group({
      email: [currentUser.email, [Validators.required, Validators.email]],
      username: [currentUser.username, Validators.required],
      password: ['', [Validators.required, this.passwordValidator()]],
    });
  }

  onSubmit(): void {
    this.formSubmitted = true;

    if (this.userForm.valid) {
      // this.isLoading = true;
      console.log('début');
      console.log(this.currentUser?.token);

      const updateCredsSub: Subscription = this.authService
        .updateCredentials(this.userForm.value, this.currentUser?.token)
        .pipe(
          finalize(() => {
            this.isLoading = false; // Cela sera exécuté à la fin, que ce soit un succès ou une erreur - (logo chargement).
          })
        )
        .subscribe({
          next: (response: LoginResponse) => {
            console.log('Bien');
            localStorage.removeItem('currentUser');
            localStorage.setItem('currentUser', JSON.stringify(response));
            // this.isLoading = false;

            this.snackBar.open(
              'Vos informations ont été mises à jour avec succès',
              'Fermer',
              {
                duration: 5000,
                verticalPosition: 'top',
              }
            );
          },
          error: (error) => {
            // this.isLoading = false;
            this.handleError(error);
          },
        });

      // this.subscriptions.add(updateCredsSub);
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

  public handleError(error: any): void {
    if (error.status === 409) {
      this.errorMessage = 'Erreur : un mail est déjà associé à ce compte.';
    } else {
      this.errorMessage =
        'Erreur : une erreur est survenue lors de la modification des données.';
    }

    this.snackBar.open(this.errorMessage, 'Fermer', {
      duration: 5000,
      verticalPosition: 'top',
    });
  }

  logOut(): void {
    this.authService.logout();
  }

  // loadUserSubscription(): void {
  //   const userSub : Subscription = this.topicService.getUserTopics(this.currentUser?.token).subscribe({
  //     next: (response: SubscriptionData[]) => {
  //       this.isLoading = false;
  //       this.userTopics = response;
  //     },
  //     error: (error) => {
  //       console.error(error);
  //       this.isLoading = false;
  //       this.onError = true;
  //       this.errorMessage = "Erreur : une erreur est survenue lors de la récupération des abonnements"
  //     },
  //   });

  //   this.subscriptions.add(userSub);
  // }
}
