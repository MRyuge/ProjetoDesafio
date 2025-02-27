import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { ListaContatosComponent } from './components/lista-contatos/lista-contatos.component';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'contatos', component: ListaContatosComponent, canActivate: [authGuard] },
  { path: '**', redirectTo: 'login' }
];
