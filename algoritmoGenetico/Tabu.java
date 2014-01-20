package algoritmoGenetico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import main.Diversos;
import ferramenta.Central;

public class Tabu {

	public static void paraTabu(String linhaCobertura) throws IOException {

		String gene = null, linhaCoberturaAux = null;
		String linha = null;
		int contx = 0;

		int tamGene = Diversos.nroEspacos((int) Central.tamanhoPopulacao)
				+ (int) Central.tamanhoIndividuo + 5;
		int tamLinhaCob = (int) Central.quantidadeElemento + 5;

		FileReader arq = new FileReader(Central.arquivoRepositorio);
		BufferedReader ptrTabu = new BufferedReader(arq);

		linha = ptrTabu.readLine();
		for (contx = 0; linha != null;) {

			gene = linha.substring(tamGene);

			gene = gene + Diversos.indexOf(gene, ':') + 1;

			if (gene == "") {
				Central.inRepositorio(gene, linhaCoberturaAux);
				if (!Diversos.cobre(linhaCobertura, linhaCoberturaAux)) {
					toFileTabu(contx++, gene, Central.arquivoTabuAux);
				} else
					System.out.println("susbstituiu");
			} // fim if strcmp
		} // fim if for

	}

	/**
	 * Método
	 * 
	 * @throws IOException
	 */
	public static void toFileTabu(int nro, String str, File arquivo)
			throws IOException {

		Populacao.toPopulacao(nro, str, arquivo);

	}

	/**
	 * Verifica se a cobertura recem gerada para o individuo identificado por
	 * nroIndividuo melhora a cobertura global. Caso sim deve entrar em tabu
	 * retirando coberturas cobertas.
	 * 
	 * @throws IOException
	 */
	public static void manutencaoTabu(String coberturaIndividuo,
			int nroIndividuo) throws IOException {
		if (melhora(coberturaIndividuo)) {
			Central.atualizaCoberturaGlobal(coberturaIndividuo);
			// guarda no repositorio.
			atualizaTabu(coberturaIndividuo, nroIndividuo);

		}

	}

	/**
	 * Verifica se a cobertura atual melhora a cobertura global mantida pela
	 * referencia ao objeto central.
	 */
	public static boolean melhora(String coberturaAtual) {
		int i = 0, tam = (int) Central.quantidadeElemento;

		char[] coberturaAtualLocal = coberturaAtual.toCharArray();
		char[] coberturaGlobalLocal = Central.coberturaGlobal.toCharArray();

		for (i = 0; i < tam; i++){
			if ((coberturaAtualLocal[i] == 'X')
					&& (coberturaGlobalLocal[i] == '-'))
				return true;
		}
		
		return false;
	}

	// Nao compensa este metodo separado apenas para carregar de tabu e nao
	// avaliar ja.
	public static void atualizaTabu(String coberturaIndividuo, int pos)
			throws IOException {

		String gene = null, geneAux = null;
		String coberturaIndTabu = null;
		int pos2 = 0, contador = 0;
		int tamGene = Diversos.nroEspacos((int) Central.tamanhoPopulacao)
				+ (int) Central.tamanhoIndividuo + 5;

		// sprintf( Comando, TDS_PATH"removeFile.sh %s -rf",
		// Central.arquivoTabuAux );
		// Central.setComandPath(Comando);
		// system ( Central.comandPath );

		String indiv = Populacao.individuos[pos].getGenes();
		String linha = null;
		FileReader arq = new FileReader(Central.arquivoTabu);
		BufferedReader ptrTabu = new BufferedReader(arq);

		linha = ptrTabu.readLine();
		for (contador = 0; linha != null;) {

			gene = linha.substring(tamGene);
			pos2 = Diversos.indexOf(gene, ':') + 1;
			geneAux = gene + pos2;

			if (geneAux == "")
				continue;

			// Recuperando cobertura dos individuos da lista tabu e armazenando
			// em coberturaindTabu
			// if ( Central.inRepositorio(geneAux, coberturaIndTabu ) == false )
			// erro(" atualizaTabu - Nao recuperou o desempenho do Individuo, Executa de novo ou força armazenar no repositorio...",1);
			// //Se cobertura em avaliação cobre cobertura de algum individuo
			// que ja esta na
			// lista tabu, este nao sera inserido em Central.arquivoTabuAux
			if (!Diversos.cobre(coberturaIndividuo, coberturaIndTabu))
				toFileTabu(contador++, geneAux, Central.arquivoTabuAux);
		}// fim for

		toFileTabu(contador++, indiv, Central.arquivoTabuAux);

		Diversos.copyFile(Central.arquivoTabuAux.getPath(),
				Central.arquivoTabu.getPath());

	}

}
