/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Henry
 * Created: 07-03-2019
 */

-- DROP TABLE public.cuenta;

CREATE TABLE public.cuenta (
	idproyecto int8 NOT NULL,
	codigo varchar(10) NOT NULL,
	nombre varchar(100) NULL,
	descripcion varchar(200) NULL,
	codigopadre varchar(10) NULL,
	CONSTRAINT cuenta_pk PRIMARY KEY (idproyecto, codigo),
	CONSTRAINT cuenta_cuenta_padre_fk FOREIGN KEY (idproyecto, codigopadre) REFERENCES cuenta(idproyecto, codigo)
);