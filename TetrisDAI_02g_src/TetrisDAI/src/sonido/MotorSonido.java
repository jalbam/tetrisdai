/** 
 * 
 * @author Joan Alba Maldonado
 */ 

//Falta: en totes les classes, impedir ficar valors no possibles en els sets (valors negatius, etc).

//Falta: poner todos los sonidos ke se sirvan en un vektor o hashtable, i poder invokar a un metodo para parar de reproducirlos todos, i otro para borrarlos. poder pasar por argumento a las funciones de reproducir sonidos ke se eliminen automatikamente de este vektor.

//ERROR: en el AMD64 solo reproduce los sonidos a veces (?) :(

package sonido;

import java.util.Hashtable;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

//import java.lang.Thread;

public class MotorSonido
{
    static protected String directorioSonido = configuracion.Otros.getDirectorioSonido();
    
    static protected String sonidoPiezaColisionada = "colision.wav"; //Archivo de sonido al colisionar una pieza.
    static protected String sonidoPiezaRotada = "rotar.wav"; //Archivo de sonido al colisionar una pieza.
    static protected String sonidoLineaHecha = "linea.wav"; //Archivo de sonido al colisionar una pieza.
    static protected String sonidoMenuOpcionCambiada = "menu_cambiar.wav"; //Archivo de sonido al cambiar de opcion del menu.
    static protected String sonidoMenuOpcionAceptada = "menu_aceptar.wav"; //Archivo de sonido al aceptar una opcion del menu.
    
    static protected Hashtable<String, AudioClip> sonidos = new Hashtable<String, AudioClip>(); //Hashtable con los submenus (si los hay).
    
    static public boolean sonidosCargados = false;
    
    private MotorSonido()
    {
    }
    
    static public void setDirectorioSonido(String directorioSonido)
    {
        MotorSonido.directorioSonido = directorioSonido;
    }
    
    static public String getDirectorioSonido()
    {
        return MotorSonido.directorioSonido;
    }
    
    static public void setSonidoPiezaColisionada(String sonidoPiezaColisionada)
    {
        MotorSonido.sonidoPiezaColisionada = sonidoPiezaColisionada;
    }
    
    static public String getSonidoPiezaColisionada()
    {
        return MotorSonido.sonidoPiezaColisionada;
    }

    static public void setSonidoPiezaRotada(String sonidoPiezaRotada)
    {
        MotorSonido.sonidoPiezaRotada = sonidoPiezaRotada;
    }
    
    static public String getSonidoPiezaRotada()
    {
        return MotorSonido.sonidoPiezaRotada;
    }

    static public void setSonidoLineaHecha(String sonidoLineaHecha)
    {
        MotorSonido.sonidoLineaHecha = sonidoLineaHecha;
    }
    
    static public String getSonidoLineaHecha()
    {
        return MotorSonido.sonidoLineaHecha;
    }

    static public void setSonidoMenuOpcionCambiada(String sonidoMenuOpcionCambiada)
    {
        MotorSonido.sonidoMenuOpcionCambiada = sonidoMenuOpcionCambiada;
    }
    
    static public String getSonidoMenuOpcionCambiada()
    {
        return MotorSonido.sonidoMenuOpcionCambiada;
    }

    static public void setSonidoMenuOpcionAceptada(String sonidoMenuOpcionAceptada)
    {
        MotorSonido.sonidoMenuOpcionAceptada = sonidoMenuOpcionAceptada;
    }
    
    static public String getSonidoMenuOpcionAceptada()
    {
        return MotorSonido.sonidoMenuOpcionAceptada;
    }
    
    static public void cargarSonidos()
    {
        MotorSonido.sonidos.put(MotorSonido.getSonidoPiezaColisionada(), MotorSonido.getSonido(MotorSonido.getSonidoPiezaColisionada()));
        MotorSonido.sonidos.put(MotorSonido.getSonidoPiezaRotada(), MotorSonido.getSonido(MotorSonido.getSonidoPiezaRotada()));
        MotorSonido.sonidos.put(MotorSonido.getSonidoLineaHecha(), MotorSonido.getSonido(MotorSonido.getSonidoLineaHecha()));
        MotorSonido.sonidos.put(MotorSonido.getSonidoMenuOpcionCambiada(), MotorSonido.getSonido(MotorSonido.getSonidoMenuOpcionCambiada()));
        MotorSonido.sonidos.put(MotorSonido.getSonidoMenuOpcionAceptada(), MotorSonido.getSonido(MotorSonido.getSonidoMenuOpcionAceptada()));
        
        MotorSonido.sonidosCargados = true;
    }
    
    static public AudioClip getSonido(String archivo)
    {
        AudioClip sonido = null;
        
        ClassLoader classLoader = MotorSonido.class.getClassLoader();
        URL sonidoURL = classLoader.getResource(MotorSonido.getDirectorioSonido() + archivo);
        
        if (sonidoURL != null)
        {
            sonido = Applet.newAudioClip(sonidoURL);
        }
        
        return sonido;
    }

    static protected AudioClip reproducirSonidoHilo(String nombreSonido)
    {
        if (!sonidosCargados) { MotorSonido.cargarSonidos(); }
        AudioClip sonido = MotorSonido.sonidos.get(nombreSonido);
        if (sonido != null)
        {
            Thread nuevoHilo = new ThreadSonido(sonido);
            nuevoHilo.start();
            //MotorSonido.reproducirSonido(sonido);
        }
        return sonido;
    }
    
    static public AudioClip reproducirSonido(String archivo)
    {
        AudioClip sonido = MotorSonido.getSonido(archivo);
        
        if (sonido != null) { return reproducirSonido(sonido); }
        else { System.out.println("Imposible cargar el archivo de audio " + archivo); return null; }
    }

    static public AudioClip reproducirSonido(AudioClip sonido)
    {
        return reproducirSonido(sonido, false);
    }

    static public AudioClip reproducirSonido(AudioClip sonido, boolean loop)
    {
        if (loop) { sonido.loop(); }

        sonido.play();
        
        return sonido;
    }
    
    static public void pararSonido(AudioClip sonido)
    {
        sonido.stop();
    }
    
    static public AudioClip piezaColisionada()
    {
        return MotorSonido.reproducirSonidoHilo(MotorSonido.getSonidoPiezaColisionada());
    }
    
    static public AudioClip piezaRotada()
    {
        return MotorSonido.reproducirSonidoHilo(MotorSonido.getSonidoPiezaRotada());
    }
    
    static public AudioClip lineaHecha()
    {
        return MotorSonido.reproducirSonidoHilo(MotorSonido.getSonidoLineaHecha());
    }
    
    static public AudioClip menuOpcionCambiada()
    {
        return MotorSonido.reproducirSonidoHilo(MotorSonido.getSonidoMenuOpcionCambiada());
    }
    
    static public AudioClip menuOpcionAceptada()
    {
        return MotorSonido.reproducirSonidoHilo(MotorSonido.getSonidoMenuOpcionAceptada());
    }
}
