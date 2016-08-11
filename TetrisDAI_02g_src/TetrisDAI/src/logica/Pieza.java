/** 
 * 
 * @author Joan Alba Maldonado
 */ 

package logica;

//Falta: en totes les classes, impedir ficar valors no possibles en els sets (valors negatius, etc).

//Pensar: kiza seria bueno impedir rotaciones imposibles aunke la pieza rotada kepa. imposibles serian si para rotarlas deberian atravesar otras piezas, aunke ne la nueva posicion esten korrektamente.

//Pensar (CREO QUE NO): para hacer lo de desplazar pieza, rotar las piezas kon margenes de vacio (0), i luego si se ha de desplazar, desplazar la pieza tanto komo se pueda rekortando sus margenes ke tengan cero i puedan rekortarse (ke en la kolumna ke se kiere rekortar solo haia vacio). hacerlo tanto a la derecha komo a la izkierda (kiza una funcion ke sea ponerLeftRecortando() o algo asi).

import java.awt.Graphics;

import gui.MotorGrafico;

import java.awt.image.BufferedImage;

public class Pieza
{
    protected byte ancho = 0; //Ancho de la pieza.
    protected byte alto = 0; //Alto de la pieza.

    protected byte left = 0; //Posicion horizontal de la pieza (en el tablero).
    protected byte top = 0; //Posicion vertical de la pieza (en el tablero).
    
    protected int[][] forma = new int[alto][ancho]; //Forma de la pieza.
    
    protected Paleta paleta; //Paleta que utiliza la pieza.

    protected final int VACIO = configuracion.Otros.getNumeroVacio(); //Numero que nos marca un vacio (no hay pieza).
    
    protected boolean desplazarPiezaSiNoCabeAlRotar; //Define si la pieza se desplaza al rotar, si no cabe.
    
    //Constructor que recibe la forma y la paleta de la pieza:
    public Pieza(int[][] forma, Paleta paleta)
    {
        this(forma, paleta, (byte) 0, (byte) 0); //Posiciona la pieza en el punto (0, 0).
    }

    //Constructor que recibe la forma, la paleta de la pieza y la posicion horizontal de la pieza:
    public Pieza(int[][] forma, Paleta paleta, byte left)
    {
        this(forma, paleta, left, (byte) (forma.length * -1)); //Posiciona la pieza en el punto (left, topNegativo).
    }
    
    //Constructor que recibe la forma, la paleta y la posicion de la pieza:
    public Pieza(int[][] forma, Paleta paleta, byte left, byte top)
    {
        this.setForma(forma);
        this.setPaleta(paleta);
        this.setLeft(left);
        this.setTop(top);
    }
    
    protected void setForma(int[][] forma)
    {
        this.forma = forma;
        this.setAncho((byte) this.calcularAncho(this.getForma())); //this.setAncho((byte) this.getForma()[0].length);
        this.setAlto((byte) this.getForma().length);
        
        //Rellena con ceros la forma, por si no era rectangular:
        int[][] formaRectangular = new int[this.getAlto()][this.getAncho()];
        for (byte f = 0; f < this.getAlto(); f++)
        {
            for (byte c = 0; c < this.getAncho(); c++)
            {
                if (f < this.getForma().length && c < this.getForma()[f].length)
                {
                    formaRectangular[f][c] = forma[f][c];
                }
                else { formaRectangular[f][c] = 0; }
            }
        }
        this.forma = formaRectangular;
    }
    
    public int[][] getForma()
    {
        return this.forma;
    }

    protected void setPaleta(Paleta paleta)
    {
        this.paleta = paleta;
    }
    
    public Paleta getPaleta()
    {
        return this.paleta;
    }
    
    protected void setAncho(byte ancho)
    {
        this.ancho = ancho;
    }
    
    public byte getAncho()
    {
        return this.ancho;
    }
    
    //Calcula el ancho maximo de una pieza dada:
    public byte calcularAncho(int[][] forma)
    {
        byte ancho = (byte) forma[0].length;
        
        for (byte f = 0; f < forma.length; f++)
        {
            if (forma[f].length > ancho) { ancho = (byte) forma[f].length; }
        }
        
        return ancho;
    }
    
    protected void setAlto(byte alto)
    {
        this.alto = alto;
    }
    
    public byte getAlto()
    {
        return this.alto;
    }

    protected void setLeft(byte left)
    {
        this.left = left;
    }
    
    public byte getLeft()
    {
        return this.left;
    }
    
    protected void setTop(byte top)
    {
        this.top = top;
    }
    
    public byte getTop()
    {
        return this.top;
    }

    public void setDesplazarPiezaSiNoCabeAlRotar(boolean desplazarPiezaSiNoCabeAlRotar)
    {
        this.desplazarPiezaSiNoCabeAlRotar = desplazarPiezaSiNoCabeAlRotar;
    }
    
    public boolean getDesplazarPiezaSiNoCabeAlRotar()
    {
        return this.desplazarPiezaSiNoCabeAlRotar;
    }
    
    //Mueve a la izquierda la pieza (decrementa su left):
    public void moverIzquierda(Tablero tablero)
    {
        this.mover(tablero, (byte) (this.getLeft() - 1), this.getTop());
    }
    
    //Mueve a la derecha la pieza (incrementa su left):
    public void moverDerecha(Tablero tablero)
    {
        this.mover(tablero, (byte) (this.getLeft() + 1), this.getTop());
    }

    //Mueve abajo la pieza (incrementa su top):
    public void moverAbajo(Tablero tablero)
    {
        this.mover(tablero, this.getLeft(), (byte) (this.getTop() + 1));
    }

    //Mueve abajo la pieza (decrementa su top):
    public void moverArriba(Tablero tablero)
    {
        this.mover(tablero, this.getLeft(), (byte) (this.getTop() - 1));
    }
    
    //Mueve la pieza a una posicion del mapa pasada por parametro:
    public void mover(Tablero tablero, byte left, byte top)
    {
        //Si la pieza cabe en la posicion enviada, se inserta:
        if (this.cabePieza(tablero, left, top))
        {
            this.setLeft(left);
            this.setTop(top);
        }
    }
    
    //Calcula si la pieza ha colisionado:
    protected boolean calcularColision(Tablero tablero)
    {
        boolean colision = false;
        
        //Si la pieza ya no esta en una posicion correcta, hay colision:
        if (!this.cabePieza(tablero)) { colision = true; }
        //...o si la pieza al bajar estuviera en una posicion incorrecta, tambien hay colision:
        else if (!this.cabePieza(tablero, this.getLeft(), (byte) (this.getTop() + 1))) { colision = true; } //<-- y si se mueve a un lado antes de que baje??? bueno, el cicloJuego() ya permite mover una vez antes de solidificarse (y vuelve a comprobar la colision, por si se ha movido).
        //...o si ha llegado al fondo (suelo), tambien hay colision:
        //else if (this.getTop() + this.getAlto() >= tablero.getAlto()) { colision = true; }
        
        return colision;
    }

    //Rota la pieza a la izquierda:
    public void rotarIzquierda(Tablero tablero)
    {
        this.rotarIzquierda(tablero, this.getDesplazarPiezaSiNoCabeAlRotar());
    }
    
    //Rota la pieza a la izquierda (definiendo si se desplaza o no si no cabe):
    public boolean rotarIzquierda(Tablero tablero, boolean desplazarPiezaSiNoCabeAlRotar)
    {
        //FALTA: cambiar left i top de la pieza, para k su eje d rotacion este situado en el centro, mas o menos.
        
        boolean esIgual = true; //Define si la pieza rotada es igual a la pieza sin rotar.
        if (this.getAncho() != this.getAlto()) { esIgual = false; }
        
        int formaRotada[][] = new int[this.getAncho()][this.getAlto()];
        
        byte fRotada, cRotada;
        
        for (byte f = 0; f < this.getAlto(); f++)
        {
            for (byte c = 0; c < this.getAncho(); c++)
            {
                fRotada = (byte) (this.getAncho() - c - 1);
                cRotada = f;
                formaRotada[fRotada][cRotada] = this.getForma()[f][c];
                
                if (esIgual && formaRotada[fRotada][cRotada] != this.getForma()[f][c]) { esIgual = false; }
            }
        }
        
        Pieza piezaRotada = new Pieza(formaRotada, this.getPaleta(), this.getLeft(), this.getTop());
        
        //Si no cabe y se ha definido desplazar, la desplaza:
        if (!this.cabePieza(piezaRotada, tablero) && desplazarPiezaSiNoCabeAlRotar)
        {
            /*
            //La desplaza a la derecha:
            piezaRotada.desplazarDerecha(tablero);
            
            //Si no cabe, la restaura en su sitio y la desplaza a arriba:
            if (!this.cabePieza(piezaRotada, tablero))
            {
                piezaRotada.setLeft(this.getLeft());
                piezaRotada.setTop(this.getTop());
                piezaRotada.desplazarArriba(tablero);
            }
            */
            //Si no cabe, la desplazanda a la derecha y arriba de forma "optima" (elige el menor numero de desplazamientos):
            if (piezaRotada.getTop() > piezaRotada.getLeft()) { piezaRotada.desplazarIzquierdaArriba(tablero); } //Si es mas larga que ancha, la desplaza a la izquierda y arriba.
            else { piezaRotada.desplazarDerechaArriba(tablero); } //Si es mas ancha que larga o igual (cuadrada, aunque tenga vacios), la desplaza a la derecha y arriba.
        }
        
        boolean piezaCabida = false;
        if (this.cabePieza(piezaRotada, tablero))
        {
            piezaCabida = true;
            
            this.setForma(formaRotada);
            this.setLeft(piezaRotada.getLeft());
            this.setTop(piezaRotada.getTop());
         }
        
        return !esIgual && piezaCabida;
    }
    
    //Rota a la derecha la pieza:
    public void rotarDerecha(Tablero tablero)
    {
        this.rotarDerecha(tablero, this.getDesplazarPiezaSiNoCabeAlRotar());
    }
    
    //Rota a la derecha la pieza (definiendo si se desplaza o no si no cabe):
    public boolean rotarDerecha(Tablero tablero, boolean desplazarPiezaSiNoCabeAlRotar)
    {
        //FALTA: cambiar left i top de la pieza, para k su eje d rotacion este situado en el centro, mas o menos.

        boolean esIgual = true; //Define si la pieza rotada es igual a la pieza sin rotar.
        if (this.getAncho() != this.getAlto()) { esIgual = false; }
        
        int formaRotada[][] = new int[this.getAncho()][this.getAlto()];
        
        byte fRotada, cRotada;
        
        for (byte f = 0; f < this.getAlto(); f++)
        {
            for (byte c = 0; c < this.getAncho(); c++)
            {
                fRotada = c;
                cRotada = (byte) (this.getAlto() - f - 1);
                formaRotada[fRotada][cRotada] = this.getForma()[f][c];
                
                if (esIgual && formaRotada[fRotada][cRotada] != this.getForma()[f][c]) { esIgual = false; }
            }
        }

        Pieza piezaRotada = new Pieza(formaRotada, this.getPaleta(), this.getLeft(), this.getTop());
        
        //Si no cabe y se ha definido desplazar, la desplaza:
        if (!this.cabePieza(piezaRotada, tablero) && desplazarPiezaSiNoCabeAlRotar)
        {
            /*
            //La desplaza a la izquierda:
            piezaRotada.desplazarIzquierda(tablero);
            
            //Si no cabe, la restaura en su sitio y la desplaza arriba:
            if (!this.cabePieza(piezaRotada, tablero))
            {
                piezaRotada.setLeft(this.getLeft());
                piezaRotada.setTop(this.getTop());
                piezaRotada.desplazarArriba(tablero);
            }
            */
            //Si no cabe, la desplazanda de forma "optima" (elige el menor numero de desplazamientos):
            if (piezaRotada.getTop() > piezaRotada.getLeft()) { piezaRotada.desplazarDerechaArriba(tablero); } //Si es mas larga que ancha, la desplaza a la derecha y arriba.
            else { piezaRotada.desplazarIzquierdaArriba(tablero); } //Si es mas ancha que larga o igual (cuadrada, aunque tenga vacios), la desplaza a la izquierda y arriba.
        }
        
        boolean piezaCabida = false;
        if (this.cabePieza(piezaRotada, tablero))
        {
            piezaCabida = true;
            this.setForma(formaRotada);
            this.setLeft(piezaRotada.getLeft());
            this.setTop(piezaRotada.getTop());
        }
        
        return !esIgual && piezaCabida;
    }

    /*
    //Desplaza la pieza varias posiciones hacia la izquierda:
    protected void desplazarIzquierda(Tablero tablero)
    {
        this.desplazarIzquierda(this, tablero);
    }
    
    //Desplaza una pieza varias posiciones hacia la izquierda:
    protected void desplazarIzquierda(Pieza pieza, Tablero tablero)
    {
        if (!this.cabePieza(pieza, tablero))
        {
            int intentosRealizados = 0, intentosMaximo = pieza.getAncho() - 1;
            while (!this.cabePieza(pieza, tablero) && intentosRealizados++ < intentosMaximo && pieza.getLeft() - 1 >= 0)
            {
                if (intentosRealizados == 1) { System.out.println("Iniciando bucle a la izquierda."); }
                pieza.setLeft((byte) (pieza.getLeft() - 1));
                System.out.println("* Desplazando a la izquierda... (" + pieza.getLeft() + ", "+pieza.getTop()+")");
                if (this.cabePieza(pieza, tablero)) { System.out.println("Finalizando bucle (ya cabe en " + pieza.getLeft() + ", " + pieza.getTop() + ")."); }
                else if (intentosRealizados >= intentosMaximo) { System.out.println("Finalizando bucle (llego al final)."); }
            }
        }
    }
   
    //Desplaza la pieza varias posiciones hacia la derecha:
    protected void desplazarDerecha(Tablero tablero)
    {
        this.desplazarDerecha(this, tablero);
    }
    
    //Desplaza una pieza varias posiciones hacia la derecha:
    protected void desplazarDerecha(Pieza pieza, Tablero tablero)
    {
        if (!this.cabePieza(pieza, tablero))
        {
            int intentosRealizados = 0, intentosMaximo = pieza.getAncho() - 1;
            while (!this.cabePieza(pieza, tablero) && intentosRealizados++ < intentosMaximo)
            {
                if (intentosRealizados == 1) { System.out.println("Iniciando bucle a la derecha."); }
                pieza.setLeft((byte) (pieza.getLeft() + 1));
                System.out.println("* Desplazando a la derecha... (" + pieza.getLeft() + ", "+pieza.getTop()+")");
                if (this.cabePieza(pieza, tablero)) { System.out.println("Finalizando bucle (ya cabe en " + pieza.getLeft() + ", " + pieza.getTop() + ")."); }
                else if (intentosRealizados >= intentosMaximo) { System.out.println("Finalizando bucle (llego al final)."); }
            }
        }
    }

    //Desplaza la pieza varias posiciones hacia arriba:
    protected void desplazarArriba(Tablero tablero)
    {
        this.desplazarArriba(this, tablero);
    }
    
    //Desplaza una pieza varias posiciones hacia arriba:
    protected void desplazarArriba(Pieza pieza, Tablero tablero)
    {
        if (!this.cabePieza(pieza, tablero))
        {
            int intentosRealizados = 0, intentosMaximo = pieza.getAlto() - 1;
            while (!this.cabePieza(pieza, tablero) && intentosRealizados++ < intentosMaximo && pieza.getTop() - 1 >= 0)
            {
                if (intentosRealizados == 1) { System.out.println("Iniciando bucle a arriba."); }
                pieza.setTop((byte) (pieza.getTop() - 1));
                System.out.println("* Desplazando a arriba... (" + pieza.getLeft() + ", "+pieza.getTop()+")");
                if (this.cabePieza(pieza, tablero)) { System.out.println("Finalizando bucle (ya cabe en " + pieza.getLeft() + ", " + pieza.getTop() + ")."); }
                else if (intentosRealizados >= intentosMaximo) { System.out.println("Finalizando bucle (llego al final)."); }
            }
        }
    }

    //Desplaza la pieza varias posiciones hacia abajo:
    protected void desplazarAbajo(Tablero tablero)
    {
        this.desplazarAbajo(this, tablero);
    }
    
    //Desplaza una pieza varias posiciones hacia abajo:
    protected void desplazarAbajo(Pieza pieza, Tablero tablero)
    {
        if (!this.cabePieza(pieza, tablero))
        {
            int intentosRealizados = 0, intentosMaximo = pieza.getAlto() - 1;
            while (!this.cabePieza(pieza, tablero) && intentosRealizados++ < intentosMaximo)
            {
                if (intentosRealizados == 1) { System.out.println("Iniciando bucle a abajo."); }
                pieza.setTop((byte) (pieza.getTop() + 1));
                System.out.println("* Desplazando a abajo... (" + pieza.getLeft() + ", "+pieza.getTop()+")");
                if (this.cabePieza(pieza, tablero)) { System.out.println("Finalizando bucle (ya cabe en " + pieza.getLeft() + ", " + pieza.getTop() + ")."); }
                else if (intentosRealizados >= intentosMaximo) { System.out.println("Finalizando bucle (llego al final)."); }
            }
        }
    }
    */
    
    //Desplaza la pieza varias posiciones a la izquierda y arriba y escoge el camino mas corto:
    protected void desplazarIzquierdaArriba(Tablero tablero)
    {
        this.desplazarIzquierdaArriba(this, tablero);
    }

    //Desplaza una pieza varias posiciones a la izquierda y arriba y escoge el camino mas corto:
    protected void desplazarIzquierdaArriba(Pieza pieza, Tablero tablero)
    {
        if (!this.cabePieza(pieza, tablero))
        {
            byte leftInicial = pieza.getLeft(), topInicial = pieza.getTop();
            byte leftOptimo = -1, topOptimo = -1;
            int intentosRealizadosX = -1, intentosMaximoX = pieza.getAncho() / 2;
            int intentosRealizadosY, intentosMaximoY = pieza.getAlto() / 4;
            int intentosRealizadosMinimo = intentosMaximoX + intentosMaximoY;
            
            //Se comienza a desplazar hacia la izquierda:
            while (!this.cabePieza(pieza, tablero) && ++intentosRealizadosX <= intentosMaximoX && pieza.getLeft() - 1 >= 0)
            {
                //Se desplaza a la izquierda:
                pieza.setLeft((byte) (pieza.getLeft() - 1));
                
                //Si no cabe, se comienza a desplazar hacia arriba:
                if (!this.cabePieza(pieza, tablero))
                {
                    intentosRealizadosY = -1;
                    //Se comienza a desplazar hacia arriba:
                    while (!this.cabePieza(pieza, tablero) && ++intentosRealizadosY <= intentosMaximoY && pieza.getTop() - 1 >= 0)
                    {
                        pieza.setTop((byte) (pieza.getTop() - 1));
                    }
                    
                    //Si la pieza no cabe, se restaura (solo se habra movido a la izquierda):
                    if (!this.cabePieza(pieza, tablero))
                    {
                        pieza.setTop(topInicial);
                    }
                    //...pero si cabe, se guarda su top y su left siempre que tenga un numero de desplazamientos igual o menor al minimo conseguido anteriormente:
                    else if (intentosRealizadosX + intentosRealizadosY <= intentosRealizadosMinimo)
                    {
                        leftOptimo = pieza.getLeft();
                        topOptimo = pieza.getTop();
                    }
                }
            }
            
            //Si se ha conseguido un desplazamiento optimo, se pone:
            if (leftOptimo != -1 && topOptimo != -1)
            {
                pieza.setLeft(leftOptimo);
                pieza.setTop(topOptimo);
            }
        }
    }

    //Desplaza la pieza varias posiciones a la derecha y arriba y escoge el camino mas corto:
    protected void desplazarDerechaArriba(Tablero tablero)
    {
        this.desplazarDerechaArriba(this, tablero);
    }
    
    //Desplaza una pieza varias posiciones a la derecha y arriba y escoge el camino mas corto:
    protected void desplazarDerechaArriba(Pieza pieza, Tablero tablero)
    {
        if (!this.cabePieza(pieza, tablero))
        {
            byte leftInicial = pieza.getLeft(), topInicial = pieza.getTop();
            byte leftOptimo = -1, topOptimo = -1;
            int intentosRealizadosX = -1, intentosMaximoX = pieza.getAncho() / 2;
            int intentosRealizadosY, intentosMaximoY = pieza.getAlto() / 4;
            int intentosRealizadosMinimo = intentosMaximoX + intentosMaximoY;
            
            //Se comienza a desplazar hacia la derecha:
            while (!this.cabePieza(pieza, tablero) && intentosRealizadosX++ < intentosMaximoX && pieza.getLeft() + pieza.getAncho() + 1 < tablero.getAncho())
            {
                //Se desplaza a la derecha:
                pieza.setLeft((byte) (pieza.getLeft() + 1));
                
                //Si no cabe, se comienza a desplazar hacia arriba:
                if (!this.cabePieza(pieza, tablero))
                {
                    intentosRealizadosY = -1;
                    //Se comienza a desplazar hacia arriba:
                    while (!this.cabePieza(pieza, tablero) && intentosRealizadosY++ < intentosMaximoY && pieza.getTop() - 1 >= 0)
                    {
                        pieza.setTop((byte) (pieza.getTop() - 1));
                    }
                    
                    //Si la pieza no cabe, se restaura (solo se habra movido a la izquierda):
                    if (!this.cabePieza(pieza, tablero))
                    {
                        pieza.setTop(topInicial);
                    }
                    //...pero si cabe, se guarda su top y su left siempre que tenga un numero de desplazamientos igual o menor al minimo conseguido anteriormente:
                    else if (intentosRealizadosX + intentosRealizadosY <= intentosRealizadosMinimo)
                    {
                        leftOptimo = pieza.getLeft();
                        topOptimo = pieza.getTop();
                    }
                }
            }
            
            //Si se ha conseguido un desplazamiento optimo, se pone:
            if (leftOptimo != -1 && topOptimo != -1)
            {
                pieza.setLeft(leftOptimo);
                pieza.setTop(topOptimo);
            }
        }
    }
    
    //Calcula si una pieza esta en una posicion correcta en su posicion actual:
    public boolean cabePieza(Pieza pieza, Tablero tablero)
    {
        //return this.cabePieza(pieza, tablero, this.getLeft(), this.getTop());
        return this.cabePieza(pieza, tablero, pieza.getLeft(), pieza.getTop());
    }
    
    //Calcula si la pieza esta en una posicion correcta en su posicion actual:
    public boolean cabePieza(Tablero tablero)
    {
        return this.cabePieza(this, tablero, this.getLeft(), this.getTop());
    }

    //Calcula si la pieza estaria posicionada correctamente en una posicion dada:
    public boolean cabePieza(Tablero tablero, byte left, byte top)
    {
        return this.cabePieza(this, tablero, left, top);
    }
    
    //Calcula si la pieza estaria posicionada correctamente en una posicion dada:
    public boolean cabePieza(Pieza pieza, Tablero tablero, byte left, byte top)
    {
        boolean cabe = true;
        
        byte fPieza = 0, cPieza;
        
        for (byte f = top; f < top + pieza.getAlto(); f++, fPieza++)
        {
            cPieza = 0;
            for (byte c = left; c < left + pieza.getAncho(); c++, cPieza++)
            {
                //Solo tiene en cuenta si en la pieza no hay un cero (cero = vacio):
                if (pieza.getForma()[fPieza][cPieza] != pieza.VACIO)
                {
                    //Si la pieza esta demasiado abajo, no cabe:
                    if (f >= tablero.getAlto()) { cabe = false; }
                    //...o si la pieza esta demasiado a la izquierda, no cabe:
                    else if (c < 0) { cabe = false; }
                    //...o si la pieza esta demasiado a la derecha, no cabe:
                    else if (c >= tablero.getAncho()) { cabe = false; }
                    //...o si la pieza se pone encima de las ya puestas, no cabe:
                    else if (f >= 0 && c >= 0 && tablero.getMapa()[f][c] != tablero.VACIO) //else if (tablero.getMapa()[f][c] != tablero.VACIO)
                    {
                        cabe = false;
                    }
                }
                if (!cabe) { break; }
            }
            if (!cabe) { break; }
        }

        return cabe;
    }
    
    //Dibuja la pieza en una imagen de buffer:
    public void dibujar(Graphics contenedor, BufferedImage imagenBuffer)
    {
        this.dibujar(imagenBuffer.getGraphics());
        contenedor.drawImage(imagenBuffer, 0, 0, null);
    }
    
    //Dibuja la pieza:
    public void dibujar(Graphics contenedor)
    {
        MotorGrafico.dibujarPieza(contenedor, this);
    }
    
    @Override
    public String toString()
    {
        String cadena = "\n";
        for (byte f = 0; f < this.getAlto(); f++)
        {
            for (byte c = 0; c < this.getAncho(); c++)
            {
                cadena += this.getForma()[f][c] + "   ";
            }
            cadena += "\n";
        }
        return cadena;
    }
}