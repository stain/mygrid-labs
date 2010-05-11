/**
 * 
 */
package net.sf.taverna.t2.provenance.api.client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import net.sf.taverna.t2.invocation.InvocationContext;
import net.sf.taverna.t2.provenance.api.NativeAnswer;
import net.sf.taverna.t2.provenance.api.ProvenanceAccess;
import net.sf.taverna.t2.provenance.api.ProvenanceConnectorType;
import net.sf.taverna.t2.provenance.api.ProvenanceQueryParser;
import net.sf.taverna.t2.provenance.api.Query;
import net.sf.taverna.t2.provenance.api.QueryAnswer;
import net.sf.taverna.t2.provenance.api.QueryParseException;
import net.sf.taverna.t2.provenance.api.QueryValidationException;
import net.sf.taverna.t2.provenance.lineageservice.Dependencies;
import net.sf.taverna.t2.provenance.lineageservice.LineageQueryResultRecord;
import net.sf.taverna.t2.provenance.lineageservice.utils.QueryVar;
import net.sf.taverna.t2.reference.T2Reference;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

/**
 * @author Paolo Missier<p/>
 * Example provenance API client.
 */
public class ProvenanceAPISampleClient {

	private static final String DEFAULT_OPM_FILENAME = "src/test/resources/OPMGraph.rdf";

	ProvenanceAccess pAccess = null;

	String DB_URL_LOCAL = PropertiesReader.getString("dbhost");  // URL of database server //$NON-NLS-1$
	String DB_USER = PropertiesReader.getString("dbuser");                        // database user id //$NON-NLS-1$
	String DB_PASSWD = PropertiesReader.getString("dbpassword"); //$NON-NLS-1$
	static String OPMGraphFilename = null;

	List<String> wfNames = null;
	Set<String> selectedProcessors = null;

	private boolean derefValues = false;
	InvocationContext ic = null;

	private static Logger logger = Logger.getLogger(ProvenanceAPISampleClient.class);

	/**
	 * Creates an instance of the client, uses it to submit a pre-defined query, and displays the results on a console 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		ProvenanceAPISampleClient client = new ProvenanceAPISampleClient();

		client.setUp();
		OPMGraphFilename = setOPMFilename();
		QueryAnswer answer = client.queryProvenance();

		client.reportAnswer(answer);
		client.saveOPMGraph(answer, OPMGraphFilename);
	}



	/**
	 * parses an XML provenance query into a Query object and invokes {@link ProvenanceAccess.executeQuery()} 
	 * @return a bean representing the query answer
	 * @throws QueryValidationException 
	 * @throws QueryParseException 
	 * @see QueryAnswer
	 */
	protected  QueryAnswer queryProvenance() throws QueryParseException, QueryValidationException {

		Query q = new Query();

		// get filename for XML query spec
		String querySpecFile = PropertiesReader.getString("query.file");
		logger.info("executing query "+querySpecFile);

		ProvenanceQueryParser pqp = new ProvenanceQueryParser();
		pqp.setPAccess(pAccess);

		q = pqp.parseProvenanceQuery(querySpecFile);

		if (q == null) {
			logger.fatal("query processing failed. So sorry.");
			return null;
		}
		logger.info("YOUR QUERY: "+q.toString());

		QueryAnswer answer=null;
		try {
			answer = pAccess.executeQuery (q);
		} catch (SQLException e) {
			logger.fatal("Exception while executing query: "+e.getMessage());
			return null;
		}
		return answer;
	}

	
	
	/////////
	/// preliminary setup methods
	/////////
	
	// user-selected file name for OPM graph?
	protected static String setOPMFilename() {
		
		String OPMGraphFilename = PropertiesReader.getString("OPM.rdf.file");
		if (OPMGraphFilename == null) {
			OPMGraphFilename = DEFAULT_OPM_FILENAME;
			logger.info("OPM.filename: "+OPMGraphFilename);			
		}
		return OPMGraphFilename;
	}
	
	
	protected void setUp() throws Exception {
		setDataSource();
		System.setProperty("raven.eclipse","true");
		pAccess = new ProvenanceAccess(ProvenanceConnectorType.MYSQL);  // creates and initializes the provenance API
		configureInterface();              // sets user-defined preferences
		
		if (derefValues) ic = pAccess.getInvocationContext();
	}

	protected  void setDataSource() {

		System.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.osjava.sj.memory.MemoryContextFactory");
		System.setProperty("org.osjava.sj.jndi.shared", "true");

		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		ds.setMaxActive(50);
		ds.setMinIdle(10);
		ds.setMaxIdle(50);
		ds.setDefaultAutoCommit(true);
		ds.setUsername(DB_USER);
		ds.setPassword(DB_PASSWD);

		try {
			ds.setUrl("jdbc:mysql://"+DB_URL_LOCAL+"/T2Provenance");

			InitialContext context = new InitialContext();
			context.rebind("jdbc/taverna", ds);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * set user-defined values for toggles on the API
	 */
	protected void configureInterface() {

		// do we need to return output processor values in addition to inputs?
		String returnOutputsPref = PropertiesReader.getString("query.returnOutputs");
		if (returnOutputsPref != null) {
			pAccess.toggleIncludeProcessorOutputs(Boolean.parseBoolean(returnOutputsPref));	
		}

		// do we need to record actual values as part of the OPM graph?
		String recordArtifacValuesPref = PropertiesReader.getString("OPM.recordArtifactValues");
		if (recordArtifacValuesPref != null) {			
			pAccess.toggleAttachOPMArtifactValues(Boolean.parseBoolean(recordArtifacValuesPref));
			logger.info("OPM.recordArtifactValues: "+ pAccess.isAttachOPMArtifactValues());
		}


		String computeOPMGraph = PropertiesReader.getString("OPM.computeGraph");
		if (computeOPMGraph != null) {
			pAccess.toggleOPMGeneration(Boolean.parseBoolean(computeOPMGraph));
			logger.info("OPM.computeGraph: "+pAccess.isOPMGenerationActive());			
		}

		
		// are we recording the actual (de-referenced) values at all?!
		// NOTE this is a client feature: the API only returns references. They are deref'd locally
		String derefValuesString = PropertiesReader.getString("query.returnDataValues");
		if (derefValuesString != null) {
			logger.info("query.returnDataValues: "+derefValuesString);
			derefValues = Boolean.parseBoolean(derefValuesString);
		}

	}





	/**
	 * writes the RDF/XML OPM string to file
	 * @param opmFilename
	 */
	private void saveOPMGraph(QueryAnswer answer, String opmFilename) {

		if (answer.getOPMAnswer_AsRDF() == null) {
			logger.info("save OPM graph: OPM graph was NOT generated.");
			return;
		}

		try {
			FileWriter fw= new FileWriter(new File(opmFilename));
			fw.write(answer.getOPMAnswer_AsRDF());
			fw.close();
		} catch (IOException e) {
			logger.warn("saveOPMGraph: error saving graph to file "+opmFilename);
			logger.warn(e.getMessage());
		}
		logger.info("OPM graph saved to "+opmFilename);
	}



	private void reportAnswer(QueryAnswer answer) {

		NativeAnswer nAnswer = answer.getNativeAnswer();

		// nAnswer contains a Map of the form 
		// 	Map<QueryVar, Map<String, List<Dependencies>>>  answer;

		Map<QueryVar, Map<String, List<Dependencies>>>  dependenciesByVar = nAnswer.getAnswer();	
		for (QueryVar v:dependenciesByVar.keySet()) {
			logger.info("dependencies for port: "+v.getPname()+":"+v.getVname()+":"+v.getPath());

			Map<String, List<Dependencies>> deps = dependenciesByVar.get(v);
			for (String path:deps.keySet()) {
				logger.info("dependencies on path "+path);
				for (Dependencies dep:deps.get(path)) {

					for (LineageQueryResultRecord record: dep.getRecords()) {
						
						// we now resolve values on the client, there are no values in the record
						// returned through the API
						record.setPrintResolvedValue(false);  
						logger.info(record.toString());
						
						// resolve reference if so desired
						if (derefValues) {
							T2Reference ref = ic.getReferenceService().referenceFromString(record.getValue());
							Object o = ic.getReferenceService().resolveIdentifier(ref, null, ic);
							
							logger.info("deref value for ref: "+ref+" "+o);
						}
					}
				}
			}
		}		
	}


}