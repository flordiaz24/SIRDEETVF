PGDMP     9                     x            sirdeetv    9.6.15    9.6.15 �   �
           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            �
           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            �
           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                       false            �
           1262    17058    sirdeetv    DATABASE     �   CREATE DATABASE sirdeetv WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Spanish_El Salvador.1252' LC_CTYPE = 'Spanish_El Salvador.1252';
    DROP DATABASE sirdeetv;
             postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
             postgres    false            �
           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                  postgres    false    3                        3079    12387    plpgsql 	   EXTENSION     ?   CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
    DROP EXTENSION plpgsql;
                  false            �
           0    0    EXTENSION plpgsql    COMMENT     @   COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';
                       false    1                       1255    17059    f_nombre_apellido()    FUNCTION     �  CREATE FUNCTION public.f_nombre_apellido() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
    BEGIN 
      NEW.PRIMER_NOMBRE = INITCAP(NEW.PRIMER_NOMBRE);
      NEW.SEGUNDO_NOMBRE = INITCAP(NEW.SEGUNDO_NOMBRE);
      NEW.PRIMER_APELLIDO = INITCAP(NEW.PRIMER_APELLIDO);
      NEW.SEGUNDO_APELLIDO = INITCAP(NEW.SEGUNDO_APELLIDO);
      NEW.CORREO_PERSONA = LOWER(NEW.CORREO_PERSONA);
      NEW.DIRECCION_RESIDENCIA = UPPER(NEW.DIRECCION_RESIDENCIA);
      RETURN NEW;
    END;
$$;
 *   DROP FUNCTION public.f_nombre_apellido();
       public       postgres    false    1    3                       1255    17060    f_usuario_nombre()    FUNCTION     �  CREATE FUNCTION public.f_usuario_nombre() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
   ULTIMO_USUARIO INT;
BEGIN
   ULTIMO_USUARIO := (SELECT COALESCE(MAX(NULLIF(REGEXP_REPLACE(NOM_USUARIO, '\D','','g'), '')::INT),0) FROM  USUARIO WHERE NOM_USUARIO LIKE '%'||LOWER(NEW.NOM_USUARIO)||'%');
   NEW.NOM_USUARIO = LOWER(NEW.NOM_USUARIO)||ULTIMO_USUARIO+1;
   RETURN NEW;
END;
$$;
 )   DROP FUNCTION public.f_usuario_nombre();
       public       postgres    false    3    1            �            1259    17061 	   actividad    TABLE     �   CREATE TABLE public.actividad (
    cod_actividad integer NOT NULL,
    cod_invest integer,
    nom_actividad character varying(200),
    desc_actividad character varying(200)
);
    DROP TABLE public.actividad;
       public         postgres    false    3                       1259    34132    actividad_01    TABLE     0  CREATE TABLE public.actividad_01 (
    id_actividad01 integer NOT NULL,
    ident_animal text,
    toma_glucemia30 integer,
    toma_glucemia60 integer,
    toma_glucemia120 integer,
    toma_glucemia180 integer,
    promedio text,
    id_encabezado integer,
    usuario text,
    fecha_registro date
);
     DROP TABLE public.actividad_01;
       public         postgres    false    3                       1259    34130    actividad_01_id_actividad01_seq    SEQUENCE     �   CREATE SEQUENCE public.actividad_01_id_actividad01_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 6   DROP SEQUENCE public.actividad_01_id_actividad01_seq;
       public       postgres    false    3    260            �
           0    0    actividad_01_id_actividad01_seq    SEQUENCE OWNED BY     c   ALTER SEQUENCE public.actividad_01_id_actividad01_seq OWNED BY public.actividad_01.id_actividad01;
            public       postgres    false    259            �            1259    17064    actividad_cod_actividad_seq    SEQUENCE     �   CREATE SEQUENCE public.actividad_cod_actividad_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 2   DROP SEQUENCE public.actividad_cod_actividad_seq;
       public       postgres    false    185    3            �
           0    0    actividad_cod_actividad_seq    SEQUENCE OWNED BY     [   ALTER SEQUENCE public.actividad_cod_actividad_seq OWNED BY public.actividad.cod_actividad;
            public       postgres    false    186                       1259    34143    actividad_encabezado    TABLE     �  CREATE TABLE public.actividad_encabezado (
    id_encabezado integer NOT NULL,
    hash text,
    buena boolean,
    centinela boolean,
    cepa text,
    concentracion_de_dosis text,
    control boolean,
    control_1 text,
    descripcion text,
    edad text,
    edad_inicial text,
    especie text,
    fecha date,
    fecha_de_necropcia date,
    fecha_final date,
    fecha_inicio date,
    grupo text,
    lapso_de_tiempo text,
    mala boolean,
    metodo_eutanasico text,
    n_del_los_animales text,
    n_jaula text,
    no boolean,
    no_programada boolean,
    observaciones_enc text,
    observaciones_pie text,
    patron boolean,
    patron_2 text,
    periodo text,
    peso text,
    programada boolean,
    protocolo text,
    regular boolean,
    responsable_de_prueba text,
    semana_n text,
    sexo text,
    si boolean,
    sustancia_de_ensayo text,
    tiempo_de_exposicion text,
    tiempo_de_observacion text,
    tratamiento boolean,
    tratamiento_3 text,
    tratamiento_4 text,
    tratamiento_5 text,
    tratamiento_6 text,
    via_de_administracion text,
    fecha_registro date,
    nombreinvestigacion text,
    codigoinvestigacion text,
    usuario text
);
 (   DROP TABLE public.actividad_encabezado;
       public         postgres    false    3                       1259    34141 &   actividad_encabezado_id_encabezado_seq    SEQUENCE     �   CREATE SEQUENCE public.actividad_encabezado_id_encabezado_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 =   DROP SEQUENCE public.actividad_encabezado_id_encabezado_seq;
       public       postgres    false    3    262            �
           0    0 &   actividad_encabezado_id_encabezado_seq    SEQUENCE OWNED BY     q   ALTER SEQUENCE public.actividad_encabezado_id_encabezado_seq OWNED BY public.actividad_encabezado.id_encabezado;
            public       postgres    false    261            �            1259    17066    analisis_lab    TABLE     �   CREATE TABLE public.analisis_lab (
    id_analisis integer NOT NULL,
    codigo_muestra character varying(50) NOT NULL,
    cod_bit_lab integer NOT NULL,
    id_tipo_prueba integer NOT NULL,
    fecha_analisis date,
    estado_ana boolean
);
     DROP TABLE public.analisis_lab;
       public         postgres    false    3            �            1259    17069    analisis_lab_id_analisis_seq    SEQUENCE     �   CREATE SEQUENCE public.analisis_lab_id_analisis_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 3   DROP SEQUENCE public.analisis_lab_id_analisis_seq;
       public       postgres    false    187    3            �
           0    0    analisis_lab_id_analisis_seq    SEQUENCE OWNED BY     ]   ALTER SEQUENCE public.analisis_lab_id_analisis_seq OWNED BY public.analisis_lab.id_analisis;
            public       postgres    false    188            �            1259    17071    animal    TABLE     �   CREATE TABLE public.animal (
    id_animal integer NOT NULL,
    id_cat_animal integer NOT NULL,
    id_analisis integer NOT NULL,
    estado_animal boolean
);
    DROP TABLE public.animal;
       public         postgres    false    3            �            1259    17074    animal_id_animal_seq    SEQUENCE     }   CREATE SEQUENCE public.animal_id_animal_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.animal_id_animal_seq;
       public       postgres    false    3    189            �
           0    0    animal_id_animal_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.animal_id_animal_seq OWNED BY public.animal.id_animal;
            public       postgres    false    190            �            1259    17076    bitacora_campo    TABLE       CREATE TABLE public.bitacora_campo (
    id_bit_campo integer NOT NULL,
    bit_id_bit_campo integer,
    id_inv_vector integer NOT NULL,
    fecha_campo date NOT NULL,
    codigo_bitacora character varying(15),
    descripcion_bitacora character varying(100)
);
 "   DROP TABLE public.bitacora_campo;
       public         postgres    false    3            �            1259    17079    bitacora_campo_id_bit_campo_seq    SEQUENCE     �   CREATE SEQUENCE public.bitacora_campo_id_bit_campo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 6   DROP SEQUENCE public.bitacora_campo_id_bit_campo_seq;
       public       postgres    false    191    3            �
           0    0    bitacora_campo_id_bit_campo_seq    SEQUENCE OWNED BY     c   ALTER SEQUENCE public.bitacora_campo_id_bit_campo_seq OWNED BY public.bitacora_campo.id_bit_campo;
            public       postgres    false    192            �            1259    17081    bitacora_lab    TABLE     �   CREATE TABLE public.bitacora_lab (
    cod_bit_lab integer NOT NULL,
    cod_actividad integer NOT NULL,
    id_inv_vector integer NOT NULL,
    bit_cod_bit_lab integer,
    nom_bit_lab character varying(200) NOT NULL
);
     DROP TABLE public.bitacora_lab;
       public         postgres    false    3            �            1259    17084    bitacora_lab_cod_bit_lab_seq    SEQUENCE     �   CREATE SEQUENCE public.bitacora_lab_cod_bit_lab_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 3   DROP SEQUENCE public.bitacora_lab_cod_bit_lab_seq;
       public       postgres    false    3    193            �
           0    0    bitacora_lab_cod_bit_lab_seq    SEQUENCE OWNED BY     ]   ALTER SEQUENCE public.bitacora_lab_cod_bit_lab_seq OWNED BY public.bitacora_lab.cod_bit_lab;
            public       postgres    false    194            �            1259    17086    cacerio    TABLE     �   CREATE TABLE public.cacerio (
    id_cacerio integer NOT NULL,
    id_col_can integer NOT NULL,
    nom_cacerio character varying(100) NOT NULL
);
    DROP TABLE public.cacerio;
       public         postgres    false    3            �            1259    17089    cacerio_id_cacerio_seq    SEQUENCE        CREATE SEQUENCE public.cacerio_id_cacerio_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.cacerio_id_cacerio_seq;
       public       postgres    false    3    195            �
           0    0    cacerio_id_cacerio_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.cacerio_id_cacerio_seq OWNED BY public.cacerio.id_cacerio;
            public       postgres    false    196            �            1259    17091    cargo    TABLE     �   CREATE TABLE public.cargo (
    id_cargo integer NOT NULL,
    cargo character varying(200),
    descripcion character varying(200),
    fecha_creacion date,
    activo boolean,
    fecha_modifica date
);
    DROP TABLE public.cargo;
       public         postgres    false    3            �            1259    17094    cargo_id_cargo_seq    SEQUENCE     {   CREATE SEQUENCE public.cargo_id_cargo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.cargo_id_cargo_seq;
       public       postgres    false    3    197            �
           0    0    cargo_id_cargo_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.cargo_id_cargo_seq OWNED BY public.cargo.id_cargo;
            public       postgres    false    198            �            1259    17096    catalogo_anima    TABLE     �   CREATE TABLE public.catalogo_anima (
    id_cat_animal integer NOT NULL,
    nom_animal character varying(200) NOT NULL,
    dispo_animal boolean NOT NULL
);
 "   DROP TABLE public.catalogo_anima;
       public         postgres    false    3            �            1259    17099     catalogo_anima_id_cat_animal_seq    SEQUENCE     �   CREATE SEQUENCE public.catalogo_anima_id_cat_animal_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 7   DROP SEQUENCE public.catalogo_anima_id_cat_animal_seq;
       public       postgres    false    3    199            �
           0    0     catalogo_anima_id_cat_animal_seq    SEQUENCE OWNED BY     e   ALTER SEQUENCE public.catalogo_anima_id_cat_animal_seq OWNED BY public.catalogo_anima.id_cat_animal;
            public       postgres    false    200            �            1259    17101    colonia_canton    TABLE     �   CREATE TABLE public.colonia_canton (
    id_col_can integer NOT NULL,
    cod_municipio character varying(50) NOT NULL,
    nom_ubicacion character varying(50) NOT NULL
);
 "   DROP TABLE public.colonia_canton;
       public         postgres    false    3            �            1259    17104    colonia_canton_id_col_can_seq    SEQUENCE     �   CREATE SEQUENCE public.colonia_canton_id_col_can_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 4   DROP SEQUENCE public.colonia_canton_id_col_can_seq;
       public       postgres    false    3    201            �
           0    0    colonia_canton_id_col_can_seq    SEQUENCE OWNED BY     _   ALTER SEQUENCE public.colonia_canton_id_col_can_seq OWNED BY public.colonia_canton.id_col_can;
            public       postgres    false    202            �            1259    17106    departamento    TABLE     �   CREATE TABLE public.departamento (
    cod_depto character varying(50) NOT NULL,
    nom_depto character varying(100) NOT NULL
);
     DROP TABLE public.departamento;
       public         postgres    false    3            �            1259    17109    detalle    TABLE     �   CREATE TABLE public.detalle (
    id_detalle integer NOT NULL,
    id_respuesta integer NOT NULL,
    opcion_repuesta character varying(200)
);
    DROP TABLE public.detalle;
       public         postgres    false    3            �            1259    17112    detalle_id_detalle_seq    SEQUENCE        CREATE SEQUENCE public.detalle_id_detalle_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.detalle_id_detalle_seq;
       public       postgres    false    3    204            �
           0    0    detalle_id_detalle_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.detalle_id_detalle_seq OWNED BY public.detalle.id_detalle;
            public       postgres    false    205            �            1259    17114 	   documento    TABLE       CREATE TABLE public.documento (
    id_doc integer NOT NULL,
    id_perfil integer NOT NULL,
    doc_id_doc integer,
    titulo_doc character varying(100) NOT NULL,
    desc_doc character varying(200),
    ruta_url character varying(500) NOT NULL,
    estado_doc boolean
);
    DROP TABLE public.documento;
       public         postgres    false    3            �            1259    17120    documento_id_doc_seq    SEQUENCE     }   CREATE SEQUENCE public.documento_id_doc_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.documento_id_doc_seq;
       public       postgres    false    3    206            �
           0    0    documento_id_doc_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.documento_id_doc_seq OWNED BY public.documento.id_doc;
            public       postgres    false    207            �            1259    17122    encuesta    TABLE     �   CREATE TABLE public.encuesta (
    id_encuesta integer NOT NULL,
    id_bit_campo integer NOT NULL,
    nom_encuesta character varying(200)
);
    DROP TABLE public.encuesta;
       public         postgres    false    3            �            1259    17125    encuesta_id_encuesta_seq    SEQUENCE     �   CREATE SEQUENCE public.encuesta_id_encuesta_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.encuesta_id_encuesta_seq;
       public       postgres    false    3    208            �
           0    0    encuesta_id_encuesta_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.encuesta_id_encuesta_seq OWNED BY public.encuesta.id_encuesta;
            public       postgres    false    209            
           1259    34245    familia    TABLE     �   CREATE TABLE public.familia (
    id integer NOT NULL,
    nombre_familia character varying(50) NOT NULL,
    id_orden integer
);
    DROP TABLE public.familia;
       public         postgres    false    3            	           1259    34243    familia_id_seq    SEQUENCE     w   CREATE SEQUENCE public.familia_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.familia_id_seq;
       public       postgres    false    3    266            �
           0    0    familia_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.familia_id_seq OWNED BY public.familia.id;
            public       postgres    false    265            �            1259    17127 
   inv_vector    TABLE     �   CREATE TABLE public.inv_vector (
    id_inv_vector integer NOT NULL,
    cod_vector integer NOT NULL,
    cod_invest integer,
    titulo_inv character varying(50),
    desc_inv_vector character varying(200),
    fecha_registro date
);
    DROP TABLE public.inv_vector;
       public         postgres    false    3            �            1259    17130    inv_vector_id_inv_vector_seq    SEQUENCE     �   CREATE SEQUENCE public.inv_vector_id_inv_vector_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 3   DROP SEQUENCE public.inv_vector_id_inv_vector_seq;
       public       postgres    false    210    3            �
           0    0    inv_vector_id_inv_vector_seq    SEQUENCE OWNED BY     ]   ALTER SEQUENCE public.inv_vector_id_inv_vector_seq OWNED BY public.inv_vector.id_inv_vector;
            public       postgres    false    211            �            1259    17132    investigacion    TABLE     $  CREATE TABLE public.investigacion (
    cod_invest integer NOT NULL,
    cod_laboratorio integer NOT NULL,
    inv_cod_invest integer,
    nom_invest character varying(200) NOT NULL,
    estado_invest boolean,
    anio integer,
    mes integer,
    fecha_creacion date,
    activo integer
);
 !   DROP TABLE public.investigacion;
       public         postgres    false    3            �            1259    17135    investigacion_cod_invest_seq    SEQUENCE     �   CREATE SEQUENCE public.investigacion_cod_invest_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 3   DROP SEQUENCE public.investigacion_cod_invest_seq;
       public       postgres    false    212    3            �
           0    0    investigacion_cod_invest_seq    SEQUENCE OWNED BY     ]   ALTER SEQUENCE public.investigacion_cod_invest_seq OWNED BY public.investigacion.cod_invest;
            public       postgres    false    213            �            1259    17137    investigacion_investigador    TABLE     q   CREATE TABLE public.investigacion_investigador (
    cod_invest integer NOT NULL,
    id_inv integer NOT NULL
);
 .   DROP TABLE public.investigacion_investigador;
       public         postgres    false    3            �            1259    17140    investigador    TABLE     �   CREATE TABLE public.investigador (
    id_inv integer NOT NULL,
    nom_inv character varying(200) NOT NULL,
    tipo_inv character varying(200) NOT NULL
);
     DROP TABLE public.investigador;
       public         postgres    false    3            �            1259    17143    laboratorio    TABLE     �   CREATE TABLE public.laboratorio (
    cod_laboratorio integer NOT NULL,
    nom_lab character varying(200) NOT NULL,
    alias character varying(200)
);
    DROP TABLE public.laboratorio;
       public         postgres    false    3            �            1259    17146    laboratorio_cod_laboratorio_seq    SEQUENCE     �   CREATE SEQUENCE public.laboratorio_cod_laboratorio_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 6   DROP SEQUENCE public.laboratorio_cod_laboratorio_seq;
       public       postgres    false    216    3            �
           0    0    laboratorio_cod_laboratorio_seq    SEQUENCE OWNED BY     c   ALTER SEQUENCE public.laboratorio_cod_laboratorio_seq OWNED BY public.laboratorio.cod_laboratorio;
            public       postgres    false    217            �            1259    17148    lote    TABLE     '  CREATE TABLE public.lote (
    id_lote integer NOT NULL,
    lot_id_lote integer,
    cod_bit_lab integer,
    fecha_creacion date,
    fecha_modificacion date,
    estatus integer,
    nombre_lote character varying(50),
    num_muestras integer,
    id_vector integer,
    id_colcan integer
);
    DROP TABLE public.lote;
       public         postgres    false    3            �            1259    17151    lote_id_lote_seq    SEQUENCE     y   CREATE SEQUENCE public.lote_id_lote_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.lote_id_lote_seq;
       public       postgres    false    218    3            �
           0    0    lote_id_lote_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.lote_id_lote_seq OWNED BY public.lote.id_lote;
            public       postgres    false    219            �            1259    17153    mantenimiento    TABLE       CREATE TABLE public.mantenimiento (
    id_manto integer NOT NULL,
    id_lote integer NOT NULL,
    id_preservante integer,
    fecha_manto character varying(50) NOT NULL,
    completado_manto boolean NOT NULL,
    fecha_prox_manto date,
    numero_mantenimiento integer
);
 !   DROP TABLE public.mantenimiento;
       public         postgres    false    3            �            1259    17156    mantenimiento_id_manto_seq    SEQUENCE     �   CREATE SEQUENCE public.mantenimiento_id_manto_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public.mantenimiento_id_manto_seq;
       public       postgres    false    220    3            �
           0    0    mantenimiento_id_manto_seq    SEQUENCE OWNED BY     Y   ALTER SEQUENCE public.mantenimiento_id_manto_seq OWNED BY public.mantenimiento.id_manto;
            public       postgres    false    221            �            1259    17158    menu    TABLE     �   CREATE TABLE public.menu (
    id_menu integer NOT NULL,
    men_id_menu integer,
    nom_menu character varying(150) NOT NULL,
    ruta character varying(200),
    icono character varying(150),
    orden integer DEFAULT 1,
    disponible boolean
);
    DROP TABLE public.menu;
       public         postgres    false    3            �            1259    17165    menu_id_menu_seq    SEQUENCE     y   CREATE SEQUENCE public.menu_id_menu_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.menu_id_menu_seq;
       public       postgres    false    3    222            �
           0    0    menu_id_menu_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.menu_id_menu_seq OWNED BY public.menu.id_menu;
            public       postgres    false    223            �            1259    17167    menu_permiso    TABLE     d   CREATE TABLE public.menu_permiso (
    id_menu integer NOT NULL,
    id_permiso integer NOT NULL
);
     DROP TABLE public.menu_permiso;
       public         postgres    false    3            �            1259    17170    muestra    TABLE     s  CREATE TABLE public.muestra (
    codigo_muestra character varying(50) NOT NULL,
    id_lote integer,
    id_tipo_mues integer NOT NULL,
    id_bit_campo integer,
    nom_jefe_fam character varying(50) NOT NULL,
    edad_muestra integer NOT NULL,
    telefono_muestra character varying(100),
    fecha_trabajo date NOT NULL,
    fecha_muestra date NOT NULL,
    numero_muestra character varying(50) NOT NULL,
    genero_muestra character varying(50),
    motivo_muestra character varying(50),
    secuencia integer,
    nom_cientifico character varying(100),
    familia_muestra character varying(100),
    parasito boolean
);
    DROP TABLE public.muestra;
       public         postgres    false    3            �            1259    17173 	   municipio    TABLE     �   CREATE TABLE public.municipio (
    cod_municipio character varying(50) NOT NULL,
    cod_depto character varying(50) NOT NULL,
    nom_municipio character varying(50) NOT NULL
);
    DROP TABLE public.municipio;
       public         postgres    false    3                       1259    34237    orden    TABLE     h   CREATE TABLE public.orden (
    id integer NOT NULL,
    nombre_orden character varying(50) NOT NULL
);
    DROP TABLE public.orden;
       public         postgres    false    3                       1259    34235    orden_id_seq    SEQUENCE     u   CREATE SEQUENCE public.orden_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.orden_id_seq;
       public       postgres    false    264    3            �
           0    0    orden_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.orden_id_seq OWNED BY public.orden.id;
            public       postgres    false    263            �            1259    17176 
   perfil_inv    TABLE     �  CREATE TABLE public.perfil_inv (
    id_perfil integer NOT NULL,
    per_id_perfil integer,
    cod_invest integer,
    antecedente character varying(200),
    justificacion character varying(2000),
    planteamiento character varying(2000),
    obj_general character varying(2000),
    obj_especifico character varying(2000),
    metodologia character varying(2000),
    desc_resul character varying(2000),
    presupuesto numeric(15,2),
    numero_persona integer
);
    DROP TABLE public.perfil_inv;
       public         postgres    false    3            �            1259    17182    perfil_inv_id_perfil_seq    SEQUENCE     �   CREATE SEQUENCE public.perfil_inv_id_perfil_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.perfil_inv_id_perfil_seq;
       public       postgres    false    3    227            �
           0    0    perfil_inv_id_perfil_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.perfil_inv_id_perfil_seq OWNED BY public.perfil_inv.id_perfil;
            public       postgres    false    228            �            1259    17184    permiso    TABLE     �   CREATE TABLE public.permiso (
    id_permiso integer NOT NULL,
    nom_permiso character varying(150) NOT NULL,
    estado_permiso boolean NOT NULL
);
    DROP TABLE public.permiso;
       public         postgres    false    3            �            1259    17187    permiso_id_permiso_seq    SEQUENCE        CREATE SEQUENCE public.permiso_id_permiso_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.permiso_id_permiso_seq;
       public       postgres    false    229    3            �
           0    0    permiso_id_permiso_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.permiso_id_permiso_seq OWNED BY public.permiso.id_permiso;
            public       postgres    false    230            �            1259    17189    persona    TABLE     g  CREATE TABLE public.persona (
    id_persona integer NOT NULL,
    id_usuario integer,
    id_encuesta integer,
    primer_nombre character varying(50) NOT NULL,
    segundo_nombre character varying(50),
    primer_apellido character varying(50),
    segundo_apellido character varying(50),
    fecha_nacimiento date,
    fecha_registro date,
    dui character varying(15),
    nit character varying(20),
    correo_persona character varying(200),
    direccion_residencia character varying(200),
    telefono_persona character varying(10),
    municipio character varying(4),
    fecha_ultima_modificacion date
);
    DROP TABLE public.persona;
       public         postgres    false    3            �            1259    17195    persona_id_persona_seq    SEQUENCE        CREATE SEQUENCE public.persona_id_persona_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.persona_id_persona_seq;
       public       postgres    false    231    3            �
           0    0    persona_id_persona_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.persona_id_persona_seq OWNED BY public.persona.id_persona;
            public       postgres    false    232            �            1259    17197    pregunta    TABLE     �   CREATE TABLE public.pregunta (
    id_pregunta integer NOT NULL,
    id_encuesta integer NOT NULL,
    desc_pregunta character varying(200) NOT NULL
);
    DROP TABLE public.pregunta;
       public         postgres    false    3            �            1259    17200    pregunta_id_pregunta_seq    SEQUENCE     �   CREATE SEQUENCE public.pregunta_id_pregunta_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.pregunta_id_pregunta_seq;
       public       postgres    false    233    3            �
           0    0    pregunta_id_pregunta_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.pregunta_id_pregunta_seq OWNED BY public.pregunta.id_pregunta;
            public       postgres    false    234            �            1259    17202    preservante    TABLE     �   CREATE TABLE public.preservante (
    id_preservante integer NOT NULL,
    nom_preservante character varying(100) NOT NULL,
    desc_preservante character varying(200) NOT NULL
);
    DROP TABLE public.preservante;
       public         postgres    false    3            �            1259    17205 	   respuesta    TABLE     �   CREATE TABLE public.respuesta (
    id_respuesta integer NOT NULL,
    id_pregunta integer NOT NULL,
    id_tipo_resp integer NOT NULL,
    opcion_respuesta character varying(200) NOT NULL
);
    DROP TABLE public.respuesta;
       public         postgres    false    3            �            1259    17208    respuesta_id_respuesta_seq    SEQUENCE     �   CREATE SEQUENCE public.respuesta_id_respuesta_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public.respuesta_id_respuesta_seq;
       public       postgres    false    3    236            �
           0    0    respuesta_id_respuesta_seq    SEQUENCE OWNED BY     Y   ALTER SEQUENCE public.respuesta_id_respuesta_seq OWNED BY public.respuesta.id_respuesta;
            public       postgres    false    237            �            1259    17210    rol    TABLE     �   CREATE TABLE public.rol (
    id_rol integer NOT NULL,
    nom_rol character varying(150) NOT NULL,
    descripcion character varying(250),
    estado_rol boolean NOT NULL
);
    DROP TABLE public.rol;
       public         postgres    false    3            �            1259    17213    rol_id_rol_seq    SEQUENCE     w   CREATE SEQUENCE public.rol_id_rol_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.rol_id_rol_seq;
       public       postgres    false    238    3            �
           0    0    rol_id_rol_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.rol_id_rol_seq OWNED BY public.rol.id_rol;
            public       postgres    false    239                       1259    17742    rol_menu    TABLE     J   CREATE TABLE public.rol_menu (
    id_rol integer,
    id_menu integer
);
    DROP TABLE public.rol_menu;
       public         postgres    false    3            �            1259    17215    rol_permiso    TABLE     b   CREATE TABLE public.rol_permiso (
    id_permiso integer NOT NULL,
    id_rol integer NOT NULL
);
    DROP TABLE public.rol_permiso;
       public         postgres    false    3            �            1259    17218    tipo_muestra    TABLE     x   CREATE TABLE public.tipo_muestra (
    id_tipo_mues integer NOT NULL,
    nom_muestra character varying(50) NOT NULL
);
     DROP TABLE public.tipo_muestra;
       public         postgres    false    3            �            1259    17221    tipo_muestra_id_tipo_mues_seq    SEQUENCE     �   CREATE SEQUENCE public.tipo_muestra_id_tipo_mues_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 4   DROP SEQUENCE public.tipo_muestra_id_tipo_mues_seq;
       public       postgres    false    3    241            �
           0    0    tipo_muestra_id_tipo_mues_seq    SEQUENCE OWNED BY     _   ALTER SEQUENCE public.tipo_muestra_id_tipo_mues_seq OWNED BY public.tipo_muestra.id_tipo_mues;
            public       postgres    false    242            �            1259    17223    tipo_prueba    TABLE     �   CREATE TABLE public.tipo_prueba (
    id_tipo_prueba integer NOT NULL,
    nom_prueba character varying(200) NOT NULL,
    estado_disp boolean
);
    DROP TABLE public.tipo_prueba;
       public         postgres    false    3            �            1259    17226    tipo_prueba_id_tipo_prueba_seq    SEQUENCE     �   CREATE SEQUENCE public.tipo_prueba_id_tipo_prueba_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 5   DROP SEQUENCE public.tipo_prueba_id_tipo_prueba_seq;
       public       postgres    false    243    3            �
           0    0    tipo_prueba_id_tipo_prueba_seq    SEQUENCE OWNED BY     a   ALTER SEQUENCE public.tipo_prueba_id_tipo_prueba_seq OWNED BY public.tipo_prueba.id_tipo_prueba;
            public       postgres    false    244            �            1259    17228    tipo_respuesta    TABLE     |   CREATE TABLE public.tipo_respuesta (
    id_tipo_resp integer NOT NULL,
    detalle_resp character varying(100) NOT NULL
);
 "   DROP TABLE public.tipo_respuesta;
       public         postgres    false    3            �            1259    17231    tipo_respuesta_id_tipo_resp_seq    SEQUENCE     �   CREATE SEQUENCE public.tipo_respuesta_id_tipo_resp_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 6   DROP SEQUENCE public.tipo_respuesta_id_tipo_resp_seq;
       public       postgres    false    3    245            �
           0    0    tipo_respuesta_id_tipo_resp_seq    SEQUENCE OWNED BY     c   ALTER SEQUENCE public.tipo_respuesta_id_tipo_resp_seq OWNED BY public.tipo_respuesta.id_tipo_resp;
            public       postgres    false    246            �            1259    17233    tratamiento    TABLE     �   CREATE TABLE public.tratamiento (
    id_tratamiento integer NOT NULL,
    id_analisis integer NOT NULL,
    desc_tratamiento character varying(500) NOT NULL,
    resultado_tratamiento character varying(500),
    fecha_tratamiento date
);
    DROP TABLE public.tratamiento;
       public         postgres    false    3            �            1259    17239    tratamiento_id_tratamiento_seq    SEQUENCE     �   CREATE SEQUENCE public.tratamiento_id_tratamiento_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 5   DROP SEQUENCE public.tratamiento_id_tratamiento_seq;
       public       postgres    false    3    247            �
           0    0    tratamiento_id_tratamiento_seq    SEQUENCE OWNED BY     a   ALTER SEQUENCE public.tratamiento_id_tratamiento_seq OWNED BY public.tratamiento.id_tratamiento;
            public       postgres    false    248            �            1259    17241 	   ubicacion    TABLE     �  CREATE TABLE public.ubicacion (
    id_ubicacion integer NOT NULL,
    id_bit_campo integer,
    id_cacerio integer,
    latitud character varying(50) NOT NULL,
    longitud character varying(50) NOT NULL,
    altitud character varying(50) NOT NULL,
    poblacion integer,
    cod_depto character varying(10),
    cod_mun character varying(10),
    cod_canton character varying(10)
);
    DROP TABLE public.ubicacion;
       public         postgres    false    3            �            1259    17244    ubicacion_id_ubicacion_seq    SEQUENCE     �   CREATE SEQUENCE public.ubicacion_id_ubicacion_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public.ubicacion_id_ubicacion_seq;
       public       postgres    false    3    249            �
           0    0    ubicacion_id_ubicacion_seq    SEQUENCE OWNED BY     Y   ALTER SEQUENCE public.ubicacion_id_ubicacion_seq OWNED BY public.ubicacion.id_ubicacion;
            public       postgres    false    250            �            1259    17246    usuario    TABLE     .  CREATE TABLE public.usuario (
    id_usuario integer NOT NULL,
    id_cargo integer,
    nom_usuario character varying(150) NOT NULL,
    clave character varying(200) NOT NULL,
    activo boolean NOT NULL,
    fecha_registro date,
    fecha_ultima_modificacion date,
    hash character varying(200)
);
    DROP TABLE public.usuario;
       public         postgres    false    3            �            1259    17252    usuario_id_usuario_seq    SEQUENCE        CREATE SEQUENCE public.usuario_id_usuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.usuario_id_usuario_seq;
       public       postgres    false    251    3            �
           0    0    usuario_id_usuario_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.usuario_id_usuario_seq OWNED BY public.usuario.id_usuario;
            public       postgres    false    252            �            1259    17254    usuario_lab    TABLE     k   CREATE TABLE public.usuario_lab (
    cod_laboratorio integer NOT NULL,
    id_usuario integer NOT NULL
);
    DROP TABLE public.usuario_lab;
       public         postgres    false    3            �            1259    17257    usuario_rol    TABLE     b   CREATE TABLE public.usuario_rol (
    id_rol integer NOT NULL,
    id_usuario integer NOT NULL
);
    DROP TABLE public.usuario_rol;
       public         postgres    false    3            �            1259    17260    usuario_rol_id_rol_seq    SEQUENCE        CREATE SEQUENCE public.usuario_rol_id_rol_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.usuario_rol_id_rol_seq;
       public       postgres    false    3    254            �
           0    0    usuario_rol_id_rol_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.usuario_rol_id_rol_seq OWNED BY public.usuario_rol.id_rol;
            public       postgres    false    255                        1259    17262    vector    TABLE     �   CREATE TABLE public.vector (
    cod_vector integer NOT NULL,
    nom_vector character varying(200) NOT NULL,
    desc_vector character varying(200),
    nom_cientifico character varying(50),
    id_familia integer
);
    DROP TABLE public.vector;
       public         postgres    false    3                       1259    17265    vector_cod_vector_seq    SEQUENCE     ~   CREATE SEQUENCE public.vector_cod_vector_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.vector_cod_vector_seq;
       public       postgres    false    256    3            �
           0    0    vector_cod_vector_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.vector_cod_vector_seq OWNED BY public.vector.cod_vector;
            public       postgres    false    257            �           2604    17267    actividad cod_actividad    DEFAULT     �   ALTER TABLE ONLY public.actividad ALTER COLUMN cod_actividad SET DEFAULT nextval('public.actividad_cod_actividad_seq'::regclass);
 F   ALTER TABLE public.actividad ALTER COLUMN cod_actividad DROP DEFAULT;
       public       postgres    false    186    185            �           2604    34135    actividad_01 id_actividad01    DEFAULT     �   ALTER TABLE ONLY public.actividad_01 ALTER COLUMN id_actividad01 SET DEFAULT nextval('public.actividad_01_id_actividad01_seq'::regclass);
 J   ALTER TABLE public.actividad_01 ALTER COLUMN id_actividad01 DROP DEFAULT;
       public       postgres    false    259    260    260            �           2604    34146 "   actividad_encabezado id_encabezado    DEFAULT     �   ALTER TABLE ONLY public.actividad_encabezado ALTER COLUMN id_encabezado SET DEFAULT nextval('public.actividad_encabezado_id_encabezado_seq'::regclass);
 Q   ALTER TABLE public.actividad_encabezado ALTER COLUMN id_encabezado DROP DEFAULT;
       public       postgres    false    261    262    262            �           2604    17268    analisis_lab id_analisis    DEFAULT     �   ALTER TABLE ONLY public.analisis_lab ALTER COLUMN id_analisis SET DEFAULT nextval('public.analisis_lab_id_analisis_seq'::regclass);
 G   ALTER TABLE public.analisis_lab ALTER COLUMN id_analisis DROP DEFAULT;
       public       postgres    false    188    187            �           2604    17269    animal id_animal    DEFAULT     t   ALTER TABLE ONLY public.animal ALTER COLUMN id_animal SET DEFAULT nextval('public.animal_id_animal_seq'::regclass);
 ?   ALTER TABLE public.animal ALTER COLUMN id_animal DROP DEFAULT;
       public       postgres    false    190    189            �           2604    17270    bitacora_campo id_bit_campo    DEFAULT     �   ALTER TABLE ONLY public.bitacora_campo ALTER COLUMN id_bit_campo SET DEFAULT nextval('public.bitacora_campo_id_bit_campo_seq'::regclass);
 J   ALTER TABLE public.bitacora_campo ALTER COLUMN id_bit_campo DROP DEFAULT;
       public       postgres    false    192    191            �           2604    17271    bitacora_lab cod_bit_lab    DEFAULT     �   ALTER TABLE ONLY public.bitacora_lab ALTER COLUMN cod_bit_lab SET DEFAULT nextval('public.bitacora_lab_cod_bit_lab_seq'::regclass);
 G   ALTER TABLE public.bitacora_lab ALTER COLUMN cod_bit_lab DROP DEFAULT;
       public       postgres    false    194    193            �           2604    17272    cacerio id_cacerio    DEFAULT     x   ALTER TABLE ONLY public.cacerio ALTER COLUMN id_cacerio SET DEFAULT nextval('public.cacerio_id_cacerio_seq'::regclass);
 A   ALTER TABLE public.cacerio ALTER COLUMN id_cacerio DROP DEFAULT;
       public       postgres    false    196    195            �           2604    17273    cargo id_cargo    DEFAULT     p   ALTER TABLE ONLY public.cargo ALTER COLUMN id_cargo SET DEFAULT nextval('public.cargo_id_cargo_seq'::regclass);
 =   ALTER TABLE public.cargo ALTER COLUMN id_cargo DROP DEFAULT;
       public       postgres    false    198    197            �           2604    17274    catalogo_anima id_cat_animal    DEFAULT     �   ALTER TABLE ONLY public.catalogo_anima ALTER COLUMN id_cat_animal SET DEFAULT nextval('public.catalogo_anima_id_cat_animal_seq'::regclass);
 K   ALTER TABLE public.catalogo_anima ALTER COLUMN id_cat_animal DROP DEFAULT;
       public       postgres    false    200    199            �           2604    17275    colonia_canton id_col_can    DEFAULT     �   ALTER TABLE ONLY public.colonia_canton ALTER COLUMN id_col_can SET DEFAULT nextval('public.colonia_canton_id_col_can_seq'::regclass);
 H   ALTER TABLE public.colonia_canton ALTER COLUMN id_col_can DROP DEFAULT;
       public       postgres    false    202    201            �           2604    17276    detalle id_detalle    DEFAULT     x   ALTER TABLE ONLY public.detalle ALTER COLUMN id_detalle SET DEFAULT nextval('public.detalle_id_detalle_seq'::regclass);
 A   ALTER TABLE public.detalle ALTER COLUMN id_detalle DROP DEFAULT;
       public       postgres    false    205    204            �           2604    17277    documento id_doc    DEFAULT     t   ALTER TABLE ONLY public.documento ALTER COLUMN id_doc SET DEFAULT nextval('public.documento_id_doc_seq'::regclass);
 ?   ALTER TABLE public.documento ALTER COLUMN id_doc DROP DEFAULT;
       public       postgres    false    207    206            �           2604    17278    encuesta id_encuesta    DEFAULT     |   ALTER TABLE ONLY public.encuesta ALTER COLUMN id_encuesta SET DEFAULT nextval('public.encuesta_id_encuesta_seq'::regclass);
 C   ALTER TABLE public.encuesta ALTER COLUMN id_encuesta DROP DEFAULT;
       public       postgres    false    209    208            �           2604    34248 
   familia id    DEFAULT     h   ALTER TABLE ONLY public.familia ALTER COLUMN id SET DEFAULT nextval('public.familia_id_seq'::regclass);
 9   ALTER TABLE public.familia ALTER COLUMN id DROP DEFAULT;
       public       postgres    false    265    266    266            �           2604    17279    inv_vector id_inv_vector    DEFAULT     �   ALTER TABLE ONLY public.inv_vector ALTER COLUMN id_inv_vector SET DEFAULT nextval('public.inv_vector_id_inv_vector_seq'::regclass);
 G   ALTER TABLE public.inv_vector ALTER COLUMN id_inv_vector DROP DEFAULT;
       public       postgres    false    211    210            �           2604    17280    investigacion cod_invest    DEFAULT     �   ALTER TABLE ONLY public.investigacion ALTER COLUMN cod_invest SET DEFAULT nextval('public.investigacion_cod_invest_seq'::regclass);
 G   ALTER TABLE public.investigacion ALTER COLUMN cod_invest DROP DEFAULT;
       public       postgres    false    213    212            �           2604    17281    laboratorio cod_laboratorio    DEFAULT     �   ALTER TABLE ONLY public.laboratorio ALTER COLUMN cod_laboratorio SET DEFAULT nextval('public.laboratorio_cod_laboratorio_seq'::regclass);
 J   ALTER TABLE public.laboratorio ALTER COLUMN cod_laboratorio DROP DEFAULT;
       public       postgres    false    217    216            �           2604    17282    lote id_lote    DEFAULT     l   ALTER TABLE ONLY public.lote ALTER COLUMN id_lote SET DEFAULT nextval('public.lote_id_lote_seq'::regclass);
 ;   ALTER TABLE public.lote ALTER COLUMN id_lote DROP DEFAULT;
       public       postgres    false    219    218            �           2604    17283    mantenimiento id_manto    DEFAULT     �   ALTER TABLE ONLY public.mantenimiento ALTER COLUMN id_manto SET DEFAULT nextval('public.mantenimiento_id_manto_seq'::regclass);
 E   ALTER TABLE public.mantenimiento ALTER COLUMN id_manto DROP DEFAULT;
       public       postgres    false    221    220            �           2604    17284    menu id_menu    DEFAULT     l   ALTER TABLE ONLY public.menu ALTER COLUMN id_menu SET DEFAULT nextval('public.menu_id_menu_seq'::regclass);
 ;   ALTER TABLE public.menu ALTER COLUMN id_menu DROP DEFAULT;
       public       postgres    false    223    222            �           2604    34240    orden id    DEFAULT     d   ALTER TABLE ONLY public.orden ALTER COLUMN id SET DEFAULT nextval('public.orden_id_seq'::regclass);
 7   ALTER TABLE public.orden ALTER COLUMN id DROP DEFAULT;
       public       postgres    false    263    264    264            �           2604    17285    perfil_inv id_perfil    DEFAULT     |   ALTER TABLE ONLY public.perfil_inv ALTER COLUMN id_perfil SET DEFAULT nextval('public.perfil_inv_id_perfil_seq'::regclass);
 C   ALTER TABLE public.perfil_inv ALTER COLUMN id_perfil DROP DEFAULT;
       public       postgres    false    228    227            �           2604    17286    permiso id_permiso    DEFAULT     x   ALTER TABLE ONLY public.permiso ALTER COLUMN id_permiso SET DEFAULT nextval('public.permiso_id_permiso_seq'::regclass);
 A   ALTER TABLE public.permiso ALTER COLUMN id_permiso DROP DEFAULT;
       public       postgres    false    230    229            �           2604    17287    persona id_persona    DEFAULT     x   ALTER TABLE ONLY public.persona ALTER COLUMN id_persona SET DEFAULT nextval('public.persona_id_persona_seq'::regclass);
 A   ALTER TABLE public.persona ALTER COLUMN id_persona DROP DEFAULT;
       public       postgres    false    232    231            �           2604    17288    pregunta id_pregunta    DEFAULT     |   ALTER TABLE ONLY public.pregunta ALTER COLUMN id_pregunta SET DEFAULT nextval('public.pregunta_id_pregunta_seq'::regclass);
 C   ALTER TABLE public.pregunta ALTER COLUMN id_pregunta DROP DEFAULT;
       public       postgres    false    234    233            �           2604    17289    respuesta id_respuesta    DEFAULT     �   ALTER TABLE ONLY public.respuesta ALTER COLUMN id_respuesta SET DEFAULT nextval('public.respuesta_id_respuesta_seq'::regclass);
 E   ALTER TABLE public.respuesta ALTER COLUMN id_respuesta DROP DEFAULT;
       public       postgres    false    237    236            �           2604    17290 
   rol id_rol    DEFAULT     h   ALTER TABLE ONLY public.rol ALTER COLUMN id_rol SET DEFAULT nextval('public.rol_id_rol_seq'::regclass);
 9   ALTER TABLE public.rol ALTER COLUMN id_rol DROP DEFAULT;
       public       postgres    false    239    238            �           2604    17291    tipo_muestra id_tipo_mues    DEFAULT     �   ALTER TABLE ONLY public.tipo_muestra ALTER COLUMN id_tipo_mues SET DEFAULT nextval('public.tipo_muestra_id_tipo_mues_seq'::regclass);
 H   ALTER TABLE public.tipo_muestra ALTER COLUMN id_tipo_mues DROP DEFAULT;
       public       postgres    false    242    241            �           2604    17292    tipo_prueba id_tipo_prueba    DEFAULT     �   ALTER TABLE ONLY public.tipo_prueba ALTER COLUMN id_tipo_prueba SET DEFAULT nextval('public.tipo_prueba_id_tipo_prueba_seq'::regclass);
 I   ALTER TABLE public.tipo_prueba ALTER COLUMN id_tipo_prueba DROP DEFAULT;
       public       postgres    false    244    243            �           2604    17293    tipo_respuesta id_tipo_resp    DEFAULT     �   ALTER TABLE ONLY public.tipo_respuesta ALTER COLUMN id_tipo_resp SET DEFAULT nextval('public.tipo_respuesta_id_tipo_resp_seq'::regclass);
 J   ALTER TABLE public.tipo_respuesta ALTER COLUMN id_tipo_resp DROP DEFAULT;
       public       postgres    false    246    245            �           2604    17294    tratamiento id_tratamiento    DEFAULT     �   ALTER TABLE ONLY public.tratamiento ALTER COLUMN id_tratamiento SET DEFAULT nextval('public.tratamiento_id_tratamiento_seq'::regclass);
 I   ALTER TABLE public.tratamiento ALTER COLUMN id_tratamiento DROP DEFAULT;
       public       postgres    false    248    247            �           2604    17295    ubicacion id_ubicacion    DEFAULT     �   ALTER TABLE ONLY public.ubicacion ALTER COLUMN id_ubicacion SET DEFAULT nextval('public.ubicacion_id_ubicacion_seq'::regclass);
 E   ALTER TABLE public.ubicacion ALTER COLUMN id_ubicacion DROP DEFAULT;
       public       postgres    false    250    249            �           2604    17296    usuario id_usuario    DEFAULT     x   ALTER TABLE ONLY public.usuario ALTER COLUMN id_usuario SET DEFAULT nextval('public.usuario_id_usuario_seq'::regclass);
 A   ALTER TABLE public.usuario ALTER COLUMN id_usuario DROP DEFAULT;
       public       postgres    false    252    251            �           2604    17297    usuario_rol id_rol    DEFAULT     x   ALTER TABLE ONLY public.usuario_rol ALTER COLUMN id_rol SET DEFAULT nextval('public.usuario_rol_id_rol_seq'::regclass);
 A   ALTER TABLE public.usuario_rol ALTER COLUMN id_rol DROP DEFAULT;
       public       postgres    false    255    254            �           2604    17298    vector cod_vector    DEFAULT     v   ALTER TABLE ONLY public.vector ALTER COLUMN cod_vector SET DEFAULT nextval('public.vector_cod_vector_seq'::regclass);
 @   ALTER TABLE public.vector ALTER COLUMN cod_vector DROP DEFAULT;
       public       postgres    false    257    256            W
          0    17061 	   actividad 
   TABLE DATA               ]   COPY public.actividad (cod_actividad, cod_invest, nom_actividad, desc_actividad) FROM stdin;
    public       postgres    false    185   C      �
          0    34132    actividad_01 
   TABLE DATA               �   COPY public.actividad_01 (id_actividad01, ident_animal, toma_glucemia30, toma_glucemia60, toma_glucemia120, toma_glucemia180, promedio, id_encabezado, usuario, fecha_registro) FROM stdin;
    public       postgres    false    260   `      �
           0    0    actividad_01_id_actividad01_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.actividad_01_id_actividad01_seq', 1, true);
            public       postgres    false    259            �
           0    0    actividad_cod_actividad_seq    SEQUENCE SET     J   SELECT pg_catalog.setval('public.actividad_cod_actividad_seq', 1, false);
            public       postgres    false    186            �
          0    34143    actividad_encabezado 
   TABLE DATA               �  COPY public.actividad_encabezado (id_encabezado, hash, buena, centinela, cepa, concentracion_de_dosis, control, control_1, descripcion, edad, edad_inicial, especie, fecha, fecha_de_necropcia, fecha_final, fecha_inicio, grupo, lapso_de_tiempo, mala, metodo_eutanasico, n_del_los_animales, n_jaula, no, no_programada, observaciones_enc, observaciones_pie, patron, patron_2, periodo, peso, programada, protocolo, regular, responsable_de_prueba, semana_n, sexo, si, sustancia_de_ensayo, tiempo_de_exposicion, tiempo_de_observacion, tratamiento, tratamiento_3, tratamiento_4, tratamiento_5, tratamiento_6, via_de_administracion, fecha_registro, nombreinvestigacion, codigoinvestigacion, usuario) FROM stdin;
    public       postgres    false    262   �      �
           0    0 &   actividad_encabezado_id_encabezado_seq    SEQUENCE SET     T   SELECT pg_catalog.setval('public.actividad_encabezado_id_encabezado_seq', 1, true);
            public       postgres    false    261            Y
          0    17066    analisis_lab 
   TABLE DATA               |   COPY public.analisis_lab (id_analisis, codigo_muestra, cod_bit_lab, id_tipo_prueba, fecha_analisis, estado_ana) FROM stdin;
    public       postgres    false    187   ?      �
           0    0    analisis_lab_id_analisis_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public.analisis_lab_id_analisis_seq', 1, false);
            public       postgres    false    188            [
          0    17071    animal 
   TABLE DATA               V   COPY public.animal (id_animal, id_cat_animal, id_analisis, estado_animal) FROM stdin;
    public       postgres    false    189   \      �
           0    0    animal_id_animal_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.animal_id_animal_seq', 1, false);
            public       postgres    false    190            ]
          0    17076    bitacora_campo 
   TABLE DATA               �   COPY public.bitacora_campo (id_bit_campo, bit_id_bit_campo, id_inv_vector, fecha_campo, codigo_bitacora, descripcion_bitacora) FROM stdin;
    public       postgres    false    191   y      �
           0    0    bitacora_campo_id_bit_campo_seq    SEQUENCE SET     N   SELECT pg_catalog.setval('public.bitacora_campo_id_bit_campo_seq', 30, true);
            public       postgres    false    192            _
          0    17081    bitacora_lab 
   TABLE DATA               o   COPY public.bitacora_lab (cod_bit_lab, cod_actividad, id_inv_vector, bit_cod_bit_lab, nom_bit_lab) FROM stdin;
    public       postgres    false    193   w      �
           0    0    bitacora_lab_cod_bit_lab_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public.bitacora_lab_cod_bit_lab_seq', 1, false);
            public       postgres    false    194            a
          0    17086    cacerio 
   TABLE DATA               F   COPY public.cacerio (id_cacerio, id_col_can, nom_cacerio) FROM stdin;
    public       postgres    false    195   �      �
           0    0    cacerio_id_cacerio_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.cacerio_id_cacerio_seq', 1, false);
            public       postgres    false    196            c
          0    17091    cargo 
   TABLE DATA               e   COPY public.cargo (id_cargo, cargo, descripcion, fecha_creacion, activo, fecha_modifica) FROM stdin;
    public       postgres    false    197   �      �
           0    0    cargo_id_cargo_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.cargo_id_cargo_seq', 8, true);
            public       postgres    false    198            e
          0    17096    catalogo_anima 
   TABLE DATA               Q   COPY public.catalogo_anima (id_cat_animal, nom_animal, dispo_animal) FROM stdin;
    public       postgres    false    199   �      �
           0    0     catalogo_anima_id_cat_animal_seq    SEQUENCE SET     O   SELECT pg_catalog.setval('public.catalogo_anima_id_cat_animal_seq', 1, false);
            public       postgres    false    200            g
          0    17101    colonia_canton 
   TABLE DATA               R   COPY public.colonia_canton (id_col_can, cod_municipio, nom_ubicacion) FROM stdin;
    public       postgres    false    201   �      �
           0    0    colonia_canton_id_col_can_seq    SEQUENCE SET     L   SELECT pg_catalog.setval('public.colonia_canton_id_col_can_seq', 1, false);
            public       postgres    false    202            i
          0    17106    departamento 
   TABLE DATA               <   COPY public.departamento (cod_depto, nom_depto) FROM stdin;
    public       postgres    false    203   ]      j
          0    17109    detalle 
   TABLE DATA               L   COPY public.detalle (id_detalle, id_respuesta, opcion_repuesta) FROM stdin;
    public       postgres    false    204   �]      �
           0    0    detalle_id_detalle_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.detalle_id_detalle_seq', 1, false);
            public       postgres    false    205            l
          0    17114 	   documento 
   TABLE DATA               n   COPY public.documento (id_doc, id_perfil, doc_id_doc, titulo_doc, desc_doc, ruta_url, estado_doc) FROM stdin;
    public       postgres    false    206   �]      �
           0    0    documento_id_doc_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.documento_id_doc_seq', 1, false);
            public       postgres    false    207            n
          0    17122    encuesta 
   TABLE DATA               K   COPY public.encuesta (id_encuesta, id_bit_campo, nom_encuesta) FROM stdin;
    public       postgres    false    208   �]      �
           0    0    encuesta_id_encuesta_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.encuesta_id_encuesta_seq', 1, false);
            public       postgres    false    209            �
          0    34245    familia 
   TABLE DATA               ?   COPY public.familia (id, nombre_familia, id_orden) FROM stdin;
    public       postgres    false    266   ^      �
           0    0    familia_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.familia_id_seq', 1, false);
            public       postgres    false    265            p
          0    17127 
   inv_vector 
   TABLE DATA               x   COPY public.inv_vector (id_inv_vector, cod_vector, cod_invest, titulo_inv, desc_inv_vector, fecha_registro) FROM stdin;
    public       postgres    false    210   F^      �
           0    0    inv_vector_id_inv_vector_seq    SEQUENCE SET     J   SELECT pg_catalog.setval('public.inv_vector_id_inv_vector_seq', 5, true);
            public       postgres    false    211            r
          0    17132    investigacion 
   TABLE DATA               �   COPY public.investigacion (cod_invest, cod_laboratorio, inv_cod_invest, nom_invest, estado_invest, anio, mes, fecha_creacion, activo) FROM stdin;
    public       postgres    false    212   �^      �
           0    0    investigacion_cod_invest_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public.investigacion_cod_invest_seq', 10, true);
            public       postgres    false    213            t
          0    17137    investigacion_investigador 
   TABLE DATA               H   COPY public.investigacion_investigador (cod_invest, id_inv) FROM stdin;
    public       postgres    false    214   v_      u
          0    17140    investigador 
   TABLE DATA               A   COPY public.investigador (id_inv, nom_inv, tipo_inv) FROM stdin;
    public       postgres    false    215   �_      v
          0    17143    laboratorio 
   TABLE DATA               F   COPY public.laboratorio (cod_laboratorio, nom_lab, alias) FROM stdin;
    public       postgres    false    216   �_      �
           0    0    laboratorio_cod_laboratorio_seq    SEQUENCE SET     N   SELECT pg_catalog.setval('public.laboratorio_cod_laboratorio_seq', 1, false);
            public       postgres    false    217            x
          0    17148    lote 
   TABLE DATA               �   COPY public.lote (id_lote, lot_id_lote, cod_bit_lab, fecha_creacion, fecha_modificacion, estatus, nombre_lote, num_muestras, id_vector, id_colcan) FROM stdin;
    public       postgres    false    218   �_      �
           0    0    lote_id_lote_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.lote_id_lote_seq', 2, true);
            public       postgres    false    219            z
          0    17153    mantenimiento 
   TABLE DATA               �   COPY public.mantenimiento (id_manto, id_lote, id_preservante, fecha_manto, completado_manto, fecha_prox_manto, numero_mantenimiento) FROM stdin;
    public       postgres    false    220   7`      �
           0    0    mantenimiento_id_manto_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.mantenimiento_id_manto_seq', 2, true);
            public       postgres    false    221            |
          0    17158    menu 
   TABLE DATA               ^   COPY public.menu (id_menu, men_id_menu, nom_menu, ruta, icono, orden, disponible) FROM stdin;
    public       postgres    false    222   x`      �
           0    0    menu_id_menu_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.menu_id_menu_seq', 2, true);
            public       postgres    false    223            ~
          0    17167    menu_permiso 
   TABLE DATA               ;   COPY public.menu_permiso (id_menu, id_permiso) FROM stdin;
    public       postgres    false    224   �`      
          0    17170    muestra 
   TABLE DATA                 COPY public.muestra (codigo_muestra, id_lote, id_tipo_mues, id_bit_campo, nom_jefe_fam, edad_muestra, telefono_muestra, fecha_trabajo, fecha_muestra, numero_muestra, genero_muestra, motivo_muestra, secuencia, nom_cientifico, familia_muestra, parasito) FROM stdin;
    public       postgres    false    225   �`      �
          0    17173 	   municipio 
   TABLE DATA               L   COPY public.municipio (cod_municipio, cod_depto, nom_municipio) FROM stdin;
    public       postgres    false    226   c      �
          0    34237    orden 
   TABLE DATA               1   COPY public.orden (id, nombre_orden) FROM stdin;
    public       postgres    false    264   	m      �
           0    0    orden_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.orden_id_seq', 1, false);
            public       postgres    false    263            �
          0    17176 
   perfil_inv 
   TABLE DATA               �   COPY public.perfil_inv (id_perfil, per_id_perfil, cod_invest, antecedente, justificacion, planteamiento, obj_general, obj_especifico, metodologia, desc_resul, presupuesto, numero_persona) FROM stdin;
    public       postgres    false    227   ?m      �
           0    0    perfil_inv_id_perfil_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.perfil_inv_id_perfil_seq', 10, true);
            public       postgres    false    228            �
          0    17184    permiso 
   TABLE DATA               J   COPY public.permiso (id_permiso, nom_permiso, estado_permiso) FROM stdin;
    public       postgres    false    229   �n      �
           0    0    permiso_id_permiso_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.permiso_id_permiso_seq', 1, false);
            public       postgres    false    230            �
          0    17189    persona 
   TABLE DATA                 COPY public.persona (id_persona, id_usuario, id_encuesta, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, fecha_nacimiento, fecha_registro, dui, nit, correo_persona, direccion_residencia, telefono_persona, municipio, fecha_ultima_modificacion) FROM stdin;
    public       postgres    false    231   �n      �
           0    0    persona_id_persona_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.persona_id_persona_seq', 9, true);
            public       postgres    false    232            �
          0    17197    pregunta 
   TABLE DATA               K   COPY public.pregunta (id_pregunta, id_encuesta, desc_pregunta) FROM stdin;
    public       postgres    false    233   �o      �
           0    0    pregunta_id_pregunta_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.pregunta_id_pregunta_seq', 1, false);
            public       postgres    false    234            �
          0    17202    preservante 
   TABLE DATA               X   COPY public.preservante (id_preservante, nom_preservante, desc_preservante) FROM stdin;
    public       postgres    false    235   �o      �
          0    17205 	   respuesta 
   TABLE DATA               ^   COPY public.respuesta (id_respuesta, id_pregunta, id_tipo_resp, opcion_respuesta) FROM stdin;
    public       postgres    false    236   p      �
           0    0    respuesta_id_respuesta_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('public.respuesta_id_respuesta_seq', 1, false);
            public       postgres    false    237            �
          0    17210    rol 
   TABLE DATA               G   COPY public.rol (id_rol, nom_rol, descripcion, estado_rol) FROM stdin;
    public       postgres    false    238   3p      �
           0    0    rol_id_rol_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.rol_id_rol_seq', 8, true);
            public       postgres    false    239            �
          0    17742    rol_menu 
   TABLE DATA               3   COPY public.rol_menu (id_rol, id_menu) FROM stdin;
    public       postgres    false    258   Cr      �
          0    17215    rol_permiso 
   TABLE DATA               9   COPY public.rol_permiso (id_permiso, id_rol) FROM stdin;
    public       postgres    false    240   hr      �
          0    17218    tipo_muestra 
   TABLE DATA               A   COPY public.tipo_muestra (id_tipo_mues, nom_muestra) FROM stdin;
    public       postgres    false    241   �r      �
           0    0    tipo_muestra_id_tipo_mues_seq    SEQUENCE SET     L   SELECT pg_catalog.setval('public.tipo_muestra_id_tipo_mues_seq', 1, false);
            public       postgres    false    242            �
          0    17223    tipo_prueba 
   TABLE DATA               N   COPY public.tipo_prueba (id_tipo_prueba, nom_prueba, estado_disp) FROM stdin;
    public       postgres    false    243   �r      �
           0    0    tipo_prueba_id_tipo_prueba_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.tipo_prueba_id_tipo_prueba_seq', 1, false);
            public       postgres    false    244            �
          0    17228    tipo_respuesta 
   TABLE DATA               D   COPY public.tipo_respuesta (id_tipo_resp, detalle_resp) FROM stdin;
    public       postgres    false    245   �r      �
           0    0    tipo_respuesta_id_tipo_resp_seq    SEQUENCE SET     N   SELECT pg_catalog.setval('public.tipo_respuesta_id_tipo_resp_seq', 1, false);
            public       postgres    false    246            �
          0    17233    tratamiento 
   TABLE DATA               ~   COPY public.tratamiento (id_tratamiento, id_analisis, desc_tratamiento, resultado_tratamiento, fecha_tratamiento) FROM stdin;
    public       postgres    false    247   �r      �
           0    0    tratamiento_id_tratamiento_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.tratamiento_id_tratamiento_seq', 1, false);
            public       postgres    false    248            �
          0    17241 	   ubicacion 
   TABLE DATA               �   COPY public.ubicacion (id_ubicacion, id_bit_campo, id_cacerio, latitud, longitud, altitud, poblacion, cod_depto, cod_mun, cod_canton) FROM stdin;
    public       postgres    false    249   
s      �
           0    0    ubicacion_id_ubicacion_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.ubicacion_id_ubicacion_seq', 2, true);
            public       postgres    false    250            �
          0    17246    usuario 
   TABLE DATA               �   COPY public.usuario (id_usuario, id_cargo, nom_usuario, clave, activo, fecha_registro, fecha_ultima_modificacion, hash) FROM stdin;
    public       postgres    false    251   Rs      �
           0    0    usuario_id_usuario_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.usuario_id_usuario_seq', 14, true);
            public       postgres    false    252            �
          0    17254    usuario_lab 
   TABLE DATA               B   COPY public.usuario_lab (cod_laboratorio, id_usuario) FROM stdin;
    public       postgres    false    253   �t      �
          0    17257    usuario_rol 
   TABLE DATA               9   COPY public.usuario_rol (id_rol, id_usuario) FROM stdin;
    public       postgres    false    254   �t      �
           0    0    usuario_rol_id_rol_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.usuario_rol_id_rol_seq', 1, false);
            public       postgres    false    255            �
          0    17262    vector 
   TABLE DATA               a   COPY public.vector (cod_vector, nom_vector, desc_vector, nom_cientifico, id_familia) FROM stdin;
    public       postgres    false    256   u      �
           0    0    vector_cod_vector_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.vector_cod_vector_seq', 1, false);
            public       postgres    false    257            �	           2606    34140    actividad_01 actividad_01_pk 
   CONSTRAINT     f   ALTER TABLE ONLY public.actividad_01
    ADD CONSTRAINT actividad_01_pk PRIMARY KEY (id_actividad01);
 F   ALTER TABLE ONLY public.actividad_01 DROP CONSTRAINT actividad_01_pk;
       public         postgres    false    260    260            �	           2606    34151 ,   actividad_encabezado actividad_encabezado_pk 
   CONSTRAINT     u   ALTER TABLE ONLY public.actividad_encabezado
    ADD CONSTRAINT actividad_encabezado_pk PRIMARY KEY (id_encabezado);
 V   ALTER TABLE ONLY public.actividad_encabezado DROP CONSTRAINT actividad_encabezado_pk;
       public         postgres    false    262    262            �	           2606    34250    familia familia_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.familia
    ADD CONSTRAINT familia_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.familia DROP CONSTRAINT familia_pkey;
       public         postgres    false    266    266            �	           2606    34242    orden orden_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.orden
    ADD CONSTRAINT orden_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.orden DROP CONSTRAINT orden_pkey;
       public         postgres    false    264    264            �           2606    17300    actividad pk_actividad 
   CONSTRAINT     _   ALTER TABLE ONLY public.actividad
    ADD CONSTRAINT pk_actividad PRIMARY KEY (cod_actividad);
 @   ALTER TABLE ONLY public.actividad DROP CONSTRAINT pk_actividad;
       public         postgres    false    185    185            	           2606    17302    analisis_lab pk_analisis_lab 
   CONSTRAINT     c   ALTER TABLE ONLY public.analisis_lab
    ADD CONSTRAINT pk_analisis_lab PRIMARY KEY (id_analisis);
 F   ALTER TABLE ONLY public.analisis_lab DROP CONSTRAINT pk_analisis_lab;
       public         postgres    false    187    187            	           2606    17304    animal pk_animal 
   CONSTRAINT     U   ALTER TABLE ONLY public.animal
    ADD CONSTRAINT pk_animal PRIMARY KEY (id_animal);
 :   ALTER TABLE ONLY public.animal DROP CONSTRAINT pk_animal;
       public         postgres    false    189    189            	           2606    17306     bitacora_campo pk_bitacora_campo 
   CONSTRAINT     h   ALTER TABLE ONLY public.bitacora_campo
    ADD CONSTRAINT pk_bitacora_campo PRIMARY KEY (id_bit_campo);
 J   ALTER TABLE ONLY public.bitacora_campo DROP CONSTRAINT pk_bitacora_campo;
       public         postgres    false    191    191            	           2606    17308    bitacora_lab pk_bitacora_lab 
   CONSTRAINT     c   ALTER TABLE ONLY public.bitacora_lab
    ADD CONSTRAINT pk_bitacora_lab PRIMARY KEY (cod_bit_lab);
 F   ALTER TABLE ONLY public.bitacora_lab DROP CONSTRAINT pk_bitacora_lab;
       public         postgres    false    193    193            	           2606    17310    cacerio pk_cacerio 
   CONSTRAINT     X   ALTER TABLE ONLY public.cacerio
    ADD CONSTRAINT pk_cacerio PRIMARY KEY (id_cacerio);
 <   ALTER TABLE ONLY public.cacerio DROP CONSTRAINT pk_cacerio;
       public         postgres    false    195    195            	           2606    17312    cargo pk_cargo 
   CONSTRAINT     R   ALTER TABLE ONLY public.cargo
    ADD CONSTRAINT pk_cargo PRIMARY KEY (id_cargo);
 8   ALTER TABLE ONLY public.cargo DROP CONSTRAINT pk_cargo;
       public         postgres    false    197    197            	           2606    17314     catalogo_anima pk_catalogo_anima 
   CONSTRAINT     i   ALTER TABLE ONLY public.catalogo_anima
    ADD CONSTRAINT pk_catalogo_anima PRIMARY KEY (id_cat_animal);
 J   ALTER TABLE ONLY public.catalogo_anima DROP CONSTRAINT pk_catalogo_anima;
       public         postgres    false    199    199            !	           2606    17316     colonia_canton pk_colonia_canton 
   CONSTRAINT     f   ALTER TABLE ONLY public.colonia_canton
    ADD CONSTRAINT pk_colonia_canton PRIMARY KEY (id_col_can);
 J   ALTER TABLE ONLY public.colonia_canton DROP CONSTRAINT pk_colonia_canton;
       public         postgres    false    201    201            $	           2606    17318    departamento pk_departamento 
   CONSTRAINT     a   ALTER TABLE ONLY public.departamento
    ADD CONSTRAINT pk_departamento PRIMARY KEY (cod_depto);
 F   ALTER TABLE ONLY public.departamento DROP CONSTRAINT pk_departamento;
       public         postgres    false    203    203            '	           2606    17320    detalle pk_detalle 
   CONSTRAINT     X   ALTER TABLE ONLY public.detalle
    ADD CONSTRAINT pk_detalle PRIMARY KEY (id_detalle);
 <   ALTER TABLE ONLY public.detalle DROP CONSTRAINT pk_detalle;
       public         postgres    false    204    204            -	           2606    17322    documento pk_documento 
   CONSTRAINT     X   ALTER TABLE ONLY public.documento
    ADD CONSTRAINT pk_documento PRIMARY KEY (id_doc);
 @   ALTER TABLE ONLY public.documento DROP CONSTRAINT pk_documento;
       public         postgres    false    206    206            1	           2606    17324    encuesta pk_encuesta 
   CONSTRAINT     [   ALTER TABLE ONLY public.encuesta
    ADD CONSTRAINT pk_encuesta PRIMARY KEY (id_encuesta);
 >   ALTER TABLE ONLY public.encuesta DROP CONSTRAINT pk_encuesta;
       public         postgres    false    208    208            5	           2606    17326    inv_vector pk_inv_vector 
   CONSTRAINT     a   ALTER TABLE ONLY public.inv_vector
    ADD CONSTRAINT pk_inv_vector PRIMARY KEY (id_inv_vector);
 B   ALTER TABLE ONLY public.inv_vector DROP CONSTRAINT pk_inv_vector;
       public         postgres    false    210    210            :	           2606    17328    investigacion pk_investigacion 
   CONSTRAINT     d   ALTER TABLE ONLY public.investigacion
    ADD CONSTRAINT pk_investigacion PRIMARY KEY (cod_invest);
 H   ALTER TABLE ONLY public.investigacion DROP CONSTRAINT pk_investigacion;
       public         postgres    false    212    212            ?	           2606    17330 8   investigacion_investigador pk_investigacion_investigador 
   CONSTRAINT     �   ALTER TABLE ONLY public.investigacion_investigador
    ADD CONSTRAINT pk_investigacion_investigador PRIMARY KEY (cod_invest, id_inv);
 b   ALTER TABLE ONLY public.investigacion_investigador DROP CONSTRAINT pk_investigacion_investigador;
       public         postgres    false    214    214    214            B	           2606    17332    investigador pk_investigador 
   CONSTRAINT     ^   ALTER TABLE ONLY public.investigador
    ADD CONSTRAINT pk_investigador PRIMARY KEY (id_inv);
 F   ALTER TABLE ONLY public.investigador DROP CONSTRAINT pk_investigador;
       public         postgres    false    215    215            E	           2606    17334    laboratorio pk_laboratorio 
   CONSTRAINT     e   ALTER TABLE ONLY public.laboratorio
    ADD CONSTRAINT pk_laboratorio PRIMARY KEY (cod_laboratorio);
 D   ALTER TABLE ONLY public.laboratorio DROP CONSTRAINT pk_laboratorio;
       public         postgres    false    216    216            J	           2606    17336    lote pk_lote 
   CONSTRAINT     O   ALTER TABLE ONLY public.lote
    ADD CONSTRAINT pk_lote PRIMARY KEY (id_lote);
 6   ALTER TABLE ONLY public.lote DROP CONSTRAINT pk_lote;
       public         postgres    false    218    218            N	           2606    17338    mantenimiento pk_mantenimiento 
   CONSTRAINT     b   ALTER TABLE ONLY public.mantenimiento
    ADD CONSTRAINT pk_mantenimiento PRIMARY KEY (id_manto);
 H   ALTER TABLE ONLY public.mantenimiento DROP CONSTRAINT pk_mantenimiento;
       public         postgres    false    220    220            R	           2606    17340    menu pk_menu 
   CONSTRAINT     O   ALTER TABLE ONLY public.menu
    ADD CONSTRAINT pk_menu PRIMARY KEY (id_menu);
 6   ALTER TABLE ONLY public.menu DROP CONSTRAINT pk_menu;
       public         postgres    false    222    222            W	           2606    17342    menu_permiso pk_menu_permiso 
   CONSTRAINT     k   ALTER TABLE ONLY public.menu_permiso
    ADD CONSTRAINT pk_menu_permiso PRIMARY KEY (id_menu, id_permiso);
 F   ALTER TABLE ONLY public.menu_permiso DROP CONSTRAINT pk_menu_permiso;
       public         postgres    false    224    224    224            \	           2606    17344    muestra pk_muestra 
   CONSTRAINT     \   ALTER TABLE ONLY public.muestra
    ADD CONSTRAINT pk_muestra PRIMARY KEY (codigo_muestra);
 <   ALTER TABLE ONLY public.muestra DROP CONSTRAINT pk_muestra;
       public         postgres    false    225    225            a	           2606    17346    municipio pk_municipio 
   CONSTRAINT     _   ALTER TABLE ONLY public.municipio
    ADD CONSTRAINT pk_municipio PRIMARY KEY (cod_municipio);
 @   ALTER TABLE ONLY public.municipio DROP CONSTRAINT pk_municipio;
       public         postgres    false    226    226            e	           2606    17348    perfil_inv pk_perfil_inv 
   CONSTRAINT     ]   ALTER TABLE ONLY public.perfil_inv
    ADD CONSTRAINT pk_perfil_inv PRIMARY KEY (id_perfil);
 B   ALTER TABLE ONLY public.perfil_inv DROP CONSTRAINT pk_perfil_inv;
       public         postgres    false    227    227            h	           2606    17350    permiso pk_permiso 
   CONSTRAINT     X   ALTER TABLE ONLY public.permiso
    ADD CONSTRAINT pk_permiso PRIMARY KEY (id_permiso);
 <   ALTER TABLE ONLY public.permiso DROP CONSTRAINT pk_permiso;
       public         postgres    false    229    229            k	           2606    17352    persona pk_persona 
   CONSTRAINT     X   ALTER TABLE ONLY public.persona
    ADD CONSTRAINT pk_persona PRIMARY KEY (id_persona);
 <   ALTER TABLE ONLY public.persona DROP CONSTRAINT pk_persona;
       public         postgres    false    231    231            o	           2606    17354    pregunta pk_pregunta 
   CONSTRAINT     [   ALTER TABLE ONLY public.pregunta
    ADD CONSTRAINT pk_pregunta PRIMARY KEY (id_pregunta);
 >   ALTER TABLE ONLY public.pregunta DROP CONSTRAINT pk_pregunta;
       public         postgres    false    233    233            r	           2606    17356    preservante pk_preservante 
   CONSTRAINT     d   ALTER TABLE ONLY public.preservante
    ADD CONSTRAINT pk_preservante PRIMARY KEY (id_preservante);
 D   ALTER TABLE ONLY public.preservante DROP CONSTRAINT pk_preservante;
       public         postgres    false    235    235            u	           2606    17358    respuesta pk_respuesta 
   CONSTRAINT     ^   ALTER TABLE ONLY public.respuesta
    ADD CONSTRAINT pk_respuesta PRIMARY KEY (id_respuesta);
 @   ALTER TABLE ONLY public.respuesta DROP CONSTRAINT pk_respuesta;
       public         postgres    false    236    236            z	           2606    17360 
   rol pk_rol 
   CONSTRAINT     L   ALTER TABLE ONLY public.rol
    ADD CONSTRAINT pk_rol PRIMARY KEY (id_rol);
 4   ALTER TABLE ONLY public.rol DROP CONSTRAINT pk_rol;
       public         postgres    false    238    238            }	           2606    17362    rol_permiso pk_rol_permiso 
   CONSTRAINT     h   ALTER TABLE ONLY public.rol_permiso
    ADD CONSTRAINT pk_rol_permiso PRIMARY KEY (id_permiso, id_rol);
 D   ALTER TABLE ONLY public.rol_permiso DROP CONSTRAINT pk_rol_permiso;
       public         postgres    false    240    240    240            �	           2606    17364    tipo_muestra pk_tipo_muestra 
   CONSTRAINT     d   ALTER TABLE ONLY public.tipo_muestra
    ADD CONSTRAINT pk_tipo_muestra PRIMARY KEY (id_tipo_mues);
 F   ALTER TABLE ONLY public.tipo_muestra DROP CONSTRAINT pk_tipo_muestra;
       public         postgres    false    241    241            �	           2606    17366    tipo_prueba pk_tipo_prueba 
   CONSTRAINT     d   ALTER TABLE ONLY public.tipo_prueba
    ADD CONSTRAINT pk_tipo_prueba PRIMARY KEY (id_tipo_prueba);
 D   ALTER TABLE ONLY public.tipo_prueba DROP CONSTRAINT pk_tipo_prueba;
       public         postgres    false    243    243            �	           2606    17368     tipo_respuesta pk_tipo_respuesta 
   CONSTRAINT     h   ALTER TABLE ONLY public.tipo_respuesta
    ADD CONSTRAINT pk_tipo_respuesta PRIMARY KEY (id_tipo_resp);
 J   ALTER TABLE ONLY public.tipo_respuesta DROP CONSTRAINT pk_tipo_respuesta;
       public         postgres    false    245    245            �	           2606    17370    tratamiento pk_tratamiento 
   CONSTRAINT     d   ALTER TABLE ONLY public.tratamiento
    ADD CONSTRAINT pk_tratamiento PRIMARY KEY (id_tratamiento);
 D   ALTER TABLE ONLY public.tratamiento DROP CONSTRAINT pk_tratamiento;
       public         postgres    false    247    247            �	           2606    17372    ubicacion pk_ubicacion 
   CONSTRAINT     ^   ALTER TABLE ONLY public.ubicacion
    ADD CONSTRAINT pk_ubicacion PRIMARY KEY (id_ubicacion);
 @   ALTER TABLE ONLY public.ubicacion DROP CONSTRAINT pk_ubicacion;
       public         postgres    false    249    249            �	           2606    17374    usuario pk_usuario 
   CONSTRAINT     X   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT pk_usuario PRIMARY KEY (id_usuario);
 <   ALTER TABLE ONLY public.usuario DROP CONSTRAINT pk_usuario;
       public         postgres    false    251    251            �	           2606    17376    usuario_lab pk_usuario_lab 
   CONSTRAINT     q   ALTER TABLE ONLY public.usuario_lab
    ADD CONSTRAINT pk_usuario_lab PRIMARY KEY (cod_laboratorio, id_usuario);
 D   ALTER TABLE ONLY public.usuario_lab DROP CONSTRAINT pk_usuario_lab;
       public         postgres    false    253    253    253            �	           2606    17378    usuario_rol pk_usuario_rol 
   CONSTRAINT     h   ALTER TABLE ONLY public.usuario_rol
    ADD CONSTRAINT pk_usuario_rol PRIMARY KEY (id_rol, id_usuario);
 D   ALTER TABLE ONLY public.usuario_rol DROP CONSTRAINT pk_usuario_rol;
       public         postgres    false    254    254    254            �	           2606    17380    vector pk_vector 
   CONSTRAINT     V   ALTER TABLE ONLY public.vector
    ADD CONSTRAINT pk_vector PRIMARY KEY (cod_vector);
 :   ALTER TABLE ONLY public.vector DROP CONSTRAINT pk_vector;
       public         postgres    false    256    256            �           1259    17381    actividad_pk    INDEX     R   CREATE UNIQUE INDEX actividad_pk ON public.actividad USING btree (cod_actividad);
     DROP INDEX public.actividad_pk;
       public         postgres    false    185            	           1259    17382    actividad_vector_fk    INDEX     U   CREATE INDEX actividad_vector_fk ON public.bitacora_lab USING btree (cod_actividad);
 '   DROP INDEX public.actividad_vector_fk;
       public         postgres    false    193            	           1259    17383    analisis_animal_fk    INDEX     L   CREATE INDEX analisis_animal_fk ON public.animal USING btree (id_analisis);
 &   DROP INDEX public.analisis_animal_fk;
       public         postgres    false    189            �           1259    17384    analisis_lab_pk    INDEX     V   CREATE UNIQUE INDEX analisis_lab_pk ON public.analisis_lab USING btree (id_analisis);
 #   DROP INDEX public.analisis_lab_pk;
       public         postgres    false    187            �           1259    17385    analisis_muestra_fk    INDEX     V   CREATE INDEX analisis_muestra_fk ON public.analisis_lab USING btree (codigo_muestra);
 '   DROP INDEX public.analisis_muestra_fk;
       public         postgres    false    187            �	           1259    17386    analisis_tratamiento_fk    INDEX     V   CREATE INDEX analisis_tratamiento_fk ON public.tratamiento USING btree (id_analisis);
 +   DROP INDEX public.analisis_tratamiento_fk;
       public         postgres    false    247            	           1259    17387 	   animal_pk    INDEX     H   CREATE UNIQUE INDEX animal_pk ON public.animal USING btree (id_animal);
    DROP INDEX public.animal_pk;
       public         postgres    false    189             	           1259    17388    bitacora_analisis_lab_fk    INDEX     X   CREATE INDEX bitacora_analisis_lab_fk ON public.analisis_lab USING btree (cod_bit_lab);
 ,   DROP INDEX public.bitacora_analisis_lab_fk;
       public         postgres    false    187            .	           1259    17389    bitacora_campo_encuesta_fk    INDEX     W   CREATE INDEX bitacora_campo_encuesta_fk ON public.encuesta USING btree (id_bit_campo);
 .   DROP INDEX public.bitacora_campo_encuesta_fk;
       public         postgres    false    208            		           1259    17390    bitacora_campo_pk    INDEX     [   CREATE UNIQUE INDEX bitacora_campo_pk ON public.bitacora_campo USING btree (id_bit_campo);
 %   DROP INDEX public.bitacora_campo_pk;
       public         postgres    false    191            	           1259    17391    bitacora_lab_pk    INDEX     V   CREATE UNIQUE INDEX bitacora_lab_pk ON public.bitacora_lab USING btree (cod_bit_lab);
 #   DROP INDEX public.bitacora_lab_pk;
       public         postgres    false    193            	           1259    17392 
   cacerio_pk    INDEX     K   CREATE UNIQUE INDEX cacerio_pk ON public.cacerio USING btree (id_cacerio);
    DROP INDEX public.cacerio_pk;
       public         postgres    false    195            �	           1259    17393    cacerio_ubicacion_fk    INDEX     P   CREATE INDEX cacerio_ubicacion_fk ON public.ubicacion USING btree (id_cacerio);
 (   DROP INDEX public.cacerio_ubicacion_fk;
       public         postgres    false    249            
	           1259    17394    cambios_campo_fk    INDEX     W   CREATE INDEX cambios_campo_fk ON public.bitacora_campo USING btree (bit_id_bit_campo);
 $   DROP INDEX public.cambios_campo_fk;
       public         postgres    false    191            )	           1259    17395    cambios_documento_fk    INDEX     P   CREATE INDEX cambios_documento_fk ON public.documento USING btree (doc_id_doc);
 (   DROP INDEX public.cambios_documento_fk;
       public         postgres    false    206            6	           1259    17396    cambios_investigacion_fk    INDEX     \   CREATE INDEX cambios_investigacion_fk ON public.investigacion USING btree (inv_cod_invest);
 ,   DROP INDEX public.cambios_investigacion_fk;
       public         postgres    false    212            	           1259    17397    cambios_laboratorio_fk    INDEX     Z   CREATE INDEX cambios_laboratorio_fk ON public.bitacora_lab USING btree (bit_cod_bit_lab);
 *   DROP INDEX public.cambios_laboratorio_fk;
       public         postgres    false    193            F	           1259    17398    cambios_lote_fk    INDEX     G   CREATE INDEX cambios_lote_fk ON public.lote USING btree (lot_id_lote);
 #   DROP INDEX public.cambios_lote_fk;
       public         postgres    false    218            b	           1259    17399    cambios_perfil_fk    INDEX     Q   CREATE INDEX cambios_perfil_fk ON public.perfil_inv USING btree (per_id_perfil);
 %   DROP INDEX public.cambios_perfil_fk;
       public         postgres    false    227            X	           1259    17400    campo_muestra_fk    INDEX     L   CREATE INDEX campo_muestra_fk ON public.muestra USING btree (id_bit_campo);
 $   DROP INDEX public.campo_muestra_fk;
       public         postgres    false    225            �	           1259    17401    campo_ubicacion_fk    INDEX     P   CREATE INDEX campo_ubicacion_fk ON public.ubicacion USING btree (id_bit_campo);
 &   DROP INDEX public.campo_ubicacion_fk;
       public         postgres    false    249            	           1259    17402    cargo_pk    INDEX     E   CREATE UNIQUE INDEX cargo_pk ON public.cargo USING btree (id_cargo);
    DROP INDEX public.cargo_pk;
       public         postgres    false    197            	           1259    17403    catalogo_anima_pk    INDEX     \   CREATE UNIQUE INDEX catalogo_anima_pk ON public.catalogo_anima USING btree (id_cat_animal);
 %   DROP INDEX public.catalogo_anima_pk;
       public         postgres    false    199            	           1259    17404    catalogo_animal_animal_fk    INDEX     U   CREATE INDEX catalogo_animal_animal_fk ON public.animal USING btree (id_cat_animal);
 -   DROP INDEX public.catalogo_animal_animal_fk;
       public         postgres    false    189            	           1259    17405    colonia_cacerio_fk    INDEX     L   CREATE INDEX colonia_cacerio_fk ON public.cacerio USING btree (id_col_can);
 &   DROP INDEX public.colonia_cacerio_fk;
       public         postgres    false    195            	           1259    17406    colonia_canton_pk    INDEX     Y   CREATE UNIQUE INDEX colonia_canton_pk ON public.colonia_canton USING btree (id_col_can);
 %   DROP INDEX public.colonia_canton_pk;
       public         postgres    false    201            "	           1259    17407    departamento_pk    INDEX     T   CREATE UNIQUE INDEX departamento_pk ON public.departamento USING btree (cod_depto);
 #   DROP INDEX public.departamento_pk;
       public         postgres    false    203            ^	           1259    17408    depto_municipio_fk    INDEX     M   CREATE INDEX depto_municipio_fk ON public.municipio USING btree (cod_depto);
 &   DROP INDEX public.depto_municipio_fk;
       public         postgres    false    226            %	           1259    17409 
   detalle_pk    INDEX     K   CREATE UNIQUE INDEX detalle_pk ON public.detalle USING btree (id_detalle);
    DROP INDEX public.detalle_pk;
       public         postgres    false    204            *	           1259    17410    documento_pk    INDEX     K   CREATE UNIQUE INDEX documento_pk ON public.documento USING btree (id_doc);
     DROP INDEX public.documento_pk;
       public         postgres    false    206            /	           1259    17411    encuesta_pk    INDEX     N   CREATE UNIQUE INDEX encuesta_pk ON public.encuesta USING btree (id_encuesta);
    DROP INDEX public.encuesta_pk;
       public         postgres    false    208            m	           1259    17412    encuesta_pregunta_fk    INDEX     P   CREATE INDEX encuesta_pregunta_fk ON public.pregunta USING btree (id_encuesta);
 (   DROP INDEX public.encuesta_pregunta_fk;
       public         postgres    false    233            2	           1259    17413    inv_vector_pk    INDEX     T   CREATE UNIQUE INDEX inv_vector_pk ON public.inv_vector USING btree (id_inv_vector);
 !   DROP INDEX public.inv_vector_pk;
       public         postgres    false    210            	           1259    17414    investigacion_campo_fk    INDEX     Z   CREATE INDEX investigacion_campo_fk ON public.bitacora_campo USING btree (id_inv_vector);
 *   DROP INDEX public.investigacion_campo_fk;
       public         postgres    false    191            ;	           1259    17415    investigacion_investigador2_fk    INDEX     g   CREATE INDEX investigacion_investigador2_fk ON public.investigacion_investigador USING btree (id_inv);
 2   DROP INDEX public.investigacion_investigador2_fk;
       public         postgres    false    214            <	           1259    17416    investigacion_investigador_fk    INDEX     j   CREATE INDEX investigacion_investigador_fk ON public.investigacion_investigador USING btree (cod_invest);
 1   DROP INDEX public.investigacion_investigador_fk;
       public         postgres    false    214            =	           1259    17417    investigacion_investigador_pk    INDEX     y   CREATE UNIQUE INDEX investigacion_investigador_pk ON public.investigacion_investigador USING btree (cod_invest, id_inv);
 1   DROP INDEX public.investigacion_investigador_pk;
       public         postgres    false    214    214            7	           1259    17418    investigacion_pk    INDEX     W   CREATE UNIQUE INDEX investigacion_pk ON public.investigacion USING btree (cod_invest);
 $   DROP INDEX public.investigacion_pk;
       public         postgres    false    212            3	           1259    17419    investigacion_vector_vector_fk    INDEX     [   CREATE INDEX investigacion_vector_vector_fk ON public.inv_vector USING btree (cod_vector);
 2   DROP INDEX public.investigacion_vector_vector_fk;
       public         postgres    false    210            @	           1259    17420    investigador_pk    INDEX     Q   CREATE UNIQUE INDEX investigador_pk ON public.investigador USING btree (id_inv);
 #   DROP INDEX public.investigador_pk;
       public         postgres    false    215            8	           1259    17421    laboratorio_investigacion_fk    INDEX     a   CREATE INDEX laboratorio_investigacion_fk ON public.investigacion USING btree (cod_laboratorio);
 0   DROP INDEX public.laboratorio_investigacion_fk;
       public         postgres    false    212            G	           1259    17422    laboratorio_lote_fk    INDEX     K   CREATE INDEX laboratorio_lote_fk ON public.lote USING btree (cod_bit_lab);
 '   DROP INDEX public.laboratorio_lote_fk;
       public         postgres    false    218            C	           1259    17423    laboratorio_pk    INDEX     X   CREATE UNIQUE INDEX laboratorio_pk ON public.laboratorio USING btree (cod_laboratorio);
 "   DROP INDEX public.laboratorio_pk;
       public         postgres    false    216            K	           1259    17424    lote_mantenimiento_fk    INDEX     R   CREATE INDEX lote_mantenimiento_fk ON public.mantenimiento USING btree (id_lote);
 )   DROP INDEX public.lote_mantenimiento_fk;
       public         postgres    false    220            Y	           1259    17425    lote_muestra_fk    INDEX     F   CREATE INDEX lote_muestra_fk ON public.muestra USING btree (id_lote);
 #   DROP INDEX public.lote_muestra_fk;
       public         postgres    false    225            H	           1259    17426    lote_pk    INDEX     B   CREATE UNIQUE INDEX lote_pk ON public.lote USING btree (id_lote);
    DROP INDEX public.lote_pk;
       public         postgres    false    218            L	           1259    17427    mantenimiento_pk    INDEX     U   CREATE UNIQUE INDEX mantenimiento_pk ON public.mantenimiento USING btree (id_manto);
 $   DROP INDEX public.mantenimiento_pk;
       public         postgres    false    220            S	           1259    17428    menu_permiso2_fk    INDEX     O   CREATE INDEX menu_permiso2_fk ON public.menu_permiso USING btree (id_permiso);
 $   DROP INDEX public.menu_permiso2_fk;
       public         postgres    false    224            T	           1259    17429    menu_permiso_fk    INDEX     K   CREATE INDEX menu_permiso_fk ON public.menu_permiso USING btree (id_menu);
 #   DROP INDEX public.menu_permiso_fk;
       public         postgres    false    224            U	           1259    17430    menu_permiso_pk    INDEX     ^   CREATE UNIQUE INDEX menu_permiso_pk ON public.menu_permiso USING btree (id_menu, id_permiso);
 #   DROP INDEX public.menu_permiso_pk;
       public         postgres    false    224    224            O	           1259    17431    menu_pk    INDEX     B   CREATE UNIQUE INDEX menu_pk ON public.menu USING btree (id_menu);
    DROP INDEX public.menu_pk;
       public         postgres    false    222            P	           1259    17432    menu_submenu_fk    INDEX     G   CREATE INDEX menu_submenu_fk ON public.menu USING btree (men_id_menu);
 #   DROP INDEX public.menu_submenu_fk;
       public         postgres    false    222            Z	           1259    17433 
   muestra_pk    INDEX     O   CREATE UNIQUE INDEX muestra_pk ON public.muestra USING btree (codigo_muestra);
    DROP INDEX public.muestra_pk;
       public         postgres    false    225            	           1259    17434    municipio_canton_fk    INDEX     W   CREATE INDEX municipio_canton_fk ON public.colonia_canton USING btree (cod_municipio);
 '   DROP INDEX public.municipio_canton_fk;
       public         postgres    false    201            _	           1259    17435    municipio_pk    INDEX     R   CREATE UNIQUE INDEX municipio_pk ON public.municipio USING btree (cod_municipio);
     DROP INDEX public.municipio_pk;
       public         postgres    false    226            +	           1259    17436    perfil_documentacion_fk    INDEX     R   CREATE INDEX perfil_documentacion_fk ON public.documento USING btree (id_perfil);
 +   DROP INDEX public.perfil_documentacion_fk;
       public         postgres    false    206            c	           1259    17437    perfil_inv_pk    INDEX     P   CREATE UNIQUE INDEX perfil_inv_pk ON public.perfil_inv USING btree (id_perfil);
 !   DROP INDEX public.perfil_inv_pk;
       public         postgres    false    227            f	           1259    17438 
   permiso_pk    INDEX     K   CREATE UNIQUE INDEX permiso_pk ON public.permiso USING btree (id_permiso);
    DROP INDEX public.permiso_pk;
       public         postgres    false    229            i	           1259    17439 
   persona_pk    INDEX     K   CREATE UNIQUE INDEX persona_pk ON public.persona USING btree (id_persona);
    DROP INDEX public.persona_pk;
       public         postgres    false    231            p	           1259    17440    pregunta_pk    INDEX     N   CREATE UNIQUE INDEX pregunta_pk ON public.pregunta USING btree (id_pregunta);
    DROP INDEX public.pregunta_pk;
       public         postgres    false    233            v	           1259    17441    pregunta_respuesta_fk    INDEX     R   CREATE INDEX pregunta_respuesta_fk ON public.respuesta USING btree (id_pregunta);
 )   DROP INDEX public.pregunta_respuesta_fk;
       public         postgres    false    236            s	           1259    17442    preservante_pk    INDEX     W   CREATE UNIQUE INDEX preservante_pk ON public.preservante USING btree (id_preservante);
 "   DROP INDEX public.preservante_pk;
       public         postgres    false    235            l	           1259    17443    relationship_49_fk    INDEX     L   CREATE INDEX relationship_49_fk ON public.persona USING btree (id_usuario);
 &   DROP INDEX public.relationship_49_fk;
       public         postgres    false    231            (	           1259    17444    respuesta_detalle_fk    INDEX     P   CREATE INDEX respuesta_detalle_fk ON public.detalle USING btree (id_respuesta);
 (   DROP INDEX public.respuesta_detalle_fk;
       public         postgres    false    204            w	           1259    17445    respuesta_pk    INDEX     Q   CREATE UNIQUE INDEX respuesta_pk ON public.respuesta USING btree (id_respuesta);
     DROP INDEX public.respuesta_pk;
       public         postgres    false    236            ~	           1259    17446    rol_permiso2_fk    INDEX     I   CREATE INDEX rol_permiso2_fk ON public.rol_permiso USING btree (id_rol);
 #   DROP INDEX public.rol_permiso2_fk;
       public         postgres    false    240            	           1259    17447    rol_permiso_fk    INDEX     L   CREATE INDEX rol_permiso_fk ON public.rol_permiso USING btree (id_permiso);
 "   DROP INDEX public.rol_permiso_fk;
       public         postgres    false    240            �	           1259    17448    rol_permiso_pk    INDEX     [   CREATE UNIQUE INDEX rol_permiso_pk ON public.rol_permiso USING btree (id_permiso, id_rol);
 "   DROP INDEX public.rol_permiso_pk;
       public         postgres    false    240    240            {	           1259    17449    rol_pk    INDEX     ?   CREATE UNIQUE INDEX rol_pk ON public.rol USING btree (id_rol);
    DROP INDEX public.rol_pk;
       public         postgres    false    238            	           1259    17450    tipo_analisis_fk    INDEX     S   CREATE INDEX tipo_analisis_fk ON public.analisis_lab USING btree (id_tipo_prueba);
 $   DROP INDEX public.tipo_analisis_fk;
       public         postgres    false    187            ]	           1259    17451    tipo_muestra_fk    INDEX     K   CREATE INDEX tipo_muestra_fk ON public.muestra USING btree (id_tipo_mues);
 #   DROP INDEX public.tipo_muestra_fk;
       public         postgres    false    225            �	           1259    17452    tipo_muestra_pk    INDEX     W   CREATE UNIQUE INDEX tipo_muestra_pk ON public.tipo_muestra USING btree (id_tipo_mues);
 #   DROP INDEX public.tipo_muestra_pk;
       public         postgres    false    241            �	           1259    17453    tipo_prueba_pk    INDEX     W   CREATE UNIQUE INDEX tipo_prueba_pk ON public.tipo_prueba USING btree (id_tipo_prueba);
 "   DROP INDEX public.tipo_prueba_pk;
       public         postgres    false    243            �	           1259    17454    tipo_respuesta_pk    INDEX     [   CREATE UNIQUE INDEX tipo_respuesta_pk ON public.tipo_respuesta USING btree (id_tipo_resp);
 %   DROP INDEX public.tipo_respuesta_pk;
       public         postgres    false    245            x	           1259    17455    tipo_respuesta_respuesta_fk    INDEX     Y   CREATE INDEX tipo_respuesta_respuesta_fk ON public.respuesta USING btree (id_tipo_resp);
 /   DROP INDEX public.tipo_respuesta_respuesta_fk;
       public         postgres    false    236            �	           1259    17456    tratamiento_pk    INDEX     W   CREATE UNIQUE INDEX tratamiento_pk ON public.tratamiento USING btree (id_tratamiento);
 "   DROP INDEX public.tratamiento_pk;
       public         postgres    false    247            �	           1259    17457    ubicacion_pk    INDEX     Q   CREATE UNIQUE INDEX ubicacion_pk ON public.ubicacion USING btree (id_ubicacion);
     DROP INDEX public.ubicacion_pk;
       public         postgres    false    249            �	           1259    17458    usuario_cargo_fk    INDEX     H   CREATE INDEX usuario_cargo_fk ON public.usuario USING btree (id_cargo);
 $   DROP INDEX public.usuario_cargo_fk;
       public         postgres    false    251            �	           1259    17459 
   usuario_pk    INDEX     K   CREATE UNIQUE INDEX usuario_pk ON public.usuario USING btree (id_usuario);
    DROP INDEX public.usuario_pk;
       public         postgres    false    251            �	           1259    17460    usuario_rol2_fk    INDEX     M   CREATE INDEX usuario_rol2_fk ON public.usuario_rol USING btree (id_usuario);
 #   DROP INDEX public.usuario_rol2_fk;
       public         postgres    false    254            �	           1259    17461    usuario_rol_fk    INDEX     H   CREATE INDEX usuario_rol_fk ON public.usuario_rol USING btree (id_rol);
 "   DROP INDEX public.usuario_rol_fk;
       public         postgres    false    254            �	           1259    17462    usuario_rol_pk    INDEX     [   CREATE UNIQUE INDEX usuario_rol_pk ON public.usuario_rol USING btree (id_rol, id_usuario);
 "   DROP INDEX public.usuario_rol_pk;
       public         postgres    false    254    254            	           1259    17463    vector_bitacora_laboratorio_fk    INDEX     `   CREATE INDEX vector_bitacora_laboratorio_fk ON public.bitacora_lab USING btree (id_inv_vector);
 2   DROP INDEX public.vector_bitacora_laboratorio_fk;
       public         postgres    false    193            �	           1259    17464 	   vector_pk    INDEX     I   CREATE UNIQUE INDEX vector_pk ON public.vector USING btree (cod_vector);
    DROP INDEX public.vector_pk;
       public         postgres    false    256            �	           2620    17465    persona t_nombre_apellido    TRIGGER     �   CREATE TRIGGER t_nombre_apellido BEFORE INSERT OR UPDATE ON public.persona FOR EACH ROW EXECUTE PROCEDURE public.f_nombre_apellido();
 2   DROP TRIGGER t_nombre_apellido ON public.persona;
       public       postgres    false    268    231            �	           2620    17466    usuario t_usuario_nombre    TRIGGER     z   CREATE TRIGGER t_usuario_nombre BEFORE INSERT ON public.usuario FOR EACH ROW EXECUTE PROCEDURE public.f_usuario_nombre();
 1   DROP TRIGGER t_usuario_nombre ON public.usuario;
       public       postgres    false    267    251            �	           2606    34152 .   actividad_01 actividad_01_actividad_encabezado    FK CONSTRAINT     �   ALTER TABLE ONLY public.actividad_01
    ADD CONSTRAINT actividad_01_actividad_encabezado FOREIGN KEY (id_encabezado) REFERENCES public.actividad_encabezado(id_encabezado);
 X   ALTER TABLE ONLY public.actividad_01 DROP CONSTRAINT actividad_01_actividad_encabezado;
       public       postgres    false    260    2468    262            �	           2606    17467 (   actividad fk_activida_reference_investig    FK CONSTRAINT     �   ALTER TABLE ONLY public.actividad
    ADD CONSTRAINT fk_activida_reference_investig FOREIGN KEY (cod_invest) REFERENCES public.investigacion(cod_invest) ON UPDATE RESTRICT ON DELETE RESTRICT;
 R   ALTER TABLE ONLY public.actividad DROP CONSTRAINT fk_activida_reference_investig;
       public       postgres    false    2362    212    185            �	           2606    17472 *   analisis_lab fk_analisis_analisis__muestra    FK CONSTRAINT     �   ALTER TABLE ONLY public.analisis_lab
    ADD CONSTRAINT fk_analisis_analisis__muestra FOREIGN KEY (codigo_muestra) REFERENCES public.muestra(codigo_muestra) ON UPDATE RESTRICT ON DELETE RESTRICT;
 T   ALTER TABLE ONLY public.analisis_lab DROP CONSTRAINT fk_analisis_analisis__muestra;
       public       postgres    false    2396    225    187            �	           2606    17477 +   analisis_lab fk_analisis_bitacora__bitacora    FK CONSTRAINT     �   ALTER TABLE ONLY public.analisis_lab
    ADD CONSTRAINT fk_analisis_bitacora__bitacora FOREIGN KEY (cod_bit_lab) REFERENCES public.bitacora_lab(cod_bit_lab) ON UPDATE RESTRICT ON DELETE RESTRICT;
 U   ALTER TABLE ONLY public.analisis_lab DROP CONSTRAINT fk_analisis_bitacora__bitacora;
       public       postgres    false    187    2322    193            �	           2606    17482 +   analisis_lab fk_analisis_tipo_anal_tipo_pru    FK CONSTRAINT     �   ALTER TABLE ONLY public.analisis_lab
    ADD CONSTRAINT fk_analisis_tipo_anal_tipo_pru FOREIGN KEY (id_tipo_prueba) REFERENCES public.tipo_prueba(id_tipo_prueba) ON UPDATE RESTRICT ON DELETE RESTRICT;
 U   ALTER TABLE ONLY public.analisis_lab DROP CONSTRAINT fk_analisis_tipo_anal_tipo_pru;
       public       postgres    false    2437    243    187            �	           2606    17487 #   animal fk_animal_analisis__analisis    FK CONSTRAINT     �   ALTER TABLE ONLY public.animal
    ADD CONSTRAINT fk_animal_analisis__analisis FOREIGN KEY (id_analisis) REFERENCES public.analisis_lab(id_analisis) ON UPDATE RESTRICT ON DELETE RESTRICT;
 M   ALTER TABLE ONLY public.animal DROP CONSTRAINT fk_animal_analisis__analisis;
       public       postgres    false    2306    187    189            �	           2606    17492 #   animal fk_animal_catalogo__catalogo    FK CONSTRAINT     �   ALTER TABLE ONLY public.animal
    ADD CONSTRAINT fk_animal_catalogo__catalogo FOREIGN KEY (id_cat_animal) REFERENCES public.catalogo_anima(id_cat_animal) ON UPDATE RESTRICT ON DELETE RESTRICT;
 M   ALTER TABLE ONLY public.animal DROP CONSTRAINT fk_animal_catalogo__catalogo;
       public       postgres    false    2333    199    189            �	           2606    17497 +   bitacora_lab fk_bitacora_actividad_activida    FK CONSTRAINT     �   ALTER TABLE ONLY public.bitacora_lab
    ADD CONSTRAINT fk_bitacora_actividad_activida FOREIGN KEY (cod_actividad) REFERENCES public.actividad(cod_actividad) ON UPDATE RESTRICT ON DELETE RESTRICT;
 U   ALTER TABLE ONLY public.bitacora_lab DROP CONSTRAINT fk_bitacora_actividad_activida;
       public       postgres    false    193    185    2301            �	           2606    17502 -   bitacora_campo fk_bitacora_cambios_c_bitacora    FK CONSTRAINT     �   ALTER TABLE ONLY public.bitacora_campo
    ADD CONSTRAINT fk_bitacora_cambios_c_bitacora FOREIGN KEY (bit_id_bit_campo) REFERENCES public.bitacora_campo(id_bit_campo) ON UPDATE RESTRICT ON DELETE RESTRICT;
 W   ALTER TABLE ONLY public.bitacora_campo DROP CONSTRAINT fk_bitacora_cambios_c_bitacora;
       public       postgres    false    2317    191    191            �	           2606    17507 +   bitacora_lab fk_bitacora_cambios_l_bitacora    FK CONSTRAINT     �   ALTER TABLE ONLY public.bitacora_lab
    ADD CONSTRAINT fk_bitacora_cambios_l_bitacora FOREIGN KEY (bit_cod_bit_lab) REFERENCES public.bitacora_lab(cod_bit_lab) ON UPDATE RESTRICT ON DELETE RESTRICT;
 U   ALTER TABLE ONLY public.bitacora_lab DROP CONSTRAINT fk_bitacora_cambios_l_bitacora;
       public       postgres    false    2322    193    193            �	           2606    17512 -   bitacora_campo fk_bitacora_investiga_inv_vect    FK CONSTRAINT     �   ALTER TABLE ONLY public.bitacora_campo
    ADD CONSTRAINT fk_bitacora_investiga_inv_vect FOREIGN KEY (id_inv_vector) REFERENCES public.inv_vector(id_inv_vector) ON UPDATE RESTRICT ON DELETE RESTRICT;
 W   ALTER TABLE ONLY public.bitacora_campo DROP CONSTRAINT fk_bitacora_investiga_inv_vect;
       public       postgres    false    191    2357    210            �	           2606    17517 +   bitacora_lab fk_bitacora_vector_bi_inv_vect    FK CONSTRAINT     �   ALTER TABLE ONLY public.bitacora_lab
    ADD CONSTRAINT fk_bitacora_vector_bi_inv_vect FOREIGN KEY (id_inv_vector) REFERENCES public.inv_vector(id_inv_vector) ON UPDATE RESTRICT ON DELETE RESTRICT;
 U   ALTER TABLE ONLY public.bitacora_lab DROP CONSTRAINT fk_bitacora_vector_bi_inv_vect;
       public       postgres    false    193    2357    210            �	           2606    17522 %   cacerio fk_cacerio_colonia_c_colonia_    FK CONSTRAINT     �   ALTER TABLE ONLY public.cacerio
    ADD CONSTRAINT fk_cacerio_colonia_c_colonia_ FOREIGN KEY (id_col_can) REFERENCES public.colonia_canton(id_col_can) ON UPDATE RESTRICT ON DELETE RESTRICT;
 O   ALTER TABLE ONLY public.cacerio DROP CONSTRAINT fk_cacerio_colonia_c_colonia_;
       public       postgres    false    201    195    2337            �	           2606    34261    lote fk_colcan    FK CONSTRAINT     �   ALTER TABLE ONLY public.lote
    ADD CONSTRAINT fk_colcan FOREIGN KEY (id_colcan) REFERENCES public.colonia_canton(id_col_can) ON UPDATE RESTRICT ON DELETE RESTRICT;
 8   ALTER TABLE ONLY public.lote DROP CONSTRAINT fk_colcan;
       public       postgres    false    201    2337    218            �	           2606    17527 -   colonia_canton fk_colonia__municipio_municipi    FK CONSTRAINT     �   ALTER TABLE ONLY public.colonia_canton
    ADD CONSTRAINT fk_colonia__municipio_municipi FOREIGN KEY (cod_municipio) REFERENCES public.municipio(cod_municipio) ON UPDATE RESTRICT ON DELETE RESTRICT;
 W   ALTER TABLE ONLY public.colonia_canton DROP CONSTRAINT fk_colonia__municipio_municipi;
       public       postgres    false    201    2401    226            �	           2606    17532 %   detalle fk_detalle_respuesta_respuest    FK CONSTRAINT     �   ALTER TABLE ONLY public.detalle
    ADD CONSTRAINT fk_detalle_respuesta_respuest FOREIGN KEY (id_respuesta) REFERENCES public.respuesta(id_respuesta) ON UPDATE RESTRICT ON DELETE RESTRICT;
 O   ALTER TABLE ONLY public.detalle DROP CONSTRAINT fk_detalle_respuesta_respuest;
       public       postgres    false    2421    204    236            �	           2606    17537 (   documento fk_document_cambios_d_document    FK CONSTRAINT     �   ALTER TABLE ONLY public.documento
    ADD CONSTRAINT fk_document_cambios_d_document FOREIGN KEY (doc_id_doc) REFERENCES public.documento(id_doc) ON UPDATE RESTRICT ON DELETE RESTRICT;
 R   ALTER TABLE ONLY public.documento DROP CONSTRAINT fk_document_cambios_d_document;
       public       postgres    false    206    206    2349            �	           2606    17542 (   documento fk_document_perfil_do_perfil_i    FK CONSTRAINT     �   ALTER TABLE ONLY public.documento
    ADD CONSTRAINT fk_document_perfil_do_perfil_i FOREIGN KEY (id_perfil) REFERENCES public.perfil_inv(id_perfil) ON UPDATE RESTRICT ON DELETE RESTRICT;
 R   ALTER TABLE ONLY public.documento DROP CONSTRAINT fk_document_perfil_do_perfil_i;
       public       postgres    false    227    206    2405            �	           2606    17547 '   encuesta fk_encuesta_bitacora__bitacora    FK CONSTRAINT     �   ALTER TABLE ONLY public.encuesta
    ADD CONSTRAINT fk_encuesta_bitacora__bitacora FOREIGN KEY (id_bit_campo) REFERENCES public.bitacora_campo(id_bit_campo) ON UPDATE RESTRICT ON DELETE RESTRICT;
 Q   ALTER TABLE ONLY public.encuesta DROP CONSTRAINT fk_encuesta_bitacora__bitacora;
       public       postgres    false    208    2317    191            �	           2606    34256    vector fk_familia    FK CONSTRAINT     �   ALTER TABLE ONLY public.vector
    ADD CONSTRAINT fk_familia FOREIGN KEY (id_familia) REFERENCES public.familia(id) ON UPDATE RESTRICT ON DELETE RESTRICT;
 ;   ALTER TABLE ONLY public.vector DROP CONSTRAINT fk_familia;
       public       postgres    false    266    256    2472            �	           2606    17552 '   inv_vector fk_inv_vect_investiga_vector    FK CONSTRAINT     �   ALTER TABLE ONLY public.inv_vector
    ADD CONSTRAINT fk_inv_vect_investiga_vector FOREIGN KEY (cod_vector) REFERENCES public.vector(cod_vector) ON UPDATE RESTRICT ON DELETE RESTRICT;
 Q   ALTER TABLE ONLY public.inv_vector DROP CONSTRAINT fk_inv_vect_investiga_vector;
       public       postgres    false    2463    256    210            �	           2606    17557 )   inv_vector fk_inv_vect_reference_investig    FK CONSTRAINT     �   ALTER TABLE ONLY public.inv_vector
    ADD CONSTRAINT fk_inv_vect_reference_investig FOREIGN KEY (cod_invest) REFERENCES public.investigacion(cod_invest) ON UPDATE RESTRICT ON DELETE RESTRICT;
 S   ALTER TABLE ONLY public.inv_vector DROP CONSTRAINT fk_inv_vect_reference_investig;
       public       postgres    false    2362    212    210            �	           2606    17562 ,   investigacion fk_investig_cambios_i_investig    FK CONSTRAINT     �   ALTER TABLE ONLY public.investigacion
    ADD CONSTRAINT fk_investig_cambios_i_investig FOREIGN KEY (inv_cod_invest) REFERENCES public.investigacion(cod_invest) ON UPDATE RESTRICT ON DELETE RESTRICT;
 V   ALTER TABLE ONLY public.investigacion DROP CONSTRAINT fk_investig_cambios_i_investig;
       public       postgres    false    212    2362    212            �	           2606    17567 9   investigacion_investigador fk_investig_investiga_investig    FK CONSTRAINT     �   ALTER TABLE ONLY public.investigacion_investigador
    ADD CONSTRAINT fk_investig_investiga_investig FOREIGN KEY (cod_invest) REFERENCES public.investigacion(cod_invest) ON UPDATE RESTRICT ON DELETE RESTRICT;
 c   ALTER TABLE ONLY public.investigacion_investigador DROP CONSTRAINT fk_investig_investiga_investig;
       public       postgres    false    2362    214    212            �	           2606    17572 :   investigacion_investigador fk_investig_investiga_investig2    FK CONSTRAINT     �   ALTER TABLE ONLY public.investigacion_investigador
    ADD CONSTRAINT fk_investig_investiga_investig2 FOREIGN KEY (id_inv) REFERENCES public.investigador(id_inv) ON UPDATE RESTRICT ON DELETE RESTRICT;
 d   ALTER TABLE ONLY public.investigacion_investigador DROP CONSTRAINT fk_investig_investiga_investig2;
       public       postgres    false    2370    214    215            �	           2606    17577 ,   investigacion fk_investig_laborator_laborato    FK CONSTRAINT     �   ALTER TABLE ONLY public.investigacion
    ADD CONSTRAINT fk_investig_laborator_laborato FOREIGN KEY (cod_laboratorio) REFERENCES public.laboratorio(cod_laboratorio) ON UPDATE RESTRICT ON DELETE RESTRICT;
 V   ALTER TABLE ONLY public.investigacion DROP CONSTRAINT fk_investig_laborator_laborato;
       public       postgres    false    2373    216    212            �	           2606    17582    lote fk_lote_cambios_l_lote    FK CONSTRAINT     �   ALTER TABLE ONLY public.lote
    ADD CONSTRAINT fk_lote_cambios_l_lote FOREIGN KEY (lot_id_lote) REFERENCES public.lote(id_lote) ON UPDATE RESTRICT ON DELETE RESTRICT;
 E   ALTER TABLE ONLY public.lote DROP CONSTRAINT fk_lote_cambios_l_lote;
       public       postgres    false    218    2378    218            �	           2606    17587    lote fk_lote_laborator_bitacora    FK CONSTRAINT     �   ALTER TABLE ONLY public.lote
    ADD CONSTRAINT fk_lote_laborator_bitacora FOREIGN KEY (cod_bit_lab) REFERENCES public.bitacora_lab(cod_bit_lab) ON UPDATE RESTRICT ON DELETE RESTRICT;
 I   ALTER TABLE ONLY public.lote DROP CONSTRAINT fk_lote_laborator_bitacora;
       public       postgres    false    193    2322    218            �	           2606    17592 (   mantenimiento fk_mantenim_lote_mant_lote    FK CONSTRAINT     �   ALTER TABLE ONLY public.mantenimiento
    ADD CONSTRAINT fk_mantenim_lote_mant_lote FOREIGN KEY (id_lote) REFERENCES public.lote(id_lote) ON UPDATE RESTRICT ON DELETE RESTRICT;
 R   ALTER TABLE ONLY public.mantenimiento DROP CONSTRAINT fk_mantenim_lote_mant_lote;
       public       postgres    false    2378    220    218            �	           2606    17597 ,   mantenimiento fk_mantenim_reference_preserva    FK CONSTRAINT     �   ALTER TABLE ONLY public.mantenimiento
    ADD CONSTRAINT fk_mantenim_reference_preserva FOREIGN KEY (id_preservante) REFERENCES public.preservante(id_preservante) ON UPDATE RESTRICT ON DELETE RESTRICT;
 V   ALTER TABLE ONLY public.mantenimiento DROP CONSTRAINT fk_mantenim_reference_preserva;
       public       postgres    false    235    220    2418            �	           2606    17602    menu fk_menu_menu_subm_menu    FK CONSTRAINT     �   ALTER TABLE ONLY public.menu
    ADD CONSTRAINT fk_menu_menu_subm_menu FOREIGN KEY (men_id_menu) REFERENCES public.menu(id_menu) ON UPDATE RESTRICT ON DELETE RESTRICT;
 E   ALTER TABLE ONLY public.menu DROP CONSTRAINT fk_menu_menu_subm_menu;
       public       postgres    false    222    2386    222            �	           2606    17607 '   menu_permiso fk_menu_per_menu_perm_menu    FK CONSTRAINT     �   ALTER TABLE ONLY public.menu_permiso
    ADD CONSTRAINT fk_menu_per_menu_perm_menu FOREIGN KEY (id_menu) REFERENCES public.menu(id_menu) ON UPDATE RESTRICT ON DELETE RESTRICT;
 Q   ALTER TABLE ONLY public.menu_permiso DROP CONSTRAINT fk_menu_per_menu_perm_menu;
       public       postgres    false    224    222    2386            �	           2606    17612 *   menu_permiso fk_menu_per_menu_perm_permiso    FK CONSTRAINT     �   ALTER TABLE ONLY public.menu_permiso
    ADD CONSTRAINT fk_menu_per_menu_perm_permiso FOREIGN KEY (id_permiso) REFERENCES public.permiso(id_permiso) ON UPDATE RESTRICT ON DELETE RESTRICT;
 T   ALTER TABLE ONLY public.menu_permiso DROP CONSTRAINT fk_menu_per_menu_perm_permiso;
       public       postgres    false    224    229    2408            �	           2606    17617 %   muestra fk_muestra_campo_mue_bitacora    FK CONSTRAINT     �   ALTER TABLE ONLY public.muestra
    ADD CONSTRAINT fk_muestra_campo_mue_bitacora FOREIGN KEY (id_bit_campo) REFERENCES public.bitacora_campo(id_bit_campo) ON UPDATE RESTRICT ON DELETE RESTRICT;
 O   ALTER TABLE ONLY public.muestra DROP CONSTRAINT fk_muestra_campo_mue_bitacora;
       public       postgres    false    2317    191    225            �	           2606    17622 !   muestra fk_muestra_lote_mues_lote    FK CONSTRAINT     �   ALTER TABLE ONLY public.muestra
    ADD CONSTRAINT fk_muestra_lote_mues_lote FOREIGN KEY (id_lote) REFERENCES public.lote(id_lote) ON UPDATE RESTRICT ON DELETE RESTRICT;
 K   ALTER TABLE ONLY public.muestra DROP CONSTRAINT fk_muestra_lote_mues_lote;
       public       postgres    false    218    2378    225            �	           2606    17627 %   muestra fk_muestra_tipo_mues_tipo_mue    FK CONSTRAINT     �   ALTER TABLE ONLY public.muestra
    ADD CONSTRAINT fk_muestra_tipo_mues_tipo_mue FOREIGN KEY (id_tipo_mues) REFERENCES public.tipo_muestra(id_tipo_mues) ON UPDATE RESTRICT ON DELETE RESTRICT;
 O   ALTER TABLE ONLY public.muestra DROP CONSTRAINT fk_muestra_tipo_mues_tipo_mue;
       public       postgres    false    2434    225    241            �	           2606    17632 (   municipio fk_municipi_depto_mun_departam    FK CONSTRAINT     �   ALTER TABLE ONLY public.municipio
    ADD CONSTRAINT fk_municipi_depto_mun_departam FOREIGN KEY (cod_depto) REFERENCES public.departamento(cod_depto) ON UPDATE RESTRICT ON DELETE RESTRICT;
 R   ALTER TABLE ONLY public.municipio DROP CONSTRAINT fk_municipi_depto_mun_departam;
       public       postgres    false    226    2340    203            �	           2606    34251    familia fk_orden    FK CONSTRAINT     �   ALTER TABLE ONLY public.familia
    ADD CONSTRAINT fk_orden FOREIGN KEY (id_orden) REFERENCES public.orden(id) ON UPDATE RESTRICT ON DELETE RESTRICT;
 :   ALTER TABLE ONLY public.familia DROP CONSTRAINT fk_orden;
       public       postgres    false    2470    264    266            �	           2606    17637 )   perfil_inv fk_perfil_i_cambios_p_perfil_i    FK CONSTRAINT     �   ALTER TABLE ONLY public.perfil_inv
    ADD CONSTRAINT fk_perfil_i_cambios_p_perfil_i FOREIGN KEY (per_id_perfil) REFERENCES public.perfil_inv(id_perfil) ON UPDATE RESTRICT ON DELETE RESTRICT;
 S   ALTER TABLE ONLY public.perfil_inv DROP CONSTRAINT fk_perfil_i_cambios_p_perfil_i;
       public       postgres    false    2405    227    227            �	           2606    17642 )   perfil_inv fk_perfil_i_reference_investig    FK CONSTRAINT     �   ALTER TABLE ONLY public.perfil_inv
    ADD CONSTRAINT fk_perfil_i_reference_investig FOREIGN KEY (cod_invest) REFERENCES public.investigacion(cod_invest) ON UPDATE RESTRICT ON DELETE RESTRICT;
 S   ALTER TABLE ONLY public.perfil_inv DROP CONSTRAINT fk_perfil_i_reference_investig;
       public       postgres    false    2362    212    227            �	           2606    17647 %   persona fk_persona_reference_encuesta    FK CONSTRAINT     �   ALTER TABLE ONLY public.persona
    ADD CONSTRAINT fk_persona_reference_encuesta FOREIGN KEY (id_encuesta) REFERENCES public.encuesta(id_encuesta) ON UPDATE RESTRICT ON DELETE RESTRICT;
 O   ALTER TABLE ONLY public.persona DROP CONSTRAINT fk_persona_reference_encuesta;
       public       postgres    false    208    2353    231            �	           2606    17652 $   persona fk_persona_relations_usuario    FK CONSTRAINT     �   ALTER TABLE ONLY public.persona
    ADD CONSTRAINT fk_persona_relations_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario) ON UPDATE RESTRICT ON DELETE RESTRICT;
 N   ALTER TABLE ONLY public.persona DROP CONSTRAINT fk_persona_relations_usuario;
       public       postgres    false    2452    231    251            �	           2606    17657 '   pregunta fk_pregunta_encuesta__encuesta    FK CONSTRAINT     �   ALTER TABLE ONLY public.pregunta
    ADD CONSTRAINT fk_pregunta_encuesta__encuesta FOREIGN KEY (id_encuesta) REFERENCES public.encuesta(id_encuesta) ON UPDATE RESTRICT ON DELETE RESTRICT;
 Q   ALTER TABLE ONLY public.pregunta DROP CONSTRAINT fk_pregunta_encuesta__encuesta;
       public       postgres    false    233    208    2353            �	           2606    17662 (   respuesta fk_respuest_pregunta__pregunta    FK CONSTRAINT     �   ALTER TABLE ONLY public.respuesta
    ADD CONSTRAINT fk_respuest_pregunta__pregunta FOREIGN KEY (id_pregunta) REFERENCES public.pregunta(id_pregunta) ON UPDATE RESTRICT ON DELETE RESTRICT;
 R   ALTER TABLE ONLY public.respuesta DROP CONSTRAINT fk_respuest_pregunta__pregunta;
       public       postgres    false    2415    233    236            �	           2606    17667 (   respuesta fk_respuest_tipo_resp_tipo_res    FK CONSTRAINT     �   ALTER TABLE ONLY public.respuesta
    ADD CONSTRAINT fk_respuest_tipo_resp_tipo_res FOREIGN KEY (id_tipo_resp) REFERENCES public.tipo_respuesta(id_tipo_resp) ON UPDATE RESTRICT ON DELETE RESTRICT;
 R   ALTER TABLE ONLY public.respuesta DROP CONSTRAINT fk_respuest_tipo_resp_tipo_res;
       public       postgres    false    2440    236    245            �	           2606    17672 )   rol_permiso fk_rol_perm_rol_permi_permiso    FK CONSTRAINT     �   ALTER TABLE ONLY public.rol_permiso
    ADD CONSTRAINT fk_rol_perm_rol_permi_permiso FOREIGN KEY (id_permiso) REFERENCES public.permiso(id_permiso) ON UPDATE RESTRICT ON DELETE RESTRICT;
 S   ALTER TABLE ONLY public.rol_permiso DROP CONSTRAINT fk_rol_perm_rol_permi_permiso;
       public       postgres    false    229    2408    240            �	           2606    17677 %   rol_permiso fk_rol_perm_rol_permi_rol    FK CONSTRAINT     �   ALTER TABLE ONLY public.rol_permiso
    ADD CONSTRAINT fk_rol_perm_rol_permi_rol FOREIGN KEY (id_rol) REFERENCES public.rol(id_rol) ON UPDATE RESTRICT ON DELETE RESTRICT;
 O   ALTER TABLE ONLY public.rol_permiso DROP CONSTRAINT fk_rol_perm_rol_permi_rol;
       public       postgres    false    240    2426    238            �	           2606    17682 *   tratamiento fk_tratamie_analisis__analisis    FK CONSTRAINT     �   ALTER TABLE ONLY public.tratamiento
    ADD CONSTRAINT fk_tratamie_analisis__analisis FOREIGN KEY (id_analisis) REFERENCES public.analisis_lab(id_analisis) ON UPDATE RESTRICT ON DELETE RESTRICT;
 T   ALTER TABLE ONLY public.tratamiento DROP CONSTRAINT fk_tratamie_analisis__analisis;
       public       postgres    false    187    247    2306            �	           2606    17687 '   ubicacion fk_ubicacio_cacerio_u_cacerio    FK CONSTRAINT     �   ALTER TABLE ONLY public.ubicacion
    ADD CONSTRAINT fk_ubicacio_cacerio_u_cacerio FOREIGN KEY (id_cacerio) REFERENCES public.cacerio(id_cacerio) ON UPDATE RESTRICT ON DELETE RESTRICT;
 Q   ALTER TABLE ONLY public.ubicacion DROP CONSTRAINT fk_ubicacio_cacerio_u_cacerio;
       public       postgres    false    2327    195    249            �	           2606    17692 (   ubicacion fk_ubicacio_campo_ubi_bitacora    FK CONSTRAINT     �   ALTER TABLE ONLY public.ubicacion
    ADD CONSTRAINT fk_ubicacio_campo_ubi_bitacora FOREIGN KEY (id_bit_campo) REFERENCES public.bitacora_campo(id_bit_campo) ON UPDATE RESTRICT ON DELETE RESTRICT;
 R   ALTER TABLE ONLY public.ubicacion DROP CONSTRAINT fk_ubicacio_campo_ubi_bitacora;
       public       postgres    false    249    2317    191            �	           2606    17697 *   usuario_lab fk_usuario__reference_laborato    FK CONSTRAINT     �   ALTER TABLE ONLY public.usuario_lab
    ADD CONSTRAINT fk_usuario__reference_laborato FOREIGN KEY (cod_laboratorio) REFERENCES public.laboratorio(cod_laboratorio) ON UPDATE RESTRICT ON DELETE RESTRICT;
 T   ALTER TABLE ONLY public.usuario_lab DROP CONSTRAINT fk_usuario__reference_laborato;
       public       postgres    false    216    2373    253            �	           2606    17702 )   usuario_lab fk_usuario__reference_usuario    FK CONSTRAINT     �   ALTER TABLE ONLY public.usuario_lab
    ADD CONSTRAINT fk_usuario__reference_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario) ON UPDATE RESTRICT ON DELETE RESTRICT;
 S   ALTER TABLE ONLY public.usuario_lab DROP CONSTRAINT fk_usuario__reference_usuario;
       public       postgres    false    2452    251    253            �	           2606    17707 %   usuario_rol fk_usuario__usuario_r_rol    FK CONSTRAINT     �   ALTER TABLE ONLY public.usuario_rol
    ADD CONSTRAINT fk_usuario__usuario_r_rol FOREIGN KEY (id_rol) REFERENCES public.rol(id_rol) ON UPDATE RESTRICT ON DELETE RESTRICT;
 O   ALTER TABLE ONLY public.usuario_rol DROP CONSTRAINT fk_usuario__usuario_r_rol;
       public       postgres    false    238    2426    254            �	           2606    17712 )   usuario_rol fk_usuario__usuario_r_usuario    FK CONSTRAINT     �   ALTER TABLE ONLY public.usuario_rol
    ADD CONSTRAINT fk_usuario__usuario_r_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario) ON UPDATE RESTRICT ON DELETE RESTRICT;
 S   ALTER TABLE ONLY public.usuario_rol DROP CONSTRAINT fk_usuario__usuario_r_usuario;
       public       postgres    false    254    251    2452            �	           2606    17717 "   usuario fk_usuario_usuario_c_cargo    FK CONSTRAINT     �   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT fk_usuario_usuario_c_cargo FOREIGN KEY (id_cargo) REFERENCES public.cargo(id_cargo) ON UPDATE RESTRICT ON DELETE RESTRICT;
 L   ALTER TABLE ONLY public.usuario DROP CONSTRAINT fk_usuario_usuario_c_cargo;
       public       postgres    false    197    251    2330            W
      x������ � �      �
   &   x�3�t4�416�46�`0��@πӐ3���b���� �q�      �
   �   x�3��5�p�-r���0503370��420����0w33N3 .�
���ȢĒ�bN##]#]3�$2���y�+�+$��e�&����EF  b���t����s@tnb�BJ�BQ~IbYfQi�Bni	�hK��܊��*C�=... c�<�      Y
      x������ � �      [
      x������ � �      ]
   �   x�mP�n�0�ɯ��Or��d��5��u<$m�$�_JI�*@݃O�w���J�r-i5R��JYd)@K����,H<�lM���v��Q��%�g�wg���[�
�\|�4K�w׾��������@���q�����4��=*Q9�G�OyV�ŕ2��&P��<C�/͉��w�E�U!yF]$|_�ƪp�7}��1��4���O�;ͻy?��[צ�3�rM�����gf��e��      _
      x������ � �      a
      x������ � �      c
   �   x���AN�0E��S��lj����p�n��\5�v�T���&��X������KR���8��;�P&�X�cd<�N��l���\�#lRd�=s.��$�`������槑�~�9����t1Q[x:���Q!�f8��H<�036���瑫����Y�p�ZV0��z�냃0\�E���Ҟ[��'��.X����l�1ߋ��      e
      x������ � �      g
      x�u}[n캲���(<��H��Ր�Z��z��ø��A�ӝXgD��ڍs���UL�b�ɠ����t;\^׷�|y�������8\^F���w:O��������^^|�\����=�^��Wy�y:��z�6]���T/���<����+t���3����q�k����w:�����b�����p�{����Ōz��w��j�.r�?�w���W��y:M���=^n����>g�b�N��c�,5�~�M� '���U~��x�.Ӌ���b~)9�S�Rb�%����I~����/4�U[�����{��c~�j�u����^��r�����I�Ն�\'����
ܥ�����:�z�M��A�w��l՜��|���tՎ�tx���F��n{�h���?��}~G"��n�r��+`t�Č߉=d�֪���4�]���%*����^=��Q�q[7�����8�~�e���3,H:���g+��&�@iN7n�\~UzՋs��ηo�d����y�����Ť�Alp����ϳ� 	\���~�_K��D�6��u���r��A�?���[���Z���W�����q~�͒��zHO��y����V���۶����}J�_|����$��^�.G�"U@Zs:-w�r�����f1|���&���ui�W|8x�w�t��[�-Mo���H��p��/Ak~�|w������������_���K@�����W��(���m��2����r(��a��h
��0�M_��^ai���M��|~���M�.��W�V!������Q2X~���%&��sž�ߗ�uF;��.-�̊>f|���֚�.2�Θ�����c�����%��q�0)�xΗ�.�LRY V�/2�ʏ��D�|{/)�9��&���F͆(���|��:[Џdx���Hi��4���Ԉ���� t�����H��/��E���&Ș�ؐ��a������A�s���+.��/n���.wz>|��M�u-�+��	����>o��W�z�s���?xW"[����_�ygo�:��1������x�V�[5��7��M�C�o�3_���ޖ.8��ݛ����g��v1F���S$��x�I�u��)�_V"���F� ���a� 5D�G�%�W V���2"x_��DX@��<0��Q���X�Xp���w�"��1�T���}�W@���mQ�e��Ma�s>@y�B�"Hs��X�o|�����&�ڣV8�~����Q� 6�:��fy���f����ڻ��Y����,x��b��F�q՝ޅ	:%KD��#���t��"U'l��n�� ?�$Q���������*?7�>H���B��������
�,����e���v$U1&��Ï�Bp�r�ʵ|�q��k���y�@�����|{n��.�#aT��|\�Q�Ԡ��p��Y�e���<�V��Aqq�'��`ꯠE�����n������H�X�[}�N?bMPk�a"���o-4[3��	��Y@��b�w�Am�`"Lؑ>�����d-!,p˕�~$؆��HWQt ������ώ��[����}��3��H��I��������`Zm�o�˟�d����uy?p��G&��ʗ/�(0:	���^._�������G|��wBI�h�ˤ܀��&�.�5Ymia�;vi�%�6���X�2�eW5�D<r!��]E�{;��9V�̦LND����4$gBͩw-e�~G�1�-7Dϳ��E�{�>xG��JdԹ�/�}���
Z'U��@��� �,�!����o.�ձ��^_�G�޾k`ˎ�Yc	NG��LX3�F��x_2ݳ�lm'/�q�/R�˓e�AZ�$��4E<�*��%��̚�x�},B��k���v}�v� �!֪5�� [o����cڻ��B��_��Z�B�0��6Th�q뵶�M�Z:�vb��+��Cf�C��]�v,Fw��܎�5i���Ƞ�j��S��('D!��{�#��o��r��Ae�ւ#�3�[�i����T���Ҏj�]j����L�E�d�҉����O:bB'�H�/Z���x��Ї	K/���
;�o�ʕ5�	�* U�i�����>�b�������[l����Ww+	��u.a��Q�?N�_|'��Zxs��d���xL �Q�\i?����Lb!�'a�1�Ӈ�W�0>��0�FW����(S���Y��cZ~�{�_��J�@0q�ڊ�<��X|A����.����r�||K�,���E�eA-#�y�@�/Zp�M< �#��qX��&��J��bkQ��:�!/O7��r���/�Et�u�\M��_�EQ��u�~01�QM��0�\Q��u�c���3��ZIA?�}d��Щ��|��K}:V��sG�/B����D/��>bQ�`ق�eTGL ��3��)�]�C@�"���t�c� ���)���冕B[�<�Sƫ2Z�^��{�_�y�� /7�p� �_ι,8��<Ă`��N_F�
���;>7��>Gj��B*���g>1�$Al<�U�&o�[|C��Ê�t��et�u�e����P8����ze����_F�Z����<R^���5��t��c:"kF"�ߏ�U�ќ7@�B�I�&Z����Q�K4�� ���T�dB�	�4�!��m�oQ����%�	��gt���������d07���KO�#���0麸!�RUy'."�6�'�5ڬ�Bc�NG����Q�q4QS�?��F�b���8��]P��p��r���扩�<��FT��.�R�'��cR`����LF7�ƶ�7����YB=k�H�\����O]����P�u](���:a�]T}�C�X������߾	��7!���/Z\��"���t��݀:��~$���/ G����Ռ�?��PRio�m��\���E�;�(��o��q،>7�(�� ah� �95���XB�Xn�;��06��jj���"D��3j�kE'.-�!H܇��S;t	I.S��}�I�V{���Rq,��>��_OD�w��1Z����G�:�𛎮"�3c�
tkc�F56��&��{��X7�,^�eL�Z?��>�IMڌT	����F����^�\�>�������+�)����Ԩ	rH;���)��N����	�k�C�00H����F�q^���ߏ�'���ܤ��q��]��*&f���ڬ�ݤϞ]�Zv��2_y|��0n7�s&(��������d��%�s�ݹ�S�u�.��*���[8��2	�����ɴeֳ��J|���98�a&y(�}2o^L�x�j9x�������]���`�pp�_&vp�{~���\}��'�Lu�Wv���ADG����63t��Ǎ�q����Q��l�9��aF�:xr�w�(���.��E�1>:7�'x��l��$מ�=vp�F};�	uo_n���`�9��=�\B�7W���9x�\�q*��� �O
8�s�nWZ��
H[˛�'�k͖w.l'b�F�o2$���ύzfq������sBF!�q�f_�g³���Q��}.���pΕR��YF��Zs��1.a���;�wC7��3�����Tډo����'޽hw�%q��ù#�N�;��,�M� ��.r�%���I%]���6��ܞ7;���?�⣸-w��3�;�ؘe�0 99ȼ&�&M�3���g9�9a,Ϲ�@���+��e� c8�� bpG�u����D�>q�y�y6'e.���4�#.��������^-�6�JAǆ�s������+z"2����#��N1P��Ȍ!�:`��b&���]����K0d�O�%�Y8ui��jw��vh9!��Y'I.B����3�f�Deؓw� 6�}m����|}��y��u�ZП�!��	]�D�B�!U�cø*�#����f���;��rPP��q.G�@s`�m�(    [L� �h,&�0T���!N�t󯠶�ꊤ��al >n����]�ˇ���#~����`������I�׈�`��ϴ`�	w�[v{���;�	%`�ū9u!M/~ȵ��rF/���Ƀ���G/D�/#'1|�� EB�*����]� �G����c�����[C�3� ߚ�ԩ� #T~z/c��H�� �
�������@�q���U�i��)gGަZ�Q̨,ڍ�~r�J^)S�;��%���>T���B��0L��7z��1}N�6�1(��u?����1)؆?f"�倨<7iIޙ
�`�wV�~"�ݨOjoڡ滑�;O��K���.ዚ�W�#�W�"Q&�x8f �	�o��2�@P�eA��|?����{��ᓍN	����_V2�1�,���ل.����l��ی$8�ezf���߈�A���%+���~�lB�F�
W̙��^�@ |Psn��x�p�f7:{�g��W��=���E+�H�
ԥV�,��tX��w"M��?��U#��<�3n|,��گd�e�3�_<�ٍ��ڤ��C�j	?Z�Rmxg�/yxg��1a0�s6i�~����`�kX�����Y��xCv�@�π�pтm}���6uu�>sU��G��D�5S��Q���G���ث���C����ЧZy�h[Gvv58h��ᥭ�N�i�t�$��' /m��J�`������U�F�g���s��NὭ��)�%^��ĭm��_���¸�8
Onu������ṁ�2�ʤ#����p��,�-d�Zu�%������?t
�C\�/���#&^��x1�\�����q$��7���+�!)��ݺ�[�F�bG����>���n�?����DW���� ��� �t�'���V�t[#��|�ݍ�N]����`ч<���_0�\��|`!ܹ���~Ӿ� �nC�~��m��z(`c�КC��m,�O4ܹ`u(�>c��%���݀���9\�`u@`� �>G��V�~���ݐ���gBV��*��ۼ�J"ȷ�քwW@G� �n�[���:v����n|
�� 9��>� O?��G��W�d�ikO�ẇ�8�8����� w?�}2A]\�aaW�g��wS� �?��o�WE���-)v�=����1���K��`�0	�.'�~y[x{l%��p�.�٠	���LV}��������P ]�.� �c]v�!�W�	��gv�,a7�� �P��$�)(�^!��Bw�=7��	��Qυ���v�	�!��i귐���}��E�� �Je�[ ~4�.D�/�b��8U)mF-��A8��s�����e'D��5��8�i*|� Lj5cv�3�}�%g�e��1� ����Y�4����w%�� ����z4����j�Ad�82<$_�0/��G�@�˸)VH������t�Oj	����j��n��0a�5�%���ʃ��	y$�en��tyc~UȾB���=�X�U���b�Ąk��̉PI�BĀ�	3G˹�&kq�v]n�L�N�I�8^2���D�ͻ�����c�!��6�!VS:l�RE�~�!��p�H��@`��Ę��	eF!�50cG��SW�q�l41�(��?8�ƭ���S �z߸L��V����#�[IB��'Fk:�05��Uޣܧ��-j��Lc=A�q�H �<��y}�~���)@"�q�f��<uqXVf�qD�w�#�� �Vx��ü<F���Ƒ7�6�i�,�� �.W`L��V$Ȭ��Fe�c��>�Ic&��\�>E7�׭љ
�[ {v<#���m�<:GZ�����a�R&�G��H�,�����)�R�ޗ�HnHY3�~�P�,�BX��w�m��b�)�W��`Y�P:`1M����>ůT>���z]���n =ǈ⺽���(�e�]�T�]���EZ���h�©|c��~]�?��Uq�_�uǠ���b��w<b�jd:�ԠoyiGbj��r�sj�0b�c4׵�mEn��Ǒ�����\Bw��z�9��=�	ls�p�X�k�o�7�i`4�=�n��E�"�t)[W b*Z�`Lj�m���������u�a�A�r�*������{���N���U�զ��?3��G,"<�>x�ua�m���h�Jp�z?�ᢍNo����ŞW�@���Z@�qUΉ{a��;�Wd��
���Us�q:��Fp��?n��<�$xh]Ud��+B�i&��$y��P��V�K��qd�Hk�4�E�ܘ4�I����������Ja�l�5���Ҡfu_M԰S]�����Z��|���R5�Q�d��躅@jU��Vv�$�TC�?��VR��ŉd��-�60���j��W)H���'��ׯ���^w�Z��>s�#��;_���5�Z��%�|�.v�I����I'N��%Ki lJ������6��kP��ѹ�׿��&x���^����]����Xˬ�?ϻ4�8��kص͜ܼ�yt/j��7����bS��絼*8�/ א��M��/+�W	.~�Mp�nҜ�|�ʒ
2V�Ae Ip�Aʴ �LS�w~9��ذ�L���z=�ܠZ{?T�������=c��v�������(K����槛��57N��~^C$��h:�\�L��H0䎝�
?�����]��׿�'�~���N��o5p��-~��nC.	�_�����xv�e��p����_ $𳢹B�" �Z�	Ϗ"�A�ǿ�7'�~kz*���K��������|�ui"��[�Ϳ����b���s�&���.x|k�}%i��?	�Op�V���A��c��˷{������J���>>����F������,F�Eh""L_N��w�"�P7��W���,���}#����
q�A#���@��d~<+vX�s"���9�<�K�KA�A�>�30aG��0\F���l�)� #��o�������Cy�5~w"�\�Y�\���e3T�ܑ��!-D��l,�>"Y�-���'א2����n8�_��<����9�S{�A`8��~�I\��d�v^:[S��< ZK��� ��
#Qꖔ�u���v�c���mcZ����h�m�H�͐m"ڍ����㠠�P�����WD��Z�}�JaJ�?�)�"d�����q�14�z�l.�0"���bB��1��s9���n(}�ԍS��Z|*;[��Fp�	}0�~�/;G�o�~��g��!3��Ũ�|:����򹈯�e�ؑ�Շ�� �����]z�M7�gq����Y�}(��^��b�	�7��3�,�~�T&���Xѕ!�&`3§�0�����������P�V���ՠΫ�&m��� �v�}VAyלeꊀ�#�>D�����C�C9xBp"�B1��Ϋ2H��X]�<��5�-X��9K&�9�z��W<Z�k�K�#��^^ן��h?�GO����*t�1'F�K�u�פ��`:�u�����d2cC���/�m�X��	���J�!{�<���*�ef��:I��	�_��-=*��c*�9�:��~֔��	�B
 3�3|?�>U�L G`]9���!�$T��O�
����%b��~����2^4Lcx���IPT�Y�����`cŴ�*҈��� z�Ƣ
�m�Ă�z��`2L�� �@[1|�~z!�)(��u��� w�s�.h���bE��5ˤ2�<���r�J�@x�J+�5�}) �-�J��'M�A�#ډi�������aUkyEF���"u�@��J��?�a�" $~���L.N�-�ݢć�le�GY`YO%���'���&E_��d��j�q �%�{\0�@"�U�_3t$�Y=��uD���%#�_���ߣ@�P�����m{�������L�ۄS@X����H]�4U�(�*x`�[�,�Ehx	�u��%��WRA�vpY���@�oo*��Oo�u˝��V�ǻ��    �}C�>4��RTC�0W���ao��D>��B�M�� 3�ƿ`�v�}�Fbȣ��`��0<�5�]�@h�!6p��
�\�&�0��ږC:���c�F�h��+�#�;*�#v\����2���{#ѮUcRD�Pq����ą�,W�i����+�9a1�Il t#"6���* 4������+��@%�@�P!]$V��X� 
Vm��@��si��� �b�ݲ���j�XS+Zլ�_�b`Z}v���M���w��V���V��࣎��������;gJ��!�w� ��[1�*�↑���v����x�v��EM9o�0������bY*jVCۮ��X;�W�s���֓d�-3h��=�5P.
�ޤ4��W��Ų|S?)����ۙ@pM�3P��Ȯ�����&���Ӣ�������Ųt��te��[�1ж�u�F7�H���µ/�gy��`�l���B��[�t��~}�@�.jZ�
�hە:�_H����� �1/m;@%����퀡y ��K-B_j��6_O�L2P����S�@���7q;b�����r0����Ѧ�;S�ut��Ay���>k�@�.������]�5�b s����Q�!�s�S�;��@�ަ���n
?N��������Ag�w�v�$� 7f�; U|S�Eq��K~���]l�7ŏA�.=1b�ݥ=���]˴�@�.=QM��t�; �Dj2��"1����:Ī	���D�t�j��*�bE����]*Q�녳X�;`+7��]zb��K��#���K�����!:Eߐ#�	t��8��@�k��|ɻT�^K�4н�]e�V���R�ΛAh�ڳ �a�&�Z�l+�4e;����v�*yi�Ƅ�I]�����I�!U��@0/=�Hh奺կ����1�]�A�-3�SI�楢Qv9\��eK�o����������r�H����Rså�ݍ�-���*��=A��^��:I�=�I���5�@o/=�K(�)�N" ��4�>��.D�R�2* ����>��vC\h����K�\#�/�?X&�D!��
�~�Vi��|\�xO�Ö@rEn�o1PfJ����p�Tb�U�F
������T�p9�(��G8Ek�*�H�%R6��*Oe Ʒ����|,ح�h�g�[������;�����5��
�|鉪A�/��S��U��З�>@Y	4�KO�R}@9aݞX�;K0��$]�3K�X��v@�í��}��*�|ߣ%�]�Y�4�P�M�1P��;��.�7�4C�/=�F������^��T�g?��'�;��y��K�4}b��@�/)sXw����f� 뽺�h �W�*�` ߗ�& �d�c�N�������R�٥��/ɩ]���m��M�T��@ԯB˭��B�ંg���W�1	�	�}-.��CaX��P�KJ����J0U�p](��m41��K�/#��E$v��b�4W�����'��!��_*�3�T` ���¹�K��tND��(�h	�A�/��0��r_C�NU;���C+�d,��Ւ�c�w�q猪�G����1M�f�ꍾ�k�:} {	m(��]���X_�&�h�U��7�1Jh����3���BG�A�i>޷zA�+�hp�ZX�U1��K&w�t��%�*a�?�:C�/���E$�m��IS'��v�<
��OTW\�L����ɦ��C�~�����|�*��&,9��/ t,��]1�-�Σ�9�VԘ�jE���fG�������2�n�$:�k���-����y�L�(���쳋D‒��C\�ޗ�jZ*YA5Q�~���J�|�)���"��x!������ċz�4D��ece@�+Z2>�'�-��"��c�J�{*���@F��� "X��֡"X�N�@H��:[kU�f_P+nm-�_��T;$�>dS͛�y;T}N���|գ��.�"��;4SI���<� ^��H.H��/X����@^��2�h�Ռo��a-�Y>���j����x�F*�@w�_
۟�Rzƶ`�A^�'�(&��[h���� �	ki��@��B�D��Fֲvā��'�������u��� �?�޲Skw'@�p9�r�i��f XH�r}׽[ A�4�)ߌA��`��c�ZX����~�D��� Kf��\ �"@������w��.hd0�}ݫN����a��,ޑ���w	1�O��"�J�+]��������|�쉬��A+}sZ�ü�!A갂�l��~u�|�3���(�杄���a~�CAh'Ke������(���"��ϧo�\b ��bO�?�U״=�)֍�����Q�=}�.�-�\v���&����7`�X�	e������O*����Q�j/�]�mPK	4P?�<	�Gs�(}f ��,�Fx!�T��}/�M
��u�P"�,�!>߰�4I�HJ�fژ�r-lKlI$�,�V�Hb~
�A%1�}�-��\x]Z��մ1�G,�uu����j�h��."�5<QD}��o�!l�O&!�'���@�,k���^h��.�Q�����a�a(�E�Vn�.����1�Oh�\���'���B�O���DT=<GTh0e{�x*n����x?��r��}>�E�T��MEp����x�;��N�j���(��FH��\;:TY�^
�@%�jk��O�6��I��j��6]u���\1?��~P�(��x�X���,V�Y߰6�A�!�F@����8C��k��� h���@Հ�^)�UR�䧸)� r	lv�����R������n�=��@Yq�Ġ���f7���b�Ee�ͦr��)�
�E������vmN�P�-��_�[̚'�V� �X�Ǐ.>Ak֣�p��W$�+g�+���l/�ż�9��b�:)sK$�-���)��EE��H�<XAo}\�U�*�����Fb���r���^e@ю�=�ۺ��8�Y�|��.$||�Q���B�^��xWV�K[5���~��kO0,ξ��xva���%����RE��Y������+�P�'�Y�aE����O���Ú�'?�XYK�����f<��ܡh/yaB,�̧�C�]r����x*��R��S��o��$���r<0��8�����r<8��6���'��x�b�R�����`Y�>W��4�r��z��q<O�*�B^��)Vx�Fy�b��ܩf�V|v�<`���������U��8�Xh��x�b���^s�G�x�⳻䁋��y�u��x�⦠��_��z��;%�a,.B\���	�Xu��{Խf��(ƺ�@? .�=�W�<��9����c��Ogz!��#NLAQ֢��[��52qK����*:o<�lܻ�vū��<����:Yuč��O�����/�c�9�����8i��<�q�n�3�G9>���1O�O]�<̱ݨ��)#�;j�W�D�y�c9�Q؍�َ�nÝ�<�q�O���^�/x��ܖ�f=������1�����}�����?��Ҁ�#�w	Q���x���c��i�z���z����۬{������z
s��w冃w>$4���k��z.����2�_^�f����!-)ʏ���̳`jJ��c1t�:F��ߗ��b!����b��'E9���|G���z�kK��o�;ݒa����>���n|.��'�?����(�����Əy-�]Xƻa�֓]��������;ە=C�ݸ�0���\+���!����ޕ6�«�GXi�����y>4�(4ɬ<O��:�ea<���;M�YY���m�DȬ�����_�����1�R�y\t�����y�,��އ����Ş���S�۶o�}1��u>+X�^مP�#��Z�#�fB�ø��]��S��q�L�l�C��oE��ۇ�tAu���!�F�q�CZ��ϯ�b�C�
5�����pqd`OK�����n���i�'����y�ɵ�
V��D�����I��&�ݓg	�:n�2�$�lR�_K��'�*eu%͓j՝;m/�f�z�    ~[�8	,�z2�Z�S�1��������EO����<}w�%({��@�@ޓ��n�OI����^R߇�aH{�5d�]�q���m\�sW�}2�vgCv���C۳\��Y�z��c{$=+X�<=�m�ޡ���v�-d=���[�	�S'l^��`Wp�k��j�;v���Jȴ.�m���q4l�h���ɓ >���iu��2qy��؉G�����I��S�L#̯��k�r2E�������a����	z"��h=�� ���i&��n��Tl���A���e={?e	< ����>x<v��cY������dד��$st���4]	<-���D�xZv9@I�,��]"l���	<3:�����HY���|�i�~rxl�ƅދ��	<9�sJq)����/������e�&�뽩�P
�_Ө�)��R��<G{�}�U؆(��Gؗ�TA��Hۖw�r?-�:(�7�!�k�Zn|��҇6�>..W�x\T�s�y2�Ǥ�첢ہڠ�JN�AW��gc 	��Fp Z�6ׁ(���Q|?��p����u�
TAK�tF(��#_�i����R͡��:�C��ݺ*	-Њ���e ���Y�Z�#�7����7�Fp�_�PM�v+}�>4���7P׈E�JU\ڼ�P,�A}�Q���In퍽��B6%ZO�闉 �)D��h���ѿ$шq����[U!����W͞�HtD�r� ���!���S<(�6�.+Q��h+��Z,� ~S�[w����jv�_u�H�%6k¯��x�Rg(��f>=ҽURͻ6Z*M����}x (��ue�����\2�>4G0(�!ZuMP�S��m�Ã��f�&�^�4A)P�A[�zP
��B�:*��8q�aJ?k�*(��i�n z��!��	+ȅ
�Ё�T��;���jb�&��^
�D��~�W$�����I~�憑��N��>�HT�J�#yO���#��v�#�(n��B빛&��1�v���>zk�>#��n�I�|���d?O�Hd�SD�?P�2�TH������D�đ�	6���Guu�/���G�a��q%�֒����r�e�HFx֍��d�TU��SE[lݸhV�sWI���Z��H�Z����G2�������sI��?R������^u�Ɍ��4�M$%*"wn�@#�=�(G�"�G�mC\[8�8-)iQB�<���[��M�2�)Q�b�D����ɓ��ߕs�2Ҥ�z��q$O2롤�L��C�T0U�3��[���>t2�Q��M]l�NrT$ ��F#�࿀ĆH��
V{f:�o�-��I�}��k�������������#'�%������D����ɐʩkb���hh��pG��H��Җu�;� ��
~�<>�O^TU(pR+{yQ9�E3���=!�$G�ڃ�G��E�#A��vi9Ց�<E�#9�Srq$IҤ��t֏���t*����^g���nDV�`���D��c�d�.�D2�]QYp��CE��(��HF��/�6�	�NYz/��SJr$�1��(�b�j ��(��@:8o�
���5P+�m�$8��H$�)�|��!�Y��@��W���0Oy��zz3>�o�PTh��Ib�rb#iLI����Fq=�Dҕ����d+���W�w$M�U�')\�u� O)�w5k&��<e�E2[,J�E3�ڐF�bm;E�Tf;�=k���4a?'[>�����[&���x1��Ŷ�������0�n�H[k�D�b��)�XQ�`9J*�[�D�b;�-{Z+r���|���s�M2�Z�͵����?/F�,�5���bX�C!�b�.D�L��?1�`����4�j�΄�		��\�� ٛ6"kgG)!�ɽr�p<\Ů�#h>:+�b�"��o%\�B���R�����/ۃ ���Fkr�,`���%׆�J���}��)?���Psmw�\%(�����)�]}�K����:���Z�k�1(<s��ޔ+���5QZ��'I�\���.@�`M�+���{��W�g8�~�ү,�~"��$�A���6w"�.�Y���V�dXn���,+��<�ԅ��Y�?��� 3�r��*즤Jwhò��g��%��+�|{��~}<ZÇm�>�%������
����+��׊�aSp� �[�}(v
#]�&P�%�	�A0��e���m���_I��ֻ�B?V�s:�M8���<h���S@�c�^���<��Q��b�|,1����x������C��/1�nY��,jQ��s0�a-��4F�v����ޝ�1Wx�4�%Z3UK0%FKV5{�Yb�졓@�v�	$�f	��R�z[op

v.:��r��})5��١3K�����A���l�7�)[��o�&r��rw�Ξ�^W�;[!�CCx�"�ъ渂-�ڳ-�9P�%�=�lj��lE�\�[B��b�'|-8V� E�����X���H&.���Ɉc4u_��T	��ٽm�^�b5��en#u�wU?(+�M�J��QҴ}Af P��廪��~2]�$�������!NK`�#qZ��:�\ͤ+'v�|X��2��Y�}��<�~2Ɋ��ʤ)�Vl���̤(��I&=1v;bf���XJ&M)K�%i#����;g����G&9ٜ�j2ىb����L4�ѭg�w����)��IM�Vp-��tg#�C�0�IL6��LN�"$G����[�������i���I�r��\N�1��Da��T�LFR6�o�dĄ��e����{&��Q���L*謧t�L"������a�L*b��u;������MO�2��Dѯ�D&!QDA0�LD�6ze���o���zX���7�[��>4�F�/vq�_	\d_����q1FW����Р���4le|9؊�Kl9�+��]�bP�+�C1I�O�q��Ik6�؃%��S��/
�ZXs|r�+ؒ�r
m.�Es\_V	w举,��~���j���7�	�����r�Fܖ#�B&�x
e������� �j�������I�a��/���x�2r�v~��~�l�)r��8t{�}�S�_B���c��$e�w!�����$�� U�w�ˤ@�s�>�FD/�L�Qwe������"�oyҙ��)ؓI<�����>F�Q^�ԷT"*���l�'(V�O�o����X(ᲄǗԽ])�ܷ�y�En;afVj��^n>*��H'�'%[�o]�`�$hb=5�H9&�V{,R�	��n)�Ȝ�F�]5�P`�1�Z$��AA��H#��@��*b]>��2.�=r��������
�
#SL��c���#��hR�|P<K��Ί��O���P�e�q�m/��-����
�C��*.�^��B�[���
wb��WG���dcw�}փ�,p���,2�CYֲu6��|'#���
ۚ��0.�uY@�m������	�p?g�P�%ڿ��Ʀ������cT�2%�\j��8+���
�EO��	�(Կ*g�/����F���ի%�)�۬g�T5��Tdd⳰-�yP������&2vK���0f��-tr	�	��Nn������B/�p7ͷ��%�(��`��׳{����*OE,v�`{��B���Y��PE�Ȍk>�?��4��0^Ђ���h.!�<��x84u�AO�E�&���C��kQ��)o��K����[��RLb�#qP��Bm!����}�XH����d�:*���XH�*ʯ�L.-�t�_��Dhp�?bT��D��ŤX�.e��K��cӠ�:��%�#{�KIv��L�,�t	��-�t����!����BN��*-��]2Ѩ�� �
C��*zt�)+>�S�-�u+T7pY��V��4[��b#�[�Be��o�c�S�]�۟
��Ǹ_i��G���GA`�����3�P�!��RS�2NKYkH=l (��r����5�E-�)�֐|(TF)C�_���C�%�R]�y��8O���C�V|冴�n��֐w�ߖ�w�!�(H�[�!���N9���]����p�� bH8��|ۆt�����֐kX���YC�!�}���~~� -  ��C�a���C�a}�i���֐pX�/�ӐuX���>���D��.�[C�a���Z��ܿ@Vx�b!ث7wJ$��������Z������.ͣA3P�KS�5�g�ifh!0�[h�R�`W�1)<߰��6s��m�Jݠ�s'f!�Kt�t[��+
����*E��s
k�{��%}���/%v����܄�-�|+V��,}+V�1[���T�>r��^����� ����ABї�\�V,4}�F�AƬ���_�ՓH,}+��-�|�@�@���)�ܠ;¸J��K��K���P,Ze&x��v�P�|�H@Ũ2P�^��J�=�b�x��RK��R�b׈!*�x��\%�,T~����ń����+,ơ��V�-�~+t���
�6��NH�d��cP��*�)��Y�i 2I�b[�b��y�V���/D�U��B뗊;�c���J��A��m���%�F1�X�����_�`�S��Q_��j�|������8-_B��̈�[�R:d�Ĭ�v������Z��j�	�C5"[��,u!ߊ���^bLGoJ�(�Z��c%�x��M����s,h7�r/A�#M;s��ꪉgj�k�d�E� ET���Ut�l�?t�X�&����8�a]�b1�8N%Z ���|�[G~���9�PT��6R=Mܰ��p[{~����	�����5�X���ǧ�_����!��8�F��P3����U�K�XU�l�2�R��b`-:ͺ�Z���1��+�����"4I����&)<s�P�6�E,�q���0��2�u�?d�"kd	���O2��������Tm��_�]����o+ɳ$$��d$kIF�W[�(t�F		I��������HHvsXKJҎ}� ��d%����?���I�汖��Ap��P�~c�;1�	��%�dIJ�Yrb,�?���
��%)i��53+�ܒ�<M�-وq�O�T�O\���aIB8�^�4��ZR�^����%)�U`�{�O�dڂ<��}�%)0�\�P�#��� ��bY������eT��=���b�^�-�m�ܬ���u����V��^�����D�D�W]~��ds���%�9�%)�ˌ�1s�Z2���%!���"�m-�H��=~\����#툷V��(�!���>M2Ҏ~��`k�DJ�-%bٶ�"8n��>iXђ����5xo-)�S����p�N>E�"\b��/W`^�.��Z��Ʒ���)�`IK�3JKRp7�Љ�%/1iÒ,�II�mC")IY����+ %�����|�!f�)�	�X���td���������΋6      i
   �   x��M
�0���"'��K鶂P���X�-�i��G�Ћ�t70�͌X:�u���Q�5�� sR�l��:xEhX2�[�Y*��Y�T�ݽ�,���Y��ǡFpK�!�/Y���鋁�,̵����j�.�D��9w��8�:���d���n�)�V����>M      j
      x������ � �      l
      x������ � �      n
      x������ � �      �
   &   x�3��L)-��LIL�4�2�t.��L�b���� �I	0      p
   u   x�M���0D��Sd�Ĵb�N���E*AjT�~�Pҝ}�;0��M����Il$ �V��P�#5�0*�~y�䲤���;�<�˚�P��I�c�CL.�]m|��`Ͼ������ Vp2Q      r
   �   x�u�m� ��)� �9��N��&��D$Y�����I�C��f�ف�lfF����M�Ѝ�B�lK+�v=Z�{�@r"���9W@��ڗ~�!>�jڤ�Ru��|��v�X��������a�#s�oiU�O����������	~Y�      t
      x������ � �      u
      x������ � �      v
   8   x�3��qt�r���WpqU��s�twt���	��:%]�9}\øb���� g��      x
   /   x�3��!##]#]sd�!���1qqq���s��qqq ��      z
   1   x�3�4�4�4202�50�50W�50�,A�4�2§�X���&F��� ��      |
   :   x�3���L.JMI�K�L�I-.-.	��!�.JM�,.)J,
H-*��KDQ���� 1X      ~
      x������ � �      
   /  x����n�0���S�<H$e�� C0�.����vW`m��E^�Ċ�&ȎdP�~��������kAG�#Ur1��mem���k a<HW ��vM�x��-�WT�+�$V�fF�a��:F�$!�##чU��	U�jl�iig6��(�/����m�۩���1]2H���JG?�ڌH������
����.������љ���T�g�s�H&��h�i�ƅ��\�Ç]���j�U�V�:^�����������/��0��ʌ��g"ć<K�CO�X��ۆ������i��YЉS�H�#i�$����8�7��R���*@1B�I����{�8��94�ʎ)����7$�!\%2���'o�����^*�A=��	
?,?M�@�e2�J�	y��m������`��P^�vh�gL��s9�Ųmg�f
�{!�ne�e:kحR���HM����z*Rj����Z]'����O������'Q*0~4�U��u:<�.���ʱ���������IA	ր�o�ˏ�>�������b�Ϊ�      �
   �	  x�eX�n;]�_�/�1�)-c9�aǞX	����-F��j*�0��7Yf�� �l�csN�lI	 $�Sd5��zP����<�zқݷ�T�%mtc*2��_�կD2"s�TfS���f�4�7��x9yo���eXY"��A��u�"P������)��L޵��lW�ɭivߛ�ق=��ךf�,B�fٺ����HR��BW�#)Qc1���iB=�����Z�V�ȵ�']ӛVcA*�t]�"� N�fc�HF�Awh�PY��]�b�*��NCJ���޸	�M��o��5��Ϯ�5X��z0���-6Ltm���5>G�eA��'Tj|p��\ҍvck�Y%�m��������ES�)=ySi6 ݮ�ɔ�\�|����5^۔IÁ�:�hj�GuOi���jswI�q��E�S��!����Ԗ�N�?���{�e*�8���RG0�ȼ��>ݟp�{�B�'��R�E��\�5�K�]۵��tM��'� luE ���7���kk[,�d*$��F�e�dv2���w�@�B�����O�/�+E�e�e&��34r�24;���L޻�ck�ɭ�}���eݶ��iF�
t�[���A�en��F�D\�� ��zM:�c��y��|L���(�?���v���R��'��Q���.���s��:�6�/0Q����,���1?o�-��w��}@�yr8O�D�;�K
LGl�M7fM�$ٟ�[��-�$K�ȽZ5���U�Pg��n�c������k����'M�;xv����X6=^F3���;��X8�op9�F�͸çY:��֮�"�	�Vfɍ�h���6�4Z�)�	Dl�0>C����87�b��r}���	b� Jr5�3�s	�Nӟ9����{;l����_H
�_�n��Q�h��Q�V����\"�>��G���\���|JN�U[���(9����U��U(p�t�7|Bv���|���nø�U�[����*#t�k7YP��_-*-4ʥ��y�t"
-���(�l�?b"z��{�r[}Z�I��E����m�M�xz� ��s�o/�7sJA>E0��@
"�;[-�J�W5��Sҷ�j?<#��<�r��8���k�����������R�{�%�� �Y�
�EE�E�V���"�rǬ��QDy`8dxb���{�A%*,�﫱(��qa��rZҾ�Aw$�ᖄ=%�\��˻X��m��D�zMb-�޵�w�&�����K���p5��Ψp#��K:�/��������\IO�a�M�^)�� ��T*�|��̹�#�3�r.����=K�&���*��wk��JL�0 "z'KE�˚��)�=Eh�uh�4����H�!$�j�U)W]C���;�eDb�¹�������v�LO� K#��ut8%9�ue�'�4��]��R��"�/Hy�^�ݞ���}y�A.�T�#�B:��J�Qۖ�Ӂ�F�T��!�h( G[�����+��c���F�CDܡ�=C2yQ�^��V�5�����^^"X jN%X������zObɑrp�_�E9�,f�*O�{'tB��1Rf�[ )�)4���q0C3��l�!'���@
AL�M��3z~�K�M	}��*%�H_�X��XL��G��1l�=8����D��$%"7X�b�hv�!l�&$ NUP!`c'K����b���M0�ed^�K�M.�iD��*l?� `@:_`��":@⁍	����	����b�����YI��8�87�6�3)h��_\��Dq(U�C���ƜI�̤�&��L��@�A���uRK;�w��ղwJ��oͫJh&�Pɘ�8�F�}8��7f��k����5|g{��e/ ��#vợc+�DR�j�zlUD�|@�� ���QFƅE^꬈���6�,�Ņ��t�_�i9F��7����`-�,Q�	*�>vC=xsW�[WK�q�&�[�sN$()�39a4�U0-Gp%c�ㅴC�c�`1�*N���,��� ��Ա
)N� opGY3"��U�G�&�g�+��;���ńFk����Z��lD����*1��,r/qڥ`yĮ]��o	}=�?y�YF�O섦����)X��a������)>(�z��@����p&U�� "�И| ����,�	�I�����}��{ �pɅ}��^�VĔ�]���  X��1+>x���ֻ�����`S	�<]9m��$0��b �3��# �[5��M�:3H����ԑ*9�O�m��M�4B�<	p6��M�t��Jr��� Rz�ko�D4`%2�e�(��*k8*PQN���?c�`{ZN�Jk��F��v��gG$!r�Y��/�W����7F�0�����*;hx2<�J6���F6C��FdJ��c�ÄOD�Ň|� 0�G.ҥ��!�b�����n���'Q��=�8�D��g��*9����<��<"� )���[bp!K���	��B����t�aE���NOO��$A�      �
   &   x�3�40T��H��,(I-�/�2�40R�u�qc���� �C	�      �
   7  x��SYn�0��O�P�0�4�#��&��#�q��&&$CR��,�Kl?����r{�}��>�>_wR��U�4�Rh��-�o�ѫ1�0��X���=~����O߫H�(J|��Gcgg��,X��ƢY$`���� 'ľ��|�
p��Dt6�&H��`l�~�T�V�{a[�uӎ�dIU��4f;Q�S�UY:�ڴ�硊bT(��Δ����k;�q��Swh���T�Hf���E�P��`�6&���u�xA���pktZ�+%��˼<Y$�2?w����fAd�s$}�s랒����_�t+A�H�� ��r��      �
      x������ � �      �
     x�MPMo�0=;���v �Nb�u.z�(먠le��~	�
��`??�X�����h:(����~�q#3R ��q�f�̀R6(���Y��u=\z�vI=�PU �h�����wl*�QE1��>�Z>�H���H�	�)G�Ĕ!�]臻�19�[�t���~?������R1����@���6Q��zǕ���f��y������	E�-��H(�#h���v��[Ȳ |Y�a�s�S����f�      �
      x������ � �      �
   5   x�3�,��)M���S��L��M��K�RR��2�r2K3S�b���� ���      �
      x������ � �      �
      x�mS�r�0����d&�I��E�<<��@$N��"e�Ԍ��YR��霎/,v˷�}tl3�D^(�\M={
iV�Ý_4j.��^�b���f�ES�L^M\�Ebr)�ʨ���]�S�T:v(lp5W6š���R�P��×�5�Fˆ���ӥ'�O��%�\G
�I�U��ڹ -8��bi*�{�B�'���
 �a@�ÁRcxJ�%���¡�F����X�E����`S�&�{t�A�(����p��r����f,:�I; P߹��@���R遟)ME�6����Z���2~|�����5�;��a|H��k7sM^�B�����Q6.0�S������H�G��]7�|V����5�]���SmR]���Nj>�+Rh���axڗ�'v5�oG�%�)�������ƙoU<��w�W��g�~�M#�[�m)�X�\�|�{)��h���^y�	~J���04{w��f���l^z���)6��j�r;xL�@R�*1AD�3���g�
�~���/����      �
      x�3�4�2c#�=... {      �
      x������ � �      �
      x�3��(�MMIL�2�,NMN����� I��      �
      x������ � �      �
      x������ � �      �
      x������ � �      �
   8   x�-��	  ��0�&�p��CE��A.��5�����L�땯�vq5��0
�      �
   g  x��л��@ ����������46�pQ$�"y�%�]c�ݩ�|91�)�*�Q��F���&���=H����2�s���s){�I:�o�	�%&Q��7����ty&e������s�FM�6�Y|eސ�4ޗ�,�&UW�Al.�:(�ֿ9�W ,�"�/�T#U� �,�1C)��7VwH�}0���;����_��Y-��}^�������J/�~Z�7q@d��_��Xə����@����Ԃ!������3�k�3�M�:�Ƶ�9��"J���Bi�4)�>��7�!��|Q\�֖i������c���&�mˣ��g*FU_�NV�뉎v?L800������ y���#ϲ�7�K��      �
      x������ � �      �
      x�3�44�2�44&\& �D��qqq ?�      �
   f   x�5�=
�0��Y:�P
�r�N�ˇ%b��ӧC������F
]3�,�\D-v{��dHX}�)�4@��U���mN3OVD����*��w����o�ğ'3���$3     