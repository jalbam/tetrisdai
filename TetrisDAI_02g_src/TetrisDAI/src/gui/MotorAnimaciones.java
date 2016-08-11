/** 
 * 
 * @author Joan Alba Maldonado
 */ 

package gui;

import java.awt.Graphics;
//import java.awt.Color;

import logica.Paleta;
import logica.Tablero;

import java.awt.image.BufferedImage;

//FALTA: Animacion de una posicion a otra para una pieza (que se le pase la pieza y hacia donde va a ir (la pieza ia tiene guardado donde esta originalmente)). se le debe pasar tambien el tablero, el menu, etc. para repintar internamente una imagen de buffer kon todo i kon la imagen movida en kada bukle.

public class MotorAnimaciones
{
    static protected final int VACIO = configuracion.Otros.getNumeroVacio(); //Numero que nos marca un vacio.
    
    private MotorAnimaciones()
    {
    }
    
    static public void hacerLineas(Graphics g, BufferedImage imagenBuffer, Tablero tablero, boolean[] lineasDonde)
    {
        Graphics contenedor = imagenBuffer.getGraphics();
                
        byte f;
        boolean lineaHecha;
        boolean alternador = true;
        
        //Alterna las lineas que se van a mostrar:
        for (int x = 0; x < 15; x++)
        {
            //Recorre las filas del tablero:
            f = 0;
            while (f < lineasDonde.length)
            {
                lineaHecha = lineasDonde[f];
                if (!lineaHecha) { f++; continue; } //Si no hay lina hecha en esta fila, continua saltandose este loop;

                //Muestra la linea pertinente segun el alternador:
                if (alternador)
                {
                    MotorAnimaciones.marcarLinea(contenedor, tablero.getPaleta(), f, tablero.getAncho(), MotorAnimaciones.VACIO);
                }
                else
                {
                    MotorAnimaciones.marcarLinea(contenedor, tablero.getPaleta(), f, tablero);
                }

                //MotorGrafico.dibujarImagenBuffer(g, imagenBuffer);
                
                f++;
            }
            
            MotorGrafico.dibujarImagenBuffer(g, imagenBuffer);
            
            alternador = !alternador;
            //Realiza una pausa:
            try { Thread.sleep(8); }
            catch (Exception ex) { System.out.println("Excepcion: " + ex.getMessage()); }
        }
    }

    //Dibuja una linea de un color:
    static protected void marcarLinea(Graphics g, Paleta paleta, byte f, byte ancho, int indiceColor)
    {
        int[][] linea = new int[1][ancho];
        for (byte c = 0; c < ancho; c++)
        {
            linea[0][c] = indiceColor;
        }
        MotorAnimaciones.dibujarLinea(g, paleta, f, ancho, linea);
    }

    //Dibuja una linea tal como esta en el tablero dado:
    static protected void marcarLinea(Graphics g, Paleta paleta, byte f, Tablero tablero)
    {
        int[][] linea = new int[1][tablero.getAncho()];
        for (byte c = 0; c < tablero.getAncho(); c++)
        {
            linea[0][c] = tablero.getMapa()[f][c];
        }
        MotorAnimaciones.dibujarLinea(g, paleta, f, tablero.getAncho(), linea);
    }
    
    //Dibuja una linea donde se especifica:
    static protected void dibujarLinea(Graphics g, Paleta paleta, byte f, byte ancho, int[][] linea)
    {
        MotorGrafico.dibujarMatriz(g, paleta, linea, (byte) 0, f, ancho, (byte) 1, true);
    }
}
