CREATE TABLE `menu` (
	`menu_idx`	int	NOT NULL,
	`menu_id`	varchar(255)	NOT NULL,
	`menu_name`	varchar(255)	NOT NULL,
	`menu_price`	int	NULL,
	`menu_image`	string	NULL,
	`menu_date`	date	NULL,
	`category_idx`	int	NOT NULL
);

CREATE TABLE `user` (
	`user_idx`	int	NOT NULL,
	`user_id`	varchar(255)	NOT NULL,
	`user_pw`	varchar(255)	NOT NULL,
	`user_nickname`	varchar(255)	NOT NULL,
	`user_role`	varchar(255)	NOT NULL,
	`user_point`	int	NULL,
	`user_join_date`	date	NULL
);

CREATE TABLE `orders` (
	`order_idx`	int	NOT NULL,
	`order_id`	varchar(255)	NOT NULL,
	`order_status`	varchar(255)	NOT NULL,
	`order_date`	date	NULL,
	`user_idx`	int	NOT NULL
);

CREATE TABLE `order_detail` (
	`order_detail_idx`	int	NOT NULL,
	`order_idx`	int	NOT NULL,
	`menu_idx`	int	NOT NULL,
	`order_detail_count`	int	NULL,
	`menu_temperature`	varchar(255)	NOT NULL,
	`menu_size`	varchar(255)	NOT NULL
);

CREATE TABLE `category` (
	`category_idx`	int	NOT NULL,
	`category_name`	varchar(255)	NOT NULL
);

ALTER TABLE `menu` ADD CONSTRAINT `PK_MENU` PRIMARY KEY (
	`menu_idx`
);

ALTER TABLE `user` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
	`user_idx`
);

ALTER TABLE `orders` ADD CONSTRAINT `PK_ORDERS` PRIMARY KEY (
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

ALTER TABLE `order_detail` ADD CONSTRAINT `FK_orders_TO_order_detail_1` FOREIGN KEY (
	`order_idx`
)
REFERENCES `orders` (
	`order_idx`
);

ALTER TABLE `order_detail` ADD CONSTRAINT `FK_menu_TO_order_detail_1` FOREIGN KEY (
	`menu_idx`
)
REFERENCES `menu` (
	`menu_idx`
);