package algoritmoGenetico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import ferramenta.Central;
import ferramenta.Ferramenta;
import ferramenta.ValiMPI;
import main.Diversos;
import main.Mersenne;

public class Teste {
	// static Diversos objDiversos;
	
	
	Mersenne r = new Mersenne();
	Individuo individuoObjt = new Individuo();
	
	
	public static void main(String[] args) throws IOException,
			InterruptedException {
		Individuo individuoObjt = new Individuo();
		Central centralobjt = new Central();
		String linha = "";
		String nomeArq = "arqconfig.txt"; // Nome do arquivo,
		// pode ser absoluto, ou pastas /dir/teste.txt
		
		FileReader arq = new FileReader(nomeArq);
		
		BufferedReader lerArq = new BufferedReader(arq);	

		linha = lerArq.readLine(); // la a primeira linha

		// a variavel "linha" recebe o valor "null" quando o processo
		// de repeticao atingir o final do arquivo texto
		while (linha != null) {
			System.out.println(linha);
			linha = lerArq.readLine(); // la a primeira linha
		}
		}

		//centralobjt.interpretaArquivoConfiguracao();
		//System.out.println("\n"+individuoObjt.forma_block_int_random(null, 16));
		
	

}