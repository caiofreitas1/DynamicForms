# DynamicForms (Android)

This repository contains the **Android** implementation of a **dynamic forms application**, built using **Kotlin**, **Jetpack Compose**, **Room**, **Hilt**, and the **MVVM architecture**. The application loads forms from JSON files, displays them dynamically, and persists the user’s responses locally.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [How to Run](#how-to-run)
- [Project Structure](#project-structure)

---

## Overview

DynamicForms (Android) is an application that **dynamically renders forms** based on JSON definitions. It supports various field types (text, number, dropdown, description) and persists the forms and user inputs in **Room**. The application loads forms only once (controlled by a simple flag in **SharedPreferences**), and reuses the saved data in subsequent sessions.

---

## Features

- **Form List Screen**: Displays the list of available forms.
- **Form Entries Screen**: Shows all saved entries for a selected form and provides an option to add a new entry.
- **Form Entry Screen**: Dynamically renders the form sections and fields, supporting different input types (text, number, dropdown, description).
- **Persistence**: All forms and user inputs are stored in **Room**, with a one-time loading of JSON resources on first launch.

---

## Prerequisites

- **Android Studio Flamingo+** (recommended).
- **Kotlin 1.9+**.
---

## How to Run

1. **Clone the Repository**
    ```bash
    git clone https://github.com/caiofreitas1/DynamicForms.git
    cd DynamicForms-Android
    ```

2. **Open in Android Studio**
    - Select the `DynamicForms-Android` project folder.
    - Make sure you have the required Kotlin and Compose versions in your Gradle settings.

3. **Run the Project**
    - Press the Run button in Android Studio.
    - The Form List Screen should appear once the app launches.

4. **Check JSON Resources**
    - Ensure `all_fields.json` and `200-form.json` are placed in `app/src/main/res/raw/`.
    - Confirm they’re properly added to your source set (e.g., no naming conflicts).

---

## Project Structure

```plaintext
app/src/main/
├── java/com/example/dynamicforms
│   ├── MyApplication.kt
│   ├── di
│   │   ├── DatabaseModule.kt
│   │   ├── LocalDataSourceModule.kt
│   │   ├── RepositoryModule.kt
│   ├── data
│   │   ├── datasource
│   │   │   ├── FormLocalDataSource.kt
│   │   │   └── FormLocalDataSourceImpl.kt
│   │   ├── local
│   │   │   ├── AppDatabase.kt
│   │   │   ├── FormDao.kt
│   │   │   ├── SectionDao.kt
│   │   │   ├── FieldDao.kt
│   │   │   ├── OptionDao.kt
│   │   │   ├── EntryDao.kt
│   │   ├── model
│   │   │   ├── FormEntity.kt
│   │   │   ├── SectionEntity.kt
│   │   │   ├── FieldEntity.kt
│   │   │   ├── OptionEntity.kt
│   │   │   ├── EntryEntity.kt
│   │   │   ├── FormDto.kt
│   │   │   ├── FieldDto.kt
│   │   │   ├── OptionDto.kt
│   │   │   ├── SectionDto.kt
│   │   │   └── DtoMappers.kt
│   │   ├── repository
│   │   │   └── FormRepositoryImpl.kt
│   ├── domain
│   │   ├── model
│   │   │   ├── Form.kt
│   │   │   ├── Field.kt
│   │   │   ├── Option.kt
│   │   │   ├── Section.kt
│   │   │   └── Entry.kt
│   │   ├── repository
│   │   │   └── FormRepository.kt
│   ├── ui
│   │   ├── MainActivity.kt
│   │   ├── navigation
│   │   │   └── AppNavGraph.kt
│   │   ├── formlist
│   │   │   ├── FormListScreen.kt
│   │   │   └── FormListViewModel.kt
│   │   ├── formentries
│   │   │   ├── FormEntriesScreen.kt
│   │   │   └── FormEntriesViewModel.kt
│   │   ├── formentry
│   │   │   ├── FormEntryScreen.kt
│   │   │   └── FormEntryViewModel.kt
│   │   └── components
│   │       └── FieldComposable.kt
│   └── utils
│       └── FormJsonParser.kt
└── res
    └── raw
        ├── all_fields.json
        └── 200-form.json
