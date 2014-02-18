package main;

import java.io.IOException;

import algoritmoGenetico.Individuo;
import algoritmoGenetico.Populacao;
import ferramenta.Central;
import ferramenta.Ferramenta;


public class Main {
	
	/***************************************************************************
	 * * This program is free software; you can redistribute it and/or modify *
	 * it under the terms of the GNU General Public License as published by *
	 * the Free Software Foundation; either version 2 of the License, or * (at
	 * your option) any later version. * *
	 ***************************************************************************/


	
		public static void main(String[] args) throws IOException, InterruptedException {
		
			

		Central.inicioExecucao = Diversos.getSecs();
		
		Central.prepareExecution();
		Central.interpretaArquivoConfiguracao();
		Ferramenta.preparaFerramenta();
		Central.setQuantidadeElemento(Ferramenta.obtemElementosRequeridosValiMPI());
		Populacao.geraPopulacaoInicial();	
		Populacao.avaliaPopulacao();
		
		Central.backup();
		Central.status();
		
		Central.fimPrimeiraExecucao = Diversos.getSecs();

		for (Central.geracaoAtual = 1; !Central.paraTeste(); Central.geracaoAtual++) {
			Populacao.evoluiPopulacao();
			Populacao.avaliaPopulacao();
			Central.backup();
			Central.manutencaoMelhorGeracao();
			Central.status();
			
		}// fim for
		
		Central.fimExecucao = Diversos.getSecs();
		System.out.print(" \nFim do processo de evolucao...");
		Central.resultado();
		
		if (Central.geracaoAtual > 1){
			Populacao.decodificaPopulacao(Central.arquivoMelhorPopulacao,
			"Populacao.res");
		}
		
     		if (Central.ativaTabu==1){
    			Populacao
    					.decodificaPopulacao(Central.arquivoTabu, "Tabu.res");
    		}
	
		System.out.print("\nFim de execução...\n");

	}
}
