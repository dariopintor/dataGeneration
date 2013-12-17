/***************************************************************************
                          central.h  -  description
                             -------------------
    begin                : Dom Jul 6 2003
    copyright            : (C) 2003 by Luciano Petinati Ferreira
    email                : petinat@inf.ufpr.br
 ***************************************************************************/

/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
#include "diversos.h"
#ifndef CENTRAL_H
#define CENTRAL_H

#define TDS_PATH "/home/asano/IC/ValiPAR/vali_tds/tdsgen/"


/**Classe de controle. Interpreta arquivo de configura��o obtendo informa��es sobre execu��o.
  *@author Luciano Petinati Ferreira
  */
//#include"diversos.h"
#include"l_string.h"

class central {
public:
	central();
	~central();
  /** Metodo que mostra na tela o valor de todas as vari�veis de controle da classe central. */
  void printAll();

  /** No descriptions */
  int setMelhorCobertura(double valor);
  /** No descriptions */
  int setIndiceMelhorGeracao(int valor);
  /** No descriptions */
  int geraLinhaPerda ();
  /** No descriptions */
  int alocaLinhaPerda ();
  /** No descriptions */
  int alocaLinhaCoberturaAtual ();
  /** No descriptions */
  int alocaCoberturaGlobal ();
  /** No descriptions */
  int alocaLinhaCoberturaAnterior ();
  /** M�todo para c�lculo do m�dulo entre dois doubles. */
  double modulo(double a, double b);
  /** M�todo para gera��o aleat�ria de um double, simula��o de sorteio. */
  double geraSorteio(double maximo);
  /** M�todo para avaliar a cobertura de cada indiv�duo na popula��o do AG. */
  int avaliacaoCobertura();
  /** No descriptions */
  int atualizaLinhaCoberturas(char * novaCobertura);
  /** No descriptions */
  int inRepositorio(char * strIndiv, char ** desempenho);
  /** No descriptions */
  int atualizaPerda(char * desempenho);
  /** No descriptions */
  int manutencaoMelhorGeracao();
  /** Metodo que carrega na classe central informa��es sobre a execu��o da ferramenta. */
  int interpretaArquivoConfiguracao();
  /** M�todo usado para gerar arquivo tar com informa��es completas para cada gera��o. */
  void backup();
  /** M�todo usado para gerar informa��es do progresso do framework ( gera��o, cobertura ). */
  void status();
  /** M�todo usado para apresentar o resultado da execu��o do framework. */
  void resultado();
  /** M�todo usado para atribuir valor � vari�vel de controle respons�vel por pausa ao fim de cada gera��o. */
  int setPausaGeracao (int valor);
  /** M�todo usado para atribuir valor � vari�vel de controle respons�vel pela gera��o de log de execu��o do framework. */
  int setGeraLog (int valor);
  /** M�todo usado para atribuir valor � vari�vel de controle que possui o n�mero de entradas por argumentos necess�rio para execu��o do programa em teste. */
  int setNumeroArgumentos (int valor);
  /** M�todo usado para atribuir valor a vari�vel de controle que identifica o tipo de string que o framework suporta. */
  int setTipoString(char * valor);
  /** No descriptions */
  int setQuantidadeFitness (double valor);
  /** No descriptions */
  int setQuantidadeIneditismo(double valor);
  /** M�todo usado para atribuir valor � vari�vel respons�vel pela quantidade de indiv�duos gerados por elitismo. */
  int setQuantidadeElitismo (double valor);
  /** No descriptions */
  int setArquivoBonusIneditismo (char * valor);
  /** No descriptions */
  int setArquivoIneditismo (char * valor);
  /** No descriptions */
  int setArquivoFitness (char * valor);
  /** No descriptions */
  int setArquivoPopulacaoTemporario (char * valor);
  /** No descriptions */
  int setArquivoPopulacao (char * valor);
  /** No descriptions */
  int setArquivoPopulacaoInicial (char * valor);
  /** No descriptions */
  int setDiretorioPool (char * valor);
  /** No descriptions */
  int setDiretorio (char * valor);
  /** No descriptions */
  int setFormatoIndividuo (char * valor);
  /** No descriptions */
  int setArquivoFonte (char * valor);
  /** No descriptions */
  int setArquivoSemelhancaIndividuos (char * valor);
  /** No descriptions */
  int setArquivoCoberturaIndividuo (char * valor);
  /** No descriptions */
  int setArquivoCoberturaElemento (char * valor);
  /** No descriptions */
  int setArquivoVariacaoFitness (char * valor);
  /** No descriptions */
  int setFuncaoATestar (char * valor);
  /** No descriptions */
  int setArquivoRepositorio (char * valor);
  /** No descriptions */
  int setArquivoAvalCobIndividuos (char * valor);
  /** No descriptions */
  int setArquivoObsCobertura (char * valor);
  /** No descriptions */
  int setArquivoMelhorPopulacao (char * valor);
  /** No descriptions */
  int setArquivoTabu (char * valor);
  /** No descriptions */
  int setArquivoTabuAux (char * valor);
  /** No descriptions */
  int setCoberturaCriterio (double valor);
  /** No descriptions */
  int setMaximoGeracoes (double valor);
  /** No descriptions */
  int setTamanhoPopulacao (double valor);
  /** No descriptions */
  int setTamanhoString (char * valor);
  /** No descriptions */
  int setCriterioTeste (char * valor);
  /** No descriptions */
  int setTamanhoIndividuo ();
  /** No descriptions */
  int setVariacaoInteiro (char * valor);
  /** No descriptions */
  int setTaxaCrossover (double valor);
  /** No descriptions */
  int setTaxaMutacao (double valor);
  /** No descriptions */
  int tamanhoTipo(int pos);
  /** No descriptions */
  int setCriterioTesteValiMPI (char * valor);
  /** No descriptions */
  int setSomatoriaFitness ( double valor );
  /** No descriptions */
  int setCoberturaAtual (double valor);
  /** No descriptions */
  int setQuantidadeElemento (double valor);
  /** No descriptions */
  int inicioTipo (int pos);
  /** M�todo usado para verificar se o framework deve encerrar execu��o ou por ter alcan�ado a cobertura desejada ou por alcan�ar limite de gera��es. */
  int paraTeste();
  /** M�todo usado para iniciar a execu��o da ferramenta.
  Apagar diret�rios e arquivos que possam ter sidos gerados por execu��es anteriores. */
  int prepareExecution();
  /** M�todo para atribuir valores para vari�vel de controle comandPath, com realoca��o de mem�ria se necess�rio. */
  int setComandPath (char * valor);
  /** No descriptions */
  int setEnviroment(char * valor);
  /** M�todo usado para recalcular a quantidade de individuos gerados por elitismo, sele��o e ineditismo. */
  int recalculaPorcEvolucao();
  /** M�todo usado para atribuir valor para vari�vel de controle linhaCoberturaAnterior */
  int setLinhaCoberturaAnterior( char * valor );
  /** M�todo usado para atribuir valor para vari�vel de controle linhaCoberturaAtual. */
  int setLinhaCoberturaAtual (char * valor);
  /** M�todo usado para configurar o valor de ativaTabu. */
  int setAtivaTabu(int valor);
  /** M�todo usado para configurar o atributo geracoesComRepositorio. */
  int setGeracoesComRepositorio(double valor);
  /** Atualiza a coberturaGlobal com a cobertura passada por argumento. */
  int atualizaCoberturaGlobal( char * novaCobertura );
  /** M�todo que configura o final da primeira execu��o. */
  int setFimPrimeiraExecucao();

	int setNProcess(int);
	int setFuncoes(char *);
	int setCCArgs(char *);

public: // Public attributes
  /** Variavel de controle para pausar a execucao a cada geracao. */
  int pausaGeracao;
  /** Variavel de controle para gerar log da execucao. */
  int geraLog;
  /** Variavel de controle para indicar quantos argumentos o programa em teste necessita */
  int numeroArgumentos;
  /** Variavel de controle para indicar o tamanho m�nimo para o tipo string ( char * ). */
  double tamanhoMinimoString;
  /** Variavel de controle para indicar o tamanho m�ximo para o tipo string ( char * ). */
  double tamanhoMaximoString;
  /** Variavel de controle para indicar o tamanho m�nimo para o tipo inteiro. */
  int tamanhoMinimoInteiro;
  /** Variavel de controle para indicar o tamanho m�ximo para o tipo inteiro. */
  int tamanhoMaximoInteiro;
  /** Variavel de controle para indicar o tamanho m�ximo que um argumento pode assumir para o programa em teste, desconsiderando seu tipo. */
  double tamanhoMaximoArgumento;
  /** Variavel de controle para indicar a varia��o do tipo inteiro. */
  int variacaoInteiro;
  /** Variavel de controle para indicar a quantidade de elementos requeridos para o programa em teste. */
  double quantidadeElemento;
  /** Variavel de controle para conter a cobertura de elementos requeridos da popula��o atual. */
  double coberturaAtual;
  /** Variavel de controle para conter a somat�ria do fitness de todos os indiv�duos para auxiliar a sele��o por roleta. */
  double somatoriaFitness;
  /** Variavel de controle para indicar o crit�rio de teste a ser usado na execu��o do framework. */
  char * criterioTeste;
  /** Variavel de controle para indicar a fun��o a ser testada do programa em teste. */
  char * funcaoATestar;
  /** Variavel de controle para indicar o crit�rio de teste da valiMPI a ser usado na execu��o do framework. */
  char * criterioTesteValiMPI;
  /**  */
  char * diretorio;
  /** Variavel de controle para indicar o diret�rio pool, usado para entrada de dados na execu��o do programa j� instrumentado. */
  char * diretorioPool;
  /** Variavel de controle para indicar o n�mero m�ximo de gera��es a serem geradas caso a cobertura desejada n�o seja alcan�ada. */
  double maximoGeracoes;
  /** Variavel de controle para indicar a gera��o atual. */
  double geracaoAtual;
  /** Vari�vel de controle para indicar o �ndice da melhor gera��o. */
  double indiceMelhorGeracao;
  /** Vari�vel de controle para indicar o tamanho do indiv�duo usado no AG. */
  double tamanhoIndividuo;
  /** Vari�vel de controle para indicar o tamanho da popula��o usada no AG. */
  double tamanhoPopulacao;
  /** Vari�vel de controle para indicar a cobertura desejada para o crit�rio selecionado. */
  double coberturaCriterio;     //novo nome coberturaDesejada
  /** Vari�vel de controle para indicar a melhor cobertura poss�vel atrav�s de todo o processo. Usa informa��es da lista Tabu. */
  double melhorCobertura;
  /** Vari�vel de controle para indicar a taxa de muta��o usada no AG. */
  double taxaMutacao;
  /** Vari�vel de controle para indicar a taxa de Crossover usada no AG */
  double taxaCrossover;
  /** Vari�vel de controle para indicar a quantidade de indiv�duos da nova gera��o que devem ser gerados com base no elitismo (fitness). */
  double quantidadeElitismo;
  /** Vari�vel de controle para indicar a quantidade de indiv�duos da nova gera��o que devem ser gerados com base no score de ineditismo dos indiv�duos. */
  double quantidadeIneditismo;
  /** Vari�vel de controle para indicar a quantidade de indiv�duos da nova gera��o que devem ser gerados com base na sele��o e operadores gen�ticos. */
  double quantidadeFitness;        //novo nome quantidadeSelecao
  /** Vari�vel de controle para indicar o formato da entrada do programa, consequentemente o formato do indiv�duo do AG. */
  char * formatoIndividuo;
  /** Vari�vel de controle para conter informa��es sobre perda de cobertura de elementos requeridos. */
  char * linhaPerda;
  /** Vari�vel de controle para conter a cobertura atual de elementos requeridos ( X: para coberto, -: para n�o coberto ). */
  char * linhaCoberturaAtual;
  /** Vari�vel de controle para conter informa��es de cobertura de elementos requeridos da gera��o anterior. */
  char * linhaCoberturaAnterior;
  /** Vari�vel de controle para conter informa��es a cobertura global de elementos requeridos, independente de gera��o. */
  char * coberturaGlobal;         // novo nome linhaCoberturaGlobal
  /** Vari�vel de controle para conter o nome do arquivo da lista Tabu. */
  char * arquivoTabu;
  /** Vari�vel de controle para conter o nome do arquivo tempor�rio da lista Tabu. */
  char * arquivoTabuAux;
  /** Vari�vel de controle para conter o nome do arquivo fonte do programa em teste. */
  char * arquivoFonte;
    /** Vari�vel de controle que identifica o tipo de string que o framework suporta. */
  char * tipoString;
  /**  Vari�vel de controle que conter� o comando a ser executado por chamada system com
  vari�vel de ambiente PATH corretamente configurada. */
  char * comandPath;
  /** Vari�vel de controle que conter� o PATH correto baseado na ferramenta de teste a ser usada. */
  //char * enviroment;
  l_string enviroment;

  /** Atributos sem coment�rios...*/
  char    * arquivoPopulacaoInicial;
  char    * arquivoPopulacao;
  char    * arquivoMelhorPopulacao;	
  char    * arquivoPopulacaoTemporario;
  char    * arquivoFitness;
  char    * arquivoVariacaoFitness;
  char    * arquivoCoberturaIndividuo;
  char    * arquivoCoberturaElemento;	
  char    * arquivoAvalCobIndividuos;
  char    * arquivoSemelhancaIndividuos;
  char    * arquivoObsCobertura;
  char    * arquivoRepositorio;	
  /** Vari�vel de controle para manter o tamanho de enviroment. */
  long tamEnviroment;
  /** Variavel de controle para manter o tamanho de comandPath. */
  long tamComandPath;
  /** Atributo usado para conter o nome para um arquivo tempor�rio para a popula��o. */
  l_string arquivoPopulacaoTemporario2;
  /** Atributo usado para controlar a ativa��o do recurso de lista Tabu. */
  int ativaTabu;
  char    * arquivoBonusIneditismo;
  char    * arquivoIneditismo;
  /** Atributo usado para guardar quantas ger��es ser�o usadas/configuradas para verificar o reposit�rio de execu��o antes de executar um determinado caso de teste. Isto pode aumentar o desempenho do sistema. */
  double geracoesComRepositorio;
  /** Atributo com o tempo em segundos da execu��o do sistema. */
  time_t inicioExecucao;
  /** Atributo com o tempo em segundo do fim de execu��o do sistema */
  time_t fimExecucao;
  /** Atributo com o tempo em segundo do fim da primeira execu��o do sistema */
  time_t fimPrimeiraExecucao;
  /** Atributo para manter a cobertura inicial do processo. */
  double coberturaInicial;

	/** Atributo que diz quantos processos o programa usa.*/
	int nProcess;
	/** Atributo que diz qual processo ser� qual n�mero*/
	char *funcoes;
	char *ccargs;

};


#endif
