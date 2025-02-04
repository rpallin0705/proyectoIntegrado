# Kizuna Gourmet - App de Restaurantes

Kizuna Gourmet es una aplicación móvil pensada para mostrar información sobre restaurantes exclusivos. Los usuarios pueden consultar detalles como el nombre, la dirección, el teléfono y la calificación de cada restaurante, todo a través de una interfaz sencilla y atractiva. En la app se utiliza un `RecyclerView` con `CardView` para mostrar la lista de restaurantes de forma visual e interactiva.

## Estructura del Proyecto

1. **Adapter**: Se encarga de gestionar la visualización de los datos de los restaurantes.
2. **Controller**: Contiene la lógica principal de la app, que conecta los datos con las vistas.
3. **DAO (Data Access Object)**: Facilita el acceso y manipulación de los datos de los restaurantes.
4. **Fragments**: Agrupa diferentes partes de la interfaz de usuario, como la pantalla con la lista de restaurantes.
5. **Interfaces**: Define cómo interactuar con los datos dentro de la app.
6. **Models**: Clases que representan los datos utilizados en la aplicación, como los restaurantes.

## Adapter

### `AdapterLocal.kt`

Este archivo define el adaptador que se usa para mostrar la lista de restaurantes en un `RecyclerView`. El adaptador se encarga de inflar la vista de cada elemento en la lista y de asociar las acciones de la interfaz con la lógica de la app.

#### Funcionalidad:

1. **Propiedades**:
   - `listLocal`: Lista mutable de objetos `Local`, que contiene los datos que se mostrarán en la interfaz.
   - `deleteOnClick`: Función de callback que se ejecuta cuando el usuario hace clic en el botón de eliminar, pasando el índice del restaurante.

2. **Métodos principales**:
   - `onCreateViewHolder`: Infla el layout para cada ítem de la lista (`activity_local.xml`), creando una nueva instancia de `ViewLocal` (el `ViewHolder`).
   - `onBindViewHolder`: Asocia los datos del objeto `Local` en la posición indicada con las vistas correspondientes en el `ViewHolder`.
   - `getItemCount`: Devuelve el número de ítems en la lista, lo que le indica al `RecyclerView` cuántos elementos debe mostrar.

Este adaptador gestiona la visualización de cada restaurante dentro del `RecyclerView` y maneja la interacción con los elementos, como el botón para eliminar un restaurante.

### `ViewLocal.kt`

Este archivo define el `ViewHolder` para cada ítem de la lista dentro del `RecyclerView`. Un `ViewHolder` se encarga de vincular los datos de cada restaurante con las vistas correspondientes en la interfaz.

#### Funcionalidad:

1. **Propiedades**:
   - `binding`: Instancia de `ActivityLocalBinding`, que se utiliza para acceder a las vistas dentro del layout de cada ítem del `RecyclerView` (definido en `activity_local.xml`).
   - `deleteOnClick`: Callback que se ejecuta cuando el usuario hace clic en el botón de eliminar un restaurante, pasando la posición del elemento.

2. **Métodos principales**:
   - `renderize`: Asocia los datos del objeto `Local` a las vistas en el layout. Esto incluye:
     - Mostrar el nombre del restaurante en `localName`.
     - Mostrar la dirección en `localAddress`.
     - Mostrar el número de contacto en `localPhone`.
     - Cargar la imagen del restaurante con `Glide` en `localImage`.
   
   - `setOnClickListener`: Configura el comportamiento del botón de eliminar. Cuando el usuario hace clic, se ejecuta el callback `deleteOnClick` y se pasa la posición del ítem.

## Controller

### `LocalController.kt`

Este archivo define la clase `Controller`, que maneja la lógica de negocio y la interacción entre los datos y la interfaz de usuario. Su principal tarea es gestionar la lista de restaurantes y configurar el adaptador para el `RecyclerView`.

#### Funcionalidad:

1. **Propiedades**:
   - `context`: El contexto de la app, que se necesita para realizar operaciones como mostrar `Toast` y acceder a recursos del sistema.
   - `listLocales`: Lista mutable de objetos `Local` que representa los restaurantes disponibles en la app.
   - `adapterLocal`: El adaptador (`AdapterLocal`) que maneja la visualización de la lista de restaurantes en el `RecyclerView`.

2. **Métodos principales**:
   - `initData`: Inicializa la lista `listLocales` llamando a `DaoLocal.myDao.getDataLocals()`, que obtiene los datos de los restaurantes desde la fuente de datos.
   - `logOut`: Muestra un mensaje de `Toast` y recorre la lista de locales (aunque no hace nada con ella en este caso).
   - `setAdapter`: Configura el `RecyclerView` con un nuevo adaptador (`AdapterLocal`) y define el comportamiento del botón de eliminar (`delHotel`), que elimina un restaurante de la lista cuando el usuario lo selecciona.
   - `delHotel`: Elimina el restaurante de la lista en la posición `pos` y notifica al adaptador que el elemento ha sido eliminado, actualizando la vista.

## Dao

### `LocalDao.kt`

Este archivo define la clase `DaoLocal`, que implementa la interfaz `InterfaceDao` y maneja el acceso a los datos de los restaurantes. Actúa como el objeto de acceso a datos (DAO) y permite interactuar con la fuente de datos de manera centralizada.

#### Funcionalidad:

1. **Propiedades**:
   - `myDao`: Instancia única de `DaoLocal` implementada mediante el patrón *Singleton* con `lazy`. Esto asegura que solo haya una instancia de `DaoLocal` en la app.

2. **Métodos**:
   - `getDataLocals()`: Implementa el método de la interfaz `InterfaceDao`. Este método devuelve la lista de restaurantes (locales) de la clase `LocalRepository`.

### `LocalDao2.kt`

Este archivo define un objeto Singleton simplificado que también maneja el acceso a los datos de los locales, utilizando el patrón Singleton en Kotlin.

#### Funcionalidad:

1. **Propiedades**:
   - `myDao`: Una propiedad estática que inicializa el acceso a los datos solo cuando se necesita (lazy initialization).

2. **Métodos**:
   - `getDataLocals()`: Retorna la lista de objetos `Local` desde el repositorio `LocalRepository`.

## Fragments

### `LocalFragment.kt`

Este archivo define un fragmento que muestra la lista de restaurantes en un `RecyclerView`. El fragmento obtiene los datos de los restaurantes desde el repositorio y los presenta utilizando un adaptador personalizado.

#### Funcionalidad:

1. **Propiedades**:
   - `recyclerView`: El `RecyclerView` donde se mostrarán los restaurantes en forma de tarjetas. Se configura con un `LinearLayoutManager` para organizar los elementos de forma vertical.
   - `adapter`: El adaptador (`AdapterLocal`) que maneja la vista de cada ítem de la lista y permite gestionar acciones como eliminar un restaurante.

2. **Métodos**:
   - `onViewCreated(view: View, savedInstanceState: Bundle?)`: Se llama después de que la vista del fragmento se ha creado. En este método:
     - Se configura el `RecyclerView` con su `LayoutManager`.
     - Se obtiene la lista de restaurantes desde el repositorio.
     - Se crea y configura el adaptador, pasando la lista de locales y un `lambda` para manejar la eliminación de un restaurante.
     - Se asigna el adaptador al `RecyclerView`.

3. **Acciones de usuario**:
   - El `RecyclerView` permite interactuar con los elementos de la lista. El adaptador gestiona la lógica para eliminar un restaurante cuando el usuario hace clic en el botón de eliminación, actualizando la vista al eliminar el restaurante.

## Interfaces

### `InterfaceDao.kt`

Este archivo define una interfaz que establece cómo acceder a los datos de los restaurantes en la app. Es implementada por las clases que proporcionan los datos, como `DaoLocal`.

#### Funcionalidad:

1. **Método**:
   - `getDataLocals()`: Este método es un **getter** que devuelve una lista de objetos `Local`. Las clases que implementan esta interfaz deben ofrecer la lógica para obtener los datos de los restaurantes, ya sea de una base de datos, API, etc.

## Model

### `Local.kt`

Este archivo define la clase `Local`, que representa a un restaurante dentro de la app. La clase almacena los datos de un restaurante y proporciona una forma estructurada de representarlos.

#### Atributos de la clase:

1. **`nombre`** (`String`): Nombre del restaurante.
2. **`direccion`** (`String`): Dirección física del restaurante.
3. **`contacto`** (`String`): Número de contacto del restaurante.
4. **`valoracion`** (`Int`): Valoración del restaurante, representada como un número (por ejemplo, 1 a 5 estrellas).

#### Métodos:

1. **`toString()`**:
   - Método sobrescrito que convierte el objeto `Local` en una cadena de texto legible, representada en formato JSON.

### `LocalRepository.kt`

Este archivo define un repositorio (`LocalRepository`) que contiene una lista estática de restaurantes. Este repositorio actúa como una base de datos predefinida, proporcionando la lista de restaurantes.

#### Atributos:

- **`locales`** (`List<Local>`): Lista de objetos `Local` con los datos de los restaurantes (nombre, dirección, contacto y valoración).

## MainActivityantiguo.kt

El archivo `MainActivityantiguo.kt` es la actividad principal que muestra el fragmento con la lista de restaurantes. Se encarga de gestionar la pantalla inicial de la app y su navegación.

## Conclusión

El diseño de esta app permite mostrar de manera efectiva información sobre restaurantes, presentando una lista organizada que el usuario puede consultar. Además, las acciones como eliminar un restaurante están bien implementadas con interacciones fáciles de usar.
