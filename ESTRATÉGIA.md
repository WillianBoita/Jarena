# Estratégia

## Movimentação

**Comandante** vai permanecer fixo, enquanto os *Soldados* vão patrulhar a arena em busca de cogumelos.

## Comandante

O **Comandante** é responsável por garantir um ponto seguro.

É o único agente que não vai se dividir, e nem procurar cogumelos, seu objetivo é sobreviver o máximo possível.

Caso seja atacado por 2 turnos seguidos, ele irá começar uma fuga e tentar fugir.

## Soldados

Sua função é ocupar os espaços laterais da área, e servir como "scout". Caso encontre cogumelos, irá alertar o restante de sua posição.

Sempre que receberem uma mensagem, irão apoiar os parceiros a não ser que estejam em um cogumelo.

Caso os *Soldados* sejam atacados por 2 turnos, eles irão se dividir.

## Dividir para conquistar

Bem no estilo de ordenações de listas, o objetivo é sempre que for encontrado um inimigo, os *Soldados* se dividem o máximo possível (limite a ser definido).
Como a maioria dos agentes inimigos apenas mede a vida do adversário, vamos aproveitar-se disso e dividir, para dar a impressão de uma vitória fácil, quando na verdade estamos com uma vantagem numérica.

## Suicídio

Foi notado nos testes que quando uma unidade morre, os inimigos recebem pontos de energia, e queremos evitar isso.
Utilizamos um cálculo envolvendo o dano da unidade inimiga, e um buffer a mais para mandar a unidade se matar e impedir que o inimigo ganhe pontos.
Isso apenas se aplica se tem mais de uma unidade em campo.