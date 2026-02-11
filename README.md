# 📅 University Exam Scheduling System

A comprehensive desktop application built with **Java Swing** and **PostgreSQL** designed to automate and optimize the process of scheduling university exams. This system handles classroom capacities, student conflicts, and time constraints to generate a conflict-free exam timetable.

## 🚀 Features

* **Role-Based Access Control:** Secure login system for Administrators and Department Coordinators.
* **Data Import:** Bulk import of **Courses** and **Students** using Excel files (`.xlsx`), powered by Apache POI.
* **Classroom Management:**
    * Add, edit, and delete classrooms.
    * **Visual Layout:** View a graphical representation of the classroom seating arrangement based on row/column configuration.
* **Smart Scheduling Algorithm:**
    * Prevents conflicts (e.g., a student cannot have two exams at the same time).
    * Checks classroom capacities against student counts.
    * Supports simultaneous exams or sequential scheduling.
* **Search & Filtering:** Advanced search functionality for students, courses, and classrooms.
* **Reporting:** Export the final generated exam schedule to **PDF** format using iText.

## 🛠️ Tech Stack

* **Language:** Java (JDK 8+)
* **GUI:** Java Swing
* **Database:** PostgreSQL
* **Build Tool:** Maven
* **Libraries:**
    * `Apache POI` (Excel Data Handling)
    * `iText PDF` (PDF Generation)
    * `JCalendar` (Date Pickers)
    * `PostgreSQL JDBC Driver`

## ⚙️ Installation & Setup

### 1. Clone the Repository
```bash
git clone [https://github.com/YOUR_USERNAME/University-Exam-Scheduling-System.git](https://github.com/YOUR_USERNAME/University-Exam-Scheduling-System.git)
cd University-Exam-Scheduling-System
```

### 2. Database Configuration
  1. Ensure PostgreSQL is installed and running.

  2. Create a new database named postgres (or modify the connection string).

  3. Run the following SQL script to create the necessary tables and the default admin user:

```bash
CREATE TABLE kullanicilar (
    id SERIAL PRIMARY KEY,
    ad_soyad VARCHAR(100) NOT NULL,
    eposta VARCHAR(100) NOT NULL,
    sifre VARCHAR(100) NOT NULL,
    bolum_adi VARCHAR(100) NOT NULL
);

CREATE TABLE derslikler (
    bolum_adi VARCHAR(100) NOT NULL,
    derslik_kodu VARCHAR(100) PRIMARY KEY,
    derslik_adi VARCHAR(100) NOT NULL,
    derslik_kapasitesi INT NOT NULL,
    sira_sutun INT NOT NULL,
    sira_satir INT NOT NULL,
    sira_yapisi INT NOT NULL
);

CREATE TABLE dersler (
    ders_kodu VARCHAR(100) NOT NULL,
    ders_adi VARCHAR(100) NOT NULL,
    ders_hocasi VARCHAR(100) NOT NULL,
    dersin_yapisi VARCHAR(100) NOT NULL,
    sinif_seviyesi VARCHAR(100) NOT NULL,
    bolum_adi VARCHAR(100) NOT NULL
);

CREATE TABLE ogrenciler (
    ogrenci_no VARCHAR(100) NOT NULL,
    ad_soyad VARCHAR(100) NOT NULL,
    sinif VARCHAR(100) NOT NULL,
    ders_kodu VARCHAR(100) NOT NULL,
    bolum_adi VARCHAR(100) NOT NULL
);

CREATE TABLE sinav_sureleri (
    ders_kodu VARCHAR(100) NOT NULL,
    sinav_suresi INT NOT NULL,
    bolum_adi VARCHAR(100) NOT NULL
);

CREATE TABLE sinav_programi (
    sinav_tarihi VARCHAR(100) NOT NULL,
    sinav_saati VARCHAR(100) NOT NULL,
    ders_adi VARCHAR(100) NOT NULL,
    ogretim_elemani VARCHAR(100) NOT NULL,
    derslik_adi VARCHAR(100) NOT NULL
);

-- Default Admin User
INSERT INTO Kullanicilar(ad_soyad, eposta, sifre, bolum_adi) VALUES('admin', 'admin', 'admin', 'admin');
```

### 3. Update Database Credentials
Open src/Aksiyonlar/DbBaglantisi.java and update your database credentials:

```bash
private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
private static final String USER = "postgres";
private static final String PASSWORD = "YOUR_DB_PASSWORD"; // Update this!
```

### 4. Run the Application
You can run the application via your IDE (Eclipse/IntelliJ) by executing the AnaPencere.java file or using Maven:

```bash
mvn clean install
java -cp target/your-app-name.jar Arayuz.AnaPencere
```
