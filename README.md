# Hệ Thống Quản Lý & Giao Dịch Bất Động Sản Trực Tuyến (Microservices Architecture)

Dự án là nền tảng giao dịch và đăng tin bất động sản được thiết kế và nâng cấp từ kiến trúc Monolith ban đầu sang hệ sinh thái **Microservices** hiện đại, an toàn và tối ưu hóa cao về hiệu năng lẫn trải nghiệm người dùng (UI/UX).

---

## 🏗️ Sơ Đồ Kiến Trúc Hệ Thống (Architecture & Request Flow)

```text
               +-------------------------------------------------+
               |                   CLIENT                        |
               +-------------------------------------------------+
                                       |
                                       | (HTTP requests)
                                       v
                                [Cổng 8000]
               +-------------------------------------------------+
               |              SPRING CLOUD GATEWAY               |
               |  - Redis IP Rate Limiting                       |
               |  - Global Audit Request Logging                 |
               +-------------------------------------------------+
                  /                    |                    \
                 /                     |                     \
 (Paths: /api/auth/**)       (Paths: /api/listings/**)     (Paths: /**)
               /                       |                       \
              v                        v                        v
         [Cổng 8081]              [Cổng 8080]              [Cổng 8080]
   +--------------------+   +--------------------+   +--------------------+
   |    AUTH SERVICE    |   |  LISTING REST API  |   |    THYMELEAF UI    |
   | - JWT Issuer       |   | - JSON DTOs        |   | - Web Pages        |
   | - Redis Session    |   | - Properties CRUD  |   | - Custom UI/UX     |
   +--------------------+   +--------------------+   +--------------------+
         \                             |                             /
          \                            |                            /
           v                           v                           v
   +----------------------------------------------------------------------+
   |                       EUREKA DISCOVERY SERVER [Cổng 8761]            |
   +----------------------------------------------------------------------+
           |                           |                           |
           v                           v                           v
   +-------------------+       +-------------------+               |
   |   MYSQL DB (3307) |       |   REDIS (6379)    |               v
   | (Shared Database) |       | (Session & Cache) |      +----------------+
   +-------------------+       +-------------------+      | Cloudinary API |
                                                          +----------------+
```

---

## 🛠️ Công Nghệ Sử Dụng (Technology Stack)

* **Backend Core**: Java 17, Spring Boot 3.3.5.
* **Microservices**:
  * **Spring Cloud Gateway**: Tầng trung gian định tuyến, lọc request.
  * **Spring Cloud Netflix Eureka Server**: Trung tâm đăng ký & khám phá dịch vụ (Service Discovery).
* **Security & Session**:
  * **Spring Security & JWT**: Xác thực, phân quyền dựa trên JSON Web Token.
  * **Spring Session Redis**: Đồng bộ, kiểm soát phiên đăng nhập và thu hồi session đồng thời (Concurrent Session Control).
* **Database & ORM**:
  * **Spring Data JPA & Hibernate**: Tương tác cơ sở dữ liệu quan hệ MySQL.
  * **Redis**: Lưu trữ bộ đếm Rate limit và thông tin phiên làm việc.
* **Giao Diện & UI/UX**:
  * **Thymeleaf**: Công cụ dựng template giao diện phía máy chủ.
  * **Modern Web API CSS**: Scrollbar customization, `text-wrap: balance/pretty`, `:user-invalid`, `accent-color`.
* **Đóng Gói**: Docker, Docker Compose.

---

## 📂 Danh Sách Các Mô-đun & Cổng Hoạt Động (Modules & Ports)

| Mô-đun | Port trong Docker | Mô Tả |
| :--- | :--- | :--- |
| **`eureka-server`** | `8761` | Service Registry quản lý thông tin các dịch vụ. |
| **`api-gateway`** | `8000` | Cổng định tuyến API, Rate Limit, ghi log request của Client. |
| **`auth-service`** | `8081` | Dịch vụ đăng ký, đăng nhập cấp JWT, invalid phiên làm việc cũ. |
| **`realestate-monolith`** | `8080` | Dịch vụ chính chứa Web UI Thymeleaf & APIs CRUD bất động sản. |
| **`realestate-db`** | `3307:3306` | Container cơ sở dữ liệu MySQL. |
| **`realestate-redis`** | `6379:6379` | Container lưu trữ Redis phục vụ session và rate limit. |

---

## 👤 Tài Khoản Dữ Liệu Seed Sẵn (Default Seed Accounts)

Hệ thống đã tự động cài đặt sẵn dữ liệu mẫu cho cơ sở dữ liệu bao gồm các vai trò và các tài khoản thử nghiệm sau:

| Email | Mật Khẩu | Tên Đầy Đủ | Vai Trò (Role) | Chức Năng |
| :--- | :--- | :--- | :--- | :--- |
| **`admin@admin.com`** | `admin123` | System Admin | `ROLE_ADMIN` | Quản trị viên hệ thống. |
| **`user@user.com`** | `user123` | John Doe | `ROLE_REALTOR` | Người dùng/Môi giới tiêu chuẩn. |
| **`realtor@realtor.com`** | `realtor123` | Alice Agent | `ROLE_REALTOR` | Môi giới chuyên nghiệp. |

---

## ⚡ Các Tính Năng & Tối Ưu Hóa Nổi Bật

### 1. Cơ Chế Chống DDoS & Giới Hạn Rate Limit
Thông qua **Spring Cloud Gateway** kết hợp **Redis Token Bucket**, hệ thống sẽ đếm tần suất gửi request dựa trên IP của máy khách:
* Route API xác thực (`/api/auth/**`): Giới hạn **5 request/giây**, tối đa dung lượng bucket là 10.
* Route xem danh sách bất động sản (`/api/listings/**`): Giới hạn **15 request/giây**.

### 2. Kiểm Soát Thiết Bị Đăng Nhập Đồng Thời (Concurrent Session Control)
* Khi người dùng đăng nhập thành công qua cổng `/api/auth/login`, `auth-service` sẽ truy cập vào bộ lưu trữ Redis qua Spring Session để tìm kiếm tất cả phiên làm việc trước đó của tài khoản này và thực hiện **hủy/xóa bỏ lập tức**. Điều này ngăn chặn việc tài khoản bị chia sẻ dùng chung trái phép.

### 3. Tối Ưu Hóa Giao Diện Người Dùng (UI/UX) theo chuẩn Modern Web
* **Màu sắc đồng bộ (`accent-color`)**: Đồng bộ hóa màu các nút checkbox, radio mặc định theo tông màu xanh thương hiệu.
* **Cân bằng văn bản (`text-wrap: balance/pretty`)**: Đảm bảo tiêu đề chính không bị lẻ loi từ ở cuối dòng khi co giãn màn hình.
* **Kiểm lỗi thông minh (`:user-invalid`)**: Form nhập liệu chỉ hiển thị lỗi đỏ sau khi người dùng bắt đầu nhập và rời khỏi input (blur/change), thay vì báo lỗi đỏ ngay khi mới tải trang.
* **Thanh cuộn cao cấp (`scrollbar-color`)**: Tùy biến thanh cuộn mảnh phù hợp với giao diện.

---

## 🚀 Hướng Dẫn Khởi Chạy Hệ Thống

### Cách 1: Khởi chạy bằng Docker Compose (Khuyên Dùng)

**Bước 1**: Đóng gói các file chạy Java Jar:
```bash
mvn clean package -DskipTests
```

**Bước 2**: Khởi động toàn bộ các container lên trong chế độ chạy ngầm:
```bash
docker compose up --build -d
```

**Bước 3**: Kiểm tra trạng thái hoạt động của các container:
```bash
docker compose ps
```

Khi toàn bộ các container ở trạng thái `Up`, bạn có thể mở trình duyệt để truy cập.

---

## 🔗 Liên Kết Truy Cập Nhanh

* **Trang chủ Website**: **[http://localhost:8000](http://localhost:8000)** (Hoặc truy cập trực tiếp qua cổng **[http://localhost:8080](http://localhost:8080)**).
* **Trang quản lý dịch vụ (Eureka)**: **[http://localhost:8761](http://localhost:8761)**.
* **Bộ sưu tập Postman để thử nghiệm API**: Bạn có thể tìm thấy tệp JSON Postman được cấu hình sẵn tại thư mục:
  `C:\Users\LAPTOP\.gemini\antigravity-ide\brain\9eac36b1-f672-4018-824e-bc17872c8a47\realestate_postman_collection.json`
