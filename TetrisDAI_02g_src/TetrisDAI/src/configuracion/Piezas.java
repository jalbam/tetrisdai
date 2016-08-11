/** 
 * 
 * @author Joan Alba Maldonado
 */ 

//FALTA: siempre validar configuracion y si no es korrekta, usar unos valores por defecto.

//FALTA: fijar el eje de rotacion mas al centro de la pieza.

//FALTA: mejorar el sistema de rotacion a la izquierda y a la derecha kuando hay desplazamiento, ya que tiene bugs.

package configuracion;

import logica.Pieza;
import logica.Paleta;


public class Piezas
{
    static private int[][][] formas;
    
    private Piezas() //Impedimos la instaciacion (quiza se podria usar una clase abstracta con el mismo resultado).
    {
    }
    
    //Piezas base (MODIFICABLE):
    static private void setPiezas()
    {
        //Piezas base (cada numero un color, ver configuracion.PaletaColores):
        int[][][] formas = {
                            {   //Pieza "Cuadrado":
                                { 1, 1 },
                                { 1, 1 }
                            },
                            {   //Pieza "Palo":
                                { 2 },
                                { 2 },
                                { 2 },
                                { 2 }
                            },
                            {
                                //Pieza "S":
                                { 3, 3, 0 },
                                { 0, 3, 3 },
                            },
                            {
                                //Pieza "S" invertida:
                                { 0, 4, 4 },
                                { 4, 4, 0 }
                            },
                            {
                                //Pieza "L":
                                { 5, 0 },
                                { 5, 0 },
                                { 5, 5 }
                            },
                            {
                                //Pieza "L" invertida:
                                { 0, 6 },
                                { 0, 6 },
                                { 6, 6 }
                            },
                            {
                                //Pieza "T":
                                { 0, 7, 0 },
                                { 7, 7, 7 }
                            }
                           };
        
        Piezas.formas = formas;
    }
    
    //Retorna un vector con las piezas definidas en el objeto (arriba):
    static public Pieza[] getPiezas(Paleta paleta)
    {
        Piezas.setPiezas();
        
        Pieza[] piezas = new Pieza[Piezas.formas.length];
        
        for (int x = 0; x < piezas.length; x++)
        {
            piezas[x] = new Pieza(Piezas.formas[x], paleta);
        }
        
        return piezas;
    }
}