package com.example.Final;

import com.example.Final.entity.securityservice.Roles;
import com.example.Final.entity.securityservice.User;
import com.example.Final.entity.listingservice.Address;
import com.example.Final.entity.listingservice.PostInformation;
import com.example.Final.entity.listingservice.Properties;
import com.example.Final.entity.listingservice.Images;
import com.example.Final.repository.RolesRepository;
import com.example.Final.repository.PropertyRepo;
import com.example.Final.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class DataInitialization {

    @Bean
    public CommandLineRunner initData(RolesRepository rolesRepository, UserService userService,
                                      PropertyRepo propertyRepo) {
        return args -> {
            // Create ROLE_ADMIN if not exists
            Roles adminRole = rolesRepository.findRolesByName("ROLE_ADMIN");
            if (adminRole == null) {
                adminRole = new Roles();
                adminRole.setName("ROLE_ADMIN");
                rolesRepository.save(adminRole);
                System.out.println("Role ROLE_ADMIN created.");
            } else {
                System.out.println("Role ROLE_ADMIN already exists.");
            }

            // Create ROLE_REALTOR if not exists
            Roles realtorRole = rolesRepository.findRolesByName("ROLE_REALTOR");
            if (realtorRole == null) {
                realtorRole = new Roles();
                realtorRole.setName("ROLE_REALTOR");
                rolesRepository.save(realtorRole);
                System.out.println("Role ROLE_REALTOR created.");
            } else {
                System.out.println("Role ROLE_REALTOR already exists.");
            }

            // Create admin user if not exists
            if (userService.findUserByEmail("admin@admin.com") == null) {
                User admin = new User();
                admin.setEmail("admin@admin.com");
                admin.setPassword("admin123");
                admin.setFullName("System Admin");
                admin.setPhone("123456789");
                userService.createAdmin(admin);
                System.out.println("Admin user created: admin@admin.com");
            } else {
                System.out.println("Admin user already exists.");
            }

            // Create standard user (realtor role) if not exists
            if (userService.findUserByEmail("user@user.com") == null) {
                User user = new User();
                user.setEmail("user@user.com");
                user.setPassword("user123");
                user.setFullName("John Doe");
                user.setPhone("0987654321");
                userService.create(user);
                System.out.println("Standard user created: user@user.com");
            } else {
                System.out.println("Standard user already exists.");
            }

            // Create premium realtor if not exists
            if (userService.findUserByEmail("realtor@realtor.com") == null) {
                User realtor = new User();
                realtor.setEmail("realtor@realtor.com");
                realtor.setPassword("realtor123");
                realtor.setFullName("Alice Agent");
                realtor.setPhone("0123456789");
                userService.create(realtor);
                System.out.println("Premium realtor created: realtor@realtor.com");
            } else {
                System.out.println("Premium realtor already exists.");
            }

            // Seed properties/listings if not exist
            if (propertyRepo.count() == 0) {
                User defaultUser = userService.findUserByEmail("realtor@realtor.com");
                if (defaultUser == null) {
                    defaultUser = userService.findUserByEmail("user@user.com");
                }
                if (defaultUser == null) {
                    defaultUser = userService.findUserByEmail("admin@admin.com");
                }

                if (defaultUser != null) {
                    // Seed 13 properties
                    // HCM Properties
                    saveProperty(propertyRepo, defaultUser, "Căn Hộ Penthouse Cao Cấp Landmark 81 - View Sông Sài Gòn", 15000000000.0,
                            "Căn hộ", "sell", "Full cao cấp", "Sổ hồng riêng",
                            "Căn hộ Penthouse đẳng cấp bậc nhất Sài Gòn tại tháp Landmark 81. Thiết kế sang trọng, view sông Sài Gòn toàn cảnh từ tầng cao, đầy đủ nội thất nhập khẩu từ Châu Âu.",
                            1, 3, 3, 180.0, 1, 10.7948, 106.7218,
                            "Hồ Chí Minh", "Bình Thạnh", "Phường 22", "208 Nguyễn Hữu Cảnh", "Tháp Landmark 81, Vinhomes Central Park, Phường 22, Bình Thạnh, Hồ Chí Minh",
                            "02/07/2026", "02/08/2026", "VIP Kim Cương", 200000.0,
                            "https://images.unsplash.com/photo-1512917774080-9991f1c4c750?auto=format&fit=crop&w=800&q=80");

                    saveProperty(propertyRepo, defaultUser, "Biệt Thự Sân Vườn Hiện Đại Thảo Điền Quận 2", 45000000000.0,
                            "Biệt thự", "sell", "Nội thất gỗ tự nhiên", "Sổ đỏ chính chủ",
                            "Biệt thự sân vườn biệt lập tại Thảo Điền, Quận 2. Diện tích khuôn viên rộng rãi có hồ bơi riêng, khu BBQ ngoài trời và thiết kế không gian xanh thoáng mát.",
                            2, 4, 4, 350.0, 2, 10.8032, 106.7329,
                            "Hồ Chí Minh", "Quận 2", "Thảo Điền", "Xuân Thủy", "Khu biệt thự Thảo Điền, Xuân Thủy, Quận 2, Hồ Chí Minh",
                            "02/07/2026", "02/08/2026", "VIP Vàng", 100000.0,
                            "https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?auto=format&fit=crop&w=800&q=80");

                    saveProperty(propertyRepo, defaultUser, "Căn Hộ Vinhomes Grand Park 2 Phòng Ngủ Giá Tốt", 12000000.0,
                            "Căn hộ", "rent", "Cơ bản đầy đủ", "Hợp đồng thuê 1 năm",
                            "Cho thuê căn hộ Vinhomes Grand Park Quận 9, 2 phòng ngủ 2 WC, tầng trung view công viên 36ha thoáng mát. Đầy đủ tiện ích hồ bơi, phòng gym miễn phí.",
                            1, 2, 2, 69.5, 3, 10.8385, 106.8402,
                            "Hồ Chí Minh", "Quận 9", "Long Thạnh Mỹ", "Nguyễn Xiển", "Khu đô thị Vinhomes Grand Park, Nguyễn Xiển, Long Thạnh Mỹ, Quận 9, Hồ Chí Minh",
                            "02/07/2026", "02/08/2026", "VIP Bạc", 40000.0,
                            "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?auto=format&fit=crop&w=800&q=80");

                    saveProperty(propertyRepo, defaultUser, "Phòng trọ cao cấp gác lửng Quận 7 gần ĐH Tôn Đức Thắng", 4500000.0,
                            "Nhà trọ", "rent", "Đầy đủ", "Hợp đồng 6 tháng",
                            "Cho thuê phòng trọ cao cấp mới xây, có gác lửng, máy lạnh, tủ quần áo, giờ giấc tự do, không chung chủ. Gần trường Đại học Tôn Đức Thắng, đại học RMIT.",
                            1, 1, 1, 30.0, 8, 10.7324, 106.6978,
                            "Hồ Chí Minh", "Quận 7", "Tân Phong", "Lê Văn Lương", "Đường Lê Văn Lương, Phường Tân Phong, Quận 7, Hồ Chí Minh",
                            "02/07/2026", "02/08/2026", "VIP Bạc", 40000.0,
                            "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?auto=format&fit=crop&w=800&q=80");

                    // Hanoi Properties
                    saveProperty(propertyRepo, defaultUser, "Phòng trọ khép kín Cầu Giấy giá rẻ, gần ĐHQG Hà Nội", 3200000.0,
                            "Nhà trọ", "rent", "Cơ bản", "Không chung chủ",
                            "Cho thuê phòng trọ khép kín tại Cầu Giấy, diện tích 25m2, đầy đủ giường tủ, bình nóng lạnh. Vị trí trung tâm gần nhiều trường đại học lớn, chợ và điểm đón xe bus.",
                            1, 1, 1, 25.0, 4, 21.0362, 105.7905,
                            "Hà Nội", "Cầu Giấy", "Dịch Vọng", "Trần Thái Tông", "Đường Trần Thái Tông, Phường Dịch Vọng, Cầu Giấy, Hà Nội",
                            "02/07/2026", "02/08/2026", "Thành viên", 0.0,
                            "https://images.unsplash.com/photo-1598928506311-c55ded91a20c?auto=format&fit=crop&w=800&q=80");

                    saveProperty(propertyRepo, defaultUser, "Căn Hộ Vinhomes Metropolis Liễu Giai 2 PN Full Nội Thất", 22000000.0,
                            "Căn hộ", "rent", "Full cao cấp", "Hợp đồng thuê 1 năm",
                            "Cho thuê căn hộ cao cấp Vinhomes Metropolis Liễu Giai, tầng cao view đẹp thành phố, 2 phòng ngủ 2 nhà vệ sinh. Trang bị đầy đủ nội thất sang trọng, hiện đại.",
                            1, 2, 2, 78.0, 5, 21.0319, 105.8136,
                            "Hà Nội", "Ba Đình", "Ngọc Khánh", "Liễu Giai", "Tòa M1 Vinhomes Metropolis, 29 Liễu Giai, Ngọc Khánh, Ba Đình, Hà Nội",
                            "02/07/2026", "02/08/2026", "VIP Kim Cương", 200000.0,
                            "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?auto=format&fit=crop&w=800&q=80");

                    saveProperty(propertyRepo, defaultUser, "Đất nền đấu giá Sóc Sơn Hà Nội - Sổ đỏ chính chủ", 2500000000.0,
                            "Đất nền", "sell", "Không", "Sổ đỏ chính chủ",
                            "Cần bán lô đất nền đấu giá tại Sóc Sơn, Hà Nội. Diện tích 100m2 vuông vắn, đường trước đất rộng 6m ô tô tránh nhau thoải mái, phù hợp xây dựng nhà ở hoặc đầu tư.",
                            1, 0, 0, 100.0, 6, 21.2583, 105.8119,
                            "Hà Nội", "Sóc Sơn", "Sóc Sơn", "QL3", "Khu đất đấu giá huyện Sóc Sơn, Quốc lộ 3, Sóc Sơn, Hà Nội",
                            "02/07/2026", "02/08/2026", "VIP Vàng", 100000.0,
                            "https://images.unsplash.com/photo-1500382017468-9049fed747ef?auto=format&fit=crop&w=800&q=80");

                    saveProperty(propertyRepo, defaultUser, "Cho thuê nhà riêng nguyên căn ngõ Thái Hà làm Văn phòng", 16000000.0,
                            "Nhà riêng", "rent", "Cơ bản", "Sổ đỏ riêng",
                            "Cho thuê nhà riêng nguyên căn tại ngõ Thái Hà, Quận Đống Đa. Diện tích 65m2 x 3 tầng, mặt tiền rộng thoáng mát, ngõ rộng ô tô đỗ cửa, thích hợp làm văn phòng công ty.",
                            3, 3, 2, 65.0, 7, 21.0116, 105.8197,
                            "Hà Nội", "Đống Đa", "Trung Liệt", "Thái Hà", "Ngõ Thái Hà, Phường Trung Liệt, Đống Đa, Hà Nội",
                            "02/07/2026", "02/08/2026", "VIP Bạc", 40000.0,
                            "https://images.unsplash.com/photo-1580587771525-78b9dba3b914?auto=format&fit=crop&w=800&q=80");

                    // Danang Properties
                    saveProperty(propertyRepo, defaultUser, "Căn Hộ Cao Cấp Azura Đà Nẵng View Sông Hàn Tuyệt Đẹp", 18000000.0,
                            "Căn hộ", "rent", "Full nội thất hiện đại", "Hợp đồng dài hạn",
                            "Cho thuê căn hộ cao cấp Azura Sơn Trà Đà Nẵng. Căn hộ tầng cao, sở hữu tầm nhìn trực diện ra sông Hàn và cầu Rồng thơ mộng, đầy đủ dịch vụ tiện ích hồ bơi, bảo vệ 24/7.",
                            1, 2, 2, 85.0, 9, 16.0718, 108.2285,
                            "Đà Nẵng", "Sơn Trà", "An Hải Bắc", "Trần Hưng Đạo", "Tòa nhà Azura, Trần Hưng Đạo, An Hải Bắc, Sơn Trà, Đà Nẵng",
                            "02/07/2026", "02/08/2026", "VIP Kim Cương", 200000.0,
                            "https://images.unsplash.com/photo-1545324418-cc1a3fa10c00?auto=format&fit=crop&w=800&q=80");

                    saveProperty(propertyRepo, defaultUser, "Đất nền dự án Nam Hòa Xuân Đà Nẵng vị trí đắc địa", 3200000000.0,
                            "Đất nền", "sell", "Không", "Sổ đỏ riêng",
                            "Bán lô đất nền dự án Khu đô thị sinh thái Nam Hòa Xuân, Ngũ Hành Sơn. Diện tích 105m2, vị trí block sạch đẹp sát sông, không vướng hố gas hay bốt điện, giao thông thuận tiện.",
                            1, 0, 0, 105.0, 10, 16.0092, 108.2394,
                            "Đà Nẵng", "Ngũ Hành Sơn", "Hòa Quý", "Nguyễn Phước Lan", "Khu đô thị Nam Hòa Xuân, Nguyễn Phước Lan, Hòa Quý, Ngũ Hành Sơn, Đà Nẵng",
                            "02/07/2026", "02/08/2026", "VIP Vàng", 100000.0,
                            "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?auto=format&fit=crop&w=800&q=80");

                    // Nghe An Properties
                    saveProperty(propertyRepo, defaultUser, "Đất đấu giá Nghi Phong, Nghi Lộc diện tích rộng", 1900000000.0,
                            "Đất nền", "sell", "Không", "Đã có sổ hồng",
                            "Bán đất đấu giá xã Nghi Phong, huyện Nghi Lộc, Nghệ An. Mặt tiền rộng 8m, nằm trong vùng quy hoạch phát triển đại lộ Vinh - Cửa Lò, tiềm năng tăng giá cực lớn.",
                            1, 0, 0, 150.0, 11, 18.7056, 105.7483,
                            "Nghệ An", "Nghi Lộc", "Nghi Phong", "Đường 72m", "Khu đấu giá Nghi Phong, Nghi Lộc, Nghệ An",
                            "02/07/2026", "02/08/2026", "VIP Bạc", 40000.0,
                            "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?auto=format&fit=crop&w=800&q=80");

                    // Hai Phong Properties
                    saveProperty(propertyRepo, defaultUser, "Cho thuê nhà riêng 3 tầng Lê Hồng Phong Ngô Quyền", 14000000.0,
                            "Nhà riêng", "rent", "Cơ bản", "Sổ hồng",
                            "Cho thuê nhà riêng 3 tầng tại mặt ngõ Lê Hồng Phong, Ngô Quyền, Hải Phòng. Thiết kế hiện đại rộng rãi, phù hợp vừa ở vừa làm văn phòng giao dịch.",
                            3, 3, 3, 90.0, 12, 20.8582, 106.7029,
                            "Hải Phòng", "Ngô Quyền", "Đông Khê", "Lê Hồng Phong", "Đường Lê Hồng Phong, Đông Khê, Ngô Quyền, Hải Phòng",
                            "02/07/2026", "02/08/2026", "VIP Bạc", 40000.0,
                            "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?auto=format&fit=crop&w=800&q=80");

                    // Can Tho Properties
                    saveProperty(propertyRepo, defaultUser, "Nhà trọ sinh viên giá rẻ gần Đại học Cần Thơ", 1800000.0,
                            "Nhà trọ", "rent", "Cơ bản", "Không chung chủ",
                            "Cho thuê nhà trọ diện tích 20m2 gần trường Đại học Cần Thơ. Phòng sạch sẽ thoáng mát, có toilet riêng, camera an ninh giám sát 24/24, giá cả phù hợp cho sinh viên học tập.",
                            1, 1, 1, 20.0, 13, 10.0336, 105.7601,
                            "Cần Thơ", "Ninh Kiều", "Xuân Khánh", "3 tháng 2", "Đường 3 Tháng 2, Xuân Khánh, Ninh Kiều, Cần Thơ",
                            "02/07/2026", "02/08/2026", "Thành viên", 0.0,
                            "https://images.unsplash.com/photo-1554995207-c18c203602cb?auto=format&fit=crop&w=800&q=80");

                    System.out.println("Real estate seed data successfully created!");
                }
            }
        };
    }

    private void saveProperty(PropertyRepo propertyRepo, User user, String title, double price,
                              String type, String transaction, String interior, String legal,
                              String description, int floor, int bedrooms, int bathrooms,
                              double squareMeters, int priority, double lat, double lng,
                              String province, String district, String ward, String street, String fullAddress,
                              String datePost, String dateEnd, String typePost, double payment, String imageUrl) {
        Properties p = new Properties();
        p.setPropertyTitle(title);
        p.setPropertyPrice(price);
        p.setPropertyType(type);
        p.setPropertyTypeTransaction(transaction);
        p.setAvailable(true);
        p.setPropertyInterior(interior);
        p.setPropertyLegal(legal);
        p.setPropertyDescription(description);
        p.setPropertyFloor(floor);
        p.setPropertyStatus("Đã duyệt");
        p.setBedrooms(bedrooms);
        p.setBathrooms(bathrooms);
        p.setSquareMeters(squareMeters);
        p.setPropertyPriority(priority);
        p.setPropertyLatitude(lat);
        p.setPropertyLongitude(lng);
        p.setUser(user);

        Address a = new Address.AddressBuilder()
                .province(province)
                .district(district)
                .ward(ward)
                .street(street)
                .fullAddress(fullAddress)
                .properties(p)
                .build();
        p.setAddress(a);

        PostInformation pi = new PostInformation.Builder()
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .datePost(datePost)
                .dateEnd(dateEnd)
                .typePost(typePost)
                .daysRemaining(30)
                .payment(payment)
                .payPerDay(payment / 30.0)
                .properties(p)
                .build();
        p.setPostInformation(pi);

        Images img = new Images();
        img.setImageUrl(imageUrl);
        img.setProperty(p);
        p.setListImages(Collections.singletonList(img));

        propertyRepo.save(p);
    }
}
