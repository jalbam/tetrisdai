/** 
 * 
 * @author Joan Alba Maldonado
 */ 

//FALTA: siempre validar configuracion y si no es korrekta, usar unos valores por defecto.

//FALTA: no poner opcion de salir si se trata de un applet!

//ACORDARSE: de poner el menu de opciones tambien dentro de pausa, para poder editar opciones kon el juego komenzado.

//FALTA: opcion de reiniciar juego en el menu de pausa.

package configuracion;

import logica.Menu;


public class Menus
{
    //Menu principal (si se modifica, debera modificarse procesarMenuPrincipal() del objeto Juego):
    static final private String[][] menuPrincipalOpciones = {
                                                               configuracion.Idiomas.iniciarJuego,
                                                               configuracion.Idiomas.opciones,
                                                               configuracion.Idiomas.finalizarJuego
                                                            };
    static final private String[] menuPrincipalValoresDevueltos = {
                                                                    "INICIAR",
                                                                    "OPCIONES",
                                                                    "FINALIZAR"
                                                                  };
    
    static final private int menuPrincipalLeft = 100;
    static final private int menuPrincipalTop = 212;
    static private Menu menuPrincipal = new Menu(menuPrincipalOpciones, menuPrincipalValoresDevueltos, configuracion.PaletaColores.getPaleta(), Menus.menuPrincipalLeft, Menus.menuPrincipalTop);

    static final private String[] textoAlternativoIniciar = configuracion.Idiomas.comienzaJuego;
    static final private int[] textoAlternativoIniciarLeft = { 90, 84, 89 };
    static final private int[] textoAlternativoIniciarTop = { 185, 185, 185 };

    static final private String[] textoAlternativoOpciones = configuracion.Idiomas.modificarOpciones;
    static final private int[] textoAlternativoOpcionesLeft = { 83, 82, 92 };
    static final private int[] textoAlternativoOpcionesTop = { 185, 185, 185 };

    static final private String[] textoAlternativoFinalizar = configuracion.Idiomas.saleJuego;
    static final private int[] textoAlternativoFinalizarLeft = { 100, 95, 105 };
    static final private int[] textoAlternativoFinalizarTop = { 185, 185, 185 };
   
    //Submenu del menu principal, para confirmar salir del juego (si se modifica, debera modificarse procesarMenuPrincipal() del objeto Juego):
    static final private String[][] menuPrincipalConfirmarSalirOpciones = {
                                                                            configuracion.Idiomas.finalizarJuego,
                                                                            configuracion.Idiomas.volver
                                                                          };
    
    static final private String[] menuPrincipalConfirmarSalirValoresDevueltos = {
                                                                                    "FINALIZAR_SI",
                                                                                    "FINALIZAR_NO"
                                                                                };
    
    static final private int menuPrincipalConfirmarSalirLeft = Menus.menuPrincipalLeft;
    static final private int menuPrincipalConfirmarSalirTop = Menus.menuPrincipalTop;
    static private Menu menuPrincipalConfirmarSalir = new Menu(menuPrincipalConfirmarSalirOpciones, menuPrincipalConfirmarSalirValoresDevueltos, configuracion.PaletaColores.getPaleta(), Menus.menuPrincipalConfirmarSalirLeft, Menus.menuPrincipalConfirmarSalirTop);
    
    static final private String[] textoAlternativoFinalizarSi = configuracion.Idiomas.saleJuego;
    static final private int[] textoAlternativoFinalizarSiLeft = { 100, 94, 104 };
    static final private int[] textoAlternativoFinalizarSiTop = { 185, 185, 185 };

    static final private String[] textoAlternativoFinalizarNo = configuracion.Idiomas.rechazarSalir;
    static final private int[] textoAlternativoFinalizarNoLeft = { 92, 93, 92 };
    static final private int[] textoAlternativoFinalizarNoTop = { 185, 185, 185 };
    
    //Texto fijo del menu:
    static final private String textoFijo = "               TetrisDAI " + Otros.getVersionPrograma() + "\nhttp://tetrisdai.sourceforge.net\n        Joan Alba Maldonado";
    static final private int textoFijoLeft = 40;
    static final private int textoFijoTop = 390;
    
    //Logo del menu:
    static final private int[][] logo = {
                                            { 9, 9, 9, 0, 10, 10, 0 },
                                            { 0, 9, 0, 0, 10, 0, 10 },
                                            { 0, 9, 0, 0, 10, 0, 10 },
                                            { 0, 9, 0, 0, 10, 0, 10 },
                                            { 0, 9, 0, 0, 10, 10, 0 }
                                        };
    static final private byte leftLogo = 3;
    static final private byte topLogo = 1;
    
    //Dibujos de las opciones principales:
    static final private int[][][] dibujos = {
                                                {
                                                    {  9, 10,  8,  8,  8,  8,  8, 10,  9 },
                                                    { 10, 99, 99, 99, 99, 99, 99, 99, 10 },
                                                    {  8, 99, 99, 99, 99, 99, 99, 99,  8 },
                                                    {  8, 99, 99, 99, 99, 99, 99, 99,  8 },
                                                    {  8, 99, 99, 99, 99, 99, 99, 99,  8 },
                                                    {  8, 99, 99, 99, 99, 99, 99, 99,  8 },
                                                    {  8, 99, 99, 99, 99, 99, 99, 99,  8 },
                                                    {  8, 99, 99, 99, 99, 99, 99, 99,  8 },
                                                    { 10, 99, 99, 99, 99, 99, 99, 99, 10 },
                                                    {  9, 10,  8,  8,  8,  8,  8, 10,  9 }
                                                },
                                                {
                                                    { 10, 11,  9,  9,  9,  9,  9, 11, 10 },
                                                    { 11, 99, 99, 99, 99, 99, 99, 99, 11 },
                                                    {  9, 99, 99, 99, 99, 99, 99, 99,  9 },
                                                    {  9, 99, 99, 99, 99, 99, 99, 99,  9 },
                                                    {  9, 99, 99, 99, 99, 99, 99, 99,  9 },
                                                    {  9, 99, 99, 99, 99, 99, 99, 99,  9 },
                                                    {  9, 99, 99, 99, 99, 99, 99, 99,  9 },
                                                    {  9, 99, 99, 99, 99, 99, 99, 99,  9 },
                                                    { 11, 99, 99, 99, 99, 99, 99, 99, 11 },
                                                    { 10, 11,  9,  9,  9,  9,  9, 11, 10 }
                                                },
                                                {
                                                    { 12, 13, 11, 11, 11, 11, 11, 13, 12 },
                                                    { 13, 99, 99, 99, 99, 99, 99, 99, 13 },
                                                    { 11, 99, 99, 99, 99, 99, 99, 99, 11 },
                                                    { 11, 99, 99, 99, 99, 99, 99, 99, 11 },
                                                    { 11, 99, 99, 99, 99, 99, 99, 99, 11 },
                                                    { 11, 99, 99, 99, 99, 99, 99, 99, 11 },
                                                    { 11, 99, 99, 99, 99, 99, 99, 99, 11 },
                                                    { 11, 99, 99, 99, 99, 99, 99, 99, 11 },
                                                    { 13, 99, 99, 99, 99, 99, 99, 99, 13 },
                                                    { 12, 13, 11, 11, 11, 11, 11, 13, 12 }
                                                },
                                           };
    static final private byte[] leftDibujos = { 2, 2, 2 };
    static final private byte[] topDibujos = { 7, 7, 7 };
    
    static final private int[][][] dibujosConfirmarSalir = {
                                                            {
                                                                {  8,  9, 12, 12, 12, 12, 12, 11, 10 },
                                                                {  9, 99, 99, 99, 99, 99, 99, 99, 11 },
                                                                { 12, 99, 99, 99, 99, 99, 99, 99, 12 },
                                                                { 12, 99, 99, 99, 99, 99, 99, 99, 12 },
                                                                { 12, 99, 99, 99, 99, 99, 99, 99, 12 },
                                                                { 12, 99, 99, 99, 99, 99, 99, 99, 12 },
                                                                { 11, 99, 99, 99, 99, 99, 99, 99,  9 },
                                                                { 10, 11, 12, 12, 12, 12, 12,  9,  8 }
                                                            },
                                                            {
                                                                { 12, 13,  9,  9,  9,  9,  9,  8, 11 },
                                                                { 13, 99, 99, 99, 99, 99, 99, 99,  8 },
                                                                {  9, 99, 99, 99, 99, 99, 99, 99,  9 },
                                                                {  9, 99, 99, 99, 99, 99, 99, 99,  9 },
                                                                {  9, 99, 99, 99, 99, 99, 99, 99,  9 },
                                                                {  9, 99, 99, 99, 99, 99, 99, 99,  9 },
                                                                {  8, 99, 99, 99, 99, 99, 99, 99, 13 },
                                                                { 11,  8,  9,  9,  9,  9,  9, 13, 12 }
                                                            }
                                                           };
    static final private byte[] leftDibujosConfirmarSalir = { 2, 2 };
    static final private byte[] topDibujosConfirmarSalir = { 7, 7 };

    
    
    
    //Menu de pausa (si se modifica, debera modificarse procesarMenuPausa() del objeto Juego):
    static final private String[][] menuPausaOpciones = {
                                                               configuracion.Idiomas.reanudarJuego,
                                                               configuracion.Idiomas.terminarJuego
                                                            };
    static final private String[] menuPausaValoresDevueltos = {
                                                                    "CONTINUAR",
                                                                    "TERMINAR"
                                                                  };
    
    static final private int menuPausaLeft = 100;
    static final private int menuPausaTop = 212;
    static private Menu menuPausa = new Menu(menuPausaOpciones, menuPausaValoresDevueltos, configuracion.PaletaColores.getPaleta(), Menus.menuPausaLeft, Menus.menuPausaTop);

    static final private String[] textoAlternativoContinuar = configuracion.Idiomas.volverJuego;
    static final private int[] textoAlternativoContinuarLeft = { 96, 88, 87 };
    static final private int[] textoAlternativoContinuarTop = { 185, 185, 185 };

    static final private String[] textoAlternativoTerminar = configuracion.Idiomas.volverMenuPrincipal;
    static final private int[] textoAlternativoTerminarLeft = { 64, 64, 72 };
    static final private int[] textoAlternativoTerminarTop = { 185, 185, 185 };

    //Submenu del menu de pausa, para confirmar salir del juego (si se modifica, debera modificarse procesarMenuPausa() del objeto Juego):
    static final private String[][] menuPausaConfirmarSalirOpciones = {
                                                                        configuracion.Idiomas.confirmarSalir,
                                                                        configuracion.Idiomas.volver
                                                                      };
    
    static final private String[] menuPausaConfirmarSalirValoresDevueltos = {
                                                                                "FINALIZAR_SI",
                                                                                "FINALIZAR_NO"
                                                                            };
    
    static final private int menuPausaConfirmarSalirLeft = Menus.menuPrincipalConfirmarSalirLeft;
    static final private int menuPausaConfirmarSalirTop = Menus.menuPrincipalConfirmarSalirTop;
    static private Menu menuPausaConfirmarSalir = new Menu(menuPausaConfirmarSalirOpciones, menuPausaConfirmarSalirValoresDevueltos, configuracion.PaletaColores.getPaleta(), Menus.menuPausaConfirmarSalirLeft, Menus.menuPausaConfirmarSalirTop);
    
    private Menus()
    {
    }
    
    static public Menu getMenuPrincipal()
    {
        //Pone el texto alternativo al menu principal:
        Menus.menuPrincipal.setTextoAlternativo(Menus.textoAlternativoIniciar, Menus.menuPrincipal.indiceValor("INICIAR"), Menus.textoAlternativoIniciarLeft, Menus.textoAlternativoIniciarTop);
        Menus.menuPrincipal.setTextoAlternativo(Menus.textoAlternativoOpciones, Menus.menuPrincipal.indiceValor("OPCIONES"), Menus.textoAlternativoOpcionesLeft, Menus.textoAlternativoOpcionesTop);
        Menus.menuPrincipal.setTextoAlternativo(Menus.textoAlternativoFinalizar, Menus.menuPrincipal.indiceValor("FINALIZAR"), Menus.textoAlternativoFinalizarLeft, Menus.textoAlternativoFinalizarTop);
        
        //Pone el texto alternativo al submenu de confirmar salir:
        Menus.menuPrincipalConfirmarSalir.setTextoAlternativo(Menus.textoAlternativoFinalizarSi, Menus.menuPrincipalConfirmarSalir.indiceValor("FINALIZAR_SI"), Menus.textoAlternativoFinalizarSiLeft, Menus.textoAlternativoFinalizarSiTop);
        Menus.menuPrincipalConfirmarSalir.setTextoAlternativo(Menus.textoAlternativoFinalizarNo, Menus.menuPrincipalConfirmarSalir.indiceValor("FINALIZAR_NO"), Menus.textoAlternativoFinalizarNoLeft, Menus.textoAlternativoFinalizarNoTop);
        
        //Se agrega el submenu de confirmar salir al menu principal:
        Menus.menuPrincipal.agregarSubmenu(menuPrincipalConfirmarSalir, Menus.menuPrincipal.indiceValor("FINALIZAR"));
        
        //Se agrega el texto permamente (en los submenus tambien):
        Menus.menuPrincipal.setTextoFijo(Menus.textoFijo, Menus.textoFijoLeft, Menus.textoFijoTop);
        Menus.menuPrincipal.obtenerSubmenu("FINALIZAR").setTextoFijo(Menus.textoFijo, Menus.textoFijoLeft, Menus.textoFijoTop);
        
        //Se agrega el logo (en los submenus tambien):
        Menus.menuPrincipal.setLogo(Menus.logo, Menus.leftLogo, Menus.topLogo);
        Menus.menuPrincipal.obtenerSubmenu("FINALIZAR").setLogo(Menus.logo, Menus.leftLogo, Menus.topLogo);
        
        //Se agregan los dibujos (en los submenus tambien):
        Menus.menuPrincipal.setDibujos(Menus.dibujos, Menus.leftDibujos, Menus.topDibujos);
        Menus.menuPrincipal.obtenerSubmenu("FINALIZAR").setDibujos(Menus.dibujosConfirmarSalir, Menus.leftDibujosConfirmarSalir, Menus.topDibujosConfirmarSalir);
        
        return Menus.menuPrincipal;
    }
    
    static public Menu getMenuPausa()
    {
        //Pone el texto alternativo al menu de pausa:
        Menus.menuPausa.setTextoAlternativo(Menus.textoAlternativoContinuar, Menus.menuPausa.indiceValor("CONTINUAR"), Menus.textoAlternativoContinuarLeft, Menus.textoAlternativoContinuarTop);
        Menus.menuPausa.setTextoAlternativo(Menus.textoAlternativoTerminar, Menus.menuPausa.indiceValor("TERMINAR"), Menus.textoAlternativoTerminarLeft, Menus.textoAlternativoTerminarTop);

        //Pone el texto alternativo al submenu de confirmar salir:
        Menus.menuPausaConfirmarSalir.setTextoAlternativo(Menus.textoAlternativoFinalizarSi, Menus.menuPausaConfirmarSalir.indiceValor("FINALIZAR_SI"), Menus.textoAlternativoFinalizarSiLeft, Menus.textoAlternativoFinalizarSiTop);
        Menus.menuPausaConfirmarSalir.setTextoAlternativo(Menus.textoAlternativoFinalizarNo, Menus.menuPausaConfirmarSalir.indiceValor("FINALIZAR_NO"), Menus.textoAlternativoFinalizarNoLeft, Menus.textoAlternativoFinalizarNoTop);
        
        //Se agrega el submenu de confirmar salir al menu de pausa:
        Menus.menuPausa.agregarSubmenu(menuPausaConfirmarSalir, Menus.menuPausa.indiceValor("TERMINAR"));
        
        //Se agrega el texto permamente (en los submenus tambien):
        Menus.menuPausa.setTextoFijo(Menus.textoFijo, Menus.textoFijoLeft, Menus.textoFijoTop);
        Menus.menuPausa.obtenerSubmenu("TERMINAR").setTextoFijo(Menus.textoFijo, Menus.textoFijoLeft, Menus.textoFijoTop);

        //Se agrega el logo (en los submenus tambien):
        Menus.menuPausa.setLogo(Menus.logo, Menus.leftLogo, Menus.topLogo);
        Menus.menuPausa.obtenerSubmenu("TERMINAR").setLogo(Menus.logo, Menus.leftLogo, Menus.topLogo);

        //Se agregan los dibujos (en los submenus tambien):
        Menus.menuPausa.setDibujos(Menus.dibujosConfirmarSalir, Menus.leftDibujosConfirmarSalir, Menus.topDibujosConfirmarSalir);
        Menus.menuPausa.obtenerSubmenu("TERMINAR").setDibujos(Menus.dibujosConfirmarSalir, Menus.leftDibujosConfirmarSalir, Menus.topDibujosConfirmarSalir);
        
        return Menus.menuPausa;
    }
}
