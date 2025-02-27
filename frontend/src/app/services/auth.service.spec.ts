import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';
  private tokenKey = 'token';

  constructor(private http: HttpClient, private router: Router) {}

  /** Realiza login e retorna o token */
  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, { username, password });
  }

  /** Armazena o token no localStorage */
  saveToken(token: string) {
    localStorage.setItem(this.tokenKey, token);
  }

  /** Retorna o token armazenado */
  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  /** Verifica se o usuário está autenticado */
  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  /** Realiza logout */
  logout() {
    localStorage.removeItem(this.tokenKey);
    this.router.navigate(['/login']);
  }
}
