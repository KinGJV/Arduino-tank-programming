enum Modo{
  parado,
  avanzando,
  giroiz,
  giroder,
  marchatras,
  
  MODOS
} modo;


int pinLD = 12; 
int pinLF = 3; 
int pinRD = 13; 
int pinRF = 11;

String valor_serial;
#define N 0x100


void setup()
    {
    Serial.begin(9600);
    pinMode(pinLD,OUTPUT);
    pinMode(pinLF,OUTPUT);
    pinMode(pinRD,OUTPUT);
    pinMode(pinRF,OUTPUT); 
    }

//sscanf(" avanzar %s %i

void loop()
    {
     char command[N];
     //Entrada de datos
     leer_comando(command);
     // Analizar el comando
     modo = analizar(command);
     // Ejecutar el comando
     switch (modo){
        case parado:
           Serial.println("Darius he parado.");
           parar();
           delay(1000);
           break;
        case avanzando:
           objetivo_avanzar();
           Serial.println("Fin del modo Avanzar.");
           avanzar();
           delay(1000);
           parar();
           break;
       case giroiz:
           objetivo_avanzar();
           Serial.println("Fin del modo Avanzar.");
           izquierda();
           delay(580);
           parar();
           break;
           
       case giroder:
           objetivo_avanzar();
           Serial.println("Fin del modo Avanzar.");
           derecha();
           delay(580);
           parar();
           break;
           
       case marchatras:
           objetivo_avanzar();
           Serial.println("Fin del modo Avanzar.");
           atras();
           delay(1000);
           parar();
           break;
              
     }
     }
