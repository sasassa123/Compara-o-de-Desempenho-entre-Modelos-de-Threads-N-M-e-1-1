import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv('resultados_brutos.csv')
resumo = df.groupby(['modelo','tarefas','pool']).ms.agg(['mean','std']).reset_index()
resumo = resumo.rename(columns={'mean':'ms_medio','std':'ms_std'})
resumo.to_csv('resultados_resumo.csv', index=False)

pool_padrao = int(df['pool'].mode()[0])
nm = resumo[(resumo['modelo']=='N:M') & (resumo['pool']==pool_padrao)][['tarefas','ms_medio']].set_index('tarefas')
m11 = resumo[resumo['modelo']=='1:1'][['tarefas','ms_medio']].groupby('tarefas').mean()

plot_df = pd.concat([nm.rename(columns={'ms_medio':'N:M (pool='+str(pool_padrao)+')'}), 
                     m11.rename(columns={'ms_medio':'1:1'})], axis=1)

plot_df.plot(marker='o')
plt.xlabel('Número de tarefas')
plt.ylabel('Tempo médio (ms)')
plt.title('Comparação N:M vs 1:1')
plt.grid(True)
plt.savefig('comparacao.png', dpi=300)
plt.show()
