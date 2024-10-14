import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../common/header/header.component';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { LoginResponse } from '../../models/auth/login-response';
import { Subscription } from 'rxjs';
import { Article } from '../../models/article';
import { Router } from '@angular/router';
import { DashboardService } from '../../services/dashboard.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [HeaderComponent, CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent implements OnInit {
  currentUser!: LoginResponse;
  onError: boolean = false;
  isLoading: boolean = false;
  errorMessage: string = '';
  articles: Article[] = [];
  ascendingOrder: boolean = true;
  articleSubscription: Subscription = new Subscription();

  constructor(
    private authService: AuthService,
    private service: DashboardService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    if (this.currentUser?.token) {
      this.loadUserArticles();
    }
  }

  loadUserArticles(): void {
    this.articleSubscription = this.service
      .getArticles(this.currentUser.token)
      .subscribe({
        next: (response: Article[]) => {
          this.articles = response.sort((a, b) => {
            const dateA = new Date(a.date);
            const dateB = new Date(b.date);
            return dateB.getTime() - dateA.getTime();
          });
        },
        error: (error: unknown) => {
          console.error(error);
          this.isLoading = false;
          this.onError = true;
          this.errorMessage =
            "Erreur : une erreur est survenue lors de la récupération de votre fil d'actualité";
        },
      });
  }

  create(): void {
    this.router.navigate(['/create']);
  }

  reverseOrder(): void {
    this.ascendingOrder = !this.ascendingOrder;

    if (this.ascendingOrder) {
      this.articles.sort((a, b) => {
        const dateA = new Date(a.date);
        const dateB = new Date(b.date);
        return dateB.getTime() - dateA.getTime();
      });
    } else {
      this.articles.sort((a, b) => {
        const dateA = new Date(a.date);
        const dateB = new Date(b.date);
        return dateA.getTime() - dateB.getTime();
      });
    }
  }

  onClickDetail(id: number) {
    this.router.navigate(['details', id]);
  }

  ngOnDestroy(): void {
    this.articleSubscription.unsubscribe();
  }
}
