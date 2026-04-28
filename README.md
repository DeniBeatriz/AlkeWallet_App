[README_AlkeWallet.md](https://github.com/user-attachments/files/27183079/README_AlkeWallet.md)
# 💸 AlkeWallet App

> Aplicación Android desarrollada en **Kotlin** para gestionar un flujo simple de billetera digital, con autenticación básica, historial de transacciones, almacenamiento local y sincronización con API REST.

<p align="center">
  <img src="https://img.shields.io/badge/Android-Studio-3DDC84?style=for-the-badge&logo=androidstudio&logoColor=white" alt="Android Studio">
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin">
  <img src="https://img.shields.io/badge/Architecture-MVVM-0A66C2?style=for-the-badge" alt="MVVM">
  <img src="https://img.shields.io/badge/Database-Room-4CAF50?style=for-the-badge" alt="Room">
  <img src="https://img.shields.io/badge/API-Retrofit-FF6F00?style=for-the-badge" alt="Retrofit">
</p>

---

## ✨ Descripción

**AlkeWallet** es una app Android construida con Activities y arquitectura MVVM, pensada para simular operaciones de una billetera digital: inicio de sesión, registro, visualización de saldo, historial de movimientos, envío y recepción de dinero, y persistencia local de datos del usuario y transacciones.[cite:13]

El proyecto integra **Retrofit** para consumir una API REST creada en MockAPI y **Room** para guardar información localmente, permitiendo que la UI se alimente desde una capa de datos más ordenada y consistente.[cite:13]

---

## 🚀 Funcionalidades implementadas

- Inicio de sesión con manejo de sesión local.[cite:13]
- Registro de usuario y flujo inicial de acceso a la app.[cite:13]
- Pantalla principal con saldo e historial de transacciones.[cite:13]
- Envío y recepción de dinero desde pantallas dedicadas.[cite:13]
- Persistencia local con Room para historial y datos de usuario.[cite:13]
- Consumo de API REST con operaciones GET y POST mediante Retrofit.[cite:13]
- Sincronización entre datos remotos y base local para mantener consistencia del historial.[cite:13]

---

## 🧱 Arquitectura

El proyecto sigue un enfoque **MVVM**, separando la interfaz, la lógica de presentación y el acceso a datos para facilitar mantenimiento y depuración.[cite:13]

### Capas principales

| Capa | Responsabilidad |
|------|------------------|
| UI | Activities como `LoginActivity`, `RegisterActivity`, `HomeActivity`, `SendMoneyActivity` y otras pantallas del flujo principal.[cite:13] |
| ViewModel | Exposición de estado a la UI, manejo de balance, historial, errores y operaciones de transacción.[cite:13] |
| Repository | Coordinación entre API REST y almacenamiento local con Room.[cite:13] |
| Data local | Entidades, DAO y base de datos Room para persistencia local.[cite:13] |
| Data remota | Cliente Retrofit y `ApiService` para consultar y registrar datos en MockAPI.[cite:13] |

---

## 🛠️ Stack tecnológico

- **Lenguaje:** Kotlin.[cite:13]
- **IDE:** Android Studio.[cite:13]
- **Arquitectura:** MVVM.[cite:13]
- **Base de datos local:** Room.[cite:13]
- **Consumo de red:** Retrofit.[cite:13]
- **Backend mock:** MockAPI en `https://69d9aebc26585bd92dd331f5.mockapi.io/api/v1/`.[cite:13]

---

## 🔄 Flujo de datos

1. La UI solicita acciones como login, carga de transacciones o envío de dinero.[cite:13]
2. El ViewModel delega la operación al Repository.[cite:13]
3. El Repository consulta la API o Room según corresponda y sincroniza resultados.[cite:13]
4. La UI observa cambios de estado y actualiza saldo, historial y mensajes de error.[cite:13]

Este enfoque permitió resolver parte importante del trabajo práctico centrado en integrar **API + base local + visualización en interfaz** dentro de una misma aplicación Android.[cite:13][cite:15]

---

## 📁 Estructura general esperada

```text
app/
└── src/main/
    ├── java/com/example/alkewallet/
    │   ├── ui/
    │   ├── viewmodel/
    │   ├── controller/
    │   ├── data/api/
    │   ├── data/network/
    │   ├── model/
    │   └── utils/
    └── res/
        ├── layout/
        ├── drawable/
        └── values/
```

Esta organización refleja la separación por responsabilidades que se fue consolidando durante el trabajo del proyecto.[cite:13][cite:22]

---

## 📌 Estado del proyecto

El proyecto ya cuenta con una base sólida para demostrar integración de interfaz Android, persistencia local y consumo de servicios REST en un caso de uso tipo billetera digital.[cite:13]

Los principales avances se concentraron en asegurar que las transacciones puedan registrarse, mostrarse y sincronizarse de forma más confiable, reduciendo inconsistencias entre datos locales y remotos.[cite:13][cite:28]

---

## ▶️ Cómo ejecutar

1. Clonar el repositorio.
2. Abrir el proyecto en Android Studio.
3. Sincronizar Gradle.
4. Verificar acceso a internet para consumir la API mock.
5. Ejecutar la app en emulador o dispositivo Android.

---

## 🌐 API utilizada

La aplicación fue conectada a una API REST creada en MockAPI para soportar la obtención y registro de datos durante el flujo de transacciones.[cite:13][cite:21]

- Base URL: [`https://69d9aebc26585bd92dd331f5.mockapi.io/api/v1/`](https://69d9aebc26585bd92dd331f5.mockapi.io/api/v1/)

---

## 👩‍💻 Autoría

Proyecto Android desarrollado como parte de un trabajo práctico orientado a integrar consumo de API REST, base de datos local y presentación en interfaz móvil.[cite:13][cite:15]
