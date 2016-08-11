/** 
 * 
 * @author Joan Alba Maldonado
 */

/*
 * TetrisDAI (http://tetrisdai.sourceforge.net)
 */

//Falta: en totes les classes, impedir ficar valors no possibles en els sets (valors negatius, etc).

package principal;

import java.awt.Graphics;

import logica.Juego;
import logica.Paleta;
import logica.Pieza;

import gui.TetrisApplet;

import gui.TetrisForm;
import gui.MotorGrafico;

//import java.awt.Dimension;

//FALTA: implementar multijugador (quiza el objeto juego de Main pueda convertirse en JuegoMultijugador y se manejaria igual).

public class Main
{
    static public Juego juego = null; //Objeto principal del motor del juego.
    
    static public TetrisForm ventanaPrincipal; //Formulario principal del juego.
    static public TetrisApplet appletPrincipal;

    static protected int idiomaActual = configuracion.Idiomas.idiomaPorDefecto; //Idioma actual del programa.

    
    public Main()
    {
    }
   
    //Main que se ejecuta cuando se utiliza el JFrame en lugar del JApplet:
    public static void main(String[] args)
    {
        //Si se ha ejecutado primero el main en lugar del JApplet, se configura para utilizar el JFrame y no el Applet:
        if (Main.appletPrincipal == null)
        { 
            configuracion.Otros.setTipoPrograma((byte) 0);
        }
        
        //Si esta configurado para utilizar JFrame, lo hace:
        if (configuracion.Otros.getTipoPrograma() == 0)
        {
            Main.ventanaPrincipal = new TetrisForm();
            Main.mostrarVentana();
            Main.juego = new Juego(Main.ventanaPrincipal.getPanelJuego());
        }
        //...pero si esta configurado para utilizar JApplet, lo hace:
        else if (configuracion.Otros.getTipoPrograma() == 1)
        {
            Main.juego = new Juego(Main.appletPrincipal.getPanelJuego());
        }

        //PRUEBA:
        Main.iniciarJuego(); //BORRAR ESTA LINEA!!!
        
        //Define el ancho y alto de la ventana o applet principal del juego:
        Main.redimensionarVentana(); //Quiza se deba comentar esto si se utliza un Applet.
        
        Main.juego.mostrarMenuPrincipal(); //<<-PRUEBA!!!
    }
    
    public static int getIdiomaActual()
    {
        return Main.idiomaActual;
    }
    
    //Muestra la ventana principal del juego:
    protected static void mostrarVentana()
    {
        //Muestra la ventana:
        Main.ventanaPrincipal.main(null);
    }
    
    //Pone el ancho y alto de la ventana o applet principal del juego:
    public static void redimensionarVentana()
    {
        int ancho = 640, alto = 480; //Dimension por defecto si no existe el juego.
        
        if (Main.juego != null)
        {
            ancho = (Main.juego.getPanel().getLeft() + Main.juego.getPanel().getAncho()) * MotorGrafico.getCeldaAncho();
            alto = Main.juego.getTablero().getAlto() * MotorGrafico.getCeldaAlto(); //Podria pasar que el alto del tablero fuese menor que el alto del panel, pero no se tendra en cuenta (solo ocurriria con piezas muy grances y un tablero menor que alguna de estas).
        }
        
        int margenHorizontal = configuracion.Aspecto.getMargenHorizontal();
        int margenVertical = configuracion.Aspecto.getMargenVertical();
        
        //Si esta configurado para utilizar JFrame, redimensiona este:
        if (configuracion.Otros.getTipoPrograma() == 0)
        {
            Main.ventanaPrincipal.setSize(ancho + margenHorizontal, alto + margenVertical);
        }
        //...pero si esta configurado para utilizar JApplet, redimensiona este:
        else if (configuracion.Otros.getTipoPrograma() == 1)
        {
            Main.appletPrincipal.setSize(ancho + margenHorizontal, alto + margenVertical);
        }
        
        //Si el juego ha comenzado, redimensiona la imagen de buffer:
        if (Main.juego != null)
        {
            Main.juego.redimensionarImagenBuffer();
        }
    }
    
    //Inicia el juego:
    public static void iniciarJuego()
    {
        //Define el ancho y alto de la ventana o applet principal del juego:
        Main.redimensionarVentana(); //Quiza se deba comentar esto si se utliza un Applet.

        //Si no se ha instanciado el juego, sale:
        if (Main.juego == null) { return; }
        
        //Inicia el juego:
        Main.juego.iniciarJuego();
    }
    
    //Finaliza el juego (no el programa):
    public static void finalizarJuego()
    {
        //Si no se ha instanciado el juego, sale:
        if (Main.juego == null) { return; }

        Main.juego.finalizarJuego();
        //Main.juego = null;
    }
    
    //Reinicia el juego (no el programa):
    public static void reiniciarJuego()
    {
        //Si no se ha instanciado el juego, sale:
        if (Main.juego == null) { return; }

        Main.juego.reiniciarJuego();
    }
    
    //Pausa el juego:
    public static void pausarJuego()
    {
        //Si no se ha instanciado el juego, sale:
        if (Main.juego == null) { return; }

        Main.juego.pausarJuego();
    }
    
    //Reanuda el juego (quita la pausa):
    public static void reanudarJuego()
    {
        //Si no se ha instanciado el juego, sale:
        if (Main.juego == null) { return; }

        Main.juego.reanudarJuego();
    }

    //Cambia el contenedor grafico del juego:
    public static void cambiarContenedorGraficoJuego(Graphics contenedor)
    {
        //Si no se ha instanciado el juego, sale:
        if (Main.juego == null) { return; }

        Main.juego.setContenedorGrafico(contenedor);
    }
    
    //Cambia la paleta del juego:
    public static void cambiarPaletaJuego(Paleta paleta)
    {
        //Si no se ha instanciado el juego, sale:
        if (Main.juego == null) { return; }

        Main.juego.setPaleta(paleta);
    }
    
    //Cambia las piezas base del juego:
    public static void cambiarPiezasBaseJuego(Pieza[] piezasBase)
    {
        //Si no se ha instanciado el juego, sale:
        if (Main.juego == null) { return; }

        Main.juego.setPiezasBase(piezasBase);
    }

    //Cambia las teclas del juego:
    public static void cambiarTeclasJuego(int[] teclaArriba, int[] teclaAbajo, int[] teclaDerecha, int[] teclaIzquierda, int[] teclaRotarDerecha, int[] teclaRotarIzquierda, int[] teclaAceptar, int[] teclaPausa, int[] teclaSalir)
    {
        //Si no se ha instanciado el juego, sale:
        if (Main.juego == null) { return; }

        Main.juego.definirTeclas(teclaArriba, teclaAbajo, teclaDerecha, teclaIzquierda, teclaRotarDerecha, teclaRotarIzquierda, teclaAceptar, teclaPausa, teclaSalir);
    }
    
    //Lee y procesa una tecla:
    public static void procesarTecla(int codigoTecla)
    {
        //Si no se ha instanciado el juego, sale:
        if (Main.juego == null) { return; }
        
        Main.juego.procesarTecla(codigoTecla);
        //System.out.println("Tecla pulsada: " + codigoTecla);
    }
    
    //Cambia el idioma actual:
    public static void cambiarIdiomaActual(int idioma)
    {
        if (idioma >= 0 && idioma < configuracion.Idiomas.idiomas.length)
        {
            Main.idiomaActual = idioma;
        }
    }
    
    //Desactiva el sonido del juego:
    public static void desactivarSonido()
    {
        //Si no se ha instanciado el juego, sale:
        if (Main.juego == null) { return; }
        
        Main.juego.desactivarSonido();
    }
    
    //Activa el sonido del juego:
    public static void activarSonido()
    {
        //Si no se ha instanciado el juego, sale:
        if (Main.juego == null) { return; }
        
        Main.juego.activarSonido();
    }
    
    //Alterna el sonido del juego (lo activa o desactiva):
    public static void alternarSonido()
    {
        //Si no se ha instanciado el juego, sale:
        if (Main.juego == null) { return; }
        
        Main.juego.alternarSonido();
    }
}
