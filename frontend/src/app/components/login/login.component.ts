import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.component.html'
})
export class LoginComponent {
  username = '';
  password = '';
  errorMessage = '';

  constructor(private authService: AuthService) {}

  /** Método chamado ao enviar o formulário */
  onSubmit() {
    this.authService.login(this.username, this.password).subscribe({
      next: (response) => {
        this.authService.saveToken(response.token);
        window.location.href = '/contatos'; // Redireciona após login
      },
      error: (error) => {
        this.errorMessage = 'Credenciais inválidas!';
      },
      complete: () => console.log('Login finalizado') // Opcional
    });
  }
}
