package com.welyab.ayszu.internal;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listens when servlet application is initialized and detroyed, in order to configure Ayszu
 * subsystems.
 *
 * @author Welyab Paula
 */
@WebListener("Listens when servlet application is initialized and detroyed")
public class AyszuContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

	static class A {

		@Override
		protected void finalize() throws Throwable {
			System.out.println("finalizada");
		}
	}

	public static void main(String[] args) {
		A a = new A();
		a = null;
		System.gc();
	}
}
