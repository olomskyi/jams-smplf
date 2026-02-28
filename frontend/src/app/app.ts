import { Component, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Header } from './shared/header/header';
import { OidcSecurityService } from 'angular-auth-oidc-client';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, Header],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  protected readonly title = signal('frontend');

  constructor(private oidcSecurityService: OidcSecurityService) {}

    ngOnInit(): void {
    this.oidcSecurityService.checkAuth().subscribe(({ isAuthenticated, accessToken }) => {
      console.log('=== CHECK AUTH RESULT ===');
      console.log('Is Authenticated:', isAuthenticated);
      console.log('Access Token:', accessToken ? 'FOUND ✓' : 'NOT FOUND ✗');
      console.log('========================');
    });
  }
}
