## Cliente-Servidor con TLSv1.3

Uso del protocolo SSL/TLS para establecer comunicaciones seguras, con un intercambio previo de claves y certificados. Se emplea una arquitectura cliente-servidor en Java junto con Secure Sockets Layers (SSL), proporcionando múltiples alternativas de clientes para realizar pruebas de rendimiento y verificar la seguridad de las comunicaciones.

El sistema hace uso de Cipher Suites establecidas por TLS versión 1.3 para garantizar
la seguridad de las comunicaciones. Las Cipher Suites son TLS_AES_128_GCM_SHA256,
TLS_AES_256_GCM_SHA384 y TLS_CHACHA20_POLY1305_SHA256, cada una con
diferentes niveles de seguridad y rendimiento. Además, se implementan medidas de
creación y almacenamiento de claves mediante keyStores y SSLStores, asegurando
la gestión segura de claves y certificados tanto en el lado del servidor como del
cliente. Estos elementos son esenciales para garantizar la confidencialidad, integridad y
autenticidad en las comunicaciones seguras en un entorno BYOD.

## Manual de uso

En Windows, añadir variable de entorno en path para keytool:

`C:\Program Files\Java\jdk-21\bin`

### Servidor

Abrir cmd como administrador y ejecutar:

1. `Keytool -genkey -keystore c:\SSLStore -alias SSLCertificate -keyalg RSA`
   
2. `keytool -export -alias SSLCertificate -keystore SSLStore -file public.cert`

Estando en la ruta del proyecto “...\src”, (ejecutar el paso 3 para crear los
archivos .class si no están todavía creados o se modifican):

3. `javac BYODServer.java`
   
4. `java –Djavax.net.ssl.keyStore=C:\SSLStore -Djavax.net.ssl.keyStorePassword=PASSWORD BYODServer`

[PASSWORD es la contraseña del paso anterior]

### Cliente Java

Para los clientes en java, se ejecutan todos igual, solo es necesario especificar el nombre del cliente. En otra cmd como administrador (ejecutar el paso 1 solo si se
usan ordenadores distintos para las pruebas):

1. `Keytool -genkey -keystore c:\SSLStore -alias SSLCertificate -keyalg RSA`
   
2. `Keytool -import -file C:\public.cert -alias firstCA -keystore myTrustStore`

[“public.cert” es el generado por el servidor, para hacerlo en localhost es el
que has generado en los pasos anteriores, para conectarte a otro PC es el
generado en el otro PC]

3. En la ruta de “…\src” (ejecutar el paso 3.1 para crear los archivos .class
si no están todavía creados o se modifican):

3.1 `javac BYODClient.java`

3.2 `java –Djavax.net.ssl.trustStore=C:\myTrustStore -Djavax.net.ssl.trustStorePassword=PASSWORD BYODClient`

## Cliente Python
Para el cliente en python, en otra cmd como administrador, teniendo instalado
openssl:

1. `openssl x509 -inform der -in public.cert -out certificate.pem`
   
[public.cert es el generado por el servidor, ejecutar en la ruta donde tengas
public.cert o especificar su ruta]

2. En la ruta de “ …\src”: `python BYODClient.py`
   
- Si en algún momento deseas consultar la información de este certificado, en
la ruta donde esté almacenado ejecutar:

`openssl x509 -in certificate.pem -text -noout`
