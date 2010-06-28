package net.sf.taverna.t2.activities.rest.ui.servicedescription;

import java.util.Arrays;
import java.util.List;

import javax.swing.Icon;

import net.sf.taverna.t2.activities.rest.RESTActivity;
import net.sf.taverna.t2.activities.rest.RESTActivityConfigurationBean;
import net.sf.taverna.t2.servicedescriptions.AbstractTemplateService;

/**
 * 
 * @author Sergejs Aleksejevs
 */
public class GenericRESTTemplateService extends AbstractTemplateService<RESTActivityConfigurationBean>
{
  public GenericRESTTemplateService ()
  {
    super();
    /*
      // TODO - re-enable this if it is necessary to add another folder inside "Service templates" in the Service Panel
      templateService = new AbstractTemplateService.TemplateServiceDescription() {
        public List<String> getPath() {
            return Arrays.asList(SERVICE_TEMPLATES, "REST");
        }
      };
    */
  }
  
  @Override
  public Class<RESTActivity> getActivityClass() {
    return RESTActivity.class;
  }
  
  @Override
  /**
   * Default values for this template service are provided in this method.
   */
  public RESTActivityConfigurationBean getActivityConfiguration() {
    RESTActivityConfigurationBean configBean = new RESTActivityConfigurationBean();
    configBean.setHttpMethod(RESTActivity.HTTP_METHOD.GET);
    configBean.setAcceptsHeaderValue("text/plain");
    configBean.setContentTypeForUpdates("application/xml");
    configBean.setUrlSignature("http://www.myexperiment.org/user.xml?id={userID}");
    return (configBean);
  }
  
  @Override
  public Icon getIcon() {
    return RESTActivityIcon.getRESTActivityIcon();
  }
  
  public String getName() {
    return "REST Service";
  }
  
  public String getDescription() {
    return "A generic REST service that can handle all HTTP methods";
  }
  
}