import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ModeloNM {
    private final int tarefas;
    private final int iteracoesPorTarefa;
    private final int tamanhoPool;

    public ModeloNM(int tarefas, int iteracoesPorTarefa, int tamanhoPool) {
        this.tarefas = tarefas;
        this.iteracoesPorTarefa = iteracoesPorTarefa;
        this.tamanhoPool = tamanhoPool;
    }

    public long executarEMedirMillis() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(tamanhoPool);
        CountDownLatch controle = new CountDownLatch(tarefas);
        long inicio = System.nanoTime();

        for (int i = 0; i < tarefas; i++) {
            pool.submit(() -> {
                try {
                    new Tarefa(iteracoesPorTarefa).run();
                } finally {
                    controle.countDown();
                }
            });
        }

        controle.await();
        long fim = System.nanoTime();
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
        return (fim - inicio) / 1_000_000;
    }
}
