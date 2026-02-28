# 🏥 Hospital Management System with AI Agent (Spring Boot / FastAPI + ReactJS / Flutter + PostgreSQL)

## 📋 Mục lục
- [Giới thiệu](#giới-thiệu)
- [Kiến trúc hệ thống](#kiến-trúc-hệ-thống)
- [Các tính năng chính](#các-tính-năng-chính)
- [Thiết kế Cơ sở dữ liệu](#thiết-kế-cơ-sở-dữ-liệu)
- [Backend API](#backend-api)
- [Frontend UI/UX](#frontend-uiux)
- [AI Agent & LangChain](#ai-agent--langchain)
- [Hệ thống nhắc nhở](#hệ-thống-nhắc-nhở)
- [Dashboard quản trị](#dashboard-quản-trị)
- [Triển khai (Deployment)](#triển-khai-deployment)
- [Giám sát & Logging](#giám-sát--logging)
- [Yêu cầu hệ thống](#yêu-cầu-hệ-thống)
- [Hướng dẫn cài đặt](#hướng-dẫn-cài-đặt)
- [Đóng góp](#đóng-góp)
- [Giấy phép](#giấy-phép)

---

## Giới thiệu
Dự án **Hospital Management System** cung cấp giải pháp toàn diện cho việc quản lý bệnh viện, từ quản lý bác sĩ, bệnh nhân, hồ sơ bệnh án, lịch hẹn, toa thuốc cho đến tích hợp AI Agent giúp trả lời và tư vấn tự động. Hệ thống hướng đến:
- Giảm thời gian thao tác thủ công.
- Cung cấp giao diện thân thiện cho bệnh nhân và bác sĩ.
- Khai thác AI để tự động hóa tra cứu, tư vấn và nhắc nhở.

---
## 📑 Tài liệu

- 📄 Báo cáo: [Clinic Management System Report](https://drive.google.com/file/d/1q_clWer57IcCypxWpnCzbhU99-wa5tVL/view?usp=sharing)
- 
---

## Kiến trúc hệ thống
Hệ thống được xây dựng theo kiến trúc **Microservices** và **API-first**:

- **Cơ sở dữ liệu (PostgreSQL)**: Lưu trữ thông tin bác sĩ, bệnh nhân, hồ sơ, lịch hẹn, toa thuốc.
- **Backend API**:
  - **Spring Boot** (hoặc **FastAPI**) cho RESTful API.
  - Tích hợp các service quản lý lịch khám, hồ sơ bệnh án.
- **Frontend**:
  - **ReactJS** (Web): cho bệnh nhân và bác sĩ.
  - **Flutter** (Mobile): ứng dụng di động bệnh nhân.
- **AI Agent**:
  - **GPT-4 + LangChain + RAG** để trả lời câu hỏi tự nhiên và truy vấn dữ liệu.
- **Notification Service**:
  - Email/SMS nhắc lịch tái khám, nhắc uống thuốc.
- **Monitoring & Logging**:
  - Cloud-based logging, metrics, và alerting.
- **Deployment**:
  - Container hóa bằng Docker.
  - Triển khai lên Cloud (AWS, Azure hoặc GCP).

---

## Các tính năng chính
- **Quản lý bác sĩ**: thông tin cá nhân, lịch làm việc, chuyên khoa.
- **Quản lý bệnh nhân**: đăng ký tài khoản, cập nhật hồ sơ cá nhân.
- **Hồ sơ bệnh án**: tạo mới, cập nhật, lưu trữ lịch sử khám.
- **Lịch hẹn khám**: đặt lịch, xác nhận, quản lý trạng thái.
- **Toa thuốc**: ghi nhận và nhắc nhở bệnh nhân.
- **AI Agent**: truy vấn lịch khám, hồ sơ bệnh án, tư vấn nhắc nhở.
- **Nhắc nhở tự động**: gửi email/SMS định kỳ.
- **Dashboard quản trị**: thống kê, báo cáo, phân tích.

---

## Thiết kế Cơ sở dữ liệu

### Bảng chính
- **doctors**:
  - `id`, `name`, `speciality`, `email`, `phone`, `schedule`
- **patients**:
  - `id`, `name`, `dob`, `gender`, `email`, `phone`, `address`
- **medical_records**:
  - `id`, `patient_id`, `doctor_id`, `diagnosis`, `treatment_plan`, `created_at`
- **appointments**:
  - `id`, `patient_id`, `doctor_id`, `appointment_date`, `status`
- **prescriptions**:
  - `id`, `medical_record_id`, `medication_name`, `dosage`, `instructions`

### Sơ đồ ERD (mô tả ngắn gọn)

---

## Backend API

### Công nghệ
- **Spring Boot** với JPA/Hibernate hoặc **FastAPI** với SQLAlchemy.
- JWT Authentication cho bảo mật API.
- OpenAPI/Swagger cho tài liệu API.

### Một số endpoint ví dụ
- `POST /api/patients` – Tạo bệnh nhân mới.
- `GET /api/doctors/{id}/schedule` – Lấy lịch làm việc bác sĩ.
- `POST /api/appointments` – Tạo lịch hẹn khám.
- `GET /api/medical-records/{patient_id}` – Truy vấn hồ sơ bệnh án bệnh nhân.
- `POST /api/prescriptions` – Ghi toa thuốc.

---

## Frontend UI/UX

### ReactJS (Web)
- **Patient Dashboard**: đặt lịch khám, xem hồ sơ bệnh án.
- **Doctor Dashboard**: xem danh sách ca khám, cập nhật kết quả khám.

### Flutter (Mobile)
- **Ứng dụng bệnh nhân**: đặt lịch, nhắc lịch, nhận thông báo.
- Giao diện hiện đại, hỗ trợ đa ngôn ngữ.

---

## AI Agent & LangChain
- **GPT-4 + LangChain + RAG**:
  - Cho phép người dùng hỏi:
    - “Ngày mai bác sĩ A có ca nào trống?”
    - “Bệnh nhân B có tái khám tuần này không?”
  - Nhắc lịch uống thuốc/tái khám.
- **RAG (Retrieval-Augmented Generation)**:
  - Kết nối cơ sở dữ liệu PostgreSQL.
  - Tạo Embeddings, vector search để truy vấn nhanh.
- **LangGraph**:
  - Xây dựng workflow phức tạp cho agent.

---

## Hệ thống nhắc nhở
- Tích hợp **Twilio/SendGrid** hoặc **AWS SNS** cho email/SMS.
- Định kỳ tự động gửi nhắc nhở:
  - Lịch khám sắp tới.
  - Lịch uống thuốc.
  - Tái khám.

---

## Dashboard quản trị
- Thống kê số ca khám theo ngày/tháng.
- Biểu đồ tỷ lệ tái khám.
- Phân tích dữ liệu theo phòng ban.
- Xuất báo cáo PDF/Excel.

---

## Triển khai (Deployment)
- **Docker Compose**:
  - `docker-compose.yml` gồm:
    - PostgreSQL
    - Backend API
    - Frontend
    - Notification service
- **CI/CD**:
  - GitHub Actions hoặc GitLab CI để tự động build/deploy.
- **Cloud Hosting**:
  - AWS EC2/ECS/EKS, Azure App Service hoặc GCP Cloud Run.
- **SSL & Domain**:
  - Reverse proxy bằng Nginx/Traefik.

---

## Giám sát & Logging
- **Monitoring**:
  - Prometheus + Grafana theo dõi CPU, RAM, requests.
- **Logging**:
  - ELK Stack (Elasticsearch, Logstash, Kibana) hoặc Cloud Logging.
- **Alerting**:
  - Cảnh báo khi hệ thống có lỗi hoặc downtime.

---

## Yêu cầu hệ thống
- **Backend**:
  - Java 17+ (Spring Boot) hoặc Python 3.11+ (FastAPI)
- **Frontend**:
  - Node.js 18+
  - Flutter 3+
- **Database**:
  - PostgreSQL 14+
- **Công cụ**:
  - Docker & Docker Compose

---

## Hướng dẫn cài đặt

### 1. Clone repository
```bash
git clone https://github.com/your-org/hospital-management-ai.git
cd hospital-management-ai
