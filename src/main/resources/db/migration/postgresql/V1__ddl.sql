create sequence hibernate_sequence;

create table if not exists association_value_entry
(
	id bigint not null
		constraint association_value_entry_pkey
			primary key,
	association_key varchar(255) not null,
	association_value varchar(255),
	saga_id varchar(255) not null,
	saga_type varchar(255)
);


create index if not exists idxk45eqnxkgd8hpdn6xixn8sgft
	on association_value_entry (saga_type, association_key, association_value);

create index if not exists idxgv5k1v2mh6frxuy5c0hgbau94
	on association_value_entry (saga_id, saga_type);

create table if not exists order_entity
(
	id varchar(255) not null
		constraint order_entity_pkey
			primary key,
	delivery_address varchar(255),
	order_state varchar(255),
	restaurant_id varchar(255),
	user_id varchar(255)
);


create table if not exists order_entity_order_line_items
(
	order_entity_id varchar(255) not null
		constraint fkgaj6h7w5xqbrdp8ny5qwv27i9
			references order_entity,
	menu_item_id varchar(255),
	name varchar(255),
	price numeric(19,2),
	quantity integer
);


create table if not exists saga_entry
(
	saga_id varchar(255) not null
		constraint saga_entry_pkey
			primary key,
	revision varchar(255),
	saga_type varchar(255),
	serialized_saga oid
);


create table if not exists token_entry
(
	processor_name varchar(255) not null,
	segment integer not null,
	owner varchar(255),
	timestamp varchar(255) not null,
	token oid,
	token_type varchar(255),
	constraint token_entry_pkey
		primary key (processor_name, segment)
);
