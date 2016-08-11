/** 
 * 
 * @author Joan Alba Maldonado
 */ 

package logica;

import java.util.Hashtable;

import java.awt.Graphics;

import java.awt.image.BufferedImage;

import principal.Main;

import gui.MotorGrafico;


//FALTA: buscar alguna manera de centrar el texto del menu.

//FALTA: Poner antes de kada opcion una llamada rekursiva si se esta en una opcion (y se ha entrado en ella) ke va hacia un submenu.

//FALTA: instanciar kon new los vektores tipo dibujos, textosAlternativos, etc. Y MIRAR SI ESTAN TODOS BIEN.

//FALTA: implementar la rekursividad sea donde sea k lo necesite (muxo kuidado donde se pone).

//NOTA: la rekursividad de getOpcionAceptadaSubmenuRecursiva() no esta mui klara, kiza deberia llamar a getOpcionAceptadaSubmenuRecursivaOptimista() rekursivamente para devolver false si es el primer nodo (menu principal) o lo ke devuelvn sus submenus (sean o no nodos).

//FALTA: (puede ke ia solucionado) que al volver a entrar en un submenu, vuelva a la primera opcion i no a la ke estaba antes. pero al salir del submenu, vuelva a la opcion k estaba antes i no a la primera.

//FALTA: getWidth i getHeight para los dibujos. i ke MotorGrafico los use para hacer bucles (o para llamar a dibujarMatriz).

//Falta: en totes les classes, impedir ficar valors no possibles en els sets (valors negatius, etc).

//SUGERENCIA: kitar final a kolores, i poner sus set korrespondiente para kada uno.

public class Menu
{
    protected String[][] opciones; //Opciones del menu (multi-idioma).
    
    protected String[] valoresRetorno; //Valores que devuelven las opciones del menu.
    
    protected Hashtable<Integer, Menu> submenus = new Hashtable<Integer, Menu>(); //Hashtable con los submenus (si los hay).
    
    protected String textoFijo = ""; //Texto permanente (es el texto que sale en todas las opciones).
    protected int textoFijoLeft = 0; //Posicion horizontal del texto permanente.
    protected int textoFijoTop = 0; //Posicion vertical del texto permanente.
    
    protected int[][] logo = new int[Byte.MAX_VALUE][Byte.MAX_VALUE]; //Logo permanente (es el dibujo que sale en todas las opciones).
    protected int[][][] dibujos = new int[Byte.MAX_VALUE][Byte.MAX_VALUE][Byte.MAX_VALUE]; //Matriz opcional con dibujos, para mostrar con cada opcion del menu.
    protected String[][] textosAlternativos = new String[Byte.MAX_VALUE][Byte.MAX_VALUE]; //Vector opcional con texto alternativo, para mostrar con cada opcion del menu.
    
    protected Paleta paleta; //Paleta que utiliza el menu.

    protected int opcionActual = 0; //Opcion en la que nos encontramos.
    protected boolean opcionAceptada = false; //Indica si la opcion actual ha sido aceptada.
    
    protected boolean mostrar = false; //Define si se muestra o no el menu.
    
    protected final int colorTextoFijo = configuracion.Otros.getNumeroColorMenuTextoFijo(); //Numero de color del texto permamente del menu.
    protected final int colorTexto = configuracion.Otros.getNumeroColorMenuTexto(); //Numero de color del texto del menu.
    protected final int colorTextoFondo = configuracion.Otros.getNumeroColorMenuTextoFondo(); //Numero de color del fondo del texto del menu.
    protected final int colorTextoSeleccionado = configuracion.Otros.getNumeroColorMenuTextoSeleccionado(); //Numero de color del texto del menu (cuando una opcion esta seleccionada).
    protected final int colorTextoFondoSeleccionado = configuracion.Otros.getNumeroColorMenuTextoFondoSeleccionado(); //Numero de color del fondo del texto del menu (cuando una opcion esta seleccionada).
    protected final int colorTextoAlternativo = configuracion.Otros.getNumeroColorMenuTextoAlternativo(); //Numero de color del texto alternativo del menu.
    protected final int colorTextoFondoAlternativo = configuracion.Otros.getNumeroColorMenuTextoFondoAlternativo(); //Numero de color de fondo del texto alternativo del menu.
    
    protected int left = 0; //Posicion vertical del menu.
    protected int top = 0; //Posicion horizontal del menu.
    protected byte leftLogo = 0; //Posicion horizontal del logo.
    protected byte topLogo = 0; //Posicion vertical del logo.
    protected byte[] leftDibujos = new byte[Byte.MAX_VALUE]; //Posiciones verticales de los dibujos.
    protected byte[] topDibujos = new byte[Byte.MAX_VALUE]; //Posiciones horizontales de los dibujos.
    protected int[][] leftTextosAlternativos = new int[Byte.MAX_VALUE][Byte.MAX_VALUE]; //Posicion vertical de los textos alternativos.
    protected int[][] topTextosAlternativos = new int[Byte.MAX_VALUE][Byte.MAX_VALUE]; //Posicion horizontal de los textos alternativos.

    
    public Menu(Paleta paleta)
    {
        this.setPaleta(paleta);
    }
    
    public Menu(String[][] opciones, String valoresRetorno[], Paleta paleta)
    {
        this(opciones, valoresRetorno, paleta, 0, 0);
    }

    public Menu(String[][] opciones, String valoresRetorno[], Paleta paleta, int left, int top)
    {
        this(paleta);
        this.setOpciones(opciones);
        this.setValoresRetorno(valoresRetorno);
        this.setLeft(left);
        this.setTop(top);
    }
    
    public int getColorTexto()
    {
        return this.colorTexto;
    }

    public int getColorTextoFijo()
    {
        return this.colorTextoFijo;
    }
    
    public int getColorTextoFondo()
    {
        return this.colorTextoFondo;
    }
    
    public int getColorTextoSeleccionado()
    {
        return this.colorTextoSeleccionado;
    }
    
    public int getColorTextoFondoSeleccionado()
    {
        return this.colorTextoFondoSeleccionado;
    }
    
    public int getColorTextoAlternativo()
    {
        return this.colorTextoAlternativo;
    }
    
    public int getColorTextoFondoAlternativo()
    {
        return this.colorTextoFondoAlternativo;
    }

    public void setTextoFijo(String textoFijo, int textoFijoLeft, int textoFijoTop)
    {
        if (textoFijo != null)
        {
            this.textoFijo = textoFijo;
            this.setTextoFijoLeft(textoFijoLeft);
            this.setTextoFijoTop(textoFijoTop);
        }
    }
    
    public String getTextoFijo()
    {
        return this.textoFijo;
    }

    public void setTextoFijoLeft(int textoFijoLeft)
    {
        this.textoFijoLeft = textoFijoLeft;
    }
    
    public int getTextoFijoLeft()
    {
        return this.textoFijoLeft;
    }

    public void setTextoFijoTop(int textoFijoTop)
    {
        this.textoFijoTop = textoFijoTop;
    }
    
    public int getTextoFijoTop()
    {
        return this.textoFijoTop;
    }
    
    public void setTextosAlternativos(String[][] textosAlternativos)
    {
        this.textosAlternativos = textosAlternativos;
    }
    
    public String[][] getTextosAlternativos()
    {
        return this.textosAlternativos;
    }
    
    public void setTextoAlternativo(String textoAlternativo[], int indice, int[] leftTextoAlternativo, int[] topTextoAlternativo)
    {
        //Si no es un indice valido, sale de la funcion:
        if (indice < 0 || indice >= this.getTextosAlternativos().length) { return; }
        
        this.textosAlternativos[indice] = textoAlternativo;
        this.setLeftTextoAlternativo(leftTextoAlternativo, indice);
        this.setTopTextoAlternativo(topTextoAlternativo, indice);
    }
    
    public String getTextoAlternativo(int indice)
    {
        return this.getTextoAlternativo(indice, Main.getIdiomaActual());
    }

    public String getTextoAlternativo(int indice, int idioma)
    {
        //Si no es un indice valido, sale de la funcion:
        if (indice < 0 || indice >= this.getTextosAlternativos().length) { return null; }
        
        //Si no hay textos alternativos, sale de la funcion:
        if (this.getTextosAlternativos() == null) { return null; }
        
        return this.getTextosAlternativos()[indice][idioma];
    }
    
    public String getTextoAlternativoActual()
    {
        return this.getTextoAlternativo(this.getOpcionActualIndice());
    }
    
    public void setLogo(int logo[][], byte leftLogo, byte topLogo)
    {
        this.logo = logo;
        this.setLeftLogo(leftLogo);
        this.setTopLogo(topLogo);
    }
    
    public int[][] getLogo()
    {
        return this.logo;
    }
    
    public void setLeftLogo(byte leftLogo)
    {
        this.leftLogo = leftLogo;
    }
    
    public byte getLeftLogo()
    {
        return this.leftLogo;
    }
    
    public void setTopLogo(byte topLogo)
    {
        this.topLogo = topLogo;
    }
    
    public byte getTopLogo()
    {
        return this.topLogo;
    }
    
    public void setDibujos(int[][][] dibujos, byte leftDibujos[], byte topDibujos[])
    {
        this.dibujos = dibujos;
        this.setLeftDibujos(leftDibujos);
        this.setTopDibujos(topDibujos);
    }
    
    public int[][][] getDibujos()
    {
        return this.dibujos;
    }
    
    public void setDibujo(int[][] dibujo, byte leftDibujo, byte topDibujo, int indice)
    {
        //Si no es un indice valido, sale de la funcion:
        if (indice < 0 || indice >= this.numeroOpciones()) { return; }
        
        this.dibujos[indice] = dibujo;
        this.setLeftDibujo(leftDibujo, indice);
        this.setTopDibujo(topDibujo, indice);
    }
    
    public int[][] getDibujo(int indice)
    {
        //Si no es un indice valido, sale de la funcion:
        if (indice < 0 || indice >= this.getDibujos().length) { return null; }
        
        if (this.getDibujos() == null) { return null; }
        
        return this.getDibujos()[indice];
    }

    //Devuelve el dibujo de la opcion actualmente seleccionada:
    public int[][] getDibujoActual()
    {
        //Si esta dentro de un submenu, llama recursivamente:
        if (this.obtenerSubmenu(this.opcionActual) != null && this.opcionAceptada) { return this.obtenerSubmenu(this.opcionActual).getDibujoActual(); }

        return this.getDibujo(this.getOpcionActualIndice());
    }
    
    public void setOpciones(String[][] opciones)
    {
        this.opciones = opciones;
    }
    
    public String[] getOpciones()
    {
        return this.getOpciones(Main.getIdiomaActual());
    }

    public String[] getOpciones(int idioma)
    {
        String[] opciones = new String[this.numeroOpciones()];
        for (int x = 0; x < this.numeroOpciones(); x++)
        {
            opciones[x] = this.opciones[x][idioma];
        }
        return opciones;
    }
    
    public void setValoresRetorno(String[] valoresRetorno)
    {
        this.valoresRetorno = valoresRetorno;
    }
    
    public String[] getValoresRetorno()
    {
        return this.valoresRetorno;
    }
    
    public void setPaleta(Paleta paleta)
    {
        this.paleta = paleta;
    }
    
    public Paleta getPaleta()
    {
        return this.paleta;
    }
    
    public void setLeft(int left)
    {
        this.left = left;
    }
    
    public int getLeft()
    {
        return this.left;
    }
    
    public void setTop(int top)
    {
        this.top = top;
    }
    
    public int getTop()
    {
        return this.top;
    }
    
    public void setLeftDibujos(byte[] leftDibujos)
    {
        this.leftDibujos = leftDibujos;
    }
    
    public byte[] getLeftDibujos()
    {
        return this.leftDibujos;
    }
    
    public void setTopDibujos(byte[] topDibujos)
    {
        this.topDibujos = topDibujos;
    }
    
    public byte[] getTopDibujos()
    {
        return this.topDibujos;
    }
    
    public void setLeftDibujo(byte leftDibujo, int indice)
    {
        //Si no es un indice valido, sale de la funcion:
        if (indice < 0 || indice >= this.getLeftDibujos().length) { return; }

        //Si no se ha inicializado el vector, sale de la funcion:
        if (this.getLeftDibujos() == null) { return; }
        
        this.getLeftDibujos()[indice] = leftDibujo;
    }
    
    public byte getLeftDibujo(int indice)
    {
        //Si no es un indice valido, sale de la funcion:
        if (indice < 0 || indice >= this.getLeftDibujos().length) { return -1; }

        //Si no se ha inicializado el vector, sale de la funcion:
        if (this.getLeftDibujos() == null) { return -1; }
        
        return this.getLeftDibujos()[indice];
    }
    
    public void setTopDibujo(byte topDibujo, int indice)
    {
        //Si no es un indice valido, sale de la funcion:
        if (indice < 0 || indice >= this.getTopDibujos().length) { return; }

        //Si no se ha inicializado el vector, sale de la funcion:
        if (this.getTopDibujos() == null) { return; }
        
        this.getTopDibujos()[indice] = topDibujo;
    }
    
    public byte getTopDibujo(int indice)
    {
        //Si no es un indice valido, sale de la funcion:
        if (indice < 0 || indice >= this.getTopDibujos().length) { return -1; }

        //Si no se ha inicializado el vector, sale de la funcion:
        if (this.getTopDibujos() == null) { return -1; }
        
        return this.getTopDibujos()[indice];
    }

    public byte getLeftDibujoActual()
    {
        return this.getLeftDibujo(this.getOpcionActualIndice());
    }

    public byte getTopDibujoActual()
    {
        return this.getTopDibujo(this.getOpcionActualIndice());
    }
    
    public void setLeftTextosAlternativos(int[][] leftTextosAlternativos)
    {
        this.leftTextosAlternativos = leftTextosAlternativos;
    }
    
    public int[][] getLeftTextosAlternativos()
    {
        return this.leftTextosAlternativos;
    }

    public void setTopTextosAlternativos(int[][] topTextosAlternativos)
    {
        this.topTextosAlternativos = topTextosAlternativos;
    }
    
    public int[][] getTopTextosAlternativos()
    {
        return this.topTextosAlternativos;
    }
    
    public void setLeftTextoAlternativo(int[] leftTextoAlternativo, int indice)
    {
        //Si no es un indice valido, sale de la funcion:
        if (indice < 0 || indice >= this.getLeftTextosAlternativos().length) { return; }

        //Si no se ha inicializado el vector, sale de la funcion:
        if (this.getLeftTextosAlternativos() == null) { return; }

        this.getLeftTextosAlternativos()[indice] = leftTextoAlternativo;
    }
    
    public int[] getLeftTextoAlternativo(int indice)
    {
        //Si no es un indice valido, sale de la funcion:
        if (indice < 0 || indice >= this.getLeftTextosAlternativos().length) { return null; }

        //Si no se ha inicializado el vector, sale de la funcion:
        if (this.getLeftTextosAlternativos() == null) { return null; }
        
        return this.getLeftTextosAlternativos()[indice];
    }
    
    public void setTopTextoAlternativo(int[] topTextoAlternativo, int indice)
    {
        //Si no es un indice valido, sale de la funcion:
        if (indice < 0 || indice >= this.getTopTextosAlternativos().length) { return; }

        //Si no se ha inicializado el vector, sale de la funcion:
        if (this.getTopTextosAlternativos() == null) { return; }
        
        this.getTopTextosAlternativos()[indice] = topTextoAlternativo;
    }
    
    public int[] getTopTextoAlternativo(int indice)
    {
        //Si no es un indice valido, sale de la funcion:
        if (indice < 0 || indice >= this.getTopTextosAlternativos().length) { return null; }

        //Si no se ha inicializado el vector, sale de la funcion:
        if (this.getTopTextosAlternativos() == null) { return null; }
        
        return this.getTopTextosAlternativos()[indice];
    }

    public int getLeftTextoAlternativoActual()
    {
        return this.getLeftTextoAlternativo(this.getOpcionActualIndice())[Main.getIdiomaActual()];
    }

    public int getTopTextoAlternativoActual()
    {
        return this.getTopTextoAlternativo(this.getOpcionActualIndice())[Main.getIdiomaActual()];
    }
    
    public void setMostrar(boolean mostrar)
    {
        this.mostrar = mostrar;
    }
    
    public boolean getMostrar()
    {
        return this.mostrar;
    }

    public void setOpcionAceptada(boolean opcionAceptada)
    {
        this.opcionAceptada = opcionAceptada;
    }
    
    public boolean getOpcionAceptada()
    {
        return this.opcionAceptada;
    }
    
    public boolean getOpcionAceptadaSubmenu()
    {
        //Si esta dentro de un submenu, llama recursivamente:
        if (this.obtenerSubmenu(this.opcionActual) != null && this.opcionAceptada) { return this.obtenerSubmenu(this.opcionActual).getOpcionAceptada(); }
        
        return false; //return this.getOpcionAceptada(); //return false;
    }

    public boolean getOpcionAceptadaSubmenuRecursiva()
    {
        //Si esta dentro de un submenu, llama recursivamente:
        if (this.obtenerSubmenu(this.opcionActual) != null && this.opcionAceptada) { return this.obtenerSubmenu(this.opcionActual).getOpcionAceptadaSubmenuRecursiva(); }
        
        return this.getOpcionAceptada();
    }
    
    public void setOpcionActual(String valorDevuelto)
    {
        int indice = indiceValor(valorDevuelto);
        
        this.setOpcionActual(indice);
    }
    
    public void setOpcionActual(int opcion)
    {
        //Del principio vuelve al final y viceversa:
        if (opcion < 0) { opcion = this.numeroOpciones() - 1; }
        else if (opcion >= this.numeroOpciones()) { opcion = 0; }
        
        this.opcionActual = opcion;
        this.opcionAceptada = false;
    }
    
    //Devuelve el indice de la opcion actual, sin recursividad:
    public int getOpcionActualIndiceSinRecursividad()
    {
        return this.opcionActual;
    }
    
    //Devuelve el indice de la opcion actual:
    public int getOpcionActualIndice()
    {
        //Si esta dentro de un submenu, llama recursivamente:
        if (this.obtenerSubmenu(this.opcionActual) != null && this.opcionAceptada) { return this.obtenerSubmenu(this.opcionActual).getOpcionActualIndice(); }

        return this.opcionActual;
    }

    //Devuelve el texto de la opcion actual (del idioma actual):
    public String getOpcionActualTexto()
    {
        return this.getOpcionActualTexto(Main.getIdiomaActual());
    }

    //Devuelve el texto de la opcion actual (de un determinado idioma):
    public String getOpcionActualTexto(int idioma)
    {
        //Si esta dentro de un submenu, llama recursivamente:
        if (this.obtenerSubmenu(this.opcionActual) != null && this.opcionAceptada) { return this.obtenerSubmenu(this.opcionActual).getOpcionActualTexto(); }

        return this.opciones[this.getOpcionActualIndice()][idioma];
    }
   
    //Devuelve el valor de la opcion actual:
    public String getOpcionActualValor()
    {
        //Si esta dentro de un submenu, llama recursivamente:
        if (this.obtenerSubmenu(this.opcionActual) != null && this.opcionAceptada) { return this.obtenerSubmenu(this.opcionActual).getOpcionActualValor(); }
        
        return this.valoresRetorno[this.getOpcionActualIndice()];
    }
    
    //Agrega una opcion al menu, en la ultima posicion:
    public void agregarOpcion(String opcion[], String valorRetorno)
    {
        this.agregarOpcion(opcion, valorRetorno, this.numeroOpciones());
    }

    //Agrega una opcion al menu, en la posicion dada:
    public void agregarOpcion(String opcion[], String valorRetorno, int indice)
    {
        //Si no es un indice valido, sale de la funcion:
        if (indice < 0 || indice > this.numeroOpciones()) { return; } //Dejamos poner uno mas, por si se quiere agregar al final, despues del ultimo.

        //Crea los vectores, con una posicion mas:
        String[][] opcionesModificado = new String[this.numeroOpciones() + 1][this.opciones[0].length];
        String[] valoresRetornoModificado = new String[this.numeroOpciones() + 1];
        
        //Pone los elementos previos:
        for (int x = 0; x < indice; x++)
        {
            opcionesModificado[x] = this.opciones[x];
            valoresRetornoModificado[x] = this.valoresRetorno[x];
        }
        
        //Inserta el nuevo elemento:
        opcionesModificado[indice] = opcion;
        valoresRetornoModificado[indice] = valorRetorno;
        
        //Continua insertando los elementos posteriores:
        for (int x = indice; x < this.numeroOpciones(); x++)
        {
            opcionesModificado[x + 1] = this.opciones[x];
            valoresRetornoModificado[x + 1] = this.valoresRetorno[x];
        }
        
        //Pone los vectores modificados como los actuales:
        this.setOpciones(opcionesModificado);
        this.setValoresRetorno(valoresRetornoModificado);
    }
    
    //Elimina una opcion del menu:
    public void eliminarOpcion(int indice)
    {
        //Si no es un indice valido, sale de la funcion:
        if (indice < 0 || indice >= this.numeroOpciones()) { return; }
        
        //Crea los vectores, con una posicion menos:
        String[][] opcionesModificado = new String[this.numeroOpciones() - 1][this.opciones[0].length];
        String[] valoresRetornoModificado = new String[this.numeroOpciones() - 1];
        
        //Pone los elementos, excepto el que se desea borrar:
        int y = 0;
        for (int x = 0; x < this.numeroOpciones(); x++)
        {
            if (x != indice)
            {
                opcionesModificado[y] = this.opciones[x];
                valoresRetornoModificado[y] = this.valoresRetorno[x];
                y++;
            }
        }
        
        //Pone los vectores modificados como los actuales:
        this.setOpciones(opcionesModificado);
        this.setValoresRetorno(valoresRetornoModificado);
    }
    
    //Agrega un submenu al menu, a una opcion dada:
    public void agregarSubmenu(Menu menu, int indice)
    {
        //Si no es un indice valido, sale de la funcion:
        if (indice < 0 || indice > this.numeroOpciones()) { return; } //Dejamos poner uno mas, por si se quiere agregar al final, despues del ultimo.
        
        this.submenus.put(indice, menu);
    }
    
    //Borra un submenu del menu:
    public void eliminarSubmenu(int indice)
    {
        //Si no es un indice valido, sale de la funcion:
        if (indice < 0 || indice >= this.numeroOpciones()) { return; }
        
        this.submenus.remove(indice);
    }

    //Obtiene el valor que corresponde a un indice (se asumen valores unicos):
    public int indiceValor(String valorDevuelto)
    {
        int indice = -1;
        String valor;
        
        for (int x = 0; x < this.valoresRetorno.length; x++)
        {
            valor = this.valoresRetorno[x];
            if (valorDevuelto.toUpperCase().equals(valor.toUpperCase())) { indice = x; break; }
        }
        
        return indice;
    }
    
    //Obtiene un submenu del menu (mediante su valor devuelto):
    public Menu obtenerSubmenu(String valorDevuelto)
    {
        int indice = indiceValor(valorDevuelto);
        
        return this.submenus.get(indice);
    }
    
    //Obtiene un submenu del menu:
    public Menu obtenerSubmenu(int indice)
    {
        //Si no es un indice valido, sale de la funcion:
        if (indice < 0 || indice >= this.numeroOpciones()) { return null; }

        return this.submenus.get(indice);
    }
    
    //Baja una opcion en el menu:
    public void bajarOpcion()
    {
        //Si esta dentro de un submenu, llama recursivamente:
        if (this.obtenerSubmenu(this.opcionActual) != null && this.opcionAceptada) { this.obtenerSubmenu(this.opcionActual).bajarOpcion(); return; }
        
        this.setOpcionActual(this.opcionActual + 1);
        this.opcionAceptada = false;
    }
    
    //Sube una opcion en el menu:
    public void subirOpcion()
    {
        //Si esta dentro de un submenu, llama recursivamente:
        if (this.obtenerSubmenu(this.opcionActual) != null && this.opcionAceptada) { this.obtenerSubmenu(this.opcionActual).subirOpcion(); return; }
        
        this.setOpcionActual(this.opcionActual - 1);
        this.opcionAceptada = false;
    }
    
    //Acepta la opcion actual:
    public void aceptarOpcionActual()
    {
        //Si esta dentro de un submenu, llama recursivamente:
        //OJO! EDITADO: if (this.obtenerSubmenu(this.opcionActual) != null && this.opcionAceptada) { this.obtenerSubmenu(this.opcionActual).aceptarOpcionActual(); return; }
        if (this.obtenerSubmenu(this.opcionActual) != null && this.opcionAceptada) { this.obtenerSubmenu(this.opcionActual).aceptarOpcionActual(); return; }

        this.opcionAceptada = true;

        //Si hay un submenu, lo hace visible y marca su primera opcion:
        if (this.obtenerSubmenu(this.opcionActual) != null)
        {
            this.obtenerSubmenu(this.opcionActual).setMostrar(true);
            this.obtenerSubmenu(this.opcionActual).setOpcionActual(0);
        }
        
        //FALTA: entrar en submenus si los hay en la opcion aktual, etc.
    }
    
    //Cancela la opcion actual (tambien sale del submenu):
    public void cancelarOpcionActual()
    {
        //Si esta dentro de un submenu, llama recursivamente:
        //OJO! EDITADO: if (this.obtenerSubmenu(this.opcionActual) != null && this.opcionAceptada) { this.obtenerSubmenu(this.opcionActual).setOpcionAceptada(false); }
        if (this.obtenerSubmenu(this.opcionActual) != null && this.opcionAceptada) { this.obtenerSubmenu(this.opcionActual).cancelarOpcionActual(); }

        this.setOpcionAceptada(false);
    }
    
    
    //Devuelve el numero de opciones que tiene el menu:
    public int numeroOpciones()
    {
        return this.opciones.length;
    }

    //Dibuja el menu o submenu actual en una imagen de buffer:
    public void dibujar(Graphics contenedor, BufferedImage imagenBuffer)
    {
        this.dibujar(imagenBuffer.getGraphics());
        contenedor.drawImage(imagenBuffer, 0, 0, null);
    }
    
    //Dibuja el menu o submenu actual:
    public void dibujar(Graphics contenedor)
    {
        //Si esta dentro de un submenu, llama recursivamente:
        if (this.obtenerSubmenu(this.opcionActual) != null && this.opcionAceptada) { this.obtenerSubmenu(this.opcionActual).dibujar(contenedor); return; }
        
        //Si el menu no se debe mostrar, sale de la funcion:
        if (!this.getMostrar()) { return; }

        MotorGrafico.dibujarMenu(contenedor, this);
    }
}