/** 
 * 
 * @author Joan Alba Maldonado
 */ 

//Falta: en totes les classes, impedir ficar valors no possibles en els sets (valors negatius, etc).

package logica.multijugador;

//import configuracion.*; //Utiliza el archivo de configuracion del juego.

import java.awt.Graphics;

import logica.*;


public class JuegoMultijugador extends Juego
{
    public JuegoMultijugador(Graphics contenedor)
    {
        super(contenedor);
    }
    
    public JuegoMultijugador(Graphics contenedor, Paleta paleta)
    {
        super(contenedor, paleta);
    }
    
    public JuegoMultijugador(Graphics contenedor, Paleta paleta, Pieza[] piezasBase)
    {
        super(contenedor, paleta, piezasBase);
    }
}