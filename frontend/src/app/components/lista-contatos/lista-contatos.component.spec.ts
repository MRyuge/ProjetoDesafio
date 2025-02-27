import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { ListaContatosComponent } from './lista-contatos.component';

describe('ListaContatosComponent', () => {
  let component: ListaContatosComponent;
  let fixture: ComponentFixture<ListaContatosComponent>;
  let httpMock: HttpTestingController;
  let router: Router;

  const mockContatos = [
    { id: 1, nome: 'João Silva', email: 'joao@example.com', celular: '1234567890', telefone: '', favorito: false },
    { id: 2, nome: 'Maria Souza', email: 'maria@example.com', celular: '0987654321', telefone: '', favorito: true }
  ];

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListaContatosComponent],
      imports: [
        HttpClientTestingModule,
        ReactiveFormsModule,
        FormsModule
      ],
      providers: [
        {
          provide: Router,
          useValue: { navigate: jasmine.createSpy('navigate') }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ListaContatosComponent);
    component = fixture.componentInstance;
    httpMock = TestBed.inject(HttpTestingController);
    router = TestBed.inject(Router);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('deve criar o componente', () => {
    expect(component).toBeTruthy();
  });

  it('deve inicializar o formulário corretamente', () => {
    component.ngOnInit();
    expect(component.formGroup).toBeDefined();
    expect(component.formGroup.get('nome')).not.toBeNull();
    expect(component.formGroup.get('email')).not.toBeNull();
    expect(component.formGroup.get('celular')).not.toBeNull();
  });

  it('deve carregar contatos do backend ao inicializar', () => {
    component.ngOnInit();
    const req = httpMock.expectOne('http://localhost:8080/api/contatos');
    expect(req.request.method).toBe('GET');
    req.flush(mockContatos);

    expect(component['contatos'].length).toBe(2);
    expect(component['contatosFiltrados'].length).toBe(2);
  });

  it('deve adicionar um novo contato com sucesso', () => {
    const novoContato = {
      nome: 'Novo Contato',
      email: 'novo@example.com',
      celular: '1122334455',
      telefone: '',
      favorito: false
    };

    component.formGroup.setValue(novoContato);
    component.adicionarContato();

    const req = httpMock.expectOne('http://localhost:8080/api/contatos');
    expect(req.request.method).toBe('POST');
    req.flush({ ...novoContato, id: 3 });

    expect(component['contatos'].length).toBe(3);
    expect(component.mensagemSucesso).toBe('Contato adicionado com sucesso!');
    expect(component.mensagemErro).toBeNull();
  });

  it('deve exibir mensagem de erro ao tentar adicionar um contato duplicado', () => {
    const contatoDuplicado = {
      nome: 'João Silva',
      email: 'joao@example.com',
      celular: '1234567890',
      telefone: '',
      favorito: false
    };

    component.formGroup.setValue(contatoDuplicado);
    component.adicionarContato();

    const req = httpMock.expectOne('http://localhost:8080/api/contatos');
    expect(req.request.method).toBe('POST');
    req.flush('Email já cadastrado', { status: 409, statusText: 'Conflict' });

    expect(component.mensagemErro).toBe('Já existe um contato com este email.');
    expect(component.mensagemSucesso).toBeNull();
  });

  it('deve filtrar contatos com base no termo de busca', () => {
    component['contatos'] = mockContatos;
    component['contatosFiltrados'] = mockContatos;

    component.termoBusca = 'João';
    component.filtrarContatos();

    expect(component['contatosFiltrados'].length).toBe(1);
    expect(component['contatosFiltrados'][0].nome).toBe('João Silva');
  });

  it('deve limpar mensagens ao fechar', () => {
    component.mensagemSucesso = 'Sucesso!';
    component.mensagemErro = 'Erro!';

    component.fecharMensagem();

    expect(component.mensagemSucesso).toBeNull();
    expect(component.mensagemErro).toBeNull();
  });

  it('deve fazer logout e redirecionar para a página de login', () => {
    spyOn(localStorage, 'removeItem');
    const routerSpy = router.navigate as jasmine.Spy;

    component.logout();

    expect(localStorage.removeItem).toHaveBeenCalledWith('token');
    expect(routerSpy).toHaveBeenCalledWith(['/login']);
  });
});
