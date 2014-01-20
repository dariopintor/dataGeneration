#include "valimpi.h"
#include "prodcons.h"
void
produce(char * *data, int *data_size)
{
    static int x = 1;
    int rank;
    VALIMPI_TRACE_DECL;
    VALIMPI_REQ_LIST_DECL;
    ValiMPI_Req_list_init(&valimpiListReq);
    ValiMPI_Init_trace(&valimpiTrace, "produce");
    ValiMPI_Check_trace(&valimpiTrace, 1);
    {
        {
            ValiMPI_Check_trace(&valimpiTrace, 2);
            MPI_Comm_rank(MPI_COMM_WORLD, &rank);
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 3);
            sprintf(*data, "teste %d:%d", rank, x++);
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 4);
            *data_size = strlen(*data) + 1;
    }} ValiMPI_Check_trace(&valimpiTrace, 5);
    ValiMPI_End_trace();
} void

producer(int producao, int size)
{
    int produzido = 0;
    int data_size;
    char *data = malloc(sizeof(char) * MAX_SIZE);
    VALIMPI_TRACE_DECL;
    VALIMPI_REQ_LIST_DECL;
    ValiMPI_Req_list_init(&valimpiListReq);
    ValiMPI_Init_trace(&valimpiTrace, "producer");
    ValiMPI_Check_trace(&valimpiTrace, 1);
    {
        {
            ValiMPI_Check_trace(&valimpiTrace, 2);
            while (ValiMPI_Check_trace(&valimpiTrace, 3), (produzido < producao))
            {
                ValiMPI_Check_trace(&valimpiTrace, 4);
                {
                    {
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 4);
                            produce(&data, &data_size);
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 5);
                            produzido++;
                        }
                        {
                            {
                                ValiMPI_Check_trace(&valimpiTrace, 6);
                                ValiMPI_Send_trace(&valimpiTrace, data, data_size, MPI_CHAR, size - 1, 0, MPI_COMM_WORLD);
                            }
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 7);
                            DEBUG_MS("enviou p%d: \'%s\'", size - 1, data);
                        }
                    }
                    ValiMPI_Check_trace(&valimpiTrace, 8);
                }
            }
        }
    }
    ValiMPI_Check_trace(&valimpiTrace, 9);
    ValiMPI_End_trace();
}

int
consume(int *necessidade, char *data, int data_size)
{
    VALIMPI_TRACE_DECL;
    VALIMPI_REQ_LIST_DECL;
    ValiMPI_Req_list_init(&valimpiListReq);
    ValiMPI_Init_trace(&valimpiTrace, "consume");
    ValiMPI_Check_trace(&valimpiTrace, 1);
    {
        {
            ValiMPI_Check_trace(&valimpiTrace, 2);
            (*necessidade)--;
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 3);
            if (*necessidade <= 0)
            {
                ValiMPI_Check_trace(&valimpiTrace, 5);
                {
                    {
                        ValiMPI_End_trace();
                        return 1;
                    }
                    ValiMPI_Check_trace(&valimpiTrace, NONODE);
                }
            }
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 4);
            {
                ValiMPI_End_trace();
                return 0;
            }
        }
    }
    ValiMPI_Check_trace(&valimpiTrace, 6);
    ValiMPI_End_trace();
}

void
consumer(int producao, int size)
{
    int flag;
    int i;
    int satisfeito = 0;
    int necessidade = producao * (size - 1);
    char **data = calloc(size - 1, sizeof(char *));
    int *data_size = calloc(size - 1, sizeof(int));
    int *qtde_produzida = calloc(size - 1, sizeof(int));
    VALIMPI_TRACE_DECL;
    VALIMPI_REQ_LIST_DECL;
    ValiMPI_Req_list_init(&valimpiListReq);
    ValiMPI_Init_trace(&valimpiTrace, "consumer");
    ValiMPI_Check_trace(&valimpiTrace, 1);
    {
        {
            ValiMPI_Check_trace(&valimpiTrace, 2);
            req = calloc(size - 1, sizeof(MPI_Request));
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 3);
            for (i = 0; ValiMPI_Check_trace(&valimpiTrace, 4), (i < size - 1); i++)
            {
                ValiMPI_Check_trace(&valimpiTrace, 5);
                {
                    data[i] = malloc(sizeof(char) * MAX_SIZE);
                    ValiMPI_Check_trace(&valimpiTrace, 6);
        }}}
        {
            ValiMPI_Check_trace(&valimpiTrace, 7);
            for (i = 0; ValiMPI_Check_trace(&valimpiTrace, 8), (i < size - 1); i++)
            {
                ValiMPI_Check_trace(&valimpiTrace, 9);
                {
                    {
                        {
                            {
                                ValiMPI_Check_trace(&valimpiTrace, 9);
                                ValiMPI_Irecv_trace(&valimpiTrace, &valimpiListReq, data[i], MAX_SIZE, MPI_CHAR, i, 0, MPI_COMM_WORLD, &req[i]);
                            }
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 10);
                            qtde_produzida[i] = 0;
                        }
                    }
                    ValiMPI_Check_trace(&valimpiTrace, 11);
                }
            }
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 12);
            DEBUG_MS("Irecvs posted, esperando, necessidade = %d", necessidade);
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 13);
            while (ValiMPI_Check_trace(&valimpiTrace, 14), (!satisfeito))
            {
                ValiMPI_Check_trace(&valimpiTrace, 15);
                {
                    {
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 15);
                            i = 0;
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 16);
                            flag = 0;
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 17);
                            do
                            {
                                ValiMPI_Check_trace(&valimpiTrace, 19);
                                {
                                    {
                                        {
                                            ValiMPI_Check_trace(&valimpiTrace, 19);
                                            i = (i + 1) % (size - 1);
                                        }
                                        {
                                            ValiMPI_Check_trace(&valimpiTrace, 20);
                                            if (qtde_produzida[i] < producao)
                                            {
                                                ValiMPI_Check_trace(&valimpiTrace, 21);
                                                {
                                                    {
                                                        {
                                                            ValiMPI_Check_trace(&valimpiTrace, 21);
                                                            ValiMPI_Test(&valimpiTrace, &valimpiListReq, &req[i], &flag, &status);
                                                        }
                                                    }
                                                    ValiMPI_Check_trace(&valimpiTrace, 22);
                                                }
                                            }
                                        }
                                    }
                                    ValiMPI_Check_trace(&valimpiTrace, 23);
                                }
                            }
                            while (ValiMPI_Check_trace(&valimpiTrace, 23), (!flag));
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 24);
                            qtde_produzida[i]++;
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 25);
                            MPI_Get_count(&status, MPI_CHAR, &data_size[i]);
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 26);
                            DEBUG_MS("recebido p%d, prod[%d]=%d, data = \'%s\' size=%d", i, i, qtde_produzida[i], data[i], data_size[i]);
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 27);
                            satisfeito = consume(&necessidade, data[i], data_size[i]);
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 28);
                            if (!satisfeito)
                            {
                                ValiMPI_Check_trace(&valimpiTrace, 29);
                                {
                                    if (qtde_produzida[i] < producao)
                                    {
                                        {
                                            ValiMPI_Check_trace(&valimpiTrace, 30);
                                            {
                                                ValiMPI_Irecv_trace(&valimpiTrace, &valimpiListReq, data[i], MAX_SIZE, MPI_CHAR, i, 0, MPI_COMM_WORLD,
                                                                    &req[i]);
                                                ValiMPI_Check_trace(&valimpiTrace, 31);
                                            }
                                        }
                                    }
                                    ValiMPI_Check_trace(&valimpiTrace, 32);
                                }
                            }
                        }
                    }
                    ValiMPI_Check_trace(&valimpiTrace, 33);
                }
            }
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 18);
            DEBUG_MS("satisfeito");
        }
    }
    ValiMPI_Check_trace(&valimpiTrace, 18);
    ValiMPI_End_trace();
}

int
main(int argc, char *argv[])
{
    int rank, size, producao;
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
            MPI_Comm_rank(MPI_COMM_WORLD, &rank);
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 4);
            MPI_Comm_size(MPI_COMM_WORLD, &size);
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 5);
            if (argc != 2 || size < 2)
            {
                ValiMPI_Check_trace(&valimpiTrace, 6);
                {
                    {
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 6);
                            if (rank == 0)
                            {
                                ValiMPI_Check_trace(&valimpiTrace, 7);
                                {
                                    {
                                        {
                                            ValiMPI_Check_trace(&valimpiTrace, 7);
                                            printf("Argumentos ou número de processos errado (mínimo 2).\n");
                                        }
                                        {
                                            ValiMPI_Check_trace(&valimpiTrace, 7);
                                            printf("Uso: %s <producao de um produtor>\n", argv[0]);
                                        }
                                    }
                                    ValiMPI_Check_trace(&valimpiTrace, 8);
                                }
                            }
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 9);
                            MPI_Finalize();
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 9);
                            exit(1);
                        }
                    }
                    ValiMPI_Check_trace(&valimpiTrace, 9);
                }
            }
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 10);
            producao = atoi(argv[1]);
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 11);
            if (producao <= 0)
            {
                ValiMPI_Check_trace(&valimpiTrace, 12);
                {
                    {
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 12);
                            if (rank == 0)
                            {
                                ValiMPI_Check_trace(&valimpiTrace, 13);
                                {
                                    {
                                        {
                                            ValiMPI_Check_trace(&valimpiTrace, 13);
                                            printf("producao %d <= 0\n", producao);
                                        }
                                    }
                                    ValiMPI_Check_trace(&valimpiTrace, 14);
                                }
                            }
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 15);
                            MPI_Finalize();
                        }
                        {
                            ValiMPI_Check_trace(&valimpiTrace, 15);
                            exit(1);
                        }
                    }
                    ValiMPI_Check_trace(&valimpiTrace, 15);
                }
            }
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 16);
            if (rank != size - 1)
            {
                ValiMPI_Check_trace(&valimpiTrace, 17);
                {
                    producer(producao, size);
                    ValiMPI_Check_trace(&valimpiTrace, 18);
                }
            }
            else
            {
                ValiMPI_Check_trace(&valimpiTrace, 19);
                {
                    consumer(producao, size);
                    ValiMPI_Check_trace(&valimpiTrace, 20);
                }
            }
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 21);
            MPI_Finalize();
        }
        {
            ValiMPI_Check_trace(&valimpiTrace, 21);
            {
                ValiMPI_End_trace();
                return 0;
            }
        }
    }
    ValiMPI_Check_trace(&valimpiTrace, 21);
    ValiMPI_End_trace();
}
