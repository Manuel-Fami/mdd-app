// import { Injectable } from '@angular/core';
// import {
//   HttpRequest,
//   HttpHandler,
//   HttpEvent,
//   HttpInterceptor,
//   HttpErrorResponse,
// } from '@angular/common/http';
// import { catchError, Observable, throwError } from 'rxjs';
// import { Router } from '@angular/router';
// import { AuthService } from '../services/auth.service';

// @Injectable()
// export class XsrfInterceptor implements HttpInterceptor {
//   constructor(private router: Router, private authService: AuthService) {}

//   intercept<T>(
//     request: HttpRequest<T>,
//     next: HttpHandler
//   ): Observable<HttpEvent<String>> {
//     return next.handle(request).pipe(
//       catchError((error: HttpErrorResponse) => {
//         if (error.status === 401 && error.error === 'Token has expired.') {
//           this.authService.logout();
//           localStorage.removeItem('currentUser');
//           this.router.navigate(['/login']);
//         }
//         return throwError(error);
//       })
//     );
//   }
// }
