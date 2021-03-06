NextGifts
=========
Esta aplicación tiene como objetivo, ser un almacén de regalos en el cual tengas un historial de regalos antiguos y futuros, así como crear una comunidad donde podrás encontrar regalos que otras personas hayan hecho y que te puedan servir de referencia para futuros regalos que tengas pensado hacer. Para tener información de los regalos existen campos de información adicional hashtags públicos y privados.

Tecnologías usadas
==================
* Backend:
Lenguaje Java con un servidor Tomcat y uso del framework de Spring con los módulos MVC y JDBC. La base de datos es MySQL.

* Frontend:
Lenguaje JavaScript, HTML5 y CSS3, utilizando los siguientes frameworks: Angular.js, jQuery y jQuery Mobile.

Funcionalidades
===============
* Registro: Crear un usuario para poder entrar en la comunidad
 
* Añadir regalo: Añadir un regalo que este en la comunidad, al añadirlo podrás poder campos propios como el hashtag privado (indicando por ejemplo para quien o quienes son o para que festividad)

* Crear regalo: Añadir un regalo

* Ver tus regalos: Ver tus regalos añadidos o cogidos de la comunidad

* Ver regalo: Ver un regalo con todos sus campos

Screenshots
===========

*Página inicio*

![Alt text](/screenshots/index.png "Inicio")

*Página home*

![Alt text](/screenshots/home.png "Home")

*Página mis regalos*

![Alt text](/screenshots/gifts.png "Mis regalos")

Futuras mejoras
===============
* Insertar imágen al regalo
* Cambio de jQuery Mobile a bootstrap
* Buscador a través de nombre y hashtags
* ¿Pasar de Java a Nodejs?

Uso
===
Para ver la aplicación solo tienes que entrar en el siguiente [enlace](http://nextgifts-alearnaiz.rhcloud.com/NextGifts/ "Enlace NextGifts") y difrutar de ella, la aplicación esta subida a un servidor Tomcat 7 con una base de datos MySQL 5.5 en [OpenShift](https://www.openshift.com/ "OpenShift")
