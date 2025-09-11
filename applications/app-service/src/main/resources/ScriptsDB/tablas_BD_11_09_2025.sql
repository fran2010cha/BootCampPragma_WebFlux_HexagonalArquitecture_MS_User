-- Table: public.users

-- DROP TABLE IF EXISTS public.users;

CREATE TABLE IF NOT EXISTS public.users
(
    user_id bigint NOT NULL DEFAULT nextval('users_user_id_seq'::regclass),
    nombre character varying(100) COLLATE pg_catalog."default" NOT NULL,
    apellido character varying(100) COLLATE pg_catalog."default" NOT NULL,
    fecha_nacimiento character varying(100) COLLATE pg_catalog."default",
    direccion character varying(100) COLLATE pg_catalog."default",
    telefono character varying(20) COLLATE pg_catalog."default",
    email character varying(150) COLLATE pg_catalog."default" NOT NULL,
    salario_base numeric(10,2),
    documento_identidad character varying(50) COLLATE pg_catalog."default" NOT NULL,
    rol_id bigint,
    password_hash character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (user_id),
    CONSTRAINT users_documento_identidad_key UNIQUE (documento_identidad),
    CONSTRAINT users_email_key UNIQUE (email),
    CONSTRAINT fk_usuario_rol FOREIGN KEY (rol_id)
        REFERENCES public.rol (uniqueid) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE SET NULL
);

- Table: public.rol

-- DROP TABLE IF EXISTS public.rol;

CREATE TABLE IF NOT EXISTS public.rol
(
    uniqueid bigint NOT NULL DEFAULT nextval('rol_uniqueid_seq'::regclass),
    nombre character varying(100) COLLATE pg_catalog."default" NOT NULL,
    descripcion character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT rol_pkey PRIMARY KEY (uniqueid)
)
