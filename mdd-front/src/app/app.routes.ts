import { Routes } from '@angular/router';
import { HomeComponent } from './components/auth/home/home.component';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AuthGuard } from './guards/auth.guard';
import { ProfilComponent } from './components/profil/profil.component';
import { TopicsComponent } from './components/topics/topics.component';
import { DetailsArticleComponent } from './components/details-article/details-article.component';
import { CreateArticleComponent } from './components/create-article/create-article.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: 'profil',
    component: ProfilComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'topics',
    component: TopicsComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'details/:id',
    component: DetailsArticleComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'create',
    component: CreateArticleComponent,
    canActivate: [AuthGuard],
  },
  { path: '**', redirectTo: '' },
];
