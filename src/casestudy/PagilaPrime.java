/*
 * 
 */
package casestudy;

import org.schemaanalyst.data.TimestampValue;
import org.schemaanalyst.schema.*;
import org.schemaanalyst.schema.columntype.*;

/**
 *
 * @author chris
 */
public class PagilaPrime extends Schema {

	private static final long serialVersionUID = -604266531249201783L;

	@SuppressWarnings("unused")
	public PagilaPrime() {
        super("Pagila'");
        
        /*
            CREATE TABLE actor (
                actor_id integer DEFAULT nextval('actor_actor_id_seq'::regclass) NOT NULL,
                first_name character varying(45) NOT NULL,
                last_name character varying(45) NOT NULL,
                last_update timestamp without time zone DEFAULT now() NOT NULL
            );
        */
        
        Table actor = createTable("actor");
        
        Column actor_actor_id = actor.addColumn("actor_id", new IntColumnType());
        actor_actor_id.setNotNull();
        actor_actor_id.setPrimaryKey();
        
        Column actor_first_name = actor.addColumn("first_name", new VarCharColumnType(45));
        actor_first_name.setNotNull();
        
        Column actor_last_name = actor.addColumn("last_name", new VarCharColumnType(45));
        actor_last_name.setNotNull();
        
        Column actor_last_update = actor.addColumn("last_update", new TimestampColumnType());
        actor_last_update.setNotNull();
        
        /*
            CREATE TABLE category (
                category_id integer DEFAULT nextval('category_category_id_seq'::regclass) NOT NULL,
                name character varying(25) NOT NULL,
                last_update timestamp without time zone DEFAULT now() NOT NULL
            );
        */
        
        Table category = createTable("category");
        
        Column category_category_id = category.addColumn("category_id", new IntColumnType());
        category_category_id.setNotNull();
        category_category_id.setPrimaryKey();
        
        Column category_name = category.addColumn("name", new VarCharColumnType(25));
        category_name.setNotNull();
        
        Column category_last_update = category.addColumn("last_update", new TimestampColumnType());
        category_last_update.setNotNull();
        
        /*
            CREATE TABLE film (
                film_id integer DEFAULT nextval('film_film_id_seq'::regclass) NOT NULL,
                title character varying(255) NOT NULL,
                description text,
                release_year year,
                language_id smallint NOT NULL,
                original_language_id smallint,
                rental_duration smallint DEFAULT 3 NOT NULL,
                rental_rate numeric(4,2) DEFAULT 4.99 NOT NULL,
                length smallint,
                replacement_cost numeric(5,2) DEFAULT 19.99 NOT NULL,
                rating mpaa_rating DEFAULT 'G'::mpaa_rating,
                last_update timestamp without time zone DEFAULT now() NOT NULL,
                special_features text[],
                fulltext tsvector NOT NULL
            );
        */
        
        Table film = createTable("film");
        
        Column film_film_id = film.addColumn("film_id", new IntColumnType());
        film_film_id.setNotNull();
        film_film_id.setPrimaryKey();
        
        Column film_title = film.addColumn("title", new VarCharColumnType(255));
        film_title.setNotNull();
        
        Column film_description = film.addColumn("description", new TextColumnType());
        
        Column film_release_year = film.addColumn("release_year", new IntColumnType());
        film.addCheckConstraint(new RelationalCheckPredicate(film_release_year, ">=", 1901));
        film.addCheckConstraint(new RelationalCheckPredicate(film_release_year, "<=", 2155));
        
        Column film_language_id = film.addColumn("language_id", new SmallIntColumnType());
        film_language_id.setNotNull();
        
        Column film_original_language_id = film.addColumn("original_language_id", new SmallIntColumnType());
        
        Column film_rental_duration = film.addColumn("rental_duration", new SmallIntColumnType());
        film_rental_duration.setNotNull();
        
        Column film_rental_rate = film.addColumn("rental_rate", new NumericColumnType(4,2));
        film_rental_rate.setNotNull();
        
        Column film_length = film.addColumn("length", new SmallIntColumnType());
        
        Column film_replacement_cost = film.addColumn("replacement_cost", new NumericColumnType(5,2));
        film_replacement_cost.setNotNull();
        
        //TODO: check for better type match for SQL 'ENUM'
        Column film_rating = film.addColumn("rating", new CharColumnType(5));
        film.addCheckConstraint(new InCheckPredicate(film_rating, "G", "PG", "PG-13", "R", "NC-17"));
        
        Column film_last_update = film.addColumn("last_update", new TimestampColumnType());
        film_last_update.setNotNull();
        
        //TODO: check for better type match for SQL 'text[]'
        Column film_special_features = film.addColumn("special_features", new TextColumnType());
        
        //TODO: check for better type match for SQL 'tsvector'
        Column fulltext = film.addColumn("fulltext", new TextColumnType());
        
        /*
            CREATE TABLE film_actor (
                actor_id smallint NOT NULL,
                film_id smallint NOT NULL,
                last_update timestamp without time zone DEFAULT now() NOT NULL
            );
        */
        
        Table film_actor = createTable("film_actor");
        
        Column film_actor_actor_id = film_actor.addColumn("actor_id", new SmallIntColumnType());
        film_actor_actor_id.setNotNull();
        
        Column film_actor_film_id = film_actor.addColumn("film_id", new SmallIntColumnType());
        film_actor_film_id.setNotNull();
        
        Column film_actor_last_update = film_actor.addColumn("last_update", new TimestampColumnType());
        film_actor_last_update.setNotNull();
        
        film_actor.setPrimaryKeyConstraint(film_actor_actor_id, film_actor_film_id);
        
        /*
            CREATE TABLE film_category (
                film_id smallint NOT NULL,
                category_id smallint NOT NULL,
                last_update timestamp without time zone DEFAULT now() NOT NULL
            );
        */
        
        Table film_category = createTable("film_category");
        
        Column film_category_film_id = film_category.addColumn("film_id", new SmallIntColumnType());
        film_category_film_id.setNotNull();
        
        Column film_category_category_id = film_category.addColumn("category_id", new SmallIntColumnType());
        film_category_category_id.setNotNull();
        
        Column film_category_last_update = film_category.addColumn("last_update", new TimestampColumnType());
        film_category_last_update.setNotNull();
        
        film_category.setPrimaryKeyConstraint(film_category_film_id, film_category_category_id);
        
        /*
            CREATE TABLE address (
                address_id integer DEFAULT nextval('address_address_id_seq'::regclass) NOT NULL,
                address character varying(50) NOT NULL,
                address2 character varying(50),
                district character varying(20) NOT NULL,
                city_id smallint NOT NULL,
                postal_code character varying(10),
                phone character varying(20) NOT NULL,
                last_update timestamp without time zone DEFAULT now() NOT NULL
            );
        */
        
        Table address = createTable("address");
        
        Column address_address_id = address.addColumn("address_id", new IntColumnType());
        address_address_id.setNotNull();
        address_address_id.setPrimaryKey();
        
        Column address_address = address.addColumn("address", new VarCharColumnType(50));
        address_address.setNotNull();
        
        Column address_address2 = address.addColumn("address2", new VarCharColumnType(50));
        
        Column address_district = address.addColumn("district", new VarCharColumnType(20));
        address_district.setNotNull();
        
        Column address_city_id = address.addColumn("city_id", new SmallIntColumnType());
        address_city_id.setNotNull();
        
        Column address_postal_code = address.addColumn("postal_code", new VarCharColumnType(10));
        
        Column address_phone = address.addColumn("phone", new VarCharColumnType(20));
        address_phone.setNotNull();
        
        Column address_last_update = address.addColumn("last_update", new TimestampColumnType());
        address_last_update.setNotNull();
        
        /*
            CREATE TABLE city (
                city_id integer DEFAULT nextval('city_city_id_seq'::regclass) NOT NULL,
                city character varying(50) NOT NULL,
                country_id smallint NOT NULL,
                last_update timestamp without time zone DEFAULT now() NOT NULL
            );
        */
        
        Table city = createTable("city");
        
        Column city_city_id = city.addColumn("city_id", new IntColumnType());
        city_city_id.setNotNull();
        city_city_id.setPrimaryKey();
        
        Column city_city = city.addColumn("city", new VarCharColumnType(50));
        city_city.setNotNull();
        
        Column city_country_id = city.addColumn("country_id", new SmallIntColumnType());
        city_country_id.setNotNull();
        
        Column city_last_update = city.addColumn("last_update", new TimestampColumnType());
        city_last_update.setNotNull();
        
        /*
            CREATE TABLE country (
                country_id integer DEFAULT nextval('country_country_id_seq'::regclass) NOT NULL,
                country character varying(50) NOT NULL,
                last_update timestamp without time zone DEFAULT now() NOT NULL
            );
        */
        
        Table country = createTable("country");
        
        Column country_country_id = country.addColumn("country_id", new IntColumnType());
        country_country_id.setNotNull();
        country_country_id.setPrimaryKey();
        
        Column country_country = country.addColumn("country", new VarCharColumnType(50));
        country_country.setNotNull();
        
        Column country_last_update = country.addColumn("last_update", new TimestampColumnType());
        country_last_update.setNotNull();
        
        /*
            CREATE TABLE customer (
                customer_id integer DEFAULT nextval('customer_customer_id_seq'::regclass) NOT NULL,
                store_id smallint NOT NULL,
                first_name character varying(45) NOT NULL,
                last_name character varying(45) NOT NULL,
                email character varying(50),
                address_id smallint NOT NULL,
                activebool boolean DEFAULT true NOT NULL,
                create_date date DEFAULT ('now'::text)::date NOT NULL,
                last_update timestamp without time zone DEFAULT now(),
                active integer
            );
        */
        
        Table customer = createTable("customer");
        
        Column customer_customer_id = customer.addColumn("customer_id", new IntColumnType());
        customer_customer_id.setNotNull();
        customer_customer_id.setPrimaryKey();
        
        Column customer_store_id = customer.addColumn("store_id", new SmallIntColumnType());
        customer_store_id.setNotNull();
        
        Column customer_first_name = customer.addColumn("first_name", new VarCharColumnType(45));
        customer_first_name.setNotNull();
        
        Column customer_last_name = customer.addColumn("last_name", new VarCharColumnType(45));
        customer_last_name.setNotNull();
        
        Column customer_email = customer.addColumn("email", new VarCharColumnType(50));
        
        Column customer_address_id = customer.addColumn("address_id", new SmallIntColumnType());
        customer_address_id.setNotNull();
        
        Column customer_activebool = customer.addColumn("activebool", new org.schemaanalyst.schema.columntype.BooleanColumnType());
        customer_activebool.setNotNull();
        
        Column customer_create_date = customer.addColumn("create_date", new DateColumnType());
        customer_create_date.setNotNull();
        
        Column customer_last_update = customer.addColumn("last_update", new TimestampColumnType());
        customer_last_update.setNotNull();
        
        Column customer_active = customer.addColumn("active", new IntColumnType());
        
        /*
            CREATE TABLE inventory (
                inventory_id integer DEFAULT nextval('inventory_inventory_id_seq'::regclass) NOT NULL,
                film_id smallint NOT NULL,
                store_id smallint NOT NULL,
                last_update timestamp without time zone DEFAULT now() NOT NULL
            );
        */
        
        Table inventory = createTable("inventory");
        
        Column inventory_inventory_id = inventory.addColumn("inventory_id", new IntColumnType());
        inventory_inventory_id.setNotNull();
        inventory_inventory_id.setPrimaryKey();
        
        Column inventory_film_id = inventory.addColumn("film_id", new SmallIntColumnType());
        inventory_film_id.setNotNull();
        
        Column inventory_store_id = inventory.addColumn("store_id", new SmallIntColumnType());
        inventory_store_id.setNotNull();
        
        Column inventory_last_update = inventory.addColumn("last_update", new TimestampColumnType());
        inventory_last_update.setNotNull();
        
        /*
            CREATE TABLE language (
                language_id integer DEFAULT nextval('language_language_id_seq'::regclass) NOT NULL,
                name character(20) NOT NULL,
                last_update timestamp without time zone DEFAULT now() NOT NULL
            );
        */
        
        Table language = createTable("language");
        
        Column language_language_id = language.addColumn("language_id", new IntColumnType());
        language_language_id.setNotNull();
        language_language_id.setPrimaryKey();
        
        Column language_name = language.addColumn("name", new CharColumnType(20));
        language_name.setNotNull();
        
        Column language_last_update = language.addColumn("last_update", new TimestampColumnType());
        language_last_update.setNotNull();
        
        /*
            CREATE TABLE payment (
                payment_id integer DEFAULT nextval('payment_payment_id_seq'::regclass) NOT NULL,
                customer_id smallint NOT NULL,
                staff_id smallint NOT NULL,
                rental_id integer NOT NULL,
                amount numeric(5,2) NOT NULL,
                payment_date timestamp without time zone NOT NULL
            );
        */
        
        Table payment = createTable("payment");
        
        setupPayment(payment);
        
        /*
            CREATE TABLE payment_p2007_01 (CONSTRAINT payment_p2007_01_payment_date_check CHECK (((payment_date >= '2007-01-01 00:00:00'::timestamp without time zone) AND (payment_date < '2007-02-01 00:00:00'::timestamp without time zone)))
            )
            INHERITS (payment);
        */
        
        Table payment_p2007_01 = createTable("payment_p2007_01");
        
        setupPayment(payment_p2007_01);
        
        Column payment_p2007_01_payment_date = payment_p2007_01.getColumn("payment_date");
        payment_p2007_01.addCheckConstraint(new RelationalCheckPredicate(payment_p2007_01_payment_date, ">=", new TimestampValue(2007, 01, 01, 0, 0, 0)));
        payment_p2007_01.addCheckConstraint(new RelationalCheckPredicate(payment_p2007_01_payment_date, "<", new TimestampValue(2007, 02, 01, 0, 0, 0)));

        
        /*
            CREATE TABLE payment_p2007_02 (CONSTRAINT payment_p2007_02_payment_date_check CHECK (((payment_date >= '2007-02-01 00:00:00'::timestamp without time zone) AND (payment_date < '2007-03-01 00:00:00'::timestamp without time zone)))
            )
            INHERITS (payment);
        */
        
        Table payment_p2007_02 = createTable("payment_p2007_02");
        
        setupPayment(payment_p2007_02);
        
        Column payment_p2007_02_payment_date = payment_p2007_02.getColumn("payment_date");
        payment_p2007_02.addCheckConstraint(new RelationalCheckPredicate(payment_p2007_02_payment_date, ">=", new TimestampValue(2007, 02, 01, 0, 0, 0)));
        payment_p2007_02.addCheckConstraint(new RelationalCheckPredicate(payment_p2007_02_payment_date, "<", new TimestampValue(2007, 03, 01, 0, 0, 0)));
        
        /*
            CREATE TABLE payment_p2007_03 (CONSTRAINT payment_p2007_03_payment_date_check CHECK (((payment_date >= '2007-03-01 00:00:00'::timestamp without time zone) AND (payment_date < '2007-04-01 00:00:00'::timestamp without time zone)))
            )
            INHERITS (payment);
        */
        
        Table payment_p2007_03 = createTable("payment_p2007_03");
        
        setupPayment(payment_p2007_03);
        
        Column payment_p2007_03_payment_date = payment_p2007_03.getColumn("payment_date");
        payment_p2007_03.addCheckConstraint(new RelationalCheckPredicate(payment_p2007_03_payment_date, ">=", new TimestampValue(2007, 03, 01, 0, 0, 0)));
        payment_p2007_03.addCheckConstraint(new RelationalCheckPredicate(payment_p2007_03_payment_date, "<", new TimestampValue(2007, 04, 01, 0, 0, 0)));
        
        /*
            CREATE TABLE payment_p2007_04 (CONSTRAINT payment_p2007_04_payment_date_check CHECK (((payment_date >= '2007-04-01 00:00:00'::timestamp without time zone) AND (payment_date < '2007-05-01 00:00:00'::timestamp without time zone)))
            )
            INHERITS (payment);
        */
        
        Table payment_p2007_04 = createTable("payment_p2007_04");
        
        setupPayment(payment_p2007_04);
        
        Column payment_p2007_04_payment_date = payment_p2007_04.getColumn("payment_date");
        payment_p2007_04.addCheckConstraint(new RelationalCheckPredicate(payment_p2007_04_payment_date, ">=", new TimestampValue(2007, 04, 01, 0, 0, 0)));
        payment_p2007_04.addCheckConstraint(new RelationalCheckPredicate(payment_p2007_04_payment_date, "<", new TimestampValue(2007, 05, 01, 0, 0, 0)));
        
        /*
            CREATE TABLE payment_p2007_05 (CONSTRAINT payment_p2007_05_payment_date_check CHECK (((payment_date >= '2007-05-01 00:00:00'::timestamp without time zone) AND (payment_date < '2007-06-01 00:00:00'::timestamp without time zone)))
            )
            INHERITS (payment);
        */
        
        Table payment_p2007_05 = createTable("payment_p2007_05");
        
        setupPayment(payment_p2007_05);
        
        Column payment_p2007_05_payment_date = payment_p2007_05.getColumn("payment_date");
        payment_p2007_05.addCheckConstraint(new RelationalCheckPredicate(payment_p2007_05_payment_date, ">=", new TimestampValue(2007, 05, 01, 0, 0, 0)));
        payment_p2007_05.addCheckConstraint(new RelationalCheckPredicate(payment_p2007_05_payment_date, "<", new TimestampValue(2007, 06, 01, 0, 0, 0)));
        
        /*
            CREATE TABLE payment_p2007_06 (CONSTRAINT payment_p2007_06_payment_date_check CHECK (((payment_date >= '2007-06-01 00:00:00'::timestamp without time zone) AND (payment_date < '2007-07-01 00:00:00'::timestamp without time zone)))
            )
            INHERITS (payment);
        */
        
        Table payment_p2007_06 = createTable("payment_p2007_06");
        
        setupPayment(payment_p2007_06);
        
        Column payment_p2007_06_payment_date = payment_p2007_06.getColumn("payment_date");
        payment_p2007_06.addCheckConstraint(new RelationalCheckPredicate(payment_p2007_06_payment_date, ">=", new TimestampValue(2007, 06, 01, 0, 0, 0)));
        payment_p2007_06.addCheckConstraint(new RelationalCheckPredicate(payment_p2007_06_payment_date, "<", new TimestampValue(2007, 07, 01, 0, 0, 0)));
        
        /*
            CREATE TABLE rental (
                rental_id integer DEFAULT nextval('rental_rental_id_seq'::regclass) NOT NULL,
                rental_date timestamp without time zone NOT NULL,
                inventory_id integer NOT NULL,
                customer_id smallint NOT NULL,
                return_date timestamp without time zone,
                staff_id smallint NOT NULL,
                last_update timestamp without time zone DEFAULT now() NOT NULL
            );
        */
        
        Table rental = createTable("rental");
        
        Column rental_rental_id = rental.addColumn("rental_id", new IntColumnType());
        rental_rental_id.setNotNull();
        rental_rental_id.setPrimaryKey();
        
        Column rental_rental_date = rental.addColumn("rental_date", new TimestampColumnType());
        rental_rental_date.setNotNull();
        
        Column rental_inventory_id = rental.addColumn("inventory_id", new IntColumnType());
        rental_inventory_id.setNotNull();
        
        Column rental_customer_id = rental.addColumn("customer_id", new SmallIntColumnType());
        rental_customer_id.setNotNull();
        
        Column rental_return_date = rental.addColumn("return_date", new TimestampColumnType());
        rental_return_date.setNotNull();
        
        Column rental_staff_id = rental.addColumn("staff_id", new SmallIntColumnType());
        rental_staff_id.setNotNull();
        
        Column rental_last_update = rental.addColumn("last_update", new TimestampColumnType());
        rental_last_update.setNotNull();
        
        /*
            CREATE TABLE staff (
                staff_id integer DEFAULT nextval('staff_staff_id_seq'::regclass) NOT NULL,
                first_name character varying(45) NOT NULL,
                last_name character varying(45) NOT NULL,
                address_id smallint NOT NULL,
                email character varying(50),
                store_id smallint NOT NULL,
                active boolean DEFAULT true NOT NULL,
                username character varying(16) NOT NULL,
                password character varying(40),
                last_update timestamp without time zone DEFAULT now() NOT NULL,
                picture bytea
            );
        */
        
        Table staff = createTable("staff");
        
        Column staff_staff_id = staff.addColumn("staff_id", new IntColumnType());
        staff_staff_id.setNotNull();
        staff_staff_id.setPrimaryKey();
        
        Column staff_first_name = staff.addColumn("first_name", new VarCharColumnType(45));
        staff_first_name.setNotNull();
        
        Column staff_last_name = staff.addColumn("last_name", new VarCharColumnType(45));
        staff_last_name.setNotNull();
        
        Column staff_address_id = staff.addColumn("address_id", new SmallIntColumnType());
        staff_address_id.setNotNull();
        
        Column staff_email = staff.addColumn("email", new VarCharColumnType(50));
        
        Column staff_store_id = staff.addColumn("store_id", new SmallIntColumnType());
        staff_store_id.setNotNull();
        
        Column staff_active = staff.addColumn("active", new org.schemaanalyst.schema.columntype.BooleanColumnType());
        staff_active.setNotNull();
        
        Column staff_username = staff.addColumn("username", new VarCharColumnType(16));
        staff_username.setNotNull();
        
        Column staff_password = staff.addColumn("password", new VarCharColumnType(40));
        
        Column staff_last_update = staff.addColumn("last_update", new TimestampColumnType());
        staff_last_update.setNotNull();
        
        //Used 'int' in place of 'bytea'
        Column staff_picture = staff.addColumn("picture", new IntColumnType());
        
        /*
            CREATE TABLE store (
                store_id integer DEFAULT nextval('store_store_id_seq'::regclass) NOT NULL,
                address_id smallint NOT NULL,
                last_update timestamp without time zone DEFAULT now() NOT NULL
            );
        */
        
        Table store = createTable("store");
        
        Column store_store_id = store.addColumn("store_id", new IntColumnType());
        store_store_id.setNotNull();
        store_store_id.setPrimaryKey();
        
        Column store_address_id = store.addColumn("address_id", new SmallIntColumnType());
        store_address_id.setNotNull();
        
        Column store_last_update = store.addColumn("last_update", new TimestampColumnType());
        store_last_update.setNotNull();
        
        /*
            CREATE TABLE manager (
                staff_id integer NOT NULL,
                store_id integer NOT NULL,
                PRIMARY KEY (staff_id, store_id),
                FOREIGN KEY (staff_id) REFERENCES staff(staff_id),
                FOREIGN KEY (store_id) REFERENCES store(store_id),
            );        
        */
        
        Table manager = createTable("manager");
        
        Column manager_staff_id = manager.addColumn("staff_id", new IntegerColumnType());
        manager_staff_id.setNotNull();
        
        Column manager_store_id = manager.addColumn("store_id", new IntegerColumnType());
        manager_store_id.setNotNull();
        
        manager.setPrimaryKeyConstraint(manager_staff_id, manager_store_id);
        
        manager.addForeignKeyConstraint(staff, manager_staff_id, staff_staff_id);
        manager.addForeignKeyConstraint(store, manager_store_id, store_store_id);
        
        /*
            Foreign key statements:
            
            ALTER TABLE ONLY address
                ADD CONSTRAINT address_city_id_fkey FOREIGN KEY (city_id) REFERENCES city(city_id) ON UPDATE CASCADE ON DELETE RESTRICT;

            ALTER TABLE ONLY city
                ADD CONSTRAINT city_country_id_fkey FOREIGN KEY (country_id) REFERENCES country(country_id) ON UPDATE CASCADE ON DELETE RESTRICT;

            ALTER TABLE ONLY customer
                ADD CONSTRAINT customer_address_id_fkey FOREIGN KEY (address_id) REFERENCES address(address_id) ON UPDATE CASCADE ON DELETE RESTRICT;

            ALTER TABLE ONLY customer
                ADD CONSTRAINT customer_store_id_fkey FOREIGN KEY (store_id) REFERENCES store(store_id) ON UPDATE CASCADE ON DELETE RESTRICT;

            ALTER TABLE ONLY film_actor
                ADD CONSTRAINT film_actor_actor_id_fkey FOREIGN KEY (actor_id) REFERENCES actor(actor_id) ON UPDATE CASCADE ON DELETE RESTRICT;

            ALTER TABLE ONLY film_actor
                ADD CONSTRAINT film_actor_film_id_fkey FOREIGN KEY (film_id) REFERENCES film(film_id) ON UPDATE CASCADE ON DELETE RESTRICT;

            ALTER TABLE ONLY film_category
                ADD CONSTRAINT film_category_category_id_fkey FOREIGN KEY (category_id) REFERENCES category(category_id) ON UPDATE CASCADE ON DELETE RESTRICT;

            ALTER TABLE ONLY film_category
                ADD CONSTRAINT film_category_film_id_fkey FOREIGN KEY (film_id) REFERENCES film(film_id) ON UPDATE CASCADE ON DELETE RESTRICT;

            ALTER TABLE ONLY film
                ADD CONSTRAINT film_language_id_fkey FOREIGN KEY (language_id) REFERENCES language(language_id) ON UPDATE CASCADE ON DELETE RESTRICT;

            ALTER TABLE ONLY film
                ADD CONSTRAINT film_original_language_id_fkey FOREIGN KEY (original_language_id) REFERENCES language(language_id) ON UPDATE CASCADE ON DELETE RESTRICT;

            ALTER TABLE ONLY inventory
                ADD CONSTRAINT inventory_film_id_fkey FOREIGN KEY (film_id) REFERENCES film(film_id) ON UPDATE CASCADE ON DELETE RESTRICT;

            ALTER TABLE ONLY inventory
                ADD CONSTRAINT inventory_store_id_fkey FOREIGN KEY (store_id) REFERENCES store(store_id) ON UPDATE CASCADE ON DELETE RESTRICT;

            ALTER TABLE ONLY payment
                ADD CONSTRAINT payment_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON UPDATE CASCADE ON DELETE RESTRICT;

            ALTER TABLE ONLY payment_p2007_01
                ADD CONSTRAINT payment_p2007_01_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES customer(customer_id);

            ALTER TABLE ONLY payment_p2007_01
                ADD CONSTRAINT payment_p2007_01_rental_id_fkey FOREIGN KEY (rental_id) REFERENCES rental(rental_id);

            ALTER TABLE ONLY payment_p2007_01
                ADD CONSTRAINT payment_p2007_01_staff_id_fkey FOREIGN KEY (staff_id) REFERENCES staff(staff_id);

            ALTER TABLE ONLY payment_p2007_02
                ADD CONSTRAINT payment_p2007_02_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES customer(customer_id);

            ALTER TABLE ONLY payment_p2007_02
                ADD CONSTRAINT payment_p2007_02_rental_id_fkey FOREIGN KEY (rental_id) REFERENCES rental(rental_id);

            ALTER TABLE ONLY payment_p2007_02
                ADD CONSTRAINT payment_p2007_02_staff_id_fkey FOREIGN KEY (staff_id) REFERENCES staff(staff_id);

            ALTER TABLE ONLY payment_p2007_03
                ADD CONSTRAINT payment_p2007_03_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES customer(customer_id);

            ALTER TABLE ONLY payment_p2007_03
                ADD CONSTRAINT payment_p2007_03_rental_id_fkey FOREIGN KEY (rental_id) REFERENCES rental(rental_id);

            ALTER TABLE ONLY payment_p2007_03
                ADD CONSTRAINT payment_p2007_03_staff_id_fkey FOREIGN KEY (staff_id) REFERENCES staff(staff_id);

            ALTER TABLE ONLY payment_p2007_04
                ADD CONSTRAINT payment_p2007_04_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES customer(customer_id);

            ALTER TABLE ONLY payment_p2007_04
                ADD CONSTRAINT payment_p2007_04_rental_id_fkey FOREIGN KEY (rental_id) REFERENCES rental(rental_id);

            ALTER TABLE ONLY payment_p2007_04
                ADD CONSTRAINT payment_p2007_04_staff_id_fkey FOREIGN KEY (staff_id) REFERENCES staff(staff_id);

            ALTER TABLE ONLY payment_p2007_05
                ADD CONSTRAINT payment_p2007_05_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES customer(customer_id);

            ALTER TABLE ONLY payment_p2007_05
                ADD CONSTRAINT payment_p2007_05_rental_id_fkey FOREIGN KEY (rental_id) REFERENCES rental(rental_id);

            ALTER TABLE ONLY payment_p2007_05
                ADD CONSTRAINT payment_p2007_05_staff_id_fkey FOREIGN KEY (staff_id) REFERENCES staff(staff_id);

            ALTER TABLE ONLY payment_p2007_06
                ADD CONSTRAINT payment_p2007_06_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES customer(customer_id);

            ALTER TABLE ONLY payment_p2007_06
                ADD CONSTRAINT payment_p2007_06_rental_id_fkey FOREIGN KEY (rental_id) REFERENCES rental(rental_id);

            ALTER TABLE ONLY payment_p2007_06
                ADD CONSTRAINT payment_p2007_06_staff_id_fkey FOREIGN KEY (staff_id) REFERENCES staff(staff_id);

            ALTER TABLE ONLY payment
                ADD CONSTRAINT payment_rental_id_fkey FOREIGN KEY (rental_id) REFERENCES rental(rental_id) ON UPDATE CASCADE ON DELETE SET NULL;

            ALTER TABLE ONLY payment
                ADD CONSTRAINT payment_staff_id_fkey FOREIGN KEY (staff_id) REFERENCES staff(staff_id) ON UPDATE CASCADE ON DELETE RESTRICT;

            ALTER TABLE ONLY rental
                ADD CONSTRAINT rental_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON UPDATE CASCADE ON DELETE RESTRICT;

            ALTER TABLE ONLY rental
                ADD CONSTRAINT rental_inventory_id_fkey FOREIGN KEY (inventory_id) REFERENCES inventory(inventory_id) ON UPDATE CASCADE ON DELETE RESTRICT;

            ALTER TABLE ONLY rental
                ADD CONSTRAINT rental_staff_id_fkey FOREIGN KEY (staff_id) REFERENCES staff(staff_id) ON UPDATE CASCADE ON DELETE RESTRICT;

            ALTER TABLE ONLY staff
                ADD CONSTRAINT staff_address_id_fkey FOREIGN KEY (address_id) REFERENCES address(address_id) ON UPDATE CASCADE ON DELETE RESTRICT;

            ALTER TABLE ONLY staff
                ADD CONSTRAINT staff_store_id_fkey FOREIGN KEY (store_id) REFERENCES store(store_id);

            ALTER TABLE ONLY store
                ADD CONSTRAINT store_address_id_fkey FOREIGN KEY (address_id) REFERENCES address(address_id) ON UPDATE CASCADE ON DELETE RESTRICT;

        */
        
        address.addForeignKeyConstraint(city, address_city_id, city_city_id);
              
        city.addForeignKeyConstraint(country, city_country_id, country_country_id);

        customer.addForeignKeyConstraint(address, customer_address_id, address_address_id);

        customer.addForeignKeyConstraint(store, customer_store_id, store_store_id);

        film_actor.addForeignKeyConstraint(actor, film_actor_actor_id, actor_actor_id);

        film_actor.addForeignKeyConstraint(film, film_actor_film_id, film_film_id);

        film_category.addForeignKeyConstraint(category, film_category_category_id, category_category_id);

        film_category.addForeignKeyConstraint(film, film_category_film_id, film_film_id);

        film.addForeignKeyConstraint(language, film_language_id, language_language_id);

        film.addForeignKeyConstraint(language, film_original_language_id, language_language_id);

        inventory.addForeignKeyConstraint(film, inventory_film_id, film_film_id);

        inventory.addForeignKeyConstraint(store, inventory_store_id, store_store_id);

        payment.addForeignKeyConstraint(customer, payment.getColumn("customer_id"), customer_customer_id);

        payment_p2007_01.addForeignKeyConstraint(customer, payment_p2007_01.getColumn("customer_id"), customer_customer_id);

        payment_p2007_01.addForeignKeyConstraint(rental, payment_p2007_01.getColumn("rental_id"), rental_rental_id);

        payment_p2007_01.addForeignKeyConstraint(staff, payment_p2007_01.getColumn("staff_id"), staff_staff_id);

        payment_p2007_02.addForeignKeyConstraint(customer, payment_p2007_02.getColumn("customer_id"), customer_customer_id);

        payment_p2007_02.addForeignKeyConstraint(rental, payment_p2007_02.getColumn("rental_id"), rental_rental_id);

        payment_p2007_02.addForeignKeyConstraint(staff, payment_p2007_02.getColumn("staff_id"), staff_staff_id);

        payment_p2007_03.addForeignKeyConstraint(customer, payment_p2007_03.getColumn("customer_id"), customer_customer_id);

        payment_p2007_03.addForeignKeyConstraint(rental, payment_p2007_03.getColumn("rental_id"), rental_rental_id);

        payment_p2007_03.addForeignKeyConstraint(staff, payment_p2007_03.getColumn("staff_id"), staff_staff_id);

        payment_p2007_04.addForeignKeyConstraint(customer, payment_p2007_04.getColumn("customer_id"), customer_customer_id);

        payment_p2007_04.addForeignKeyConstraint(rental, payment_p2007_04.getColumn("rental_id"), rental_rental_id);

        payment_p2007_04.addForeignKeyConstraint(staff, payment_p2007_04.getColumn("staff_id"), staff_staff_id);

        payment_p2007_05.addForeignKeyConstraint(customer, payment_p2007_05.getColumn("customer_id"), customer_customer_id);

        payment_p2007_05.addForeignKeyConstraint(rental, payment_p2007_05.getColumn("rental_id"), rental_rental_id);

        payment_p2007_05.addForeignKeyConstraint(staff, payment_p2007_05.getColumn("staff_id"), staff_staff_id);

        payment_p2007_06.addForeignKeyConstraint(customer, payment_p2007_06.getColumn("customer_id"), customer_customer_id);

        payment_p2007_06.addForeignKeyConstraint(rental, payment_p2007_06.getColumn("rental_id"), rental_rental_id);

        payment_p2007_06.addForeignKeyConstraint(staff, payment_p2007_06.getColumn("staff_id"), staff_staff_id);
        
        rental.addForeignKeyConstraint(customer, rental_customer_id, customer_customer_id);

        rental.addForeignKeyConstraint(inventory, rental_inventory_id, inventory_inventory_id);

        rental.addForeignKeyConstraint(staff, rental_staff_id, staff_staff_id);

        staff.addForeignKeyConstraint(address, staff_address_id, address_address_id);

        staff.addForeignKeyConstraint(store, staff_store_id, store_store_id);

        store.addForeignKeyConstraint(address, store_address_id, address_address_id);
        
        payment.addForeignKeyConstraint(rental, payment.getColumn("rental_id"), rental_rental_id);
        
        payment.addForeignKeyConstraint(staff, payment.getColumn("staff_id"), staff_staff_id);
        
        
    }
    
    private void setupPayment(Table table) {
        Column payment_payment_id = table.addColumn("payment_id", new IntColumnType());
        payment_payment_id.setNotNull();
        payment_payment_id.setPrimaryKey();
        
        Column payment_customer_id = table.addColumn("customer_id", new SmallIntColumnType());
        payment_customer_id.setNotNull();
        
        Column payment_staff_id = table.addColumn("staff_id", new SmallIntColumnType());
        payment_staff_id.setNotNull();
        
        Column payment_rental_id = table.addColumn("rental_id", new IntColumnType());
        payment_rental_id.setNotNull();
        
        Column payment_amount = table.addColumn("amount", new NumericColumnType(5,2));
        payment_amount.setNotNull();
        
        Column payment_payment_date = table.addColumn("payment_date", new TimestampColumnType());
        payment_payment_date.setNotNull();
    }
    

}
