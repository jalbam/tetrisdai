/** 
 * 
 * @author Joan Alba Maldonado
 */ 

package sonido;

//import java.lang.Thread;

import java.applet.AudioClip;

public class ThreadSonido extends Thread
{
    protected AudioClip sonido = null;
    protected boolean loop;

    public ThreadSonido(AudioClip sonido)
    {
        this(sonido, false);
    }
    
    public ThreadSonido(AudioClip sonido, boolean loop)
    {
        this.setSonido(sonido);
        this.setLoop(loop);
    }
    
    public void setSonido(AudioClip sonido)
    {
        this.sonido = sonido;
    }
    
    public AudioClip getSonido()
    {
        return this.sonido;
    }
    
    public void setLoop(boolean loop)
    {
        this.loop = loop;
    }
    
    public boolean getLoop()
    {
        return this.loop;
    }
    
    @Override
    public void run()
    {
        AudioClip sonido = this.getSonido();
        //sonido.play();
        MotorSonido.reproducirSonido(sonido, this.getLoop());
    }
}
