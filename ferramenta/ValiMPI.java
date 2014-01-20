package ferramenta;

/**
 *Instruções para usar a VALIMPI junto com esta classe java.
 *Para todos os métodos é necessário colocar o caminho onde 
 *se encontra a ferramenta no sistema. Por exemplo, para o método
 *que usa a vali_inst: "/home/labs/Software/ValiMPI_mmr_tds/vali_inst/vali_inst ".
 *Somente o módulo vali_inst precisa de modificação direta no arquivo.
 *. No arquivo vali_inst dentro da pasta vali_inst é necessário 
 *colocar corretamente o caminho da VALIMPI (nessa versão nas linhas 23, 27, 32, 42 e 47).
 *Ainda nessa mesma pasta fazer o mesmo nos arquivos def_uses (nessa versão linha 5)
 * e strip_macros (nessa versão linha 5).
 */


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
				"/home/labs/Software/ValiMPI_mmr_tds/vali_inst/vali_inst "
						+ nomeArqFonte + ">> valimpi.log; exit echo '<enter>'; read" };

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
				"/home/labs/Software/ValiMPI_mmr_tds/vali_elem/vali_elem " + nProcess
						+ " " + nomefuncoes + ">> valimpi.log; exit echo '<enter>'; read" };

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
				"/home/labs/Software/ValiMPI_mmr_tds/vali_exec/vali_cc -o exe "
						+ nomePrograma
						+ ".c_instrumentado.c " + ">> valimpi.log; exit  echo '<enter>'; read" };

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
				"/home/labs/Software/ValiMPI_mmr_tds/vali_exec/vali_exec " + ndadoTeste
						+ " run " + nProcessos + " exe " +  " \""
						+ dadoTeste + "\"" + ">> valimpi.log; exit echo '<enter>'; read" };
		
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
				"/home/labs/Software/ValiMPI_mmr_tds/vali_eval/vali_eval " + criterio
						+ " " + nProcess + " " + nomefuncoes
						+ " > " + Central.diretorio.getPath()+"/vali_eval.out; exit echo '<enter>'; read" };

		return cmd;

	}
}
