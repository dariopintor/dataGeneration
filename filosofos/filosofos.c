/**
 * Problema dos filósofos glutões
 * @author Alexandre Hausen
  @article{DBLP:journals/acta/Dijkstra71,
    author    = {Edsger W. Dijkstra},
    title     = {Hierarchical Ordering of Sequential Processes},
    journal   = {Acta Inf.},
    volume    = {1},
    year      = {1971},
    pages     = {115-138},
    bibsource = {DBLP, http://dblp.uni-trier.de}
 }
 */
#include <unistd.h>
#include <time.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <mpi.h>
#include <stdio.h>
#include "filosofos.h"

/**
 * Espera até que algum filósofo queira pegar um garfo
 * ou devolver um dos garfos
 * ou todos os filósofos já tenham comido, neste caso termina.
 */
void garfos(void)
{
    int v_garfos[N_FILOS];
    int comeram = 0;
    int tag, i, pegou;

    for (i = 0; i < N_FILOS; i++)
        v_garfos[i] = LIVRE;

    while (comeram < N_FILOS)
    {
        MPI_Recv(&i, 1, MPI_INT, MPI_ANY_SOURCE, MPI_ANY_TAG, MPI_COMM_WORLD, &status);
        tag = status.MPI_TAG;

        if (tag == TAG_PEGA)
        {
            if (v_garfos[i] == LIVRE)
            {
                v_garfos[i] = OCUPADO;
                pegou = TRUE;
            }
            else
                pegou = FALSE;
            MPI_Send(&pegou, 1, MPI_INT, status.MPI_SOURCE, TAG_PEGA, MPI_COMM_WORLD);
        }
        else if (tag == TAG_LIBERA)
        {
            v_garfos[i] = LIVRE;
        }
        else // TAG_FIM
        {
            printf("filósofo %d terminou de comer.\n", i + 1);
            comeram++;
        }
    }
}

/**
 * O filósofo pensa, pega os garfos, come e devolve os garfos
 * @param i número do filósofo
 * @param fome fome do filósofo
 */
void filosofo(int i, int fome)
{
    int esq, dir;
    int peguei_esq, peguei_dir;

    esq = i;
    dir = (i + 1) % N_FILOS;
    
    while (fome > 0)
    {
        pensa(i);
        peguei_esq = FALSE;
        peguei_dir = FALSE;

        // PEGA OS GARFOS DA ESQUERDA E DIREITA
        while (!peguei_esq && !peguei_dir)
        {
            // TENTA PEGAR O GARFO À ESQUERDA
            MPI_Send(&esq, 1, MPI_INT, TASK_GARFOS, TAG_PEGA, MPI_COMM_WORLD);
            MPI_Recv(&peguei_esq, 1, MPI_INT, TASK_GARFOS, TAG_PEGA, MPI_COMM_WORLD, &status);

            // TENTA PEGAR O GARFO À DIREITA
            MPI_Send(&dir, 1, MPI_INT, TASK_GARFOS, TAG_PEGA, MPI_COMM_WORLD);
            MPI_Recv(&peguei_dir, 1, MPI_INT, TASK_GARFOS, TAG_PEGA, MPI_COMM_WORLD, &status);

            // DEVOLVE UM DOS GARFOS SE NÃO PEGOU OS DOIS
            if (peguei_esq && !peguei_dir)
            {
                // DEVOLVE O GARFO DA ESQUERDA
                MPI_Send(&esq, 1, MPI_INT, TASK_GARFOS, TAG_LIBERA, MPI_COMM_WORLD);
                peguei_esq = FALSE;
                espera_um_pouco();
            }
            if (!peguei_esq && peguei_dir)
            {
                // DEVOLVE O GARFO DA DIREITA
                MPI_Send(&dir, 1, MPI_INT, TASK_GARFOS, TAG_LIBERA, MPI_COMM_WORLD);
                peguei_dir = FALSE;
                espera_um_pouco();
            }
        }

        fome -= come(i);

        // DEVOLVE OS DOIS GARFOS
        MPI_Send(&esq, 1, MPI_INT, TASK_GARFOS, TAG_LIBERA, MPI_COMM_WORLD);
        MPI_Send(&dir, 1, MPI_INT, TASK_GARFOS, TAG_LIBERA, MPI_COMM_WORLD);

        printf("filósofo %d devolveu os garfos, fome = %d\n", i + 1, fome);
    }

    // FILÓSOFO SACIOU SUA FOME
    MPI_Send(&i, 1, MPI_INT, TASK_GARFOS, TAG_FIM, MPI_COMM_WORLD);
}


/**
 * Espera um pouco
 */
void espera_um_pouco(void)
{
    processa();
}

/**
 * pensa
 * @param i número do filósofo
 */
void pensa(int i)
{
    printf("filósofo %d pensando\n", i + 1);
    processa();
}

/**
 * Come
 * @param i número do filósofo
 * @return o quanto da fome foi saciada
 */
int come(int i)
{
    printf("filósofo %d comendo\n", i + 1);
    return processa();
}

/**
 * Dorme por um tempo randômico, entre 0 e 1 seg.
 * @return o tempo dormido (em milisegundos)
 */
int processa(void)
{
    int x = 500000;
    usleep(x);
    return x / 1000;
}


int main(int argc, char ** argv)
{
    int n_tasks, my_rank, fome;

    MPI_Init(&argc, &argv);

    MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);

    MPI_Comm_size(MPI_COMM_WORLD, &n_tasks);

    if (n_tasks != N_TASKS)
    {
        printf("n_tasks(%d) != %d\n", n_tasks, N_FILOS + 1);
        MPI_Finalize();
        return 1;
    }

    if (argc - 1 != N_FILOS)
    {
        printf("uso: %s [fome do filósofo 1] ... [fome do filósofo %d].\n", argv[0], N_FILOS);
        MPI_Finalize();
        return 1;
    }

    // inicia semente randômica
    srand(my_rank + time(NULL));

    if (my_rank == TASK_GARFOS)
        garfos();
    else
    {
        fome = atoi(argv[my_rank + 1]);
        filosofo(my_rank, fome);
    }

    MPI_Finalize();
    return 0;
}

