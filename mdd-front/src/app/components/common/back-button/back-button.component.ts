import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-back-button',
  standalone: true,
  imports: [],
  templateUrl: './back-button.component.html',
  styleUrl: './back-button.component.scss',
})
export class BackButtonComponent {
  constructor(private router: Router) {}

  goBack() {
    this.router.navigate(['..']); // Navigue vers la page précédente
  }
}
