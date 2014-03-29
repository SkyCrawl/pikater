package pikater.utility.pikaterDatabase;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.postgresql.PGConnection;

import pikater.data.PostgreSQLConnectionProvider;
import pikater.data.jpa.JPADataSetLO;
import pikater.data.jpa.JPARole;
import pikater.data.jpa.JPAUser;
import pikater.data.jpa.JPAUserPriviledge;
import pikater.utility.pikaterDatabase.exceptions.UserNotFoundException;

public class DatabaseInitialisation {

	public enum PasswordChangeResult{Success,Error};
	
	PGConnection connection;
	EntityManagerFactory emf=null;
	EntityManager em = null;
	Database database = null;
	
	public DatabaseInitialisation(EntityManagerFactory emf,PGConnection connection){
		this.emf=emf;
		this.connection=connection;
		this.database = new Database(emf, connection);
	}
	
	/**
	 * Initialisation of test-data in DataBase
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws UserNotFoundException 
	 */
	private void itialisationData() throws SQLException, IOException, UserNotFoundException{
				
		File f=new File("data/files/25d7d5d689042a3816aa1598d5fd56ef");
		System.out.println("--------------------");
		System.out.println(f.getAbsolutePath());
		System.out.println("MD5 hash: "+database.getMD5Hash(f));
		System.out.println("--------------------");
		
		File f2=new File("data/files/772c551b8486b932aed784a582b9c1b1");
		System.out.println(f2.getAbsolutePath());
		System.out.println("MD5 hash: "+database.getMD5Hash(f2));
		System.out.println("--------------------");
		
		File f3=new File("data/files/dc7ce6dea5a75110486760cfac1051a5");
		System.out.println(f3.getAbsolutePath());
		System.out.println("MD5 hash: "+database.getMD5Hash(f3));
		System.out.println("--------------------");
		
		
		JPAUser user=database.getUserByLogin("stepan");
		System.out.println(user.getEmail());
		
		JPADataSetLO first= database.saveDataSet(user, f, "Iris",database.createGlobalMetaData(150,"Classification"));
		//first.setGlobalMetaData();
		//database.persist(first);
		
		
		
		JPADataSetLO second=database.saveDataSet(user, f2, "Weather",database.createGlobalMetaData(14,"Multivariate"));
		//second.setGlobalMetaData(database.createGlobalMetaData(14,"Multivariate"));
		//database.persist(second);
		
		JPADataSetLO third=database.saveDataSet(user, f3, "Linear Data",database.createGlobalMetaData(5000,"Regression"));
		//third.setGlobalMetaData(database.createGlobalMetaData(5000,"Regression"));//!!!!CHECK DEFAULT TASK TYPE
		//database.persist(third);
		
		/**
		JPADataSetLO fourth=database.saveDataSet(user, f2, "Dataset from second file, but identical with previous");
		JPADataSetLO fifth=database.saveDataSet(user, f3, "Dataset from the third file");
		JPADataSetLO sixth=database.saveDataSet(user, f3, "Dataset from the third file, but identical with previous");
		**/
		
		
		
		
		for(JPADataSetLO dslo:database.getAllDataSetLargeObjects()){
			System.out.println("OID: "+dslo.getOID()+"  Hash:  "+dslo.getHash()+"  "+dslo.getDescription()+" ---  "+dslo.getOwner().getLogin()+"  GM.noInst: "+dslo.getGlobalMetaData().getNumberofInstances()+"  GM.DefTT: "+dslo.getGlobalMetaData().getDefaultTaskType().getName() );
		}
		
		
/**
		JPAUserPriviledge priviledgeSaveData = new JPAUserPriviledge();
		priviledgeSaveData.setName("SaveDatates");

		JPAUserPriviledge priviledgeSaveBox = new JPAUserPriviledge();
		priviledgeSaveBox.setName("SaveBox");

		
		JPARole userRole = new JPARole();
		userRole.setName("user");
		userRole.setDescription("Standard user role");
		userRole.addPriviledge(priviledgeSaveData);

		JPARole adminRole = new JPARole();
		adminRole.setName("admin");
		adminRole.setDescription("Admin role");
		userRole.addPriviledge(priviledgeSaveData);
		userRole.addPriviledge(priviledgeSaveBox);

		
		JPAUser stepanUser = new JPAUser();
		stepanUser.setLogin("stepan");
		stepanUser.setPassword("123");
		stepanUser.setEmail("Bc.Stepan.Balcar@gmail.com");
		stepanUser.setPriorityMax(9);
		stepanUser.setRole(adminRole);

		JPAUser kjUser = new JPAUser();
		kjUser.setLogin("kj");
		kjUser.setPassword("123");
		kjUser.setEmail("kj@gmail.com");
		kjUser.setPriorityMax(9);
		kjUser.setRole(adminRole);

		JPAUser sjUser = new JPAUser();
		sjUser.setLogin("sj");
		sjUser.setPassword("123");
		sjUser.setEmail("sj@gmail.com");
		sjUser.setPriorityMax(9);
		sjUser.setRole(adminRole);

		JPAUser spUser = new JPAUser();
		spUser.setLogin("sp");
		spUser.setPassword("123");
		spUser.setEmail("sp@gmail.com");
		spUser.setPriorityMax(9);
		spUser.setRole(adminRole);

		JPAUser martinUser = new JPAUser();
		martinUser.setLogin("martin");
		martinUser.setPassword("123");
		martinUser.setEmail("Martin.Pilat@mff.cuni.cz");
		martinUser.setPriorityMax(9);
		martinUser.setRole(userRole);
		
		database.persist(stepanUser);
		
		
		
		JPADataSetLO dslo=database.saveDataSet(stepanUser, new File());
		**/
		
		
		
		
	}

	private void testData() throws SQLException, IOException, UserNotFoundException{
		
		database.addRole("user", "Standard user role");
		database.addRole("admin","Standard administrator role");
		
		database.addUser("stepan", "123", "bc.stepan.balcar@gmail.com", 9); // + role
		database.addUser("kj", "123", "nassoftwerak@gmail.com", 6);
		database.addUser("sj", "123", "nassoftwerak@gmail.com", 6);
		database.addUser("sp", "123", "nassoftwerak@gmail.com", 6);
		database.addUser("martin", "123", "Martin.Pilat@mff.cuni.cz", 0);

		database.setRoleForUser("stepan", "admin");
		database.setRoleForUser("kj", "admin");
		database.setRoleForUser("sj", "admin");
		database.setRoleForUser("sp", "admin");
		database.setRoleForUser("martin", "user");
				
		JPAUserPriviledge priviledgeSaveData = new JPAUserPriviledge();
		priviledgeSaveData.setName("SaveDataSet");

		JPAUserPriviledge priviledgeSaveBox = new JPAUserPriviledge();
		priviledgeSaveBox.setName("SaveBox");

		JPARole roleAdmin = database.getRoleByName("admin");
		roleAdmin.addPriviledge(priviledgeSaveData);
		roleAdmin.addPriviledge(priviledgeSaveBox);
		
		JPARole roleUser = database.getRoleByName("user");
		roleUser.addPriviledge(priviledgeSaveData);
	
		JPAUser stepan = database.getUserByLogin("stepan");
		stepan.setRole(roleAdmin);

		database.persist(stepan);
		/**
		JPAUser john=this.getUserByLogin("johndoe");
		this.saveGeneralFile(john.getId(), "First Data File",new File( "./data/files/25d7d5d689042a3816aa1598d5fd56ef"));
		this.saveGeneralFile(john.getId(), "Second Data File",new File( "./data/files/772c551b8486b932aed784a582b9c1b1"));
		this.saveGeneralFile(john.getId(), "Third Data File",new File( "./data/files/dc7ce6dea5a75110486760cfac1051a5"));
		**/
		
		
		for(JPADataSetLO dslo:database.getAllDataSetLargeObjects()){
			System.out.println("OID: "+dslo.getOID()+"  Hash:  "+dslo.getHash()+"  "+dslo.getDescription()+" ---  "+dslo.getOwner().getLogin()+"  GM.noInst: "+dslo.getGlobalMetaData().getNumberofInstances()+"  GM.DefTT: "+dslo.getGlobalMetaData().getDefaultTaskType().getName() );
		}

	}
	
	public static void main(String[] args) throws SQLException, IOException, UserNotFoundException, ClassNotFoundException {
        
		EntityManagerFactory emf=Persistence.createEntityManagerFactory("pikaterDataModel");
		
		DatabaseInitialisation data = new DatabaseInitialisation(emf,(PGConnection)(new PostgreSQLConnectionProvider("jdbc:postgresql://nassoftwerak.ms.mff.cuni.cz:5432/pikater", "pikater", "a").getConnection()));
		data.itialisationData();
	}
}
