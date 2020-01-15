create table item_pedido (
	id bigint not null auto_increment,
    quantidade integer not null,
    preco_unitario decimal(15,2) not null,
    preco_total decimal(15,2) not null,
    observacao text,
    produto_id bigint not null,
    pedido_id bigint not null,

	primary key (id)
);

alter table item_pedido add constraint fk_item_pedido_produto
foreign key (produto_id) references produto (id)
ON DELETE NO ACTION
ON UPDATE CASCADE;

alter table item_pedido add constraint fk_item_pedido_pedido
foreign key (pedido_id) references pedido (id)
ON DELETE NO ACTION
ON UPDATE CASCADE;