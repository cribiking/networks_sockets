# Sessió 2 - Xat Client-Servidor (17/02)

## Objectiu
Crear un xat de comunicació **client-servidor** amb el client interactiu i persistent.

## Servidor - `InteractiveServer.java`
El servidor ha de:
1. Obrir una connexió en un port determinat (**1234**, per exemple).
2. Quan la connexió hagi estat acceptada:
    - Llegir pel canal una cadena (*String*).
    - Retornar-la pel canal en **majúscules**.
    - Si la cadena llegida és `"FI"`:
        - Tancar la connexió i esperar la següent.
    - Altrament, continuar llegint.
3. Quan acabi de parlar amb un client, ha de quedar a l'espera d'un altre.

## Client - `HowlerClient.java`
El client ha de:
1. Obrir una connexió cap a una **IP/port**.
2. Llegir de la consola una línia de text (*String*).
3. Enviar-la per la connexió.
4. Llegir la resposta del servidor i mostrar-la per pantalla.
5. Si la cadena llegida és `"FI"`:
    - Acabar.
    - Altrament, continuar llegint.

## Control d'errors
És important controlar els errors tant en el **client** com en el **servidor** amb blocs `try-catch`.

## Homework
Intentar fer que el server pugui acceptar més d'un client al mateix temps.

*Pista*: multi-threading.

## Autor 
Albert.