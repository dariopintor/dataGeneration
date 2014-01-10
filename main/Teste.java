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
	/** Método que aplica operador crossover nos indivíduos representados por indiv1 e indiv2 */
	
	
	public static void main(String[] args) throws IOException,
			InterruptedException {
		 Central.interpretaArquivoConfiguracao();
		// Populacao.geraPopulacaoInicialArquivo();
		// System.out.println(Populacao.toPopulacao(0, "+34+25+25",
		// Central.arquivoPopulacaoTemporario));
		
		// System.out.println( Central.inicioTipo(1));
		// System.out.println( Central.tamanhoTipo(1));
		
		
	}
}
