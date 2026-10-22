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
	private static int contador = 0;
	private boolean comandante;
	private boolean recebendoEnergia;

	public AgenteDecepticons(Integer x, Integer y, Integer energia) {
		super(x, y, energia);
		comandante = (contador++ == 0) ? true : false;

		if (comandante) {
			setDirecao(NENHUMA_DIRECAO);
		} else {
			setDirecao(geraDirecaoAleatoria());
		}
	}
	
	public void pensa() {
		// Se não conseguimos nos mover para a direção atual, quer dizer
		// que chegamos no final do mapa ou existe algo bloqueando nosso
		// caminho.
		if (!comandante) {
			if(!podeMoverPara(getDirecao())) {
				// Como não conseguimos nos mover, vamos escolher uma direção
				// nova.
				setDirecao(geraDirecaoAleatoria());
			}
	
			if (!recebendoEnergia && isParado()) {
				setDirecao(geraDirecaoAleatoria());
			}
			recebendoEnergia = false;
		} else {
			para();
		}
		
		// Se o agente conseguie se dividir (tem energia) e se o total de energia
		// do agente é maior que 400, nos dividimos. O agente filho terá a metade
		// da nossa energia atual.
		
	}
	
	public void recebeuEnergia() {
		// Invocado sempre que o agente recebe energia.
		para();
		recebendoEnergia = true;
		enviaMensagem(getEquipe() + " [ENERGIA] posição: " + getX() + "," + getY());
	}
	
	public void tomouDano(int energiaRestanteInimigo) {
		// Invocado quando o agente está na mesma posição que um agente inimigo
		// e eles estão batalhando (ambos tomam dano).
		if(podeDividir() && !comandante) {
			divide();
			para();
		}

		enviaMensagem(getEquipe() + " [INIMIGO] posição: " + getX() + "," + getY());

		if (this.getEnergia() <= (Constants.ENTIDADE_COMBATE_DANO * 2) + (this.getDirecao() == Agente.NENHUMA_DIRECAO ? Constants.ENTIDADE_ENERGIA_GASTO_VIVER : (Constants.ENTIDADE_ENERGIA_GASTO_ANDAR + Constants.ENTIDADE_ENERGIA_GASTO_VIVER))) {
			this.morre();
		}
	}
	
	public void ganhouCombate() {
		// Invocado se estamos batalhando e nosso inimigo morreu.
	}
	
	public void recebeuMensagem(String msg) {
		if (msg.contains("[ENERGIA]")) {
			String posicao = msg.split(":")[1];
			String x = posicao.split(",")[0];
			String y = posicao.split(",")[1];
		}
	}
	
	public String getEquipe() {
		// Definimos que o nome da equipe do agente é "Decepticons".
		return "Decepticons";
	}
}
