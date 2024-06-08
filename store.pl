% Base de dados
:- dynamic(cliente/4).
:- dynamic(custo_envio/2).
:- dynamic(categoria/1).
:- dynamic(desconto_categoria/2).
:- dynamic(desconto_lealdade/2).
:- dynamic(item/5).
:- dynamic(compra/7).

% Base de factos
% Clientes
cliente(1, 'Alice', 'Aveiro', 3).
cliente(2, 'Beatriz', 'Braga', 1).
cliente(3, 'Carlos', 'Coimbra', 2).
cliente(4, 'Diogo', 'Lisboa', 4).
cliente(5, 'Eva', 'Porto', 1).
cliente(6, 'Francisca', 'Faro', 3).
cliente(7, 'Guilhermina', 'Viseu', 5).

% Custos de Envio
custo_envio('Braga', 2.5).
custo_envio('Coimbra', 5).
custo_envio('Faro', 15).
custo_envio('Viseu', 3).

% Categorias
categoria('Wands').
categoria('Potions').
categoria('Enchanted Books').
categoria('Crystals').
categoria('Accessories').
categoria('Scrolls').
categoria('Ingredients').

% Descontos por Categoria
desconto_categoria('Accessories', 0).
desconto_categoria('Scrolls', 20).
desconto_categoria('Ingredients', 5).
desconto_categoria('Potions', 3).

% Itens em Inventário
item(6, 'Standard Wand', 'Wands', 20.0, 100).
item(7, 'Love Potion', 'Potions', 10.0, 50).
item(8, 'Advanced Spellbook', 'Enchanted Books', 15.0, 30).
item(9, 'Crystal of Magic Vision', 'Crystals', 30.0, 20).
item(10, 'Flying Broomstick', 'Accessories', 50.0, 10).
item(11, 'Enchanted Scroll', 'Scrolls', 8.0, 50).
item(12, 'Fairy Dust', 'Ingredients', 5.0, 100).

% Histórico de Compras
compra(1, '20/05/2024', 50, 5, 0, 5, 50).
compra(2, '21/05/2024', 30, 3, 1, 3, 29).
compra(3, '22/05/2024', 40, 4, 0, 4, 40).
compra(4, '23/05/2024', 60, 6, 2.5, 6, 57.5).
compra(5, '23/05/2024', 25, 2.5, 0, 2.5, 25).
compra(6, '25/05/2024', 35, 3.5, 2, 3.5, 33).
compra(7, '26/05/2024', 75, 7.5, 0, 7.5, 75).
compra(3, '27/05/2024', 45, 4.5, 0, 4.5, 45).
compra(4, '28/05/2024', 55, 5.5, 10, 5, 44.5).
compra(1, '28/05/2024', 60, 6, 0, 6, 60).

% Regras
% Adicionar um novo histórico de compra
adicionar_compra(IDCliente, Data, Valor, DescontoCategoria, DescontoLealdade, CustoEnvio, Total) :-
    assertz(compra(IDCliente, Data, Valor, DescontoCategoria, DescontoLealdade, CustoEnvio, Total)).

% Ver todas as vendas de uma determinada data (Histórico completo)
vendas_por_data(Data, Vendas) :-
    findall(
        [IDCliente, Data, Valor, DescontoCategoria, DescontoLealdade, CustoEnvio, Total],
        compra(IDCliente, Data, Valor, DescontoCategoria, DescontoLealdade, CustoEnvio, Total),
        Vendas
    ).

% Ver todas as vendas para um determinado cliente (Histórico completo)
vendas_por_cliente(IDCliente, Vendas) :-
    findall(
        [IDCliente, Data, Valor, DescontoCategoria, DescontoLealdade, CustoEnvio, Total],
        compra(IDCliente, Data, Valor, DescontoCategoria, DescontoLealdade, CustoEnvio, Total),
        Vendas
    ).

% Ver todas as vendas de um determinado distrito (Histórico completo)
vendas_por_distrito(Distrito, Vendas) :-
    findall(
        [IDCliente, Data, Valor, DescontoCategoria, DescontoLealdade, CustoEnvio, Total],
        (
            cliente(IDCliente, _, Distrito, _),
            compra(IDCliente, Data, Valor, DescontoCategoria, DescontoLealdade, CustoEnvio, Total)
        ),
        Vendas
    ).

% Ver os totais das vendas de um determinado distrito: Valor (Sem descontos), Desconto de Categoria, Desconto Lealdade, Custo de Envio, Total
totais_vendas_por_distrito(Distrito, ValorTotal, DescontoCategoriaTotal, DescontoLealdadeTotal, CustoEnvioTotal, TotalTotal) :-
    findall(
        Valor,
        (cliente(IDCliente, _, Distrito, _), compra(IDCliente, _, Valor, _, _, _, _)),
        ValorLista
    ),
    sum_list(ValorLista, ValorTotal),
    findall(
        DescontoCategoria,
        (cliente(IDCliente, _, Distrito, _), compra(IDCliente, _, _, DescontoCategoria, _, _, _)),
        DescontoCategoriaLista
    ),
    sum_list(DescontoCategoriaLista, DescontoCategoriaTotal),
    findall(
        DescontoLealdade,
        (cliente(IDCliente, _, Distrito, _), compra(IDCliente, _, _, _, DescontoLealdade, _, _)),
        DescontoLealdadeLista
    ),
    sum_list(DescontoLealdadeLista, DescontoLealdadeTotal),
    findall(
        CustoEnvio,
        (cliente(IDCliente, _, Distrito, _), compra(IDCliente, _, _, _, _, CustoEnvio, _)),
        CustoEnvioLista
    ),
    sum_list(CustoEnvioLista, CustoEnvioTotal),
    findall(
        Total,
        (cliente(IDCliente, _, Distrito, _), compra(IDCliente, _, _, _, _, _, Total)),
        TotalLista
    ),
    sum_list(TotalLista, TotalTotal).

% Ver os totais das vendas de uma determinada data: Valor (Sem descontos), Desconto de Categoria, Desconto Lealdade, Custo de Envio, Total
totais_vendas_por_data(Data, ValorTotal, DescontoCategoriaTotal, DescontoLealdadeTotal, CustoEnvioTotal, TotalTotal) :-
    findall(
        Valor,
        compra(_, Data, Valor, _, _, _, _),
        ValorLista
    ),
    sum_list(ValorLista, ValorTotal),
    findall(
        DescontoCategoria,
        compra(_, Data, _, DescontoCategoria, _, _, _),
        DescontoCategoriaLista
    ),
    sum_list(DescontoCategoriaLista, DescontoCategoriaTotal),
    findall(
        DescontoLealdade,
        compra(_, Data, _, _, DescontoLealdade, _, _),
        DescontoLealdadeLista
    ),
    sum_list(DescontoLealdadeLista, DescontoLealdadeTotal),
    findall(
        CustoEnvio,
        compra(_, Data, _, _, _, CustoEnvio, _),
        CustoEnvioLista
    ),
    sum_list(CustoEnvioLista, CustoEnvioTotal),
    findall(
        Total,
        compra(_, Data, _, _, _, _, Total),
        TotalLista
    ),
    sum_list(TotalLista, TotalTotal).

% Saber o Distrito onde foram dados mais descontos (Categoria + Lealdade)
distrito_com_maior_desconto_total(Distrito) :-
    findall(
        [DescontoTotal, D],
        (
            cliente(_, _, D, _),
            findall(
                DescontoCategoria,
                (
                    cliente(IDCliente, _, D, _),
                    compra(IDCliente, _, _, DescontoCategoria, _, _, _)
                ),
                DescontoCategoriaLista
            ),
            sum_list(DescontoCategoriaLista, DescontoCategoriaTotal),
            findall(
                DescontoLealdade,
                (
                    cliente(IDCliente, _, D, _),
                    compra(IDCliente, _, _, _, DescontoLealdade, _, _)
                ),
                DescontoLealdadeLista
            ),
            sum_list(DescontoLealdadeLista, DescontoLealdadeTotal),
            DescontoTotal is DescontoCategoriaTotal + DescontoLealdadeTotal
        ),
        DescontoTotalLista
    ),
    max_member([_, Distrito], DescontoTotalLista).

% Ver os itens em inventário (Todas as informações)
itens_inventario(ItensLista) :-
    findall(
        [IDItem, Nome, Categoria, Custo, Inventario],
        item(IDItem, Nome, Categoria, Custo, Inventario),
        ItensLista
    ).

% Ver os itens em inventário de uma determinada categoria
itens_inventario_por_categoria(Categoria, ItensLista) :-
    findall(
        [IDItem, Nome, Categoria, Custo, Inventario],
        item(IDItem, Nome, Categoria, Custo, Inventario),
        ItensLista
    ).

% Ver Categorias disponíveis
categorias(CategoriaLista) :-
    findall(
        Categoria,
        categoria(Categoria),
        CategoriaLista
    ).

% Adicionar, modificar e remover uma categoria
adicionar_categoria(Categoria) :-
    assertz(categoria(Categoria)).

modificar_categoria_lista([], _).
modificar_categoria_lista([IDItem | Resto], CategoriaNova) :-
    item(IDItem, Nome, _, Custo, Inventario),
    retract(item(IDItem, Nome, _, Custo, Inventario)),
    assertz(item(IDItem, Nome, CategoriaNova, Custo, Inventario)),
    modificar_categoria_lista(Resto, CategoriaNova).

modificar_categoria(CategoriaAntiga, CategoriaNova) :-
    retract(categoria(CategoriaAntiga)),
    assertz(categoria(CategoriaNova)),
    findall(IDItem, item(IDItem, _, CategoriaAntiga, _, _), IDItemLista),
    modificar_categoria_lista(IDItemLista, CategoriaNova).

remover_categoria_lista([]).
remover_categoria_lista([IDItem | Resto]) :-
    retract(item(IDItem, _, _, _, _)),
    remover_categoria_lista(Resto).

remover_categoria(Categoria) :-
    retract(categoria(Categoria)),
    findall(IDItem, item(IDItem, _, Categoria, _, _), IDItemLista),
    remover_categoria_lista(IDItemLista).

% Adicionar, modificar e remover um item de inventário
adicionar_item(IDItem, Nome, Categoria, Custo, Inventario) :-
    assertz(item(IDItem, Nome, Categoria, Custo, Inventario)),
    (   categoria(Categoria)
    ->  true
    ;   adicionar_categoria(Categoria)
    ).

modificar_item(IDItem, Nome, Categoria, Custo, Inventario) :-
    retract(item(IDItem, _, _, _, _)),
    assertz(item(IDItem, Nome, Categoria, Custo, Inventario)),
    (   categoria(Categoria)
    ->  true
    ;   adicionar_categoria(Categoria)
    ).


remover_item(IDItem) :-
    retract(item(IDItem, _, _, _, _)).

% Ver todos os custos de envio (Todas as informações)
custos_envio(CustoEnvioLista) :-
    findall(
        [Distrito, Custo],
        custo_envio(Distrito, Custo),
        CustoEnvioLista
    ).

% Ver todos os tipos de desconto de categoria (Todas as informações)
descontos_categoria(DescontoCategoriaLista) :-
    findall(
        [Categoria, DescontoCategoria],
        desconto_categoria(Categoria, DescontoCategoria),
        DescontoCategoriaLista
    ).

% Ver todos os tipos de desconto de lealdade (Todas as informações)
descontos_lealdade(DescontoLealdadeLista) :-
    findall(
        [Categoria, DescontoLealdade],
        desconto_lealdade(Categoria, DescontoLealdade),
        DescontoLealdadeLista
    ).

% Adicionar, modificar e remover um custo de envio
adicionar_custo_envio(Distrito, Custo) :-
    assertz(custo_envio(Distrito, Custo)).

modificar_custo_envio(Distrito, CustoNovo) :-
    retract(custo_envio(Distrito, _)),
    assertz(custo_envio(Distrito, CustoNovo)).

remover_custo_envio(Distrito) :-
    retract(custo_envio(Distrito, _)).

% Adicionar, modificar e remover um desconto de categoria
adicionar_desconto_categoria(Categoria, DescontoCategoria) :-
    assertz(desconto_categoria(Categoria, DescontoCategoria)),
    (   categoria(Categoria)
    ->  true
    ;   adicionar_categoria(Categoria)
    ).

modificar_desconto_categoria(Categoria, DescontoCategoriaNovo) :-
    retract(desconto_categoria(Categoria, _)),
    assertz(desconto_categoria(Categoria, DescontoCategoriaNovo)).

remover_desconto_categoria(Categoria) :-
    retract(desconto_categoria(Categoria, _)).

% Adicionar, modificar e remover um desconto de lealdade
adicionar_desconto_lealdade(Lealdade, DescontoLealdade) :-
    assertz(desconto_lealdade(Lealdade, DescontoLealdade)).

modificar_desconto_lealdade(Lealdade, DescontoLealdadeNovo) :-
    retract(desconto_lealdade(Lealdade, _)),
    assertz(desconto_lealdade(Lealdade, DescontoLealdadeNovo)).

remover_desconto_lealdade(Lealdade) :-
    retract(desconto_lealdade(Lealdade, _)).

% Ver todos os clientes (Todas as informações)
clientes(ClienteLista) :-
    findall(
        [IDCliente, Nome, Distrito, Lealdade],
        cliente(IDCliente, Nome, Distrito, Lealdade),
        ClienteLista
    ).

% Ver todos os clientes de um determinado distrito (Todas as informações)
clientes_por_distrito(Distrito, ClienteLista) :-
    findall(
        [IDCliente, Nome, Distrito, Lealdade],
        cliente(IDCliente, Nome, Distrito, Lealdade),
        ClienteLista
    ).

% Ver todos os clientes com lealdade superior a determinado valor
clientes_com_lealdade_superior(LealdadeCorte, ClienteLista) :-
    findall(
        [IDCliente, Nome, Distrito, Lealdade],
        (
            cliente(IDCliente, Nome, Distrito, Lealdade),
            Lealdade > LealdadeCorte
        ),
        ClienteLista
    ).

% Adicionar, modificar e remover um cliente
adicionar_cliente(IDCliente, Nome, Distrito, Lealdade) :-
    assertz(cliente(IDCliente, Nome, Distrito, Lealdade)).

modificar_cliente(IDCliente, Nome, Distrito, Lealdade) :-
    retract(cliente(IDCliente, _, _, _)),
    assertz(cliente(IDCliente, Nome, Distrito, Lealdade)).

remover_cliente(IDCliente) :-
    retract(cliente(IDCliente, _, _, _)).
