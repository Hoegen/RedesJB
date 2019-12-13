//--------------------------------------------------------------
// NOMES DOS RESPONSÁVEIS: Marco Aurélio Hoegen Martins
//--------------------------------------------------------------

#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include <malloc.h>
#include <string.h>
#include <iostream>

using namespace std;

// ######### ESCREVA O NROUSP DO PRIMEIRO INTEGRANTE AQUI
char* nroUSP1() {
    return("10723711");
}

// ######### ESCREVA O NROUSP DO SEGUNDO INTEGRANTE AQUI (OU DEIXE COM ZERO)
char* nroUSP2() {
    return("0000000");
}


// elemento da lista ligada a ser gerada
// observando que cada nó contém apenas um caractere (ch)
typedef struct estr {
        char ch;
        struct estr *prox;
} NO;


// funcao principal
NO* decodificar(char* entrada);

NO* gerarNo(char ch, NO* anterior);

void imprimirLista(NO* primeiro);

//------------------------------------------
// O EP consiste em implementar esta funcao
// e outras funcoes auxiliares que esta
// necessitar
//------------------------------------------

NO* decodificar(char* entrada) {
	
	NO* resp = NULL;
	NO* last = NULL;
	int i = 0;
	
	while(entrada[i] != '\0'){
		if(entrada[i] >= '0' && entrada[i] <= '9' && entrada[i+1] != '\0'){
			i++;
			for(int j = 0; j < entrada[i-1] - 48; j++){
				last = gerarNo(entrada[i], last);
				if(i == 1) resp = last;
			}
		}else{		
			last = gerarNo(entrada[i], last);
			if(i == 0){
				resp = last;
			}
		}
		i++;
	}
	
	return resp;
}

NO* gerarNo(char letra, NO* anterior){
	NO* novono = (NO*)malloc(sizeof(NO));
	if(anterior) anterior->prox = novono;
	novono->prox = NULL;
	novono->ch = letra;
	return novono;
}

void imprimirLista(NO* no){
	int limite = 0;
	while(no != NULL){
		cout << no->ch;
		no = no->prox;
		limite ++;
		if (limite >99) break;
	}
}

//---------------------------------------------------------
// use main() para fazer chamadas de teste ao seu programa
//---------------------------------------------------------
int main() {

	NO* p = NULL;

	// aqui vc pode incluir codigo para transformar 
	// o string entrada em uma lista ligada p

	// o EP sera testado com chamadas deste tipo
	NO* teste = NULL;
	char entrada[] = "1Li 11 vez e entendi! Vou tirar 1190";
	for(int i = 0; i < sizeof(entrada); i++){
		printf("%c", entrada[i]);
	}
	cout<<"\n";
	teste = decodificar(entrada);
	imprimirLista(teste);
	
	return 0;
}

// por favor nao inclua nenhum código abaixo da função main()
