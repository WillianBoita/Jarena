/**
 * Um exemplo de agente que anda aleatoriamente na arena. Esse agente pode ser usado como base
 * para a criação de um agente mais esperto. Para mais informações sobre métodos que podem
 * ser utilizados, veja a classe Agente.java.
 * 
 * Fernando Bevilacqua <fernando.bevilacqua@uffs.edu.br>
 */

/*
	Dupla: Willian Kronhardt Boita e Nicolas Kronhardt Boita

*/

package br.uffs.cc.jarena;

public class AgenteDecepticons extends Agente
{

	private boolean recebendoEnergia;

	public AgenteDecepticons(Integer x, Integer y, Integer energia) {
		super(x, y, energia);
		setDirecao(geraDirecaoAleatoria());
	}
	
	public void pensa() {
		// Se não conseguimos nos mover para a direção atual, quer dizer
		// que chegamos no final do mapa ou existe algo bloqueando nosso
		// caminho.
		if(!podeMoverPara(getDirecao())) {
			// Como não conseguimos nos mover, vamos escolher uma direção
			// nova.
			setDirecao(geraDirecaoAleatoria());
		}

		if (!recebendoEnergia && isParado()) {
			setDirecao(geraDirecaoAleatoria());
		}
		recebendoEnergia = false;
		
		// Se o agente conseguie se dividir (tem energia) e se o total de energia
		// do agente é maior que 400, nos dividimos. O agente filho terá a metade
		// da nossa energia atual.
		
	}
	
	public void recebeuEnergia() {
		// Invocado sempre que o agente recebe energia.
		para();
		recebendoEnergia = true;
	}
	
	public void tomouDano(int energiaRestanteInimigo) {
		// Invocado quando o agente está na mesma posição que um agente inimigo
		// e eles estão batalhando (ambos tomam dano).
		if(podeDividir()) {
			divide();
		}

		if (this.getEnergia() <= (Constants.ENTIDADE_COMBATE_DANO * 2) + (this.getDirecao() == Agente.NENHUMA_DIRECAO ? Constants.ENTIDADE_ENERGIA_GASTO_VIVER : (Constants.ENTIDADE_ENERGIA_GASTO_ANDAR + Constants.ENTIDADE_ENERGIA_GASTO_VIVER))) {
			System.out.println(this.getId() + " morreu");
			this.morre();
		}
	}
	
	public void ganhouCombate() {
		// Invocado se estamos batalhando e nosso inimigo morreu.
	}
	
	public void recebeuMensagem(String msg) {
		// Invocado sempre que um agente aliado próximo envia uma mensagem.
	}
	
	public String getEquipe() {
		// Definimos que o nome da equipe do agente é "Decepticons".
		return "Decepticons";
	}
}
