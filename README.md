# Compara-o-de-Desempenho-entre-Modelos-de-Threads-N-M-e-1-1




Desempenho do Modelo N:M:

O modelo N:M é menos eficiente quando o número de tarefas é grande. Como várias threads de usuário são gerenciadas por poucas threads reais, o sistema acaba "se sobrecarregando", aumentando o tempo de execução à medida que as tarefas crescem.

Desempenho do Modelo 1:1:

O modelo 1:1 é mais eficiente e lida melhor com um número maior de tarefas. Como cada thread de usuário é diretamente mapeada para uma thread do sistema, o sistema pode realizar as tarefas de forma mais paralela e sem sobrecarga.








<img width="1094" height="816" alt="image" src="https://github.com/user-attachments/assets/bd5daf8b-8287-450e-821d-14f1e1e2db48" />

