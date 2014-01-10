package ferramenta;

import java.io.IOException;

/**
 * Nessa classe ainda funcionar corretamente dois m�todos: exeVali_inst( ) e
 * exeVali_cc( ) o metodo vali_reduce n�o foi implementado
 * 
 * @throws IOException
 */
public class ValiMPI {
	/**
	 * Metodo utilizado para executar a instrumentacao de um codigo pela ValiMPI
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void exeVali_inst(String nomeArqFonte) throws IOException,
			InterruptedException {
		String cmd[] = {
				"gnome-terminal",
				"-x",
				"bash",
				"-c",
				"/home/labs/Software/ValiMPI_mmr/vali_inst/vali_inst "
						+ nomeArqFonte + ";  echo '<enter>'; read" };

		Process proc = Runtime.getRuntime().exec(cmd, null, null);
		proc.waitFor();
	}

	/**
	 * metod apra executar a vali reduce
	 * 
	 * @throws IOException
	 */
	public static void exeVali_reduce(String nomeArqFonte) {

	}

	/**
	 * metodo usado para executar a vali elem
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void exeVali_elem(String nProcess, String nomefuncoes)
			throws IOException, InterruptedException {
		String cmd[] = {
				"gnome-terminal",
				"-x",
				"bash",
				"-c",
				"/home/labs/Software/ValiMPI_mmr/vali_elem/vali_elem " + nProcess
						+ " " + nomefuncoes + "; exit echo '<enter>'; read" };

		Process proc = Runtime.getRuntime().exec(cmd, null, null);
		proc.waitFor();
	}

	/**
	 * metodos usado para executar o executavel a partir do arquivo
	 * instrumentado
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void exeVali_cc(String nomePrograma) throws IOException,
			InterruptedException {
		String cmd[] = {
				"gnome-terminal",
				"-x",
				"bash",
				"-c",
				"/home/labs/Software/ValiMPI_mmr/vali_exec/vali_cc -o exe"
						+ nomePrograma
						+ ".c_instrumentado.c " + ";  echo '<enter>'; read" };

		Process proc = Runtime.getRuntime().exec(cmd, null, null);
		proc.waitFor();
	}

	/**
	 * metodo usado para executar um dado de teste na ferramenta
	 * @return 
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static String [] exeVali_exec(String ndadoTeste, String nProcessos,
			 String dadoTeste) throws IOException,
			InterruptedException {

		String cmd[] = {
				"gnome-terminal",
				"-x",
				"bash",
				"-c",
				"/home/labs/Software/ValiMPI_mmr/vali_exec/vali_exec " + ndadoTeste
						+ " run " + nProcessos + " exe " +  " \""
						+ dadoTeste + "\"" + "; exit echo '<enter>'; read" };
		
		return cmd;
	}

	/**
	 * metodo usado para obter a cobertura do dado de teste
	 * @return 
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static String [] exeVali_eval(String criterio, String nProcess,
			String nomefuncoes) throws IOException, InterruptedException {
		String cmd[] = {
				"gnome-terminal",
				"-x",
				"bash",
				"-c",
				"/home/labs/Software/ValiMPI_mmr/vali_eval/vali_eval " + criterio
						+ " " + nProcess + " " + nomefuncoes
						+ " > gcd/vali_eval.out; exit  echo '<enter>'; read" };

		return cmd;

	}
}
