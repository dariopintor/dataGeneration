package algoritmoGenetico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.server.ObjID;
import java.util.Formattable;



import java.util.Random;

import main.Diversos;
import main.Mersenne;
import ferramenta.*;

public class Individuo{
	/***************************************************************************
	 * individuo.cpp - description ------------------- begin : Dom Jul 6 2003
	 * copyright : (C) 2003 by Luciano Petinati Ferreira email :
	 * petinat@inf.ufpr.br
	 ***************************************************************************/

	/***************************************************************************
	 * * This program is free software; you can redistribute it and/or modify *
	 * it under the terms of the GNU General Public License as published by *
	 * the Free Software Foundation; either version 2 of the License, or * (at
	 * your option) any later version. * *
	 ***************************************************************************/
	/** Atributo que contem a representacao dos individuos. */
	
	public String genes="";
	
	public Individuo() {
		// cout << "\nConstruindo individuo...";		
		genes ="";

	}

	/** Metodo para criar um novo individuo aleatorio. */
	public  Individuo(int numGenes) {
		String bloco = "";
		int blocoTam = (int) Central.tamanhoIndividuo;

		for (int i = 0; i < numGenes; i++) {

			switch (Central.formatoIndividuo.charAt(i)) {
			case 'I': {
				bloco = forma_block_int_random(bloco, blocoTam);
				break;
			}
			
			default: {
				System.out.printf(
						"\nFormato : %c\nFormato de tipo invalido\n\r",
						Central.formatoIndividuo.charAt(i));
			}
			}// fim switch
			genes += bloco;
		} // fim for

	}

	// _________________________________________________________________

	/**
	 * * Metodo para criar um novo individuo baseado na linha do arquivo e na
	 * configuracao fornecida ao framework.
	 */

	public void individuo (String linha) {
		int numArgumentos = Central.formatoIndividuo.length();
		String bloco = null;
		String [] dado =null;
		for (int cont = 0; cont < numArgumentos; cont++) {
			dado = linha.split(",");
			
			switch (Central.formatoIndividuo.charAt(cont)) {
			case 'I': {
				bloco =	forma_block_int(Central.tamanhoTipo(cont) + 1,
						dado[cont]);
				// printf("\nRecuperado Arg %d - Integer '%s'", cont, bloco);
				break;
			}

			
			}// fim switch
			genes += bloco;
		}

	}

	/**
	 * * Metodo para recuperar o pos-esimo individuo da populacao. pos deve
	 * variar de 0 ate tamanho da populacao - 1.
	 * @throws IOException 
	 */
	public void load(int pos) throws IOException {
		int i = 0;
		String gene = null, geneAux = null;
		String saida = null, linha = null;
		
		FileReader fr = new FileReader(Central.arquivoPopulacao);
		BufferedReader br = new BufferedReader(fr);	
		
		if (pos >= Central.tamanhoPopulacao) {
			 saida = String.format("Pos, %d, extrapolou tamanho da populacao %f", pos, Central.tamanhoPopulacao);
			 Diversos.erro(saida,1);
		}

		linha = br.readLine();
		for (i = 0; i <= pos; i++) {
			// cout << "\n ind nro " << i;
			if (pos == 14){
				pos += 0;
			}	
			gene = linha;
			linha = br.readLine();	
		}// fim for	
		br.close();
		
		i = Diversos.indexOf(gene.trim(), ':') + 1;
		geneAux = gene + i;
		setGenes(geneAux.trim());		
	}

	// _________________________________________________________________

	/** Metodo que atribui valor para a gene de um individuo. */
	public void setGenes(String valor) {	
		genes = valor;
	}

	// _________________________________________________________________

	/** Metodo que recupera o respectivo valor do bloco passado como argumento. */
	public int decode_block_int(String bloco) {

		return Integer.parseInt(bloco);
	}

	// _________________________________________________________________

	/**
	 * Metodo usado para formatar um bloco a partir de um valor passado por
	 * argumento.
	 */
	public String forma_block_int(int limite, String entrada) {
		String res = "";
		int tam = 0;
		String aux="+", numero=null;
		if ((entrada.charAt(0) == '-') || (entrada.charAt(0) == '+')) {
			aux = aux.replace(aux.charAt(0), entrada.charAt(0));
		}
	
		numero = entrada.trim();
		aux += numero;
		
			res = aux;
		
		return res;
	}

	// _________________________________________________________________

	/** Metodo usado para formatar um bloco aleatoriamente. */
	public String  forma_block_int_random(String res, int limite){	
		int num = 0;
		String aux = "+";
		num = Diversos.rand(Central.tamanhoMinimoInteiro, Central.tamanhoMaximoInteiro);
		aux  += String.valueOf(num);
		res = aux;
		
		return res;
		}// fim forma_block_int_random

	// _________________________________________________________________
	
	/** Metodo usado para avaliar a populacao do AG. Cada individuo eh executado e
	 * o fitness o calculado com base no numero de elementos requeridos
	 * satisfeitos pela execucao do mesmo.
	 * @throws IOException 
	 * @throws InterruptedException */
	public void getAptidao() throws IOException, InterruptedException {
		
		
		 
		geraCoberturaIndividuo();
		Diversos.toFile("log_alocacoes.tst", "0, 0, 0, PProva");
		Diversos.toFile("log_alocacoes.tst", "1, 0, 0, PProva");
		geraBonusIneditismo();
		geraIneditismoPopulacao();

//		Central.geraLinhaPerda();
//		System.out.println("\n Linha de perda:\n" << Central.linhaPerda());

		geraFitness();
		if (Central.geraLog != 0){		
		Diversos.toFile("log_erro.log", "---saindo avaliaPopulacao");
		}
	}// fim avaliaPopulacao()
	
	public String getGenes() {
        return genes;
    }
	}
