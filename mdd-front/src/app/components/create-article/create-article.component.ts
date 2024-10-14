import { Component, OnDestroy, OnInit } from '@angular/core';
import { HeaderComponent } from '../common/header/header.component';
import { CommonModule } from '@angular/common';
import { LoginResponse } from '../../models/auth/login-response';
import { Topic } from '../../models/topic';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Subscription } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { TopicService } from '../../services/topic.service';
import { DashboardService } from '../../services/dashboard.service';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { MessageResponse } from '../../models/message-response';
import { BackButtonComponent } from '../common/back-button/back-button.component';

@Component({
  selector: 'app-create-article',
  standalone: true,
  imports: [
    HeaderComponent,
    CommonModule,
    ReactiveFormsModule,
    BackButtonComponent,
    MatSnackBarModule,
  ],
  templateUrl: './create-article.component.html',
  styleUrl: './create-article.component.scss',
})
export class CreateArticleComponent implements OnInit, OnDestroy {
  currentUser!: LoginResponse;
  onError: boolean = false;
  isLoading: boolean = false;
  errorMessage: string = '';
  formSubmitted: boolean = false;
  topics: Topic[] = [];
  articleForm!: FormGroup;
  subscriptions: Subscription = new Subscription();

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    if (this.currentUser.token) {
      this.initForm();
      this.loadTopics();
    }
  }

  constructor(
    private authService: AuthService,
    private topicService: TopicService,
    private dashboardService: DashboardService,
    private snackBar: MatSnackBar,
    private router: Router,
    private fb: FormBuilder
  ) {}

  initForm(): void {
    this.articleForm = this.fb.group({
      topicId: ['', Validators.required],
      title: ['', [Validators.required, Validators.maxLength(200)]],
      description: ['', [Validators.required, Validators.maxLength(2500)]],
    });
  }

  loadTopics(): void {
    if (this.currentUser.token) {
      const topicsSubscription: Subscription = this.topicService
        .getAllTopics(this.currentUser.token)
        .subscribe({
          next: (response: Topic[]) => {
            this.topics = response;
          },
          error: (error) => {
            console.error(error);
            this.isLoading = false;
            this.onError = true;
            this.errorMessage =
              'Erreur : une erreur est survenue lors de la récupération des données';
          },
        });

      this.subscriptions.add(topicsSubscription);
    }
  }

  onSubmit(): void {
    console.log('debut');
    this.formSubmitted = true;

    if (this.articleForm.valid) {
      console.log('dans le if');
      this.isLoading = true;
      const articleSubscription: Subscription = this.dashboardService
        .createArticle(this.articleForm.value, this.currentUser.token)
        .subscribe({
          next: (response: MessageResponse) => {
            console.log("C'est ok");

            this.isLoading = false;
            this.snackBar.open(response.message, 'Fermer', {
              duration: 5000,
              verticalPosition: 'top',
            });

            this.router.navigate(['/dashboard']);
          },
          error: (error: unknown) => {
            console.error(error);
            this.isLoading = false;
            this.onError = true;
            this.errorMessage =
              "Une erreur est survenue au moment de la publication de l'article.";
          },
        });

      this.subscriptions.add(articleSubscription);
    }
    console.log('Not if');
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }
}
