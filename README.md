Uso del protocolo SSL/TLS para establecer comunicaciones seguras, con un intercambio previo de claves y certificados. Se emplea una arquitectura cliente-servidor en Java junto con Secure Sockets Layers (SSL), proporcionando múltiples alternativas de clientes para realizar pruebas de rendimiento y verificar la seguridad de las comunicaciones.

El sistema hace uso de Cipher Suites establecidas por TLS versión 1.3 para garantizar
la seguridad de las comunicaciones. Las Cipher Suites son TLS_AES_128_GCM_SHA256,
TLS_AES_256_GCM_SHA384 y TLS_CHACHA20_POLY1305_SHA256, cada una con
diferentes niveles de seguridad y rendimiento. Además, se implementan medidas de
creación y almacenamiento de claves mediante keyStores y SSLStores, asegurando
la gestión segura de claves y certificados tanto en el lado del servidor como del
cliente. Estos elementos son esenciales para garantizar la confidencialidad, integridad y
autenticidad en las comunicaciones seguras en un entorno BYOD.
