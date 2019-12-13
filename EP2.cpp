#include <iostream>
#include <stdio.h>
#include <malloc.h>
#include <stdlib.h>

using namespace std;
// elementos da matriz esparsa (use tambem se quiser criar listas auxiliares)
typedef struct estr {
        int info;
	int lin;
	int col;
        struct estr *proxC;   // ponteiro para a direita
        struct estr *proxL;   // ponteiro para baixo
} NO;

// matriz 10 x 10 (a linha e coluna 0 são desprezadas)
typedef struct {
	NO* LIN[11];
	NO* COL[11];
} LISTASCR;	



// funcao principal
void substituir(LISTASCR* m, int i, int j);




//funções auxiliares para testar o programa
void telaPrincipal();

void telaSubstituir(LISTASCR* m);

LISTASCR* inicializaMatriz();

NO* primeiroElemento(LISTASCR* m);

NO* proxElemento(NO* no, LISTASCR* m);

NO* buscarElemento(LISTASCR* m, int lin, int col);

NO* buscarElemento(LISTASCR* m, int info);

void imprimeMatriz(LISTASCR* m);

void limpaMatriz(LISTASCR* m);

void espaco();

void telaNovoElemento(LISTASCR* m);

bool novoElemento(LISTASCR* m, int col, int lin, int info);

void telaExcluirElemento(LISTASCR* m);

bool excluirElemento(LISTASCR* m, int lin, int col);

bool excluirElemento(LISTASCR* m, int info);

bool excluirElemento(LISTASCR* m, NO* excl);

void telaGerador(LISTASCR* m);

void geradorAleatorioDeElementos(LISTASCR*m, int numero);

int contadorDeElementos(LISTASCR*m);

void telaPreencher(LISTASCR* m);

bool preencher(LISTASCR*m, int lin, int col);

void testeInfinito(LISTASCR*m);

//------------------------------------------
// O EP consiste em implementar esta funcao
// e outras funcoes auxiliares que esta
// necessitar
//------------------------------------------
void substituir(LISTASCR* m, int i, int j) {
	NO* templ = NULL;
	NO* tempc = NULL;
	int min = 0, lin = i, col = j, infol, infoc;
	if(i < j){
		min = i;
	}else{
		min = j;
	}
	for(int k = 1; k < min; k++){
		templ = buscarElemento(m, i, k);
		tempc = buscarElemento(m, k, j);
		if(!templ && !tempc){cout << "testex";
			continue;
			}else{
				if((templ && !tempc)||(!templ && tempc)){
					if(templ){
						novoElemento(m, j, k, templ->info);cout << "testel";
						excluirElemento(m, templ);
					}else{
						novoElemento(m, k, i, tempc->info);cout << "testec";
						excluirElemento(m, tempc);
					}
				}else{cout<<"testeha";
					infol = templ->info;
					infoc = tempc->info;
					excluirElemento(m, templ);
					excluirElemento(m, tempc);
					novoElemento(m, k, i, infoc);
					novoElemento(m, j, k, infol); cout << "teste";					
				}
		}
	}

}


// por favor nao entregue sua função main() nem seu código de teste


void telaPrincipal(LISTASCR* m){
	int i = 0;
	cout << "Insira: \n1 - para inserir novo elemento na matriz\n2 - para excluir um elemento da matriz\n3 - para implementar a funcao de troca\n4 - para gerar elementos aleatorios\n5 - para limpar a matriz\n6 - para contar os elementos da matriz\n7 - para PREENCHER\n\n";
	
	imprimeMatriz(m);
	espaco();
	
	cin >> i;
	
	switch(i){
		case 1:
			telaNovoElemento(m);
			break;
		
		case 2:
			telaExcluirElemento(m);
			break;
			
		case 3:
			telaSubstituir(m);
			break;
			
		case 4:
			telaGerador(m);
			break;
			
		case 5:
			limpaMatriz(m);
			break;
			
		case 6:
			cout << contadorDeElementos(m) << " elementos, entre com qualquer valor para continuar.\n";
			cin >> i;
			break;
			
		case 7:
			telaPreencher(m);
			break;
			
		case 8:
			testeInfinito(m);
			break;
			
	}
	espaco();
	telaPrincipal(m);
}

void telaSubstituir(LISTASCR* m){
	int lin, col;
	cout << "digite a coluna da troca\n";
	cin >> col;
	cout << "digite a linha da troca\n";
	cin >> lin;
	substituir(m, lin, col);
}

void telaGerador(LISTASCR* m){
	int n;
	cout << "Quantos elementos voce gostaria de ter na matriz?\n";
	cin >> n;
	geradorAleatorioDeElementos(m, n);
}

NO* primeiroElemento(LISTASCR* m){
	for(int i = 1; i <= 10; i++){
		if(m->LIN[i]){
			NO* to_tradition = m->LIN[i];
			return to_tradition;
		}
	}
}

NO* buscarElemento(LISTASCR* m, int lin, int col){
	NO* it = m->LIN[lin];
	while(it){
		if(it->col == col) return it;
		it = it->proxC;
	}
	return NULL;
}

NO* buscarElemento(LISTASCR* m, int info){
	NO* it = primeiroElemento(m);
	while(it){
		if(it->info == info) return it;
		it = proxElemento(it, m);
	}
	return NULL;
}

LISTASCR* inicializaMatriz(){
	LISTASCR *m;
	m = (LISTASCR*)malloc(sizeof(LISTASCR));
	
	for(int i = 0; i <= 10; i++){
		m->COL[i] = NULL;
		m->LIN[i] = NULL;
	}
	
	return m;
}

NO* proxElemento(NO* no, LISTASCR* m){
	if(!no){
		for(int i = 1; i <= 10; i++){
			if(m->LIN[i])return m->LIN[i];
		}
	}
	if(no->proxC){
		return no->proxC;
	}else{
		for(int i = no->lin + 1; i <= 10; i++){
			if(m->LIN[i]){
				return m->LIN[i];
			}
		}
	}
	return NULL;
}

void imprimeMatriz(LISTASCR* m){
	NO* it = primeiroElemento(m);
	int col = 0, lin = 1, esc = 0;
	
	for(int i = 0; i<=10; i++){
		cout << "  " << i << " ";
	}cout << "\n1  - ";
	
	while(it){
		
		for(int i = lin; i < it->lin; i ++){
			for(int j = col; j < 10; j++){
				cout << "N/A ";
			}
			if(i+1 == 10){
				cout << "\n" << i+1 << " - ";
			}else {
				cout << "\n" << i+1 << "  - ";
			}
			col = 0;
		}
		for(int i = col+1; i < it->col; i ++){
			printf("N/A ");
		} 
		cout << " " << it->info << "  ";
		
		if(it){
			col = it->col;
			lin = it->lin;			
		}
		it = proxElemento(it, m);
		esc++;
		if(esc > 102) break;
	}
	
	if(col <= 10 && lin <= 10){
		for(int i = col; i < 10; i++)
			printf("N/A ");
		cout << '\n';
		
		if(lin+1 == 10){
			cout << lin+1 << " - ";
		}else if(lin+1 < 10){
			cout << lin+1 << "  - ";
		}
	}
	for(int i = lin; i < 10; i++){
		for(int j = 0; j < 10; j++){
			printf("N/A ");
		}
		if(i == 9)break;
		if(i == 8){
			cout << '\n' << i+2 << " - ";
			continue;
		}
		cout << '\n' << i+2 << "  - ";
	}
}

void telaNovoElemento(LISTASCR* m){
	int lin = 0, col = 0, info = 0;
	cout << "\nDigite a linha do novo elemento \n";
	cin >> lin;
	cout << "\nDigite a coluna do novo elemento \n";
	cin >> col;
	cout << "\nDigite o valor do novo elemento \n";
	cin >> info;
	novoElemento( m, col, lin, info);
}

bool novoElemento(LISTASCR* m, int col, int lin, int info){
	NO* teste = buscarElemento(m, lin, col);
	if(teste){
		return false;
	}
	
	int temp = 0;
	NO* novono = (NO*)malloc(sizeof(NO));
	
	novono->col = col;
	novono->lin = lin;
	novono->info = info;
	
	//este laço coloca o novo nó na sua coluna da matriz
	NO* it = m->COL[col];
	if(!it || m->COL[col]->lin > lin){
		m->COL[col] = novono;
		novono->proxL = it;
	}else{
		while(true){
			if(it->proxL){
				temp = it->proxL->lin;
			}
			if((temp > novono->lin && it->lin < novono->lin) || it->proxL == NULL){
				novono->proxL = it->proxL;
				it->proxL = novono;
				break;
			}else if (novono->lin == it->lin){
				cout << "erro col\n"; 
				return false;
				break;
			}
			it = it->proxL;
		}
	}
	
	
	temp = 0;
	
	//este laço coloca o novo nó na sua respectiva linha
	it = m->LIN[lin];
	if(!it || m->LIN[lin]->col > col){
		m->LIN[lin] = novono;
		novono->proxC = it; cout;
	}else{
		while(true){
			if(it->proxC){
				temp = it->proxC->col;
			}
			if((temp > novono->col && it->col < novono->col) || it->proxC == NULL){
				novono->proxC = it->proxC;
				it->proxC = novono;
				break;
			}else{
				if (novono->col == it->col){
					cout << "erro lin\n"; 
					return false;
					break;
					}
			}
			it = it->proxC;
		}
	}
	return true;
}

void telaExcluirElemento(LISTASCR* m){
	int lin = 0, col = 0;
	cout << "\nDigite a linha do elemento que você quer excluir\n";
	cin >> lin;
	cout << "\nDigite a coluna do novo elemento que você quer excluir\n";
	cin >> col;
	excluirElemento(m, lin, col);
}

bool excluirElemento(LISTASCR* m, int lin, int col){
	NO* excl = buscarElemento(m, lin, col);
	
	NO* it = m->LIN[lin];
	
	if(it == excl){
		m->LIN[lin] = excl->proxC;
		it = NULL;
	}
	while(it){
		if(it->proxC == excl){
			it->proxC = excl->proxC;
			break;
		}
		it = it->proxC;
	}
	
	
	it = m->COL[col];
	if(it == excl){
		m->COL[col] = excl->proxL;
		it = NULL;
	}
	while(it){
		if(it->proxL == excl){
			it->proxL = excl->proxL;
			break;
		}
		it = it->proxL;
	}
	free(excl);
	return true;
}

bool excluirElemento(LISTASCR* m, int info){
	NO* temp = buscarElemento(m, info);
	int lin = temp->lin, col = temp->col;
	if(excluirElemento(m, lin, col))return true;
}

bool excluirElemento(LISTASCR* m, NO* excl){
	return excluirElemento(m, excl->lin, excl->col);
}

void geradorAleatorioDeElementos(LISTASCR* m, int numero){
	int lin, col, quant = contadorDeElementos(m);
	
	for(int i = quant; i < numero; i++){
		
		lin = rand()%10 + 1;
		col = rand()%10 + 1;
		
		if(buscarElemento(m, lin, col)){
			i--;
			continue;
		}else{
			if(novoElemento(m, col, lin, rand() % 10)){
				if(buscarElemento(m, lin, col)){
					cout << "Elemento numero " << i+1 << " gerado!\n";
				}else{
					cout << "ERRO! SEU FODIDO!\n";
					i--;
					continue;
				}
			} 
		}		
	}
}

int contadorDeElementos(LISTASCR* m){
	int to_tradition = 0;
	NO* it = primeiroElemento(m);
	while(it){
		to_tradition++;
		it = proxElemento(it, m);
	}
	return to_tradition;
}

void telaPreencher(LISTASCR* m){
	int lin, col;
	cout << "Digite a linha em que voce quer a funcao aplicada\n";
	cin >> lin;
	cout << "Digite a coluna em que voce quer a funcao aplicada\n";
	cin >> col;
	preencher(m, lin, col);
}

bool preencher(LISTASCR*m, int lin, int col){
		
	if(buscarElemento(m, lin, col)){
		return false;
	}
	
	novoElemento(m, col, lin, 0);
	
	for(int i = -1; i <= 1; i++){
		if(lin+i > 0 && lin+i < 11)	preencher(m, lin + i, col);
		if(col+i > 0 && col+1 < 11)	preencher(m, lin, col + i);
	}
	return true;
}

void limpaMatriz(LISTASCR* m){
	NO* it = primeiroElemento(m);
	NO* ant = NULL;
	while(it){
		ant = it;
		it = proxElemento(it, m);
		excluirElemento(m, ant);
	}
}

void espaco(){
	for(int i = 0; i < 8; i++) printf("\n");
}

void testeInfinito(LISTASCR*m){
	while(true){
		geradorAleatorioDeElementos(m, rand()%100+1);		
		imprimeMatriz(m);
		espaco();
		substituir(m, rand()%10+1, rand()%10+1);
		espaco();
		imprimeMatriz(m);
		limpaMatriz(m);
	}
}


int main(){
	
	LISTASCR* m = inicializaMatriz();
	telaPrincipal(m);
	
	return 0;
}





