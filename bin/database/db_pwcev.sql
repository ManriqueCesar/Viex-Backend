PGDMP     6                    x            db_pwcev    12.3    12.3 A    ]           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            ^           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            _           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            `           1262    24687    db_pwcev    DATABASE     ?   CREATE DATABASE db_pwcev WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Spanish_Spain.1252' LC_CTYPE = 'Spanish_Spain.1252';
    DROP DATABASE db_pwcev;
                postgres    false            ?            1259    24690    curso    TABLE     &  CREATE TABLE public.curso (
    id_curso integer NOT NULL,
    eap character varying(200) NOT NULL,
    alumnos_email character varying(500) NOT NULL,
    centro_estudios character varying(200) NOT NULL,
    nombre character varying(100) NOT NULL,
    periodo character varying(10) NOT NULL
);
    DROP TABLE public.curso;
       public         heap    postgres    false            ?            1259    24688    curso_id_curso_seq    SEQUENCE     ?   CREATE SEQUENCE public.curso_id_curso_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.curso_id_curso_seq;
       public          postgres    false    203            a           0    0    curso_id_curso_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.curso_id_curso_seq OWNED BY public.curso.id_curso;
          public          postgres    false    202            ?            1259    24789    detallecurso    TABLE     ?   CREATE TABLE public.detallecurso (
    id_detalle_curso integer NOT NULL,
    id_curso integer NOT NULL,
    id_usuario integer NOT NULL
);
     DROP TABLE public.detallecurso;
       public         heap    postgres    false            ?            1259    24787 !   detallecurso_id_detalle_curso_seq    SEQUENCE     ?   CREATE SEQUENCE public.detallecurso_id_detalle_curso_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 8   DROP SEQUENCE public.detallecurso_id_detalle_curso_seq;
       public          postgres    false    216            b           0    0 !   detallecurso_id_detalle_curso_seq    SEQUENCE OWNED BY     g   ALTER SEQUENCE public.detallecurso_id_detalle_curso_seq OWNED BY public.detallecurso.id_detalle_curso;
          public          postgres    false    215            ?            1259    24701    examen    TABLE     ?   CREATE TABLE public.examen (
    id_examen integer NOT NULL,
    tiempo_duracion real NOT NULL,
    descripcion character varying(200) NOT NULL,
    titulo character varying(100) NOT NULL,
    id_curso integer NOT NULL,
    id_usuario integer NOT NULL
);
    DROP TABLE public.examen;
       public         heap    postgres    false            ?            1259    24699    examen_id_examen_seq    SEQUENCE     ?   CREATE SEQUENCE public.examen_id_examen_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.examen_id_examen_seq;
       public          postgres    false    205            c           0    0    examen_id_examen_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.examen_id_examen_seq OWNED BY public.examen.id_examen;
          public          postgres    false    204            ?            1259    24709    pregunta    TABLE     ?   CREATE TABLE public.pregunta (
    id_pregunta integer NOT NULL,
    descripcion character varying(200) NOT NULL,
    puntaje real NOT NULL,
    id_examen integer NOT NULL
);
    DROP TABLE public.pregunta;
       public         heap    postgres    false            ?            1259    24707    pregunta_id_pregunta_seq    SEQUENCE     ?   CREATE SEQUENCE public.pregunta_id_pregunta_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.pregunta_id_pregunta_seq;
       public          postgres    false    207            d           0    0    pregunta_id_pregunta_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.pregunta_id_pregunta_seq OWNED BY public.pregunta.id_pregunta;
          public          postgres    false    206            ?            1259    24717 	   respuesta    TABLE     ?   CREATE TABLE public.respuesta (
    id_respuesta integer NOT NULL,
    descripcion character varying(200) NOT NULL,
    estado boolean NOT NULL,
    id_pregunta integer NOT NULL
);
    DROP TABLE public.respuesta;
       public         heap    postgres    false            ?            1259    24715    respuesta_id_respuesta_seq    SEQUENCE     ?   CREATE SEQUENCE public.respuesta_id_respuesta_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public.respuesta_id_respuesta_seq;
       public          postgres    false    209            e           0    0    respuesta_id_respuesta_seq    SEQUENCE OWNED BY     Y   ALTER SEQUENCE public.respuesta_id_respuesta_seq OWNED BY public.respuesta.id_respuesta;
          public          postgres    false    208            ?            1259    24725 	   resultado    TABLE     ?   CREATE TABLE public.resultado (
    id_resultado integer NOT NULL,
    nota real NOT NULL,
    tiempo_fuera real NOT NULL,
    id_examen integer NOT NULL,
    id_usuario integer NOT NULL
);
    DROP TABLE public.resultado;
       public         heap    postgres    false            ?            1259    24723    resultado_id_resultado_seq    SEQUENCE     ?   CREATE SEQUENCE public.resultado_id_resultado_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public.resultado_id_resultado_seq;
       public          postgres    false    211            f           0    0    resultado_id_resultado_seq    SEQUENCE OWNED BY     Y   ALTER SEQUENCE public.resultado_id_resultado_seq OWNED BY public.resultado.id_resultado;
          public          postgres    false    210            ?            1259    24731    rol    TABLE     ?   CREATE TABLE public.rol (
    id_rol integer NOT NULL,
    descripcion character varying(255),
    nombre character varying(255)
);
    DROP TABLE public.rol;
       public         heap    postgres    false            ?            1259    24739    usuario    TABLE     m  CREATE TABLE public.usuario (
    id_usuario integer NOT NULL,
    apellido character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    estado boolean NOT NULL,
    face_url character varying(255) NOT NULL,
    nombre character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    username character varying(255) NOT NULL
);
    DROP TABLE public.usuario;
       public         heap    postgres    false            ?            1259    24747    usuario_rol    TABLE     b   CREATE TABLE public.usuario_rol (
    id_usuario integer NOT NULL,
    id_rol integer NOT NULL
);
    DROP TABLE public.usuario_rol;
       public         heap    postgres    false            ?
           2604    24693    curso id_curso    DEFAULT     p   ALTER TABLE ONLY public.curso ALTER COLUMN id_curso SET DEFAULT nextval('public.curso_id_curso_seq'::regclass);
 =   ALTER TABLE public.curso ALTER COLUMN id_curso DROP DEFAULT;
       public          postgres    false    202    203    203            ?
           2604    24792    detallecurso id_detalle_curso    DEFAULT     ?   ALTER TABLE ONLY public.detallecurso ALTER COLUMN id_detalle_curso SET DEFAULT nextval('public.detallecurso_id_detalle_curso_seq'::regclass);
 L   ALTER TABLE public.detallecurso ALTER COLUMN id_detalle_curso DROP DEFAULT;
       public          postgres    false    215    216    216            ?
           2604    24704    examen id_examen    DEFAULT     t   ALTER TABLE ONLY public.examen ALTER COLUMN id_examen SET DEFAULT nextval('public.examen_id_examen_seq'::regclass);
 ?   ALTER TABLE public.examen ALTER COLUMN id_examen DROP DEFAULT;
       public          postgres    false    205    204    205            ?
           2604    24712    pregunta id_pregunta    DEFAULT     |   ALTER TABLE ONLY public.pregunta ALTER COLUMN id_pregunta SET DEFAULT nextval('public.pregunta_id_pregunta_seq'::regclass);
 C   ALTER TABLE public.pregunta ALTER COLUMN id_pregunta DROP DEFAULT;
       public          postgres    false    206    207    207            ?
           2604    24720    respuesta id_respuesta    DEFAULT     ?   ALTER TABLE ONLY public.respuesta ALTER COLUMN id_respuesta SET DEFAULT nextval('public.respuesta_id_respuesta_seq'::regclass);
 E   ALTER TABLE public.respuesta ALTER COLUMN id_respuesta DROP DEFAULT;
       public          postgres    false    208    209    209            ?
           2604    24728    resultado id_resultado    DEFAULT     ?   ALTER TABLE ONLY public.resultado ALTER COLUMN id_resultado SET DEFAULT nextval('public.resultado_id_resultado_seq'::regclass);
 E   ALTER TABLE public.resultado ALTER COLUMN id_resultado DROP DEFAULT;
       public          postgres    false    210    211    211            M          0    24690    curso 
   TABLE DATA           _   COPY public.curso (id_curso, eap, alumnos_email, centro_estudios, nombre, periodo) FROM stdin;
    public          postgres    false    203   N       Z          0    24789    detallecurso 
   TABLE DATA           N   COPY public.detallecurso (id_detalle_curso, id_curso, id_usuario) FROM stdin;
    public          postgres    false    216   ?N       O          0    24701    examen 
   TABLE DATA           g   COPY public.examen (id_examen, tiempo_duracion, descripcion, titulo, id_curso, id_usuario) FROM stdin;
    public          postgres    false    205   ?N       Q          0    24709    pregunta 
   TABLE DATA           P   COPY public.pregunta (id_pregunta, descripcion, puntaje, id_examen) FROM stdin;
    public          postgres    false    207   ?N       S          0    24717 	   respuesta 
   TABLE DATA           S   COPY public.respuesta (id_respuesta, descripcion, estado, id_pregunta) FROM stdin;
    public          postgres    false    209   ?N       U          0    24725 	   resultado 
   TABLE DATA           \   COPY public.resultado (id_resultado, nota, tiempo_fuera, id_examen, id_usuario) FROM stdin;
    public          postgres    false    211   O       V          0    24731    rol 
   TABLE DATA           :   COPY public.rol (id_rol, descripcion, nombre) FROM stdin;
    public          postgres    false    212   %O       W          0    24739    usuario 
   TABLE DATA           l   COPY public.usuario (id_usuario, apellido, email, estado, face_url, nombre, password, username) FROM stdin;
    public          postgres    false    213   ?O       X          0    24747    usuario_rol 
   TABLE DATA           9   COPY public.usuario_rol (id_usuario, id_rol) FROM stdin;
    public          postgres    false    214   3P       g           0    0    curso_id_curso_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.curso_id_curso_seq', 1, false);
          public          postgres    false    202            h           0    0 !   detallecurso_id_detalle_curso_seq    SEQUENCE SET     P   SELECT pg_catalog.setval('public.detallecurso_id_detalle_curso_seq', 1, false);
          public          postgres    false    215            i           0    0    examen_id_examen_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.examen_id_examen_seq', 1, false);
          public          postgres    false    204            j           0    0    pregunta_id_pregunta_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.pregunta_id_pregunta_seq', 1, false);
          public          postgres    false    206            k           0    0    respuesta_id_respuesta_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('public.respuesta_id_respuesta_seq', 1, false);
          public          postgres    false    208            l           0    0    resultado_id_resultado_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('public.resultado_id_resultado_seq', 1, false);
          public          postgres    false    210            ?
           2606    24698    curso curso_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.curso
    ADD CONSTRAINT curso_pkey PRIMARY KEY (id_curso);
 :   ALTER TABLE ONLY public.curso DROP CONSTRAINT curso_pkey;
       public            postgres    false    203            ?
           2606    24794    detallecurso detallecurso_pkey 
   CONSTRAINT     j   ALTER TABLE ONLY public.detallecurso
    ADD CONSTRAINT detallecurso_pkey PRIMARY KEY (id_detalle_curso);
 H   ALTER TABLE ONLY public.detallecurso DROP CONSTRAINT detallecurso_pkey;
       public            postgres    false    216            ?
           2606    24706    examen examen_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.examen
    ADD CONSTRAINT examen_pkey PRIMARY KEY (id_examen);
 <   ALTER TABLE ONLY public.examen DROP CONSTRAINT examen_pkey;
       public            postgres    false    205            ?
           2606    24714    pregunta pregunta_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.pregunta
    ADD CONSTRAINT pregunta_pkey PRIMARY KEY (id_pregunta);
 @   ALTER TABLE ONLY public.pregunta DROP CONSTRAINT pregunta_pkey;
       public            postgres    false    207            ?
           2606    24722    respuesta respuesta_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.respuesta
    ADD CONSTRAINT respuesta_pkey PRIMARY KEY (id_respuesta);
 B   ALTER TABLE ONLY public.respuesta DROP CONSTRAINT respuesta_pkey;
       public            postgres    false    209            ?
           2606    24730    resultado resultado_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.resultado
    ADD CONSTRAINT resultado_pkey PRIMARY KEY (id_resultado);
 B   ALTER TABLE ONLY public.resultado DROP CONSTRAINT resultado_pkey;
       public            postgres    false    211            ?
           2606    24738    rol rol_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.rol
    ADD CONSTRAINT rol_pkey PRIMARY KEY (id_rol);
 6   ALTER TABLE ONLY public.rol DROP CONSTRAINT rol_pkey;
       public            postgres    false    212            ?
           2606    24751 $   usuario uk_863n1y3x0jalatoir4325ehal 
   CONSTRAINT     c   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT uk_863n1y3x0jalatoir4325ehal UNIQUE (username);
 N   ALTER TABLE ONLY public.usuario DROP CONSTRAINT uk_863n1y3x0jalatoir4325ehal;
       public            postgres    false    213            ?
           2606    24746    usuario usuario_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id_usuario);
 >   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_pkey;
       public            postgres    false    213            ?
           2606    24782 '   usuario_rol fk3ftpt75ebughsiy5g03b11akt    FK CONSTRAINT     ?   ALTER TABLE ONLY public.usuario_rol
    ADD CONSTRAINT fk3ftpt75ebughsiy5g03b11akt FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);
 Q   ALTER TABLE ONLY public.usuario_rol DROP CONSTRAINT fk3ftpt75ebughsiy5g03b11akt;
       public          postgres    false    213    2753    214            ?
           2606    24795    detallecurso fk_detalle_curso    FK CONSTRAINT     ?   ALTER TABLE ONLY public.detallecurso
    ADD CONSTRAINT fk_detalle_curso FOREIGN KEY (id_curso) REFERENCES public.curso(id_curso);
 G   ALTER TABLE ONLY public.detallecurso DROP CONSTRAINT fk_detalle_curso;
       public          postgres    false    216    2739    203            ?
           2606    24800    detallecurso fk_detalle_usuario    FK CONSTRAINT     ?   ALTER TABLE ONLY public.detallecurso
    ADD CONSTRAINT fk_detalle_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);
 I   ALTER TABLE ONLY public.detallecurso DROP CONSTRAINT fk_detalle_usuario;
       public          postgres    false    216    2753    213            ?
           2606    24752    examen fk_examen_curso    FK CONSTRAINT     |   ALTER TABLE ONLY public.examen
    ADD CONSTRAINT fk_examen_curso FOREIGN KEY (id_curso) REFERENCES public.curso(id_curso);
 @   ALTER TABLE ONLY public.examen DROP CONSTRAINT fk_examen_curso;
       public          postgres    false    2739    205    203            ?
           2606    24805    examen fk_examen_user    FK CONSTRAINT     ?   ALTER TABLE ONLY public.examen
    ADD CONSTRAINT fk_examen_user FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);
 ?   ALTER TABLE ONLY public.examen DROP CONSTRAINT fk_examen_user;
       public          postgres    false    2753    205    213            ?
           2606    24757    pregunta fk_pregunta_examen    FK CONSTRAINT     ?   ALTER TABLE ONLY public.pregunta
    ADD CONSTRAINT fk_pregunta_examen FOREIGN KEY (id_examen) REFERENCES public.examen(id_examen);
 E   ALTER TABLE ONLY public.pregunta DROP CONSTRAINT fk_pregunta_examen;
       public          postgres    false    205    207    2741            ?
           2606    24762    respuesta fk_respuesta_pregunta    FK CONSTRAINT     ?   ALTER TABLE ONLY public.respuesta
    ADD CONSTRAINT fk_respuesta_pregunta FOREIGN KEY (id_pregunta) REFERENCES public.pregunta(id_pregunta);
 I   ALTER TABLE ONLY public.respuesta DROP CONSTRAINT fk_respuesta_pregunta;
       public          postgres    false    209    207    2743            ?
           2606    24767    resultado fk_resultado_examen    FK CONSTRAINT     ?   ALTER TABLE ONLY public.resultado
    ADD CONSTRAINT fk_resultado_examen FOREIGN KEY (id_examen) REFERENCES public.examen(id_examen);
 G   ALTER TABLE ONLY public.resultado DROP CONSTRAINT fk_resultado_examen;
       public          postgres    false    2741    211    205            ?
           2606    24772    resultado fk_resultado_usuario    FK CONSTRAINT     ?   ALTER TABLE ONLY public.resultado
    ADD CONSTRAINT fk_resultado_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);
 H   ALTER TABLE ONLY public.resultado DROP CONSTRAINT fk_resultado_usuario;
       public          postgres    false    211    213    2753            ?
           2606    24777 '   usuario_rol fkkxcv7htfnm9x1wkofnud0ewql    FK CONSTRAINT     ?   ALTER TABLE ONLY public.usuario_rol
    ADD CONSTRAINT fkkxcv7htfnm9x1wkofnud0ewql FOREIGN KEY (id_rol) REFERENCES public.rol(id_rol);
 Q   ALTER TABLE ONLY public.usuario_rol DROP CONSTRAINT fkkxcv7htfnm9x1wkofnud0ewql;
       public          postgres    false    214    212    2749            M   }   x??1!Ek8??4Z?m???2	;?0?fϵG?b??????\? 6?F?????????1??e?>?l?L?qVrwH$??`????-??jv_+?$??,m??M1&?????}?+?      Z      x?????? ? ?      O      x?????? ? ?      Q      x?????? ? ?      S      x?????? ? ?      U      x?????? ? ?      V   ^   x?3?tL????,.)JTH?Q?Rs?DS򋸌8??R?????y??
a?E%??9?ŜE?i??@%Ɯ??ɩ)?
H????sJs???b???? (W'?      W   ?   x?U?A?0E?3???T?.5$?$q???KS?N??n/Q@Y???_U?9?gv?E???.!?j?7??M??fCP???? j?rY??ϡ??8?G???zbC???,r3?P?8Ŋ??k86?)?d??^?\d>?G??O	5R?D|%?I?      X      x?3?4?2?4?2?4?????? E     