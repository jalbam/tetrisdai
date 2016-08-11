/** 
 * 
 * @author Joan Alba Maldonado
 */ 

package logica;

//import java.awt.event.KeyListener;

import java.util.Hashtable;

import java.util.Enumeration;

import principal.Main;

import java.awt.event.*;

//FALTA: buscar alguna forma para que al cambiar el foco de la ventana y no soltar una tecla, detecte que se ha soltado. a lo mejor en leerTeclado se peude consultar el estado de la tecla otra vez.

//FALTA: en KeyPressed no mirar si la tecla esta pulsada si no si una tecla que hace la misma accion esta pulsada, y salir tal komo se hace ahora.

//PENSAR: si poner o no un acelerador, para que al dejar pulsada una tecla se vaya acelerando su procesamiento (o si no, al menos un retraso inicial en la primera tecla).

public class Teclado implements KeyListener
{
    static protected Hashtable<Integer, Boolean> teclasPulsadas = new Hashtable<Integer, Boolean>(); //Guarda el estado de las teclas (si estan pulsadas o no).
    static protected long ultimaLecturaTeclaOtras = 0; //Guarda cuando fue la ultima lectura de teclado de una tecla que no es de rotacion.
    static protected long ultimaLecturaTeclaRotacion = 0; //Guarda cuando fue la ultima lectura de teclado de una tecla de rotacion.
    
    public Teclado()
    {
    }
    
    //Vacia las teclas (pone como si ninguna se ha pulsado):
    static public void vaciarTeclas()
    {
        Teclado.teclasPulsadas.clear();
    }
    
    //Devuelve si es una tecla de rotacion:
    static public boolean esTeclaRotacion(int tecla)
    {
        boolean loEs = false;
        
        if (Main.juego != null)
        {
            if (Main.juego.teclaEnTeclas(tecla, Main.juego.getTeclaRotarDerecha()) || Main.juego.teclaEnTeclas(tecla, Main.juego.getTeclaRotarIzquierda()))
            {
                loEs = true;
            }
        }
        
        return loEs;
    }

    //Codigo que se procesa una tecla:
    static synchronized public void procesarTecla(int tecla, boolean contarTiempo)
    {
        if (Main.juego != null)
        {
           
            //Si aun no ha pasado el tiempo necesario, sale:
            long tiempoNecesario = Main.juego.getRetrasoTeclasOtras();
            if (Teclado.esTeclaRotacion(tecla)) { tiempoNecesario = Main.juego.getRetrasoTeclasRotacion(); }
            long tiempoActual = System.currentTimeMillis();
            long tiempoTranscurrido = tiempoActual - Teclado.ultimaLecturaTeclaOtras;
            if (Teclado.esTeclaRotacion(tecla)) { tiempoTranscurrido = tiempoActual - Teclado.ultimaLecturaTeclaRotacion; }
            if (tiempoTranscurrido < tiempoNecesario) { return; }
            
            //Procesa la tecla:
            Main.juego.procesarTecla(tecla);
            
            //Si se ha enviado incrementar el contador de la ultima lectura, se procede:
            if (contarTiempo)
            {
                if (Teclado.esTeclaRotacion(tecla)) { Teclado.ultimaLecturaTeclaRotacion = System.currentTimeMillis(); }
                else { Teclado.ultimaLecturaTeclaOtras = System.currentTimeMillis(); }
                //System.out.println("durmiendo...");
                //try{Thread.sleep(2000);}catch(Exception e){}
            }
        }
    }
    
    //Codigo que se ejecuta repetidamente para procesar las teclas pulsadas:
    static synchronized public void leerTeclado(boolean limitadorTiempo)
    {
        if (Main.juego == null) { return; }
        
        long tiempoNecesario, tiempoActual, tiempoTranscurrido;
        boolean seHaProcesadoAlgunaTeclaOtras = false;
        boolean seHaProcesadoAlgunaTeclaRotacion = false;
        
        //Recorre las teclas:
        Enumeration teclas = Teclado.teclasPulsadas.keys();
        int tecla;
        boolean pulsada;
        while (teclas.hasMoreElements())
        {
            tecla = (Integer) teclas.nextElement();
            pulsada = Teclado.teclasPulsadas.get(tecla);
            
            //Si la tecla esta pulsada y es valida para el juego, la procesa:
            if (pulsada && Main.juego.teclaEnTeclasTodas(tecla))
            {
                
                //Si se ha enviado utilizar el limitador de tiempo:
                if (limitadorTiempo)
                {
                    //Si aun no ha pasado el tiempo necesario, sale:
                    tiempoNecesario = Main.juego.getRetrasoTeclasOtras();
                    if (Teclado.esTeclaRotacion(tecla)) { tiempoNecesario = Main.juego.getRetrasoTeclasRotacion(); /*System.out.println("tekla rotacion!!!");*/ }
                    tiempoActual = System.currentTimeMillis();
                    tiempoTranscurrido = tiempoActual - Teclado.ultimaLecturaTeclaOtras;
                    if (Teclado.esTeclaRotacion(tecla)) { tiempoTranscurrido = tiempoActual - Teclado.ultimaLecturaTeclaRotacion; }
                    //if (tiempoTranscurrido < tiempoNecesario) { /*System.out.println("Se necesitan " + tiempoNecesario + " y solo han transcurrido " + tiempoTranscurrido + ", asi que sale.");*/ return; }
                    if (tiempoTranscurrido < tiempoNecesario) { continue; }
                }
                
                //Main.juego.procesarTecla(tecla);
                Teclado.procesarTecla(tecla, false);
                if (Teclado.esTeclaRotacion(tecla)) { seHaProcesadoAlgunaTeclaRotacion = true; }
                else { seHaProcesadoAlgunaTeclaOtras = true; }
                //Teclado.ultimaLecturaTeclado = System.currentTimeMillis();
                //break; //Solo deja procesar una tecla.
            }
        }
        
        if (seHaProcesadoAlgunaTeclaOtras) { Teclado.ultimaLecturaTeclaOtras = System.currentTimeMillis(); }
        if (seHaProcesadoAlgunaTeclaRotacion) { Teclado.ultimaLecturaTeclaRotacion = System.currentTimeMillis(); }
        
        //System.out.println("* Lectura de teclado efectuada en: " + Teclado.ultimaLecturaTeclado);
    }
    
    @Override
    public void keyPressed(KeyEvent e)
    {
        //Si el juego esta activo:
        if (Main.juego != null)
        {
            //Si la tecla ya ha sido pulsada y no liberada, sale:
            int tecla = e.getKeyCode();
            if (Teclado.teclasPulsadas.get(tecla) != null && Teclado.teclasPulsadas.get(tecla)) { return; }
            
            //Si la tecla es una tecla valida para el juego:
            if (Main.juego.teclaEnTeclasTodas(tecla))
            {
                //Guarda como que la tecla se ha pulsado:
                Teclado.teclasPulsadas.put(tecla, true);
                
                //Si las teclas son excluyentes, desactiva su contraria:
                int[] teclasIzquierda = Main.juego.getTeclaIzquierda();
                int[] teclasDerecha = Main.juego.getTeclaDerecha();
                int[] teclasArriba = Main.juego.getTeclaArriba();
                int[] teclasAbajo = Main.juego.getTeclaAbajo();
                
                if (Main.juego.teclaEnTeclas(tecla, teclasIzquierda)) //Si se ha apretado izquierda, desactiva derecha.
                {
                    //Desactiva todos los codigos que hagan la misma accion:
                    for (int x = 0; x < teclasDerecha.length; x++)
                    {
                        Teclado.teclasPulsadas.put(teclasDerecha[x], false);
                    }
                }
                else if (Main.juego.teclaEnTeclas(tecla, teclasDerecha)) //Si se ha apretado derecha, desactiva izquierda.
                {
                    //Desactiva todos los codigos que hagan la misma accion:
                    for (int x = 0; x < teclasIzquierda.length; x++)
                    {
                        Teclado.teclasPulsadas.put(teclasIzquierda[x], false);
                    }
                }
                else if (Main.juego.teclaEnTeclas(tecla, teclasArriba)) //Si se ha apretado arriba, desactiva abajo.
                {
                    //Desactiva todos los codigos que hagan la misma accion:
                    for (int x = 0; x < teclasAbajo.length; x++)
                    {
                        Teclado.teclasPulsadas.put(teclasAbajo[x], false);
                    }
                }
                else if (Main.juego.teclaEnTeclas(tecla, teclasAbajo)) //Si se ha apretado abajo, desactiva arriba.
                {
                    //Desactiva todos los codigos que hagan la misma accion:
                    for (int x = 0; x < teclasArriba.length; x++)
                    {
                        Teclado.teclasPulsadas.put(teclasArriba[x], false);
                    }
                }
                
                //Procesa la tecla acabada de pulsar:
                Teclado.procesarTecla(tecla, true);
                //Teclado.leerTeclado(false);
                //Main.juego.procesarTecla(e.getKeyCode());
                //System.out.println("Se aprieta " + tecla);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        //Si el juego esta activo:
        if (Main.juego != null)
        {
            //Si la tecla es una tecla valida para el juego:
            if (Main.juego.teclaEnTeclasTodas(e.getKeyCode()))
            {
                //Guarda como que la tecla se ha dejado de pulsar:
                Teclado.teclasPulsadas.put(e.getKeyCode(), false);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }
}
