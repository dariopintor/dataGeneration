package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.crypto.Data;

import algoritmoGenetico.Individuo;
import algoritmoGenetico.Populacao;
import ferramenta.Central;
import ferramenta.Ferramenta;
import ferramenta.ValiMPI;

public class Teste {

	public static void main(String[] args) throws IOException,
			InterruptedException {

		Central.interpretaArquivoConfiguracao();

		Ferramenta.lerArquivo("Populacao.res", "todas-arestasS");
		FileReader fr = new FileReader("gcd/vali_eval.out");
		BufferedReader bf = new BufferedReader(fr);
		
		String linha = null; 
		linha  = bf.readLine();
		while ( linha != null){
			System.out.println(linha);
			linha  = bf.readLine();
		}
	}
}