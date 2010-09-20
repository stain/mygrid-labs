package net.sf.taverna.t2.ui.perspectives.biocatalogue.integration.menus;

import java.awt.event.ActionEvent;
import java.net.URISyntaxException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;

import net.sf.taverna.biocatalogue.model.SoapProcessorIdentity;
import net.sf.taverna.t2.ui.menu.AbstractContextualMenuAction;
import net.sf.taverna.t2.ui.perspectives.biocatalogue.integration.Integration;
import net.sf.taverna.t2.ui.perspectives.biocatalogue.integration.health_check.ServiceHealthChecker;
import net.sf.taverna.t2.workflowmodel.Dataflow;


/**
 * NB! This class is not used anymore -- entry for it in META-INF/services/net.sf.taverna.t2.ui.menu.MenuComponent
 *     has been removed, so that it is not picked up by Taverna's SPI manager.
 * 
 * This class is generally no longer needed, as workflow health checks made by the
 * BioCatalogue plugin are now integration into the Validation Reporting mechanism.
 * 
 * @author Sergejs Aleksejevs
 */
public class MenuActionDataflowHealthCheck extends AbstractContextualMenuAction {

  public MenuActionDataflowHealthCheck() throws URISyntaxException {
    super(BioCatalogueContextualMenuSection.BIOCATALOGUE_MENU_SECTION_ID, 50);
  }

  @Override
  protected Action createAction() {
    Action action = new AbstractAction("Workflow Health Check") {
      public void actionPerformed(ActionEvent e) {
        List<SoapProcessorIdentity> processorsToCheck = Integration.extractSupportedProcessorsFromDataflow(getContextualSelection());
        ServiceHealthChecker.checkAllProcessorsInTheWorkflow(processorsToCheck);
      }
    };
    action.putValue(Action.SHORT_DESCRIPTION, "Check monitoring status of all processors in this workflow");
    return (action);
  }

  @Override
  public boolean isEnabled() {
    return super.isEnabled() && getContextualSelection().getSelection() instanceof Dataflow;
  }
  
  
}
