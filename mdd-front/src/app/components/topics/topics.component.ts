import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../common/header/header.component';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-topics',
  standalone: true,
  imports: [HeaderComponent],
  templateUrl: './topics.component.html',
  styleUrl: './topics.component.scss',
})
export class TopicsComponent implements OnInit {
  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    console.log(this.authService.isLoggedIn());
  }
}
