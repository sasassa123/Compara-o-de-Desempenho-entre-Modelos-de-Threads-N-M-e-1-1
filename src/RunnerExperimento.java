import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class RunnerExperimento {
    private static final int[] TASK_COUNTS = {10, 100, 500, 1000};
    private static final int ITERACOES_POR_TAREFA = 50000;
    private static final int[] POOL_SIZES = {
            1,
            2,
            4,
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors() * 2
    };
    private static final int REPETICOES = 3;

    public static void main(String[] args) throws Exception {
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Núcleos detectados: " + cores);
        System.out.println("TASK_COUNTS: " + Arrays.toString(TASK_COUNTS));
        System.out.println("POOL_SIZES: " + Arrays.toString(POOL_SIZES));
        try (PrintWriter out = new PrintWriter(new FileWriter("resultados_brutos.csv"))) {
            out.println("modelo,tarefas,pool,rep,ms");
            new ModeloNM(10, 1000, Math.max(1, cores / 2)).executarEMedirMillis();
            new Modelo1Para1(10, 1000).executarEMedirMillis();
            System.gc();
            Thread.sleep(200);

            for (int tasks : TASK_COUNTS) {
                for (int pool : POOL_SIZES) {
                    for (int rep = 1; rep <= REPETICOES; rep++) {
                        ModeloNM nm = new ModeloNM(tasks, ITERACOES_POR_TAREFA, pool);
                        long msNm = nm.executarEMedirMillis();
                        System.out.printf("N:M tasks=%d pool=%d rep=%d -> %d ms%n", tasks, pool, rep, msNm);
                        out.printf("N:M,%d,%d,%d,%d%n", tasks, pool, rep, msNm);

                        Modelo1Para1 m11 = new Modelo1Para1(tasks, ITERACOES_POR_TAREFA);
                        long ms11 = m11.executarEMedirMillis();
                        System.out.printf("1:1 tasks=%d rep=%d -> %d ms%n", tasks, rep, ms11);
                        out.printf("1:1,%d,%d,%d,%d%n", tasks, pool, rep, ms11);

                        Thread.sleep(300);
                    }
                }
            }
        }
        System.out.println("Arquivo gerado: resultados_brutos.csv");
        System.out.println("Sink: " + Tarefa.getSink());
    }
}
