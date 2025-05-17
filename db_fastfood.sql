-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: db_fastfood
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK9emlp6m95v5er2bcqkjsw48he` (`user_id`),
  CONSTRAINT `FKl70asp4l4w0jmbm1tqyofho4o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (1,5),(2,16);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `cart_id` bigint NOT NULL,
  `menu_item_id` bigint NOT NULL,
  KEY `FK2remxfpj89unkv6v37cufmmed` (`menu_item_id`),
  KEY `FK99e0am9jpriwxcm6is7xfedy3` (`cart_id`),
  CONSTRAINT `FK2remxfpj89unkv6v37cufmmed` FOREIGN KEY (`menu_item_id`) REFERENCES `menu_item` (`id`),
  CONSTRAINT `FK99e0am9jpriwxcm6is7xfedy3` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
INSERT INTO `cart_items` VALUES (2,1),(2,2),(1,1),(1,4);
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `img_category` varchar(255) DEFAULT NULL,
  `type` enum('burger','chicken','combo','dessert','drink','fries','pizza','salad','sandwich') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'https://res.cloudinary.com/dzzzjkbj3/image/upload/v1746984587/combo_ootdam.png','combo'),(2,'https://res.cloudinary.com/dzzzjkbj3/image/upload/v1746984587/burger_sfi609.png','burger'),(3,'https://res.cloudinary.com/dzzzjkbj3/image/upload/v1746984587/pizza_uomfkq.png','pizza'),(4,'https://res.cloudinary.com/dzzzjkbj3/image/upload/v1746984587/drink_irm5qs.png','drink'),(5,'https://res.cloudinary.com/dzzzjkbj3/image/upload/v1746984587/dessert_hyvpic.png','dessert'),(6,'https://res.cloudinary.com/dzzzjkbj3/image/upload/v1746984587/salad_tndvyw.png','salad'),(7,'https://res.cloudinary.com/dzzzjkbj3/image/upload/v1747288425/sandwich_rtnmes.png','sandwich'),(8,'https://res.cloudinary.com/dzzzjkbj3/image/upload/v1746984586/chicken_kzpfo7.png','chicken'),(9,'https://res.cloudinary.com/dzzzjkbj3/image/upload/v1746984587/fries_scpqiw.png','fries');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon` (
  `discount_value` double NOT NULL,
  `expiry_date` date DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `discount_type` enum('fixed','percentage') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite_item`
--

DROP TABLE IF EXISTS `favorite_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorite_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_added` date DEFAULT NULL,
  `menu_item_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd81xkrxyv0us42ta7f8h8d044` (`menu_item_id`),
  KEY `FKmrknsxprgr0pve4aj5mo0if8v` (`user_id`),
  CONSTRAINT `FKd81xkrxyv0us42ta7f8h8d044` FOREIGN KEY (`menu_item_id`) REFERENCES `menu_item` (`id`),
  CONSTRAINT `FKmrknsxprgr0pve4aj5mo0if8v` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite_item`
--

LOCK TABLES `favorite_item` WRITE;
/*!40000 ALTER TABLE `favorite_item` DISABLE KEYS */;
INSERT INTO `favorite_item` VALUES (29,'2025-05-11',1,16),(30,'2025-05-11',2,16),(31,'2025-05-11',3,16),(34,'2025-05-12',2,5),(35,'2025-05-12',3,5),(37,'2025-05-15',4,5),(39,'2025-05-15',7,5),(41,'2025-05-16',1,5);
/*!40000 ALTER TABLE `favorite_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu_item`
--

DROP TABLE IF EXISTS `menu_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu_item` (
  `create_date` date DEFAULT NULL,
  `price` double NOT NULL,
  `sold_quantity` int NOT NULL,
  `category_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `img_menu_item` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfmgn0386uxf8sqtilegkufxk0` (`category_id`),
  CONSTRAINT `FKfmgn0386uxf8sqtilegkufxk0` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_item`
--

LOCK TABLES `menu_item` WRITE;
/*!40000 ALTER TABLE `menu_item` DISABLE KEYS */;
INSERT INTO `menu_item` VALUES ('2025-04-14',120000,152,1,1,'Combo nhỏ gồm burger và nước ngọt.','https://burgerking.vn/media/catalog/product/cache/1/small_image/1000x/9df78eab33525d08d6e5fb8d27136e95/s/h/sharing_combo_159.jpg','Mini Combo'),('2025-04-14',150000,122,1,2,'Combo vừa đủ gồm burger, khoai tây chiên và nước.','https://burgerking.vn/media/catalog/product/cache/1/small_image/1000x/9df78eab33525d08d6e5fb8d27136e95/s/h/sharing_combo_199.jpg','Combo Vừa'),('2025-04-14',180000,95,1,3,'Combo lớn cho 2 người.','https://burgerking.vn/media/catalog/product/cache/1/small_image/1000x/9df78eab33525d08d6e5fb8d27136e95/s/h/sharing_combo-03.jpg','Combo Lớn'),('2025-04-14',30000,200,2,4,'Burger bò cổ điển.','https://api.photon.aremedia.net.au/wp-content/uploads/sites/11/food/2014/11/28/AustralianWomensWeeklyBR116498/classic-beef-burger.jpg?fit=2048%2C1536','Classic Beef Burger'),('2025-04-14',30000,180,2,5,'Burger gà chiên giòn.','https://www.scott.pizzanonstop.ca/wp-content/uploads/2022/05/main-header.jpg','Crispy Chicken Burger'),('2025-04-14',40000,140,2,6,'Burger phô mai tan chảy.','https://cdn.sanity.io/images/czqk28jt/staging_bk_za/592fee1733947fe6acec651084634bb9c4932496-6000x3000.jpg','Cheese Lover Burger'),('2025-04-14',80000,111,3,7,'Pizza hải sản phô mai.','https://seafoodexperts.com.au/wp-content/uploads/2016/02/SR-063-Seafood-lovers-pizza-CLAN-AI-1.jpg','Seafood Pizza'),('2025-04-14',60000,130,3,8,'Pizza xúc xích truyền thống.','https://www.allrecipes.com/thmb/uL-LvW7FACZtEW_UkXfhPVp0b0c=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/8229081-b552c32d02054be8b41941dc5fb1bb10.jpg','Sausage Pizza'),('2025-04-14',60000,125,3,9,'Pizza rau củ chay.','https://www.allrecipes.com/thmb/gKD4hlJ26TYEvBTrYzX2SiF95Io=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/AR-15022-veggie-pizza-DDMFS-4x3-hero-3dabf0783ef544eeaa23bdf28b48b178.jpg','Vegetarian Pizza'),('2025-04-14',20000,300,4,10,'Nước ngọt có ga.','https://w7.pngwing.com/pngs/639/352/png-transparent-coca-cola-fizzy-drinks-diet-coke-sprite-coke-cola-paper-cup-carbonated-soft-drinks.png','Coca-Cola'),('2025-04-14',20000,290,4,11,'Nước cam tươi mát.','https://st2.depositphotos.com/1177973/6257/i/450/depositphotos_62571079-stock-photo-orange-juice-in-fast-food.jpg','Orange Juice'),('2025-04-14',20000,270,4,12,'Sữa tươi có đường.','https://mcdonalds.vn/uploads/2018/food/beverage/milk_300x300.png','Fresh Milk'),('2025-04-14',3.49,170,5,13,'Bánh flan caramel.','https://www.thespruceeats.com/thmb/h9hTzwViqJIVYDuWQzc8xi7cB48=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/caramel-flan-individual-servings-2343004-hero-01-44e1e9d1ab954ef990505d69e19e856c.jpg','Caramel Flan'),('2025-04-14',2.99,160,5,14,'Pudding socola.','https://www.foodandwine.com/thmb/RYsqVqa6snK1_WkmZm7m-h6rwfM=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/FAW-recipes-double-chocolate-pudding-hero-f8cf686f09504feb8b155c4c2c459838.jpg','Chocolate Pudding'),('2025-04-14',4.29,155,5,15,'Bánh cheesecake dâu.','https://www.amummytoo.co.uk/wp-content/uploads/2022/09/strawberry-cheesecake-SQUARE.jpg','Strawberry Cheesecake'),('2025-04-14',2.49,90,6,16,'Salad rau xanh sốt mè.','https://www.allrecipes.com/thmb/XgWHycCFMP4eAvMfKXnX3pzC_DA=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/ALR-14452-green-salad-VAT-hero-4x3-22eb1ac6ccd14e5bacf18841b9672313.jpg','Green Salad'),('2025-04-14',2.99,85,6,17,'Salad gà nướng.','https://www.slenderkitchen.com/sites/default/files/styles/gsd-16x9/public/recipe_images/grilled-chicken-Greek-salad.jpg','Grilled Chicken Salad'),('2025-04-14',3.49,80,6,18,'Salad cá ngừ tươi.','https://img.taste.com.au/xg3XMRpy/taste/2016/11/mediterranean-tuna-salad-31059-1.jpeg','Tuna Salad'),('2025-04-14',3.99,100,7,19,'Sandwich trứng và phô mai.','https://www.seriouseats.com/thmb/FOFHUZtFUfpPnIPQAseHyzbpMfo=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/09122022-GrilledCheeseEggplosionRecipe-AmandaSuarez-Hero-eb123d43626f43f4bdd3bc16d497918b.JPG','Egg Cheese Sandwich'),('2025-04-14',4.49,90,7,20,'Sandwich thịt nguội.','https://www.seriouseats.com/thmb/s1tqfozqvGgOEDO6xe3gzaskncg=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/20240305-SEA-HamandCheese-AmandaSuarez15-fa4cf0500a28452ab2f1575057e5c059.jpg','Ham Sandwich'),('2025-04-14',4.99,95,7,21,'Sandwich gà nướng.','https://www.indianhealthyrecipes.com/wp-content/uploads/2024/07/grilled-chicken-sandwich-recipe.jpg','Grilled Chicken Sandwich'),('2025-04-14',5.99,170,8,22,'Gà rán truyền thống.','https://assets.surlatable.com/m/3a06d31be15bd69f/72_dpi_webp-REC-292887-Fried_Chicken.jpg','Classic Fried Chicken'),('2025-04-14',6.49,160,8,23,'Gà rán cay Hàn Quốc.','https://www.adabi.com/wp-content/uploads/2020/02/Spicy-Korean-Sauce-Fried-Chicken.jpg','Spicy Korean Chicken'),('2025-04-14',7.99,150,8,24,'Gà không xương BBQ.','https://www.missinthekitchen.com/wp-content/uploads/2021/10/BBQ-Boneless-Wings-Recipe-photo.jpeg','Boneless BBQ Chicken'),('2025-04-14',1.99,300,9,25,'Khoai tây chiên nhỏ.','https://m.media-amazon.com/images/I/61rAGH6s0hS.jpg','Small Fries'),('2025-04-14',2.49,280,9,26,'Khoai tây chiên vừa.','https://m.media-amazon.com/images/I/61rAGH6s0hS.jpg','Medium Fries'),('2025-04-14',2.99,260,9,27,'Khoai tây chiên lớn.','https://m.media-amazon.com/images/I/61rAGH6s0hS.jpg','Large Fries');
/*!40000 ALTER TABLE `menu_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_detail` (
  `order_detail_id` bigint NOT NULL,
  `order_address` varchar(255) DEFAULT NULL,
  `order_amount` double NOT NULL,
  `order_time` date DEFAULT NULL,
  `order_total` double NOT NULL,
  `rating` int NOT NULL,
  `review` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user_phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`order_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES (6,'1 Đ. Võ Văn Ngân, Linh Chiểu, Thủ Đức, Hồ Chí Minh, Việt Nam',630000,'2025-05-15',645000,0,'','cancelled','Trọng Lê Kim',NULL),(7,'1 Đ. Võ Văn Ngân, Linh Chiểu, Thủ Đức, Hồ Chí Minh, Việt Nam',630000,'2025-05-15',645000,0,'','cancelled','Trọng Lê Kim',NULL),(8,'1 Đ. Võ Văn Ngân, Linh Chiểu, Thủ Đức, Hồ Chí Minh, Việt Nam',630000,'2025-05-15',645000,0,' ','shipping','Trọng Lê Kim',NULL),(9,'1 Đ. Võ Văn Ngân, Linh Chiểu, Thủ Đức, Hồ Chí Minh, Việt Nam',150000,'2025-05-15',165000,4,' ','delivered','Trọng Lê Kim',NULL),(10,'1 Đ. Võ Văn Ngân, Linh Chiểu, Thủ Đức, Hồ Chí Minh, Việt Nam',7.99,'2025-05-15',15007.99,0,' ','delivered','Trọng Lê Kim','0359618543'),(11,'1 Đ. Võ Văn Ngân, Linh Chiểu, Thủ Đức, Hồ Chí Minh, Việt Nam',120000,'2025-05-15',135000,4,' ','delivered','Trọng Lê Kim','0359618543'),(12,'1 Đ. Võ Văn Ngân, Linh Chiểu, Thủ Đức, Hồ Chí Minh, Việt Nam',240000,'2025-05-15',255000,0,'','cancelled','Trọng Lê Kim','0359618543');
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `quantity` int NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `menu_item_id` bigint DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1my78itket7mhyn3wt4g6j4sq` (`menu_item_id`),
  KEY `FKt4dc2r9nbvbujrljv3e23iibt` (`order_id`),
  CONSTRAINT `FK1my78itket7mhyn3wt4g6j4sq` FOREIGN KEY (`menu_item_id`) REFERENCES `menu_item` (`id`),
  CONSTRAINT `FKt4dc2r9nbvbujrljv3e23iibt` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (4,4,4,4),(5,5,5,5),(2,6,1,1),(1,7,2,1),(3,8,3,1),(2,9,1,2),(1,10,4,2),(1,11,2,3),(2,12,5,3),(3,13,2,6),(1,14,3,6),(3,15,2,7),(1,16,3,7),(3,17,2,8),(1,18,3,8),(1,19,2,9),(1,20,7,10),(1,21,1,11),(2,22,1,12);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item_detail`
--

DROP TABLE IF EXISTS `order_item_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item_detail` (
  `item_id` bigint NOT NULL,
  `item_amount` double NOT NULL,
  `item_img` varchar(255) DEFAULT NULL,
  `item_name` varchar(255) DEFAULT NULL,
  `item_price` double NOT NULL,
  `quantity` int NOT NULL,
  `order_detail_order_detail_id` bigint DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `FK9sdgivlmsbs4c2xwnqnbp1vsm` (`order_detail_order_detail_id`),
  CONSTRAINT `FK9sdgivlmsbs4c2xwnqnbp1vsm` FOREIGN KEY (`order_detail_order_detail_id`) REFERENCES `order_detail` (`order_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item_detail`
--

LOCK TABLES `order_item_detail` WRITE;
/*!40000 ALTER TABLE `order_item_detail` DISABLE KEYS */;
INSERT INTO `order_item_detail` VALUES (13,450000,'https://burgerking.vn/media/catalog/product/cache/1/small_image/1000x/9df78eab33525d08d6e5fb8d27136e95/s/h/sharing_combo_199.jpg','Combo Vừa',150000,3,6),(14,180000,'https://burgerking.vn/media/catalog/product/cache/1/small_image/1000x/9df78eab33525d08d6e5fb8d27136e95/s/h/sharing_combo-03.jpg','Combo Lớn',180000,1,6),(15,450000,'https://burgerking.vn/media/catalog/product/cache/1/small_image/1000x/9df78eab33525d08d6e5fb8d27136e95/s/h/sharing_combo_199.jpg','Combo Vừa',150000,3,7),(16,180000,'https://burgerking.vn/media/catalog/product/cache/1/small_image/1000x/9df78eab33525d08d6e5fb8d27136e95/s/h/sharing_combo-03.jpg','Combo Lớn',180000,1,7),(17,450000,'https://burgerking.vn/media/catalog/product/cache/1/small_image/1000x/9df78eab33525d08d6e5fb8d27136e95/s/h/sharing_combo_199.jpg','Combo Vừa',150000,3,8),(18,180000,'https://burgerking.vn/media/catalog/product/cache/1/small_image/1000x/9df78eab33525d08d6e5fb8d27136e95/s/h/sharing_combo-03.jpg','Combo Lớn',180000,1,8),(19,150000,'https://burgerking.vn/media/catalog/product/cache/1/small_image/1000x/9df78eab33525d08d6e5fb8d27136e95/s/h/sharing_combo_199.jpg','Combo Vừa',150000,1,9),(20,7.99,'https://seafoodexperts.com.au/wp-content/uploads/2016/02/SR-063-Seafood-lovers-pizza-CLAN-AI-1.jpg','Seafood Pizza',7.99,1,10),(21,120000,'https://burgerking.vn/media/catalog/product/cache/1/small_image/1000x/9df78eab33525d08d6e5fb8d27136e95/s/h/sharing_combo_159.jpg','Mini Combo',120000,1,11),(22,240000,'https://burgerking.vn/media/catalog/product/cache/1/small_image/1000x/9df78eab33525d08d6e5fb8d27136e95/s/h/sharing_combo_159.jpg','Mini Combo',120000,2,12);
/*!40000 ALTER TABLE `order_item_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_time` date DEFAULT NULL,
  `order_total` double NOT NULL,
  `rating` int NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `order_address` varchar(255) DEFAULT NULL,
  `review` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKel9kyl84ego2otj2accfd8mr7` (`user_id`),
  CONSTRAINT `FKel9kyl84ego2otj2accfd8mr7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES ('2025-04-19',120000,0,1,5,'45 Nguyễn Huệ, Quận 1, TP.HCM',NULL,'PENDING'),('2025-04-19',89000,0,2,5,'23 Lê Lợi, Quận 3, TP.HCM','','cancelled'),('2025-04-19',175000,0,3,5,'78 Trần Hưng Đạo, Quận 5, TP.HCM','','cancelled'),('2025-04-19',100000,0,4,5,'12 Phạm Ngũ Lão, Quận 1, TP.HCM',NULL,'CANCELLED'),('2025-04-19',210000,0,5,5,'56 Võ Thị Sáu, Quận 3, TP.HCM','','cancelled'),('2025-05-15',645000,0,6,5,'1 Đ. Võ Văn Ngân, Linh Chiểu, Thủ Đức, Hồ Chí Minh, Việt Nam','','cancelled'),('2025-05-15',645000,0,7,5,'1 Đ. Võ Văn Ngân, Linh Chiểu, Thủ Đức, Hồ Chí Minh, Việt Nam','','cancelled'),('2025-05-15',645000,0,8,5,'1 Đ. Võ Văn Ngân, Linh Chiểu, Thủ Đức, Hồ Chí Minh, Việt Nam',' ','shipping'),('2025-05-15',165000,4,9,5,'1 Đ. Võ Văn Ngân, Linh Chiểu, Thủ Đức, Hồ Chí Minh, Việt Nam',' ','delivered'),('2025-05-15',15007.99,0,10,5,'1 Đ. Võ Văn Ngân, Linh Chiểu, Thủ Đức, Hồ Chí Minh, Việt Nam',' ','delivered'),('2025-05-15',135000,4,11,5,'1 Đ. Võ Văn Ngân, Linh Chiểu, Thủ Đức, Hồ Chí Minh, Việt Nam',' ','delivered'),('2025-05-15',255000,0,12,5,'1 Đ. Võ Văn Ngân, Linh Chiểu, Thủ Đức, Hồ Chí Minh, Việt Nam','','cancelled');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `otp_verification`
--

DROP TABLE IF EXISTS `otp_verification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `otp_verification` (
  `verified` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `otp` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `otp_verification`
--

LOCK TABLES `otp_verification` WRITE;
/*!40000 ALTER TABLE `otp_verification` DISABLE KEYS */;
INSERT INTO `otp_verification` VALUES (_binary '','2025-04-13 13:02:20.312607',1,'manhtu.cutu@gmail.com','424665'),(_binary '','2025-04-13 13:08:32.772935',2,'manhtu.cutu@gmail.com','668038'),(_binary '','2025-04-13 13:23:09.162156',3,'manhtu.cutu@gmail.com','415483'),(_binary '','2025-04-13 13:27:54.253439',4,'manhtu.cutu@gmail.com','856737'),(_binary '\0','2025-04-13 17:15:50.871262',5,'manhtu.cutu@gmail.com','137000'),(_binary '\0','2025-04-13 17:31:38.666428',6,'manhtu.cutu@gmail.com','150144'),(_binary '\0','2025-04-13 17:33:54.658043',7,'manhtu.cutu@gmail.com','388044'),(_binary '\0','2025-04-13 17:36:57.484139',8,'manhtu.cutu@gmail.com','233315'),(_binary '','2025-04-13 17:40:12.840673',9,'manhtu.cutu@gmail.com','420355'),(_binary '\0','2025-04-13 20:23:00.152220',10,'manhtu.cutu@gmail.com','659262'),(_binary '\0','2025-04-13 20:23:39.939063',11,'manhtu.cutu@gmail.com','461161'),(_binary '\0','2025-04-13 20:23:41.747169',12,'manhtu.cutu@gmail.com','940956'),(_binary '\0','2025-04-13 20:23:42.522083',13,'manhtu.cutu@gmail.com','937160'),(_binary '','2025-04-13 20:39:16.510910',14,'manhtu.cutu@gmail.com','520109'),(_binary '\0','2025-04-13 20:44:18.949839',15,'manhtu.cutu@gmail.com','801173'),(_binary '','2025-04-13 20:45:26.402665',16,'manhtu.cutu@gmail.com','242412'),(_binary '\0','2025-04-13 20:53:17.428384',17,'manhtu.cutu@gmail.com','329740'),(_binary '\0','2025-04-13 20:56:02.762417',18,'manhtu.cutu@gmail.com','847112'),(_binary '\0','2025-04-13 21:03:52.709069',19,'manhtu.cutu@gmail.com','279630'),(_binary '\0','2025-04-13 21:05:01.648266',20,'manhtu.cutu@gmail.com','653048'),(_binary '\0','2025-04-13 21:06:31.195663',21,'manhtu.cutu@gmail.com','342125'),(_binary '\0','2025-04-13 21:11:37.081320',22,'manhtu.cutu@gmail.com','893093'),(_binary '\0','2025-04-20 16:02:07.438339',23,'manhtu.cutu@gmail.com','711019'),(_binary '\0','2025-04-20 16:03:01.145479',24,'manhtu.cutu@gmail.com','472614'),(_binary '\0','2025-04-20 16:09:27.355720',25,'manhtu.cutu@gmail.com','778935'),(_binary '\0','2025-04-20 16:14:57.751216',26,'raichuvn11@gmail.com','189821'),(_binary '\0','2025-04-20 16:15:00.859082',27,'raichuvn11@gmail.com','587250'),(_binary '\0','2025-04-20 16:16:32.326856',28,'raichuvn11@gmail.com','417102'),(_binary '\0','2025-04-20 16:17:44.701618',29,'raichuvn11@gmail.com','206731'),(_binary '','2025-04-20 16:18:47.251100',30,'raichuvn11@gmail.com','900243'),(_binary '','2025-04-20 16:25:31.491349',31,'raichuvn11@gmail.com','789846'),(_binary '\0','2025-04-20 16:29:02.069806',32,'raichuvn11@gmail.com','562369'),(_binary '','2025-04-20 16:33:59.222004',33,'raichuvn11@gmail.com','334108'),(_binary '','2025-04-20 16:37:19.040646',34,'raichuvn11@gmail.com','757067'),(_binary '\0','2025-04-20 16:40:29.454311',35,'raichuvn11@gmail.com','354417'),(_binary '\0','2025-04-20 16:42:30.305548',36,'raichuvn11@gmail.com','140632'),(_binary '','2025-04-20 16:45:55.380319',37,'raichuvn11@gmail.com','817577'),(_binary '\0','2025-04-26 12:01:14.151118',38,'manhtu@gmail.com','803622'),(_binary '\0','2025-05-08 20:08:58.252589',39,'manhtu.cutu@gmail.com','256190'),(_binary '\0','2025-05-08 20:09:14.224742',40,'manhtu.cutu@gmail.com','620532'),(_binary '','2025-05-12 00:39:43.238017',41,'22110455@student.hcmute.edu.vn','449249'),(_binary '\0','2025-05-12 00:44:24.439139',42,'22110455@student.hcmute.edu.vn','839511'),(_binary '\0','2025-05-12 00:50:46.588723',43,'22110455@student.hcmute.edu.vn','172422'),(_binary '','2025-05-15 12:48:36.344423',44,'22110455@student.hcmute.edu.vn','994980'),(_binary '\0','2025-05-15 12:49:57.908323',45,'22110455@student.hcmute.edu.vn','847709');
/*!40000 ALTER TABLE `otp_verification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `amount` int NOT NULL,
  `created_at` date DEFAULT NULL,
  `transaction_ref` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKmf7n8wo2rwrxsd6f3t9ub2mep` (`order_id`),
  CONSTRAINT `FKlouu98csyullos9k25tbpk4va` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recent_search`
--

DROP TABLE IF EXISTS `recent_search`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recent_search` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `keyword` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recent_search`
--

LOCK TABLES `recent_search` WRITE;
/*!40000 ALTER TABLE `recent_search` DISABLE KEYS */;
INSERT INTO `recent_search` VALUES (4,'2025-04-24 13:22:21.538481','burger',-1),(5,'2025-04-24 13:22:21.538481','burger',-1),(6,'2025-05-11 23:47:42.798861','chicken',16);
/*!40000 ALTER TABLE `recent_search` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `device_token` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (5,'1 Đ. Võ Văn Ngân, Linh Chiểu, Thủ Đức, Hồ Chí Minh, Việt Nam','uploads/avatar/e5131fbe-3cfb-478a-9d6a-b9a242c48986_avatar_1747300958692.jpg','d_rxhPt4T1OLvcCHgR9u3Q:APA91bFXkQI3KBlMivupO3Mgym3IFcGttBzhr4j07ENFYbdC2_KquoEHUIJaRmfoUkpIsD4E41c-UAipTS9Eia1hPXsgcjrqNptbddVS7i-WbVgrvOE3zJk','raichuvn123@gmail.com',NULL,'0359618543','Trọng Lê Kim'),(16,'37J3+H93, Hoà Trị, Phú Hòa, Phú Yên, Việt Nam','https://lh3.googleusercontent.com/a/ACg8ocKIUK2Ct5EIEh-yUvzdjVHKhAz-PXmrKbpuX1JLOIk0i5P2pmI=s96-c','dZTSWCZZSiG_a7Ao9lPsFp:APA91bEhOl6dv7oUeSR9AITkq0mSiAMWnMRT0JhU99rAnjaZR0Nm7wpA4e3DxFAT0vsXbFw3nSi8oMoV-7SnYlYLh0_KaTM56vdDXeBMbj011uEDWuBB1f8','manhtu.cutu@gmail.com','q/PQBDBn9+Erwe9/dAeGQcN67Q/e5qHgdnEwOwd+unY=',NULL,'nguyễn mạnh tú'),(18,'201/10 Đ. Lê Văn Việt, khu phố 5, Thủ Đức, Hồ Chí Minh, Việt Nam',NULL,'d_rxhPt4T1OLvcCHgR9u3Q:APA91bFXkQI3KBlMivupO3Mgym3IFcGttBzhr4j07ENFYbdC2_KquoEHUIJaRmfoUkpIsD4E41c-UAipTS9Eia1hPXsgcjrqNptbddVS7i-WbVgrvOE3zJk','22110455@student.hcmute.edu.vn','JWoHgkSytRRfQlOjFdJwqHdmu0csDN3P4eL4AhD617U=',NULL,'Nguyễn Mạnh Tú');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-16 12:04:41
