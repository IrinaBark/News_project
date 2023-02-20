package by.htp.ex.listener;

import by.htp.ex.dao.connection_pool.ConnectionPool;
import by.htp.ex.dao.connection_pool.ConnectionPoolException;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ServContextListener implements ServletContextListener{
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		try {
			connectionPool.initPoolData();
		} catch (ConnectionPoolException e) {
			throw new RuntimeException("error during init of connection pool", e);
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce){
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		connectionPool.dispose();
		
	}

}
