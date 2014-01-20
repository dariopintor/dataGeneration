#include <unistd.h>
#include <time.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "valimpi.h"
#include <stdio.h>
#include "filosofos.h"
void
garfos(void)
{
    int v_garfos[N_FILOS];
    int comeram = 0;
    int tag, i, pegou;
    VALIMPI_TRACE_DECL;
    VALIMPI_REQ_LIST_DECL;
    ValiMPI_Req_list_init(&valimpiListReq);
    ValiMPI_Init_trace(&valimpiTrace, "garfos");
    ValiMPI_Check_trace(&valimpiTrace, 1);
    {
        {
            ValiMPI_Check_trace(&valimpiTrace, 1);
            for (i = 0; ValiMPI_Check_trace(&valimpiTrace, 2), (i < N_FILOS); i++)
            {
                ValiMPI_Check_trace(&valimpiTrace, 3);
                {
                    v_garfos[i] = LIVRE;
                    ValiMPI_Check_trace(&valimpiTrace, 4);
                }
            }
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 5);
            while (ValiMPI_Check_trace(&valimpiTrace, 6), (comeram < N_FILOS))
            {
                ValiMPI_Check_trace(&valimpiTrace, 7);
                {
                    {
                        {
                            {
                                ValiMPI_Check_trace(&valimpiTrace, 7);
                                ValiMPI_Recv_trace(&valimpiTrace, &i, 1, MPI_INT, MPI_ANY_SOURCE, MPI_ANY_TAG, MPI_COMM_WORLD, &status);
                            }
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 8);
                            tag = status.MPI_TAG;
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 9);
                            if (tag == TAG_PEGA)
                            {
                                ValiMPI_Check_trace(&valimpiTrace, 10);
                                {
                                    {
                                        {
                                            ValiMPI_Check_trace(&valimpiTrace, 10);
                                            if (v_garfos[i] == LIVRE)
                                            {
                                                ValiMPI_Check_trace(&valimpiTrace, 11);
                                                {
                                                    {
                                                        {
                                                            ValiMPI_Check_trace(&valimpiTrace, 11);
                                                            v_garfos[i] = OCUPADO;
                                                        }
                                                        {
                                                            ValiMPI_Check_trace(&valimpiTrace, 12);
                                                            pegou = TRUE;
                                                        }
                                                    }
                                                    ValiMPI_Check_trace(&valimpiTrace, 13);
                                                }
                                            }
                                            else
                                            {
                                                ValiMPI_Check_trace(&valimpiTrace, 14);
                                                {
                                                    pegou = FALSE;
                                                    ValiMPI_Check_trace(&valimpiTrace, 15);
                                                }
                                            }
                                        }
                                        {
                                            {
                                                ValiMPI_Check_trace(&valimpiTrace, 16);
                                                ValiMPI_Send_trace(&valimpiTrace, &pegou, 1, MPI_INT, status.MPI_SOURCE, TAG_PEGA, MPI_COMM_WORLD);
                                            }
                                        }
                                    }
                                    ValiMPI_Check_trace(&valimpiTrace, 17);
                                }
                            }
                            else
                            {
                                ValiMPI_Check_trace(&valimpiTrace, 18);
                                {
                                    if (tag == TAG_LIBERA)
                                    {
                                        ValiMPI_Check_trace(&valimpiTrace, 19);
                                        {
                                            {
                                                {
                                                    ValiMPI_Check_trace(&valimpiTrace, 19);
                                                    v_garfos[i] = LIVRE;
                                                }
                                            }
                                            ValiMPI_Check_trace(&valimpiTrace, 20);
                                        }
                                    }
                                    else
                                    {
                                        ValiMPI_Check_trace(&valimpiTrace, 21);
                                        {
                                            {
                                                {
                                                    ValiMPI_Check_trace(&valimpiTrace, 21);
                                                    printf("filósofo %d terminou de comer.\n", i + 1);
                                                }
                                                {
                                                    ValiMPI_Check_trace(&valimpiTrace, 22);
                                                    comeram++;
                                                }
                                            }
                                            ValiMPI_Check_trace(&valimpiTrace, 23);
                                        }
                                    }
                                    ValiMPI_Check_trace(&valimpiTrace, 24);
                                }
                            }
                        }
                    }
                    ValiMPI_Check_trace(&valimpiTrace, 25);
                }
            }
        }
    }
    ValiMPI_Check_trace(&valimpiTrace, 26);
    ValiMPI_End_trace();
}

void
filosofo(int i, int fome)
{
    int esq, dir;
    int peguei_esq, peguei_dir;
    VALIMPI_TRACE_DECL;
    VALIMPI_REQ_LIST_DECL;
    ValiMPI_Req_list_init(&valimpiListReq);
    ValiMPI_Init_trace(&valimpiTrace, "filosofo");
    ValiMPI_Check_trace(&valimpiTrace, 1);
    {
        {
            ValiMPI_Check_trace(&valimpiTrace, 2);
            esq = i;
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 3);
            dir = (i + 1) % N_FILOS;
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 4);
            while (ValiMPI_Check_trace(&valimpiTrace, 5), (fome > 0))
            {
                ValiMPI_Check_trace(&valimpiTrace, 6);
                {
                    {
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 6);
                            pensa(i);
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 7);
                            peguei_esq = FALSE;
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 8);
                            peguei_dir = FALSE;
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 9);
                            while (ValiMPI_Check_trace(&valimpiTrace, 12), (!peguei_esq && !peguei_dir))
                            {
                                ValiMPI_Check_trace(&valimpiTrace, 13);
                                {
                                    {
                                        {
                                            {
                                                ValiMPI_Check_trace(&valimpiTrace, 13);
                                                ValiMPI_Send_trace(&valimpiTrace, &esq, 1, MPI_INT, TASK_GARFOS, TAG_PEGA, MPI_COMM_WORLD);
                                            }
                                        }
                                        {
                                            {
                                                ValiMPI_Check_trace(&valimpiTrace, 14);
                                                ValiMPI_Recv_trace(&valimpiTrace, &peguei_esq, 1, MPI_INT, TASK_GARFOS, TAG_PEGA, MPI_COMM_WORLD,
                                                                   &status);
                                            }
                                        }
                                        {
                                            {
                                                ValiMPI_Check_trace(&valimpiTrace, 15);
                                                ValiMPI_Send_trace(&valimpiTrace, &dir, 1, MPI_INT, TASK_GARFOS, TAG_PEGA, MPI_COMM_WORLD);
                                            }
                                        }
                                        {
                                            {
                                                ValiMPI_Check_trace(&valimpiTrace, 16);
                                                ValiMPI_Recv_trace(&valimpiTrace, &peguei_dir, 1, MPI_INT, TASK_GARFOS, TAG_PEGA, MPI_COMM_WORLD,
                                                                   &status);
                                            }
                                        }
                                        {
                                            ValiMPI_Check_trace(&valimpiTrace, 17);
                                            if (peguei_esq && !peguei_dir)
                                            {
                                                ValiMPI_Check_trace(&valimpiTrace, 18);
                                                {
                                                    {
                                                        {
                                                            {
                                                                ValiMPI_Check_trace(&valimpiTrace, 18);
                                                                ValiMPI_Send_trace(&valimpiTrace, &esq, 1, MPI_INT, TASK_GARFOS, TAG_LIBERA,
                                                                                   MPI_COMM_WORLD);
                                                            }
                                                        }
                                                        {
                                                            ValiMPI_Check_trace(&valimpiTrace, 19);
                                                            peguei_esq = FALSE;
                                                        }
                                                        {
                                                            ValiMPI_Check_trace(&valimpiTrace, 20);
                                                            espera_um_pouco();
                                                        }
                                                    }
                                                    ValiMPI_Check_trace(&valimpiTrace, 20);
                                                }
                                            }
                                        }
                                        {
                                            ValiMPI_Check_trace(&valimpiTrace, 21);
                                            if (!peguei_esq && peguei_dir)
                                            {
                                                ValiMPI_Check_trace(&valimpiTrace, 22);
                                                {
                                                    {
                                                        {
                                                            {
                                                                ValiMPI_Check_trace(&valimpiTrace, 22);
                                                                ValiMPI_Send_trace(&valimpiTrace, &dir, 1, MPI_INT, TASK_GARFOS, TAG_LIBERA,
                                                                                   MPI_COMM_WORLD);
                                                            }
                                                        }
                                                        {
                                                            ValiMPI_Check_trace(&valimpiTrace, 23);
                                                            peguei_dir = FALSE;
                                                        }
                                                        {
                                                            ValiMPI_Check_trace(&valimpiTrace, 24);
                                                            espera_um_pouco();
                                                        }
                                                    }
                                                    ValiMPI_Check_trace(&valimpiTrace, 24);
                                                }
                                            }
                                        }
                                    }
                                    ValiMPI_Check_trace(&valimpiTrace, 25);
                                }
                            }
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 26);
                            fome -= come(i);
                        }
                        {
                            {
                                ValiMPI_Check_trace(&valimpiTrace, 27);
                                ValiMPI_Send_trace(&valimpiTrace, &esq, 1, MPI_INT, TASK_GARFOS, TAG_LIBERA, MPI_COMM_WORLD);
                            }
                        }
                        {
                            {
                                ValiMPI_Check_trace(&valimpiTrace, 28);
                                ValiMPI_Send_trace(&valimpiTrace, &dir, 1, MPI_INT, TASK_GARFOS, TAG_LIBERA, MPI_COMM_WORLD);
                            }
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 29);
                            printf("filósofo %d devolveu os garfos, fome = %d\n", i + 1, fome);
                        }
                    }
                    ValiMPI_Check_trace(&valimpiTrace, 30);
                }
            }
        }
        {
            {
                ValiMPI_Check_trace(&valimpiTrace, 10);
                ValiMPI_Send_trace(&valimpiTrace, &i, 1, MPI_INT, TASK_GARFOS, TAG_FIM, MPI_COMM_WORLD);
            }
        }
    }
    ValiMPI_Check_trace(&valimpiTrace, 11);
    ValiMPI_End_trace();
}

void
espera_um_pouco(void)
{
    VALIMPI_TRACE_DECL;
    VALIMPI_REQ_LIST_DECL;
    ValiMPI_Req_list_init(&valimpiListReq);
    ValiMPI_Init_trace(&valimpiTrace, "espera_um_pouco");
    ValiMPI_Check_trace(&valimpiTrace, 1);
    {
        {
            ValiMPI_Check_trace(&valimpiTrace, 1);
            processa();
    }} ValiMPI_Check_trace(&valimpiTrace, 1);
    ValiMPI_End_trace();
} void

pensa(int i)
{
    VALIMPI_TRACE_DECL;
    VALIMPI_REQ_LIST_DECL;
    ValiMPI_Req_list_init(&valimpiListReq);
    ValiMPI_Init_trace(&valimpiTrace, "pensa");
    ValiMPI_Check_trace(&valimpiTrace, 1);
    {
        {
            ValiMPI_Check_trace(&valimpiTrace, 2);
            printf("filósofo %d pensando\n", i + 1);
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 3);
            processa();
    }} ValiMPI_Check_trace(&valimpiTrace, 3);
    ValiMPI_End_trace();
} int

come(int i)
{
    VALIMPI_TRACE_DECL;
    VALIMPI_REQ_LIST_DECL;
    ValiMPI_Req_list_init(&valimpiListReq);
    ValiMPI_Init_trace(&valimpiTrace, "come");
    ValiMPI_Check_trace(&valimpiTrace, 1);
    {
        {
            ValiMPI_Check_trace(&valimpiTrace, 2);
            printf("filósofo %d comendo\n", i + 1);
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 3);
            {
                ValiMPI_End_trace();
                return processa();
            }
        }
    }
    ValiMPI_Check_trace(&valimpiTrace, 3);
    ValiMPI_End_trace();
}

int
processa(void)
{
    int x = 500000;
    VALIMPI_TRACE_DECL;
    VALIMPI_REQ_LIST_DECL;
    ValiMPI_Req_list_init(&valimpiListReq);
    ValiMPI_Init_trace(&valimpiTrace, "processa");
    ValiMPI_Check_trace(&valimpiTrace, 1);
    {
        {
            ValiMPI_Check_trace(&valimpiTrace, 1);
            usleep(x);
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 2);
            {
                ValiMPI_End_trace();
                return x / 1000;
            }
        }
    }
    ValiMPI_Check_trace(&valimpiTrace, 3);
    ValiMPI_End_trace();
}

int
main(int argc, char * *argv)
{
    int n_tasks, my_rank, fome;
    VALIMPI_TRACE_DECL;
    VALIMPI_REQ_LIST_DECL;
    ValiMPI_Req_list_init(&valimpiListReq);
    ValiMPI_Init_trace(&valimpiTrace, "main");
    ValiMPI_Check_trace(&valimpiTrace, 1);
    {
        {
            ValiMPI_Check_trace(&valimpiTrace, 2);
            MPI_Init(&argc, &argv);
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 3);
            MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 4);
            MPI_Comm_size(MPI_COMM_WORLD, &n_tasks);
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 5);
            if (n_tasks != N_TASKS)
            {
                ValiMPI_Check_trace(&valimpiTrace, 16);
                {
                    {
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 16);
                            printf("n_tasks(%d) != %d\n", n_tasks, N_FILOS + 1);
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 17);
                            MPI_Finalize();
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 17);
                            {
                                ValiMPI_End_trace();
                                return 1;
                            }
                        }
                    }
                    ValiMPI_Check_trace(&valimpiTrace, NONODE);
                }
            }
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 6);
            if (argc - 1 != N_FILOS)
            {
                ValiMPI_Check_trace(&valimpiTrace, 14);
                {
                    {
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 14);
                            printf("uso: %s [fome do filósofo 1] ... [fome do filósofo %d].\n", argv[0], N_FILOS);
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 15);
                            MPI_Finalize();
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 15);
                            {
                                ValiMPI_End_trace();
                                return 1;
                            }
                        }
                    }
                    ValiMPI_Check_trace(&valimpiTrace, NONODE);
                }
            }
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 7);
            srand(my_rank + time(NULL));
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 8);
            if (my_rank == TASK_GARFOS)
            {
                ValiMPI_Check_trace(&valimpiTrace, 9);
                {
                    garfos();
                    ValiMPI_Check_trace(&valimpiTrace, 9);
                }
            }
            else
            {
                ValiMPI_Check_trace(&valimpiTrace, 10);
                {
                    {
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 10);
                            fome = atoi(argv[my_rank + 1]);
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 11);
                            filosofo(my_rank, fome);
                        }
                    }
                    ValiMPI_Check_trace(&valimpiTrace, 12);
                }
            }
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 13);
            MPI_Finalize();
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 13);
            {
                ValiMPI_End_trace();
                return 0;
            }
        }
    }
    ValiMPI_Check_trace(&valimpiTrace, 18);
    ValiMPI_End_trace();
}
