package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import main.java.com.excilys.formation.cdb.model.Company;
import main.java.com.excilys.formation.cdb.model.Computer;
import main.java.com.excilys.formation.cdb.persistence.ComputerDAO;
import main.java.com.excilys.formation.cdb.persistence.Connexion;


@RunWith(PowerMockRunner.class)
@PrepareForTest(Connexion.class)
@PowerMockIgnore( {"javax.management.*"})
public class TestSQL {




	/**
	 * Create a connection.
	 * 
	 * @return connection object
	 * @throws SQLException
	 */
	private static Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:hsqldb:mem:computer-database_db", "admincdb", "qwerty1234");
	}

	/**
	 * Database initialization for testing i.e.
	 * <ul>
	 * <li>Creating Table</li>
	 * <li>Inserting record</li>
	 * </ul>
	 * 
	 * @throws SQLException
	 */
	private static void initDatabase() {
		try (Connection connection = getConnection(); Statement statement = connection.createStatement();) {

			statement.execute("CREATE USER admincdb PASSWORD qwerty1234 ADMIN");
			connection.commit();

			statement.execute(
					"  create table company (\n" + 
							"    id                        bigint not null ,\n" + 
							"    name                      varchar(255),\n" + 
							"    constraint pk_company primary key (id))\n" + 
							"  ;\n" + 
					"\n");
			connection.commit();
			statement.execute(
					"  create table computer ("
					+ "id bigint not null , "
					+ "name varchar(255), "
					+ "introduced date NULL, "
					+ "company_id bigint NULL, \n" 
					+ "discontinued date NULL,\n" 
					+ "constraint pk_computer primary key (id), "
					+ "constraint fk_computer_company_1 "
					+ "foreign key (company_id) references company (id));");
			connection.commit();

			statement.executeUpdate(
					"insert into company (id,name) values (  1,'Apple Inc.');");
			connection.commit();
			statement.executeUpdate("insert into computer (id,name,introduced,discontinued,company_id) "
					+ "values (  1,'MacBook Pro 15.4 inch',null,null,1);");
			connection.commit();
			statement.executeUpdate("insert into computer (id,name,introduced,discontinued,company_id) "
					+ "values (  3,'CM-200',null,null,1);");
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@BeforeClass
	public static void init() throws SQLException, ClassNotFoundException, IOException {
		//Class.forName("org.hsqldb.jdbc.JDBCDriver");

		// initialize database
		initDatabase();
	}


	@Before
	public void initTest() throws SQLException {
		PowerMockito.mockStatic(Connexion.class);
		when(Connexion.getInstance()).thenReturn(getConnection());

	}

	@Test
	public void testGetListComputers() {
		
		List <String> ls = ComputerDAO.getInstance().getList();

		assertEquals("MacBook Pro 15.4 inch", ls.get(0));
		
		assertEquals("CM-200" , ls.get(1));
	}

	/*
	@Test 
	public void testComputerGetCount() {



		int pageMax = ComputerDAO.getInstance().getMaxPage(1);


		assertEquals(2, pageMax);


	}*/

	@Test 
	public void testComputerGetById() throws SQLException {

		Computer computerActual = ComputerDAO.getInstance().getById(1).orElse(new Computer());

		Computer computerExpected = new Computer.ComputerBuilder().withId(1)
				.withName("MacBook Pro 15.4 inch").withDateIntro(null).withDateDisc(null).withCompany(
						new Company(1, "Apple Inc.")).build();


		if (computerExpected != null && computerActual != null) {
			assertTrue(computerExpected.equals(computerActual));
		}



	}






	@AfterClass
	public static void destroy() throws SQLException, ClassNotFoundException, IOException {
		try (Connection connection = getConnection(); Statement statement = connection.createStatement();) {
			statement.executeUpdate("DROP TABLE computer;\n"
					+ "DROP TABLE company;");
			connection.commit();
		}
	}


}
