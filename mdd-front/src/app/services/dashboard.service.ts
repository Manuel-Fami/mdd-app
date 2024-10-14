import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {
  Article,
  ArticleDetail,
  ArticleRequest,
  ArticleResponse,
} from '../models/article';
import { Observable } from 'rxjs';
import { MessageResponse } from '../models/message-response';
import { CommentRequest } from '../models/comment';

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  public path: string = 'http://localhost:8080/api/articles';
  public commentsPath: string = 'http://localhost:8080/api/comment';

  constructor(private httpClient: HttpClient) {}

  getArticles(token: string): Observable<Article[]> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.get<Article[]>(`${this.path}/all`, {
      headers: headers,
    });
  }

  getArticle(id: number, token: string): Observable<ArticleDetail> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.get<ArticleDetail>(`${this.path}/${id}`, {
      headers: headers,
    });
  }

  createArticle(
    request: ArticleRequest,
    token: string
  ): Observable<MessageResponse> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.post<MessageResponse>(
      `${this.path}/create`,
      request,
      { headers: headers }
    );
  }

  addComment(
    id: number,
    request: CommentRequest,
    token: string
  ): Observable<ArticleResponse> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.httpClient.post<ArticleResponse>(
      `${this.commentsPath}/${id}/create`,
      request,
      { headers: headers }
    );
  }
}
