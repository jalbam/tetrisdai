/** 
 * 
 * @author Joan Alba Maldonado
 */ 


//FALTA: siempre validar configuracion y si no es korrekta, usar unos valores por defecto.

//PENSAR: si poner mas opciones de configuracion.

package configuracion;


public class Otros
{
    //Respectivo al programa:
    static private byte tipoPrograma = 1; //0 = JFrame, 1 = JApplet, 2 = J2ME (si se configura en JApplet y no se ejecuta como tal, se vuelve a JFrame automaticamente).
    static final private String versionPrograma = "0.2g";
    
    //Repectivo al tablero (Modificable):
    static final private byte tableroAncho = 12;
    static final private byte tableroAlto = 22;

    //Repectivo al juego (Modificable):
    static final private int velocidadMinima = 300; //Entre mas alta, mas lento.
    static final private int velocidadMaxima = 10; //Entre mas alta, mas lento.
    static final private int velocidadInicial = velocidadMinima; //Entre mas alta, mas lento.
    static final private int incrementoVelocidad = -5; //Si es negtivo, la velocidad ira aumentando.
    static final private int lineasNecesariasNivel = 10; //Lineas necesarias para pasar de nivel.
    static final private int puntosPorLinea = 10; //Puntos por cada linea hecha.
    static final private int puntosPorLineaAdicional = 5; //Puntos por cada linea hecha de mas (para que un tetris no valga mas que cuatro simples, por ejemplo).
    static final private int puntosPorPiezaPuesta = 10; //Puntos por cada pieza puesta.
    static final private boolean sonidoActivado = true;
    static final private boolean musicaActivada = true;
    static final private boolean desplazarPiezaSiNoCabeAlRotar = false; //Desplaza la pieza al rotar, si no cabe.
    static final private int velocidadLectorTeclado = 10; //Milisegundos que tarda en volver a leer el teclado.
    static final private int retrasoTeclasRotacion = 200; //Milisegundos como minimo entre dos procesamientos de tecla (todas menos de rotacion).
    static final private int retrasoTeclasOtras = 140; //Milisegundos como minimo entre dos procesamientos de tecla, cuando es de rotacion.
    
    //General (Modificable):
    static final private String directorioImagenes = "img/"; //Define el directorio de las imagenes.
    static final private String directorioSonido = "snd/"; //Define el directorio de las imagenes.
    
    //No modificable:
    static final private int numeroVacio = 0; //Numero que marca una casilla vacia (es un indice de la paleta).
    static final private int numeroColorPanel = numeroVacio; //Numero que marca el fondo del panel (es un indice de la paleta).
    static final private int numeroColorPanelTexto = 101; //Numero del color del texto del panel (es un indice de la paleta).
    static final private int numeroColorMenuTextoFijo = 102; //Numero del color del texto permamemte del menu (es un indice de la paleta).
    static final private int numeroColorMenuTexto = 103; //Numero del color del texto del menu, al seleccionar una opcion (es un indice de la paleta).
    static final private int numeroColorMenuTextoSeleccionado = 104; //Numero del color del texto del menu, de las opciones seleccionadas (es un indice de la paleta).
    static final private int numeroColorMenuTextoFondo = 105; //Numero del color de fondo del texto del menu, de las opciones sin seleccionar (es un indice de la paleta).
    static final private int numeroColorMenuTextoFondoSeleccionado = 106; //Numero del color de fondo del texto del menu, al seleccionar una opcion (es un indice de la paleta).
    static final private int numeroColorMenuTextoAlternativo = 107; //Numero del color del texto del panel (es un indice de la paleta).
    static final private int numeroColorMenuTextoFondoAlternativo = 108; //Numero del color del texto del panel (es un indice de la paleta).
    static final private int numeroCodificacionPiezas = -1; //Numero por el que se multiplica cada numero que compone una pieza para insertar el resultado de la operacion en el tablero como piezas ya puestas. Debe ser negativo.
    
    private Otros()
    {
    }
    
    static public void setTipoPrograma(byte tipoPrograma)
    {
        Otros.tipoPrograma = tipoPrograma;
    }
    
    static public byte getTipoPrograma()
    {
        return Otros.tipoPrograma;
    }
    
    static public String getVersionPrograma()
    {
        return Otros.versionPrograma;
    }
    
    static public byte getTableroAncho()
    {
        return Otros.tableroAncho;
    }
    
    static public byte getTableroAlto()
    {
        return Otros.tableroAlto;
    }
    
    static public int getVelocidadMinima()
    {
        return Otros.velocidadMinima;
    }
    
    static public int getVelocidadMaxima()
    {
        return Otros.velocidadMaxima;
    }

    static public int getVelocidadInicial()
    {
        return Otros.velocidadInicial;
    }
    
    static public int getIncrementoVelocidad()
    {
        return Otros.incrementoVelocidad;
    }
    
    static public int getNumeroVacio()
    {
        return Otros.numeroVacio;
    }
    
    static public int getNumeroColorPanel()
    {
        return Otros.numeroColorPanel;
    }
    
    static public int getNumeroCodificacionPiezas()
    {
        return Otros.numeroCodificacionPiezas;
    }
    
    static public int getLineasNecesariasNivel()
    {
        return Otros.lineasNecesariasNivel;
    }
    
    static public int getNumeroColorPanelTexto()
    {
        return Otros.numeroColorPanelTexto;
    }

    static public int getNumeroColorMenuTextoFijo()
    {
        return Otros.numeroColorMenuTextoFijo;
    }
    
    static public int getNumeroColorMenuTexto()
    {
        return Otros.numeroColorMenuTexto;
    }

    static public int getNumeroColorMenuTextoSeleccionado()
    {
        return Otros.numeroColorMenuTextoSeleccionado;
    }
    
    static public int getNumeroColorMenuTextoFondo()
    {
        return Otros.numeroColorMenuTextoFondo;
    }
    
    static public int getNumeroColorMenuTextoFondoSeleccionado()
    {
        return Otros.numeroColorMenuTextoFondoSeleccionado;
    }

    static public int getNumeroColorMenuTextoAlternativo()
    {
        return Otros.numeroColorMenuTextoAlternativo;
    }

    static public int getNumeroColorMenuTextoFondoAlternativo()
    {
        return Otros.numeroColorMenuTextoFondoAlternativo;
    }

    static public int getPuntosPorLinea()
    {
        return Otros.puntosPorLinea;
    }

    static public int getPuntosPorLineaAdicional()
    {
        return Otros.puntosPorLineaAdicional;
    }
    
    static public int getPuntosPorPiezaPuesta()
    {
        return Otros.puntosPorPiezaPuesta;
    }
    
    static public boolean getSonidoActivado()
    {
        return Otros.sonidoActivado;
    }

    static public boolean getMusicaActivada()
    {
        return Otros.musicaActivada;
    }
    
    static public boolean getDesplazarPiezaSiNoCabeAlRotar()
    {
        return Otros.desplazarPiezaSiNoCabeAlRotar;
    }

    static public int getVelocidadLectorTeclado()
    {
        return Otros.velocidadLectorTeclado;
    }
    
    static public int getRetrasoTeclasRotacion()
    {
        return Otros.retrasoTeclasRotacion;
    }

    static public int getRetrasoTeclasOtras()
    {
        return Otros.retrasoTeclasOtras;
    }
    
    static public String getDirectorioImagenes()
    {
        return Otros.directorioImagenes;
    }
    
    static public String getDirectorioSonido()
    {
        return Otros.directorioSonido;
    }

}
