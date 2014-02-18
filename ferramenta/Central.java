package ferramenta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Formatter;
import java.util.TimeZone;
import java.util.zip.Deflater;
import java.util.zip.ZipOutputStream;

import javax.print.attribute.IntegerSyntax;

import main.Diversos;
import main.Mersenne;

public class Central {
	/***************************************************************************
	 * central.cpp - description ------------------- begin : Dom Jul 6 2003
	 * copyright : (C) 2003 by Luciano Petinati Ferreira email : it and/or
	 * modify * it under the terms of the GNU General Public License as
	 * published by * the Free Software Foundation; either version 2 of the
	 * License, or * (at your option) any later version. * *
	 ***************************************************************************/

	/** Variavel de controle para pausar a execucao a cada geracao. */
	public static int pausaGeracao;
	public static int geraLog;
	/**
	 * Variavel de controle para indicar quantos argumentos o programa em teste
	 * necessita
	 */
	public static int numeroArgumentos;
	/**
	 * Variavel de controle para indicar o tamanho manimo para o tipo string (*
	 * String ).
	 */
	public static double tamanhoMinimoString;
	/**
	 * Variavel de controle para indicar o tamanho maximo para o tipo string (*
	 * String ).
	 */
	public static double tamanhoMaximoString;
	/**
	 * Variavel de controle para indicar o tamanho manimo para o tipo inteiro.
	 */
	public static int tamanhoMinimoInteiro;
	/**
	 * Variavel de controle para indicar o tamanho maximo para o tipo inteiro.
	 */
	public static int tamanhoMaximoInteiro;
	/**
	 * Variavel de controle para indicar o tamanho maximo que um argumento
	 * podeassumir para o programa em teste, desconsiderando seu tipo.
	 */
	public static double tamanhoMaximoArgumento;
	/** Variavel de controle para indicar a variacao do tipo inteiro. */
	public static int variacaoInteiro;
	/** Variavel de controle para indicar quantidade de casas decimais. */
	public static String variacaoInteiroTam;
	/**
	 * Variavel de controle para indicar a quantidade de elementos requeridos
	 * para o programa em teste.
	 */
	public static int quantidadeElemento;
	/**
	 * Variavel de controle para conter a cobertura de elementos requeridos da
	 * populacao atual.
	 */
	public static double coberturaAtual;
	/**
	 * Variavel de controle para conter a somataria do fitness de todos os
	 * indivaduos para auxiliar a selecao por roleta.
	 */
	public static double somatoriaFitness;
	/**
	 * Variavel de controle para indicar o critario de teste a ser usado na
	 * execucao do framework.
	 */
	public static String criterioTeste;
	/**
	 * Variavel de controle para indicar a funcao a ser testada do programa em
	 * este.
	 */
	public static String funcaoATestar;
	/**
	 * Variavel de controle para indicar o critario de teste da valiMPI a ser
	 * usado na execucao do framework.
	 */
	public static String criterioTesteValiMPI;
	/**  */

	/**
	 * Variavel de controle para indicar o diretario pool, usado para entrada de
	 * dados na execucao do programa ja instrumentado.
	 */
	public static String diretorioPool;
	/**
	 * Variavel de controle para indicar o namero maximo de geracaes a serem
	 * geradas caso a cobertura desejada nao seja alcanaada.
	 */
	public static double maximoGeracoes;
	/** Variavel de controle para indicar a geracao atual. */
	public static double geracaoAtual;
	/** Variavel de controle para indicar o andice da melhor geracao. */
	public static double indiceMelhorGeracao;
	/**
	 * Variavel de controle para indicar o tamanho do indivaduo usado no AG.
	 */
	public static double tamanhoIndividuo;
	/**
	 * Variavel de controle para indicar o tamanho da populacao usada no AG.
	 */
	public static double tamanhoPopulacao;
	/**
	 * Variavel de controle para indicar a cobertura desejada para o critario
	 * selecionado.
	 */
	public static double coberturaCriterio; // novo nome coberturaDesejada
	/**
	 * Variavel de controle para indicar a melhor cobertura possavel atravas de
	 * todo o processo. Usa informacaes da lista Tabu.
	 */
	public static double melhorCobertura;
	/** Variavel de controle para indicar a taxa de mutacao usada no AG. */
	public static double taxaMutacao;
	/** Variavel de controle para indicar a taxa de Crossover usada no AG */
	public static double taxaCrossover;
	/**
	 * Variavel de controle para indicar a quantidade de indivaduos da nova
	 * geracao que devem ser gerados com base no elitismo (fitness).
	 */
	public static double quantidadeElitismo;
	/**
	 * Variavel de controle para indicar a quantidade de indivaduos da nova
	 * geracao que devem ser gerados com base no score de ineditismo dos
	 * indivaduos.
	 */
	public static double quantidadeIneditismo;
	/**
	 * Variavel de controle para indicar a quantidade de indivaduos da nova
	 * geracao que devem ser gerados com base na selecao e operadores genaticos.
	 */
	public static double quantidadeFitness; // novo nome quantidadeSelecao
	/**
	 * * Variavel de controle para indicar o formato da entrada do programa,
	 * consequentemente o formato do indivaduo do AG.
	 */
	public static String formatoIndividuo;
	/**
	 * * Variavel de controle para conter informacaes sobre perda de cobertura
	 * de elementos requeridos.
	 */
	public static String linhaPerda;
	/***
	 * Variavel de controle para conter a cobertura atual de elementos
	 * requeridos ( 1: para coberto, 0: para nao coberto ).
	 */
	public static String linhaCoberturaAtual;
	/***
	 * Variavel de controle para conter informacaes de cobertura de elementos
	 * requeridos da geracao anterior.
	 */
	public static String linhaCoberturaAnterior;
	/***
	 * Variavel de controle para conter informacaes a cobertura global de
	 * elementos requeridos, independente de geracao.
	 */
	public static String coberturaGlobal; // novo nome linhaCoberturaGlobal
	/** Variavel de controle para conter o nome do arquivo da lista Tabu. */
	public static File arquivoTabu;
	/**
	 * Variavel de controle para conter o nome do arquivo temporario da lista
	 * Tabu.
	 */
	public static File arquivoTabuAux;
	/**
	 * Variavel de controle para conter o nome do arquivo fonte do programa em
	 * teste.
	 */
	public static String arquivoFonte;
	/**
	 * Variavel de controle que identifica o tipo de string que o framework
	 * suporta.
	 */
	public static String tipoString;
	/**
	 * Variavel de controle que contera o comando a ser executado por chamada
	 * system com variavel de ambiente PATH corretamente configurada.
	 */
	public static String comandPath;
	public static String enviroment;

	/** arquivos gerados durante a geração */
	public static File arquivoPopulacaoInicial;
	public static File arquivoPopulacao;
	public static File arquivoMelhorPopulacao;
	public static File arquivoPopulacaoAnterior;
	public static File arquivoFitness;
	public static File arquivoVariacaoFitness;
	public static File arquivoCoberturaIndividuo;
	public static File arquivoVali_EvalOut;
	public static File arquivoCoberturaElemento;
	public static File arquivoAvalCobIndividuos;
	public static File arquivoSemelhancaIndividuos;
	public static File arquivoObsCobertura;
	public static File arquivoRepositorio;
	public static File diretorio;

	public long tamEnviroment;
	/** Variavel de controle para manter o tamanho de enviroment. */
	public long tamComandPath;
	/** Variavel de controle para manter o tamanho de comandPath. */
	public static File arquivoPopulacaoTemporario2;
	/**
	 * Atributo usado para conter o nome para um arquivo temporario para a
	 * populacao.
	 */
	public static int ativaTabu = 0;
	/** Atributo usado para controlar a ativacao do recurso de lista Tabu. */
	public static File arquivoBonusIneditismo;
	public static File arquivoIneditismo;
	/**
	 * * Atributo usado para guardar quantas gercaes serao usadas/configuradas
	 * para verificar o repositario de execucao antes de executar um determinado
	 * caso de teste. Isto pode aumentar o desempenho do sistema.
	 */
	public static double geracoesComRepositorio;
	/** Atributo com o tempo em segundos da execucao do sistema. */
	public static long inicioExecucao;
	/** Atributo com o tempo em segundo do fim de execucao do sistema */
	public static long fimExecucao;
	/** Atributo com o tempo em segundo do fim da primeira execucao do sistema */
	public static long fimPrimeiraExecucao;
	/** Atributo para manter a cobertura inicial do processo. */
	public static double coberturaInicial;
	/** Atributo que diz quantos processos o programa usa. */
	public static String nProcess;
	/** Atributo que diz qual processo sera qual namero */
	public static String funcoes;
	public static String ccargs;

	static Diversos Diversos; // declaracao do objeto diversos

	static String pegaDiretorio;
	public static File arquivoResumo;

	public Central() {

	}

	/**
	 * Metodo que carrega na classe central informacaes sobre a execucao da
	 * ferramenta.
	 * 
	 * @throws IOException
	 */
	public static void interpretaArquivoConfiguracao() throws IOException {

		String nomeArq = "arqconfig.txt"; // Nome do arquivo,
		// pode ser absoluto, ou pastas /dir/teste.txt
		String linha = "", parametro = "", valorIntermediario = "", valor = "";
		String quebra[];
		String quebra2[];

		FileReader arq = new FileReader(nomeArq);
		BufferedReader lerArq = new BufferedReader(arq);

		setTipoString("alfabetico");

		linha = lerArq.readLine(); // la a primeira linha

		// a variavel "linha" recebe o valor "null" quando o processo
		// de repeticao atingir o final do arquivo texto
		while (linha != null) {

			if (linha.charAt(0) == '#') {
				// System.out.println("passei aqui");
				quebra = linha.split("=");
				parametro = quebra[0].trim(); // pega parametro
				valorIntermediario = quebra[1].trim(); // pega valor
				quebra2 = valorIntermediario.split(";");
				valor = quebra2[0].trim();

				if (parametro.equals("#NumeroArgumentos"))
					setNumeroArgumentos(Integer.parseInt(valor));

				else if (parametro.equals("#Log"))
					setGeraLog(1);

				else if (parametro.equals("#PausaGeracao"))
					setPausaGeracao(1);

				else if (parametro.equals("#Elitismo"))
					setQuantidadeElitismo(Integer.parseInt(valor));

				else if (parametro.equals("#Ineditismo"))
					setQuantidadeIneditismo(Integer.parseInt(valor));

				else if (parametro.equals("#Fitness"))
					setQuantidadeFitness(Integer.parseInt(valor));

				else if ((parametro.equals("#ArquivoFonte"))
						|| (parametro.equals("#SourceFile")))
					setArquivoFonte(valor);

				else if ((parametro.equals("#FormatoEntrada"))
						|| (parametro.equals("#InputFormat")))
					setFormatoIndividuo(valor);

				else if ((parametro.equals("#VariacaoString"))
						|| (parametro.equals("#StringRange")))
					setTamanhoString(valor);

				else if ((parametro.equals("#StringType"))
						|| (parametro.equals("#TipoString")))
					setTipoString(valor);

				else if ((parametro.equals("#ArquivoPopulacao"))
						|| (parametro.equals("#PopulationFile")))
					setArquivoPopulacaoInicial(valor);

				else if ((parametro.equals("#TamanhoPopulacao"))
						|| (parametro.equals("#PopulationSize")))
					setTamanhoPopulacao(Integer.parseInt(valor));

				else if ((parametro.equals("#NumeroGeracoes"))
						|| (parametro.equals("#GenerationNumber")))
					setMaximoGeracoes(Integer.parseInt(valor));

				else if ((parametro.equals("#CoberturaCriterio"))
						|| (parametro.equals("#CriterioCoverage")))
					setCoberturaCriterio(Integer.parseInt(valor));

				else if ((parametro.equals("#Criterio"))
						|| (parametro.equals("#Criterium"))) {
					setCriterioTeste(valor);
				}

				else if ((parametro.equals("#TaxaMutacao"))
						|| (parametro.equals("#MutationRate")))
					setTaxaMutacao(Double.parseDouble(valor));

				else if ((parametro.equals("#TaxaCrossover"))
						|| (parametro.equals("#CrossOverRate")))
					setTaxaCrossover(Double.parseDouble(valor));

				else if ((parametro.equals("#VariacaoInt"))
						|| (parametro.equals("#IntRange")))
					setVariacaoInteiro(valor);

				else if (parametro.equals("#NomeFuncao")) {
					setFuncaoATestar(valor);
					setDiretorio(valor);
					setArquivoPopulacao(valor);
					setArquivoPopulacaoTemporario(valor);
					setArquivoFitness(valor);
					setArquivoIneditismo(valor);
					setArquivoBonusIneditismo(valor);
				}// fim if-then

				else if ((parametro.equals("#AtivaTabu"))
						&& ((valor.equals("sim")) || (valor.equals("Sim")) || ((valor
								.equals("1"))))) {
					setAtivaTabu(1);
					setArquivoTabu("tabu");
					setArquivoTabuAux("tabuAux");

				}

				else if (parametro.equals("#GeracoesComRepositorio"))
					setGeracoesComRepositorio(Integer.parseInt(valor));

				else if (parametro.equals("#NumeroProcessos"))
					setNProcess(valor);

				else if (parametro.equals("#Funcoes"))
					setFuncoes(valor);

				else if (parametro.equals("#CCArgs"))
					setCCArgs(valor);

				linha = lerArq.readLine();
			} else {
				System.out.printf("\n IGNORANDO: %s", linha);
				linha = lerArq.readLine();
			}
		}
		arq.close();
		// quebra de linha
		System.out.println();
		setArquivoCoberturaElemento("cobXElemento");
		setArquivoCoberturaIndividuo("cobXIndividuo");
		setArquivoRepositorio("repositorio");
		setArquivoObsCobertura("relCobertura");
		setTamanhoIndividuo();
		setArquivoMelhorPopulacao("melhorPop");
		// setQuantidadeElemento(Ferramenta.obtemElementosRequeridos());
		if (tamanhoMaximoString > 15)
			tamanhoMaximoArgumento = tamanhoMaximoString;

	}

	/**
	 * Metodo usado para gerar arquivo tar com informacaes completas para cada
	 * geracao.
	 * 
	 * @throws IOException
	 */
	public static void backup() throws IOException {

		File arquivoBackup;
		arquivoBackup = new File(funcaoATestar + "_GER_" + (geracaoAtual));

		arquivoBackup.mkdir();
		Diversos.copyDirectory(Central.diretorio, arquivoBackup);

	}

	/**
	 * Metodo usado para gerar informacaes do progresso do framework ( geracao,
	 * cobertura ).
	 * 
	 * @throws IOException
	 */
	public static void status() throws IOException {

		String saida = "";
		System.out.printf("GERACAO: <%.2f>   COBERTURA: <%.2f> \n",
				geracaoAtual, coberturaAtual);

		saida += String.format("GERACAO: <%.2f>   COBERTURA: <%.2f> \n",
				geracaoAtual, coberturaAtual); // escreve no arquivo

		saida += "\n###########################################";
		System.out.printf("\n###########################################");
		Diversos.escreverArquivo("resumo.tst", saida);

	}

	/**
	 * Metodo usado para apresentar o resultado da execucao do framework.
	 * 
	 * @throws IOException
	 */
	public static void resultado() throws IOException {
		String tempoPrimeiraExec = "", tempoExecucaco = "", resultado = "";
		tempoPrimeiraExec = Diversos.pegarTempo(fimPrimeiraExecucao
				- inicioExecucao);
		tempoExecucaco = Diversos.pegarTempo(fimExecucao - inicioExecucao);

		resultado = String.format("\n\n\nResultado \nCobertura Inicial: " + coberturaInicial
				+ "\nTempo Avaliacao Inicial: " + tempoPrimeiraExec
				+ "\nMelhor Cobertura: " + melhorCobertura
				+ "\nMelhor Geração: " + indiceMelhorGeracao
				+ "\nCobertura Possivel: " + 
				(double) (Diversos.numberOf(coberturaGlobal, 'X') *  100 / quantidadeElemento)
				+ "\nTempo de Execução : " + tempoExecucaco
				+ "\nQuantidade de Gerações: " + geracaoAtual);

		System.out.printf(resultado);

		Diversos.escreverArquivo("resumo.tst", resultado);

	}

	/**
	 * Metodo usado para atribuir valor a variavel de controle que identifica o
	 * tipo de string que o framework suporta.
	 */
	public static void setTipoString(String valor) {
		tipoString = valor;
	}

	/**
	 * Metodo usado para atribuir valor a variavel de controle que possui o
	 * namero de entradas por argumentos necessario para execucao do programa em
	 * teste.
	 */
	public static void setNumeroArgumentos(int valor) {
		numeroArgumentos = valor;
	}

	/***
	 * Metodo usado para atribuir valor a variavel de controle responsavel pela
	 * geracao de log de execucao do framework.
	 * 
	 * @throws IOException
	 */
	public static void setGeraLog(int valor) throws IOException {
		geraLog = valor;
		File file = new File("log_erro.log");
		file.createNewFile();
	}

	/**
	 * Metodo usado para atribuir valor a variavel de controle responsavel por
	 * pausa ao fim de cada geracao.
	 */
	public static void setPausaGeracao(int valor) {
		pausaGeracao = valor;

	}

	/**
	 * Metodo usado para atribuir valor a variavel responsavel pela quantidade
	 * de indivaduos gerados por elitismo.
	 */
	public static void setQuantidadeElitismo(double valor) {
		quantidadeElitismo = valor;

	}

	/** No descriptions */
	public static void setQuantidadeIneditismo(double valor) {
		quantidadeIneditismo = valor;
	}

	/** No descriptions */
	public static void setQuantidadeFitness(double valor) {
		quantidadeFitness = valor;
	}

	/** No descriptions */
	public static void setDiretorio(String valor) {

		diretorio = new File(valor);
		diretorio.mkdir();
		pegaDiretorio = diretorio.getPath() + "/"; // pega o diretario e adicona

	}

	/** No descriptions */
	public static void setArquivoPopulacaoInicial(String valor)
			throws IOException {
		arquivoPopulacaoInicial = new File(valor);
		arquivoPopulacaoInicial.createNewFile();
	}

	/***
	 * No descriptions *
	 * 
	 * @throws IOException
	 */
	public static void setArquivoPopulacao(String valor) throws IOException {
		arquivoPopulacao = new File((pegaDiretorio + valor + ".pop"));
		arquivoPopulacao.createNewFile();

	}

	/**
	 * No descriptions
	 * 
	 * @throws IOException
	 */
	public static void setArquivoPopulacaoTemporario(String valor)
			throws IOException {
		arquivoPopulacaoAnterior = new File(pegaDiretorio + valor + ".tmp");
		arquivoPopulacaoAnterior.createNewFile();

	}

	/**
	 * No descriptions
	 * 
	 * @throws IOException
	 */
	public static void setArquivoFitness(String valor) throws IOException {
		arquivoFitness = new File(pegaDiretorio + valor + ".fit");
		arquivoFitness.createNewFile();
	}

	/**
	 * No descriptions
	 * 
	 * @throws IOException
	 */
	public static void setArquivoIneditismo(String valor) throws IOException {
		int tam = valor.length();

		arquivoIneditismo = new File(pegaDiretorio + valor + ".ine");
		arquivoIneditismo.createNewFile();

	}

	/***
	 * No descriptions*
	 * 
	 * @throws IOException
	 */
	public static void setArquivoBonusIneditismo(String valor)
			throws IOException {

		arquivoBonusIneditismo = new File(pegaDiretorio + valor + ".bon");
		arquivoBonusIneditismo.createNewFile();

	}

	/** No descriptions */
	public static void setArquivoFonte(String valor) {
		arquivoFonte = valor;

	}

	/** No descriptions */
	public static void setFormatoIndividuo(String valor) {
		formatoIndividuo = valor;

	}

	/** seta as funçoes serem testadas do programa em teste. */
	public static void setFuncaoATestar(String valor) {
		funcaoATestar = valor;
	}

	public static void setArquivoCoberturaElemento(String valor)
			throws IOException {
		arquivoCoberturaElemento = new File(pegaDiretorio + valor + ".cov");
		arquivoCoberturaElemento.createNewFile();
	}

	public static void setArquivoCoberturaIndividuo(String valor)
			throws IOException {
		arquivoCoberturaIndividuo = new File(pegaDiretorio + valor + ".cov");
		arquivoCoberturaIndividuo.createNewFile();
	}

	public static void setArquivoRepositorio(String valor) throws IOException {
		arquivoRepositorio = new File(valor + ".dep");
		arquivoRepositorio.createNewFile();
	}

	public static void setArquivoObsCobertura(String valor) throws IOException {
		arquivoObsCobertura = new File(valor + ".tst");
		arquivoObsCobertura.createNewFile();

	}

	public static void setArquivoMelhorPopulacao(String valor)
			throws IOException {
		arquivoMelhorPopulacao = new File(valor + ".pop");
		arquivoMelhorPopulacao.createNewFile();
	}

	public static void setArquivoTabu(String valor) throws IOException {
		arquivoTabu = new File(valor + ".pop");
		arquivoTabu.createNewFile();

	}

	public static void setArquivoTabuAux(String valor) throws IOException {
		arquivoTabuAux = new File(valor + ".pop");
		arquivoTabuAux.createNewFile();

	}

	/** Metodo para indicar o tamanho manimo para o tipo string */
	public static void setTamanhoString(String valor) {

		String quebra[];
		quebra = valor.split("/");
		tamanhoMinimoString = Integer.parseInt(quebra[0].trim());
		tamanhoMaximoString = Integer.parseInt(quebra[1].trim());

	}

	/** No descriptions */
	public static void setTamanhoPopulacao(double valor) {
		tamanhoPopulacao = valor;
	}

	/** No descriptions */
	public static void setMaximoGeracoes(double valor) {
		maximoGeracoes = valor;
	}

	/** No descriptions */
	public static void setCoberturaCriterio(double valor) {
		coberturaCriterio = valor;
	}

	/**
	 * No descriptions
	 * 
	 * @throws IOException
	 */
	public static void setCriterioTeste(String valor) throws IOException {
		criterioTeste = valor;
	}

	/** No descriptions */
	public static void setTaxaMutacao(double valor) {
		taxaMutacao = valor;
	}

	/** No descriptions */
	public static void setTaxaCrossover(double valor) {
		taxaCrossover = valor;
	}

	/** No descriptions */
	public static void setVariacaoInteiro(String valor) {
		String quebra[];
		quebra = valor.split("/");
		tamanhoMinimoInteiro = Integer.parseInt(quebra[0].trim());
		tamanhoMaximoInteiro = Integer.parseInt(quebra[1].trim());
		variacaoInteiro = tamanhoMaximoInteiro - tamanhoMinimoInteiro;
		variacaoInteiroTam = String.valueOf(variacaoInteiro);

	}

	/** No descriptions */
	public static void setMelhorCobertura(double valor) {
		melhorCobertura = valor;
	}

	/** No descriptions */
	public static void setIndiceMelhorGeracao(double valor) {
		indiceMelhorGeracao = valor;
	}

	/** determina o tamanho do individuo */
	public static void setTamanhoIndividuo() {
		int tam = formatoIndividuo.length();
		for (int pos = 0; pos < tam; pos++)
			tamanhoIndividuo += tamanhoTipo(pos);
	}

	/**
	 * Metodo usado para setar o formato da entrada do programa,
	 * consequentemente o formato do indivaduo do AG.
	 */
	public static int tamanhoTipo(int pos) {
		switch (formatoIndividuo.charAt(pos)) {
		case 'I':
			return 6;
		case 'D':
			return 10;
		case 'F':
			return 9;
		case 'S':
			return (int) tamanhoMaximoString;
		case 'C':
			return 1;
		}
		return 0; // zero nao influencia no tamanho do outros tipos.
	}

	/** No descriptions */
	public static void setQuantidadeElemento(int valor) {
		quantidadeElemento = valor;

	}

	/** No descriptions */
	public static void setCoberturaAtual(double valor) {
		coberturaAtual = valor;
		if (geracaoAtual == 0)
			coberturaInicial = valor;

	}

	/** No descriptions */
	public static void setSomatoriaFitness(double valor) {
		somatoriaFitness = valor;
	}

	/** No descriptions */
	public static void setCriterioTesteValiMPI(String valor) {
		criterioTesteValiMPI = valor;

	}

	/** No descriptions */
	public static int inicioTipo(int pos) {
		int res = 0;
		for (int i = 0; i != pos; i++)
			res += tamanhoTipo(i);

		return res;
	}

	/****
	 * Metodo usado para verificar se o framework deve encerrar execucao ou por
	 * ter alcancado a cobertura desejada ou por alcancar limite de geracaes.
	 */
	public static boolean paraTeste() {
		if (geracaoAtual == (maximoGeracoes + 1))
			return true;

		if (coberturaAtual >= 100) {
			return true;
		}

		return false;
	}

	/** Metodo para geracao aleataria de um double, simulacao de sorteio. */
	public static double geraSorteio(double maximo) {
		/**/
		double num1 = Math.random() * 100;
		return Diversos.modulo(num1, maximo);
	}

	/** No descriptions */
	public static void atualizaLinhaCoberturas(String novaCobertura) {
		setLinhaCoberturaAnterior(linhaCoberturaAtual);
		setLinhaCoberturaAtual(novaCobertura);
		// setCoberturaAtual(0);
	}

	/** No descriptions */
	public void atualizaPerda(String desempenho) {
		int indice = -1;
		while ((indice = Diversos.indexOf(linhaPerda, 'P')) > 0) {
			if (desempenho.charAt(indice) == 'X')
				linhaPerda = linhaPerda.replace(linhaPerda.charAt(indice),
						linhaPerda.charAt('-'));

		}

	}

	/**
	 * No descriptions
	 * 
	 * @throws IOException
	 */
	public static void manutencaoMelhorGeracao() throws IOException {
		if (coberturaAtual > melhorCobertura) {
			setMelhorCobertura(coberturaAtual);
			setIndiceMelhorGeracao(geracaoAtual);
			Diversos.copyFile(arquivoPopulacao.getPath(),
					arquivoMelhorPopulacao);
		}
	}

	/**
	 * Metodo usado para recalcular a quantidade de individuos gerados por
	 * elitismo, selecao e ineditismo.
	 */
	public static void recalculaPorcEvolucao() {
		double total = (quantidadeElitismo + quantidadeIneditismo + quantidadeFitness);
		quantidadeElitismo = (int) (quantidadeElitismo * tamanhoPopulacao / total);
		quantidadeIneditismo = (int) (quantidadeIneditismo * tamanhoPopulacao / total);
		quantidadeFitness = tamanhoPopulacao
				- (quantidadeElitismo + quantidadeIneditismo);

	}

	/**
	 * Metodo usado para atribuir valor para variavel de controle
	 * linhaCoberturaAnterior
	 */
	public static void setLinhaCoberturaAnterior(String valor) {
		linhaCoberturaAnterior = valor;

	}

	/**
	 * Metodo usado para atribuir valor para variavel de controle
	 * linhaCoberturaAtual.
	 */
	public static void setLinhaCoberturaAtual(String valor) {

		linhaCoberturaAtual = valor;

	}

	/** Metodo usado para configurar o valor de ativaTabu. */
	public static void setAtivaTabu(int valor) {
		ativaTabu = valor;
	}

	/** Metodo usado para configurar o atributo geracoesComRepositorio. */
	public static void setGeracoesComRepositorio(int valor) {
		geracoesComRepositorio = valor;
	}

	/** Atualiza a coberturaGlobal com a cobertura passada por argumento. */
	public static void atualizaCoberturaGlobal(String novaCobertura) {
		int tam = (int) Central.quantidadeElemento;
		
		String globalTemp = "";
		if (coberturaGlobal == null) {
			coberturaGlobal = novaCobertura;
			return;
		} else {
			char[] charNovaCobertura = novaCobertura.toCharArray();
			char[] coberturaGlobalLocal = coberturaGlobal.toCharArray();
			for (int i = 0; i < tam; i++) {
				if ((charNovaCobertura[i] == 'X')) {
					coberturaGlobalLocal[i] = 'X';
				}
				globalTemp += coberturaGlobalLocal[i];
			}
			coberturaGlobal = globalTemp;
		}
	}

	/** Metodo que configura o final da primeira execucao. */
	public static void setFimPrimeiraExecucao() {
		fimPrimeiraExecucao = Diversos.getSecs();
	}

	/** Metodo que seta o namero de processos. */
	public static void setNProcess(String n) {
		nProcess = n;
	}

	/** Metodo que seta o argumento de funcoes necessario para ValiMPI. */
	public static void setFuncoes(String valor) {

		funcoes = valor;
	}

	public static void setCCArgs(String valor) {

		ccargs = valor;

	}

	/**
	 * M\E9todo usado para iniciar a execu\E7\E3o da ferramenta. Apagar
	 * diret\F3rios e arquivos que possam ter sidos gerados por execu\E7\F5es
	 * anteriores.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void prepareExecution() throws IOException,
			InterruptedException {

		System.out
				.println(" \n Atenção: Preparando a execução do framework. Diretórios e arquivos serão removidos...");

		String cmd = "rm  logerror.tes log_erro.log *.log *.dot "
				+ "Tabu.res Populacao.res melhorPop.pop popManejo.pop tabu.pop tabuAux.pop *.tst *.dot "
				+ " relCobertura.tst resumo.tst exe valimpi.log lsHeaders "
				+ " lixo.lxo -rf valimpi "
				+ "repositorio.dep evolucao.fil arquivoElementos.elem";

		Process proc = Runtime.getRuntime().exec(cmd);
		proc.waitFor();
		;

		// System.out.println(" \n removeFile.sh %s %s valimpi logerror.tes log_erro.log detalhes.log Populacao.res Tabu.res tabu.pop melhorPop.pop resumo.tst resultado.tst relCobertura.tst avalCoberturas.tst *.gfc lixo.lxo entrada.kyb resultado.tst");

	}

	// _________________________________________________________________
	/**
	 * M\E9todo usado para verificar se um determinado indiv\EDduo j\E1 foi
	 * executado anteriormente, caso positivo, copia o desempenho armazenado no
	 * reposit\F3rio parra a vari\E1vel desempenho.
	 * 
	 * @throws IOException
	 */
	public static String inRepositorio(String strIndiv, String desempenho)
			throws IOException {
		FileReader arq = new FileReader(arquivoRepositorio);
		BufferedReader lerArq = new BufferedReader(arq);
		String[] quebra = null;

		String linha = null, dadoLocal = null, desempenhoLocal = null;

		linha = lerArq.readLine();
		while (linha != null) {
			quebra = linha.split(":");
			dadoLocal = quebra[0].trim();
			desempenhoLocal = quebra[1].trim();
			;
			if (strIndiv.equals(dadoLocal)) {
				desempenho = desempenhoLocal;
				break;
			} // fim if

			linha = lerArq.readLine();
		}// fim while
		lerArq.close();
		return desempenho.trim();
	}
}
