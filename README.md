# Árvores 🌲!
>*Neste projeto, encontraremos ferramentas que demonstram a forma que diversas árvores operam como:*
*******
Tabelas de conteúdo 
>* [Árvore B e B+](#arvorebeb) - Estruturas de dados hierárquicas que permite armazenar mais de um registro!
>* [Árvore AVL](#arvoreavl) -  É uma árvore binária na qual as alturas das duas subárvores de todo nó nunca difere em mais de 1!
>* [Árvore Binária](#arvorebinaria) - É uma estrutura de dados útil quando precisam ser tomadas decisões bidirecionais em cada ponto de um processo!
>* [Rubo Negra](#arvorerubonegra) - Árvore binária de busca ditas balanceadas com altura O(logn)!

*******

## ⚙️ Requisitos...

| Ferramentas   |      Nome      |  Observações |
|----------|:-------------:|------:|
| ![PostgreeSQL](https://upload.wikimedia.org/wikipedia/commons/thumb/2/29/Postgresql_elephant.svg/150px-Postgresql_elephant.png) |  PostgreSQL | Necessário criar um banco chamado "aluno" |
| ![JavaSDK](https://upload.wikimedia.org/wikipedia/pt/thumb/3/30/Java_programming_language_logo.svg/96px-Java_programming_language_logo.svg.png) |    JAVASDK   |   Necessário para o projeto! sua linguagem é java |





## 🚀 Starting...


*******
<div id='arvorebeb'/> 
  
### O que é Árvore B e B+?

>* Árvores B e B+ são estruturas de dados usadas em bancos de dados para armazenar e gerenciar grandes conjuntos de dados de forma eficiente. Elas são árvores balanceadas que permitem a busca, inserção e exclusão eficientes de registros. A principal diferença é que na Árvore B os dados podem estar em qualquer nó, enquanto na Árvore B+ apenas as folhas contêm os dados, o que facilita a busca e a manutenção de registros.

##### É assim que a árvore se aparece:  

![ArvoreBeB](https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/B-tree-definition.png/400px-B-tree-definition.png)

#### ✍️ Exemplo escrito...
> **Árvore B:**
> 
> Suponhamos que estamos criando um banco de dados de estudantes, onde os registros são armazenados em uma Árvore B. Cada nó interno da Árvore B contém várias chaves que servem como pontos de referência para os registros armazenados em discos. Por exemplo:
> 
> - Nó interno 1: Chaves [10, 20]
> - Nó interno 2: Chaves [30, 40, 50]
> - Folha 1: Registros [Estudante1, Estudante2, Estudante3]
> - Folha 2: Registros [Estudante4, Estudante5]
> 
> Neste caso, se quisermos encontrar informações sobre o Estudante com ID 35, começaríamos na raiz da Árvore B, iríamos para o nó interno 2 e, em seguida, para a Folha 2, onde encontraríamos o registro do Estudante5.
>
> **Árvore B+:**
> 
> A Árvore B+ é semelhante à Árvore B, mas com algumas diferenças notáveis. Neste exemplo, cada nó interno só contém chaves que são usadas para navegar para subárvores, e os registros são armazenados apenas nas folhas:
> 
> - Nó interno 1: Chaves [20, 40]
> - Nó interno 2: Chaves [10, 30]
> - Folha 1: Registros [Estudante1, Estudante2, Estudante3]
> - Folha 2: Registros [Estudante4, Estudante5]
> 
> Novamente, se quisermos encontrar informações sobre o Estudante com ID 35, começaríamos na raiz, iríamos para o nó interno 1, depois para o nó interno 2 e finalmente para a Folha 2, onde encontraríamos o registro do Estudante5.
> 
> A principal diferença é que na Árvore B+ as folhas contêm todos os registros, tornando as operações de busca mais eficientes e simplificadas em comparação com a Árvore B.

<div id='arvoreavl'/>   
  
### O que é Árvore AVL?  

>* Uma Árvore AVL é uma estrutura de dados de árvore binária balanceada na qual a diferença de altura entre as subárvores esquerda e direita de qualquer nó (chamada de fator de equilíbrio) é mantida em no máximo 1. Isso garante que a árvore permaneça balanceada e as operações de busca, inserção e exclusão sejam eficientes, com complexidade O(log n), onde "n" é o número de nós na árvore.


##### É assim que a árvore se aparece:  

![ArvoreAVL](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjg6cf_7EtbqPdvEo-Va7bxIdHgesfcvVgwA&usqp=CAU)
  
#### ✍️ Exemplo escrito...

> **Árvore AVL:**
> 
> Uma Árvore AVL é uma estrutura de dados de árvore binária balanceada. Ela garante que a diferença de altura entre as subárvores esquerda e direita de qualquer nó, chamada de fator de equilíbrio, seja mantida em no máximo 1. Isso significa que a árvore é sempre balanceada, evitando desequilíbrios significativos entre os ramos.
> 
> Por exemplo, se adicionarmos um novo nó a uma Árvore AVL e isso causar um desequilíbrio, a árvore reorganizará automaticamente sua estrutura para restaurar o equilíbrio. Isso é alcançado através de rotações e ajustes na altura dos nós.
> 
> As Árvores AVL são particularmente úteis em situações em que é necessário garantir um bom desempenho em operações de busca, inserção e exclusão, pois as operações em uma Árvore AVL têm uma complexidade média de O(log n), onde "n" é o número de nós na árvore. Elas são comumente usadas em bancos de dados, sistemas de busca e outras aplicações onde a eficiência na manipulação de dados é essencial.

<div id='arvorebinaria'/> 

### O que é Árvore Binária?

>* Uma Árvore Binária é uma estrutura de dados em forma de árvore composta por nós, onde cada nó pode ter no máximo dois filhos: um filho à esquerda e um filho à direita. Ela é usada para organizar dados de forma hierárquica e é amplamente usada em estruturas de dados e algoritmos. As árvores binárias são usadas em muitas aplicações, como em estruturas de pesquisa e classificação de dados.


##### É assim que a árvore se aparece:  

![Arvorebinaria](https://www.macoratti.net/16/05/vbn_arvbin4.png)

#### ✍️ Exemplo escrito...

> **Árvore Binária:**
> 
> Uma Árvore Binária é uma estrutura de dados em forma de árvore, onde cada nó pode ter no máximo dois filhos: um filho à esquerda e um filho à direita. Ela é frequentemente usada para organizar dados de forma hierárquica. Em uma árvore binária de busca, os nós são organizados de modo que os nós à esquerda contenham valores menores do que o nó pai, e os nós à direita contenham valores maiores. Isso facilita a busca eficiente em dados organizados dessa forma.
> 
> Por exemplo, em uma Árvore Binária de Busca, se quisermos encontrar um valor específico, podemos começar na raiz da árvore e seguir as ramificações à esquerda ou à direita, com base na comparação do valor desejado com o valor do nó atual. Isso nos permite localizar o valor desejado de forma eficiente, com uma complexidade média de O(log n) nas operações de busca.
> 
> Árvores Binárias são amplamente usadas em estruturas de dados e algoritmos, incluindo em sistemas de arquivos, expressões aritméticas, e em muitas outras aplicações onde a organização hierárquica é útil.

<div id='arvorerubonegra'/> 
  
### O que é Árvore Rubo Negra?  
  
>* Uma "árvore Rubro-Negra" é uma estrutura de dados de árvore binária de pesquisa que possui propriedades adicionais para garantir que ela seja balanceada. Ela é semelhante a uma árvore binária de busca, mas com regras que garantem que a altura da árvore seja mantida em um nível razoável, resultando em operações de busca, inserção e exclusão eficientes com uma complexidade média de O(log n), onde "n" é o número de nós na árvore. As árvores Rubro-Negras são amplamente usadas em estruturas de dados, especialmente em sistemas de gerenciamento de bancos de dados e compiladores.

##### É assim que a árvore se aparece:  
![Arvorerubonegra](https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/Red-black_tree_example.svg/500px-Red-black_tree_example.svg.png)

#### ✍️ Exemplo escrito...

> **Árvore Rubro-Negra:**
> 
> Uma Árvore Rubro-Negra é uma estrutura de dados de árvore binária de pesquisa que possui propriedades adicionais para garantir que ela seja balanceada. Ela é semelhante a uma árvore binária de pesquisa, mas com regras que garantem que a altura da árvore seja mantida em um nível razoável, resultando em operações de busca, inserção e exclusão eficientes com complexidade média de O(log n), onde "n" é o número de nós na árvore.
> 
> Uma das principais características das Árvores Rubro-Negras é a atribuição de cores a cada nó, que pode ser vermelho ou preto. Essas cores são atribuídas de acordo com regras que garantem que a árvore permaneça balanceada. Por exemplo, algumas regras incluem:
> 
> - As folhas são sempre pretas.
> - Nenhum caminho da raiz a uma folha passa por dois nós vermelhos consecutivos.
> - Para cada nó, todos os caminhos da raiz às folhas têm o mesmo número de nós pretos.
> 
> Essas regras garantem que a altura da árvore seja mantida em um nível logarítmico em relação ao número de nós, o que resulta em operações eficientes.
> 
> As Árvores Rubro-Negras são frequentemente usadas em sistemas de gerenciamento de bancos de dados e outras aplicações em que o desempenho e a previsibilidade são cruciais. Eles são uma escolha popular para implementar estruturas de dados como árvores de busca e dicionários, proporcionando uma boa combinação de desempenho e facilidade de manutenção.
