package ferramenta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.omg.CORBA.StringValueHelper;

import main.Diversos;
import algoritmoGenetico.*;

public class Ferramenta {
	/***************************************************************************
	 * ferramenta.cpp - description ------------------- begin : Dom Jul 6 2003
	 * copyright : (C) 2003 by Luciano Petinati Ferreira email :
	 * petinat@inf.ufpr.br This program is free software; you can redistribute
	 * it and/or modify * it under the terms of the GNU General Public License
	 * as published by * the Free Software Foundation; either version 2 of the
	 * License, or * (at your option) any later version. * *
	 ***************************************************************************/

	Individuo objIndividuo;

	public Ferramenta() {
		System.out.println("\nConstruindo ferramenta...");
		objIndividuo = new Individuo();

	}

	/**
	 * Metodo usado para inicar a ferramenta adequada baseado nas informacoes de
	 * controle contidas em ctl.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void prepareTool() throws IOException, InterruptedException {
		System.out.println("\nUtilizando ValiMPI.");

		//ValiMPI.exeVali_inst(Central.arquivoFonte);
		//ValiMPI.exeVali_reduce(null); // ainda nao implementadotado
		ValiMPI.exeVali_elem(Central.nProcess, Central.funcoes);
		//ValiMPI.exeVali_cc(Central.funcaoATestar);

		System.out.println("Termina uso da ValiMPI.");
	}

	/**
	 * Metodo usado para obter a quantidade de elementos requeridos para
	 * criterios suportados pela ferramenta ValiMPI.
	 * 
	 * @throws IOException
	 */
	public static int obtemElementosRequeridosValiMPI() throws IOException {
		System.out.println("\n---obtemElementosValiMPI");

		int quantidadeElemReq = 0;
		String dir_elem_req = "valimpi/res/";

		File arquivoElementos;
		arquivoElementos = new File("arquivoElementos.elem");
		arquivoElementos.createNewFile();
		Diversos.limpaArquivo("arquivoElementos.elem");

		if (Central.criterioTeste.equals("Todas as Arestas")) {
			Diversos.copyFile((dir_elem_req + "todas-arestas.req"),
					arquivoElementos);
			Central.setCriterioTesteValiMPI("todas-arestas");
		}

		else if (Central.criterioTeste.equals("Todos os Nos")) {
			Diversos.copyFile((dir_elem_req + "todos-nos.req"),
					arquivoElementos);
			Central.setCriterioTesteValiMPI("todos-nos");

		} else if (Central.criterioTeste.equals("Todos os Potenciais Usos")) {
			Diversos.copyFile((dir_elem_req + "todos-p-usos.req"),
					arquivoElementos);
			Central.setCriterioTesteValiMPI("todos-p-usos");

		} else if (Central.criterioTeste.equals("Todos os Usos Computacionais")) {
			Diversos.copyFile((dir_elem_req + "todos-c-usos.req"),
					arquivoElementos);
			Central.setCriterioTesteValiMPI("todos-c-usos");

		} else if (Central.criterioTeste.equals("Todos os Usos de Sincronismo")) {
			Diversos.copyFile((dir_elem_req + "todos-s-usos.req"),
					arquivoElementos);
			Central.setCriterioTesteValiMPI("todos-s-usos");

		} else if (Central.criterioTeste.equals("Todos os Nos S")) {
			Diversos.copyFile((dir_elem_req + "todos-nosS.req"),
					arquivoElementos);
			Central.setCriterioTesteValiMPI("todos-nosS");

		} else if (Central.criterioTeste.equals("Todos os Nos R")) {
			Diversos.copyFile((dir_elem_req + "todos-nosR.req"),
					arquivoElementos);
			Central.setCriterioTesteValiMPI("todos-nosR");

		} else if (Central.criterioTeste.equals("Todas as Arestas S")) {
			Diversos.copyFile((dir_elem_req + "todas-arestasS.req"),
					arquivoElementos);
			Central.setCriterioTesteValiMPI("todas-arestasS");
		}

		// Diversos.copyFile((dir_elem_req + "todos-nos.req"),
		// arquivoElementos);

		if (arquivoElementos.length() == 0) {
			Diversos.erro(
					"nao abriu o arquivo dos elementos requeridos da ValiMPI corretamente",
					1);
		}

		// menos 2 são as duas primeiras linhas do arq. que n se refere aos
		// elementos
		quantidadeElemReq = Diversos.quantidadeLinhas(arquivoElementos) - 2;
		System.out.print("\n--- Saindo de obtemElementosValiMPI");

		return quantidadeElemReq;

	}

	/**
	 * Metodo usado para avaliar o nro-esimo individuo da populacao. Recupera o
	 * nro-esimo individuo da populacao; Recupera os argumentos/entradas que
	 * este representa; Separa os argumentos (argumento de chamada ou entrada de
	 * teclado) conforme necessidade do programa; Executa o programa e avalia o
	 * resultado da execucao.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void usaValiMPI(String dado, int nDado) throws IOException,
			InterruptedException {
		// executa dado na valimpi

		Process proc1 = Runtime.getRuntime().exec(
				ValiMPI.exeVali_exec("1", Central.nProcess, dado));
		proc1.waitFor();

		// avalia dado recem executado na valimpi
		Process proc2 = Runtime.getRuntime().exec(
				ValiMPI.exeVali_eval(Central.criterioTesteValiMPI,
						Central.nProcess, Central.funcoes));
		proc2.waitFor();

		// gera cobertura em arquivos
		Thread.sleep(1000);
		obtemCoberturaValiMPI(nDado);
		// Thread.sleep(1000);

	}

	/**
	 * Metodo usado para obter linha de cobertura que contem o desempenho do
	 * individuo perante aos elementos requeridos.
	 * 
	 * @throws IOException
	 */
	public static void obtemCoberturaValiMPI(int nDadoTeste) throws IOException {

		String linhaCobertura = "";
		String[] vetorPegaElementos;
		vetorPegaElementos = pegaElementos("arquivoElementos.elem");

		String[] vetorPegaElementosCobertos;
		vetorPegaElementosCobertos = pegaElementosCobertos("gcd/vali_eval.out");
		String[] saida = new String[vetorPegaElementos.length];

		for (int i = 0; i < vetorPegaElementos.length; i++) {
			for (int j = 0; j < vetorPegaElementosCobertos.length; j++) {
				if (vetorPegaElementos[i].equals(vetorPegaElementosCobertos[j])) {
					saida[i] = "X";
				}
			}// termina for para i
		}// termina for para j

		for (int i = 0; i < saida.length; i++) {
			if (saida[i] != "X") {
				saida[i] = "-";
			}
			linhaCobertura += saida[i];
		}

		String conteudo = nDadoTeste + ": " + linhaCobertura;

		Diversos.escreverArquivo("gcd/cobXIndividuo.cov", conteudo);

	}

	/**
	 * Pega os elementos requiridos presente em "arquivoElementos.elem" e c
	 * coloca em um vetor
	 * 
	 * @throws IOException
	 */
	public static String[] pegaElementos(String arquivo) throws IOException {

		String[] vetor = new String[Central.quantidadeElemento];
		String linhaArqElementos = null;
		int contLinhaElementos = 0;

		// permissoes de leitura do arquivo de elementos
		FileReader arqElemementos = new FileReader(arquivo);
		BufferedReader lerArqElemementos = new BufferedReader(arqElemementos);

		// le a primeira linah do arquivo
		linhaArqElementos = lerArqElemementos.readLine();
		for (int i = 0; linhaArqElementos != null;) {
			contLinhaElementos++;
			// pega os elementos a partir da segunda linha do arquivo
			if (contLinhaElementos > 2) {
				vetor[i] = linhaArqElementos.trim();
				i++;
			}
			linhaArqElementos = lerArqElemementos.readLine();
		}
		lerArqElemementos.close();
		return vetor;
	}

	/**
	 * Pega os elementos requiridos cobertos presente em "gcd/vali_eval.out" e
	 * coloca em um vetor
	 * 
	 * @throws IOException
	 */
	public static String[] pegaElementosCobertos(String arquivo)
			throws IOException {
		String[] vetor = new String[Central.quantidadeElemento];
		String[] quebraLinha = null;
		String linhaArqElemcobertos = null;
		int contLinhaElementos = 0;

		FileReader arqElemCobertos = new FileReader(arquivo);
		BufferedReader lerElemCobertos = new BufferedReader(arqElemCobertos);

		linhaArqElemcobertos = lerElemCobertos.readLine();

		for (int i = 0; linhaArqElemcobertos != null; contLinhaElementos++) {

			// para quando alcaçar a linha elementos nao cobertos
			if (linhaArqElemcobertos
					.equals("-- ELEMENTOS REQUERIDOS NÃO COBERTOS --")) {
				break;
			}
			// inicia a partir da segunda linha
			if (contLinhaElementos > 0) {
				if (Central.criterioTeste
						.equals("Todos os Usos de Sincronismo")
						|| Central.criterioTeste
								.equals("Todos os Usos Computacionais")
						|| Central.criterioTeste
								.equals("Todos os Potenciais Usos")) {

					quebraLinha = linhaArqElemcobertos.split(",  c");
					vetor[i] = quebraLinha[0].trim();
					i++;
				} else {
					quebraLinha = linhaArqElemcobertos.split(",");
					vetor[i] = quebraLinha[0].trim();
					i++;
				}

			}// fim if
			linhaArqElemcobertos = lerElemCobertos.readLine();
		} // fim for

		lerElemCobertos.close();

		return vetor;
	}

	/**
	 * Metodo usado para obter linha de cobertura que contem o desempenho do
	 * individuo perante aos elementos requeridos.
	 * 
	 * @throws IOException
	 */
	
}
