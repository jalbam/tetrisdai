/** 
 * 
 * @author Joan Alba Maldonado
 */ 

package logica;

//Falta: en totes les classes, impedir ficar valors no possibles en els sets (valors negatius, etc).

//SUGERENCIA: para hacerlo mas eficiente, al kalkular si haylineas, mirar solo las lineas donde se ha solidifikado la pieza k akaba d ponerse (debera pasarse la pieza o mejor, para gastar menos memoria, la primera i la ultima linea ke afektan).

//SUGERENCIA: no morir kuando se llena la ultima linea del tablero -que es la primera fila- (dejar esta funcion por si se kiere para otra kosa), si no kuando al kaer una pieza esta se solidifika kon su Y negativa. otra manera es representar visualmente del tablero todo menos la ultima linea (primera fila).

import java.awt.Graphics;

import java.awt.image.BufferedImage;

import gui.MotorGrafico;


public class Tablero
{
    protected byte ancho; //Ancho del tablero (mapa).
    protected byte alto; //Alto del tablero (mapa).
    
    protected int[][] mapa; //Mapa del tablero.
    
    protected Paleta paleta; //Paleta que utiliza el tablero.
    
    public final int VACIO = configuracion.Otros.getNumeroVacio(); //Numero que nos marca un vacio (no hay pieza).
    
    protected final int numeroCodificacionPiezas = configuracion.Otros.getNumeroCodificacionPiezas(); //Numero por el que se multiplica cada numero que compone una pieza para insertar el resultado de la operacion en el tablero como piezas ya puestas.
    
    //Constructor que recibe el ancho, alto y la paleta del tablero:
    public Tablero(byte ancho, byte alto, Paleta paleta)
    {
        this.mapa = new int[alto][ancho];
        this.vaciar();
        this.setAncho(ancho);
        this.setAlto(alto);
        this.setPaleta(paleta);
    }
    
    public byte getAncho()
    {
        return this.ancho;
    }
    
    public void setAncho(byte ancho)
    {
        this.ancho = ancho;
        this.actualizarDimension();
    }
    
    public byte getAlto()
    {
        return this.alto;
    }
    
    public void setAlto(byte alto)
    {
        this.alto = alto;
        this.actualizarDimension();
    }
    
    //Actualiza al mapa a una nueva dimension:
    protected void actualizarDimension()
    {
        int[][] mapaAnterior = this.getMapa();

        //Si las dimensiones son iguales que antes, sale de la funcion:
        if (this.getAlto() == mapaAnterior.length && this.getAncho() == mapaAnterior[0].length) { return; }
        
        this.mapa = new int[this.getAlto()][this.getAncho()];
        this.vaciar();
        
        //Copia el mapa anterior en el nuevo:
        for (byte f = 0; f < mapaAnterior.length; f++)
        {
            for (byte c = 0; c < mapaAnterior[0].length; c++)
            {
                if (f < this.getAlto() && c < this.getAncho())
                {
                    this.mapa[f][c] = mapaAnterior[f][c];
                }
            }
        }
    }
    
    //Vacia todo el tablero:
    public void vaciar()
    {
        //Borra todas las lineas del mapa:
        for (byte f = 0; f < this.getAlto(); f++)
        {
            this.borrarLinea(f);
        }
    }

    protected void setPaleta(Paleta paleta)
    {
        this.paleta = paleta;
    }

    public Paleta getPaleta()
    {
        return this.paleta;
    }
    
    //Devuelve si el tablero esta lleno o no:
    public boolean estaLleno()
    {
        //Si hay alguna celda ocupada en la ultima fila, se asume lleno:
        boolean lleno = false;
        for (byte c = 0; c < this.getAncho(); c++)
        {
            if (this.getMapa()[0][c] != this.VACIO) { lleno = true; break; }
        }
        return lleno;
    }
    
    //Calcula si hay lineas y las procesa si es asi (retorna las lineas hechas):
    public byte procesarLineas()
    {
        byte numeroLineas = 0;
        
        //Busca las lineas y si hay, las borra:
        for (byte f = 0; f < this.getAlto(); f++)
        {
            if (this.hayLinea(f))
            {
                numeroLineas++;
                this.borrarLinea(f);
            }
        }
        
        //Si han habido lineas, hace caer las piezas:
        if (numeroLineas > 0) { this.bajarPiezas(); }
        
        return numeroLineas;
    }

    //Devuelve las lineas que hay en todo el tablero:
    protected byte lineasHechas()
    {
        byte lineasHechas = 0;
        for (byte f = 0; f < this.getAlto(); f++)
        {
            if (hayLinea(f)) { lineasHechas++; }
        }
        return lineasHechas;
    }

    //Devuelve en que filas hay lineas hechas:
    protected boolean[] lineasHechasDonde()
    {
        boolean[] lineasHechasDonde = new boolean[this.getAlto()];
        for (byte f = 0; f < this.getAlto(); f++)
        {
            if (hayLinea(f)) { lineasHechasDonde[f] = true; }
            else { lineasHechasDonde[f] = false; } //Supongo que no es necesario.
        }
        return lineasHechasDonde;
    }
    
    //Devuelve si hay linea o no en una fila determinada:
    protected boolean hayLinea(byte fila)
    {
        boolean hayLinea = true;
        for (byte c = 0; c < this.getAncho(); c++)
        {
            if (this.getMapa()[fila][c] == this.VACIO) { hayLinea = false; break; }
        }
        return hayLinea;
    }
    
    //Borra una linea del tablero:
    protected void borrarLinea(byte fila)
    {
        for (byte c = 0; c < this.getAncho(); c++)
        {
            this.getMapa()[fila][c] = this.VACIO;
        }
    }
    
    //Devuelve si una linea esta vacia o no:
    protected boolean lineaVacia(byte fila)
    {
        boolean vacia = true;
        for (byte c = 0; c < this.getAncho(); c++)
        {
            if (this.getMapa()[fila][c] != this.VACIO) { vacia = false; break; }
        }
        return vacia;
    }
    
    //Hace bajar las piezas por gravedad:
    protected void bajarPiezas()
    {
        for (byte f = 1; f < this.getAlto(); f++) //Comienza por la segunda linea (la primera no tiene superiores).
        {
            //Si la linea esta vacia:
            if (this.lineaVacia(f))
            {
                //Recorre las lineas superiores (hasta llegar a la primera):
                for (byte fSuperior = (byte)(f - 1); fSuperior >= 0; fSuperior--)
                {
                    //Si la linea superior esta vacia, sale del bucle (porque las siguientes ya habran caido anteriormente):
                    if (this.lineaVacia(fSuperior)) { break; }
                    //...pero si no, mueve el contenido de la superior a la inferior (pasa el contenido de la linea superior a la de abajo y borra la superior):
                    else { this.copiarFila(fSuperior, (byte) (fSuperior + 1)); this.borrarLinea(fSuperior); } //else innecesario.
                }
            }
        }
    }
    
    //Copia el contenido de una fila en otra:
    protected void copiarFila(byte fOrigen, byte fDestino)
    {
        for (byte c = 0; c < this.getAncho(); c++)
        {
            this.getMapa()[fDestino][c] = this.getMapa()[fOrigen][c];
        }
    }
    
    //Agrega una pieza al tablero en la posicion dada:
    public void agregarPieza(Pieza pieza, byte left, byte top) //<- pensar si fa falta la y (top)
    {
        //Si la pieza cabe, la agrega:
        if (this.cabePieza(pieza, left, top))
        {
            byte fPieza = 0, cPieza;
            for (byte f = top; f < top + pieza.getAlto(); f++, fPieza++)
            {
                cPieza = 0;
                for (byte c = left; c < left + pieza.getAncho(); c++, cPieza++)
                {
                    //Agrega la pieza codificandola (para saber que esta puesta):
                    if (pieza.getForma()[fPieza][cPieza] != this.VACIO)
                    {
                        //Solo se agregan las casillas con posicion positiva:
                        if (f >= 0 && c >= 0) //if (f >= 0) <- en realidad solo haria falta comprobar las filas, pues las columnas jamas se permiten negativas.
                        {
                            this.getMapa()[f][c] = pieza.getForma()[fPieza][cPieza] * this.numeroCodificacionPiezas;
                        }
                    }
                }
            }
        }
    }

    //Agrega una pieza al tablero (usando el left y top de la pieza):
    public void agregarPieza(Pieza pieza)
    {
        this.agregarPieza(pieza, pieza.getLeft(), pieza.getTop());
    }
    
    //Retorna si una pieza cabe en el tablero en una posicion dada:
    public boolean cabePieza(Pieza pieza, byte left, byte top)
    {
        return pieza.cabePieza(this, left, top);
    }
    
    //Retorna si una pieza cabe en el tablero (usando el left y top de la pieza):
    public boolean cabePieza(Pieza pieza)
    {
        return this.cabePieza(pieza, pieza.getLeft(), pieza.getTop());
    }
    
    public int[][] getMapa()
    {
        return this.mapa;
    }

    //Dibuja el tablero en una imagen de buffer:
    public void dibujar(Graphics contenedor, BufferedImage imagenBuffer)
    {
        this.dibujar(imagenBuffer.getGraphics());
        contenedor.drawImage(imagenBuffer, 0, 0, null);
    }
    
    //Dibuja el tablero:
    public void dibujar(Graphics contenedor)
    {
        //Graphics g = contenedor.getGraphics();
        MotorGrafico.dibujarTablero(contenedor, this);
    }
    
    @Override
    public String toString()
    {
        String cadena = "\n";
        for (byte f = 0; f < this.getAlto(); f++)
        {
            for (byte c = 0; c < this.getAncho(); c++)
            {
                cadena += this.getMapa()[f][c] + "   ";
            }
            cadena += "\n";
        }
        return cadena;
    }
}