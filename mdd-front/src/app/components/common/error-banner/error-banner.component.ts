import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-error-banner',
  standalone: true,
  imports: [],
  templateUrl: './error-banner.component.html',
  styleUrl: './error-banner.component.scss',
})
export class ErrorBannerComponent {
  @Input() errorMessage: string = '';
}
