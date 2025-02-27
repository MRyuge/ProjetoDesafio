import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ContatoService {
  private apiUrl = 'http://localhost:8080/api/contatos';

  constructor(private http: HttpClient) {}

  getContatos(): Observable<any[]> {
    const headers = new HttpHeaders({ Authorization: `Bearer ${localStorage.getItem('token')}` });
    return this.http.get<any[]>(this.apiUrl, { headers });
  }

  adicionarContato(contato: any): Observable<any> {
    const headers = new HttpHeaders({ Authorization: `Bearer ${localStorage.getItem('token')}` });
    return this.http.post<any>(this.apiUrl, contato, { headers });
  }
}
