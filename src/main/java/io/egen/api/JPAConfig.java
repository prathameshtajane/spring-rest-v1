package io.egen.api;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement

public class JPAConfig {
	
	@Autowired
    private Environment env;
    
	/*
    * Since we don't have EntityManagerFactory , we will declare it over here.
    * As you already know that EntityManagerFactory has to be a SINGLETON , so in SPRING what is way to create
    * SINGLETON? -> BEAN
    * Here we can not use Annotation because we don't have an access to a class which is well within SPRING source code
    * to use @Component ,we will do it in following way.
    * */
	
	@Bean
    public LocalContainerEntityManagerFactoryBean emf(){
        LocalContainerEntityManagerFactoryBean emf= new LocalContainerEntityManagerFactoryBean();
        /*
        * We will use the instance of LocalContainerEntityManagerFactoryBean emf to configure all the configuration
        * details that was done in persistence.xml
        * */
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        //This specifies the persistence that we are going to use
        //Spring has its own implemenetation which provide persistence implementation called as 'Hibernate'
        //This line is equivalent of setting
        //<provider>org.hibernate.jpa.HibernatePersistenceProvider</provider> in persistence.xml

        emf.setDataSource(getDataSource());
        emf.setPackagesToScan("io.egen.api.entity");//This package will store all the entity objects
        emf.setJpaProperties(jpaProperties());
       
        return emf;
    }
	
	
	@Bean
    public DataSource getDataSource(){
		
        /*
        * <property name="javax.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
            <property name="javax.persistence.jdbc.url"
                      value="jdbc:mysql://localhost:3306/example_db?useUnicode=true&amp;useJDBCCompliantTimezoneShift=true&amp;useLegacyDatetimeCode=false&amp;serverTimezone=UTC"/>
            <property name="javax.persistence.jdbc.user" value="root"/>
            <property name="javax.persistence.jdbc.password" value="root"/>
        *
        * We will be setting theses properties over here in JAVA object form*/


        DriverManagerDataSource ds = new DriverManagerDataSource();

        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        
       
        ds.setUrl("jdbc:mysql://localhost:3306/example_db?serverTimezone=UTC");
        //ds.setUrl("jdbc:mysql://localhost:3306/example_db?useUnicode=true;useJDBCCompliantTimezoneShift=true;useLegacyDatetimeCode=false;serverTimezone=UTC");
        ds.setUsername("root");
        ds.setPassword("root");
       
       
        /*
        ds.setUrl(env.getProperty("db.url","Put Fallback URL here"));
        ds.setUsername(env.getProperty("db.user","root"));
        ds.setPassword(env.getProperty("db.password","root"));
		*/
        
        return (DataSource) ds;
    }
	
	
	private Properties jpaProperties(){
        /*

        This function is used to set all other properties that was set in persistence.xml configuration file.

        * <property name="hibernate.dialect" value="org.hibernate.dialect.MySQLDialect"/>
          <property name="hibernate.hbm2ddl.auto" value="validate"/>
          <!--<property name="hibernate.hbm2ddl.auto" value="create-drop"/>-->
          <!-- Way to tell Hibernate to drop all of the tables when App is started and drop all of the tables
          from the database when App is closed -->
          <property name="hibernate.show_sql" value="true"/>
          <property name="hibernate.format_sql" value="true"/>
        *
        * */
		
        Properties props=new Properties();
        props.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
        
        /*
        props.setProperty("hibernate.hbm2ddl.auto","create");
        props.setProperty("hibernate.show_sql","true");
        props.setProperty("hibernate.format_sql","true");
        */
        
        props.setProperty("hibernate.hbm2ddl.auto",env.getProperty("hibernate.hbm2ddl","create"));
        props.setProperty("hibernate.show_sql",env.getProperty("hibernate.show.sql","true"));
        props.setProperty("hibernate.format_sql",env.getProperty("hibernate.format.sql","true"));
        return props;
    }
	
	@Bean
    public PlatformTransactionManager txnMgr(EntityManagerFactory emf){
            return new JpaTransactionManager(emf);
    }

}
