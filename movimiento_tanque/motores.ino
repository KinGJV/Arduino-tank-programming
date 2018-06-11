

void avanzar()
    { 
    digitalWrite(pinLD,LOW);
    digitalWrite(pinRD, LOW);
    analogWrite(pinLF,255);   
    analogWrite(pinRF,255);
    }
void atras()
    {
     digitalWrite(pinLD,HIGH);
     digitalWrite(pinRD,HIGH);
     analogWrite(pinLF,255);
     analogWrite(pinRF,255);
    }

void derecha()
    {
   digitalWrite(pinLD,HIGH);
   digitalWrite(pinRD,LOW);
   analogWrite(pinLF, 255);
   analogWrite(pinRF,255);   
    }
void izquierda()
    {
   digitalWrite(pinLD,LOW);
   digitalWrite(pinRD,HIGH);
   analogWrite(pinLF, 255);
   analogWrite(pinRF,255);  
    }
void parar()
    {
     digitalWrite(pinLD,HIGH);
     digitalWrite(pinRD,HIGH);
     analogWrite(pinLF,0);
     analogWrite(pinRF,0); 
    }
