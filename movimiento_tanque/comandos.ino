bool startsWith(const char *pre, const char *str)
{
    size_t lenpre = strlen(pre),
           lenstr = strlen(str);
    return lenstr < lenpre ? false : strncmp(pre, str, lenpre) == 0;
}

void leer_comando(char command[N]){
     int letra;
     int i = 0;
     while ((letra =  Serial.read()) != '\n'){
      if ( letra == -1 ) {
        delay(100);
        continue;
      }
      command[i++] = (char) letra;
     }
     command[i] = '\0';
}

enum Modo analizar(char command[N]) {
  if (startsWith("A", command)){
      Serial.println("Entrando en modo Avanzar.");
      return avanzando;
  }
  if (startsWith("S", command)){
      Serial.println("Entrando en modo espera.");
      return parado;
  }

   if (startsWith("I", command)){
      Serial.println("Entrando en modo espera.");
      return giroiz;
  }
   if (startsWith("D", command)){
      Serial.println("Entrando en modo espera.");
      return giroder;
  }
   if (startsWith("B", command)){
      Serial.println("Entrando en modo espera.");
      return marchatras;
  }
}

