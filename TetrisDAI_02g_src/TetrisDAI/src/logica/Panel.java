/**
 *
 * @author Joan Alba Maldonado
 */

//Falta: en totes les classes, impedir ficar valors no possibles en els sets (valors negatius, etc).

//FALTA: poner toString

package logica;

import java.awt.Graphics;

import java.awt.image.BufferedImage;

import gui.MotorGrafico;


public class Panel
{
    protected Pieza[] piezasBase; //Piezas base que utiliza el panel.
    
    protected Tablero tablero; //Tablero que utiliza el panel (para calcular donde situarse).
    
    protected byte left; //Posicion horizontal del panel.
    protected byte top; //Posicion vertical del panel.
    
    protected byte ancho; //Ancho del panel.
    protected byte alto; //Alto del panel.

    protected Paleta paleta; //Paleta que utiliza el panel.
    
    protected int[][] mapa; //Mapa del panel.
    
    protected Pieza piezaInsertada; //Pieza que esta insertada en el panel.

    protected final int colorTexto = configuracion.Otros.getNumeroColorPanelTexto(); //Numero de color del texto del panel.
    
    protected final int color = configuracion.Otros.getNumeroColorPanel(); //Numero que marca un vacio para el panel (color de fondo del panel).
    
    protected final int VACIO = configuracion.Otros.getNumeroVacio(); //Numero que marca un vacio para la pieza.
    
    protected final byte margen = 1; //Numero de casillas de margen (que separaran a la pieza que esta dentro del panel del borde de este, y al panel del tablero).
    
    protected int nivel = 0; //Numero de nivel.
    
    protected int puntos = 0; //Puntuacion obtenida.
    
    protected int lineas = 0; //Numero de lineas hechas.
    protected int lineasNivel = 0; //Numero de lineas hecha en el nivel actual.
    
    public Panel(Pieza[] piezasBase, Paleta paleta, Tablero tablero)
    {
        this.setPiezasBase(piezasBase);
        this.setPaleta(paleta);
        this.setTablero(tablero);
    }

    public int getColorTexto()
    {
        return this.colorTexto;
    }
    
    public byte getMargen()
    {
        return this.margen;
    }
    
    public void setPaleta(Paleta paleta)
    {
        this.paleta = paleta;
    }
    
    public Paleta getPaleta()
    {
        return this.paleta;
    }
    
    public void setPiezasBase(Pieza[] piezasBase)
    {
        this.piezasBase = piezasBase;
        byte[] anchoAltoMayores = this.buscarAnchoAltoMayores(piezasBase);
        this.setAncho(anchoAltoMayores[0]);
        this.setAlto(anchoAltoMayores[1]);
        this.mapa = new int[this.getAlto()][this.getAncho()];
        this.vaciarMapa();
    }
    
    public Pieza[] getPiezasBase()
    {
        return this.piezasBase;
    }
    
    public void setTablero(Tablero tablero)
    {
        this.tablero = tablero;
        this.setLeft(this.getTablero().getAncho());
        this.setTop((byte) 0);
    }
    
    public Tablero getTablero()
    {
        return this.tablero;
    }
    
    public void setLeft(byte left)
    {
        this.left = (byte) (left + this.margen);
    }
    
    public byte getLeft()
    {
        return this.left;
    }
    
    public void setTop(byte top)
    {
        this.top = (byte) (top + this.margen);
    }
    
    public byte getTop()
    {
        return this.top;
    }
    
    public void setAncho(byte ancho)
    {
        this.ancho = (byte) (ancho + this.margen * 2);
    }
    
    public byte getAncho()
    {
        return this.ancho;
    }
    
    public void setAlto(byte alto)
    {
        this.alto = (byte) (alto + this.margen * 2);
    }
    
    public byte getAlto()
    {
        return this.alto;
    }
    
    public int[][] getMapa()
    {
        return this.mapa;
    }
    
    public void insertarPieza(Pieza pieza)
    {
        this.setPiezaInsertada(pieza);
    }
    
    public void setPiezaInsertada(Pieza pieza) //Nota: Faltaria insertarla en las piezasBase si la pieza enviada no esta en ellas, pero lo dejare asi.
    {
        this.piezaInsertada = pieza;
        
        //Si la pieza es mas grande que lo que se pensaba (que cualquiera de las piezasBase), se actualiza la dimension:
        if (pieza.getAncho() > this.getAncho()) { this.setAncho(pieza.getAncho()); }
        if (pieza.getAlto() > this.getAlto()) { this.setAlto(pieza.getAlto()); }
        
        //Actualiza el mapa con la nueva pieza:
        this.insertarPiezaEnMapa(pieza);
    }
    
    public Pieza getPiezaInsertada()
    {
        return this.piezaInsertada;
    }

    public void setNivel(int nivel)
    {
        this.nivel = nivel;
    }
    
    public int getNivel()
    {
        return this.nivel;
    }
    
    public void setPuntos(int puntos)
    {
        //Permitiremos numeros negativos por si se quisiera hacer un juego con magias o penalizaciones que restaran puntos:
        this.puntos = puntos;
    }
    
    public int getPuntos()
    {
        return this.puntos;
    }
    
    public void setLineas(int lineas)
    {
        if (lineas >= 0)
        {
            this.lineas = lineas;
        }
    }
    
    public int getLineas()
    {
        return this.lineas;
    }
    
    public void setLineasNivel(int lineasNivel)
    {
        if (lineasNivel >= 0)
        {
            this.lineasNivel = lineasNivel;
        }
    }

    public int getLineasNivel()
    {
        return this.lineasNivel;
    }
    
    //Inserta una pieza en el mapa del panel:
    protected void insertarPiezaEnMapa(Pieza pieza)
    {
        int[][] forma = pieza.getForma();
        
        this.vaciarMapa();

        byte leftPieza = this.calcularLeftPieza(pieza), topPieza = this.calcularTopPieza(pieza);
        
        byte fPieza = 0, cPieza;
        
        for (byte f = topPieza; f < this.getAlto(); f++)
        {
            cPieza = 0;
            for (byte c = leftPieza; c < this.getAncho(); c++)
            {
                if (fPieza < pieza.getAlto() && cPieza < pieza.getAncho())
                {
                    if (forma[fPieza][cPieza] != this.VACIO)
                    {
                        this.getMapa()[f][c] = forma[fPieza][cPieza];
                    }
                }
                cPieza++;
            }
            fPieza++;
        }
    }
    
    //Calcula el left de la pieza, para que quede alineada en el panel:
    protected byte calcularLeftPieza(Pieza pieza) //No tiene en cuenta los vacios.
    {
        byte left;
        
        left = (byte) ((this.getAncho() - pieza.getAncho()) / 2);
        
        //Si la pieza no esta alineada y hay demasiado margen derecho, incrementa el margen izquierdo:
        byte margenDerecho = (byte) Math.abs((left + pieza.getAncho()) - this.getAncho());
        while (left < margenDerecho && margenDerecho > this.margen) { left += this.margen; }
        
        return left;
    }
    
    //Calcula el top de la pieza, para que quede alineada en el panel:
    protected byte calcularTopPieza(Pieza pieza) //No tiene en cuenta los vacios.
    {
        byte top;// = this.margen;
        
        top = (byte) ((this.getAlto() - pieza.getAlto()) / 2);

        //Si la pieza no esta alineada y hay demasiado margen inferior, incrementa el margen superior:
        byte margenInferior = (byte) Math.abs((top + pieza.getAlto()) - this.getAlto());
        while (top < margenInferior && margenInferior > this.margen) { top += this.margen; }
        
        return top;
    }
    
    //Vacia el mapa:
    protected void vaciarMapa()
    {
        for (byte f = 0; f < this.getAlto(); f++)
        {
            for (byte c = 0; c < this.getAncho(); c++)
            {
                this.getMapa()[f][c] = this.color;
            }
        }
    }
    
    //Vacia el panel (el mapa):
    public void vaciar()
    {
        this.vaciarMapa();
    }
    
    //Devuelve un vector con dos posiciones con el alto y ancho mayores entre todas las piezas:
    public byte[] buscarAnchoAltoMayores(Pieza[] piezasBase)
    {
        byte[] anchoAltoMayores = new byte[2];
        
        anchoAltoMayores[0] = piezasBase[0].getAncho();
        anchoAltoMayores[1] = piezasBase[0].getAlto();
        
        for (int x = 0; x < piezasBase.length; x++)
        {
            if (piezasBase[x].getAncho() > anchoAltoMayores[0]) { anchoAltoMayores[0] = piezasBase[x].getAncho(); }
            if (piezasBase[x].getAlto() > anchoAltoMayores[1]) { anchoAltoMayores[1] = piezasBase[x].getAlto(); }
        }
        
        return anchoAltoMayores;
    }

    //Dibuja el panel en una imagen de buffer:
    public void dibujar(Graphics contenedor, BufferedImage imagenBuffer)
    {
        this.dibujar(imagenBuffer.getGraphics());
        contenedor.drawImage(imagenBuffer, 0, 0, null);
    }

    //Dibuja el panel:
    public void dibujar(Graphics contenedor)
    {
        MotorGrafico.dibujarPanel(contenedor, this);
    }
}
