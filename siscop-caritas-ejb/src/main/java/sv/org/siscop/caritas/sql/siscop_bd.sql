/**
 * Author:  Henry
 * Created: 06-06-2019
 */

-- SECUENCIAS

CREATE SEQUENCE seq_persona START 1;
CREATE SEQUENCE seq_telefono START 1;
CREATE SEQUENCE seq_direccion START 1;
CREATE SEQUENCE seq_documento START 1;
CREATE SEQUENCE seq_usuario START 1;
CREATE SEQUENCE seq_proyecto START 1;

-- Drop table

-- DROP TABLE public.persona;

CREATE TABLE public.persona (
	id int8 NOT NULL,
	idtipo int8 NULL,
	nomcom varchar(50) NULL,
	razsocial varchar(50) NULL,
	nombre1 varchar(15) NULL,
	nombre2 varchar(15) NULL,
	nombre3 varchar(15) NULL,
	apecasada varchar(15) NULL,
	fechacrea timestamp NULL,
	usercrea varchar(15) NULL,
	fechamod timestamp NULL,
	usermod varchar(15) NULL,
	CONSTRAINT persona_pk PRIMARY KEY (id),
	CONSTRAINT persona_item_fk FOREIGN KEY (idtipo) REFERENCES item_catalogo(id)
);


-- Drop table

-- DROP TABLE public.direccion;

CREATE TABLE public.direccion (
	id int8 NOT NULL,
	idpersona int8 NULL,
	idtipo int8 NULL,
	direccion varchar(200) NULL,
	fechacrea timestamp NULL,
	usercrea varchar(15) NULL,
	fechamod timestamp NULL,
	usermod varchar(15) NULL,
	CONSTRAINT direccion_persona_unique UNIQUE (idpersona, idtipo),
	CONSTRAINT direccion_pk PRIMARY KEY (id),
	CONSTRAINT direccion_item_fk FOREIGN KEY (idtipo) REFERENCES item_catalogo(id),
	CONSTRAINT direccion_persona_fk FOREIGN KEY (idpersona) REFERENCES persona(id)
);

-- Drop table

-- DROP TABLE public.documento;

CREATE TABLE public.documento (
	id int8 NOT NULL,
	idpersona int8 NULL,
	idtipo int8 NULL,
	numero varchar(20) NULL,
	fechaexpedicion date NULL,
	fechavence date NULL,
	fechacrea timestamp NULL,
	usercrea varchar(15) NULL,
	fechamod timestamp NULL,
	usermod varchar(15) NULL,
	CONSTRAINT documento_numero_unique UNIQUE (idtipo, numero),
	CONSTRAINT documento_persona_unique UNIQUE (idpersona, idtipo),
	CONSTRAINT documento_pk PRIMARY KEY (id),
	CONSTRAINT documento_item_fk FOREIGN KEY (idtipo) REFERENCES item_catalogo(id),
	CONSTRAINT documento_persona_fk FOREIGN KEY (idpersona) REFERENCES persona(id)
);



-- Drop table

-- DROP TABLE public.telefono;

CREATE TABLE public.telefono (
	id int8 NOT NULL,
	idpersona int8 NULL,
	idtipo int8 NULL,
	"extension" varchar(4) NULL,
	numero varchar(15) NULL,
	fechacrea timestamp NULL,
	usercrea varchar(15) NULL,
	fechamod timestamp NULL,
	usermod varchar(15) NULL,
	CONSTRAINT telefono_persona_unique UNIQUE (idpersona, idtipo),
	CONSTRAINT telefono_pk PRIMARY KEY (id),
	CONSTRAINT telefono_item_fk FOREIGN KEY (idtipo) REFERENCES item_catalogo(id),
	CONSTRAINT telefono_persona_fk FOREIGN KEY (idpersona) REFERENCES persona(id)
);

-- Drop table

-- DROP TABLE public.usuario;

CREATE TABLE public.usuario (
	id int8 NOT NULL,
	usuario varchar(15) NULL,
	idpersona int8 NULL,
	password varchar(50) NULL,
	fechacrea timestamp NULL,
	usercrea varchar(15) NULL,
	fechamod timestamp NULL,
	usermod varchar(15) NULL,
	CONSTRAINT usuario_pk PRIMARY KEY (id),
	CONSTRAINT usuario_unique UNIQUE (usuario),
	CONSTRAINT usuario_persona_fk FOREIGN KEY (idpersona) REFERENCES persona(id)
);


-- Drop table

-- DROP TABLE public.proyecto;

CREATE TABLE public.proyecto (
	id int8 NOT NULL,
	codigo varchar(10) NULL,
	nombre varchar(200) NULL,
	nombrecorto varchar(20) NULL,
	fechaini date NULL,
	fechafin date NULL,
	fechacrea time NULL,
	usercrea varchar(15) NULL,
	fechamod timestamp NULL,
	usermod varchar(15) NULL,
	CONSTRAINT proyecto_codigo_unique UNIQUE (codigo),
	CONSTRAINT proyecto_pk PRIMARY KEY (id)
);

