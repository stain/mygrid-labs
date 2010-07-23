package uk.org.taverna.tng.mockup3.workbench.views;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;

import uk.org.taverna.tng.mockup3.core.ComponentDefinitionsRegistry;
import uk.org.taverna.tng.mockup3.core.ISearchTermProvider;
import uk.org.taverna.tng.mockup3.workbench.commands.ICommandParameters;
import uk.org.taverna.tng.mockup3.workbench.commands.ICommands;
import uk.org.taverna.tng.mockup3.workbench.util.CustomContentProvider;
import uk.org.taverna.tng.mockup3.workbench.util.CustomLabelProvider;

public class ComponentPaletteView extends ViewPart implements
		ISearchTermProvider {
	public static final String ID = "uk.org.taverna.tng.mockup3.workbench.views.ComponentPaletteView";

	protected static final int SASH_LIMIT = 50;

	private Composite parentContainer;
	private StackLayout containerStackLayout;
	private FilteredTree currentComponentsTree;
	private Button searchMoreButton;

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		
		parentContainer = parent;

		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		GridLayout mainLayout = new GridLayout(1, true);
		mainLayout.marginWidth = 0;
		mainLayout.marginHeight = 0;
		parent.setLayout(mainLayout);
		
		containerStackLayout = new StackLayout();
		
		Composite stacks = new Composite(parent, SWT.NONE);
		stacks.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		stacks.setLayout(containerStackLayout);

		currentComponentsTree = new FilteredTree(stacks, SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL, new PatternFilter(), true);
		currentComponentsTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				true, true));
		TreeViewer currentComponentsTreeViewer = currentComponentsTree
				.getViewer();
		getSite().setSelectionProvider(currentComponentsTreeViewer);
		currentComponentsTreeViewer
				.setContentProvider(new CustomContentProvider());
		currentComponentsTreeViewer.setLabelProvider(new CustomLabelProvider());
		currentComponentsTreeViewer
				.setInput(ComponentDefinitionsRegistry.INSTANCE.getAllEntries());

		containerStackLayout.topControl = currentComponentsTree;
		
		searchMoreButton = new Button(parent, SWT.PUSH);
		searchMoreButton.setText("Search for new components...");
		searchMoreButton.setLayoutData(new GridData(SWT.FILL, SWT.END, true, false));
		searchMoreButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					// TODO: cache these instead of fetching each time!
					IHandlerService handlerService = (IHandlerService) getSite().getService(IHandlerService.class);
					ICommandService commandService = (ICommandService) getSite().getService(ICommandService.class);
					
					Command searchCommand = commandService.getCommand(ICommands.SEARCH_NEW_COMPONENTS);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put(ICommandParameters.SEARCH_TERM, getSearchTerm());
					ParameterizedCommand paramSarchCommand = ParameterizedCommand.generateCommand(searchCommand, params);
					ExecutionEvent execEvent = handlerService.createExecutionEvent(paramSarchCommand, null);
					searchCommand.executeWithChecks(execEvent);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		if (currentComponentsTree != null) {
			currentComponentsTree.getFilterControl().setFocus();
		}
	}

	@Override
	public String getSearchTerm() {
		if (currentComponentsTree != null) {
			return currentComponentsTree.getFilterControl().getText();
		} else {
			return "";
		}
	}

}
