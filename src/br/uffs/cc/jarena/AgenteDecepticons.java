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
	private int xEnergia = -1;
	private int yEnergia = -1;

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

		if (!comandante && !recebendoEnergia) {
			if (xEnergia != -1 && yEnergia != -1) {
				moverPara(xEnergia, yEnergia);
				if(!podeMoverPara(getDirecao()) && !isParado()) {
					switch (getDirecao()) {
						case Agente.DIREITA:
							setDirecao(Agente.CIMA);
						break;

						case Agente.CIMA:
							setDirecao(Agente.DIREITA);
						break;

						case Agente.ESQUERDA:
							setDirecao(Agente.BAIXO);
						break;

						case Agente.BAIXO:
							setDirecao(Agente.ESQUERDA);
						break;
					
						default:
							setDirecao(geraDirecaoAleatoria());
						break;
					}
				}
			}

			if(!podeMoverPara(getDirecao()) && !isParado()) {
				setDirecao(geraDirecaoAleatoria());
			}

			if (!recebendoEnergia && isParado()) {
				moverPara(Constants.LARGURA_TELA / 2, Constants.ALTURA_TELA / 2);
			}
			
		} else {
			para();
		}

		recebendoEnergia = false;
		
		// Se o agente conseguie se dividir (tem energia) e se o total de energia
		// do agente é maior que 400, nos dividimos. O agente filho terá a metade
		// da nossa energia atual.
		
	}
	
	public void recebeuEnergia() {
		// Invocado sempre que o agente recebe energia.
		para();
		setDirecao(NENHUMA_DIRECAO);
		recebendoEnergia = true;
		enviaMensagem(getId() + " [ENERGIA] posição: " + getX() + "," + getY());
	}
	
	public void tomouDano(int energiaRestanteInimigo) {
		// Invocado quando o agente está na mesma posição que um agente inimigo
		// e eles estão batalhando (ambos tomam dano).
		if(podeDividir() && !comandante) {
			divide();
			para();
		}

		enviaMensagem(getId() + " [INIMIGO] posição: " + getX() + "," + getY());

		if (getEnergia() <= (Constants.ENTIDADE_COMBATE_DANO * 2)
			+ (getDirecao() == Agente.NENHUMA_DIRECAO ? Constants.ENTIDADE_ENERGIA_GASTO_VIVER : (Constants.ENTIDADE_ENERGIA_GASTO_ANDAR + Constants.ENTIDADE_ENERGIA_GASTO_VIVER))) {
			contador--;
			morre();
		}
	}
	
	public void ganhouCombate() {
		// Invocado se estamos batalhando e nosso inimigo morreu.
	}
	
	public void recebeuMensagem(String msg) {
		if (msg.contains("[ENERGIA]") && !recebendoEnergia && !comandante) {
			String[] posicao = msg.split(":")[1].split(",");
			int x = Integer.parseInt(posicao[0].trim());
			int y = Integer.parseInt(posicao[1].trim());
			xEnergia = x;
			yEnergia = y;
		}
	}
	
	public String getEquipe() {
		// Definimos que o nome da equipe do agente é "Decepticons".
		return "Decepticons";
	}

	private void moverPara(int x, int y) {
		int diferencaX = x - getX();
		int diferencaY = y - getY();
		int direcao;
		if (diferencaX == 0 && diferencaY == 0) {
			xEnergia = -1;
			yEnergia = -1;
			para();
			return;
		}

		boolean priorizarDirecaoX = getId() % 2 == 0 ? true : false;

		if (diferencaX == 0) {
        direcao = diferencaY > 0 ? BAIXO : CIMA;
    } else if (diferencaY == 0) {
        direcao = diferencaX > 0 ? DIREITA : ESQUERDA;
    } else if (priorizarDirecaoX) {
        direcao = diferencaX > 0 ? DIREITA : ESQUERDA;
    } else {
        direcao = diferencaY > 0 ? BAIXO : CIMA;
    }

		if (podeMoverPara(direcao)) setDirecao(direcao);
	}
}
