CREATE TABLE "users" (
	"user_id" serial NOT NULL,
	"title" varchar(4000) NOT NULL,
	"phone" varchar(4000) NOT NULL,
	"email" varchar(4000) NOT NULL,
	"password" varchar(4000) NOT NULL,
	"is_enabled" BOOLEAN NOT NULL DEFAULT 'false',
	"create_date" TIMESTAMP NOT NULL,
	"confirm_token" varchar(300) NOT NULL,
	"is_confirmed" BOOLEAN NOT NULL,
	"confirmed_date" TIMESTAMP,
	CONSTRAINT users_pk PRIMARY KEY ("user_id")
) WITH (
OIDS=FALSE
);



CREATE TABLE "users_roles" (
	"user_role_id" serial NOT NULL,
	"user_id" bigint NOT NULL,
	"role_id" bigint NOT NULL,
	CONSTRAINT users_roles_pk PRIMARY KEY ("user_role_id")
) WITH (
OIDS=FALSE
);



CREATE TABLE "roles" (
	"role_id" serial NOT NULL,
	"title" varchar(4000) NOT NULL,
	CONSTRAINT roles_pk PRIMARY KEY ("role_id")
) WITH (
OIDS=FALSE
);



CREATE TABLE "chat_messages" (
	"chat_history_id" serial NOT NULL,
	"sender_id" bigint NOT NULL,
	"receiver_id" bigint NOT NULL,
	"notificationInputMessage" varchar(4000) NOT NULL,
	"send_date" TIMESTAMP NOT NULL,
	CONSTRAINT chat_messages_pk PRIMARY KEY ("chat_history_id")
) WITH (
OIDS=FALSE
);



CREATE TABLE "notifications" (
	"notification_id" serial NOT NULL,
	"receiver_id" bigint NOT NULL,
	"subject" varchar(4000) NOT NULL,
	"message_test" varchar(4000) NOT NULL,
	"create_date" TIMESTAMP NOT NULL,
	"status" varchar(25) NOT NULL,
	CONSTRAINT notifications_pk PRIMARY KEY ("notification_id")
) WITH (
OIDS=FALSE
);



CREATE TABLE "partners" (
	"partners_id" bigserial NOT NULL,
	"sender_id" bigint NOT NULL,
	"receiver_id" bigint NOT NULL,
	"status" TEXT NOT NULL,
	"create_date" TIMESTAMP NOT NULL,
	"last_change_date" TIMESTAMP NOT NULL,
	"mark_for_sender" bigint,
	"mark_for_receiver" bigint,
	CONSTRAINT partners_pk PRIMARY KEY ("partners_id")
) WITH (
OIDS=FALSE
);



CREATE TABLE "categories" (
	"category_id" serial NOT NULL,
	"parent_id" bigint,
	"title" varchar(300) NOT NULL,
	CONSTRAINT categories_pk PRIMARY KEY ("category_id")
) WITH (
OIDS=FALSE
);



CREATE TABLE "offer_categories" (
	"company_id" bigint NOT NULL,
	"category_id" bigint NOT NULL
) WITH (
OIDS=FALSE
);



CREATE TABLE "companies" (
	"company_id" bigint NOT NULL,
	"contact_person" varchar(300) NOT NULL,
	"contact_email" TEXT NOT NULL,
	"caption" varchar(4000) NOT NULL,
	"description" varchar(4000) NOT NULL,
	"about" varchar(4000) NOT NULL,
	"rating" numeric(8,2),
	"region_id" bigint,
	"gender_male" BOOLEAN NOT NULL,
	"gender_female" BOOLEAN NOT NULL,
	"age_min" bigint NOT NULL,
	"age_max" bigint NOT NULL,
	"income_min" bigint NOT NULL,
	"income_max" bigint NOT NULL,
	"client_count" bigint NOT NULL,
	"photo_id" bigint,
	CONSTRAINT companies_pk PRIMARY KEY ("company_id")
) WITH (
OIDS=FALSE
);



CREATE TABLE "blogs" (
	"blog_id" serial NOT NULL,
	"title" varchar(4000) NOT NULL,
	"body" varchar(4000) NOT NULL,
	"create_date" TIMESTAMP NOT NULL,
	"photo_id" bigint NOT NULL,
	CONSTRAINT blogs_pk PRIMARY KEY ("blog_id")
) WITH (
OIDS=FALSE
);



CREATE TABLE "regions" (
	"region_id" serial NOT NULL,
	"title" varchar(4000) NOT NULL,
	CONSTRAINT regions_pk PRIMARY KEY ("region_id")
) WITH (
OIDS=FALSE
);



CREATE TABLE "search_orders" (
	"search_order_id" serial NOT NULL,
	"company_id" bigint,
	"search" varchar(4000) NOT NULL,
	"marketing_task" varchar(4000) NOT NULL,
	"region" varchar(4000) NOT NULL,
	"offer" varchar(4000) NOT NULL,
	"deadline" TIMESTAMP NOT NULL,
	"contacts" varchar(4000),
	"categories" varchar(4000),
	"photo_id" bigint,
	"order_date" TIMESTAMP NOT NULL,
	CONSTRAINT search_orders_pk PRIMARY KEY ("search_order_id")
) WITH (
OIDS=FALSE
);



CREATE TABLE "reviews" (
	"review_id" serial NOT NULL,
	"company_id" bigint NOT NULL,
	"author_id" bigint NOT NULL,
	"contact_person" varchar(300) NOT NULL,
	"notificationInputMessage" varchar(4000) NOT NULL,
	"is_good" BOOLEAN NOT NULL,
	"create_date" TIMESTAMP NOT NULL,
	CONSTRAINT reviews_pk PRIMARY KEY ("review_id")
) WITH (
OIDS=FALSE
);



CREATE TABLE "photos" (
	"photo_id" serial NOT NULL,
	"original_path" varchar(4000) NOT NULL,
	CONSTRAINT photos_pk PRIMARY KEY ("photo_id")
) WITH (
OIDS=FALSE
);



CREATE TABLE "favorites" (
	"company_id" bigint NOT NULL,
	"favorite_company_id" bigint NOT NULL
) WITH (
OIDS=FALSE
);



CREATE TABLE "marketing_channels" (
	"marketing_channel_id" serial NOT NULL,
	"title" varchar(4000) NOT NULL,
	CONSTRAINT marketing_channels_pk PRIMARY KEY ("marketing_channel_id")
) WITH (
OIDS=FALSE
);



CREATE TABLE "marketing_channels_companies" (
	"company_id" bigint NOT NULL,
	"marketing_channel_id" bigint NOT NULL
) WITH (
OIDS=FALSE
);



CREATE TABLE "search_categories" (
	"company_id" bigint NOT NULL,
	"category_id" bigint NOT NULL
) WITH (
OIDS=FALSE
);



CREATE TABLE "partners_notifications" (
	"partners_notification_id" serial NOT NULL,
	"sender_id" bigint NOT NULL,
	"receiver_id" bigint NOT NULL,
	"status" TEXT NOT NULL,
	"date_create" TIMESTAMP NOT NULL,
	"is_readed" BOOLEAN NOT NULL,
	CONSTRAINT partners_notifications_pk PRIMARY KEY ("partners_notification_id")
) WITH (
OIDS=FALSE
);




ALTER TABLE "users_roles" ADD CONSTRAINT "users_roles_fk0" FOREIGN KEY ("user_id") REFERENCES "users"("user_id");
ALTER TABLE "users_roles" ADD CONSTRAINT "users_roles_fk1" FOREIGN KEY ("role_id") REFERENCES "roles"("role_id");


ALTER TABLE "chat_messages" ADD CONSTRAINT "chat_messages_fk0" FOREIGN KEY ("sender_id") REFERENCES "companies"("company_id");
ALTER TABLE "chat_messages" ADD CONSTRAINT "chat_messages_fk1" FOREIGN KEY ("receiver_id") REFERENCES "companies"("company_id");

ALTER TABLE "notifications" ADD CONSTRAINT "notifications_fk0" FOREIGN KEY ("receiver_id") REFERENCES "companies"("company_id");

ALTER TABLE "partners" ADD CONSTRAINT "partners_fk0" FOREIGN KEY ("sender_id") REFERENCES "companies"("company_id");
ALTER TABLE "partners" ADD CONSTRAINT "partners_fk1" FOREIGN KEY ("receiver_id") REFERENCES "companies"("company_id");

ALTER TABLE "categories" ADD CONSTRAINT "categories_fk0" FOREIGN KEY ("parent_id") REFERENCES "categories"("category_id");

ALTER TABLE "offer_categories" ADD CONSTRAINT "offer_categories_fk0" FOREIGN KEY ("company_id") REFERENCES "companies"("company_id");
ALTER TABLE "offer_categories" ADD CONSTRAINT "offer_categories_fk1" FOREIGN KEY ("category_id") REFERENCES "categories"("category_id");

ALTER TABLE "companies" ADD CONSTRAINT "companies_fk0" FOREIGN KEY ("company_id") REFERENCES "users"("user_id");
ALTER TABLE "companies" ADD CONSTRAINT "companies_fk1" FOREIGN KEY ("region_id") REFERENCES "regions"("region_id");
ALTER TABLE "companies" ADD CONSTRAINT "companies_fk2" FOREIGN KEY ("photo_id") REFERENCES "photos"("photo_id");

ALTER TABLE "blogs" ADD CONSTRAINT "blogs_fk0" FOREIGN KEY ("photo_id") REFERENCES "photos"("photo_id");


ALTER TABLE "search_orders" ADD CONSTRAINT "search_orders_fk0" FOREIGN KEY ("company_id") REFERENCES "companies"("company_id");
ALTER TABLE "search_orders" ADD CONSTRAINT "search_orders_fk1" FOREIGN KEY ("photo_id") REFERENCES "photos"("photo_id");

ALTER TABLE "reviews" ADD CONSTRAINT "reviews_fk0" FOREIGN KEY ("company_id") REFERENCES "companies"("company_id");
ALTER TABLE "reviews" ADD CONSTRAINT "reviews_fk1" FOREIGN KEY ("author_id") REFERENCES "companies"("company_id");


ALTER TABLE "favorites" ADD CONSTRAINT "favorites_fk0" FOREIGN KEY ("company_id") REFERENCES "companies"("company_id");
ALTER TABLE "favorites" ADD CONSTRAINT "favorites_fk1" FOREIGN KEY ("favorite_company_id") REFERENCES "companies"("company_id");


ALTER TABLE "marketing_channels_companies" ADD CONSTRAINT "marketing_channels_companies_fk0" FOREIGN KEY ("company_id") REFERENCES "companies"("company_id");
ALTER TABLE "marketing_channels_companies" ADD CONSTRAINT "marketing_channels_companies_fk1" FOREIGN KEY ("marketing_channel_id") REFERENCES "marketing_channels"("marketing_channel_id");

ALTER TABLE "search_categories" ADD CONSTRAINT "search_categories_fk0" FOREIGN KEY ("company_id") REFERENCES "companies"("company_id");
ALTER TABLE "search_categories" ADD CONSTRAINT "search_categories_fk1" FOREIGN KEY ("category_id") REFERENCES "categories"("category_id");





INSERT INTO users (title, phone, email, password, create_date, confirm_token, is_confirmed, confirmed_date)
VALUES ('Нет названия', '8-800-2000-600', 'test@test.ru', '1q2w3e4r5t6y', CURRENT_TIMESTAMP, '', false, CURRENT_TIMESTAMP);

INSERT INTO roles (title) VALUES ('ROLE_ADMIN');
INSERT INTO roles (title) VALUES ('ROLE_USER');
INSERT INTO roles (title) VALUES ('ROLE_CONFIRMED_USER');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);


INSERT INTO regions (title) VALUES ('Абаза');
INSERT INTO regions (title) VALUES ('Абакан');
INSERT INTO regions (title) VALUES ('Абдулино');
INSERT INTO regions (title) VALUES ('Абинск');
INSERT INTO regions (title) VALUES ('Агидель');
INSERT INTO regions (title) VALUES ('Агрыз');
INSERT INTO regions (title) VALUES ('Адыгейск');
INSERT INTO regions (title) VALUES ('Азнакаево');
INSERT INTO regions (title) VALUES ('Азов');
INSERT INTO regions (title) VALUES ('Ак-Довурак');
INSERT INTO regions (title) VALUES ('Аксай');
INSERT INTO regions (title) VALUES ('Алагир');
INSERT INTO regions (title) VALUES ('Алапаевск');
INSERT INTO regions (title) VALUES ('Алатырь');
INSERT INTO regions (title) VALUES ('Алдан');
INSERT INTO regions (title) VALUES ('Алейск');
INSERT INTO regions (title) VALUES ('Александров');
INSERT INTO regions (title) VALUES ('Александровск');
INSERT INTO regions (title) VALUES ('Александровск-Сахалинский');
INSERT INTO regions (title) VALUES ('Алексеевка');
INSERT INTO regions (title) VALUES ('Алексин');
INSERT INTO regions (title) VALUES ('Алзамай');
INSERT INTO regions (title) VALUES ('Алупка');
INSERT INTO regions (title) VALUES ('Алушта');
INSERT INTO regions (title) VALUES ('Альметьевск');
INSERT INTO regions (title) VALUES ('Амурск');
INSERT INTO regions (title) VALUES ('Анадырь');
INSERT INTO regions (title) VALUES ('Анапа');
INSERT INTO regions (title) VALUES ('Ангарск');
INSERT INTO regions (title) VALUES ('Андреаполь');
INSERT INTO regions (title) VALUES ('Анжеро-Судженск');
INSERT INTO regions (title) VALUES ('Анива');
INSERT INTO regions (title) VALUES ('Апатиты');
INSERT INTO regions (title) VALUES ('Апрелевка');
INSERT INTO regions (title) VALUES ('Апшеронск');
INSERT INTO regions (title) VALUES ('Арамиль');
INSERT INTO regions (title) VALUES ('Аргун');
INSERT INTO regions (title) VALUES ('Ардатов');
INSERT INTO regions (title) VALUES ('Ардон');
INSERT INTO regions (title) VALUES ('Арзамас');
INSERT INTO regions (title) VALUES ('Аркадак');
INSERT INTO regions (title) VALUES ('Армавир');
INSERT INTO regions (title) VALUES ('Армянск');
INSERT INTO regions (title) VALUES ('Армянськ');
INSERT INTO regions (title) VALUES ('Арсеньев');
INSERT INTO regions (title) VALUES ('Арск');
INSERT INTO regions (title) VALUES ('Артем');
INSERT INTO regions (title) VALUES ('Артемовск');
INSERT INTO regions (title) VALUES ('Артемовский');
INSERT INTO regions (title) VALUES ('Архангельск');
INSERT INTO regions (title) VALUES ('Асбест');
INSERT INTO regions (title) VALUES ('Асино');
INSERT INTO regions (title) VALUES ('Астрахань');
INSERT INTO regions (title) VALUES ('Аткарск');
INSERT INTO regions (title) VALUES ('Ахтубинск');
INSERT INTO regions (title) VALUES ('Ахтубинск-7');
INSERT INTO regions (title) VALUES ('Ачинск');
INSERT INTO regions (title) VALUES ('Аша');
INSERT INTO regions (title) VALUES ('Бабаево');
INSERT INTO regions (title) VALUES ('Бабушкин');
INSERT INTO regions (title) VALUES ('Бавлы');
INSERT INTO regions (title) VALUES ('Багратионовск');
INSERT INTO regions (title) VALUES ('Байкальск');
INSERT INTO regions (title) VALUES ('Баймак');
INSERT INTO regions (title) VALUES ('Бакал');
INSERT INTO regions (title) VALUES ('Баксан');
INSERT INTO regions (title) VALUES ('Балабаново');
INSERT INTO regions (title) VALUES ('Балаково');
INSERT INTO regions (title) VALUES ('Балахна');
INSERT INTO regions (title) VALUES ('Балашиха');
INSERT INTO regions (title) VALUES ('Балашов');
INSERT INTO regions (title) VALUES ('Балей');
INSERT INTO regions (title) VALUES ('Балтийск');
INSERT INTO regions (title) VALUES ('Барабинск');
INSERT INTO regions (title) VALUES ('Барнаул');
INSERT INTO regions (title) VALUES ('Барыш');
INSERT INTO regions (title) VALUES ('Батайск');
INSERT INTO regions (title) VALUES ('Бахчисарай');
INSERT INTO regions (title) VALUES ('Бежецк');
INSERT INTO regions (title) VALUES ('Белая Калитва');
INSERT INTO regions (title) VALUES ('Белая Холуница');
INSERT INTO regions (title) VALUES ('Белгород');
INSERT INTO regions (title) VALUES ('Белебей');
INSERT INTO regions (title) VALUES ('Белев');
INSERT INTO regions (title) VALUES ('Белинский');
INSERT INTO regions (title) VALUES ('Белово');
INSERT INTO regions (title) VALUES ('Белогорск');
INSERT INTO regions (title) VALUES ('Белогорск');
INSERT INTO regions (title) VALUES ('Белозерск');
INSERT INTO regions (title) VALUES ('Белокуриха');
INSERT INTO regions (title) VALUES ('Беломорск');
INSERT INTO regions (title) VALUES ('Белорецк');
INSERT INTO regions (title) VALUES ('Белореченск');
INSERT INTO regions (title) VALUES ('Белоусово');
INSERT INTO regions (title) VALUES ('Белоярский');
INSERT INTO regions (title) VALUES ('Белый');
INSERT INTO regions (title) VALUES ('Бердск');
INSERT INTO regions (title) VALUES ('Березники');
INSERT INTO regions (title) VALUES ('Березовский');
INSERT INTO regions (title) VALUES ('Березовский');
INSERT INTO regions (title) VALUES ('Беслан');
INSERT INTO regions (title) VALUES ('Бийск');
INSERT INTO regions (title) VALUES ('Бикин');
INSERT INTO regions (title) VALUES ('Билибино');
INSERT INTO regions (title) VALUES ('Биробиджан');
INSERT INTO regions (title) VALUES ('Бирск');
INSERT INTO regions (title) VALUES ('Бирюсинск');
INSERT INTO regions (title) VALUES ('Бирюч');
INSERT INTO regions (title) VALUES ('Благовещенск');
INSERT INTO regions (title) VALUES ('Благовещенск');
INSERT INTO regions (title) VALUES ('Благодарный');
INSERT INTO regions (title) VALUES ('Бобров');
INSERT INTO regions (title) VALUES ('Богданович');
INSERT INTO regions (title) VALUES ('Богородицк');
INSERT INTO regions (title) VALUES ('Богородск');
INSERT INTO regions (title) VALUES ('Боготол');
INSERT INTO regions (title) VALUES ('Богучар');
INSERT INTO regions (title) VALUES ('Бодайбо');
INSERT INTO regions (title) VALUES ('Бокситогорск');
INSERT INTO regions (title) VALUES ('Болгар');
INSERT INTO regions (title) VALUES ('Бологое');
INSERT INTO regions (title) VALUES ('Болотное');
INSERT INTO regions (title) VALUES ('Болохово');
INSERT INTO regions (title) VALUES ('Болхов');
INSERT INTO regions (title) VALUES ('Большой Камень');
INSERT INTO regions (title) VALUES ('Бор');
INSERT INTO regions (title) VALUES ('Борзя');
INSERT INTO regions (title) VALUES ('Борисоглебск');
INSERT INTO regions (title) VALUES ('Боровичи');
INSERT INTO regions (title) VALUES ('Боровск');
INSERT INTO regions (title) VALUES ('Боровск-1');
INSERT INTO regions (title) VALUES ('Бородино');
INSERT INTO regions (title) VALUES ('Братск');
INSERT INTO regions (title) VALUES ('Бронницы');
INSERT INTO regions (title) VALUES ('Брянск');
INSERT INTO regions (title) VALUES ('Бугульма');
INSERT INTO regions (title) VALUES ('Бугуруслан');
INSERT INTO regions (title) VALUES ('Буденновск');
INSERT INTO regions (title) VALUES ('Бузулук');
INSERT INTO regions (title) VALUES ('Буинск');
INSERT INTO regions (title) VALUES ('Буй');
INSERT INTO regions (title) VALUES ('Буйнакск');
INSERT INTO regions (title) VALUES ('Бутурлиновка');
INSERT INTO regions (title) VALUES ('Валдай');
INSERT INTO regions (title) VALUES ('Валуйки');
INSERT INTO regions (title) VALUES ('Велиж');
INSERT INTO regions (title) VALUES ('Великие Луки');
INSERT INTO regions (title) VALUES ('Великие Луки-1');
INSERT INTO regions (title) VALUES ('Великий Новгород');
INSERT INTO regions (title) VALUES ('Великий Устюг');
INSERT INTO regions (title) VALUES ('Вельск');
INSERT INTO regions (title) VALUES ('Венев');
INSERT INTO regions (title) VALUES ('Верещагино');
INSERT INTO regions (title) VALUES ('Верея');
INSERT INTO regions (title) VALUES ('Верхнеуральск');
INSERT INTO regions (title) VALUES ('Верхний Тагил');
INSERT INTO regions (title) VALUES ('Верхний Уфалей');
INSERT INTO regions (title) VALUES ('Верхняя Пышма');
INSERT INTO regions (title) VALUES ('Верхняя Салда');
INSERT INTO regions (title) VALUES ('Верхняя Тура');
INSERT INTO regions (title) VALUES ('Верхотурье');
INSERT INTO regions (title) VALUES ('Верхоянск');
INSERT INTO regions (title) VALUES ('Весьегонск');
INSERT INTO regions (title) VALUES ('Ветлуга');
INSERT INTO regions (title) VALUES ('Видное');
INSERT INTO regions (title) VALUES ('Вилюйск');
INSERT INTO regions (title) VALUES ('Вилючинск');
INSERT INTO regions (title) VALUES ('Вихоревка');
INSERT INTO regions (title) VALUES ('Вичуга');
INSERT INTO regions (title) VALUES ('Владивосток');
INSERT INTO regions (title) VALUES ('Владикавказ');
INSERT INTO regions (title) VALUES ('Владимир');
INSERT INTO regions (title) VALUES ('Волгоград');
INSERT INTO regions (title) VALUES ('Волгодонск');
INSERT INTO regions (title) VALUES ('Волгореченск');
INSERT INTO regions (title) VALUES ('Волжск');
INSERT INTO regions (title) VALUES ('Волжский');
INSERT INTO regions (title) VALUES ('Вологда');
INSERT INTO regions (title) VALUES ('Володарск');
INSERT INTO regions (title) VALUES ('Волоколамск');
INSERT INTO regions (title) VALUES ('Волосово');
INSERT INTO regions (title) VALUES ('Волхов');
INSERT INTO regions (title) VALUES ('Волчанск');
INSERT INTO regions (title) VALUES ('Вольск');
INSERT INTO regions (title) VALUES ('Вольск-18');
INSERT INTO regions (title) VALUES ('Воркута');
INSERT INTO regions (title) VALUES ('Воронеж');
INSERT INTO regions (title) VALUES ('Воронеж-45');
INSERT INTO regions (title) VALUES ('Ворсма');
INSERT INTO regions (title) VALUES ('Воскресенск');
INSERT INTO regions (title) VALUES ('Воткинск');
INSERT INTO regions (title) VALUES ('Всеволожск');
INSERT INTO regions (title) VALUES ('Вуктыл');
INSERT INTO regions (title) VALUES ('Выборг');
INSERT INTO regions (title) VALUES ('Выкса');
INSERT INTO regions (title) VALUES ('Высоковск');
INSERT INTO regions (title) VALUES ('Высоцк');
INSERT INTO regions (title) VALUES ('Вытегра');
INSERT INTO regions (title) VALUES ('Вышний Волочек');
INSERT INTO regions (title) VALUES ('Вяземский');
INSERT INTO regions (title) VALUES ('Вязники');
INSERT INTO regions (title) VALUES ('Вязьма');
INSERT INTO regions (title) VALUES ('Вятские Поляны');
INSERT INTO regions (title) VALUES ('Гаврилов Посад');
INSERT INTO regions (title) VALUES ('Гаврилов-Ям');
INSERT INTO regions (title) VALUES ('Гагарин');
INSERT INTO regions (title) VALUES ('Гаджиево');
INSERT INTO regions (title) VALUES ('Гай');
INSERT INTO regions (title) VALUES ('Галич');
INSERT INTO regions (title) VALUES ('Гатчина');
INSERT INTO regions (title) VALUES ('Гвардейск');
INSERT INTO regions (title) VALUES ('Гдов');
INSERT INTO regions (title) VALUES ('Геленджик');
INSERT INTO regions (title) VALUES ('Георгиевск');
INSERT INTO regions (title) VALUES ('Глазов');
INSERT INTO regions (title) VALUES ('Голицыно');
INSERT INTO regions (title) VALUES ('Горбатов');
INSERT INTO regions (title) VALUES ('Горно-Алтайск');
INSERT INTO regions (title) VALUES ('Горнозаводск');
INSERT INTO regions (title) VALUES ('Горняк');
INSERT INTO regions (title) VALUES ('Городец');
INSERT INTO regions (title) VALUES ('Городище');
INSERT INTO regions (title) VALUES ('Городовиковск');
INSERT INTO regions (title) VALUES ('Городской округ Черноголовка');
INSERT INTO regions (title) VALUES ('Гороховец');
INSERT INTO regions (title) VALUES ('Горячий Ключ');
INSERT INTO regions (title) VALUES ('Грайворон');
INSERT INTO regions (title) VALUES ('Гремячинск');
INSERT INTO regions (title) VALUES ('Грозный');
INSERT INTO regions (title) VALUES ('Грязи');
INSERT INTO regions (title) VALUES ('Грязовец');
INSERT INTO regions (title) VALUES ('Губаха');
INSERT INTO regions (title) VALUES ('Губкин');
INSERT INTO regions (title) VALUES ('Губкинский');
INSERT INTO regions (title) VALUES ('Гудермес');
INSERT INTO regions (title) VALUES ('Гуково');
INSERT INTO regions (title) VALUES ('Гулькевичи');
INSERT INTO regions (title) VALUES ('Гурьевск');
INSERT INTO regions (title) VALUES ('Гурьевск');
INSERT INTO regions (title) VALUES ('Гусев');
INSERT INTO regions (title) VALUES ('Гусиноозерск');
INSERT INTO regions (title) VALUES ('Гусь-Хрустальный');
INSERT INTO regions (title) VALUES ('Давлеканово');
INSERT INTO regions (title) VALUES ('Дагестанские Огни');
INSERT INTO regions (title) VALUES ('Далматово');
INSERT INTO regions (title) VALUES ('Дальнегорск');
INSERT INTO regions (title) VALUES ('Дальнереченск');
INSERT INTO regions (title) VALUES ('Данилов');
INSERT INTO regions (title) VALUES ('Данков');
INSERT INTO regions (title) VALUES ('Дегтярск');
INSERT INTO regions (title) VALUES ('Дедовск');
INSERT INTO regions (title) VALUES ('Демидов');
INSERT INTO regions (title) VALUES ('Дербент');
INSERT INTO regions (title) VALUES ('Десногорск');
INSERT INTO regions (title) VALUES ('Джанкой');
INSERT INTO regions (title) VALUES ('Джанкой');
INSERT INTO regions (title) VALUES ('Дзержинск');
INSERT INTO regions (title) VALUES ('Дзержинский');
INSERT INTO regions (title) VALUES ('Дивногорск');
INSERT INTO regions (title) VALUES ('Дигора');
INSERT INTO regions (title) VALUES ('Димитровград');
INSERT INTO regions (title) VALUES ('Дмитриев');
INSERT INTO regions (title) VALUES ('Дмитров');
INSERT INTO regions (title) VALUES ('Дмитровск');
INSERT INTO regions (title) VALUES ('Дно');
INSERT INTO regions (title) VALUES ('Добрянка');
INSERT INTO regions (title) VALUES ('Долгопрудный');
INSERT INTO regions (title) VALUES ('Долинск');
INSERT INTO regions (title) VALUES ('Домодедово');
INSERT INTO regions (title) VALUES ('Донецк');
INSERT INTO regions (title) VALUES ('Донской');
INSERT INTO regions (title) VALUES ('Дорогобуж');
INSERT INTO regions (title) VALUES ('Дрезна');
INSERT INTO regions (title) VALUES ('Дубна');
INSERT INTO regions (title) VALUES ('Дубовка');
INSERT INTO regions (title) VALUES ('Дудинка');
INSERT INTO regions (title) VALUES ('Духовщина');
INSERT INTO regions (title) VALUES ('Дюртюли');
INSERT INTO regions (title) VALUES ('Дятьково');
INSERT INTO regions (title) VALUES ('Евпатория');
INSERT INTO regions (title) VALUES ('Егорьевск');
INSERT INTO regions (title) VALUES ('Ейск');
INSERT INTO regions (title) VALUES ('Екатеринбург');
INSERT INTO regions (title) VALUES ('Елабуга');
INSERT INTO regions (title) VALUES ('Елец');
INSERT INTO regions (title) VALUES ('Елизово');
INSERT INTO regions (title) VALUES ('Ельня');
INSERT INTO regions (title) VALUES ('Еманжелинск');
INSERT INTO regions (title) VALUES ('Емва');
INSERT INTO regions (title) VALUES ('Енисейск');
INSERT INTO regions (title) VALUES ('Ермолино');
INSERT INTO regions (title) VALUES ('Ершов');
INSERT INTO regions (title) VALUES ('Ессентуки');
INSERT INTO regions (title) VALUES ('Ефремов');
INSERT INTO regions (title) VALUES ('Железноводск');
INSERT INTO regions (title) VALUES ('Железногорск');
INSERT INTO regions (title) VALUES ('Железногорск');
INSERT INTO regions (title) VALUES ('Железногорск-Илимский');
INSERT INTO regions (title) VALUES ('Железнодорожный');
INSERT INTO regions (title) VALUES ('Жердевка');
INSERT INTO regions (title) VALUES ('Жигулевск');
INSERT INTO regions (title) VALUES ('Жиздра');
INSERT INTO regions (title) VALUES ('Жирновск');
INSERT INTO regions (title) VALUES ('Жуков');
INSERT INTO regions (title) VALUES ('Жуковка');
INSERT INTO regions (title) VALUES ('Жуковский');
INSERT INTO regions (title) VALUES ('Завитинск');
INSERT INTO regions (title) VALUES ('Заводоуковск');
INSERT INTO regions (title) VALUES ('Заволжск');
INSERT INTO regions (title) VALUES ('Заволжье');
INSERT INTO regions (title) VALUES ('Задонск');
INSERT INTO regions (title) VALUES ('Заинск');
INSERT INTO regions (title) VALUES ('Закаменск');
INSERT INTO regions (title) VALUES ('Заозерный');
INSERT INTO regions (title) VALUES ('Заозерск');
INSERT INTO regions (title) VALUES ('Западная Двина');
INSERT INTO regions (title) VALUES ('Заполярный');
INSERT INTO regions (title) VALUES ('Зарайск');
INSERT INTO regions (title) VALUES ('Заречный');
INSERT INTO regions (title) VALUES ('Заречный');
INSERT INTO regions (title) VALUES ('Заринск');
INSERT INTO regions (title) VALUES ('Звенигово');
INSERT INTO regions (title) VALUES ('Звенигород');
INSERT INTO regions (title) VALUES ('Зверево');
INSERT INTO regions (title) VALUES ('Зеленогорск');
INSERT INTO regions (title) VALUES ('Зеленогорск');
INSERT INTO regions (title) VALUES ('Зеленоград');
INSERT INTO regions (title) VALUES ('Зеленоградск');
INSERT INTO regions (title) VALUES ('Зеленодольск');
INSERT INTO regions (title) VALUES ('Зеленокумск');
INSERT INTO regions (title) VALUES ('Зерноград');
INSERT INTO regions (title) VALUES ('Зея');
INSERT INTO regions (title) VALUES ('Зима');
INSERT INTO regions (title) VALUES ('Златоуст');
INSERT INTO regions (title) VALUES ('Злынка');
INSERT INTO regions (title) VALUES ('Змеиногорск');
INSERT INTO regions (title) VALUES ('Знаменск');
INSERT INTO regions (title) VALUES ('Зубцов');
INSERT INTO regions (title) VALUES ('Зуевка');
INSERT INTO regions (title) VALUES ('Ивангород');
INSERT INTO regions (title) VALUES ('Иваново');
INSERT INTO regions (title) VALUES ('Ивантеевка');
INSERT INTO regions (title) VALUES ('Ивдель');
INSERT INTO regions (title) VALUES ('Игарка');
INSERT INTO regions (title) VALUES ('Ижевск');
INSERT INTO regions (title) VALUES ('Избербаш');
INSERT INTO regions (title) VALUES ('Изобильный');
INSERT INTO regions (title) VALUES ('Иланский');
INSERT INTO regions (title) VALUES ('Инза');
INSERT INTO regions (title) VALUES ('Инкерман');
INSERT INTO regions (title) VALUES ('Инсар');
INSERT INTO regions (title) VALUES ('Инта');
INSERT INTO regions (title) VALUES ('Ипатово');
INSERT INTO regions (title) VALUES ('Ирбит');
INSERT INTO regions (title) VALUES ('Иркутск');
INSERT INTO regions (title) VALUES ('Иркутск-45');
INSERT INTO regions (title) VALUES ('Исилькуль');
INSERT INTO regions (title) VALUES ('Искитим');
INSERT INTO regions (title) VALUES ('Истра');
INSERT INTO regions (title) VALUES ('Истра-1');
INSERT INTO regions (title) VALUES ('Ишим');
INSERT INTO regions (title) VALUES ('Ишимбай');
INSERT INTO regions (title) VALUES ('Йошкар-Ола');
INSERT INTO regions (title) VALUES ('Кадников');
INSERT INTO regions (title) VALUES ('Казань');
INSERT INTO regions (title) VALUES ('Калач');
INSERT INTO regions (title) VALUES ('Калачинск');
INSERT INTO regions (title) VALUES ('Калач-на-Дону');
INSERT INTO regions (title) VALUES ('Калининград');
INSERT INTO regions (title) VALUES ('Калининск');
INSERT INTO regions (title) VALUES ('Калтан');
INSERT INTO regions (title) VALUES ('Калуга');
INSERT INTO regions (title) VALUES ('Калязин');
INSERT INTO regions (title) VALUES ('Камбарка');
INSERT INTO regions (title) VALUES ('Каменка');
INSERT INTO regions (title) VALUES ('Каменногорск');
INSERT INTO regions (title) VALUES ('Каменск-Уральский');
INSERT INTO regions (title) VALUES ('Каменск-Шахтинский');
INSERT INTO regions (title) VALUES ('Камень-на-Оби');
INSERT INTO regions (title) VALUES ('Камешково');
INSERT INTO regions (title) VALUES ('Камызяк');
INSERT INTO regions (title) VALUES ('Камышин');
INSERT INTO regions (title) VALUES ('Камышлов');
INSERT INTO regions (title) VALUES ('Канаш');
INSERT INTO regions (title) VALUES ('Кандалакша');
INSERT INTO regions (title) VALUES ('Канск');
INSERT INTO regions (title) VALUES ('Карабаново');
INSERT INTO regions (title) VALUES ('Карабаш');
INSERT INTO regions (title) VALUES ('Карабулак');
INSERT INTO regions (title) VALUES ('Карасук');
INSERT INTO regions (title) VALUES ('Карачаевск');
INSERT INTO regions (title) VALUES ('Карачев');
INSERT INTO regions (title) VALUES ('Каргат');
INSERT INTO regions (title) VALUES ('Каргополь');
INSERT INTO regions (title) VALUES ('Карпинск');
INSERT INTO regions (title) VALUES ('Карталы');
INSERT INTO regions (title) VALUES ('Касимов');
INSERT INTO regions (title) VALUES ('Касли');
INSERT INTO regions (title) VALUES ('Каспийск');
INSERT INTO regions (title) VALUES ('Катав-Ивановск');
INSERT INTO regions (title) VALUES ('Катайск');
INSERT INTO regions (title) VALUES ('Качканар');
INSERT INTO regions (title) VALUES ('Кашин');
INSERT INTO regions (title) VALUES ('Кашира');
INSERT INTO regions (title) VALUES ('Кашира-8');
INSERT INTO regions (title) VALUES ('Кедровый');
INSERT INTO regions (title) VALUES ('Кемерово');
INSERT INTO regions (title) VALUES ('Кемь');
INSERT INTO regions (title) VALUES ('Керчь');
INSERT INTO regions (title) VALUES ('Кизел');
INSERT INTO regions (title) VALUES ('Кизилюрт');
INSERT INTO regions (title) VALUES ('Кизляр');
INSERT INTO regions (title) VALUES ('Кимовск');
INSERT INTO regions (title) VALUES ('Кимры');
INSERT INTO regions (title) VALUES ('Кингисепп');
INSERT INTO regions (title) VALUES ('Кинель');
INSERT INTO regions (title) VALUES ('Кинешма');
INSERT INTO regions (title) VALUES ('Киреевск');
INSERT INTO regions (title) VALUES ('Киренск');
INSERT INTO regions (title) VALUES ('Киржач');
INSERT INTO regions (title) VALUES ('Кириллов');
INSERT INTO regions (title) VALUES ('Кириши');
INSERT INTO regions (title) VALUES ('Киров');
INSERT INTO regions (title) VALUES ('Киров');
INSERT INTO regions (title) VALUES ('Кировград');
INSERT INTO regions (title) VALUES ('Кирово-Чепецк');
INSERT INTO regions (title) VALUES ('Кировск');
INSERT INTO regions (title) VALUES ('Кировск');
INSERT INTO regions (title) VALUES ('Кирс');
INSERT INTO regions (title) VALUES ('Кирсанов');
INSERT INTO regions (title) VALUES ('Киселевск');
INSERT INTO regions (title) VALUES ('Кисловодск');
INSERT INTO regions (title) VALUES ('Климовск');
INSERT INTO regions (title) VALUES ('Клин');
INSERT INTO regions (title) VALUES ('Клинцы');
INSERT INTO regions (title) VALUES ('Княгинино');
INSERT INTO regions (title) VALUES ('Ковдор');
INSERT INTO regions (title) VALUES ('Ковров');
INSERT INTO regions (title) VALUES ('Ковылкино');
INSERT INTO regions (title) VALUES ('Когалым');
INSERT INTO regions (title) VALUES ('Кодинск');
INSERT INTO regions (title) VALUES ('Козельск');
INSERT INTO regions (title) VALUES ('Козловка');
INSERT INTO regions (title) VALUES ('Козьмодемьянск');
INSERT INTO regions (title) VALUES ('Кола');
INSERT INTO regions (title) VALUES ('Кологрив');
INSERT INTO regions (title) VALUES ('Коломна');
INSERT INTO regions (title) VALUES ('Колпашево');
INSERT INTO regions (title) VALUES ('Колпино');
INSERT INTO regions (title) VALUES ('Кольчугино');
INSERT INTO regions (title) VALUES ('Коммунар');
INSERT INTO regions (title) VALUES ('Комсомольск');
INSERT INTO regions (title) VALUES ('Комсомольск-на-Амуре');
INSERT INTO regions (title) VALUES ('Конаково');
INSERT INTO regions (title) VALUES ('Кондопога');
INSERT INTO regions (title) VALUES ('Кондрово');
INSERT INTO regions (title) VALUES ('Константиновск');
INSERT INTO regions (title) VALUES ('Копейск');
INSERT INTO regions (title) VALUES ('Кораблино');
INSERT INTO regions (title) VALUES ('Кореновск');
INSERT INTO regions (title) VALUES ('Коркино');
INSERT INTO regions (title) VALUES ('Королев');
INSERT INTO regions (title) VALUES ('Короча');
INSERT INTO regions (title) VALUES ('Корсаков');
INSERT INTO regions (title) VALUES ('Коряжма');
INSERT INTO regions (title) VALUES ('Костерево');
INSERT INTO regions (title) VALUES ('Костомукша');
INSERT INTO regions (title) VALUES ('Кострома');
INSERT INTO regions (title) VALUES ('Котельники');
INSERT INTO regions (title) VALUES ('Котельниково');
INSERT INTO regions (title) VALUES ('Котельнич');
INSERT INTO regions (title) VALUES ('Котлас');
INSERT INTO regions (title) VALUES ('Котово');
INSERT INTO regions (title) VALUES ('Котовск');
INSERT INTO regions (title) VALUES ('Кохма');
INSERT INTO regions (title) VALUES ('Красавино');
INSERT INTO regions (title) VALUES ('Красноармейск');
INSERT INTO regions (title) VALUES ('Красноармейск');
INSERT INTO regions (title) VALUES ('Красновишерск');
INSERT INTO regions (title) VALUES ('Красногорск');
INSERT INTO regions (title) VALUES ('Краснодар');
INSERT INTO regions (title) VALUES ('Красное Село');
INSERT INTO regions (title) VALUES ('Краснозаводск');
INSERT INTO regions (title) VALUES ('Краснознаменск');
INSERT INTO regions (title) VALUES ('Краснознаменск');
INSERT INTO regions (title) VALUES ('Краснокаменск');
INSERT INTO regions (title) VALUES ('Краснокамск');
INSERT INTO regions (title) VALUES ('Красноперекопск');
INSERT INTO regions (title) VALUES ('Красноперекопск');
INSERT INTO regions (title) VALUES ('Краснослободск');
INSERT INTO regions (title) VALUES ('Краснослободск');
INSERT INTO regions (title) VALUES ('Краснотурьинск');
INSERT INTO regions (title) VALUES ('Красноуральск');
INSERT INTO regions (title) VALUES ('Красноуфимск');
INSERT INTO regions (title) VALUES ('Красноярск');
INSERT INTO regions (title) VALUES ('Красный Кут');
INSERT INTO regions (title) VALUES ('Красный Сулин');
INSERT INTO regions (title) VALUES ('Красный Холм');
INSERT INTO regions (title) VALUES ('Кременки');
INSERT INTO regions (title) VALUES ('Кронштадт');
INSERT INTO regions (title) VALUES ('Кропоткин');
INSERT INTO regions (title) VALUES ('Крымск');
INSERT INTO regions (title) VALUES ('Кстово');
INSERT INTO regions (title) VALUES ('Кубинка');
INSERT INTO regions (title) VALUES ('Кувандык');
INSERT INTO regions (title) VALUES ('Кувшиново');
INSERT INTO regions (title) VALUES ('Кудымкар');
INSERT INTO regions (title) VALUES ('Кузнецк');
INSERT INTO regions (title) VALUES ('Кузнецк-12');
INSERT INTO regions (title) VALUES ('Кузнецк-8');
INSERT INTO regions (title) VALUES ('Куйбышев');
INSERT INTO regions (title) VALUES ('Кулебаки');
INSERT INTO regions (title) VALUES ('Кумертау');
INSERT INTO regions (title) VALUES ('Кунгур');
INSERT INTO regions (title) VALUES ('Купино');
INSERT INTO regions (title) VALUES ('Курган');
INSERT INTO regions (title) VALUES ('Курганинск');
INSERT INTO regions (title) VALUES ('Курильск');
INSERT INTO regions (title) VALUES ('Курлово');
INSERT INTO regions (title) VALUES ('Куровское');
INSERT INTO regions (title) VALUES ('Курск');
INSERT INTO regions (title) VALUES ('Куртамыш');
INSERT INTO regions (title) VALUES ('Курчатов');
INSERT INTO regions (title) VALUES ('Куса');
INSERT INTO regions (title) VALUES ('Кушва');
INSERT INTO regions (title) VALUES ('Кызыл');
INSERT INTO regions (title) VALUES ('Кыштым');
INSERT INTO regions (title) VALUES ('Кяхта');
INSERT INTO regions (title) VALUES ('Лабинск');
INSERT INTO regions (title) VALUES ('Лабытнанги');
INSERT INTO regions (title) VALUES ('Лагань');
INSERT INTO regions (title) VALUES ('Ладушкин');
INSERT INTO regions (title) VALUES ('Лаишево');
INSERT INTO regions (title) VALUES ('Лакинск');
INSERT INTO regions (title) VALUES ('Лангепас');
INSERT INTO regions (title) VALUES ('Лахденпохья');
INSERT INTO regions (title) VALUES ('Лебедянь');
INSERT INTO regions (title) VALUES ('Лениногорск');
INSERT INTO regions (title) VALUES ('Ленинск');
INSERT INTO regions (title) VALUES ('Ленинск-Кузнецкий');
INSERT INTO regions (title) VALUES ('Ленск');
INSERT INTO regions (title) VALUES ('Лермонтов');
INSERT INTO regions (title) VALUES ('Лесной');
INSERT INTO regions (title) VALUES ('Лесозаводск');
INSERT INTO regions (title) VALUES ('Лесосибирск');
INSERT INTO regions (title) VALUES ('Ливны');
INSERT INTO regions (title) VALUES ('Ликино-Дулево');
INSERT INTO regions (title) VALUES ('Липецк');
INSERT INTO regions (title) VALUES ('Липки');
INSERT INTO regions (title) VALUES ('Лиски');
INSERT INTO regions (title) VALUES ('Лихославль');
INSERT INTO regions (title) VALUES ('Лобня');
INSERT INTO regions (title) VALUES ('Лодейное Поле');
INSERT INTO regions (title) VALUES ('Ломоносов');
INSERT INTO regions (title) VALUES ('Лосино-Петровский');
INSERT INTO regions (title) VALUES ('Луга');
INSERT INTO regions (title) VALUES ('Луза');
INSERT INTO regions (title) VALUES ('Лукоянов');
INSERT INTO regions (title) VALUES ('Луховицы');
INSERT INTO regions (title) VALUES ('Лысково');
INSERT INTO regions (title) VALUES ('Лысьва');
INSERT INTO regions (title) VALUES ('Лыткарино');
INSERT INTO regions (title) VALUES ('Льгов');
INSERT INTO regions (title) VALUES ('Любань');
INSERT INTO regions (title) VALUES ('Люберцы');
INSERT INTO regions (title) VALUES ('Любим');
INSERT INTO regions (title) VALUES ('Людиново');
INSERT INTO regions (title) VALUES ('Лянтор');
INSERT INTO regions (title) VALUES ('Магадан');
INSERT INTO regions (title) VALUES ('Магас');
INSERT INTO regions (title) VALUES ('Магнитогорск');
INSERT INTO regions (title) VALUES ('Майкоп');
INSERT INTO regions (title) VALUES ('Майский');
INSERT INTO regions (title) VALUES ('Макаров');
INSERT INTO regions (title) VALUES ('Макарьев');
INSERT INTO regions (title) VALUES ('Макушино');
INSERT INTO regions (title) VALUES ('Малая Вишера');
INSERT INTO regions (title) VALUES ('Малгобек');
INSERT INTO regions (title) VALUES ('Малмыж');
INSERT INTO regions (title) VALUES ('Малоархангельск');
INSERT INTO regions (title) VALUES ('Малоярославец');
INSERT INTO regions (title) VALUES ('Мамадыш');
INSERT INTO regions (title) VALUES ('Мамоново');
INSERT INTO regions (title) VALUES ('Мантурово');
INSERT INTO regions (title) VALUES ('Мариинск');
INSERT INTO regions (title) VALUES ('Мариинский Посад');
INSERT INTO regions (title) VALUES ('Маркс');
INSERT INTO regions (title) VALUES ('Махачкала');
INSERT INTO regions (title) VALUES ('Мглин');
INSERT INTO regions (title) VALUES ('Мегион');
INSERT INTO regions (title) VALUES ('Медвежьегорск');
INSERT INTO regions (title) VALUES ('Медногорск');
INSERT INTO regions (title) VALUES ('Медынь');
INSERT INTO regions (title) VALUES ('Межгорье');
INSERT INTO regions (title) VALUES ('Междуреченск');
INSERT INTO regions (title) VALUES ('Мезень');
INSERT INTO regions (title) VALUES ('Меленки');
INSERT INTO regions (title) VALUES ('Мелеуз');
INSERT INTO regions (title) VALUES ('Менделеевск');
INSERT INTO regions (title) VALUES ('Мензелинск');
INSERT INTO regions (title) VALUES ('Мещовск');
INSERT INTO regions (title) VALUES ('Миасс');
INSERT INTO regions (title) VALUES ('Микунь');
INSERT INTO regions (title) VALUES ('Миллерово');
INSERT INTO regions (title) VALUES ('Минеральные Воды');
INSERT INTO regions (title) VALUES ('Минусинск');
INSERT INTO regions (title) VALUES ('Миньяр');
INSERT INTO regions (title) VALUES ('Мирный');
INSERT INTO regions (title) VALUES ('Мирный');
INSERT INTO regions (title) VALUES ('Михайлов');
INSERT INTO regions (title) VALUES ('Михайловка');
INSERT INTO regions (title) VALUES ('Михайловск');
INSERT INTO regions (title) VALUES ('Михайловск');
INSERT INTO regions (title) VALUES ('Мичуринск');
INSERT INTO regions (title) VALUES ('Могоча');
INSERT INTO regions (title) VALUES ('Можайск');
INSERT INTO regions (title) VALUES ('Можга');
INSERT INTO regions (title) VALUES ('Моздок');
INSERT INTO regions (title) VALUES ('Мончегорск');
INSERT INTO regions (title) VALUES ('Морозовск');
INSERT INTO regions (title) VALUES ('Моршанск');
INSERT INTO regions (title) VALUES ('Мосальск');
INSERT INTO regions (title) VALUES ('Москва');
INSERT INTO regions (title) VALUES ('Московский');
INSERT INTO regions (title) VALUES ('Московский');
INSERT INTO regions (title) VALUES ('Муравленко');
INSERT INTO regions (title) VALUES ('Мураши');
INSERT INTO regions (title) VALUES ('Мурманск');
INSERT INTO regions (title) VALUES ('Муром');
INSERT INTO regions (title) VALUES ('Мценск');
INSERT INTO regions (title) VALUES ('Мыски');
INSERT INTO regions (title) VALUES ('Мытищи');
INSERT INTO regions (title) VALUES ('Мышкин');
INSERT INTO regions (title) VALUES ('Набережные Челны');
INSERT INTO regions (title) VALUES ('Навашино');
INSERT INTO regions (title) VALUES ('Наволоки');
INSERT INTO regions (title) VALUES ('Надым');
INSERT INTO regions (title) VALUES ('Назарово');
INSERT INTO regions (title) VALUES ('Назрань');
INSERT INTO regions (title) VALUES ('Называевск');
INSERT INTO regions (title) VALUES ('Нальчик');
INSERT INTO regions (title) VALUES ('Нариманов');
INSERT INTO regions (title) VALUES ('Наро-Фоминск');
INSERT INTO regions (title) VALUES ('Нарткала');
INSERT INTO regions (title) VALUES ('Нарьян-Мар');
INSERT INTO regions (title) VALUES ('Находка');
INSERT INTO regions (title) VALUES ('Невель');
INSERT INTO regions (title) VALUES ('Невельск');
INSERT INTO regions (title) VALUES ('Невинномысск');
INSERT INTO regions (title) VALUES ('Невьянск');
INSERT INTO regions (title) VALUES ('Нелидово');
INSERT INTO regions (title) VALUES ('Неман');
INSERT INTO regions (title) VALUES ('Нерехта');
INSERT INTO regions (title) VALUES ('Нерчинск');
INSERT INTO regions (title) VALUES ('Нерюнгри');
INSERT INTO regions (title) VALUES ('Нестеров');
INSERT INTO regions (title) VALUES ('Нефтегорск');
INSERT INTO regions (title) VALUES ('Нефтекамск');
INSERT INTO regions (title) VALUES ('Нефтекумск');
INSERT INTO regions (title) VALUES ('Нефтеюганск');
INSERT INTO regions (title) VALUES ('Нея');
INSERT INTO regions (title) VALUES ('Нижневартовск');
INSERT INTO regions (title) VALUES ('Нижнекамск');
INSERT INTO regions (title) VALUES ('Нижнеудинск');
INSERT INTO regions (title) VALUES ('Нижние Серги');
INSERT INTO regions (title) VALUES ('Нижние Серги-3');
INSERT INTO regions (title) VALUES ('Нижний Ломов');
INSERT INTO regions (title) VALUES ('Нижний Новгород');
INSERT INTO regions (title) VALUES ('Нижний Тагил');
INSERT INTO regions (title) VALUES ('Нижняя Салда');
INSERT INTO regions (title) VALUES ('Нижняя Тура');
INSERT INTO regions (title) VALUES ('Николаевск');
INSERT INTO regions (title) VALUES ('Николаевск-на-Амуре');
INSERT INTO regions (title) VALUES ('Никольск');
INSERT INTO regions (title) VALUES ('Никольск');
INSERT INTO regions (title) VALUES ('Никольское');
INSERT INTO regions (title) VALUES ('Новая Ладога');
INSERT INTO regions (title) VALUES ('Новая Ляля');
INSERT INTO regions (title) VALUES ('Новоалександровск');
INSERT INTO regions (title) VALUES ('Новоалтайск');
INSERT INTO regions (title) VALUES ('Новоаннинский');
INSERT INTO regions (title) VALUES ('Нововоронеж');
INSERT INTO regions (title) VALUES ('Новодвинск');
INSERT INTO regions (title) VALUES ('Новозыбков');
INSERT INTO regions (title) VALUES ('Новокубанск');
INSERT INTO regions (title) VALUES ('Новокузнецк');
INSERT INTO regions (title) VALUES ('Новокуйбышевск');
INSERT INTO regions (title) VALUES ('Новомичуринск');
INSERT INTO regions (title) VALUES ('Новомосковск');
INSERT INTO regions (title) VALUES ('Новопавловск');
INSERT INTO regions (title) VALUES ('Новоржев');
INSERT INTO regions (title) VALUES ('Новороссийск');
INSERT INTO regions (title) VALUES ('Новосибирск');
INSERT INTO regions (title) VALUES ('Новосиль');
INSERT INTO regions (title) VALUES ('Новосокольники');
INSERT INTO regions (title) VALUES ('Новотроицк');
INSERT INTO regions (title) VALUES ('Новоузенск');
INSERT INTO regions (title) VALUES ('Новоульяновск');
INSERT INTO regions (title) VALUES ('Новоуральск');
INSERT INTO regions (title) VALUES ('Новохоперск');
INSERT INTO regions (title) VALUES ('Новочебоксарск');
INSERT INTO regions (title) VALUES ('Новочеркасск');
INSERT INTO regions (title) VALUES ('Новошахтинск');
INSERT INTO regions (title) VALUES ('Новый Оскол');
INSERT INTO regions (title) VALUES ('Новый Уренгой');
INSERT INTO regions (title) VALUES ('Ногинск');
INSERT INTO regions (title) VALUES ('Нолинск');
INSERT INTO regions (title) VALUES ('Норильск');
INSERT INTO regions (title) VALUES ('Ноябрьск');
INSERT INTO regions (title) VALUES ('Нурлат');
INSERT INTO regions (title) VALUES ('Нытва');
INSERT INTO regions (title) VALUES ('Нюрба');
INSERT INTO regions (title) VALUES ('Нягань');
INSERT INTO regions (title) VALUES ('Нязепетровск');
INSERT INTO regions (title) VALUES ('Няндома');
INSERT INTO regions (title) VALUES ('Облучье');
INSERT INTO regions (title) VALUES ('Обнинск');
INSERT INTO regions (title) VALUES ('Обоянь');
INSERT INTO regions (title) VALUES ('Обь');
INSERT INTO regions (title) VALUES ('Одинцово');
INSERT INTO regions (title) VALUES ('Ожерелье');
INSERT INTO regions (title) VALUES ('Озерск');
INSERT INTO regions (title) VALUES ('Озерск');
INSERT INTO regions (title) VALUES ('Озеры');
INSERT INTO regions (title) VALUES ('Октябрьск');
INSERT INTO regions (title) VALUES ('Октябрьский');
INSERT INTO regions (title) VALUES ('Окуловка');
INSERT INTO regions (title) VALUES ('Олекминск');
INSERT INTO regions (title) VALUES ('Оленегорск');
INSERT INTO regions (title) VALUES ('Оленегорск-1');
INSERT INTO regions (title) VALUES ('Оленегорск-2');
INSERT INTO regions (title) VALUES ('Оленегорск-4');
INSERT INTO regions (title) VALUES ('Олонец');
INSERT INTO regions (title) VALUES ('Омск');
INSERT INTO regions (title) VALUES ('Омутнинск');
INSERT INTO regions (title) VALUES ('Онега');
INSERT INTO regions (title) VALUES ('Опочка');
INSERT INTO regions (title) VALUES ('Орёл');
INSERT INTO regions (title) VALUES ('Оренбург');
INSERT INTO regions (title) VALUES ('Орехово-Зуево');
INSERT INTO regions (title) VALUES ('Орлов');
INSERT INTO regions (title) VALUES ('Орск');
INSERT INTO regions (title) VALUES ('Оса');
INSERT INTO regions (title) VALUES ('Осинники');
INSERT INTO regions (title) VALUES ('Осташков');
INSERT INTO regions (title) VALUES ('Остров');
INSERT INTO regions (title) VALUES ('Островной');
INSERT INTO regions (title) VALUES ('Острогожск');
INSERT INTO regions (title) VALUES ('Отрадное');
INSERT INTO regions (title) VALUES ('Отрадный');
INSERT INTO regions (title) VALUES ('Оха');
INSERT INTO regions (title) VALUES ('Оханск');
INSERT INTO regions (title) VALUES ('Очер');
INSERT INTO regions (title) VALUES ('Павлово');
INSERT INTO regions (title) VALUES ('Павловск');
INSERT INTO regions (title) VALUES ('Павловск');
INSERT INTO regions (title) VALUES ('Павловский Посад');
INSERT INTO regions (title) VALUES ('Палласовка');
INSERT INTO regions (title) VALUES ('Партизанск');
INSERT INTO regions (title) VALUES ('Певек');
INSERT INTO regions (title) VALUES ('Пенза');
INSERT INTO regions (title) VALUES ('Первомайск');
INSERT INTO regions (title) VALUES ('Первоуральск');
INSERT INTO regions (title) VALUES ('Перевоз');
INSERT INTO regions (title) VALUES ('Пересвет');
INSERT INTO regions (title) VALUES ('Переславль-Залесский');
INSERT INTO regions (title) VALUES ('Пермь');
INSERT INTO regions (title) VALUES ('Пестово');
INSERT INTO regions (title) VALUES ('Петергоф');
INSERT INTO regions (title) VALUES ('Петров Вал');
INSERT INTO regions (title) VALUES ('Петровск');
INSERT INTO regions (title) VALUES ('Петровск-Забайкальский');
INSERT INTO regions (title) VALUES ('Петрозаводск');
INSERT INTO regions (title) VALUES ('Петропавловск-Камчатский');
INSERT INTO regions (title) VALUES ('Петухово');
INSERT INTO regions (title) VALUES ('Петушки');
INSERT INTO regions (title) VALUES ('Печора');
INSERT INTO regions (title) VALUES ('Печоры');
INSERT INTO regions (title) VALUES ('Пикалево');
INSERT INTO regions (title) VALUES ('Пионерский');
INSERT INTO regions (title) VALUES ('Питкяранта');
INSERT INTO regions (title) VALUES ('Плавск');
INSERT INTO regions (title) VALUES ('Пласт');
INSERT INTO regions (title) VALUES ('Плес');
INSERT INTO regions (title) VALUES ('Поворино');
INSERT INTO regions (title) VALUES ('Подгорное');
INSERT INTO regions (title) VALUES ('Подольск');
INSERT INTO regions (title) VALUES ('Подпорожье');
INSERT INTO regions (title) VALUES ('Покачи');
INSERT INTO regions (title) VALUES ('Покров');
INSERT INTO regions (title) VALUES ('Покровск');
INSERT INTO regions (title) VALUES ('Полевской');
INSERT INTO regions (title) VALUES ('Полесск');
INSERT INTO regions (title) VALUES ('Полысаево');
INSERT INTO regions (title) VALUES ('Полярные Зори');
INSERT INTO regions (title) VALUES ('Полярный');
INSERT INTO regions (title) VALUES ('Поронайск');
INSERT INTO regions (title) VALUES ('Порхов');
INSERT INTO regions (title) VALUES ('Похвистнево');
INSERT INTO regions (title) VALUES ('Почеп');
INSERT INTO regions (title) VALUES ('Починок');
INSERT INTO regions (title) VALUES ('Пошехонье');
INSERT INTO regions (title) VALUES ('Правдинск');
INSERT INTO regions (title) VALUES ('Приволжск');
INSERT INTO regions (title) VALUES ('Приморск');
INSERT INTO regions (title) VALUES ('Приморск');
INSERT INTO regions (title) VALUES ('Приморско-Ахтарск');
INSERT INTO regions (title) VALUES ('Приозерск');
INSERT INTO regions (title) VALUES ('Прокопьевск');
INSERT INTO regions (title) VALUES ('Пролетарск');
INSERT INTO regions (title) VALUES ('Протвино');
INSERT INTO regions (title) VALUES ('Прохладный');
INSERT INTO regions (title) VALUES ('Псков');
INSERT INTO regions (title) VALUES ('Пугачев');
INSERT INTO regions (title) VALUES ('Пудож');
INSERT INTO regions (title) VALUES ('Пустошка');
INSERT INTO regions (title) VALUES ('Пучеж');
INSERT INTO regions (title) VALUES ('Пушкин');
INSERT INTO regions (title) VALUES ('Пушкино');
INSERT INTO regions (title) VALUES ('Пущино');
INSERT INTO regions (title) VALUES ('Пыталово');
INSERT INTO regions (title) VALUES ('Пыть-Ях');
INSERT INTO regions (title) VALUES ('Пятигорск');
INSERT INTO regions (title) VALUES ('Радужный');
INSERT INTO regions (title) VALUES ('Радужный');
INSERT INTO regions (title) VALUES ('Райчихинск');
INSERT INTO regions (title) VALUES ('Раменское');
INSERT INTO regions (title) VALUES ('Рассказово');
INSERT INTO regions (title) VALUES ('Ревда');
INSERT INTO regions (title) VALUES ('Реж');
INSERT INTO regions (title) VALUES ('Реутов');
INSERT INTO regions (title) VALUES ('Ржев');
INSERT INTO regions (title) VALUES ('Родники');
INSERT INTO regions (title) VALUES ('Рославль');
INSERT INTO regions (title) VALUES ('Россошь');
INSERT INTO regions (title) VALUES ('Ростов');
INSERT INTO regions (title) VALUES ('Ростов-на-Дону');
INSERT INTO regions (title) VALUES ('Рошаль');
INSERT INTO regions (title) VALUES ('Ртищево');
INSERT INTO regions (title) VALUES ('Рубцовск');
INSERT INTO regions (title) VALUES ('Рудня');
INSERT INTO regions (title) VALUES ('Руза');
INSERT INTO regions (title) VALUES ('Рузаевка');
INSERT INTO regions (title) VALUES ('Рыбинск');
INSERT INTO regions (title) VALUES ('Рыбное');
INSERT INTO regions (title) VALUES ('Рыльск');
INSERT INTO regions (title) VALUES ('Ряжск');
INSERT INTO regions (title) VALUES ('Рязань');
INSERT INTO regions (title) VALUES ('Саки');
INSERT INTO regions (title) VALUES ('Саки');
INSERT INTO regions (title) VALUES ('Салават');
INSERT INTO regions (title) VALUES ('Салаир');
INSERT INTO regions (title) VALUES ('Салехард');
INSERT INTO regions (title) VALUES ('Сальск');
INSERT INTO regions (title) VALUES ('Самара');
INSERT INTO regions (title) VALUES ('Санкт-Петербург');
INSERT INTO regions (title) VALUES ('Саранск');
INSERT INTO regions (title) VALUES ('Сарапул');
INSERT INTO regions (title) VALUES ('Саратов');
INSERT INTO regions (title) VALUES ('Саров');
INSERT INTO regions (title) VALUES ('Сасово');
INSERT INTO regions (title) VALUES ('Сатка');
INSERT INTO regions (title) VALUES ('Сафоново');
INSERT INTO regions (title) VALUES ('Саяногорск');
INSERT INTO regions (title) VALUES ('Саянск');
INSERT INTO regions (title) VALUES ('Светлогорск');
INSERT INTO regions (title) VALUES ('Светлоград');
INSERT INTO regions (title) VALUES ('Светлый');
INSERT INTO regions (title) VALUES ('Светогорск');
INSERT INTO regions (title) VALUES ('Свирск');
INSERT INTO regions (title) VALUES ('Свободный');
INSERT INTO regions (title) VALUES ('Себеж');
INSERT INTO regions (title) VALUES ('Севастополь');
INSERT INTO regions (title) VALUES ('Северобайкальск');
INSERT INTO regions (title) VALUES ('Северодвинск');
INSERT INTO regions (title) VALUES ('Северо-Курильск');
INSERT INTO regions (title) VALUES ('Североморск');
INSERT INTO regions (title) VALUES ('Североуральск');
INSERT INTO regions (title) VALUES ('Северск');
INSERT INTO regions (title) VALUES ('Севск');
INSERT INTO regions (title) VALUES ('Сегежа');
INSERT INTO regions (title) VALUES ('Сельцо');
INSERT INTO regions (title) VALUES ('Семенов');
INSERT INTO regions (title) VALUES ('Семикаракорск');
INSERT INTO regions (title) VALUES ('Семилуки');
INSERT INTO regions (title) VALUES ('Сенгилей');
INSERT INTO regions (title) VALUES ('Серафимович');
INSERT INTO regions (title) VALUES ('Сергач');
INSERT INTO regions (title) VALUES ('Сергиев Посад');
INSERT INTO regions (title) VALUES ('Сергиев Посад-7');
INSERT INTO regions (title) VALUES ('Сердобск');
INSERT INTO regions (title) VALUES ('Серов');
INSERT INTO regions (title) VALUES ('Серпухов');
INSERT INTO regions (title) VALUES ('Сертолово');
INSERT INTO regions (title) VALUES ('Сестрорецк');
INSERT INTO regions (title) VALUES ('Сибай');
INSERT INTO regions (title) VALUES ('Сим');
INSERT INTO regions (title) VALUES ('Симферополь');
INSERT INTO regions (title) VALUES ('Сковородино');
INSERT INTO regions (title) VALUES ('Скопин');
INSERT INTO regions (title) VALUES ('Славгород');
INSERT INTO regions (title) VALUES ('Славск');
INSERT INTO regions (title) VALUES ('Славянск-на-Кубани');
INSERT INTO regions (title) VALUES ('Сланцы');
INSERT INTO regions (title) VALUES ('Слободской');
INSERT INTO regions (title) VALUES ('Слюдянка');
INSERT INTO regions (title) VALUES ('Смоленск');
INSERT INTO regions (title) VALUES ('Снегири');
INSERT INTO regions (title) VALUES ('Снежинск');
INSERT INTO regions (title) VALUES ('Снежногорск');
INSERT INTO regions (title) VALUES ('Собинка');
INSERT INTO regions (title) VALUES ('Советск');
INSERT INTO regions (title) VALUES ('Советск');
INSERT INTO regions (title) VALUES ('Советск');
INSERT INTO regions (title) VALUES ('Советская Гавань');
INSERT INTO regions (title) VALUES ('Советский');
INSERT INTO regions (title) VALUES ('Сокол');
INSERT INTO regions (title) VALUES ('Солигалич');
INSERT INTO regions (title) VALUES ('Соликамск');
INSERT INTO regions (title) VALUES ('Солнечногорск');
INSERT INTO regions (title) VALUES ('Солнечногорск-2');
INSERT INTO regions (title) VALUES ('Солнечногорск-25');
INSERT INTO regions (title) VALUES ('Солнечногорск-30');
INSERT INTO regions (title) VALUES ('Солнечногорск-7');
INSERT INTO regions (title) VALUES ('Сольвычегодск');
INSERT INTO regions (title) VALUES ('Соль-Илецк');
INSERT INTO regions (title) VALUES ('Сольцы');
INSERT INTO regions (title) VALUES ('Сольцы 2');
INSERT INTO regions (title) VALUES ('Сорочинск');
INSERT INTO regions (title) VALUES ('Сорск');
INSERT INTO regions (title) VALUES ('Сортавала');
INSERT INTO regions (title) VALUES ('Сосенский');
INSERT INTO regions (title) VALUES ('Сосновка');
INSERT INTO regions (title) VALUES ('Сосновоборск');
INSERT INTO regions (title) VALUES ('Сосновый Бор');
INSERT INTO regions (title) VALUES ('Сосногорск');
INSERT INTO regions (title) VALUES ('Сочи');
INSERT INTO regions (title) VALUES ('Спас-Деменск');
INSERT INTO regions (title) VALUES ('Спас-Клепики');
INSERT INTO regions (title) VALUES ('Спасск');
INSERT INTO regions (title) VALUES ('Спасск-Дальний');
INSERT INTO regions (title) VALUES ('Спасск-Рязанский');
INSERT INTO regions (title) VALUES ('Среднеколымск');
INSERT INTO regions (title) VALUES ('Среднеуральск');
INSERT INTO regions (title) VALUES ('Сретенск');
INSERT INTO regions (title) VALUES ('Ставрополь');
INSERT INTO regions (title) VALUES ('Старая Купавна');
INSERT INTO regions (title) VALUES ('Старая Русса');
INSERT INTO regions (title) VALUES ('Старица');
INSERT INTO regions (title) VALUES ('Стародуб');
INSERT INTO regions (title) VALUES ('Старый крым');
INSERT INTO regions (title) VALUES ('Старый Оскол');
INSERT INTO regions (title) VALUES ('Стерлитамак');
INSERT INTO regions (title) VALUES ('Стрежевой');
INSERT INTO regions (title) VALUES ('Строитель');
INSERT INTO regions (title) VALUES ('Струнино');
INSERT INTO regions (title) VALUES ('Ступино');
INSERT INTO regions (title) VALUES ('Суворов');
INSERT INTO regions (title) VALUES ('Судак');
INSERT INTO regions (title) VALUES ('Суджа');
INSERT INTO regions (title) VALUES ('Судогда');
INSERT INTO regions (title) VALUES ('Суздаль');
INSERT INTO regions (title) VALUES ('Суоярви');
INSERT INTO regions (title) VALUES ('Сураж');
INSERT INTO regions (title) VALUES ('Сургут');
INSERT INTO regions (title) VALUES ('Суровикино');
INSERT INTO regions (title) VALUES ('Сурск');
INSERT INTO regions (title) VALUES ('Сусуман');
INSERT INTO regions (title) VALUES ('Сухиничи');
INSERT INTO regions (title) VALUES ('Сухой Лог');
INSERT INTO regions (title) VALUES ('Сызрань');
INSERT INTO regions (title) VALUES ('Сыктывкар');
INSERT INTO regions (title) VALUES ('Сысерть');
INSERT INTO regions (title) VALUES ('Сычевка');
INSERT INTO regions (title) VALUES ('Сясьстрой');
INSERT INTO regions (title) VALUES ('Тавда');
INSERT INTO regions (title) VALUES ('Таганрог');
INSERT INTO regions (title) VALUES ('Тайга');
INSERT INTO regions (title) VALUES ('Тайшет');
INSERT INTO regions (title) VALUES ('Талдом');
INSERT INTO regions (title) VALUES ('Талица');
INSERT INTO regions (title) VALUES ('Тамбов');
INSERT INTO regions (title) VALUES ('Тара');
INSERT INTO regions (title) VALUES ('Тарко-Сале');
INSERT INTO regions (title) VALUES ('Таруса');
INSERT INTO regions (title) VALUES ('Татарск');
INSERT INTO regions (title) VALUES ('Таштагол');
INSERT INTO regions (title) VALUES ('Тверь');
INSERT INTO regions (title) VALUES ('Теберда');
INSERT INTO regions (title) VALUES ('Тейково');
INSERT INTO regions (title) VALUES ('Темников');
INSERT INTO regions (title) VALUES ('Темрюк');
INSERT INTO regions (title) VALUES ('Терек');
INSERT INTO regions (title) VALUES ('Тетюши');
INSERT INTO regions (title) VALUES ('Тимашевск');
INSERT INTO regions (title) VALUES ('Тихвин');
INSERT INTO regions (title) VALUES ('Тихорецк');
INSERT INTO regions (title) VALUES ('Тобольск');
INSERT INTO regions (title) VALUES ('Тогучин');
INSERT INTO regions (title) VALUES ('Тольятти');
INSERT INTO regions (title) VALUES ('Томари');
INSERT INTO regions (title) VALUES ('Томмот');
INSERT INTO regions (title) VALUES ('Томск');
INSERT INTO regions (title) VALUES ('Топки');
INSERT INTO regions (title) VALUES ('Торжок');
INSERT INTO regions (title) VALUES ('Торопец');
INSERT INTO regions (title) VALUES ('Тосно');
INSERT INTO regions (title) VALUES ('Тотьма');
INSERT INTO regions (title) VALUES ('Трехгорный');
INSERT INTO regions (title) VALUES ('Трехгорный-1');
INSERT INTO regions (title) VALUES ('Троицк');
INSERT INTO regions (title) VALUES ('Троицк');
INSERT INTO regions (title) VALUES ('Трубчевск');
INSERT INTO regions (title) VALUES ('Туапсе');
INSERT INTO regions (title) VALUES ('Туймазы');
INSERT INTO regions (title) VALUES ('Тула');
INSERT INTO regions (title) VALUES ('Тулун');
INSERT INTO regions (title) VALUES ('Туран');
INSERT INTO regions (title) VALUES ('Туринск');
INSERT INTO regions (title) VALUES ('Тутаев');
INSERT INTO regions (title) VALUES ('Тында');
INSERT INTO regions (title) VALUES ('Тырныауз');
INSERT INTO regions (title) VALUES ('Тюкалинск');
INSERT INTO regions (title) VALUES ('Тюмень');
INSERT INTO regions (title) VALUES ('Уварово');
INSERT INTO regions (title) VALUES ('Углегорск');
INSERT INTO regions (title) VALUES ('Углич');
INSERT INTO regions (title) VALUES ('Удачный');
INSERT INTO regions (title) VALUES ('Удомля');
INSERT INTO regions (title) VALUES ('Ужур');
INSERT INTO regions (title) VALUES ('Узловая');
INSERT INTO regions (title) VALUES ('Улан-Удэ');
INSERT INTO regions (title) VALUES ('Ульяновск');
INSERT INTO regions (title) VALUES ('Унеча');
INSERT INTO regions (title) VALUES ('Урай');
INSERT INTO regions (title) VALUES ('Урень');
INSERT INTO regions (title) VALUES ('Уржум');
INSERT INTO regions (title) VALUES ('Урус-Мартан');
INSERT INTO regions (title) VALUES ('Урюпинск');
INSERT INTO regions (title) VALUES ('Усинск');
INSERT INTO regions (title) VALUES ('Усмань');
INSERT INTO regions (title) VALUES ('Усолье');
INSERT INTO regions (title) VALUES ('Усолье-Сибирское');
INSERT INTO regions (title) VALUES ('Уссурийск');
INSERT INTO regions (title) VALUES ('Усть-Джегута');
INSERT INTO regions (title) VALUES ('Усть-Илимск');
INSERT INTO regions (title) VALUES ('Усть-Катав');
INSERT INTO regions (title) VALUES ('Усть-Кут');
INSERT INTO regions (title) VALUES ('Усть-Лабинск');
INSERT INTO regions (title) VALUES ('Устюжна');
INSERT INTO regions (title) VALUES ('Уфа');
INSERT INTO regions (title) VALUES ('Ухта');
INSERT INTO regions (title) VALUES ('Учалы');
INSERT INTO regions (title) VALUES ('Уяр');
INSERT INTO regions (title) VALUES ('Фатеж');
INSERT INTO regions (title) VALUES ('Феодосия');
INSERT INTO regions (title) VALUES ('Фокино');
INSERT INTO regions (title) VALUES ('Фокино');
INSERT INTO regions (title) VALUES ('Фролово');
INSERT INTO regions (title) VALUES ('Фрязино');
INSERT INTO regions (title) VALUES ('Фурманов');
INSERT INTO regions (title) VALUES ('Хабаровск');
INSERT INTO regions (title) VALUES ('Хадыженск');
INSERT INTO regions (title) VALUES ('Ханты-Мансийск');
INSERT INTO regions (title) VALUES ('Харабали');
INSERT INTO regions (title) VALUES ('Харовск');
INSERT INTO regions (title) VALUES ('Хасавюрт');
INSERT INTO regions (title) VALUES ('Хвалынск');
INSERT INTO regions (title) VALUES ('Хилок');
INSERT INTO regions (title) VALUES ('Химки');
INSERT INTO regions (title) VALUES ('Холм');
INSERT INTO regions (title) VALUES ('Холмск');
INSERT INTO regions (title) VALUES ('Хотьково');
INSERT INTO regions (title) VALUES ('Цивильск');
INSERT INTO regions (title) VALUES ('Цимлянск');
INSERT INTO regions (title) VALUES ('Чадан');
INSERT INTO regions (title) VALUES ('Чайковский');
INSERT INTO regions (title) VALUES ('Чапаевск');
INSERT INTO regions (title) VALUES ('Чаплыгин');
INSERT INTO regions (title) VALUES ('Чебаркуль');
INSERT INTO regions (title) VALUES ('Чебоксары');
INSERT INTO regions (title) VALUES ('Чегем');
INSERT INTO regions (title) VALUES ('Чекалин');
INSERT INTO regions (title) VALUES ('Челябинск');
INSERT INTO regions (title) VALUES ('Чердынь');
INSERT INTO regions (title) VALUES ('Черемхово');
INSERT INTO regions (title) VALUES ('Черепаново');
INSERT INTO regions (title) VALUES ('Череповец');
INSERT INTO regions (title) VALUES ('Черкесск');
INSERT INTO regions (title) VALUES ('Чермоз');
INSERT INTO regions (title) VALUES ('Черноголовка');
INSERT INTO regions (title) VALUES ('Черногорск');
INSERT INTO regions (title) VALUES ('Чернушка');
INSERT INTO regions (title) VALUES ('Черняховск');
INSERT INTO regions (title) VALUES ('Чехов');
INSERT INTO regions (title) VALUES ('Чехов-2');
INSERT INTO regions (title) VALUES ('Чехов-3');
INSERT INTO regions (title) VALUES ('Чехов-8');
INSERT INTO regions (title) VALUES ('Чистополь');
INSERT INTO regions (title) VALUES ('Чита');
INSERT INTO regions (title) VALUES ('Чкаловск');
INSERT INTO regions (title) VALUES ('Чудово');
INSERT INTO regions (title) VALUES ('Чулым');
INSERT INTO regions (title) VALUES ('Чулым-3');
INSERT INTO regions (title) VALUES ('Чусовой');
INSERT INTO regions (title) VALUES ('Чухлома');
INSERT INTO regions (title) VALUES ('Шагонар');
INSERT INTO regions (title) VALUES ('Шадринск');
INSERT INTO regions (title) VALUES ('Шали');
INSERT INTO regions (title) VALUES ('Шарыпово');
INSERT INTO regions (title) VALUES ('Шарья');
INSERT INTO regions (title) VALUES ('Шатура');
INSERT INTO regions (title) VALUES ('Шахтерск');
INSERT INTO regions (title) VALUES ('Шахты');
INSERT INTO regions (title) VALUES ('Шахунья');
INSERT INTO regions (title) VALUES ('Шацк');
INSERT INTO regions (title) VALUES ('Шебекино');
INSERT INTO regions (title) VALUES ('Шелехов');
INSERT INTO regions (title) VALUES ('Шенкурск');
INSERT INTO regions (title) VALUES ('Шилка');
INSERT INTO regions (title) VALUES ('Шимановск');
INSERT INTO regions (title) VALUES ('Шиханы');
INSERT INTO regions (title) VALUES ('Шлиссельбург');
INSERT INTO regions (title) VALUES ('Шумерля');
INSERT INTO regions (title) VALUES ('Шумиха');
INSERT INTO regions (title) VALUES ('Шуя');
INSERT INTO regions (title) VALUES ('Щекино');
INSERT INTO regions (title) VALUES ('Щелкино');
INSERT INTO regions (title) VALUES ('Щелково');
INSERT INTO regions (title) VALUES ('Щербинка');
INSERT INTO regions (title) VALUES ('Щигры');
INSERT INTO regions (title) VALUES ('Щучье');
INSERT INTO regions (title) VALUES ('Электрогорск');
INSERT INTO regions (title) VALUES ('Электросталь');
INSERT INTO regions (title) VALUES ('Электроугли');
INSERT INTO regions (title) VALUES ('Элиста');
INSERT INTO regions (title) VALUES ('Энгельс');
INSERT INTO regions (title) VALUES ('Энгельс-19');
INSERT INTO regions (title) VALUES ('Энгельс-2');
INSERT INTO regions (title) VALUES ('Эртиль');
INSERT INTO regions (title) VALUES ('Юбилейный');
INSERT INTO regions (title) VALUES ('Югорск');
INSERT INTO regions (title) VALUES ('Южа');
INSERT INTO regions (title) VALUES ('Южно-Сахалинск');
INSERT INTO regions (title) VALUES ('Южно-Сухокумск');
INSERT INTO regions (title) VALUES ('Южноуральск');
INSERT INTO regions (title) VALUES ('Юрга');
INSERT INTO regions (title) VALUES ('Юрьевец');
INSERT INTO regions (title) VALUES ('Юрьев-Польский');
INSERT INTO regions (title) VALUES ('Юрюзань');
INSERT INTO regions (title) VALUES ('Юхнов');
INSERT INTO regions (title) VALUES ('Юхнов-1');
INSERT INTO regions (title) VALUES ('Юхнов-2');
INSERT INTO regions (title) VALUES ('Ядрин');
INSERT INTO regions (title) VALUES ('Якутск');
INSERT INTO regions (title) VALUES ('Ялта');
INSERT INTO regions (title) VALUES ('Ялуторовск');
INSERT INTO regions (title) VALUES ('Янаул');
INSERT INTO regions (title) VALUES ('Яранск');
INSERT INTO regions (title) VALUES ('Яровое');
INSERT INTO regions (title) VALUES ('Ярославль');
INSERT INTO regions (title) VALUES ('Ярцево');
INSERT INTO regions (title) VALUES ('Ясногорск');
INSERT INTO regions (title) VALUES ('Ясный');
INSERT INTO regions (title) VALUES ('Яхрома');


INSERT INTO categories (parent_id, title) VALUES (NULL, 'Организация мероприятий');
INSERT INTO categories (parent_id, title) VALUES (NULL, 'Рассылки клиентам');
INSERT INTO categories (parent_id, title) VALUES (NULL, 'Всё для конкурса');
INSERT INTO categories (parent_id, title) VALUES (NULL, 'Ко-брендинг (доп. каналы)');
INSERT INTO categories (parent_id, title) VALUES (NULL, 'Распространение продукции');
INSERT INTO categories (parent_id, title) VALUES (NULL, 'Партнерство в соцсетях');
INSERT INTO categories (parent_id, title) VALUES (NULL, 'BTL маркетинг');
INSERT INTO categories (parent_id, title) VALUES (NULL, 'Предложения для сотруников');

INSERT INTO categories (parent_id, title) VALUES (1, 'место');
INSERT INTO categories (parent_id, title) VALUES (1, 'алкоголь');
INSERT INTO categories (parent_id, title) VALUES (1, 'еда');
INSERT INTO categories (parent_id, title) VALUES (1, 'перфонманс/развлечения');
INSERT INTO categories (parent_id, title) VALUES (1, 'продвижение в соц сетях');
INSERT INTO categories (parent_id, title) VALUES (1, 'блоггеров/звезд и фото с ними в конкретном месте/с логотипами и т.д.');
INSERT INTO categories (parent_id, title) VALUES (1, 'продвижение на свою целевую аудиторию');
INSERT INTO categories (parent_id, title) VALUES (1, 'рассылка по базе партнеров');
INSERT INTO categories (parent_id, title) VALUES (1, 'брендирование');
INSERT INTO categories (parent_id, title) VALUES (1, 'украшение');
INSERT INTO categories (parent_id, title) VALUES (1, 'мастера / услуги на мероприятие (например, стилистов / визажистов)');
INSERT INTO categories (parent_id, title) VALUES (1, 'одежда для ведущих');
INSERT INTO categories (parent_id, title) VALUES (1, 'трансферы (такси / лимо)');
INSERT INTO categories (parent_id, title) VALUES (1, 'размещение для иногородних / иностранных гостей');
INSERT INTO categories (parent_id, title) VALUES (1, 'техническое сопровождение (свет / звук / декорации)');
INSERT INTO categories (parent_id, title) VALUES (1, 'услуги ведущего');
INSERT INTO categories (parent_id, title) VALUES (2, 'рассылка через email');
INSERT INTO categories (parent_id, title) VALUES (2, 'рассылка через СМС');
INSERT INTO categories (parent_id, title) VALUES (2, 'web push-рассылка');
INSERT INTO categories (parent_id, title) VALUES (2, 'mobile push-рассылка');
INSERT INTO categories (parent_id, title) VALUES (2, 'сообщения в личный кабинет');
INSERT INTO categories (parent_id, title) VALUES (2, 'сообщение(пуши) на сайте партнера');
INSERT INTO categories (parent_id, title) VALUES (3, 'бесплатные призы для конкурса/лотереи (от промо-кодов до автомобиля)');
INSERT INTO categories (parent_id, title) VALUES (3, 'анонсирование конкурса');
INSERT INTO categories (parent_id, title) VALUES (3, 'другое продвижение по каналам партнера');
INSERT INTO categories (parent_id, title) VALUES (4, 'размещение на сайтах партнеров (баннеры/спец проекты/спец размещения)');
INSERT INTO categories (parent_id, title) VALUES (4, 'брендирование продукции');
INSERT INTO categories (parent_id, title) VALUES (4, 'наружная реклама');
INSERT INTO categories (parent_id, title) VALUES (4, 'Indoor реклама');
INSERT INTO categories (parent_id, title) VALUES (4, 'реклама в прессе');
INSERT INTO categories (parent_id, title) VALUES (4, 'ТВ реклама');
INSERT INTO categories (parent_id, title) VALUES (4, 'реклама в кинотеатрах');
INSERT INTO categories (parent_id, title) VALUES (4, 'размещение рекламы на других нестандартных носителях (автомобили, офисы, пакеты и тд)');
INSERT INTO categories (parent_id, title) VALUES (5, 'образцы (сэмплы / миниатюры)  продукции для распространения');
INSERT INTO categories (parent_id, title) VALUES (5, 'полноразмерные продукты (подарки партнерам)');
INSERT INTO categories (parent_id, title) VALUES (5, 'продукция для тестирования');
INSERT INTO categories (parent_id, title) VALUES (5, 'промо-коды');
INSERT INTO categories (parent_id, title) VALUES (6, 'обмен постами (кросс-пост)');
INSERT INTO categories (parent_id, title) VALUES (6, 'посты за подарки');
INSERT INTO categories (parent_id, title) VALUES (6, 'личное сообщение участникам группы/сообщества');
INSERT INTO categories (parent_id, title) VALUES (7, 'раздача листовок в точках с промо-предложениями');
INSERT INTO categories (parent_id, title) VALUES (7, 'кросс-партнеры рядом');
INSERT INTO categories (parent_id, title) VALUES (8, 'скидки для сотрудников');
INSERT INTO categories (parent_id, title) VALUES (8, 'специальные предложения для конкурса среди сотрудников партнера');
INSERT INTO categories (parent_id, title) VALUES (8, 'сэмплинг в офис');


INSERT INTO marketing_channels (title) VALUES ('E-mail рассылки');
INSERT INTO marketing_channels (title) VALUES ('Наружная реклама');
INSERT INTO marketing_channels (title) VALUES ('Реклама на телевидении');
INSERT INTO marketing_channels (title) VALUES ('Реклама в журналах');
INSERT INTO marketing_channels (title) VALUES ('Реклама в газетах');
INSERT INTO marketing_channels (title) VALUES ('Instagram');
INSERT INTO marketing_channels (title) VALUES ('Telegram канал');
INSERT INTO marketing_channels (title) VALUES ('Ритейл сети');
INSERT INTO marketing_channels (title) VALUES ('Маленькие магазины у дома');
INSERT INTO marketing_channels (title) VALUES ('Помещение в собственности');



ALTER TABLE companies ALTER COLUMN "income_min" DROP NOT NULL;
ALTER TABLE companies ALTER COLUMN "income_max" DROP NOT NULL;


CREATE TABLE "companies_regions" (
  "company_region_id" serial NOT NULL,
  "company_id" bigint NOT NULL,
  "region_id" bigint NOT NULL,
  CONSTRAINT companies_regions_pk PRIMARY KEY ("company_region_id")
) WITH (
OIDS=FALSE
);
ALTER TABLE "companies_regions" ADD CONSTRAINT "companies_regions_fk0" FOREIGN KEY ("company_id") REFERENCES "companies"("company_id");
ALTER TABLE "companies_regions" ADD CONSTRAINT "companies_regions_fk1" FOREIGN KEY ("region_id") REFERENCES "regions"("region_id");

INSERT INTO companies_regions (company_id, region_id)
  SELECT company_id, region_id FROM companies WHERE region_id IS NOT NULL;

ALTER TABLE companies DROP COLUMN region_id;

INSERT INTO regions (region_id, title) VALUES (0, 'Россия')