/*
Navicat PGSQL Data Transfer

Source Server         : 10.15.12.150
Source Server Version : 90324
Source Host           : 10.15.12.150:5432
Source Database       : test1
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 90324
File Encoding         : 65001

Date: 2019-07-09 11:45:14
*/


-- ----------------------------
-- Table structure for base_users
-- ----------------------------
DROP TABLE IF EXISTS "public"."base_users";
CREATE TABLE "public"."base_users" (
"name" varchar COLLATE "default",
"created" int8,
"updated" int8,
"created_by" varchar COLLATE "default",
"updated_by" varchar COLLATE "default",
"active" bool DEFAULT false,
"id" int4 DEFAULT nextval('base_users_id_seq'::regclass) NOT NULL
)
WITH (OIDS=FALSE)
;

-- ----------------------------
-- Alter Sequences Owned By
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table base_users
-- ----------------------------
ALTER TABLE "public"."base_users" ADD PRIMARY KEY ("id");
