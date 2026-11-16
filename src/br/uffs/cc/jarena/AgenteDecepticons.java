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
  private boolean recebendoEnergia = false;
  private boolean recebeuSinalEnergia = false;
  private boolean foiAoMeio = false;
  private int xEnergia = -1;
  private int yEnergia = -1;
  private int contadorCombate = 0;
 
  public AgenteDecepticons(Integer x, Integer y, Integer energia) {
    super(x, y, energia);
    comandante = (contador++ == 0) ? true : false;
 
    if (comandante) {
      setDirecao(NENHUMA_DIRECAO);
      para();
    } else {
      moverPara((int)(Constants.LARGURA_TELA * 0.95) / 2, (int)(Constants.ALTURA_TELA * 0.95) / 2);
    }
  }
  
  public void pensa() {
 
    if (contador < 2) {
      setDirecao(NENHUMA_DIRECAO);
      para();
    } else {
      if (contadorCombate <= 1 && comandante) {
        para();
      }
  
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
 
        if (!recebendoEnergia && !recebeuSinalEnergia) {
          if (!estaNoMeio() && !foiAoMeio) {
            moverPara((int)(Constants.LARGURA_TELA * 0.95) / 2, (int)(Constants.ALTURA_TELA * 0.95) / 2);
          } else if (getDirecao() == NENHUMA_DIRECAO) {
            setDirecao(geraDirecaoAleatoria());
          }
        }
      }
    }
 
    recebeuSinalEnergia = false;
    recebendoEnergia = false;
    contadorCombate = 0;
  }
  
  public void recebeuEnergia() {
    // Invocado sempre que o agente recebe energia.
    para();
    setDirecao(NENHUMA_DIRECAO);
    recebendoEnergia = true;
    foiAoMeio = false;
    enviaMensagem(getId() + " [ENERGIA] posição: " + getX() + "," + getY());
  }
  
  public void tomouDano(int energiaRestanteInimigo) {
    // Invocado quando o agente está na mesma posição que um agente inimigo
    // e eles estão batalhando (ambos tomam dano).
    contadorCombate++;
 
    if (!comandante) {
      setDirecao(geraDirecaoAleatoria());
    }
 
    if(podeDividir() && !comandante) {
      divide();
      para();
    }
 
    if(comandante && contadorCombate > 1) {
      if (Math.random() > 0.5) {
        moverPara(getX(), getY() + Constants.ENTIDADE_VELOCIDADE);
      } else {
        moverPara(getX() + Constants.ENTIDADE_VELOCIDADE, getY());
      }
    }
 
    enviaMensagem(getId() + " [INIMIGO] posição: " + getX() + "," + getY());
 
    if (getEnergia() <= (Constants.ENTIDADE_COMBATE_DANO * 2)
      + (getDirecao() == Agente.NENHUMA_DIRECAO ? Constants.ENTIDADE_ENERGIA_GASTO_VIVER : (Constants.ENTIDADE_ENERGIA_GASTO_ANDAR + Constants.ENTIDADE_ENERGIA_GASTO_VIVER)) && contador > 1) {
      contador--;
      morre();
    }
  }
  
  public void ganhouCombate() {
    // Invocado se estamos batalhando e nosso inimigo morreu.
    //moverPara(xEnergia, yEnergia);
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
        return;
    }
 
    boolean priorizarDirecaoX = getId() % 3 == 0;
 
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
    int meioX = (int)(Constants.LARGURA_TELA * 0.95) / 2;
    int meioY = (int)(Constants.ALTURA_TELA * 0.95) / 2;
    if (Math.abs(meioX - getX()) <= 5 && Math.abs(meioY - getY()) <= 5) {
      foiAoMeio = true;
      return true;
    }
 
    return false;
  }
}