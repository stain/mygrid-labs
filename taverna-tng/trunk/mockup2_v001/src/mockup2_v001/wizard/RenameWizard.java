package mockup2_v001.wizard;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class RenameWizard extends Wizard {

	private class RenamePage extends WizardPage {

		public Text nameText;
		
		public RenamePage(String pageName) {
			super(pageName);
			setTitle("Rename");
			setDescription("Rename a Workflow Node");
		}
		
		@Override
		public void createControl(Composite parent) {
			Composite composite = new Composite(parent, SWT.NONE);
			
			Label lab = new Label(composite, SWT.NONE);
			lab.setText("Rename to: ");
			
			nameText = new Text(composite, SWT.NONE);
			nameText.setText(oldName);
			
			RowLayout layout = new RowLayout();
			composite.setLayout(layout);
			setControl(composite);
			
		}
		
	} // end of nested class
	
	private String oldName;
	private String newName;
	
	public RenameWizard(String oldName) {
		this.oldName = oldName;
		this.newName = null;
		
		addPage(new RenamePage("MyRenamePage"));
	}
	
	@Override
	public boolean performFinish() {
		RenamePage page = (RenamePage)getPage("MyRenamePage");
		
		if (page.nameText.getText().isEmpty()) {
			page.setErrorMessage("Something went wrong");
			return false;
		}
		newName = page.nameText.getText();
		return true;
	}
	
	public String getRenameValue() {
		return newName;
	}

}