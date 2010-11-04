/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package uk.org.taverna.t3.workbench.canvas.models.canvas.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;

import uk.org.taverna.t3.workbench.canvas.models.canvas.util.CanvasAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class CanvasItemProviderAdapterFactory extends CanvasAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
	/**
	 * This keeps track of the root adapter factory that delegates to this adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComposedAdapterFactory parentAdapterFactory;

	/**
	 * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IChangeNotifier changeNotifier = new ChangeNotifier();

	/**
	 * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<Object> supportedTypes = new ArrayList<Object>();

	/**
	 * This constructs an instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CanvasItemProviderAdapterFactory() {
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}

	/**
	 * This keeps track of the one adapter used for all {@link uk.org.taverna.t3.workbench.canvas.models.canvas.WorkflowInput} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WorkflowInputItemProvider workflowInputItemProvider;

	/**
	 * This creates an adapter for a {@link uk.org.taverna.t3.workbench.canvas.models.canvas.WorkflowInput}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createWorkflowInputAdapter() {
		if (workflowInputItemProvider == null) {
			workflowInputItemProvider = new WorkflowInputItemProvider(this);
		}

		return workflowInputItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link uk.org.taverna.t3.workbench.canvas.models.canvas.WorkflowOutput} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WorkflowOutputItemProvider workflowOutputItemProvider;

	/**
	 * This creates an adapter for a {@link uk.org.taverna.t3.workbench.canvas.models.canvas.WorkflowOutput}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createWorkflowOutputAdapter() {
		if (workflowOutputItemProvider == null) {
			workflowOutputItemProvider = new WorkflowOutputItemProvider(this);
		}

		return workflowOutputItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link uk.org.taverna.t3.workbench.canvas.models.canvas.ComponentInstanceInput} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentInstanceInputItemProvider componentInstanceInputItemProvider;

	/**
	 * This creates an adapter for a {@link uk.org.taverna.t3.workbench.canvas.models.canvas.ComponentInstanceInput}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createComponentInstanceInputAdapter() {
		if (componentInstanceInputItemProvider == null) {
			componentInstanceInputItemProvider = new ComponentInstanceInputItemProvider(this);
		}

		return componentInstanceInputItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link uk.org.taverna.t3.workbench.canvas.models.canvas.ComponentInstanceOutput} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentInstanceOutputItemProvider componentInstanceOutputItemProvider;

	/**
	 * This creates an adapter for a {@link uk.org.taverna.t3.workbench.canvas.models.canvas.ComponentInstanceOutput}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createComponentInstanceOutputAdapter() {
		if (componentInstanceOutputItemProvider == null) {
			componentInstanceOutputItemProvider = new ComponentInstanceOutputItemProvider(this);
		}

		return componentInstanceOutputItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link uk.org.taverna.t3.workbench.canvas.models.canvas.ComponentDefinitionReference} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentDefinitionReferenceItemProvider componentDefinitionReferenceItemProvider;

	/**
	 * This creates an adapter for a {@link uk.org.taverna.t3.workbench.canvas.models.canvas.ComponentDefinitionReference}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createComponentDefinitionReferenceAdapter() {
		if (componentDefinitionReferenceItemProvider == null) {
			componentDefinitionReferenceItemProvider = new ComponentDefinitionReferenceItemProvider(this);
		}

		return componentDefinitionReferenceItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link uk.org.taverna.t3.workbench.canvas.models.canvas.Canvas} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CanvasItemProvider canvasItemProvider;

	/**
	 * This creates an adapter for a {@link uk.org.taverna.t3.workbench.canvas.models.canvas.Canvas}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createCanvasAdapter() {
		if (canvasItemProvider == null) {
			canvasItemProvider = new CanvasItemProvider(this);
		}

		return canvasItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link uk.org.taverna.t3.workbench.canvas.models.canvas.Node} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NodeItemProvider nodeItemProvider;

	/**
	 * This creates an adapter for a {@link uk.org.taverna.t3.workbench.canvas.models.canvas.Node}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createNodeAdapter() {
		if (nodeItemProvider == null) {
			nodeItemProvider = new NodeItemProvider(this);
		}

		return nodeItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link uk.org.taverna.t3.workbench.canvas.models.canvas.CoreComponentInstance} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CoreComponentInstanceItemProvider coreComponentInstanceItemProvider;

	/**
	 * This creates an adapter for a {@link uk.org.taverna.t3.workbench.canvas.models.canvas.CoreComponentInstance}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createCoreComponentInstanceAdapter() {
		if (coreComponentInstanceItemProvider == null) {
			coreComponentInstanceItemProvider = new CoreComponentInstanceItemProvider(this);
		}

		return coreComponentInstanceItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link uk.org.taverna.t3.workbench.canvas.models.canvas.HelperComponentInstance} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HelperComponentInstanceItemProvider helperComponentInstanceItemProvider;

	/**
	 * This creates an adapter for a {@link uk.org.taverna.t3.workbench.canvas.models.canvas.HelperComponentInstance}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createHelperComponentInstanceAdapter() {
		if (helperComponentInstanceItemProvider == null) {
			helperComponentInstanceItemProvider = new HelperComponentInstanceItemProvider(this);
		}

		return helperComponentInstanceItemProvider;
	}

	/**
	 * This returns the root adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComposeableAdapterFactory getRootAdapterFactory() {
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	/**
	 * This sets the composed adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		this.parentAdapterFactory = parentAdapterFactory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object type) {
		return supportedTypes.contains(type) || super.isFactoryForType(type);
	}

	/**
	 * This implementation substitutes the factory itself as the key for the adapter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter adapt(Notifier notifier, Object type) {
		return super.adapt(notifier, this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object adapt(Object object, Object type) {
		if (isFactoryForType(type)) {
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class<?>) || (((Class<?>)type).isInstance(adapter))) {
				return adapter;
			}
		}

		return null;
	}

	/**
	 * This adds a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void addListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.addListener(notifyChangedListener);
	}

	/**
	 * This removes a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void removeListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.removeListener(notifyChangedListener);
	}

	/**
	 * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void fireNotifyChanged(Notification notification) {
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null) {
			parentAdapterFactory.fireNotifyChanged(notification);
		}
	}

	/**
	 * This disposes all of the item providers created by this factory. 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void dispose() {
		if (workflowInputItemProvider != null) workflowInputItemProvider.dispose();
		if (workflowOutputItemProvider != null) workflowOutputItemProvider.dispose();
		if (componentInstanceInputItemProvider != null) componentInstanceInputItemProvider.dispose();
		if (componentInstanceOutputItemProvider != null) componentInstanceOutputItemProvider.dispose();
		if (componentDefinitionReferenceItemProvider != null) componentDefinitionReferenceItemProvider.dispose();
		if (canvasItemProvider != null) canvasItemProvider.dispose();
		if (nodeItemProvider != null) nodeItemProvider.dispose();
		if (coreComponentInstanceItemProvider != null) coreComponentInstanceItemProvider.dispose();
		if (helperComponentInstanceItemProvider != null) helperComponentInstanceItemProvider.dispose();
	}

}