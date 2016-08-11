/** 
 * 
 * @author Joan Alba Maldonado
 */ 

//FALTA: siempre validad configuracion y si no es korrekta, usar unos valores por defecto.

//SUGERENCIA: una tekla para bajar de golpe la pieza sin poder mover a los lados, previa animacion (o muy rapido, asi se podria kiza mover a un lado mienras kayera).

package configuracion;

import java.awt.event.*;


public class Teclas
{
    //Variables que definen las teclas (MODIFICABLE):
    static final private int[] teclaArriba = { KeyEvent.VK_UP }; //Solo util si hay algun menu en el juego, etc.
    static final private int[] teclaAbajo = { KeyEvent.VK_DOWN }; //40 };
    static final private int[] teclaDerecha = { KeyEvent.VK_RIGHT };
    static final private int[] teclaIzquierda = { KeyEvent.VK_LEFT };
    static final private int[] teclaRotarDerecha = { Teclas.teclaArriba[0], 17, 13, 190 }; //Podremos rotar con la misma tecla que se usa en teclaArriba.
    static final private int[] teclaRotarIzquierda = { KeyEvent.VK_SPACE, KeyEvent.VK_ENTER, 45, 96 };
    static final private int[] teclaAceptar = { KeyEvent.VK_ENTER, 13, 10 };
    static final private int[] teclaPausa = { KeyEvent.VK_ESCAPE, 80, 112 };
    static final private int[] teclaSalir = { KeyEvent.VK_ESCAPE };
    
    private Teclas()
    {
    }
    
    static public int[] getTeclaArriba()
    {
        return Teclas.teclaArriba;
    }
    
    static public int[] getTeclaAbajo()
    {
        return Teclas.teclaAbajo;
    }
    
    static public int[] getTeclaDerecha()
    {
        return Teclas.teclaDerecha;
    }
    
    static public int[] getTeclaIzquierda()
    {
        return Teclas.teclaIzquierda;
    }
    
    static public int[] getTeclaRotarDerecha()
    {
        return Teclas.teclaRotarDerecha;
    }
    
    static public int[] getTeclaRotarIzquierda()
    {
        return Teclas.teclaRotarIzquierda;
    }
    
    static public int[] getTeclaAceptar()
    {
        return Teclas.teclaAceptar;
    }

    static public int[] getTeclaPausa()
    {
        return Teclas.teclaPausa;
    }

    static public int[] getTeclaSalir()
    {
        return Teclas.teclaSalir;
    }

}