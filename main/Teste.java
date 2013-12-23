package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import algoritmoGenetico.Individuo;
import ferramenta.Central;
import ferramenta.Ferramenta;
import ferramenta.ValiMPI;

public class Teste {
	// static Diversos objDiversos;
	
	
	
	
	public static void main(String[] args) throws IOException,
			InterruptedException {
		Individuo individuoObjt = new Individuo();
		//Central centralobjt = new Central();
		

		Central.interpretaArquivoConfiguracao();
			Individuo[] individuos;
	        individuos = new Individuo[10];
	        for (int i = 0; i < individuos.length; i++) {
	            individuos[i] = new Individuo(Central.formatoIndividuo.length());
	        }
	        
	        for (int i = 0; i < individuos.length; i++) {
	        	System.out.println(individuos[i].getGenes());
	        }
	    }
		//System.out.println("\n" + Individuo.getGenes());
		
}