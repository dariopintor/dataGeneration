package algoritmoGenetico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.server.ObjID;
import java.util.Formattable;



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
	
	public String representacao;
	static Mersenne merseneObjeto;
	
	public Individuo() {
		// cout << "\nConstruindo individuo...";
		merseneObjeto = new Mersenne();
		representacao = null;

	}

	/** Metodo para criar um novo individuo aleatorio. */
	public void novo() {
		String bloco = null;
		int nroBloco = Central.formatoIndividuo.length();
		int blocoTam = (int) Central.tamanhoIndividuo;

		for (int i = 0; i < nroBloco; i++) {

			switch (Central.formatoIndividuo.charAt(i)) {
			case 'I': {
				forma_block_int_random(bloco, blocoTam);
				break;
			}
			
			default: {
				System.out.printf(
						"\nFormato : %c\nFormato de tipo invalido\n\r",
						Central.formatoIndividuo.charAt(i));
			}
			}// fim switch
			representacao = bloco;
		} // fim for

	}

	// _________________________________________________________________

	/**
	 * * Metodo para criar um novo individuo baseado na linha do arquivo e na
	 * configuracao fornecida ao framework.
	 */

	public void novo(String linha) {
		int numArgumentos = Central.formatoIndividuo.length();
		int virg = -1;
		String blocoLinha = null, bloco = null;
		
		for (int cont = 0; cont < numArgumentos; cont++) {
			if ((virg = Diversos.indexOf(linha, ',')) != -1){		
				blocoLinha = linha;
			}
				else{
					blocoLinha = linha;
				}			
			linha = linha.trim() + virg + 1;
			
			switch (Central.formatoIndividuo.charAt(cont)) {
			case 'I': {
				forma_block_int(bloco, Central.tamanhoTipo(cont) + 1,
						blocoLinha);
				// printf("\nRecuperado Arg %d - Integer '%s'", cont, bloco);
				break;
			}

			
			}// fim switch
			representacao = bloco;
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
		setRepresentacao(geneAux.trim());		
	}

	// _________________________________________________________________

	/** Metodo que atribui valor para a representacao de um individuo. */
	public void setRepresentacao(String valor) {	
		representacao = valor;
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
	public void forma_block_int(String res, int limite, String lido) {
		int tam = 0;
		String aux=null, numero=null;
		if ((lido.charAt(0) == '-') || (lido.charAt(0) == '+')) {
			aux = aux.replace(aux.charAt(0), lido.charAt(0));
			//lido++;
		}
		
		numero = lido.trim();
		tam = numero.length();

		if (limite > (int) aux.length()){
			res = aux;
		}
			else{
				Diversos.erro(
					"Estouro de tamanho gerando inteiro randomicamente", 1);
			}

	}

	// _________________________________________________________________

	/** Metodo usado para formatar um bloco aleatoriamente. */
	public static  String  forma_block_int_random(String res, int limite){
		int tam = 0;
		String aux = null,  numero = null;
		 aux = "+00000";
		 int num = 0;
		 if (Central.tamanhoMinimoInteiro < 0){
			 int sinal = merseneObjeto.genrand() % 2; 
			
			 //[0+,1-]
			 	if(sinal == 1){
			 		num = ( merseneObjeto.genrand() % ( ( -1 * (Central.tamanhoMinimoInteiro ) +1 ) ));
			 		aux = aux.replace(aux.charAt(0), '-');
			 	}
			 		else //+
			 			num = (merseneObjeto.genrand());
		 }
		 	else{
		 		num =  merseneObjeto.genrand();
		 	}
		 numero = String.format("%d", num);
		 System.out.println(numero);
		//strcpy(aux + (6-tam), numero);
		 aux += numero.trim();
		
		 if (limite >= (int) aux.length())
			 res = aux;
		 	else 
		 		Diversos.erro("Estouro de tamanho gerando inteiro randomicamente",1);

return res;
}// fim forma_block_int_random

	// _________________________________________________________________

	}
