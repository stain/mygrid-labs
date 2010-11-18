package uk.org.taverna.t3.workbench.products.main.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import uk.org.taverna.t3.workbench.products.main.Initialiser;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		// TODO: move this out and use extension points instead.
		// See JIRA task: http://www.mygrid.org.uk/dev/issues/browse/TNG-102
		Initialiser initialiser = new Initialiser();
		initialiser.setupInitialSetOfWorkflowsComponents();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
