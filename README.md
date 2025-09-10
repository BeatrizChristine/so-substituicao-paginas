# Simulador de Substituição de Páginas (2025)🖥️
**Trabalho Prático III — Sistemas Operacionais (IEC584)**  
Autor: **Beatriz Christine Azevedo Batista**  

## Descrição
Este projeto implementa um simulador de substituição de páginas em Java. Ele permite testar e comparar os algoritmos:

1. FIFO – First In, First Out
2. LRU – Least Recently Used
3. Segunda Chance / Clock

O sistema recebe uma string de referência de páginas de processos e simula a alocação em memória de 32 MB (8.000 quadros de 4 KB), calculando o número de page faults para cada algoritmo.

## Funcionalidades
- Simulação de FIFO, LRU e Segunda Chance
- Contagem de page faults
- Entrada de sequência de páginas via terminal
- Suporte a múltiplos processos e páginas com valores entre 0 e 65535

## Estrutura do repositório
```bash
SubstituicaoPaginas/
├─ src/
│   └─ SubstituicaoPaginas.java
```

## Como compilar
1. Clone o repositório:
```bash
git clone https://github.com/SEU-USUARIO/NOME-REPOSITORIO.git
```
2. Acesse a pasta:
```bash
cd SubstituicaoPaginas
```
3. Compile o código:
```bash
javac SubstituicaoPaginas.java
```
4. Execute o simulador:
```bash
java SubstituicaoPaginas
```
5. Digite a sequência de referências no formato:
```bash
1,0;1,1;2,0;1,1;2,1;3,0;0,0
```
> A saída mostrará o número de page faults para cada algoritmo:
```bash
Page Faults FIFO: XXXXX
Page Faults LRU: XXXXX
Page Faults Segunda Chance: XXXXX
```

## Tecnologias
- **Linguagem:** Java
- **Conceitos:** Estruturas de dados, Threads (conceitual), Simulação de memória, Page Replacement

## Observações
- O simulador utiliza estruturas de dados para representar memória e páginas.
- O mesmo conjunto de páginas deve ser usado para cada algoritmo para garantir comparação válida.
- O código está comentado para facilitar compreensão e estudo dos algoritmos de substituição de páginas.

## 📄 Licença
Este projeto foi desenvolvido por **Beatriz Christine Azevedo Batista**  
e está licenciado sob a **Licença MIT** — veja o arquivo [LICENSE](LICENSE) para mais detalhes.
