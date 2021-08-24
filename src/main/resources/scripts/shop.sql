/*
 Navicat Premium Data Transfer

 Source Server         : LocalDB
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : shop

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 24/08/2021 23:59:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE `shop`;

-- ----------------------------
-- Table structure for authorities
-- ----------------------------
DROP TABLE IF EXISTS `shop`.`authorities`;
CREATE TABLE `shop`.`authorities`  (
                                       `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                       PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of authorities
-- ----------------------------
INSERT INTO `shop`.`authorities` VALUES ('ROLE_ADMIN');
INSERT INTO `shop`.`authorities` VALUES ('ROLE_ANONYMOUS');
INSERT INTO `shop`.`authorities` VALUES ('ROLE_USER');

-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS `shop`.`categories`;
CREATE TABLE `shop`.`categories`  (
                                      `id` bigint NOT NULL AUTO_INCREMENT,
                                      `create_date` datetime(6) NOT NULL,
                                      `enabled` bit(1) NOT NULL,
                                      `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                      PRIMARY KEY (`id`) USING BTREE,
                                      UNIQUE INDEX `UK_tkwloef0k6ccv94cipgnmvma8`(`title`) USING BTREE,
                                      INDEX `IDXtkwloef0k6ccv94cipgnmvma8`(`title`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of categories
-- ----------------------------

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `shop`.`products`;
CREATE TABLE `shop`.`products`  (
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `comments_count` bigint NOT NULL,
                                    `create_date` datetime(6) NOT NULL,
                                    `enabled` bit(1) NOT NULL,
                                    `price` double NOT NULL,
                                    `rate` float NOT NULL,
                                    `rates_count` bigint NOT NULL,
                                    `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                    `category_id` bigint NOT NULL,
                                    PRIMARY KEY (`id`) USING BTREE,
                                    INDEX `IDX8xtpej5iy2w4cte2trlvrlayy`(`title`) USING BTREE,
                                    INDEX `IDXof5oeawsy50x878ic9tyapdnv`(`category_id`) USING BTREE,
                                    CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of products
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `shop`.`users`;
CREATE TABLE `shop`.`users`  (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `activated` bit(1) NOT NULL,
                                 `activation_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                 `blocked` bit(1) NOT NULL,
                                 `create_date` datetime(6) NOT NULL,
                                 `email` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                 `password_hash` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                 `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                 PRIMARY KEY (`id`) USING BTREE,
                                 UNIQUE INDEX `UK_6dotkott2kjsp8vw4d0m25fb7`(`email`) USING BTREE,
                                 UNIQUE INDEX `UK_r43af9ap4edm43mmtq01oddj6`(`username`) USING BTREE,
                                 INDEX `IDXr43af9ap4edm43mmtq01oddj6`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `shop`.`users` VALUES (1, b'1', NULL, b'0', '2021-08-23 20:18:15.416000', 'admin@domain.com', '$2a$10$Xb2C3rUAzajSS/tWLkOw2.TqckXm3l3edcbJd0D6vQ9yi75NpDqOO', 'admin');

-- ----------------------------
-- Table structure for user_authorities
-- ----------------------------
DROP TABLE IF EXISTS `shop`.`user_authorities`;
CREATE TABLE `shop`.`user_authorities`  (
                                            `user_id` bigint NOT NULL,
                                            `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                            PRIMARY KEY (`user_id`, `role_name`) USING BTREE,
                                            INDEX `FKf0c6pauya11km9naxnnvm63ti`(`role_name`) USING BTREE,
                                            CONSTRAINT `FKf0c6pauya11km9naxnnvm63ti` FOREIGN KEY (`role_name`) REFERENCES `authorities` (`name`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                            CONSTRAINT `FKhiiib540jf74gksgb87oofni` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_authorities
-- ----------------------------
INSERT INTO `shop`.`user_authorities` VALUES (1, 'ROLE_ADMIN');
INSERT INTO `shop`.`user_authorities` VALUES (1, 'ROLE_USER');

-- ----------------------------
-- Table structure for profiles
-- ----------------------------
DROP TABLE IF EXISTS `shop`.`profiles`;
CREATE TABLE `shop`.`profiles`  (
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `dob` date NULL DEFAULT NULL,
                                    `first_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                    `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                    `last_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                    `phone_number` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                    `user_id` bigint NOT NULL,
                                    PRIMARY KEY (`id`) USING BTREE,
                                    UNIQUE INDEX `UK_4ixsj6aqve5pxrbw2u0oyk8bb`(`user_id`) USING BTREE,
                                    UNIQUE INDEX `UK_bpki6nygo3ndvjpojbam4iamf`(`phone_number`) USING BTREE,
                                    INDEX `IDX4ixsj6aqve5pxrbw2u0oyk8bb`(`user_id`) USING BTREE,
                                    CONSTRAINT `FK410q61iev7klncmpqfuo85ivh` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of profiles
-- ----------------------------

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `shop`.`comments`;
CREATE TABLE `shop`.`comments`  (
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `create_date` datetime(6) NOT NULL,
                                    `message` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                    `product_id` bigint NOT NULL,
                                    `user_id` bigint NOT NULL,
                                    PRIMARY KEY (`id`) USING BTREE,
                                    INDEX `IDX1x3vdhb5vv8eu5708riqe07wc`(`user_id`) USING BTREE,
                                    INDEX `IDX9dgbf2frihdis1se0j19ujgdx`(`product_id`) USING BTREE,
                                    CONSTRAINT `FK6uv0qku8gsu6x1r2jkrtqwjtn` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                    CONSTRAINT `FK8omq0tc18jd43bu5tjh6jvraq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comments
-- ----------------------------

-- ----------------------------
-- Table structure for rates
-- ----------------------------
DROP TABLE IF EXISTS `shop`.`rates`;
CREATE TABLE `shop`.`rates`  (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `create_date` datetime(6) NOT NULL,
                                 `point` int NOT NULL,
                                 `product_id` bigint NOT NULL,
                                 `user_id` bigint NOT NULL,
                                 PRIMARY KEY (`id`) USING BTREE,
                                 INDEX `IDXdllgmiddwd2cneqre3w857gv7`(`user_id`) USING BTREE,
                                 INDEX `IDXb7u9orl3auvn8rxpho4pbeoxv`(`product_id`) USING BTREE,
                                 CONSTRAINT `FK4mdsmkrr7od84tpgxto2v3t2e` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                 CONSTRAINT `FKanlgavwqngljux10mtly8qr6f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rates
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
