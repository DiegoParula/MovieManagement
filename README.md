# MovieManagement 🎥

**MovieManagement** es una aplicación desarrollada en Java utilizando Spring Boot para la gestión de películas y usuarios. Permite realizar operaciones CRUD tanto para películas como usuarios, y proporciona funcionalidades avanzadas como calificaciones de películas, validaciones personalizadas y manejo profesional de excepciones.

## 🚀 Características principales
- API REST modular y escalable: Diseñada con Spring Boot 3 y una arquitectura modular para garantizar la escalabilidad y facilidad de mantenimiento.
- Gestión de entidades Movie, User y Rating: CRUD completo e intuitivo utilizando Spring Data JPA, con consultas optimizadas para mejorar el rendimiento.
- Sistema de calificaciones dinámico: Los usuarios pueden asignar valoraciones a las películas, permitiendo un análisis enriquecido de datos.
- Manejo avanzado de transacciones: Garantiza la integridad de los datos y evita inconsistencias mediante un enfoque profesional de transacciones.
- Patrón DTO y mapeo de datos: Implementación del patrón DTO y clases Mapper para estructurar las respuestas de manera eficiente y mejorar la separación de responsabilidades.
- Validaciones robustas: Validaciones personalizadas con expresiones regulares (REGEX) y soporte completo para la validación de datos con Jakarta Validation API, asegurando la integridad de las entradas del usuario.
- Gestión global de excepciones: Sistema centralizado para manejar errores en toda la API, proporcionando respuestas consistentes y amigables para el cliente.
- Consultas avanzadas con JPA Specifications: Realización de búsquedas dinámicas y filtros complejos, adaptados a las necesidades del cliente o la aplicación.
- Paginación y ordenamiento optimizados: Implementación profesional de paginación y ordenamiento en las respuestas HTTP para mejorar la experiencia del usuario y la eficiencia del sistema.

## 📁 Estructura del proyecto

El proyecto sigue una estructura estándar para facilitar la escalabilidad y mantenimiento.

## 🛠️ Tecnologías utilizadas

- **Java** 17+
- **Spring Boot** 3
  - Spring Data JPA
  - Spring Web
  - Spring Validation
- **Jakarta Validation API**
- **Base de datos**: MySQL (puede ser configurada con cualquier otro RDBMS)
- **JPA Specifications** para consultas avanzadas
- **Docker** 

## 🚧 Instalación y configuración

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/tu-usuario/moviemanagement.git
   cd moviemanagement

1. **Configurar la base de datos**:

Asegúrate de tener una base de datos MySQL configurada.
Modifica el archivo application.properties para incluir tus credenciales y configuración de base de datos

En el caso de que desees utilizar una imagen de docker:
Ejecutar el docker-compose 

🔍 Consultas avanzadas
El proyecto implementa JPA Specifications para permitir consultas dinámicas y avanzadas.