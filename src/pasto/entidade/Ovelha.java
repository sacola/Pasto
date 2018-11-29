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

public class Ovelha implements Entidade {
    /** O ícone desta entidade. */
    private final ImageIcon imagem = new ImageIcon("imagens/sheep.gif"); 
    /** A posição desta entidade. */
    protected Pasto pasto;
    /** O número de tiques que devem passar até que essa entidade possa se mover. */
    protected int tempoParaMover;
    
    protected int reproduzir;

    protected int tempoParaNovaReproducao;
    
    protected boolean vivo = true;
    
    private static final int REPRODUCAO = 20;
    
    protected int tempoSemComida;
    
    private void comer(){
    	for ( Entidade ser :
    	pasto.getEntidadesEm(pasto.getPosicaoEntidade(this))){
    		if(ser instanceof Planta) {
    		pasto.removeEntidade(ser);
    		tempoSemComida = 20;
    		}
    	};
    }

    /**
     * Faz as ações relevantes a esta entidade, dependendo de que tipo de entidade seja.
     */
    @Override
    public void tick() 
    {
	tempoParaNovaReproducao--;
        
        if(tempoParaNovaReproducao == 0) {      
            reproduzir();

            tempoParaNovaReproducao = REPRODUCAO;
        }
    	
        tempoParaMover--;
        
        if(tempoParaMover == 0) {
            Point neighbour = 
                (Point)getMembroAleatorio
                (pasto.getVizinhosLivres(this));
            
            if(neighbour != null) 
                pasto.moveEntidade(this, neighbour);

            tempoParaMover = 15;
            comer();
        }if(tempoSemComida==0) {
       pasto.removeEntidade(this);
       }
    }
    public Ovelha(Pasto pasto) {
		this.pasto = pasto;
		tempoParaNovaReproducao = REPRODUCAO;
		tempoParaMover = 15;
		tempoSemComida = 20;
	}

	public void reproduzir() {
		Point vizinho = 
                (Point)getMembroAleatorio
                (pasto.getVizinhosLivres(this));
		
		pasto.adicionaEntidade(new Ovelha(pasto), vizinho);
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
