package net.sf.taverna.t2.activities.rest.ui.servicedescription;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.sf.taverna.t2.activities.rest.RESTActivity;
import net.sf.taverna.t2.workbench.activityicons.ActivityIconSPI;
import net.sf.taverna.t2.workflowmodel.processor.activity.Activity;

/**
 * 
 * @author Sergejs Aleksejevs
 */
public class RESTActivityIcon implements ActivityIconSPI
{
  private static ImageIcon icon;
  
  public int canProvideIconScore(Activity<?> activity)
  {
    if (activity.getClass().getName().equals(RESTActivity.class.getName()))
      return DEFAULT_ICON + 1;
    else
      return NO_ICON;
  }

  public Icon getIcon(Activity<?> activity) {
    return (getRESTActivityIcon());
  }

  public static Icon getRESTActivityIcon() {
    if (icon == null) {
      synchronized(RESTActivityIcon.class) {
        if (icon == null) {
          try {
            icon = new ImageIcon(RESTActivityIcon.class.getResource("service_type_rest.png"));
          }
          catch (NullPointerException e) {
            /* icon wasn't found - do nothing, but no icon will be available */
          }
        }
      }
    }
    return (icon);
  }

}