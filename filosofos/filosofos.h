
/* número de filósofos */
#define N_FILOS 5

/* número de tarefas */
#define N_TASKS (N_FILOS + 1)

/* tarefa responsável pelo tratamento dos garfos (última tarefa) */
#define TASK_GARFOS N_FILOS

/* garfos à esquerda e à direita do filósofo i */
//#define ESQ(i) (i)
//#define DIR(i) ((i + 1) % N_FILOS)

/* estados do garfo */
#define LIVRE      3
#define OCUPADO    4

/* tags de comunicação */
#define TAG_FIM   99
#define TAG_PEGA   1
#define TAG_LIBERA 2

/* valores booleanos */
#define TRUE  (1==1)
#define FALSE !TRUE

/* variável global */
MPI_Status status;

/* protótipos */
void filosofo(int i, int fome);
void garfos(void);
void pensa(int i);
int come(int i);
void espera_um_pouco(void);
int processa(void);
