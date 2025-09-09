import java.util.*;

public class SubstituicaoPaginas {

    static class Pagina {
        int processo;
        int numero;
        boolean bitReferencia;

        public Pagina(int processo, int numero) {
            this.processo = processo;
            this.numero = numero;
            this.bitReferencia = false; // Bit de referência inicializado como 0
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pagina)) return false;
            Pagina pagina = (Pagina) o;
            return processo == pagina.processo && numero == pagina.numero;
        }

        @Override
        public int hashCode() {
            return Objects.hash(processo, numero);
        }
    }

    private static final int TOTAL_QUADROS = 8000; // Capacidade máxima dos quadros de memória

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite a sequência de referências:");
        String entrada = scanner.nextLine();

        // Converte a entrada para lista de páginas (processo, número)
        List<Pagina> referencias = analisarEntrada(entrada);

        // Calcula e imprime os page faults para cada algoritmo
        System.out.println("Page Faults FIFO: " + fifo(referencias));
        System.out.println("Page Faults LRU: " + lru(referencias));
        System.out.println("Page Faults Segunda Chance: " + segundaChanceCorreto(referencias));
    }

    // FIFO: remove a página mais antiga quando necessário (primeira que entrou)
    public static int fifo(List<Pagina> referencias) {
        Set<Pagina> quadros = new LinkedHashSet<>(); // Mantém ordem de inserção
        int faltas = 0;

        for (Pagina p : referencias) {
            if (!quadros.contains(p)) { // Page fault
                if (quadros.size() == TOTAL_QUADROS) {
                    // Remove a página que está há mais tempo na memória (primeira inserida)
                    Iterator<Pagina> it = quadros.iterator();
                    it.next();
                    it.remove();
                }
                quadros.add(p);
                faltas++;
            }
        }
        return faltas;
    }

    // LRU: remove a página menos recentemente usada
    // Usa LinkedHashMap com acesso ordenado para facilitar isso
    public static int lru(List<Pagina> referencias) {
        LinkedHashMap<Pagina, Integer> quadros = new LinkedHashMap<>(TOTAL_QUADROS, 0.75f, true);
        int faltas = 0;

        for (Pagina p : referencias) {
            if (!quadros.containsKey(p)) { // Page fault
                if (quadros.size() == TOTAL_QUADROS) {
                    // Remove a página que não foi usada por mais tempo (primeira na ordem de acesso)
                    Pagina lru = quadros.entrySet().iterator().next().getKey();
                    quadros.remove(lru);
                }
                faltas++;
            }
            // Atualiza ou insere página, marcando-a como mais recentemente usada
            quadros.put(p, 0);
        }
        return faltas;
    }

    // Segunda Chance: usa ponteiro circular e bit de referência para decidir substituição
    public static int segundaChanceCorreto(List<Pagina> referencias) {
        Pagina[] quadros = new Pagina[TOTAL_QUADROS];
        int ponteiro = 0;
        int faltas = 0;
        Set<Pagina> conjuntoQuadros = new HashSet<>(); // Para acesso rápido e teste de existência

        for (Pagina p : referencias) {
            if (!conjuntoQuadros.contains(p)) { // Page fault
                // Percorre os quadros até encontrar página com bitReferencia == false
                while (quadros[ponteiro] != null && quadros[ponteiro].bitReferencia) {
                    quadros[ponteiro].bitReferencia = false; // Reseta bit para dar "segunda chance"
                    ponteiro = (ponteiro + 1) % TOTAL_QUADROS;
                }
                // Remove página antiga do conjunto se existir
                if (quadros[ponteiro] != null) {
                    conjuntoQuadros.remove(quadros[ponteiro]);
                }
                // Substitui com nova página e adiciona ao conjunto
                quadros[ponteiro] = p;
                conjuntoQuadros.add(p);
                faltas++;
                ponteiro = (ponteiro + 1) % TOTAL_QUADROS; // Avança ponteiro circular
            } else {
                // Página já está na memória, só marca bit de referência para indicar acesso recente
                for (int i = 0; i < TOTAL_QUADROS; i++) {
                    if (p.equals(quadros[i])) {
                        quadros[i].bitReferencia = true;
                        break;
                    }
                }
            }
        }
        return faltas;
    }

    // Converte string de entrada no formato "processo,pagina;processo,pagina;..." para lista de páginas
    private static List<Pagina> analisarEntrada(String entrada) {
        List<Pagina> referencias = new ArrayList<>();
        String[] pares = entrada.split(";");
        for (String par : pares) {
            String[] numeros = par.split(",");
            int processo = Integer.parseInt(numeros[0]);
            int pagina = Integer.parseInt(numeros[1]);
            if (processo == 0 && pagina == 0) break; // Fim da entrada
            referencias.add(new Pagina(processo, pagina));
        }
        return referencias;
    }
}
