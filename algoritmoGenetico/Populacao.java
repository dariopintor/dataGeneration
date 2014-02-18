package algoritmoGenetico;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.Random;

import main.Diversos;
import main.Mersenne;
import ferramenta.Central;
import ferramenta.Ferramenta;

/***************************************************************************
 populacao.cpp  -  description
 -------------------
 begin                : Dom Jul 6 2003
 copyright            : (C) 2003 by Luciano Petinati Ferreira
 email                : petinat@inf.ufpr.br
 ***************************************************************************/

/***************************************************************************
 * * This program is free software; you can redistribute it and/or modify * it
 * under the terms of the GNU General Public License as published by * the Free
 * Software Foundation; either version 2 of the License, or * (at your option)
 * any later version. * *
 ***************************************************************************/

public class Populacao {

	public static Individuo[] individuos;
	public static Individuo[] novosIndividuos;
	private static int tamPopulacao;

	// cria uma população com indivíduos sem valor, será composto posteriormente
	public static void novaPopulacao(int tamPop) {
		tamPopulacao = tamPop;
		novosIndividuos = new Individuo[tamPop];
		for (int i = 0; i < individuos.length; i++) {
			novosIndividuos[i] = null;
		}
	}

	// cria uma população com indivíduos sem valor, será composto posteriormente
	public Populacao(int tamPop) {
		tamPopulacao = tamPop;
		individuos = new Individuo[tamPop];
		for (int i = 0; i < individuos.length; i++) {
			individuos[i] = null;
		}
	}

	/** coloca um indivíduo na próxima posição disponível da população */
	public static void setIndivNovaPop(Individuo individuo) {
		for (int i = 0; i < individuos.length; i++) {
			if (novosIndividuos[i] == null) {
				novosIndividuos[i] = individuo;
				return;
			}
		}
	}

	public static void geraPopulacaoInicial() throws IOException {

		Diversos.toFile("log_erro.log", "---geraPopulacaoInicial");
		if (Central.arquivoPopulacaoInicial == null) {
			geraPopulacaoInicialAleatoria(Central.formatoIndividuo.length(),
					(int) Central.tamanhoPopulacao);
		} else {
			geraPopulacaoInicialArquivo();
		}

		// move de "Central.arquivoPopulacaoTemporario" para
		// "Central.arquivoPopulacao"
		Diversos.copyFile(Central.arquivoPopulacao.getPath(),
				Central.arquivoPopulacaoAnterior);
		Diversos.toFile("log_erro.log", "---saindo geraPopulacaoInicial");

	}

	/**
	 * Cria a população inicial aleatória, com base nas configurações fornecidas
	 */
	public static void geraPopulacaoInicialAleatoria(int numGenes, int tamPop)
			throws IOException {
		System.out.println("Gerando População inicial Aleatória");
		String saida = "";
		individuos = new Individuo[tamPop];
		Diversos.limpaArquivo(Central.arquivoPopulacao.getPath());
		for (int contador = 0; contador < individuos.length; contador++) {
			individuos[contador] = new Individuo(numGenes);

			saida = String.format("%d : %s", contador + 1,
					individuos[contador].getGenes());
			Diversos.escreverArquivo(Central.arquivoPopulacao, saida);

		} // fim for

		System.out
				.println("Fim da Geração da População inicial Aleatória \n\n");
	}

	/**
	 * Cria a populacao inicial baseado em arquivo fornecido.
	 * 
	 * @throws IOException
	 */
	public static void geraPopulacaoInicialArquivo() throws IOException {

		System.out.println("Gerando População inicial Arquivo");

		individuos = new Individuo[(int) Central.tamanhoPopulacao];
		Diversos.limpaArquivo(Central.arquivoPopulacao.getPath());

		String linha = null, saida = "";
		int contador = 0;
		FileReader arq = new FileReader(Central.arquivoPopulacaoInicial);
		BufferedReader lerArq = new BufferedReader(arq);

		linha = lerArq.readLine();
		for (contador = 0; linha != null;) {
			linha = linha.trim();
			individuos[contador] = new Individuo(linha);

			saida = String.format("%d : %s", contador + 1,
					individuos[contador].getGenes());
			Diversos.escreverArquivo(Central.arquivoPopulacao, saida);

			if (linha != ("")) {
				contador++;
			}
			linha = lerArq.readLine();
		} // fim for

		lerArq.close();
		Central.setTamanhoPopulacao(contador);

		System.out.println("Fim População Inicial Arquivo \n\n");
		// Central.recalculaPorcEvolucao();
	}

	/**
	 * Metodo usado para avaliar a populacao do AG. Cada individuo eh executado
	 * e o fitness o calculado com base no numero de elementos requeridos
	 * satisfeitos pela execucao do mesmo.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void avaliaPopulacao() throws IOException,
			InterruptedException {

		if (Central.geraLog != 0) {
			Diversos.toFile("log_erro.log", "---avaliaPopulacao");
		}

		geraCoberturaIndividuo();
		geraBonusIneditismo();
		geraIneditismoPopulacao();
		geraFitness();

		if (Central.geraLog != 0) {
			Diversos.toFile("log_erro.log", "---saindo avaliaPopulacao");
		}

	}// fim avaliaPopulacao()

	/**
	 * Método usado para gerar a cobertura dos indivíduos da população.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void geraCoberturaIndividuo() throws IOException,
			InterruptedException {
		Diversos.toFile("log_erro.log",
				"------entrando em geraCoberturaIndividuo");

		// gera cobertura dos individuos na valimpi
		geraCoberturaIndividuoValiMPI();

		Central.setCoberturaAtual(Diversos.numberOf(
				Central.linhaCoberturaAtual, 'X')
				* 100
				/ Central.quantidadeElemento);

		Central.coberturaGlobal = sobrepoe((Central.coberturaGlobal),
				Central.linhaCoberturaAtual);

		geraCoberturaElementos();

		Diversos.toFile("log_erro.log", "------saindo geraCoberturaIndividuo");
	}

	/**
	 * * Metodo usado para gerar a cobertura dos individuos da populacao com
	 * base em arquivos gerados pela utilizacao da ferramenta ValiMPI. Para cada
	 * individuo da populacao, deve obter a cobertura. verifica se ja esta no
	 * repositorio, se estiver, recupera a cobertura/desempenho do repositorio.
	 * se nao estiver executa o programa instrumentado para cada individuo.
	 * obtem a cobertura alcancada. Documenta a cobertura no arquivo verifica se
	 * deve ser incluido na lista tabu. se tiver, substitui todos os individuos
	 * com cobertura coberta por esse individuo e insere na lista tabu senao nao
	 * faz nada.
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void geraCoberturaIndividuoValiMPI() throws IOException,
			InterruptedException {

		System.out.println("Avaliando Individuos da População "
				+ Central.geracaoAtual);
		String  linhaCobertura = "", linhaCoberturaCriterio = null;
		
		Diversos.limpaArquivo(Central.arquivoCoberturaIndividuo.getPath());

		for (int contador = 0; contador < Central.tamanhoPopulacao; contador++) {
			// verifica necessidade de execucao
			if ((Central.geracaoAtual >= Central.geracoesComRepositorio)
					|| (Central.inRepositorio(individuos[contador].getGenes(),
							"") == "")) {

				Ferramenta.evaluateIndividual(contador);
				linhaCobertura = Ferramenta.obtemCoberturaValiMPI(contador);
				Populacao.toRepositorio((contador), linhaCobertura.trim());
				

			} // fim if
			else {
				linhaCobertura = Central.inRepositorio(
						individuos[contador].getGenes(), "");				
			}
			
			linhaCoberturaCriterio = sobrepoe(linhaCoberturaCriterio, linhaCobertura);
			
			String conteudo = contador + ": " + linhaCobertura;
			Diversos.escreverArquivo(Central.arquivoCoberturaIndividuo.getPath(), conteudo);
			
			if ( Central.ativaTabu != 0) {
		        Tabu.manutencaoTabu(linhaCobertura, contador);
		        }
		
		 	Central.atualizaLinhaCoberturas(linhaCoberturaCriterio);
		}
		System.out.println("Fim Avaliação Individuo da População "
				+ Central.geracaoAtual);
	}// fim geraCoberturaIndividuoValiMPI()

	public static void evoluiPopulacao() throws IOException {
		System.out.printf("\n\n###########################################");
		System.out.println("\nEvoluindo População " + Central.geracaoAtual);
		Diversos.escreverArquivo("evolucao.fil",
				"####################nEvolução população: " + ""
						+ Central.geracaoAtual + "#######################");

		// gera um nova população
		novaPopulacao((int) Central.tamanhoPopulacao);
		if (Central.quantidadeFitness > 0) {
			evolucaoPorFitness();
		}
		if (Central.quantidadeElitismo > 0)
			evolucaoPorElitismo();

		if (Central.quantidadeIneditismo > 0)
			evolucaoPorIneditismo();

		individuos = novosIndividuos;

		String saida = "";
		Diversos.limpaArquivo(Central.arquivoPopulacao.getPath());
		// escreve a populacao em arquivo
		for (int contador = 0; contador < (int) Central.tamanhoPopulacao; contador++) {
			saida = String.format("%d : %s", contador + 1,
					individuos[contador].getGenes());
			// System.out.println(individuos[contador].getGenes());
			Diversos.escreverArquivo(Central.arquivoPopulacao, saida);
		}
		/*
		 * Para recuperar individuos que foram perdidos da geracao passada e nao
		 * poderiam ser pois era um individuo que cobria um elemento nao coberto
		 * na nova populacao, deve-se manter uma copia da populacao anterior.
		 * Essa esta sendo guardada em ctl->arquivoPopulacaoTemporario...
		 */

		// copia de Central.arquivoPopulacao para "popManejo.pop"
		Diversos.copyFile(Central.arquivoPopulacao.getPath(), "popManejo.pop");

		// move de "Central.arquivoPopulacaoTemporario" para
		// "Central.arquivoPopulacao"
		Diversos.copyFile(Central.arquivoPopulacao.getPath(),
				Central.arquivoPopulacaoAnterior);

		if (Central.geraLog != 0)
			Diversos.toFile("log_erro.log", "---saindo EvoluiPopulacao");

		System.out.println("Fim evolução da População " + Central.geracaoAtual);

	}

	/**
	 * Metodo usado para realizar evolucao por elitismo.
	 * 
	 * @throws IOException
	 */
	public static void evolucaoPorElitismo() throws IOException {
		Diversos.toFile("log_erro.log", "------evolucaoIneditismo");
		String saida = "";
		int contador = 0;
		int posicao = 0;
		double porcFitness, porcElitismo;

		porcFitness = Math.floor((Central.quantidadeFitness / 10)
				* Central.tamanhoPopulacao);
		porcElitismo = Math.round((Central.quantidadeElitismo / 10)
				* Central.tamanhoPopulacao);

		for (contador = (int) (porcFitness); contador < (int) (porcFitness + porcElitismo);) {
			Individuo indiv;

			posicao = melhorFitAntNaoEm(Central.arquivoPopulacaoAnterior);
			if (posicao != -1) {
				if (!toPopulacao(contador, individuos[posicao].getGenes(),
						Central.arquivoPopulacaoAnterior)) {
					setIndivNovaPop(individuos[posicao]);
					saida = String.format("%d INDIVIDUO : %s Elitismo",
							(contador) + 1, individuos[posicao].getGenes());
					Diversos.toFile("evolucao.fil", saida);
					contador++;
				}
			} else {

				indiv = new Individuo(Central.formatoIndividuo.length());

				if (toPopulacao(contador, indiv.getGenes(),
						Central.arquivoPopulacaoAnterior)) {
					setIndivNovaPop(indiv);
					saida = String.format(
							"%d INDIVIDUO : %s Elitismo aleatório",
							(contador) + 1, indiv.getGenes());
					Diversos.toFile("evolucao.fil", saida);
					contador++;

				}
			}

		} // fim for ineditismo
		Diversos.toFile("log_erro.log", "------saindo evolucaoIneditismo");
	}

	/**
	 * Metodo usado para evoluir populacao com base no indice de ineditismo do
	 * individuo.
	 * 
	 * @throws IOException
	 */
	public static void evolucaoPorIneditismo() throws IOException {

		if (Central.geraLog != 0) {
			Diversos.toFile("log_erro.log", "------evolucaoIneditismo");
		}

		String saida = "";
		int contador = 0;
		int posicao = 0;
		double porcFitness, porcElitismo, porcIneditismo;
		porcFitness = Math.floor((Central.quantidadeFitness / 10)
				* Central.tamanhoPopulacao);
		porcElitismo = Math.round((Central.quantidadeElitismo / 10)
				* Central.tamanhoPopulacao);
		porcIneditismo = Math.round((Central.quantidadeIneditismo / 10)
				* Central.tamanhoPopulacao);

		for (contador = (int) (porcFitness + porcElitismo); contador < (int) (porcFitness
				+ porcElitismo + porcIneditismo);) {
			posicao = melhorInedAntNaoEm(Central.arquivoPopulacaoAnterior);
			Individuo indiv;

			if (posicao != -1) {
				if (!toPopulacao(contador, individuos[posicao].getGenes(),
						Central.arquivoPopulacaoAnterior)) {
					setIndivNovaPop(individuos[posicao]);
					saida = String.format("%d INDIVIDUO : %s Ineditismo",
							(contador) + 1, individuos[posicao].getGenes());
					Diversos.toFile("evolucao.fil", saida);
					contador++;
				}
			} else {

				indiv = new Individuo(Central.formatoIndividuo.length());

				if (toPopulacao(contador, indiv.getGenes(),
						Central.arquivoPopulacaoAnterior)) {
					setIndivNovaPop(indiv);
					saida = String.format(
							"%d INDIVIDUO : %s  Ineditismo aleatório",
							(contador) + 1, indiv.getGenes());
					Diversos.toFile("evolucao.fil", saida);

					contador++;

				}
			}

		} // fim for

		if (Central.geraLog != 0) {
			Diversos.toFile("log_erro.log", "------saindo evolucaoIneditismo");
		}
	}

	/**
	 * Metodo usado para gerar o fitness da populacao
	 * 
	 * @throws IOException
	 */
	public static void geraFitness() throws IOException {

		String saida = null, linha = "";
		String[] quebra = null;

		if (Central.geraLog != 0) {
			Diversos.toFile("log_erro.log", "------geraFitness");
		}
		String linhaCobertura = null, desempenho = null;
		double fitness = 0, somatoriaFitness = 0;

		FileReader fr = new FileReader(Central.arquivoCoberturaIndividuo);
		BufferedReader br = new BufferedReader(fr);
		linha = br.readLine();
		Diversos.limpaArquivo(Central.arquivoFitness.getPath());
		for (int i = 0; i < Central.tamanhoPopulacao; i++) {
			quebra = linha.split(":");
			desempenho = quebra[1].trim();
			linhaCobertura = sobrepoe(linhaCobertura, desempenho);
			fitness = ((double) Diversos.numberOf(desempenho, 'X') * 100)
					/ (double) (Central.quantidadeElemento);
			individuos[i].setAptidao(fitness);
			saida = i + 1 + ": " + fitness;
			// System.out.println(saida);
			Diversos.escreverArquivo(Central.arquivoFitness, saida);

			linha = br.readLine();
			somatoriaFitness += fitness;
		}// fim for
			// rastro("PONTO1.7");*/

		br.close();
		Central.setSomatoriaFitness(somatoriaFitness);
		Diversos.toFile(Central.arquivoObsCobertura.getPath(), linhaCobertura);
		Diversos.toFile("log_erro.log", "------saindo geraFitness");
	}

	/**
	 * Metodo usado para evoluir a populacao com base no fitness dos individuos.
	 * 
	 * @throws IOException
	 */
	public static void evolucaoPorFitness() throws IOException {
		Diversos.toFile("log_erro.log", "------evolucaoFitness");
		double cross = 0, mut = 0;
		double sorteio = 0;
		int contador = 0;
		double porcFitness;
		porcFitness = Math.floor((Central.quantidadeFitness / 10)
				* Central.tamanhoPopulacao);
		Individuo pai1;
		Individuo pai2;
		String saida = null;
		Individuo[] filhos = new Individuo[2];

		for (contador = 0; contador < porcFitness;) {
			sorteio = Central.geraSorteio(Central.somatoriaFitness);
			pai1 = individuos[indiceIndividuoSorteado(sorteio)];

			saida = String.format("1º Sorteio  : %f :  Pai 1 : %s", sorteio,
					pai1.getGenes());
			Diversos.toFile("evolucao.fil", saida);

			sorteio = Central.geraSorteio(Central.somatoriaFitness);
			pai2 = individuos[indiceIndividuoSorteado(sorteio)];

			saida = String.format("2º Sorteio  : %f :  Pai 2 %s", sorteio,
					pai2.getGenes());
			Diversos.toFile("evolucao.fil", saida);

			// aplica a taxa de crossover
			cross = Math.random();
			if (cross <= Central.taxaCrossover) {
				filhos = crossover(pai1, pai2);

				saida = String.format(
						"CROSSOVER   Pai 1: %s   \nCROSSOVER   Pai 2: %s",
						filhos[0].getGenes(), filhos[1].getGenes());
				Diversos.toFile("evolucao.fil", saida);
			}

			// aplica a taxa de mutacao
			mut = Math.random();
			if (mut <= Central.taxaMutacao) {
				filhos[0] = new Individuo(mutacao(filhos[0].getGenes()));
				saida = String.format("MUTACAO 1   Pai 1: %s",
						filhos[0].getGenes());
				Diversos.toFile("evolucao.fil", saida);
			}

			if (toPopulacao(contador, filhos[0].getGenes(),
					Central.arquivoPopulacaoAnterior)) {
				contador++;
				// adiciona na população
				setIndivNovaPop(filhos[0]);
				saida = String.format("%d INDIVIDUO : %s fitness", contador,
						filhos[0].getGenes());
				Diversos.toFile("evolucao.fil", saida);
			}

			if (contador < Central.quantidadeFitness) {
				mut = Math.random();
				if (mut <= Central.taxaMutacao) {
					filhos[1] = new Individuo(mutacao(filhos[1].getGenes()));
					saida = String.format("MUTACAO 2  Pai 1: %s",
							filhos[1].getGenes());
					Diversos.toFile("evolucao.fil", saida);
				}
				if (toPopulacao(contador, filhos[1].getGenes(),
						Central.arquivoPopulacaoAnterior)) {
					contador++;
					setIndivNovaPop(filhos[1]);
					saida = String.format("%d INDIVIDUO : %s fitness",
							contador, filhos[1].getGenes());
					Diversos.toFile("evolucao.fil", saida);
					/**/
				}
			}// fim if

		} // fim for fitness
		if (Central.geraLog != 0)
			Diversos.toFile("log_erro.log", "------saindo evolucaoFitness");

		// adiciona os filhos na nova geração

	}

	public static Individuo[] crossover(Individuo individuo1,
			Individuo individuo2) {
		int posCross = 0, tamTipo = 0, inicTipo = 0, tamFormat = 0;

		String geneFilho1 = "";
		String geneFilho2 = "";
		String genePai1 = individuo1.getGenes();
		String genePai2 = individuo2.getGenes();
		Individuo[] filhos = new Individuo[2];

		tamFormat = Central.formatoIndividuo.length();
		for (int i = 0; i < tamFormat; i++) {

			inicTipo = Central.inicioTipo(i);
			tamTipo = Central.tamanhoTipo(i);
			posCross = ((int) (Math.random() * (tamTipo - Central.variacaoInteiroTam
					.length())) + Central.variacaoInteiroTam.length());

			geneFilho1 += genePai2.substring(inicTipo, (posCross + inicTipo));
			geneFilho1 += genePai1.substring((posCross + inicTipo),
					(tamTipo + inicTipo));
			geneFilho2 += genePai1.substring(inicTipo, (posCross + inicTipo));
			geneFilho2 += genePai2.substring((posCross + inicTipo),
					(tamTipo + inicTipo));

		}// fim for

		filhos[0] = new Individuo(geneFilho1);
		filhos[1] = new Individuo(geneFilho2);

		return filhos;
	}

	/** Metodo usado para aplicar mutacao em um individuo. */
	public static String mutacao(String individuo) {

		char[] indiv = individuo.toCharArray();
		int mudou = 0, posMut = (int) (Math.random() * indiv.length);
		int posicao = 0, posicaoFato = 0, tamTipo = 0, inicTipo = 0, aleatorio = 0;

		for (; mudou == 0;) {
			switch (indiv[posMut]) {
			case '+':
				indiv[posMut] = '+';
				mudou = 1;
				break;
			case '-':
				indiv[posMut] = '-';
				mudou = 1;
				break;
			case ((char) 216):
				posMut = (int) (Math.random() * indiv.length);
				break; // escolhe outra posicao
			default: {
				mudou = 1;
				for (int i = 0; i < Central.formatoIndividuo.length(); i++) {
					inicTipo = Central.inicioTipo(i);
					tamTipo = Central.tamanhoTipo(i);
					posicao = tamTipo - Central.variacaoInteiroTam.length();
					aleatorio = (int) (Math.random() * Central.variacaoInteiroTam
							.length());
					posicaoFato = inicTipo + posicao + aleatorio;

					if ((Central.formatoIndividuo.charAt(i) == 'C')
							|| (Central.formatoIndividuo.charAt(i) == 'S')) {
						indiv[posicaoFato] = Diversos.generateChar(
								Central.tipoString).charAt(0);
					} // fim if
					else {

						indiv[posicaoFato] = Diversos.generateChar(
								Central.tipoString).charAt(0);
					}
				}
			}// fim default
			}// fim switch

		}// fim for
		individuo = "";

		for (int j = 0; j < indiv.length; j++) {
			individuo += indiv[j];
		}

		return individuo;
	}

	/**
	 * Metodo retorna o indice do individuo sorteado. Com base na somatoria do
	 * fitness e na ordem dos individuos, simulando o metodo de selecao da
	 * roleta. Retorna qual o individuo possui esta na faixa sorteada.
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public static int indiceIndividuoSorteado(double sorteio)
			throws IOException {
		double superior = 0;
		String saida = null;
		// int tamLinha = nroEspacos( (int) Central.tamanhoPopulacao ) + 30;
		String linha = null;
		String[] quebra = null;
		FileReader fr = new FileReader(Central.arquivoFitness);
		BufferedReader br = new BufferedReader(fr);

		linha = br.readLine();
		for (int i = 0; (i < Central.tamanhoPopulacao); i++) {
			quebra = linha.split(":");
			linha = quebra[1].trim();
			superior += Double.parseDouble(linha);
			if (sorteio <= superior) {
				br.close();
				return i;
			} // fim if
			linha = br.readLine();
		}// fim for

		br.close();
		return -1;
	}

	/**
	 * Metodo usado para calcular o ineditismo da populacao.
	 * 
	 * @throws IOException
	 */
	public static void geraBonusIneditismo() throws IOException {

		Diversos.toFile("log_erro.log", "------geraIneditismo");
		String saida = null, linhaBrPtrCobertura = null;
		String elemCobertura = null;
		String[] ponto2 = null;
		int contador = 0, quantCob = 0;
		double bonificacao = 0;

		FileReader frPtrCobertura = new FileReader(
				Central.arquivoCoberturaElemento);
		BufferedReader brPtrCobertura = new BufferedReader(frPtrCobertura);

		Diversos.limpaArquivo(Central.arquivoBonusIneditismo.getPath());

		linhaBrPtrCobertura = brPtrCobertura.readLine();
		for (contador = 0; contador < Central.quantidadeElemento; contador++) {
			ponto2 = linhaBrPtrCobertura.split(":");
			elemCobertura = ponto2[1].trim();
			bonificacao = 0;
			quantCob = Diversos.numberOf(elemCobertura, 'X');

			if (quantCob != 0)
				bonificacao = 100 * (1 - (quantCob / Central.tamanhoPopulacao));
			saida = contador + ": " + bonificacao;
			Diversos.escreverArquivo(Central.arquivoBonusIneditismo, saida);

			linhaBrPtrCobertura = brPtrCobertura.readLine();
		}// fim for

		brPtrCobertura.close();

		if (Central.geraLog != 0)
			Diversos.toFile("log_erro.log", "------saindo geraIneditismo");
	}

	/**
	 * Metodo usado para obter o indice do individuo a ser mantido
	 * 
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public static int melhorInedAntNaoEm(File arquivoAuxiliar)
			throws NumberFormatException, IOException {
		String linha = null;
		FileReader fr = new FileReader(Central.arquivoIneditismo);
		BufferedReader br = new BufferedReader(fr);
		String[] quebra = null;
		int posMelhor = -1, pos2p = 0;
		double ineditismo = 0, ineditismoMelhor = -1;

		linha = br.readLine();
		for (int cont = 0; cont < Central.tamanhoPopulacao; cont++) {
			pos2p = Diversos.indexOf(linha, ':');
			if (pos2p != -1) {

				quebra = linha.split(":");
				linha = quebra[1].trim();
				ineditismo = Double.parseDouble(linha);

				if ((ineditismo > ineditismoMelhor)
						&& (inPopulacao(individuos[cont].getGenes(),
								arquivoAuxiliar))) {
					posMelhor = cont;
					ineditismoMelhor = ineditismo;
				}
			} // fim if
			linha = br.readLine();
		}// fim for

		br.close();
		return posMelhor;
	}

	/**
	 * Metodo usado para obter o indice do individuo a ser mantido
	 * 
	 * @throws IOException
	 */
	public static int melhorFitAntNaoEm(File arquivoAuxiliar)
			throws IOException {
		String linha = null;
		String[] quebra = null;
		int posMelhor = -1, pos2p = 0;
		double fit = 0, fitMelhor = -1;

		FileReader fr = new FileReader(Central.arquivoFitness);
		BufferedReader br = new BufferedReader(fr);

		linha = br.readLine();
		for (int cont = 0; cont < Central.tamanhoPopulacao; cont++) {
			pos2p = Diversos.indexOf(linha, ':');
			if (pos2p != -1) {
				quebra = linha.split(":");
				linha = quebra[1].trim();
				fit = Double.parseDouble(linha);
				if ((fit > fitMelhor)
						&& (inPopulacao(individuos[cont].getGenes(),
								arquivoAuxiliar))) {
					posMelhor = cont;
					fitMelhor = fit;
				}
			} // fim if
			linha = br.readLine();
		}// fim for
		br.close();
		return posMelhor;
	}

	/**
	 * Metodo usado para calcular a cobertura dos elementos, com base na
	 * cobertura dos individuos.
	 * 
	 * @throws IOException
	 */
	public static void geraCoberturaElementos() throws IOException {
		String linhaArquivo = null;
		String linhaConbertura = "";
		String conteudo = "";
		String[] quebra = null;
		String[][] matriz = new String[Central.quantidadeElemento][(int) Central.tamanhoPopulacao];
		int contLinha = 0;
		FileReader fr = new FileReader(Central.arquivoCoberturaIndividuo);
		BufferedReader br = new BufferedReader(fr);

		linhaArquivo = br.readLine();
		for (contLinha = 0; linhaArquivo != null; contLinha++) {
			quebra = linhaArquivo.split(":");
			linhaConbertura = quebra[1].trim();
			for (int j = 0; j < Central.quantidadeElemento; j++) {
				matriz[j][contLinha] = Character.toString(linhaConbertura
						.charAt(j));
				// System.out.println(matriz[j][contLinha]);
			}

			linhaArquivo = br.readLine();
		}

		br.close();

		for (int i = 0; i < Central.quantidadeElemento; i++) {
			conteudo += (i + 1) + ": ";
			for (int j = 0; j < Central.tamanhoPopulacao; j++) {
				conteudo += matriz[i][j];
			}
			conteudo += "\n";
		}
		Diversos.limpaArquivo(Central.arquivoCoberturaElemento.getPath());
		Diversos.escreverArquivo(Central.arquivoCoberturaElemento, conteudo);
	}

	/**
	 * Metodo usado para gerar um arquivo que reflita os dados de uma populacao
	 * de forma entendivel/decodificada.
	 * 
	 * @throws IOException
	 */
	public static void decodificaPopulacao(File origem, String destino)
			throws IOException {

		String block = "", block_aux = "", gene = "", saida = null;
		String linha = null;
		int tamFormato = Central.formatoIndividuo.length();
		int inicBlock;
		int fimBlock;
		String[] quebra = null;

		FileReader fr = new FileReader(origem);
		BufferedReader br = new BufferedReader(fr);

		linha = br.readLine();
		while (linha != null) {
			quebra = linha.split(":");
			gene = quebra[1].trim();
			for (int i = 0; i < tamFormato; i++) {
				inicBlock = Central.inicioTipo(i);
				fimBlock = Central.tamanhoTipo(i);
				block = gene.substring(inicBlock, (inicBlock + fimBlock));
				switch (Central.formatoIndividuo.charAt(i)) {

				case 'I':
					block_aux += String.format("%d ",
							Individuo.decode_block_int(block));
					break;
				}// fim switch

			}// fim while
			Diversos.escreverArquivo(destino, block_aux);
			block_aux = "";
			linha = br.readLine();
		} // fim for

		br.close();
		if (Central.geraLog != 0) {
			Diversos.toFile("log_erro.log", "---decodificaPopulacao");
		}
	}

	/**
	 * Metodo usado para armazenar desempenho de um individuo, assim não precisa
	 * executa-lo novamente para obter sua cobertura.
	 * 
	 * @throws IOException
	 */
	public static void toRepositorio(int nro, String desempenho)
			throws IOException {
		String saida = "";

		saida += String.format("%s : %s", individuos[nro].getGenes(),
				desempenho);
		Diversos.escreverArquivo(Central.arquivoRepositorio, saida);

	}

	/** Metodo usado para sobrepor duas linhas de coberturas. */
	public static String sobrepoe(String cobAnterior, String cobAtual) {
		int i = 0;
		String novaCobertura = "";

		if (cobAnterior == null) {
			novaCobertura = cobAtual;
		} else {

			char charsNovaCobertura[] = cobAnterior.toCharArray();
			char charsCobAtual[] = cobAtual.toCharArray();

			for (i = 0; i < charsCobAtual.length; i++) {
				if (charsNovaCobertura[i] == '-') {
					charsNovaCobertura[i] = charsCobAtual[i];
				}

				novaCobertura += charsNovaCobertura[i];
			}
		}
		// fim els
		return novaCobertura;
	}// fim sobrepoe

	/**
	 * Metodo usado para incluir um novo individuo no arquivo de populacao. Caso
	 * o individuo ja exista na populacao a insercao nao sera realizada.
	 * 
	 * @throws IOException
	 */
	public static boolean toPopulacao(int nro, String strIndividuo, File arquivo)
			throws IOException {

		String saida = "";

		if (inPopulacao(strIndividuo, arquivo)) {
			return false;
		}

		saida += String.format("%d : %s", nro, strIndividuo);
		Diversos.escreverArquivo(arquivo, saida);

		return true;
	}

	/**
	 * Metodo usado para verificar se um individuo esta na populacao
	 * representada pelo arquivo passado por argumento.
	 * 
	 * @throws IOException
	 */
	public static boolean inPopulacao(String strIndividuo, File arquivo)
			throws IOException {

		if (strIndividuo.length() <= 0)
			return false;

		if (arquivo == null) {
			return false;
		}

		String linhaArquivo = null;
		String[] quebraLinhaArq = null;

		FileReader fr = new FileReader(arquivo);
		BufferedReader br = new BufferedReader(fr);

		linhaArquivo = br.readLine();
		while (linhaArquivo != null) {
			quebraLinhaArq = linhaArquivo.split(":");
			if (quebraLinhaArq[1].trim().equals(strIndividuo)) {
				br.close();
				return true;
			}

			linhaArquivo = br.readLine();
		}

		br.close();
		return false;
	}

	/**
	 * Este metodo retorna o valor do bonus para os individuos que cobriram o
	 * elemento informado por pos.
	 * 
	 * @throws IOException
	 */
	public static double getBonusElemento(int pos) throws IOException {

		int i = 0;
		String gene = null, bonificacaoStr = null, linha = null;
		String[] quebra = null;
		double bonificacao = 0;

		FileReader fr = new FileReader(Central.arquivoBonusIneditismo);
		BufferedReader br = new BufferedReader(fr);

		linha = br.readLine();
		for (i = 0; i <= pos; i++) {
			gene = linha;
			linha = br.readLine();
		}// fim for
		br.close();

		quebra = gene.split(":");
		bonificacaoStr = quebra[1].trim();
		bonificacao = Double.parseDouble(bonificacaoStr.trim());

		return bonificacao;
	}

	/**
	 * Gera o arquivo com a bonificacao final por ineditismo.
	 * 
	 * @throws IOException
	 */
	public static void geraIneditismoPopulacao() throws IOException {

		if (Central.geraLog != 0) {
			Diversos.toFile("log_erro.log", "---------geraIneditismoPopulacao");
		}

		String linhaCobInd = null, saida = null;
		String[] quebra = null;
		int cont = 0;
		double ineditismo = 0;

		Diversos.limpaArquivo(Central.arquivoIneditismo.getPath());
		FileReader fr = new FileReader(Central.arquivoCoberturaIndividuo);
		BufferedReader brPtrCobInd = new BufferedReader(fr);

		linhaCobInd = brPtrCobInd.readLine();
		for (cont = 0; cont < (int) (Central.tamanhoPopulacao); cont++) {
			quebra = linhaCobInd.split(":");
			linhaCobInd = quebra[1].trim();
			ineditismo = 0;

			for (int i = 0; i < Central.quantidadeElemento; i++) {
				if (linhaCobInd.charAt(i) == 'X')
					ineditismo += getBonusElemento(i);
			}// fim for

			saida = cont + ": " + ineditismo;
			Diversos.escreverArquivo(Central.arquivoIneditismo.getPath(), saida);
			linhaCobInd = brPtrCobInd.readLine();
		}// fim for
		brPtrCobInd.close();

		if (Central.geraLog != 0) {
			Diversos.toFile("log_erro.log",
					"---------saindo geraIneditismoPopulacao");
		}

	}

	public static void ordenaPopulacao() {
		boolean trocou = true;
		while (trocou) {
			trocou = false;
			for (int i = 0; i < individuos.length - 1; i++) {
				if (individuos[i].getAptidao() < individuos[i + 1].getAptidao()) {
					Individuo temp = individuos[i];
					individuos[i] = individuos[i + 1];
					individuos[i + 1] = temp;
					trocou = true;
				}
			}
		}
	}
}
