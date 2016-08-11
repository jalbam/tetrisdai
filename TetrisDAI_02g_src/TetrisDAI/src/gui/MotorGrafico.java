/** 
 * 
 * @author Joan Alba Maldonado
 */ 

package gui;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import logica.Paleta;
import logica.Menu;
//import logica.Juego;
import logica.Panel;
import logica.Pieza;
import logica.Tablero;

import principal.Main;

import java.awt.Image;

import java.awt.Toolkit;
import java.net.URL;
import java.awt.MediaTracker;

import java.awt.image.BufferedImage;

//SUGERENCIA: ke los metodos de pintar solo pinten en el graficoBackup, y luego al final llamar aun metodo para ke pinte este en pantalla. asi solo se ahorraria en memoria y se ganaria velocidad.

public class MotorGrafico
{
    //Grafico que guarda lo que hay en pantalla:
    static protected int[][] graficoBackup = new int[Byte.MAX_VALUE][Byte.MAX_VALUE]; //Se pone lo maximo que puede ocupar un dato de tipo byte, para que nunca se quede corto.
    static protected boolean[][] graficoBackupPintado = new boolean[Byte.MAX_VALUE][Byte.MAX_VALUE]; //Define que partes del grafico backup han sido pintadas.
    
    static protected boolean graficoInicializado = false; //Define si el grafico de backup ha sido inicializado (si el tablero ha sido dibujado alguna vez).
    
    static protected final int numeroCodificacionPiezas = configuracion.Otros.getNumeroCodificacionPiezas();
    
    static protected final int VACIO = configuracion.Otros.getNumeroVacio(); //Numero que nos marca un vacio.

    static protected boolean usarImagenes = configuracion.Aspecto.getUsarImagenes();
    
    static protected byte celdaAncho = configuracion.Aspecto.getCeldaAncho();
    static protected byte celdaAlto = configuracion.Aspecto.getCeldaAlto();
    
    static protected Image[] imagenes = new Image[Byte.MAX_VALUE];
    static protected String directorioImagenes = configuracion.Otros.getDirectorioImagenes();


    public MotorGrafico() //Permito la instanciacion para poder hacer toString().
    {
    }

    static public void setUsarImagenes(boolean usarImagenes)
    {
        MotorGrafico.usarImagenes = usarImagenes;
    }
    
    static public boolean getUsarImagenes()
    {
        return MotorGrafico.usarImagenes;
    }
    
    static public void setCeldaAncho(byte celdaAncho)
    {
        MotorGrafico.celdaAncho = celdaAncho;
    }
    
    static public byte getCeldaAncho()
    {
        return MotorGrafico.celdaAncho;
    }

    static public void setCeldaAlto(byte celdaAlto)
    {
        MotorGrafico.celdaAlto = celdaAlto;
    }
    
    static public byte getCeldaAlto()
    {
        return MotorGrafico.celdaAlto;
    }
    
    //Dibuja un tablero, una pieza o el panel:
    static public void dibujar(Graphics g, Object objeto)
    {
        if (objeto instanceof Pieza) { dibujarPieza(g, (Pieza) objeto); }
        else if (objeto instanceof Tablero) { dibujarTablero(g, (Tablero) objeto); }
        else if (objeto instanceof Panel) { dibujarPanel(g, (Panel) objeto); }
        else if (objeto instanceof Menu) { dibujarMenu(g, (Menu) objeto); }
    }

    //Dibuja una pieza:
    static public void dibujarPieza(Graphics g, Pieza pieza)
    {
        Paleta paleta = pieza.getPaleta();
        byte x = pieza.getLeft();
        byte y = pieza.getTop();
        byte ancho = pieza.getAncho();
        byte alto = pieza.getAlto();
        int[][] matriz = pieza.getForma();

        MotorGrafico.dibujarMatriz(g, paleta, matriz, x, y, ancho, alto, false);
    }
    
    //Dibuja un tablero:
    static public void dibujarTablero(Graphics g, Tablero tablero)
    {
        Paleta paleta = tablero.getPaleta();
        byte x = 0;
        byte y = 0;
        byte ancho = tablero.getAncho();
        byte alto = tablero.getAlto();
        int[][] matriz = tablero.getMapa();

        MotorGrafico.dibujarMatriz(g, paleta, matriz, x, y, ancho, alto, true);
    }
    
    //Dibuja un panel:
    static public void dibujarPanel(Graphics g, Panel panel)
    {
        Paleta paleta = panel.getPaleta();
        byte x = panel.getLeft();
        byte y = panel.getTop();
        byte ancho = panel.getAncho();
        byte alto = panel.getAlto();
        int[][] matriz = panel.getMapa();

        MotorGrafico.dibujarMatriz(g, paleta, matriz, x, y, ancho, alto, true);
        
        //Muestra tambien la informacion del panel:
        dibujarPanelInformacion(g, panel);
    }
    
    //Dibuja la informacion del panel:
    static protected void dibujarPanelInformacion(Graphics g, Panel panel)
    {
        int nivel = panel.getNivel();
        int puntos = panel.getPuntos();
        int lineas = panel.getLineas();
        int lineasNivel = panel.getLineasNivel();
        
        int x = panel.getLeft() * MotorGrafico.getCeldaAncho();
        int y = (panel.getTop() + panel.getAlto() + panel.getMargen()) * MotorGrafico.getCeldaAlto() + configuracion.Aspecto.getEspacioLineasTexto();
 
        //Borra el texto anterior, por si quedaba en pantalla:
        g.clearRect(x, y - configuracion.Aspecto.getEspacioLineasTexto(), 400, configuracion.Aspecto.getEspacioLineasTexto() * 5);
        
        //Pone el texto:
        Color colorTexto = panel.getPaleta().getColor(panel.getColorTexto());
        Font fuenteTexto = new Font(Font.SANS_SERIF, Font.PLAIN, configuracion.Aspecto.getDimensionTexto());
        g.setColor(colorTexto);
        g.setFont(fuenteTexto);
        x += configuracion.Aspecto.getDimensionTexto();
        MotorGrafico.dibujarTexto(g, configuracion.Idiomas.nivel[principal.Main.getIdiomaActual()] + ": " + nivel, x, y, configuracion.Aspecto.getEspacioLineasTexto()); y += configuracion.Aspecto.getEspacioLineasTexto();
        MotorGrafico.dibujarTexto(g, configuracion.Idiomas.puntuacion[principal.Main.getIdiomaActual()] + ": " + puntos, x, y, configuracion.Aspecto.getEspacioLineasTexto()); y += configuracion.Aspecto.getEspacioLineasTexto();
        MotorGrafico.dibujarTexto(g, configuracion.Idiomas.lineasTotales[principal.Main.getIdiomaActual()] + ": " + lineas, x, y, configuracion.Aspecto.getEspacioLineasTexto()); y += configuracion.Aspecto.getEspacioLineasTexto();
        MotorGrafico.dibujarTexto(g, configuracion.Idiomas.lineasNivel[principal.Main.getIdiomaActual()] + ": " + lineasNivel, x, y, configuracion.Aspecto.getEspacioLineasTexto()); y += configuracion.Aspecto.getEspacioLineasTexto();
    }
    
    //Dibuja un menu:
    static public void dibujarMenu(Graphics g, Menu menu)
    {
        //FALTA: color de fondo y dibujo de la opcion aktual.
        String[] opciones = menu.getOpciones();
        
        Color colorTexto = menu.getPaleta().getColor(menu.getColorTexto());
        Color colorTextoFijo = menu.getPaleta().getColor(menu.getColorTextoFijo());
        Color colorTextoFondo = menu.getPaleta().getColor(menu.getColorTextoFondo());
        Color colorTextoSeleccionado = menu.getPaleta().getColor(menu.getColorTextoSeleccionado());
        Color colorTextoFondoSeleccionado = menu.getPaleta().getColor(menu.getColorTextoFondoSeleccionado());
        Color colorTextoAlternativo = menu.getPaleta().getColor(menu.getColorTextoAlternativo());
        Color colorTextoFondoAlternativo = menu.getPaleta().getColor(menu.getColorTextoFondoAlternativo());

        Font fuenteTexto = new Font(Font.SANS_SERIF, Font.PLAIN, configuracion.Aspecto.getDimensionTexto());
        g.setFont(fuenteTexto);

        //Pone el texto permanente:
        g.setColor(colorTextoFijo);
        MotorGrafico.dibujarTexto(g, menu.getTextoFijo(), menu.getTextoFijoLeft(), menu.getTextoFijoTop(), configuracion.Aspecto.getEspacioLineasTexto());
        
        //Dibuja el logo:
        int[][] logo = menu.getLogo();
        MotorGrafico.dibujarMatriz(g, menu.getPaleta(), logo, menu.getLeftLogo(), menu.getTopLogo(), (byte) logo[0].length, (byte) logo.length, false);
        
        int top = menu.getTop();
        int left = menu.getLeft();
        
        //FALTA: si top y/o left son mas grandes que el graphics, ponerlo al maximo o donde se kiera poner.
         
        //Pone el dibujo de la opcion actual (si tiene):
        int[][] dibujo;
        dibujo = menu.getDibujoActual();
        if (dibujo != null)
        {
            MotorGrafico.dibujarMatriz(g, menu.getPaleta(), dibujo, menu.getLeftDibujoActual(), menu.getTopDibujoActual(), (byte) dibujo[0].length, (byte) dibujo.length, false);
        }
        
        //Pone las opciones del menu:
        for (int x = 0; x < opciones.length; x++)
        {
            //FALTA: color de fondo!!!!!

            //Si la opcion esta marcada, la marca con los colores pertinentes:
            if (x == menu.getOpcionActualIndice())
            {
                //Muestra su dibujo, si tiene:
                /*dibujo = menu.getDibujoActual();
                if (dibujo != null)
                {
                    MotorGrafico.dibujarMatriz(g, menu.getPaleta(), dibujo, menu.getLeftDibujoActual(), menu.getTopDibujoActual(), (byte) dibujo[0].length, (byte) dibujo.length, false);
                }*/
                //Pone el color para el texto de la opcion:
                g.setColor(colorTextoSeleccionado);
            }
            else { g.setColor(colorTexto); }

            MotorGrafico.dibujarTexto(g, opciones[x], left, top, configuracion.Aspecto.getEspacioLineasTexto());
            top += configuracion.Aspecto.getEspacioLineasTexto();
        }
        
        //Pone el texto alternativo actual (si hay):
        //FALTA: color de fondo!!!!!
        if (menu.getTextoAlternativoActual() != null)
        {
            if (menu.getTextoAlternativoActual().trim() != "")
            {
                g.setColor(colorTextoAlternativo);
                left = menu.getLeftTextoAlternativoActual();
                top = menu.getTopTextoAlternativoActual();
                MotorGrafico.dibujarTexto(g, menu.getTextoAlternativoActual(), left, top, configuracion.Aspecto.getEspacioLineasTexto());
            }
        }
    }

    //Dibuja un texto (teniendo en cuenta los saltos de linea), con un color:
    static public void dibujarTexto(Graphics g, String texto, int left, int top, int espacioEntreLineas, Color colorTexto)
    {
        g.setColor(colorTexto);
        MotorGrafico.dibujarTexto(g, texto, left, top, espacioEntreLineas);
    }
    
    //Dibuja un texto (teniendo en cuenta los saltos de linea):
    static public void dibujarTexto(Graphics g, String texto, int left, int top, int espacioEntreLineas)
    {
        int widthMayor = 0;
        int lineas = 0;
        
        String textoParcial;
        String[] textoLineas = new String[100];
        
        int topActual = top;

        //Nota: se podria mejorar interpretando los \t (tabuladores).
        while (texto.trim().length() > 0)
        {
            if (texto.indexOf("\n") != -1) { textoParcial = texto.substring(0, texto.indexOf("\n")); }
            else { textoParcial = texto; }
            if (texto.indexOf("\n") != -1) { texto = texto.substring(texto.indexOf("\n") + 1); }
            else { texto = ""; }
            if (widthMayor < textoParcial.length()) { widthMayor = textoParcial.length(); }
            textoLineas[lineas] = textoParcial;
            lineas++;
        }

        for (int x = 0; x < lineas; x++)
        {
            g.drawString(textoLineas[x], left, topActual);
            topActual += espacioEntreLineas;
        }
    }
    
    //Dibuja el grafico backup en pantalla:
    static public void dibujarGraficoBackup(Graphics g, Paleta paleta, int[][] matriz, byte left, byte top, byte ancho, byte alto, boolean dibujarVacios)
    {
        int[][] graficoBackup = new int[alto][ancho];
        
        for (int f = 0; f < alto; f++)
        {
            for (int c = 0; c < ancho; c++)
            {
                if (MotorGrafico.graficoBackupPintado[f][c]) { graficoBackup[f][c] = matriz[f][c]; }
                else { graficoBackup[f][c] = MotorGrafico.VACIO; }
            }
        }
        
        MotorGrafico.dibujarMatriz(g, paleta, graficoBackup, left, top, ancho, alto, dibujarVacios);
    }
    
    //Dibuja una matriz en una posicion inicial dada:
    static synchronized public void dibujarMatriz(Graphics g, Paleta paleta, int[][] matriz, byte left, byte top, byte ancho, byte alto, boolean dibujarVacios)
    {
        if (g == null) { System.out.println("Graphics es null!!!!!"); return; }
        
        //Si el tablero no ha sido dibujado antes, pone todo el grafico backup a -1 (para asegurarse de que se pinte):
        if (!MotorGrafico.graficoInicializado) { MotorGrafico.inicializarGraficoBackup(); MotorGrafico.graficoInicializado = true; }
        
        int indiceColor;
        byte x, y = top;
        for (byte f = 0; f < alto; f++)
        {
            x = left;
            for (byte c = 0; c < ancho; c++)
            {
                indiceColor = matriz[f][c];

                //Si es un vacio y se ha enviado no dibujarlos, pasa de loop:
                if (indiceColor == MotorGrafico.VACIO && !dibujarVacios) { x++; continue; }

                MotorGrafico.dibujarCasilla(g, paleta, indiceColor, x, y);

                x++;
            }
            y++;
        }
    }
    
    //Dibuja una casilla en una posicion dada:
    static synchronized protected void dibujarCasilla(Graphics g, Paleta paleta, int indiceColor, byte x, byte y) //////////
    {
        //Si se ha enviado la x o la y negativas, sale de la funcion:
        if (x < 0 || y < 0) { return; }
        
        //Decodifica el color, si hace falta:
        if (indiceColor < 0) { indiceColor /= MotorGrafico.numeroCodificacionPiezas; }

        //Si la casilla que se va a pintar es igual a la que ya hay (y ua se ha pintado), no la pinta:
        if (MotorGrafico.graficoBackup[y][x] == indiceColor && graficoBackupPintado[y][x]) { return; } //<-- parece ser ke no va porke el metodo paint lo borra todo y deja el background!!!
        else { MotorGrafico.actualizarGraficoBackup(indiceColor, x, y); } //Actualiza el grafico backup, para la proxima vez.
       
        //Pone el color que corresponde:
        Color color = paleta.getColor(indiceColor);
        g.setColor(color);

        //Coge el alto y ancho de la casilla definidos en la configuracion:
        int xReal = x * MotorGrafico.getCeldaAncho();
        int yReal = y * MotorGrafico.getCeldaAlto();
        
        boolean celdaRepresentada = false;
        
        if (MotorGrafico.getUsarImagenes())
        {
            //Si no se ha cargado la imagen, la intenta cargar:
            if (MotorGrafico.imagenes[indiceColor] == null) { MotorGrafico.cargarImagen(indiceColor); }
            
            //Si la imagen existe y se ha cargado bien, la representa:
            Image img = MotorGrafico.imagenes[indiceColor];
            if (img != null)
            {
                MediaTracker tracker = null;
                
                if (configuracion.Otros.getTipoPrograma() == 0)
                {
                    tracker = new MediaTracker(Main.ventanaPrincipal);
                }
                else if (configuracion.Otros.getTipoPrograma() == 1)
                {
                    tracker = new MediaTracker(Main.appletPrincipal);
                }
                
                if (tracker != null)
                {
                    tracker.addImage(img, 1);

                    /*
                    while (tracker.checkID(1) == false)
                    {
                        //Espera a que la imagen este cargarda. Quiza seria bueno poner un tope de tiempo.
                    }
                    */

                    try
                    {
                        tracker.waitForAll();
                    }
                    catch (Exception e) { System.out.println("Excepcion: " + e.getMessage()); }

                    g.drawImage(img, xReal, yReal, MotorGrafico.getCeldaAncho(), MotorGrafico.getCeldaAlto(), null);
                    celdaRepresentada = true;
                }
            }
        }
        
        if (!celdaRepresentada) { g.fillRect(xReal, yReal, MotorGrafico.getCeldaAncho(), MotorGrafico.getCeldaAlto()); }
    }
    
    //Actualiza el grafico backup:
    static protected void actualizarGraficoBackup(int indiceColor, byte x, byte y)
    {
        //Si la casilla es diferente a la que habia antes, la guarda:
        if (MotorGrafico.graficoBackup[y][x] != indiceColor)
        {
            MotorGrafico.graficoBackup[y][x] = indiceColor;
            MotorGrafico.graficoBackupPintado[y][x] = true;
        }
    }
    
    //Inicializa ("vacia") el grafico backup:
    static public void inicializarGraficoBackup()
    {
        for (byte f = 0; f < MotorGrafico.graficoBackup.length; f++)
        {
            for (byte c = 0; c < MotorGrafico.graficoBackup[0].length; c++)
            {
                MotorGrafico.graficoBackup[f][c] = -1;
                MotorGrafico.graficoBackupPintado[f][c] = false;
            }
        }
    }
    
    //Carga las imagenes:
    static protected void cargarImagen(int indice)
    {
        Image img = null;
        
        ClassLoader classLoader = MotorGrafico.class.getClassLoader();
        URL gifURL = classLoader.getResource(MotorGrafico.getDirectorioImagenes() + indice + ".gif");
        URL jpgURL = classLoader.getResource(MotorGrafico.getDirectorioImagenes() + indice + ".jpg");
        URL jpegURL = classLoader.getResource(MotorGrafico.getDirectorioImagenes() + indice + ".jpeg");
        URL pngURL = classLoader.getResource(MotorGrafico.getDirectorioImagenes() + indice + ".png");
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        
        if (gifURL != null)
        {
            img = toolkit.getImage(gifURL);
        }
        else if (jpgURL != null)
        {
            img = toolkit.getImage(jpgURL);
        }
        else if (jpegURL != null)
        {
            img = toolkit.getImage(jpegURL);
        }
        else if (pngURL != null)
        {
            img = toolkit.getImage(pngURL);
        }
        
        MotorGrafico.imagenes[indice] = img;
    }

    //Devuelve la imagen de fondo, si existe (si no, devuelve null):
    static protected Image getImagenFondo()
    {
        Image img = null;
        
        ClassLoader classLoader = MotorGrafico.class.getClassLoader();
        URL gifURL = classLoader.getResource(MotorGrafico.getDirectorioImagenes() + "bg.gif");
        URL jpgURL = classLoader.getResource(MotorGrafico.getDirectorioImagenes() + "bg.jpg");
        URL jpegURL = classLoader.getResource(MotorGrafico.getDirectorioImagenes() + "bg.jpeg");
        URL pngURL = classLoader.getResource(MotorGrafico.getDirectorioImagenes() + "bg.png");
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        
        if (gifURL != null)
        {
            img = toolkit.getImage(gifURL);
        }
        else if (jpgURL != null)
        {
            img = toolkit.getImage(jpgURL);
        }
        else if (jpegURL != null)
        {
            img = toolkit.getImage(jpegURL);
        }
        else if (pngURL != null)
        {
            img = toolkit.getImage(pngURL);
        }
        
        return img;
    }
    
    static public void setDirectorioImagenes(String directorioImagenes)
    {
        MotorGrafico.directorioImagenes = directorioImagenes;
    }
    
    static public String getDirectorioImagenes()
    {
        //return MotorGrafico.directorioImagenes;
        String directorio = MotorGrafico.directorioImagenes;
        
        /*
        if (configuracion.Otros.getTipoPrograma() == 0)
        {
            directorio = "/" + directorio;
        }
        else if (configuracion.Otros.getTipoPrograma() == 1)
        {
            //String pathConArchivo = Main.appletPrincipal.getDocumentBase().toString();
            String pathConArchivo = Main.appletPrincipal.getCodeBase().getPath();
            
            directorio = "/" + pathConArchivo.substring(0, pathConArchivo.lastIndexOf("/") + 1) + directorio;
            
            //Main.appletPrincipal.getCodeBase().
        }
        */
        //System.out.println("Retornando: " + directorio);
        
        return directorio;
    }
    
    //Borra una imagen de buffer:
    static public void limpiarImagenBuffer(BufferedImage imagenBuffer, Paleta paleta)
    {
        //Nota: a pesar de que se represente la imagen, se sigue poniendo el color de fondo (por si queremos poner una imagen transparente encima).
        
        Graphics contenedorBuffer = imagenBuffer.getGraphics();

        //Limpia visualmente la imagen de buffer, con el color de fondo predefinido:
        contenedorBuffer.setColor(configuracion.Aspecto.getColorFondoImagenBuffer());
        contenedorBuffer.fillRect(0, 0, imagenBuffer.getWidth(), imagenBuffer.getHeight());
        
        //Si esta definido utilizar una imagen de fondo y esta existe, la pone:
        if (configuracion.Aspecto.getUsarImagenFondo())
        {
            Image img = getImagenFondo();
            
            if (img != null)
            {
                MediaTracker tracker = null;
                
                if (configuracion.Otros.getTipoPrograma() == 0)
                {
                    tracker = new MediaTracker(Main.ventanaPrincipal);
                }
                else if (configuracion.Otros.getTipoPrograma() == 1)
                {
                    tracker = new MediaTracker(Main.appletPrincipal);
                }
                
                if (tracker != null)
                {
                    tracker.addImage(img, 1);

                    /*
                    while (tracker.checkID(1) == false)
                    {
                        //Espera a que la imagen este cargarda. Quiza seria bueno poner un tope de tiempo.
                    }
                    */

                    try
                    {
                        tracker.waitForAll();
                    }
                    catch (Exception e) { System.out.println("Excepcion: " + e.getMessage()); }

                    //contenedorBuffer.drawImage(img, 0, 0, imagenBuffer.getWidth(), imagenBuffer.getHeight(), Main.ventanaPrincipal);
                    contenedorBuffer.drawImage(img, 0, 0, imagenBuffer.getWidth(), imagenBuffer.getHeight(), null);
                }
            }
        }
        
        //Guarda el grafico backup antes de borrarlo:
        //int[][] graficoBackupAnterior = MotorGrafico.graficoBackup;
        
        //Borra el grafico de backup del motor grafico:
        //MotorGrafico.inicializarGraficoBackup();
        
        //Vuelve a dibujar encima de la imagen de buffer, con lo que habia en graficoBackup:
        int ancho = imagenBuffer.getWidth() / MotorGrafico.getCeldaAncho() + 1;
        int alto =  imagenBuffer.getHeight() / MotorGrafico.getCeldaAlto() + 1;
        
        if (ancho > Byte.MAX_VALUE) { ancho = Byte.MAX_VALUE; }
        if (alto > Byte.MAX_VALUE) { alto = Byte.MAX_VALUE; }
        
        //MotorGrafico.dibujarGraficoBackup(contenedorBuffer, paleta, graficoBackupAnterior, (byte) 0, (byte) 0, ancho, alto, false);
        
        //if (ancho < 0) { ancho = 100; } //Hago esto porque en Linux da negativo.
        //if (alto < 0) { ancho = 100; } //Hago esto porque en Linux da negativo.
        
        //if (ancho < 0) { System.out.println("[ALERTA] El ancho del grafico backup se ha calcualdo negativo: " + ancho); ancho = 100; } //Hago esto porque en Linux da negativo.
        //if (alto < 0) { System.out.println("[ALERTA] El alto del grafico backup se ha calcualdo negativo: " + alto);  alto = 100; } //Hago esto porque en Linux da negativo.
        
        MotorGrafico.dibujarGraficoBackup(contenedorBuffer, paleta, MotorGrafico.graficoBackup, (byte) 0, (byte) 0, (byte) ancho, (byte) alto, false);
        
        //Borra el grafico de backup del motor grafico:
        MotorGrafico.inicializarGraficoBackup();
        
        //if (Main.juego != null && Main.juego.getContenedorGrafico() != null) { MotorGrafico.dibujarImagenBuffer(Main.juego.getContenedorGrafico(), imagenBuffer); }
    }

    //Dibuja una imagen de buffer en un contenedor grafico:
    static public void dibujarImagenBuffer(Graphics contenedor, BufferedImage imagenBuffer)
    {
        //contenedor.drawImage(imagenBuffer, 0, 0, null);
        
        MediaTracker tracker = null;
                
        if (configuracion.Otros.getTipoPrograma() == 0)
        {
            tracker = new MediaTracker(Main.ventanaPrincipal);
        }
        else if (configuracion.Otros.getTipoPrograma() == 1)
        {
            tracker = new MediaTracker(Main.appletPrincipal);
        }

        if (tracker != null)
        {
            tracker.addImage(imagenBuffer, 1);

            /*
            while (tracker.checkID(1) == false)
            {
                //Espera a que la imagen este cargarda. Quiza seria bueno poner un tope de tiempo.
            }
            */

            try
            {
                tracker.waitForAll();
            }
            catch (Exception e) { System.out.println("Excepcion: " + e.getMessage()); }

            contenedor.drawImage(imagenBuffer, 0, 0, null);
        }
    }
    
    public String toString()
    {
        return MotorGrafico.toString(null);
    }
    
    static public String toString(Object o)
    {
        String cadena = "\n";
        byte filas = 30;
        byte columnas = 30;
        if (Main.juego != null)
        {
            filas = (byte) (Main.juego.getTablero().getAlto());
            columnas = (byte) (Main.juego.getPanel().getLeft() + Main.juego.getPanel().getAncho());
        }
        
        for (byte f = 0; f < filas; f++)
        {
            for (byte c = 0; c < columnas; c++)
            {
                cadena += MotorGrafico.graficoBackup[f][c] + "   ";
            }
            cadena += "\n";
        }
        return cadena;
    }
}
