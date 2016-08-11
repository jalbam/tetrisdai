/** 
 * 
 * @author Joan Alba Maldonado
 */ 

//FALTA: siempre validar configuracion y si no es korrekta, usar unos valores por defecto.

//Nota: Para configurar la paleta, ir a configuracion.PaletaColores.

package configuracion;

import java.awt.Color;


public class Aspecto
{
    //Variables de aspecto (Modificable):
    static final private byte celdaAncho = 20; //Ancho de una casilla, en pixels.
    static final private byte celdaAlto = 20; //Alto de una casilla, en pixels.
    
    static final private byte margenHorizontal = 40; //Margen horizontal del juego respecto a su ventana.
    static final private byte margenVertical = 40; //Margen vertical del juego respecto a su ventana.
    
    static final private byte dimensionTexto = 12; //Dimension de la fuente utilizada.

    static final private byte espacioLineasTexto = 20; //Espacio entre linea y linea de texto.
    
    static final private boolean usarImagenes = true; //Define si utilizar imagenes, si existen, en lugar de colores. Cada codigo de la paleta se traduce a una imagen de la forma "<codigo>.ext" (puede ser gif, jpg/jpeg o png).
    static final private boolean usarImagenFondo = true; //Define si utilizar una imagen de fondo si existe (llamada bg.gif), detras del tablero, etc. (independiente de usarImagenes).
    
    static final private Color colorFondoImagenBuffer = new Color(0, 0, 128); //Color de fondo con el que se va limpiando la imagen del buffer.
    
    private Aspecto()
    {
    }
    
    static public byte getCeldaAncho()
    {
        return Aspecto.celdaAncho;
    }
    
    static public byte getCeldaAlto()
    {
        return Aspecto.celdaAlto;
    }
    
    static public byte getMargenHorizontal()
    {
        return Aspecto.margenHorizontal;
    }
    
    static public byte getMargenVertical()
    {
        return Aspecto.margenVertical;
    }
    
    static public byte getDimensionTexto()
    {
        return Aspecto.dimensionTexto;
    }
    
    static public byte getEspacioLineasTexto()
    {
        return Aspecto.espacioLineasTexto;
    }

    static public boolean getUsarImagenes()
    {
        return Aspecto.usarImagenes;
    }

    static public boolean getUsarImagenFondo()
    {
        return Aspecto.usarImagenFondo;
    }
    
    static public Color getColorFondoImagenBuffer()
    {
        return Aspecto.colorFondoImagenBuffer;
    }
}
