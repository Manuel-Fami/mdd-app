import { Component, OnDestroy, OnInit } from '@angular/core';
import { HeaderComponent } from '../common/header/header.component';
import { CommonModule } from '@angular/common';
import { LoginResponse } from '../../models/auth/login-response';
import { ArticleDetail } from '../../models/article';
import { Subscription } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { DashboardService } from '../../services/dashboard.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Comment } from '../../models/comment';

@Component({
  selector: 'app-details-article',
  standalone: true,
  imports: [HeaderComponent, CommonModule],
  templateUrl: './details-article.component.html',
  styleUrl: './details-article.component.scss',
})
export class DetailsArticleComponent implements OnInit, OnDestroy {
  currentUser!: LoginResponse;
  onError: boolean = false;
  isLoading: boolean = false;
  errorMessage: string = '';
  id!: number;
  article!: ArticleDetail;
  comments: Comment[] = [];
  commentForm!: FormGroup;
  subscriptions: Subscription = new Subscription();

  constructor(
    private authService: AuthService,
    private service: DashboardService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    const articleId = this.route.snapshot.paramMap.get('id');
    if (articleId) {
      this.id = +articleId;
      this.initForm();
      this.loadArticleDetails(this.id, this.currentUser?.token);
    }
  }

  loadArticleDetails(id: number, token: string): void {
    const articleDetailSub: Subscription = this.service
      .getArticle(id, token)
      .subscribe({
        next: (response: ArticleDetail) => {
          this.article = response;
          this.comments = this.article.comments;
        },
        error: (error: unknown) => {
          console.error(error);
          this.isLoading = false;
          this.onError = true;
          this.errorMessage =
            "Erreur : une erreur est survenue lors du chargement de l'article";
        },
      });

    this.subscriptions.add(articleDetailSub);
  }

  initForm(): void {
    this.commentForm = this.fb.group({
      content: ['', [Validators.required, Validators.maxLength(2500)]],
    });
  }

  onSubmit(): void {
    if (this.commentForm.valid) {
      this.isLoading = true;
      const createCommentSub: Subscription = this.service
        .addComment(this.id, this.commentForm.value, this.currentUser.token)
        .subscribe({
          next: () => {
            this.loadArticleDetails(this.id, this.currentUser.token);
            this.initForm();
            this.isLoading = false;
          },
          error: (error: unknown) => {
            console.error(error);
            this.isLoading = false;
            this.onError = true;
            this.errorMessage =
              "Erreur : une erreur est survenue lors de l'ajout du commentaire";
            this.isLoading = false;
          },
        });

      this.subscriptions.add(createCommentSub);
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }
}
