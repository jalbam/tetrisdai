/** 
 * 
 * @author Joan Alba Maldonado
 */ 

//FALTA: siempre validar configuracion y si no es korrekta, usar unos valores por defecto.

//Nota: Si usarImagenes de configuracion.Aspecto esta activado, cada codigo se traduce a una imagen en lugar de a un color (ver configuracion.Aspecto).

package configuracion;

import java.awt.Color;

import logica.Paleta;


public class PaletaColores
{
    static private Paleta paleta;
    
    static private Color[] colores;
    
    private PaletaColores() //Impedimos la instaciacion (quiza se podria usar una clase abstracta con el mismo resultado).
    {
    }

    //Colores utilizados segun el numero que forma la pieza (MODIFICABLE):
    static private void setPaleta()
    {
        byte numeroColores = Byte.MAX_VALUE;
        PaletaColores.colores = new Color[numeroColores];
        
        //Nota: no utilizar nunca un valor negativo (los utiliza MotorGrafico).
        PaletaColores.colores[configuracion.Otros.getNumeroVacio()] = Color.BLACK; //Vacio.
        PaletaColores.colores[configuracion.Otros.getNumeroColorPanel()] = Color.BLACK; //Fondo del panel.
        PaletaColores.colores[configuracion.Otros.getNumeroColorPanelTexto()] = Color.MAGENTA; //Texto del panel.
        PaletaColores.colores[configuracion.Otros.getNumeroColorMenuTextoFijo()] = Color.YELLOW; //Texto permamente del menu.
        PaletaColores.colores[configuracion.Otros.getNumeroColorMenuTexto()] = Color.GRAY; //Texto del menu.
        PaletaColores.colores[configuracion.Otros.getNumeroColorMenuTextoFondo()] = Color.LIGHT_GRAY; //Fondo del texto del menu.
        PaletaColores.colores[configuracion.Otros.getNumeroColorMenuTextoSeleccionado()] = Color.GREEN; //Texto seleccionado del menu.
        PaletaColores.colores[configuracion.Otros.getNumeroColorMenuTextoFondoSeleccionado()] = Color.WHITE; //Fondo del texto seleccionado del menu.
        PaletaColores.colores[configuracion.Otros.getNumeroColorMenuTextoAlternativo()] = Color.RED; //Texto alternativo del menu.
        PaletaColores.colores[configuracion.Otros.getNumeroColorMenuTextoFondoAlternativo()] = Color.BLUE; //Fondo del texto alternativo del menu.
        
        PaletaColores.colores[1] = Color.BLUE;
        PaletaColores.colores[2] = Color.RED;
        PaletaColores.colores[3] = Color.GREEN;
        PaletaColores.colores[4] = Color.ORANGE;
        PaletaColores.colores[5] = Color.PINK;
        PaletaColores.colores[6] = Color.YELLOW;
        PaletaColores.colores[7] = Color.CYAN;
        
        PaletaColores.colores[8] = new Color(255, 125, 60, 80);
        PaletaColores.colores[9] = new Color(125, 255, 60, 80);
        PaletaColores.colores[10] = new Color(60, 125, 255, 80);
        PaletaColores.colores[11] = new Color(125, 60, 255, 80);
        PaletaColores.colores[12] = new Color(255, 60, 125, 80);
        PaletaColores.colores[13] = new Color(60, 225, 125, 80);
        
        PaletaColores.colores[99] = new Color(0, 0, 0, 200);
        
        PaletaColores.paleta = new Paleta(colores);
    }
    
    //Retorna la paleta definida en el objeto (arriba):
    static public Paleta getPaleta()
    {
        PaletaColores.setPaleta();
        return PaletaColores.paleta;
    }
}