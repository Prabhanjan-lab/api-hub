/**
 * 
 */
package de.sulzer.pages.model;

/**
 * @author GCH9F5D
 *
 */
public class ModelLoggingDetail {

	private String sourceItComponent;
	private String importTimeStamp;
	private String importResult;
	private int importErrors; 
	private int deletedRecords;
	private int adaptedRecords;
	private int addedRecords;
	
	/**
	 * 
	 */
	public ModelLoggingDetail() {
		
		super();
		
		this.sourceItComponent = "";
		this.importTimeStamp = "";
		this.importResult = "";
	}
	
	/**
	 * @param sourceItComponent
	 * @param importTimeStamp
	 * @param importResult
	 * @param importErrors
	 * @param deletedRecords
	 * @param adaptedRecords
	 * @param addedRecords
	 */
	public ModelLoggingDetail(String sourceItComponent, String importTimeStamp, String importResult, int importErrors,
			int deletedRecords, int adaptedRecords, int addedRecords) {
		
		super();
		
		this.sourceItComponent = sourceItComponent;
		this.importTimeStamp = importTimeStamp;
		this.importResult = importResult;
		this.importErrors = importErrors;
		this.deletedRecords = deletedRecords;
		this.adaptedRecords = adaptedRecords;
		this.addedRecords = addedRecords;
	}
	
	/**
	 * @return the sourceItComponent
	 */
	public String getSourceItComponent() {
		return sourceItComponent;
	}
	
	/**
	 * @param sourceItComponent the sourceItComponent to set
	 */
	public void setSourceItComponent(String sourceItComponent) {
		this.sourceItComponent = sourceItComponent;
	}

	/**
	 * @return the importTimeStamp
	 */
	public String getImportTimeStamp() {
		return importTimeStamp;
	}
	
	/**
	 * @param importTimeStamp the importTimeStamp to set
	 */
	public void setImportTimeStamp(String importTimeStamp) {
		this.importTimeStamp = importTimeStamp;
	}
	
	/**
	 * @return the importResult
	 */
	public String getImportResult() {
		return importResult;
	}
	
	/**
	 * @param importResult the importResult to set
	 */
	public void setImportResult(String importResult) {
		this.importResult = importResult;
	}
	
	/**
	 * @return the importErrors
	 */
	public int getImportErrors() {
		return importErrors;
	}
	
	/**
	 * @param importErrors the importErrors to set
	 */
	public void setImportErrors(int importErrors) {
		this.importErrors = importErrors;
	}
	
	/**
	 * @return the deletedRecords
	 */
	public int getDeletedRecords() {
		return deletedRecords;
	}
	
	/**
	 * @param deletedRecords the deletedRecords to set
	 */
	public void setDeletedRecords(int deletedRecords) {
		this.deletedRecords = deletedRecords;
	}
	
	/**
	 * @return the adaptedRecords
	 */
	public int getAdaptedRecords() {
		return adaptedRecords;
	}
	
	/**
	 * @param adaptedRecords the adaptedRecords to set
	 */
	public void setAdaptedRecords(int adaptedRecords) {
		this.adaptedRecords = adaptedRecords;
	}
	
	/**
	 * @return the addedRecords
	 */
	public int getAddedRecords() {
		return addedRecords;
	}
	
	/**
	 * @param addedRecords the addedRecords to set
	 */
	public void setAddedRecords(int addedRecords) {
		this.addedRecords = addedRecords;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ModelLoggingDetail [sourceItComponent=" + sourceItComponent + ", importTimeStamp=" + importTimeStamp
				+ ", importResult=" + importResult + ", importErrors=" + importErrors + ", deletedRecords="
				+ deletedRecords + ", adaptedRecords=" + adaptedRecords + ", addedRecords=" + addedRecords + "]";
	}

}
