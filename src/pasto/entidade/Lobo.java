package pasto.entidade;
import javax.swing.*;

import pasto.Pasto;
import pasto.gui.PastoGUI;

import java.util.*;
import java.awt.*;

/*
 * Note que a classe Dummy é um exemplo MUITO ruim de OOP.
 * Ao invés de ter classes separadas para dummies parados
 * e móveis, eles são dinstinquigeis pela flag "vivo". Você
 * não deve usar essa classe como base para sua solução nesta
 * classe.
 */

public class Lobo implements Entidade {
    /** O ícone desta entidade. */
    private final ImageIcon imagem = new ImageIcon("imagens/wolf.gif"); 
    /** A posição desta entidade. */
    protected Pasto pasto;
    /** O número de tiques que devem passar até que essa entidade possa se mover. */
    protected int tempoParaMover;
    
    protected int tempoParaNovaReproducao;
    
    protected int reproduzir;
    
    private static final int REPRODUCAO = 35;
    
    protected int tempoSemComida;
    
    private void comer(){
    	for ( Entidade ser :
    	pasto.getEntidadesEm(pasto.getPosicaoEntidade(this))){
    		if(ser instanceof Ovelha) {
    		pasto.removeEntidade(ser);
    		tempoSemComida = 20;
    		}
    	};
    }
    
    protected boolean vivo = true;

    /**
     * Cria uma nova instância desta classe, com o pasto dado como seu pasto
     * @param pasto o pasto a qual esta entidade deve pertencer.
     */
    public Lobo(Pasto pasto) {
        this.pasto = pasto;
        tempoParaNovaReproducao = REPRODUCAO;
        tempoParaMover = 10;
    }

    /**
     * Cria uma nova instância desta classe, com o pasto como seu pasto
 	 * e uma flag "vivo" para indicar se é um Dummy móvel ou estático.
     * @param pasto o pasto a qual esta entidade pertence.
     * @param vivo true ou false se o Dummy é vivo ou não.
     */
    public Lobo(Pasto pasto, boolean vivo) {
        this.pasto   = pasto;
        this.vivo     = vivo;
        tempoParaMover      = 10;
    }

    /**
     * Faz as ações relevantes a esta entidade, dependendo de que tipo de entidade seja.
     */
    @Override
    public void tick() {
        if(vivo)
            tempoParaMover--;
        
        if(tempoParaMover == 0) {
            Point neighbour = 
                (Point)getMembroAleatorio
                (pasto.getVizinhosLivres(this));
            
            if(neighbour != null) 
                pasto.moveEntidade(this, neighbour);

            tempoParaMover = 10;
            comer();
        }	tempoParaNovaReproducao--;
        
        if(tempoParaNovaReproducao == 0) {      
            reproduzir();

            tempoParaNovaReproducao = REPRODUCAO;
        }
        
    }
    public void reproduzir() {
		Point vizinho = 
                (Point)getMembroAleatorio
                (pasto.getVizinhosLivres(this));
		
		pasto.adicionaEntidade(new Lobo(pasto), vizinho);
	}
    /** 
     * Retorna o ícone desta entidade, para ser mostrada pela gui do pasto
     * @see PastoGUI
     */
    @Override
    public ImageIcon getImagem() { return imagem; }

    /**
     * Testa se esta entidade pode estar na mesma posição no pasto da outra, passada por parâmetro.
     */
    @Override
    public boolean eCompativel(Entidade otherEntity) { return false; }
    
    protected static <X> X getMembroAleatorio(Collection<X> c) {
        if (c.size() == 0)
            return null;
        
        Iterator<X> it = c.iterator();
        int n = (int)(Math.random() * c.size());

        while (n-- > 0) {
            it.next();
        }

        return it.next();
    }

}
