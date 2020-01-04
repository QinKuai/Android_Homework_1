/*
Navicat MySQL Data Transfer

Source Server         : bendi
Source Server Version : 80017
Source Host           : localhost:3306
Source Database       : elearndemo

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2020-01-04 22:19:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `course`
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` varchar(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `code` varchar(50) NOT NULL,
  `category_id` varchar(20) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `price` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `open_date` date DEFAULT NULL,
  `last_update_on` datetime DEFAULT NULL,
  `level` varchar(20) DEFAULT NULL,
  `shared` varchar(20) DEFAULT NULL,
  `shared_url` varchar(50) DEFAULT NULL,
  `avatar` varchar(100) DEFAULT NULL,
  `big_avatar` varchar(100) DEFAULT NULL,
  `certification` varchar(50) DEFAULT NULL,
  `certification_duration` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('001', 'C language Development', '2019SSE_001', 'ICT_SW_001', 'A course for basic C language', '0', 'Open', '2019-09-01', '2019-11-25 09:01:20', 'basic', '0', null, '001\\av001.jpg', null, 'BJTU', 'Forever');
INSERT INTO `course` VALUES ('002', 'Web Development', '2019SSE_002', 'ICT_SW_002', 'HTML, CSS, JS and basic Web Server technologies.', '0', 'Open', '2020-01-02', '2019-11-26 10:11:42', 'basic', '0', null, '002\\av002.jpg', null, 'BJTU SSE', 'Forever');
INSERT INTO `course` VALUES ('003', 'Python Language', '2019SSE_003', 'ICT_SW_003', 'A course for basic Python language', null, null, '2019-09-06', null, null, null, null, '003\\av003.jpg', null, null, null);
INSERT INTO `course` VALUES ('004', 'Java language Development', '2019SSE_004', 'ICT_SW_004', 'A course for basic JAVA language', null, null, '2019-09-06', null, null, null, null, '004\\av004.jpg', null, null, null);
INSERT INTO `course` VALUES ('005', 'MATLAB Development', '2019SSE_005', 'ICT_SW_005', 'A course for basic MATLAB', null, null, '2019-09-06', null, null, null, null, '005\\av005.jpg', null, null, null);

-- ----------------------------
-- Table structure for `elearn_user`
-- ----------------------------
DROP TABLE IF EXISTS `elearn_user`;
CREATE TABLE `elearn_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `headerurl` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `type` varchar(1) NOT NULL,
  `openid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of elearn_user
-- ----------------------------
INSERT INTO `elearn_user` VALUES ('1', 'QinKuai', '123456', 'header/1.jpg', 'n', null);
INSERT INTO `elearn_user` VALUES ('2', 'XJ', '123456', 'header/2.jpg', 'n', null);
INSERT INTO `elearn_user` VALUES ('3', 'QinKuai100', '123456', 'header/3.jpg', 'n', null);
INSERT INTO `elearn_user` VALUES ('4', 'QinKuai1000', '123456', 'header/1.jpg', 'n', null);
INSERT INTO `elearn_user` VALUES ('5', 'QinKuai10000', 'ab123456', 'header/1.jpg', 'n', null);
INSERT INTO `elearn_user` VALUES ('6', 'QinKuai100000', 'ab12', 'header/1.jpg', 'n', null);
INSERT INTO `elearn_user` VALUES ('12', 'QinKuaitemp13467', '123456', 'http://thirdqq.qlogo.cn/g?b=oidb&k=BCoWiax1boiamrw58al3RwlQ&s=100&t=1553175274', 'q', '8D1AB3C355530E5697EA8A42DBEC69AD');
INSERT INTO `elearn_user` VALUES ('13', '123456', '123456', 'header/1.jpg', 'n', null);
INSERT INTO `elearn_user` VALUES ('14', '1234567', '123456', 'header/1.jpg', 'n', null);

-- ----------------------------
-- Table structure for `material`
-- ----------------------------
DROP TABLE IF EXISTS `material`;
CREATE TABLE `material` (
  `id` varchar(20) NOT NULL,
  `course_id` varchar(20) NOT NULL,
  `mediatype` varchar(20) NOT NULL,
  `material_type` varchar(50) DEFAULT NULL,
  `material_url` varchar(100) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of material
-- ----------------------------
INSERT INTO `material` VALUES ('1', '001', 'video', 'lecture video', '001\\001video.mp4', '2019-12-04 22:06:49', null, '1');
INSERT INTO `material` VALUES ('2', '001', 'image', 'image', '001\\001video.jpg', '2019-12-04 22:07:49', null, '1');
INSERT INTO `material` VALUES ('3', '001', 'audio', 'lecture audio', '001\\001audio.mp3', '2019-12-04 22:08:26', null, '1');
INSERT INTO `material` VALUES ('4', '001', 'pdf', 'slide pdf', '001\\001pdf.pdf', '2019-12-04 22:08:57', null, '1');

-- ----------------------------
-- Table structure for `teacher`
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `userid` varchar(20) NOT NULL,
  `course_id` varchar(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('1', '001', 'Wang Hong', '001\\001teacher.jpg', '01051690273', 'wang@bjtu.edu', 'android teacher');
INSERT INTO `teacher` VALUES ('2', '002', '张亮', '002\\002teacher.jpg', '01051680732', 'zhang@bjtu.edu', 'web development teacher');
INSERT INTO `teacher` VALUES ('3', '003', 'QinKuai', '003\\003teacher.jpg', '01051680732', 'zhang@bjtu.edu', 'web development teacher');
INSERT INTO `teacher` VALUES ('4', '004', 'QinKuai', '004\\004teacher.jpg', '01051680732', 'zhang@bjtu.edu', 'web development teacher');
INSERT INTO `teacher` VALUES ('5', '005', 'QinKuai', '005\\005teacher.jpg', '01051680732', 'zhang@bjtu.edu', 'web development teacher');
