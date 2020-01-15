create table pedido (
	id bigint not null auto_increment,
    subtotal decimal(15, 2) not null,
    taxa_frete decimal(15, 2) not null,
    valor_total decimal(15, 2) not null,
    data_criacao datetime not null,
    data_confirmacao datetime,
    data_cancelamento datetime,
    data_entrega datetime,
    status varchar(12) not null,
    endereco_cep varchar(9) not null,
    endereco_logradouro varchar(100) not null,
    endereco_numero varchar(20) not null,
    endereco_bairro varchar(60) not null,
    endereco_complemento varchar(60),
    endereco_cidade_id bigint not null,
    forma_pagamento_id bigint not null,
    usuario_cliente_id bigint not null,
    restaurante_id bigint not null,

	primary key (id)
);

alter table pedido add constraint fk_pedido_endereco_cidade
foreign key (endereco_cidade_id) references cidade (id)
ON DELETE NO ACTION
ON UPDATE CASCADE;

alter table pedido add constraint fk_pedido_forma_pagamento
foreign key (forma_pagamento_id) references forma_pagamento (id)
ON DELETE NO ACTION
  ON UPDATE CASCADE;

alter table pedido add constraint fk_pedido_usuario_cliente
foreign key (usuario_cliente_id) references usuario (id)
ON DELETE NO ACTION
ON UPDATE CASCADE;

alter table pedido add constraint fk_pedido_restaurante
foreign key (restaurante_id) references restaurante (id)
ON DELETE NO ACTION
ON UPDATE CASCADE;