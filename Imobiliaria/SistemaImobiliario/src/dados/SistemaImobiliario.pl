:- dynamic(imobiliaria/2).

  
  %Lista de dados da imobiliaria Baita Chão
  imobiliaria(baita_chao,[
                         vendedor(paulo,[cliente(2010,60,advogado,
                         [compra(casa,3,300000)]),
                         cliente(2012,39,veterinario,
                         [compra(casa,2,26000),compra(casa,1,150000)])]),
                         vendedor(marisa,[cliente(2011,45,militar,[compra(apartamento,2,250000)])])]).
 
%Lista de dados da imobiliaria Alegrete
  imobiliaria(alegrete,[
                       vendedor(maria,[cliente(
                             1010,55,veterinario,[compra(casa,3,300000),compra(apartamento,1,120)]),
                             cliente(1011,53,medico,[compra(apartamento,3,500000)])]),
                             vendedor(rosa,[cliente(1012,40,militar,[compra(apartamento,2,300000)])]),
                             vendedor(miguel,[cliente( 1013,65,advogado,[compra(apartamento,3,650000),
                                                          compra(apartamento,1,145000),
                                                          compra(apartamento,2,160000)])])]).
  %Lista de dados da imobiliaria Ibirapuita
  imobiliaria(ibirapuita,[
                       vendedor(ana,[cliente(3010,25,professora,[compra(apartamento,1,130000)])]),
                       vendedor(jose,[cliente(3011,35,professor,
                       [compra(casa,1,220000)])]),
                       vendedor(lucas,[cliente(3012,47,medico,
                       [compra(casa,2,250000),compra(apartamento,2,140000)])])]).

  %plus- Lista todos os vendedores de cada imobiliaria
  listarvendedores(X,LC):-
   imobiliaria(X,L),
   findall(C,member(vendedor(C,_),L),LC).
 
 %2.1 - listar clientes por codigo
  listarcodCliente(X,LCC):-
    imobiliaria(X,L),
    findall(V,member(vendedor(_,V),L),LV),
    flatten(LV,LC),
    findall(C,member(cliente(C,_,_,_),LC),LCC).
	
  %2.2-Listar dados
  listardadoscliente(X,C,D):-
    imobiliaria(X,L),
    findall(V,member(vendedor(_,V),L),LV),
    flatten(LV,LC),
    bagof((C,N,ID,P),member(cliente(C,N,ID,P),LC),D).

  %2.3 - listar vendas dos tipos imóveis por determinada imobiliária
  	listarvendas(X,LM):-
   	 imobiliaria(X,L),
    	findall(V,member(vendedor(_,V),L),LV),
  		  flatten(LV,LC),
    		findall(C,member(cliente(_,_,_,C),LC),LCC),
   			 flatten(LCC,LCCC),
   			 findall(M,member(compra(M,_,_),LCCC),LM).
  
 %2.4 - Listar por profissão
  listarprofissao(X,P,LCC):-
     imobiliaria(X,L),
     findall(V,member(vendedor(_,V),L),LV),
     flatten(LV,LC),
     bagof((C,I,P),member(cliente(C,I,P,_),LC),LCC).
  
  %2.5 - Valor médio de venda
  preco_medio(X,Med):-
      imobiliaria(X,L),
      findall(V,member(vendedor(_,V),L),LV),
      flatten(LV,LC),
      findall(C,member(cliente(_,_,_,C),LC),LP),
      flatten(LP,LP2),
      findall(P,member(compra(_,_,P),LP2),LP3),
      media(Med,LP3).
	
	somatorio(0,[]).
    somatorio(X,[Y|R]):- somatorio(S,R),
            X is S+Y.
  
    comprimento(0,[]).
    comprimento(N,[_|R]):- comprimento(N1,R),
            N is 1 + N1.

    media(X,L):- comprimento(N,L),
    somatorio(S,L),
    X is S/N.
 
 %2.6 - Alterar dados do cliente
	  alterarid(Cod,Idade,Prof,D):-
          retract(imobiliaria(X,L)),
              findall(K,member(vendedor(Name,K),L),L2),
                  flatten(L2,LC),
                      select(cliente(Cod,_,_,V),LC,L3),
                          append([cliente(Cod,Idade,Prof,V)],L3,L4),
                              append([vendedor(Name,L4)],L4,L5),
                                  assert(imobiliaria(X,L5)),
                                      listardadoscliente(_,Cod,D).                  

  %2.7 - Listar imobiliária com maior venda em ordem decrescente
 listarValorImobiliariadescrecente(NomeDaLista) :-
      findall(I-V, totalvendasimobiliaria(I, V), L),
      	transpose_pairs(L, X),
      		sort(0, @>=, X, NomeDaLista).  

	totalvendasimobiliaria(Imob,Preco):- 
           imobiliaria(Imob,L),
            findall(V,member(vendedor(_,V),L),LV),
              flatten(LV,LC),
                findall(C,member(cliente(_,_,_,C),LC),LCC),
                     flatten(LCC,LCCC),
                        findall(P,member(compra(_,_,P),LCCC),LISTA),
                          somatorio(Preco,LISTA).

  %2.8 - Listar vendedores por ordem crescente no valor de vendas
 listarvendedorescrescente(NomeDaLista) :-
    findall(I-V, totalvendasvendedores(I, V), L),
       	transpose_pairs(L, X),
        	sort(0, @=<, X, NomeDaLista).

  totalvendasvendedores(Vend,Preco):- 
         imobiliaria(_,L),
    		member(vendedor(Vend,_),L),
                 findall(V,member(vendedor(Vend,V),L),LN),
             	 flatten(LN,LC),
    				findall(C,member(cliente(_,_,_,C),LC),LCC),
                     flatten(LCC,LCCC),
                      findall(P,member(compra(_,_,P),LCCC),Lista),
    						somatorio(Preco,Lista).