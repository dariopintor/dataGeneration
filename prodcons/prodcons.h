#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_SIZE 50

#define DEBUG_MS(...){\
    int rank;\
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);\
    fprintf(stdout, "DEBUG : %s() : p%d: %d : ", __func__, rank, __LINE__);\
    fprintf(stdout, __VA_ARGS__);\
    fprintf(stdout, "\n");\
    fflush(stdout);\
}

/*
struct Buffer {
    char data[MAX_SIZE];
    int data_size;
    int qtde_produzida;
    MPI_Request req;
};
struct Buffer * buffer;
*/
MPI_Request * req;
MPI_Status    status;
