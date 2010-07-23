package prototype;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.InvalidXPathException;
import org.dom4j.Node;
import org.dom4j.XPath;


/**
 * @author Sergejs Aleksejevs
 */
public class XPathActivityConfigurationPanel extends JPanel
{
  private JTextArea taSourceXML;
  private JButton bParseXML;
  private XPathActivityXMLTree xmlTree;
  
  private JLabel jlXPathExpressionStatus;
  private JTextField tfXPathExpression;
  private JButton bRunXPath;
  
  private JPanel jpTemp;
  
  
  public XPathActivityConfigurationPanel()
  {
    this.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    
    c.gridx = 0;
    c.gridy = 0;
    c.fill = GridBagConstraints.BOTH;
    c.weightx = 0.5;
    c.weighty = 1.0;
    c.insets = new Insets(10, 10, 0, 5);
    taSourceXML = new JTextArea(10, 10);
    JScrollPane spSourceXML = new JScrollPane(taSourceXML);
    this.add(spSourceXML, c);
    
    c.gridx = 1;
    c.fill = GridBagConstraints.NONE;
    c.weightx = 0;
    c.weighty = 0;
    c.insets = new Insets(0, 0, 0, 0);
    bParseXML = new JButton(">>");
    bParseXML.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        parseXML();
      }
    });
    this.add(bParseXML, c);
    
    c.gridx = 2;
    c.fill = GridBagConstraints.BOTH;
    c.weightx = 0.5;
    c.weighty = 1.0;
    c.insets = new Insets(10, 5, 0, 10);
    JTextArea taTemp = new JTextArea(10, 10);
    taTemp.setEditable(false);
    jpTemp = new JPanel(new GridLayout(1,1));
    jpTemp.add(new JScrollPane(taTemp));
    this.add(jpTemp, c);
    
    
    
    c.gridx = 0;
    c.gridy++;
    c.gridwidth = 3;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 1.0;
    c.weighty = 0;
    c.insets = new Insets(10, 10, 10, 10);
    this.add(createXPathPanel(), c);
  }
  
  
  private JPanel createXPathPanel()
  {
    this.jlXPathExpressionStatus = new JLabel(XPathActivityIcon.getIconById(XPathActivityIcon.XPATH_STATUS_UNKNOWN_ICON));
    
    this.bRunXPath = new JButton("Run XPath");
    this.bRunXPath.setEnabled(false);
    this.bRunXPath.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        runXPath();
      }
    });
    
    this.tfXPathExpression = new JTextField(30);
    this.tfXPathExpression.setPreferredSize(new Dimension(0, this.bRunXPath.getPreferredSize().height));
    this.tfXPathExpression.addCaretListener(new CaretListener() {
      public void caretUpdate(CaretEvent e) {
        validateXPath();
      }
    });
    this.tfXPathExpression.addKeyListener(new KeyListener() {
      public void keyPressed(KeyEvent e) { 
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          runXPath();
        }
      }
      public void keyReleased(KeyEvent e) { /* not in use */ }
      public void keyTyped(KeyEvent e) { /* not in use */ }
    });
    
    JPanel jpXPath = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    
    c.weightx = 0;
    jpXPath.add(jlXPathExpressionStatus);
    
    c.weightx = 1.0;
    c.insets = new Insets(0, 10, 0, 10);
    jpXPath.add(tfXPathExpression, c);
    
    c.weightx = 0;
    c.insets = new Insets(0, 0, 0, 0);
    jpXPath.add(bRunXPath, c);
    
    return (jpXPath);
  }
  
  
  private void parseXML()
  {
    String xmlData = taSourceXML.getText();
    
    try {
      xmlTree = XPathActivityXMLTree.createFromXMLData(xmlData, this);
      xmlTree.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      JScrollPane spXMLTree = new JScrollPane(xmlTree);
      jpTemp.removeAll();
      jpTemp.add(spXMLTree);
      
      this.validate();
      this.repaint();
    }
    catch (DocumentException e) {
      JOptionPane.showMessageDialog(this, e.getMessage(), "XPath Activity", JOptionPane.ERROR_MESSAGE);
      return;
    }
  }
  
  
  protected void updateXPathTextField() {
    tfXPathExpression.setText(xmlTree.getCurrentXPathExpression());
  }
  
  
  private void validateXPath()
  {
    String candidatePath = tfXPathExpression.getText().trim();
    
    if (candidatePath.length() == 0) {
      jlXPathExpressionStatus.setIcon(XPathActivityIcon.getIconById(XPathActivityIcon.XPATH_STATUS_UNKNOWN_ICON));
      this.bRunXPath.setEnabled(false);
    }
    else {
      try {
        DocumentHelper.createXPath(candidatePath);
        jlXPathExpressionStatus.setIcon(XPathActivityIcon.getIconById(XPathActivityIcon.XPATH_STATUS_OK_ICON));
        this.bRunXPath.setEnabled(true);
      }
      catch (InvalidXPathException e) {
        jlXPathExpressionStatus.setIcon(XPathActivityIcon.getIconById(XPathActivityIcon.XPATH_STATUS_ERROR_ICON));
        this.bRunXPath.setEnabled(false);
      }
    }
  }
  
  
  private void runXPath()
  {
    try {
      XPath expr = DocumentHelper.createXPath(tfXPathExpression.getText());
      
      Document doc = DocumentHelper.parseText(taSourceXML.getText());
      List<Node> matchingNodes = expr.selectNodes(doc);
      
      
      StringBuffer outNodes = new StringBuffer();
      
      outNodes.append(matchingNodes.size() + "\n\n");
      for (Node n : matchingNodes) {
        outNodes.append(n.asXML() + "\n");
      }
      JOptionPane.showMessageDialog(this, outNodes);
    }
    catch (DocumentException e) {
      JOptionPane.showMessageDialog(this, e.getMessage(), "XPath Activity", JOptionPane.ERROR_MESSAGE);
    }
    
  }
  
  
  public static void main(String[] args)
  {
    JFrame frame = new JFrame();
    frame.getContentPane().add(new XPathActivityConfigurationPanel());
    frame.pack();
    frame.setSize(new Dimension(800, 600));
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

}
