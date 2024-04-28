create database mydb;

USE mydb;

CREATE TABLE `menu` (
	`menu_idx`	int	NOT NULL,
	`menu_id`	string	NULL,
	`menu_name`	string	NULL,
	`menu_price`	int	NULL,
	`menu_image`	string	NULL,
	`menu_date`	date	NULL,
	`category_idx`	int	NOT NULL
);

CREATE TABLE `user` (
	`user_idx`	int	NOT NULL,
	`user_id`	string	NULL,
	`user_pw`	string	NULL,
	`user_nickname`	string	NULL,
	`user_role`	string	NULL,
	`user_point`	int	NULL,
	`user_join_date`	date	NULL
);

CREATE TABLE `order` (
	`order_idx`	int	NOT NULL,
	`order_id`	string	NULL,
	`order_status`	string	NULL,
	`order_date`	date	NULL,
	`user_idx`	int	NOT NULL
);

CREATE TABLE `order_detail` (
	`order_detail_idx`	int	NOT NULL,
	`order_idx`	int	NOT NULL,
	`menu_idx`	int	NOT NULL,
	`order_detail_count`	int	NULL,
	`menu_temperature`	string	NULL,
	`menu_size`	string	NULL
);

CREATE TABLE `category` (
	`category_idx`	int	NOT NULL,
	`category_name`	string	NULL
);

ALTER TABLE `menu` ADD CONSTRAINT `PK_MENU` PRIMARY KEY (
	`menu_idx`
);

ALTER TABLE `user` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
	`user_idx`
);

ALTER TABLE `order` ADD CONSTRAINT `PK_ORDER` PRIMARY KEY (
	`order_idx`
);

ALTER TABLE `order_detail` ADD CONSTRAINT `PK_ORDER_DETAIL` PRIMARY KEY (
	`order_detail_idx`,
	`order_idx`,
	`menu_idx`
);

ALTER TABLE `category` ADD CONSTRAINT `PK_CATEGORY` PRIMARY KEY (
	`category_idx`
);

ALTER TABLE `order_detail` ADD CONSTRAINT `FK_order_TO_order_detail_1` FOREIGN KEY (
	`order_idx`
)
REFERENCES `order` (
	`order_idx`
);

ALTER TABLE `order_detail` ADD CONSTRAINT `FK_menu_TO_order_detail_1` FOREIGN KEY (
	`menu_idx`
)
REFERENCES `menu` (
	`menu_idx`
);
