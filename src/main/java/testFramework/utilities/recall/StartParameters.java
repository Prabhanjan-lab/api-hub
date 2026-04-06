/*    */ package testFramework.utilities.recall;
/*    */
/*    */

import de.audi.oru.recall.faultyvalues.EnumFaultyValues;
import org.kohsuke.args4j.Option;

import java.io.File;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class StartParameters
/*    */ {
/*    */   @Option(name="-s", usage="RECALL JSON schema file, e.g., c:\\temp\\recallSpecification_v0_9_0.schema.json or recallSpecification_v0_9_0.schema.json")
/* 19 */   private File inputSchemaFile = new File("recallSpecification_v0_9_0.schema.json");
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */   @Option(name="-i", usage="RECALL Spec Input File")
/* 26 */   private File inputFile = null;
/*    */
		   @Option(name="-f", usage="Optional parameter: Specifiy faulty data (e. g.: modelYearFaulty )")
		   private String faultyData = null;
/*    */
/*    */   public File getInputSchemaFile()
/*    */   {
/* 31 */     return this.inputSchemaFile;
/*    */   }
/*    */
/*    */   public void setInputSchemaFile(File inputSchemaFile)
/*    */   {
/* 36 */     this.inputSchemaFile = inputSchemaFile;
/*    */   }
/*    */
/*    */   public File getInputFile()
/*    */   {
/* 41 */     return this.inputFile;
/*    */   }
/*    */
/*    */   public void setInputFile(File inputFile)
/*    */   {
/* 46 */     this.inputFile = inputFile;
/*    */   }

		public String getFaultyData() {
			return this.faultyData;
		}

		public void setFaultyData(String value) {
			this.faultyData = EnumFaultyValues.valueOf(value).toString();
		}

/*    */ }


/* Location:              C:\downloads\recall_generator\generator-1.0-SNAPSHOT-jar-with-dependencies.jar!\de\audi\oru\recall\StartParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */