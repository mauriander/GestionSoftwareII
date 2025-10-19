# 📚 Documentación del Sistema Veterinario

<div style="position: fixed; bottom: 20px; right: 20px; z-index: 1000;">
  <a href="#inicio" style="text-decoration: none;">
    <button style="
      background: #2E86C1;
      color: white;
      border: none;
      border-radius: 50%;
      width: 60px;
      height: 60px;
      font-size: 24px;
      cursor: pointer;
      box-shadow: 0 4px 8px rgba(0,0,0,0.3);
      transition: all 0.3s ease;
    " onmouseover="this.style.transform='scale(1.1)'; this.style.background='#1B4F72';" 
    onmouseout="this.style.transform='scale(1)'; this.style.background='#2E86C1';">
      ↑
    </button>
  </a>
</div>

| 📘[[# MANUAL DE USUARIO]] | 📚 [[# DICCIONARIO DE DATOS]] | 🛠️ [[#MANUAL TÉCNICO]]]|

# 🖥️ Centro de Documentación

<div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 25px; margin: 30px 0;"><div style="background: white; padding: 25px; border-radius: 12px; box-shadow: 0 8px 25px rgba(0,0,0,0.1); border-left: 5px solid #4CAF50; transition: transform 0.3s ease;"> <div style="text-align: center; margin-bottom: 15px;"> <span style="font-size: 3em;">📘</span> </div> <h3 style="color: #2c3e50; margin-bottom: 15px; text-align: center;">Manual de Usuario</h3> <div style="position: fixed; bottom: 20px; right: 20px; z-index: 1000;">
</div></div></div>

### 📘 [[# MANUAL DE USUARIO]]

**Guía completa para usuarios finales**  
Aprende a utilizar todas las funciones del sistema con ejemplos prácticos y paso a paso.

---

<div style="background: white; padding: 25px; border-radius: 12px; box-shadow: 0 8px 25px rgba(0,0,0,0.1); border-left: 5px solid #2196F3; transition: transform 0.3s ease;"> <div style="text-align: center; margin-bottom: 15px;"> <span style="font-size: 3em;">📚</span> </div> <h3 style="color: #2c3e50; margin-bottom: 15px; text-align: center;">Diccionario de Datos</h3> <div style="text-align: center;"></div> </div>

### 📚 [[# DICCIONARIO DE DATOS]]

**Estructuras y relaciones de datos**  
Definiciones técnicas esenciales para desarrolladores y administradores.

---

<div style="background: white; padding: 25px; border-radius: 12px; box-shadow: 0 8px 25px rgba(0,0,0,0.1); border-left: 5px solid #FF9800; transition: transform 0.3s ease;"> <div style="text-align: center; margin-bottom: 15px;"> <span style="font-size: 3em;">🛠️</span> </div> <h3 style="color: #2c3e50; margin-bottom: 15px; text-align: center;">Manual Técnico</h3> <div style="text-align: center;"> </div> </div>
### 🛠️ [[#MANUAL TÉCNICO]]
**Arquitectura y configuración**  
Documentación técnica completa para el desarrollo y mantenimiento.

---

## 🔗 Tabla de Contenidos

### 📖 Manual de Usuario

- [[#1 Introducción|1. Introducción]]
- [[#2 Acceso al Sistema|2. Acceso al Sistema]]
- [[#3 Módulo de Usuarios|3. Módulo de Usuarios]]
- [[#4 Módulo de Mascotas|4. Módulo de Mascotas]]
- [[#5 Módulo de Consultas Veterinarias|5. Módulo de Consultas Veterinarias]]
- [[#6 Funciones Comunes|6. Funciones Comunes]]
- [[#7 Consejos y Mejores Prácticas|7. Consejos y Mejores Prácticas]]
- [[#8 Solución de Problemas|8. Solución de Problemas]]

### 🗃️ Diccionario de Datos

- [[#VentanaUsuarios|VentanaUsuarios]]
- [[#VentanaMascotas|VentanaMascotas]]
- [[#VentanaLogin|VentanaLogin]]
- [[#VentanaVeterinaria|VentanaVeterinaria]]
- [[#Relaciones entre Tablas|Relaciones entre Tablas]]

### 🔧 Manual Técnico

- [[#Arquitectura del Sistema|Arquitectura del Sistema]]
- [[#Funciones por Ventana|Funciones por Ventana]]
- [[#Patrones Implementados|Patrones Implementados]]

---

<a name="manual-de-usuario"></a>

## MANUAL DE USUARIO

[[#📚 Documentación del Sistema Veterinario|🔼 Volver al Inicio]]

- [1. Introducción](app://obsidian.md/index.html#1%20Introducci%C3%B3n)
- [2. Acceso al Sistema](app://obsidian.md/index.html#2%20Acceso%20al%20Sistema)
- [3. Módulo de Usuarios](app://obsidian.md/index.html#3%20M%C3%B3dulo%20de%20Usuarios)
- [4. Módulo de Mascotas](app://obsidian.md/index.html#4%20M%C3%B3dulo%20de%20Mascotas)
- [5. Módulo de Consultas Veterinarias](app://obsidian.md/index.html#5%20M%C3%B3dulo%20de%20Consultas%20Veterinarias)
- [6. Funciones Comunes](app://obsidian.md/index.html#6%20Funciones%20Comunes)
- [7. Consejos y Mejores Prácticas](app://obsidian.md/index.html#7%20Consejos%20y%20Mejores%20Pr%C3%A1cticas)
- [8. Solución de Problemas](app://obsidian.md/index.html#8%20Soluci%C3%B3n%20de%20Problemas)

### 1 Introducción

#### 1.1. Propósito del Sistema

El Sistema de Gestión Veterinaria es una aplicación desktop diseñada para administrar pacientes animales, sus dueños y el historial de consultas médicas veterinarias.

#### 1.2. Público Objetivo

- Veterinarios
- Recepcionistas de clínicas veterinarias
- Administradores de consultorios veterinarios

### 2 Acceso al Sistema

#### 2.1. Pantalla de Login

![[Pasted image 20251019184320.png]]

**Pasos para ingresar:**

1. **Usuario:** Ingrese su nombre de usuario asignado
2. **Contraseña:** Escriba su contraseña
3. **Botón Ingresar:** Haga clic para acceder al sistema

**Ejemplo:**

```
Usuario: veterinario1
Contraseña: ********
```

**⚠️ Nota:** Si las credenciales son incorrectas, el sistema mostrará un mensaje de error.

![[Pasted image 20251019183930.png]]

### 3 Módulo de Usuarios

#### 3.1. Gestión de Dueños de Mascotas

**Agregar Nuevo Usuario:**

1. Complete los campos obligatorios (\*):

   - **DNI\*:** Número de documento (ej: 12345678)
   - **Nombre\*:** Primer nombre (ej: María)
   - **Apellido\*:** Apellido completo (ej: González)

2. Complete los campos opcionales:

   - **Teléfono:** Número de contacto (ej: 1122334455)
   - **Email:** Correo electrónico (ej: maria@email.com)

3. **Activo:** Seleccione "Sí" (por defecto)
4. Haga clic en **"Agregar"**

**Editar Usuario Existente:**

1. **Seleccione** un usuario de la tabla haciendo clic en la fila
2. Los datos se cargarán automáticamente en el formulario
3. Modifique los campos necesarios
4. Haga clic en **"Editar"**

### 4 Módulo de Mascotas

[[#📚 Documentación del Sistema Veterinario|🔼 Volver al Inicio]]

<span id="4-modulo-mascotas"></span>

#### 4.1. Registro de Pacientes Animales

**Agregar Nueva Mascota:**

1. **Campos obligatorios:**

   - **Nombre\*:** Nombre de la mascota (ej: "Firulais")
   - **Dueño\*:** Seleccione un dueño de la lista desplegable
   - **Especie\*:** Seleccione el tipo de animal

2. **Campos opcionales:**
   - **Raza:** Raza o variedad (ej: "Labrador")
   - **Fecha Nacimiento:** Formato DD/MM/AAAA (ej: 15/03/2020)
   - **Color:** Color predominante (ej: "Marrón")
   - **Peso:** En kilogramos (ej: 15.5)

#### 4.2. Ejemplos de Registro por Especie

**Perro:**

```yaml
Nombre: Max
Dueño: Juan Pérez
Especie: Perro
Raza: Golden Retriever
Fecha Nacimiento: 10/05/2019
Color: Dorado
Peso: 25.5
☑ Esterilizado
```

**Gato:**

```yaml
Nombre: Luna
Dueño: Ana García
Especie: Gato
Raza: Siamés
Fecha Nacimiento: 20/08/2021
Color: Blanco y negro
Peso: 4.2
☐ Esterilizado
```

### 5 Módulo de Consultas Veterinarias

#### 5.1. Registrar Nueva Consulta

**Pasos para Agendar Consulta:**

1. **Fecha\*:** DD/MM/AAAA (ej: 25/12/2024)
2. **Seleccionar Usuario\*:** Elija el dueño de la lista
3. **Seleccionar Mascota\*:** Se filtran solo las mascotas del dueño seleccionado
4. **Tipo Consulta:** Seleccione de: Control, Urgencia, Vacunación, Cirugía
5. **Peso\*:** Peso actual en kg (ej: 12.5)
6. **Observación\*:** Descripción de la consulta o síntomas
7. Haga clic en **"Agregar Consulta"**

#### 5.2. Ejemplos de Consultas

**Consulta de Control:**

```yaml
Fecha: 15/01/2024
Usuario: María González
Mascota: Firulais
Especie: Canino
Tipo Consulta: Control
Peso: 18.3
Observación: Control anual, buen estado general
```

### 6 Funciones Comunes

#### 6.1. Validaciones del Sistema

**Validaciones de Fecha:**

- Formato requerido: DD/MM/AAAA
- Ejemplos válidos: 01/01/2024, 25/12/2023
- Ejemplos inválidos: 1/1/24, 2024-01-01

**Validaciones de Peso:**

- Formato: Número decimal
- Separador decimal: Punto o coma
- Rango válido: 0.01 - 999.99 kg

### 7 Consejos y Mejores Prácticas

#### 7.1. Para Usuarios del Sistema

1. **Registre dueños completos:** Incluya teléfono y email para contactos de emergencia
2. **Actualice pesos:** Pese a las mascotas en cada consulta para seguimiento preciso
3. **Descriptivas observaciones:** Incluya síntomas, tratamientos y recomendaciones

### 8 Solución de Problemas

#### 8.1. Problemas Comunes

**"Error al conectar con la base de datos"**

- Verifique que el archivo Database2.accdb exista en la ruta especificada
- Confirme que no esté abierto en otro programa

**"Formato de fecha inválido"**

- Use siempre el formato DD/MM/AAAA
- Incluya ceros a la izquierda (ej: 05/03/2024, no 5/3/2024)

---

<a name="diccionario-de-datos"></a>

## DICCIONARIO DE DATOS

[[#📚 Documentación del Sistema Veterinario|🔼 Volver al Inicio]]

- [[#VentanaUsuarios|VentanaUsuarios]]
- [[#VentanaMascotas|VentanaMascotas]]
- [[#VentanaLogin|VentanaLogin]]
- [[#VentanaVeterinaria|VentanaVeterinaria]]
- [[#Relaciones entre Tablas|Relaciones entre Tablas]]

### VentanaUsuarios

#### Tabla: Usuarios

| Campo          | Tipo de Dato | Rango/Descripción                      | Ejemplo             |
| -------------- | ------------ | -------------------------------------- | ------------------- |
| **ID_Usuario** | INT (PK)     | Número entero positivo autoincremental | 1, 2, 3...          |
| **DNI**        | VARCHAR(20)  | Texto alfanumérico, único              | "12345678"          |
| **Nombre**     | VARCHAR(50)  | Texto alfabético, 2-50 caracteres      | "Juan"              |
| **Apellido**   | VARCHAR(50)  | Texto alfabético, 2-50 caracteres      | "Pérez"             |
| **Telefono**   | VARCHAR(20)  | Texto numérico, 7-20 caracteres        | "1122334455"        |
| **Email**      | VARCHAR(100) | Formato de email válido                | "usuario@email.com" |
| **Activo**     | BOOLEAN      | Valores: TRUE/FALSE                    | TRUE                |

#### Componentes de la Interfaz

| Componente      | Tipo       | Propósito            | Validación         |
| --------------- | ---------- | -------------------- | ------------------ |
| **txtDNI**      | JTextField | Número de documento  | Obligatorio, único |
| **txtNombre**   | JTextField | Nombre del usuario   | Obligatorio        |
| **txtApellido** | JTextField | Apellido del usuario | Obligatorio        |

### VentanaMascotas

#### Tabla: Mascotas

| Campo               | Tipo de Dato | Rango/Descripción                      | Ejemplo      |
| ------------------- | ------------ | -------------------------------------- | ------------ |
| **ID_Mascota**      | INT (PK)     | Número entero positivo autoincremental | 1, 2, 3...   |
| **ID_Usuario**      | INT (FK)     | Clave foránea a Usuarios               | 101, 102...  |
| **Nombre**          | VARCHAR(50)  | Texto alfanumérico                     | "Firulais"   |
| **Especie**         | VARCHAR(50)  | Valores predefinidos                   | "Canino"     |
| **Raza**            | VARCHAR(50)  | Texto descriptivo                      | "Labrador"   |
| **FechaNacimiento** | DATE         | Formato DD/MM/YYYY                     | "15/03/2020" |
| **Peso**            | DECIMAL(5,2) | 0.01 - 999.99 kg                       | 15.50        |

#### Campos Específicos por Especie

| Especie        | Campos Específicos | Tipo        | Ejemplo    |
| -------------- | ------------------ | ----------- | ---------- |
| **Perro/Gato** | Esterilizado       | BOOLEAN     | TRUE/FALSE |
| **Pájaro**     | PuedeVolar         | BOOLEAN     | TRUE/FALSE |
| **Pez**        | TipoAgua           | VARCHAR(20) | "Dulce"    |

### VentanaLogin

#### Tabla: Usuarios (Para Login)

| Campo       | Tipo de Dato | Rango/Descripción    | Ejemplo       |
| ----------- | ------------ | -------------------- | ------------- |
| **usuario** | VARCHAR(50)  | Nombre de usuario    | "admin"       |
| **clave**   | VARCHAR(50)  | Contraseña de acceso | "password123" |

### VentanaVeterinaria

#### Tabla: ConsultasVeterinarias

| Campo            | Tipo de Dato | Rango/Descripción                      | Ejemplo      |
| ---------------- | ------------ | -------------------------------------- | ------------ |
| **ID_Consulta**  | INT (PK)     | Número entero positivo                 | 1, 2, 3...   |
| **Fecha**        | DATE         | No futura, máximo 3 meses              | "25/12/2023" |
| **ID_Usuario**   | INT (FK)     | Clave foránea a Usuarios               | 101, 102...  |
| **ID_Mascota**   | INT (FK)     | Clave foránea a Mascotas               | 201, 202...  |
| **TipoConsulta** | VARCHAR(20)  | Control, Urgencia, Vacunación, Cirugía | "Vacunación" |
| **Peso**         | DECIMAL(5,2) | 0.01 - 999.99 kg                       | 15.50        |

### Relaciones entre Tablas

| Tabla                     | Relación     | Tabla Relacionada | Tipo         |
| ------------------------- | ------------ | ----------------- | ------------ |
| **Mascotas**              | ID_Usuario → | Usuarios          | Muchos a Uno |
| **ConsultasVeterinarias** | ID_Usuario → | Usuarios          | Muchos a Uno |
| **ConsultasVeterinarias** | ID_Mascota → | Mascotas          | Muchos a Uno |

---

<a name="manual-tecnico"></a>

## MANUAL TÉCNICO

[[#📚 Documentación del Sistema Veterinario|🔼 Volver al Inicio]]

- [[#Arquitectura del Sistema|Arquitectura del Sistema]]
- [[#Funciones por Ventana|Funciones por Ventana]]
- [[#Patrones Implementados|Patrones Implementados]]

### Arquitectura del Sistema

#### Estructura de Paquetes

```
ventanas/
├── VentanaLogin.java
├── VentanaPrincipal.java
├── VentanaUsuarios.java
├── VentanaMascotas.java
└── VentanaVeterinaria.java
```

#### Diagrama de Dependencias

```
VentanaLogin → VentanaPrincipal → VentanaUsuarios
                                 → VentanaMascotas
                                 → VentanaVeterinaria
```

### Funciones por Ventana

#### VentanaLogin

**`validarLogin()`**

- Propósito: Autenticar usuario contra la base de datos
- Flujo: Captura credenciales → Consulta BD → Abre VentanaPrincipal
- Tecnología: JDBC con UCanAccess, PreparedStatement

**`personalizarCampoTexto()`**

- Propósito: Estilo consistente en campos de texto
- Características: Fuente SF Pro, bordes dinámicos, efectos visuales

#### VentanaUsuarios

**`agregarUsuario()`**

```java
// Validaciones
if (txtNombre.getText().trim().isEmpty()) {
    JOptionPane.showMessageDialog(this, "Nombre obligatorio");
    return;
}
// Inserción BD
String sql = "INSERT INTO Usuarios VALUES (?, ?, ?, ?, ?, ?)";
```

**`editarUsuario()`**

- Mecanismo: ListSelectionListener en tabla
- Al seleccionar: Habilita edición y carga datos

#### VentanaMascotas

**`actualizarCamposEspecificos()`**

- Propósito: Campos dinámicos según especie
- Implementación: switch + panelEspecifico.removeAll()

**`calcularEdad()`**

- Algoritmo: LocalDate + Period de Java Time API
- Resultado: "2 años y 3 meses", "6 meses"

#### VentanaVeterinaria

**`validarFecha()`**

- Reglas: No futura, máximo 3 meses antigüedad
- Implementación: SimpleDateFormat + Calendar

### Patrones Implementados

#### Gestión de Conexiones

- Conexión única pasada entre ventanas
- Prepared Statements
- Try-with-resources para manejo de recursos

#### Validación por Capas

1. **UI:** Campos obligatorios, formatos básicos
2. **Lógica:** Fechas, rangos, relaciones

#### Mapeo Manual

```java
ResultSet rs = stmt.executeQuery();
if (rs.next()) {
    txtNombre.setText(rs.getString("Nombre"));
    comboEspecie.setSelectedItem(rs.getString("Especie"));
}
```

### Manejo de Errores

#### Estrategia General

- Try-Catch específico para SQLException
- Feedback claro al usuario via JOptionPane

### Consideraciones de Performance

#### Mejoras Futuras

- Diseño
- Optimizacion

---

**📅 Versión:** 1.0  
**💻 Sistema Compatible:** Windows con Microsoft Access

[[#📚 Documentación del Sistema Veterinario|🔼 Volver al Inicio]]
