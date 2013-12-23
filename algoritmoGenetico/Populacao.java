package algoritmoGenetico;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;

import main.Diversos;
import main.Mersenne;
import ferramenta.Central;
import ferramenta.Ferramenta;

/***************************************************************************
 populacao.cpp  -  description
 -------------------
 begin                : Dom Jul 6 2003
 copyright            : (C) 2003 by Luciano Petinati Ferreira
 email                : petinat@inf.ufpr.br
 ***************************************************************************/

/***************************************************************************
 * * This program is free software; you can redistribute it and/or modify * it
 * under the terms of the GNU General Public License as published by * the Free
 * Software Foundation; either version 2 of the License, or * (at your option)
 * any later version. * *
 ***************************************************************************/

public class Populacao {
	
	
	 private Individuo[] individuos;
	 private int tamPopulacao;
	 
	 	public Populacao() throws IOException {
		System.out.println("\nConstruindo populacao...");
		
		if (Central.arquivoPopulacao == null) {
			geraPopulacaoInicialAleatoria();
			} else {
				geraPopulacaoInicialArquivo();
				}
		}
	
	 	//cria uma população com indivíduos sem valor, será composto posteriormente
	    public Populacao(int tamPop) {
	        tamPopulacao = tamPop;
	        individuos = new Individuo[tamPop];
	        for (int i = 0; i < individuos.length; i++) {
	            individuos[i] = null;
	        }
	    }
	    
	    /** //coloca um indivíduo em uma certa posição da população */
	    public void setIndividuo(Individuo individuo, int posicao) {
	        individuos[posicao] = individuo;
	    }

	    /**coloca um indivíduo na próxima posição disponível da população */
	    public void setIndividuo(Individuo individuo) {
	        for (int i = 0; i < individuos.length; i++) {
	            if (individuos[i] == null) {
	                individuos[i] = individuo;
	                return;
	            }
	        }
	    }

	    /**ordena a população pelo valor de aptidão de cada indivíduo,
	     *  do maior valor para o menor, assim se eu quiser obter o 
	     *  melhor indivíduo desta população, acesso a posição 0 
	     *  do array de indivíduos */
	    public void ordenaPopulacao() {
	        boolean trocou = true;
	        while (trocou) {
	            trocou = false;
	            for (int i = 0; i < individuos.length - 1; i++) {
	                if (individuos[i].getAptidao() < individuos[i + 1].getAptidao()) {
	                    Individuo temp = individuos[i];
	                    individuos[i] = individuos[i + 1];
	                    individuos[i + 1] = temp;
	                    trocou = true;
	                }
	            }
	        }
	    }
	/** Cria a populacao inicial aleatoria, com base nas configuracoees fornecidas */
	public void geraPopulacaoInicialAleatoria() {
		System.out.println("\nGerando pop.inic.aleatoria");
		Individuo[] individuos;
        individuos = new Individuo[(int) Central.tamanhoPopulacao];
		for (int contador = 0; contador < individuos.length; contador++) {
			individuos[contador] = new Individuo(Central.formatoIndividuo.length());		
		} // fim for
	}


	/** Cria a populacao inicial baseado em arquivo fornecido. 
	 * @throws IOException */
	public void geraPopulacaoInicialArquivo() throws IOException {
		//ler o arquivo da populacao inical ler linha por linha e passar cada conteudo da linha para o 
		//novo da classe individuo
		System.out.printf("\nGerando pop.inic.arquivo %s", Central.arquivoPopulacaoInicial);
		
		FileReader arq = new FileReader(Central.arquivoPopulacaoInicial);
		BufferedReader lerArq = new BufferedReader(arq);
	
		String linha = null;
		int contador = 0;
		linha = lerArq.readLine();		
		for (contador = 0; linha != null;) {		
			linha = linha.trim();
			
				if (linha !=("")) {
				objIndividuo.individuo(linha);
				if (toPopulacao(contador, objIndividuo.genes, Central.arquivoPopulacao) == true){
					contador++;
				}
			}
		} // fim for
		Central.setTamanhoPopulacao(contador);
		Central.recalculaPorcEvolucao();	
	}
	 

	/**Metodo usado para incluir um novo individuo no arquivo de populacao. Caso
	 * o individuo ja exista na populacao a insercao nao sera realizada. 
	 * @throws IOException */
	boolean toPopulacao(int nro, String strIndividuo, File arquivo) throws IOException {
		
		String saida = ""; 
		
		if ((strIndividuo.equals("")) || (strIndividuo.equals(216)))
			System.out
					.println(" to erro, tentando salvar individuo vazio ou 216,216");
		
		if (nro != 0) {
			if (inPopulacao(strIndividuo, arquivo)) {
				return false;
			}
		}		
		
		
		saida += String.format("%5d : %s\n", nro , strIndividuo);
		Diversos.escreverArquivo(arquivo, saida);
		
		
		return true;
	}

	/** Metodo usado para verificar se um individuo esta na populacao
	 * representada pelo arquivo passado por argumento. 
	 * @throws IOException */	
	public boolean inPopulacao(String strIndividuo, File arquivo) throws IOException{	  
		String linhaArquivo = null;
		String pegaQuebra = null;
		String [] quebraLinhaArq = null;
		
		FileReader fr = new FileReader(arquivo);
		BufferedReader br = new BufferedReader(fr);
		
		linhaArquivo = br.readLine();		
		
		while (linhaArquivo != null){
			quebraLinhaArq = linhaArquivo.split(":");
			pegaQuebra = quebraLinhaArq[1].trim();
						
			if(pegaQuebra.equals(strIndividuo)){
			br.close();
			return true;				
			}
			
			linhaArquivo = br.readLine();
		}
		
		br.close();
		return false;		
	}

	

	 
	/** Metodo usado para gerar a cobertura dos individuos da populacao. 
	 * @throws IOException 
	 * @throws InterruptedException */
	public void geraCoberturaIndividuo() throws IOException, InterruptedException {

		if (Central.geraLog != 0 ){
					Diversos.toFile("log_erro.log", "------geraCoberturaIndividuo");
		}
		
			System.out.printf("\nGerando cobertura para ValiMPI...");
			geraCoberturaIndividuoValiMPI();
		// Tanto geraCoberturaValiMPI como geraCoberturaProteum geram
		// Central.coberturaCriterio
			
		Central.setCoberturaAtual(Diversos.numberOf(Central.linhaCoberturaAtual,
				'X') * 100 / Central.quantidadeElemento);

		System.out.println("\nCobertura Gerada...");
		System.out.println("coberturaGlobal - " + Central.coberturaGlobal
				+ "\nlinhaCobertura - " + Central.linhaCoberturaAtual);
		sobrepoe((Central.coberturaGlobal), Central.linhaCoberturaAtual);

		geraCoberturaElementos();

		if (Central.geraLog != 0) {
			Diversos.toFile("log_erro.log", "------saindo geraCoberturaIndividuo");
		}
		
	}
 

	/**	 * Metodo usado para gerar a cobertura dos individuos da populacao com base
	 * em arquivos gerados pela utilizacao da ferramenta ValiMPI. 
	 * Para cada individuo da populacao, deve obter a cobertura. verifica se ja
	 * esta no repositorio, se estiver, recupera a cobertura/desempenho do
	 * repositorio. se nao estiver executa o programa instrumentado para cada
	 * individuo. obtem a cobertura alcancada. Documenta a cobertura no arquivo
	 * verifica se deve ser incluido na lista tabu. se tiver, substitui todos os
	 * individuos com cobertura coberta por esse individuo e insere na lista
	 * tabu senao nao faz nada.
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void geraCoberturaIndividuoValiMPI() throws IOException, InterruptedException{
      
   String linhaCobertura = null;
   String linhaCoberturaCriterio = null;

    System.out.println( "\nAvaliando os Individuos:\n") ;   
    
    //sprintf(Comando, "rm %s -rf", (*ctl)->arquivoCoberturaIndividuo);
    Diversos.limpaArquivo(Central.arquivoCoberturaIndividuo.getPath());
    
   for (int contador = 0; contador < Central.tamanhoPopulacao; contador ++){
      //recupera individuo da populacao e inicializa a linha de cobertura.
      individuo[contador].load(contador); //ou contador+1);
    
           //verifica necessidade de execucao
      if ( (Central.geracaoAtual == 0) ||
    		  (Central.geracaoAtual >= Central.geracoesComRepositorio )
    		  || (Central.inRepositorio(objIndividuo.genes,  linhaCobertura) == linhaCobertura) ) {   //if (true)     {
         //deve executar o programa.
        Ferramenta.evaluateIndividual( contador );
        Ferramenta.obtemCoberturaValiMPI( linhaCobertura, (int) ( Central.quantidadeElemento + 20 ) );

         toRepositorio( contador, linhaCobertura );
      }  // fim if
      else{
         System.out.println( "R");
      }

      //atualiza a cobertura do criterio / sobrepoe.
      sobrepoe(linhaCoberturaCriterio, linhaCobertura);

//      if ( !Diversos.toFile( Central.arquivoCoberturaIndividuo, contador, linhaCobertura ) ){
//       String saida = saida.format("nao gravou no arquivo : %s", Central.arquivoCoberturaIndividuo);
//        Diversos.erro(saida,1);
//        }
     
   }   // fim for contador

   Central.atualizaLinhaCoberturas(linhaCoberturaCriterio);
   //Central.setCoberturaAtual( 0);

   }  // fim geraCoberturaIndividuoValiMPI()

	 

	/**
	 * Metodo usado para armazenar desempenho de um individuo, assim não precisa
	 * executa-lo novamente para obter sua cobertura.	 
	 * @throws IOException */
	public void toRepositorio(int nro, String desempenho) throws IOException {
		String saida = "";
		
		if (nro >= Central.tamanhoPopulacao)
			Diversos.erro("extrapolou tamanho da populacao", 1);

		objIndividuo.load(nro);
						
		saida += String.format("%s : %s", objIndividuo.genes, desempenho);
		Diversos.escreverArquivo(Central.arquivoRepositorio, saida);
				
	}

	/** Metodo usado para sobrepor duas linhas de coberturas.	 */	
	public void sobrepoe(String cobertura, String desempenho) {
		int i = 0;
		String coberturaLocal = null;
		String mostrar = null;
		coberturaLocal = cobertura;
		
		if (coberturaLocal.equals(null))
			coberturaLocal = desempenho;
		else {
			int tam = cobertura.length();

			for (i = 0; i < tam; i++)
				if (coberturaLocal.charAt(i) == '-') {
					mostrar = coberturaLocal.replace(coberturaLocal.charAt(i),
							desempenho.charAt(i));
				}
		}
		// fim else
		coberturaLocal = mostrar;
		cobertura = coberturaLocal;
		
		}// fim sobrepoe

	/** Metodo que evolui a populacao de individuos. 
	 * @throws IOException */
	public void evoluiPopulacao() throws IOException {
		
		if (Central.quantidadeFitness > 0)
			evolucaoPorFitness();

		if (Central.quantidadeElitismo > 0)
			evolucaoPorElitismo();

		if (Central.quantidadeIneditismo > 0)
			evolucaoPorIneditismo();

		/*
		 * Para recuperar individuos que foram perdidos da geracao passada e nao
		 * poderiam ser pois era um individuo que cobria um elemento nao coberto
		 * na nova populacao, deve-se manter uma copia da populacao anterior.
		 * Essa esta sendo guardada em ctl->arquivoPopulacaoTemporario...
		 */
		
		//copia de Central.arquivoPopulacao para "popManejo.pop"
		Diversos.copyFile(Central.arquivoPopulacao.getPath(), "popManejo.pop");
		
		
		//move de "Central.arquivoPopulacaoTemporario" para "Central.arquivoPopulacao" 
		Diversos.copyFile(Central.arquivoPopulacaoTemporario.getPath(),
				Central.arquivoPopulacao);
						
		// move de "popManejo.pop" para Central.arquivoPopulacaoTemporario
		Diversos.copyFile("popManejo.pop",Central.arquivoPopulacaoTemporario);
		
		if (Central.geraLog != 0)
			Diversos.toFile("log_erro.log", "---saindo EvoluiPopulacao");

	}

	/**Metodo usado para evoluir a populacao com base no fitness dos individuos.
	 * @throws IOException */
	public void  evolucaoPorFitness() throws IOException{
	 Diversos.toFile("log_erro.log", "------evolucaoFitness");
  		int cross = 0, mut = 0;
  		double sorteio = 0;
  		int contador = 0;

   //Apagar o arquivo evolucaio.fil caso seja a primeira evolucao da populacao, retirando assim,
  //informaces de execucoes anteriores.
  		if (Central.geracaoAtual == 0) {
  			Diversos.limpaArquivo("evolucao.fil");
  		}
  Diversos.toFile("evolucao.fil", "");
  Diversos.toFile("evolucao.fil", "##############################################################");
  String saida = null;
  Diversos.toFile("evolucao.fil", 
		  String.format("Evolucao Geracao :%d", (int) Central.geracaoAtual));
  
  Diversos.toFile("evolucao.fil", 
		  "==============================================================");
  
  saida =  String.format("EVOLUCAO POR FITNESS (%.0f) / somatoriaFitness = %.0f", (int)Central.quantidadeFitness , Central.somatoriaFitness);
  Diversos.toFile("evolucao.fil", saida);
  /**/

  //contador possui a quantidade de individuos gerados e aceitos na proxima geracao.
  //Iteracao para gerar novo individuo enquanto contador for menr que a quantidade configurada.
  for(contador=0; contador < Central.quantidadeFitness; )
    {
      sorteio =  Central.geraSorteio( Central.somatoriaFitness );

      objIndividuo.load((int)indiceIndividuoSorteado(sorteio ));
     
      Diversos.toFile("evolucao.fil", 
    		  "--------------------------------------------------------------");
      saida = String.format("1 Sorteio : %f : %s", sorteio, objIndividuo.genes);
      Diversos.toFile("evolucao.fil", saida);
      /**/

      sorteio = Central.geraSorteio( Central.somatoriaFitness );
      objIndividuo.load( (int) indiceIndividuoSorteado ( sorteio ) );
      saida = String.format("2 Sorteio : %f : %s", sorteio, objIndividuo.genes);
      Diversos.toFile("evolucao.fil", saida);
      /**/

      cross = Mersenne.genrand() % 100;
      
      if(cross <= Central.taxaCrossover * 100){
        crossover( (objIndividuo.genes), (objIndividuo.genes) );   
        saida = String.format("  op CROSSOVER : %s / %s", 
        		objIndividuo.genes, objIndividuo.genes);
        
        Diversos.toFile("evolucao.fil", saida);    
        }
      
      mut   = Mersenne.genrand() % 100;
      
      if(mut <= Central.taxaMutacao * 100){
        mutacao(objIndividuo.genes);
        saida = String.format(" op MUTACAO 1  : %s", objIndividuo.genes);
        Diversos.toFile("evolucao.fil", saida);   
        }
      
      if( toPopulacao( contador, objIndividuo.genes, Central.arquivoPopulacaoTemporario ) ){
        contador ++;
        saida = String.format(" ** %d INDIVIDUO   : %s", contador, objIndividuo.genes);
        Diversos.toFile("evolucao.fil", saida);        
        }
      
      if(contador < Central.quantidadeFitness ){
    	  mut   = Mersenne.genrand() % 100;
        if(mut <= Central.taxaMutacao * 100){
          mutacao(objIndividuo.genes);
          /*prova*/
          saida = String.format("    op MUTACAO 2  : %s", objIndividuo.genes);
          Diversos.toFile("evolucao.fil", saida);
          /**/
          }
        if( toPopulacao( contador, objIndividuo.genes, Central.arquivoPopulacaoTemporario ) ){
          contador ++;
          /*prova*/
          saida = String.format( " ** %d INDIVIDUO   : %s", contador, objIndividuo.genes);
          Diversos.toFile("evolucao.fil", saida);
          /**/
          }
        }// fim if
    } // fim for fitness
  
  if(Central.geraLog != 0) Diversos.toFile("log_erro.log", "------saindo evolucaoFitness");
  
}

	/** Metodo retorna o indice do individuo sorteado. Com base na somatoria do
	 * fitness e na ordem dos individuos, simulando o metodo de selecao da
	 * roleta. Retorna qual o individuo possui esta na faixa sorteada.
	 * @throws IOException */
	@SuppressWarnings("unused")
	public int indiceIndividuoSorteado(double sorteio) throws IOException{
		double superior = 0;
		String saida = null;
		//int tamLinha = nroEspacos( (int) Central.tamanhoPopulacao ) + 30;
		String linha = null;
		String [] quebra = null;
		FileReader fr = new FileReader(Central.arquivoFitness);
		BufferedReader br = new BufferedReader(fr);
				
		if(br == null){
			 saida = String.format("nao abriu o arquivo de Variacao de fitness : %s", Central.arquivoVariacaoFitness);
			Diversos.erro(saida, 1);
		}
		
		linha = br.readLine();
		for (int i=0; ( i < Central.tamanhoPopulacao) ; i++)	{
			quebra = linha.split(":");
			linha = quebra [0].trim();
     		superior += Double.parseDouble(linha);
     			if(sorteio <= superior){
     			br.close();        
     			return i;
     		} // fim if
		}// fim for
		
		br.close(); 
		return -1;
	}

	/** Metodo usado para aplicar mutacao em um individuo. */
	public void mutacao(String indiv) {
		int i = 0, mudou = 0, posMut = (int)(Math.random() * indiv.length());
		for (; mudou == 0;) {
			switch (indiv.charAt(posMut)) {
			case '+':
				indiv = indiv.replace(indiv.charAt(posMut), '-');
				mudou = 1;
				break;
			case '-':
				indiv = indiv.replace(indiv.charAt(posMut), '+');
				mudou = 1;
				break;
			// case '#': posMut = rand() % strlen( indiv ); break; //escolhe
			// outra posicao
			case ((char) 216):
				posMut = (int)(Math.random()* indiv.length());
				break; // escolhe outra posicao
			default: {
				int pos = 0;
				mudou = 1;
				for (i = 1; posMut >= Central.inicioTipo(i); i++)
					;
				if ((Central.formatoIndividuo.charAt(i - 1) == 'C')
						|| (Central.formatoIndividuo.charAt(i - 1) == 'S')) {
					indiv = indiv.replace(indiv.charAt(posMut), Diversos.generateChar(Central.tipoString));
				} // fim if
				else {
					indiv = indiv.replace(indiv.charAt(posMut), Diversos.generateChar("n"));
				} // fim else
			}// fim default
			}// fim switch
		}// fim for
		
	}

	/**
	 * Metodo que aplica operador crossover nos individuos representados por
	 * indiv1 e indiv2
	 */
public void crossover( String  indiv1, String  indiv2 ){
  int i=0, ocorre=0, posCross=0, tamTipo=0, inicTipo=0, tamFormat = 0;
  String block = null,  indivAux = null;

  
   indivAux = indiv1;

  tamFormat =  Central.formatoIndividuo.length();
  for(i=0; i<tamFormat; i++)
    {
      ocorre = (int)(Math.random() * 2);
      if(ocorre != 0){    // ocorre crossover no tipo chance de 50%
        inicTipo = Central.inicioTipo(i);
        tamTipo =  Central.tamanhoTipo(i);
        posCross = ((int)(Math.random() * tamTipo));
        switch( Central.formatoIndividuo.charAt(i))
          {
          case 'S':
            {
            
              int maxTam=0, maxTam2=0;
               block = indiv1 + inicTipo;
              //maxTam = Diversos.indexOf(block, '#');
              maxTam = Diversos.indexOf(block, (char) 216);
              if(maxTam == -1)
                maxTam = tamTipo;
             block = indiv2 + inicTipo;
              //maxTam2 = Diversos.indexOf(block, '#');
              maxTam2 = Diversos.indexOf(block, (char) 216);
              if(maxTam2 == -1)
                maxTam2 = tamTipo;
              if(maxTam > maxTam2)
                maxTam = maxTam2;
              if(maxTam == 0)
                posCross = 0;
              else
                posCross = ((int)(Math.random() * maxTam));
              break;
            }// case
          case 'C':
            {
              posCross = 0;
              break;
            }// case
          }// fim switch
       indiv1 = indiv2+(posCross+inicTipo);
       indiv2 =  indivAux+(posCross+inicTipo);
      }// fim if
    }// fim for
  //  rastro("saiu -  crossover");
 
 
}

	/** Metodo usado para gerar o fitness da populacao 
	 * @throws IOException */
	public  void  geraFitness() throws IOException{
		
		String saida = null;
		if(Central.geraLog != 0) Diversos.toFile("log_erro.log", "------geraFitness");

		String linhaCobertura = null,desempenho = null;
		double fitness = 0, somatoriaFitness = 0, cobertura=0;
				 
		if(Central.arquivoCoberturaIndividuo == null) {
      	saida = String.format("nao abriu o arquivo de cobertura/individuo : %s", Central.arquivoCoberturaIndividuo );
      	Diversos.erro(saida, 1 );
		}

		for (int i=0; i < Central.tamanhoPopulacao; i++){
			desempenho = desempenho + Diversos.indexOf(desempenho,':');
			desempenho = desempenho.trim();
			sobrepoe(linhaCobertura, desempenho);

			fitness = (double)Diversos.numberOf(desempenho, 'X') * 100 / (double) (Central.quantidadeElemento);
			saida = String.format("%5d : %10.2f\n", i, fitness);
			Diversos.escreverArquivo(Central.arquivoFitness, saida);
    
		}// fim for
		//rastro("PONTO1.7");*/
 
		Central.setSomatoriaFitness(somatoriaFitness );
		Diversos.toFile( Central.arquivoObsCobertura.getPath(), linhaCobertura); 
		Diversos.toFile("log_erro.log", "------saindo geraFitness");
}

	/** Metodo usado para realizar evolucao por elitismo. 
	 * @throws IOException */
	public void evolucaoPorElitismo() throws IOException{
		 Diversos.toFile("log_erro.log", "------evolucaoIneditismo");
		 int contador = 0;
		 double posicao = 0;
		 String saida = null;

		 System.out.printf("EVOLUCAO POR INEDITISMO (%.0f)", Central.quantidadeIneditismo);
		 
		 for(contador = (int)( Central.quantidadeFitness); contador < (int) ( Central.quantidadeFitness + Central.quantidadeElitismo  ); )  {
			 posicao = melhorFitAntNaoEm( Central.arquivoPopulacaoTemporario);
			 if (posicao != -1){
				 objIndividuo.load( (int) posicao );        
				 System.out.printf(" Ineditismo do indiv pos %0.0f : %s", posicao, objIndividuo.genes);
			 }
			 	else{
			 		objIndividuo.individuo();
			 		System.out.printf("Ineditismo do novo indiv : %s", objIndividuo.genes);
			 	}
			 
			 if ( toPopulacao( contador , objIndividuo.genes, Central.arquivoPopulacaoTemporario ) )	{
				 contador ++;	  
				 System.out.printf(" ** %d INDIVIDUO   : %s", contador, objIndividuo.genes);
	  
			 }
		 } // fim for ineditismo
		 Diversos.toFile("log_erro.log", "------saindo evolucaoIneditismo");
  }

	/** Metodo usado para obter o indice do individuo a ser mantido 
	 * @throws IOException 
	 * @throws NumberFormatException */
	public double melhorInedAntNaoEm ( File arquivoAuxiliar ) throws NumberFormatException, IOException{
		String saida = null;
		String linha = null;
		FileReader fr = new FileReader(Central.arquivoIneditismo);
		BufferedReader br = new BufferedReader(fr);
		
		if(br == null){
			saida = String.format("nao abriu o arquivo de Ineditismo corretamente : %s", Central.arquivoIneditismo);
			Diversos.erro(saida, 1);
		}
		
		int pos2p = 0;
		double posMelhor = -1, pos = 0, ineditismo = 0, ineditismoMelhor = -1;
		int tamLinha = 100;
		
		linha = br.readLine();
		for( int cont = 0; cont < Central.tamanhoPopulacao; cont ++ ){
			pos2p = Diversos.indexOf(linha, ':');
			if(pos2p != -1){
				linha = linha + pos2p + 1;
				linha = linha.trim();
				ineditismo = Double.parseDouble(linha);
				objIndividuo.load(cont);
				if ( (ineditismo > ineditismoMelhor) && (inPopulacao(objIndividuo.genes, arquivoAuxiliar)) ){
					posMelhor = cont;
					ineditismoMelhor = ineditismo;
				}
			} // fim if
			linha = br.readLine();
		}// fim for
  
		br.close();
  return posMelhor;
}

	/** Metodo usado para obter o indice do individuo a ser mantido 
	 * @throws IOException */
	public double melhorFitAntNaoEm( File arquivoAuxiliar ) throws IOException{
		String saida = null;
		String linha = null;
		int pos2p = 0;
		double posMelhor = -1, pos = 0, fit = 0, fitMelhor = -1;
		  
		FileReader fr = new FileReader(Central.arquivoFitness);
		BufferedReader br = new BufferedReader(fr);
		if(br == null){
			saida = String.format("nao abriu o arquivo de Fitness corretamente : %s", Central.arquivoFitness);
			Diversos.erro(saida, 1);
		}
		
		linha  = br.readLine();
		for( int cont = 0; cont < Central.tamanhoPopulacao; cont ++ ){
			pos2p = Diversos.indexOf(linha, ':');
			if(pos2p != -1){
				linha = linha + pos2p + 1;
				linha = linha.trim();
				fit = Double.parseDouble(linha);
				objIndividuo.load(cont);
				if ( (fit > fitMelhor) && (!inPopulacao(objIndividuo.genes, arquivoAuxiliar) ) ){
					posMelhor = cont;
					fitMelhor = fit;
				}
			} // fim if
			linha  = br.readLine();
		}// fim for   
		br.close();
		return posMelhor;
	}

	
	/**
	 * Metodo usado para calcular a cobertura dos elementos, com base na
	 * cobertura dos individuos.
	 * @throws IOException 
	 */
	public void geraCoberturaElementos() throws IOException{
		
		if(Central.geraLog != 0){ 
			Diversos.toFile("log_erro.log", "***------geraCoberturaElementos");
		}
		
		String saida = null;
		String linhaCobInd = null,  linhaCobElem = null;
		int contador=0, cont=0, lixo=0, ponto2=0;
				
		FileReader frPtrCobElem = new FileReader(Central.arquivoCoberturaElemento);
		BufferedReader brPtrCobertura = new BufferedReader(frPtrCobElem);
		
		FileReader frPtrCobInd = new FileReader( Central.arquivoCoberturaElemento);
		BufferedReader brPtrCobInd = new BufferedReader(frPtrCobInd);
		
		if (brPtrCobInd == null){
			saida = String.format("nao abriu o arquivo de cobertura corretamente : %s", Central.arquivoCoberturaIndividuo);
			Diversos.erro(saida ,1);
		}

		linhaCobInd = brPtrCobertura.readLine();
		for (contador = 0; contador < Central.quantidadeElemento; contador++){
			
			linhaCobElem = brPtrCobInd.readLine();
			for (cont = 0; cont < (int) (Central.tamanhoPopulacao); cont++){
				ponto2 = Diversos.indexOf(linhaCobInd,':');
				linhaCobInd = linhaCobInd + ponto2 + 1;
				
				if( linhaCobInd.length() == Central.quantidadeElemento ){
					linhaCobInd = linhaCobInd.trim();
					linhaCobElem = linhaCobElem.replace(linhaCobElem.charAt(cont), linhaCobInd.charAt(contador));
				}
				linhaCobInd = brPtrCobertura.readLine();
			}// fim for
    
			if(linhaCobElem.length() != Central.tamanhoPopulacao ){
				saida = String.format(" geraCoberturaElementos - Cobertura do elemento gerada errada para o elm %d - ", contador);
				Diversos.erro(saida, 1);
			}
			saida = String.format("%5d : %s\n", contador, linhaCobElem );
			Diversos.escreverArquivo(Central.arquivoCoberturaElemento, saida);
		
			linhaCobInd = brPtrCobertura.readLine();
		}// fim for
		
		brPtrCobInd.close();
		brPtrCobertura.close();
		
		if(Central.geraLog != 0){
			Diversos.toFile("log_erro.log", "***------saindo geraCoberturaElementos");
		}
  
}

	
	/**
	 * Metodo usado para gerar um arquivo que reflita os dados de uma populacao
	 * de forma entendivel/decodificada.
	 * @throws IOException 
	 */
	public void  decodificaPopulacao(File  origem, String  destino) throws IOException{
   
		
		String    block = "",  block_aux = "",  gene = "", saida = null;
		String linhaPtrPopulacao = null;
		int  tamFormato = Central.formatoIndividuo.length();
		int  inicBlock,  pos;
		
		int geneTam = 0, tamBloco = 30;
		geneTam = (int) ( Central.tamanhoIndividuo + 20 );

		if(Central.tamanhoMaximoString > tamBloco){
			tamBloco = (int) Central.tamanhoMaximoString;
		}
		
		FileReader frPtrPopulacao = new FileReader(origem);
		BufferedReader brPtrPopulacao = new BufferedReader(frPtrPopulacao);
		  
		if (brPtrPopulacao == null){
			saida =  String.format("nao abriu o arquivo de populacao corretamente : %s", origem);
			Diversos.erro(saida,1);
		}		
   		  		
  		linhaPtrPopulacao = brPtrPopulacao.readLine();
  		for (int contador = 1; brPtrPopulacao!= null; contador++){
  			gene = linhaPtrPopulacao.trim();
  			if( gene != ""){
  				gene = (gene + Diversos.indexOf(gene, ':') + 1).trim(); //errado
  				for (int i = 0; i < tamFormato ; i++){
  					inicBlock = Central.inicioTipo(i);
  					block = gene + inicBlock;
  					switch( Central.formatoIndividuo.charAt(i)){
  						case 'I': block_aux = String.format("%d", objIndividuo.decode_block_int(block) ); break;
  					}// fim switch
  					Diversos.escreverArquivo(destino,  block_aux);		
  					if( i != tamFormato - 1 ){
  						Diversos.escreverArquivo(destino,  ", ");
  					}
  				}//fim for  		
  			} // fim if gene == ""
  			linhaPtrPopulacao = brPtrPopulacao.readLine();
  		} // fim for
  
  		brPtrPopulacao.close();
  		if(Central.geraLog != 0){
  			Diversos.toFile("log_erro.log", "---decodificaPopulacao");  
  		}
	}


	
	
}
