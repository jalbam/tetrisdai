/** 
 * 
 * @author Joan Alba Maldonado
 */ 

//Falta: en totes les classes, impedir ficar valors no possibles en els sets (valors negatius, etc).

package logica;

import java.util.TimerTask;


public class TimerTeclado extends TimerTask
{
    protected Juego juego;
    
    public TimerTeclado()
    {
        super();
    }
    
    @Override
    public void run()
    {
        //Procesa las teclas pulsadas, si las hay:
        //System.out.println("[Se intenta llamar a lectorTeclado...]");
        Teclado.leerTeclado(true);
    }
}