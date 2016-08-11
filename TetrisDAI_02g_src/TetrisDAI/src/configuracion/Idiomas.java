/** 
 * 
 * @author Joan Alba Maldonado
 */ 

//FALTA: siempre validar configuracion y si no es korrekta, usar unos valores por defecto.

package configuracion;


public class Idiomas
{
   //Idiomas existentes y por defecto (Modificable):
   static final public String[] idiomas = { "Català", "Castellano", "Ingles" }; //Idiomas existentes.
   static final public int idiomaPorDefecto = 2; //Indice del idioma por defecto al iniciar el juego.

   //Etiquetas traducibles (Modificable):
   static final public String[] piezaSiguiente = {
                                                     "Peça següent",
                                                     "Pieza siguiente",
                                                     "Next piece"
                                                 };

   static final public String[] lineasTotales = {
                                                    "Linies totals",
                                                    "Lineas totales",
                                                    "Total lines"
                                                };

   static final public String[] lineasNivel = {
                                                  "Linies nivell",
                                                  "Lineas nivel",
                                                  "Level lines"
                                              };

   static final public String[] nivel = {
                                            "Nivell",
                                            "Nivel",
                                            "Level"
                                        };
   
   static final public String[] puntuacion = {
                                                 "Puntuació",
                                                 "Puntuación",
                                                 "Score"
                                             };

   static final public String[] gameOver = {
                                               "Fi del joc",
                                               "Fin del juego",
                                               "Game Over"
                                           };
    
   
   static final public String[] iniciarJuego = {
                                                   "Inicia joc",
                                                   "Iniciar juego",
                                                   "Start game"
                                               };
   
   
   static final public String[] finalizarJuego = {
                                                     "Surt",
                                                     "Salir",
                                                     "Exit"
                                                 };
   
   
   static final public String[] volver = {
                                             "Torna",
                                             "Volver",
                                             "Return"
                                         };
   
   static final public String[] opciones = {
                                               "Opcions",
                                               "Opciones",
                                               "Options"
                                           };
   
   static final public String[] comienzaJuego = {
                                                    "Comença el joc",
                                                    "Comienza el juego",
                                                    "Starts the game"
                                                };
   
   static final public String[] modificarOpciones = {
                                                        "Modificar opcions",
                                                        "Modificar opciones",
                                                        "Change options"
                                                    };
   
   static final public String[] saleJuego = {
                                                "Surt del joc",
                                                "Sale del juego",
                                                "Exit game"
                                            };
   
   static final public String[] confirmarSalir = {
                                                    "Confirmar",
                                                    "Confirmar",
                                                    "Confirm"
                                                };
   
   static final public String[] rechazarSalir = {
                                                    "Torna al menu",
                                                    "Volver al menu",
                                                    "Return to menu"
                                                };
   
   static final public String[] volverJuego = {
                                                "Torna al joc",
                                                "Vuelve al juego",
                                                "Returns to game"
                                              };
   
   static final public String[] volverMenuPrincipal = {
                                                        "Tornar al menú principal",
                                                        "Volver al menú principal",
                                                        "Return to main menu"
                                                      };
   
   static final public String[] volverMenu = {
                                                 "Tornar",
                                                 "Volver",
                                                 "Return"
                                             };

   static final public String[] reanudarJuego = {
                                                    "Continuar",
                                                    "Continuar",
                                                    "Continue"
                                                };
   
   static final public String[] terminarJuego = {
                                                    "Terminar",
                                                    "Terminar",
                                                    "Finalize"
                                                };
   
   private Idiomas()
   {
   }
}