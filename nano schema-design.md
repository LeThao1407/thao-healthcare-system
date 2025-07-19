# Database Schema Design

## Tables

### 1. Patient
- `id`: INT, PRIMARY KEY, AUTO_INCREMENT
- `name`: VARCHAR(100)
- `email`: VARCHAR(100), UNIQUE
- `password`: VARCHAR(255)

### 2. Doctor
- `id`: INT, PRIMARY KEY, AUTO_INCREMENT
- `name`: VARCHAR(100)
- `email`: VARCHAR(100), UNIQUE
- `specialization`: VARCHAR(100)
- `available_times`: TEXT

### 3. Appointment
- `id`: INT, PRIMARY KEY, AUTO_INCREMENT
- `patient_id`: INT, FOREIGN KEY → Patient(id)
- `doctor_id`: INT, FOREIGN KEY → Doctor(id)
- `appointment_time`: DATETIME
- `status`: ENUM('Scheduled', 'Completed', 'Cancelled')

### 4. Admin
- `id`: INT, PRIMARY KEY, AUTO_INCREMENT
- `username`: VARCHAR(100), UNIQUE
- `password`: VARCHAR(255)
