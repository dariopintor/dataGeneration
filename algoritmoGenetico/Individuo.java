package algoritmoGenetico;

import java.awt.image.DirectColorModel;
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

public class Individuo {
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

	public String genes = "";
	double aptidao = 0;
	
	public Individuo() {
		// cout << "\nConstruindo individuo...";
		genes = "";

	}

	/** Metodo para criar um novo individuo aleatorio. */
	public Individuo(int numGenes) {
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

	public Individuo(String linha) {
		int numArgumentos = Central.formatoIndividuo.length();
		String bloco = null;
		String[] dado = null;
		for (int cont = 0; cont < numArgumentos; cont++) {

			if (linha.charAt(0) == '+' || linha.charAt(0) == '-') {
				genes = linha.trim();
				break;
			} else {
				dado = linha.split(",");

				switch (Central.formatoIndividuo.charAt(cont)) {
				case 'I': {
					bloco = forma_block_int(Central.tamanhoTipo(cont) + 1,
							dado[cont]);
					// printf("\nRecuperado Arg %d - Integer '%s'", cont,
					// bloco);
					break;
				}

				}// fim switch
				genes += bloco;
			}
		}

	}

	// _________________________________________________________________

	/** Metodo que atribui valor para a gene de um individuo. */
	public void setGenes(String valor) {
		genes = valor;
	}

	// _________________________________________________________________

	/** Metodo que recupera o respectivo valor do bloco passado como argumento. */
	public static int decode_block_int(String bloco) {

		return Integer.parseInt(bloco);
	}

	// _________________________________________________________________

	/**
	 * Metodo usado para formatar um bloco a partir de um valor passado por
	 * argumento.
	 */
	public static String forma_block_int(int limite, String entrada) {
		String res = "";
		String num = "";
		String aux = "+00000";
		int diferenca = 0;

		int tam = 0;
		String numero = null;

		if ((entrada.charAt(0) == '-') || (entrada.charAt(0) == '+')) {
			aux = aux.replace(aux.charAt(0), entrada.charAt(0));
		}

		diferenca = aux.length() - entrada.length();
		res = aux.substring(0, diferenca);
		res += entrada.substring(0, entrada.length());

		return res;
	}

	// _________________________________________________________________

	/** Metodo usado para formatar um bloco aleatoriamente. */
	public static String forma_block_int_random(String res, int limite) {
		String num = "";
		String aux = "+00000";
		int diferenca = 0;
		num = String.valueOf(Diversos.rand(Central.tamanhoMinimoInteiro,
				Central.tamanhoMaximoInteiro));
		diferenca = aux.length() - num.length();
		res = aux.substring(0, diferenca);
		res += num.substring(0, num.length());
		return res;
	}// fim forma_block_int_random

	// _________________________________________________________________

	public String getGenes() {
		return genes;
	}
	
	public double getAptidao() {
		return aptidao;
	}

	public void setAptidao(double aptidao) {
		this.aptidao = aptidao;
	}
	
	
}
