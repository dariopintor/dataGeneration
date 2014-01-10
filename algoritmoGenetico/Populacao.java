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
	private int tamPopulacao;

	// cria uma população com indivíduos sem valor, será composto posteriormente
	public Populacao(int tamPop) {
		tamPopulacao = tamPop;
		individuos = new Individuo[tamPop];
		for (int i = 0; i < individuos.length; i++) {
			individuos[i] = null;
		}
	}
	/** coloca um indivíduo na próxima posição disponível da população */
	public void setIndividuo(Individuo individuo) {
		for (int i = 0; i < individuos.length; i++) {
			if (individuos[i] == null) {
				individuos[i] = individuo;
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
		
		System.out.println("Fim da Geração da População inicial Aleatória \n\n");
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
			 Central.setTamanhoPopulacao(contador);
			 
			 System.out.println("Fim População Inicial Arquivo \n\n");
	//	Central.recalculaPorcEvolucao();
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

		geraCoberturaIndividuoValiMPI();


		if (Central.geraLog != 0) {
			Diversos.toFile("log_erro.log", "---saindo avaliaPopulacao");
		}

		geraFitness();
	}// fim avaliaPopulacao()

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
		
		System.out.println("Avaliando Individuos da População " + Central.geracaoAtual);
		String dado = "";
		String[] quebra = null;
		Diversos.limpaArquivo(Central.arquivoCoberturaIndividuo.getPath());

		for (int contador = 0; contador < Central.tamanhoPopulacao; contador++) {
			
			 System.out.println(individuos[contador].getGenes());
			quebra = individuos[contador].getGenes().split("\\+");

			
			for (String b : quebra) {
				dado += b + " ";
			}
			Ferramenta.usaValiMPI(dado, contador + 1);
			dado = "";
		} // fim for contador
		
		System.out.println("Fim Avaliação Individuo da População " + Central.geracaoAtual);

		geraCoberturaElementos();
	} // fim geraCoberturaIndividuoValiMPI()

	public static void evoluiPopulacao() throws IOException {
		System.out.printf("\n\n###########################################");
		System.out.println("\nEvoluindo População "+Central.geracaoAtual);
		
		if (Central.quantidadeFitness > 0){
			individuos = evolucaoPorFitness(Central.tamanhoPopulacao);
			String saida = "";
			Diversos.limpaArquivo(Central.arquivoPopulacao.getPath());
			//escreve a populacao em arquivo
			for ( int contador = 0; contador < individuos.length; contador ++){
				saida = String.format("%d : %s", contador + 1,
						individuos[contador].getGenes());
				Diversos.escreverArquivo(Central.arquivoPopulacao, saida);
			}
				
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
		Diversos.copyFile(Central.arquivoPopulacaoTemporario.getPath(),
				Central.arquivoPopulacao);

		// move de "popManejo.pop" para Central.arquivoPopulacaoTemporario
		Diversos.copyFile("popManejo.pop", Central.arquivoPopulacaoTemporario);

		if (Central.geraLog != 0)
			Diversos.toFile("log_erro.log", "---saindo EvoluiPopulacao");
		
		System.out.println("Fim evolução da População " +Central.geracaoAtual);

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
		double fitness = 0, somatoriaFitness = 0, cobertura = 0;

		if (Central.arquivoCoberturaIndividuo == null) {
			saida = String.format(
					"nao abriu o arquivo de cobertura/individuo : %s",
					Central.arquivoCoberturaIndividuo);
			Diversos.erro(saida, 1);
		}

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
			saida = i + 1 + ": " + fitness;
		//	System.out.println(saida);
			Diversos.escreverArquivo(Central.arquivoFitness, saida);

			linha = br.readLine();
			somatoriaFitness += fitness;
		}// fim for
			// rastro("PONTO1.7");*/

		Central.setSomatoriaFitness(somatoriaFitness);
		Diversos.toFile(Central.arquivoObsCobertura.getPath(), linhaCobertura);
		Diversos.toFile("log_erro.log", "------saindo geraFitness");
	}

	/**
	 * Metodo usado para evoluir a populacao com base no fitness dos individuos.
	 * 
	 * @throws IOException
	 */
	public static Individuo [] evolucaoPorFitness(double tamPopulacao) throws IOException {
		Individuo[] novosIndividuos = new Individuo[(int) tamPopulacao];
		Diversos.toFile("log_erro.log", "------evolucaoFitness");
		int cross = 0, mut = 0;
		double sorteio = 0;
		int contador = 0;
		Individuo pai1;
		Individuo pai2;
		String filho1 = "";
		String filho2="";
		String [] quebra;
		String saida = null;
		
		for (contador = 0; contador < Central.tamanhoPopulacao; contador ++) {
			sorteio = Central.geraSorteio(Central.somatoriaFitness);
			pai1 = individuos[indiceIndividuoSorteado(sorteio)];
			sorteio = Central.geraSorteio(Central.somatoriaFitness);
			pai2 = individuos[indiceIndividuoSorteado(sorteio)];
			
			// aplica a taxa de crossover
			cross = Mersenne.genrand() % 100;
			if (cross <= Central.taxaCrossover * 100) {
				quebra = crossover(pai1.getGenes(), pai2.getGenes()).trim().split("\\&");
				filho1=quebra[0]; 
				filho2=quebra[1];
			}

			// aplica a taxa de mutacao
			mut = (int) Math.random() * 100;
			if (mut <= Central.taxaMutacao * 100) {
				filho1 = mutacao(filho1);
				saida = String.format(" op MUTACAO 1  : %s", filho1);
				Diversos.toFile("evolucao.fil", saida);
				
				filho2= mutacao(pai2.getGenes());
				saida = String.format("    op MUTACAO 2  : %s",	filho2);
				Diversos.toFile("evolucao.fil", saida);
			}
			novosIndividuos[contador] = new Individuo(filho1);					
		} // fim for fitness
		if (Central.geraLog != 0)
			Diversos.toFile("log_erro.log", "------saindo evolucaoFitness");
		return novosIndividuos;

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

		if (br == null) {
			saida = String.format(
					"nao abriu o arquivo de Variacao de fitness : %s",
					Central.arquivoVariacaoFitness);
			Diversos.erro(saida, 1);
		}

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

	/** Metodo usado para aplicar mutacao em um individuo. */
	public static String mutacao(String indiv) {
		int i = 0, mudou = 0, posMut = (int) (Math.random() * indiv.length());
		for (; mudou == 0;) {
			switch (indiv.charAt(posMut)) {
			case '+':
				indiv = indiv.replace(indiv.charAt(posMut), '+');
				mudou = 1;
				break;
			case '-':
				indiv = indiv.replace(indiv.charAt(posMut), '-');
				mudou = 1;
				break;
			// case '#': posMut = rand() % strlen( indiv ); break; //escolhe
			// outra posicao
			case ((char) 216):
				posMut = (int) (Math.random() * indiv.length());
				break; // escolhe outra posicao
			default: {
				int pos = 0;
				mudou = 1;
				for (i = 1; posMut >= Central.inicioTipo(i); i++)
					;
				if ((Central.formatoIndividuo.charAt(i - 1) == 'C')
						|| (Central.formatoIndividuo.charAt(i - 1) == 'S')) {
					indiv = indiv.replace(indiv.charAt(posMut), Diversos
							.generateChar(Central.tipoString).charAt(0));
				} // fim if
				else {
					indiv = indiv.replace(indiv.charAt(posMut), Diversos
							.generateChar("n").charAt(0));
				} // fim else
			}// fim default
			}// fim switch

		}// fim for
		return indiv;
	}

	public static String crossover(String individuo1, String individuo2) {
        Random r = new Random();

        //sorteia o ponto de corte
        int pontoCorte1 = r.nextInt((individuo1.length()/2) -2) + 1;
        int pontoCorte2 = r.nextInt((individuo1.length()/2) -2) + individuo1.length()/2;

        String filho1 = "";
        String filho2 = "";

        //pega os genes dos pais
        String genePai1 = individuo1;
        String genePai2 = individuo2;

        String geneFilho1;
        String geneFilho2;

        //realiza o corte, 
        geneFilho1 = genePai1.substring(0, pontoCorte1);
        geneFilho1 += genePai2.substring(pontoCorte1, pontoCorte2);
        geneFilho1 += genePai1.substring(pontoCorte2, genePai1.length());
        
        geneFilho2 = genePai2.substring(0, pontoCorte1);
        geneFilho2 += genePai1.substring(pontoCorte1, pontoCorte2);
        geneFilho2 += genePai2.substring(pontoCorte2, genePai2.length());

        //cria o novo indivíduo com os genes dos pais
        filho1 = geneFilho1;
        filho2= geneFilho2;

        return filho1  +"&"+ filho2;
    }
	/**
	 * Metodo usado para obter o indice do individuo a ser mantido
	 * 
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public double melhorInedAntNaoEm(File arquivoAuxiliar)
			throws NumberFormatException, IOException {
		String saida = null;
		String linha = null;
		FileReader fr = new FileReader(Central.arquivoIneditismo);
		BufferedReader br = new BufferedReader(fr);

		int pos2p = 0;
		double posMelhor = -1, pos = 0, ineditismo = 0, ineditismoMelhor = -1;
		int tamLinha = 100;

		linha = br.readLine();
		for (int cont = 0; cont < Central.tamanhoPopulacao; cont++) {
			pos2p = Diversos.indexOf(linha, ':');
			if (pos2p != -1) {
				linha = linha + pos2p + 1;
				linha = linha.trim();
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
	public double melhorFitAntNaoEm(File arquivoAuxiliar) throws IOException {
		String saida = null;
		String linha = null;
		int pos2p = 0;
		double posMelhor = -1, pos = 0, fit = 0, fitMelhor = -1;

		FileReader fr = new FileReader(Central.arquivoFitness);
		BufferedReader br = new BufferedReader(fr);

		linha = br.readLine();
		for (int cont = 0; cont < Central.tamanhoPopulacao; cont++) {
			pos2p = Diversos.indexOf(linha, ':');
			if (pos2p != -1) {
				linha = linha + pos2p + 1;
				linha = linha.trim();
				fit = Double.parseDouble(linha);
				if ((fit > fitMelhor)
						&& (!inPopulacao(individuos[cont].getGenes(),
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
		String linhaPtrPopulacao = null;
		int tamFormato = Central.formatoIndividuo.length();
		int inicBlock, pos;

		int geneTam = 0, tamBloco = 30;
		geneTam = (int) (Central.tamanhoIndividuo + 20);

		if (Central.tamanhoMaximoString > tamBloco) {
			tamBloco = (int) Central.tamanhoMaximoString;
		}

		FileReader frPtrPopulacao = new FileReader(origem);
		BufferedReader brPtrPopulacao = new BufferedReader(frPtrPopulacao);

		if (brPtrPopulacao == null) {
			saida = String.format(
					"nao abriu o arquivo de populacao corretamente : %s",
					origem);
			Diversos.erro(saida, 1);
		}

		linhaPtrPopulacao = brPtrPopulacao.readLine();
		for (int contador = 1; brPtrPopulacao != null; contador++) {
			gene = linhaPtrPopulacao.trim();
			if (gene != "") {
				gene = (gene + Diversos.indexOf(gene, ':') + 1).trim(); // errado
				for (int i = 0; i < tamFormato; i++) {
					inicBlock = Central.inicioTipo(i);
					block = gene + inicBlock;
					switch (Central.formatoIndividuo.charAt(i)) {
					case 'I':
						block_aux = String.format("%d",
								Individuo.decode_block_int(block));
						break;
					}// fim switch
					Diversos.escreverArquivo(destino, block_aux);
					if (i != tamFormato - 1) {
						Diversos.escreverArquivo(destino, ", ");
					}
				}// fim for
			} // fim if gene == ""
			linhaPtrPopulacao = brPtrPopulacao.readLine();
		} // fim for

		brPtrPopulacao.close();
		if (Central.geraLog != 0) {
			Diversos.toFile("log_erro.log", "---decodificaPopulacao");
		}
	}

	/** Metodo usado para sobrepor duas linhas de coberturas. */
	public static String sobrepoe(String cobAnterior, String cobAtual) {
		int i = 0;
		String novaCobertura = "";

		if (cobAnterior == null) {
			novaCobertura = cobAtual;
		}
		else {
			
			char charsNovaCobertura[] = cobAnterior.toCharArray(); 
			char charsCobAtual[] = cobAtual.toCharArray(); 
			
			for (i = 0; i < charsCobAtual.length; i++) {
				if (charsNovaCobertura[i] == '-') {
					charsNovaCobertura[i] = charsCobAtual[i];
				}
				
				novaCobertura+=charsNovaCobertura[i];
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

		if ((strIndividuo.equals("")) || (strIndividuo.equals(216)))
			System.out
					.println(" to erro, tentando salvar individuo vazio ou 216,216");

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
}
