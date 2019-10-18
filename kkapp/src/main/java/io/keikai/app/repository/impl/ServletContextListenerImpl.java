package io.keikai.app.repository.impl;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import io.keikai.app.BookManager;
import io.keikai.app.impl.BookManagerImpl;
import io.keikai.app.repository.BookRepositoryFactory;
import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.WebApps;

public class ServletContextListenerImpl implements ServletContextListener, Serializable {
	private static final long serialVersionUID = 7123078891875657326L;
	private static final Log logger = Log.lookup(ServletContextListenerImpl.class.getName());

	@Override
	public void contextInitialized(ServletContextEvent sce) {}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//ZSS-1052: skip this case, repository doesn't loaded before means there is nothing unsaved file waiting for saving.
		if(WebApps.getCurrent() != null) {
			BookManager manager = BookManagerImpl.getInstance(
					BookRepositoryFactory.getInstance().getRepository());
			manager.shutdownAutoFileSaving();
			try {
				manager.saveAll();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("Saving all files causes error: " + e.getMessage());
			}
		}
	}
}
