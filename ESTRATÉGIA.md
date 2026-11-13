# Estratégia

## Movimentação

Vamos ocupar a faixa central do mapa, sua borda é ditada pelo alcance da cura dos cogumelos * 2. O **Comandante** vai ocupar o centro absoluto, enquanto os *Soldados* vão ocupar as laterais.

## Comandante

O **Comandante** é responsável por  garantir um ponto seguro.

É o único agente que não vai se dividir, e nem procurar cogumelos, seu objetivo é sobreviver o máximo possível.

Caso seja atacado, irá mandar mensagem aos *Soldados*, que irão para a luta.

## Soldados

Sua função é ocupar os espaços laterais da área, e servir como "scout". Caso encontre cogumelos ou agentes inimigos, irá alertar o restante de sua posição.

Sempre que receberem uma mensagem, irão apoiar os parceiros a não ser que estejam em um cogumelo.

A ordem de prioridade das mensagens é a seguinte: 1 - Cogumelo, 2 - **Comandante** em ataque, 3 - Soldado em ataque (pode ser alterada)

## Dividir para conquistar

Bem no estilo de ordenações de listas, o objetivo é sempre que for encontrado um cogumelo ou inimigo, os *Soldados* se dividem o máximo possível (limite a ser definido).
Como a maioria dos agentes inimigos apenas mede a vida do adversário, vamos aproveitar-se disso e dividir, para dar a impressão de uma vitória fácil, quando na verdade estamos com uma vantagem numérica.
Na mesma lógica, os agentes inimigos vão fazer a estratégia ao redor dos cogumelos. Vários agentes consomem um cogumelo mais rápido, e os *Soldados* vão dividir se para "drenar" o cogumelo e impedir que o adversário use os pontos de vida do cogumelo.

## Final do jogo

Quando não houverem mais cogumelos, os *Soldados* vão a caça dos inimigos restantes, enquanto o **Comandante** permanece parado, para tentar durar o máximo possível.