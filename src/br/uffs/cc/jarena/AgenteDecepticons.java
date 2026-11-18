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

/*
	Se o objeto a estiver à direita: a diferença é 49 pixels.
	Se o objeto a estiver à esquerda: a diferença é -31 pixels.
	Se o objeto a estiver acima: a diferença é -54 pixels.
	Se o objeto a estiver abaixo: a diferença é 26 pixels.
*/
 
package br.uffs.cc.jarena;

import java.util.ArrayList;
import java.util.Arrays;
 
public class AgenteDecepticons extends Agente
{
  // não pode variável static :(
  // reescrever toda a lógica do comandante e dos soldados, encontrar uma maneira de descobrir o id de cada um.
  // tentar tirar vantagem do spawn onde todos estão juntos, cada um manda um id.
  // ideia, criar vetor de ids, e povoar com mensagens recebidas de cada agente.

	private int LIMITE_LARGURA_DIREITA = (int)(Constants.LARGURA_TELA * 0.95) - 45;
	private int LIMITE_LARGURA_ESQUERDA = 27;
	private int LIMITE_ALTURA_CIMA = 50;
	private int LIMITE_ALTURA_BAIXO = (int)(Constants.ALTURA_TELA * 0.95) - 22;

	private int agenteId;
  private ArrayList<String> agenteIds = new ArrayList<>();
	private boolean posicionou = false;
  private boolean sinalizouId = false;
  private boolean sinalizado = false;

  private boolean comandante;
	private boolean soldadoBorda;
  private boolean recebendoEnergia = false;
  private boolean recebeuSinalEnergia = false;
  private boolean emPosicao = false;
  private boolean fugindo = false;
  private int xEnergia = -1;
  private int yEnergia = -1;
  private int contadorCombate = 0;
 
  public AgenteDecepticons(Integer x, Integer y, Integer energia) {
    super(x, y, energia);
 
	 	if (getX() == 0 && getY() == 0) {
			xEnergia = LIMITE_LARGURA_ESQUERDA;
			yEnergia = LIMITE_ALTURA_CIMA;
			
		} else if (getX() == (int)(Constants.LARGURA_TELA * 0.95) && getY() == 0) {
			xEnergia = LIMITE_LARGURA_DIREITA;
			yEnergia = LIMITE_ALTURA_CIMA;
			
		} else if (getX() == 0 && getY() == (int)(Constants.ALTURA_TELA * 0.95)) {
			xEnergia = LIMITE_LARGURA_ESQUERDA;
			yEnergia = LIMITE_ALTURA_BAIXO;
			
		} else if (getX() == (int)(Constants.LARGURA_TELA * 0.95) && getY() == (int)(Constants.ALTURA_TELA * 0.95)) {
			xEnergia = LIMITE_LARGURA_DIREITA;
			yEnergia = LIMITE_ALTURA_BAIXO;
			
		} else {
			xEnergia = (LIMITE_LARGURA_ESQUERDA + LIMITE_LARGURA_DIREITA) / 2;
			yEnergia = (LIMITE_ALTURA_BAIXO + LIMITE_ALTURA_CIMA) / 2;
		}
  }
  
  public void pensa() {

    if (!sinalizouId) {
      enviaMensagem("[ID] -" + getId());
      sinalizouId = true;
    }

    if (sinalizouId && !sinalizado) {
      System.out.println("LISTA: " + agenteIds);
      int indice = agenteIds.indexOf(String.valueOf(getId()));

      if (indice == 0) {
          comandante = true;
      } else if (indice > 0 && indice < 5) {
          soldadoBorda = true;
      }
      
      sinalizado = true;
    }
 
    if(posicionou) {
      if (contadorCombate <= 1 && comandante && !fugindo) {
        para();
      }

      if (fugindo && comandante) {
      System.out.println("comandante tentou fugir");
      if (Math.random() > 0.5) {
        moverPara(getX(), getY() + Constants.ENTIDADE_VELOCIDADE);
      } else {
        moverPara(getX() + Constants.ENTIDADE_VELOCIDADE, getY());
      }
    } else {
      setDirecao(NENHUMA_DIRECAO);
      para();
    }
  
      if (!recebendoEnergia) {
        if (xEnergia != -1 && yEnergia != -1) {
          moverPara(xEnergia, yEnergia);
        }
				
        if (!comandante && !recebendoEnergia && !recebeuSinalEnergia) {
					
					if(!podeMoverPara(getDirecao()) && !isParado()) {
						setDirecao(geraDirecaoAleatoria());
					}

					if (soldadoBorda && !podeMoverPara(getDirecao())) {
						setDirecao(geraDirecaoAleatoria());
					}

          if (!estaNoMeio() && !emPosicao && !soldadoBorda) {
						moverPara((LIMITE_LARGURA_ESQUERDA + LIMITE_LARGURA_DIREITA) / 2, (LIMITE_ALTURA_BAIXO + LIMITE_ALTURA_CIMA) / 2);
					} else if (getDirecao() == NENHUMA_DIRECAO) {
            setDirecao(geraDirecaoAleatoria());
          }

					if (getX() > LIMITE_LARGURA_DIREITA) {
						if (Math.random() > 0.5) {
							setDirecao(ESQUERDA);
						} else {
							setDirecao(BAIXO);
						}

					} else if (getX() < LIMITE_LARGURA_ESQUERDA) {
						if (Math.random() > 0.5) {
							setDirecao(DIREITA);
						} else {
							setDirecao(CIMA);
						}

					} if (getY() < LIMITE_ALTURA_CIMA) {
						if (Math.random() > 0.5) {
							setDirecao(BAIXO);
						} else {
							setDirecao(DIREITA);
						}

					} else if (getY() > LIMITE_ALTURA_BAIXO) {
						if (Math.random() > 0.5) {
							setDirecao(CIMA);
						} else {
							setDirecao(ESQUERDA);
						}
					}

				}
      }
    } else {
			moverPara(xEnergia, yEnergia);
		}
 
    recebeuSinalEnergia = false;
    recebendoEnergia = false;
    fugindo = false;
  }
  
  public void recebeuEnergia() {
    // Invocado sempre que o agente recebe energia.
    para();
    setDirecao(NENHUMA_DIRECAO);
    recebendoEnergia = true;
    emPosicao = false;
    enviaMensagem(getId() + " [ENERGIA] posição: " + getX() + "," + getY());
		if (!comandante && podeDividir() && getEnergia() > 600) {
			divide();
		}
  }
  
  public void tomouDano(int energiaRestanteInimigo) {
    // Invocado quando o agente está na mesma posição que um agente inimigo
    // e eles estão batalhando (ambos tomam dano).
    
    if (posicionou) {
      contadorCombate++;
      if(comandante && contadorCombate > 1) {
        contadorCombate = 0;
        fugindo = true;
      }
   
      if (!comandante) {
        para();
        setDirecao(NENHUMA_DIRECAO);
        if (podeDividir() && contadorCombate > 1) {
          divide();
        }
      }
   
      enviaMensagem(getId() + " [INIMIGO] posição: " + getX() + "," + getY());
    }

 
    if (getEnergia() <= (Constants.ENTIDADE_COMBATE_DANO * 2)
      + (getDirecao() == Agente.NENHUMA_DIRECAO ? Constants.ENTIDADE_ENERGIA_GASTO_VIVER : (Constants.ENTIDADE_ENERGIA_GASTO_ANDAR + Constants.ENTIDADE_ENERGIA_GASTO_VIVER))) {
      morre();
    }
  }
  
  public void ganhouCombate() {
    contadorCombate = 0;
    // Invocado se estamos batalhando e nosso inimigo morreu.
  }
  
  public void recebeuMensagem(String msg) {
    if (msg.contains("[ENERGIA]") && !recebendoEnergia && !comandante) {
      String[] posicao = msg.split(":")[1].split(",");
      int x = Integer.parseInt(posicao[0].trim());
      int y = Integer.parseInt(posicao[1].trim());
      xEnergia = x;
      yEnergia = y;
      recebeuSinalEnergia = true;
    }

    if (msg.contains("[ID]")) {
      agenteIds.addAll(Arrays.asList(msg.split("-")[1]));
    }
  }
  
  public String getEquipe() {
    // Definimos que o nome da equipe do agente é "Decepticons".
    return "Decepticons";
  }
 
  private void moverPara(int x, int y) {
    int diferencaX = x - getX();
    int diferencaY = y - getY();
 
    if (Math.abs(diferencaX) <= 5 && Math.abs(diferencaY) <= 5) {
			xEnergia = -1;
			yEnergia = -1;
			setDirecao(NENHUMA_DIRECAO);
			para();
			posicionou = true;
			return;
    }
 
    boolean priorizarDirecaoX = agenteId % 2 == 0;
 
    int direcao;
 
    if (Math.abs(diferencaX) <= 5) {
      direcao = diferencaY > 0 ? BAIXO : CIMA;
    } else if (Math.abs(diferencaY) <= 5) {
      direcao = diferencaX > 0 ? DIREITA : ESQUERDA;
    } else if (priorizarDirecaoX) {
      direcao = diferencaX > 0 ? DIREITA : ESQUERDA;
    } else {
      direcao = diferencaY > 0 ? BAIXO : CIMA;
    }
 
    if (podeMoverPara(direcao)) {
			setDirecao(direcao);
    }
  }
 
  private boolean estaNoMeio() {
    int meioX = (LIMITE_LARGURA_ESQUERDA + LIMITE_LARGURA_DIREITA) / 2;
    int meioY = (LIMITE_ALTURA_BAIXO + LIMITE_ALTURA_CIMA) / 2;
    if (Math.abs(meioX - getX()) <= 5 && Math.abs(meioY - getY()) <= 5) {
			setDirecao(NENHUMA_DIRECAO);
      emPosicao = true;
      return true;
    }
 
    return false;
  }
}