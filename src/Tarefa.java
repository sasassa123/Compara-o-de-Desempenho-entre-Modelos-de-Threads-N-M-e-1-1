public class Tarefa implements Runnable {
    private final int iteracoes;
    private static volatile double sink = 0.0;

    public Tarefa(int iteracoes) {
        this.iteracoes = iteracoes;
    }

    @Override
    public void run() {
        double local = 0.0;
        for (int i = 1; i <= iteracoes; i++) {
            local += Math.sqrt(i) * Math.log(i + 1);
        }
        sink = local;
    }

    public static double getSink() {
        return sink;
    }
}
