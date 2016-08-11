/** 
 * 
 * @author Joan Alba Maldonado
 */ 

package logica;

//Falta: en totes les classes, impedir ficar valors no possibles en els sets (valors negatius, etc).

//FALTA: en todos, pensar si poner un destructor (para piezas iria bien, a lo mejor).

//SUGERENCIA: poner trukos ke se tekleen (si es multiplataforama, buskar otra forma de hacerlos o no poder aktivarlos). kiza la forma seria utilizando kombinacion de las teklas usadas para jugar en el menu principal o en otro lugar konkreto (kiza poner un tiempo tope para poder introducir la kombinacion).

//PROBLEMA: parace ser ke al hacer cancel() y purge() del timer no va (no se para el timer), hay que ponerlo a null i volver a instanciarlo. alguna solucion???

//FALTA: ranking kon mejores puntuaciones, offline (i si fuera online, kiza en una web o algo los mejores).

//PENSARSE: poner un menu gameover.

//FALTA: siempre komprobar ke piezaSiguiente, etc. no sean null antes de rotar, i otras kosas iwal., para k no de error nunka.

//FALTA: poner metodo toString() en pieza, panel, juego (para una escena kompleta), MotorGrafico, etc... en tablero ia esta puesto! :D

//ACORDASE de utilizar el objeto de sonido en los sitios rekeridos.

//FALTA: una opcion de guardar partida i otra de salvar partida. kiza la de salvar podria ser iniciarJuego(datos), i ke esta llamara a iniciarJuego() i luego a cargarDatos(datos), donde datos pueden ser ma de un parametro o un archivo o lo k sea (pensarlo).

//*** KUIDADO: revisar lo komentado kon ////........

//SUGERENCIA: al kalkular en el ciklo k hai una kolision de pieza, si apretamos la tekla hacia abajo solidifikarla i no esperar a ke vuelva el siguiente ciklo. asi al apretar hacia abajo iria mas rapido lo de solidifikarse i no tardaria tanto en llegar la proxima pieza,

//PENSARSE: no reproducir sonido del juego kuando el menu principal este aktivo. pero si reproducir sonidos del menu.
//PENSARSE: Poder hacer ciclo de juego sin sonido o desaktivar el sonido, para ke al inicio siempre ke este el menu no se reproduzkan sonidos (solo los del menu, si tiene, i una musika si keremos).

//PENSARSE: separar la lectura de teclas e interpretacion del menu a un objeto especifico, llamado por ejemplo ManejadorMenu.

//SUGERENCIA: marcar donde va a caer la pieza.

//PROBLEMA: al acabar juego y comenzar otro, a veces la pieza siguiente anterior no se borra bien.

//PROBLEMA: al iniciar el programa, a veces no se pinta todo bien.

//PROBLEMA: el sonido en mi AMD64.

//PROBAR: Al iniciar juego, vaciar Teclado (y quiza limpiar la imagen de buffer).

//SUGERENCIA: Cuando se salga de un menu, al volver que se quede en la misma opcion. Pero al finalizar un juego y volver a jugar otra vez, al volver al menu de pausa ke no ete otra vez en finalizar.

//PROBLEMA: al arrastrar la ventana principal mientras se ejecuta un setSize, esta no cambia de dimension.

//SUGERENCIA: en esta y todas las demas klases, kitar imports de configuracion.loquesea, ya que no haca falta porque siempre se usa la ruta absoluta.

//SUGERENCIA: dejar pulsar tekla de pausa al estar haciendo una animacion (no komo ahora, ke no se permite ninguna tekla). ver ke la animacion kede bien kon el menu en pausa i no se ponga encima ni nada.


import configuracion.*; //Utiliza el archivo de configuracion del juego.

import principal.Main;

import java.awt.Graphics; //import javax.swing.JPanel; //import java.awt.Graphics;
//import java.awt.event.*;

import java.util.Timer;

import java.util.Random;

import java.awt.image.BufferedImage;

import gui.MotorGrafico;
import gui.MotorAnimaciones;
import sonido.MotorSonido;


public class Juego
{
    protected int[] teclaArriba = configuracion.Teclas.getTeclaArriba(); //Tecla(s) para ir arriba (en Tetris no se puede, pero por si hubiera algun menu).
    protected int[] teclaAbajo = configuracion.Teclas.getTeclaAbajo(); //Tecla(s) para ir abajo.
    protected int[] teclaDerecha = configuracion.Teclas.getTeclaDerecha(); //Tecla(s) para ir a la derecha.
    protected int[] teclaIzquierda = configuracion.Teclas.getTeclaIzquierda(); //Tecla(s) para ir a la izquierda.
    protected int[] teclaRotarDerecha = configuracion.Teclas.getTeclaRotarDerecha(); //Tecla(s) para rotar a la derecha.
    protected int[] teclaRotarIzquierda = configuracion.Teclas.getTeclaRotarIzquierda(); //Tecla(s) para rotar a la derecha.
    protected int[] teclaAceptar = configuracion.Teclas.getTeclaAceptar(); //Tecla(s) para aceptar.
    protected int[] teclaPausa = configuracion.Teclas.getTeclaPausa(); //Tecla(s) para pausar.
    protected int[] teclaSalir = configuracion.Teclas.getTeclaSalir(); //Tecla(s) para salir.

    protected Timer timerJuego = null; //Timer que llama a cicloJuego() (equivalente a setInterval de otros lenguajes).
    
    protected Timer timerRepintado = null; //Timer que llama a representarTodo() (equivalente a setInterval de otros lenguajes).

    protected Timer timerLectorTeclado = null; //Timer que llama al lector de teclado (equivalente a setInterval en otros lenguajes).
    
    protected int velocidadLectorTeclado = configuracion.Otros.getVelocidadLectorTeclado(); //Milisegundos que tarda en volver a leer el teclado.
    protected int retrasoTeclasRotacion = configuracion.Otros.getRetrasoTeclasRotacion(); //Milisegundos como minimo entre dos procesamientos de tecla, cuando es de rotacion.
    protected int retrasoTeclasOtras = configuracion.Otros.getRetrasoTeclasOtras(); //Milisegundos como minimo entre dos procesamientos de tecla (todas menos de rotacion).

    protected Graphics contenedorGrafico; //Contenedor grafico donde se representara todo el juego.

    protected BufferedImage imagenBuffer = null; //Imagen de buffer para representar todo a mas velocidad.
    
    //protected Sonido motorSonido = new Sonido(); //Manejador de sonido.
    protected boolean sonidoActivado = configuracion.Otros.getSonidoActivado(); //Define si se reproducen sonidos o no.
    protected boolean musicaActivada = configuracion.Otros.getMusicaActivada(); //Define si se reproducen musica o no.
    
    protected Panel panel = null; //Panel donde va la puntuacion, ficha siguiente, etc.
    
    protected Menu menuPrincipal = null; //Menu principal del juego.
    protected Menu menuPausa = null; //Menu de pausa.
    
    protected Paleta paleta; //Paleta que utiliza el juego.
    
    protected Pieza[] piezasBase; //Vector con las piezas base.
            
    protected int velocidad = configuracion.Otros.getVelocidadInicial(); //Retraso entre ciclos, en milisegundos (numero menor = mas rapido).
    protected int velocidadMinima = configuracion.Otros.getVelocidadMinima(); //Velocidad minima posible (numero menor = mas rapido).
    protected int velocidadMaxima = configuracion.Otros.getVelocidadMaxima(); //Velocidad maxima posible (numero menor = mas rapido).
    protected int incrementoVelocidad = configuracion.Otros.getIncrementoVelocidad(); //Incremento de la velocidad cuando esta sube (si es negativo, la velocidad sube).
    
    protected boolean juegoComenzado = false; //Define si el juego ha comenzado o no.
    protected boolean juegoPausado = false; //Define si el juego esta en pausa o no.

    protected int nivel; //Numero de nivel.
    
    protected int puntos; //Puntuacion.
    protected int puntosPorPiezaPuesta = configuracion.Otros.getPuntosPorPiezaPuesta();
    protected int puntosPorLineaAdicional = configuracion.Otros.getPuntosPorLineaAdicional();
    protected int puntosPorLinea = configuracion.Otros.getPuntosPorLinea();
    
    protected int lineas; //Lineas hechas.
    protected int lineasNivel; //Lineas hechas en el nivel actual.
    protected int lineasNecesariasNivel = configuracion.Otros.getLineasNecesariasNivel(); //Lineas necesarias para pasar de nivel.
    
    protected Tablero tablero; //Tablero del juego.
    
    protected boolean desplazarPiezaSiNoCabeAlRotar = Otros.getDesplazarPiezaSiNoCabeAlRotar();
    
    protected Pieza piezaActual; //Ficha actual que controla el jugador.
    protected Pieza piezaSiguiente; //Ficha que va a venir luego.
    
    protected boolean piezaColisionada; //Define si la pieza ha colisionado en el ciclo anterior.
    
    protected boolean ignorarTeclas = false; //Define si ignorar las teclas o no (para cuando esta haciendo una animacion, etc).
    
    //Constructor que recibe el contenedor del juego:
    public Juego(Graphics contenedor)
    {
        this(contenedor, null, null);
    }
    
    //Constructor que recibe el contenedor y la paleta del juego:
    public Juego(Graphics contenedor, Paleta paleta)
    {
        this(contenedor, paleta, null); //Pondra como piezas base las definidas en la configuracion.
    }
    
    //Constructor que recibe el contenedor, la paleta y las piezas base del juego:
    public Juego(Graphics contenedor, Paleta paleta, Pieza[] piezasBase)
    {
        this.setContenedorGrafico(contenedor);
        
        //Si no se ha enviado una paleta, pone la definida en la configuracion:
        if (paleta == null)
        {
            paleta = configuracion.PaletaColores.getPaleta();
        }
        this.setPaleta(paleta);
        //Si no se ha enviado piezas base, pone las definidas en la configuracion:
        if (piezasBase == null)
        {
            piezasBase = configuracion.Piezas.getPiezas(this.getPaleta());
        }
        this.setPiezasBase(piezasBase);
        
        //Crea el tablero con el alto y ancho definidos en la configuracion:
        byte alto = configuracion.Otros.getTableroAlto();
        byte ancho = configuracion.Otros.getTableroAncho();
        this.tablero = new Tablero(ancho, alto, this.getPaleta());
        
        //Instancia el panel:
        this.panel = new Panel(this.getPiezasBase(), this.getPaleta(), this.getTablero());
        
        //Instancia el menu principal:
        this.menuPrincipal = configuracion.Menus.getMenuPrincipal();
        this.menuPrincipal.setPaleta(this.getPaleta());
        
        //Instancia el menu de pausa:
        this.menuPausa = configuracion.Menus.getMenuPausa();
        this.menuPausa.setPaleta(this.getPaleta());
        
        //Carga los sonidos:
        if (!MotorSonido.sonidosCargados) { MotorSonido.cargarSonidos(); }
        
        //PRUEBA: //////Pausa para que no de error en Linux (sucede a veces):
        try { Thread.sleep(100); }
        catch (Exception e) { System.out.println("Excepcion: " + e.getMessage()); }
        
        //Crea la imagen de buffer:
        this.redimensionarImagenBuffer();
        
        //Comienza el repintado automatico:
        this.iniciarRepintado();
        
        //Comienza a leer el teclado:
        this.iniciarLectorTeclado();
    }
    
    public void setContenedorGrafico(Graphics contenedor)
    {
        this.contenedorGrafico = contenedor;
    }
    
    public Graphics getContenedorGrafico()
    {
        return this.contenedorGrafico;
    }
    
    public void setPaleta(Paleta paleta)
    {
        this.paleta = paleta;
        if (this.getPanel() != null)
        {
            this.getPanel().setPaleta(paleta);
        }
        if (this.getMenuPrincipal() != null)
        {
            this.getMenuPrincipal().setPaleta(paleta);
        }
        if (this.getMenuPausa() != null)
        {
            this.getMenuPausa().setPaleta(paleta);
        }
    }
    
    public Paleta getPaleta()
    {
        return this.paleta;
    }
    
    public int[] getTeclaArriba()
    {
        return this.teclaArriba;
    }

    public void setTeclaArriba(int[] teclaArriba)
    {
        this.teclaArriba = teclaArriba;
    }

    public int[] getTeclaAbajo()
    {
        return this.teclaAbajo;
    }

    public void setTeclaAbajo(int[] teclaAbajo)
    {
        this.teclaAbajo = teclaAbajo;
    }

    public int[] getTeclaDerecha()
    {
        return this.teclaDerecha;
    }

    public void setTeclaDerecha(int[] teclaDerecha)
    {
        this.teclaDerecha = teclaDerecha;
    }

    public int[] getTeclaIzquierda()
    {
        return this.teclaIzquierda;
    }

    public void setTeclaIzquierda(int[] teclaIzquierda)
    {
        this.teclaIzquierda = teclaIzquierda;
    }

    public int[] getTeclaRotarDerecha()
    {
        return this.teclaRotarDerecha;
    }

    public void setTeclaRotarDerecha(int[] teclaRotarDerecha)
    {
        this.teclaRotarDerecha = teclaRotarDerecha;
    }

    public int[] getTeclaRotarIzquierda()
    {
        return this.teclaRotarIzquierda;
    }

    public void setTeclaRotarIzquierda(int[] teclaRotarIzquierda)
    {
        this.teclaRotarIzquierda = teclaRotarIzquierda;
    }

    public int[] getTeclaAceptar()
    {
        return this.teclaAceptar;
    }

    public void setTeclaAceptar(int[] teclaAceptar)
    {
        this.teclaAceptar = teclaAceptar;
    }

    public int[] getTeclaPausa()
    {
        return this.teclaPausa;
    }

    public void setTeclaPausa(int[] teclaPausa)
    {
        this.teclaPausa = teclaPausa;
    }

    public int[] getTeclaSalir() {
        return this.teclaSalir;
    }

    public void setTeclaSalir(int[] teclaSalir)
    {
        this.teclaSalir = teclaSalir;
    }
    
    public int getVelocidadLectorTeclado()
    {
        return this.velocidadLectorTeclado;
    }
    
    public void setVelocidadLectorTeclado(int velocidadLectorTeclado)
    {
        if (velocidadLectorTeclado > 0)
        {
            this.velocidadLectorTeclado = velocidadLectorTeclado;
        }
    }
    
    public int getRetrasoTeclasRotacion()
    {
        return this.retrasoTeclasRotacion;
    }
    
    public void setRetrasoTeclasRotacion(int retrasoTeclasRotacion)
    {
        this.retrasoTeclasRotacion = retrasoTeclasRotacion;
    }

    public int getRetrasoTeclasOtras()
    {
        return this.retrasoTeclasOtras;
    }
    
    public void setRetrasoTeclasOtras(int retrasoTeclasOtras)
    {
        this.retrasoTeclasOtras = retrasoTeclasOtras;
    }
    
    public Panel getPanel()
    {
        return this.panel;
    }

    public Menu getMenuPrincipal()
    {
        return this.menuPrincipal;
    }

    public Menu getMenuPausa()
    {
        return this.menuPausa;
    }
    
    public Tablero getTablero()
    {
        return this.tablero;
    }
    
    public void setPiezasBase(Pieza[] piezasBase)
    {
        this.piezasBase = piezasBase;
        if (this.getPanel() != null)
        {
            this.getPanel().setPiezasBase(piezasBase);
            
            //Vuelve a dimensionar la ventana principal del juego:
            Main.redimensionarVentana(); //Quiza se deba comentar esto si se utiliza un Applet.
        
            //Redimensiona la imagen de buffer:
            this.redimensionarImagenBuffer();
        }
    }
    
    protected int numeroPiezasBase()
    {
        return this.getPiezasBase().length;
    }
    
    public Pieza[] getPiezasBase()
    {
        return this.piezasBase;
    }

    public void setDesplazarPiezaSiNoCabeAlRotar(boolean desplazarPiezaSiNoCabeAlRotar)
    {
        this.desplazarPiezaSiNoCabeAlRotar = desplazarPiezaSiNoCabeAlRotar;
    }
    
    public boolean getDesplazarPiezaSiNoCabeAlRotar()
    {
        return this.desplazarPiezaSiNoCabeAlRotar;
    }
    
    public void setSonidoActivado(boolean sonidoActivado)
    {
        this.sonidoActivado = sonidoActivado;
    }
    
    public boolean getSonidoActivado()
    {
        return this.sonidoActivado;
    }
    
    //Desactiva el sonido:
    public void desactivarSonido()
    {
        this.setSonidoActivado(false);
    }
    
    //Activa el sonido:
    public void activarSonido()
    {
        this.setSonidoActivado(true);
    }
    
    //Alterna el sonido (lo activa o desactiva):
    public void alternarSonido()
    {
        if (this.getSonidoActivado())
        {
            this.desactivarSonido();
        }
        else { this.activarSonido(); }
    }
    
    public void setMusicaActivada(boolean musicaActivada)
    {
        this.musicaActivada = musicaActivada;
    }
    
    public boolean getMusicaActivada()
    {
        return this.musicaActivada;
    }
    
    //Desactiva la musica:
    public void desactivarMusica()
    {
        this.setMusicaActivada(false);
    }
    
    //Activa la musica:
    public void activarMusica()
    {
        this.setMusicaActivada(true);
    }
    
    //Alterna la musica (la activa o desactiva):
    public void alternarMusica()
    {
        if (this.getMusicaActivada())
        {
            this.desactivarMusica();
        }
        else { this.activarMusica(); }
    }

    //Define las teclas de funcionamiento:
    public void definirTeclas(int[] teclaArriba, int[] teclaAbajo, int[] teclaDerecha, int[] teclaIzquierda, int[] teclaRotarDerecha, int[] teclaRotarIzquierda, int[] teclaAceptar, int[] teclaPausa, int[] teclaSalir)
    {
        this.setTeclaArriba(teclaArriba);
        this.setTeclaAbajo(teclaAbajo);
        this.setTeclaDerecha(teclaDerecha);
        this.setTeclaIzquierda(teclaIzquierda);
        this.setTeclaRotarDerecha(teclaRotarDerecha);
        this.setTeclaRotarIzquierda(teclaRotarIzquierda);
        this.setTeclaAceptar(teclaAceptar);
        this.setTeclaPausa(teclaPausa);
        this.setTeclaSalir(teclaSalir);
    }
    
    //Modifica la dimension del tablero:
    public void modificarDimensionesTablero(byte ancho, byte alto)
    {
        this.getTablero().setAncho(ancho);
        this.getTablero().setAlto(alto);
        
        this.getPanel().setTablero(this.getTablero());
        
        //Vuelve a dimensionar la ventana principal del juego:
        Main.redimensionarVentana(); //Quiza se deba comentar esto si se utiliza un Applet.
        
        //Redimensiona la imagen de buffer:
        this.redimensionarImagenBuffer();
        
        //Actualiza visualmente el tablero:
        //this.representarTodo(this.getContenedorGrafico());
    }
   
    //Crea o redimensiona la imagen de buffer:
    public void redimensionarImagenBuffer()
    {
        int imagenAncho = 0, imagenAlto = 0; //Nota: Cuando se haga en J2ME u otro que no sea ni JFrame ni Applet, hay que darle valores a estas variables.
        
        if (configuracion.Otros.getTipoPrograma() == 0)
        {
            imagenAncho = Main.ventanaPrincipal.getWidth();
            imagenAlto = Main.ventanaPrincipal.getHeight();
        }
        else if (configuracion.Otros.getTipoPrograma() == 1)
        {
            imagenAncho = Main.appletPrincipal.getWidth();
            imagenAlto = Main.appletPrincipal.getHeight();
        }
        
        this.imagenBuffer = new BufferedImage(imagenAncho, imagenAlto, BufferedImage.TYPE_4BYTE_ABGR_PRE);
        
        this.limpiarImagenBuffer();
        
        this.representarTodo(this.getContenedorGrafico());
    }
    
    //Limpia la imagen de buffer:
    protected void limpiarImagenBuffer()
    {
        MotorGrafico.limpiarImagenBuffer(this.imagenBuffer, this.getPaleta());
    }
    
    public void setVelocidad(int velocidad)
    {
        if (velocidad <= this.velocidadMinima && velocidad >= this.velocidadMaxima) //La velocidad debe ser inferior o igual a la minima y superior o igual a la maxima, ya que la minima debe ser mas alta que la maxima (entre numeros mas altos, mas lento).
        {
            this.velocidad = velocidad;
        }
        else if (velocidad < this.velocidadMaxima)
        {
            this.velocidad = this.velocidadMaxima;
        }
        else
        {
            this.velocidad = this.velocidadMinima;
        }
    }
    
    public int getVelocidad()
    {
        return this.velocidad;
    }
    
    public void setVelocidadMinima(int velocidadMinima)
    {
        if (velocidadMinima > 0 && velocidadMinima > this.velocidadMaxima) //La velocidad minima debe ser superior a la maxima, ya que entre mas alto mas lento.
        {
            this.velocidadMinima = velocidadMinima;
        }
    }
    
    public int getVelocidadMinima()
    {
        return this.velocidadMinima;
    }

    public void setVelocidadMaxima(int velocidadMaxima)
    {
        if (velocidadMaxima > 0 && velocidadMaxima < this.velocidadMinima) //La velocidad maxima debe ser inferior a la minima, ya que entre mas alto mas lento.
        {
            this.velocidadMaxima = velocidadMaxima;
        }
    }
    
    public int getVelocidadMaxima()
    {
        return this.velocidadMaxima;
    }

    public void setIncrementoVelocidad(int incrementoVelocidad)
    {
        if (incrementoVelocidad > 0 && incrementoVelocidad <= this.velocidadMinima - this.velocidadMaxima)
        {
            this.incrementoVelocidad = incrementoVelocidad;
        }
    }
    
    public int getIncrementoVelocidad()
    {
        return this.incrementoVelocidad;
    }
    
    protected void subirVelocidad()
    {
        this.setVelocidad(this.getVelocidad() + this.getIncrementoVelocidad());
        
        //Si el juego ha comenzado, inicia el ciclo con la nueva velocidad:
        if (this.juegoComenzado)
        {
            this.pararCiclo();
            this.iniciarCiclo();
        }
    }

    protected void bajarVelocidad()
    {
        this.setVelocidad(this.getVelocidad() - this.getIncrementoVelocidad());
        
        //Si el juego ha comenzado, inicia el ciclo con la nueva velocidad:
        if (this.juegoComenzado)
        {
            this.pararCiclo();
            this.iniciarCiclo();
        }
    }
    
    public void setNivel(int nivel)
    {
        this.nivel = nivel;
        this.getPanel().setNivel(nivel);
    }
    
    public int getNivel()
    {
        return this.nivel;
    }
    
    protected void subirNivel()
    {
        this.setNivel(this.getNivel() + 1);
        
        //Sube la velocidad tambien (podria hacerse que solo lo hiciera cada X niveles, o en los pares):
        this.subirVelocidad();
    }
    
    protected void setPuntos(int puntos)
    {
        //Permitiremos numeros negativos por si se quisiera hacer un juego con magias o penalizaciones que restaran puntos:
        this.puntos = puntos;
        this.getPanel().setPuntos(this.getPuntos());
    }
    
    public int getPuntos()
    {
        return this.puntos;
    }
    
    protected void setPuntosPorPiezaPuesta(int puntosPorPiezaPuesta)
    {
        //Permitimos 0 puntos para posibles hechizos y castigos en un futuro:
        if (puntosPorPiezaPuesta >= 0)
        {
            this.puntosPorPiezaPuesta = puntosPorPiezaPuesta;
        }
    }
    
    protected int getPuntosPorPiezaPuesta()
    {
        return this.puntosPorPiezaPuesta;
    }

    protected void setPuntosPorLinea(int puntosPorLinea)
    {
        //Permitimos 0 puntos para posibles hechizos y castigos en un futuro:
        if (puntosPorLinea >= 0)
        {
            this.puntosPorLinea = puntosPorLinea;
        }
    }
    
    protected int getPuntosPorLinea()
    {
        return this.puntosPorLinea;
    }

    protected void setPuntosPorLineaAdicional(int puntosPorLineaAdicional)
    {
        //Permitimos 0 puntos para posibles hechizos y castigos en un futuro:
        if (puntosPorLineaAdicional >= 0)
        {
            this.puntosPorLineaAdicional = puntosPorLineaAdicional;
        }
    }
    
    protected int getPuntosPorLineaAdicional()
    {
        return this.puntosPorLineaAdicional;
    }
    
    protected void sumarPuntos(int puntos)
    {
        this.setPuntos(this.getPuntos() + puntos);
    }
    
    protected void setLineas(int lineas)
    {
        if (lineas >= 0)
        {
            this.lineas = lineas;
            this.getPanel().setLineas(lineas);
        }
    }
    
    public int getLineas()
    {
        return this.lineas;
    }

    protected void setLineasNivel(int lineasNivel)
    {
        if (lineasNivel >= 0)
        {
            this.lineasNivel = lineasNivel;
            this.getPanel().setLineasNivel(lineasNivel);
        }
    }
    
    public int getLineasNivel()
    {
        return this.lineasNivel;
    }
    
    public void setLineasNecesariasNivel(int lineasNecesariasNivel)
    {
        if (lineasNecesariasNivel >= 1)
        {
            this.lineasNecesariasNivel = lineasNecesariasNivel;
        }
    }
    
    public int getLineasNecesariasNivel()
    {
        return this.lineasNecesariasNivel;
    }
    
    protected void sumarLineas(int numeroLineas)
    {
        //Se permiten mas de 5 porque pueden haber piezas personalizadas:
        if (numeroLineas > 0)
        {
            this.setLineas(this.getLineas() + numeroLineas);
        }
    }

    protected void sumarLineasNivel(int numeroLineas)
    {
        //Se permiten mas de 5 porque pueden haber piezas personalizadas:
        if (numeroLineas > 0)
        {
            this.setLineasNivel(this.getLineasNivel() + numeroLineas);
        }
    }
    
    //Realiza una accion dependiendo de una tecla dada:
    public void procesarTecla(int tecla)
    {
        //Nota: Dejar entrar aunke el juego haya terminado, por si keremos movernos en algun menu futuro.
        //Nota: No pongo else para permitir que una tecla pueda hacer mas de una funcion.
        
        //Si se ha definido ignorar las teclas, lo hace:
        if (this.ignorarTeclas) { return; }
        
        boolean limpiarBuffer = false; //Indica si al final del metodo se debe limpiar la imagen de buffer.
        boolean repintarTodo = false; //Indica si al final del metodo se debe representar todo en pantalla.
        
        boolean impedirRotacion = false; //Indica si se debe impedir la rotacion (usado al salir del menu de pausa, para que no rote al aceptar la opcion de reanudar).
        
        //Tecla aceptar:
        if (this.teclaEnTeclas(tecla, this.getTeclaAceptar()))
        {
            //Si el menu principal existe y esta activo:
            if (this.getMenuPrincipal() != null)
            {
                if (this.getMenuPrincipal().getMostrar())
                {
                    //Acepta la opcion actual y procesa la accion:
                    this.getMenuPrincipal().aceptarOpcionActual();
                    this.procesarMenuPrincipal();

                    //Muestra el sonido pertinetne si asi se ha establecido:
                    if (this.getSonidoActivado()) { MotorSonido.menuOpcionAceptada(); }
                    
                    //Limpia la imagen de buffer para que no queden restos del texto anterior:
                    limpiarBuffer = true;

                    //Representa el menu, si se debe hacer (y todo lo demas, para que se repinte todo bien):
                    if (this.getMenuPrincipal().getMostrar()) { repintarTodo = true; } //this.menuPrincipal.dibujar(this.getContenedorGrafico()); }
                }
            }
            
            //Si el menu de pausa existe y esta activo:
            if (this.getMenuPausa() != null)
            {
                if (this.getMenuPausa().getMostrar())
                {
                    //Acepta la opcion actual y procesa la accion:
                    this.getMenuPausa().aceptarOpcionActual();
                    this.procesarMenuPausa();

                    //Muestra el sonido pertinetne si asi se ha establecido:
                    if (this.getSonidoActivado()) { MotorSonido.menuOpcionAceptada(); }
                    
                    //Limpia la imagen de buffer para que no queden restos del texto anterior:
                    limpiarBuffer = true;
                    
                    //Representa el menu, si se debe hacer (y todo lo demas, para que se repinte todo bien):
                    if (this.getMenuPausa().getMostrar()) { repintarTodo = true; } //this.menuPrincipal.dibujar(this.getContenedorGrafico()); }
                    
                    //Impide la rotacion (por que no rote si al aceptar hemos salido del menu):
                    impedirRotacion = true;
                }
            }
        }

        //Tecla arriba:
        if (this.teclaEnTeclas(tecla, this.getTeclaArriba()))
        {
            //Si el menu principal existe y esta activo:
            if (this.getMenuPrincipal() != null)
            {
                if (this.getMenuPrincipal().getMostrar())
                {
                    //Sube una opcion del menu:
                    this.getMenuPrincipal().subirOpcion();
                    
                    //Muestra el sonido pertinetne si asi se ha establecido:
                    if (this.getSonidoActivado()) { MotorSonido.menuOpcionCambiada(); }

                    //Limpia la imagen de buffer para que no queden restos del texto anterior:
                    limpiarBuffer = true;
                    
                    //Representa el menu, si se debe hacer (y todo lo demas, para que se repinte todo bien):
                    if (this.getMenuPrincipal().getMostrar()) { repintarTodo = true; } //this.menuPrincipal.dibujar(this.getContenedorGrafico()); }
                }
            }

            //Si el menu de pausa existe y esta activo:
            if (this.getMenuPausa() != null)
            {
                if (this.getMenuPausa().getMostrar())
                {
                    //Sube una opcion del menu:
                    this.getMenuPausa().subirOpcion();

                    //Muestra el sonido pertinetne si asi se ha establecido:
                    if (this.getSonidoActivado()) { MotorSonido.menuOpcionCambiada(); }
                    
                    //Limpia la imagen de buffer para que no queden restos del texto anterior:
                    limpiarBuffer = true;
                    
                    //Representa el menu, si se debe hacer (y todo lo demas, para que se repinte todo bien):
                    if (this.getMenuPausa().getMostrar()) { repintarTodo = true; } //this.menuPrincipal.dibujar(this.getContenedorGrafico()); }
                }
            }
        }
        
        //Tecla abajo:
        if (this.teclaEnTeclas(tecla, this.getTeclaAbajo()))
        {
            //Si el juego ha comenzado y no esta pausado:
            if (this.getJuegoComenzado() && !this.getJuegoPausado())
            {
                //Mueve la pieza hacia abajo:
                if (this.piezaActual != null)
                {
                    this.piezaActual.moverAbajo(this.getTablero());
                    //this.piezaActual.dibujar(this.getContenedorGrafico(), this.imagenBuffer);///////
                    ///////this.representarTodo(this.getContenedorGrafico()); ///////
                    repintarTodo = true; ///////////
                }
            }
            //Si el menu principal existe y esta activo:
            if (this.getMenuPrincipal() != null)
            {
                if (this.getMenuPrincipal().getMostrar())
                {
                    //Baja una opcion del menu:
                    this.getMenuPrincipal().bajarOpcion();

                    //Muestra el sonido pertinetne si asi se ha establecido:
                    if (this.getSonidoActivado()) { MotorSonido.menuOpcionCambiada(); }
                    
                    //Limpia la imagen de buffer para que no queden restos del texto anterior:
                    limpiarBuffer = true;
                    
                    //Representa el menu, si se debe hacer (y todo lo demas, para que se repinte todo bien):
                    if (this.getMenuPrincipal().getMostrar()) { repintarTodo = true; } //this.menuPrincipal.dibujar(this.getContenedorGrafico()); }
                }
            }
            
            //Si el menu de pausa existe y esta activo:
            if (this.getMenuPausa() != null)
            {
                if (this.getMenuPausa().getMostrar())
                {
                    //Baja una opcion del menu:
                    this.getMenuPausa().bajarOpcion();

                    //Muestra el sonido pertinetne si asi se ha establecido:
                    if (this.getSonidoActivado()) { MotorSonido.menuOpcionCambiada(); }
                    
                    //Limpia la imagen de buffer para que no queden restos del texto anterior:
                    limpiarBuffer = true;
                    
                    //Representa el menu, si se debe hacer (y todo lo demas, para que se repinte todo bien):
                    if (this.getMenuPausa().getMostrar()) { repintarTodo = true; } //this.menuPrincipal.dibujar(this.getContenedorGrafico()); }
                }
            }
        }
        
        //Tecla derecha:
        if (this.teclaEnTeclas(tecla, this.getTeclaDerecha()))
        {
            //Si el juego ha comenzado y no esta pausado:
            if (this.getJuegoComenzado() && !this.getJuegoPausado())
            {
                //Mueve la pieza a la derecha:
                if (this.piezaActual != null)
                {
                    this.piezaActual.moverDerecha(this.getTablero());
                    //this.piezaActual.dibujar(this.getContenedorGrafico(), this.imagenBuffer);///////
                    //this.representarTodo(this.getContenedorGrafico()); ///////
                    repintarTodo = true; //////////
                }
            }
        }
        
        //Tecla izquierda:
        if (this.teclaEnTeclas(tecla, this.getTeclaIzquierda()))
        {
            //Si el juego ha comenzado y no esta pausado:
            if (this.getJuegoComenzado() && !this.getJuegoPausado())
            {
                //Mueve la pieza a la izquierda:
                if (this.piezaActual != null)
                {
                    this.piezaActual.moverIzquierda(this.getTablero());
                    //this.piezaActual.dibujar(this.getContenedorGrafico(), this.imagenBuffer);///////
                    //this.representarTodo(this.getContenedorGrafico()); ///////
                    repintarTodo = true; //////////
                }
            }
        }
        
        //Tecla rotar derecha:
        if (!impedirRotacion && this.teclaEnTeclas(tecla, this.getTeclaRotarDerecha()))
        {
            //Si el juego ha comenzado y no esta pausado:
            if (this.getJuegoComenzado() && !this.getJuegoPausado())
            {
                //Rota la pieza hacia la derecha:
                if (this.piezaActual != null)
                {
                    boolean piezaRotada = this.piezaActual.rotarDerecha(this.getTablero(), this.getDesplazarPiezaSiNoCabeAlRotar());
                    
                    //Si esta definido utilizar sonido, reproduce el sonido pertinente:
                    if (this.getSonidoActivado() && piezaRotada) { MotorSonido.piezaRotada(); }
                    
                    //this.piezaActual.dibujar(this.getContenedorGrafico(), this.imagenBuffer); ///////
                    //this.representarTodo(this.getContenedorGrafico()); ///////
                    repintarTodo = true; //////////
                }
            }
        }
        
        //Tecla rotar izquierda:
        if (!impedirRotacion && this.teclaEnTeclas(tecla, this.getTeclaRotarIzquierda()))
        {
            //Si el juego ha comenzado y no esta pausado:
            if (this.getJuegoComenzado() && !this.getJuegoPausado())
            {
                //Rota la pieza hacia la izquierda:
                if (this.piezaActual != null)
                {
                    boolean piezaRotada = this.piezaActual.rotarIzquierda(this.getTablero(), this.getDesplazarPiezaSiNoCabeAlRotar());

                    //Si esta definido utilizar sonido, reproduce el sonido pertinente:
                    if (this.getSonidoActivado() && piezaRotada) { MotorSonido.piezaRotada(); }
                    
                    //this.piezaActual.dibujar(this.getContenedorGrafico(), this.imagenBuffer); /////
                    //this.representarTodo(this.getContenedorGrafico()); ///////
                    repintarTodo = true; ////////////
                }
            }
        }
        
        //Tecla pausar:
        boolean menuPausaMostradoAhora = false;
        if (this.teclaEnTeclas(tecla, this.getTeclaPausa()))
        {
            //Si el juego ha comenzado y no esta pausado:
            if (this.getJuegoComenzado() && !this.getJuegoPausado())
            {
                //Si el menu de pausa existe y no esta activo:
                if (this.getMenuPausa() != null && !this.getMenuPausa().getMostrar())
                {
                    //Si el menu principal existe y no esta activo:
                    if (this.getMenuPrincipal() != null && !this.getMenuPrincipal().getMostrar())
                    {
                        //Pausa el juego:
                        this.pausarJuego();
                        
                        //Define que se acaba de mostrar el menu:
                        menuPausaMostradoAhora = true;
                    }
                }
            }
        }
                
        //Tecla salir:
        if (this.teclaEnTeclas(tecla, this.getTeclaSalir()))
        {
            //Si el juego ha comenzado y no esta pausado:
            if (this.getJuegoComenzado() && !this.getJuegoPausado())
            {
                //Si el menu de pausa existe y no esta activo:
                if (this.getMenuPausa() != null && !this.getMenuPausa().getMostrar())
                {
                    //Si el menu principal existe y no esta activo:
                    if (this.getMenuPrincipal() != null && !this.getMenuPrincipal().getMostrar())
                    {
                        //Pausa el juego:
                        this.pausarJuego();
                        
                        /////limpiarBuffer = true;
                        
                        repintarTodo = true;
                    }
                }
            }
            
            //Si el menu principal existe y esta activo:
            if (this.getMenuPrincipal() != null && this.getMenuPrincipal().getMostrar())
            {
                //Si se esta dentro de un submenu:
                if (this.getMenuPrincipal().obtenerSubmenu(this.getMenuPrincipal().getOpcionActualIndiceSinRecursividad()) != null && this.getMenuPrincipal().getOpcionAceptada())
                {
                    //Sale del submenu:
                    this.getMenuPrincipal().cancelarOpcionActual();
                    //Representa el menu, si se debe hacer (y todo lo demas, para que se repinte todo bien):
                    if (this.getMenuPrincipal().getMostrar()) { repintarTodo = true; } //this.menuPrincipal.dibujar(this.getContenedorGrafico()); }
                }
                //...y si no, si el menu esta en la raiz:
                else
                {
                    //Selecciona la opcion de salir:
                    this.getMenuPrincipal().setOpcionActual("FINALIZAR");
                    //Representa el menu, si se debe hacer (y todo lo demas, para que se repinte todo bien):
                    if (this.getMenuPrincipal().getMostrar()) { repintarTodo = true; } //this.menuPrincipal.dibujar(this.getContenedorGrafico()); }
                }
                
                limpiarBuffer = true; /////////
            }
            
            //Si el menu de pausa existe y esta activo:
            if (this.getMenuPausa() != null && this.getMenuPausa().getMostrar())
            {
                //Si se esta dentro de un submenu:
                if (this.getMenuPausa().obtenerSubmenu(this.getMenuPausa().getOpcionActualIndiceSinRecursividad()) != null && this.getMenuPausa().getOpcionAceptada())
                {
                    //Sale del submenu:
                    this.getMenuPausa().cancelarOpcionActual();
                    //Representa el menu, si se debe hacer (y todo lo demas, para que se repinte todo bien):
                    if (this.getMenuPausa().getMostrar()) { repintarTodo = true; } //this.menuPrincipal.dibujar(this.getContenedorGrafico()); }
                }
                //...y si no, si el menu esta en la raiz:
                else
                {
                    if (!menuPausaMostradoAhora)
                    {
                        //Reanuda el juego:
                        this.reanudarJuego();
                        
                        repintarTodo = true; ////////
                    }
                }
                
                limpiarBuffer = true; ///////
            }
        }
        
        if (limpiarBuffer) { this.limpiarImagenBuffer(); }
        if (repintarTodo) { this.representarTodo(this.getContenedorGrafico()); }
    }

    
    //Procesa la opcion seleccionada del menu principal:
    protected void procesarMenuPrincipal()
    {
        //Si el menu principal existe, esta activo y la opcion ha sido seleccionada: 
        if (this.getMenuPrincipal() != null)
        {
            if (this.getMenuPrincipal().getMostrar() && this.getMenuPrincipal().getOpcionAceptada())
            {
                String opcionSeleccionada = this.getMenuPrincipal().getOpcionActualValor();
                
                //Si se ha escogido iniciar el juego:
                if (opcionSeleccionada.toUpperCase().equals("INICIAR"))
                {
                    //Si no se ha comenzado, comienza el juego:
                    if (!this.getJuegoComenzado()) { this.ocultarMenuPrincipal(); this.iniciarJuego(); }
                    //...si no, lo reinicia:
                    else { this.ocultarMenuPrincipal(); this.reiniciarJuego(); }
                }
                //...o si se escogido opciones:
                else if (opcionSeleccionada.toUpperCase().equals("OPCIONES"))
                {
                    //No hace nada, ya se encargara el menu de entrar en el submenu.
                }
                //...o si se ha escogido salir:
                else if (opcionSeleccionada.toUpperCase().equals("FINALIZAR"))
                {
                    //No hace nada, ya se encargara el menu de entrar en el submenu.
                }
                //...o si se ha escogido confirmar salir:
                else if (opcionSeleccionada.toUpperCase().equals("FINALIZAR_SI"))
                {
                    //Si se ha aceptado la opcion, procede:
                    if (this.getMenuPrincipal().getOpcionAceptadaSubmenu())
                    {
                        //Si no ha acabado el juego, lo acaba (en realidad es una tonteria):
                        if (this.getJuegoComenzado()) { this.finalizarJuego(); }
                        //Sale del programa:
                        System.exit(0);
                    }
                }
                //...o si se ha escogido no salir (volver al menu principal):
                else if (opcionSeleccionada.toUpperCase().equals("FINALIZAR_NO"))
                {
                    //Si se ha aceptado la opcion, procede:
                    if (this.getMenuPrincipal().getOpcionAceptadaSubmenu())
                    {
                        //Sale del submenu:
                        this.getMenuPrincipal().cancelarOpcionActual();
                    }
                }
            }
        }
    }
    
    
    //Procesa la opcion seleccionada del menu de pausa:
    protected void procesarMenuPausa()
    {
        //Si el menu de pausa existe, esta activo y la opcion ha sido seleccionada: 
        if (this.getMenuPausa() != null)
        {
            if (this.getMenuPausa().getMostrar() && this.getMenuPausa().getOpcionAceptada())
            {
                String opcionSeleccionada = this.getMenuPausa().getOpcionActualValor();
                
                //Si se ha escogido continuar:
                if (opcionSeleccionada.toUpperCase().equals("CONTINUAR"))
                {
                    //Reanuda el juego:
                    this.reanudarJuego();
                }
                //...o si se ha escogido salir:
                else if (opcionSeleccionada.toUpperCase().equals("TERMINAR"))
                {
                    //No hace nada, ya se encargara el menu de entrar en el submenu.
                }
                //...o si se ha escogido confirmar salir:
                else if (opcionSeleccionada.toUpperCase().equals("FINALIZAR_SI"))
                {
                    //Si se ha aceptado la opcion, procede:
                    if (this.getMenuPausa().getOpcionAceptadaSubmenu())
                    {
                        //Sale del submenu:
                        this.getMenuPausa().cancelarOpcionActual();
                        
                        //Si no ha acabado el juego, lo acaba:
                        if (this.getJuegoComenzado()) { this.finalizarJuego(); }
                                                
                        //Reanuda el juego (y oculta el menu de pausa):
                        //this.reanudarJuego();
                        this.iniciarJuego();/////////
                        
                        //Oculta el menu de pausa:
                        this.ocultarMenuPausa();

                        //Muestra el menu principal:
                        this.mostrarMenuPrincipal();
                    }
                }
                //...o si se ha escogido no salir (volver al menu de pausa):
                else if (opcionSeleccionada.toUpperCase().equals("FINALIZAR_NO"))
                {
                    //Si se ha aceptado la opcion, procede:
                    if (this.getMenuPausa().getOpcionAceptadaSubmenu())
                    {
                        //Sale del submenu:
                        this.getMenuPausa().cancelarOpcionActual();
                    }
                }
            }
        }
    }
    
    
    //Devuelve si una tecla esta en el vector de teclas enviado o no:
    protected boolean teclaEnTeclas(int tecla, int teclas[])
    {
        boolean encontrada = false;
        for (int x = 0; x < teclas.length; x++)
        {
            if (teclas[x] == tecla)
            {
                encontrada = true;
                break;
            }
        }
        return encontrada;
    }

    //Devuelve si una tecla esta en alguno de los vectores de teclas del juego:
    public boolean teclaEnTeclasTodas(int tecla)
    {
        boolean encontrada = false;
        
        if (this.teclaEnTeclas(tecla, this.getTeclaArriba())) { encontrada = true; }
        if (this.teclaEnTeclas(tecla, this.getTeclaAbajo())) { encontrada = true; }
        if (this.teclaEnTeclas(tecla, this.getTeclaDerecha())) { encontrada = true; }
        if (this.teclaEnTeclas(tecla, this.getTeclaIzquierda())) { encontrada = true; }
        if (this.teclaEnTeclas(tecla, this.getTeclaRotarDerecha())) { encontrada = true; }
        if (this.teclaEnTeclas(tecla, this.getTeclaRotarIzquierda())) { encontrada = true; }
        if (this.teclaEnTeclas(tecla, this.getTeclaAceptar())) { encontrada = true; }
        if (this.teclaEnTeclas(tecla, this.getTeclaPausa())) { encontrada = true; }
        if (this.teclaEnTeclas(tecla, this.getTeclaSalir())) { encontrada = true; }
            
        return encontrada;
    }
    
    //Comprueba si se ha perdido el juego o no:
    public boolean comprobarGameOver()
    {
        //Si se ha perdido, finalizar juego y acordarse del GameOver.
        return true; //CAMBIAR!!!
    }
 
    //Devuelve si el juego ha comenzado o no:
    public boolean getJuegoComenzado()
    {
        return this.juegoComenzado;
    }

    //Devuelve si el juego esta pausado o no:
    public boolean getJuegoPausado()
    {
        return this.juegoPausado;
    }
    
    //Restaura el valor de las propiedades del objeto:
    public void restaurarValores()
    {
        //Vacia el tablero, por si acaso:
        this.getTablero().vaciar();
        
        //Varia el panel, por si acaso:
        this.getPanel().vaciar();
        
        //Vacia el grafico de backup del motor grafico:
        //MotorGrafico.inicializarGraficoBackup();

        //Vacia la imagen de buffer:
        //this.limpiarImagenBuffer();
        
        //"Resetea" las pieza actual y siguiente, por si acaso:
        this.piezaActual = null;
        this.piezaSiguiente = null;
        
        //Define que ninguna pieza ha colisionado en ningun ciclo anterior:
        this.piezaColisionada = false;

        //Pone el nivel al primero:
        this.setNivel(0);
        
        //Vuelve la puntuacion a cero:
        this.setPuntos(0);
        
        //Pone el numero de lineas hechas a cero:
        this.setLineas(0);
        
        //Pone el numero de lineas hechas en el nivel a cero:
        this.setLineasNivel(0);
    }
    
    //Inicia el juego:
    public void iniciarJuego()
    {
        //Si el juego ya ha comenzado, sale de la funcion:
        if (this.getJuegoComenzado()) { return; }

        //Reanuda el juego (por si acaso):
        this.reanudarJuego();
        
        //Define como que ya ha comenzado el juego:
        this.juegoComenzado = true;

        //Restaura el valor de las propiedades:
        this.restaurarValores();
        
        //Comienza los ciclos:
        this.iniciarCiclo();
    }
    
    //Finaliza el juego:
    public void finalizarJuego()
    {
        //Si el juego no habia comenzado, sale de la funcion:
        if (!this.juegoComenzado) { return; }
        
        //Define como que no ha comenzado el juego:
        this.juegoComenzado = false;
        
        //Acaba los ciclos:
        this.pararCiclo();
       
        //Restaura el valor de las propiedades:
        this.restaurarValores();
    }
    
    //Pausa el juego:
    public void pausarJuego()
    {
        if (this.getJuegoComenzado() && !this.getJuegoPausado())
        {
            this.juegoPausado = true;

            //Muestra el menu de pausa:
            this.mostrarMenuPausa();
            
            //Inicia el repintado automatico (para que haga auto-redraw):
            this.iniciarRepintado();
            
            //Para el ciclo:
            this.pararCiclo();
        }
    }
    
    //Reanuda el juego (quita la pausa):
    public void reanudarJuego()
    {
        if (this.getJuegoPausado()) //if (this.getJuegoComenzado() && this.getJuegoPausado())
        {
            this.juegoPausado = false;
            
            //Oculta el menu de pausa:
            this.ocultarMenuPausa();
            
            //Inicia el ciclo (o lo reinicia):
            this.iniciarCiclo();
        }
    }
    
    //Reinicia el juego:
    public void reiniciarJuego()
    {
        this.finalizarJuego();
        this.iniciarJuego();
    }
  
    //Retorna una pieza aleatoria de entre las piezas base posibles:
    protected Pieza obtenerPieza()
    {
        int indiceAleatorio = new Random().nextInt(this.getPiezasBase().length);
        return this.getPiezasBase()[indiceAleatorio];
    }
    
    //Introduce una pieza en el juego (la que el usuario va a controlar):
    protected void sacarPieza()
    {
        int[][] piezaForma;
        byte piezaAncho;
        
        //Si es la primera, la saca aleatoriamente:
        if (this.piezaSiguiente == null)
        {
            Pieza piezaAleatoria = this.obtenerPieza();
            piezaForma = piezaAleatoria.getForma();
            piezaAncho = piezaAleatoria.getAncho();
        }
        //...pero si no, saca la que habia en siguiente y escoge otra siguiente:
        else
        {
            piezaForma = piezaSiguiente.getForma();
            piezaAncho = piezaSiguiente.getAncho();
        }
        
        //Pone la pieza actual elegida (en su lugar correcto) y la siguiente:
        byte piezaPosicionHorizontal = (byte) (this.getTablero().getAncho() / 2 - piezaAncho / 2);
        this.piezaActual = new Pieza(piezaForma, this.getPaleta(), piezaPosicionHorizontal);
        //this.piezaActual = this.piezaSiguiente;
        this.piezaSiguiente = this.obtenerPieza();
        
        //Inserta la pieza siguiente en el panel:
        this.getPanel().insertarPieza(this.piezaSiguiente);
    }
    
    //Hace el ciclo del juego (contiene toda la logica):
    protected void cicloJuego()
    {
        boolean sacarPieza = false;
        
        //Si el juego esta en pausa, no hace el ciclo:
        if (this.juegoPausado) { representarTodo(this.getContenedorGrafico()); return; } //Antes de salir hace un repintado por si es necesario (seria mejor buscar otro metodo mas eficiente).
        //...o si el juego no ha comenzado, no hace el ciclo:
        if (!this.getJuegoComenzado()) { return; }
        else //else innecesario.
        {
            //Si la pieza colisiono la anterior vez:
            if (this.piezaColisionada)
            {
                //Si continua habiendo colision en la nueva posicion, se agrega al tablero:
                if (piezaActual.calcularColision(this.getTablero()))
                {
                    //Desactiva las posibles teclas residuales del lector de teclado:
                    //Teclado.vaciarTeclas();
                    
                    this.sumarPuntos(this.getPuntosPorPiezaPuesta());
                    
                    this.piezaColisionada = false;
                
                    this.getTablero().agregarPieza(piezaActual);
                
                    ///////this.piezaActual = null;
                    sacarPieza = true; //Define que hay que sacar una pieza.
                    
                    //Si esta definido utilizar sonido, reproduce el sonido pertinente:
                    if (this.getSonidoActivado()) { MotorSonido.piezaColisionada(); }
                    
                    //Representa el tablero:
                    ///////this.getTablero().dibujar(this.getContenedorGrafico(), this.imagenBuffer);
                    
                    //Calcula si se ha hecho linea:
                    //byte lineasHechas = this.getTablero().procesarLineas();
                    byte lineasHechas = this.getTablero().lineasHechas();
                    if (lineasHechas > 0)
                    {
                        this.sumarLineas(lineasHechas);
                        this.sumarLineasNivel(lineasHechas);
                        this.sumarPuntos(lineasHechas * this.getPuntosPorLinea());
                        this.sumarPuntos((lineasHechas - 1) * this.getPuntosPorLineaAdicional());
                        
                        //Guarda en que filas se han hecho lineas:
                        boolean[] filasLinea = this.getTablero().lineasHechasDonde();
                        
                        //Reproduce una animacion:
                        if (!this.menuPrincipal.getMostrar()) { this.ignorarTeclas = true; } //Si no esta en el menu principal, se ignoran las teclas mientras haya animacion.
                        MotorAnimaciones.hacerLineas(this.getContenedorGrafico(), this.imagenBuffer, this.getTablero(), filasLinea);
                        if (!this.menuPrincipal.getMostrar()) { this.ignorarTeclas = false; }
                        
                        //Procesa las lineas hechas:
                        this.getTablero().procesarLineas();
                        
                        //Si esta definido utilizar sonido, reproduce el sonido pertinente:
                        if (this.getSonidoActivado()) { MotorSonido.lineaHecha(); }
                        
                        //Pasar los niveles que sean necesarios, segun las lineas hechas en el nivel actual:
                        while (this.getLineasNivel() >= this.lineasNecesariasNivel)
                        {
                            this.setLineasNivel(this.getLineasNivel() - this.lineasNecesariasNivel);
                            this.subirNivel();
                        }
                    }
                    
                    //Calcula si se ha perdido:
                    if (this.getTablero().estaLleno())
                    {
                        //FALTA: Notificar del fin del juego!!!
                        this.reiniciarJuego(); //PRUEBA!!!
                    }
                }
                //...pero si ya no colisiona, se deja otra oportunidad la siguiente vez (para que tampoco se solidifique a la primera):
                else
                {
                    this.piezaColisionada = false;
                } 
            }
            
            //Si no hay pieza actual o se ha definido sacar una pieza, saca una:
            if (this.piezaActual == null || sacarPieza) { this.sacarPieza(); }
            
            //////////this.representarTodo(this.getContenedorGrafico());
            
            //Si la pieza ha colisionado, lo indica para el proximo ciclo:
            if (this.piezaActual != null && this.piezaActual.calcularColision(this.getTablero()))
            {
                this.piezaColisionada = true;
            }
            //...pero si no:
            else
            {
                //Hace caer la pieza:
                if (this.piezaActual != null) { this.piezaActual.moverAbajo(this.getTablero()); }
            }
        }
    }

    //Para el timer que llama al ciclo del juego:
    protected void pararCiclo()
    {
        if (this.timerJuego != null)
        {
            this.timerJuego.cancel();
            this.timerJuego.purge();
            this.timerJuego = null; //Si no pongo esto, peta al llamar a scheduleAtFixedRate() (?).
        }
    }

    //Comienza el timer que llama al ciclo del juego:
    protected void iniciarCiclo()
    {
        //Si ya existe otro timer, lo para primero:
        if (this.timerJuego != null) { this.pararCiclo(); }
            
        this.timerJuego = new Timer();
        TimerJuego tarea = new TimerJuego(this);
        this.timerJuego.scheduleAtFixedRate(tarea, 0, this.getVelocidad());
    }

    //Para el timer que llama al repintado del juego:
    protected void pararRepintado()
    {
        if (this.timerRepintado != null)
        {
            this.timerRepintado.cancel();
            this.timerRepintado.purge();
            this.timerRepintado = null; //Si no pongo esto, peta al llamar a scheduleAtFixedRate() (?).
        }
    }

    //Comienza el timer que llama al repintado del juego:
    protected void iniciarRepintado()
    {
        //Si ya existe otro timer, lo para primero:
        if (this.timerRepintado != null) { this.pararRepintado(); }
            
        this.timerRepintado = new Timer();
        TimerRepintado tarea = new TimerRepintado(this);
        this.timerRepintado.scheduleAtFixedRate(tarea, 0, this.getVelocidad());
    }

    //Para el timer que llama al lector del teclado:
    protected void pararLectorTeclado()
    {
        if (this.timerLectorTeclado != null)
        {
            this.timerLectorTeclado.cancel();
            this.timerLectorTeclado.purge();
            this.timerLectorTeclado = null; //Si no pongo esto, peta al llamar a scheduleAtFixedRate() (?).
        }
    }

    //Comienza el timer que llama al lector del teclado:
    protected void iniciarLectorTeclado()
    {
        //Si ya existe otro timer, lo para primero:
        if (this.timerLectorTeclado != null) { this.pararLectorTeclado(); }
            
        this.timerLectorTeclado = new Timer();
        TimerTeclado tarea = new TimerTeclado();
        this.timerLectorTeclado.scheduleAtFixedRate(tarea, 0, this.getVelocidadLectorTeclado());
    }
    
    //Muestra el menu principal:
    public void mostrarMenuPrincipal()
    {
        //Nota: Dejare mostrar el menu principal aun con el juego comenzado.
        this.getMenuPrincipal().setMostrar(true);
        this.getMenuPrincipal().dibujar(this.getContenedorGrafico(), this.imagenBuffer);
    }
    
    //Oculta el menu principal:
    public void ocultarMenuPrincipal()
    {
        //Nota: Dejare mostrar el menu principal aun con el juego comenzado.
        this.getMenuPrincipal().setMostrar(false);
        this.limpiarImagenBuffer();
        this.representarTodo(this.getContenedorGrafico());
    }

    //Muestra el menu de pausa:
    public void mostrarMenuPausa()
    {
        this.getMenuPausa().setMostrar(true);
        this.getMenuPausa().dibujar(this.getContenedorGrafico(), this.imagenBuffer);
    }
    
    //Oculta el menu de pausa:
    public void ocultarMenuPausa()
    {
        this.getMenuPausa().setMostrar(false);
        this.limpiarImagenBuffer();
        this.representarTodo(this.getContenedorGrafico());
    }

    //Actualiza el panel con la puntuacion, la pieza siguiente, etc. utilizando una imagen de buffer:
    protected void actualizarPanel(Graphics contenedor, BufferedImage imagenBuffer)
    {
        this.getPanel().dibujar(contenedor, imagenBuffer);
    }
    
    //Actualiza el panel con la puntuacion, la pieza siguiente, etc:
    protected void actualizarPanel(Graphics contenedor)
    {
        this.getPanel().dibujar(contenedor);
    }

    //Dibuja una imagen de buffer en un contenedor grafico:
    protected void dibujarImagenBuffer(Graphics contenedor, BufferedImage imagenBuffer)
    {
        MotorGrafico.dibujarImagenBuffer(contenedor, imagenBuffer);
    }
    
    //Dibuja el juego (tablero, pieza y panel):
    protected void representarTodo(Graphics contenedor)
    {
        //Guarda una copia de seguridad del contenedor grafico:
        Graphics contenedorBackup = contenedor;
        
        //Recoge el contenedor grafico de la imagen de buffer:
        contenedor = this.imagenBuffer.getGraphics();
        
        //Dibuja el tablero:
        this.getTablero().dibujar(contenedor);
        
        //Dibuja la pieza:
        if (this.piezaActual != null) { this.piezaActual.dibujar(contenedor); }
        
        //Actualiza el panel:
        this.actualizarPanel(contenedor);

        //Si el menu principal debe mostrarse, se muestra:
        if (this.getMenuPrincipal().getMostrar()) { this.getMenuPrincipal().dibujar(contenedor); }
        
        //Si el menu de pausa debe mostrarse, se muestra:
        if (this.getMenuPausa().getMostrar()) { this.getMenuPausa().dibujar(contenedor); }

        //Vuelca la imagen de backup en el contenedor grafico original:
        this.dibujarImagenBuffer(contenedorBackup, imagenBuffer);
    }
}
