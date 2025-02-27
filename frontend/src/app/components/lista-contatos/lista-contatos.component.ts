import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

export interface Contato {
  id?: number;
  nome: string;
  email: string;
  celular: string;
  telefone: string;
  favorito: boolean;
}

@Component({
  selector: 'app-lista-contatos',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './lista-contatos.component.html'
})
export class ListaContatosComponent implements OnInit, OnDestroy {
  contatos: Contato[] = [];
  contatosFiltrados: Contato[] = [];
  formGroup!: FormGroup;
  termoBusca: string = ''; // Termo de busca
  mensagemSucesso: string | null = null; // Mensagem de sucesso
  mensagemErro: string | null = null; // Mensagem de erro
  private apiUrl = 'http://localhost:8080/api/contatos';
  private destroy$ = new Subject<void>();

  constructor(private http: HttpClient, private fb: FormBuilder, private router: Router) { }

  ngOnInit() {
    this.formGroup = this.fb.group({
      nome: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      celular: ['', [Validators.required, Validators.pattern(/^\d{10,11}$/)]],
      telefone: [''],
      favorito: [false]
    });

    this.carregarContatos();
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  carregarContatos() {
    const headers = this.getAuthHeaders();
    this.http.get<Contato[]>(this.apiUrl, { headers }).pipe(
      takeUntil(this.destroy$)
    ).subscribe({
      next: (data) => {
        console.log('Contatos carregados:', data);
        this.contatos = data;
        this.contatosFiltrados = [...this.contatos]; // Inicializa a lista filtrada
      },
      error: (error) => { console.error('Erro ao buscar contatos', error); }
    });
  }

  filtrarContatos() {
    if (!this.termoBusca) {
      // Se o termo de busca estiver vazio, exibe todos os contatos
      this.contatosFiltrados = [...this.contatos];
    } else {
      // Filtra os contatos com base no termo de busca
      const termo = this.termoBusca.toLowerCase();
      this.contatosFiltrados = this.contatos.filter(contato =>
        contato.nome.toLowerCase().includes(termo) ||
        contato.email.toLowerCase().includes(termo) ||
        contato.celular.includes(termo)
      );
    }
  }

  adicionarContato() {
    if (this.formGroup.invalid) {
      alert('Preencha todos os campos corretamente!');
      return;
    }

    const novoContato = this.formGroup.value;
    const headers = this.getAuthHeaders();

    this.http.post<Contato>(this.apiUrl, novoContato, { headers }).subscribe({
      next: (contatoCadastrado) => {
        this.mensagemSucesso = 'Contato adicionado com sucesso!';
        this.mensagemErro = null; // Limpa mensagem de erro
        this.contatos.push(contatoCadastrado);
        this.filtrarContatos(); // Atualiza a lista filtrada
        this.formGroup.reset();
      },
      error: (error) => {
        if (error.status === 409) {
          this.mensagemErro = 'Já existe um contato com este email.';
        } else {
          console.error('Erro ao adicionar contato:', error);
          this.mensagemErro = 'Contato já esta cadastrado!';
        }
        this.mensagemSucesso = null; // Limpa mensagem de sucesso
      }
    });
  }

  toggleFavorito(contato: Contato) {
    if (!contato.id) {
      console.error('ID do contato é indefinido.');
      return;
    }

    contato.favorito = !contato.favorito;
    const headers = this.getAuthHeaders();
    this.http.put(`${this.apiUrl}/${contato.id}`, contato, { headers }).subscribe({
      next: () => this.carregarContatos(),
      error: (error) => { console.error('Erro ao alterar favorito', error); }
    });
  }

  editarContato(contato: Contato) {
    if (!contato.id) {
      console.error('ID do contato é indefinido.');
      return;
    }

    const nome = prompt('Novo nome para o contato:', contato.nome);
    if (nome) {
      contato.nome = nome;
      const headers = this.getAuthHeaders();
      this.http.put(`${this.apiUrl}/${contato.id}`, contato, { headers }).subscribe({
        next: () => this.carregarContatos(),
        error: (error) => { console.error('Erro ao editar contato', error); }
      });
    }
  }

  excluirContato(id: number | undefined) {
    if (!id) {
      console.error('ID do contato é indefinido.');
      return;
    }

    if (confirm('Tem certeza que deseja excluir este contato?')) {
      const headers = this.getAuthHeaders();
      this.http.delete(`${this.apiUrl}/${id}`, { headers }).subscribe({
        next: () => this.carregarContatos(),
        error: (error) => { console.error('Erro ao excluir contato', error); }
      });
    }
  }

  fecharMensagem() {
    this.mensagemSucesso = null;
    this.mensagemErro = null;
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    if (!token || token === 'null' || token === 'undefined') {
      console.warn('⚠️ Nenhum token válido encontrado no localStorage.');
      return new HttpHeaders();
    }
    return new HttpHeaders()
      .set('Authorization', `Bearer ${token}`)
      .set('Content-Type', 'application/json');
  }
}
