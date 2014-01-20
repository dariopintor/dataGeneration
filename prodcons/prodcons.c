#include <mpi.h>
#include "prodcons.h"

/* ... */

void produce(char **data, int *data_size)
{
    static int x = 1;
    int rank;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);

    // produz data...
    sprintf(*data, "teste %d:%d", rank, x++);
    *data_size = strlen(*data) + 1;
}

/* ... */

void producer(int producao, int size)
{
    int produzido = 0;
    int  data_size;
    char * data = malloc (sizeof(char) * MAX_SIZE);
    
    //buffer = (struct Buffer *) malloc(sizeof(struct Buffer));
    while (produzido < producao) {
        produce(&data, &data_size);
        produzido++;
        MPI_Send(data, data_size, MPI_CHAR, size - 1, 0, MPI_COMM_WORLD);
        DEBUG_MS("enviou p%d: \'%s\'", size - 1, data);
    }
    //free(buffer);
}

/* ... */

int consume(int *necessidade, char *data, int data_size)
{
    // processa data...
    
    (*necessidade)--;
    if (*necessidade <= 0)
        return 1;
    return 0;
}

/* ... */

void consumer(int producao, int size)
{
    int flag;
    int i;
    int satisfeito = 0;   
    int necessidade = producao * (size - 1);

    char ** data = calloc(size - 1, sizeof(char *));
    int * data_size = calloc(size - 1, sizeof(int));
    int * qtde_produzida = calloc(size - 1, sizeof(int));
    req = calloc(size - 1, sizeof (MPI_Request));
    for (i = 0; i < size -1; i++) 
        data[i] = malloc(sizeof(char) * MAX_SIZE);
    
    //buffer = (struct Buffer *) calloc(sizeof(struct Buffer), size - 1);
    
    for (i = 0; i < size - 1; i++) {
        MPI_Irecv(data[i], MAX_SIZE, MPI_CHAR, i, 0, MPI_COMM_WORLD, &req[i]);
        qtde_produzida[i] = 0;
    }

    DEBUG_MS("Irecvs posted, esperando, necessidade = %d", necessidade);
    
    while (!satisfeito) {

        i = 0;
        flag = 0;
        do {
            i = (i + 1) % (size - 1);
            if (qtde_produzida[i] < producao) {
                MPI_Test(&req[i], &flag, &status);
            }
        } while (!flag);
    
        qtde_produzida[i]++;
        MPI_Get_count(&status, MPI_CHAR, &data_size[i]);
        DEBUG_MS("recebido p%d, prod[%d]=%d, data = \'%s\' size=%d", i, i, qtde_produzida[i], data[i], data_size[i]);
        satisfeito = consume(&necessidade, data[i], data_size[i]);
        if (!satisfeito)
            if (qtde_produzida[i] < producao)
                MPI_Irecv(data[i], MAX_SIZE, MPI_CHAR, i, 0, MPI_COMM_WORLD, &req[i]);
    }
    DEBUG_MS("satisfeito");
    //free(buffer);
}

/* ... */

int main(int argc, char *argv[])
{
    int rank, size, producao;
    
    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    if (argc != 2 || size < 2) {
        if (rank == 0) {
            printf("Argumentos ou número de processos errado (mínimo 2).\n");
            printf("Uso: %s <producao de um produtor>\n", argv[0]);
        }
        MPI_Finalize();
        exit(1);
    }
    producao = atoi(argv[1]);
    if (producao <= 0) {
        if (rank == 0) {
            printf("producao %d <= 0\n", producao);
        }
        MPI_Finalize();
        exit(1);
    }

    if (rank != size - 1)
        producer(producao, size);
    else
        consumer(producao, size);

    MPI_Finalize();
    return 0;
}
