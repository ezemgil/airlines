# **Aerolíneas**

*Aerolíneas* es una compañía aérea ficticia que opera vuelos nacionales e internacionales. La empresa ofrece una variedad de servicios, incluyendo la reserva de vuelos, la gestión de reservas y el seguimiento de vuelos en tiempo real. Este proyecto es una aplicación web que simula las operaciones de la aerolínea.

Este proyecto está basado en un **Ejercicio Práctico Complementario** del curso de *Diseño de Sistemas de Información (DSI)* del programa de *Ingeniería en Sistemas de Información (ISI)* en la **Universidad Tecnológica Nacional**, **Facultad Regional Córdoba** (UTN FRC).

## **Descripción del Dominio**

Una aerolínea quiere implementar un sitio web donde los pasajeros potenciales puedan verificar la disponibilidad de vuelos a los diferentes destinos ofrecidos por la compañía. La definición de los vuelos se diagrama determinando el número de vuelo, el(los) día(s) de la semana y la hora de salida, la duración, los aeropuertos de origen y destino, y las tarifas de los boletos. Estas tarifas dependen del destino, el tipo de clase deseada (*básica, turista, negocios reducida, negocios, primera clase*) y el tipo de aeronave. Cabe destacar que las ciudades pueden tener más de un aeropuerto, por lo que los vuelos se diagraman para un aeropuerto específico. Tanto las ciudades como los aeropuertos tienen un código que los identifica, por ejemplo: *Buenos Aires, código BUE; Aeropuerto Jorge Newbery, código (AEP)*.

La disponibilidad depende de las reservas que tenga cada vuelo, entendiendo que la venta de boletos siempre se finaliza a partir de reservas. Las reservas pertenecen a un pasajero, tienen una fecha y hora de vencimiento, y también los detalles del tipo de clase solicitada. La capacidad de la aeronave está limitada por el tipo de clase que admiten (algunas solo tienen clase turista y otras una combinación, por ejemplo).

La aerolínea permite diferentes tipos de vuelos: *de ida (origen-destino)* o *de ida y vuelta (origen-destino-origen)*. Las consultas web deben solicitar que se indique el tipo de vuelo y también si el pasajero desea una hora específica o es flexible. La flexibilidad horaria permitirá al sistema buscar todos los vuelos disponibles en una fecha dentro de las 24 horas de esa fecha.

Además, el sistema debe ofrecer seguimiento de vuelos, ya que una vez programados, los vuelos pueden completarse, retrasarse o cancelarse. Si ha llegado la hora de embarque de un vuelo programado, el sistema debe informarlo para permitir registrar si el embarque se ha completado o no. Si esto fue posible, el sistema informará que está embarcando; de lo contrario, informará que está retrasado. El sistema debe permitir reprogramar un vuelo programado o retrasado.

Cuando un vuelo no puede completarse por diversas razones, debe registrarse como cancelado. Esta situación es posible para vuelos que están en un estado no final. El empleado responsable de cada cambio de estado realizado en el vuelo debe registrarse, así como el día y la hora en que se realizó el cambio de estado. En el caso de cancelaciones, se debe indicar el motivo de la cancelación.

Una vez que todos los pasajeros han abordado, el vuelo se cierra y no pueden abordar más pasajeros. Luego, el vuelo se considera en ejecución desde el momento en que el avión despega. Cuando el avión alcanza la altitud de crucero, el vuelo asume el estado *"EnAire"* hasta que esté en la zona de pre-aterrizaje y, finalmente, aterrizado. Dado que es necesario informar la situación de cada vuelo en todo momento y a lo largo del tiempo, debemos mantener esta información en el sistema. Finalmente, el vuelo se considera completado cuando todos los pasajeros están en el área de reclamo de equipaje.

### Consideraciones del Dominio:
Para simplificar el problema, no se consideran en el modelado los siguientes aspectos:
- *Escalas intermedias* entre los destinos disponibles.
- Variación en las tarifas de reserva de boletos basada en:
    - La *fecha de compra* (las tarifas son más baratas si se compran con anticipación).
    - Reserva de vuelos que permiten *modificación de fechas y horas* (a veces las aerolíneas permiten este tipo de reserva por un costo adicional).
    - Si es un *vuelo de ida* o un *vuelo de ida y vuelta* (en este último caso, en realidad serían dos reservas, una para el vuelo de ida y otra para el vuelo de regreso).

## **Alcance y objetivos**

*Gestión de reservas y seguimiento de vuelos para una aerolínea, gestionando la definición de vuelos, aeropuertos vinculados a itinerarios y aeronaves; proporcionando información relacionada con la gestión.*

### **Requisitos Funcionales**

- **Gestión de Aeropuertos**
- **Gestión de Aeronaves**
- **Gestión de Definiciones de Vuelos**
- **Gestión de Países y Ciudades**
- **Gestión de Vuelos**
- **Gestión de Reservas**
- **Generación y Emisión de Informes** relacionados con vuelos, reservas y tarifas

### **No incluye:**

- Gestión de ventas y pagos de vuelos reservados
- Variación de tarifas por compra anticipada, tipo de vuelo (ida o ida y vuelta) y posibilidad de cambiar fecha y hora

### **Representación Visual**

Como esta es una aerolínea ficticia, que obviamente no tiene una flota ni rutas aéreas, para proporcionar al pasajero una *"experiencia"* similar, se desarrollará un módulo desde el frontend que permita realizar el check-in, abordar la aeronave, vuelo, aterrizaje, y demás, con sus tiempos de espera estimados en la realidad. Además, se considerarán los posibles estados del vuelo (*retrasado, cancelado, reprogramado, etc.*)
> 🛈 *Se proporcionarán más detalles sobre esta sección en el futuro.*

## **Reglas de Negocio**

| Nº | Nombre | Descripción |
|---|---|---|
| 1 | **Escalas Intermedias** | No se consideran vuelos con escalas en destinos intermedios. |
| 2 | **Itinerario de Vuelo** | Los vuelos tienen un único aeropuerto de origen y un único aeropuerto de destino. |
| 3 | **Definición de Vuelo** | La definición de los vuelos se diagrama determinando el número de vuelo, el(los) día(s) de la semana, hora de salida, duración, aeropuertos de origen y destino, y tarifas de los boletos. |
| 4 | **Tarifas de Vuelo** | Las tarifas de los vuelos dependen del destino, el tipo de clase deseada (*básica, turista, negocios reducida, negocios, primera clase*) y el tipo de aeronave. No se consideran variaciones por fecha de compra anticipada, tipo de vuelo (ida o ida y vuelta) o reserva con posibilidad de cambiar fecha y hora. |
| 5 | **Estado del Vuelo** | Un vuelo puede estar en varios estados a lo largo del tiempo, pero solo en un estado en un momento particular. La situación de cada vuelo debe ser informada en todo momento y a lo largo del tiempo. |
| 6 | **Reservas de Vuelo** | Las reservas tienen una fecha y hora de vencimiento. |
| 7 | **Cancelación de Vuelo** | Un vuelo puede ser cancelado en cualquier momento hasta que aterrice. Una vez aterrizado, no puede ser cancelado. |
| 8 | **Venta de Boletos** | La venta de boletos siempre se finaliza a partir de reservas. Es decir, un boleto no puede ser vendido directamente; primero debe ser reservado y luego vendido. |

> 🛈 Las reglas de negocio están sujetas a cambios futuros, lo que puede implicar la creación, eliminación o modificación de las mismas.