public class Modelo1Para1 {
    private final int tarefas;
    private final int iteracoesPorTarefa;

    public Modelo1Para1(int tarefas, int iteracoesPorTarefa) {
        this.tarefas = tarefas;
        this.iteracoesPorTarefa = iteracoesPorTarefa;
    }

    public long executarEMedirMillis() throws InterruptedException {
        Thread[] threads = new Thread[tarefas];
        long inicio = System.nanoTime();

        for (int i = 0; i < tarefas; i++) {
            threads[i] = new Thread(new Tarefa(iteracoesPorTarefa));
            threads[i].start();
        }

        for (int i = 0; i < tarefas; i++) {
            threads[i].join();
        }

        long fim = System.nanoTime();
        return (fim - inicio) / 1_000_000;
    }
}
