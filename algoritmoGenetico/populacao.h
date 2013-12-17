package algoritmoGenetico;

public interface populacao {
	/***************************************************************************
	 * populacao.h - description ------------------- begin : Dom Jul 6 2003
	 * copyright : (C) 2003 by Luciano Petinati Ferreira email :
	 * petinat@inf.ufpr.br
	 ***************************************************************************/

	/***************************************************************************
	 * * This program is free software; you can redistribute it and/or modify *
	 * it under the terms of the GNU General Public License as published by *
	 * the Free Software Foundation; either version 2 of the License, or * (at
	 * your option) any later version. * *
	 ***************************************************************************/

	/**
	 * @author Luciano Petinati Ferreira
	 * 
	 *         //#include "diversos.h" //#include "central.h" //#include
	 *         "individuo.h" //#include "ferramenta.h" //#include "tabu.h"
	 */

	/** No descriptions */

	/**
	 * M�todo usado para gerar popula��o inicial para o AG. Cria a popula��o
	 * inicial aleat�ria, com base nas configura��es fornecidas ou Cria a
	 * popula��o inicial baseado em um arquivo fornecido.
	 */
	int geraPopulacaoInicial();

	/** Cria a popula��o inicial baseado em arquivo fornecido. */
	int geraPopulacaoInicialArquivo();

	/**
	 * Cria a popula��o inicial aleat�ria, com base nas configura��es fornecidas
	 */
	int geraPopulacaoInicialAleatoria();

	/**
	 * M�todo usado para verificar se um individuo est� na popula��o
	 * representada pelo arquivo passado por argumento.
	 */
	int inPopulacao(String strIndividuo, String arquivo);

	/**
	 * M�todo usado para incluir um novo indiv�duo no arquivo de popula��o. Caso
	 * o indiv�duo j� exista na popula��o a inser��o n�o ser� realizada.
	 */
	int toPopulacao(int nro, String strIndividuo, String arquivo);

	/**
	 * M�todo usado para avaliar a popula��o do AG. Cada individuo � executado e
	 * o fitness � calculado com base no n�mero de elementos requeridos
	 * satisfeitos pela execu��o do mesmo.
	 */
	int avaliaPopulacao();

	/** M�todo usado para gerar a cobertura dos indiv�duos da popula��o. */
	int geraCoberturaIndividuo();

	/**
	 * M�todo usado para gerar a cobertura dos indiv�duos da popula��o com base
	 * em arquivos gerados pela utiliza��o da ferramenta ValiMPI.
	 */
	int geraCoberturaIndividuoValiMPI();

	/**
	 * M�todo usado para gerar a cobertura dos indiv�duos da popula��o com base
	 * em arquivos gerados pela utiliza��o da ferramenta Proteum.
	 */
	int geraCoberturaIndividuoProteum();

	/**
	 * M�todo usado para armazenar desempenho de um indiv�duo, assim n�o precisa
	 * executa-lo novamente para obter sua cobertura.
	 */
	int toRepositorio(int nro, String desempenho);

	/** M�todo usado para sobrepor duas linhas de coberturas. */
	int sobrepoe(String cobertura, String desempenho);

	/** M�todo que evolui a popula��o de indiv�duos. */
	int evoluiPopulacao();

	/**
	 * M�todo usado para evoluir a popula��o com base no fitness dos indiv�duos.
	 */
	int evolucaoPorFitness();

	/**
	 * M�todo retorna o �ndice do indiv�duo sorteado. Com base na somat�ria do
	 * fitness e na ordem dos indiv�duos, simulando o m�todo de sele��o da
	 * roleta. Retorna qual o indiv�duo possui est� na faixa sorteada.
	 */
	int indiceIndividuoSorteado(double sorteio);

	/** M�todo usado para aplicar muta��o em um indiv�duo. */
	int mutacao(String indiv);

	/**
	 * M�todo que aplica operador crossover nos indiv�duos representados por
	 * indiv1 e indiv2
	 */
	int crossover(String indiv1, String indiv2);

	/** M�todo usado para gerar o fitness da popula��o */
	int geraFitness();

	/** M�todo usado para realizar evolu��o por elitismo. */
	int evolucaoPorElitismo();

	/** Metodo usado para obter o indice do individuo a ser mantido */
	double melhorInedAntNaoEm(String arquivoAuxiliar);

	/** Metodo usado para obter o indice do individuo a ser mantido */
	double melhorFitAntNaoEm(String arquivoAuxiliar);

	/** M�todo usado para calcular o ineditismo da popula��o. */
	int geraBonusIneditismo();

	/**
	 * M�todo usado para calcular a cobertura dos elementos, com base na
	 * cobertura dos indiv�duos.
	 */
	int geraCoberturaElementos();

	/**
	 * M�todo usado para evoluir popula��o com base no �ndice de ineditismo do
	 * indiv�duo.
	 */
	int evolucaoPorIneditismo();

	/**
	 * M�todo usado para gerar um arquivo que reflita os dados de uma popula��o
	 * de forma entend�vel/decodificada.
	 */
	int decodificaPopulacao(String origem, String destino);

	/**
	 * Este m�todo � usado para limpar e regerar o diret�rio de pool e seus
	 * arquivos. Os arquivos s�o gerados para serem importados e executados para
	 * serem avaliados
	 */
	int geraPool();

	/**
	 * Este m�todo retorna o valor do bonus para os indiv�duos que cobriram o
	 * elemento informado por pos.
	 */
	double getBonusElemento(int pos);

	/** Gera o arquivo com a bonifica��o final por ineditismo. */
	int geraIneditismoPopulacao();
	// public: // Public attributes
	/**
	 * Ponteiro para objeto tipo central, e serve para controlar a execu��o do
	 * framework.
	 */
	// central ** ctl;
	// };

}
