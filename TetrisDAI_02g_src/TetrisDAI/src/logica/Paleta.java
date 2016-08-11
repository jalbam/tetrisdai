/** 
 * 
 * @author Joan Alba Maldonado
 */ 

package logica;

//Falta: en totes les classes, impedir ficar valors no possibles en els sets (valors negatius, etc).

import java.awt.Color;


public class Paleta
{
    protected Color[] colores;
   
    public Paleta(Color[] colores)
    {
        this.setColores(colores);
    }

    public void setColores(Color[] colores)
    {
        this.colores = colores;
    }
    
    public Color getColor(int indice)
    {
        Color color = null;
        if (indice >= 0 && indice < this.getColores().length)
        {
            color = this.colores[indice];
        }
        return color;
    }

    public void setColor(int indice, Color color)
    {
        if (indice >= 0 && indice < this.getColores().length)
        {
            this.colores[indice] = color;
        }
    }
    
    public Color[] getColores()
    {
        return this.colores;
    }
    
    public int numeroColores()
    {
        return this.getColores().length;
    }
}
