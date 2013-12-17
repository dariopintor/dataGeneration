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
		System.out.println("\n"+individuoObjt.forma_block_int_random(null, 16));
		
	

}
}